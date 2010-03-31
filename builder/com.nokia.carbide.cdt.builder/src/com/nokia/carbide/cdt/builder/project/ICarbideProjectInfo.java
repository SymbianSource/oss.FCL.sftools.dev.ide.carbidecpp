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
package com.nokia.carbide.cdt.builder.project;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

/**
 * This interface represents the base project info for a single Carbide.c++ project. A Carbide.c++ project
 * is made up of global data store in a .cproject file as well as configuration data stored in the
 * \.settings\.carbide_build_settings file.
 * 
 * @see ICarbideProjectInfo CarbideBuildManager.getProjectInfo(IProject)
 *
 */
public interface ICarbideProjectInfo {
	
	// String setting values
	public static final String PROJECT_RELATIVE_INFFILE_PROPS_KEY = "infFileLocation"; //$NON-NLS-1$
	public static final String BLD_FROM_INF_PROPS_KEY = "buildFromInf"; //$NON-NLS-1$
	public static final String INF_COMPONENTS_PROPS_KEY = "infBuildComponents"; //$NON-NLS-1$
	public static final String TEST_COMPONENT_LABEL = "[test component]"; //$NON-NLS-1$
	
	public static final String BINARY_PARSER_EXT_POINT_ID =  "org.eclipse.cdt.core.BinaryParser"; //$NON-NLS-1$
	
	public static final String[] REQUIRED_BINARY_PARSER_IDS = { "com.nokia.cdt.debug.cw.symbian.SymbianE32",
															    "org.eclipse.cdt.core.PE",
															    "org.eclipse.cdt.core.ELF"
															   }; //$NON-NLS-1$
	
	public static final int CLEAN_LEVEL_1 = 0;
	public static final int CLEAN_LEVEL_2 = 1;
	public static final int CLEAN_LEVEL_3 = 2;

	public static final int ACTION_NONE = 0;
	public static final int ACTION_LINK_ONLY = 1;
	public static final int ACTION_COMPILE_AND_LINK = 2;

	/**
	 * Load all the configurations associated with this project. If this is a new project,
	 * then a dummy .settings file will be created which can be used to generate new build
	 * configurations.
	 * @return A list of ICarbideBuildConfiguration objects, which may be empty
	 */
	public List<ICarbideBuildConfiguration> getBuildConfigurations();
	
	/**
	 * Get an ICarbideBuildConfiguration object from a display name.
	 * @param configName - The display name for the build configuration
	 * @return - An ICarbideBuildConfiguration. Returns null if the configuration does not exist.
	 */
	public ICarbideBuildConfiguration getNamedConfiguration(String configName);

	/**
	 * Get the default configuration for the current project. The default configuration is the build configuration
	 * that gets executed on a project when build gets executed (e.g. Project > Build)
	 * @return A valid ICarbideBuildConfiguration. Returns null if no configurations exist.
	 */
	public ICarbideBuildConfiguration getDefaultConfiguration();
	
	/**
	 * Get the configuration/display name for the currently selected build configuration.
	 * @return A String of the display name.
	 */
	public String getDefaultBuildConfigName();
	
	/**
	 * Check whether or not all components of the inf are built or subcomponents.
	 * Building from inf means that only 'abld build' is invoked rather than invoking 'abld build' on specific MMP and makefiles
	 * @return true when building from bld.inf
	 */
	public boolean isBuildingFromInf();
	
	/**
	 * Return the full path to the bld.inf file for the project.
	 * @return IPath with the bld.inf file
	 */
	public IPath getAbsoluteBldInfPath();
	
	/**
	 * Get a path to the bld.inf file that is relative to the project root.
	 * @return IPath
	 */
	public IPath getProjectRelativeBldInfPath();
	
	/**
	 * Get a path to the bld.inf file that is relative to the workspace root.
	 * @return IPath
	 */
	public IPath getWorkspaceRelativeBldInfPath();
	
	/**
	 * Get the name of the MMP file that is used to calculate the target output (final artifact) for setting up launch configurations
	 * @return
	 * 
	 * @deprecated no longer used in 1.3.  now returns an empty string.
	 */
	public String getMMPTargetFile();
	
	/**
	 * Get the IProject for the Carbide.c++ project.
	 * @return IProject object
	 */
	public IProject getProject();
	
	/**
	 * Get a list of the names of the bld.inf components as they come from the settings.
	 * Test components (PRJ_TESTMMPFILES) contain a suffix (" [test component]") to identify a particular
	 * component as being a test component.
	 * @return A list of strings with the components names as they are from the .cproject file
	 * @see getInfBuildComponents() for the names with test suffix cleansed.
	 */
	public List<String> getInfBuildComponentsRawSettings();
	
	/**
	 * Get a list of all the names of the bld.inf components to be built.
	 * @return A List of component names with extension.
	 * @see isBuildingFromInf()
	 */
	public List<String> getInfBuildComponents();
	
	/**
	 * Get the list of PRJ_MMPFILES that are currently being built for the project. This list will only be populated when
	 * <code>ICarbideProjectInfo.isBuildingFromInf()</code> returns true, otherwise it will be an empty list.
	 * @return A list of Strings with the component names as they appear in the bld.inf file
	 * @see com.nokia.carbide.cdt.builder.EpocEngineHelper.getMakMakeFiles(IPath, List<ISymbianBuildContext>, 
		List<IPath>, List<IPath>, IProgressMonitor )
	 */
	public List<String> getNormalInfBuildComponents();
	
	/**
	 * Get the list of PRJ_TESTMMPFILES that are currently being built for the project. This list will only be populated when
	 * ICarbideProjectInfo#isBuildingFromInf() returns true, otherwise it will be an empty list.
	 * @return A list of Strings with the component names as they appear in the bld.inf file
	 * @see com.nokia.carbide.cdt.builder.EpocEngineHelper.getMakMakeFiles(IPath, List<ISymbianBuildContext>, 
		List<IPath>, List<IPath>, IProgressMonitor )
	 */
	public List<String> getTestInfBuildComponents();
		
	/**
	 * Get the working directory of the bld.inf file for the default configuration. This may change for a project if
	 * a bld.inf file is on a different drive than the EPOCROOT of the SDK, but only if
	 * they only differ in the drive spec. For example, a bld.inf of "m:\myproj\group\bld.inf"
	 * and an EPOCROOT of "x:\", the IPath will be returned as "x:\myproj\group\". Otherwise,
	 * the original path is returned, "m:\myproj\group\".
	 * @return the absolute path of the bld.inf in the local file system,
	 *  or <code>null</code> if no path can be determined
	 */
	public IPath getINFWorkingDirectory();

	/**
	 * Returns int value for the clean level for the project
	 * @return {@value #CLEAN_LEVEL_1} for clean, {@value #CLEAN_LEVEL_2} for reallyclean, {@value #CLEAN_LEVEL_3}
	 * for reallyclean/bldmake clean
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 1.3
	 */
	public int getCleanLevel();

	/**
	 * Get the project setting that determines whether or not test components (PRJ_TESTMMPFILES)
	 * should be built when building from bld.inf.
	 * @return true when enabled.
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 */
	public boolean isBuildingTestComps();
	
	/**
	 * Get the project setting that determines whether or not Carbide manages the generated makefiles
	 * or leaves it all up to the Symbian command line build system.
	 * @return true when enabled
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 1.3
	 */
	public boolean areMakefilesManaged();
	
	/**
	 * Get the project setting that determines whether or not Carbide takes advantage of the make "-j" option
	 * which will run make jobs in parallel.
	 * @return true when enabled
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 1.3
	 */
	public boolean isConcurrentBuildingEnabled();

	/**
	 * Get the project setting for the maximum number or parallel make jobs.
	 * @return the number of jobs
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 1.3
	 */
	public int concurrentBuildJobs();

	/**
	 * Get the project setting that determines whether or not Carbide should ask the user what action to take
	 * when building and mmp files have changed.
	 * @return true when enabled
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 1.3
	 */
	public boolean promptForMMPChangedAction();

	/**
	 * Returns int value for the mmp action type for the project.
	 * @return {@value #ACTION_NONE}, {@value #ACTION_LINK_ONLY}, or {@value #ACTION_COMPILE_AND_LINK}
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 1.3
	 */
	public int defaultMMPChangedAction();

	/**
	 * Get the project setting that determines whether or not the Eclipse incremental builder is to be used.
	 * @return true when enabled
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 1.3
	 */
	public boolean incrementalBuilderEnabled();

	/**
	 * Get the project setting that determines whether or not SBSv2 should keep going even when
	 * some build commands fail.
	 * @return true when enabled
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 2.0
	 */
	public boolean useKeepGoing();
	
	/**
	 * Get the project setting that determines whether or not SBSv2 should run in debug mode.
	 * @return true when enabled
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 2.0
	 */
	public boolean useDebugMode();
	
	/**
	 * Get the project setting that determines whether or not to override the make engine used
	 * by SBSv2.
	 * @return true when enabled
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 2.0
	 */
	public boolean shouldOverrideMakeEngine();
	
	/**
	 * Get the project setting that is the name of the make engine to be used by SBSv2.
	 * @return name of the make engine to use
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 * 
	 * @since 2.0
	 */
	public String makeEngineToUse();
	
	/**
	 * Get arbitrary additional arguments to pass to sbs (Raptor) on the command-line
	 * 
	 * Note that the workspace setting is returned unless it is overridden by the project setting
	 *  
	 * @since 2.2
	 */
	public String extraSBSv2Args();
	
	/**
	 * Appends arbitrary text to a default Carbide/Raptor configuration (the -c parameter). The default name is always given, e.g. armv5_urel, but users can 
	 * append whatever they want to change the behavior of the build. 
	 * @return the string to append the text to for the Raptor -c parameter
	 * 
	 * @since 2.6
	 */
	public String buildConfigAppender();
}
