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
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianSDK;
import com.nokia.carbide.cpp.internal.sdk.core.xml.DevicesLoader;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

public class SDKCreationTest extends BaseDeviceModifierTest {
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCreateSDK() throws Exception {
		String devicesSkeleton = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<devices version=\"1.0\">\n</devices>";
	    FileUtils.writeFileContents(devicesFile, devicesSkeleton.toCharArray(), null);
		assertTrue(devicesFile.exists());
		
			DevicesType devicesType = DevicesLoader.loadDevices(devicesFile.toURI().toURL());
			EList devices = devicesType.getDevice();
			for (Iterator iter = devices.iterator(); iter.hasNext();) {
				SymbianSDK sdk = new SymbianSDK((DeviceType) iter.next());
				assertNotNull(sdk);
				ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
				// Test an SDK that exists and we know OS version
				if (sdk.getUniqueId().equals("UIQ3")){
					List<String> platMacros = sdk.getPlatformMacros("WINSCW");
					assertTrue(platMacros != null);
					assertEquals(3, platMacros.size());
					if (sbsv1BuildInfo != null) {
						assertTrue(sbsv1BuildInfo.getFilteredBuildConfigurations(sdk).size() > 0);
					}
				}
				// Test an SDK that does not exist. Check for proper null values
				else if (sdk.getUniqueId().equals("SDK_No_Exist")){
					if (sbsv1BuildInfo != null) {
						assertTrue(sbsv1BuildInfo.getSDKVersion(sdk).getMajor() == 0);
						assertTrue(sbsv1BuildInfo.getAvailablePlatforms(sdk).size() == 0);
						assertTrue(sbsv1BuildInfo.getFilteredBuildConfigurations(sdk).size() == 0);
					}
					assertTrue(sdk.getOSVersion().getMajor() == 0);
					assertTrue(sdk.getPlatformMacros("WINSCW").size() == 0);
					assertTrue(sdk.getSupportedTargetTypes().size() == 0);
					File epocRoot = new File(sdk.getEPOCROOT());
					assertTrue(epocRoot.exists() == false);
				}
			}
	}
}
