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
import java.util.Collections;
import java.util.List;

import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.internal.sdk.core.model.SBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianMissingSDKFactory;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianSDK;
import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.IBSFPlatform;
import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

public class BuildContextSBSv1 implements ISBSv1BuildContext {

	private String sdkId;
	private String platform;
	private String target;
	private String displayString = null;
		
	private static String EMULATOR_DISPLAY_TEXT = "Emulator"; //$NON-NLS-1$
	private static String PHONE_DISPLAY_TEXT = "Phone"; //$NON-NLS-1$
	private static String DEBUG_DISPLAY_TEXT = "Debug"; //$NON-NLS-1$
	private static String RELEASE_DISPLAY_TEXT = "Release"; //$NON-NLS-1$
	private static String SPACE_DISPLAY_TEXT = " "; //$NON-NLS-1$
	private static String SDK_NOT_INSTALLED = "SDK not installed"; //$NON-NLS-1$
	
	// a copy of bad SDK default to fall back
	private static ISymbianSDK fallbackForBadSdk = SymbianMissingSDKFactory.createInstance("dummy_ID"); //$NON-NLS-1$
	
	// Preference data
	protected BuildArgumentsInfo buildArgumentsInfo;
	protected final static String ARGUMENTS_DATA_ID = "ARGUMENTS_DATA_ID"; //$NON-NLS-1$
	
	public BuildContextSBSv1(ISymbianSDK theSDK, String thePlatform, String theTarget) {
		sdkId = theSDK.getUniqueId();
		platform = thePlatform.toUpperCase();
		target = theTarget.toUpperCase();
		
		getDisplayString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((platform == null) ? 0 : platform.hashCode());
		result = prime * result + ((getSDK() == null) ? 0 : getSDK().getEPOCROOT().hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BuildContextSBSv1))
			return false;
		final BuildContextSBSv1 other = (BuildContextSBSv1) obj;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		if (getSDK() == null) {
			if (other.getSDK() != null)
				return false;
		} else if (!getSDK().getEPOCROOT().equals(other.getSDK().getEPOCROOT()))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target)) {
			return false;
		}
		return true;
	}


	public ISymbianSDK getSDK() {
		
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(sdkId, true);
		if (sdk == null){
			sdk = fallbackForBadSdk;
		}
		
		return sdk;
	}

	public String getPlatformString() {
		return platform.toUpperCase();
	}

	public String getTargetString() {
		return target.toUpperCase();
	}

	public String getDisplayString() {
		if (displayString == null) {
			// in the form Emulation Debug (WINSCW) [S60_3rd_MR] or
			// Phone Release (GCCE) [S60_3rd_MR]
			if (platform.compareTo(EMULATOR_PLATFORM) == 0) {
				displayString = EMULATOR_DISPLAY_TEXT;
			} else {
				displayString = PHONE_DISPLAY_TEXT;
			}
			
			if (target.compareTo(ISymbianBuildContext.DEBUG_TARGET) == 0) {
				displayString = displayString + SPACE_DISPLAY_TEXT + DEBUG_DISPLAY_TEXT;
			} else {
				displayString = displayString + SPACE_DISPLAY_TEXT + RELEASE_DISPLAY_TEXT;
			}
			
			String basePlatform = platform;
			
			displayString = displayString + " (" + basePlatform + ") [" + getSDK().getUniqueId() + "]"; //$NON-NLS-1$
		}
		return displayString;
	}
	
	public static ISymbianBuildContext getBuildContextFromDisplayName(String displayName) {
		if (isValidConfigName(displayName)) {
			String sdkId = getSDKIDFromConfigName(displayName);
			ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDK(sdkId, true);
			if (sdk == null) {
				// add a dummy should a build context ask for a removed SDK
				sdk = SDKManagerInternalAPI.addMissingSdk(sdkId);
			}
						
			return new BuildContextSBSv1(sdk, 
						getPlatformFromBuildConfigName(displayName), 
						getTargetFromBuildConfigName(displayName));
		}
		return new BuildContextSBSv1(fallbackForBadSdk, SDK_NOT_INSTALLED, SDK_NOT_INSTALLED);
	}

	private static String getPlatformFromBuildConfigName(String configName) {
		String[] tokens = configName.split(SPACE_DISPLAY_TEXT);
		String sdkIdToken = tokens[2];
		if (sdkIdToken.contains("_")){
			sdkIdToken = sdkIdToken.substring(1, sdkIdToken.length()-1);
			String[] aliasTokens = sdkIdToken.split("_");
			return aliasTokens[0];
		} else {
			return sdkIdToken.substring(1, sdkIdToken.length()-1);
		}
		
	}

	public static String getSDKIDFromConfigName(String configName) {
		int indexBegin = configName.indexOf("[");  //$NON-NLS-1$
		int indexEnd = configName.indexOf("]");  //$NON-NLS-1$
		if (indexBegin > 0 && indexEnd > 0){
			return configName.substring(indexBegin+1, indexEnd);
		} else {
			return ""; //$NON-NLS-1$
		}
	}

	private static String getTargetFromBuildConfigName(String configName) {
		String[] tokens = configName.split(SPACE_DISPLAY_TEXT);
		if (tokens[1].compareTo(DEBUG_DISPLAY_TEXT) == 0) {
			return ISymbianBuildContext.DEBUG_TARGET;
		} else {
			return ISymbianBuildContext.RELEASE_TARGET;
		}
	}

	private static boolean isValidConfigName(String configName) {
		// <Phone | Emulator> <Target> (<Platform>) [<SDK ID>]
		if (configName != null && !configName.equals("")) { //$NON-NLS-1$
			String[] tokens = configName.split(SPACE_DISPLAY_TEXT);
			if (tokens.length >= 4) {
				if (tokens[0].compareTo(EMULATOR_DISPLAY_TEXT) == 0 || tokens[0].compareTo(PHONE_DISPLAY_TEXT) == 0) {
					if (tokens[1].compareTo(DEBUG_DISPLAY_TEXT) == 0 || tokens[1].compareTo(RELEASE_DISPLAY_TEXT) == 0) {
						if (tokens[2].matches("(.*)")) { //$NON-NLS-1$
							if (tokens[3].matches("\\[.*")) { //$NON-NLS-1$
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public String toString() {
		return getDisplayString();
	}
	
	private ISBSv1BuildInfo getBuildInfo() {
		ISBSv1BuildInfo buildInfo = (ISBSv1BuildInfo)getSDK().getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
		if (buildInfo == null) {
			buildInfo = new SBSv1BuildInfo(getSDK());
			((SymbianSDK)getSDK()).setBuildInfo(buildInfo, ISymbianBuilderID.SBSV1_BUILDER);
		}
		return buildInfo;
	}

	public String getDefaultDefFileDirectoryName() {
		// TODO: How the ASSP option affects the path?

		String dirName = getDefFileDirectoryNameForPlatform(platform);
		if (dirName == null) {
			// check BSF's
			IBSFCatalog catalog = getBuildInfo().getBSFCatalog();
			if (catalog != null) {
	    		for (IBSFPlatform plat : catalog.getPlatforms()) {
	    			if (plat.getName().compareToIgnoreCase(platform) == 0) {
	    				String mainPlatform = catalog.getReleasePlatform(platform);
	    				if (mainPlatform != null) {
	    					dirName = getDefFileDirectoryNameForPlatform(mainPlatform);
	    					if (dirName == null || dirName.length() < 1) {
	    						// fallback - all BSF's should be EABI anyway
			    				return "EABI"; //$NON-NLS-1$
	    					}
	    				}
	    			}
	    		}
	    	}
		}
		
		if (dirName == null) {
			// fallback for unknown cases
			dirName = platform;
		}
		
		return dirName;
	}
	
	private String getDefFileDirectoryNameForPlatform(String platform) {
		if (platform.equals(EMULATOR_PLATFORM)) {
			return "BWINS"; //$NON-NLS-1$
		} else if (platform.equals(ARMV5_PLATFORM)
					|| platform.equals(ARMV5_ABIV2_PLATFORM)
					|| platform.equals(ARMV6_PLATFORM)
					|| platform.equals(ARMV6_ABIV2_PLATFORM)
					|| platform.equals(GCCE_PLATFORM)) {
			return "EABI"; //$NON-NLS-1$
		}
		return null;
	}

	public IPath getCompilerPrefixFile() {
		if (platform.equals(GCCE_PLATFORM)) {
			return getGCCEPrefixFilePath();
		} else if (platform.equals(ARMV5_PLATFORM)
					|| platform.equals(ARMV5_ABIV2_PLATFORM)
					|| platform.equals(ARMV6_PLATFORM)
					|| platform.equals(ARMV6_ABIV2_PLATFORM)) {
			return getRVCTPrefixFilePath();
		} else {
			// check BSF's
			IBSFCatalog catalog = getBuildInfo().getBSFCatalog();
	    	if (catalog != null) {
	    		for (IBSFPlatform plat : catalog.getPlatforms()) {
	    			if (plat.getName().compareToIgnoreCase(platform) == 0) {
	    				String mainPlatform = catalog.getReleasePlatform(platform);
	    				if (mainPlatform != null) {
	    					if (mainPlatform.equals(GCCE_PLATFORM)) {
	    						return getGCCEPrefixFilePath();
	    					} else if (mainPlatform.equals(ARMV5_PLATFORM) 
	    								|| mainPlatform.equals(ARMV5_ABIV2_PLATFORM)
	    								|| mainPlatform.equals(ARMV6_PLATFORM)
	    								|| mainPlatform.equals(ARMV6_ABIV2_PLATFORM)) {
	    						return getRVCTPrefixFilePath();
	    					} else {
	    						// fallback - all BSF's should be EABI anyway
	    						return getRVCTPrefixFilePath();
	    					}
	    				}
	    			}
	    		}
	    	}
		}

		// fallback for WINSCW, MSVC, etc.
		return null;
	}

	private IPath getIncludePath() {
		return getSDK().getIncludePath();
	}

	private IPath getGCCEPrefixFilePath() {
		return getIncludePath().append("gcce/gcce.h"); //$NON-NLS-1$
	}

	private IPath getRVCTPrefixFilePath() {
		IRVCTToolChainInfo[] installedRVCTTools = SDKCorePlugin.getSDKManager().getInstalledRVCTTools();
		// use default in case tools aren't installed
		String rvctFragment = "rvct2_2"; //$NON-NLS-1$
		if (installedRVCTTools.length > 0) {
			rvctFragment = getRVCTFragment(installedRVCTTools[0]);
		}
		IPath prefixFilePath = getIncludePath().append(rvctFragment).append(rvctFragment + ".h"); //$NON-NLS-1$
		if (prefixFilePath.toFile().exists()){
			return prefixFilePath;
		} else {
			// SF kits around SF^3 started to only use a single rvct.h header instead of specific versioned ones
			// based on the default installation
			return getIncludePath().append("rvct").append("rvct" + ".h");
		}
	}

	private String getRVCTFragment(IRVCTToolChainInfo info) {
		int major = 0, minor = 0;
		if (info != null) {
			Version rvctToolsVersion = info.getRvctToolsVersion();
			if (rvctToolsVersion != null) {
				major = info.getRvctToolsVersion().getMajor();
				minor = info.getRvctToolsVersion().getMinor();
			}
		}
		return "rvct" + major + "_" + minor; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public List<IDefine> getVariantHRHDefines() {

		return getCachedData().getVariantHRHDefines();
	}

	public List<File> getPrefixFileIncludes() {
		return getCachedData().getPrefixFileIncludes();
	}


	public List<IDefine> getCompilerMacros() {
		// we parse the compiler prefix file to gather macros.  this can be time consuming so do it
		// once and cache the values.  only reset the cache when the compiler prefix has changed.
		
		IPath prefixFile = getCompilerPrefixFile();
		if (prefixFile == null) {
			return Collections.emptyList();
		}
		
		return getCachedData().getCompilerMacros(prefixFile);
	}


	public String getBuildVariationName() {
		String varName = "";
		
		String[] tokens = getPlatformString().split("\\.");
		if (tokens.length == 2){
			varName = tokens[1];
		}
		
		return varName;
	}


	public boolean isSymbianBinaryVariation() {
		if (getPlatformString().split("\\.").length == 2){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the cache holding the data that applies to this build context globally.
	 * A build context is subclassed by CarbideBuildConfiguration, which has multiple
	 * instances at runtime, thus, a SymbianBuildContext instance should not hold a cache itself.
	 * @return cache, never <code>null</code>
	 */
	private SymbianBuildContextDataCache getCachedData() {
		return SymbianBuildContextDataCache.getCache(this);
	}
	

	public String getBasePlatformForVariation() {
		String plat = "";
		
		String[] tokens = getPlatformString().split("\\.");
		if (tokens.length == 2){
			plat = tokens[0];
		} else {
			return platform;
		}
		
		return plat;
	}


	/**
	 * Get the list of #include paths detected for this context.
	 * @return List or <code>null</code>
	 */
	public List<File> getCachedSystemIncludePaths() {
		return getCachedData().getSystemIncludePaths();
	}

	@Override
	public void loadConfigurationSettings(ICStorageElement se) {
		if (se.getName().equals(ARGUMENTS_DATA_ID)) {
			loadBuildArgsFromStorage(se);
		}
	}

	@Override
	public IBuildArgumentsInfo getBuildArgumentsInfo() {
		return (IBuildArgumentsInfo)buildArgumentsInfo;
	}
	
	@Override
	public BuildArgumentsInfo getBuildArgumentsInfoCopy() {
		return new BuildArgumentsInfo(buildArgumentsInfo);
	}
	
	@Override
	public void setBuildArgumentsInfo(BuildArgumentsInfo buildArgumentsInfo) {
		this.buildArgumentsInfo = buildArgumentsInfo;
	}
	
	@SuppressWarnings("deprecation")
	private void loadBuildArgsFromStorage(ICStorageElement rootStorage) {
		buildArgumentsInfo = new BuildArgumentsInfo(getSDK());
		String value = rootStorage.getAttribute(BuildArgumentsInfo.BLDMAKEBLDFILESARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.bldmakeBldFilesArgs = value;
		}
		
		value = rootStorage.getAttribute(BuildArgumentsInfo.BLDMAKECLEANARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.bldmakeCleanArgs = value;
		}

		value = rootStorage.getAttribute(BuildArgumentsInfo.ABLDBUILDARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.abldBuildArgs = value;
		}

		value = rootStorage.getAttribute(BuildArgumentsInfo.ABLDEXPORTARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.abldExportArgs = value;
		}

		value = rootStorage.getAttribute(BuildArgumentsInfo.ABLDMAKEFILEARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.abldMakefileArgs = value;
		}

		value = rootStorage.getAttribute(BuildArgumentsInfo.ABLDLIBRARYARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.abldLibraryArgs = value;
		}

		value = rootStorage.getAttribute(BuildArgumentsInfo.ABLDRESOURCEARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.abldResourceArgs = value;
		}

		value = rootStorage.getAttribute(BuildArgumentsInfo.ABLDTARGETARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.abldTargetArgs = value;
		}

		value = rootStorage.getAttribute(BuildArgumentsInfo.ABLDFINALARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.abldFinalArgs = value;
		}

		value = rootStorage.getAttribute(BuildArgumentsInfo.ABLDCLEANARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.abldCleanArgs = value;
		}

		value = rootStorage.getAttribute(BuildArgumentsInfo.ABLDFREEZEARGSSTORAGE);
		if (value != null) {
			buildArgumentsInfo.abldFreezeArgs = value;
		}
	}
	
	public void saveBuildArgsToStorage(ICStorageElement rootStorage) {
		if (buildArgumentsInfo == null){
			buildArgumentsInfo = new BuildArgumentsInfo(getSDK());
		}
		
		if (buildArgumentsInfo.bldmakeBldFilesArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.BLDMAKEBLDFILESARGSSTORAGE, buildArgumentsInfo.bldmakeBldFilesArgs);
		}

		if (buildArgumentsInfo.bldmakeCleanArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.BLDMAKECLEANARGSSTORAGE, buildArgumentsInfo.bldmakeCleanArgs);
		}

		if (buildArgumentsInfo.abldBuildArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.ABLDBUILDARGSSTORAGE, buildArgumentsInfo.abldBuildArgs);
		}

		if (buildArgumentsInfo.abldExportArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.ABLDEXPORTARGSSTORAGE, buildArgumentsInfo.abldExportArgs);
		}

		if (buildArgumentsInfo.abldMakefileArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.ABLDMAKEFILEARGSSTORAGE, buildArgumentsInfo.abldMakefileArgs);
		}

		if (buildArgumentsInfo.abldLibraryArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.ABLDLIBRARYARGSSTORAGE, buildArgumentsInfo.abldLibraryArgs);
		}

		if (buildArgumentsInfo.abldResourceArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.ABLDRESOURCEARGSSTORAGE, buildArgumentsInfo.abldResourceArgs);
		}

		if (buildArgumentsInfo.abldTargetArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.ABLDTARGETARGSSTORAGE, buildArgumentsInfo.abldTargetArgs);
		}

		if (buildArgumentsInfo.abldFinalArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.ABLDFINALARGSSTORAGE, buildArgumentsInfo.abldFinalArgs);
		}

		if (buildArgumentsInfo.abldCleanArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.ABLDCLEANARGSSTORAGE, buildArgumentsInfo.abldCleanArgs);
		}

		if (buildArgumentsInfo.abldFreezeArgs.trim().length() > 0) {
			rootStorage.setAttribute(BuildArgumentsInfo.ABLDFREEZEARGSSTORAGE, buildArgumentsInfo.abldFreezeArgs);
		}
	}

	@Override
	public void saveConfigurationSettings(ICStorageElement se, ISymbianBuildContext context) {
		saveBuildArgsToStorage(se.createChild(ARGUMENTS_DATA_ID)); 
	}

	@Override
	/**
	 * Get the unique ID for this build configuration.
	 * For ABLD it is the display name, for SBSv2, it is the builder ID
	 * @return
	 */
	public String getConfigurationID() {
		return getDisplayString();
	}

}
