/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
/**
 * 
 */
package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.ISegmentHandler;

/**
 * This handler creates text from scratch by following the
 * segment list to the letter.
 * 
 *
 */
class NewTextHandler implements ISegmentHandler {
    StringBuffer buffer;
	protected ISourceFormatter formatter;
    
    NewTextHandler(ISourceFormatter formatter) {
    	if (formatter == null)
    		formatter = ISourceFormatter.NULL_SOURCE_FORMATTER;
    	this.formatter = formatter;
        buffer = new StringBuffer();
        formatter.setBuffer(buffer);
    }
    
    public String toString() {
    	return buffer.toString();
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.ISegmentHandler#handleAstNode(com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode)
	 */
	public void handleAstNode(IAstNode node) {
		// just create new text
		node.handleSegments(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.ISegmentHandler#handleText(Object)
	 */
	public void handleText(Object text) {
        produceText(formatter.getFormattedText(text));
	}

    public void produceText(CharSequence text) {
        buffer.append(text);
    }

    ////////////
    
    public boolean usingCleanText() {
        return false;
    }
    
    public void produceCleanNode(IAstNode node) {
        buffer.append(node.getOriginalText());
    }

}