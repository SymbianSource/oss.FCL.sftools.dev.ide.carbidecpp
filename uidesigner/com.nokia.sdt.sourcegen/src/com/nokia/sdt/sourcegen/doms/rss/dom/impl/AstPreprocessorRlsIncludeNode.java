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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsSourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorRlsIncludeNode;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.*;

/**
 * 
 *
 */
public class AstPreprocessorRlsIncludeNode extends
        AstPreprocessorIncludeDirective implements
        IAstPreprocessorRlsIncludeNode {

    private int langCode;

    /**
     * @param filename
     * @param userPath
     * @param file
     */
    public AstPreprocessorRlsIncludeNode(String filename, boolean userPath,
            IAstSourceFile file, int langCode) {
        super(filename, userPath, file);
        Check.checkArg(file instanceof IAstRlsSourceFile);
        setLanguageCode(langCode);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorRlsIncludeNode#getLanguageCode()
     */
    public int getLanguageCode() {
        return langCode;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorRlsIncludeNode#setLanguageCode(int)
     */
    public void setLanguageCode(int langCode) {
        this.langCode = langCode;
        dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstPreprocessorIncludeDirective#getTextSegments()
     */
    public Object[] getTextSegments() {
        List segs = new ArrayList();
        segs.add("#ifdef LANGUAGE_" + Character.forDigit(langCode / 10, 10) //$NON-NLS-1$
                + Character.forDigit(langCode % 10, 10));
        segs.add(ISourceFormatter.SEGMENT_NEWLINE);
        segs.addAll(Arrays.asList(super.getTextSegments()));
        segs.add("#endif"); //$NON-NLS-1$
        segs.add(ISourceFormatter.SEGMENT_NEWLINE);
        return segs.toArray();
    }
}
