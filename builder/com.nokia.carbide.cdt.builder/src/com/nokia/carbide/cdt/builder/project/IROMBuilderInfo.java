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
 * IROMBuilderInfo represents one instance of configuration data for the ROM Builder tab.
 * @see ICarbideBuildConfiguration
 */
public interface IROMBuilderInfo {
	
	/**
	 * Get the command line to build the ROM
	 * @return string which may be empty
	 */
	String getCommandLine();
	
	/**
	 * Set the command line with which to build the ROM
	 * @param commandLine
	 */
	void setCommandLine(String commandLine);
	
	/**
	 * Get the working directory to run the build from
	 * @return string which may be empty
	 */
	String getWorkingDirectory();
	
	/**
	 * Set the working directory from which to run the build
	 * @param workingDirectory
	 */
	void setWorkingDirectory(String workingDirectory);

}
