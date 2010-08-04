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
package com.nokia.carbide.cdt.internal.api.builder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.cdt.core.ICExtensionReference;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICStorageElement;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationData;
import org.eclipse.cdt.core.settings.model.extension.CConfigurationDataProvider;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.osgi.framework.Version;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.builder.project.IEnvironmentVariable;
import com.nokia.carbide.cdt.builder.project.IEnvironmentVarsInfo;
import com.nokia.carbide.cdt.internal.builder.BuildConfigurationData;
import com.nokia.carbide.cdt.internal.builder.CarbideBuildConfiguration;
import com.nokia.carbide.cdt.internal.builder.CarbideProjectInfo;
import com.nokia.carbide.cdt.internal.builder.EnvironmentVarsInfo;
import com.nokia.carbide.cdt.internal.builder.EnvironmentVarsInfo2;
import com.nokia.carbide.cdt.internal.builder.SISBuilderInfo;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType;
import com.nokia.carbide.cdt.internal.builder.xml.CarbideBuildConfigurationLoader;
import com.nokia.carbide.cpp.internal.api.sdk.BuildArgumentsInfo;
import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv1;
import com.nokia.carbide.cpp.internal.api.sdk.BuildContextSBSv2;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildConfigInfo;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.SBSv2BuilderInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.core.SymbianSDKFactory;

/**
 * Main interface point with CDT with regards to our build configurations.  Note that
 * we manage the creation/removal of configs.  We just have to return CConfigurationData
 * for configs so CDT can use it to get interesting data for build configs like binary/error
 * parsers, include paths and macros, etc.
 *
 */
public class CarbideConfigurationDataProvider extends CConfigurationDataProvider {

	public static final String BUILD_SYSTEM_ID = "com.nokia.carbide.cdt.builder.CarbideConfigurationDataProvider"; //$NON-NLS-1$

	protected static final String BUILD_CONFIG_ROOT_ELEMENT_OPEN = "<CarbideBuilderConfigInfo>"; //$NON-NLS-1$
	protected static final String BUILD_CONFIG_ROOT_ELEMENT_CLOSE = "</CarbideBuilderConfigInfo>"; //$NON-NLS-1$
	protected static final String CARBIDE_BUILD_CONFIG_SETTINGS_FOLDER = ".settings"; //$NON-NLS-1$
	protected static final String CARBIDE_BUILD_CONFIG_SETTINGS_FILE = ".carbide_build_settings"; //$NON-NLS-1$

	
	@Override
	public CConfigurationData applyConfiguration(
			ICConfigurationDescription des,
			ICConfigurationDescription baseDescription,
			CConfigurationData baseData, IProgressMonitor monitor)
			throws CoreException {

		ICProjectDescription projDes = des.getProjectDescription();
		if (projDes != null) {
			IProject project = projDes.getProject();
			ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
			if (cpi != null) {
				if (baseData instanceof BuildConfigurationData) {
					ICarbideBuildConfiguration buildConfig = ((BuildConfigurationData)baseData).getConfiguration();
					((CarbideBuildConfiguration)buildConfig).saveToStorage(des);
					return baseData;
				}
			}
		}
		return baseData;
	}

	@Override
	public CConfigurationData createConfiguration(
			ICConfigurationDescription des,
			ICConfigurationDescription baseDescription,
			CConfigurationData baseData, boolean clone, IProgressMonitor monitor)
			throws CoreException {

		// no need to do anything here
		return baseData;
	}

	@SuppressWarnings("restriction")
	@Override
	public CConfigurationData loadConfiguration(ICConfigurationDescription des,
			IProgressMonitor monitor) throws CoreException {

		IProject project = des.getProjectDescription().getProject();
		if (project != null) {
			if (!CarbideBuilderPlugin.getBuildManager().isCarbideProject(project)) {
				throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, IStatus.OK, "Project " + project.getName() + " is not a valid Carbide project.", null));
			}
			
			ISymbianBuildContext context = null;
			if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(project)){
				
				context = loadSBSv2Configuration(des, monitor);
				
				if (context == null){
					throw new CoreException(new Status(IStatus.ERROR,
							CarbideBuilderPlugin.PLUGIN_ID, IStatus.OK,
							"Unable to load Carbide settings for project "
									+ project.getProject().getName(), null));
				}
			} else {
				// Presume it's SBSv1
				// find the configuration that matches the id (sdk, platform, target)
				String configId = des.getConfiguration().getId();
				context = BuildContextSBSv1.getBuildContextFromDisplayName(configId);
			}
			
			if (context == null) {
				throw new CoreException(new Status(IStatus.ERROR, CarbideBuilderPlugin.PLUGIN_ID, IStatus.OK, "SDK specified in project " + project.getName() + " is not installed, please set it up from project property", null));
			}

			CarbideBuildConfiguration newConfig = new CarbideBuildConfiguration(project, context);
			
			try {
				newConfig.loadFromStorage(des);
			} catch (CoreException e) {
				CarbideBuilderPlugin.log(e.getStatus());
			}
			
			return newConfig.getBuildConfigurationData();
		}

		return null;
	}

	@SuppressWarnings("restriction")
	private ISymbianBuildContext loadSBSv2Configuration(ICConfigurationDescription des, 
														IProgressMonitor monitor) {
		
		ICStorageElement rootStorage;
		try {
			rootStorage = des.getStorage(CarbideBuildConfiguration.CARBIDE_STORAGE_ID, false);
		} catch (CoreException e) {
			return null;
		}
		String configID = des.getConfiguration().getId();
		String buidAlias = null;
		String platform = null;
		String target = null;
		String displayString = null;
		String sdkID = null;
		if (rootStorage != null) {
			for (ICStorageElement se : rootStorage.getChildren()) {
				if (se.getName().equals(
						ISBSv2BuildContext.SBSV2_DATA_ID)) {
					
					SBSv2BuilderInfo sbsv2BuilderInfo = new SBSv2BuilderInfo();
					sbsv2BuilderInfo.loadFromStorage(se);
					platform = sbsv2BuilderInfo.getSBSv2Setting(ISBSv2BuildConfigInfo.ATRRIB_CONFIG_BASE_PLATFORM);
					target = sbsv2BuilderInfo.getSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_CONFIG_TARGET);
					buidAlias = sbsv2BuilderInfo.getSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_BUILD_ALIAS);
					displayString = sbsv2BuilderInfo.getSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_CONFIG_DISPLAY_STRING);				
					sdkID = sbsv2BuilderInfo.getSBSv2Setting(ISBSv2BuildConfigInfo.ATTRIB_SBSV2_SDK_ID);
				}
			}
		} else {
			return null;
		}
		
		ISymbianSDK sdk = null;
		if (!configID.startsWith(ISBSv2BuildContext.BUILDER_ID)){
			// pre-C3 (Carbide 2.x) project, get SDK id from config name
			if (displayString == null){
				displayString = configID;
			} 
			if (sdkID == null){
				sdkID = BuildContextSBSv2.getSDKIDFromV1ConfigName(displayString);
			}
			if (platform == null){
				platform = BuildContextSBSv2.getPlatformFromV1ConfigName(displayString);
			}
			if (target == null){
				target = BuildContextSBSv2.getTargetFromV1ConfigName(displayString);
			}
			if (buidAlias == null){
				buidAlias = BuildContextSBSv2.getBuildAliasFromV1ConfigName(displayString);
			}
		}
		if (sdkID != null){
			sdk = SDKCorePlugin.getSDKManager().getSDK(sdkID, true);
			if (sdk != null){
				return new BuildContextSBSv2(sdk, platform, target, buidAlias, displayString, configID);
			} else {
				ISymbianSDK deadSDK = SymbianSDKFactory.createInstance(sdkID, "FIXME", new Version("9.5"));
				SDKCorePlugin.getSDKManager().addSDK(deadSDK);
				
				return new BuildContextSBSv2(deadSDK, platform, target, buidAlias, displayString, configID);
			}
		}
		
		return null;
		
	}

	@Override
	public void removeConfiguration(ICConfigurationDescription des,
			CConfigurationData data, IProgressMonitor monitor) {

		// no need to do anything here
	}

	protected static CarbideBuilderConfigInfoType getOldConfigInfo(IProject project) {

		CarbideBuilderConfigInfoType buildConfigType = null;
		
		File configSettingsFile = getConfigurationSettingsFile(project);
		
		if (configSettingsFile.exists()) {
			try {
				buildConfigType = CarbideBuildConfigurationLoader.loadBuildConfigurations(configSettingsFile.toURL());
				
				// check to make sure that the settings version is up to date...
				if (buildConfigType.getVersion() != CarbideBuildConfigurationLoader.SETTINGS_CURRENT_VERSION) {
					convertSettingsData(project, buildConfigType);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				CarbideBuilderPlugin.log(e);
				return null;
			}
		}
		
		return buildConfigType;
	}
	
	protected static void convertSettingsData(IProject project, CarbideBuilderConfigInfoType buildConfigType) {
		
		if (buildConfigType.getVersion() == CarbideBuildConfigurationLoader.SETTINGS_VERSION_0) {
			// Iterate through all the configurations and convert from version 0 to 1.
			// Reset all PATH, EPOCROOT, and MW* variables set back to their defaults
			// as they are computed dynamically now.
			for (Iterator i = buildConfigType.getConfiguration().iterator(); i.hasNext();) {
				ConfigurationType currConfig = (ConfigurationType)i.next();
				
				@SuppressWarnings("restriction")
				ISymbianBuildContext context = BuildContextSBSv1.getBuildContextFromDisplayName(currConfig.getName());
				IEnvironmentVarsInfo envSettings = new EnvironmentVarsInfo(project, context, currConfig.getEnvVars());
				List<IEnvironmentVariable> varsFromSettings = envSettings.getModifiedEnvVarsListFromSettings();
				List<IEnvironmentVariable> updatedEnvList = new ArrayList<IEnvironmentVariable>();
				if (varsFromSettings != null) {
					for (IEnvironmentVariable currVar : varsFromSettings) {
						if (currVar.getName().toUpperCase().equals(IEnvironmentVariable.PATH_ENV_VAR_NAME) || 
							currVar.getName().toUpperCase().equals(IEnvironmentVariable.EPOCROOT_ENV_VAR_NAME) ||
							currVar.getName().toUpperCase().equals(IEnvironmentVariable.MWCSYM2INCLUDES_ENV_VAR_NAME) ||
							currVar.getName().toUpperCase().equals(IEnvironmentVariable.MWCSYM2LIBRARIES_ENV_VAR_NAME) ||
							currVar.getName().toUpperCase().equals(IEnvironmentVariable.MWCSYM2LIBRARYFILES_ENV_VAR_NAME)) {
							// removing variable to reset back to default
						} else {
							updatedEnvList.add(currVar);
						}
					}
					envSettings.setModifiedEnvVarsList(updatedEnvList);
				}
			}
		}
	}

	public static File getConfigurationSettingsFile(IProject project) {
		
		final IFolder settingsFolder = project.getFolder(new Path(CARBIDE_BUILD_CONFIG_SETTINGS_FOLDER));
		if (!settingsFolder.exists()) {
			    // Don't use IFolder to create the folder here or you will get a conflict with the indexer (bug #3332)
				settingsFolder.getLocation().toFile().mkdirs();
				//settingsFolder.create(true, true, null);
		}
		
		File settingsFile = new File(settingsFolder.getLocation() + File.separator + CARBIDE_BUILD_CONFIG_SETTINGS_FILE);
		if (!settingsFile.exists()) {
			try {
				// New config file, need to write outer node to keep parser happy
				settingsFile.createNewFile();
				FileWriter f = new FileWriter(settingsFile);
				f.write(BUILD_CONFIG_ROOT_ELEMENT_OPEN + BUILD_CONFIG_ROOT_ELEMENT_CLOSE);
				f.close();
			}catch (IOException e) {
				e.printStackTrace();
				CarbideBuilderPlugin.log(e);
			}
				Job createFolderJob = new Job("Refresh setting folder") { //$NON-NLS-1$
					protected IStatus run(IProgressMonitor monitor) {
						try {
							// and make Eclipse happy too :)
							// Refresh the folder in a job or you'll get conflicts with the indexer (bug# 3332)
							settingsFolder.refreshLocal(IFolder.DEPTH_ONE, null);
						} catch (CoreException e) {
							e.printStackTrace();
							CarbideBuilderPlugin.log(e);
						}
						return new Status(IStatus.OK, CarbideBuilderPlugin.PLUGIN_ID, IStatus.OK, ".setting folder refreshed", null); 
					}
				};
				createFolderJob.setPriority(Job.SHORT);
				ISchedulingRule rule = project;
				createFolderJob.setRule(rule);
				createFolderJob.schedule();
		}
		return settingsFile;
	}
	
	@SuppressWarnings("deprecation")
	public static void convertProject(ICProjectDescription projDes, ICExtensionReference[] cext) throws CoreException {
		if (cext.length > 0) {
			ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().createProjectInfo(projDes);
			if (cpm != null) {
				boolean wasVerboseChecked = false;
				
				// convert the settings
				for (int i = 0; i < cext.length; i++) {
					String orig = cext[i].getExtensionData(ICarbideProjectInfo.PROJECT_RELATIVE_INFFILE_PROPS_KEY);
					if (orig != null) {
						cpm.writeProjectSetting(ICarbideProjectInfo.PROJECT_RELATIVE_INFFILE_PROPS_KEY, orig);
					}
					
					orig = cext[i].getExtensionData(ICarbideProjectInfo.BLD_FROM_INF_PROPS_KEY);
					if (orig != null) {
						cpm.writeProjectSetting(ICarbideProjectInfo.BLD_FROM_INF_PROPS_KEY, orig);
					}

					orig = cext[i].getExtensionData(ICarbideProjectInfo.INF_COMPONENTS_PROPS_KEY);
					if (orig != null) {
						cpm.writeProjectSetting(ICarbideProjectInfo.INF_COMPONENTS_PROPS_KEY, orig);
					}

					orig = cext[i].getExtensionData("cleanCommand");
					if (orig != null) {
						int cleanLevel = ICarbideProjectInfo.CLEAN_LEVEL_1;
						if (orig.startsWith("really")) {
							cleanLevel = ICarbideProjectInfo.CLEAN_LEVEL_2;
						}
						cpm.writeProjectSetting(CarbideProjectInfo.CLEAN_LEVEL, Integer.toString(cleanLevel));
					}
					
					// convert verbose.  just add -v to the abld build arguments
					orig = cext[i].getExtensionData("verboseFlag");
					if (orig != null) {
						if (orig.compareToIgnoreCase("true") == 0) {
							wasVerboseChecked = true;
						}
					}

					orig = cext[i].getExtensionData(CarbideProjectInfo.BUILD_TEST_COMPS_PROPS_KEY);
					if (orig != null) {
						cpm.writeProjectSetting(CarbideProjectInfo.BUILD_TEST_COMPS_PROPS_KEY, orig);
					}
				}
				
				// override the workspace settings so they're using the same settings they had previously
				cpm.writeProjectSetting(CarbideProjectInfo.OVERRIDE_WORKSPACE_SETTINGS_KEY, "true");
				
				// now create the configs
				IProject project = projDes.getProject();
				
				CarbideBuilderConfigInfoType oldConfigInfo = getOldConfigInfo(project);
				if (oldConfigInfo != null) {

					// get the list of existing configs
					List<ISymbianBuildContext> configs = new ArrayList<ISymbianBuildContext>();
					for (Iterator i = oldConfigInfo.getConfiguration().iterator(); i.hasNext();) {
						ConfigurationType currConfig = (ConfigurationType)i.next();
						
		    			@SuppressWarnings("restriction")
						ISymbianBuildContext context = BuildContextSBSv1.getBuildContextFromDisplayName(currConfig.getName());
						if (context != null) {
							configs.add(context);
						}
					}
					
					// sort the list alphabetically
					Collections.sort(configs, new Comparator<ISymbianBuildContext>() {

						public int compare(ISymbianBuildContext o1, ISymbianBuildContext o2) {
							return o1.getDisplayString().compareTo(o2.getDisplayString());
						}
			    		
			    	});

					// now create a new config for each of the old ones, converting the settings
					boolean isDefaultConfig = true; // only the first one is default
					for (ISymbianBuildContext context : configs) {
						String contextId = context.getDisplayString();
						
						for (Iterator i = oldConfigInfo.getConfiguration().iterator(); i.hasNext();) {
							ConfigurationType currConfig = (ConfigurationType)i.next();
							
							if (contextId.equals(currConfig.getName())) {
				    			CarbideBuildConfiguration buildConfiguration = (CarbideBuildConfiguration)cpm.createNewConfiguration(context, isDefaultConfig);

				    			// if the old pkg file was (none) then ignore
				    			SISBuilderInfo oldSisInfo = new SISBuilderInfo(project, currConfig.getSisBuilder(), context.getSDK());
								if (!oldSisInfo.getPKGFileString().equals("(none)")) {
					    			buildConfiguration.getSISBuilderInfoList().add(new SISBuilderInfo2(oldSisInfo));
								}
								
				    			EnvironmentVarsInfo oldEnvVars = new EnvironmentVarsInfo(project, context, currConfig.getEnvVars());
				    			buildConfiguration.setEnvironmentVarsInfo(new EnvironmentVarsInfo2(oldEnvVars));
				    			
				    			if (context instanceof ISBSv1BuildContext)
				    			if (wasVerboseChecked) {
				    				((ISBSv1BuildContext)context).setBuildArgumentsInfo(new BuildArgumentsInfo("", "", "-v", "", "", "", "", "", "", "-v", "-v"));
				    			}
				    			
								buildConfiguration.saveConfiguration(false);

								isDefaultConfig = false;
								break;
							}
						}
					}
					
		    		// rename the .carbide_build_settings file so the user knows it's no longer used
		    		File oldSettingsFile = getConfigurationSettingsFile(project);
		    		if (oldSettingsFile.exists()) {
		    			oldSettingsFile.renameTo(new File(oldSettingsFile.getAbsolutePath() + "_obsolete")); //$NON-NLS-1$
		    		}
				}
			}
		}
	}
}
