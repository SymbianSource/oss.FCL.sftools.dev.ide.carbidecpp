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
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.jface.text.*;

/**
 * This class represents an AST token for use by ASTParser, which
 * contains more accurate source region information.
 *
 */
public class ASTToken extends com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.Token implements PreParserCoreConstants {

	public IToken iToken;
	public int offset;
    public int length;
    public boolean followsSpace, followsNewline;

    /**
     * Create a token 
     */
    public ASTToken(IToken iToken, int type) {
        this(iToken, iToken, iToken.getText(), type);
    }
    
    /**
     * Given two tokens in a range, combine their ranges and tokens into one and return that.
     * @param firstToken
     * @param lastToken
     * @param type
     */
    public ASTToken(IToken firstToken, IToken lastToken, String text, int type) {
    	IToken combined = ParserUtils.getCombinedToken(firstToken, lastToken, text);
        
        this.kind = type;
        this.iToken = combined;
        this.image = getTokenText();
        this.offset = firstToken.getOffset();
        this.length = combined.getLength();
        
        this.followsSpace = firstToken.followsSpace();
        this.followsNewline = firstToken.followsNewline();
        
        if (combined.getLocation() instanceof IDocumentTokenLocation) {
        	IDocumentTokenLocation location = (IDocumentTokenLocation) combined.getLocation();
	        try {
	        	int startOffset = combined.getOffset();
	        	int endOffset = combined.getEndOffset();
	        	IDocument document = location.getDocument();
	        	
		        IRegion info = document.getLineInformationOfOffset(startOffset);
		        this.beginLine = document.getLineOfOffset(startOffset) + 1;
		        this.endLine = document.getLineOfOffset(endOffset > startOffset ? endOffset - 1 : startOffset) + 1;
		        this.beginColumn = startOffset - info.getOffset();
		        this.endColumn = endOffset - info.getOffset();
	        } catch (BadLocationException e) {
	        	Check.checkState(false);
	        }
        }
    }

	/**
	 * @return
	 */
	public String getTokenText() {
		return iToken.getText();
	}

}
