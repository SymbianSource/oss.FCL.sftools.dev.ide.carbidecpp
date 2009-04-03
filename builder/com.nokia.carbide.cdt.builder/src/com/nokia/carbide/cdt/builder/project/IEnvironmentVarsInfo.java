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

import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

/**
 * IEnvironmentVarsInfo represents the set of all environment variables for a given ICarbideBuildConfiguration object.
 * @see IEnvironmentVariable, ICarbideBuildConfiguration
 *
 */
public interface IEnvironmentVarsInfo {
	
	/**
	 * Get the list of environment variables that are modified from their default value.
	 * This ONLY returns the list of variables that are stored in the configuraiton settings
	 * that differ from the default values
	 * @return The list of modified environment variables
	 */
	List<IEnvironmentVariable> getModifiedEnvVarsListFromSettings();
	
	/**
	 * Update the entire variable list, which upates the interface to the DOM as well
	 *
	 */
	void setModifiedEnvVarsList(List<IEnvironmentVariable> envVarsList);
		
	/**
	 * The fully resolved list of environment variables that can be used to build for a configuration.
	 * Use this to construct a full environment for a particular configuraiton's process invocation
	 * @return A String array of environment variables of form &lt;var&gt;=&lt;value&gt;
	 */
	String[] getResolvedEnvironmentVariables();
	
	/**
	 * Get the array of variables (resolved) that differ from the default. This includes both
	 * those modified in the settings and those that Carbide implicity modifies such as EPOCROOT and PATH
	 * @return An array of string values that differ from original of form &lt;var&gt;=&lt;value&gt;
	 */
	String[] getModifiedEnvironmentVariables();
	
	/**
	 * Get the list of environment variables for configuration/platform
	 * @param carbideProject - The current project
	 * @param context - The current context to get the vars for.
	 * @return An array a resolved environment variables of format &lt;var&gt;=&lt;value&gt.
	 */
	String[] getDefaultEnvVarsSettings(ICarbideProjectInfo carbideProject, ISymbianBuildContext context);
	
	/**
	 * Get the list of list of environment variables that are modifed from default
	 * @param carbideProject - current carbide project
	 * @param context - platform we are building for
	 * @return - The list of modified variables that are created as defaults.
	 */
	List<IEnvironmentVariable> getDefaultEnvVarsList(ICarbideProjectInfo carbideProject, ISymbianBuildContext context);
	
	/**
	 * Get an IEnvironmentVariable from the configuration from a variable name. This is only Win32 OS specific
	 * so variable name checks are case insensitive. Only variables that have overrides from the default environment
	 * value are returned.
	 * @param varName - The variable name to check to see if overridden.
	 * @return IEnvironmentVariable, if defined in the configuration, otherwise null meaning that the default is used.
	 */
	IEnvironmentVariable getEnvVarFromConfiguration(String varName);
	
}
