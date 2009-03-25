/*
* Copyright (c) 2005-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.parser.bldinf;


import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ASTToken;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock;

/**
 * This class represents an AST token for use by ASTParser, which
 * contains more accurate source region information.
 *
 */
public class BldInfToken extends ASTToken {

	public IConditionalBlock block;

    /**
     * Create a token 
     */
    public BldInfToken(IConditionalBlock block, IToken iToken, int type) {
        this(block, iToken, iToken, iToken.getText(), type);
    }
    
    /**
     * Given two tokens in a range, combine their ranges and tokens into one and return that.
     * @param firstToken
     * @param lastToken
     * @param type
     */
    public BldInfToken(IConditionalBlock block, IToken firstToken, IToken lastToken, String text, int type) {
    	super(firstToken, lastToken, text, type);
    	this.block = block;
    }

}
