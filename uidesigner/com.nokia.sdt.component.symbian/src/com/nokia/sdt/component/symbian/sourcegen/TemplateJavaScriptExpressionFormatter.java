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

package com.nokia.sdt.component.symbian.sourcegen;

import com.nokia.sdt.sourcegen.templates.backend.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

/**
 * This formatter generates Javascript code from XML attributes
 * containing variables
 * 
 * 
 * */
public class TemplateJavaScriptExpressionFormatter implements ITextChunkVisitor {
    StringBuffer buffer;
	private boolean anyText;
	private boolean empty;

    public TemplateJavaScriptExpressionFormatter() {
        empty = true;
        buffer = new StringBuffer();
    }
    
    public void reset() {
        buffer.setLength(0);
        empty = true;
        anyText = false;
    }

    private void appendText(TextChunk chunk) {
        buffer.append(chunk.toString());
        empty = false;
        anyText = true;
    }

    public String toString() {
    	if (!empty) {
	    	if (!anyText) {
	    		buffer.append("\"\"");
	    	}
    		empty = true;
    		anyText = true;
    	}
        return buffer.toString();
    }
    
    public void visit(ExprTextChunk chunk) {
    	if (!empty)
    		buffer.append("+");
    	appendText(chunk);
    }

    public void visit(RawTextChunk chunk, boolean isFirst, boolean isLast) {
    	if (!empty)
    		buffer.append("+");
    		
        String text = chunk.getText();
        buffer.append(TextUtils.quote(text, '"'));
        empty = false;
        anyText = true;
    }

    public void visit(ScriptTextChunk chunk) {
    	Check.checkState(false);
    }

	public void visit(LiteralTextChunk chunk) {
    	if (!empty)
    		buffer.append("+");
        buffer.append(chunk.toString());
        empty = false;
    }
}