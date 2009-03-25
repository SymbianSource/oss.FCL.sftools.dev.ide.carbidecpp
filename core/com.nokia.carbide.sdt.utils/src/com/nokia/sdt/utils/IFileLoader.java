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
 * This interface manages the loading of text files in an abstract manner.
 *
 */
public interface IFileLoader {
    /**
     * Load a file from disk or memory.
     * @throws CoreException if the file does not exist or cannot
     * otherwise be loaded cleanly.
     */
    public char[] loadFileText(File file) throws CoreException;
    
	/**
	 * Get the timestamp of the file.
	 * @return
	 */
	long getModificationTime(File file);
    

}
