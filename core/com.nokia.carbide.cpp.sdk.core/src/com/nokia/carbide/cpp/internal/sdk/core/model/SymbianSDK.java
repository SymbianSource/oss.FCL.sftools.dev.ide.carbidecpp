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
package com.nokia.carbide.cpp.internal.sdk.core.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Version;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISymbianSDKModifier;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.sun.org.apache.xpath.internal.XPathAPI;

public class SymbianSDK implements ISymbianSDK, ISymbianSDKModifier {

	public static final String MANIFEST_XML_LOCATION = "/epoc32/kit/manifest.xml"; //$NON-NLS-1$
	
	// manifest.xml attributes
	private static final String PATH_ID_INCLUDEDIR = "includeDir"; //$NON-NLS-1$
	private static final String PATH_ID_TOOLSDIR = "toolsDir"; //$NON-NLS-1$
	private static final String PATH_ID_SRCDIR = "srcDir"; //$NON-NLS-1$
	
	private static final String RELEASE = "release"; //$NON-NLS-1$
	public static final String VARIANT_CFG_FILE = "epoc32/tools/variant/variant.cfg"; //$NON-NLS-1$
	public static final String SPP_VARIANT_CFG_FILE = "epoc32/tools/variant/spp_variant.cfg"; //$NON-NLS-1$
	private static final String TARGETTYPE_PM_FILE = "epoc32/tools/trgtype.pm"; //$NON-NLS-1$
	private static final String BUILD_INFO_TXT_FILE = "epoc32/data/buildinfo.txt"; //$NON-NLS-1$
	private static final String BUILD_INFO_KEYWORD = "ManufacturerSoftwareBuild";
	
	private static final String WINSCW_UREL_DIR = "epoc32/release/winscw/urel";
	private static final String WINSCW_UDEB_DIR = "epoc32/release/winscw/udeb";
	private static final String ARMV5_UDEB_DIR = "epoc32/release/armv5/udeb";
	
	protected DeviceType deviceEntry = null;
	private boolean enabled = true;
	private Version osVersion;
	private List<String> supportedTargetTypesList = new ArrayList<String>();
	private Map<String, ISDKBuildInfo> buildInfoMap = new HashMap<String, ISDKBuildInfo>();
	private Map<String, File> prefixFileMap = new HashMap<String, File>();
	@SuppressWarnings("rawtypes")
	private Set<Object> sdkFeatures = new HashSet<Object>();

	// greedy match means the filename is in the last group
	public static Pattern VARIANT_HRH_LINE_PATTERN = Pattern.compile("(?i)(.*)(/|\\\\)(.*hrh)");

	public SymbianSDK(DeviceType device) {
		deviceEntry = device;
		setBuildInfo(new SBSv1BuildInfo(this), ISymbianBuilderID.SBSV1_BUILDER);
		setBuildInfo(new SBSv2BuildInfo(this), ISymbianBuilderID.SBSV2_BUILDER);
		scanSDK();
	}

	@SuppressWarnings("unchecked")
	public void addSupportedFeature(Object feature) {
		sdkFeatures.add(feature);
	}

	public ISDKBuildInfo getBuildInfo(String builderId) {
		ISDKBuildInfo buildInfo = buildInfoMap.get(builderId);
		return buildInfo;
	}

	public String getEPOCROOT() {
		if (deviceEntry != null) {
			String epocRoot = deviceEntry.getEpocroot();
			
			if (epocRoot.length() > 2 && epocRoot.substring(1, 2).equals(":")){
				// make sure it's a full windows path...
				File resolvedEPOCROOT = new File(epocRoot);
				try {
					// canonicalize the path so the case is correct
					// for URI lookups (e.g. for prebuilt indexes)
					resolvedEPOCROOT = resolvedEPOCROOT.getCanonicalFile();
					epocRoot = resolvedEPOCROOT.toString();
				} catch (IOException e) {
					// ignored...
				}
			}
			
			int len = epocRoot.length();
			if (len > 0 && epocRoot.charAt(len-1) != '\\' && epocRoot.charAt(len-1) != '/'){
				epocRoot += File.separator;
			}
			
			return epocRoot;
		}
		return "";
	}

	public IPath getIncludePath() {
		String epocRoot = getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32IncPath = new Path(epocRoot).append("epoc32/include");
			// try to canonicalize it so it matches actual file system case
			try {
				epoc32IncPath = new Path(epoc32IncPath.toFile().getCanonicalPath());
			} catch (IOException e) {
			}
			return epoc32IncPath;
		}
		return null;
	}

	public Version getOSVersion() {		
		if (osVersion == null){
			return new Version("0.0");
		}
		return osVersion;
	}

	public File getPrefixFile(String builderId) {
		File prefixFile = prefixFileMap.get(builderId);
		return prefixFile;
	}

	public IPath getReleaseRoot() {
		String epocRoot = getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32RelPath = new Path(epocRoot).append("epoc32/release");
			// try to canonicalize it so it matches actual file system case
			try {
				epoc32RelPath = new Path(epoc32RelPath.toFile().getCanonicalPath());
			} catch (IOException e) {
			}
			return epoc32RelPath;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Set getSupportedFeatures() {
		return sdkFeatures;
	}

	public List<String> getSupportedTargetTypes() {
		
		synchronized (supportedTargetTypesList) {
			if (supportedTargetTypesList.size() > 0){
				return supportedTargetTypesList;
			}
			
			File epocRoot = new File(getEPOCROOT());
			File targetTypePM = new File(epocRoot, TARGETTYPE_PM_FILE);
			if (!targetTypePM.exists())
				return supportedTargetTypesList;
			
			// greedy match means the filename is in the last group
			try {
				char[] cbuf = new char[(int) targetTypePM.length()];
				Reader reader = new FileReader(targetTypePM);
				reader.read(cbuf);
				reader.close();
				String[] lines = new String(cbuf).split("\r|\r\n|\n");
				for (int i = 0; i < lines.length; i++) {
					// skip comments and blank lines
					String line = removeComments(lines[i]);
					if (line.matches("\\s*#.*") || line.trim().length() == 0) 
						continue;
					
					// parse current line... the slitting could be done better with more efficent reg exp....
					line = line.trim();
					line = line.replaceAll(" ", "");
					if (line.endsWith("=>{")){
						String[] lineSplit = line.split("=>");
						if (lineSplit.length == 2 && Character.isLetter(lineSplit[0].charAt(0))){
							supportedTargetTypesList.add(lineSplit[0]);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return supportedTargetTypesList;
	}

	public IPath getToolsPath() {
		String epocRoot = getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32ToolsPath = new Path(epocRoot).append("epoc32/tools");
			// try to canonicalize it so it matches actual file system case
			try {
				epoc32ToolsPath = new Path(epoc32ToolsPath.toFile().getCanonicalPath());
			} catch (IOException e) {
			}
			return epoc32ToolsPath;
		}
		return null;
	}

	public String getUniqueId() {
		if (deviceEntry != null) {
			return deviceEntry.getId();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public List<String> getVariantCFGMacros(){
		
		List<String> variantCFGMacros = new ArrayList<String>();
		File epocRoot = new File(getEPOCROOT());
		File variantCfg;
		variantCfg = new File(epocRoot, SPP_VARIANT_CFG_FILE);
		if (!variantCfg.exists()) {
			variantCfg = new File(epocRoot, VARIANT_CFG_FILE);
			if (!variantCfg.exists())
				return Collections.EMPTY_LIST;
		}
		
		try {
			char[] cbuf = new char[(int) variantCfg.length()];
			Reader reader = new FileReader(variantCfg);
			reader.read(cbuf);
			reader.close();
			String[] lines = new String(cbuf).split("\r\n|\r|\n");
			for (int i = 0; i < lines.length; i++) {
				// skip comments and blank lines
				String line = removeComments(lines[i]);
				if (line.matches("\\s*#.*") || line.trim().length() == 0) 
					continue;
				
				// parse the variant line, which is an EPOCROOT-relative
				// path to a bldvariant.hrh file
				Matcher matcher = VARIANT_HRH_LINE_PATTERN.matcher(line);
				if (matcher.matches()) {
					continue; // Skip this it's the file
				} else {
					// all other patterns are assumed to be macro
					variantCFGMacros.add(line.trim());
				}
			}
		} catch (IOException e) {
		}
		
		return variantCFGMacros;
	}

	public boolean isEnabled() {
		if (!SDKCorePlugin.SUPPORTS_SBSV1_BUILDER && 
			(getOSVersion().getMajor() < 9 ||
			(getOSVersion().getMajor() == 9 && getOSVersion().getMinor() <= 4))){
			return false;
		}
		return enabled;
	}

	/**
	 * Remove single line C-style comments, multi-line C++-style comments and blank lines
	 */
	public static String removeComments(String aIntermediateContent) {
		// Strip out comments. There are uncovered cases here where this regex approach fails
		// when combining cpp and c style comments.
		String lNewline = "(\\r\\n|\\r|\\n)";
		
		// Note: we used to use the regex below for cpp comments (from  http://ostermiller.org/findcomment.html )
		//    String lCppStyleComments = "/\\*(?:.|[\\n\\r])*?\\*/"; 
		// but it throws StackOverFlowExceptions for large inputs
		//    http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5050507
		// suggests that regex OR is the cause - therefore use dotall mode: (?s) instead
		
		String lCStyleComments = "(?m)//(.*)$";
		String lCppStyleComments = "(?s)/\\*.*?\\*/"; 
		String lBlankLines = "(?m)(^(\\s)*$"+lNewline+")+";
		aIntermediateContent = aIntermediateContent.replaceAll(lCStyleComments, "");
		aIntermediateContent = aIntermediateContent.replaceAll(lCppStyleComments, "");
		aIntermediateContent = aIntermediateContent.replaceAll(lBlankLines,"");
		return aIntermediateContent;
	}

	public void scanSDK(){
		ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
		ISBSv2BuildInfo sbsv2BuildInfo = (ISBSv2BuildInfo)getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER);

		sbsv1BuildInfo.clearPlatformMacros();

		if (!setDataFromManifestXML()){
			//need to scan SDK files for OS and SDK version
			scanSDKForVersionInfo();
		}
		
		setPrefixFile(sbsv1BuildInfo.getPrefixFromVariantCfg(), ISymbianBuilderID.SBSV1_BUILDER);
		setPrefixFile(sbsv2BuildInfo.getPrefixFromVariantCfg(), ISymbianBuilderID.SBSV2_BUILDER);
		
		setSupportFeatures();
	}

	public void setBuildInfo(ISDKBuildInfo buildInfo, String builderId) {
		buildInfoMap.put(builderId, buildInfo);
	}

	public void setEnabled(boolean enable) {
		enabled = enable;
	}

	public void setEPOCROOT(String epocRoot) {
		deviceEntry.setEpocroot(epocRoot);	
	}

	public void setName(String name) {
		if (deviceEntry != null) {
			deviceEntry.setName(name);
		}
	}

	public void setOSVersion(Version osVer) {
		this.osVersion = osVer;
	}
	
	public void setPrefixFile(IPath prefixFile, String builderId) {
		if (prefixFile == null)
			return;
		File file = new File(prefixFile.toOSString());
		prefixFileMap.put(builderId, file);
	}

	public void setUniqueId(String id) {
		deviceEntry.setId(id);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getUniqueId();
	}
	
	private boolean hasManifestXML(){
		File manifestXML = new File(deviceEntry.getEpocroot(), MANIFEST_XML_LOCATION);
		if (manifestXML.exists()){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Parses the epoc32\kit\manifest.xml file and add the relavant SDK information 
	 * Currently this is just a bunch of hackish code to parse the simplest manifest.xml file that really does nothing useful at all
	 * For a manifest to be properly mapped you need at minimum: epocRoot, id, and label filled out.
	 */
	
	private boolean processManifest(File manifestFile){
		
		boolean hasParseError = false;
		Document doc = null;
		// XML Valiation code from: http://java.sun.com/developer/technicalArticles/xml/validationxpath/
		try {

            // Parse an XML document into a DOM tree.
            DocumentBuilder parser =
                DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = parser.parse(manifestFile);

            // Create a SchemaFactory capable of understanding WXS schemas.
            SchemaFactory factory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Load a WXS schema, represented by a Schema instance.
            String tempStr = manifestFile.getAbsolutePath();
            int index = tempStr.lastIndexOf(File.separator);
            tempStr = tempStr.substring(0, index);
            tempStr += File.separator + "sdkManifest.xsd";
            Source schemaFile = new StreamSource(new File(tempStr));
            Schema schema = factory.newSchema(schemaFile);

            // Create a Validator object, which can be used to validate
            // an instance document.
            Validator validator = schema.newValidator();

            // Validate the DOM tree.
            // Don't use new DOMSource(doc) for manifest, that requires namespace 
            // and some (e.g. S60) manifest.xml says xsi:noNamespaceSchemaLocation.
            // Failure will show in Java 6
            // see detail in XERCESJ-1163 boog report
            // http://issues.apache.org/jira/browse/XERCESJ-1163?page=all 
            validator.validate(new StreamSource(manifestFile));
	
        } catch (ParserConfigurationException e) {
        	ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.getPluginId(), IStatus.ERROR, "SDK Manifest could not be parsed correctly.", e));
        	hasParseError = true;
        } catch (SAXException e) {
        	ResourcesPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, SDKCorePlugin.getPluginId(), IStatus.ERROR, "SDK Manifest failed schema validation.", e));
        	hasParseError = true;
        	// exception handling - document not valid!
        } catch (IOException e) {
        	// This SDK has not manifest, don't post error
        	hasParseError = true;
        }       
			
		// if there were any parse errors, throw an exception so that suitable defaults
		// can be calculated from the devices.xml entry.
		if (hasParseError) {
			return false;
		}
		
		try {
			
			ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
			Node node = XPathAPI.selectSingleNode(doc, "sdk/paths");
			for (NodeIterator nodeIter = XPathAPI.selectNodeIterator(doc, "sdk"); (node = nodeIter.nextNode()) != null;) {
				NamedNodeMap attribs = node.getAttributes();
				
				node = XPathAPI.selectSingleNode(doc, "sdk/paths/@root");
				String path = node == null ? null : node.getNodeValue();
				if (null != path) {
					// make sure EPOCROOT is terminated with a path delimiter
					if (!path.endsWith(File.separator)) {
						path += File.separator;
					}
					// EPOCROOT is currently ignored.
					
					for (NodeIterator pathsNodeIter = XPathAPI.selectNodeIterator(doc, "sdk/paths/root"); (node = pathsNodeIter.nextNode()) != null;) 
					{
						// Loop through the sdk:paths:root elements and pick out check all the 'id' attriutes for matches...
						attribs = node.getAttributes();
						String rootType = attribs.getNamedItem("id").getNodeValue();
						if (rootType.equals(PATH_ID_INCLUDEDIR)) {
							node = attribs.getNamedItem("path");
							/*if (null!=node){
								setIncludePath(new Path(path + node.getNodeName()));
							}*/
						} else if (rootType.equals(RELEASE)) {
							node = attribs.getNamedItem("path");
							/*if (null!=node){
								setReleaseRoot(new Path(path + node.getNodeName()));
							}*/
						} else if (rootType.equals(PATH_ID_TOOLSDIR)) {
							node = attribs.getNamedItem("path");
							/*if (null!=node){
								setToolsPath(new Path(path + node.getNodeName()));
							}*/
						} else if (rootType.equals(PATH_ID_SRCDIR)) {
							node = attribs.getNamedItem("path");
							/*if (null!=node){
								setSourcePath(new Path(path + node.getNodeName()));
							}*/
						}
					}
				}

				// Get the osInfo
				node = XPathAPI.selectSingleNode(doc, "sdk/osInfo");
				if (null != node){
					attribs = node.getAttributes();
					node = attribs.getNamedItem("version");
					if (null != node)
					{
						try {
							setOSVersion(new Version(node.getNodeValue()));
						}
						catch (IllegalArgumentException e){	
							// ignored...improper format
						}
					}
				}
				
				//  Get the SDK Version...
				node = XPathAPI.selectSingleNode(doc, "sdk/sdkVersion");
				if (null != node) {
					try {
						// ignored
					}
					catch (IllegalArgumentException e){	
						// ignored...improper format
					} 
				}				
			}
		} catch (TransformerException e){
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * Scans the SDK's epoc32\data\buildinfo.txt file and tries to build the Version
	 * and branch identifiers. This should not be called when a manifest.xml file exits
	 *
	 */
	private void scanSDKForVersionInfo(){
		File epocRoot = new File(getEPOCROOT());
		File bldInfoFile = new File(epocRoot, BUILD_INFO_TXT_FILE);
		if (!bldInfoFile.exists())
			return;
		
		if (getOSVersion().getMajor() == 0)
			setOSVersion(new Version("9.5.0"));  // Set a default version that will work with all EKA2
		
		try {
			ISBSv1BuildInfo sbsv1BuildInfo = (ISBSv1BuildInfo)getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER);
			char[] cbuf = new char[(int) bldInfoFile.length()];
			Reader reader = new FileReader(bldInfoFile);
			reader.read(cbuf);
			reader.close();
			String[] lines = new String(cbuf).split("\r|\r\n|\n");
			for (int i = 0; i < lines.length; i++) {
				// skip comments and blank lines
				String line = removeComments(lines[i]);
				if (line.matches("\\s*#.*") || line.trim().length() == 0) 
					continue;
				
				line = line.trim();
				if (line.startsWith(BUILD_INFO_KEYWORD)){
					String[] versionTokens = line.split(" ");
					if (versionTokens.length == 3){

						if (versionTokens[2].toUpperCase().contains("TB92SF")){
							setOSVersion(new Version("9.5.0"));
							break;
						} else if (versionTokens[2].toUpperCase().contains("TB101SF")){
							setOSVersion(new Version("9.6.0"));
							break;
						} else if (versionTokens[2].toUpperCase().contains("TB102SF")){
							setOSVersion(new Version("9.6.0"));
							break;
						}
						else if (versionTokens[2].lastIndexOf("v") > 0){
							int index = versionTokens[2].lastIndexOf("v");
							String osVersionString = versionTokens[2].substring(index+1, versionTokens[2].length());
							
							// Set the version, split on alphanumeric to get rid of any junk at the end
							String[] tempStr = osVersionString.split("[a-zA-Z_]");
							if (tempStr.length != 0){
								try {
									setOSVersion(Version.parseVersion(tempStr[0]));
								} catch (Exception e) {
									// ignore, default version already set
									// just catch exception so we move along to the next SDK
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
		}
		
		
	}

	private boolean setDataFromManifestXML(){
		if (hasManifestXML()){
			
			if (processManifest(new File(deviceEntry.getEpocroot(), MANIFEST_XML_LOCATION))){
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setSupportFeatures() {
		scanForWINSCW_UREL();
		scanForAvkon();
		sdkFeatures.add(ISymbianSDKFeatures.IS_EKA2);
	}

	/**
	 * Check if avkon is a supported feature.
	 */
	private void scanForAvkon() {
		String armv5UDEBFullPathStr = getEPOCROOT();
		armv5UDEBFullPathStr += ARMV5_UDEB_DIR;
		IPath armv5UDEBPath = new Path(armv5UDEBFullPathStr);
		if (armv5UDEBPath != null && armv5UDEBPath.toFile().exists()){
			if (armv5UDEBPath.append("avkon.dll").toFile().exists()){
				sdkFeatures.add(ISymbianSDKFeatures.IS_AVKON_SUPPORTED);
				return;
			}
			// not there, check winscw
			String winscwURELFullPathStr = getEPOCROOT();
			winscwURELFullPathStr += WINSCW_UREL_DIR;
			IPath winscwURELPath = new Path(winscwURELFullPathStr);
			if (winscwURELPath != null && winscwURELPath.toFile().exists()){
				if (winscwURELPath.append("avkon.dll").toFile().exists()){
					sdkFeatures.add(ISymbianSDKFeatures.IS_AVKON_SUPPORTED);
					return;
				}
			}
		}
	}
	
	/**
	 * Check to see whether or not we should support WINSCW UREL
	 */
	@SuppressWarnings("unchecked")
	private void scanForWINSCW_UREL(){
		String winscwURELFullPathStr = getEPOCROOT();
		winscwURELFullPathStr += WINSCW_UREL_DIR;
		IPath winscwURELPath = new Path(winscwURELFullPathStr);
		if (winscwURELPath != null && winscwURELPath.toFile().exists()){
			if (winscwURELPath.append("epoc.exe").toFile().exists()){
				if (winscwURELPath.append("euser.dll").toFile().exists()){
					sdkFeatures.add(ISymbianSDKFeatures.IS_WINSCW_UREL_SUPPORTED);
				}
			}
		}
	}

}
