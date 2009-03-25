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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;


public abstract class ASTStatement extends ASTNode implements IASTStatement {

	private IASTTopLevelNode next;

	public ASTStatement() {
		
	}
	public ASTStatement(ASTStatement other) {
		super(other);
	}
	
	public String getKeywordName() {
		return null;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode(); // ^ hashCodeOr0(getDocument()) ^ hashCodeOr0(getLocation()) ^ hashCodeOr0(getRegion());
		//return super.hashCode() ^ hashCodeOr0(getDocument()) ^ hashCodeOr0(getLocation()) ^ hashCodeOr0(getRegion());
	}
	
	public void setNext(IASTTopLevelNode node) {
		this.next = node;
	}

	public IASTTopLevelNode getNext() {
		return next;
	}
}
