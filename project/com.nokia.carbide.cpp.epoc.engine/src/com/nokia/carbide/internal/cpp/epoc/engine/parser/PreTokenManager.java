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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import java.util.HashMap;
import java.util.Map;

/**
 * Scan tokens for the preprocessor parser.
 *
 */
public class PreTokenManager extends BaseTokenManager implements PreParserCoreConstants {

	private boolean inDirective;
	
	static final Map<String, Integer> ppKeywords = new HashMap<String, Integer>();
	static {
		ppKeywords.put("if", IF); //$NON-NLS-1$
		ppKeywords.put("ifdef", IFDEF); //$NON-NLS-1$
		ppKeywords.put("ifndef", IFNDEF); //$NON-NLS-1$
		ppKeywords.put("elif", ELIF); //$NON-NLS-1$
		ppKeywords.put("else", ELSE); //$NON-NLS-1$
		ppKeywords.put("endif", ENDIF); //$NON-NLS-1$
		ppKeywords.put("define", DEFINE); //$NON-NLS-1$
		ppKeywords.put("undef", UNDEF); //$NON-NLS-1$
		ppKeywords.put("include", INCLUDE); //$NON-NLS-1$
		ppKeywords.put("error", ERROR); //$NON-NLS-1$
		ppKeywords.put("warning", WARNING); //$NON-NLS-1$
		ppKeywords.put("pragma", PRAGMA); //$NON-NLS-1$
	}

	/**
	 * Create a token manager using a document which is guaranteed
	 * not to change over time.
	 * @param document
	 * @param path
	 */
	public PreTokenManager(IDocument document, IPath path) {
		super(document, path);
		this.inDirective = false;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.TokenManager#getNextToken()
	 */
	public Token getNextToken() {
		IToken itoken = readToken();
		switch (itoken.getType()) {
		case IToken.EOF:
			return convertToken(itoken, 0);
		case IToken.EOL:
			inDirective = false;
			return convertToken(itoken, EOL);
		}
		
		if (!inDirective) {
			if (itoken.followsNewline()) {
				// see if a directive is starting
				if (itoken.getText().equals("#")) { //$NON-NLS-1$
					inDirective = true;
					atNewLine = false;
					return convertToken(itoken, POUND);
				}
			}
			return convertToken(itoken, PPTOKEN);
		}
		
		switch (itoken.getType()) {
		case IToken.IDENTIFIER:
			Integer type = ppKeywords.get(itoken.getText());
			if (type != null)
				return convertToken(itoken, type.intValue());
			return convertToken(itoken, IDENTIFIER);
		case IToken.NUMBER:
			return convertToken(itoken, INTEGER);
		case IToken.STRING:
			return convertToken(itoken, STRING);
		case IToken.CHAR:
			return convertToken(itoken, CHAR);
		}
		
		// reinterpret punctuation
		unreadToken(itoken);
		
		int type = scanDirectiveToken(itoken.followsSpace());
		if (type == -1) {
			// unrecognized, eat a char
			type = PPTOKEN;
			get();
		}
		String tok = copyToken();
		itoken = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
				IToken.PUNC, 
				tok,
				location, itoken.getOffset(), tok.length(), 
				itoken.followsSpace(), itoken.followsNewline());

		// rescan
		return new ASTToken(itoken, type);
	}
	
	
	/**
	 * Scan a preprocessor token.
	 * @param followsSpace 
	 * @return token code, or 0 for EOF, or -1 for unknown
	 */
	protected int scanDirectiveToken(boolean followsSpace) {
		int start = idx;
		char ch = peek();
		switch (ch) {
		case 0:
			return EOF;
		case '#':
			get();
			return POUND;
		case '(':
			// this token is only generated for #define FOO(...)
			// to identify an argument declaration list, so we
			// don't want to falsely identify the parenthesis
			// in #define FLAGS (EFlag1|EFlag2) (boog 6600)
			if (!followsSpace) {
				get();
				return LPAREN;
			} else {
				return -1;
			}
		case ')':
			get();
			return RPAREN;
		case '.':
			get();
			if (get() == '.' && get() == '.') {
				return ELLIPSIS;
			}
			idx = start;
			currentToken.setLength(0);
			return -1;
		case ',':
			get();
			return COMMA;
		}
		
		if (isAlpha(ch)) {
			consumeAlphaNumericSequence();
			
			String token = copyToken();
			Integer type = ppKeywords.get(token);
			if (type != null)
				return type.intValue();
			return IDENTIFIER;
		}
		
		if (isNumeric(ch)) {
			consumeNumericSequence();
			return INTEGER;
		}
		
		return -1;
	}
	
	/** Scan raw text to the end of line */
	/*
	protected int scanText() {
		char ch;
		while ((ch = peek()) != 0) {
			if (ch == '\r' || ch == '\n')
				break;
			get();
		}
		return TEXT;
	}*/


	/** Used to scan tokens without interpreting as directive tokens,
	 * and fixup the given token to be the correct type.
	 */
	public void resetDirectiveScanningState(ASTToken token) {
		inDirective = false;
		if (token.kind != EOF && token.kind != EOL)
			token.kind = PPTOKEN;
	}
}
