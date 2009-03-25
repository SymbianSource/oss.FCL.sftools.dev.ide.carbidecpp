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

package com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.DocumentTokenLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ASTToken;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.*;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.PreprocessedTranslationUnitTokenIterator;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.*;


public class MMPTokenManager implements TokenManager, MMPParserCoreConstants {

	Map<String, Integer> mmpKeywords;
	private IMMPParserConfiguration config;
	private PreprocessedTranslationUnitTokenIterator tokenIter;
	private IToken lastToken; // last one returned
	private IToken nextToken; // next one to return
	private boolean firstTokenOnLine;
	private ASTToken eofToken;
	private String blockType;
	private IASTTranslationUnit ppTu;
	private IPreprocessorResults ppResults;
	
	/**
	 * @param document
	 * @param path
	 * @param config 
	 */
	public MMPTokenManager(IMMPParserConfiguration config, IPreprocessorResults ppResults) {
		this.ppResults = ppResults;
		this.ppTu = ppResults.getFilteredTranslationUnit();
		this.tokenIter = new PreprocessedTranslationUnitTokenIterator(ppTu);
		
		mmpKeywords = new HashMap<String, Integer>();
		this.firstTokenOnLine = true;
		this.config = config; 
		
		mmpKeywords.put("END", END); //$NON-NLS-1$
		if (config.isAifStatementRecognized())
			mmpKeywords.put("AIF", AIF); //$NON-NLS-1$
		if (config.isOptionStatementRecognized()) {
			mmpKeywords.put("OPTION", OPTION); //$NON-NLS-1$
			mmpKeywords.put("LINKEROPTION", LINKEROPTION); //$NON-NLS-1$
			mmpKeywords.put("OPTION_REPLACE", OPTION_REPLACE); //$NON-NLS-1$
		}
		if (config.isStartBlockStatementRecognized())
			mmpKeywords.put("START", START); //$NON-NLS-1$
		if (config.isUidStatementRecognized())
			mmpKeywords.put("UID", UID); //$NON-NLS-1$
	}

	/**
	 * Get the parser's token type for the given token type.
	 * @param token
	 * @return
	 */
	private int classifyToken(int iType) {
		switch (iType) {
		case IToken.EOF:
			return EOF;
		case IToken.EOL:
			return EOL;
		case IToken.IDENTIFIER:
		case IToken.NUMBER:
		case IToken.STRING:
		case IToken.PUNC:
		case IToken.CHAR:
			return ARGUMENT;
		default:
			Check.checkState(false);
			return -1;
		}
	}
	
	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.parser.ASTTokenManager#getNextToken()
	 */
	public Token getNextToken() {
		ASTToken token = null;
		token = readToken();
		if (token == null) {
			if (eofToken == null) {
				IToken iEofToken;
				if (lastToken != null)
					iEofToken = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
							IToken.EOF, "", lastToken.getLocation(),//$NON-NLS-1$ 
							lastToken.getEndOffset(), 0, false, false);
				else
					iEofToken = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
							IToken.EOF, "",  //$NON-NLS-1$
							new DocumentTokenLocation(ppTu.getMainDocument(), ppTu.getMainLocation()),
							ppTu.getMainDocument().getLength(), 0,
							false, false);
				eofToken = new ASTToken(iEofToken, EOF);
			}
			return eofToken;
		}
		
		if (firstTokenOnLine) {
			if (token.kind == ARGUMENT) {
				int type = categorizeStatement(token.image);
				if (type != -1)
					token = new ASTToken(token.iToken, type);
				
				if (token.image.equalsIgnoreCase("MACRO")) { //$NON-NLS-1$
					// Yikes, the rest of the line is probably frotzed,
					// since this is likely to reference the same macros passed 
					// by the platform.  Replace the line with the unpreprocessed one.
					tokenIter.swapLineForUnpreprocessedLine(ppResults, 1); 
					nextToken = null;
				}
				
			}
			firstTokenOnLine = false;
		}
		if (token.kind == EOL) {
			firstTokenOnLine = true;
		}
		return token;
	}
	
	/**
	 * Read the next AST Token
	 * @return
	 */
	private ASTToken readToken() {
		IToken first = fetch();
		if (first == null)
			return null;
		
		StringBuilder tokenText = new StringBuilder();
		int thisClass = classifyToken(first.getType());
		tokenText.append(first.getText());
		
		IToken last = first;
		IToken token = fetch();
		while (token != null
				&& classifyToken(token.getType()) == thisClass
				&& !token.followsSpace() 
				&& token.getType() != IToken.EOL && token.getType() != IToken.EOF) {
			tokenText.append(token.getText());
			last = token;
			token = fetch();
		}
		
		nextToken = token;
		lastToken = last;
		
		return createToken(thisClass, tokenText.toString(), first, last);
	}

	/**
	 * Get the next IToken or use the last cached value.
	 * @return
	 */
	private IToken fetch() {
		while (true) {
			if (nextToken != null) {
				IToken itoken = nextToken;
				nextToken = null;
				if (itoken.getType() == IToken.RAW)
					continue;
				return itoken;
			}
			else /*if (tokenIter.hasNext())*/ {
				try {
					IToken itoken = tokenIter.next();
					if (itoken.getType() == IToken.RAW)
						continue;
					return itoken;
				} catch (NoSuchElementException e) {
					return null;
				}
			}
		}
	}

	private ASTToken createToken(int type, String text, IToken first, IToken last) {
		return new ASTToken(first, last, text, type);
	}

	/**
	 * Get the parser's token type for the given statement keyword.
	 * @param text
	 * @return
	 */
	private int categorizeStatement(String text) {
		String canonicalToken = text.toUpperCase();
		Integer type = mmpKeywords.get(canonicalToken);
		if (type != null) {
			// don't recognize START inside START
			if (type == MMPParserCoreConstants.START
					&& blockType != null
					&& (blockType.equalsIgnoreCase("RESOURCE") //$NON-NLS-1$
							|| blockType.equalsIgnoreCase("BITMAP") //$NON-NLS-1$
							|| blockType.equalsIgnoreCase("STRINGTABLE"))) { //$NON-NLS-1$
				return -1;
			}

			return type.intValue();
		}
		
		// special case for overloaded token
		if (blockType != null
				&& blockType.equalsIgnoreCase("BITMAP") //$NON-NLS-1$
				&& config.isBitmapSourceStatementRecognized()
				&& "SOURCE".equals(canonicalToken))  //$NON-NLS-1$
			return BITMAP_SOURCE;
		
		int category = config.categorizeStatement(canonicalToken);
		switch (category) {
		case IMMPParserConfiguration.UNKNOWN_STATEMENT:
			return -1;
		case IMMPParserConfiguration.FLAG_STATEMENT:
			return FLAG_STATEMENT;
		case IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT:
			return SINGLE_ARGUMENT_STATEMENT;
		case IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT:
			return LIST_ARGUMENT_STATEMENT;
		default:
			Check.checkState(false);
			return 0;
		}
	}
}
