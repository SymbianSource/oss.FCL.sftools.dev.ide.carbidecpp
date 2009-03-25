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

import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

/**
 * Interface used to make modifications to a Carbide.c++ project.
 *
 */
public interface ICarbideProjectModifier extends ICarbideProjectInfo {

	/**
	 * Creates a new configuration and writes it to disk. Given the input data, this should create a minimal build configuration that
	 * will have the build, error parsers, and environment written so a basic build can be performed after it's creation
	 * @return ICarbideBuildConfiguration, or null if the settings file does not exist.
	 */
	public ICarbideBuildConfiguration createNewConfiguration(ISymbianBuildContext context, boolean makeDefault);  
	
	/**
	 * Deletes a configuration and all settings data. If the configuration is the default the first configuration
	 * on the list becomes the default.
	 * @param config - A valid ICarbideBuildConfiguration object
	 * @return true if the configuration exists and was removed successfully
	 */
	public boolean deleteConfiguration(ICarbideBuildConfiguration config);

	/**
	 * Set the default Carbide.c++ build configuration for a given project.
	 * @param config - A valid Carbide.c++ build configuration for a project
	 * @return true if the configuration exists and it set
	 */
	public boolean setDefaultConfiguration(ICarbideBuildConfiguration config);
	
	/**
	 * Write a Carbide.c++ project setting name and value. Items written to the .cproject file and are project-wide settings
	 * @param settingName - The 'key' value
	 * @param settingValue - Value for the 'key'
	 */
	public void writeProjectSetting(String settingName, String settingValue);
	
	/**
	 * Apply all changes made since creation.  Must be called for any changes made
	 * to be applied.
	 * @return true if the changes were saved successfully, false otherwise
	 */
	public boolean saveChanges();
	
}
