/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.parser.gen;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.DocumentTokenLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.PreprocessedTranslationUnitTokenIterator;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstSourceFile;
import com.nokia.sdt.sourcegen.doms.rss.parser.AugmentedPreprocessedTranslationUnitTokenIterator;
import com.nokia.sdt.sourcegen.doms.rss.parser.RssParser;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.*;

/**
 * This token manager uses the preprocessed DOM to retrieve tokens for the RSS parser.
 * 
 *
 */
public class RssTokenManager implements TokenManager, SymbianRssParserConstants {

	private IASTTranslationUnit ppTu;
	private PreprocessedTranslationUnitTokenIterator tokenIter;
	private IToken nextToken;
	private IToken lastToken;
	private RssToken eofToken;
	private RssParser rssParser;

	private static final Map<String, Integer> puncTokens = new HashMap<String, Integer>();
	
	static {
        puncTokens.put("(", new Integer(LPAREN)); //$NON-NLS-1$
        puncTokens.put(")", new Integer(RPAREN)); //$NON-NLS-1$
        puncTokens.put("{", new Integer(LBRACE)); //$NON-NLS-1$
        puncTokens.put("}", new Integer(RBRACE)); //$NON-NLS-1$
        puncTokens.put("[", new Integer(LBRACKET)); //$NON-NLS-1$
        puncTokens.put("]", new Integer(RBRACKET)); //$NON-NLS-1$
        puncTokens.put("<", new Integer(LT)); //$NON-NLS-1$
        puncTokens.put(">", new Integer(GT)); //$NON-NLS-1$
        puncTokens.put("+", new Integer(PLUS)); //$NON-NLS-1$
        puncTokens.put("-", new Integer(MINUS)); //$NON-NLS-1$
        puncTokens.put("/", new Integer(DIV)); //$NON-NLS-1$
        puncTokens.put("*", new Integer(TIMES)); //$NON-NLS-1$
        puncTokens.put("%", new Integer(MOD)); //$NON-NLS-1$
        puncTokens.put("|", new Integer(OR)); //$NON-NLS-1$
        puncTokens.put("=", new Integer(EQUALS)); //$NON-NLS-1$
        puncTokens.put(";", new Integer(SEMICOLON)); //$NON-NLS-1$
        puncTokens.put(",", new Integer(COMMA)); //$NON-NLS-1$

	}
	
    static private final Map<String, Integer> keywords = new HashMap<String, Integer>();
    static {
        keywords.put("CHARACTER_SET", new Integer(CHARACTER_SET)); //$NON-NLS-1$
        keywords.put("NAME", new Integer(NAME)); //$NON-NLS-1$
        keywords.put("ENUM", new Integer(ENUM)); //$NON-NLS-1$
        keywords.put("enum", new Integer(ENUM)); //$NON-NLS-1$
        keywords.put("STRUCT", new Integer(STRUCT)); //$NON-NLS-1$
        keywords.put("RESOURCE", new Integer(RESOURCE)); //$NON-NLS-1$
        keywords.put("BYTE", new Integer(BYTE)); //$NON-NLS-1$
        keywords.put("WORD", new Integer(WORD)); //$NON-NLS-1$
        keywords.put("LONG", new Integer(LONG)); //$NON-NLS-1$
        keywords.put("TEXT", new Integer(TEXT)); //$NON-NLS-1$
        keywords.put("DOUBLE", new Integer(DOUBLE)); //$NON-NLS-1$
        keywords.put("LTEXT", new Integer(LTEXT)); //$NON-NLS-1$
        keywords.put("LTEXT8", new Integer(LTEXT8)); //$NON-NLS-1$
        keywords.put("BUF", new Integer(BUF)); //$NON-NLS-1$
        keywords.put("BUF8", new Integer(BUF8)); //$NON-NLS-1$
        keywords.put("LINK", new Integer(LINK)); //$NON-NLS-1$
        keywords.put("LLINK", new Integer(LLINK)); //$NON-NLS-1$
        keywords.put("SRLINK", new Integer(SRLINK)); //$NON-NLS-1$
        keywords.put("LEN", new Integer(LEN)); //$NON-NLS-1$
        keywords.put("UID2", new Integer(UID2)); //$NON-NLS-1$
        keywords.put("UID3", new Integer(UID3)); //$NON-NLS-1$
        keywords.put("rls_string", new Integer(RLS_STRING)); //$NON-NLS-1$
        keywords.put("LTEXT16", new Integer(LTEXT16)); //$NON-NLS-1$
        keywords.put("TEXT16", new Integer(TEXT16)); //$NON-NLS-1$
    }

    
	/**
	 * @param ppResults
	 */
	public RssTokenManager(RssParser rssParser, IPreprocessorResults ppResults) {
		this.rssParser = rssParser;
		this.ppTu = ppResults.getFilteredTranslationUnit();
		this.tokenIter = new AugmentedPreprocessedTranslationUnitTokenIterator(
				ppTu, rssParser);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.parser.gen.TokenManager#getNextToken()
	 */
	public Token getNextToken() {
		RssToken token = null;
		//do {
			token = readToken();
			if (token == null /*!tokenIter.hasNext() && nextToken == null*/) {
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
					eofToken = createToken(EOF, "", iEofToken, iEofToken);
				}
				return eofToken;
			}
			
		//} while (token == null);
		
		return token;
	}

	/**
	 * Read the next AST Token
	 * @return
	 */
	private RssToken readToken() {
		IToken first = fetch();
		if (first == null)
			return null;
		
		StringBuilder tokenText = new StringBuilder();
		int thisClass = classifyToken(first);
		tokenText.append(first.getText());
		
		IToken last = first;
		//IToken token = fetch();
		/*
		while (token != null
				&& classifyToken(token) == thisClass
				&& !token.followsSpace() 
				&& token.getType() != IToken.EOL && token.getType() != IToken.EOF) {
			tokenText.append(token.getText());
			last = token;
			token = fetch();
		}
		*/
		//nextToken = token;
		nextToken = null;
		lastToken = last;
		
		return createToken(thisClass, tokenText.toString(), first, last);
	}

	/**
	 * @param token
	 * @return
	 */
	private int classifyToken(IToken token) {
		String text = token.getText();
		if (token.getType() == IToken.PUNC) {
			Integer kw = puncTokens.get(text);
			if (kw != null)
				return kw;
		}
		
		if (token.getType() == IToken.IDENTIFIER) {
			// map any identifiers to RSS keywords
			Integer kw = keywords.get(text);
			if (kw != null)
				return kw;
		}
		
		int kind;
    	switch (token.getType()) {
        case IToken.EOF:
        	kind = EOF;
        	break;
        case IToken.EOL:
        	Check.checkState(false);
        case IToken.IDENTIFIER:
        	kind = IDENTIFIER;
        	break;
        case IToken.NUMBER:
        	text = text.toLowerCase();
        	if (!text.startsWith("0x") && (text.contains(".") || text.contains("e")))
        		kind = FLOAT;
        	else
        		kind = INTEGER;
        	break;
        case IToken.CHAR:
    		kind = CHAR;
    		break;
        case IToken.STRING:
        	kind = STRING;
        	break;
        
        case IToken.PUNC:
        case IToken.RAW:
        default:
        	kind = ERROR;
        	break;
        }
        
		return kind;
	}

	/**
	 * Get the next IToken or use the last cached value.  Ignore raw tokens (empty macro
	 * expansions) or end of line characters, which aren't syntactically significant.
	 * If a file switch is detected, stop parsing.
	 * @return
	 */
	private IToken fetch() {
		while (true) {
			IToken itoken = nextToken;
			if (itoken == null) {
				try {
					itoken = tokenIter.next();
				} catch (NoSuchElementException e) {
					
				}
			}
			if (itoken != null) {
				nextToken = null;
				//if (itoken.getType() == IToken.FILE_SWITCH) 
				//	return null;
				if (itoken.getType() == IToken.RAW || itoken.getType() == IToken.EOL)
					continue;
				return itoken;
			} else {
				return null;
			}
		}
	}
	
	private RssToken createToken(int type, String text, IToken first, IToken last) {
		// guaranteed to come from only one file, so combine but ignore the source
		IToken token = ParserUtils.getCombinedToken(first, last, text);
		ISourceRegion region = ParserUtils.getFlattenedTokenLocation(token, token.getLocation());
    	Check.checkState(region != null);
		IAstSourceFile sourceFile = rssParser.getSourceFile(region.getFirstLocation());
		return new RssToken(sourceFile.getSourceFile(), sourceFile, region.getInclusiveHeadRegion().getRegion(), type, text);
	}

}
