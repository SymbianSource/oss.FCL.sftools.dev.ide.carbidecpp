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

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cdt.builder.BuildArgumentsInfo;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

/**
 * An ICarbideBuildConfiguration interface represents on buildable target for a project. A single
 * Carbide.c++ project (ICarbideProjectInfo) contains 1 to N ICarbideBuildConfigation objects.
 *
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICarbideBuildConfiguration {

	/** Integer identifier for the set of parser to be used for building the WINSCW platform */
    public static final int ERROR_PARSERS_WINSCW = 1;
    /** Integer identifier for the set of parser to be used for building the GCCE platform */
    public static final int ERROR_PARSERS_GCCE = 2;
    /** Integer identifier for the set of parser to be used for building the ARMVx platforms */
    public static final int ERROR_PARSERS_ARMVx = 3;
    /** Integer identifier for the set of parser to be used for building with makesis, makekeys, and signsis */
    public static final int ERROR_PARSERS_SIS_BUILDER = 4;
    /** Integer identifier for the set of parser to be used for building the ARMI, THUMB, and ARM4 platforms */
    public static final int ERROR_PARSERS_BLDMAKE_MAKE = 6;
    /** Integer identifier for the set of parser to be used for building ROM images */
    public static final int ERROR_PARSERS_ROM_BUILDER = 7;
    /** Integer identifier to use all available parsers */
    public static final int ERROR_PARSERS_ALL = 99;
	
	/**
	 * Get a list of error parser ID's for this configuration.
	 * @return A full list of error parser IDs. Returns an empty array if none are specified
	 * @see {@link CarbideCPPBuilder#getParserIdArray(int)}
	 */
	String[] getErrorParserList();

	/**
	 * Writes this configuration to the .settings\file. If the data does no exist it will
	 * be created. Otherwise the configuration data will be updated.
	 * @param refreshFileSystem - Set to true if eclipse needs to know about changes to the file system. Set to false if there's possibility thata the ressource tree is locked on the project (e.g. the project is being created)
	 * @return true on success, false otherwise.
	 */
	boolean saveConfiguration(boolean refreshFileSystem);
	
	/** 
	 * Get the modifiable list of SIS builder info. Contains pref settings used to generated SIS files from PKG files
	 * @return ISiSBuilderInfo list, which may be empty
	 */
	List<ISISBuilderInfo> getSISBuilderInfoList();
	
	/**
	 * Get the (abld)build arguments info.  Contains pref settings from the Arguments tab.
	 * This only applies when building with SBSv1 (bldmake, abld)
	 * @return IBuildArgumentsInfo instance, never null
	 * 
	 * @deprecated use 
	 */
	IBuildArgumentsInfo getBuildArgumentsInfo();
	
	/**
	 * Get the (abld)build arguments info.  Contains pref settings from the Arguments tab.
	 * This only applies when building with SBSv1 (bldmake, abld)
	 * @return A copy of BuildArgumentsInfo instance, never null
	 * @deprecated - ABLD specific implementation. ABLD will be removed in a future Carbide 3.x release.
	 */
	BuildArgumentsInfo getBuildArgumentsInfoCopy();
	
	/**
	 * Set the build arguments info for SBSv2 build arguments. This only sets values in memory, does
	 * not write settings to disk. See 
	 * @return IBuildArgumentsInfo instance, never null
	 * @deprecated - ABLD specific implementation. ABLD will be removed in a future Carbide 3.x release.
	 */
	void setBuildArgumentsInfo(BuildArgumentsInfo bldArgInfo);
	
	/**
	 * Get the parent Carbide project of this configuration.
	 * @return ICarbideProjectInfo
	 */
	ICarbideProjectInfo getCarbideProject();
	
	/**
	 * Get the environment variables for this configuration.
	 * @return IEnvironmentVarsInfo object.
	 */
	IEnvironmentVarsInfo getEnvironmentVarsInfo();
	
	/** Get the full path of the release directory into the SDK where binaries are built
	 *  NOTE: This does not account for whether or not a project has the FEATUREVARIANT keyword
	 * @return IPath
	 * 
	 * @since 2.6
	 */
	IPath getTargetOutputDirectory();
	
	/**
	 * Retrieve the build context specific info.
	 * @return ISymbianBuildContext
	 * @since 3.0
	 */
	ISymbianBuildContext getBuildContext();
	
	/** ISymbianBuildContext wrapper
	 *  @since 3.0 */
	String getDisplayString();
	
	/** ISymbianBuildContext wrapper
	 * @since 3.0 */
	ISymbianSDK getSDK();
	
	/** ISymbianBuildContext wrapper 
	 * @since 3.0 */
	String getPlatformString();
	
	/** ISymbianBuildContext wrapper
	 * @since 3.0 */
	String getTargetString();
	
	/** ISymbianBuildContext wrapper
	 * @since 3.0 */
	public IPath getCompilerPrefixFile();
	
	/** ISymbianBuildContext wrapper
	 * @since 3.0 */
	public List<IDefine> getCompilerMacros();
	
	/** ISymbianBuildContext wrapper
	 * @since 3.0 */
	public List<IDefine> getVariantHRHDefines();
	
	/** ISymbianBuildContext wrapper
	 * @since 3.0 */
	public List<File> getPrefixFileIncludes();
	
	/** ISymbianBuildContext wrapper
	 * @since 3.0 */
	public String getBuildVariationName();

	/**
	 * Get the <cconfiguration/> 'id' attribute for this configuration (from .cproject file). This is an ISymbianBuildContext wrapper.
	 * @return String
	 * @since 3.0
	 */
	String getConfigurationID();
	
	/**
	 * Check that at least one MMP in the project configuration has stdcpp support keyword
	 * @return
	 * @since 3.0
	 */
	public boolean hasSTDCPPSupport();
	
}
