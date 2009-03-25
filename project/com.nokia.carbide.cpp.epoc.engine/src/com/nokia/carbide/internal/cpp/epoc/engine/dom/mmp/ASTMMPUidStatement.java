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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPUidStatement;


public class ASTMMPUidStatement extends ASTMMPStatement implements
		IASTMMPUidStatement {

	private IASTLiteralTextNode uid2;
	private IASTLiteralTextNode uid3;

	/**
	 */
	public ASTMMPUidStatement(IASTLiteralTextNode uid2, IASTLiteralTextNode uid3) {
		super();
		setUid2(uid2);
		setUid3(uid3);
	}

	/**
	 * @param statement
	 */
	public ASTMMPUidStatement(ASTMMPUidStatement statement) {
		super(statement);
		if (statement.getUid2() != null)
			setUid2((IASTLiteralTextNode) statement.getUid2().copy());
		else
			setUid2(null);
		if (statement.getUid3() != null)
			setUid3((IASTLiteralTextNode) statement.getUid3().copy());
		else
			setUid3(null);
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPUidStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPUidStatement node = (ASTMMPUidStatement) obj;
		return equalValue(node.uid2, uid2)
		 && equalValue(node.uid3, uid3);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ hashCodeOr0(uid2) ^ hashCodeOr0(uid3) ^ 0x39ce;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPUidStatement#getUid2()
	 */
	public IASTLiteralTextNode getUid2() {
		return uid2;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPUidStatement#setUid2(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression)
	 */
	public void setUid2(IASTLiteralTextNode uid2) {
		unparent(uid2);
		parent(uid2);
		this.uid2 = uid2;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPUidStatement#getUid3()
	 */
	public IASTLiteralTextNode getUid3() {
		return uid3;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPUidStatement#setUid3(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression)
	 */
	public void setUid3(IASTLiteralTextNode uid3) {
		unparent(uid3);
		parent(uid3);
		this.uid3 = uid3;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTMMPUidStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText(getKeywordName());
		if (uid2 != null) {
			handler.emitSpace();
			handler.emitNode(uid2);
		}
		if (uid3 != null) {
			handler.emitSpace();
			handler.emitNode(uid3);
		}
		handler.emitNewline();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		if (uid2 != null)
			if (uid3 != null)
				return new IASTNode[] { uid2, uid3 };
			else
				return new IASTNode[] { uid2 };
		else if (uid3 != null)
			return new IASTNode[] { uid3 };
		else
			return NO_CHILDREN;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return UID_KEYWORD;
	}
}
