package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2ConfigQueryData;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2MinimumVersionException;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
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
	
	private boolean reportedSBSVersionError;
	
	// Raptor config query data
	private ISBSv2ConfigQueryData configQueryData;
	
	// cconfiguration data store
	private SBSv2BuilderInfo sbsv2BuildInfo;
	
	private IPath cachedVariantHRHFile = null;
	
	public BuildContextSBSv2(ISymbianSDK sdk, String platform, String target, String alias, String displayString, String configID) {
		this.sdk = sdk;
		if (platform == null){
			this.platform = "unknown_platform";
		} else {
			this.platform = platform.toUpperCase();
		}
		
		if (target == null){
			this.target = "unknown_target";
		} else {
			this.target = target.toUpperCase();
		}
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

	public ISymbianSDK getSDK() {
		return sdk;
	}

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

	public String getTargetString() {
		if (target == null){
			return configQueryData.getConfigurationErrorMessage();
		}
		return target;
	}

	public String getConfigID(){
		return configID;
	}
	
	public String getDisplayString() {
		Check.checkState(displayString != null);
		return displayString;
	}

	@Override
	public String toString() {
		return getConfigID();
	}

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
	
	public IPath getCompilerPrefixFile() {
		
		if (sbsv2Alias.toUpperCase().contains(TOOLCHAIN_GCCE) ||
			sbsv2Alias.toUpperCase().contains(TOOLCHAIN_ARM)) {
			if (configQueryData != null) {
				if (configQueryData.getBuildPrefix() != null && !(new File(configQueryData.getBuildPrefix()).exists())){
					this.configQueryData = setConfigQueryData(sdk, getSBSv2Alias());
				}
				return new Path(configQueryData.getBuildPrefix());
			}
		} 

		// fallback for WINSCW, MSVC, etc.
		return null;
	}
	
	public List<IDefine> getVariantHRHDefines() {
		return getCachedData().getVariantHRHDefines();
	}

	public List<File> getVariantHRHIncludes() {
		return getCachedData().getPrefixFileIncludes();
	}

	public List<IDefine> getCompilerPreincludeDefines() {
		IPath prefixFile = getCompilerPrefixFile();
		if (prefixFile == null) {
			return getCachedData().getCompilerMacros(null);
		}
		
		return getCachedData().getCompilerMacros(prefixFile);
	}

	public String getBuildVariationName() {
		// Not needed for Raptor
		return "";
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((configID == null) ? 0 : configID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildContextSBSv2 other = (BuildContextSBSv2) obj;
		if (configID == null) {
			if (other.configID != null)
				return false;
		} else if (!configID.equals(other.configID))
			return false;
		return true;
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
			reportedSBSVersionError = true; // only report once per IDE session.
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				public void run() {
					if (!reportedSBSVersionError){
						MessageDialog.openError(WorkbenchUtils.getSafeShell(), "Minimum sbs version not met.", e.getMessage());
					}
				}
			});	
			Logging.log(SDKCorePlugin.getDefault(), Logging.newSimpleStatus(0, IStatus.ERROR,
						MessageFormat.format(e.getMessage(), ""), e));
		}
		
		return configQueryData;
	}

	public ISBSv2ConfigQueryData getConfigQueryData() {
		return configQueryData;
	}

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

	public List<IPath> getSystemIncludes() {
		if (configQueryData != null) {
			List<String> includes = configQueryData.getMetaDataIncludes();
			if (includes != null && !includes.isEmpty()) {
				List<IPath> includePaths = new ArrayList<IPath>();
				for (Iterator<String> itr = includes.iterator(); itr.hasNext();) {
					String include = itr.next();
					if (include.length() == 0 || include.equals(".")) {
						continue;
					}
					Path includePath = new Path(include);
					if (!includePaths.contains(includePath)) {
						includePaths.add(includePath);
					}
				}
				return includePaths;
			}
		}
		return null;
	}

	public List<String> getSupportedTargettypes() {
		return configQueryData.getTargettypes();
	}


	public void loadConfigurationSettings(ICStorageElement se) {
		if (sbsv2BuildInfo == null){
			sbsv2BuildInfo = new SBSv2BuilderInfo();
		}
		
		sbsv2BuildInfo.loadFromStorage(se);
	}

	public void saveConfigurationSettings(ICStorageElement se, ISymbianBuildContext context) {
		sbsv2BuildInfo = new SBSv2BuilderInfo((ISBSv2BuildContext)context);
		sbsv2BuildInfo.saveToStorage(se.createChild(SBSV2_DATA_ID)); 
	}

	/**
	 * Get the unique ID for this build configuration.
	 * For ABLD it is the display name, for SBSv2, it is the builder ID (cconfiguration 'id')
	 * @return
	 */
	public String getConfigurationID() {
		return getConfigID();
	}
	
	/**
	 * Get the full path to the prefix file defined under \epoc32\tools\variant\variant.cfg
	 * @return A path object, or null if the variant.cfg does not exist. This routine does not check to see if the returned path exists.
	 */
	public IPath getPrefixFromVariantCfg(){
		ISBSv2BuildInfo sbsv2BldInfo = ((ISBSv2BuildInfo)getSDK().getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER));
		if (sbsv2BldInfo.getPrefixFromVariantCfg()== null || sbsv2BldInfo.getPrefixFromVariantCfg().toOSString().length() == 0){
			return null;
		}
		return sbsv2BldInfo.getPrefixFromVariantCfg();
	}

	public List<IDefine> getBuildMacros() {
		ISBSv2BuildInfo sbsv2BldInfo = ((ISBSv2BuildInfo)getSDK().getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER));
		
		Map<String, String> buildMacroMap = sbsv2BldInfo.getBuildMacros(getSBSv2Alias());
		List<IDefine> defines = new ArrayList<IDefine>();
		for (String macroName : buildMacroMap.keySet()){
			defines.add(DefineFactory.createDefine(macroName, buildMacroMap.get(macroName)));
		}

		return defines;
	}

	public List<IDefine> getMetadataMacros() {
		ISBSv2BuildInfo sbsv2BldInfo = ((ISBSv2BuildInfo)getSDK().getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER));
		Map<String, String> platMacroMap = sbsv2BldInfo.getMetadataMacros(getSBSv2Alias());
		List<IDefine> defines = new ArrayList<IDefine>();
		for (String macroName : platMacroMap.keySet()){
			defines.add(DefineFactory.createDefine(macroName, platMacroMap.get(macroName)));
		}
		
		return defines;
	}

	public IDefine getTargetTypeMacro(String targettype) {
		ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);
		return DefineFactory.createDefine(sbsv2BuildInfo.getTargetTypeMacro(targettype));
	}


	
}
