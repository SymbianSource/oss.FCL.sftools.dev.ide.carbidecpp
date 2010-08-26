/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
* Test the BldInfViewPathHelper class.
*
*/
package com.nokia.carbide.cdt.builder.test;

import java.io.File;
import java.util.List;

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

public class SBSv2QueryTests extends BaseTest {
	
	//private boolean printTimingStats = true;
	
	private long startTime;
	
	//private final String SDK_ID1 = "Nokia_Symbian3_SDK_v0.9"; // SDK with additional aliases and products
	private final String SDK_ID1 = "K_92_WK12";
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Version sbsVersion = SDKCorePlugin.getSDKManager().getSBSv2Version(true);
		if ((sbsVersion.getMajor() == 2 && sbsVersion.getMinor() < 15) ||
			 sbsVersion.getMajor() < 2){
			
			fail("SBS Query tests require Raptor 2.15 or greater. Raptor version found is: " + sbsVersion);
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	/**
	 * Query data for a single configuration
	 * @throws Exception
	 */
	public void testQueryBuildConfigsFromSDK() throws Exception {
		
		SBSv2QueryUtils.removeAllCachedQueries();  // ensure sbs needs to build an entirely new cache
		
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(SDK_ID1, true);
		assertNotNull(sdk);
		
		ISDKBuildInfo sbsv2BuildInfo = sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
		assertNotNull(sbsv2BuildInfo);
		
		List<ISymbianBuildContext> buildContexts = sbsv2BuildInfo.getFilteredBuildConfigurations();
		// WINSCW UREL is filtered out from default b/c it does not exist for current test SDK
		assertEquals(5, buildContexts.size());
		
		for (ISymbianBuildContext context : buildContexts){
			assertTrue(context instanceof ISBSv2BuildContext);
			ISBSv2BuildContext sbsv2Context = (ISBSv2BuildContext)context;
			
			assertTrue(sbsv2Context.getVariantHRHDefines().size() > 0);
			if (sbsv2Context.getConfigQueryData().getConfigurationErrorMessage() == null || sbsv2Context.getConfigQueryData().getConfigurationErrorMessage().length() == 0){
				assertTrue((new File(sbsv2Context.getConfigQueryData().getMetaDataVariantHRH())).exists());
			} else {
				System.out.println("Config " + sbsv2Context.getSBSv2Alias() + " had error, cannot fully test: " + sbsv2Context.getConfigQueryData().getConfigurationErrorMessage());
			}
			
			assertTrue(sbsv2Context.getConfigQueryData().getTargettypes().size() > 0);
		}
	}
		
	/**
	 * This test should be run on individual %EPOCROOT% values
	 * Products are defined in the SDK
	 * @throws Exception
	 */
	public void testQueryProductsFromSDKs() throws Exception {
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(SDK_ID1, false);
		assertNotNull("Missing SDK on your system: " + SDK_ID1, sdk);
		List<String> productList = SBSv2QueryUtils.getProductVariantsForSDK(sdk);
		assertTrue(productList.size() > 0);
	}
	
	public String getTimingStats(){
		if (startTime != 0){
			long millisec = (System.currentTimeMillis() - startTime);
			long minutes = millisec / 1000 / 60;
			long seconds = (millisec / 1000) % 60;
			if (minutes > 0){
				return "\nTotal Time: " + minutes + " min, " + seconds + " sec\n";
			} else {
				if (seconds == 1){
					return "\nTotal Time: " + millisec + " msec\n";
				} else {
					return "\nTotal Time: " + seconds + " sec\n";
				}
			}
			
		} else {
			return "";
		}

	}
	
}
