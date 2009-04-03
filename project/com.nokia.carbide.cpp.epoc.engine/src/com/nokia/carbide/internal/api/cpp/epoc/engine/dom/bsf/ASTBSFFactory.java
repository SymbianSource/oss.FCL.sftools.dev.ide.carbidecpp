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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bsf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.bsf.*;
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ASTBSFFactory extends ASTFactory {

	public static IASTListNode<IASTBSFStatement> createBSFStatementListNode() {
		return createListNode(""); //$NON-NLS-1$
	}

	public static IASTBSFFlagStatement createBSFFlagStatement(String name) {
		return new ASTBSFFlagStatement(createPreprocessorLiteralTextNode(name));
	}
	
	public static IASTBSFFlagStatement createBSFFlagStatement(IASTLiteralTextNode name) {
		return new ASTBSFFlagStatement(name);
	}
	
	public static IASTBSFTranslationUnit createBSFTranslationUnit(
			IASTListNode<IASTBSFStatement> nodes) {
		if (nodes == null)
			nodes = createTopLevelNodeListNode();
		return new ASTBSFTranslationUnit(nodes);
	}

	public static IASTBSFTranslationUnit createBSFTranslationUnit() {
		return createBSFTranslationUnit(null);
	}

	public static IASTBSFProblemStatement createBSFProblemStatement(IASTPreprocessorTokenStream tokenStream, IMessage message) {
		return new ASTBSFProblemStatement(tokenStream, message);
	}

	public static IASTBSFProblemStatement createBSFProblemStatement(String string, IMessage message) {
		return createBSFProblemStatement(createPreprocessorTokenStream(string), message);
	}

	public static IASTBSFArgumentStatement createBSFArgumentStatement(
			String keyword, String arguments) {
		return createBSFArgumentStatement(createPreprocessorLiteralTextNode(keyword),
				createPreprocessorLiteralTextNode(arguments));
	}

	public static IASTBSFArgumentStatement createBSFArgumentStatement(
			IASTLiteralTextNode keyword,
			IASTLiteralTextNode arguments) {
		return new ASTBSFArgumentStatement(keyword, arguments);
	}

	public static IASTBSFStatement createBSFCommentStatement(String line) {
		if (line == null)
			line = ""; //$NON-NLS-1$
		return new ASTBSFCommentStatement(createPreprocessorTokenStream(line));
	}

}
