package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2ConfigQueryData;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2MinimumVersionException;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.sdk.core.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISBSv2ConfigQueryData;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

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
		this.configQueryData = setConfigQueryData(sdk, alias);
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
		
		if (sbsv2Alias.toUpperCase().contains(TOOLCHAIN_GCCE) ||
			sbsv2Alias.toUpperCase().contains(TOOLCHAIN_ARM)) {
			if (configQueryData != null) {
				return new Path(configQueryData.getBuildPrefix());
			}
		} 

		// fallback for WINSCW, MSVC, etc.
		return null;
	}
	
	@Override
	public List<IDefine> getVariantHRHDefines() {
		return getCachedData().getVariantHRHDefines();
	}

	@Override
	public List<File> getPrefixFileIncludes() {
		return getCachedData().getPrefixFileIncludes();
	}

	@Override
	public List<IDefine> getCompilerMacros() {
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
		return SymbianBuildContextDataCache.getCache(this);
	}
	
	/**
	 * Get the list of #include paths detected for this context.
	 * @return List or <code>null</code>
	 */
	public List<File> getCachedSystemIncludePaths() {
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
		if (!configID.equalsIgnoreCase(other.configID)){
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
		String pathString = configQueryData.getOutputPathString();
		if (pathString == null || pathString.length() == 0) {
			platform = "";
			target = "";
			return;
		}
		
		IPath releasePath = new Path(configQueryData.getOutputPathString());
		int epoc32SegmentIndex = 0;
		for (String segment : releasePath.segments()){
			if (segment.toLowerCase().equals("epoc32"))
				break;
			epoc32SegmentIndex++;
		}
		// assuming /epoc32/<release>/<target>/
		platform = releasePath.segment(epoc32SegmentIndex+2).toUpperCase();
		target = releasePath.segment(epoc32SegmentIndex+3).toUpperCase();
	}

	private ISBSv2ConfigQueryData setConfigQueryData(ISymbianSDK sdk, String alias) {
		SBSv2ConfigQueryData configQueryData = null;
		try {
			configQueryData = SBSv2QueryUtils.getConfigQueryDataForSDK(sdk, alias);
			if (configQueryData == null) {
				Map<String, String> aliasToMeaningMap = SBSv2QueryUtils.getAliasesForSDK(sdk);
				List<String> aliasList = new ArrayList<String>();
				aliasList.add(alias);
				String configQueryXML = SBSv2QueryUtils.getConfigQueryXMLforSDK(sdk, aliasList);
				if (aliasToMeaningMap.get(alias) != null){
					configQueryData = new SBSv2ConfigQueryData(alias, aliasToMeaningMap.get(alias), configQueryXML);
				}
			}
		} catch (final SBSv2MinimumVersionException e) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					MessageDialog.openError(WorkbenchUtils.getSafeShell(), "Minimum sbs version not met.", e.getMessage());
				}
			});	
			Logging.log(SDKCorePlugin.getDefault(), Logging.newSimpleStatus(0, IStatus.ERROR,
						MessageFormat.format(e.getMessage(), ""), e));
		}
		
		return configQueryData;
	}

	@Override
	public ISBSv2ConfigQueryData getConfigQueryData() {
		return configQueryData;
	}

	@Override
	public String getToolChain() {
		
		if (configQueryData != null) {
			Map<String, String> buildMacros = configQueryData.getBuildMacros();
			// try to figure out the tool chain using macros from Raptor config query
			if (buildMacros.containsKey(MACRO_ARM)) {
				return TOOLCHAIN_ARM;
			} else if (buildMacros.containsKey(MACRO_GCCE)) {
				return TOOLCHAIN_GCCE;
			} else if (buildMacros.containsKey(MACRO_WINSCW)) {
				return TOOLCHAIN_WINSCW;
			}
		} else {
			// if no macros available, use alias name instead
			if (sbsv2Alias.toUpperCase().contains(TOOLCHAIN_ARM)) {
				return TOOLCHAIN_ARM;
			} else if (sbsv2Alias.toUpperCase().contains(TOOLCHAIN_GCCE)) {
				return TOOLCHAIN_GCCE;
			} else if (sbsv2Alias.toUpperCase().contains(TOOLCHAIN_WINSCW)) {
				return TOOLCHAIN_WINSCW;
			}
		}
		return TOOLCHAIN_UNKNOWN;
	}
}
