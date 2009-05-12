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

import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.runtime.*;

import com.nokia.carbide.cpp.internal.sdk.core.model.SBVCatalogFactory;
import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISBVPlatform;


public class SBVCatalogTest extends TestCase {

	private IPath sdkPath;
	private IPath sdkIncludePath;
	private ISBVCatalog catalog;

	private void setupForSDK(IPath relativeSdkPath) throws Exception {
		if (Platform.isRunning()) {
			URL url = FileLocator.find(TestPlugin.getDefault().getBundle(), relativeSdkPath, null);
			URL fileURL = FileLocator.toFileURL(url);
			sdkPath = new Path(fileURL.getPath());
		} else {
			sdkPath = relativeSdkPath;
		}
		sdkIncludePath = sdkPath.append("epoc32/include");
		
		catalog = SBVCatalogFactory.createCatalog(sdkPath, sdkIncludePath);
		assertNotNull(catalog);
	}
	
	public void testSDKScan() throws Exception {
		setupForSDK(new Path("Data"));
		assertEquals(0, catalog.getPlatforms().length);
		assertNull(catalog.findPlatform("ARMV5"));
	}
	
	/**
	 * Test that the correct SBV platforms are created
	 * @throws Exception
	 */
	public void testSBVCatalogCreation() throws Exception {
		setupForSDK(new Path("Data/var/group1"));
		ISBVPlatform[] platforms = catalog.getPlatforms();
		assertEquals(7, platforms.length);
		
		assertEquals("barney", platforms[0].getName());
		assertEquals("default", platforms[1].getName());
		assertEquals("dino79", platforms[2].getName());
		assertEquals("flintstone500", platforms[3].getName());
		assertEquals("fred99nhd", platforms[4].getName());
		assertEquals("variants", platforms[5].getName());
		assertEquals("differentname", platforms[6].getName()); // filename is wilma88, but test that we us the VARIANT keyword
	}
	
	/**
	 * Test SBV support, which establishes a hierarchy of SBV platforms
	 * and also supports additional levels of system include paths.
	 * @throws Exception
	 */
	public void testSBVDataView() throws Exception {
		setupForSDK(new Path("Data/var/group1"));
		ISBVPlatform[] platforms = catalog.getPlatforms();
		assertEquals(7, platforms.length);
		
		ISBVPlatform platform;
		platform = catalog.findPlatform("BARNEY");
		assertNotNull(platform);
		assertEquals("barney", platform.getName());
		assertEquals("FLINTSTONE500", platform.getExtendedVariantName().toUpperCase());
		
		assertFalse(platform.isVirtual());
		
		platform = catalog.findPlatform(platform.getExtendedVariantName());
		assertNotNull(platform);
		assertTrue(platform.isVirtual());
		assertEquals("VARIANTS", platform.getExtendedVariantName().toUpperCase());
		
		assertNotNull(platform.getBuildIncludePaths());
		assertEquals(3, platform.getBuildIncludePaths().size());
		
		assertTrue("Didn't get expected VARIANT_HRH value", platform.getBuildVariantHRHFile().toPortableString().contains("/epoc32/include/feature_settings.hrh"));
		
		// test null platform
		platform = catalog.findPlatform("wilma88");
		assertNull(platform);
		
		// test build include paths
		platform = catalog.findPlatform("dino79");
		assertNotNull(platform);
		assertEquals(4, platform.getBuildIncludePaths().size());
		
		List<IPath> incPaths = platform.getBuildIncludePaths();
		for (IPath path : incPaths){
			System.out.println("Include path for dino79 bianry variant: " + path.toOSString());
		}
		
		assertEquals(16, platform.getROMBuildIncludePaths().size());
		
	}	
	
	/**
	 * @param built
	 * @param string
	 * @return
	 */
	private boolean findPlatform(ISBVPlatform[] platforms, String string) {
		for (ISBVPlatform platform : platforms)
			if (platform.getName().equalsIgnoreCase(string))
				return true;
		return false;
	}

}
