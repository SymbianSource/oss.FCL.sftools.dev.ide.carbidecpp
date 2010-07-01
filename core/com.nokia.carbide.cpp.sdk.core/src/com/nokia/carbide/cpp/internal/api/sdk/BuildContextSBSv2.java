package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;
import com.nokia.carbide.cpp.sdk.core.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISBSv2ConfigQueryData;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
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
	
	// Raptor config query data
	private ISBSv2ConfigQueryData configQueryData;
	
	public BuildContextSBSv2(ISymbianSDK sdk, String platform, String target, String alias, String displayString, String configID) {
		this.sdk = sdk;
		this.platform = platform.toUpperCase();
		this.target = target.toUpperCase();
		this.sbsv2Alias = alias;
		this.displayString = displayString;
		this.configID = configID;
	}

	public BuildContextSBSv2(ISymbianSDK sdk, String alias, ISBSv2ConfigQueryData configData) {
		this.sdk = sdk;
		this.sbsv2Alias = alias;
		this.configQueryData = configData;
		setPlatformAndTargetFromOutputPath();
		this.configID = ISBSv2BuildContext.BUILDER_ID + "." + sbsv2Alias + "." + sdk.getUniqueId();
		this.displayString = sbsv2Alias + " [" + sdk.getUniqueId() + "]"; 
	}

	@Override
	public ISymbianSDK getSDK() {
		return sdk;
	}

	@Override
	public String getPlatformString() {
		
		if (platform == null){
			return configQueryData.getConfigurationErrorMessage();
		}
		
		if (platform.contains(".")){
			String[] tok = platform.split(".");
			if (tok.length > 0) return tok[0];
		}
		return platform;
	}
	
	public String getPlatformReleaseDirName() {
		return platform;
	}

	@Override
	public String getTargetString() {
		if (target == null){
			return configQueryData.getConfigurationErrorMessage();
		}
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

	@Override
	public String toString() {
		return getConfigID();
	}

	@Override
	public String getDefaultDefFileDirectoryName() {
	
		String dirName = getDefFileDirectoryNameForPlatform(platform);
		
		if (dirName == null) {
			// fallback for unknown cases
			dirName = platform;
		}
		
		return dirName;
	}

	private String getDefFileDirectoryNameForPlatform(String platform) {
		if (sbsv2Alias.toUpperCase().contains("WINSCW")) {
			return "BWINS"; //$NON-NLS-1$
		} else if (sbsv2Alias.toUpperCase().contains("ARM")) {
			return "EABI"; //$NON-NLS-1$
		}
		return null;
	}
	
	@Override
	public IPath getCompilerPrefixFile() {
		
		if (sbsv2Alias.toUpperCase().contains(TOOLCHAIN_GCCE)) {
			return getGCCEPrefixFilePath();
		} else if (sbsv2Alias.toUpperCase().contains((TOOLCHAIN_ARM))) {
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
		} else if (!sbsv2Alias.equalsIgnoreCase(other.sbsv2Alias))
			return false;
		if (getSDK() == null) {
			if (other.getSDK() != null)
				return false;
		} else if (!getSDK().getEPOCROOT().equalsIgnoreCase(other.getSDK().getEPOCROOT()))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equalsIgnoreCase(other.target)) {
			return false;
		} else if (!configID.equalsIgnoreCase(other.configID)){
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
	
	private void setPlatformAndTargetFromOutputPath() {
		if (configQueryData.getOutputPathString() == null) return;
		
		IPath releasePath = new Path(configQueryData.getOutputPathString());
		int epoc32SegmentIndex = 0;
		for (String segment : releasePath.segments()){
			if (segment.toLowerCase().equals("epoc32"))
				break;
			epoc32SegmentIndex++;
		}
		// assuming /epoc32/<release>/<target>/
		platform = releasePath.segment(epoc32SegmentIndex+2);
		target = releasePath.segment(epoc32SegmentIndex+3);
	}

	public ISBSv2ConfigQueryData getConfigQueryData() {
		return configQueryData;
	}

}
