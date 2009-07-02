/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IMacroProvider;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.*;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.MacroExpandingStream;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.MacroExpandingTokenIterator;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.*;

/**
 * Token manager for the preprocessor expression parser.  This takes a token stream
 * and passes it through the macro expansion engine.  Since the punctuation tokens are 
 * single characters, it reassembles them into actual operators.  It also handles 
 * avoiding macro expansion for the identifiers following the "defined X" and "defined(X)" operators.
 *
 */
public class ASTTokenManager implements TokenManager, ASTParserCoreConstants {

	private MacroExpandingTokenIterator iter;
	private MacroExpandingStream macroStream;
	private ASTToken eofToken;
	private IToken current;
	private int nextPosition;
	private StringBuilder tokenText;
	
	/**
	 * @param document
	 * @param path
	 * @param config 
	 */
	public ASTTokenManager(IASTPreprocessorTokenStream tokenStream, IMacroProvider macroProvider) {
		List<IToken> iTokens = tokenStream.getTokens();
		this.macroStream = new MacroExpandingStream(macroProvider, iTokens);
		this.iter = macroStream.iterator();
		this.tokenText = new StringBuilder();
		
		IToken iEofToken;
		if (iTokens.size() > 0) {
			IToken lastToken = iTokens.get(iTokens.size() - 1); 
			iEofToken = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
					IToken.EOF, "", lastToken.getLocation(),//$NON-NLS-1$ 
						lastToken.getEndOffset(), 0, false, false);
		} else
			iEofToken = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
					IToken.EOF, "", false, false); //$NON-NLS-1$
		eofToken = new ASTToken(iEofToken, EOF);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.parser.ASTTokenManager#getNextToken()
	 */
	public Token getNextToken() {
		IToken token = null;
		if (iter.hasNext()) {
			token = iter.next();
			return scanToken(token);
		} else {
			return eofToken;
		}
	}

	private ASTToken scanToken(IToken token) {
		
		if (token.getType() != IToken.PUNC) {
			int type = classifyToken(token);
			if (type == DEFINED) {
				// don't expand the next identifier
				iter.enableMacroExpansion(false);
			} else if (type == IDENTIFIER) {
				// reset any non-expanding state from before
				iter.enableMacroExpansion(true);
			}
			return new ASTToken(token, type);
		}
		
		// Our punctuation was split into single characters.
		// Peek ahead in the stream and catenate adjacent punctuation if
		// it forms known tokens.
		//
		// Freeze the macro stream so we can safely reset the iterator
		// if we read ahead too far (this prevents macro expansion too,
		// which is desirable as well).
		nextPosition = iter.mark();
		current = token;
		char ch = token.getText().charAt(0);
		tokenText.setLength(0);
		tokenText.append(ch);
		int type = scanPuncToken(ch);
		iter.release(nextPosition);
		
		if (type != -1) {
			return new ASTToken(token, current, tokenText.toString(), type);
		} else {
			// unknown
			return new ASTToken(token, type);
		}
	}

	private int classifyToken(IToken iToken) {
		switch (iToken.getType()) {
		case IToken.EOF:
			return EOF;
		case IToken.EOL:
			return EOL;
		case IToken.IDENTIFIER:
			if ("defined".equals(iToken.getText())) //$NON-NLS-1$
				return DEFINED;
			return IDENTIFIER;
		case IToken.NUMBER:
			return INTEGER;
		case IToken.STRING:
			return -1;
		case IToken.PUNC:
			return -1;
		case IToken.CHAR:
			return -1;
		case IToken.RAW:
			if (iToken.getText().length() == 0)
				return -1;
			// else fall through
			
		default:
			Check.checkState(false);
			return -1;
		}
	}

	/** punctuation reclassification: peek at the next punctuation token */
	private char peek() {
		if (!iter.hasNext())
			return 0;
		current = iter.next();
		nextPosition++;
		if (current == null || current.followsSpace() || current.getType() != IToken.PUNC)
			return 0;
		return current.getText().charAt(0);
	}
	
	/** punctuation reclassification: unpeek at the last punctuation token */
	private void unpeek() {
		nextPosition--;
	}
	
	/** punctuation reclassification: approve the current token */
	private void keep() {
		tokenText.append(current.getText());
	}
	
	/**
	 * Scan a preprocessor token.
	 * @return token code, or 0 for EOF, or -1 for unknown
	 */
	protected int scanPuncToken(char ch) {
		switch (ch) {
		case '~':
			return INVERT;
		case '%':
			return MOD;
		case '^':
			return XOR;
		case '|':
			if (peek() == '|') {
				keep();
				return LOGOR;
			}
			unpeek();
			return OR;
		case '&':
			if (peek() == '&') {
				keep();
				return LOGAND;
			}
			unpeek();
			return AND;
		case '*':
			return TIMES;
		case '/':
			return DIV;
		case '(':
			return LPAREN;
		case ')':
			return RPAREN;
		case '-':
			return MINUS;
		case '+':
			return PLUS;
		case '=':
			if (peek() == '=') {
				keep();
				return EQ;
			} 
			// '=' not recognized here
			unpeek();
			return -1;
		case '!':
			if (peek() == '=') {
				keep();
				return NE;
			}
			unpeek();
			return NOT;
		case '<':
			if (peek() == '=') {
				keep();
				return LTE;
			}
			unpeek();
			return LT;
		case '>':
			if (peek() == '=') {
				keep();
				return GTE;
			}
			unpeek();
			return GT;
		case '?':
			return QUESTION;
		case ':':
			return COLON;
		}
		return -1;
	}
	
}
