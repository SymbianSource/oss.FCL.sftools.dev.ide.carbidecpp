package com.nokia.carbide.cpp.internal.api.sdk;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Version;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.internal.sdk.core.model.SBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianSDK;
import com.nokia.carbide.cpp.sdk.core.IRVCTToolChainInfo;
import com.nokia.carbide.cpp.sdk.core.ISBSv2BuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class BuildContextSBSv2 implements ISBSv2BuildContext {
	
	private String platform;
	private String target;
	private String sbsv2Alias;
	private String meaning;
	private ISymbianSDK sdk;
	private String displayString;
	private String configID;  // cconfiguration 'id' attribute from .cproject
	
	// Raptor config query data
	private String outputPathString;
	private List<String> metaDataMacros = new ArrayList<String>();  // macros to parse the INF/MMPs files (these do not contain values)
	private List<String> metaDataIncludes = new ArrayList<String>();
	private String metaDataVariantHRH;
	private String configParseErrorMessage = null;
	
	public BuildContextSBSv2(ISymbianSDK theSDK, String thePlatform, String theTarget, String theSBSv2Alias, String displayName, String configID) {
		this.sdk = theSDK;
		this.platform = thePlatform.toUpperCase();
		this.target = theTarget.toUpperCase();
		this.sbsv2Alias = theSBSv2Alias;
		this.displayString = displayName;
		this.configID = configID;
	}

	public BuildContextSBSv2(ISymbianSDK sdk, String alias, String meaning, String contextQueryXML) {
		this.sdk = sdk;
		this.sbsv2Alias = alias;
		this.meaning = meaning;
		this.configID = ISBSv2BuildContext.BUILDER_ID + "." + sbsv2Alias + "." + sdk.getUniqueId();
		parseQueryConfigResults(contextQueryXML);
		this.displayString = sbsv2Alias + " [" + sdk.getUniqueId() + "]"; 
	}

	@Override
	public ISymbianSDK getSDK() {
		return sdk;
	}

	@Override
	public String getPlatformString() {
		
		if (platform == null){
			return configParseErrorMessage;
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
			return configParseErrorMessage;
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
	
	
	private void parseQueryConfigResults(String queryResult) {
		
		try {
    		Element root = null;
    		DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    		parser.setErrorHandler(new DefaultHandler());
    		
    		StringReader reader = new StringReader( queryResult );
    		InputSource inputSource = new InputSource( reader );
    		root = parser.parse(inputSource).getDocumentElement();
    		
    		NodeList children = root.getChildNodes();
    		for (int i=0; i< children.getLength(); i++) {
    			Node configNode = children.item(i);
    			if (configNode.getNodeName().equals("config")){
    				NamedNodeMap aliasAttribs = configNode.getAttributes();
    				String dottedName = aliasAttribs.getNamedItem("meaning").getNodeValue();
    				if (!dottedName.equalsIgnoreCase(meaning)){
    					continue;
    				}
    				if (configNode.getTextContent() != null&& configNode.getTextContent().length() > 0){
    					// The config failed, likely due to envrionment set up issue.
    					// Save the error message
    					configParseErrorMessage = configNode.getTextContent();
    					break;
    				}
    				
    				String outputpath = aliasAttribs.getNamedItem("outputpath").getNodeValue();
    				if (outputpath != null){
    					outputPathString = outputpath;
    				}
    				
    				// get <metadata>
    				NodeList configChillens = configNode.getChildNodes();
    				for (int ii = 0; ii < configChillens.getLength(); ii++){
    					Node metaDataNode = configChillens.item(ii);
    					if (metaDataNode.getNodeName().equals("metadata")){
    						NodeList metaDataChillens = metaDataNode.getChildNodes();
    						for (int iii = 0; iii < metaDataChillens.getLength(); iii++){
    							Node metaChild = metaDataChillens.item(iii);
    							NamedNodeMap attribs = metaChild.getAttributes();
    							try {
									if (metaChild.getNodeName().equals("macro")){
										String name = attribs.getNamedItem("name").getNodeValue();
										metaDataMacros.add(name);
									} else if (metaChild.getNodeName().equals("include")){
										String path = attribs.getNamedItem("path").getNodeValue();
										metaDataIncludes.add(path);
									} else if (metaChild.getNodeName().equals("preinclude")){
										metaDataVariantHRH = attribs.getNamedItem("file").getNodeValue();
									}
								} catch (Exception e) {
									// skip it
									e.printStackTrace();
								}
    						}
    						
    					} else if (metaDataNode.getNodeName().equals("compilerinfo")){
    						// TODO: Placeholder for future cpp preprocessor macros and compiler prefix
    						// Not sure what it will be called yet.
    					}
    				}
    				
    				break;
    			}
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		Logging.log(SDKCorePlugin.getDefault(), Logging.newStatus(SDKCorePlugin.getDefault(), e));
    	}
    	
    	setPlatformAndTargetFromOutputPath();
		
	}

	private void setPlatformAndTargetFromOutputPath() {
		if (outputPathString == null) return;
		
		IPath releasePath = new Path(outputPathString);
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

	/**
	 * Error message, if any.
	 * @return An error message if a problem occurred while trying to get config info from Raptor. Null if no error.
	 */
	@Override
	public String getConfigurationErrorMessage(){
		return configParseErrorMessage;
	}

}
