/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen;

import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Version;

/**
 * This represents a single patch to a source file.
 * 
 *
 */
public interface ISourceGenPatch {
	public static final String DESCRIPTION_PREFIX = "@@@ ";
	
	/**
	 * Get a serial number
	 */
	int getSerialNumber();

	/**
	 * Set serial number
	 */
	void setSerialNumber(int number);

	/**
	 * Get description of patch; includes serial number.
	 */
	String getDescription();

	/**
	 * Get the affected component id
	 */
	String getComponentId();

	/**
	 * Get the project-relative path to the file
	 */
	IPath getPath();
	
	/**
	 * Get the semantic location for the patch
	 */
	String getLocationPath();
	
	/**
	 * Get original component version
	 */
	Version getFromVersion();
	
	/**
	 * Get new component version 
	 */
	Version getToVersion();

	/**
	 * Get the unified diff format lines for the patch
	 * @return array of strings in unified diff format, never null
	 */
	String[] getPatchLines();

	/**
	 * Tell whether the patch conflicts with the source.
	 */
	boolean isConflicting();
	
	/**
	 * Get description of conflict.
	 * @return
	 */
	String getConflictMessage();
	
	/**
	 * Mark a patch as in conflict. 
	 * @param conflictMessage
	 */
	void markConflicting(String conflictMessage);

}
