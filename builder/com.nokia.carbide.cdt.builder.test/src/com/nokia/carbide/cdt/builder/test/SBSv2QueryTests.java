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

import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.Version;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class SBSv2QueryTests extends BaseTest {
	
	private static HashMap<String, String> sbsAliasMap;
	private long startTime;
	
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
		
		final String QUERY_COMMAND = "--query=aliases";
		
		startTime = System.currentTimeMillis();
		
		List<String> argList = new ArrayList<String>();
		argList.add(QUERY_COMMAND);
		
		String queryResult = getSBSQueryOutput(argList, null);
		System.out.println("Query output from " + QUERY_COMMAND + " : " + queryResult);
		assertTrue("No output found from " + QUERY_COMMAND, queryResult.length() > 0);
		
		sbsAliasMap = parseQueryAliasResult(queryResult);
		
		assertTrue("No configs were successfully parsed from the sbs commmand : " + QUERY_COMMAND, sbsAliasMap.size() > 0);
		
		String aliasLookup = sbsAliasMap.get("armv5_udeb"); // look-up some known build alias
		assertNotNull(aliasLookup);
		
		aliasLookup = sbsAliasMap.get("there_is_no_way_this_would_be_config_name");
		assertNull(aliasLookup);
		
		System.out.println("Time for testQueryProductsFromSDKs(): " + getTimingStats());
		
	}
	
	/**
	 * TODO: The SBS API FOR THIS QUERY IS UNDER CONSTRUCTION.....
	 * TODO; This test should be run on individual %EPOCROOT% values
	 * @throws Exception
	 */
	public void testQueryConfigs() throws Exception {
		
		final String QUERY_CONFIG_COMMAND = "--query=config";
		assertNotNull(sbsAliasMap);

		startTime = System.currentTimeMillis();
		
		List<String> argList = new ArrayList<String>();
		
		for (String key : sbsAliasMap.keySet()){
			argList.add(QUERY_CONFIG_COMMAND + "[" + key + "]");
		}
		
		String queryResult = getSBSQueryOutput(argList, null);
		System.out.println("Query output from " + QUERY_CONFIG_COMMAND + " : " + queryResult);
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
		final String QUERY_PRODUCTS_COMMAND = "--query=products";

		startTime = System.currentTimeMillis();
		
		List<String> argList = new ArrayList<String>();
		
		argList.add(QUERY_PRODUCTS_COMMAND);
		
		// TODO: Just hard-coding a known SDK that has variants set up.
		Properties envVars = EnvironmentReader.getEnvVars();
		envVars.setProperty("EPOCROOT", "K:");
		
		String queryResult = getSBSQueryOutput(argList, createEnvStringList(envVars));
		System.out.println("Query output from " + QUERY_PRODUCTS_COMMAND + " : " + queryResult);
		assertTrue("No output found from " + QUERY_PRODUCTS_COMMAND, queryResult.length() > 0);
		
		List<String> productList = parseQueryProductsResults(queryResult);
		assertTrue("No products were found in query for : " + QUERY_PRODUCTS_COMMAND, productList.size() > 0);
		
		System.out.println("Time for testQueryProductsFromSDKs(): " + getTimingStats());
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
				return "\nTotal Time: " + seconds + " sec\n";
			}
			
		} else {
			return "";
		}

	}
	
}
