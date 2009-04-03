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
 * An expression chunk is converted to a string in JavaScript
 * so this handler converts its text to a string.
 *  
 * 
 *
 */
public class LiteralTextChunk extends TextChunk {
    public LiteralTextChunk(String text) {
        super(text);
    }
 
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.javascript.TextChunk#accept(com.nokia.sdt.sourcegen.templates.javascript.TextChunkVisitor)
     */
    public void accept(ITextChunkVisitor visitor, boolean isFirst, boolean isLast) {
        visitor.visit(this);
    }
}



