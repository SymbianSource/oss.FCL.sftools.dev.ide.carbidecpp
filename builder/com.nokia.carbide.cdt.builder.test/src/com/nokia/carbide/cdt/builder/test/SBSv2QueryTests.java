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
import java.io.File;
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

import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Version;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cdt.builder.test.sandbox.ISBSv2ConfigData;
import com.nokia.carbide.cdt.builder.test.sandbox.ISBSv2QueryData;
import com.nokia.carbide.cdt.builder.test.sandbox.SBSv2ConfigData;
import com.nokia.carbide.cdt.builder.test.sandbox.SBSv2QueryData;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class SBSv2QueryTests extends BaseTest {
	
	private static ISBSv2QueryData sbsAliasBaseQuery;
	
	private long startTime;
	
	private final String QUERY_PRODUCTS_COMMAND = "--query=products";
	private final String QUERY_CONFIG_COMMAND = "--query=config";
	private final String QUERY_COMMAND = "--query=aliases";
	
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
			sbsAliasBaseQuery = getSBSv2QueryData();
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
	
	private ISBSv2QueryData getSBSv2QueryData() {
		List<String> argListConfigQuery = new ArrayList<String>();
		List<String> argListProductQuery = new ArrayList<String>();
		argListConfigQuery.add(QUERY_COMMAND);
		SBSv2QueryData sbsQueryData = new SBSv2QueryData();
		
		/////// Invoke Raptor once with no EPOCROOT
		Properties envVars = EnvironmentReader.getEnvVars();
		envVars.setProperty("EPOCROOT", "FOOBAR");
		String queryResult = getSBSQueryOutput(argListConfigQuery, createEnvStringList(envVars));
	
		HashMap<String, String> sbsAliasMap = parseQueryAliasResult(queryResult);
		
		for (String aliasKey : sbsAliasMap.keySet()){
			String meaning = sbsAliasMap.get(aliasKey);
			SBSv2ConfigData oneSBSConfig = new SBSv2ConfigData(aliasKey, meaning, null);
			sbsQueryData.addConfigurationData(oneSBSConfig);
		}
		
		/////// Do for each SDK to build up the alias list...
		for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()){
			IPath epocRoot = new Path(sdk.getEPOCROOT());
			if ((sdk.getOSVersion().getMajor() <= 9 && sdk.getOSVersion().getMinor() <5)
				|| !epocRoot.toFile().exists()){
				
				continue; // skip it, the sdk is not supported or broken
			}
			
			envVars = EnvironmentReader.getEnvVars();
			envVars.setProperty("EPOCROOT", sdk.getEPOCROOT());
			
			queryResult = getSBSQueryOutput(argListConfigQuery, createEnvStringList(envVars));
			
			sbsAliasMap = parseQueryAliasResult(queryResult);
			
			for (String aliasKey : sbsAliasMap.keySet()){
				String meaning = sbsAliasMap.get(aliasKey);
				SBSv2ConfigData oneSBSConfig = new SBSv2ConfigData(aliasKey, meaning, sdk);
				sbsQueryData.addConfigurationData(oneSBSConfig);
			}
			
			// Now get the products for each SDK
			argListProductQuery.add(QUERY_PRODUCTS_COMMAND);
			queryResult = getSBSQueryOutput(argListProductQuery, createEnvStringList(envVars));
			List<String> productList = parseQueryProductsResults(queryResult);
			sbsQueryData.addProductListForSDK(sdk, productList);
		}
		
		return sbsQueryData;
	}

	/**
	 * TODO: The SBS API FOR THIS QUERY IS UNDER CONSTRUCTION.....
	 * TODO; This test should be run on individual %EPOCROOT% values
	 * @throws Exception
	 */
	public void testQueryConfigs() throws Exception {
		
		assertNotNull(sbsAliasBaseQuery);

		startTime = System.currentTimeMillis();
		
		List<String> argList = new ArrayList<String>();
		
		for (ISBSv2ConfigData baseConfig: sbsAliasBaseQuery.getBaseSBSConfigurations()){
			argList.add(QUERY_CONFIG_COMMAND + "[" + baseConfig.getBuildAlias() + "]");
		}
		
		String queryResult = getSBSQueryOutput(argList, null);
		assertTrue("No output found from " + QUERY_CONFIG_COMMAND, queryResult.length() > 0);
		
		HashMap<String, String> outputMap = parseQueryConfigResults(queryResult);
		assertTrue("No configs were found in query for : " + QUERY_CONFIG_COMMAND, outputMap.size() > 0);
		
		System.out.println("Time for testQueryProductsFromSDKs(): " + getTimingStats());
		
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
		
		testQueryConfigs();
		testQueryConfigs();
		testQueryConfigs();
		testQueryConfigs();
		testQueryConfigs();
		testQueryConfigs();
		testQueryConfigs();
		testQueryConfigs();
		testQueryConfigs();
		testQueryConfigs();
		
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
	
	private List<String> parseQueryProductsResults(String queryResult) {
		List<String> productList = new ArrayList<String>();
		
		try {
    		Element root = null;
    		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		parser.setErrorHandler(new DefaultHandler());
    		
    		StringReader reader = new StringReader( queryResult );
    		InputSource inputSource = new InputSource( reader );
    		root = parser.parse(inputSource).getDocumentElement();
    		
    		NodeList children = root.getChildNodes();
    		for (int i=0; i< children.getLength(); i++) {
    			Node aliasNode = children.item(i);
    			if (aliasNode.getNodeName().equals("product")){
    				NamedNodeMap productAttribs = aliasNode.getAttributes();
    				String name = productAttribs.getNamedItem("name").getNodeValue();
    				//System.out.println("ALIAS QUERY ==> " + dottedName + " <==> " + alias);
    				productList.add(name);
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		Logging.log(SDKCorePlugin.getDefault(), Logging.newStatus(SDKCorePlugin.getDefault(), e));
    	}
		
		return productList;
	}

	private HashMap<String, String> parseQueryAliasResult(String queryResult) {
		/* Alias to dotted name config */
		HashMap<String, String> sbsAliasMap = new HashMap<String, String>();
		
		try {
    		Element root = null;
    		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		parser.setErrorHandler(new DefaultHandler());
    		
    		StringReader reader = new StringReader( queryResult );
    		InputSource inputSource = new InputSource( reader );
    		root = parser.parse(inputSource).getDocumentElement();
    		
    		NodeList children = root.getChildNodes();
    		for (int i=0; i< children.getLength(); i++) {
    			Node aliasNode = children.item(i);
    			if (aliasNode.getNodeName().equals("alias")){
    				NamedNodeMap meaning = aliasNode.getAttributes();
    				String dottedName = meaning.getNamedItem("meaning").getNodeValue();
    				String alias = meaning.getNamedItem("name").getNodeValue();
    				//System.out.println("ALIAS QUERY ==> " + dottedName + " <==> " + alias);
    				sbsAliasMap.put(alias, dottedName);
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		Logging.log(SDKCorePlugin.getDefault(), Logging.newStatus(SDKCorePlugin.getDefault(), e));
    	}
		
		
		return sbsAliasMap;
	}

	private HashMap<String, String> parseQueryConfigResults(String queryResult) {
		/* Alias to output directory */
		HashMap<String, String> sbsAliasMap = new HashMap<String, String>();
		
		try {
    		Element root = null;
    		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		parser.setErrorHandler(new DefaultHandler());
    		
    		StringReader reader = new StringReader( queryResult );
    		InputSource inputSource = new InputSource( reader );
    		root = parser.parse(inputSource).getDocumentElement();
    		
    		NodeList children = root.getChildNodes();
    		for (int i=0; i< children.getLength(); i++) {
    			Node aliasNode = children.item(i);
    			if (aliasNode.getNodeName().equals("config")){
    				NamedNodeMap meaning = aliasNode.getAttributes();
    				String outputpath = meaning.getNamedItem("outputpath").getNodeValue();
    				String fullName = meaning.getNamedItem("fullname").getNodeValue();
    				//System.out.println("ALIAS QUERY ==> " + dottedName + " <==> " + alias);
    				sbsAliasMap.put(fullName, outputpath);
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		Logging.log(SDKCorePlugin.getDefault(), Logging.newStatus(SDKCorePlugin.getDefault(), e));
    	}
		
		
		return sbsAliasMap;
	}
	
	
	private String getSBSQueryOutput(List<String> queryCommandList, String[] env) {
		String overallOutput = "";
		
		Runtime rt = Runtime.getRuntime();
		IPath sbsPath = SBSv2Utils.getSBSPath();
		Process p = null;
		List<String> args = new ArrayList<String>();
		args.add(sbsPath.toOSString());
		args.addAll(queryCommandList);
		try {
			p = rt.exec(args.toArray(new String[args.size()]), env);
		} catch (IOException e) {
			// no such process, SBSv2 not available
			Logging.log(
					SDKCorePlugin.getDefault(),
					Logging.newSimpleStatus(
							0,
							IStatus.WARNING,
							MessageFormat
									.format(
											"Could not find or launch Raptor script ''{0}''; SBSv2 support will not be available",
											sbsPath), e));
		}
		if (p != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			
			String stdErrLine = null;
			try {

				// Only try for 30 seconds then bail in case Raptor hangs
				int maxTries = 60;
				int numTries = 0;
				while (numTries < maxTries) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// ignore
					}
					if (br.ready()) {
						while ((stdErrLine = br.readLine()) != null) {
							overallOutput += stdErrLine;
							numTries = maxTries;
						}

					}
					numTries++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return overallOutput;
	}
	
	private static String[] createEnvStringList(Properties envProps) {
		String[] env = null;
		List<String> envList = new ArrayList<String>();
		Enumeration<?> names = envProps.propertyNames();
		if (names != null) {
			while (names.hasMoreElements()) {
				String key = (String) names.nextElement();
				envList.add(key + "=" + envProps.getProperty(key));
			}
			env = (String[]) envList.toArray(new String[envList.size()]);
		}
		return env;
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
