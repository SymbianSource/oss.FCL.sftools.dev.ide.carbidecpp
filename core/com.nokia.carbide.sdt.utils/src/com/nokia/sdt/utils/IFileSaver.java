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
package com.nokia.sdt.utils;

import org.eclipse.core.runtime.CoreException;

import java.io.File;

/**
 * This interface manages the saving of text files in an abstract manner.
 *
 */
public interface IFileSaver {
    /**
     * Save a file to disk or editor buffer.
     * The charset reflects either the character set 
     * of the previously existing file or the initial
     * character set desired for new files.
 	 * @throws CoreException 
 	 */
    public void saveFileText(File file, String charset, char[] text) throws CoreException;

	/**
	 * Touch the file's timestamp without otherwise modifying it.
	 * Unlike normal meaning of "touch", this will not create an empty file.
	 * @param file
	 * @throws CoreException 
	 */
	public void touchFile(File file) throws CoreException;

	/**
	 * Check whether the file exists
	 * @param file
	 * @return
	 */
	public boolean fileExists(File file);
    
}
