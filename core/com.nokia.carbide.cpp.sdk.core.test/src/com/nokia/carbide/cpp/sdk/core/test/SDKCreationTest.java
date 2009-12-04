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

import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianSDK;
import com.nokia.carbide.cpp.internal.sdk.core.xml.DevicesLoader;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.emf.common.util.EList;

import java.io.File;
import java.util.Iterator;
import java.util.List;

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
				// Test an SDK that exists and we know OS version
				if (sdk.getUniqueId().equals("UIQ3")){
					List<String> platMacros = sdk.getPlatformMacros("WINSCW");
					assertTrue(platMacros != null);
					assertEquals(3, platMacros.size());
					assertTrue(sdk.getFilteredBuildConfigurations().size() > 0);
				}
				// Test an SDK that does not exist. Check for proper null values
				else if (sdk.getUniqueId().equals("SDK_No_Exist")){
					assertTrue(sdk.getOSVersion().getMajor() == 0);
					assertTrue(sdk.getSDKVersion().getMajor() == 0);
					assertTrue(sdk.getPlatformMacros("WINSCW").size() == 0);
					assertTrue(sdk.getProjectVariantHRHMacros().size() == 0);
					assertTrue(sdk.getAvailablePlatforms().size() == 0);
					assertTrue(sdk.getFilteredBuildConfigurations().size() == 0);
					assertTrue(sdk.getSupportedTargetTypes().size() == 0);
					File epocRoot = new File(sdk.getEPOCROOT());
					assertTrue(epocRoot.exists() == false);
				}
			}
	}
}
