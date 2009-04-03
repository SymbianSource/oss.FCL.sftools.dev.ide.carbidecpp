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

package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.doms.rss.IIncludePathHandler;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.io.File;
import java.util.*;

/**
 * 
 *
 */
public class IncludePathHandler implements IIncludePathHandler {
    /** List of user include paths */
    private List userIncludePaths;
    /** List of system include paths */
    private List systemIncludePaths;

    /**
     * 
     */
    public IncludePathHandler() {
        userIncludePaths = new ArrayList();
        systemIncludePaths = new ArrayList();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#addIncludePath(com.nokia.sdt.sourcegen.doms.rss.dom.IncludePath)
     */
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IIncludePathHandler#addUserIncludePath(java.lang.String)
     */
    public void addUserIncludePath(String path) {
        Check.checkArg(path);
        userIncludePaths.add(path);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#addIncludePath(com.nokia.sdt.sourcegen.doms.rss.dom.IncludePath)
     */
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IIncludePathHandler#addSystemIncludePath(java.lang.String)
     */
    public void addSystemIncludePath(String path) {
        Check.checkArg(path);
        systemIncludePaths.add(path);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ITranslationUnit#getIncludePaths()
     */
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IIncludePathHandler#getIncludePaths()
     */
    public String[] getIncludePaths() {
        String[] all = new String[userIncludePaths.size() + systemIncludePaths.size()];
        int idx = 0;
        for (Iterator iter = systemIncludePaths.iterator(); iter.hasNext();) {
            String p = (String) iter.next();
            all[idx++] = p;
        }
        for (Iterator iter = userIncludePaths.iterator(); iter.hasNext();) {
            String p = (String) iter.next();
            all[idx++] = p;
        }
        return all;
    }
    
     /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IIncludePathHandler#findIncludeFile(java.lang.String, boolean, java.lang.String)
     */
    public File findIncludeFile(String file, boolean isUser, File currentDir) {
        File f;
        
        if (currentDir != null) {
            f = new File(currentDir, file);
            if (f.exists())
                return f;
        }
        
        if (isUser) {
            for (Iterator iter = userIncludePaths.iterator(); iter.hasNext();) {
                String p = (String) iter.next();
                f = new File(p, file);
                if (f.exists())
                    return f;
            }
        }
        
        for (Iterator iter = systemIncludePaths.iterator(); iter.hasNext();) {
            String p = (String) iter.next();
            f = new File(p, file);
            if (f.exists())
                return f;
        }
        
        return null;
    }
     

}
