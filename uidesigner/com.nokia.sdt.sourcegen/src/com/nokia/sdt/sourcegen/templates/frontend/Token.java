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

package com.nokia.sdt.sourcegen.templates.frontend;

import com.nokia.cpp.internal.api.utils.core.MessageLocation;


/**
 * This class represents a single token read by the lexer.
 * Tokens are categorized simply into whitespace, newlines,
 * text, and delimiters.  Delimiters are configured in the Lexer. 
 * No semantic meaning is attached to a token.  
 * <p>
 * This is intended to be a lightweight object, thus it has
 * no getters or setters.  But the text and ref fields should
 * never be null, and the type should never be NONE unless
 * uninitialized.
 * 
 * 
 *
 */
public class Token {
    public Token(MessageLocation ref) {
        if (ref == null)
            throw new IllegalArgumentException();
        this.ref = ref;
    }

    public Token(int type, String text, MessageLocation ref) {
        if (type == NONE || text == null || ref == null)
            throw new IllegalArgumentException();
        this.type = type;
        this.text = text;
        this.ref = ref;
    }

    /** No type */
    static public final int NONE = 0;
    /** Delimiter */
    static public final int DELIM = 1;
    /** Text */
    static public final int TEXT = 2;

    /** The type of token */
    public int type;
    /** The text of the token (unparsed) */
    public String text;
    /** The location of the token */
    public MessageLocation ref;

    /** Token value 
     * for type == DELIM, the Node type,
     * otherwise, undefined
     */
    public int value;
    
    public boolean isOpenDelimiter() {
        return type == DELIM && value > 0;
    }
    public boolean isCloseDelimiter() {
        return type == DELIM && value < 0;
    }
    
    public String toString() {
        return text;
    }
}
