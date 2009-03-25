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
package com.nokia.carbide.internal.cpp.epoc.engine.parser.pkg;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.BaseTokenManager;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.PKGParserCoreConstants;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.Token;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.TokenManager;

/*
 * We gut out the BaseTokenManager intended for C/C++ preprocessor
 * and reuse Token get/peek/unget to make our life easier
 */
public class PKGTokenManager extends BaseTokenManager implements TokenManager,
		PKGParserCoreConstants {

	private IPKGParserConfiguration config;

	public PKGTokenManager(IPKGParserConfiguration config, IDocument document,
			IPath path) {
		super(document, path);
		setGenerateCharTokens(false);
		this.config = config;
	}

	// Yike, MakeSis does not specify EOL in it's grammar
	// It doesn't include EOL as Token and use special readChar()
	// to take comment to the end of the line... Mimic that sad behavior
	public Token getNextAsCommentBodyToken() {
		int start = idx;
		start();
		char ch = peek();
		// eats EOL/whitespace
		while (ch != '\n' && ch != '\r' && ch != 0) {
			get();
			ch = peek();
		}
		String tokString = copyToken();
		IToken itoken = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
				IToken.RAW, tokString, location, start, tokString.length(),
				false, false);
		return convertToken(itoken, PKGParserCoreConstants.RAW);
	}

	public Token getNextToken() {
		char ch = peek();
		// eats EOL/whitespace
		while ((isWhitespace(ch) || ch == '\n' || ch == '\r') && ch != 0) {
			get();
			ch = peek();
		}
		int start = idx;
		start();
		int type = scanPKGToken();
		String tokString = copyToken();
		IToken itoken = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
				IToken.RAW, tokString, location, start, tokString.length(),
				false, false);
		return convertToken(itoken, type);
	}

	private int consumeStringStartingFromDoubleQuote() {
		char ch;
		get();
		while ((ch = peek()) != '\"' && ch != '\r' && ch != '\n' && ch != 0) {
			get();
		}
		get();
		if (ch == '\"') {
			if (peek() == '<') {
				return consumeStringStartingFromEscapeSequence();
			}
		} else {
			return returnRaw();
		}
		return PKGParserCoreConstants.STRING;
	}

	private int consumeStringStartingFromEscapeSequence() {
		char ch;
		get();
		// don't keep parsing if it's not a legal number
		if (!consumeNumericSequence()) {
			return returnRaw();
		}
		ch = get();
		if (ch == '>') {
			if (peek() == '\"') {
				return consumeStringStartingFromDoubleQuote();
			}
		} else {
			return returnRaw();
		}
		return PKGParserCoreConstants.STRING;
	}

	private int returnRaw() {
		while (currentToken.length() > 1) {
			unget();
		}
		return PKGParserCoreConstants.RAW;
	}

	/** Scan a PP token */
	protected int scanPKGToken() {
		char ch = peek();

		switch (ch) {
		case ';':
			// ; -> comment_start
			get();
			return PKGParserCoreConstants.COMMENT_START;

			// & -> language_start
		case '&':
			get();
			return PKGParserCoreConstants.LANGUAGE_START;

			// % -> multilangual_vendor_start
		case '%':
			get();
			return PKGParserCoreConstants.MULTILINGUAL_VENDOR_START;

		case '*':
			get();
			return PKGParserCoreConstants.ASTERISK;

		case '@':
			get();
			return PKGParserCoreConstants.AMPERSAND;

			// , -> comma
		case ',':
			get();
			return PKGParserCoreConstants.COMMA;

			// : -> unique_vendor_start
		case ':':
			get();
			return PKGParserCoreConstants.UNIQUE_VENDOR_START;

			// # -> unique_vendor_start
		case '#':
			get();
			return PKGParserCoreConstants.PACKAGE_HEADER_START;

		case '{':
			get();
			return PKGParserCoreConstants.OPEN_CURLY_BRACE;

		case '}':
			get();
			return PKGParserCoreConstants.CLOSE_CURLY_BRACE;

		case '(':
			get();
			return PKGParserCoreConstants.OPEN_PARENTHESIS;

		case ')':
			get();
			return PKGParserCoreConstants.CLOSE_PARENTHESIS;

		case '[':
			get();
			return PKGParserCoreConstants.OPEN_SQUARE_BRACKET;

		case ']':
			get();
			return PKGParserCoreConstants.CLOSE_SQUARE_BRACKET;

		case '~':
			get();
			return PKGParserCoreConstants.TIDLE;

		case '+':
			get();
			return PKGParserCoreConstants.PLUS;

		case '-':
			get();
			return PKGParserCoreConstants.MINUS;

		case '!':
			get();
			return PKGParserCoreConstants.OPTIONSLIST_START;

		case '=':
			get();
			return PKGParserCoreConstants.EQUAL;

		case '<':
			get();
			ch = peek();
			if (ch == '=') {
				get();
				return PKGParserCoreConstants.LESS_EQUAL;
			} else if (ch == '>') {
				get();
				return PKGParserCoreConstants.LESS_GREATER;
			} else {
				int result = consumeStringStartingFromEscapeSequence();
				if (result != PKGParserCoreConstants.STRING) {
					return PKGParserCoreConstants.LESS;
				}
				return PKGParserCoreConstants.STRING;
			}

		case '>':
			get();
			ch = peek();
			if (ch == '=') {
				get();
				return PKGParserCoreConstants.GREATER_EQUAL;
			}
		}

		// alpha -> identifier
		// if (isAlpha(ch)) {
		// consumeAlphaNumericSequence();
		// return IToken.IDENTIFIER;
		// }
		// numeric -> number
		if (isNumeric(ch)) {
			consumeNumericSequence();
			return PKGParserCoreConstants.INT;
		}

		// eat quoted string, PKG strings are single line
		if (ch == '\"') {
			return consumeStringStartingFromDoubleQuote();
		}

		if (isAlpha(ch)) {
			do {
				ch = get();
			} while (isAlpha(peek()));

			if (currentToken.length() == 2) {
				if (peek() == '(') {
					int dialectLength = 0;

					do {
						get(); // find a (dialect) that is not too long, must
								// be a number within
					} while (dialectLength++ < 10
							&& (isNumeric(peek()) || peek() == 'x' || peek() == 'X')
							&& peek() != ')');

					if (peek() == ')') {
						get();
						return PKGParserCoreConstants.WORD; // Language(dialect)
					} else {
						while (currentToken.length() > 2) {
							unget();
						}
					}
				}
			}

			String word = currentToken.toString();

			if (word.equals("SH") || word.equals("NC") || word.equals("RU")) {
				return PKGParserCoreConstants.PACKAGE_OPTION;
			}

			if (word.equals("TYPE")) {
				while (peek() == ' ' || peek() == '\t') {
					get();
				}
				if (peek() == '=') {
					do {
						get();
					} while (peek() == ' ' || peek() == '\t');
					if (isAlpha(peek())) {
						while (isAlpha(peek())) {
							get();
						}
						String typeString = currentToken.toString();
						typeString = typeString.substring(typeString
								.lastIndexOf("=") + 1);
						typeString = typeString.trim();
						if (typeString.equals("SA") || typeString.equals("SP")
								|| typeString.equals("SA")
								|| typeString.equals("PU")
								|| typeString.equals("PA")
								|| typeString.equals("PP")) {
							return PKGParserCoreConstants.PACKAGE_OPTION;
						}
					}
				}

				// give up the TYPE... just return TYPE as word
				while (currentToken.toString().length() > "TYPE".length()) {
					unget();
				}
			}

			if (word.toLowerCase().equals("if")) {
				return PKGParserCoreConstants.IF;
			}

			if (word.toLowerCase().equals("elseif")) {
				return PKGParserCoreConstants.ELSEIF;
			}

			if (word.toLowerCase().equals("else")) {
				return PKGParserCoreConstants.ELSE;
			}

			if (word.toLowerCase().equals("endif")) {
				return PKGParserCoreConstants.ENDIF;
			}

			if (word.toLowerCase().equals("and")) {
				return PKGParserCoreConstants.AND;
			}

			if (word.toLowerCase().equals("or")) {
				return PKGParserCoreConstants.OR;
			}

			if (word.toLowerCase().equals("not")) {
				return PKGParserCoreConstants.NOT;
			}

			if (word.toLowerCase().equals("exists")) {
				return PKGParserCoreConstants.COND_EXISTS;
			}

			if (word.toLowerCase().equals("approp")) {
				return PKGParserCoreConstants.COND_APPROP;
			}

			if (word.toLowerCase().equals("package")) {
				return PKGParserCoreConstants.COND_PACKAGE;
			}

			return PKGParserCoreConstants.WORD;
		}

		switch (ch) {
		case 0:
			atNewLine = false;
			reachedEOF = true;
			return PKGParserCoreConstants.EOF;
		default:
			get();
			return returnRaw();
		}
	}

}
