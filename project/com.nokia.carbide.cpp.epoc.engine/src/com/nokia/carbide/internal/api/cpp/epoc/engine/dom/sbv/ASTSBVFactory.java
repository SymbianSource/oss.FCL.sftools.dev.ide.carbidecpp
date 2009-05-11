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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.sbv;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.sbv.*;
import com.nokia.cpp.internal.api.utils.core.IMessage;


public abstract class ASTSBVFactory extends ASTFactory {

	public static IASTListNode<IASTSBVStatement> createSBVStatementListNode() {
		return createListNode(""); //$NON-NLS-1$
	}

	public static IASTSBVFlagStatement createSBVFlagStatement(String name) {
		return new ASTSBVFlagStatement(createPreprocessorLiteralTextNode(name));
	}
	
	public static IASTSBVFlagStatement createSBVFlagStatement(IASTLiteralTextNode name) {
		return new ASTSBVFlagStatement(name);
	}
	
	public static IASTSBVTranslationUnit createSBVTranslationUnit(
			IASTListNode<IASTSBVStatement> nodes) {
		if (nodes == null)
			nodes = createTopLevelNodeListNode();
		return new ASTSBVTranslationUnit(nodes);
	}

	public static IASTSBVTranslationUnit createSBVTranslationUnit() {
		return createSBVTranslationUnit(null);
	}

	public static IASTSBVProblemStatement createSBVProblemStatement(IASTPreprocessorTokenStream tokenStream, IMessage message) {
		return new ASTSBVProblemStatement(tokenStream, message);
	}

	public static IASTSBVProblemStatement createSBVProblemStatement(String string, IMessage message) {
		return createSBVProblemStatement(createPreprocessorTokenStream(string), message);
	}

	public static IASTSBVArgumentStatement createSBVArgumentStatement(
			String keyword, String arguments) {
		return createSBVArgumentStatement(createPreprocessorLiteralTextNode(keyword),
				createPreprocessorLiteralTextNode(arguments));
	}

	public static IASTSBVArgumentStatement createSBVArgumentStatement(
			IASTLiteralTextNode keyword,
			IASTLiteralTextNode arguments) {
		return new ASTSBVArgumentStatement(keyword, arguments);
	}

	public static IASTSBVStatement createSBVCommentStatement(String line) {
		if (line == null)
			line = ""; //$NON-NLS-1$
		return new ASTSBVCommentStatement(createPreprocessorTokenStream(line));
	}

}
