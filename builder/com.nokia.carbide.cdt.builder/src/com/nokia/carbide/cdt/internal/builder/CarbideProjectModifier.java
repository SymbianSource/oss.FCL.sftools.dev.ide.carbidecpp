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
package com.nokia.carbide.cdt.internal.builder;

import java.util.ArrayList;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.cdt.core.settings.model.WriteAccessException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.internal.api.builder.CarbideConfigurationDataProvider;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContextDataCache;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.cpp.internal.api.utils.core.Logging;

public class CarbideProjectModifier extends CarbideProjectInfo implements ICarbideProjectModifier {

	// need to make all changes to the same description and save it
	protected ICProjectDescription projDes;
	private boolean shouldForceRebuildCache;


	/*
	 * Used when the project is being created
	 */
	public CarbideProjectModifier(ICProjectDescription projDes) {
		super(projDes.getProject());
		this.projDes = projDes;
		
		initializeDefaults();
	}

	/*
	 * Used when project already exists.  This is a copy of the original CarbideProjectInfo.
	 * When this is saved it will replace the existing CarbideProjectInfo in the build manager.
	 */
	public CarbideProjectModifier(CarbideProjectInfo cpi) {
		// get the latest ICProjectDescription
		super(cpi.projectTracker.getProject());

		projDes = getProjectDescription(true);
		projectRelativeBldInfPath = cpi.projectRelativeBldInfPath;
		buildFromInf = cpi.buildFromInf;
		overrideWorkspaceSettings = cpi.overrideWorkspaceSettings;
		cleanLevel = cpi.cleanLevel;
		isBuildingTestComps = cpi.isBuildingTestComps;
		manageDependencies = cpi.manageDependencies;
		useConcurrentBuilding = cpi.useConcurrentBuilding;
		concurrentBuildJobs = cpi.concurrentBuildJobs;
		promptForMMPChangedAction = cpi.promptForMMPChangedAction;
		defaultMMPChangedAction = cpi.defaultMMPChangedAction;
		macrosFile = cpi.macrosFile;
		useMMPMacros = cpi.useMMPMacros;
		useIncrementalBuilder = cpi.useIncrementalBuilder;
		useKeepGoing = cpi.useKeepGoing;
		useDebugMode = cpi.useDebugMode;
		overrideMakeEngine = cpi.overrideMakeEngine;
		makeEngineToUse = cpi.makeEngineToUse;
		extraSBSv2Args = cpi.extraSBSv2Args;
		
		// make copies of the original data because we don't want to be modifying those
		// objects in case the save is never done on this object.
		infBuildComponentsList = new ArrayList<String>();
		infBuildComponentsList.addAll(cpi.infBuildComponentsList);
	}

	public ICarbideBuildConfiguration createNewConfiguration(ISymbianBuildContext context, boolean makeDefault) {

		CarbideBuildConfiguration config = new CarbideBuildConfiguration(projectTracker.getProject(), context);

		// create a CDT build config
		try {
			ICConfigurationDescription configDes = projDes.createConfiguration(CarbideConfigurationDataProvider.BUILD_SYSTEM_ID, config.getBuildConfigurationData());
			// if this is to be the default then make it the default CDT config as well
			if (makeDefault) {
				projDes.setDefaultSettingConfiguration(configDes);
				projDes.setActiveConfiguration(configDes);
			}
			
		} catch (WriteAccessException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		} catch (CoreException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		}

		// write to disk
		config.saveConfiguration(true);

		return config;
	}

	public boolean deleteConfiguration(ICarbideBuildConfiguration config) {
		// remove the CDT build config as well
		projDes.removeConfiguration(config.getDisplayString());

		return true;
	}

	public boolean setDefaultConfiguration(ICarbideBuildConfiguration config) {
		if (!config.getDisplayString().equals(this.getDefaultBuildConfigName())) {
			// change the default CDT config
			ICConfigurationDescription configDes = projDes.getConfigurationByName(config.getDisplayString());
			projDes.setDefaultSettingConfiguration(configDes);
			projDes.setActiveConfiguration(configDes);
		}
		
		return true;
	}

	public void writeProjectSetting(String settingName, String settingValue) {
		try {
			ICStorageElement storage = projDes.getStorage(CarbideBuilderPlugin.getCarbideBuilderExtensionID(), true);
			if (storage != null) {
				String orig = storage.getAttribute(settingName); 
				if (orig == null || !orig.equals(settingValue)) {
					
					// setting has changed, write value to object cache
					if (settingName.equals(PROJECT_RELATIVE_INFFILE_PROPS_KEY)) {
						projectRelativeBldInfPath = new Path(settingValue);
					} else if (settingName.equals(BLD_FROM_INF_PROPS_KEY)) {
						shouldForceRebuildCache = true;
						if (settingValue.equalsIgnoreCase("false")) {
							buildFromInf = false;
						} else {
							buildFromInf = true;
						}
					} else if (settingName.equals(INF_COMPONENTS_PROPS_KEY)) {
						shouldForceRebuildCache = true;
						setInfBuildComponentList(settingValue);
					} else if (settingName.equals(OVERRIDE_WORKSPACE_SETTINGS_KEY)) {
						if (settingValue.equalsIgnoreCase("false")) {
							overrideWorkspaceSettings = false;
						} else {
							overrideWorkspaceSettings = true;
						}
					} else if (settingName.equals(CLEAN_LEVEL)) {
						cleanLevel = Integer.parseInt(settingValue);
					} else if (settingName.equals(BUILD_TEST_COMPS_PROPS_KEY)) {
						if (settingValue.equalsIgnoreCase("false")) {
							isBuildingTestComps = false;
						} else {
							isBuildingTestComps = true;
						}
					} else if (settingName.equals(MANAGE_DEPENDENCIES)) {
						if (settingValue.equalsIgnoreCase("false")) {
							manageDependencies = false;
						} else {
							manageDependencies = true;
						}
					} else if (settingName.equals(USE_CONCURRENT_BUILDING)) {
						if (settingValue.equalsIgnoreCase("false")) {
							useConcurrentBuilding = false;
						} else {
							useConcurrentBuilding = true;
						}
					} else if (settingName.equals(CONCURRENT_BUILD_JOBS)) {
						concurrentBuildJobs = Integer.parseInt(settingValue);
					} else if (settingName.equals(PROMPT_FOR_MMP_CHANGED_ACTION)) {
						if (settingValue.equalsIgnoreCase("false")) {
							promptForMMPChangedAction = false;
						} else {
							promptForMMPChangedAction = true;
						}
					} else if (settingName.equals(DEFAULT_MMP_CHANGED_ACTION)) {
						defaultMMPChangedAction = Integer.parseInt(settingValue);
					} else if (settingName.equals(MACROS_FILE)) {
						macrosFile = settingValue;
					} else if (settingName.equals(USE_MMP_MACROS)) {
						if (settingValue.equalsIgnoreCase("false")) {
							useMMPMacros = false;
						} else {
							useMMPMacros = true;
						}
					} else if (settingName.equals(USE_INCREMENTAL_BUILDER)) {
						if (settingValue.equalsIgnoreCase("false")) {
							useIncrementalBuilder = false;
						} else {
							useIncrementalBuilder = true;
						}
					} else if (settingName.equals(USE_KEEP_GOING)) {
						if (settingValue.equalsIgnoreCase("false")) {
							useKeepGoing = false;
						} else {
							useKeepGoing = true;
						}
					} else if (settingName.equals(USE_DEBUG_MODE)) {
						if (settingValue.equalsIgnoreCase("false")) {
							useDebugMode = false;
						} else {
							useDebugMode = true;
						}
					} else if (settingName.equals(OVERRIDE_MAKE_ENGINE)) {
						if (settingValue.equalsIgnoreCase("false")) {
							overrideMakeEngine = false;
						} else {
							overrideMakeEngine = true;
						}
					} else if (settingName.equals(MAKE_ENGINE_TO_USE)) {
						makeEngineToUse = settingValue;
					} else if (settingName.equals(EXTRA_SBSV2_ARGS)) {
						extraSBSv2Args = settingValue;
					}
					
					// now write it to the file
					storage.setAttribute(settingName, settingValue); 
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		}
	}

	public boolean saveChanges() {

		checkInternalSettings();
		
		// make sure this flag is set before saving the ICProjectDescription
		projDes.setCdtProjectCreated();
		
		boolean rebuildCacheAndReindex = shouldForceRebuildCache && !getIndexAllPreference();
		
		if (rebuildCacheAndReindex) {
			for (ICConfigurationDescription configuration : projDes.getConfigurations()) {
				BuildConfigurationData data = (BuildConfigurationData) configuration.getConfigurationData();
				data.forceRebuildCache();
			}
		}
		
		try {
			
			// save the CDT project description
			
			try {
				// let the build context caches know we may be iterating them all
				SymbianBuildContextDataCache.startProjectOperation();
				
				// TODO PERFORMANCE: this can lead to CarbideLanguageData#buildCache(), which is an enormously expensive operation. 
				// So use a real progress monitor, say from a Job, so UI will be updated
				CCorePlugin.getDefault().setProjectDescription(projectTracker.getProject(), projDes, true, new NullProgressMonitor());
				
				// replace the old info in the map with the new
				CarbideBuilderPlugin.getBuildManager().setProjectInfo(this);
				
			} finally {
				SymbianBuildContextDataCache.endProjectOperation();
			}
			if (rebuildCacheAndReindex) {
				ICProject cproject = CoreModel.getDefault().create(projectTracker.getProject());
				if (cproject != null)
					CCorePlugin.getIndexManager().reindex(cproject);				
			}
			
			return true;
		} catch (CoreException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		}
		return false;
	}
	
	private static boolean getIndexAllPreference() {
		// Can't access this pref from the project ui plugin because it would cause a circular dependency
		Plugin plugin = Platform.getPlugin("com.nokia.carbide.cpp.project.ui"); //$NON-NLS-1$
		if (plugin == null) {
			CarbideBuilderPlugin.log(Logging.newStatus(CarbideBuilderPlugin.getDefault(), 
					IStatus.WARNING, "Could not find project UI plugin!"));
			return true;
		}
		return plugin.getPluginPreferences().getBoolean("indexAll"); //$NON-NLS-1$
	}

	public void checkInternalSettings() {

		// make sure our internal settings are written to disk
		try {
			ICStorageElement storage = projDes.getStorage(CarbideBuilderPlugin.getCarbideBuilderExtensionID(), true);
			if (storage != null) {
				// if one is then they all should be
				if (storage.getAttribute(MANAGE_DEPENDENCIES) == null) {
					
					
					storage.setAttribute(OVERRIDE_WORKSPACE_SETTINGS_KEY, overrideWorkspaceSettings ? "true" : "false");
					storage.setAttribute(CLEAN_LEVEL, Integer.toString(cleanLevel));
					storage.setAttribute(BUILD_TEST_COMPS_PROPS_KEY, isBuildingTestComps ? "true" : "false");
					storage.setAttribute(MANAGE_DEPENDENCIES, manageDependencies ? "true" : "false");
					storage.setAttribute(USE_CONCURRENT_BUILDING, useConcurrentBuilding ? "true" : "false");
					storage.setAttribute(CONCURRENT_BUILD_JOBS, Integer.toString(concurrentBuildJobs));
					storage.setAttribute(PROMPT_FOR_MMP_CHANGED_ACTION, promptForMMPChangedAction ? "true" : "false");
					storage.setAttribute(DEFAULT_MMP_CHANGED_ACTION, Integer.toString(defaultMMPChangedAction));
					storage.setAttribute(MACROS_FILE, macrosFile);
					storage.setAttribute(USE_MMP_MACROS, useMMPMacros ? "true" : "false");
					storage.setAttribute(USE_INCREMENTAL_BUILDER, useIncrementalBuilder ? "true" : "false");
					storage.setAttribute(USE_KEEP_GOING, useKeepGoing ? "true" : "false");
					storage.setAttribute(USE_DEBUG_MODE, useDebugMode ? "true" : "false");
					storage.setAttribute(OVERRIDE_MAKE_ENGINE, overrideMakeEngine ? "true" : "false");
					storage.setAttribute(MAKE_ENGINE_TO_USE, makeEngineToUse);
					storage.setAttribute(EXTRA_SBSV2_ARGS, extraSBSv2Args);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		}
	}
}
