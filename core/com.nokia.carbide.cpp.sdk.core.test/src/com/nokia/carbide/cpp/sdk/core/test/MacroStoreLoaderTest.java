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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.SymbianMacroStore;
import com.nokia.carbide.cpp.internal.sdk.core.model.BSFCatalogFactory;
import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;

public class MacroStoreLoaderTest extends TestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void testOSPlatformMacros() throws Exception {
		SymbianMacroStore macroStore = SymbianMacroStore.getInstance();
		assertNotNull(macroStore);
		
		List<String> macroStoreList = new ArrayList<String>();
		macroStoreList = macroStore.getPlatformMacros(Version.parseVersion("9.1"), "", null, "WINSCW");
		assertTrue(macroStoreList.size() > 0);
		/*System.out.print("\n--------------------\n");
		System.out.print("*** Platform Macros for OS 9.1 / WINSCW\n--------------------\n\t");
		
			for (String macro : macroStoreList){
				System.out.print("\t" + macro);
			}*/
	}
	
	public void testSupportedOSVersions(){
		SymbianMacroStore macroStore = SymbianMacroStore.getInstance();
		assertNotNull(macroStore);
		
		List<String> osList = new ArrayList<String>();
		osList = macroStore.getSupportedOSVersions();
		assertTrue(osList.size() != 0);
		
		/*System.out.print("\n\n--------------------\n");
		System.out.print("*** Supported OS Versions in the Macro Store\n--------------------\n\t");
		
		for (String osStr : osList){
			System.out.print("  " + osStr);
		}*/
	}
	
	public void testSupportedPlatforms(){
		SymbianMacroStore macroStore = SymbianMacroStore.getInstance();
		assertNotNull(macroStore);
		List<String> platList = macroStore.getSupportedPlatforms(Version.parseVersion("9.2"), "");
		
		assertNotNull(platList);
		assertTrue(platList.size() > 0);
		
		/*System.out.print("\n\n--------------------\n");
		System.out.print("*** Supported Platforms for OS 9.2\n--------------------\n\t");
		
		for (String plat : platList){
			System.out.print("  " + plat);
		}*/
		
	}
	
	public void testSDKVersions(){
		SymbianMacroStore macroStore = SymbianMacroStore.getInstance();
		assertNotNull(macroStore);
		List<String> sdkVersions = macroStore.getSDKVersions();
		
		// just test that all the available versions can make a valid version object
		// and all major version are > 0
		for (String currVerStr : sdkVersions){
			Version ver = new Version(currVerStr);
			assertTrue(ver.getMajor() > 0);
		}
	}
	
	public void testVendorMacros(){
		SymbianMacroStore macroStore = SymbianMacroStore.getInstance();
		assertNotNull(macroStore);
		
		List<String> vendorMacroList = new ArrayList<String>();
		vendorMacroList = macroStore.getVendorMacros(Version.parseVersion("3.0"), "com.nokia.s60");
		assertEquals(2, vendorMacroList.size());
		
		vendorMacroList = macroStore.getVendorMacros(Version.parseVersion("3.1"), "com.nokia.s60");
		assertEquals(2, vendorMacroList.size());
		
		vendorMacroList = macroStore.getVendorMacros(Version.parseVersion("3.2"), "com.nokia.s60");
		assertEquals(3, vendorMacroList.size());
		
		vendorMacroList = macroStore.getVendorMacros(Version.parseVersion("5.0"), "com.nokia.s60");
		assertEquals(4, vendorMacroList.size());
		
		// make sure non-existant SDK returns list size zero
		vendorMacroList = macroStore.getVendorMacros(Version.parseVersion("3.9"), "com.fudge.bar");
		assertEquals(0, vendorMacroList.size());
		
	}
	
	public void testForUnsupportedVersions(){
		SymbianMacroStore macroStore = SymbianMacroStore.getInstance();
		assertNotNull(macroStore);
		
		List<String> macroStoreList = new ArrayList<String>();
		macroStoreList = macroStore.getPlatformMacros(Version.parseVersion("9.1"), "x", null, "POOP");
		assertEquals(0, macroStoreList.size());
		
		List<String> vendorMacroList = new ArrayList<String>();
		vendorMacroList = macroStore.getVendorMacros(Version.parseVersion("5.1"), "com.foo.bar");
		assertEquals(0, vendorMacroList.size());
		
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

	public void testBSFVariantMacros() throws Exception {
		SymbianMacroStore macroStore = SymbianMacroStore.getInstance();
		assertNotNull(macroStore);
	
		IPath sdkPath = new Path(pluginRelativeFile("Data/bsf/NewStyleBSFSDK").getAbsolutePath());
		IPath sdkIncludePath = sdkPath.append("epoc32").append("include");
		IBSFCatalog catalog = BSFCatalogFactory.createCatalog(sdkPath, sdkIncludePath);
		
		List<String> basePlatformMacros = macroStore.getPlatformMacros(
				new Version("9.1"), "", catalog, "ARMV5");
		assertTrue(basePlatformMacros.size() > 0);
		
		// ensure R_PRODUCT drills down to ARMV5 and returns a superset with each variant macro added
		List<String> platformMacros = macroStore.getPlatformMacros(
				new Version("9.1"), "", catalog, "R_PRODUCT");
		assertTrue(platformMacros.contains("R_PRODUCT"));
		assertTrue(platformMacros.contains("NCX51"));
		assertTrue(platformMacros.contains("CONFIG"));
		assertTrue(platformMacros.contains("VARIANT"));
		assertTrue(platformMacros.containsAll(basePlatformMacros));
		platformMacros.removeAll(basePlatformMacros);
		assertEquals(4, platformMacros.size());
	}
}
