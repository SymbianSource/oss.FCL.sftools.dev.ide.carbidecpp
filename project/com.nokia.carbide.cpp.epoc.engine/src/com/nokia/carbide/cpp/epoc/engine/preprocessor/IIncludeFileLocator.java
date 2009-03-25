/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.preprocessor;

import java.io.File;

/**
 * This interface provides a means to look up include files
 * using normal C/C++ semantics
 *
 */
public interface IIncludeFileLocator {

    /**
     * Look up a file on the include paths
     * @param file the filename
     * @param isUser true: #include "...", false: #include <...>
     * @param currentDir if not null, the current directory and implicit first directory to search
     * @return the located file, or null; the file is a key to ITranslationUnitProvider.
     */
    public File findIncludeFile(String file, boolean isUser,
            File currentDir);
    
    /**
     * Get the array of user include paths searched.
     * @return array,never null
     */
    File[] getUserPaths();
    
    /**
     * Get the array of system include paths searched.
     * @return array,never null
     */
    File[] getSystemPaths();
    
    
}