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
package com.nokia.carbide.cpp.project.core.tests;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.api.sdk.SDKManagerInternalAPI;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContext;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.*;

public class MissingSdkTest extends TestCase {
	
	private static final String BLD_INF = "bld.inf";
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testMissingSdk () throws Exception {
		
		File devicesFile = null;
		File backupFile = null;
		if (SBSv2Utils.enableSBSv1Support()) {
			devicesFile = SDKCorePlugin.getSDKManager().getDevicesXMLFile(); 
			assertNotNull("Devices.xml file is NULL!", devicesFile);
			assertTrue(devicesFile.toString() + " file doesn't exist", devicesFile.exists());
			backupFile = new File(devicesFile.toString() + ".backup");
		}
		
		try {
			ISDKManager sdkManager = SDKCorePlugin.getSDKManager();
			List<ISymbianSDK> sdkList = sdkManager.getSDKList();
			assertNotNull(sdkList);
	
			if (devicesFile != null && backupFile != null) {
				if (backupFile.exists()) {
					backupFile.delete();
				}
				backupFile.createNewFile();
			
				// backup devices.xml
				copyFile(devicesFile, backupFile);
			}
			
			IProject project = null;
			ISymbianSDK lastSdkFound = null;
			try {
				project = ProjectCorePlugin.createProject("missingsdk", null);
			
				assertNotNull(project);
				
				// put all configs among SDKs in devices.xml				
				List<ISymbianBuildContext> allConfigs = new ArrayList<ISymbianBuildContext>();
				for (ISymbianSDK sdk : sdkList) {
					List<ISymbianBuildContext> projectConfigs = sdk.getFilteredBuildConfigurations();
					if (projectConfigs == null)
						continue;
					if (projectConfigs.size() <= 0)
						continue;
					allConfigs.addAll(projectConfigs);
					lastSdkFound = sdk;
				}
				assertTrue(allConfigs.size() > 0);
				ProjectCorePlugin.postProjectCreatedActions(project, BLD_INF, allConfigs, new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());
			} catch (CoreException e) {;
			}
			
			// remove last of the SDK with configs in devices.xml
			sdkManager.removeSDK(lastSdkFound.getUniqueId());
	
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			assertNotNull(cpi);
			
			// check we only have those configs in the removed SDK in the missing list
			List<ICarbideBuildConfiguration> configList = cpi.getBuildConfigurations();
			int badCount = 0;
			for (ICarbideBuildConfiguration config: configList) {				
				if (SDKManagerInternalAPI.getMissingSdk(SymbianBuildContext.getSDKIDFromConfigName(config.getDisplayString())) != null) {
					badCount++;
				}
			}
			// we only remove the first
			assertTrue(badCount == lastSdkFound.getFilteredBuildConfigurations().size());
				
		} finally {
			if (backupFile != null) {
				// restore devices.xml
				try {
					copyFile(backupFile, devicesFile);
				} catch (Exception e) {
					fail(e.getMessage());
				}
				backupFile.delete();
			}
		}
		
	}

	private File pluginRelativeFile(String file) throws IOException {
		Bundle bundle = TestsPlugin.getDefault().getBundle();
		URL url = FileLocator.find(bundle, new Path("."), null);
		if (url == null)
			fail("could not make URL from bundle " + bundle + " and path " + file);
		url = FileLocator.resolve(url);
		TestCase.assertEquals("file", url.getProtocol());
		return new File(url.getPath(), file);
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

}
