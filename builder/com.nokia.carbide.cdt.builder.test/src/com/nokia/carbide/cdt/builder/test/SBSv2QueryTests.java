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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.Version;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cdt.builder.test.sandbox.ISBSv2ConfigData;
import com.nokia.carbide.cdt.builder.test.sandbox.ISBSv2QueryData;
import com.nokia.carbide.cdt.builder.test.sandbox.SBSv2QueryUtils;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class SBSv2QueryTests extends BaseTest {
	
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
			sbsAliasBaseQuery = SBSv2QueryUtils.queryAliasAndProductVariants();
		}
		System.out.println("Time for testQueryProductsFromSDKs(): " + getTimingStats());
		
		List<ISBSv2ConfigData> baseConfigs = sbsAliasBaseQuery.getBaseSBSConfigurations();
		
		for (ISBSv2ConfigData config : baseConfigs){
			assertTrue("Configuration should be true : " + config.toString(), config.isBaseConfig());
		}
		assertEquals(18, baseConfigs.size());
		
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias("armv5_udeb"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias("armv5_urel"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias("winscw_udeb"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias("winscw_urel"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias("armv5_urel_gcce4_4_1"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByAlias("armv5_udeb_gcce4_4_1"));
		
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning("arm.v5.udeb.rvct2_2"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning("arm.v5.urel.rvct2_2"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning("winscw_base.winscw_debug"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning("winscw_base.winscw_release"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning("arm.v5.urel.gcce4_4_1"));
		assertNotNull(sbsAliasBaseQuery.getSBSConfigByMeaning("arm.v5.udeb.gcce4_4_1"));
		
		assertNull(sbsAliasBaseQuery.getSBSConfigByAlias("armv5_udeb.foobar"));
		assertNull(sbsAliasBaseQuery.getSBSConfigByMeaning("arm.v5.udeb.foo.bar"));
		
		// Get the Raptor configs that are defined in an SDK
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(SDK_ID1, false);
		assertNotNull("Missing SDK on your system: " + SDK_ID1, sdk);
		List<ISBSv2ConfigData> sdkSpecificConfigs = sbsAliasBaseQuery.getSDKSpecificConfigData(sdk);
		assertEquals(10, sdkSpecificConfigs.size());
		
		// Get the union of the base Raptor configs and the SDK
		List<ISBSv2ConfigData> allSDKConfigUnion = sbsAliasBaseQuery.getAllConfigurationsForSDK(sdk);
		assertEquals(28, allSDKConfigUnion.size());
	}
	
	/**
	 * Query data for a single configuration
	 * @throws Exception
	 */
	public void testQuerySingleConfig() throws Exception {
		
		assertNotNull(sbsAliasBaseQuery);

		startTime = System.currentTimeMillis();
		
		ISBSv2ConfigData config = sbsAliasBaseQuery.getSBSConfigByAlias("armv5_udeb");
		assertNotNull(config);
		assertEquals("/epoc32/release/armv5/udeb", config.getReleaseDirectory(null));
		assertEquals("armv5", config.getTraditionalPlatform(null));
		assertEquals("udeb", config.getTraditionalTarget(null));
		
		config = sbsAliasBaseQuery.getSBSConfigByAlias("armv5_udeb_gcce");
		assertNotNull(config);
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
		HashMap<String, String> releaseMap = SBSv2QueryUtils.queryConfigTargetInfo(aliasOrMeaningArray , null);
		
		assertEquals(2, releaseMap.size());
		
//		String queryResult = getSBSQueryOutput(argList, null);
//		assertTrue("No output found from " + SBSv2QueryUtils.QUERY_CONFIG_COMMAND, queryResult.length() > 0);
//		
//		HashMap<String, String> outputMap = parseQueryConfigResults(queryResult);
//		assertTrue("No configs were found in query for : " + SBSv2QueryUtils.QUERY_CONFIG_COMMAND, outputMap.size() > 0);
//		
//		System.out.println("Time for testQueryProductsFromSDKs(): " + getTimingStats());
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
		assertEquals(38, products.size());
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
