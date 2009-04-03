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
package com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGProblemStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTProblemNode;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTProblemTopLevelNode;
import com.nokia.cpp.internal.api.utils.core.*;

public class ASTPKGProblemStatement extends ASTProblemNode implements
		IASTPKGProblemStatement {

	private IASTTopLevelNode next;

	public ASTPKGProblemStatement(IASTPreprocessorTokenStream tokenStream,
			IMessage message) {
		super(tokenStream, message);
	}

	public ASTPKGProblemStatement(ASTProblemTopLevelNode node) {
		super(node);
	}

	public String getKeywordName() {
		return null;
	}

	public IASTTopLevelNode getNext() {
		return next;
	}

	public void setNext(IASTTopLevelNode node) {
		next = node;
	}

}
