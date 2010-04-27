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
import java.text.MessageFormat;
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
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.Version;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cpp.sdk.core.ISDKManager;
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
	private static final String SBSV2_FILTERED_CONFIGS_STORE_INITED = "sbsv2FilteredConfigsInited"; //$NON-NLS-1$
	private static final String SBSV2_FILTERED_CONFIGS_DELIMETER = ";"; //$NON-NLS-1$
	private static final long VALID_ABLD_SIZE = 1024;

	/** Path, to and including the SBS script */ 
	protected static IPath sbsPath; 
	
	private static boolean scannedSbsState = false; 
	private static final String sbsScriptName = "sbs.bat"; 

	/** Map of usable Raptor alias for -c parameter and base platform: <alias, base plat>  */
	private static Map<String, String> unfilteredSBSv2ConfigNames; 
	
	/**
     * Get the path to the SBSv2 bin directory.  not including the sbs executable.
     * May or may not actually exist.
     * @return absolute path to the bin directory, or null if sbs is not set
     */
    public static IPath getSBSBinDirectory() {
    	String pathValue = EnvironmentReader.getEnvVar("PATH"); //$NON-NLS-1$ 
    	IPath sbs = HostOS.findProgramOnPath(sbsScriptName, pathValue); 
    	if (sbs != null){ 
    		sbs = sbs.removeLastSegments(1); 
    	}
    	return sbs;
    }

    /**
     * Get the build configurations supported by SBSv2
     * @param refreshList whether or not to parse the configuration xml files again
     * @return A map of raptor aliases (key) to base build platform. Never null; 
     */
    public static Map<String, String> getUnfilteredSBSv2BuildConfigurations(boolean refreshList) { 
    	
    	if (unfilteredSBSv2ConfigNames == null || refreshList || unfilteredSBSv2ConfigNames.size() == 0) {
    		unfilteredSBSv2ConfigNames = new HashMap<String, String>(); 
    		
        	// parse the xml files in <sbs-install>/lib/config/ to get SBSv2 configs
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
		
		initDefaultConfigsToFilter();
		
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
	 * There are many build aliases presented by default from Raptor
	 * Filter out those that are less commonly used on new workspace creation.
	 */
	public static void initDefaultConfigsToFilter() {
		IEclipsePreferences prefs = new InstanceScope().getNode(SDKCorePlugin.getPluginId());
		String inited = prefs.get(SBSV2_FILTERED_CONFIGS_STORE_INITED, "");
		if (inited == null || inited.length() == 0){
			Iterator it = getUnfilteredSBSv2BuildConfigurations(false).entrySet().iterator(); 
			List<String> defaultConfigsToFilter = new ArrayList<String>();
			while (it.hasNext()){ 
				Map.Entry buildConfigPair = (Map.Entry)it.next();
				String buildAlias = (String)buildConfigPair.getKey();
				if (buildAlias.toLowerCase().startsWith("armv6") ||
					buildAlias.toLowerCase().startsWith("armv7") ||
					buildAlias.toLowerCase().startsWith("armv9")){
					defaultConfigsToFilter.add(buildAlias);
				}
			}
			prefs.put(SBSV2_FILTERED_CONFIGS_STORE_INITED, "true");
			setSBSv2ConfigurationsToFilter(defaultConfigsToFilter.toArray(new String[defaultConfigsToFilter.size()]));
			
		}
		
	}

	/**
	 * Whether or not to display SBSv1 builder UI
	 * @return true if SBSv1 is available, false otherwise
	 */
	public static boolean enableSBSv1Support() {
		if (!enableSBSv2Support())
			return true;
		
		if (isSBSv1Supported())
			return true;
		
		return false;
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
    	if (sbsPath != null){
    		return null;
    	}
    	IPath expectedPath = getSBSBinDirectory(); 
    	if (expectedPath != null) { 
    		expectedPath = expectedPath.append(sbsScriptName); 
    		if (expectedPath.toFile().exists()) { 
    			sbsPath = expectedPath; 
    		}
    	}
		if (sbsPath == null) {
			return MessageFormat.format(Messages
					.getString("SBSv2Utils.CannotFindSBSScriptError"), //$NON-NLS-1$ 
					sbsScriptName);
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
    	
		// 2 sorting stages to handle long Raptor aliases, and multiple aliases that have a similar platform and target prefix (e.g. armv5_urel)
		
		Collections.sort(contexts, new Comparator<ISymbianBuildContext>() {

			// First sort the target name (Debug / Release) and push Emulation to the top
			public int compare(ISymbianBuildContext o1, ISymbianBuildContext o2) {
				String sbsAlias1 = o1.getSBSv2Alias();
				String sbsAlias2 = o2.getSBSv2Alias();
				
				if (o1.getPlatformString().equals(o2.getPlatformString())) {
					if (o1.getSBSv2Alias().split("_").length != o2.getSBSv2Alias().split("_").length)
						return o1.getTargetString().compareTo(o2.getTargetString());
					else if (sbsAlias1.split("_").length >= 3){
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
				
				if (o1.getSBSv2Alias().split("_").length == 3 && o2.getSBSv2Alias().split("_").length == 3 &&
						o1.getPlatformString().equals(o2.getPlatformString()))
					return o1.getTargetString().compareTo(o2.getTargetString());
				else if (sbsAlias1.split("_").length >= 3 && sbsAlias1.split("_").length >= 3 && !sbsAlias1.equals(sbsAlias2)){
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
		
    	return contexts; 
    }
    
    /**
	 * If a variant is defined and it changes the output directory, return the directory name.
	 * For example, armv5_udeb.phone1 would return '.phone1'. If not variant that changes the release tree, then null
	 * NOTE: This method deals with variant text applied to the end of a build alias, specifically testing for
	 * variant text defined in the SBSv2 Build Configuration tab.
	 * @return null if not a variant or the value to append to the platform release tree directory
	 * @see com.nokia.carbide.cdt.internal.builder.ui#SBSv2BuildConfigTab
	 */
	public static String getVariantOutputDirModifier(String variantText) {
		
		String[] ignoredVariants =  { "generic", "tracecompiler", "trace", "test", "savespace", 
				"bfc", "smp", "rvct2_2", "rvct4_0", "rvct3_1", "gcce4_3_2", "remove_freeze" };
		
		String newOutputDir = null;
		if (variantText != null && variantText.length() > 1){
			String[] variantTok = variantText.split("\\.");
			if (variantTok.length > 1){
				for (String ignore : ignoredVariants){
					if (variantTok[1].toLowerCase().equals(ignore))
						return null;
				}
				newOutputDir = "." + variantTok[1];
			}
		}
		return newOutputDir;
	}

	private static boolean isSBSv1Supported() {
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		for (ISymbianSDK sdk : sdkMgr.getSDKList()) {
			File abld = new File(sdk.getEPOCROOT(), "epoc32/tools/abld.pl"); //$NON-NLS-1$
			if (abld.exists()) {
				long size = abld.length();
				if (size >= VALID_ABLD_SIZE)
					return true;
			}
		}
		return false;
	}
}
