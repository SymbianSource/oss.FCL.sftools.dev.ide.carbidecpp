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

package com.nokia.carbide.internal.cpp.epoc.engine.parser;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.ParseException;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.Token;
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ParserBase {
    protected IExtensionParser extensionParser;
    protected boolean hadErrors;
    protected ITokenLocation tokenLocation;

    public boolean hadErrors() {
    	return hadErrors;
    }

    protected IASTLiteralTextNode getLiteralTextNodeSpanning(Token start, Token end) throws ParseException {
    	IASTLiteralTextNode node = ASTFactory.createPreprocessorLiteralTextNode(ParserUtils.getLiteralTextSpanning(start, end));
    	ParserUtils.setSourceRangeFromTokenSpan(node, start, end);
    	return node;
    }

    protected IASTLiteralTextNode getLiteralTextNodeSpanningUpTo(Token start, Token end) throws ParseException {
    	IASTLiteralTextNode node = ASTFactory.createPreprocessorLiteralTextNode(ParserUtils.getLiteralTextSpanningUpTo(start, end));
    	ParserUtils.setSourceRangeFromTokenSpanUpTo(node, start, end);
    	return node;
    }

    protected IASTPreprocessorLiteralExpression getPreprocessorLiteralExpressionSpanning(Token start, Token end) throws ParseException {
    	IASTPreprocessorLiteralExpression expr = ASTFactory.createPreprocessorLiteralExpression(ParserUtils.getLiteralTextSpanning(start, end));
    	ParserUtils.setSourceRangeFromTokenSpan(expr, start, end);
    	return expr;
    }
    
	protected IASTLiteralTextNode getRawLiteralTextNodeSpanning(Token start, Token end) {
		IASTLiteralTextNode node = ASTFactory.createRawLiteralTextNode(ParserUtils.getLiteralTextSpanning(start, end));
		ParserUtils.setSourceRangeFromTokenSpan(node, start, end);
		return node;
	}

    protected IMessage createMessage(Token token, ParseException e) {
    	return new Message(IMessage.ERROR, 
    			ParserUtils.getMessageLocationFromToken(token),
    			"Parser.ParseException", //$NON-NLS-1$
    			e.getLocalizedMessage());
    }


}
