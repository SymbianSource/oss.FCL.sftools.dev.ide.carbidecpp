/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.internal.project.core.updater;

import com.nokia.carbide.cpp.internal.api.sdk.*;
import com.nokia.carbide.cpp.internal.project.core.Messages;
import com.nokia.carbide.cpp.internal.project.core.updater.SymbianBuildParser.ValueInfo.IConverter;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.w3c.dom.*;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

import javax.xml.parsers.*;

public class SymbianBuildParser {
	
	private static final String RESOURCE_APPS = "resource" + File.separator + "apps"; //$NON-NLS-1$
	private static final String CDTBUILD_FILENAME = ".cdtbuild"; //$NON-NLS-1$
	private static final String OPTION = "option"; //$NON-NLS-1$
	private static final String TOOL = "tool"; //$NON-NLS-1$
	private static final String RES_CONF = "resourceConfiguration"; //$NON-NLS-1$
	private static final String ARTIFACT_EXTENSION = "artifactExtension"; //$NON-NLS-1$
	private static final String ARTIFACT_NAME = "artifactName"; //$NON-NLS-1$
	private static final String TOOL_CHAIN = "toolChain"; //$NON-NLS-1$
	private static final String CONFIGURATION = "configuration"; //$NON-NLS-1$
	private static final String LIST_OPTION_VALUE = "listOptionValue"; //$NON-NLS-1$
	private static final String VALUE = "value"; //$NON-NLS-1$
	private static final String ID = "id"; //$NON-NLS-1$
	private static final String PARENT = "parent"; //$NON-NLS-1$
	private static final String SUPER_CLASS = "superClass"; //$NON-NLS-1$
	private static final String RESOURCE_PATH = "resourcePath"; //$NON-NLS-1$
	private static final String PROJECT_TYPE = "projectType"; //$NON-NLS-1$
	private static final String EPOCROOT_VAR = "${EPOCROOT}"; //$NON-NLS-1$
	private static final String LANGUAGE_PREFIX = "LANGUAGE_"; //$NON-NLS-1$
	private static final String CAPABILITY = "capability"; //$NON-NLS-1$
	private static final String SECURE_ID = "secureid"; //$NON-NLS-1$
	private static final String VENDOR_ID = "vendorid"; //$NON-NLS-1$
	private static final String UID3 = "uid3"; //$NON-NLS-1$
	private static final String UID2 = "uid2"; //$NON-NLS-1$
	private static final String TARGET_TYPE = "targetType"; //$NON-NLS-1$
	private static final String STACK = "stack"; //$NON-NLS-1$
	private static final String TARGET_PATH = "targetPath"; //$NON-NLS-1$
	private static final String SYSTEM_INCLUDE_PATHS = "systemIncludePaths"; //$NON-NLS-1$
	private static final String LANGUAGES = "languages"; //$NON-NLS-1$
	private static final String LIBRARIES = "libraries"; //$NON-NLS-1$
	private static final String LIB_EXTENSION = "lib"; //$NON-NLS-1$
	private static final String MACROS = "macros"; //$NON-NLS-1$
	private static final String MBM = "mbm"; //$NON-NLS-1$
	private static final String MIF = "mif"; //$NON-NLS-1$
	private static final String DEFAULT_TARGET = "untitled.exe"; //$NON-NLS-1$
	private static final String DEFAULT_TARGETTYPE = "exe"; //$NON-NLS-1$
	private static final String[] plaformMacros = {
		"GCC32", "EPOC32", "MARM", "EABI", "GENERIC_MARM",  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		"MARM_ARMV5", "MARM_ARM4", "MARM_ARM4T", "ARMCC", "ARMCC_0",  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		"ARMCC_0_0", "ARM9E", "MARM_ARMI", "MARM_ARMV4", "ARMV6",  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		"ARMV6_ABIV2", "CW32", "TOOLS", "CWTOOLS", "WINS",  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		"WINSCW", "GCCE", "GCCXML",	"SINGLE", "MARM_THUMB" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		};
	private static Set<String> platformMacroSet;

	private static final Pattern VARIABLE_PATTERN = Pattern.compile(".*\\$\\{.+\\}.*"); //$NON-NLS-1$
	private static final String PATTERN_PREFIX = ".*\\."; //$NON-NLS-1$
	private static final String PATTERN_SUFFIX = "(\\..*)?"; //$NON-NLS-1$
	private static final Pattern CAPABILITY_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + CAPABILITY + PATTERN_SUFFIX);
	private static final Pattern SECURE_ID_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + SECURE_ID + PATTERN_SUFFIX);
	private static final Pattern VENDOR_ID_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + VENDOR_ID + PATTERN_SUFFIX);
	private static final Pattern UID3_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + UID3 + PATTERN_SUFFIX);
	private static final Pattern UID2_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + UID2 + PATTERN_SUFFIX);
	private static final Pattern TARGET_TYPE_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + TARGET_TYPE + PATTERN_SUFFIX);
	private static final Pattern STACK_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + STACK + PATTERN_SUFFIX);
	private static final Pattern TARGET_PATH_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + TARGET_PATH + PATTERN_SUFFIX);
	private static final Pattern SYSTEM_INCLUDE_PATHS_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + SYSTEM_INCLUDE_PATHS + PATTERN_SUFFIX);
	private static final Pattern RCOMP_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + "rcomp" + PATTERN_SUFFIX); //$NON-NLS-1$
	private static final Pattern RCOMP_DEFSYM_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + "rcomp\\.general\\.definedSymbols" + PATTERN_SUFFIX); //$NON-NLS-1$
	private static final Pattern LINKER_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + "linker" + PATTERN_SUFFIX); //$NON-NLS-1$
	private static final Pattern LINKER_LIBRARIES_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + "linker\\.libraries\\.libraries" + PATTERN_SUFFIX); //$NON-NLS-1$
	private static final Pattern COMPILER_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + "compiler" + PATTERN_SUFFIX); //$NON-NLS-1$
	private static final Pattern COMPILER_DEFSYM_PATTERN = 
				Pattern.compile(PATTERN_PREFIX + "compiler.definedSymbols" + PATTERN_SUFFIX); //$NON-NLS-1$
	private static final Pattern MBM_PATTERN = 
		Pattern.compile(PATTERN_PREFIX + MBM + PATTERN_SUFFIX);
	private static final Pattern MBM_GENERAL_PATTERN = 
		Pattern.compile(PATTERN_PREFIX + "mbm.general.targetPath" + PATTERN_SUFFIX); //$NON-NLS-1$
	private static final Pattern MIF_PATTERN = 
		Pattern.compile(PATTERN_PREFIX + MIF + PATTERN_SUFFIX);
	private static final Pattern MIF_GENERAL_PATTERN = 
		Pattern.compile(PATTERN_PREFIX + "mif.general.targetPath" + PATTERN_SUFFIX); //$NON-NLS-1$

	static class ValueInfo {
		private final String name;
		private final Pattern pattern;
		private final boolean isList;
		private final IConverter converter;
		public interface IConverter {
			String convert(String value);
		}
		
		public ValueInfo(String name, Pattern pattern, boolean isList, IConverter converter) {
			this.name = name;
			this.pattern = pattern;
			this.isList = isList;
			this.converter = converter;
		}
		public String parentTag() { return OPTION; }
		public String name() { return name; }
		public Pattern pattern() { return pattern; }
		public boolean isList() { return isList; }
		public IConverter converter() { return converter; }
	}
	
	static class ToolInfo extends ValueInfo {
		private final Pattern innerPattern;
		public ToolInfo(String name, Pattern pattern, Pattern innerPattern, boolean isList, IConverter converter) {
			super(name, pattern, isList, converter);
			this.innerPattern = innerPattern;
		}
		public String parentTag() { return TOOL; }
		public String innerTag() { return OPTION; }
		public Pattern innerPattern() { return innerPattern; }
	}
	
	private static ValueInfo[] toolChainValueInfos = {
		new ValueInfo(CAPABILITY, CAPABILITY_PATTERN, false, null), 
		new ValueInfo(SECURE_ID, SECURE_ID_PATTERN, false, null), 
		new ValueInfo(VENDOR_ID, VENDOR_ID_PATTERN, false, null), 
		new ValueInfo(UID3, UID3_PATTERN, false, null), 
		new ValueInfo(UID2, UID2_PATTERN, false, null), 
		new ValueInfo(TARGET_TYPE, TARGET_TYPE_PATTERN, false, new IConverter() {
			public String convert(String value) {
				String[] strings = value.split("\\."); //$NON-NLS-1$
				if (strings.length == 0)
					return value;
				
				return strings[strings.length - 1];
			}
		}), 
		new ValueInfo(STACK, STACK_PATTERN, false, null),
		new ValueInfo(TARGET_PATH, TARGET_PATH_PATTERN, false, null),
		new ValueInfo(SYSTEM_INCLUDE_PATHS, SYSTEM_INCLUDE_PATHS_PATTERN, true, new IConverter() {
			public String convert(String value) {
				String pathString = TextUtils.unquote(value, '"');
				if (pathString.startsWith(EPOCROOT_VAR)) {
					pathString = File.separator + pathString.substring(EPOCROOT_VAR.length()); //$NON-NLS-1$
					return pathString;
				}
				else if (!VARIABLE_PATTERN.matcher(pathString).matches()) // no other variables
					return pathString;
				
				return null;
			}
		}),
		new ToolInfo(LANGUAGES, RCOMP_PATTERN, RCOMP_DEFSYM_PATTERN, true, new IConverter() {
			public String convert(String value) {
				if (value.startsWith(LANGUAGE_PREFIX))
					return value.substring(LANGUAGE_PREFIX.length());
				
				return null;
			}
		}),
		new ToolInfo(LIBRARIES, LINKER_PATTERN, LINKER_LIBRARIES_PATTERN, true, new IConverter() {
			public String convert(String value) {
				String library = TextUtils.unquote(value, '"');
				IPath libPath = new Path(library);
				libPath = new Path(libPath.lastSegment());
				String extension = FileUtils.getSafeFileExtension(libPath);
				if (!extension.equalsIgnoreCase(LIB_EXTENSION)) {
					libPath = libPath.removeFileExtension();
					libPath = libPath.addFileExtension(LIB_EXTENSION);
				}
				
				return libPath.toString();
			}
		}),
		new ToolInfo(MACROS, COMPILER_PATTERN, COMPILER_DEFSYM_PATTERN, true, new IConverter() {
			public String convert(String value) {
				if (value.charAt(0) == '_' || isPlatformMacro(value))
					return null;
				
				return value;
			}
		}),
		new ToolInfo(MBM, MBM_PATTERN, MBM_GENERAL_PATTERN, false, null),
		new ToolInfo(MIF, MIF_PATTERN, MIF_GENERAL_PATTERN, false, null)
	};
	
	private Document document;
	private List<Element> configurations;
	private Element defaultConfiguration; 
	private Map<Element, ISymbianBuildContext> configurationToContextMap;
	private Map<String, String> valueStrings;
	private Map<String, Set<String>> valueSets;
	private Map<String, String> targetPaths;
	
	private SymbianBuildParser() {
	}
	
	public static SymbianBuildParser createParser(IProject project, String defaultConfigKey) {
		SymbianBuildParser parser = new SymbianBuildParser();
		IResource resource = project.findMember(CDTBUILD_FILENAME);
		if (resource == null)
			return null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} 
		catch (ParserConfigurationException e) {
			Check.checkContract(false); // should never occur
		}
		Check.checkState(db != null);
		IFile iFile = (IFile) resource.getAdapter(IFile.class);
		String fileLocation = iFile.getLocation().toString();
		File file = new File(fileLocation);
		try {
			parser.document = db.parse(file);
		} catch (Exception e) {
			ProjectCorePlugin p = ProjectCorePlugin.getDefault();
			String message = 
				MessageFormat.format(Messages.getString("SymbianBuildParser.ParseFileError"), new Object[] { fileLocation }); //$NON-NLS-1$
			Logging.log(p, Logging.newSimpleStatus(p, IStatus.ERROR, message, e));
			return null;
		}
		
		NodeList list = 
			parser.document.getElementsByTagName(CONFIGURATION);
		// create SymbianBuildContext list by parsing info from the configurations
		parser.createSymbianBuildContexts(list);
		parser.getSortedConfigurations();
		
		// default to first config, but try to set from persistent key
		parser.setDefaultConfigurationId(defaultConfigKey);
		
		parser.scanToolChain();
		parser.scanResourceConfigurations();

		return parser;
	}
	
	private void scanResourceConfigurations() {
		targetPaths = new LinkedHashMap<String, String>();
		for (Element configuration : configurations) {
			NodeList resConfigs = configuration.getElementsByTagName(RES_CONF);
			for (int i = 0; i < resConfigs.getLength(); i++) {
				Element resConfig = (Element) resConfigs.item(i);
				String resourcePath = resConfig.getAttribute(RESOURCE_PATH);
				if (resourcePath != null 
						&& resourcePath.length() > 0 &&
									!targetPaths.containsKey(resourcePath)) {
					String targetPath = getTargetPathValue(resConfig);
					if (targetPath != null)
						targetPaths.put(resourcePath, targetPath);
				}
			}
		}
	}
	
	private String getTargetPathValue(Element resourceConfig) {
		String targetPath = null;
		NodeList tools = resourceConfig.getElementsByTagName(TOOL);
		int numTools = tools.getLength();
		for (int i = 0; i < numTools; i++) {
			Element tool = (Element) tools.item(i);
			NodeList options = tool.getElementsByTagName(OPTION);
			int numOptions = options.getLength();
			Check.checkState(numOptions <= 1);
			if (numOptions != 0) {
				Element option = (Element) options.item(0);
				targetPath = option.getAttribute(VALUE);
				break;
			}
		}
		
		return targetPath;
	}
	
	private void scanToolChain() {
		valueStrings = new LinkedHashMap<String, String>();
		valueSets = new LinkedHashMap<String, Set<String>>();
		for (int i = 0; i < toolChainValueInfos.length; i++) {
			boolean found = false;
			for (Element configuration : configurations) {
				Element toolChain = getToolChain(configuration);
				ValueInfo valueInfo = toolChainValueInfos[i];
				NodeList options = toolChain.getElementsByTagName(valueInfo.parentTag());
				found = scanToolChainOptions(options, valueInfo);
				if (found)
					break;
			}
		}
	}

	private boolean scanToolChainOptions(NodeList options, ValueInfo valueInfo) {
		boolean found = false;
		for (int i = 0; i < options.getLength(); i++) {
			Element element = (Element) options.item(i);
			String attribute = element.getAttribute(SUPER_CLASS);
			if (valueInfo.pattern().matcher(attribute).matches()) {
				element = checkToolInfo(element, valueInfo);
				if (element == null)
					continue;
				if (valueInfo.isList()) {
					// if it's a list, get the list option values
					NodeList list = element.getElementsByTagName(LIST_OPTION_VALUE);
					if (list.getLength() > 0) {
						Set<String> set = getValueSet(valueInfo.name());
						for (int j = 0; j < list.getLength(); j++) {
							Element listItem = (Element) list.item(j);
							addValueStringToSet(set, listItem.getAttribute(VALUE), valueInfo);
						}
					}
				}
				else {
					// get the string value
					addValueStringToMap(valueStrings, valueInfo.name(), element.getAttribute(VALUE), valueInfo);
					found = true;
					break;
				}
			}
		}
		return found;
	}

	private static Element checkToolInfo(Element element, ValueInfo valueInfo) {
		if (valueInfo instanceof ToolInfo) {
			ToolInfo toolInfo = (ToolInfo) valueInfo;
			NodeList tools = element.getElementsByTagName(toolInfo.innerTag());
			for (int i = 0; i < tools.getLength(); i++) {
				Element tool = (Element) tools.item(i);
				String attribute = tool.getAttribute(SUPER_CLASS);
				if (toolInfo.innerPattern().matcher(attribute).matches()) {
					if (valueInfo.isList())
						return element; // return the parent tool element to get the list options from
					else {
						// return the option child element
						NodeList options = element.getElementsByTagName(OPTION);
						if (options.getLength() > 0)
							return (Element) options.item(0);
					}
				}
			}
			
			return null;
		}
		else
			return element;
	}
	
	private Set<String> getValueSet(String name) {
		Set<String> set = valueSets.get(name);
		if (set == null) {
			set = new LinkedHashSet<String>();
			valueSets.put(name, set);
		}
		
		return set;
	}
	
	private static void addValueStringToSet(Set<String> set, String value, ValueInfo valueInfo) {
		if (valueInfo.converter() != null) {
			value = valueInfo.converter().convert(value);
		}
		if (value != null)
			set.add(value);
	}
	
	private static void addValueStringToMap(Map<String, String> map, String key, String value, ValueInfo valueInfo) {
		if (valueInfo.converter() != null) {
			value = valueInfo.converter().convert(value);
		}
		if (value != null)
			map.put(key, value);
	}

	private void getSortedConfigurations() {
		// sort the emulator platforms down, so that device values will have higher priority
		configurations = new ArrayList<Element>();
		configurations.addAll(configurationToContextMap.keySet());
		Collections.sort(configurations, new Comparator<Element>() {
			public int compare(Element o1, Element o2) {
				ISymbianBuildContext symbianBuildContext1 = configurationToContextMap.get(o1);
				if (symbianBuildContext1 == null)
					return 0;
				String platform1 = symbianBuildContext1.getPlatformString();
				ISymbianBuildContext symbianBuildContext2 = configurationToContextMap.get(o2);
				if (symbianBuildContext2 == null)
					return 0;
				String platform2 = symbianBuildContext2.getPlatformString();
				if (platform1.equals(ISymbianBuildContext.EMULATOR_PLATFORM))
					return 1;
				else if (platform2.equals(ISymbianBuildContext.EMULATOR_PLATFORM))
					return -1;
				return 0;
			}
		});
	}

	private Element getToolChain(Element configuration) {
		NodeList toolChains = configuration.getElementsByTagName(TOOL_CHAIN);
		// tool chain should be unique
		Check.checkState(toolChains.getLength() == 1);
		return (Element) toolChains.item(0);
	}
	
	private void setDefaultConfigurationId(String id) {
		if (id == null) {
			// No default config -- likely because it was checked out from CVS 
			// and the default references a nonexistent SDK.  Just pick the first one.
			if (configurations.size() > 0) {
				defaultConfiguration = configurations.get(0);
			}
			return;
		}
		
		for (Element element : configurations) {
			String attribute = element.getAttribute(ID);
			if (id.equals(attribute)) {
				defaultConfiguration = element;
				break;
			}
		}
		
		if (defaultConfiguration == null) {
			// no matches, so pick the first one
			if (configurations.size() > 0) {
				defaultConfiguration = configurations.get(0);
			}
		}
	}
	
	private ISymbianBuildContext parseConfigurationId(String configurationId) {
		// E.g., S60_2nd_FP3_com.symbian.cdt.build.projectType.pdll_thumb.urel_S60_2nd_FP3
		// ...projectType.<targetType_><platform>.<target>_<SDK>
	
		int curIndex = configurationId.indexOf(PROJECT_TYPE);
		if (curIndex < 0) {
			return null;
		}
		configurationId = configurationId.substring(curIndex + PROJECT_TYPE.length() + 1);
		curIndex = configurationId.indexOf('_') + 1;
		if (curIndex < 0) {
			return null;
		}
		configurationId = configurationId.substring(curIndex);
		String[] strings = configurationId.split("\\."); //$NON-NLS-1$
		if (strings.length != 2) {
			return null;
		}
		String platform = strings[0].toUpperCase();
		curIndex = strings[1].indexOf('_');
		if (curIndex < 0) {
			return null;
		}
		String target = strings[1].substring(0, curIndex).toUpperCase();
		String sdk = strings[1].substring(target.length() + 1);
		ISymbianSDK symbianSDK = SDKCorePlugin.getSDKManager().getSDK(sdk, true);
		if (symbianSDK == null) {
			return null;
		}
		return new SymbianBuildContext(symbianSDK, platform, target);
	}

	private void createSymbianBuildContexts(NodeList list) {
		if (configurationToContextMap == null)
			configurationToContextMap = new LinkedHashMap<Element, ISymbianBuildContext>();
	
		for (int i = 0; i < list.getLength(); i++) {
			Element configuration = (Element) list.item(i);
			// parent is id but without unique numerical value which we don't need
			String configurationId = configuration.getAttribute(PARENT);
			ISymbianBuildContext context = parseConfigurationId(configurationId);
			configurationToContextMap.put(configuration, context);
		}
	}

	private static boolean isPlatformMacro(String s) {
		if (platformMacroSet == null) {
			platformMacroSet = new HashSet<String>();
			for (int i = 0; i < plaformMacros.length; i++) {
				platformMacroSet.add(plaformMacros[i]);
			}
		}
		return platformMacroSet.contains(s);
	}

	public String getTarget() {
		if (defaultConfiguration == null)
			return DEFAULT_TARGET;
		
		String target = defaultConfiguration.getAttribute(ARTIFACT_NAME);
		target += '.';
		target += defaultConfiguration.getAttribute(ARTIFACT_EXTENSION);
		
		return target;
	}
	
	public String getTargetType() {
		String targetType = valueStrings.get(TARGET_TYPE);
		if (targetType == null) { // turenki sometimes left this out, so best approximation
			if (defaultConfiguration != null)
				targetType = defaultConfiguration.getAttribute(ARTIFACT_EXTENSION);
			else
				targetType = DEFAULT_TARGETTYPE;
		}
		
		return targetType;
	}
	
	public String getUID2() {
		String uid2 = valueStrings.get(UID2);
		
		return uid2;
	}
	
	public String getUID3() {
		String uid3 = valueStrings.get(UID3);
		
		return uid3;
	}
	
	public String getVendorId() {
		String vendorId = valueStrings.get(VENDOR_ID);
		
		return vendorId;
	}
	
	public String getSecureId() {
		String secureId = valueStrings.get(SECURE_ID);
		
		return secureId;
	}
	
	public String getCapability() {
		String capability = valueStrings.get(CAPABILITY);
		
		return capability;
	}
	
	public String getStack() {
		String stack = valueStrings.get(STACK);
		
		return stack;
	}
	
	public String getTargetPath() {
		String targetPath = valueStrings.get(TARGET_PATH);
		
		return targetPath;
	}
	
	public String getMBMTargetPath() {
		String mbmTargetPath = valueStrings.get(MBM);
		if (mbmTargetPath == null)
			mbmTargetPath = ""; //$NON-NLS-1$
		
		return mbmTargetPath;
	}
	
	public String getMIFTargetPath() {
		String mifTargetPath = valueStrings.get(MIF);
		if (mifTargetPath == null)
			mifTargetPath = RESOURCE_APPS;
		return mifTargetPath;
	}
	
	public Collection<ISymbianBuildContext> getBuildContexts() {
		// create result, but put the default configuration first so that it will be made default
		List<ISymbianBuildContext> buildContextList = new ArrayList<ISymbianBuildContext>();

		if (configurationToContextMap == null)
			return buildContextList;
		
		for (Element config : configurationToContextMap.keySet()) {
			ISymbianBuildContext symbianBuildContext = configurationToContextMap.get(config);
			if (symbianBuildContext == null)
				continue;
			
			if (config.equals(defaultConfiguration))
				buildContextList.add(0, symbianBuildContext);
			else
				buildContextList.add(symbianBuildContext);
		}
		
		return buildContextList;
	}
	
	public ISymbianBuildContext getDefaultBuildContext() {
		if (defaultConfiguration == null)
			return null;
		return parseConfigurationId(defaultConfiguration.getAttribute(PARENT));
	}
	
	public Collection<String> getSystemIncludePaths() {
		return valueSets.get(SYSTEM_INCLUDE_PATHS);
	}

	public Collection<String> getLanguages() {
		return valueSets.get(LANGUAGES);
	}
	
	public Collection<String> getLibraries() {
		return valueSets.get(LIBRARIES);
	}
	
	public Collection<String> getMacros() {
		return valueSets.get(MACROS);
	}
	
	public Map<String, String> getTargetPathMappings() {
		return targetPaths;
	}
}
