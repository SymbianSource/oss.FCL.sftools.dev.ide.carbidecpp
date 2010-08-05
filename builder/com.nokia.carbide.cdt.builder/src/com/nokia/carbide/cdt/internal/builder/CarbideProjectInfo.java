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
import java.util.List;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationData;
import org.eclipse.cdt.internal.core.settings.model.CProjectDescriptionManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.internal.builder.ui.BuilderPreferencePage;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.PathUtils;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;


public class CarbideProjectInfo implements ICarbideProjectInfo {
	
	public static final String INF_COMPONENTS_DELIMITER = ";";
	protected TrackedResource projectTracker;
	protected IPath projectRelativeBldInfPath;
	protected List<String> infBuildComponentsList;
	protected boolean buildFromInf;
	
	protected boolean overrideWorkspaceSettings;
	protected int cleanLevel;
	protected boolean isBuildingTestComps;
	protected boolean manageDependencies;
	protected boolean useConcurrentBuilding;
	protected int concurrentBuildJobs;
	protected boolean promptForMMPChangedAction;
	protected int defaultMMPChangedAction;
	protected String macrosFile;
	protected boolean useMMPMacros;
	protected boolean useIncrementalBuilder;
	protected boolean useKeepGoing;
	protected boolean useDebugMode;
	protected boolean overrideMakeEngine;
	protected String makeEngineToUse;
	protected String extraSBSv2Args;
	
	// for internal plugin use
	public static final String OVERRIDE_WORKSPACE_SETTINGS_KEY = "overrideWorkspaceSettings"; //$NON-NLS-1$
	public static final String CLEAN_LEVEL = "cleanLevel"; //$NON-NLS-1$
	public static final String BUILD_TEST_COMPS_PROPS_KEY = "buildingTestComps"; //$NON-NLS-1$
	public static final String MANAGE_DEPENDENCIES = "manageDependencies"; //$NON-NLS-1$
	public static final String USE_CONCURRENT_BUILDING = "useConcurrentBuilding"; //$NON-NLS-1$
	public static final String CONCURRENT_BUILD_JOBS = "concurrentBuildJobs"; //$NON-NLS-1$
	public static final String PROMPT_FOR_MMP_CHANGED_ACTION = "promptForMMPChangedAction"; //$NON-NLS-1$
	public static final String DEFAULT_MMP_CHANGED_ACTION = "defaultMMPChangedAction"; //$NON-NLS-1$
	public static final String MACROS_FILE = "macrosFile"; //$NON-NLS-1$
	public static final String USE_MMP_MACROS = "useMMPMacros"; //$NON-NLS-1$
	public static final String USE_INCREMENTAL_BUILDER = "useIncrementalBuilder"; //$NON-NLS-1$
	public static final String USE_KEEP_GOING = "useKeepGoing"; //$NON-NLS-1$
	public static final String USE_DEBUG_MODE = "useDebugMode"; //$NON-NLS-1$
	public static final String OVERRIDE_MAKE_ENGINE = "overrideMakeEngine"; //$NON-NLS-1$
	public static final String MAKE_ENGINE_TO_USE = "makeEngineToUse"; //$NON-NLS-1$
	public static final String EXTRA_SBSV2_ARGS = "extraSBSv2Args"; //$NON-NLS-1$
	
	public CarbideProjectInfo(IProject project) {
		this.projectTracker = new TrackedResource(project);
		
		try {
			ICProjectDescription projDes = getProjectDescription(false);
			if (projDes != null) {
				initializeDefaults();

				ICStorageElement storage = projDes.getStorage(CarbideBuilderPlugin.getCarbideBuilderExtensionID(), false);
				if (storage != null) {
					String orig = storage.getAttribute(PROJECT_RELATIVE_INFFILE_PROPS_KEY);
					if (orig != null){
						projectRelativeBldInfPath = PathUtils.createPath(orig);
					}

					orig = storage.getAttribute(BLD_FROM_INF_PROPS_KEY);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							buildFromInf = false;
						} else {
							buildFromInf = true;
						}
					}
					orig = storage.getAttribute(INF_COMPONENTS_PROPS_KEY); //$NON-NLS-1$
					if (orig != null){
						setInfBuildComponentList(orig);
					}

					// internal only options
					orig = storage.getAttribute(OVERRIDE_WORKSPACE_SETTINGS_KEY);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							overrideWorkspaceSettings = false;
						} else {
							overrideWorkspaceSettings = true;
						}
					}
					orig = storage.getAttribute(CLEAN_LEVEL);
					if (orig != null){
						try {
							cleanLevel = Integer.parseInt(orig);
						} catch (NumberFormatException e) {
							e.printStackTrace();
							CarbideBuilderPlugin.log(e);
						}
					}
					orig = storage.getAttribute(BUILD_TEST_COMPS_PROPS_KEY);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							isBuildingTestComps = false;
						} else {
							isBuildingTestComps = true;
						}
					}
					orig = storage.getAttribute(MANAGE_DEPENDENCIES);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							manageDependencies = false;
						} else {
							manageDependencies = true;
						}
					}
					orig = storage.getAttribute(USE_CONCURRENT_BUILDING);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							useConcurrentBuilding = false;
						} else {
							useConcurrentBuilding = true;
						}
					}
					orig = storage.getAttribute(CONCURRENT_BUILD_JOBS);
					if (orig != null){
						try {
							concurrentBuildJobs = Integer.parseInt(orig);
						} catch (NumberFormatException e) {
							e.printStackTrace();
							CarbideBuilderPlugin.log(e);
						}
					}
					orig = storage.getAttribute(PROMPT_FOR_MMP_CHANGED_ACTION);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							promptForMMPChangedAction = false;
						} else {
							promptForMMPChangedAction = true;
						}
					}
					orig = storage.getAttribute(DEFAULT_MMP_CHANGED_ACTION);
					if (orig != null){
						try {
							defaultMMPChangedAction = Integer.parseInt(orig);
						} catch (NumberFormatException e) {
							e.printStackTrace();
							CarbideBuilderPlugin.log(e);
						}
					}
					orig = storage.getAttribute(MACROS_FILE);
					if (orig != null){
						macrosFile = orig;
					}
					orig = storage.getAttribute(USE_MMP_MACROS);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							useMMPMacros = false;
						} else {
							useMMPMacros = true;
						}
					}
					orig = storage.getAttribute(USE_INCREMENTAL_BUILDER);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							useIncrementalBuilder = false;
						} else {
							useIncrementalBuilder = true;
						}
					}
					orig = storage.getAttribute(USE_KEEP_GOING);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							useKeepGoing = false;
						} else {
							useKeepGoing = true;
						}
					}
					orig = storage.getAttribute(USE_DEBUG_MODE);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							useDebugMode = false;
						} else {
							useDebugMode = true;
						}
					}
					orig = storage.getAttribute(OVERRIDE_MAKE_ENGINE);
					if (orig != null){
						if (orig.equalsIgnoreCase("false")){
							overrideMakeEngine = false;
						} else {
							overrideMakeEngine = true;
						}
					}
					orig = storage.getAttribute(MAKE_ENGINE_TO_USE);
					if (orig != null){
						makeEngineToUse = orig;
					}
					orig = storage.getAttribute(EXTRA_SBSV2_ARGS);
					if (orig != null){
						extraSBSv2Args = orig;
					}
				}
			}
		}
		catch (CoreException e) {
			e.printStackTrace();
			CarbideBuilderPlugin.log(e);
		}
	}
	
	protected void initializeDefaults() {
		overrideWorkspaceSettings = false;

		if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(projectTracker.getProject())) {
			cleanLevel = BuilderPreferencePage.getCleanLevelv2();
		} else {
			cleanLevel = BuilderPreferencePage.getCleanLevel();
		}

		isBuildingTestComps = BuilderPreferencePage.isBuildingTestComps();
		manageDependencies = BuilderPreferencePage.manageDependencies();
		useConcurrentBuilding = BuilderPreferencePage.useConcurrentBuilding();
		concurrentBuildJobs = BuilderPreferencePage.concurrentBuildJobs();
		promptForMMPChangedAction = BuilderPreferencePage.promptForMMPChangedAction();
		defaultMMPChangedAction = BuilderPreferencePage.defaultMMPChangedAction();
		macrosFile = "";
		useMMPMacros = true;
		useIncrementalBuilder = BuilderPreferencePage.useIncrementalBuilder();
		useKeepGoing = BuilderPreferencePage.keepGoing();
		useDebugMode = BuilderPreferencePage.debugMode();
		overrideMakeEngine = BuilderPreferencePage.overrideDefaultMakeEngine();
		makeEngineToUse = BuilderPreferencePage.makeEngine();
		extraSBSv2Args = BuilderPreferencePage.extraSBSv2ArgsTextStore();
	}

	public List<ICarbideBuildConfiguration> getBuildConfigurations() {
		
		List<ICarbideBuildConfiguration> configs = new ArrayList<ICarbideBuildConfiguration>();
		
		ICProjectDescription projectDescription = getProjectDescription(false);
		if (projectDescription != null) {
			for (ICConfigurationDescription config : projectDescription.getConfigurations()) {
				CConfigurationData data = config.getConfigurationData();
				if (data instanceof BuildConfigurationData) {
					configs.add(((BuildConfigurationData)data).getConfiguration());
				}
			}
		}

		return configs;
	}
	
	public ICarbideBuildConfiguration getNamedConfiguration(String configName) {
		ICProjectDescription projectDescription = getProjectDescription(false);
		if (projectDescription != null) {
			ICConfigurationDescription config = projectDescription.getConfigurationByName(configName);
			if (config != null) {
				CConfigurationData data = config.getConfigurationData();
				if (data instanceof BuildConfigurationData) {
					return ((BuildConfigurationData)data).getConfiguration();
				}
			}
		}
				
		return null;
	}
	
	public ICarbideBuildConfiguration getDefaultConfiguration() {
		ICProjectDescription projectDescription = getProjectDescription(false);
		if (projectDescription == null)
			return null;
		ICConfigurationDescription config = projectDescription.getActiveConfiguration();
		if (config != null) {
			CConfigurationData data = config.getConfigurationData();
			if (data == null){
				ICConfigurationDescription[] config2 = projectDescription.getConfigurations();
				data = config2[0].getConfigurationData();
			}
			if (data instanceof BuildConfigurationData) {
				return ((BuildConfigurationData)data).getConfiguration();
			}
		}
		return null;
	}

	public String getDefaultBuildConfigName() {
		ICarbideBuildConfiguration buildConfig = getDefaultConfiguration();
		if (buildConfig == null) {
			return "";
		}
		return buildConfig.getDisplayString();
	}
	
	public boolean isBuildingFromInf() {
		return buildFromInf;
	}
	
	public IPath getAbsoluteBldInfPath(){
		return CarbideBuilderPlugin.getProjectRoot(projectTracker.getProject()).append(projectRelativeBldInfPath);
	}
	
	public IPath getProjectRelativeBldInfPath(){
		return projectRelativeBldInfPath;
	}

	public IPath getWorkspaceRelativeBldInfPath(){
		return new Path(projectTracker.getProject().getName()).append(projectRelativeBldInfPath);
	}
	
	public IProject getProject(){
		return projectTracker.getProject();
	}
	
	public List<String> getInfBuildComponentsRawSettings(){
		return infBuildComponentsList;
	}
	
	public List<String> getInfBuildComponents(){
		List<String> infComponentsList = new ArrayList<String>();

		if (infBuildComponentsList != null) {
			for (String currComp : infBuildComponentsList){
				if (currComp.contains(TEST_COMPONENT_LABEL)){
					currComp = currComp.replace(TEST_COMPONENT_LABEL, "").trim();
				}
				infComponentsList.add(currComp);
			}
		}
		return infComponentsList;
	}
	
	public List<String> getNormalInfBuildComponents(){
		List<String> compList = new ArrayList<String>();
		for (String currComp : infBuildComponentsList){
			if (!currComp.contains(TEST_COMPONENT_LABEL)){
				compList.add(currComp);
			}
		}
		return compList;
	}
	
	public List<String> getTestInfBuildComponents(){
		List<String> compList = new ArrayList<String>();
		for (String currComp : infBuildComponentsList){
			if (currComp.contains(TEST_COMPONENT_LABEL)){
				currComp = currComp.replace(TEST_COMPONENT_LABEL, "").trim();
				compList.add(currComp);
			}
		}
		return compList;
	}
	
	protected void setInfBuildComponentList(String componentString){
		
		if (componentString.length() > 0){
			infBuildComponentsList = new ArrayList<String>();
			String [] tokens = componentString.split(";");
			for (String currTok : tokens){
				infBuildComponentsList.add(currTok);
			}
		} else {
			infBuildComponentsList = new ArrayList<String>(0);
			return;
		}
	}
	
	public IPath getINFWorkingDirectory() {
		IPath workingDir = getAbsoluteBldInfPath().removeLastSegments(1);
        ICarbideBuildConfiguration defaultConfig = getDefaultConfiguration();
        if (defaultConfig != null){
	        // Here we need to check if the working directory is on the same spec as EPOCROOT
        	ISymbianSDK sdk = defaultConfig.getSDK();
        	if (sdk != null){
        		IPath epocPath = new Path(sdk.getEPOCROOT());
        		if (epocPath.getDevice() != null && workingDir.getDevice() != null) {	// default fall back SDK(when having bad SDK) or non Windows OS in the future
    				if (!workingDir.getDevice().toUpperCase().equals(epocPath.getDevice().toUpperCase()) ){
    					// Drive spec of bld.inf and epocroot are not the same.
    					// Check to see if the bld.inf file exists on the new EPOCROOT drive spec
    					IPath newWorkingDir = workingDir.setDevice(epocPath.getDevice());
    					if (newWorkingDir.toFile().exists()){
    						workingDir = newWorkingDir;
    					}
    				}
        		}
        	}
        }
		return workingDir;
	}
	
	protected ICProjectDescription getProjectDescription(boolean writable) {
		return CoreModel.getDefault().getProjectDescription(projectTracker.getProject(), writable);
	}
	
	public int getCleanLevel() {
		if (overrideWorkspaceSettings) {
			return cleanLevel;
		}
		
		if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(projectTracker.getProject())) {
			return BuilderPreferencePage.getCleanLevelv2();
		}
		
		return BuilderPreferencePage.getCleanLevel();
	}

	public boolean isBuildingTestComps() {
		if (overrideWorkspaceSettings) {
			return isBuildingTestComps;
		}
		return BuilderPreferencePage.isBuildingTestComps();
	}

	public boolean areMakefilesManaged() {
		if (overrideWorkspaceSettings) {
			return manageDependencies;
		}
		return BuilderPreferencePage.manageDependencies();
	}

	public boolean isConcurrentBuildingEnabled() {
		if (overrideWorkspaceSettings) {
			return useConcurrentBuilding;
		}
		return BuilderPreferencePage.useConcurrentBuilding();
	}
	
	public int concurrentBuildJobs() {
		if (overrideWorkspaceSettings) {
			return concurrentBuildJobs;
		}
		return BuilderPreferencePage.concurrentBuildJobs();
	}

	public boolean promptForMMPChangedAction() {
		if (overrideWorkspaceSettings) {
			return promptForMMPChangedAction;
		}
		return BuilderPreferencePage.promptForMMPChangedAction();
	}
	
	public int defaultMMPChangedAction() {
		if (overrideWorkspaceSettings) {
			return defaultMMPChangedAction;
		}
		return BuilderPreferencePage.defaultMMPChangedAction();
	}

	public String getMacrosFile() {
		return macrosFile;
	}

	public boolean shouldUseMMPMacros() {
		return useMMPMacros;
	}

	public boolean incrementalBuilderEnabled() {
		if (overrideWorkspaceSettings) {
			return useIncrementalBuilder;
		}
		return BuilderPreferencePage.useIncrementalBuilder();
	}
	
	public boolean useKeepGoing() {
		if (overrideWorkspaceSettings) {
			return useKeepGoing;
		}
		return BuilderPreferencePage.keepGoing();
	}

	public boolean useDebugMode() {
		if (overrideWorkspaceSettings) {
			return useDebugMode;
		}
		return BuilderPreferencePage.debugMode();
	}

	public boolean shouldOverrideMakeEngine() {
		if (overrideWorkspaceSettings) {
			return overrideMakeEngine;
		}
		return BuilderPreferencePage.overrideDefaultMakeEngine();
	}

	public String makeEngineToUse() {
		if (overrideWorkspaceSettings) {
			return makeEngineToUse;
		}
		return BuilderPreferencePage.makeEngine();
	}

	public String extraSBSv2Args() {
		if (overrideWorkspaceSettings) {
			return extraSBSv2Args;
		}
		return BuilderPreferencePage.extraSBSv2ArgsTextStore();
	}
	
	/*
	 * The following methods are non-API
	 */
	public boolean overrideWorkspaceBuildSettingsProjectValue() {
		return overrideWorkspaceSettings;
	}

	public int getCleanLevelProjectValue() {
		return cleanLevel;
	}

	public boolean isBuildingTestCompsProjectValue() {
		return isBuildingTestComps;
	}

	public boolean areMakefilesManagedProjectValue() {
		return manageDependencies;
	}

	public boolean isConcurrentBuildingEnabledProjectValue() {
		return useConcurrentBuilding;
	}
	
	public int concurrentBuildJobsProjectValue() {
		return concurrentBuildJobs;
	}

	public boolean promptForMMPChangedActionProjectValue() {
		return promptForMMPChangedAction;
	}
	
	public int defaultMMPChangedActionProjectValue() {
		return defaultMMPChangedAction;
	}

	public boolean incrementalBuilderEnabledProjectValue() {
		return useIncrementalBuilder;
	}

	public boolean useKeepGoingProjectValue() {
		return useKeepGoing;
	}

	public boolean useDebugModeProjectValue() {
		return useDebugMode;
	}

	public boolean overrideMakeEngineProjectValue() {
		return overrideMakeEngine;
	}

	public String makeEngineProjectValue() {
		return makeEngineToUse;
	}
	
	public String extraSBSv2ArgsProjectValue() {
		return extraSBSv2Args;
	}
	
}
