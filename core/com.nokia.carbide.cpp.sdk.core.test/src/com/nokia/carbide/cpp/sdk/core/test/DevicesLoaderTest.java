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

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType;
import com.nokia.carbide.cpp.internal.sdk.core.xml.DevicesLoader;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SymbianSDKFactory;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.HostOS;

/**
 * NOTE: this test expects all three methods to be run in order.
 *
 */
public class DevicesLoaderTest extends BaseDeviceModifierTest {
	
	private static final String UIQ3_SDKID = "UIQ3";
	private static final String S60_SDKID = "S60_CustKit";
	private static final String TV_SDKID = "TV_CustKit";
	private static final String UIQ3_EPOCROOT = HostOS.IS_WIN32 ? "C:\\Symbian\\UIQ3SDK\\" : "/opt/symbian/UIQ3SDK/";
	private static final String M_DRIVE = HostOS.IS_WIN32 ? "m:\\" : "/media/M/";
	private static final String P_DRIVE = HostOS.IS_WIN32 ? "p:\\" : "/media/P/";
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddNewDeviceEntries() throws Exception {
		String devicesSkeleton = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<devices version=\"1.0\">\n</devices>";
	    FileUtils.writeFileContents(devicesFile, devicesSkeleton.toCharArray(), null);
		assertTrue(devicesFile.exists());
		
		Version osVersion = new Version("9.1");
		Version sdkVersion = new Version("3.0");
		ISymbianSDK sdk = SymbianSDKFactory.createInstance(S60_SDKID, M_DRIVE, ISBSv1BuildInfo.SERIES60_SDK_NAME, osVersion, sdkVersion);
		DevicesLoader.updateDevice(sdk, devicesFile.toURI().toURL());
		
		sdk = SymbianSDKFactory.createInstance(TV_SDKID, P_DRIVE, ISBSv1BuildInfo.TECHVIEW_SDK_NAME, osVersion, sdkVersion);
		DevicesLoader.updateDevice(sdk, devicesFile.toURI().toURL());
		
		sdk = SymbianSDKFactory.createInstance(UIQ3_SDKID, UIQ3_EPOCROOT, ISBSv1BuildInfo.UIQ_SDK_NAME, osVersion, sdkVersion);
		DevicesLoader.updateDevice(sdk, devicesFile.toURI().toURL());	
	}
	
	
	
	public void testDevicesLoader() throws Exception {
		DevicesType devicesType = DevicesLoader.loadDevices(devicesFile.toURI().toURL());
		EList devices = devicesType.getDevice();
		assertEquals(3, devices.size());
		for (Iterator iter = devices.iterator(); iter.hasNext();) {
			DeviceType device = (DeviceType) iter.next();
			
			// make sure there's no failures with reading back in
			// either "uderdeletetable" attribute (incorrect output from CW)
			// or the corret "userdeletable" form. (boog 3125)
			device.setUserdeletetable("no");
			device.setUserdeletable("no");
			if (!iter.hasNext()) {
				String epocroot = device.getEpocroot();
				if (!epocroot.equalsIgnoreCase(UIQ3_EPOCROOT) &&	// this filepath is canonicalized, so it depends on the filesystem's case, which may vary
					!epocroot.equalsIgnoreCase(M_DRIVE)       &&
					!epocroot.equalsIgnoreCase(P_DRIVE)         ){
					
					fail();
				}
			}
		}
	}

	
	public void testDeleteDevice() throws Exception{
		DevicesType devicesType = DevicesLoader.loadDevices(devicesFile.toURI().toURL());
		EList devices = devicesType.getDevice();
		assertEquals(3, devices.size());
		for (Iterator iter = devices.iterator(); iter.hasNext();) {
			DeviceType device = (DeviceType) iter.next();
			if (device.getId().equals(UIQ3_SDKID)){
				DevicesLoader.deleteDeviceEntry(device, devicesFile.toURI().toURL());
				break;
			}
		}
		
		// read the file back in...
		devicesType = DevicesLoader.loadDevices(devicesFile.toURI().toURL());
		devices = devicesType.getDevice();
		assertEquals(2, devices.size());
	}
}
