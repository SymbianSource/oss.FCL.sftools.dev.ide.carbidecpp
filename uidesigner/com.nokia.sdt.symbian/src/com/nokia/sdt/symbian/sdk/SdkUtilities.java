/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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
*
*/

package com.nokia.sdt.symbian.sdk;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;

import org.eclipse.core.resources.IProject;
import org.osgi.framework.Version;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Wrap the SDK utilities
 * 
 *
 */
public class SdkUtilities {
	
	public static final String S60_VENDOR_PATTERN = 
		ISymbianSDK.S60_FAMILY_ID + "|" + ISymbianSDK.SERIES60_FAMILY_ID;
	public static final String UIQ_VENDOR_PATTERN = ISymbianSDK.UIQ_FAMILY_ID;
    
	public static final Comparator compareBySdkId = new Comparator<ISymbianSDK>() {
		public int compare(ISymbianSDK o1, ISymbianSDK o2) {
			return o1.getUniqueId().compareTo(o2.getUniqueId());
		}
	};

    /**
     * Find closest SDK for a given vendor and version.  
     * 
     * @param vendor vendor/family id (SDKManifest.xxx_FAMILY_ID)
     * @param versionString version string (e.g. "2.6" or "3.0.0")
     * @return the directory file
     */
    public static ISymbianSDK getBestSDKForVendorAndVersion(String vendor, String versionString) {
        String vendorPattern = vendor;
        if (vendor.equalsIgnoreCase(SymbianModelUtils.S60_SDK)
        		|| vendor.equalsIgnoreCase(ISymbianSDK.S60_SDK_NAME)
        		|| vendor.equalsIgnoreCase(ISymbianSDK.SERIES60_SDK_NAME)
        		|| vendor.equalsIgnoreCase(ISymbianSDK.S60_FAMILY_ID)
        		|| vendor.equalsIgnoreCase(ISymbianSDK.SERIES60_FAMILY_ID)) {
        	vendorPattern = S60_VENDOR_PATTERN;
        } else if (vendor.equalsIgnoreCase(SymbianModelUtils.UIQ_SDK)
        		||	vendor.equalsIgnoreCase(ISymbianSDK.UIQ_SDK_NAME)
        		|| vendor.equalsIgnoreCase(ISymbianSDK.UIQ_FAMILY_ID)) {
        	vendorPattern = UIQ_VENDOR_PATTERN;
        }
    	List<ISymbianSDK> sdks = getMatchingVendorSDKs(vendorPattern);

    	Version version = new Version(3, 0, 0);
    	try {
    		version = new Version(versionString);
    	} catch (IllegalArgumentException e) {
    		SymbianPlugin.getDefault().log("Invalid SDK version: " + versionString); //$NON-NLS-1$
    	}
    	
    	// get SDK with exact major+minor and closest micro version
    	ISymbianSDK closestCompatible = null;
    	ISymbianSDK closestMatch = null;
    	int microDiff = 999;
    	int minorDiff = 999;
    	for (Iterator iter = sdks.iterator(); iter.hasNext();) {
			ISymbianSDK sdk = (ISymbianSDK) iter.next();
			
			// no known version?
			Version sdkVersion = sdk.getSDKVersion();
			if (sdkVersion == null)
				continue;
			
			if (!sdkVersionIsCompatible(sdkVersion, version, S60_VENDOR_PATTERN.equals(vendorPattern)))
				continue;
			
			if (sdkVersion.getMajor() == version.getMajor() && sdkVersion.getMinor() == version.getMinor()) {
				// exact major+minor match
				if (closestMatch == null || Math.abs(sdkVersion.getMicro() - version.getMicro()) < microDiff) { 
					closestMatch = sdk;
					microDiff = Math.abs(sdkVersion.getMicro() - version.getMicro());
				}
			} else {
				// this SDK is backward compatible
				if (closestCompatible == null || Math.abs(sdkVersion.getMinor() - version.getMinor()) < minorDiff) {
					closestCompatible = sdk;
					minorDiff = Math.abs(sdkVersion.getMinor() - version.getMinor());
				}
			}
		}
    	
    	if (closestMatch != null)
    		return closestMatch;
    	else if (closestCompatible != null)
    		return closestCompatible;
    	else
    		return null;
    }

    /**
     * Tell whether the given SDK is compatible with the given version 
	 * @param sdkVersion an SDK version
	 * @param version a version to test
	 * @return true if compatible - for s60, allow majors to differ if both > 3
	 */
	private static boolean sdkVersionIsCompatible(Version sdkVersion, Version version, boolean isS60) {
		int sdkMajor = sdkVersion.getMajor();
		int baselineMajor = version.getMajor();
		int sdkMinor = sdkVersion.getMinor();
		int baselineMinor = version.getMinor();

		if (isS60) {
			if (sdkMajor >= 3 && baselineMajor >= 3 && sdkMajor > baselineMajor)
				return true;
		}

		return sdkMajor == baselineMajor && sdkMinor >= baselineMinor;
	}

	/**
     * Get a list of SDKs matching the given vendor.
	 * @return a list of ISymbianSDK, each of which an SDK manifest and an EPOCROOT
     */
    public static List<ISymbianSDK> getMatchingVendorSDKs(String vendorPattern) {
    	Pattern pattern = Pattern.compile(vendorPattern, Pattern.CASE_INSENSITIVE);
    	List<ISymbianSDK> matchList = new ArrayList<ISymbianSDK>();
    	List<ISymbianSDK> sdks = SDKCorePlugin.getSDKManager().getSDKList();
    	for (Iterator iter = sdks.iterator(); iter.hasNext();) {
			ISymbianSDK sdk = (ISymbianSDK) iter.next();
			String sdkFamily = sdk.getFamily();
			if (sdkFamily != null && pattern.matcher(sdkFamily).matches())
				matchList.add(sdk);
    	}
    	return matchList;
    }
    
	/**
	 *	Get the list of SDKs that correspond to the build configurations 
	 *	for the project.
	 * 	@return sorted list or null for broken project.  Every entry has
	 * an SDK manifest and an EPOCROOT
	 */
	public static List<ISymbianSDK> getSDKsUsedInProject(IProject project) {
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (cpi == null)
			return null;
		List<ICarbideBuildConfiguration> configs = cpi.getBuildConfigurations();
		if (configs == null)
			return null;
		
		List<ISymbianSDK> sdkList = new ArrayList<ISymbianSDK>();
		for (ICarbideBuildConfiguration config : configs) {
			ISymbianSDK sdk = config.getSDK();
			if (sdk != null) {
				if (!sdkList.contains(sdk))
					sdkList.add(sdk);
			}
		}
		return sdkList;
	}
	
	public static class SelectableSDKInfo {
		public List<ISymbianSDK> selectableSDKs;
		public ISymbianSDK minimumConfiguredSDK;
	}
	
	/**
	 * Return a list of the SDKs that may be selected by the user as
	 * the component versions e.g. "baseline" SDK.  
	 * <p>
	 * The rules are:
	 * <p>
	 * 1) Only the minimum SDK implied by the configured SDKs is allowed.<br>
	 * 2) All other installed SDKs compatible with that minimum are allowed.<br>
	 * 
	 * A class is used to return both the list of selectable SDKs, and the minimum
	 * selectable SDK, in case it's needed as a default value.
	 */
	public static SelectableSDKInfo getSelectableSDKs(List<ISymbianSDK> configSdks, String vendorPattern) {
		List<ISymbianSDK> allSdks = SdkUtilities.getMatchingVendorSDKs(vendorPattern);
		
		// track the minimum config SDK, to select as the default
		ISymbianSDK minimumConfigSdk = null;
		Version minimumConfigSdkVersion = null;
		
		List<ISymbianSDK> selectable = new ArrayList<ISymbianSDK>();
		if (configSdks != null && configSdks.size() > 0) {
			
			// Get the minimum configured SDK. 
			for (ISymbianSDK cfgSdk : configSdks) {
				Version cfgVersion = cfgSdk.getSDKVersion();
				
				// ignore SDKs for which we don't have a version
				if (cfgVersion.getMajor() == 0 && cfgVersion.getMinor() == 0)
					continue;
				
				// track the minimal configured SDK for the project
				if (minimumConfigSdk != null) {
					if (cfgVersion.compareTo(minimumConfigSdkVersion) < 0) {
						minimumConfigSdk = cfgSdk;
						minimumConfigSdkVersion = cfgVersion;
					}
				} else {
					minimumConfigSdk = cfgSdk;
					minimumConfigSdkVersion = cfgVersion;
				}
			}

			// add that minimum: may be null if version 0.0 is chosen
			if (minimumConfigSdk != null) {
				selectable.add(minimumConfigSdk);
				
				// now, add all SDKs older and compatible with the minimum
				for (ISymbianSDK sdk : allSdks) {
					Version sdkVersion = sdk.getSDKVersion();
					// allow user to select any SDK with the same major version
					if (!selectable.contains(sdk) && 
							sdkVersionIsCompatible(minimumConfigSdkVersion, sdkVersion, S60_VENDOR_PATTERN.equals(vendorPattern))) {
						selectable.add(sdk);
					}
				}
			}
		} 

		SelectableSDKInfo result = new SelectableSDKInfo();
		result.selectableSDKs = selectable;
		result.minimumConfiguredSDK = minimumConfigSdk;
		return result;
	}

	/**
	 * Return a list of the SDKs that may be selected by the user as
	 * the component versions e.g. "baseline" SDK.  
	 * <p>
	 * The rules are:
	 * <p>
	 * 1) Only the minimum SDK implied by the configured SDKs is allowed.<br>
	 * 2) All other installed SDKs compatible with that minimum are allowed.<br>
	 * 
	 * A class is used to return both the list of selectable SDKs, and the minimum
	 * selectable SDK, in case it's needed as a default value.
	 */
	public static SelectableSDKInfo getSelectableSDKsForProject(IProject project, String vendorPattern) {
		// get the SDKs referenced by build configurations
		List<ISymbianSDK> configSdks = SdkUtilities.getSDKsUsedInProject(project);
		return getSelectableSDKs(configSdks, vendorPattern);
	}

    /**
     * Tell whether an SDK is S60 or not.
     * @param sdk
     * @return
     */
    public static boolean isSeries60(ISymbianSDK sdk) {
    	return sdk.isS60();
    }

    /**
     * Get the S60 version for the SDK.
     * @param sdk
     * @return Version or null if not S60
     */
    public static Version getSeries60Version(ISymbianSDK sdk) {
        if (!isSeries60(sdk))
            return null;
        return sdk.getSDKVersion();
    }
}
