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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.bldinf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfProblemStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTProblemNode;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTBldInfProblemStatement extends ASTProblemNode implements
		IASTBldInfProblemStatement {

	private IASTTopLevelNode next;

	/**
	 * @param tokenStream
	 * @param message
	 */
	public ASTBldInfProblemStatement(IASTPreprocessorTokenStream tokenStream, IMessage message) {
		super(tokenStream, message);
	}

	/**
	 * @param statement
	 */
	public ASTBldInfProblemStatement(ASTBldInfProblemStatement statement) {
		super(statement);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTNode#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ASTBldInfProblemStatement))
			return false;
		return super.equals(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTProblemNode#copy()
	 */
	@Override
	public IASTNode copy() {
		return new ASTBldInfProblemStatement(this);
	}
	
	public IASTTopLevelNode getNext() {
		return next;
	}
	
	public void setNext(IASTTopLevelNode node) {
		this.next = node;
	}

}
