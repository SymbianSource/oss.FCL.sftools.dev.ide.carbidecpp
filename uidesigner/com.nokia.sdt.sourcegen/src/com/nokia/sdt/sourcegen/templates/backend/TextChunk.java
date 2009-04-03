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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TextChunk {
    String text;
    static final Pattern newLineMatcher = Pattern.compile("\r\n|\r(?!\n)", Pattern.MULTILINE); //$NON-NLS-1$
    
    public TextChunk(String text) {
        // canonicalize newlines
        Matcher m = newLineMatcher.matcher(text);
        text = m.replaceAll("\n"); //$NON-NLS-1$
        this.text = text;
    }
    public boolean empty() {
        if (text.length() == 0)
            return true;
        int firstNonWs;
        for (firstNonWs = 0; firstNonWs < text.length(); firstNonWs++) {
            char ch = text.charAt(firstNonWs);
            if (ch != ' ' && ch != '\t')
                break;
        }
        int lastNonWs;
        for (lastNonWs = text.length(); lastNonWs > 0; lastNonWs--) {
            char ch = text.charAt(lastNonWs - 1);
            if (ch != ' ' && ch != '\t')
                break;
        }
        return firstNonWs == lastNonWs;
    }
    public String toString() {
        return text;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
    public abstract void accept(ITextChunkVisitor visitor, boolean isFirst, boolean isLast);
}