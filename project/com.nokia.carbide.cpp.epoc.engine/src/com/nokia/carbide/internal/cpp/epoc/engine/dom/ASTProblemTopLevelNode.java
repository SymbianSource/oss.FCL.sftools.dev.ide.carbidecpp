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

package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTProblemTopLevelNode extends ASTProblemNode implements
		IASTProblemTopLevelNode {

	private IASTTopLevelNode next;

	public ASTProblemTopLevelNode(IASTPreprocessorTokenStream tokenStream, IMessage message) {
		super(tokenStream, message);
	}

	/**
	 * @param node
	 */
	public ASTProblemTopLevelNode(ASTProblemTopLevelNode node) {
		super(node);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTNode#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ASTProblemTopLevelNode))
			return false;
		return super.equals(obj);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTProblemNode#copy()
	 */
	@Override
	public IASTNode copy() {
		return new ASTProblemTopLevelNode(this);
	}
	
	public IASTTopLevelNode getNext() {
		return next;
	}
	
	public void setNext(IASTTopLevelNode node) {
		this.next = node;
	}
}
