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

/**
 * IBuildArgumentsInfo represents one instance of configuration data for the Arguments tab.
 * @see ICarbideBuildConfiguration
 */
public interface IBuildArgumentsInfo {
	
	/**
	 * Get the arguments to pass to bldmake bldfiles
	 * @return string of arguments which may be empty
	 */
	String getBldmakeBldFilesArgs();
	
	/**
	 * Get the arguments to pass to bldmake clean
	 * @return string of arguments which may be empty
	 */
	String getBldmakeCleanArgs();

	/**
	 * Get the arguments to pass to abld build
	 * @return string of arguments which may be empty
	 */
	String getAbldBuildArgs();

	/**
	 * Get the arguments to pass to abld export
	 * @return string of arguments which may be empty
	 */
	String getAbldExportArgs();

	/**
	 * Get the arguments to pass to abld makefile
	 * @return string of arguments which may be empty
	 */
	String getAbldMakefileArgs();

	/**
	 * Get the arguments to pass to abld library
	 * @return string of arguments which may be empty
	 */
	String getAbldLibraryArgs();

	/**
	 * Get the arguments to pass to abld resource
	 * @return string of arguments which may be empty
	 */
	String getAbldResourceArgs();

	/**
	 * Get the arguments to pass to abld target
	 * @return string of arguments which may be empty
	 */
	String getAbldTargetArgs();

	/**
	 * Get the arguments to pass to abld final
	 * @return string of arguments which may be empty
	 */
	String getAbldFinalArgs();

	/**
	 * Get the arguments to pass to abld clean
	 * @return string of arguments which may be empty
	 */
	String getAbldCleanArgs();

	/**
	 * Get the arguments to pass to abld freeze
	 * @return string of arguments which may be empty
	 */
	String getAbldFreezeArgs();
}
