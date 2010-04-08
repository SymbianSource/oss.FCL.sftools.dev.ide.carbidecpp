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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences;
import org.osgi.framework.Version;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * Utility class for SBSv2
 * @since 2.0
 */
public class SBSv2Utils {

	private static final String SBSV2_FILTERED_CONFIGS_STORE = "sbsv2FilteredConfigs"; //$NON-NLS-1$
	private static final String SBSV2_FILTERED_CONFIGS_DELIMETER = ";"; //$NON-NLS-1$

	protected static String sbsHome; 
	protected static IPath sbsPath; 
	
	private static boolean scannedSbsState = false; 
	private static final String sbsScriptName = "sbs.bat"; 
	protected static final String SBS_HOME = "SBS_HOME"; 

	/** Map of usable Raptor alias for -c parameter and base platform: <alias, base plat>  */
	private static Map<String, String> unfilteredSBSv2ConfigNames; 
	
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
     * @return A map of raptor aliases (key) to base build platform. Never null; 
     */
    public static Map<String, String> getUnfilteredSBSv2BuildConfigurations(boolean refreshList) { 
    	
    	if (unfilteredSBSv2ConfigNames == null || refreshList) {
    		unfilteredSBSv2ConfigNames = new HashMap<String, String>(); 
    		
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
			}
			if (store.length() >= 0){
				// lenght of zero means there are not configs to filter (or show them all)
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
		
		Iterator it = getUnfilteredSBSv2BuildConfigurations(false).entrySet().iterator(); 
		while (it.hasNext()){ 
			Map.Entry buildConfigPair = (Map.Entry)it.next(); 
			String alias = (String)buildConfigPair.getKey(); // The sbsv2 alias 
			String basePlat = (String)buildConfigPair.getValue(); 
			
			boolean addConfig = true;
			for (String filteredConfig : getSBSv2ConfigurationsToFilter()) {
				if (filteredConfig.compareTo(alias) == 0) {
					addConfig = false;
					break;
				}
			}

			if (addConfig) {
				// only support configs that fall into something we can make a build context
				// out of.  They must have a platform and a target.
				String targetString = null;
				String[] configTokens = alias.split("_"); // $//$NON-NLS-N$ 
				// We presume that aliases have the second token as the "target". 
				if (configTokens[1].toLowerCase().endsWith("deb")) { //$NON-NLS-1$
		    		targetString = ISymbianBuildContext.DEBUG_TARGET;
				} else if (configTokens[1].toLowerCase().endsWith("rel")) { //$NON-NLS-1$
		    		targetString = ISymbianBuildContext.RELEASE_TARGET;
		    	}
		    	
		    	if (targetString != null) {
		    		
		    		SymbianBuildContext context = null; 
		    		context = new SymbianBuildContext(sdk, basePlat, targetString, alias); 	
		    		if (context != null) 
		    			contexts.add(context);
		    	}
			}
		}
		
		return sortContexts(contexts);
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
    			getConfigsForNode(children.item(i), root);
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		Logging.log(SDKCorePlugin.getDefault(), Logging.newStatus(SDKCorePlugin.getDefault(), e));
    	}
    }
    
	private static void getConfigsForNode(Node node, Node parentNode) { 
		if (node.getNodeName().equals("config")) { //$NON-NLS-1$
			Node abstractNode = node.getAttributes().getNamedItem("abstract");  //$NON-NLS-1$
			Node namedNode = node.getAttributes().getNamedItem("name"); //$NON-NLS-1$ 
			if (abstractNode == null || abstractNode.getNodeValue().equals("false")) { //$NON-NLS-1$
				if (namedNode != null) {
					
					// Get the parent base build platform 
					String baseBuildPlatform = null; 
					if (parentNode != null){ 
						baseBuildPlatform = parentNode.getAttributes().getNamedItem("name").getNodeValue();
						if (baseBuildPlatform.split("_").length > 1){
							baseBuildPlatform = baseBuildPlatform.split("_")[0];
						}
					}
					// only support configs that fall into something we can make a build context
					// out of.  They must have a platform and a target.
					String configName = namedNode.getNodeValue();
					String[] configTokens = configName.split("_"); 
					if (configTokens.length >= 2) { //$NON-NLS-1$ 
						String target = configTokens[1]; 
						if (target.endsWith("deb") || target.endsWith("rel")){ //$NON-NLS-1$ 
							if (baseBuildPlatform == null){ 
								baseBuildPlatform = "unknown"; 
							}
							unfilteredSBSv2ConfigNames.put(configName, baseBuildPlatform); 
						}
					}
				}
			}

			NodeList children = node.getChildNodes();
			for (int i=0; i< children.getLength(); i++) {
				getConfigsForNode(children.item(i), node);
			}
		}
    }
    
    /**  
     *  (Re-)scan the SBSv2 / Raptor configuration  
     *   @return message if error, else null  
     **/  
    public static String scanSBSv2() {  
    	// do some basic checks  
    	sbsHome = System.getenv(SBS_HOME);  
    	if (sbsHome == null) {  
    		return "Please define the SBS_HOME environment (e.g. /path/to/raptor) and add $SBS_HOME/bin to your PATH before running Carbide.";
    		}  
    	
    	sbsPath = HostOS.findProgramOnPath(sbsScriptName, null);
    	if (sbsPath == null) {  
    		return "Please add $SBS_HOME/bin to your PATH before running Carbide.";  
    		}  
    	
    	return null; 
    	}  
    
    /**  
     * Get the path to SBSv2 (sbs.bat or sbs)  
     **/  
    public static IPath getSBSPath() {  
    	if (!scannedSbsState) {  
    		scanSBSv2();  
    		scannedSbsState = true;  
    		}  
    	return sbsPath != null ? sbsPath : new Path(sbsScriptName); 
    }
    
    private static List<ISymbianBuildContext> sortContexts(List<ISymbianBuildContext> contexts){ 
    	
		// 3 sorting stages to handle long Raptor aliases, and multiple aliases that have a similar platform and target prefix (e.g. armv5_urel)
		
		Collections.sort(contexts, new Comparator<ISymbianBuildContext>() {

			// First sort the target name (Debug / Release) and push Emulation to the top
			public int compare(ISymbianBuildContext o1, ISymbianBuildContext o2) {
				String sbsAlias1 = o1.getSBSv2Alias();
				String sbsAlias2 = o2.getSBSv2Alias();
				
				if (o1.getPlatformString().equals(o2.getPlatformString())) {
					if (o1.getSBSv2Alias().split("_").length == 2 && o2.getSBSv2Alias().split("_").length == 2)
						return o1.getTargetString().compareTo(o2.getTargetString());
					else if (sbsAlias1.split("_").length >= 3 && sbsAlias1.split("_").length >= 3)
						return 1;
				} else {
					if (sbsAlias1.toUpperCase().startsWith(ISymbianBuildContext.EMULATOR_PLATFORM)) {
						return -1;
					}else if (sbsAlias2.toUpperCase().startsWith(ISymbianBuildContext.EMULATOR_PLATFORM)) {
						return 1;
					} 
				}
				return sbsAlias1.compareTo(sbsAlias2);
				
			}
			
		});

		// Sort long alias names
		Collections.sort(contexts, new Comparator<ISymbianBuildContext>() {

			public int compare(ISymbianBuildContext o1, ISymbianBuildContext o2) {
				String sbsAlias1 = o1.getSBSv2Alias();
				String sbsAlias2 = o2.getSBSv2Alias();
				
				if (sbsAlias1.split("_").length >= 3 && sbsAlias1.split("_").length >= 3 && !sbsAlias1.equals(sbsAlias2)){
					String temp1[] = sbsAlias1.split("_");
					String temp2[] = sbsAlias2.split("_");
					String suffix1 = "";
					String suffix2 = "";
					for (int i = 2; i < temp1.length; i++){
						suffix1 += temp1[i] + "_";
					}
					
					for (int i = 2; i < temp2.length; i++){
						suffix2 += temp2[i] + "_";
					}
					
					return suffix1.compareTo(suffix2);
				} 
				
				return 0;	
			}
		});
		
		// Sort the target string for long aliases
		Collections.sort(contexts, new Comparator<ISymbianBuildContext>() {

			public int compare(ISymbianBuildContext o1, ISymbianBuildContext o2) {
				String sbsAlias1 = o1.getSBSv2Alias();
				String sbsAlias2 = o2.getSBSv2Alias();
				String temp1[] = sbsAlias1.split("_");
				String temp2[] = sbsAlias2.split("_");
				String suffix1 = "";
				String suffix2 = "";
				for (int i = 2; i < temp1.length; i++){
					suffix1 += temp1[i] + "_";
				}
				
				for (int i = 2; i < temp2.length; i++){
					suffix2 += temp2[i] + "_";
				}
				
				if (sbsAlias1.split("_").length >= 3 && sbsAlias1.split("_").length >= 3 && suffix1.equals(suffix2)){
					return o1.getTargetString().compareTo(o2.getTargetString());
				} 
				
				return 0;	
			}
		});
		
    	return contexts; 
    }
    
}
