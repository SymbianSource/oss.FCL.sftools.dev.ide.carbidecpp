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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakefileStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTBldInfMakefileStatement extends ASTBldInfMakMakeStatement
		implements IASTBldInfMakefileStatement {

	private IASTLiteralTextNode type;

	/**
	 * @param path
	 * @param attributes
	 */
	public ASTBldInfMakefileStatement(IASTLiteralTextNode type,
			IASTLiteralTextNode path,
			IASTListNode<IASTLiteralTextNode> attributes) {
		super(path, attributes);
		setType(type);
		dirty = false;
	}

	/**
	 * @param other
	 */
	public ASTBldInfMakefileStatement(ASTBldInfMakefileStatement other) {
		super(other);
		setType((IASTLiteralTextNode) other.getType().copy());
		dirty = other.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBldInfMakefileStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTBldInfMakefileStatement node = (ASTBldInfMakefileStatement) obj;
		return node.type.equalValue(type);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ type.hashCode() ^ 0x39372;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakefileStatement#getType()
	 */
	public IASTLiteralTextNode getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakefileStatement#setType(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setType(IASTLiteralTextNode type) {
		Check.checkArg(type);
		unparent(this.type);
		parent(type);
		this.type = type;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTBldInfMakefileStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { getAttributes(), getPath(), getType() };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(type);
		handler.emitSpace();
		handler.emitNode(getPath());
		if (getAttributes().size() > 0) {
			handler.emitSpace();
			handler.emitNode(getAttributes());
		}
		handler.emitNewline();
	}

}
