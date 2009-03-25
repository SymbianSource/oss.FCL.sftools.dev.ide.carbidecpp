/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nokia.carbide.internal.cpp.epoc.engine.Messages;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTListNode;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTLiteralTextExpression;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTLiteralTextNode;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorBinaryExpression;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorDefineStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorElifStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorElseStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorEndifStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorErrorStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorIfStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorIfdefStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorIfndefStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorIncludeStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorLiteralExpression;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorPragmaStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorTokenStream;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorTokenStreamStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorTrinaryExpression;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorUnaryExpression;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorUndefStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorUnknownStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTPreprocessorWarningStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTProblemTopLevelNode;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.Token;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.BaseTokenizer;
import com.nokia.cpp.internal.api.utils.core.*;

/**
 * This is the factory for creating instances of IASTNodes
 * for the basic DOM.
 *
 */
public abstract class ASTFactory {

	/**
	 * Create a list node from the given string list.
	 * @param strings list of strings, or null
	 * @return new list node, or null
	 */
	public static IASTListNode<IASTLiteralTextNode> createPreprocessorLiteralTextNodeList(List<String> strings) {
		if (strings == null)
			return null;
		IASTListNode<IASTLiteralTextNode> nodeList = createLiteralTextNodeList();
		for (String string : strings)
			nodeList.add(createPreprocessorLiteralTextNode(string));
		nodeList.setDirty(false);
		return nodeList;
	}

	/**
	 * Create a list node from the given string array.  The separator by default is a space.
	 * @param strings array of strings, or null
	 * @return new list node, or null
	 */
	public static IASTListNode<IASTLiteralTextNode> createPreprocessorLiteralTextNodeList(String[] strings) {
		if (strings == null)
			return null;
		IASTListNode<IASTLiteralTextNode> nodeList = createLiteralTextNodeList();
		for (String string : strings)
			nodeList.add(createPreprocessorLiteralTextNode(string));
		nodeList.setDirty(false);
		return nodeList;
	}
	
	/**
	 * Create a list node from the given string list.
	 * @param strings list of strings, or null
	 * @return new list node, or null
	 */
	public static IASTListNode<IASTLiteralTextNode> createRawLiteralTextNodeList(List<String> strings) {
		if (strings == null)
			return null;
		IASTListNode<IASTLiteralTextNode> nodeList = createLiteralTextNodeList();
		for (String string : strings)
			nodeList.add(createRawLiteralTextNode(string));
		nodeList.setDirty(false);
		return nodeList;
	}

	/**
	 * Create a list node from the given string array.  The separator by default is a space.
	 * @param strings array of strings, or null
	 * @return new list node, or null
	 */
	public static IASTListNode<IASTLiteralTextNode> createRawLiteralTextNodeList(String[] strings) {
		if (strings == null)
			return null;
		IASTListNode<IASTLiteralTextNode> nodeList = createLiteralTextNodeList();
		for (String string : strings)
			nodeList.add(createRawLiteralTextNode(string));
		nodeList.setDirty(false);
		return nodeList;
	}
	
	/**
	 * Create a list node from the given string array.
	 * @param nodes array of text nodes, or null
	 * @return new list node, or null
	 */
	public static IASTListNode<IASTLiteralTextNode> createLiteralTextNodeList(IASTLiteralTextNode[] nodes) {
		if (nodes == null)
			return null;
		IASTListNode<IASTLiteralTextNode> nodeList = createLiteralTextNodeList();
		for (IASTLiteralTextNode node : nodes)
			nodeList.add(node);
		nodeList.setDirty(false);
		return nodeList;
	}

	
	public static IASTListNode<IASTLiteralTextNode> createLiteralTextNodeList() {
		return createListNode(" "); //$NON-NLS-1$
	}
	
	public static <T extends IASTTopLevelNode> IASTListNode<T> createTopLevelNodeListNode() {
		return new ASTListNode<T>();
	}


	public static <T extends IASTNode> IASTListNode<T> createListNode(String separator) {
		IASTListNode<T> list = new ASTListNode<T>();
		list.setSeparator(separator);
		return list;
	}

	public static IASTLiteralTextExpression createPreprocessorLiteralTextExpression(String text) {
		if (text == null)
			return null;
		return new ASTLiteralTextExpression(IASTLiteralTextExpression.EStyle.PREPROCESSOR, text);
	}

	public static IASTPreprocessorBinaryExpression createPreprocessorBinaryExpression(
			int operator, IASTPreprocessorExpression left, IASTPreprocessorExpression right) {
		return new ASTPreprocessorBinaryExpression(operator, left, right);
	}

	private static final Pattern DEFINE_STATEMENT = Pattern.compile(
	"(\\w+)\\s*(\\((.*)\\))?(\\s*)(.*)?"); //$NON-NLS-1$

	/**
	 * Create a define statement from the suffix of a #define (excluding '#define').
	 * This does a generic parse of the contents, which is not suitable for real use!
	 * @param definition text after '#define' (name, arguments, expansion)
	 * @return
	 */
	public static IASTPreprocessorDefineStatement createPreprocessorDefineStatement(
			String definition) {
		return createPreprocessorDefineStatement(null, definition);
	}

	public static IASTPreprocessorDefineStatement createPreprocessorDefineStatement(
			ITokenLocation location, String definition) {
		Matcher matcher = DEFINE_STATEMENT.matcher(definition);
		Check.checkArg(matcher.matches());
		String macroName = matcher.group(1);
		String macroArgs = matcher.group(3);
		String macroExpansion = matcher.group(5);
		IASTListNode<IASTLiteralTextNode> macroArgsList = null;
		if (macroArgs != null) {
			macroArgsList = createPreprocessorLiteralTextNodeList(macroArgs.split(",")); //$NON-NLS-1$
			macroArgsList.setSeparator(null);
		}
		
		return new ASTPreprocessorDefineStatement(
				createPreprocessorLiteralTextNode(macroName),
				macroArgsList,
				createPreprocessorTokenStream(location, macroExpansion));
	}

	public static IASTPreprocessorDefineStatement createPreprocessorDefineStatement(
			IASTLiteralTextNode macroName,
			IASTListNode<IASTLiteralTextNode> macroArgs,
			IASTPreprocessorTokenStream macroExpansion) {
		return new ASTPreprocessorDefineStatement(macroName, macroArgs, macroExpansion);
	}

	public static IASTPreprocessorElifStatement createPreprocessorElifStatement(
			IASTPreprocessorTokenStream tokenStream) {
		return new ASTPreprocessorElifStatement(tokenStream);
	}
	public static IASTPreprocessorElseStatement createPreprocessorElseStatement() {
		return new ASTPreprocessorElseStatement();
	}

	public static IASTPreprocessorEndifStatement createPreprocessorEndifStatement() {
		return new ASTPreprocessorEndifStatement();
	}

	public static IASTPreprocessorIfdefStatement createPreprocessorIfdefStatement(
			IASTLiteralTextNode macroName) {
		return new ASTPreprocessorIfdefStatement(macroName);
	}

	
	public static IASTPreprocessorIfndefStatement createPreprocessorIfndefStatement(
			IASTLiteralTextNode macroName) {
		return new ASTPreprocessorIfndefStatement(macroName);
	}

	public static IASTPreprocessorIfStatement createPreprocessorIfStatement(
			IASTPreprocessorTokenStream tokenStream) {
		return new ASTPreprocessorIfStatement(tokenStream);
	}

	public static IASTPreprocessorLiteralExpression createPreprocessorLiteralExpression(
			String value) {
		return new ASTPreprocessorLiteralExpression(value);
	}

	public static IASTPreprocessorLiteralExpression createPreprocessorLiteralExpression(boolean value) {
		return new ASTPreprocessorLiteralExpression(Boolean.toString(value));
	}
	
	public static IASTPreprocessorLiteralExpression createPreprocessorLiteralExpression(int value) {
		return new ASTPreprocessorLiteralExpression(Integer.toString(value));
	}
	
	public static IASTPreprocessorTrinaryExpression createPreprocessorTrinaryExpression(
			IASTPreprocessorExpression condition, IASTPreprocessorExpression trueBranch,
			IASTPreprocessorExpression falseBranch) {
		return new ASTPreprocessorTrinaryExpression(condition,
				trueBranch, falseBranch);
	}

	public static IASTPreprocessorUnaryExpression createPreprocessorUnaryExpression(
			int operator, IASTPreprocessorExpression operand) {
		return new ASTPreprocessorUnaryExpression(operator, operand);
	}

	public static IASTPreprocessorUndefStatement createPreprocessorUndefStatement(
			IASTLiteralTextNode macroName) {
		return new ASTPreprocessorUndefStatement(macroName);
	}

	public static IASTPreprocessorIncludeStatement createPreprocessorIncludeStatement(
			String fileName, boolean isUser) {
		if (isUser)
			fileName = '"' + fileName + '"';
		else
			fileName = '<' + fileName + '>';
		return new ASTPreprocessorIncludeStatement(
				createPreprocessorTokenStream(fileName));
	}
	public static IASTPreprocessorIncludeStatement createPreprocessorIncludeStatement(
			IASTPreprocessorTokenStream name) {
		if (name == null)
			name = createPreprocessorTokenStream();
		return new ASTPreprocessorIncludeStatement(name);
	}

	public static IASTPreprocessorErrorStatement createPreprocessorErrorStatement(
			String text) {
		return new ASTPreprocessorErrorStatement(
				createPreprocessorTokenStream(text));
	}
	public static IASTPreprocessorErrorStatement createPreprocessorErrorStatement(
			IASTPreprocessorTokenStream error) {
		if (error == null)
			error = createPreprocessorTokenStream();
		return new ASTPreprocessorErrorStatement(error);
	}

	public static IASTPreprocessorWarningStatement createPreprocessorWarningStatement(
			String text) {
		return new ASTPreprocessorWarningStatement(
				createPreprocessorTokenStream(text));
	}
	public static IASTPreprocessorWarningStatement createPreprocessorWarningStatement(
			IASTPreprocessorTokenStream warning) {
		if (warning == null)
			warning = createPreprocessorTokenStream();
		return new ASTPreprocessorWarningStatement(warning);
	}

	public static IASTPreprocessorPragmaStatement createPreprocessorPragmaStatement(
			String text) {
		return new ASTPreprocessorPragmaStatement(
				createPreprocessorTokenStream(text));
	}
	public static IASTPreprocessorPragmaStatement createPreprocessorPragmaStatement(
			IASTPreprocessorTokenStream pragma) {
		if (pragma == null)
			pragma = createPreprocessorTokenStream();
		return new ASTPreprocessorPragmaStatement(pragma);
	}

	public static IASTPreprocessorUnknownStatement createPreprocessorUnknownStatement(
			String text) {
		return new ASTPreprocessorUnknownStatement(
				createPreprocessorTokenStream(text));
	}
	public static IASTPreprocessorUnknownStatement createPreprocessorUnknownStatement(
			IASTPreprocessorTokenStream unknown) {
		if (unknown == null)
			unknown = createPreprocessorTokenStream();
		return new ASTPreprocessorUnknownStatement(unknown);
	}

	public static IASTTranslationUnit createTranslationUnit() {
		return new ASTTranslationUnit(new ASTListNode<IASTTopLevelNode>());
	}

	public static IASTTranslationUnit createTranslationUnit(
			IASTListNode<IASTTopLevelNode> nodes) {
		if (nodes == null)
			nodes = createTopLevelNodeListNode();
		return new ASTTranslationUnit(nodes);
	}

	public static IASTLiteralTextNode createPreprocessorLiteralTextNode(String string) {
		if (string == null)
			return null;

		return new ASTLiteralTextNode(IASTLiteralTextNode.EStyle.PREPROCESSOR, string);
	}

	public static IASTLiteralTextNode createRawLiteralTextNode(String string) {
		if (string == null)
			return null;

		return new ASTLiteralTextNode(IASTLiteralTextNode.EStyle.RAW, string);
	}
	public static IASTLiteralTextNode createLiteralTextNode(IASTLiteralTextNode.EStyle style, String string) {
		if (string == null)
			return null;
		
		return new ASTLiteralTextNode(style, string);
	}
	
	/** Create a message using a stock message key (see IASTMessages) */
	public static IMessage createMessage(int severity, String messageKey, Object[] args, MessageLocation location) {
		IMessage message = new Message(severity, location, messageKey, 
				MessageFormat.format(Messages.getString(messageKey),
						args));
		return message;
	}
	
	/** Create an error using a stock message key (see IASTMessages) */
	public static IMessage createErrorMessage(String messageKey, Object[] args, MessageLocation location) {
		return createMessage(IMessage.ERROR, messageKey, args, location);
	}
	
	/** Create a preprocessor token.<p>
	 * @param type IToken#...
	 * @param text the raw text
	 * @param location the token location
	 * @param offset offset within location
	 * */
	public static IToken createToken(int type, String text, ITokenLocation location, int offset,
			boolean followsSpace, boolean followsNewline) {
		return new Token(type, text, location, offset, text.length(), followsSpace, followsNewline);
	}
	
	/**
	 * Do a simple scan of the given text to identify identifiers,
	 * numbers, and single punctuation characters.  For use in testing
	 * only -- otherwise use the real parser.
	 * @param text
	 * @return
	 */
	public static List<IToken> createTokenList(ITokenLocation location, String text) {
		if (text == null)
			return null;
		List<IToken> tokenList = new ArrayList<IToken>();
		
		BaseTokenizer tokenizer = new BaseTokenizer(location, text.toCharArray());
		while (!tokenizer.atEOF()) {
			IToken token = tokenizer.readToken();
			tokenList.add(token);
		}
		return tokenList;
	}


	public static List<IToken> createTokenList(IToken[] tokens) {
		if (tokens == null)
			return null;
		List<IToken> tokenList = new ArrayList<IToken>();
		for (IToken token : tokens)
			tokenList.add(token);
		return tokenList;
	}
	
	/**
	 * Do a simple scan of the given text to identify identifiers,
	 * numbers, and single punctuation characters.  For use in testing
	 * only -- otherwise use the real parser.
	 * @param text
	 * @return
	 */
	public static IASTPreprocessorTokenStream createPreprocessorTokenStream(String text) {
		return createPreprocessorTokenStream(createTokenList(null, text));
	}

	public static IASTPreprocessorTokenStream createPreprocessorTokenStream(ITokenLocation location, String text) {
		return createPreprocessorTokenStream(createTokenList(location, text));
	}


	public static IASTPreprocessorTokenStream createPreprocessorTokenStream(List<IToken> tokens) {
		if (tokens == null)
			return null;
		return new ASTPreprocessorTokenStream(tokens);
	}
	
	public static IASTPreprocessorTokenStream createPreprocessorTokenStream(IToken[] tokens) {
		return createPreprocessorTokenStream(createTokenList(tokens));
	}

	public static IASTPreprocessorTokenStream createPreprocessorTokenStream() {
		List<IToken> tokenList = new ArrayList<IToken>();
		return new ASTPreprocessorTokenStream(tokenList);
	}
	public static IASTPreprocessorTokenStreamStatement createPreprocessorTokenStreamStatement(
			String text) {
		return createPreprocessorTokenStreamStatement(null, text);
	}
	public static IASTPreprocessorTokenStreamStatement createPreprocessorTokenStreamStatement(
			ITokenLocation location, String text) {
		return new ASTPreprocessorTokenStreamStatement(createPreprocessorTokenStream(location, text));
	}

	public static IASTPreprocessorTokenStreamStatement createPreprocessorTokenStreamStatement(List<IToken> tokens) {
		if (tokens == null)
			return null;
		return new ASTPreprocessorTokenStreamStatement(createPreprocessorTokenStream(tokens));
	}

	public static IASTPreprocessorTokenStreamStatement createPreprocessorTokenStreamStatement(IToken[] tokens) {
		return createPreprocessorTokenStreamStatement(createTokenList(tokens));
	}

	public static IASTPreprocessorTokenStreamStatement createPreprocessorTokenStreamStatement() {
		List<IToken> tokenList = new ArrayList<IToken>();
		return new ASTPreprocessorTokenStreamStatement(createPreprocessorTokenStream(tokenList));
	}
	public static IASTPreprocessorTokenStreamStatement createPreprocessorTokenStreamStatement(
			IASTPreprocessorTokenStream tokens) {
		return new ASTPreprocessorTokenStreamStatement(tokens);
	}

	public static IASTProblemTopLevelNode createProblemTopLevelNode(IASTPreprocessorTokenStream stream, IMessage message) {
		return new ASTProblemTopLevelNode(stream, message);
	}
}
