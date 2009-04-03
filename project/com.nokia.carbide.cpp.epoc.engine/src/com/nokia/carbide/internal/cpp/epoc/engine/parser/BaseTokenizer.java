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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation;


/**
 * A stupid preprocessor tokenizer that understands identifiers, numbers,
 * and simple punctuation.
 *
 */
public class BaseTokenizer {

	protected char[] text;
	protected int idx;
	protected boolean atNewLine;
	protected boolean reachedEOF;
	protected boolean atSpace;
	protected ITokenLocation location;
	protected StringBuilder currentToken;
	private boolean generateCharTokens;
	
	public BaseTokenizer(ITokenLocation location, char[] text) {
		this.location = location; 
		this.text = text;
		this.idx = 0;
		this.atNewLine = true;
		this.reachedEOF = false;
		this.atSpace = false;
		this.currentToken = new StringBuilder();
		this.generateCharTokens = true;
	}
	
	public ITokenLocation getTokenLocation() {
		return location;
	}
	
	/**
	 * Get current index.
	 * @return
	 */
	public int getCurrent() {
		return idx;
	}
	
	/**
	 * Tell if text is consumed.
	 */
	public boolean atEOF() {
		return idx >= text.length;
	}
	
	/**
	 * Start parsing a token
	 */
	public void start() {
		currentToken.setLength(0);
	}
	
	/**
	 * Get the token text from the start (via #start()) to the current index.
	 */
	public String copyToken() {
		return currentToken.toString();
	}
	
	/**
	 * Peek at the next character.
	 * @return character or 0 for EOF
	 */
	public char peek() {
		return idx < text.length ? text[idx] : 0;
	}
	
	/**
	 * Get the next character and increment the index,
	 * adding the character to the current token.
	 * @return character or 0 for EOF
	 */
	public char get() {
		if (idx < text.length) {
			char ch = text[idx++];
			currentToken.append(ch);
			return ch;
		} else {
			return 0;
		}
	}
	
	/**
	 * Get the next character and increment the index, without
	 * adding character to current token.
	 * @return character or 0 for EOF
	 */
	public char skip() {
		if (idx < text.length) {
			return text[idx++];
		} else {
			return 0;
		}
	}
	
	/**
	 * Unget a character (can be repeated).
	 */
	public void unget() {
		if (idx > 0) {
			idx--;
			if (currentToken.length() > 1) {
				currentToken.deleteCharAt(currentToken.length()-1);
			}
		}
	}

	/**
	 * Consume everything to the end of line, not including the EOL itself.
	 * @return true if anything consumed
	 */
	public boolean consumeToEndOfLine() {
		char ch;
		int start = idx;
		while ((ch = peek()) != 0) {
			if (ch == '\r' || ch == '\n')
				break;
			consumeCatenatedLine();
			get();
		}
		return start != idx;
	}
	
	/** Tell whether the character is considered whitespace.
	 * <p>
	 * This impl returns true for ' ', '\t', and '\f'.
	 * @param ch
	 * @return
	 */
	public boolean isWhitespace(char ch) {
		return ch == ' ' || ch == '\t' || ch == '\f';		
	}
	
	/**
	 * Consume whitespace, as determined by #isWhitespace().
	 * @return true if anything consumed
	 */
	public boolean consumeWhitespace() {
		char ch;
		int start = idx;
		while ((ch = peek()) != 0) {
			consumeCatenatedLine();
			if (isWhitespace(ch))
				get();
			/*else if (ch == '\\') {
				int prev = idx;
				if (!consumeCatenatedLine()) {
					idx = prev;
					break;
				}
			}*/
			else
				break;
		}
		return start != idx;
	}
	
	/**
	 * Consume whitespace and comments, using #isWhitespace()
	 * and #consumeComment().
	 * @return true if anything consumed
	 */
	public boolean consumeWhitespaceAndComments() {
		char ch;
		int start = idx;
		while ((ch = peek()) != 0) {
			if (isWhitespace(ch)) {
				get();
				consumeCatenatedLine();
			}
			/*else if (ch == '\\') {
				int prev = idx;
				if (!consumeCatenatedLine()) {
					idx = prev;
					break;
				}
			}*/
			else if (!consumeComment())
				break;
		}
		return start != idx;
	}
	
	/**
	 * Consume a comment.<p>
	 * This impl checks for C-style and C++-style comments.
	 */
	public boolean consumeComment() {
		int start = idx;
		int origLength = currentToken.length();
		char ch = get();
		if (ch == '/') {
			ch = get();
			if (ch == '/') {
				// EOL
				consumeToEndOfLine();
				return true;
			} else if (ch == '*') {
				// block comment
				while ((ch = peek()) != 0) {
					int prev = idx;
					/*if (ch == '\\' && consumeCatenatedLine()) {
						continue;
					}*/
					if (consumeCatenatedLine())
						continue;
					idx = prev;
					get();
					if (ch == '*' && peek() == '/') {
						get();
						return true;
					} 
				}
				// unterminated comment
				//throw new UnterminatedCommentException(start, idx);
			}
		}
		
		// not a comment
		idx = start;
		currentToken.setLength(origLength);
		return false;
	}
	
	/**
	 * Tell whether a char is considered alphanumeric.
	 * <p>
	 * This impl checks for ASCII 'A'-'Z', 'a'-'z', '0'-'9', and '_'.
	 */
	public boolean isAlphaNumeric(char ch) {
		return isAlpha(ch) || isNumeric(ch);
	}
	
	/**
	 * Tell whether a char is considered numeric.
	 * <p>
	 * This impl checks for '0'-'9'.
	 */
	public boolean isNumeric(char ch) {
		return (ch >= '0' && ch <= '9');
	}
	
	/**
	 * Consume an alphanumeric sequence. 
	 * @return true if matched (and skipped), else false (and no idx change)
	 */
	public boolean consumeAlphaNumericSequence() {
		int start = idx;
		char ch;
		while ((ch = peek()) != 0) {
			if (isAlphaNumeric(ch))
				get();
			/*else if (ch == '\\') {
				int prev = idx;
				if (!consumeCatenatedLine()) {
					idx = prev;
					break;
				}
			}*/
			else
				break;
			consumeCatenatedLine();
		}
		return start != idx;
	}
	
	/**
	 * Consume \\ and EOL for catenated line
	 * @return true if catenation detected and skipped
	 */
	protected boolean consumeCatenatedLine() {
		if (peek() == '\\') {
			int prevIdx = idx;
			skip();
			if (peek() == '\r') {
				skip();
				if (peek() == '\n')
					skip();
				return true;
			} else if (peek() == '\n') {
				skip();
				return true;
			}
			idx = prevIdx;
		}
		return false;
	}

	/**
	 * Consume a numeric sequence.  This starts with a number
	 * and contains other numbers and letters and '.' and ('e' + '-'|'+'). 
	 * @return true if matched (and skipped), else false (and no idx change)
	 */
	public boolean consumeNumericSequence() {
		if (!isNumeric(peek()))
			return false;
		
		int start = idx;
		char ch;
		char prev = 0;
		while ((ch = peek()) != 0) {
			if (isAlphaNumeric(ch))
				get();
			else if (ch == '.')
				get();
			else if ((ch == '-' || ch == '+') && (prev == 'e' || prev == 'E'))
				get();
			/*else if (ch == '\\') {
				int prev2 = idx;
				if (!consumeCatenatedLine()) {
					idx = prev2;
					break;
				}
			}*/
			else
				break;
			prev = ch;
			consumeCatenatedLine();
		}
		return start != idx;
	}
	/**
	 * Tell whether the char is considered alphabetic.
	 * <p>
	 * This impl checks for ASCII 'A'-'Z', 'a'-'z', and '_'.
	 */
	public boolean isAlpha(char ch) {
		return (ch >= 'A' && ch <= 'Z')
			|| (ch >= 'a' && ch <= 'z')
			|| (ch == '_');
	}
	
	/**
	 * Consume an alphabetic sequence.
	 * @return true if anything consumed and idx updated
	 */
	public boolean consumeAlphaSequence() {
		int start = idx;
		char ch;
		while ((ch = peek()) != 0) {
			if (isAlpha(ch)) 
				get();
			/*else if (ch == '\\') {
				int prev = idx;
				if (!consumeCatenatedLine()) {
					idx = prev;
					break;
				}
			}*/
			else 
				break;
			consumeCatenatedLine();
		}
		return start != idx;
	}
	
	public void resetFollowsSpace() {
		atSpace = false;
	}
	

	protected IToken readEOLorEOF(int start, boolean wasAtSpace, boolean followsNewLine) {
		// check for EOL and EOF
		String tok = null; 
		switch (peek()) {
			case '\r':
				//currentToken.setLength(0);
				get();
				if (peek() == '\n') {
					get();
				}
				atNewLine = true;
				tok = copyToken();
				return new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
						IToken.EOL, tok, location, start, tok.length(), wasAtSpace, followsNewLine);
						
			case '\n':
				//currentToken.setLength(0);
				get();
				atNewLine = true;
				tok = copyToken();
				return new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
						IToken.EOL, tok, location, start, tok.length(), wasAtSpace, followsNewLine);
			case 0:
				if (!reachedEOF && !followsNewLine) {
					// ensure at least one EOL before EOF
					reachedEOF = true;
					tok = copyToken(); 
					return new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
							IToken.EOL, tok, location, start, tok.length(), wasAtSpace, followsNewLine); //$NON-NLS-1$
				}
				atNewLine = false;
				reachedEOF = true;
				tok = copyToken();
				return new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
						IToken.EOF, tok, location, start, tok.length(), wasAtSpace, followsNewLine); //$NON-NLS-1$
			default:
				atNewLine = false;
				return null;
			
		}
	}
	
	/**
	 * Consume the next token, after skipping space.
	 * @return
	 */
	public IToken readToken() {
		int origStart = idx;
		
		start();
		
		consumeWhitespace();
		
		int start = idx;
		
		boolean wasAtSpace = (start > origStart);
		
		boolean followsNewline = atNewLine;
		
		if (consumeWhitespaceAndComments())
			followsNewline = false;
		
		IToken token = readEOLorEOF(origStart, false, followsNewline);
		if (token != null) 
			return token;
		
		// reset token, which isn't whitespace
		start = idx;
		start();
		
		int type = scanPPToken();
		String tok = copyToken();
		return new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
				type, tok, location,
				start, tok.length(), wasAtSpace, followsNewline);
	}
	
	/**
	 * Replace the given token.  Must be the last one read.
	 * @param token
	 */
	public void unreadToken(IToken token) {
		// not always accurate due to line continuations
		//Check.checkArg(token.getOffset() + token.getLength() == idx);
		idx = token.getOffset();
		atSpace = token.followsSpace();
		atNewLine = token.followsNewline();
		currentToken.setLength(0);
	}
	
	/** Scan a PP token */
	protected int scanPPToken() {
		char ch = peek();
		// alpha -> identifier
		if (isAlpha(ch)) {
			consumeAlphaNumericSequence();
			return IToken.IDENTIFIER;
		}
		// numeric -> number
		if (isNumeric(ch)) {
			consumeNumericSequence();
			return IToken.NUMBER;
		}
		// eat quoted string
		if (ch == '\"' || (ch == '\'' && generateCharTokens)) {
			char terminal = ch;
			get();
			while ((ch = peek()) != terminal && ch != '\r' && ch != '\n' && ch != 0) {
				int prev = idx;
				if (ch == '\\') {
					if (consumeCatenatedLine())
						continue;
					// eat the escape
					get();
					prev = idx;
					// eat the next character (fall through)
				}
				idx = prev;
				get();
			}
			if (ch == terminal) {
				get();
				return terminal == '\"' ? IToken.STRING : IToken.CHAR;
			} else {
				return IToken.RAW;
			}
		}
		
		// take one char
		get();
		consumeCatenatedLine();
		return IToken.PUNC;
	}

	/**
	 * Tell whether to generate IToken.CHAR tokens -- on by default
	 * @return
	 */
	protected void setGenerateCharTokens(boolean flag) {
		generateCharTokens = flag;
	}

}
