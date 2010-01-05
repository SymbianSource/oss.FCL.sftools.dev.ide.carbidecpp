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

import java.util.List;

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

import junit.framework.TestCase;

/**
 * This test the creation and APIs of the Symbian build context class.
 * Much of this functionality is covered by com.nokia.carbide.cpp.build plugin
 */
public class SymbianContextTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSymbianBuildContext() throws Exception {
		
		List<ISymbianSDK> sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		assertNotNull(sdkList);
		assertTrue(sdkList.size() > 0);
		
		for (ISymbianSDK sdk : sdkList) {
			doTestSDK(sdk);
		}
		
	}

	/**
	 * @param sdk
	 */
	private void doTestSDK(ISymbianSDK sdk) {
		if (sdk.getName().equals("S60_5th_Edition_SDK_v1.0")) {
			// test that we get the SDK version
			Version sdkVer = sdk.getSDKVersion();
			assertEquals(5,sdkVer.getMajor());
			assertEquals(0,sdkVer.getMinor());
			
			// test that we get the OS version
			Version osVer = sdk.getOSVersion();
			assertEquals(9,osVer.getMajor());
			assertEquals(4,osVer.getMinor());
		}
		
		SymbianBuildContext context = new SymbianBuildContext(sdk, "WINSCW", "UDEB");
		ISymbianSDK contextSDK = context.getSDK();
		
		assertEquals(sdk, contextSDK);
		
		// test that we can get the macros for valid SDKs 
		// (if the default of 0.0, then we never fetch macros)
		if (sdk.getOSVersion().compareTo(new Version(0, 0, 0)) > 0) {
			List<String> platMacros = contextSDK.getPlatformMacros("WINSCW");
			if (platMacros.size() == 0)
				fail("WINSCW platform macros should be > 0");
		}
	}
	
}
