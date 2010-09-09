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

import junit.framework.TestCase;

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv1;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

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
		ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
		
		BuildContextSBSv1 context = new BuildContextSBSv1(sdk, "WINSCW", "UDEB");
		ISymbianSDK contextSDK = context.getSDK();
		sbsv1BuildInfo = (ISBSv1BuildInfo)contextSDK.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
		
		assertEquals(sdk.getUniqueId(), contextSDK.getUniqueId());
		
		// test that we can get the macros for valid SDKs 
		// (if the default of 0.0, then we never fetch macros)
		if (sbsv1BuildInfo != null && sdk.getOSVersion().compareTo(new Version(0, 0, 0)) > 0) {
			List<String> platMacros = sbsv1BuildInfo.getPlatformMacros("WINSCW");
			if (platMacros.size() == 0)
				fail("WINSCW platform macros should be > 0");
		}
	}
	
}
