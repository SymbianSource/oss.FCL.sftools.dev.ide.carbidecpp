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

import com.nokia.sdt.sourcegen.core.ISourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsSourceFile;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class AstRlsSourceFile extends AstRssSourceFile implements
        IAstRlsSourceFile {

    private int languageCode;

    /**
     * Create a source file with the given string table
     * @param file
     * @param langCode 
     */
    public AstRlsSourceFile(ISourceFile file, int langCode) {
        super(file);
        setLanguageCode(langCode);
        dirty = false;
    }
 
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsSourceFile#getLanguageCode()
     */
    public int getLanguageCode() {
        return languageCode;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstRlsSourceFile#setLanguageCode(int)
     */
    public void setLanguageCode(int languageCode) {
        Check.checkArg(languageCode < 100);
        this.languageCode = languageCode;
        dirty = true;
    }

}
