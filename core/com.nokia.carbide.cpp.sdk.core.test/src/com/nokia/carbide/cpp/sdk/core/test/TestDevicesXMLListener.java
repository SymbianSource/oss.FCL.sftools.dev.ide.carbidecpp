/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/
package com.nokia.carbide.cpp.sdk.core.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;

import com.nokia.carbide.cpp.internal.api.sdk.ICarbideDevicesXMLChangeListener;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

/**
 * Tests the ICarbideDevicesXMLChangeListener class
 */
public class TestDevicesXMLListener extends TestCase {
	
	private static final String devicesTestFile = "Data/xml/devicesXMLListenerTest.xml";
	
	boolean outOfSyncListenerWasTested = false;
	
	protected ICarbideDevicesXMLChangeListener listener;
			
	// First thing we have to do is actually create a project in a workspace...
	protected void setUp() throws Exception {
		
		listener = new ICarbideDevicesXMLChangeListener() {

			public void devicesXMLOutOfSync() {
				//System.out.println("Synchronized event detected");
				outOfSyncListenerWasTested = true;
			}
		};
			

		ISDKManagerInternal sdkDevicesXMLListener = (ISDKManagerInternal)SDKCorePlugin.getSDKManager();
		sdkDevicesXMLListener.addDevicesXMLChangeListener(listener);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		ISDKManagerInternal sdkDevicesXMLListener = (ISDKManagerInternal)SDKCorePlugin.getSDKManager();
		sdkDevicesXMLListener.removeDevicesXMLChangeListener(listener);
	}
	
	// Here we just rescan the sdks and the listener will perform the test
	public void testCarbideConfigurationChangedListener() throws Exception{
		 
		if (!SBSv2Utils.enableSBSv1Support())
			return;
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		
		// get devics.xml and make a backup copy
		File devicesFile = SDKCorePlugin.getSDKManager().getDevicesXMLFile(); 
		assertNotNull("Devices.xml file is NULL!", devicesFile);
		assertTrue(devicesFile.toString() + "file doesn't exist", devicesFile.exists());
		File backupFile = new File(devicesFile.toString() + ".backup");
		
		if (backupFile.exists()) {
			backupFile.delete();
		}
		backupFile.createNewFile();
		// backup devices.xml
		copyFile(devicesFile, backupFile);
		

		
		// Here we just changed the time stamp and not the contents so it should be OK.
		assertTrue("Devices.xml has changed only timestamp, bad return value", ((SDKManager)sdkMgr).checkDevicesXMLSynchronized());
		
		ISymbianSDK sdk = sdkMgr.getSDKList().get(0);
		((SymbianSDK)sdk).setEPOCROOT("K:\\");
		sdkMgr.updateSDK(sdk);
		assertTrue("Devices.xml should still be true with sdk update via APIs", ((SDKManager)sdkMgr).checkDevicesXMLSynchronized());
		
		// copy a different devices.xml file over.
		copyFile (pluginRelativeFile(devicesTestFile), devicesFile);
		
		assertFalse("Devices.xml has changed changed content, should reutrn false. ", ((SDKManager)sdkMgr).checkDevicesXMLSynchronized());
		
		ISDKManagerInternal sdkMgrInternal = (ISDKManagerInternal)sdkMgr;
		sdkMgrInternal.fireDevicesXMLChanged();
		
		if (backupFile != null) {
			// restore devices.xml
			try {
				copyFile(backupFile, devicesFile);
			} catch (Exception e) {
				fail(e.getMessage());
			}
			backupFile.delete();
		}
		
		assertTrue("devices.xml change was not detected properly", outOfSyncListenerWasTested);
		
	}
	
	public void copyFile(File in, File out) throws Exception {
		FileInputStream fis  = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;
		while((i=fis.read(buf))!=-1) {
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}
	
	private File pluginRelativeFile(String file) throws IOException {
		Bundle bundle = TestPlugin.getDefault().getBundle();
		URL url = FileLocator.find(bundle, new Path("."), null);
		if (url == null)
			fail("could not make URL from bundle " + bundle + " and path " + file);
		url = FileLocator.resolve(url);
		TestCase.assertEquals("file", url.getProtocol());
		return new File(url.getPath(), file);
	}
	
}
