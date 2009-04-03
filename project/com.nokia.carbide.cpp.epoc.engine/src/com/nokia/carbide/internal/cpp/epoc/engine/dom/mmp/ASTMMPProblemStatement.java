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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPProblemStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IMMPSourcePathDependentContext;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTProblemNode;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTMMPProblemStatement extends ASTProblemNode implements
		IASTMMPProblemStatement {

	private IASTTopLevelNode next;

	public ASTMMPProblemStatement(IASTPreprocessorTokenStream tokenStream,
			IMessage message) {
		super(tokenStream, message);
		dirty = false;
	}
	
	/**
	 * @param statement
	 */
	public ASTMMPProblemStatement(ASTMMPProblemStatement statement) {
		super(statement);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTProblemNode#copy()
	 */
	@Override
	public IASTNode copy() {
		return new ASTMMPProblemStatement(this);
	}
	
	public IASTTopLevelNode getNext() {
		return next;
	}
	
	public void setNext(IASTTopLevelNode node) {
		this.next = node;
	}
	
	public IMMPSourcePathDependentContext getSourcePathDependentContext() {
		return null;
	}
}
