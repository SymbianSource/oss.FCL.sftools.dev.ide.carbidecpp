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

package com.nokia.sdt.sourcegen.doms.rss;

import com.nokia.sdt.sourcegen.IIncludeFileLocator;


/**
 * This interface provides a set of include paths 
 * as well as the behavior to search the paths.
 * 
 * 
 *
 */
public interface IIncludePathHandler extends IIncludeFileLocator {

    /** 
     * Add a path searched for #include "..." files
     */
    public void addUserIncludePath(String path);

    /** 
     * Add a path searched for #include <...> files
     */
    public void addSystemIncludePath(String path);

}