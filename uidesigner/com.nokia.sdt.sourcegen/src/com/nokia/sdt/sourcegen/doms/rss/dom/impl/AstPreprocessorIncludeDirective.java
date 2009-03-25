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

import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstPreprocessorIncludeDirective extends AstPreprocessorDirective
        implements IAstPreprocessorIncludeDirective {

    private String filename;
    private boolean userPath;
    private IAstSourceFile file;

    /**
     */
    public AstPreprocessorIncludeDirective(String filename, boolean userPath, IAstSourceFile file) {
        super();
        setFilename(filename);
        setUserPath(userPath);
        setFile(file);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
    	if (userPath)
    		return new Object[] { 
        		"#include ", //$NON-NLS-1$ 
        		"\""+filename+"\"",  //$NON-NLS-1$ //$NON-NLS-2$
        		ISourceFormatter.SEGMENT_NEWLINE 
        		};
    	else
    		return new Object[] { 
        		"#include ", //$NON-NLS-1$
        		"<"+filename+">",//$NON-NLS-1$ //$NON-NLS-2$ 
        		ISourceFormatter.SEGMENT_NEWLINE 
        		};  
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective#getFilename()
     */
    public String getFilename() {
        return filename;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective#setFilename(java.lang.String)
     */
    public void setFilename(String filename) {
        Check.checkArg(filename);
        this.filename = filename;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective#isUserPath()
     */
    public boolean isUserPath() {
        return userPath;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective#setUserPath(boolean)
     */
    public void setUserPath(boolean userPath) {
        this.userPath = userPath;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective#getFile()
     */
    public IAstSourceFile getFile() {
        return file;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective#setFile(com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile)
     */
    public void setFile(IAstSourceFile file) {
        this.file = file;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
    	return NO_CHILDREN;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
    	if (file != null)
           return new IAstNode[] { file };
        else
        	return NO_CHILDREN;
    }

}
