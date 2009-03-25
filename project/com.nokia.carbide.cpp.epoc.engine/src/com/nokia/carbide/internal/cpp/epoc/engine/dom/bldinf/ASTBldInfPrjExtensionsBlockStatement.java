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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.*;


public class ASTBldInfPrjExtensionsBlockStatement 
		extends ASTBldInfBlockStatement<IASTBldInfExtensionBlockStatement>
		implements IASTBldInfPrjExtensionsBlockStatement {
	
	/**
	 * 
	 */
	public ASTBldInfPrjExtensionsBlockStatement(IASTLiteralTextNode keyword,
			IASTListNode<IASTBldInfExtensionBlockStatement> stmts) {
		super(keyword, stmts);
	}

	/**
	 * @param other
	 */
	public ASTBldInfPrjExtensionsBlockStatement(ASTBldInfPrjExtensionsBlockStatement other) {
		super(other);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBldInfPrjExtensionsBlockStatement))
			return false;
		return super.equalValue(obj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ 23938;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTBldInfPrjExtensionsBlockStatement(this);
	}
}
