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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.bldinf.*;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTBldInfFactory extends ASTFactory {
	
	private static final String PRJ_MMPFILES_KEYWORD = "PRJ_MMPFILES"; //$NON-NLS-1$
	private static final String PRJ_TESTMMPFILES_KEYWORD = "PRJ_TESTMMPFILES"; //$NON-NLS-1$
	private static final String PRJ_EXPORTS_KEYWORD = "PRJ_EXPORTS"; //$NON-NLS-1$
	private static final String PRJ_TESTEXPORTS_KEYWORD = "PRJ_TESTEXPORTS"; //$NON-NLS-1$
	private static final String PRJ_PLATFORMS_KEYWORD = "PRJ_PLATFORMS"; //$NON-NLS-1$
	private static final String PRJ_EXTENSIONS_KEYWORD = "PRJ_EXTENSIONS"; //$NON-NLS-1$
	private static final String PRJ_TESTEXTENSIONS_KEYWORD = "PRJ_TESTEXTENSIONS"; //$NON-NLS-1$
	private static final String START_KEYWORD = "START"; //$NON-NLS-1$

	public static IASTListNode<IASTBldInfStatement> createBldInfStatementListNode() {
		return createListNode(""); //$NON-NLS-1$
	}
	public static IASTListNode<IASTBldInfMakMakeStatement> createBldInfMakMakeStatementList() {
		return createListNode(""); //$NON-NLS-1$
	}
	public static IASTListNode<IASTBldInfExportStatement> createBldInfExportStatementList() {
		return createListNode(""); //$NON-NLS-1$
	}
	public static IASTListNode<IASTBldInfExtensionStatement> createBldInfExtensionStatementList() {
		return createListNode(""); //$NON-NLS-1$
	}
	public static IASTListNode<IASTBldInfExtensionBlockStatement> createBldInfExtensionBlockStatementList() {
		return createListNode(""); //$NON-NLS-1$
	}
	
	public static IASTBldInfBlockStatement createBldInfBlockStatement(String keyword) {
		if (PRJ_MMPFILES_KEYWORD.equalsIgnoreCase(keyword) || PRJ_TESTMMPFILES_KEYWORD.equalsIgnoreCase(keyword)) 
			return createBldInfPrjMmpfilesBlockStatement(keyword);
		else if (PRJ_EXPORTS_KEYWORD.equalsIgnoreCase(keyword) || PRJ_TESTEXPORTS_KEYWORD.equalsIgnoreCase(keyword)) 
			return createBldInfPrjExportsBlockStatement(keyword);
		else if (PRJ_PLATFORMS_KEYWORD.equalsIgnoreCase(keyword))
			return createBldInfPrjPlatformsBlockStatement(new String[0]);
		else if (PRJ_EXTENSIONS_KEYWORD.equalsIgnoreCase(keyword) || PRJ_TESTEXTENSIONS_KEYWORD.equalsIgnoreCase(keyword)) 
			return createBldInfPrjExtensionsBlockStatement(keyword);
		else
			throw new IllegalArgumentException();
	}
	public static IASTBldInfExportStatement createBldInfExportStatement(
			IASTLiteralTextNode srcPath,
			IASTLiteralTextNode targetPath, IASTLiteralTextNode zipModifier) {
		return new ASTBldInfExportStatement(srcPath, targetPath, zipModifier);
	}

	public static IASTBldInfExportStatement createBldInfExportStatement(
			String srcPath,
			String targetPath, String zipModifier) {
		return new ASTBldInfExportStatement(
				createPreprocessorLiteralTextNode(srcPath), 
				createPreprocessorLiteralTextNode(targetPath), 
				createPreprocessorLiteralTextNode(zipModifier));
	}

	public static IASTBldInfMakefileStatement createBldInfMakefileStatement(
			IASTLiteralTextNode type,
			IASTLiteralTextNode path,
			IASTListNode<IASTLiteralTextNode> attributes) {
		if (attributes == null)
			attributes = createLiteralTextNodeList();
		return new ASTBldInfMakefileStatement(type, path, attributes);
	}
			
	public static IASTBldInfMakefileStatement createBldInfMakefileStatement(
			String type,
			String path,
			List<String> attributes) {
		if (attributes == null)
			attributes = new ArrayList<String>();
		return new ASTBldInfMakefileStatement(
				createPreprocessorLiteralTextNode(type), 
				createPreprocessorLiteralTextNode(path), 
				createPreprocessorLiteralTextNodeList(attributes));
	}

	public static IASTBldInfMakefileStatement createBldInfMakefileStatement(
			String type,
			String path,
			String[] attributes) {
		if (attributes == null)
			attributes = new String[0];
		return new ASTBldInfMakefileStatement(
				createPreprocessorLiteralTextNode(type), 
				createPreprocessorLiteralTextNode(path), 
				createPreprocessorLiteralTextNodeList(attributes));
	}

	public static IASTBldInfMmpfileStatement createBldInfMmpfileStatement(
			IASTLiteralTextNode path,
			IASTListNode<IASTLiteralTextNode> attributes) {
		if (attributes == null)
			attributes = createLiteralTextNodeList();
		return new ASTBldInfMmpfileStatement(path, attributes);
	}
			
	public static IASTBldInfMmpfileStatement createBldInfMmpfileStatement(
			String path,
			List<String> attributes) {
		if (attributes == null)
			attributes = new ArrayList<String>();
		
		return new ASTBldInfMmpfileStatement(
				createPreprocessorLiteralTextNode(path), 
				createPreprocessorLiteralTextNodeList(attributes));
	}

	public static IASTBldInfMmpfileStatement createBldInfMmpfileStatement(
			String path,
			String[] attributes) {
		if (attributes == null)
			attributes = new String[0];
		return new ASTBldInfMmpfileStatement(
				createPreprocessorLiteralTextNode(path), 
				createPreprocessorLiteralTextNodeList(attributes));
	}
	
	public static IASTBldInfPrjExportsBlockStatement createBldInfPrjExportsBlockStatement(
			IASTLiteralTextNode keyword,
			IASTListNode<IASTBldInfExportStatement> exports) {
		if (exports == null)
			exports = createBldInfExportStatementList();
		return new ASTBldInfPrjExportsBlockStatement(keyword, exports);
	}

	public static IASTBldInfPrjExportsBlockStatement createBldInfPrjExportsBlockStatement(
			String keyword) {
		return new ASTBldInfPrjExportsBlockStatement(
				createPreprocessorLiteralTextNode(keyword), 
				createBldInfExportStatementList());
	}

	public static IASTBldInfPrjMmpfilesBlockStatement createBldInfPrjMmpfilesBlockStatement(
			IASTLiteralTextNode keyword,
			IASTListNode<IASTBldInfMakMakeStatement> stmts) {
		if (stmts == null)
			stmts = createBldInfMakMakeStatementList();
		return new ASTBldInfPrjMmpfilesBlockStatement(keyword, stmts);
	}
	
	public static IASTBldInfPrjMmpfilesBlockStatement createBldInfPrjMmpfilesBlockStatement(
			String keyword) {
		return new ASTBldInfPrjMmpfilesBlockStatement(createPreprocessorLiteralTextNode(keyword),
				createBldInfMakMakeStatementList());
	}

	public static IASTBldInfPrjPlatformsBlockStatement createBldInfPrjPlatformsBlockStatement(
			IASTLiteralTextNode keyword, IASTListNode<IASTLiteralTextNode> platforms) {
		if (platforms == null)
			platforms = createLiteralTextNodeList();
		return new ASTBldInfPrjPlatformsBlockStatement(
				keyword,
				platforms);
	}
	
	public static IASTBldInfPrjPlatformsBlockStatement createBldInfPrjPlatformsBlockStatement(
			List<String> platforms) {
		return new ASTBldInfPrjPlatformsBlockStatement(
				createPreprocessorLiteralTextNode(PRJ_PLATFORMS_KEYWORD),
				createPreprocessorLiteralTextNodeList(platforms));
	}
	
	public static IASTBldInfPrjPlatformsBlockStatement createBldInfPrjPlatformsBlockStatement(
			String[] platforms) {
		return new ASTBldInfPrjPlatformsBlockStatement(
				createPreprocessorLiteralTextNode(PRJ_PLATFORMS_KEYWORD),
				createPreprocessorLiteralTextNodeList(platforms));
	}
	public static IASTBldInfTranslationUnit createBldInfTranslationUnit() {
		return new ASTBldInfTranslationUnit(createTopLevelNodeListNode());
	}

	public static IASTBldInfProblemStatement createBldInfProblemStatement(IASTPreprocessorTokenStream tokenStream, IMessage message) {
		return new ASTBldInfProblemStatement(tokenStream, message);
	}

	public static IASTBldInfProblemStatement createBldInfProblemStatement(String string, IMessage message) {
		return createBldInfProblemStatement(createPreprocessorTokenStream(string), message);
	}

	public static IASTBldInfExtensionStatement createBldInfExtensionStatement(
			IASTLiteralTextNode keyword,
			IASTListNode<IASTLiteralTextNode> args) {
		return new ASTBldInfExtensionStatement(keyword, args);
	}

	public static IASTBldInfExtensionStatement createBldInfExtensionStatement(
			String keyword,
			String[] args) {
		return new ASTBldInfExtensionStatement(
				createPreprocessorLiteralTextNode(keyword), 
				createPreprocessorLiteralTextNodeList(args)); 
	}
	
	public static IASTBldInfExtensionBlockStatement createBldInfExtensionBlockStatement(
			IASTListNode<IASTLiteralTextNode> args,
			IASTListNode<IASTBldInfExtensionStatement> stmts) {
		if (stmts == null)
			stmts = createBldInfExtensionStatementList();
		return new ASTBldInfExtensionBlockStatement(
				createPreprocessorLiteralTextNode(START_KEYWORD), args, stmts);
	}

	public static IASTBldInfExtensionBlockStatement createBldInfExtensionBlockStatement(
			String[] args) {
		return new ASTBldInfExtensionBlockStatement(
				createPreprocessorLiteralTextNode(START_KEYWORD),
				createPreprocessorLiteralTextNodeList(args),
				createBldInfExtensionStatementList()); 
	}


	public static IASTBldInfPrjExtensionsBlockStatement createBldInfPrjExtensionsBlockStatement(
			IASTLiteralTextNode keyword,
			IASTListNode<IASTBldInfExtensionBlockStatement> extensions) {
		if (extensions == null)
			extensions = createBldInfExtensionBlockStatementList();
		return new ASTBldInfPrjExtensionsBlockStatement(keyword, extensions);
	}

	public static IASTBldInfPrjExtensionsBlockStatement createBldInfPrjExtensionsBlockStatement(
			String keyword) {
		return new ASTBldInfPrjExtensionsBlockStatement(
				createPreprocessorLiteralTextNode(keyword), 
				createBldInfExtensionBlockStatementList());
	}

	
}
