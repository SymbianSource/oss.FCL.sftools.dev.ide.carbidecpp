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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp;

import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTListNode;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.mmp.*;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating MMP DOM nodes.
 * 
 */
public abstract class ASTMMPFactory extends ASTFactory {
	public static <T extends IASTMMPStatement> IASTListNode<T> createMMPStatementListNode() {
		return new ASTListNode<T>();
	}

	public static IASTMMPAifStatement createMMPAifStatement(String targetFile,
			String sourcePath, String resource, String colorDepth,
			String[] sourceBitmaps) {
		IASTListNode<IASTLiteralTextNode> sourceBitmapList = createPreprocessorLiteralTextNodeList(sourceBitmaps);
		if (sourceBitmapList == null)
			sourceBitmapList = createLiteralTextNodeList();
		return new ASTMMPAifStatement(
				createPreprocessorLiteralTextNode(targetFile), createPreprocessorLiteralTextNode(sourcePath),
				createPreprocessorLiteralTextNode(resource), createPreprocessorLiteralTextNode(colorDepth),
				sourceBitmapList);
	}

	public static IASTMMPAifStatement createMMPAifStatement(String targetFile,
			String sourcePath, String resource, String colorDepth,
			List<String> sourceBitmaps) {
		IASTListNode<IASTLiteralTextNode> sourceBitmapList = createPreprocessorLiteralTextNodeList(sourceBitmaps);
		if (sourceBitmapList == null)
			sourceBitmapList = createLiteralTextNodeList();

		return new ASTMMPAifStatement(
				createPreprocessorLiteralTextNode(targetFile), createPreprocessorLiteralTextNode(sourcePath),
				createPreprocessorLiteralTextNode(resource), createPreprocessorLiteralTextNode(colorDepth),
				sourceBitmapList);
	}

	public static IASTMMPAifStatement createMMPAifStatement(IASTLiteralTextNode targetFile,
			IASTLiteralTextNode sourcePath, IASTLiteralTextNode resource, IASTLiteralTextNode colorDepth,
			IASTListNode<IASTLiteralTextNode> sourceBitmaps) {
		return new ASTMMPAifStatement(
				targetFile, sourcePath,
				resource, colorDepth,
				sourceBitmaps);
	}

	/**
	 * Create a string describing the bitmap format.
	 * 
	 * @param colorDepth
	 *            color depth, 1, 2, 4, 8, 16, 32
	 * @param maskDepth
	 *            mask depth, 0 (none), 1, 4, 8
	 * @param isColor
	 * @return format string
	 */
	public static String createBitmapFormat(int colorDepth, int maskDepth, boolean isColor) {
		return new ImageFormat(isColor, colorDepth, maskDepth).toString();
	}
	
	public static IASTMMPBitmapSourceStatement createMMPBitmapSourceStatement(
			IASTLiteralTextNode format, IASTListNode<IASTLiteralTextNode> bitmaps) {
		return new ASTMMPBitmapSourceStatement(format, bitmaps);
	}

	public static IASTMMPBitmapSourceStatement createMMPBitmapSourceStatement(
			String format, List<String> bitmaps) {
		IASTListNode<IASTLiteralTextNode> bitmapList = createPreprocessorLiteralTextNodeList(bitmaps);
		if (bitmapList == null)
			bitmapList = createLiteralTextNodeList();
		return new ASTMMPBitmapSourceStatement(createPreprocessorLiteralTextNode(format), 
				bitmapList);
	}

	public static IASTMMPBitmapSourceStatement createMMPBitmapSourceStatement(
			String format, String[] bitmaps) {
		IASTListNode<IASTLiteralTextNode> bitmapList = createPreprocessorLiteralTextNodeList(bitmaps);
		if (bitmapList == null)
			bitmapList = createLiteralTextNodeList();
		return new ASTMMPBitmapSourceStatement(createPreprocessorLiteralTextNode(format), 
				bitmapList);
	}

	public static IASTMMPFlagStatement createMMPFlagStatement(IASTLiteralTextNode keyword) {
		return new ASTMMPFlagStatement(keyword);
	}
	public static IASTMMPFlagStatement createMMPFlagStatement(String keyword) {
		return new ASTMMPFlagStatement(createPreprocessorLiteralTextNode(keyword));
	}
	
	public static IASTMMPOptionStatement createMMPOptionStatement(
			IASTLiteralTextNode keyword, IASTLiteralTextNode compiler, IASTListNode<IASTLiteralTextNode> options) {
		return new ASTMMPOptionStatement(keyword, compiler, options);
	}
	public static IASTMMPOptionStatement createMMPOptionStatement(String keyword, String compiler, String[] options) {
		return new ASTMMPOptionStatement(createPreprocessorLiteralTextNode(keyword),
				createPreprocessorLiteralTextNode(compiler), 
				createPreprocessorLiteralTextNodeList(options));
	}
	
	public static IASTMMPSingleArgumentStatement createMMPSingleArgumentStatement(IASTLiteralTextNode keyword,
			IASTLiteralTextNode argument) {
		return new ASTMMPSingleArgumentStatement(keyword, argument);
	}
	public static IASTMMPSingleArgumentStatement createMMPSingleArgumentStatement(
			String keyword, String argument) {
		return new ASTMMPSingleArgumentStatement(
				createPreprocessorLiteralTextNode(keyword), 
				createPreprocessorLiteralTextNode(argument));
	}

	public static IASTMMPStartBlockStatement createMMPStartBlockStatement(
			String blockType, List<String> blockArguments) {
		IASTListNode<IASTLiteralTextNode> blockArgumentList = createPreprocessorLiteralTextNodeList(blockArguments);
		if (blockArgumentList == null)
			blockArgumentList = createLiteralTextNodeList();
		return new ASTMMPStartBlockStatement(createPreprocessorLiteralTextNode(blockType),
				blockArgumentList,
				createMMPStatementListNode());
	}
	
	public static IASTMMPStartBlockStatement createMMPStartBlockStatement(
			IASTLiteralTextNode blockType, 
			IASTListNode<IASTLiteralTextNode> blockArguments,
			IASTListNode<IASTMMPStatement> statements) {
		return new ASTMMPStartBlockStatement(blockType,
				blockArguments, statements);
	}
	
	public static IASTMMPTranslationUnit createMMPTranslationUnit() {
		return new ASTMMPTranslationUnit(createMMPStatementListNode());
	}

	public static IASTMMPTranslationUnit createMMPTranslationUnit(IASTListNode<? extends IASTTopLevelNode> nodes) {
		if (nodes == null)
			nodes = createMMPStatementListNode();
		return new ASTMMPTranslationUnit(nodes);
	}

	public static IASTMMPUidStatement createMMPUidStatement(String uid2, String uid3) {
		return new ASTMMPUidStatement(createPreprocessorLiteralTextNode(uid2),
				createPreprocessorLiteralTextNode(uid3));
	}

	public static IASTMMPUidStatement createMMPUidStatement(IASTLiteralTextNode uid2, IASTLiteralTextNode uid3) {
		return new ASTMMPUidStatement(uid2, uid3);
	}

	public static IASTMMPListArgumentStatement createMMPListArgumentStatement(String keyword, List<String> arguments) {
		IASTListNode<IASTLiteralTextNode> argumentList = createPreprocessorLiteralTextNodeList(arguments);
		if (argumentList == null)
			argumentList = createLiteralTextNodeList();
		return new ASTMMPListArgumentStatement(createPreprocessorLiteralTextNode(keyword),
				argumentList);
	}

	public static IASTMMPListArgumentStatement createMMPListArgumentStatement(String keyword, String[] arguments) {
		IASTListNode<IASTLiteralTextNode> argumentList = createPreprocessorLiteralTextNodeList(arguments);
		if (argumentList == null)
			argumentList = createLiteralTextNodeList();
		return new ASTMMPListArgumentStatement(createPreprocessorLiteralTextNode(keyword),
				argumentList);
	}

	public static IASTMMPListArgumentStatement createMMPListArgumentStatement(IASTLiteralTextNode keyword, IASTListNode<IASTLiteralTextNode> arguments) {
		return new ASTMMPListArgumentStatement(keyword, arguments);
	}

	public static IASTMMPListArgumentStatement createMMPListArgumentStatement(String keyword) {
		return createMMPListArgumentStatement(keyword, new ArrayList<String>());
	}
	
	public static IASTMMPProblemStatement createMMPProblemStatement(IASTPreprocessorTokenStream tokenStream, IMessage message) {
		return new ASTMMPProblemStatement(tokenStream, message);
	}

	public static IASTMMPProblemStatement createMMPProblemStatement(String string, IMessage message) {
		return createMMPProblemStatement(createPreprocessorTokenStream(string), message);
	}

	public static IASTMMPUnknownStatement createMMPUnknownStatement(String keyword, String[] arguments) {
		return new ASTMMPUnknownStatement(createPreprocessorLiteralTextNode(keyword),
				createPreprocessorLiteralTextNodeList(arguments));
	}

	public static IASTMMPUnknownStatement createMMPUnknownStatement(IASTLiteralTextNode keyword, IASTListNode<IASTLiteralTextNode> arguments) {
		return new ASTMMPUnknownStatement(keyword, arguments);
	}

}
