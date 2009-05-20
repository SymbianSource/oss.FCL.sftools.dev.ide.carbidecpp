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
*/
package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.runtime.*;
import org.osgi.framework.Version;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * Utility class for SBSv2
 * @since 2.0
 */
public class SBSv2Utils {

	private static final String SBSV2_FILTERED_CONFIGS_STORE = "sbsv2FilteredConfigs"; //$NON-NLS-1$
	private static final String SBSV2_FILTERED_CONFIGS_DELIMETER = ";"; //$NON-NLS-1$

	private static List<String> unfilteredSBSv2ConfigNames;

	
	/**
     * Get the path to the SBSv2 bin directory.  This is based on the SBS_HOME environment variable
     * and may or may not actually exist.
     * @return absolute path to the bin directory, or null if SBS_HOME is not set
     */
    public static IPath getSBSBinDirectory() {
    	String sbsHome = EnvironmentReader.getEnvVar("SBS_HOME"); //$NON-NLS-1$
    	if (sbsHome != null) {
    		return new Path(sbsHome).append("bin"); //$NON-NLS-1$
    	}
    	return null;
    }

    /**
     * Get the build configurations supported by SBSv2
     * @param refreshList whether or not to parse the configuration xml files again
     * @return list of configuration names, never null
     */
    public static List<String> getUnfilteredSBSv2BuildConfigurations(boolean refreshList) {
    	
    	if (unfilteredSBSv2ConfigNames == null || refreshList) {
    		unfilteredSBSv2ConfigNames = new ArrayList<String>();
    		
        	// parse the xml files in SBS_HOME/lib/config/ to get SBSv2 configs
    		try {

    			IPath configPath = getSBSBinDirectory();
    			if (configPath != null) {
    				configPath = configPath.removeLastSegments(1).append("lib/config"); //$NON-NLS-1$
    				File configDir = configPath.toFile();
    				if (configDir.exists() && configDir.isDirectory()) {
    					File[] configFiles = FileUtils.listFilesInTree(configDir, new FileFilter() {

    						public boolean accept(File arg0) {
    							if (arg0.isDirectory()) {
    								return true;
    							}
    							return arg0.getName().toLowerCase().endsWith("xml"); //$NON-NLS-1$
    						}
    						
    					}, false);
    					
    					for (File file : configFiles) {
    						getConfigsForFile(file);
    					}
    				}
    			}

    		} catch (Exception e) {
        		e.printStackTrace();
        		Logging.log(SDKCorePlugin.getDefault(), Logging.newStatus(SDKCorePlugin.getDefault(), e));
    		}
    	}
    	
    	return unfilteredSBSv2ConfigNames;
	}

    /**
     * Given a list of SDKs, returns the list of the SDK's supported by SBSv2
     * @param sdks list of SDK's to check
     * @return list of SBSv2 supported SDK's, may be empty
     */
    public static List<ISymbianSDK> getSupportedSDKs(List<ISymbianSDK> sdks) {
    	List<ISymbianSDK> supportedSDKs = new ArrayList<ISymbianSDK>();
    	
    	//TODO need a better way to do this from Symbian.
    	// For now, just filter out anything older than 9.4
    	for (ISymbianSDK sdk : sdks) {
    		Version osVersion = sdk.getOSVersion();
    		if (osVersion.getMajor() > 8 && osVersion.getMinor() > 3) {
    			supportedSDKs.add(sdk);
    		}
    	}
    	
    	return supportedSDKs;
    }
    
	/**
	 * Returns the list of SBSv2 build configuration names that should
	 * be filtered out of any UI
	 */
	public static String[] getSBSv2ConfigurationsToFilter() {
		Preferences prefs = SDKCorePlugin.getDefault().getPluginPreferences();
		if (prefs != null) {
			String configs = prefs.getString(SBSV2_FILTERED_CONFIGS_STORE);
			return configs.split(SBSV2_FILTERED_CONFIGS_DELIMETER);
		}
		return new String[0];
	}

	/**
	 * Set the list of SBSv2 build configurations that should be filtered
	 * out of any UI
	 * @param configs configs to be filtered
	 */
	public static void setSBSv2ConfigurationsToFilter(String[] configs) {
		Preferences prefs = SDKCorePlugin.getDefault().getPluginPreferences();
		if (prefs != null) {
			String store = ""; //$NON-NLS-1$
			for (String config : configs) {
				store = store + SBSV2_FILTERED_CONFIGS_DELIMETER + config;
			}
			
			// remove the leading delimeter
			if (store.length() > 0) {
				store = store.substring(1);
				prefs.setValue(SBSV2_FILTERED_CONFIGS_STORE, store);
				SDKCorePlugin.getDefault().savePluginPreferences();

			}
		}
	}

	/**
	 * Gets the list of SBSv2 build contexts for the given SDK
	 * @param sdk the SDK to get the build contexts for
	 * @return the list of SBSv2 build contexts.  the list may be empty
	 */
	public static List<ISymbianBuildContext> getFilteredSBSv2BuildContexts(ISymbianSDK sdk) {
		List<ISymbianBuildContext> contexts = new ArrayList<ISymbianBuildContext>();
		
		for (String name : getUnfilteredSBSv2BuildConfigurations(false)) {

			boolean addConfig = true;
			for (String filteredConfig : getSBSv2ConfigurationsToFilter()) {
				if (filteredConfig.compareTo(name) == 0) {
					addConfig = false;
					break;
				}
			}

			if (addConfig) {
				// only support configs that fall into something we can make a build context
				// out of.  They must have a platform and a target.
				String targetString = null;
		    	if (name.toLowerCase().endsWith("_udeb") || name.toLowerCase().endsWith("_deb")) { //$NON-NLS-1$ //$NON-NLS-2$
		    		targetString = ISymbianBuildContext.DEBUG_TARGET;
		    	} else if (name.toLowerCase().endsWith("_urel") || name.toLowerCase().endsWith("_rel")) { //$NON-NLS-1$ //$NON-NLS-2$
		    		targetString = ISymbianBuildContext.RELEASE_TARGET;
		    	}
		    	
		    	if (targetString != null) {
		    		String[] parts = name.split("_"); //$NON-NLS-1$
		    		if (parts.length == 2) {
		    			SymbianBuildContext context = new SymbianBuildContext(sdk, parts[0].toUpperCase(), targetString);
		    			contexts.add(context);
		    		}
		    	}
			}
		}
		
		Collections.sort(contexts, new Comparator<ISymbianBuildContext>() {

			public int compare(ISymbianBuildContext o1, ISymbianBuildContext o2) {
				String platform1 = o1.getPlatformString();
				String platform2 = o2.getPlatformString();
				if (platform1.equals(platform2)) {
					return o1.getTargetString().compareTo(o2.getTargetString());
				} else {
					if (platform1.equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
						return -1;
					}
					else if (platform2.equals(ISymbianBuildContext.EMULATOR_PLATFORM)) {
						return 1;
					}
				}
				return 0;
			}
			
		});

		return contexts;
	}

	/**
	 * Whether or not to display SBSv2 builder UI
	 * @return true if SBSv2 is installed, false otherwise
	 */
	public static boolean enableSBSv2Support() {
		IPath sbsBinPath = getSBSBinDirectory();
		if (sbsBinPath != null && sbsBinPath.toFile().exists()) {
			return true;
		}
		return false;
	}

	private static void getConfigsForFile(File file) {
    	
    	try {
    		Element root = null;
    		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		parser.setErrorHandler(new DefaultHandler());

    		InputSource source = new InputSource(URIUtil.toURI(file.getAbsolutePath()).getPath());
    		root = parser.parse(source).getDocumentElement();
    		
    		NodeList children = root.getChildNodes();
    		for (int i=0; i< children.getLength(); i++) {
    			getConfigsForNode(children.item(i));
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		Logging.log(SDKCorePlugin.getDefault(), Logging.newStatus(SDKCorePlugin.getDefault(), e));
    	}
    }
    
    private static void getConfigsForNode(Node node) {
		if (node.getNodeName().equals("config")) { //$NON-NLS-1$
			Node abstractNode = node.getAttributes().getNamedItem("abstract");  //$NON-NLS-1$
			if (abstractNode == null || abstractNode.getNodeValue().equals("false")) { //$NON-NLS-1$
				Node namedNode = node.getAttributes().getNamedItem("name"); //$NON-NLS-1$
				if (namedNode != null) {
					// only support configs that fall into something we can make a build context
					// out of.  They must have a platform and a target.
					String configName = namedNode.getNodeValue();
			    	if (configName.toLowerCase().endsWith("_udeb") || configName.toLowerCase().endsWith("_deb") || //$NON-NLS-1$ //$NON-NLS-2$
		    			configName.toLowerCase().endsWith("_urel") || configName.toLowerCase().endsWith("_rel")) { //$NON-NLS-1$ //$NON-NLS-2$
			    		if (configName.split("_").length == 2) { //$NON-NLS-1$
							unfilteredSBSv2ConfigNames.add(configName);
			    		}
			    	}
				}
			}

			NodeList children = node.getChildNodes();
			for (int i=0; i< children.getLength(); i++) {
				getConfigsForNode(children.item(i));
			}
		}
    }
}
