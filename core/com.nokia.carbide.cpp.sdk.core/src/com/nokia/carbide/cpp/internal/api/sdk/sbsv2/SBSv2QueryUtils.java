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
package com.nokia.carbide.cpp.internal.api.sdk.sbsv2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class SBSv2QueryUtils {

	public static final String QUERY_ALIASES_COMMAND = "--query=aliases";
	public static final String QUERY_PRODUCTS_COMMAND = "--query=products";
	public static final String QUERY_CONFIG_COMMAND = "--query=config";

	public static final String ALIAS_CACHE_KEY = "alias_cache";
	public static final String CONFIG_CACHE_KEY = "config_cache";
	public static final String PRODUCT_CACHE_KEY = "product_cache";

	public static final String BAD_EPOCROOT = "BADEPOCROOT";

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> getAliasesForSDK(ISymbianSDK sdk) throws SBSv2MinimumVersionException {
		HashMap<String, String> aliases;
		Map<String, HashMap<String, String>> aliasesMap = SDKCorePlugin.getCache().getCachedData(ALIAS_CACHE_KEY, Map.class, 0);
		SBSv2SDKKey key = new SBSv2SDKKey(sdk);

		if (aliasesMap == null) {
			aliasesMap = new HashMap<String, HashMap<String, String>>();
		}
		else {
			aliases = aliasesMap.get(key.toString());
			if (aliases != null && !aliases.containsKey(BAD_EPOCROOT)) {
				return aliases;
			}
		}

		aliases = getAliasesQuery(sdk);
		aliasesMap.put(key.toString(), aliases);
		SDKCorePlugin.getCache().putCachedData(ALIAS_CACHE_KEY, (Serializable)aliasesMap, 0);
		return aliases;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getProductVariantsForSDK(ISymbianSDK sdk) throws SBSv2MinimumVersionException {
		List<String> products;
		Map<String, List<String>> productsMap = SDKCorePlugin.getCache().getCachedData(PRODUCT_CACHE_KEY, Map.class, 0);
		SBSv2SDKKey key = new SBSv2SDKKey(sdk);
		
		if (productsMap == null) {
			productsMap = new HashMap<String, List<String>>();
		}
		else {
			products = productsMap.get(key.toString());
			if (products != null && !products.contains(BAD_EPOCROOT)) {
				return products;
			}
		}

		products = getProductsQuery(sdk);
		productsMap.put(key.toString(), products);
		SDKCorePlugin.getCache().putCachedData(PRODUCT_CACHE_KEY, (Serializable)productsMap, 0);
		return products;
	}

	public static String getConfigQueryXMLforSDK(ISymbianSDK sdk, List<String> aliasOrMeaningArray) throws SBSv2MinimumVersionException {
		
		checkForMinimumRaptorVersion();
		
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

		return getSBSQueryOutput(argListConfigQuery, createEnvStringList(envVars));
	}

	@SuppressWarnings("unchecked")
	public static SBSv2ConfigQueryData getConfigQueryDataForSDK(ISymbianSDK sdk, String alias) {
		SBSv2ConfigQueryData configQueryData = null;
		Map<String, SBSv2ConfigQueryData> configsMap = SDKCorePlugin.getCache().getCachedData(CONFIG_CACHE_KEY, Map.class, 0);
		String key = (new SBSv2SDKKey(sdk)).toString() + "[" + alias + "]";

		if (configsMap != null) {
			configQueryData = configsMap.get(key);
		}
		return configQueryData;
	}

	@SuppressWarnings("unchecked")
	public static void storeConfigQueryDataForSDK(ISymbianSDK sdk, String alias, SBSv2ConfigQueryData configQueryData) {
		Map<String, SBSv2ConfigQueryData> configsMap = SDKCorePlugin.getCache().getCachedData(CONFIG_CACHE_KEY, Map.class, 0);
		String key = (new SBSv2SDKKey(sdk)).toString() + "[" + alias + "]";

		if (configsMap == null) {
			configsMap = new HashMap<String, SBSv2ConfigQueryData>();
		} else {
			if (configsMap.get(key) != null) {
				// configQueryData already exist in cache
				return;
			}
		}

		configsMap.put(key, configQueryData);
		SDKCorePlugin.getCache().putCachedData(CONFIG_CACHE_KEY, (Serializable)configsMap, 0);		
	}

	public static HashMap<String, String> queryConfigTargetInfo(ISymbianSDK sdk, List<String> aliasOrMeaningArray) throws SBSv2MinimumVersionException{
		
		checkForMinimumRaptorVersion();
		
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

	private static HashMap<String, String> getAliasesQuery(ISymbianSDK sdk) throws SBSv2MinimumVersionException {
		
		checkForMinimumRaptorVersion();
		if (!isEpocRootValid(sdk)) {
			HashMap<String, String> result = new HashMap<String, String>();
			result.put(BAD_EPOCROOT, "");
			return result;
		}
		
		List<String> argListAliasQuery = new ArrayList<String>();
		argListAliasQuery.add(QUERY_ALIASES_COMMAND);
		
		Properties envVars = EnvironmentReader.getEnvVars();
		if (sdk != null){
			envVars.setProperty("EPOCROOT", sdk.getEPOCROOT());
		} else {
			envVars.setProperty("EPOCROOT", "FOOBAR");
		}
		
		String queryResult = getSBSQueryOutput(argListAliasQuery, createEnvStringList(envVars));
		
		return parseQueryAliasResult(queryResult);
	}

	private static List<String> getProductsQuery(ISymbianSDK sdk) throws SBSv2MinimumVersionException {
		
		checkForMinimumRaptorVersion();
		if (!isEpocRootValid(sdk)) {
			List<String> result = new ArrayList<String>();
			result.add(BAD_EPOCROOT);
			return result;
		}

		List<String> argListProductQuery = new ArrayList<String>();
		argListProductQuery.add(QUERY_PRODUCTS_COMMAND);
		
		Properties envVars = EnvironmentReader.getEnvVars();
		if (sdk != null){
			envVars.setProperty("EPOCROOT", sdk.getEPOCROOT());
		} else {
			envVars.setProperty("EPOCROOT", "FOOBAR");
		}
		
		String queryResult = getSBSQueryOutput(argListProductQuery, createEnvStringList(envVars));
		return parseQueryProductsResults(queryResult);
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

	private static Boolean isEpocRootValid(ISymbianSDK sdk) {
		IPath epocRoot = new Path(sdk.getEPOCROOT());
		epocRoot = epocRoot.append("epoc32");
		File epocRootFile = epocRoot.toFile();
		if (epocRootFile.exists()) {
			return true;
		} else {
			return false;
		}
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
    				String fullName = meaning.getNamedItem("meaning").getNodeValue();
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
    				productList.add(name);
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		Logging.log(SDKCorePlugin.getDefault(), Logging.newStatus(SDKCorePlugin.getDefault(), e));
    	}
		
		return productList;
	}

	private static boolean checkForMinimumRaptorVersion() throws SBSv2MinimumVersionException{
		Version sbsVers = SDKCorePlugin.getSDKManager().getSBSv2Version(false);
		if (sbsVers.compareTo(SDKCorePlugin.getSDKManager().getMinimumSupportedSBSv2Version()) >= 0)
			return true;
		else {
			String message = "Raptor/SBSv2 minimum version supported in Carbide.c++ is " + SDKCorePlugin.getSDKManager().getMinimumSupportedSBSv2Version() + ". Your sbs version is " + sbsVers + ". Please update your sbs installation and 'Rescan' from the Build Configuration Filtering preference page .";
			throw new SBSv2MinimumVersionException(message);
		}
	}

	public static HashMap<String, String> getCompleteAliasList() throws SBSv2MinimumVersionException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		// iterate all SDKs and build the map up
		for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()) {
			if (sdk.isEnabled() && isEpocRootValid(sdk)) {
				HashMap<String, String> aliasMap = getAliasesForSDK(sdk);
				for (String alias : aliasMap.keySet()) {
					if (alias.equals(BAD_EPOCROOT)) {
						continue;
					}
					if (resultMap.get(alias) == null) {
						resultMap.put(alias, aliasMap.get(alias));
					}
				}
			}
		}
		
		return resultMap;
	}

	public static List<String> getCompleteProductVariantList() throws SBSv2MinimumVersionException {
		List<String> resultList = new ArrayList<String>();
		
		// iterate all SDKs and build the map up
		for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()) {
			if (sdk.isEnabled() && isEpocRootValid(sdk)) {
				List<String> productList = getProductVariantsForSDK(sdk);
				for (String variant : productList) {
					if (variant.equals(BAD_EPOCROOT)) {
						continue;
					}
					if (!resultList.contains(variant)) {
						resultList.add(variant);
					}
				}
			}
		}
		
		return resultList;
	}

	public static void removeAllCachedQueries() {
		removeCachedAliases();
		removeCachedProducts();
		removeCachedConfigurations();
	}

	public static void removeCachedAliases() {
		SDKCorePlugin.getCache().removeCache(ALIAS_CACHE_KEY);
	}

	public static void removeCachedProducts() {
		SDKCorePlugin.getCache().removeCache(PRODUCT_CACHE_KEY);		
	}

	public static void removeCachedConfigurations() {
		SDKCorePlugin.getCache().removeCache(CONFIG_CACHE_KEY);
	}

}
