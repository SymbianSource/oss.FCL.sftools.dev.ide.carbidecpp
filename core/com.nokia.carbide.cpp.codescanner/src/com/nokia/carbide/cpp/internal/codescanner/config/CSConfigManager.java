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
* Contributors:
*
* Description: 
*
*/

package com.nokia.carbide.cpp.internal.codescanner.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.ui.CSPreferenceConstants;
import com.nokia.cpp.internal.api.utils.core.HostOS;

/**
 * A class for handling global and project specific Codescanner configuration settings.
 */
public class CSConfigManager implements IResourceChangeListener {

	// name of CodeScanner configuration file generated by this plugin
	public static final String CS_CONFIG_SETTINGS_FILE = "carbide_cs_config.xml";

	// name of CodeScanner commandline tool
	public static final String CS_CMDLINE_TOOL = "codescanner" + HostOS.EXE_EXT;

	// private members
	private static CSConfigSettings defaultConfig;
	private String codescannerDir = null;
	private String resultsDir = null;
	private String outputFormatArgument = null;
	private List<String> inputArguments = null;
	private boolean htmlResults = false;
	private boolean xmlResults = false;
	private Map<IProject, CSProjectSettings> settingsMap;

	/**
	 * Create an instance of CSConfigManager.
	 */
	public CSConfigManager() {
		super();
		defaultConfig = new CSConfigSettings();
		defaultConfig.loadDefaultConfig();
		settingsMap = new HashMap<IProject, CSProjectSettings>();
	}
	
	/**
	 * Get the default CodeScanner configuration settings.
	 * @return default CodeScanner configuration settings
	 */
	public CSConfigSettings getDefaultConfig() {
		if (defaultConfig == null) {
			defaultConfig = new CSConfigSettings();
			defaultConfig.loadDefaultConfig();
		}
		return defaultConfig;
	}

	/**
	 * Find out whether the CodeScanner commandline tool exists in the specified
	 * CodeScanner directory. If it does, return its full path, otherwise return null.
	 * @return full path of the CodeScanner commandline tool if it exists
	 */
	public String getCSCommandLineTool() {
        String csCmdLineTool = CS_CMDLINE_TOOL;
        if (codescannerDir != null && codescannerDir.length() > 0) {
        	csCmdLineTool = codescannerDir + csCmdLineTool;
		}
        IPath fullPath = new Path(csCmdLineTool);
        if (fullPath.toFile().exists()) {
        	return csCmdLineTool;
        }
        else {
        	return null;
        }
	}

	/**
	 * Return the directory for scanning results.
	 * @return the directory for scanning results
	 */
	public String getCSResultsDirectory() {
		return resultsDir;
	}

	/**
	 * Are we generating html reports for scanning results?
	 * @return true if generating html reports
	 */
	public boolean generateHtmlResults() {
		return htmlResults;
	}

	/**
	 * Are we generating xml reports for scanning results?
	 * @return true if generating xml reports
	 */
	public boolean generateXmlResults() {
		return xmlResults;
	}

	/**
	 * Create CodeScanner settings for a given project and store it.
	 * @param project - the project in question
	 * @return the project specific CodeScanner settings
	 */
	public CSProjectSettings createProjectSettings(IProject project) {
		synchronized (settingsMap) {
			assert(settingsMap.get(project) == null);
			CSProjectSettings settings = new CSProjectSettings(project);
			settingsMap.put(project, settings);
			return settings;
		}
	}
	
	/**
	 * Get the CodeScanner settings for a given project.
	 * @param project - the project in question
	 * @return the project specific CodeScanner settings
	 */
	public CSProjectSettings getProjectSettings(IProject project) {
		synchronized (settingsMap) {
			CSProjectSettings settings = settingsMap.get(project);
			if (settings == null) {
				settings = createProjectSettings(project);
			}
			return settings;
		}
	}

	/**
	 * Handle any resource change event
	 * @param event - the resource change event in question
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		// remove projects from map when they are closed or deleted
		if (event.getType() == IResourceChangeEvent.PRE_CLOSE || event.getType() == IResourceChangeEvent.PRE_DELETE) {
			settingsMap.remove(event.getResource());
		}
	}

	/**
	 * Load either project specific or global CodeScanner configuration settings.
	 * @param project - project associated with currently selected resource
	 * @param configFilePath - path of CodeScanner configuration file
	 * @return true if settings has been successfully loaded
	 */
	public boolean loadConfigSettings(IProject project) {
		boolean success = false;
		if (project != null) {
			success = loadProjectConfigSettings(project);
		}
		if (!success) {
			success = loadGlobalConfigSettings();
		}
		clearArguments();
		return success;
	}

	/**
	 * Load project specific CodeScanner settings.
	 * @param project - project associated with currently selected resource
	 * @param configFilePath - path of CodeScanner configuration file
	 * @return true if settings has been successfully loaded
	 */
	public boolean loadProjectConfigSettings(IProject project) {
		if (project == null) {
			return false;
		}
		boolean success = false;
		CSProjectSettings projectSettings = getProjectSettings(project);
		IDialogSettings cSettings = projectSettings.getDialogSettings();
		IDialogSettings pageSettings = cSettings.getSection(CSPreferenceConstants.PROPERTY_SETTINGS_ID);
		if (pageSettings == null) {
			success = false;
		}
		else {
			success = pageSettings.getBoolean(CSPreferenceConstants.PROJ_SETTINGS);
			if (success) {
				codescannerDir = pageSettings.get(CSPreferenceConstants.CODESCANNER_FOLDER);
				htmlResults = pageSettings.getBoolean(CSPreferenceConstants.HTML_RESULTS);
				xmlResults = pageSettings.getBoolean(CSPreferenceConstants.XML_RESULTS);
				resultsDir = pageSettings.get(CSPreferenceConstants.RESULTS_FOLDER);
			}
			else {
				codescannerDir = "";
				htmlResults = false;
				xmlResults = false;
				resultsDir = "";
			}
		}
		return success;
	}
	
	/**
	 * Load global CodeScanner settings.
	 * @param project - project associated with currently selected resource
	 * @param configFilePath - path of CodeScanner configuration file
	 * @return true if settings has been successfully loaded
	 */
	public boolean loadGlobalConfigSettings() {
		IPreferenceStore store = CSPlugin.getCSPrefsStore();
		codescannerDir = store.getString(CSPreferenceConstants.CODESCANNER_FOLDER);
		htmlResults = store.getBoolean(CSPreferenceConstants.HTML_RESULTS);
		xmlResults = store.getBoolean(CSPreferenceConstants.XML_RESULTS);
		resultsDir = store.getString(CSPreferenceConstants.RESULTS_FOLDER);
		return true;
	}
	
	/**
	 * Create a CodeScanner configuration file from either project specific or 
	 * global CodeScanner configuration settings.
	 * @param project - project associated with currently selected resource
	 * @param configFilePath - path of CodeScanner configuration file
	 * @return true if configuration file has been successfully created
	 */
	public File createConfigFile(IProject project, String configFilePath) {
		File configFile = null;
		if (project != null) {
			configFile = createProjectConfigFile(project, configFilePath);
		}
		if (configFile == null) {
			configFile = createGlobalConfigFile(project, configFilePath);
		}
		return configFile;
	}
	
	/**
	 * Create a CodeScanner configuration file from project specific CodeScanner settings.
	 * @param configFilePath - path of CodeScanner configuration file
	 * @param settings - project specific CodeScanner settings
	 * @return CodeScanner configuration file
	 */
	public File createProjectConfigFile(IProject project, String configFilePath) {
		if (configFilePath.length() == 0) {
			return null;
		}

		// load project specific settings
		CSProjectSettings projectSettings = getProjectSettings(project);
		IDialogSettings cSettings = projectSettings.getDialogSettings();
		IDialogSettings pageSettings = cSettings.getSection(CSPreferenceConstants.PROPERTY_SETTINGS_ID);
		if (pageSettings == null) {
			return null;
		}
		if (!pageSettings.getBoolean(CSPreferenceConstants.PROJ_SETTINGS)) {
			return null;
		}

		// create and initialize a configuration file
		File configFile = new File(configFilePath);
		if (configFile.exists()) {
			configFile.delete();
		}
		CSConfigSettings configSettings = new CSConfigSettings();
		configSettings.loadDefaultConfig();

		// retrieve various stored values
		String fileFilterString = pageSettings.get(CSPreferenceConstants.FILE_FILTERS);
		if (fileFilterString != null) {
			StringTokenizer fileFilters = new StringTokenizer(fileFilterString, CSPreferenceConstants.DELIMETER);
			EList<String> fileFilterList = configSettings.getSourceFilters().getExclude();
			fileFilterList.clear();
			while (fileFilters.hasMoreTokens()) {
				String fileFilter = fileFilters.nextToken();
				fileFilterList.add(fileFilter);
			}
		}

		String scriptsString = pageSettings.get(CSPreferenceConstants.RULE_SCRIPTS);
		String categoriesString = pageSettings.get(CSPreferenceConstants.RULE_CATEGORIES);
		String severitiesString = pageSettings.get(CSPreferenceConstants.RULE_SEVERITIES);
		String enabledString = pageSettings.get(CSPreferenceConstants.RULES_ENABLED);
		if ((scriptsString != null) && 
			(categoriesString != null) && 
			(severitiesString != null) && 
			(enabledString != null)) {
			StringTokenizer scriptTokens = new StringTokenizer(scriptsString, CSPreferenceConstants.DELIMETER);
			StringTokenizer categoryTokens = new StringTokenizer(categoriesString, CSPreferenceConstants.DELIMETER);
			StringTokenizer severityTokens = new StringTokenizer(severitiesString, CSPreferenceConstants.DELIMETER);
			StringTokenizer enabledTokens = new StringTokenizer(enabledString, CSPreferenceConstants.DELIMETER);
			while (scriptTokens.hasMoreTokens() &&
				   categoryTokens.hasMoreTokens() &&
				   severityTokens.hasMoreTokens() &&
				   enabledTokens.hasMoreTokens()) {
				String scriptString = "script_" + scriptTokens.nextToken();
				String categoryString = categoryTokens.nextToken();
				String severityString = severityTokens.nextToken();
				int value = Integer.valueOf(enabledTokens.nextToken());
				boolean enabled = (value != 0);
				CSScript script = CSScript.toScript(scriptString);
				if (!script.equals(CSScript.script_unknown)) {
					configSettings.setScriptCategory(script, categoryString);
					configSettings.setScriptSeverity(script, severityString);
					configSettings.setScriptEnabled(script, enabled);
				}
			}
		}

		configSettings.setScriptCClassIgnore(pageSettings.get(CSPreferenceConstants.CCLASSIGNORE));
		configSettings.setScriptForbiddenWords(pageSettings.get(CSPreferenceConstants.FORBIDEENWORDS));
		String icons = pageSettings.get(CSPreferenceConstants.ICONS);
		if (icons.length() == 0) {
			icons = null;
		}
		configSettings.setScriptIcons(icons);
		configSettings.setScriptLFunctionIgnore(pageSettings.get(CSPreferenceConstants.LFUNCTIONIGNORE));
		configSettings.setScriptLongLinesLength(pageSettings.getInt(CSPreferenceConstants.LONGLINES_LENGTH));
		configSettings.setScriptOpenIgnore(pageSettings.get(CSPreferenceConstants.OPENIGNORE));
		configSettings.setScriptWorryingComments(pageSettings.get(CSPreferenceConstants.WORRYINGCOMMENTS));

		configSettings.setInputArguments(inputArguments);
        configSettings.setOutputFormatArgument(outputFormatArgument);

        if (isProjectKbScanningEnabled(pageSettings)) {
        	CSPlugin.getKbManager().clearRules();
            CSPlugin.getKbManager().addKBaseRulesToConfigSettings(project, configSettings);
        }

        // copy settings to the configuration file and return it
		configSettings.saveConfig(configFile);
		return configFile;
	}

	/**
	 * Create a CodeScanner configuration file from global CodeScanner settings.
	 * @param configFilePath - path of CodeScanner configuration file
	 * @return CodeScanner configuration file
	 */
	public File createGlobalConfigFile(IProject project, String configFilePath) {
		if (configFilePath.length() == 0) {
			return null;
		}

		// create and initialize a configuration file
		File configFile = new File(configFilePath);
		if (configFile.exists()) {
			configFile.delete();
		}
		CSConfigSettings configSettings = new CSConfigSettings();
		configSettings.loadDefaultConfig();

		// retrieve various stored values
		IPreferenceStore store = CSPlugin.getCSPrefsStore();

		String fileFilterString = store.getString(CSPreferenceConstants.FILE_FILTERS);
		if (fileFilterString != null) {
			StringTokenizer fileFilters = new StringTokenizer(fileFilterString, CSPreferenceConstants.DELIMETER);
			EList<String> fileFilterList = configSettings.getSourceFilters().getExclude();
			fileFilterList.clear();
			while (fileFilters.hasMoreTokens()) {
				String fileFilter = fileFilters.nextToken();
				fileFilterList.add(fileFilter);
			}
		}

		String scriptsString = store.getString(CSPreferenceConstants.RULE_SCRIPTS);
		String categoriesString = store.getString(CSPreferenceConstants.RULE_CATEGORIES);
		String severitiesString = store.getString(CSPreferenceConstants.RULE_SEVERITIES);
		String enabledString = store.getString(CSPreferenceConstants.RULES_ENABLED);
		if ((scriptsString != null) && 
			(categoriesString != null) && 
			(severitiesString != null) && 
			(enabledString != null)) {
			StringTokenizer scriptTokens = new StringTokenizer(scriptsString, CSPreferenceConstants.DELIMETER);
			StringTokenizer categoryTokens = new StringTokenizer(categoriesString, CSPreferenceConstants.DELIMETER);
			StringTokenizer severityTokens = new StringTokenizer(severitiesString, CSPreferenceConstants.DELIMETER);
			StringTokenizer enabledTokens = new StringTokenizer(enabledString, CSPreferenceConstants.DELIMETER);
			while (scriptTokens.hasMoreTokens() &&
				   categoryTokens.hasMoreTokens() &&
				   severityTokens.hasMoreTokens() &&
				   enabledTokens.hasMoreTokens()) {
				String scriptString = "script_" + scriptTokens.nextToken();
				String categoryString = categoryTokens.nextToken();
				String severityString = severityTokens.nextToken();
				int value = Integer.valueOf(enabledTokens.nextToken());
				boolean enabled = (value != 0);
				CSScript script = CSScript.toScript(scriptString);
				if (!script.equals(CSScript.script_unknown)) {
					configSettings.setScriptCategory(script, categoryString);
					configSettings.setScriptSeverity(script, severityString);
					configSettings.setScriptEnabled(script, enabled);
				}
			}
		}

		configSettings.setScriptCClassIgnore(store.getString(CSPreferenceConstants.CCLASSIGNORE));
		configSettings.setScriptForbiddenWords(store.getString(CSPreferenceConstants.FORBIDEENWORDS));
		String icons = store.getString(CSPreferenceConstants.ICONS);
		if (icons != null && icons.length() == 0) {
			icons = null;
		}
		configSettings.setScriptIcons(icons);
		configSettings.setScriptLFunctionIgnore(store.getString(CSPreferenceConstants.LFUNCTIONIGNORE));
		configSettings.setScriptLongLinesLength(store.getInt(CSPreferenceConstants.LONGLINES_LENGTH));
		configSettings.setScriptOpenIgnore(store.getString(CSPreferenceConstants.OPENIGNORE));
		configSettings.setScriptWorryingComments(store.getString(CSPreferenceConstants.WORRYINGCOMMENTS));		
		configSettings.setInputArguments(inputArguments);
        configSettings.setOutputFormatArgument(outputFormatArgument);

        if (isGlobalKbScanningEnabled()) {
        	CSPlugin.getKbManager().clearRules();
        	CSPlugin.getKbManager().addKBaseRulesToConfigSettings(project, configSettings);
        }

		// copy settings to the configuration file and return it
		configSettings.saveConfig(configFile);
		return configFile;
	}

	/**
	 * Load project specific CodeScanner settings from a CodeScanner configuration file.
	 * @param configFilePath - path of CodeScanner configuration file
	 * @param settings - project specific CodeScanner settings
	 */
	public void loadProjectConfigFile(String configFilePath, IDialogSettings settings) {
		if ((configFilePath.length() == 0) || (settings == null)) {
			return;
		}

		// load settings from a configuration file
		File configFile = new File(configFilePath);
    	CSConfigSettings configSettings = new CSConfigSettings();
    	configSettings.loadDefaultConfig();
    	if (configSettings.loadConfig(configFile)) {
    		// store various values
    		String fileFilters = "";
    		EList<String> fileFilterList = configSettings.getSourceFilters().getExclude();
    		for (Iterator<String> iterator = fileFilterList.iterator(); iterator.hasNext();) {
    			fileFilters += iterator.next() + CSPreferenceConstants.DELIMETER;
    		}
    		settings.put(CSPreferenceConstants.FILE_FILTERS, fileFilters);       		

    		String scriptString = "";
    		String categoryString = "";
    		String severityString = "";
    		String enabledString = "";
    		for (CSScript script : CSScript.values()) {
    			if (!script.equals(CSScript.script_unknown)) {
        			scriptString += script.toString() + CSPreferenceConstants.DELIMETER;
        			if (configSettings.getScript(script) != null) {
            			categoryString += configSettings.getScriptCategory(script) + CSPreferenceConstants.DELIMETER;
            			severityString += configSettings.getScriptSeverity(script) + CSPreferenceConstants.DELIMETER;
            			int flag = 1;
            			if (!configSettings.getScriptEnabled(script))
            				flag = 0;
            			enabledString += flag + CSPreferenceConstants.DELIMETER;
        			}
        			else {
            			categoryString += defaultConfig.getScriptCategory(script) + CSPreferenceConstants.DELIMETER;
            			severityString += defaultConfig.getScriptSeverity(script) + CSPreferenceConstants.DELIMETER;
            			int flag = 1;
            			if (!defaultConfig.getScriptEnabled(script))
            				flag = 0;
            			enabledString += flag + CSPreferenceConstants.DELIMETER;
        			}
    			}
    		}			
    		settings.put(CSPreferenceConstants.RULE_SCRIPTS, scriptString);
    		settings.put(CSPreferenceConstants.RULE_CATEGORIES, categoryString);
    		settings.put(CSPreferenceConstants.RULE_SEVERITIES, severityString);
    		settings.put(CSPreferenceConstants.RULES_ENABLED, enabledString);

    		settings.put(CSPreferenceConstants.CCLASSIGNORE, getCClassIgnoreFromConfig(configSettings));
    		settings.put(CSPreferenceConstants.FORBIDEENWORDS, getForbiddenWordsFromConfig(configSettings));
    		settings.put(CSPreferenceConstants.ICONS, getIconsFromConfig(configSettings));
    		settings.put(CSPreferenceConstants.LFUNCTIONIGNORE, getLFunctionIgnoreFromConfig(configSettings));
    		settings.put(CSPreferenceConstants.LONGLINES_LENGTH, getLongLinesLengthFromConfig(configSettings));
    		settings.put(CSPreferenceConstants.OPENIGNORE, getOpenIgnoreFromConfig(configSettings));
    		settings.put(CSPreferenceConstants.WORRYINGCOMMENTS, getWorryingCommentsFromConfig(configSettings));	
    	}
	}

	/**
	 * Load global CodeScanner settings from a CodeScanner configuration file.
	 * @param configFilePath - path of CodeScanner configuration file
	 */
	public void loadGlobalConfigFile(String configFilePath) {
		if (configFilePath.length() == 0) {
			return;
		}

		// load settings from a configuration file
		File configFile = new File(configFilePath);
    	CSConfigSettings configSettings = new CSConfigSettings();
    	configSettings.loadDefaultConfig();
    	if (configSettings.loadConfig(configFile)) {
    		IPreferenceStore store = CSPlugin.getCSPrefsStore();

    		// store various values
    		String fileFilters = "";
    		EList<String> fileFilterList = configSettings.getSourceFilters().getExclude();
    		for (Iterator<String> iterator = fileFilterList.iterator(); iterator.hasNext();) {
    			fileFilters += iterator.next() + CSPreferenceConstants.DELIMETER;
    		}
    		store.setValue(CSPreferenceConstants.FILE_FILTERS, fileFilters);

    		String scriptString = "";
    		String categoryString = "";
    		String severityString = "";
    		String enabledString = "";
    		for (CSScript script : CSScript.values()) {
    			if (!script.equals(CSScript.script_unknown)) {
        			scriptString += script.toString() + CSPreferenceConstants.DELIMETER;
        			if (configSettings.getScript(script) != null) {
            			categoryString += configSettings.getScriptCategory(script) + CSPreferenceConstants.DELIMETER;
            			severityString += configSettings.getScriptSeverity(script) + CSPreferenceConstants.DELIMETER;
            			int flag = 1;
            			if (!configSettings.getScriptEnabled(script))
            				flag = 0;
            			enabledString += flag + CSPreferenceConstants.DELIMETER;
        			}
        			else {
            			categoryString += defaultConfig.getScriptCategory(script) + CSPreferenceConstants.DELIMETER;
            			severityString += defaultConfig.getScriptSeverity(script) + CSPreferenceConstants.DELIMETER;
            			int flag = 1;
            			if (!defaultConfig.getScriptEnabled(script))
            				flag = 0;
            			enabledString += flag + CSPreferenceConstants.DELIMETER;
        			}
    			}
    		}			
    		store.setValue(CSPreferenceConstants.RULE_SCRIPTS, scriptString);
    		store.setValue(CSPreferenceConstants.RULE_CATEGORIES, categoryString);
    		store.setValue(CSPreferenceConstants.RULE_SEVERITIES, severityString);
    		store.setValue(CSPreferenceConstants.RULES_ENABLED, enabledString);

    		store.setValue(CSPreferenceConstants.CCLASSIGNORE, getCClassIgnoreFromConfig(configSettings));
    		store.setValue(CSPreferenceConstants.FORBIDEENWORDS, getForbiddenWordsFromConfig(configSettings));
    		store.setValue(CSPreferenceConstants.ICONS, getIconsFromConfig(configSettings));
    		store.setValue(CSPreferenceConstants.LFUNCTIONIGNORE, getLFunctionIgnoreFromConfig(configSettings));
    		store.setValue(CSPreferenceConstants.LONGLINES_LENGTH, getLongLinesLengthFromConfig(configSettings));
        	store.setValue(CSPreferenceConstants.OPENIGNORE, getOpenIgnoreFromConfig(configSettings));
        	store.setValue(CSPreferenceConstants.WORRYINGCOMMENTS, getWorryingCommentsFromConfig(configSettings));	
    	}
	}

	/**
	 * Set the input arguments.
	 * @param configSettings - CodeScanner configuration settings to be set
	 * @param inputPaths - input paths to be converted into input arguments
	 */
	public void setInputArguments(List<IPath> inputPaths) {
		inputArguments = new ArrayList<String>();
		for (IPath inputPath : inputPaths){
			inputArguments.add(inputPath.toOSString());
		}
	}

	/**
	 * Set the output format argument.
	 * @param configSettings - CodeScanner configuration settings to be set
	 */
	public void setOutputFormatArgument() {
		outputFormatArgument = "std";
        if (generateHtmlResults()) {
        	outputFormatArgument += "|html";
        }
        if (generateXmlResults()) {
        	outputFormatArgument += "|xml";
        }
	}

	/**
	 * Clear various argument settings.
	 */
	private void clearArguments() {
		inputArguments = null;
		outputFormatArgument = null;
	}

	/**
	 * Get the C class ignore list from from a CodeScanner configuration settings. 
	 * If the list does not exist in, get it from the default configuration settings.
	 * @param configSettings - CodeScanner configuration settings to be read
	 * @return C class ignore list as a string
	 */
	private String getCClassIgnoreFromConfig(CSConfigSettings configSettings) {
		String value = configSettings.getScriptCClassIgnore();
		if (value == null) {
			value = getDefaultConfig().getScriptCClassIgnore();
		}
		return value;
	}

	/**
	 * Get the forbidden words list from from a CodeScanner configuration settings. 
	 * If the list does not exist in, get it from the default configuration settings.
	 * @param configSettings - CodeScanner configuration settings to be read
	 * @return forbidden words list as a string
	 */
	private String getForbiddenWordsFromConfig(CSConfigSettings configSettings) {
		String value = configSettings.getScriptForbiddenWords();
		if (value == null) {
			value = getDefaultConfig().getScriptForbiddenWords();
		}
		return value;
	}

	/**
	 * Get the customizable icons list from from a CodeScanner configuration settings. 
	 * If the list does not exist in, get it from the default configuration settings.
	 * @param configSettings - CodeScanner configuration settings to be read
	 * @return customizable icons list as a string
	 */
	private String getIconsFromConfig(CSConfigSettings configSettings) {
		String value = configSettings.getScriptIcons();
		if (value == null) {
			value = getDefaultConfig().getScriptIcons();
		}
		if (value == null) {
			value = "";
		}
		return value;
	}

	/**
	 * Get the L-function ignore list from from a CodeScanner configuration settings. 
	 * If the list does not exist in, get it from the default configuration settings.
	 * @param configSettings - CodeScanner configuration settings to be read
	 * @return L-function ignore list as a string
	 */
	private String getLFunctionIgnoreFromConfig(CSConfigSettings configSettings) {
		String value = configSettings.getScriptLFunctionIgnore();
		if (value == null) {
			value = getDefaultConfig().getScriptLFunctionIgnore();
		}
		return value;
	}

	/**
	 * Get the long lines length attribute value from a CodeScanner configuration settings.
	 * @param configSettings - CodeScanner configuration settings to be read
	 * @return the long lines length attribute value
	 */
	private int getLongLinesLengthFromConfig(CSConfigSettings configSettings) {
		return configSettings.getScriptLongLinesLength();
	}

	/**
	 * Get the Open() function ignore list from from a CodeScanner configuration settings. 
	 * If the list does not exist in, get it from the default configuration settings.
	 * @param configSettings - CodeScanner configuration settings to be read
	 * @return Open() function  ignore list as a string
	 */
	private String getOpenIgnoreFromConfig(CSConfigSettings configSettings) {
		String value = configSettings.getScriptOpenIgnore();
		if (value == null) {
			value = getDefaultConfig().getScriptOpenIgnore();
		}
		return value;
	}

	/**
	 * Get the worrying comments list from from a CodeScanner configuration settings. 
	 * If the list does not exist in, get it from the default configuration settings.
	 * @param configSettings - CodeScanner configuration settings to be read
	 * @return worrying comments list as a string
	 */
	private String getWorryingCommentsFromConfig(CSConfigSettings configSettings) {
		String value = configSettings.getScriptWorryingComments();
		if (value == null) {
			value = getDefaultConfig().getScriptWorryingComments();
		}
		return value;
	}

	/**
	 * Check whether to perform project specific knowledge base scanning.
	 * @param pageSettings - project specific settings
	 * @return true if performing project specific knowledge base scanning.
	 */
	private boolean isProjectKbScanningEnabled (IDialogSettings pageSettings) {
		return pageSettings.getBoolean(CSPreferenceConstants.KBSCANNING);
	}

	/**
	 * Check whether to perform global knowledge base scanning.
	 * @return true if performing global knowledge base scanning.
	 */
	private boolean isGlobalKbScanningEnabled() {
		return CSPlugin.getCSPrefsStore().getBoolean(CSPreferenceConstants.KBSCANNING);
	}

}
