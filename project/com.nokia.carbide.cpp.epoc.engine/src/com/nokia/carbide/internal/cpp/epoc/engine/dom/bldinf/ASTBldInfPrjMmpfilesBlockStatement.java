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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakMakeStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfPrjMmpfilesBlockStatement;


public class ASTBldInfPrjMmpfilesBlockStatement
		extends ASTBldInfBlockStatement<IASTBldInfMakMakeStatement>
		implements IASTBldInfPrjMmpfilesBlockStatement {

	/**
	 * 
	 */
	public ASTBldInfPrjMmpfilesBlockStatement(
			IASTLiteralTextNode keyword,
			IASTListNode<IASTBldInfMakMakeStatement> stmts) {
		super(keyword, stmts);
	}

	/**
	 * @param other
	 */
	public ASTBldInfPrjMmpfilesBlockStatement(ASTBldInfPrjMmpfilesBlockStatement other) {
		super(other);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBldInfPrjMmpfilesBlockStatement))
			return false;
		return super.equalValue(obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ -2535393;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTBldInfPrjMmpfilesBlockStatement(this);
	}
}
