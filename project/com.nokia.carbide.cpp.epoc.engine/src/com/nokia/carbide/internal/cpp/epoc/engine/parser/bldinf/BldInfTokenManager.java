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

package com.nokia.carbide.internal.cpp.epoc.engine.parser.bldinf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.DocumentTokenLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ASTToken;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.BldInfParserCoreConstants;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.Token;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.TokenManager;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.*;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.*;


public class BldInfTokenManager implements TokenManager, BldInfParserCoreConstants {

	Map<String, Integer> bldInfKeywords;
	private BlockAwarePreprocessedTranslationUnitTokenIterator tokenIter;
	private IToken lastToken; // last one returned
	private IToken nextToken; // next one to return
	private boolean firstTokenOnLine;
	private ASTToken eofToken;
	private IASTTranslationUnit ppTu;
	private boolean inExtension;
	private IConditionalBlock nextBlock;
	private IConditionalBlock lastBlock;
	
	/**
	 * @param document
	 * @param path
	 * @param config 
	 */
	public BldInfTokenManager(IPreprocessorResults ppResults) {
		this.ppTu = ppResults.getFilteredTranslationUnit();
		this.tokenIter = new BlockAwarePreprocessedTranslationUnitTokenIterator(ppResults, ppTu);
		
		bldInfKeywords = new HashMap<String, Integer>();
		this.firstTokenOnLine = true;
		this.inExtension = false;

		bldInfKeywords.put("PRJ_PLATFORMS", PRJ_PLATFORMS); //$NON-NLS-1$
		bldInfKeywords.put("PRJ_EXPORTS", PRJ_EXPORTS); //$NON-NLS-1$
		bldInfKeywords.put("PRJ_TESTEXPORTS", PRJ_TESTEXPORTS); //$NON-NLS-1$
		bldInfKeywords.put("PRJ_MMPFILES", PRJ_MMPFILES); //$NON-NLS-1$
		bldInfKeywords.put("PRJ_TESTMMPFILES", PRJ_TESTMMPFILES); //$NON-NLS-1$
		bldInfKeywords.put("PRJ_EXTENSIONS", PRJ_EXTENSIONS); //$NON-NLS-1$
		bldInfKeywords.put("PRJ_TESTEXTENSIONS", PRJ_TESTEXTENSIONS); //$NON-NLS-1$
		
		bldInfKeywords.put("GNUMAKEFILE", GNUMAKEFILE); //$NON-NLS-1$
		bldInfKeywords.put("NMAKEFILE", NMAKEFILE); //$NON-NLS-1$
		bldInfKeywords.put("MAKEFILE", MAKEFILE); //$NON-NLS-1$
		
		bldInfKeywords.put(":ZIP", ZIP); //$NON-NLS-1$
	}

	public void setInExtension(boolean in) {
		this.inExtension = in;
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
		case IToken.BLOCK_ENTER:
			return BLOCK_ENTER;
		case IToken.BLOCK_EXIT:
			return BLOCK_EXIT;
		case IToken.BLOCK_SWITCH:
			return BLOCK_SWITCH;
		default:
			Check.checkState(false);
			return -1;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.parser.ASTTokenManager#getNextToken()
	 */
	public Token getNextToken() {
		BldInfToken token = readToken();
		
		if (token == null) {
			if (eofToken == null) {
				IToken iEofToken;
				if (lastToken != null)
					iEofToken = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
							IToken.EOF, "", lastToken.getLocation(), //$NON-NLS-1$
							lastToken.getEndOffset(), 0, false, false);
				else
					iEofToken = new com.nokia.carbide.internal.cpp.epoc.engine.dom.Token(
							IToken.EOF, "",  //$NON-NLS-1$
							new DocumentTokenLocation(ppTu.getMainDocument(), ppTu.getMainLocation()),
							ppTu.getMainDocument().getLength(), 0,
							false, false);
				eofToken = new BldInfToken(lastBlock, iEofToken, EOF);
			}
			return eofToken;
		}
		
		
		if (firstTokenOnLine) {
			if (token.kind == ARGUMENT) {
				int type = categorizeStatement(token.image);
				if (type != -1)
					token = new BldInfToken(token.block, token.iToken, type);
			}
			firstTokenOnLine = false;
		}
		if (token.kind == EOL || isBlockToken(token.kind)) {
			firstTokenOnLine = true;
		}
		return token;
	}
	
	/**
	 * @param kind
	 * @return
	 */
	private boolean isBlockToken(int kind) {
		return kind == BLOCK_ENTER || kind == BLOCK_EXIT || kind == BLOCK_SWITCH;
	}

	/**
	 * Read the next AST Token
	 * @return
	 */
	private BldInfToken readToken() {
		Pair<IToken, IConditionalBlock> firstInfo = fetch();
		if (firstInfo == null)
			return null;
		IToken first = firstInfo.first;
		
		StringBuilder tokenText = new StringBuilder();
		int thisClass = classifyToken(first.getType());
		tokenText.append(first.getText());

		IConditionalBlock block = firstInfo.second;
		
		IToken last = first;
		Pair<IToken, IConditionalBlock> tokenInfo = fetch();
		while (tokenInfo != null
				&& classifyToken(tokenInfo.first.getType()) == thisClass
				&& !tokenInfo.first.followsSpace() 
				&& !isWhitespaceToken(tokenInfo.first.getType())) {
			IToken token = tokenInfo.first;
			tokenText.append(token.getText());
			block = tokenInfo.second;
			last = token;
			tokenInfo = fetch();
		}
		
		nextToken = tokenInfo != null ? tokenInfo.first : null;
		nextBlock = tokenInfo != null ? tokenInfo.second : null;
		lastToken = last;
		lastBlock = nextBlock;
		
		String text = tokenText.toString();
		if (text.equalsIgnoreCase(":zip")) //$NON-NLS-1$
			thisClass = ZIP;
		
		return createToken(block, thisClass, tokenText.toString(), first, last);
	}

	/**
	 * @param type
	 * @return
	 */
	private boolean isWhitespaceToken(int type) {
		 return type == IToken.EOL || type == IToken.EOF 
		 || type == IToken.BLOCK_ENTER || type == IToken.BLOCK_SWITCH || type == IToken.BLOCK_EXIT; 
	}

	/**
	 * Get the next IToken or use the last cached value.
	 * @return
	 */
	private Pair<IToken, IConditionalBlock> fetch() {
		while (true) {
			if (nextToken != null) {
				IToken itoken = nextToken;
				IConditionalBlock block = nextBlock;
				nextToken = null;
				nextBlock = null;
				if (itoken.getType() == IToken.RAW)
					continue;
				return new Pair<IToken, IConditionalBlock>(itoken, block);
			} else {
				try {
					IToken itoken = tokenIter.next();
					IConditionalBlock block = tokenIter.getLastBlock();
					if (itoken.getType() == IToken.RAW)
						continue;
					return new Pair<IToken, IConditionalBlock>(itoken, block);
				} catch (NoSuchElementException e) {
					return null;
				}
			}
		}
	}
	
	private BldInfToken createToken(IConditionalBlock block, int type, String text, IToken first, IToken last) {
		return new BldInfToken(block, first, last, text, type);
	}

	/**
	 * Get the parser's token type for the given statement keyword.
	 * @param text
	 * @return
	 */
	private int categorizeStatement(String text) {
		String canonicalToken = text.toUpperCase();
		Integer type = bldInfKeywords.get(canonicalToken);
		if (type != null)
			return type.intValue();
		
		if (inExtension) {
			if (text.equalsIgnoreCase("START")) //$NON-NLS-1$
				return START;
			if (text.equalsIgnoreCase("END")) //$NON-NLS-1$
				return END;
		}
		return -1;
	}
}
