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

import com.nokia.carbide.cpp.epoc.engine.preprocessor.*;
import com.nokia.carbide.cpp.internal.api.sdk.BuildPlat;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContext;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.BasicIncludeFileLocator;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.MacroScanner;
import com.sun.org.apache.xpath.internal.XPathAPI;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.osgi.framework.Version;
import org.w3c.dom.*;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

public class SymbianSDK implements ISymbianSDK {

	public static final String MANIFEST_XML_LOCATION = "/epoc32/kit/manifest.xml"; //$NON-NLS-1$
	
	// manifest.xml attributes
	private static final String PATH_ID_EPOC32DIR = "epoc32Dir"; //$NON-NLS-1$
	private static final String PATH_ID_INCLUDEDIR = "includeDir"; //$NON-NLS-1$
	private static final String PATH_ID_TOOLSDIR = "toolsDir"; //$NON-NLS-1$
	private static final String PATH_ID_SRCDIR = "srcDir"; //$NON-NLS-1$
	
	private static final String INCLUDE = "include"; //$NON-NLS-1$
	private static final String RELEASE = "release"; //$NON-NLS-1$
	private static final String TOOLS = "tools"; //$NON-NLS-1$
	private static final String EPOC32_DIR = "epoc32"; //$NON-NLS-1$
	private static final String INCLUDE_SUBDIR = "epoc32/" + INCLUDE; //$NON-NLS-1$
	private static final String RELEASE_SUBDIR = "epoc32/" + RELEASE; //$NON-NLS-1$
	private static final String TOOLS_SUBDIR = "epoc32/" + TOOLS; //$NON-NLS-1$
	private static final String VARIANT_CFG_FILE = "epoc32/tools/variant/variant.cfg"; //$NON-NLS-1$
	private static final String SPP_VARIANT_CFG_FILE = "epoc32/tools/variant/spp_variant.cfg"; //$NON-NLS-1$
	private static final String TARGETTYPE_PM_FILE = "epoc32/tools/trgtype.pm"; //$NON-NLS-1$
	private static final String BUILD_INFO_TXT_FILE = "epoc32/data/buildinfo.txt"; //$NON-NLS-1$
	private static final String BUILD_INFO_KEYWORD = "ManufacturerSoftwareBuild";
	
	private static final String WINSCW_UREL_DIR = "epoc32\\release\\winscw\\urel";
	
	protected DeviceType deviceEntry = null;
	private boolean enabled = true;
	private boolean wasScanned = false;
	private Version osVersion;
	private Version sdkVersion;
	private String sdkOSBranch;

	private File licenseFile;
	private File prefixFile;
	
	private List<IDefine> variantHRHMacros = null;
	private List<ISymbianBuildContext> bsfContextList = new ArrayList<ISymbianBuildContext>(0);
	
	private List<ISymbianBuildContext> binaryVariantContextList = new ArrayList<ISymbianBuildContext>(0);
	
	private Date createDate;
	private URL publisherURL;
	private String sdkDescription;
	private String publisherName;
	
	private boolean supportsWINSCW_UREL;
	
	List<String> supportedTargetTypesList = new ArrayList<String>();

	private IBSFCatalog bsfCatalog;
	private ISBVCatalog sbvCatalog;

	private Map<String, List<String>> cachedPlatformMacros = new HashMap<String, List<String>>();
	
	// greedy match means the filename is in the last group
	private static Pattern VARIANT_HRH_LINE_PATTERN = Pattern.compile("(?i)(.*)(/|\\\\)(.*hrh)");

	private long hrhFileTimeStamp = 0;
	
	public SymbianSDK(DeviceType device) {
		deviceEntry = device;
		scanSDK();
	}
	
	public void scanSDK(){
		
		cachedPlatformMacros.clear();
		variantHRHMacros = null;
		
		if (!setDataFromManifestXML()){
			// must derive the OS and SDK version
			if (!deriveOSVersionFromDeviceId()){
				//need to scan SDK files for OS and SDK version
				scanSDKForVersionInfo();
			}
		}
		
		IPath prefixFileFullPath =  getPrefixFromVariantCfg();
		if (prefixFileFullPath != null){
			setPrefixFile(prefixFileFullPath);
		}
		
		// Trick for SEMC. Try to set the SDK version based on a UIQ HRH file
		// if there's is no SDK version
		if (getSDKVersion().getMajor() == 0 && getName().equalsIgnoreCase(ISymbianSDK.UIQ_SDK_NAME)){
			// This might be an SEMC CustKit, get the version from the HRH file
			if (getPrefixFile() != null){
				String prefixFileStr = getPrefixFile().toString();
				if (prefixFileStr.indexOf("UIQ_") != -1 && prefixFileStr.indexOf(".hrh") != -1){
					prefixFileStr = prefixFileStr.substring(prefixFileStr.indexOf("UIQ_")+4, prefixFileStr.indexOf(".hrh"));
					if (prefixFileStr.length() == 3 && prefixFileStr.contains(".")){
						setSDKVersion(new Version(prefixFileStr));
					}
				}
			}
		}
		
		scanForWINSCW_UREL();
	}
	
	public List<String> getAvailablePlatforms() {
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		return sdkMgr.getSymbianMacroStore().getSupportedPlatforms(getOSVersion(), getSDKOSBranch(), getBSFCatalog());
	}

	public Date getCreationDate() {
		return createDate;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enable) {
		enabled = enable;
	}

	public String getFamily() {
		String[] parts = getName().split("\\.");
		if (parts.length == 3){ 
			if (getSDKVersion().getMajor() == 5 && getName().equalsIgnoreCase(NOKIA_SF_SDK_NAME)){ 
				// A vendor of "symbian" and SDK major version 5 is the same as prior naming for "com.nokia.s60" & 5th Edition. 
				// Return "s60" so that project template generation continues to work as it's a S60 5th ed. SDK. 
				return ISymbianSDK.S60_FAMILY_ID;
			} else {
				return parts[2];
			}
		}
			
		return "";
	}
	
	
	public List<String> getTargetTypeMacros(String targettype) {
		// this is based on \epoc32\tools\trgtype.pm which changes from
		// OS version to OS version, but largely remains constant with
		// regards to the basic type.
		List<String> macros = new ArrayList<String>();
		
		// if it's not one of these then it's a DLL
		if (targettype.compareToIgnoreCase("EPOCEXE") == 0) {
			macros.add("__EXEDLL__");
		} else if (targettype.compareToIgnoreCase("EXEDLL") == 0) {
			macros.add("__EXEDLL__");
		} else if (targettype.compareToIgnoreCase("EXE") == 0) {
			macros.add("__EXE__");
		} else if (targettype.compareToIgnoreCase("EXEXP") == 0) {
			macros.add("__EXE__");
		} else if (targettype.compareToIgnoreCase("IMPLIB") == 0) {
			macros.add("__IMPLIB__");
		} else if (targettype.compareToIgnoreCase("KLIB") == 0) {
			macros.add("__LIB__");
		} else if (targettype.compareToIgnoreCase("LIB") == 0) {
			macros.add("__LIB__");
		} else {
			macros.add("__DLL__");
		}
		return macros;
	}


	public List<ISymbianBuildContext> getUnfilteredBuildConfigurations() {
		
		List<ISymbianBuildContext> buildTargets = new ArrayList<ISymbianBuildContext>();
		
		// note that this gets variant platforms but not regular BSF's
		List <String>buildPlats =  getAvailablePlatforms();
		
		if (buildPlats.size() == 0){
			return Collections.EMPTY_LIST;
		}
		
		buildTargets.add(new SymbianBuildContext(this, ISymbianBuildContext.EMULATOR_PLATFORM, ISymbianBuildContext.DEBUG_TARGET));
		
		if (supportsWINSCW_UREL()){
			buildTargets.add(new SymbianBuildContext(this, ISymbianBuildContext.EMULATOR_PLATFORM, ISymbianBuildContext.RELEASE_TARGET));
		}
		
		for (String currPlat : buildPlats){
			
			if (currPlat.equals(ISymbianBuildContext.EMULATOR_PLATFORM) ) { 
				// emulation targets already determined (some SDKs don't get WISNCW UREL
				continue;
			}
			
			if (!currPlat.equals(ISymbianBuildContext.ARM4_PLATFORM) &&
				!currPlat.equals(ISymbianBuildContext.ARMI_PLATFORM) &&
				!currPlat.equals(ISymbianBuildContext.THUMB_PLATFORM)) { // EKA1 targets don't get debug builds
				buildTargets.add(new SymbianBuildContext(this, currPlat, ISymbianBuildContext.DEBUG_TARGET));
			}
			
			// everything gets release except for WINSCW
			buildTargets.add(new SymbianBuildContext(this, currPlat, ISymbianBuildContext.RELEASE_TARGET));
		}
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (sdkMgr.getBSFScannerEnabled()){
			buildTargets.addAll(getBSFPlatformContexts());
			buildTargets.addAll(getBinaryVariationPlatformContexts()); // Symbian Binary Variation (.var)
		}
		
		return buildTargets;
	}
	
	public List<ISymbianBuildContext> getBSFPlatformContexts(){
		
		synchronized (bsfContextList) {
			if (!bsfContextList.isEmpty()){
				return bsfContextList;
			}
			
			IBSFCatalog catalog = getBSFCatalog();
			for (IBSFPlatform platform : catalog.getPlatforms()) {
				// only return non-variant style BSF's.  see boog #4533 for details.
				if (!platform.isVariant()) {
					bsfContextList.add(new SymbianBuildContext(this, platform.getName().toUpperCase(), ISymbianBuildContext.DEBUG_TARGET));
					bsfContextList.add(new SymbianBuildContext(this, platform.getName().toUpperCase(), ISymbianBuildContext.RELEASE_TARGET));
				}
			}
		}
		
		return bsfContextList;
	}
	
public List<ISymbianBuildContext> getBinaryVariationPlatformContexts(){
		
		synchronized (binaryVariantContextList) {
			if (!binaryVariantContextList.isEmpty()){
				return binaryVariantContextList;
			}
			
			ISBVCatalog catalog = getSBVCatalog();
			for (ISBVPlatform sbvPlatform : catalog.getPlatforms()) {
				// Currently only variation of ARMV5 is supported... So just hard code the variated platform
				// Only add the build platform if it's not virtual.
				if (!sbvPlatform.isVirtual()){
					binaryVariantContextList.add(new SymbianBuildContext(this, SymbianBuildContext.ARMV5_PLATFORM + "." + sbvPlatform.getName(), ISymbianBuildContext.DEBUG_TARGET));
					binaryVariantContextList.add(new SymbianBuildContext(this, SymbianBuildContext.ARMV5_PLATFORM + "." + sbvPlatform.getName(), ISymbianBuildContext.RELEASE_TARGET));
				}
			}
		}
		
		return binaryVariantContextList;
	}
 	
	public List<ISymbianBuildContext> getFilteredBuildConfigurations() {
		
		 List<ISymbianBuildContext> buildContexts =  getUnfilteredBuildConfigurations();
		
		if (buildContexts.size() == 0){
			return Collections.EMPTY_LIST;
		}
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		 List<BuildPlat> platFilterList = sdkMgr.getPlatformList();
		
		Iterator<ISymbianBuildContext> li = buildContexts.iterator();

		while(li.hasNext()){
			ISymbianBuildContext currContext = li.next();
			for (BuildPlat currPlat : platFilterList){ // see which ones need to be filtered out.
				
				if (currPlat.getPlatName().equals(currContext.getPlatformString())){
					if (!currPlat.isEnabled()){
						if (isEKA1()&& currPlat.getOsIdentifier().equals(BuildPlat.EKA1_IDENTIFIER)){
							li.remove(); // filtered out in UI, don't show
							break;
						} else if (isEKA2() && currPlat.getOsIdentifier().equals(BuildPlat.EKA2_IDENTIFIER)){
							li.remove();  // filtered out in UI, don't show
							break;
						}
					}
				}
				
			}
		}

		return buildContexts;
		
	}

	public IPath getIncludePath() {
		String epocRoot = getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32IncPath = new Path(epocRoot).append("epoc32\\include");
			// try to canonicalize it so it matches actual file system case
			try {
				epoc32IncPath = new Path(epoc32IncPath.toFile().getCanonicalPath());
			} catch (IOException e) {
			}
			return epoc32IncPath;
		}
		return null;
	}

	public File getLicenseFile() {
		return licenseFile;
	}

	public String getName() {
		if (deviceEntry != null) {
			return deviceEntry.getName();
		}
		return "";
	}

	public Version getOSVersion() {		
		if (osVersion == null){
			return new Version("0.0");
		}
		return osVersion;
	}
	
	public Version getSDKVersion() {
		if (sdkVersion == null){
			return new Version("0.0");
		}
		return sdkVersion;
	}

	public List<String> getPlatformMacros(String platform) {
		List<String> platformMacros = cachedPlatformMacros.get(platform.toUpperCase());
		if (platformMacros == null) {
			synchronized (cachedPlatformMacros) {
				ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
				platformMacros = sdkMgr.getSymbianMacroStore().getPlatformMacros(getOSVersion(), getSDKOSBranch(), getBSFCatalog(), platform);
				cachedPlatformMacros.put(platform.toUpperCase(), platformMacros);
			}
		}
		return platformMacros;
	}
	
	public List<String> getVendorSDKMacros() {
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		return sdkMgr.getSymbianMacroStore().getVendorMacros(getSDKVersion(), getName());
	}
	
	public File getPrefixFile() {
		return prefixFile;
	}

	public URL getPublisherURL() {
		return publisherURL;
	}

	public IPath getReleaseRoot() {
		String epocRoot = getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32RelPath = new Path(epocRoot).append("epoc32\\release");
			// try to canonicalize it so it matches actual file system case
			try {
				epoc32RelPath = new Path(epoc32RelPath.toFile().getCanonicalPath());
			} catch (IOException e) {
			}
			return epoc32RelPath;
		}
		return null;
	}

	public String getSDKDescription() {
		if (sdkDescription == null){
			return "";
		}
		return sdkDescription;
	}

	public String getSDKOSBranch() {
		if (sdkOSBranch == null){
			return "";
		}
		
		return sdkOSBranch;
	}

	public IPath getToolsPath() {
		String epocRoot = getEPOCROOT();
		if (epocRoot.length() > 0) {
			IPath epoc32ToolsPath = new Path(epocRoot).append("epoc32\\tools");
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

	public String getVendor() {
		String[] parts = getName().split("\\.");
		if (parts.length == 3)
			return parts[1];
		
		return "";
	}

	public List<IDefine> getProjectVariantHRHDefines() {
		// this is deprecated. should use the one in ISymbianBuildContext instead.

		long hrhTime = 0;
		if (getPrefixFile() != null){
			hrhTime = getPrefixFile().lastModified();
		}
		
		if (variantHRHMacros == null || variantHRHMacros.size() == 0 || hrhTime != hrhFileTimeStamp) {
			synchronized (this) {
				hrhFileTimeStamp = hrhTime;  // update time stamp to latest
				List<IDefine> macros = new ArrayList<IDefine>();
				Map<String, IDefine> namedMacros = new HashMap<String, IDefine>();
				File file = getPrefixFile();
				if (file != null){
					
					// Note: MacroScanner argument 'BasicIncludeFileLocation' can take 
					// paramaters for user/system includes, however, for getting macros
					// from the prefix file it should not be necessary.
					MacroScanner scanner = new MacroScanner(
							new BasicIncludeFileLocator(null, null),
							DefaultModelDocumentProvider.getInstance(), 
							DefaultTranslationUnitProvider.getInstance());
					scanner.scanFile(file);
		
					List<IDefine> scannedMacros = (List<IDefine>)scanner.getMacroDefinitions();
					for (IDefine scannedMacro : scannedMacros){
						// we don't want duplicate macros, so check to see if it's already there.
						// if it is, remove it and then add the newer one.  this is consistent with
						// how it would be from a compiler standpoint.
						IDefine macro = namedMacros.get(scannedMacro.getName());
						if (macro != null) {
							macros.remove(macro);
						}
						
						macros.add(scannedMacro);
						namedMacros.put(scannedMacro.getName(), scannedMacro);
					}
					
					List<String> variantCFGMacros = getVariantCFGMacros();
					for (String cfgMacros : variantCFGMacros){
						// we don't want duplicate macros, so check to see if it's already there.
						IDefine existingMacro = namedMacros.get(cfgMacros);
						if (existingMacro != null) {
							macros.remove(existingMacro);
						}
						
						IDefine macro = DefineFactory.createSimpleFreeformDefine(cfgMacros);
						macros.add(macro);
						namedMacros.put(macro.getName(), macro);
					}
				}
				variantHRHMacros = macros;
			}
		}
		
		return variantHRHMacros;
	}
	
	public List<String> getProjectVariantHRHMacros() {
		// this API is deprecated, so don't cache this
		List<IDefine> defines = getProjectVariantHRHDefines();
		List<String> macros = new ArrayList<String>(defines.size());
		for (IDefine define : defines) {
			macros.add(define.getDefinitionText());
		}
		return macros;
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
	
	public boolean isDefaultSDK() {
		if (deviceEntry == null) {
			return false;
		}
		
		if (deviceEntry.getDefault().equals(DefaultType.YES_LITERAL)){
			return true;
		} else {
			return false;
		}
	}

	public boolean isValid() {
		// TODO: What makes us valid?  We only need a valid devices.xml entry
		// to do a build, but we need the other data for most everything else.
		// this should probably return false unless we get everything we need.
		return false;
	}

	public List<String> validationErrors() {
		// TODO return error strings for everything that is not setup
		// properly.
		return null;
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
			
			Node node = XPathAPI.selectSingleNode(doc, "sdk/paths");
			for (NodeIterator nodeIter = XPathAPI.selectNodeIterator(doc, "sdk"); (node = nodeIter.nextNode()) != null;) {
				NamedNodeMap attribs = node.getAttributes();
				
				node = XPathAPI.selectSingleNode(doc, "sdk/@id");
				if (node != null) {
					// currently ignored...
					String tempStr = node.getNodeValue();
					
					
				}
				
				// name is currently ignored
				node = attribs.getNamedItem("name");
				String sdkFamily = node == null ? null : node.getNodeValue();
				
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
							} /*
							else if (rootType.equals(PATH_ID_EPOC32DIR)) {
								node = attribs.getNamedItem("path");
								if (null!=node){
									set(new File(path, node.getNodeName()));
								}
							}
							*/
						}
				}
				
				node = XPathAPI.selectSingleNode(doc, "sdk/paths/license/@file");
				if (node != null){
					String licenseStr = node.getNodeValue();
					this.setLicenseFile(new File(path, licenseStr));
				}
				// Get the general SDK description...
				node = XPathAPI.selectSingleNode(doc, "sdk/description");
				if (null != node) {
					setSDKDescription(node.getTextContent());
				}	
				
				// Get the build configuration to use...
				/*
				node = XPathAPI.selectSingleNode(doc, "sdk/buildConfig");
				if (null != node) {
					sdkManifest.setSDKBuildConfig(node.getTextContent());
					this.id = sdkManifest.getSDKBuildConfig();
				}
				*/
				
				//  Get the SDK Version...
				node = XPathAPI.selectSingleNode(doc, "sdk/sdkVersion");
				if (null != node) {
					try {
						setSDKVersion(new Version(node.getTextContent()));
					}
					catch (IllegalArgumentException e){	
						// ignored...improper format
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
					node = attribs.getNamedItem("branch");
					if (null != node)
					{
						if (getOSVersion().getMajor() == 9){
							setOSSDKBranch("");
						}else {
							setOSSDKBranch(node.getNodeValue());
						}
					}
				}
				//	 Get the creation time/date
				node = XPathAPI.selectSingleNode(doc, "sdk/createdDate");
				if (null != node){
					try {
						// Get the default MEDIUM/SHORT DateFormat
						DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
						setCreateDate(format.parse(node.getTextContent()));
					}
					catch (ParseException pe) {
						pe.printStackTrace();
					}
				}
				
				// Get the publisher
				node = XPathAPI.selectSingleNode(doc, "sdk/publisher");
				if (null != node){
					attribs = node.getAttributes();
					node = attribs.getNamedItem("link");
					if (null != node)
					{
						try {
							setPublisherURL(new URL(node.getNodeValue()));
						}
						catch (IllegalArgumentException e){	
							// ignored...improper format
						}
					}
					node = attribs.getNamedItem("logo");
					/*if (null != node){
						setPublisherLogo(new File(path, node.getNodeValue()));
					}*/
					node = attribs.getNamedItem("name");
					if (null != node)
					{
						setPublisherName(node.getNodeValue());
					}
				}
			}
		
		} catch (TransformerException e){
			e.printStackTrace();
		} catch (MalformedURLException e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void setLicenseFile(File licenseFile) {
		 this.licenseFile = licenseFile;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public void setIsDefaultSDK(boolean isDefault) {
		if (isDefault){
			deviceEntry.setDefault(DefaultType.YES_LITERAL);
		} else {
			deviceEntry.setDefault(DefaultType.NO_LITERAL);
		}
		
	}

	public void setOSSDKBranch(String branch) {
		sdkOSBranch = branch;
		
	}

	public void setOSVersion(Version osVer) {
		this.osVersion = osVer;
		
	}

	public void setPrefixFile(IPath prefixFile) {
		this.prefixFile = new File(prefixFile.toOSString());
	}


	public void setPublisherURL(URL publisherURL) {
		this.publisherURL = publisherURL;
	}

	public void setSDKVersion(Version sdkVers) {
		sdkVersion = sdkVers;
	}

	public void setSDKDescription(String sdkDescription) {
		this.sdkDescription = sdkDescription;
	}
	
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	private boolean deriveOSVersionFromDeviceId(){
		boolean foundOSVersion = false;
		
		if (getUniqueId().equals("S60_3rd")){
			setOSVersion(new Version("9.1.0"));
			setSDKVersion(new Version("3.0.0"));
			foundOSVersion = true;
		} else if (getUniqueId().equals("UIQ3") || getUniqueId().equals("UIQ_3_PB2")){
			setOSVersion(new Version("9.1.0"));
			setSDKVersion(new Version("3.0.0"));
			foundOSVersion = true;
		} else if (getUniqueId().equals("Series60_1_2_CW")){
			setOSVersion(new Version("6.1.0"));
			setSDKVersion(new Version("1.2.0"));
			foundOSVersion = true;
		} else if (getUniqueId().equals("Series60_2_0_CW")){
			setOSVersion(new Version("7.0.0"));
			setSDKVersion(new Version("2.0.0"));
			foundOSVersion = true;
		} else if (getUniqueId().equals("Series60_v21_CW")){
			setOSVersion(new Version("7.0.0"));
			setSDKVersion(new Version("2.1.0"));
			foundOSVersion = true;
		} else if (getUniqueId().equals("S60_2nd_FP2_CW")){
			setOSVersion(new Version("8.0.0"));
			setSDKVersion(new Version("2.6.0"));
			setOSSDKBranch(EKA1_A_BRANCH_IDENTIFIER);
			foundOSVersion = true;
		}  else if (getUniqueId().equals("S60_2nd_FP3") || getUniqueId().equals("S60_2nd_FP3_CW") || getUniqueId().equals("S60_2nd_FP3_B")){
			setOSVersion(new Version("8.1.0"));
			setSDKVersion(new Version("2.8.0"));
			setOSSDKBranch(EKA1_A_BRANCH_IDENTIFIER);
			foundOSVersion = true;
		} else if (getUniqueId().equals("UIQ_21")){
			setOSVersion(new Version("7.0.15"));
			setSDKVersion(new Version("2.1.0"));
			foundOSVersion = true;
		} else if (getUniqueId().equals("Series80_DP2_0_SDK_CW")){
			setOSVersion(new Version("7.0.0"));
			setSDKVersion(new Version("2.0.0"));
			foundOSVersion = true;
		}
		return foundOSVersion;
		
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
		
		try {
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
						
						if (versionTokens[2].toUpperCase().startsWith("TB92SF") || versionTokens[2].toUpperCase().endsWith("TB92SF")){
							setOSVersion(new Version("9.5.0"));
							setSDKVersion(new Version("5.2.0"));
							break;
						} else if (versionTokens[2].toUpperCase().startsWith("TB101SF") || versionTokens[2].toUpperCase().endsWith("TB101SF")){
							setOSVersion(new Version("9.6.0"));
							setSDKVersion(new Version("6.0.0"));
							break;
						}
						else if (versionTokens[2].lastIndexOf("v") > 0){
							int index = versionTokens[2].lastIndexOf("v");
							String osVersionString = versionTokens[2].substring(index+1, versionTokens[2].length());
							
							if (osVersionString.compareToIgnoreCase("tb91sf") == 0){
								setOSVersion(new Version("9.4.0"));
								setSDKVersion(new Version("5.1.0"));
								break;
							}
							
							if (osVersionString.compareToIgnoreCase("tb92sf") == 0){
								setOSVersion(new Version("9.5.0"));
								setSDKVersion(new Version("5.2.0"));
								break;
							}
							
							if (osVersionString.compareToIgnoreCase("tb101sf") == 0){
								setOSVersion(new Version("9.6.0"));
								setSDKVersion(new Version("6.0.0"));
								break;
							}
							
							if (osVersionString.endsWith(EKA1_A_BRANCH_IDENTIFIER) || 
							    osVersionString.endsWith(EKA2_B_BRANCH_IDENTIFIER) ||
							    osVersionString.endsWith(EKA1_S_BRANCH_IDENTIFIER)){
								
								String branch = osVersionString.substring(osVersionString.length()-1, osVersionString.length());
								if (branch != null){
									setOSSDKBranch(branch);
								}
							}
							
							// Set the version, split on alphanumeric to get rid of any junk at the end
							String[] tempStr = osVersionString.split("[a-zA-Z]");
							if (tempStr.length != 0){
								setOSVersion(Version.parseVersion(tempStr[0]));
							}
						}
					}
				}
			}
		} catch (IOException e) {
		}
		
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getUniqueId();
	}
	
	/**
	 * Get the full path to the prefix file defined under \epoc32\tools\variant\variant.cfg
	 * @return A path object, or null if the variant.cfg does not exist. This routine does not check to see if the returned path exists.
	 */
	public IPath getPrefixFromVariantCfg(){
		File epocRoot = new File(getEPOCROOT());
		File variantCfg;
		variantCfg = new File(epocRoot, SPP_VARIANT_CFG_FILE);
		if (!variantCfg.exists()) {
			variantCfg = new File(epocRoot, VARIANT_CFG_FILE);
			if (!variantCfg.exists())
				return null;
		}
		
		String variantDir = null;
		String variantFile = null;
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
					variantDir = matcher.group(1);
					variantFile = matcher.group(3); 
					File variantFullPathFile = new File(epocRoot, variantDir + File.separator + variantFile);
					IPath variantFilePath = new Path(variantFullPathFile.getAbsolutePath());
					return variantFilePath;
				}
			}
		} catch (IOException e) {
		}
		
		return null; // can't find the file...
	}
	
	/**
	 * Remove single line C-style comments, multi-line C++-style comments and blank lines
	 */
	private static String removeComments(String aIntermediateContent) {
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

	public void setEPOCROOT(String epocRoot) {
		deviceEntry.setEpocroot(epocRoot);	
	}

	public void setName(String name) {
		deviceEntry.setName(name);
	}

	public void setUniqueID(String id) {
		deviceEntry.setId(id);
	}
	
	@Deprecated
	public boolean getRequiresRestart() {
		return false;
	}

	public String getPublisherName() {
		return publisherName;
	}
	
	public boolean isEKA1() {
		return !isEKA2() && getOSVersion().getMajor() < 9 && getOSVersion().getMajor() >= 6;
	}
	
	public boolean isEKA2() {
		if (getOSVersion().getMajor() >= 9) {
			return true;
		}
		if (getOSVersion().getMajor() == 8 
				&& getSDKOSBranch().equals(ISymbianSDK.EKA2_B_BRANCH_IDENTIFIER)){
			return true;
		}
		return false;
	}
	
	public boolean isS60() {
		return getFamily().equals(ISymbianSDK.S60_FAMILY_ID)
			|| getFamily().equals(ISymbianSDK.SERIES60_FAMILY_ID);
	}

	public void setSupportsWINSCW_UREL(boolean isSupported) {
		supportsWINSCW_UREL = isSupported;
	}

	public boolean supportsWINSCW_UREL() {
		return supportsWINSCW_UREL;
	}
	
	/**
	 * Check to see whether or not we should support WINSCW UREL
	 */
	private void scanForWINSCW_UREL(){
		supportsWINSCW_UREL = false;
		String winscwURELFullPathStr = getEPOCROOT();
		winscwURELFullPathStr += WINSCW_UREL_DIR;
		IPath winscwURELPath = new Path(winscwURELFullPathStr);
		if (winscwURELPath != null && winscwURELPath.toFile().exists()){
			if (winscwURELPath.append("epoc.exe").toFile().exists()){
				if (winscwURELPath.append("euser.dll").toFile().exists()){
					supportsWINSCW_UREL = true;
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISymbianSDK#getBSFCatalog()
	 */
	public IBSFCatalog getBSFCatalog() {
		synchronized (this) {
			if (bsfCatalog == null) {
				bsfCatalog = BSFCatalogFactory.createCatalog(this);
			}
		}
		return bsfCatalog;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.sdk.core.ISymbianSDK#getBSFCatalog()
	 */
	public ISBVCatalog getSBVCatalog() {
		synchronized (this) {
			if (sbvCatalog == null) {
				sbvCatalog = SBVCatalogFactory.createCatalog(this);
			}
		}
		return sbvCatalog;
	}

	public void setPreviouslyScanned(boolean wasScanned) {
		this.wasScanned = wasScanned;
	}
	
	public boolean isPreviouslyScanned() {
		return wasScanned;
	}
	
}
