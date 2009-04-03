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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExtensionStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTBldInfExtensionStatement extends ASTBldInfStatement implements
		IASTBldInfExtensionStatement {

	private IASTLiteralTextNode keyword;
	private IASTListNode<IASTLiteralTextNode> args;

	public ASTBldInfExtensionStatement(IASTLiteralTextNode keyword, 
			IASTListNode<IASTLiteralTextNode> args) {
		setKeyword(keyword);
		setArguments(args);
		dirty = false;
	}

	/**
	 * @param other
	 */
	public ASTBldInfExtensionStatement(ASTBldInfExtensionStatement other) {
		super(other);
		setKeyword((IASTLiteralTextNode) other.getKeyword().copy());
		if (other.getArguments() != null)
			setArguments((IASTListNode<IASTLiteralTextNode>) other.getArguments().copy());
		dirty = other.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBldInfExtensionStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTBldInfExtensionStatement node = (ASTBldInfExtensionStatement) obj;
		return equalValue(node.keyword, keyword)
		&& equalValue(node.args, args);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ hashCodeOr0(keyword)
		^ hashCodeOr0(args) ^ 0x3847298;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return keyword.getValue();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExtensionStatement#getKeyword()
	 */
	public IASTLiteralTextNode getKeyword() {
		return keyword;
	}
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExtensionStatement#getArguments()
	 */
	public IASTListNode<IASTLiteralTextNode> getArguments() {
		return args;
	}

	public void setKeyword(IASTLiteralTextNode keyword) {
		Check.checkArg(keyword);
		unparent(this.keyword);
		parent(keyword);
		this.keyword = keyword;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement#setTargetPath(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setArguments(IASTListNode<IASTLiteralTextNode> args) {
		unparent(this.args);
		parent(args);
		this.args = args;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTBldInfExtensionStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		if (args != null)
			return new IASTNode[] { keyword, args };
		else
			return new IASTNode[] { keyword };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(keyword);
		if (args != null) {
			handler.emitSpace();
			handler.emitNode(args);
		}
		handler.emitNewline();
	}

}
