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
package com.nokia.carbide.cdt.builder.test.sandbox;

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
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class SBSv2QueryUtils {

	public static final String QUERY_PRODUCTS_COMMAND = "--query=products";
	public static final String QUERY_CONFIG_COMMAND = "--query=config";
	public static final String QUERY_COMMAND = "--query=aliases";
	
	public static ISBSv2QueryData queryAliasAndProductVariants() {
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
			sbsQueryData.addConfigurationData(null, oneSBSConfig);
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
				sbsQueryData.addConfigurationData(sdk, oneSBSConfig);
			}
			
			// Now get the products for each SDK
			argListProductQuery.add(QUERY_PRODUCTS_COMMAND);
			queryResult = getSBSQueryOutput(argListProductQuery, createEnvStringList(envVars));
			List<String> productList = parseQueryProductsResults(queryResult);
			sbsQueryData.addProductListForSDK(sdk, productList);
		}
		
		return sbsQueryData;
	}
	
	public static HashMap<String, String> queryConfigTargetInfo(List<String> aliasOrMeaningArray, ISymbianSDK sdk){
		
		List<String> argListConfigQuery = new ArrayList<String>();
		
		for (String alias : aliasOrMeaningArray){
			argListConfigQuery.add(QUERY_CONFIG_COMMAND + "[" + alias + "]");
		}
		
		Properties envVars = null;
		if (sdk != null){
			File epocRoot = new File(sdk.getEPOCROOT());
			if (epocRoot.exists()){
				envVars = EnvironmentReader.getEnvVars();
				envVars.setProperty("EPOCROOT", sdk.getEPOCROOT());
			}
		}
		String queryResult = getSBSQueryOutput(argListConfigQuery, createEnvStringList(envVars));
		
		return parseQueryConfigResults(queryResult);
	}
	
	private static String[] createEnvStringList(Properties envProps) {
		
		if (envProps == null){
			return null;
		}
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
	
	private static String getSBSQueryOutput(List<String> queryCommandList, String[] env) {
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
	
	private static HashMap<String, String> parseQueryAliasResult(String queryResult) {
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

	private static HashMap<String, String> parseQueryConfigResults(String queryResult) {
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
	
	private static List<String> parseQueryProductsResults(String queryResult) {
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

	
	
	
}
