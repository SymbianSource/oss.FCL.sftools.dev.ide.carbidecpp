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
package com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg;

import org.eclipse.core.runtime.IPath;

import java.util.List;
import java.util.Map;

public interface IPKGInstallFile extends IPKGStatement {

	/**
	 * Get the container for the install file statement or null if top-level
	 * statement
	 * 
	 * @return IPKGStatementContainer
	 */
	IPKGStatementContainer getContainer();

	/**
	 * Get the source files as a map of EPKGLanguage to IPath If language
	 * independent install file, only a single entry mapped to
	 * EPKGLanguage#INDEPENDENT returned
	 * 
	 * @see EPKGLanguage
	 * @return Map
	 */
	Map<EPKGLanguage, IPath> getSourceFiles();

	/**
	 * Get the destination file
	 * 
	 * @return IPath
	 */
	IPath getDestintationFile();

	/**
	 * Set the destination file
	 * 
	 * @param destinationFile
	 */
	void setDestinationFile(IPath destinationFile);

	/**
	 * Get the options as list of strings - can be empty
	 * 
	 * @return List
	 */
	List<String> getOptions();

	/**
	 * @return a copy of this
	 */
	IPKGInstallFile copy();

}
