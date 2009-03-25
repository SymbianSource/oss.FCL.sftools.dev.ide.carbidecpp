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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstLxxSourceFile;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 
 *
 */
public class AstLxxSourceFile extends AstRssSourceFile implements
        IAstLxxSourceFile {

    private int languageCode;
    
    // this pattern is intentionally greedy; we don't want to be too smart
    private static final Pattern STRING_MACRO_PATTERN = Pattern.compile(
    		"((\".*\")|(<[0-9A-Fa-fxX]+>))+"); //$NON-NLS-1$
    
    /**
     * Create a source file with the given string table
     * @param file
     * @param langCode 
     */
    public AstLxxSourceFile(ISourceFile file, int langCode) {
        super(file);
        setLanguageCode(langCode);
        dirty = false;
    }

     /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLxxSourceFile#getLanguageCode()
     */
    public int getLanguageCode() {
        return languageCode;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLxxSourceFile#setLanguageCode(int)
     */
    public void setLanguageCode(int languageCode) {
        Check.checkArg(languageCode < 100);
        this.languageCode = languageCode;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstLxxSourceFile#getStringMacros()
     */
    public IAstPreprocessorDefineDirective[] getStringMacros() {
    	Collection<IAstPreprocessorDefineDirective> defines = new ArrayList<IAstPreprocessorDefineDirective>();
    	
    	for (Iterator iter = getFileNodeList().iterator(); iter.hasNext();) {
			IAstNode node = (IAstNode) iter.next();
			if (node instanceof IAstPreprocessorDefineDirective) {
				IAstPreprocessorDefineDirective define = (IAstPreprocessorDefineDirective) node;
				if (define.getMacro() instanceof IObjectStyleMacro) {
					IObjectStyleMacro macro = (IObjectStyleMacro) define.getMacro();
					if (STRING_MACRO_PATTERN.matcher(macro.getExpansion()).matches()) {
						defines.add(define);
					}
				}
			}
		}
    	return (IAstPreprocessorDefineDirective[]) defines.toArray(new IAstPreprocessorDefineDirective[defines.size()]);
    }
}
