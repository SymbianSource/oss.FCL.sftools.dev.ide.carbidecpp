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

package com.nokia.sdt.sourcegen.templates.backend;

/** 
 * Raw text is converted into a JavaScript string, thus
 * this chunk automatically escapes and quote-encapsulates
 * its text.
 * 
 * 
 */
public class RawTextChunk extends TextChunk {
    String escape(String text) {
        String ctrls = "\\\b\t\n\f\r\"'"; //$NON-NLS-1$
        String repls = "\\btnfr\"'";  //$NON-NLS-1$
        StringBuffer repl = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            int idx = ctrls.indexOf(text.charAt(i)); 
            if (idx != -1) {
                repl.append('\\');
                repl.append(repls.charAt(idx));
            } else {
                repl.append(text.charAt(i));
            }
        }
        return repl.toString();
    }
    public RawTextChunk(String text) {
        super(text);
    }
 
    public String toString() {
        return "\"" + escape(text) + "\""; //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.javascript.TextChunk#accept(com.nokia.sdt.sourcegen.templates.javascript.TextChunkVisitor)
     */
    public void accept(ITextChunkVisitor visitor, boolean isFirst, boolean isLast) {
        visitor.visit(this, isFirst, isLast);
    }
}