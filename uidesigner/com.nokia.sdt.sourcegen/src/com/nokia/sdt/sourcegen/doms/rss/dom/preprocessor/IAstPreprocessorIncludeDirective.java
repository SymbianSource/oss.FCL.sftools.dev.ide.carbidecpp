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

package com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor;

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile;

/**
 * An #include statement.  This refers to a file which may or may not
 * exist on disk.
 * 
 * 
 *
 */
public interface IAstPreprocessorIncludeDirective extends IAstPreprocessorDirective {
    /** Get the filename string */
    public String getFilename();
    
    /** Set the filename string */
    public void setFilename(String name);
    
    /** Get the user/system flag
     * @return true if "xxx", false if <xxx>
     */
    public boolean isUserPath();

    /** Set the user/system flag
     * @param userPath true if "xxx", false if <xxx>
     */
    public void setUserPath(boolean userPath);

    /** Get the resolved file
     * @return null if file not found
     */
    public IAstSourceFile getFile();

    /** Set the resolved file (may be null) */
    public void setFile(IAstSourceFile file);
}
