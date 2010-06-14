package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.internal.sdk.core.model.SBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianSDK;
import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.IBSFPlatform;
import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;
import com.nokia.carbide.cpp.sdk.core.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Check;

public class BuildContextSBSv2 implements ISBSv2BuildContext {
	
	private String platform;
	private String target;
	private String sbsv2Alias;
	private ISymbianSDK sdk;
	private String displayString;
	private String configID;  // cconfiguration 'id' attribute from .cproject
	
	public BuildContextSBSv2(ISymbianSDK theSDK, String thePlatform, String theTarget, String theSBSv2Alias, String displayName, String configID) {
		this.sdk = theSDK;
		this.platform = thePlatform.toUpperCase();
		this.target = theTarget.toUpperCase();
		this.sbsv2Alias = theSBSv2Alias;
		this.displayString = displayName;
		this.configID = configID;
	}

	@Override
	public ISymbianSDK getSDK() {
		return sdk;
	}

	@Override
	public String getPlatformString() {
		return platform;
	}

	@Override
	public String getTargetString() {
		return target;
	}

	public String getConfigID(){
		return configID;
	}
	
	@Override
	public String getDisplayString() {
		Check.checkState(displayString != null);
		return displayString;
	}

	private ISBSv2BuildInfo getBuildInfo() {
		ISBSv2BuildInfo buildInfo = (ISBSv2BuildInfo)getSDK().getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
		if (buildInfo == null) {
			buildInfo = new SBSv2BuildInfo(getSDK());
			((SymbianSDK)getSDK()).setBuildInfo(buildInfo, ISymbianBuilderID.SBSV2_BUILDER);
		}
		return buildInfo;
	}

	@Override
	public String toString() {
		return getConfigID();
	}

	@Override
	public String getDefaultDefFileDirectoryName() {
		// TOOD: THIS IS ABLD STUFF. isASSP does not belong with Raptor
		// TODO: How the ASSP option affects the path?

		String dirName = getDefFileDirectoryNameForPlatform(platform);
		// TODO: Previously BSF's folder was EABI? What is it for SBSv2 variants?
		
		if (dirName == null) {
			// fallback for unknown cases
			dirName = platform;
		}
		
		return dirName;
	}

	private String getDefFileDirectoryNameForPlatform(String platform) {
		// TODO: This is still ABLD stype stuff
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
	
	@Override
	public IPath getCompilerPrefixFile() {
		// TODO: This is ABLD hard-code mechanism. Should be able to get from Raptor query mechanism
		if (platform.equals(GCCE_PLATFORM)) {
			return getGCCEPrefixFilePath();
		} else if (platform.equals(ARMV5_PLATFORM)
					|| platform.equals(ARMV5_ABIV2_PLATFORM)
					|| platform.equals(ARMV6_PLATFORM)
					|| platform.equals(ARMV6_ABIV2_PLATFORM)) {
			return getRVCTPrefixFilePath();
		} 

		// fallback for WINSCW, MSVC, etc.
		return null;
	}
	
	private IPath getIncludePath() {
		return getSDK().getIncludePath();
	}

	private IPath getGCCEPrefixFilePath() {
		// TOOD: Should get from Raptor query when available
		return getIncludePath().append("gcce/gcce.h"); //$NON-NLS-1$
	}

	private IPath getRVCTPrefixFilePath() {
		// TODO: Should get this from query mechanism
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
		// TODO: This should not be needed when raptor query is complete
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
	
	@Override
	public List<IDefine> getVariantHRHDefines() {
		// TODO: Should get from raptor query
		return getCachedData().getVariantHRHDefines();
	}

	@Override
	public List<File> getPrefixFileIncludes() {
		// TODO: Should get from raptor query
		return getCachedData().getPrefixFileIncludes();
	}

	@Override
	public List<IDefine> getCompilerMacros() {
		// TODO: Should get from raptor query
		// we parse the compiler prefix file to gather macros.  this can be time consuming so do it
		// once and cache the values.  only reset the cache when the compiler prefix has changed.
		
		IPath prefixFile = getCompilerPrefixFile();
		if (prefixFile == null) {
			return Collections.emptyList();
		}
		
		return getCachedData().getCompilerMacros(prefixFile);
	}

	@Override
	public String getBuildVariationName() {
		// TODO: This should not be needed for Raptor
		return "";
	}

	@Override
	public boolean isSymbianBinaryVariation() {
		// This should not be needed for Raptor. We do need a check
		// in the MPP for the featurevariant keyword perhaps
		return false;
	}
	
	/**
	 * See if the build configuration is an SBSv2 alias, and if so get the build-able alias name 
	 * @param displayName
	 * @return The full SBSv2 alias that can be used with -c, otherwise null if not SBSv2
	 */
	private static String getSBSv2AliasFromConfigName(String displayName) {
		int indexBegin = displayName.indexOf("(");  //$NON-NLS-1$
		int indexEnd = displayName.indexOf(")");  //$NON-NLS-1$
		if (indexBegin > 0 && indexEnd > 0){
			String configPart =  displayName.substring(indexBegin+1, indexEnd);
			if (configPart.split("_").length > 1){
				return configPart;
			}
		} 
		
		return null;
	}

	@Override
	public String getSBSv2Alias() {
		return sbsv2Alias;
	}

	/**
	 * Get the cache holding the data that applies to this build context globally.
	 * A build context is subclassed by CarbideBuildConfiguration, which has multiple
	 * instances at runtime, thus, a SymbianBuildContext instance should not hold a cache itself.
	 * @return cache, never <code>null</code>
	 */
	private SymbianBuildContextDataCache getCachedData() {
		// TODO: Still need to consider this for SBSv2 refactoring / Raptor query
		return SymbianBuildContextDataCache.getCache(this);
	}
	
	/**
	 * Get the list of #include paths detected for this context.
	 * @return List or <code>null</code>
	 */
	public List<File> getCachedSystemIncludePaths() {
		// TODO: Still need to consider this for SBSv2 refactoring / Raptor query
		return getCachedData().getSystemIncludePaths();
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
		if (!(obj instanceof BuildContextSBSv2))
			return false;
		final BuildContextSBSv2 other = (BuildContextSBSv2) obj;
		if (sbsv2Alias == null) {
			if (other.sbsv2Alias != null)
				return false;
		} else if (!sbsv2Alias.equals(other.sbsv2Alias))
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
		} else if (!configID.equals(other.configID)){
			// TODO: Do we really need anything other than a config ID comparison?
			return false;
		}
		return true;
	}

	public static String getPlatformFromV1ConfigName(String displayString) {
		String[] tokens = displayString.split(" ");
		String sdkIdToken = tokens[2];
		if (sdkIdToken.contains("_")){
			sdkIdToken = sdkIdToken.substring(1, sdkIdToken.length()-1);
			String[] aliasTokens = sdkIdToken.split("_");
			return aliasTokens[0];
		} else {
			return sdkIdToken.substring(1, sdkIdToken.length()-1);
		}
	}

	public static String getTargetFromV1ConfigName(String displayString) {
		String[] tokens = displayString.split(" ");
		if (tokens[1].compareTo("Debug") == 0) {
			return ISymbianBuildContext.DEBUG_TARGET;
		} else {
			return ISymbianBuildContext.RELEASE_TARGET;
		}
	}

	public static String getBuildAliasFromV1ConfigName(String displayString) {
		String target = getTargetFromV1ConfigName(displayString);
		String platform = getPlatformFromV1ConfigName(displayString);
		return platform.toLowerCase() + "_" + target.toLowerCase();
	}
	
	// Fall-back to get SDK id from old config name
	public static String getSDKIDFromV1ConfigName(String configName) {
		int indexBegin = configName.indexOf("[");  //$NON-NLS-1$
		int indexEnd = configName.indexOf("]");  //$NON-NLS-1$
		if (indexBegin > 0 && indexEnd > 0){
			return configName.substring(indexBegin+1, indexEnd);
		} else {
			return ""; //$NON-NLS-1$
		}
	}



}
