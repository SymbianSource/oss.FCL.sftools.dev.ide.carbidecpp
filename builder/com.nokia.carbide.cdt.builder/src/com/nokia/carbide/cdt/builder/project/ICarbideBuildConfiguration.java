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

import com.nokia.carbide.cdt.builder.BuildArgumentsInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

/**
 * An ICarbideBuildConfiguration interface represents on buildable target for a project. A single
 * Carbide.c++ project (ICarbideProjectInfo) contains 1 to N ICarbideBuildConfigation objects.
 *
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICarbideBuildConfiguration extends ISymbianBuildContext {

	/** Integer identifier for the set of parser to be used for building the WINSCW platform */
    public static final int ERROR_PARSERS_WINSCW = 1;
    /** Integer identifier for the set of parser to be used for building the GCCE platform */
    public static final int ERROR_PARSERS_GCCE = 2;
    /** Integer identifier for the set of parser to be used for building the ARMVx platforms */
    public static final int ERROR_PARSERS_ARMVx = 3;
    /** Integer identifier for the set of parser to be used for building with makesis, makekeys, and signsis */
    public static final int ERROR_PARSERS_SIS_BUILDER = 4;
    /** Integer identifier for the set of parser to be used for building the ARMI, THUMB, and ARM4 platforms */
    public static final int ERROR_PARSERS_ARM_EKA1 = 5;
    /** Integer identifier for the set of parser to be used when calling bldmake bldfiles platform */
    public static final int ERROR_PARSERS_BLDMAKE_MAKE = 6;
    /** Integer identifier for the set of parser to be used for building ROM images */
    public static final int ERROR_PARSERS_ROM_BUILDER = 7;
    /** Integer identifier to use all available parsers */
    public static final int ERROR_PARSERS_ALL = 99;
	
	/**
	 * Get a list of error parser ID's
	 * @return A full list of error parser IDs. Returns an empty array if none are specified
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
	 */
	BuildArgumentsInfo getBuildArgumentsInfoCopy();
	
	/**
	 * Set the build arguments info for SBSv2 build arguments. This only sets values in memory, does
	 * not write settings to disk. See 
	 * @return IBuildArgumentsInfo instance, never null
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
	
	/**
	 * Get the id that specifies the array of error parsers to use for a given build platform.
	 * @return The integer ID of the parser to be use.
	 * @see CarbideCPPBuilder.getParserIds(int id)
	 */
	int getErrorParserId();
	
	/**
	 * Returns the list of all built in macros for this configuration
	 * <p>
	 * Macros will be just a name, e.g. "_DEBUG", "__SYMBIAN32__", etc..
	 * </p>
	 *
	 * @return a list of macros which may be empty.
	 */
	List<String> getBuiltinMacros();
	
	/**
	 * Returns the ROM builder info from the ROM Builder tab.
	 * @return
	 */
	IROMBuilderInfo getROMBuildInfo();
	
	/**
	 * Compares two configurations to see if their display names are equivalent.
	 */
	boolean equals(Object obj);
	
}
