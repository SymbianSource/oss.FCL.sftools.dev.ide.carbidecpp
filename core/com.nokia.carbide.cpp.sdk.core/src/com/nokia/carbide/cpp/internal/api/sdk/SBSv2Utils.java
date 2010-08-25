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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.cdt.utils.spawner.EnvironmentReader;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.Version;
import org.osgi.service.prefs.BackingStoreException;

import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.Logging;

/**
 * Utility class for SBSv2
 * @since 2.0
 */
public class SBSv2Utils {

	//private static final String SBSV2_FILTERED_CONFIGS_STORE = "sbsv2FilteredConfigs"; //$NON-NLS-1$
	private static final String SBSV2_FILTERED_CONFIGS_STORE_V2 = "sbsv2FilteredConfigs_V2"; //$NON-NLS-1$
	//private static final String SBSV2_FILTERED_CONFIGS_STORE_INITED = "sbsv2FilteredConfigsInited"; //$NON-NLS-1$
	private static final String SBSV2_FILTERED_CONFIGS_STORE_INITED_V2 = "sbsv2FilteredConfigsInited_V2"; //$NON-NLS-1$
	private static final String SBSV2_FILTERED_CONFIGS_DELIMETER = ";"; //$NON-NLS-1$
	private static final long VALID_ABLD_SIZE = 1024;

	/**
	 * Map of usable Raptor alias for -c parameter and base platform: <alise, base plat>
	 */
//	private static Map<String, String> unfilteredSBSv2ConfigNames;

	/** Path, to and including the SBS script */
	protected static IPath sbsPath;

	private static boolean scannedSbsState = false;
	private static final String sbsScriptName = HostOS.IS_WIN32 ? "sbs.bat" : "sbs"; 
	
	/**
     * Get the path to the SBSv2 bin directory, not including the sbs executable.  
     * May or may not actually exist.
     * @return absolute path to the bin directory, or null if sbs is not found
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
     * Given a list of SDKs, returns the list of the SDK's supported by SBSv2
     * @param sdks list of SDK's to check
     * @return list of SBSv2 supported SDK's, may be empty
     */
    public static List<ISymbianSDK> getSupportedSDKs(List<ISymbianSDK> sdks) {
    	List<ISymbianSDK> supportedSDKs = new ArrayList<ISymbianSDK>();

    	// If there is no SBSv1 builder, then assume all SDKs are SBSv2 capable
    	if (!enableSBSv1Support()) {
    		supportedSDKs.addAll(sdks);
    	} else {
	    	// Filter out anything older than 9.4
	    	for (ISymbianSDK sdk : sdks) {
	    		Version osVersion = sdk.getOSVersion();
	    		if (osVersion.getMajor() > 8 || osVersion.getMajor() == 0 ||
	    				(osVersion.getMajor() == 9 && osVersion.getMinor() > 3)) {
	    			supportedSDKs.add(sdk);
	    		}
	    	}
    	}
    	return supportedSDKs;
    }
    
	/**
	 * Returns the list of SBSv2 build configuration names that should
	 * INCLUDED in any UI. Only configs to be displayed are saved
	 */
	public static List<String> getSBSv2FilteredConfigPreferences() {
		List<String> buildAliasList = new ArrayList<String>();
		IEclipsePreferences prefs = new InstanceScope().getNode(SDKCorePlugin.PLUGIN_ID);
		if (prefs != null) {
			String configs = prefs.get(SBSV2_FILTERED_CONFIGS_STORE_V2, "");
			if (configs.length() == 0){
				initDefaultConfigsToFilter();
				configs = prefs.get(SBSV2_FILTERED_CONFIGS_STORE_V2, "");
			}
			String aliasesToInclude[] = configs.split(SBSV2_FILTERED_CONFIGS_DELIMETER);
			for (String alias : aliasesToInclude){
				buildAliasList.add(alias);
			}
		}
		
		return buildAliasList;
	}

	/**
	 * Set the list of SBSv2 build configurations that should be included in a build config list
	 * All others will be filtered out
	 * @param configs configs to be filtered
	 */
	public static void setSBSv2FilteredConfigs(String[] configs) {
		IEclipsePreferences prefs = new InstanceScope().getNode(SDKCorePlugin.PLUGIN_ID);
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
				prefs.put(SBSV2_FILTERED_CONFIGS_STORE_V2, store);
				try {
					prefs.flush();
				} catch (BackingStoreException e) {
					Logging.log(SDKCorePlugin.getDefault(),
							Logging.newStatus(SDKCorePlugin.getDefault(), e));
				}
			}
		}
	}

	/**
	 * There are many build aliases presented by default from Raptor
	 * Filter out those that are less commonly used on new workspace creation.
	 */
	public static void initDefaultConfigsToFilter() {
		IEclipsePreferences prefs = new InstanceScope().getNode(SDKCorePlugin.getPluginId());
		String inited = prefs.get(SBSV2_FILTERED_CONFIGS_STORE_INITED_V2, "");
		if (inited == null || inited.length() == 0){
			List<String> defaultConfigsToFilter = new ArrayList<String>();
			
			defaultConfigsToFilter.add("armv5_udeb");
			defaultConfigsToFilter.add("armv5_urel");
			defaultConfigsToFilter.add("armv5_udeb_gcce");
			defaultConfigsToFilter.add("armv5_urel_gcce");
			defaultConfigsToFilter.add("winscw_urel");
			defaultConfigsToFilter.add("winscw_udeb");
			
			prefs.put(SBSV2_FILTERED_CONFIGS_STORE_INITED_V2, "true");
			setSBSv2FilteredConfigs(defaultConfigsToFilter.toArray(new String[defaultConfigsToFilter.size()]));
			
		}
	}

	/**
	 * Whether or not to display SBSv1 builder UI
	 * @return true if SBSv1 is available, false otherwise
	 */
	
	public static boolean enableSBSv1Support() {
		
		// hard-coded shut off
		if (!SDKCorePlugin.SUPPORTS_SBSV1_BUILDER)
			return false;
		
		// check perl script
		else if (isSBSv1Supported()) 
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

	/**
	 * (Re-)scan the SBSv2 / Raptor configuration
	 * @return message if error, else null
	 */
	public static String scanSBSv2() {
		if (sbsPath != null){
			return null;
		}
		// do some basic checks
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
     */
	public static IPath getSBSPath() {
		if (!scannedSbsState) {
			scanSBSv2();
			scannedSbsState = true;
		}
		return sbsPath != null ? sbsPath : new Path(sbsScriptName);  // dummy
	}
	
	private static List<ISymbianBuildContext> sortContexts(List<ISymbianBuildContext> contexts){ 
		Collections.sort(contexts, new Comparator<ISymbianBuildContext>() {
			public int compare(ISymbianBuildContext o1, ISymbianBuildContext o2) {
				return o2.getDisplayString().compareTo(o1.getDisplayString());
			}
		});
		
		return contexts; 
	}
	
	private static boolean isSBSv1Supported() {
		if (HostOS.IS_UNIX){
			return false;
		}
		
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
