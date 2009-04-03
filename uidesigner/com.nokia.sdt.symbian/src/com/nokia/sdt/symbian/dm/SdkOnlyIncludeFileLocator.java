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

package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.sourcegen.IIncludeFileLocator;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.resources.IProject;

import java.io.File;

/**
 * Find include files in the current directory or in
 * a given SDK only.  Mainly for testing.
 * 
 *
 */
public class SdkOnlyIncludeFileLocator implements IIncludeFileLocator {

    private File sdkIncludes;
    
    /**
     * 
     */
    public SdkOnlyIncludeFileLocator(IProject project, File sdkHome) {
        Check.checkArg(project);
        this.sdkIncludes = new File(new File(sdkHome, "epoc32"), "include"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IIncludePathHandler#findIncludeFile(java.lang.String, boolean, java.io.File)
     */
    public File findIncludeFile(String file, boolean isUser, File currentDir) {
        File f;
        if (currentDir != null) {
            f = new File(currentDir, file);
            if (f.exists())
                return f;
        }
        f = new File(sdkIncludes, file);
        if (f.exists())
            return f;
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.IIncludeFileLocator#getIncludePaths()
     */
    public String[] getIncludePaths() {
        return new String[] { sdkIncludes.toString() };
    }
}
