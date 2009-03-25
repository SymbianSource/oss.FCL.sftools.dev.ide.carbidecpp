/**
 * Copyright (c)2007, 2008 Nokia Corporation. All rights reserved.
 */

package com.nokia.carbide.cpp.internal.codescanner.kb;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.Messages;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigSettings;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigFactory;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomruleType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.KeywordType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CallType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ClassType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CommentType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FiletypeType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.InheritanceType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataBodyType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataCategoryType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywordType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywordsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataMetadataType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPlatformType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataRefType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LocalType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MacroType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MemberType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MessagesType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MethodType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParameterType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SolutionType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SymptomType;
import com.nokia.carbide.cpp.internal.codescanner.xml.CSKbdataXMLLoader;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

/**
 * A class for handling CodeScanner knowledge base rules.
 *
 */
public class CSKbManager {

	public static final String KBASE_EXTENSION_ID = CSPlugin.PLUGIN_ID + ".rules"; //$NON-NLS-1$
	public static final String LOCATION = "location"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_BASECLASS = "baseclass"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_CALL = "call"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_CLASS = "class"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_COMMENT = "comment"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_GENERIC = "generic"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_LOCAL = "local"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_MACRO = "macro"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_MEMBER = "member"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_METHOD = "method"; //$NON-NLS-1$
	public static final String KEYWORDTYPE_PARAMETER = "parameter"; //$NON-NLS-1$

	// private members
	private static final String CATEGORY_ERROR = "error"; //$NON-NLS-1$
	private static final String CATEGORY_WARNING = "warning"; //$NON-NLS-1$
	private static final String CATEGORY_INFORMATION = "information"; //$NON-NLS-1$
	private static final String SEVERITY_HIGH = "high"; //$NON-NLS-1$
	private static final String SEVERITY_MEDIUM = "medium"; //$NON-NLS-1$
	private static final String SEVERITY_LOW = "low"; //$NON-NLS-1$
	private static final String KBDATA_FILE_FILTER = "*.xml"; //$NON-NLS-1$

	private ArrayList<CSKbRule> rules;
	private ArrayList<CSKbPluginInfo> pluginInfoList;

	/**
	 * Constructor.
	 */
	public CSKbManager() {
		rules = new ArrayList<CSKbRule>();
		pluginInfoList = new ArrayList<CSKbPluginInfo>();
	}

	/**
	 * Add a knowledge base rule.
	 * @param rule - knowledge base rule to be added.
	 */
	public void addRule(CSKbRule rule) {
		rules.add(rule);
	}

	/**
	 * Add a collection of knowledge base rules.
	 * @param rules - knowledge base rules to be added.
	 */
	public void addRules(ArrayList<CSKbRule> rules) {
		this.rules.addAll(rules);
	}

	/**
	 * Clear all knowledge base rules.
	 */
	public void clearRules() {
		rules.clear();
	}

	/**
	 * Retrieve all knowledge base rules contributed by a plugin.
	 * @param pluginID - ID of contributing plugin 
	 * @return all knowledge base rules contributed by a plugin
	 */
	public ArrayList<CSKbRule> getPluginRules(String pluginID) {
		if (rules.isEmpty()) {
			return null;
		}

		ArrayList<CSKbRule> pluginRules = new ArrayList<CSKbRule>();
		Iterator<CSKbRule> iterator = rules.iterator();
		while (iterator.hasNext()) {
			CSKbRule rule = iterator.next();
			if (rule.getPluginId().equals(pluginID)) {
				pluginRules.add(rule);
			}
		}
		return pluginRules;
	}

	/**
	 * Find a knowledge base rule by its name.
	 * @param name - name of rule
	 * @return knowledge base rule with matching name
	 */
	public CSKbRule getRule(String name) {
		Iterator<CSKbRule> iterator = rules.iterator();
		while (iterator.hasNext()) {
			CSKbRule rule = iterator.next();
			if (rule.getName().equals(name)) {
				return rule;
			}
		}
		return null;
	}
	
	/**
	 * Retrieve all knowledge base rules.
	 * @return all knowledge base rules
	 */
	public ArrayList<CSKbRule> getRules() {
		if (rules.isEmpty()) {
			return null;
		}
		else {
			return rules;
		}
	}

	/**
	 * Remove a knowledge base rule.
	 * @param name - name of the rule to be removed.
	 */
	public void removeRule(String name) {
		CSKbRule found = null;
		Iterator<CSKbRule> iterator = rules.iterator();
		while (iterator.hasNext()) {
			CSKbRule current = iterator.next();
			if (current.getName().equals(name)) {
				found = current;
			}
		}

		if (found != null) {
			rules.remove(found);
		}
	}

	/**
	 * Remove a collection of knowledge base rules.
	 * @param rules - knowledge base rules to be removed.
	 */
	public void removeRules(ArrayList<CSKbRule> rules) {
		this.rules.removeAll(rules);
	}

	/**
	 * Remove all knowledge base rules contributed by a plugin.
	 * @param pluginID - ID of contributing plugin 
	 */
	public void removeAllPluginRules(String pluginID) {
		ArrayList<CSKbRule> pluginRules = getPluginRules(pluginID);
		rules.removeAll(pluginRules);
	}

	/**
	 * Copy a collection of knowledge base rules.
	 * @param rules - knowledge base rules to be copied.
	 */
	@SuppressWarnings("unchecked")
	public void setRules(ArrayList<CSKbRule> rules) {
		Object object = rules.clone();
		if (object instanceof ArrayList) {
			this.rules = (ArrayList<CSKbRule>)object;
		}
	}

	/**
	 * Add knowledge base rules to CodeScanner configuration settings.
	 * @param configSettings - configuration settings to be updated.
	 */
	public void addKBaseRulesToConfigSettings(IProject project, CSConfigSettings configSettings) {
		readRulesFromPlugins();
		filterRulesforSDKs(getSDKVersions(project));

		if (rules.size() > 0) {
			EList<CustomruleType> customRuleList = configSettings.getCustomRules().getCustomrule();
			customRuleList.clear();
			for (Iterator<CSKbRule> ruleIterator = rules.iterator(); ruleIterator.hasNext();) {
				CSKbRule kbRule = ruleIterator.next();
				CustomruleType customRule = CSConfigFactory.eINSTANCE.createCustomruleType();
				customRule.setDescription(kbRule.getDescription());
				customRule.setLink(kbRule.getLink());
				customRule.setName(kbRule.getName());
				customRule.setSeverity(kbRule.getSeverity());
				customRule.setTitle(kbRule.getTitle());

				EList<KeywordType> keywordList = customRule.getKeyword();
				keywordList.clear();
				ArrayList<CSKbRuleKeyword> keywords = kbRule.getKeywords();
				for (Iterator<CSKbRuleKeyword> keywordIterator = keywords.iterator(); keywordIterator.hasNext();) {
					CSKbRuleKeyword kbaseKeyword = keywordIterator.next();
					KeywordType customKeyword = CSConfigFactory.eINSTANCE.createKeywordType();
					customKeyword.setType(kbaseKeyword.getType());
					customKeyword.setValue(kbaseKeyword.getContent());
					keywordList.add(customKeyword);
				}

				EList<String> filetypeList = customRule.getFiletype();
				filetypeList.clear();
				filetypeList.addAll(kbRule.getFileTypes());
				
				customRuleList.add(customRule);
			}
		}
	}

	/**
	 * Retrieve knowledge base rules from contributing plugins using the knowledge base extension.
	 */
	@SuppressWarnings("unchecked")
	public void readRulesFromPlugins() {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = extensionRegistry.getExtensionPoint(KBASE_EXTENSION_ID);
		IExtension[] extensions = extensionPoint.getExtensions();

		for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			String pluginID = extension.getNamespaceIdentifier();
			Bundle bundle = Platform.getBundle(pluginID);
			CSKbPluginInfo kbPluginInfo = new CSKbPluginInfo(pluginID, getBundleVersion(bundle));
			pluginInfoList.add(kbPluginInfo);

			IConfigurationElement[] elements = extension.getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				String location = elements[j].getAttribute(LOCATION);
				Enumeration e = bundle.findEntries(location, KBDATA_FILE_FILTER, false);
				while (e!= null && e.hasMoreElements()) {
					URL url = (URL)e.nextElement();
					if (url != null) {
						KbdataType kbdata = loadKbdata(url, pluginID);
						if (kbdata != null) {
							CSKbRule rule = createKbRule(kbdata);
							rule.setPluginId(pluginID);
							addRule(rule);
						}
			        }
				}
			}
		}
	}

	/**
	 * Filter knowledge base rules for a list of SDK versions.
	 * @param sdkVersions - list of SDK versions.
	 */
	private void filterRulesforSDKs(ArrayList<Version> sdkVersions) {
		ArrayList<CSKbRule> filteredRules = new ArrayList<CSKbRule>();
		for (Iterator<CSKbRule> ruleIterator = rules.iterator(); ruleIterator.hasNext();) {
			CSKbRule kbRule = ruleIterator.next();
			ArrayList<Version> platforms = kbRule.getPlatforms();
			boolean remove = true;
			for (Iterator<Version> pfIterator = platforms.iterator(); pfIterator.hasNext();) {
				Version platform = pfIterator.next();
				if (sdkVersions.contains(platform)) {
					remove = false;
				}
			}
			if (remove) {
				filteredRules.add(kbRule);
			}
		}
		removeRules(filteredRules);
	}

	/**
	 * Create a knowledge base rule using knowledge base data.
	 * @param kbdata - knowledge base data
	 * @return a knowledge base rule
	 */
	private CSKbRule createKbRule(KbdataType kbdata) {
		CSKbRule rule = new CSKbRule();
		rule.setName(kbdata.getId());
		KbdataBodyType kbdataBody = kbdata.getKbdataBody();
		EList<KbdataMetadataType> metaDataList = kbdataBody.getKbdataMetadata();
		for (Iterator<KbdataMetadataType> iterator1 = metaDataList.iterator(); iterator1.hasNext();) {
			KbdataMetadataType metaData = iterator1.next();
			KbdataKeywordsType keywordObject = metaData.getKbdataKeywords();
			ArrayList<CSKbRuleKeyword> keywords = readKbRuleKeywords(keywordObject);
			ArrayList<String> filetypes = readFileTypes(keywordObject);
			ArrayList<Version> platforms = readKbdataPlatforms(keywordObject);
			rule.setFileTypes(filetypes);
			rule.setKeywords(keywords);
			rule.setPlatforms(platforms);
		}
		EList<MessagesType> messageList = kbdataBody.getMessages();
		for (Iterator<MessagesType> iterator2 = messageList.iterator(); iterator2.hasNext();) {
			MessagesType message = iterator2.next();
			String severity = readSeverity(message);
			String title = readTitle(message);
			String description = readDescription(message);
			String link = readLink(message);
			rule.setSeverity(severity);
			rule.setTitle(title);
			rule.setDescription(description);
			rule.setLink(link);
		}
		return rule;
	}

	
	
	/**
	 * Retrieve bundle version from a given bundle.
	 * @param bundle - target bundle
	 * @return bundle version
	 */
	@SuppressWarnings("unchecked")
	private String getBundleVersion(Bundle bundle) {
		String version = "0.0"; //$NON-NLS-1$
		Dictionary bundleHeaders = bundle.getHeaders();
		if (bundleHeaders != null) {
			version = (String) bundleHeaders.get("Bundle-Version");
		}
		return version;
	}

	/**
	 * Retrieve SDK versions from the build configurations of a project.
	 * @param project - project in question
	 * @return list of SDK versions
	 */
	private ArrayList<Version> getSDKVersions(IProject project) {
		ArrayList<Version> sdkVersions = new ArrayList<Version>();
		ICarbideProjectInfo projectInfo = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		if (projectInfo != null) {
			List<ICarbideBuildConfiguration> buildConfigList = projectInfo.getBuildConfigurations();
			for (Iterator<ICarbideBuildConfiguration> iterator = buildConfigList.iterator(); iterator.hasNext();) {
				ICarbideBuildConfiguration buildConfig = iterator.next();
				ISymbianSDK sdk = buildConfig.getSDK();
				Version version = sdk.getSDKVersion();
				sdkVersions.add(version);
			}
		}
		
		return sdkVersions;
	}

	/**
	 * Load data from a knowledge base data file.
	 * @param url - url of knowledge base data file
	 * @param pluginID - unique ID of the plugin containing the knowledge base data file
	 * @return knowledge base data
	 */
	@SuppressWarnings("static-access")
	private KbdataType loadKbdata(URL url, String pluginID) {
		KbdataType kbdata = null;
		try {
			kbdata = CSKbdataXMLLoader.loadKbdata(url);
		}
		catch (Exception e) {
			String format = Messages.getString("KbManager.LoadFailMessage");
			String message = MessageFormat.format(format, url.getFile(), pluginID);
			IStatus status = new Status(Status.ERROR, CSPlugin.getDefault().PLUGIN_ID, message, e);
			CSPlugin.getDefault().getLog().log(status);
		}
		return kbdata;
	}

	/**
	 * Read the knowledge base keywords.
	 * @param keywordObject - knowledge base keyword object
	 * @return a list of knowledge base keywords
	 */
	private ArrayList<CSKbRuleKeyword> readKbRuleKeywords(KbdataKeywordsType keywordObject) {
		ArrayList<CSKbRuleKeyword> keywordList = new ArrayList<CSKbRuleKeyword>();
		CallType call = keywordObject.getCall();
		if (call != null) {
			Object object =  call.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_CALL));
			}
		}
		ClassType class_ = keywordObject.getClass_();
		if (class_ != null) {
			Object object =  class_.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_CLASS));
			}
		}
		CommentType comment = keywordObject.getComment();
		if (comment != null) {
			Object object =  comment.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_COMMENT));
			}
		}
		InheritanceType inheritance = keywordObject.getInheritance();
		if (inheritance != null) {
			Object object =  inheritance.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_BASECLASS));
			}
		}
		KbdataKeywordType kbdataKeyword = keywordObject.getKbdataKeyword();
		if (kbdataKeyword != null) {
			Object object =  kbdataKeyword.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_GENERIC));
			}
		}
		LocalType local = keywordObject.getLocal();
		if (local != null) {
			Object object =  local.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_LOCAL));
			}
		}
		MacroType macro = keywordObject.getMacro();
		if (macro != null) {
			Object object =  macro.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_MACRO));
			}
		}
		MemberType member = keywordObject.getMember();
		if (member != null) {
			Object object =  member.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_MEMBER));
			}
		}
		MethodType method = keywordObject.getMethod();
		if (method != null) {
			Object object =  method.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_METHOD));
			}
		}
		ParameterType parameter = keywordObject.getParameter();
		if (parameter != null) {
			Object object =  parameter.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				keywordList.add(new CSKbRuleKeyword((String)object, KEYWORDTYPE_PARAMETER));
			}
		}
		return keywordList;
	}

	/**
	 * Read the platforms associated with a knowledge base rule.
	 * @param keywordObject - knowledge base keyword object
	 * @return a list of platforms
	 */
	private ArrayList<Version> readKbdataPlatforms(KbdataKeywordsType keywordObject) {
		EList<KbdataPlatformType> platforms = keywordObject.getKbdataPlatform();
		ArrayList<Version> platformList = null;
		if (platforms != null) {
			platformList = new ArrayList<Version>();
			for (Iterator<KbdataPlatformType> iterator = platforms.iterator(); iterator.hasNext();) {
				KbdataPlatformType platform = iterator.next();
				Object object = platform.getMixed().getValue(0);
				if (object != null && object instanceof String) {
					try {
						Version version = new Version((String)object);
						platformList.add(version);
					}
					catch (IllegalArgumentException e) {
						platformList.add(new Version("0.0.0"));
					}
				}
			}
			
		}
		return platformList;
	}

	/**
	 * Read the file types associated with a knowledge base.
	 * @param keywordObject - knowledge base keyword object
	 * @return a list of file types, or <code>null</code> if none
	 */
	private ArrayList<String> readFileTypes(KbdataKeywordsType keywordObject) {
		EList<FiletypeType> fileTypes = keywordObject.getFiletype();
		ArrayList<String> fileTypeList = null;
		if (fileTypes != null) {
			fileTypeList = new ArrayList<String>();
			for (Iterator<FiletypeType> iterator = fileTypes.iterator(); iterator.hasNext();) {
				FiletypeType fileType = iterator.next();
				Object object = fileType.getMixed().getValue(0);
				if (object != null && object instanceof String) {
					fileTypeList.add((String)object);
				}
			}
		}
		return fileTypeList;
	}

	/**
	 * Read the severity level of a knowledge base rule.
	 * @param message - knowledge base message object
	 * @return severity level
	 */
	private String readSeverity(MessagesType message) {
		String severity = SEVERITY_LOW;
		KbdataCategoryType category = message.getKbdataCategory();
		if (category != null) {
			Object object = category.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				String categoryString = (String)object;
				if (categoryString.toLowerCase().equals(CATEGORY_ERROR)) {
					severity = SEVERITY_HIGH;
				}
				else if (categoryString.toLowerCase().equals(CATEGORY_WARNING)) {
					severity = SEVERITY_MEDIUM;
				}
				else if (categoryString.toLowerCase().equals(CATEGORY_INFORMATION)) {
					severity = SEVERITY_LOW;
				}
			}
		}
		return severity;
	}

	/**
	 * Read the message title of a knowledge base rule.
	 * @param message - knowledge base message object
	 * @return message title
	 */
	private String readTitle(MessagesType message) {
		String title = " ";
		SymptomType symptom = message.getSymptom();
		if (symptom != null) {
			Object object = symptom.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				title = (String)object;
			}
		}
		return title;
	}

	/**
	 * Read the description of a knowledge base rule.
	 * @param message - knowledge base message object
	 * @return description
	 */
	private String readDescription(MessagesType message) {
		String description = " ";
		SolutionType solution = message.getSolution();
		if (solution != null) {
			Object object = solution.getMixed().getValue(0);
			if (object != null && object instanceof String) {
				description = (String)object;
			}
		}
		return description;
	}

	/**
	 * Read the external link of a knowledge base rule.
	 * @param message - knowledge base message object
	 * @return external link, or <code>null</code> if none
	 */
	private String readLink(MessagesType message) {
		String link = null;
		KbdataRefType reference = message.getKbdataRef();
		if (reference != null) {
			Object object = reference.getHref();
			if (object != null && object instanceof String) {
				link = (String)object;
			}
		}
		return link;
	}
}
