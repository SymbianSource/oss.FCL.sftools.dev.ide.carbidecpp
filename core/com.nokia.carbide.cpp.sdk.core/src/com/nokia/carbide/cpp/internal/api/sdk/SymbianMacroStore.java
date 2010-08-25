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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.xml.SymbianMacroStoreLoader;
import com.nokia.carbide.cpp.sdk.core.*;

/**
 * Singleton class that allows clients to get Symbian OS macros for the OS, platform, and vendor
 * The data store from the XML file (data\symbianMacroStore.xml) stores all version information
 * strictly as a string with major.minor qualifiers (e.g. "9.1"). SDK specific macros are retrieved
 * based on both the SDK name and SDK version. If these are not correctly set in the SDK preferences
 * they will not be retrieved.
 *
 */

public class SymbianMacroStore {
	
	//private static final String PLUGIN_DATA_FOLDER = "data";
	//private static final String MACRO_STORE_FILE = "symbianMacroStore.xml";
	//private static final String BEECH_BRANCH_ID = "a";
	//private static final String CEDAR_BRANCH_ID = "b";
	private static final String MACRO_STORE_LOCATION ="/data/symbianMacroStore.xml";
	private static SymbianMacroStore instance;
	
	/**	A map of all macros for a given OS version. The key is the os version major.minor */	
	HashMap<String, List<String>> osMacros;
	/** A map of all the platform macros for a given OS version. */
	HashMap<String, HashMap<String, List<String>>> platformMacrosMap;
	/** A map of all vendor specific macros. The key is made up of the name:major.minor */
	HashMap<String, List<String>> vendorMacrosList;
	
	public static SymbianMacroStore getInstance() {
		if (instance == null) {
			instance = new SymbianMacroStore();
		}
		return instance;
	}
	
	private SymbianMacroStore() {
		initData();
		readMacros();
	}
	
	private void readMacros(){
		
		Bundle bundle = Platform.getBundle(SDKCorePlugin.getPluginId());
		if (bundle == null)
			return;
		
		URL macroStoreUrl = null;
		try {
			macroStoreUrl = FileLocator.toFileURL(bundle.getEntry(MACRO_STORE_LOCATION));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			SymbianMacroStoreType macroStoreType = SymbianMacroStoreLoader.loadMacroStore(macroStoreUrl);
			SymbianOSMacrosType osMacrosType = macroStoreType.getSymbianOSMacros();
			EList osVersionList = osMacrosType.getOsVersion();
			
			for (Iterator i = osVersionList.iterator(); i.hasNext();) {
				OsVersionType osVersion = (OsVersionType) i.next();
				String osVersionString = osVersion.getVersion();
				// Get the platform specific macros for this OS version
				PlatformMacrosType platformMacros = osVersion.getPlatformMacros();
				EList platformsList = platformMacros.getPlatform(); // Get all the platforms for this OS
				HashMap<String, List<String>> platToMacroStringMap = new HashMap<String, List<String>>();
				
				for (Iterator ii = platformsList.iterator(); ii.hasNext();) {
					PlatformType plat = (PlatformType) ii.next();
					// Get the macros for this os/platform combo...
					EList macroList = plat.getMacro();
					List<String> tempMacroList = new ArrayList<String>();
					for (Iterator iii = macroList.iterator(); iii.hasNext();) {
						String macroName = (String) iii.next();
						tempMacroList.add(macroName);
					}
					
					platToMacroStringMap.put(plat.getName(), tempMacroList);
				}
				
				this.platformMacrosMap.put(osVersionString, platToMacroStringMap);
				
				// Now get all the OS macros that apply to all platforms...
				OsMacrosType osMacros = osVersion.getOsMacros();
				EList osMacroList = osMacros.getMacro();
				List<String> osMacroStringList = new ArrayList<String>();
				for (Iterator iv = osMacroList.iterator(); iv.hasNext();) {
					String macroName = (String) iv.next();
					osMacroStringList.add(macroName);
				}
				
				this.osMacros.put(osVersionString, osMacroStringList);
				
			}
			
			VendorMacrosType vendorMacros = macroStoreType.getVendorMacros();
			EList vendorMacroList = vendorMacros.getSdkVendor();
			for (Iterator i = vendorMacroList.iterator(); i.hasNext();) {
				SdkVendorType vendor = (SdkVendorType)i.next();
				String vendorName = vendor.getName();
				String sdkVersion = vendor.getVersion();
				EList macroList = vendor.getMacro();
				String vendorSDKNameKey = vendorName + ":" + sdkVersion;
				List<String> tempMacroList = new ArrayList<String>();
				for (Iterator ii = macroList.iterator(); ii.hasNext();) {
					String macroName = (String) ii.next();
					tempMacroList.add(macroName);
				}
				
				vendorMacrosList.put(vendorSDKNameKey, tempMacroList);
				
			}
			} catch (IOException e){
				e.printStackTrace();
			} catch (URISyntaxException e){
				 e.printStackTrace();
			}
	} 
	
	/*
    private File getMacroStoreFile() throws IOException {
    	String location = "";
		try {
			URL pluginURL = FileLocator.find(SDKCorePlugin.getDefault().getBundle(), new Path(PLUGIN_DATA_FOLDER), null);
			pluginURL = FileLocator.resolve(pluginURL);
			String pluginFilePath = pluginURL.getFile();
			Path pluginPath = new Path(pluginFilePath);
			location = pluginPath.toOSString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new File (location + File.separator + MACRO_STORE_FILE);
	}
	*/
	
    private void initData(){
    	osMacros = new HashMap<String, List<String>>();
    	platformMacrosMap = new HashMap<String, HashMap<String, List<String>>>();
    	vendorMacrosList   = new HashMap<String, List<String>>();
    }
    
    /**
     * Get a list of Macros for a given OS. Format will just be the name (e.g. "FOO").
     * @param osVersion - A version object. Only the major and minor rev are checked.
     * @param branch - An OS branch (e.g. 'a' or 'b') or an empty string if none.
     * @return A full list of define macro names (no values). Returns an empty list if the OS version is not in the store
     * @deprecated - use getOSMacros(Version osVersion)
     */
    public List<String> getOSMacros(Version osVersion, String branch){
    	return getOSMacros(osVersion);
    }
    
    /**
     * Get a list of Macros for a given OS. Format will just be the name (e.g. "FOO").
     * @param osVersion - A version object. Only the major and minor rev are checked.
     * @param branch - An OS branch (e.g. 'a' or 'b') or an empty string if none.
     * @return A full list of define macro names (no values). Returns an empty list if the OS version is not in the store
     * @deprecated - use {@link ISymbianBuildContext#getVariantHRHDefines()}
     */
    public List<String> getOSMacros(Version osVersion){
    	List<String> osMacroList = new ArrayList<String>();
    	
    	String majStr = String.valueOf(osVersion.getMajor());
    	String minStr = String.valueOf(osVersion.getMinor());
    	if (majStr.length() > 0 && minStr.length() > 0){
    		String osVerStr =  majStr + "." + minStr;
    		osMacroList = osMacros.get(osVerStr);
    	}
    	
    	if (osMacroList == null){
    		return new ArrayList<String>(0);
    	}
    	return osMacroList;
    	
    }
    
    
    /**
     * Get a hash map of all the platforms and their associated macros for a given OS version
     * For OS version, only major/minor are checked in the store.
     * @param osVersion - A version object
     * @param branch - An OS branch (e.g. 'a' or 'b') or an empty string if none.
     * @return - A hash map where the key is the supported platform. Or an empty map if the OS version is not found.
     */
    public HashMap<String, List<String>> getAllPlatformMacros(Version osVersion, String branch){
    	HashMap<String, List<String>> allPlatMacroMap = new HashMap<String, List<String>>();
    	
    	String majStr = String.valueOf(osVersion.getMajor());
    	String minStr = String.valueOf(osVersion.getMinor());
    	if (majStr.length() > 0 && minStr.length() > 0){
    		String osVerStr =  majStr + "." + minStr;
    		allPlatMacroMap = platformMacrosMap.get(osVerStr + branch);
    	}
    	
    	if (allPlatMacroMap == null){
    		return new HashMap<String, List<String>>(0);
    	}
    	
    	return allPlatMacroMap;
    }
    
    /**
	 * Get a list of platform macros for a given OS/Platform combination
	 * @param osVersion - A version object
	 * @param branch - An OS branch (e.g. 'a' or 'b') or an empty string if none.
	 * @param platform - A valid build platform for a given OS (e.g. WINSCW, GCCE)
	 * @return A list of macro names (not values). Or an empty list of OS version or platform is not in the store.
	 * @deprecated Use {@link #getPlatformMacros(Version,String,IBSFCatalog,String)} instead
	 */
	public List<String> getPlatformMacros(Version osVersion, String branch, String platform){
		return getPlatformMacros(osVersion, branch, null, platform);
	}

	/**
     * Get a list of platform macros for a given OS/Platform combination
     * @param osVersion - A version object
     * @param branch - An OS branch (e.g. 'a' or 'b') or an empty string if none.
     * @param bsfCatalog - The catalog of BSF platforms
     * @param platform - A valid build platform for a given OS (e.g. WINSCW, GCCE)
     * @return A list of macro names (not values). Or an empty list of OS version or platform is not in the store.
     */
    public List<String> getPlatformMacros(Version osVersion, String branch, IBSFCatalog bsfCatalog, String platform){
    	List<String> macroList = new ArrayList<String>();
    	String majStr = String.valueOf(osVersion.getMajor());
    	String minStr = String.valueOf(osVersion.getMinor());
    	if (majStr.length() > 0 && minStr.length() > 0){
    		String osVerStr =  majStr + "." + minStr;
    		HashMap<String, List<String>> platMap = platformMacrosMap.get(osVerStr + branch);
    		if (platMap != null){
    			macroList = platMap.get(platform);
    			
    		}
    	}
    	
    	if (macroList == null) {
    		if (bsfCatalog != null) {
				// check for BSF platform
	    		//
	    		macroList = new ArrayList<String>();
	    		
				String basePlatform = null;
	    		IBSFPlatform bsfPlatform = bsfCatalog.findPlatform(platform);
	    		while (bsfPlatform != null) {
	    			// each BSF defines a macro of its own name
	    			macroList.add(bsfPlatform.getName().toUpperCase());
	    			
	    			if (bsfPlatform.getCustomizedPlatformName() != null) {
	    				basePlatform = bsfPlatform.getCustomizedPlatformName();
	    				bsfPlatform = bsfPlatform.getCustomizedPlatform();
	    			} else {
	    				break;
	    			}
	    		}
	    		
	    		// finally, get the base platform macros from the store
	    		if (basePlatform != null && !basePlatform.equalsIgnoreCase(platform)) {
	    			macroList.addAll(getPlatformMacros(osVersion, branch, null, basePlatform));
				}
    		}
    		
    		if (macroList == null) {
    			macroList = Collections.emptyList();
    		}
		}
    	
    	return macroList;
    }
    
    /**
     * Get a hash map of all the vendor specific macros. Not all vendors have specifc macros
     * that will be returned.
     * @param sdkVersion - 
     * @param name - This is the 'name' attribute from devices.xml. Format com.vendor.sdk.
     * @return A list of macro names (not values)
     */
    public List<String> getVendorMacros(Version sdkVersion, String name){
    	List<String> macroList = new ArrayList<String>();
    	String majStr = String.valueOf(sdkVersion.getMajor());
    	String minStr = String.valueOf(sdkVersion.getMinor());
    	if (majStr.length() > 0 && minStr.length() > 0){
    		String osVerStr =  majStr + "." + minStr;
    		macroList = vendorMacrosList.get(name + ":" + osVerStr);
    	}

    	if (macroList == null){
    		return new ArrayList<String>(0);
    	}
    	
    	return macroList;
    }
    
    /**
     * Get a list of supported OS versions in the macro store.
     * @return A list of OS verion strings. May have a branch identifier (e.g. 8.1a, 8.1b)
     */
    public List<String> getSupportedOSVersions(){
    	List<String> supportedOSVersionList = new ArrayList<String>();
    	for (String osVersionString : osMacros.keySet()){
    		supportedOSVersionList.add(osVersionString);
    	}
    	
    	if (supportedOSVersionList == null){
    		return Collections.emptyList();
    	}
    	
    	Collections.sort(supportedOSVersionList, new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
    		
    	});
    	
    	return supportedOSVersionList;
    }
    
    /**
     * Get a list of supported platforms for a particular OS from the macro store,
     * using the BSF catalog to find non-built-in entries.
     * @return A list of platforms.
     */
	public List<String> getSupportedPlatforms(Version osVersion, String branch,
			IBSFCatalog catalog) {
    	List<String> platList = new ArrayList<String>();
    	String majStr = String.valueOf(osVersion.getMajor());
    	String minStr = String.valueOf(osVersion.getMinor());
    	
    	if (majStr.length() > 0 && minStr.length() > 0){
    		 HashMap<String, List<String>> platMap = platformMacrosMap.get(majStr + "." + minStr + branch);
    		if (platMap != null){
    			for (String platform : platMap.keySet()){
    				platList.add(platform);
    			}
    		}
    	}
    	
    	if (platList == null){
    		// Can't find OS platforms
    		platList = new ArrayList<String>(0);
    	}
    	
    	if (catalog != null) {
    		IBSFPlatform[] platforms = catalog.getVariantPlatforms();
    		for (IBSFPlatform platform : platforms ) {
    			platList.add(platform.getName().toUpperCase());
    		}
    	}
    	
    	return platList;
	}

	/**
     * Get a list of supported platforms for a particular OS from the macro store.
     * @return A list of platforms.
     */
    public List<String> getSupportedPlatforms(Version osVersion, String branch){
    	return getSupportedPlatforms(osVersion, branch, null);
    }
    
    /**
     * Get a list of known SDK versions. This is done without regard to the sdk name or vendor.
     * SDK version checks in the macro store are done with regard to a version (major/minor) check
     * along with the name attribute (e.g. com.vendor.sdk).
     * @return
     */
    public List<String> getSDKVersions(){
    	List<String> verList = new ArrayList<String>();
    	
    	verList.add("5.2");
    	verList.add("5.1");
    	verList.add("5.0");
    	verList.add("3.2");
    	verList.add("3.1");
    	verList.add("3.0");
    	verList.add("2.8");
    	verList.add("2.6");
    	verList.add("2.1");
    	verList.add("1.2");
    	
    	Collections.sort(verList, new Comparator<String>() {

			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}
    		
    	});
    	
    	return verList;
    }
}
