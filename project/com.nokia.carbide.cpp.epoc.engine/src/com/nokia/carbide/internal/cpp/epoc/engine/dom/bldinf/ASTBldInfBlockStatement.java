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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfBlockStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ASTBldInfBlockStatement<ElementType extends IASTNode> 
	extends ASTBldInfStatement implements IASTBldInfBlockStatement<ElementType>,
		IASTListHolder<ElementType> {
	
	private IASTListNode<ElementType> list;
	private IASTLiteralTextNode keyword;

	/**
	 * 
	 */
	public ASTBldInfBlockStatement(IASTLiteralTextNode keyword,
			IASTListNode<ElementType> stmts) {
		setKeyword(keyword);
		setList(stmts);
		dirty = false;
	}

	/**
	 * @param other
	 */
	public ASTBldInfBlockStatement(ASTBldInfBlockStatement<ElementType> other) {
		super(other);
		setKeyword((IASTLiteralTextNode) other.getKeyword().copy());
		setList((IASTListNode<ElementType>) other.getList().copy());
		dirty = other.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBldInfBlockStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTBldInfBlockStatement node = (ASTBldInfBlockStatement) obj;
		return node.list.equalValue(list)
		&& node.keyword.equalValue(keyword);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ list.hashCode() ^ keyword.hashCode() ^ -293;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return keyword.getValue();
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfPrjExportsBlockStatement#getExports()
	 */
	public IASTListNode<ElementType> getList() {
		return list;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfPrjExportsBlockStatement#setExports(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode)
	 */
	public void setList(IASTListNode<ElementType> list) {
		Check.checkArg(list);
		unparent(this.list);
		parent(list);
		this.list = list;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfPrjExportsBlockStatement#getKeyword()
	 */
	public IASTLiteralTextNode getKeyword() {
		return keyword;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfPrjExportsBlockStatement#setKeyword(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
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
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { list, keyword };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(keyword);
		handler.emitNewline();
		handler.emitNode(list);
	}

}
