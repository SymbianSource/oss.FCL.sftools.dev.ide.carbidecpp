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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.ISBSv2ConfigData;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.ISBSv2QueryData;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

public class SBSv2QueryTests extends BaseTest {
	
	private boolean printTimingStats = true;
	
	private static ISBSv2QueryData sbsAliasBaseQuery;
	
	private long startTime;
	
	private final String SDK_ID1 = "K_92_WK12"; // SDK with additional aliases and products
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Version sbsVersion = SDKCorePlugin.getSDKManager().getSBSv2Version(true);
		if ((sbsVersion.getMajor() == 2 && sbsVersion.getMinor() < 14) ||
			 sbsVersion.getMajor() < 2){
			
			fail("SBS Query tests require Raptor 2.14 or greater. Raptor version found is: " + sbsVersion);
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testQueryAliases() throws Exception {
		
		startTime = System.currentTimeMillis();
		if (sbsAliasBaseQuery == null){
			sbsAliasBaseQuery = SBSv2QueryUtils.queryFilteredConfigsForSDK(null);
		}
		
		if (printTimingStats)
			System.out.println("Time for testQueryProductsFromSDKs(): " + getTimingStats());
		
		HashMap<String, ISBSv2ConfigData> baseConfigs = sbsAliasBaseQuery.getBaseSBSConfigurations();
		
		for (String aliasKey : baseConfigs.keySet()){
			ISBSv2ConfigData config = baseConfigs.get(aliasKey);
			boolean isBaseConfig = config.getSupportingSDK() == null ? true : false;
			assertTrue("Configuration should be true : " + config.toString(), isBaseConfig);
		}
		assertEquals(18, baseConfigs.size());
		
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias(null, "armv5_udeb"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias(null, "armv5_urel"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias(null, "winscw_udeb"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias(null, "winscw_urel"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias(null, "armv5_urel_gcce4_4_1"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias(null, "armv5_udeb_gcce4_4_1"));
		
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning(null, "arm.v5.udeb.rvct2_2"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning(null, "arm.v5.urel.rvct2_2"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning(null, "winscw_base.winscw_debug"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning(null, "winscw_base.winscw_release"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning(null, "arm.v5.urel.gcce4_4_1"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning(null, "arm.v5.udeb.gcce4_4_1"));
		
		assertNull(sbsAliasBaseQuery.getSBSConfigByAlias(null, "armv5_udeb.foobar"));
		assertNull(sbsAliasBaseQuery.getSBSConfigByMeaning(null, "arm.v5.udeb.foo.bar"));
		
		// Get the Raptor configs that are defined in an SDK
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(SDK_ID1, false);
		assertNotNull("Missing SDK on your system: " + SDK_ID1, sdk);
		List<ISBSv2ConfigData> sdkSpecificConfigs = sbsAliasBaseQuery.getSDKSpecificConfigData(sdk);
		assertEquals(10, sdkSpecificConfigs.size());
		
		// Get the union of the base Raptor configs and the SDK
		List<ISBSv2ConfigData> allSDKConfigUnion = sbsAliasBaseQuery.getConfigsForSDK(sdk);
		assertEquals(28, allSDKConfigUnion.size());
	}
	
	/**
	 * Query data for a single configuration
	 * @throws Exception
	 */
	public void testQuerySingleConfig() throws Exception {
		
		assertNotNull(sbsAliasBaseQuery);

		startTime = System.currentTimeMillis();
		
		ISBSv2ConfigData config = sbsAliasBaseQuery.getSBSConfigByAlias(null,  "armv5_udeb");
		assertNotNull(config);
		assertEquals("/epoc32/release/armv5/udeb", config.getReleaseDirectory());
		assertEquals("armv5", config.getTraditionalPlatform());
		assertEquals("udeb", config.getTraditionalTarget());
		
		config = sbsAliasBaseQuery.getSBSConfigByAlias(null, "armv5_udeb_gcce");
		assertNull(config); // This config should only be defined by SDK suppliers
		
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(SDK_ID1, false);
		assertNotNull("Missing SDK on your system: " + SDK_ID1, sdk);
		config = sbsAliasBaseQuery.getSBSConfigByAlias(sdk, "armv5_udeb_gcce");
		assertNotNull(config); // This config should only be defined by SDK suppliers
		
		// TODO: This should fail if 'SBS_GCCE432BIN is not set
		// So we should have one test that will fail with known error message
		// and another that will pass when an env var is set correctly.
		// e.g. test two different versions of GCCE aliases.
//		assertEquals("/epoc32/release/armv5/udeb", config.getReleaseDirectory(null));
//		assertEquals("armv5", config.getTraditionalPlatform(null));
//		assertEquals("udeb", config.getTraditionalTarget(null));
	}
	

	/**
	 * TODO: The SBS API FOR THIS QUERY IS UNDER CONSTRUCTION.....
	 * TODO; This test should be run on individual %EPOCROOT% values
	 * @throws Exception
	 */
	public void testQueryMultipleConfigs() throws Exception {
		
		assertNotNull(sbsAliasBaseQuery);
		
		startTime = System.currentTimeMillis();
		
		
		List<String> aliasOrMeaningArray = new ArrayList<String>();
		aliasOrMeaningArray.add("armv5_udeb");
		aliasOrMeaningArray.add("arm.9e.udeb.rvct2_2");
		HashMap<String, String> releaseMap = SBSv2QueryUtils.queryConfigTargetInfo(null, aliasOrMeaningArray);
		
		assertEquals(2, releaseMap.size());
		
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(SDK_ID1, false);
		assertNotNull("Missing SDK on your system: " + SDK_ID1, sdk);
		releaseMap = SBSv2QueryUtils.queryConfigTargetInfo(sdk, aliasOrMeaningArray);
		assertEquals(2, releaseMap.size());
//		String queryResult = getSBSQueryOutput(argList, null);
//		assertTrue("No output found from " + SBSv2QueryUtils.QUERY_CONFIG_COMMAND, queryResult.length() > 0);
//		
//		HashMap<String, String> outputMap = parseQueryConfigResults(queryResult);
//		assertTrue("No configs were found in query for : " + SBSv2QueryUtils.QUERY_CONFIG_COMMAND, outputMap.size() > 0);
//		
		if (printTimingStats)
			System.out.println("Time for testQueryProductsFromSDKs(): " + getTimingStats());
//		
	}
	
	/**
	 * This test should be run on individual %EPOCROOT% values
	 * Products are defined in the SDK
	 * @throws Exception
	 */
	public void testQueryProductsFromSDKs() throws Exception {
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(SDK_ID1, false);
		assertNotNull("Missing SDK on your system: " + SDK_ID1, sdk);
		List<String> products = sbsAliasBaseQuery.getProductsForSDK(sdk);
		assertEquals(19, products.size());
	}
	
	public void testStressQueryTest() throws Exception {

		long stressTestStartTime = System.currentTimeMillis();
		
		testQueryAliases();
		
		testQueryMultipleConfigs();
		testQueryMultipleConfigs();
		testQueryMultipleConfigs();
		testQueryMultipleConfigs();
		testQueryMultipleConfigs();
		testQueryMultipleConfigs();
		testQueryMultipleConfigs();
		testQueryMultipleConfigs();
		testQueryMultipleConfigs();
		testQueryMultipleConfigs();
		
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		testQueryProductsFromSDKs();
		
		startTime = stressTestStartTime;
		
		if (printTimingStats)
			System.out.println("Time for testStressQueryTest(): " + getTimingStats());
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
