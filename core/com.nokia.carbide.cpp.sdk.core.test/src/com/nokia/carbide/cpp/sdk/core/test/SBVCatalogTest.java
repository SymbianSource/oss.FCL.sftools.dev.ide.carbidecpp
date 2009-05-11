/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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
	 * Test the catalog for new-style BSFs
	 * @throws Exception
	 */
	public void testSBVCatalog() throws Exception {
		setupForSDK(new Path("Data/var/group1"));
		ISBVPlatform[] platforms = catalog.getPlatforms();
		assertEquals(6, platforms.length);
		
		assertEquals("barney", platforms[0].getName());
		assertEquals("default", platforms[1].getName());
		assertEquals("dino79", platforms[2].getName());
		assertEquals("flintstone500", platforms[3].getName());
		assertEquals("fred99nhd", platforms[4].getName());
		assertEquals("wilma88", platforms[5].getName());
	}
	
	/**
	 * Test new-style Nokia BSF support, which establishes a hierarchy of BSF platforms
	 * and also supports additional levels of system include paths.
	 * @throws Exception
	 */
//	public void testNewStyleBSFSDK1() throws Exception {
//		setupForSDK(new Path("Data/var/group1"));
//		ISBVPlatform[] platforms = catalog.getPlatforms();
//		assertEquals(12, platforms.length);
//		
//		IBSFPlatform platform;
//		platform = catalog.findPlatform("ARM11");
//		assertNotNull(platform);
//		assertEquals("arm11", platform.getName());
//		assertEquals("ARMV5_ABIV1", platform.getCustomizedPlatformName());
//		assertNull(platform.getCustomizedPlatform()); // built-in
//		assertEquals(ETristateFlag.UNSPECIFIED, platform.getCompileWithParent());
//		
//		assertFalse(platform.isVariant());
//		assertFalse(platform.isVirtualVariant());
//		assertEquals(2, platform.getCustomizationOptions().size());
//		assertEquals("-Ono_known_library --enum_is_int --fpmode ieee_no_fenv --export_all_vtbl --no_vfe --APCS /inter --dllimport_runtime -O3 -Otime",
//				platform.getCustomizationOptions().get("INVARIANT_OPTIONS"));
//		assertEquals(ETristateFlag.UNSPECIFIED, platform.getCompileWithParent());
//
//		// no custom include dirs for this level
//		assertNull(platform.getSystemIncludePath());
//		assertEquals(0, platform.getSystemIncludePaths().length);
//
//		//////
//		
//		platform = catalog.findPlatform("variant");
//		assertNotNull(platform);
//		assertEquals("variant", platform.getName());
//		assertEquals("ARMV5", platform.getCustomizedPlatformName());
//		assertNull(platform.getCustomizedPlatform()); // built-in
//		assertFalse(platform.isVariant());
//		assertTrue(platform.isVirtualVariant());
//		assertEquals(ETristateFlag.ENABLED, platform.getCompileWithParent());
//		
//		assertEquals(0, platform.getCustomizationOptions().size());
//		
//		// no custom include dirs
//		assertNull(platform.getSystemIncludePath());
//		assertEquals(0, platform.getSystemIncludePaths().length);
//
//		//////
//		
//		platform = catalog.findPlatform("config");
//		assertNotNull(platform);
//		assertEquals("config", platform.getName());
//		// lowercase since it found the real platform
//		assertEquals("variant", platform.getCustomizedPlatformName());
//		
//		IBSFPlatform cust = platform.getCustomizedPlatform();
//		assertNotNull(cust);
//		assertEquals("variant", cust.getName());
//		
//		assertTrue(platform.isVariant());
//		assertFalse(platform.isVirtualVariant());
//		assertEquals(ETristateFlag.UNSPECIFIED, platform.getCompileWithParent());
//		
//		assertEquals(0, platform.getCustomizationOptions().size());
//		
//		// now it has an include dir
//		assertEquals(sdkIncludePath.append("config"), platform.getSystemIncludePath());
//		
//		// and the full list has only this entry
//		IPath[] systemIncludePaths = platform.getSystemIncludePaths();
//		assertEquals(1, systemIncludePaths.length);
//		assertEquals(sdkIncludePath.append("config"), systemIncludePaths[0]);
//
//		IBSFPlatform[] built = catalog.getAdditionalBuiltPlatforms("ncx51");
//		assertEquals(5, built.length);
//		assertTrue(findPlatform(built, "m_product"));
//		assertTrue(findPlatform(built, "r_product"));
//		assertTrue(findPlatform(built, "s_product"));
//		assertTrue(findPlatform(built, "a_product"));
//		assertTrue(findPlatform(built, "dev51"));
//		
//		built = catalog.getAdditionalBuiltPlatforms("armv5");
//		assertEquals(7, built.length);
//		assertTrue(findPlatform(built, "s_product"));
//		assertTrue(findPlatform(built, "ncx51"));
//		assertTrue(findPlatform(built, "config"));
//		assertFalse(findPlatform(built, "variant")); // virtuals not built
//	}	
	
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

	/**
	 * Test new-style Nokia BSF support, which establishes a hierarchy of BSF platforms
	 * and also supports additional levels of system include paths.
	 * @throws Exception
	 */
//	public void testNewStyleBSFSDK2() throws Exception {
//		setupForSDK(new Path("Data/bsf/NewStyleBSFSDK"));
//		IBSFPlatform[] platforms = catalog.getPlatforms();
//		assertEquals(12, platforms.length);
//		
//		IBSFPlatform platform;
//		platform = catalog.findPlatform("r_product");
//		assertNotNull(platform);
//		assertEquals("r_product", platform.getName());
//		assertEquals("ncx51", platform.getCustomizedPlatformName());
//		assertNotNull(platform.getCustomizedPlatform()); // built-in
//		assertEquals(ETristateFlag.UNSPECIFIED, platform.getCompileWithParent());
//		
//		assertTrue(platform.isVariant());
//		assertFalse(platform.isVirtualVariant());
//		assertEquals(0, platform.getCustomizationOptions().size());
//		assertEquals(ETristateFlag.UNSPECIFIED, platform.getCompileWithParent());
//
//		// now it has an include dir
//		assertEquals(sdkIncludePath.append("config").append("ncx51").append("r_product"), platform.getSystemIncludePath());
//		
//		// and the full list entries from deepest to shallowest
//		IPath[] systemIncludePaths = platform.getSystemIncludePaths();
//		assertEquals(3, systemIncludePaths.length);
//		assertEquals(sdkIncludePath.append("config").append("ncx51").append("r_product"), systemIncludePaths[0]);
//		assertEquals(sdkIncludePath.append("config").append("ncx51"), systemIncludePaths[1]);
//		assertEquals(sdkIncludePath.append("config"), systemIncludePaths[2]);
//		
//		IBSFPlatform[] built = catalog.getAdditionalBuiltPlatforms("r_product");
//		assertEquals(0, built.length);
//		
//		built = catalog.getAdditionalBuiltPlatforms("variant");
//		assertEquals(7, built.length);
//		// spot test
//		assertTrue(findPlatform(built, "config"));
//		assertTrue(findPlatform(built, "r_product"));
//
//	}	
	
//	/** The #getReleasePlatform() API should point to the first non-variant platform in the 
//	 * BSF tree.  Before (boog 5132) it was going all the way to the root.
//	 * @throws Exception
//	 */
//	public void testReleasePlatformBug5132() throws Exception {
//		setupForSDK(new Path("Data/bsf/S60_50_BSFs"));
//		
//		assertEquals("armv6", catalog.getReleasePlatform("armv6"));
//		assertEquals("ARMV5", catalog.getReleasePlatform("config"));
//		assertEquals("ARMV5", catalog.getReleasePlatform("variant"));
//		assertEquals("arm9e", catalog.getReleasePlatform("arm9e"));
//		assertEquals("ARMV5", catalog.getReleasePlatform("t_product"));
//		assertEquals("arm11", catalog.getReleasePlatform("arm11"));
//		assertEquals("gccev6t2", catalog.getReleasePlatform("gccev6t2"));
//		
//	}
}
