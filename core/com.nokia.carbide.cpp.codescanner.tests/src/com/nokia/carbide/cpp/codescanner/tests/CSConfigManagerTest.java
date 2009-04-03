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

package com.nokia.carbide.cpp.codescanner.tests;

import java.io.File;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigManager;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigSettings;
import com.nokia.carbide.cpp.internal.codescanner.config.CSProjectSettings;
import com.nokia.carbide.cpp.internal.codescanner.ui.CSFileFiltersTabPage;
import com.nokia.carbide.cpp.internal.codescanner.ui.CSGeneralTabPage;
import com.nokia.carbide.cpp.internal.codescanner.ui.CSPreferenceConstants;
import com.nokia.carbide.cpp.internal.codescanner.ui.CSPreferenceInitializer;
import com.nokia.carbide.cpp.internal.codescanner.ui.CSRulesTabPage;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;

/**
 * Test cases for Class CSConfigManager.
 * 
 */
public class CSConfigManagerTest extends TestCase {

	private static CSConfigManager configManager;
	private static IProgressMonitor monitor;
	private static IProject project;

	protected void setUp() throws Exception {
		super.setUp();
		try {
			if (configManager == null) {
				configManager = new CSConfigManager();				
			}
			if (monitor == null) {
				monitor = new NullProgressMonitor();				
			}
			if (project == null) {
				project = ProjectCorePlugin.createProject("CSConfigManagerTest", null);
			}
			project.open(monitor);
		} catch (CoreException e) {
			fail();
		}
	}

	protected void tearDown() throws Exception {
		try {
			project.close(monitor);
		} catch (CoreException e) {
			fail();
		}
		super.tearDown();
	}

	public void testGetDefaultConfig() {
		CSConfigSettings settings = configManager.getDefaultConfig();
		assertNotNull(settings);
	}

	public void testCreateProjectSettings() {
		CSProjectSettings projectSettings = configManager.createProjectSettings(project);
		assertNotNull(projectSettings);
	}

	public void testGetProjectSettings() {
		CSProjectSettings projectSettings = configManager.getProjectSettings(project);
		assertNotNull(projectSettings);
	}

	public void testCreateProjectConfigFile() {
		CSProjectSettings projectSettings = configManager.getProjectSettings(project);
		getProjPropertySettings(projectSettings);
		String configFilePath = getConfigFilePath();
		File configFile = configManager.createProjectConfigFile(project, configFilePath);
		assertNotNull(configFile);
		assertTrue(configFile.exists());
		configFile.delete();
	}

	public void testCreateGlobalConfigFile() {
		CSPreferenceInitializer prefInitializer = new CSPreferenceInitializer();
		prefInitializer.initializeDefaultPreferences();
		String configFilePath = getConfigFilePath();
		File configFile = configManager.createGlobalConfigFile(null, configFilePath);
		assertNotNull(configFile);
		assertTrue(configFile.exists());
		configFile.delete();
	}

	public void testCreateConfigFile() {
		CSProjectSettings projectSettings = configManager.getProjectSettings(project);
		getProjPropertySettings(projectSettings);
		CSPreferenceInitializer prefInitializer = new CSPreferenceInitializer();
		prefInitializer.initializeDefaultPreferences();
		String configFilePath = getConfigFilePath();
		File configFile = configManager.createConfigFile(null, configFilePath);
		assertNotNull(configFile);
		assertTrue(configFile.exists());
		configFile.delete();
		configFile = configManager.createConfigFile(project, configFilePath);
		assertNotNull(configFile);
		assertTrue(configFile.exists());
		configFile.delete();
		projectSettings.getDialogSettings().getSection(CSPreferenceConstants.PROPERTY_SETTINGS_ID).put(CSPreferenceConstants.PROJ_SETTINGS, false);
		configFile = configManager.createConfigFile(project, configFilePath);
		assertNotNull(configFile);
		assertTrue(configFile.exists());
		configFile.delete();
	}

	public void testLoadProjectConfigSettings() {
		CSProjectSettings projectSettings = configManager.getProjectSettings(project);
		getProjPropertySettings(projectSettings);
		assertTrue(configManager.loadProjectConfigSettings(project));
		projectSettings.getDialogSettings().getSection(CSPreferenceConstants.PROPERTY_SETTINGS_ID).put(CSPreferenceConstants.PROJ_SETTINGS, false);
		assertFalse(configManager.loadProjectConfigSettings(project));
	}

	public void testLoadGlobalConfigSettings() {
		CSPreferenceInitializer prefInitializer = new CSPreferenceInitializer();
		prefInitializer.initializeDefaultPreferences();
		assertTrue(configManager.loadGlobalConfigSettings());
	}

	public void testLoadConfigSettings() {
		CSProjectSettings projectSettings = configManager.getProjectSettings(project);
		getProjPropertySettings(projectSettings);
		CSPreferenceInitializer prefInitializer = new CSPreferenceInitializer();
		prefInitializer.initializeDefaultPreferences();
		assertTrue(configManager.loadConfigSettings(null));
		assertTrue(configManager.loadConfigSettings(project));
		projectSettings.getDialogSettings().getSection(CSPreferenceConstants.PROPERTY_SETTINGS_ID).put(CSPreferenceConstants.PROJ_SETTINGS, false);
		assertTrue(configManager.loadConfigSettings(project));
	}

	public void testLoadProjectConfigFile() {
		CSProjectSettings projectSettings = configManager.getProjectSettings(project);
		IDialogSettings settings = getProjPropertySettings(projectSettings);
		String configFilePath = getConfigFilePath();
		File configFile = new File(configFilePath);
		assertNotNull(configFile);
		configManager.getDefaultConfig().saveConfig(configFile);
		assertTrue(configFile.exists());
		configManager.loadProjectConfigFile(configFilePath, settings);
		assertTrue(settings.get(CSPreferenceConstants.RULE_SCRIPTS).length() > 0);
		assertTrue(settings.get(CSPreferenceConstants.RULE_CATEGORIES).length() > 0);
		assertTrue(settings.get(CSPreferenceConstants.RULE_SEVERITIES).length() > 0);
		assertTrue(settings.get(CSPreferenceConstants.RULES_ENABLED).length() > 0);
		assertTrue(settings.get(CSPreferenceConstants.CCLASSIGNORE).length() > 0);
		assertTrue(settings.get(CSPreferenceConstants.ICONS).length() == 0);
		assertTrue(settings.get(CSPreferenceConstants.LFUNCTIONIGNORE).length() > 0);
		assertTrue(settings.get(CSPreferenceConstants.LONGLINES_LENGTH).length() > 0);
		assertTrue(settings.get(CSPreferenceConstants.OPENIGNORE).length() > 0);
		assertTrue(settings.get(CSPreferenceConstants.WORRYINGCOMMENTS).length() > 0);
		assertTrue(settings.getInt(CSPreferenceConstants.LONGLINES_LENGTH) > 0);
		configFile.delete();
	}

	public void testLoadGlobalConfigFile() {
		CSPreferenceInitializer prefInitializer = new CSPreferenceInitializer();
		prefInitializer.initializeDefaultPreferences();
		String configFilePath = getConfigFilePath();
		File configFile = new File(configFilePath);
		assertNotNull(configFile);
		configManager.getDefaultConfig().saveConfig(configFile);
		assertTrue(configFile.exists());
		configManager.loadGlobalConfigFile(configFilePath);
		IPreferenceStore store = CSPlugin.getCSPrefsStore();
		assertTrue(store.getString(CSPreferenceConstants.RULE_SCRIPTS).length() > 0);
		assertTrue(store.getString(CSPreferenceConstants.RULE_CATEGORIES).length() > 0);
		assertTrue(store.getString(CSPreferenceConstants.RULE_SEVERITIES).length() > 0);
		assertTrue(store.getString(CSPreferenceConstants.RULES_ENABLED).length() > 0);
		assertTrue(store.getString(CSPreferenceConstants.CCLASSIGNORE).length() > 0);
		assertTrue(store.getString(CSPreferenceConstants.ICONS).length() >= 0);
		assertTrue(store.getString(CSPreferenceConstants.LFUNCTIONIGNORE).length() > 0);
		assertTrue(store.getString(CSPreferenceConstants.LONGLINES_LENGTH).length() > 0);
		assertTrue(store.getString(CSPreferenceConstants.OPENIGNORE).length() > 0);
		assertTrue(store.getString(CSPreferenceConstants.WORRYINGCOMMENTS).length() > 0);
		assertTrue(store.getInt(CSPreferenceConstants.LONGLINES_LENGTH) > 0);
		configFile.delete();
	}

	private String getConfigFilePath() {
		String configFilePath = project.getLocation() + CSConfigManager.CS_CONFIG_SETTINGS_FILE;
		return configFilePath;
	}

	private IDialogSettings getProjPropertySettings(CSProjectSettings projectSettings) {
		assertNotNull(projectSettings);
		IDialogSettings cSettings = projectSettings.getDialogSettings();
		IDialogSettings pageSettings = cSettings.getSection(CSPreferenceConstants.PROPERTY_SETTINGS_ID);
		if (pageSettings == null) {
			pageSettings = cSettings.addNewSection(CSPreferenceConstants.PROPERTY_SETTINGS_ID);
			pageSettings.put(CSPreferenceConstants.PROJ_SETTINGS, true);
			CSGeneralTabPage.initializePropertyValues(pageSettings);
			CSFileFiltersTabPage.initializePropertyValues(pageSettings);
			CSRulesTabPage.initializePropertyValues(pageSettings);
		}
		else {
			pageSettings.put(CSPreferenceConstants.PROJ_SETTINGS, true);
		}
		return pageSettings;
	}

}
