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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPKeywordStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ASTMMPKeywordStatement extends ASTMMPStatement implements
		IASTMMPKeywordStatement {

	private IASTLiteralTextNode keyword;

	/**
	 * @param keyword
	 * @param arguments
	 */
	public ASTMMPKeywordStatement(IASTLiteralTextNode keyword) {
		super();
		setKeyword(keyword);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTMMPKeywordStatement(ASTMMPKeywordStatement statement) {
		super(statement);
		setKeyword((IASTLiteralTextNode) statement.getKeyword().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPFlagStatement#setKeyword(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setKeyword(IASTLiteralTextNode keyword) {
		Check.checkArg(keyword);
		unparent(this.keyword);
		parent(keyword);
		this.keyword = keyword;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPFlagStatement#getKeyword()
	 */
	public IASTLiteralTextNode getKeyword() {
		return keyword;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPKeywordStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPKeywordStatement node = (ASTMMPKeywordStatement) obj;
		return node.keyword.equalValue(keyword);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ keyword.hashCode() ^ -203982;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { keyword };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ASTMMPStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return keyword.getValue();
	}
}
