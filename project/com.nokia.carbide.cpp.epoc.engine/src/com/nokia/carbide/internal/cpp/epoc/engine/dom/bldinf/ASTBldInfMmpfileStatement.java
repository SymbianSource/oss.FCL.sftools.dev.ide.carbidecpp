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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMmpfileStatement;


public class ASTBldInfMmpfileStatement extends ASTBldInfMakMakeStatement
		implements IASTBldInfMmpfileStatement {

	/**
	 * @param path
	 * @param attributes
	 */
	public ASTBldInfMmpfileStatement(IASTLiteralTextNode path,
			IASTListNode<IASTLiteralTextNode> attributes) {
		super(path, attributes);
	}

	/**
	 * @param other
	 */
	public ASTBldInfMmpfileStatement(ASTBldInfMmpfileStatement other) {
		super(other);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBldInfMmpfileStatement))
			return false;
		return super.equalValue(obj);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTBldInfMmpfileStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { getAttributes(), getPath() };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(getPath());
		if (getAttributes().size() > 0) {
			handler.emitSpace();
			handler.emitNode(getAttributes());
		}
		handler.emitNewline();
	}

}
