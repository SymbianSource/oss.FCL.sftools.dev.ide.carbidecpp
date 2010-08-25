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
* This is a singleton class that allows you to get any Carbide.c++ project. You can get this instance by calling
* <code>CarbideBuilderPlugin.getBuildManager()</code>.
* 
* @see CarbideBuilderPlugin, ICarbideProjectInfo
*/
package com.nokia.carbide.cdt.builder;

import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.core.resources.IProject;

import com.nokia.carbide.cdt.builder.extension.ICarbidePrefsModifier;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;

/**
 * This is a singleton class that allows you to get any Carbide.c++ project. You can get this instance by calling
 * <code>CarbideBuilderPlugin.getBuildManager()</code>.
 * 
 * @see CarbideBuilderPlugin, ICarbideProjectInfo
 *
 */
public interface ICarbideBuildManager {
	
	/**
	 * Test whether or not this has a Carbide.c++ version 1.2 and greater project nature.
	 * @param project - An IProject interface.
	 * @return true if the Carbide.c++ project nature is for version 1.2 and greater.
	 */
	public boolean isCarbideProject(IProject project);
	
	/**
	 * Test whether or not this is a Carbide.c++ version 1.1 or less project.
	 * @param project
	 * @return true if the Carbide.c++ version of project is 1.1 or less. 
	 */
	public boolean isCoronaProject(IProject project);
	
	/**
	 * Test whether or not this has a Carbide.c++ SBSv2 project nature.
	 * @param project - An IProject interface.
	 * @return true if the project has the Carbide.c++ SBSv2 project nature.
	 * @since 2.0
	 */
	public boolean isCarbideSBSv2Project(IProject project);

	/**
	 * Get the ICarbideProjectInfo for a given IProject. This is the main interface to all the project settings.
	 * @param project - An IProject interface.
	 * @return An ICarbideProjectInfo, or null if not found.
	 */
	public ICarbideProjectInfo getProjectInfo(IProject project);
	
	/**
	 * Creates a minimal Carbide.c++ project
	 * @param projDes - An IProjectDescription
	 * @return ICarbideProjectInfo interface on success, otherwise null on failure.
	 */
	public ICarbideProjectModifier createProjectInfo(ICProjectDescription projDes);

	/**
	 * Get the ICarbideProjectModifier for a given IProject. This is the main interface to change any project settings.
	 * @param project - An IProject interface.
	 * @return An ICarbideProjectModifier, or null if not found.
	 */
	public ICarbideProjectModifier getProjectModifier(IProject project);

	/**
	 * Replace the old ICarbideProjectInfo with the new
	 * @param newInfo a copy of the old info with changes applied
	 */
	public void setProjectInfo(ICarbideProjectInfo newInfo);
	
	/**
	 * Retrieve the ICarbidePrefsModifier interface to allow getting and setting
	 * of certain preferences by ID. This only sets values in memory, does not write
	 * to disk.
	 * @return ICarbidePrefsModifier
	 */
	ICarbidePrefsModifier getPrefsModifier();
	
}
