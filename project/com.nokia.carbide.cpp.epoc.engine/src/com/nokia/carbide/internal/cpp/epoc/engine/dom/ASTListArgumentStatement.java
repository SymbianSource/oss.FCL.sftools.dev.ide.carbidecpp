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


public abstract class ASTListArgumentStatement extends ASTStatement implements
		IASTListArgumentStatement {

	private IASTListNode<IASTLiteralTextNode> arguments;
	
	/**
	 * @param arguments
	 */
	public ASTListArgumentStatement(IASTListNode<IASTLiteralTextNode> arguments) {
		super();
		setArguments(arguments);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTListArgumentStatement(ASTListArgumentStatement statement) {
		super(statement);
		setArguments((IASTListNode<IASTLiteralTextNode>) statement.getArguments().copy());
		dirty = statement.dirty;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTListArgumentStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTListArgumentStatement node = (ASTListArgumentStatement) obj;
		return node.arguments.equalValue(arguments);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ arguments.hashCode() ^ 0x91717239;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder#getList()
	 */
	public IASTListNode<IASTLiteralTextNode> getList() {
		return arguments;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPListArgumentStatement#getArguments()
	 */
	public IASTListNode<IASTLiteralTextNode> getArguments() {
		return arguments;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPListArgumentStatement#setArguments(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode)
	 */
	public void setArguments(IASTListNode<IASTLiteralTextNode> arguments) {
		Check.checkArg(arguments);
		unparent(this.arguments);
		parent(arguments);
		this.arguments = arguments;
		fireChanged();
		dirty = true;
	}

	/**
	 * Rewrite the arguments, each with a leading space.
	 */
	/*
	protected void rewriteArguments(IRewriteHandler handler) {
		for (IASTLiteralTextNode node : arguments) {
			handler.emitSpace();
			handler.emitNode(node);
		}
	}*/
	
	protected IASTNode[] makeChildList() {
		return new IASTNode[] { arguments };
	}

	protected IASTNode[] makeChildListWith(IASTNode other) {
		if (other != null) {
			IASTNode[] kids = new IASTNode[2];
			kids[0] = arguments;
			kids[1] = other;
			return kids;
		} else {
			return new IASTNode[] { arguments };
		}
	}
	
	protected IASTNode[] makeChildListWith(IASTNode other, IASTNode other2) {
		IASTNode[] kids = new IASTNode[3];
		int idx = 0; 
		kids[idx++] = arguments;
		if (other != null)
			kids[idx++] = other;
		if (other2 != null)
			kids[idx++] = other2;
		if (idx == 3)
			return kids;
		
		IASTNode[] smallerKids = new IASTNode[idx];
		System.arraycopy(kids, 0, smallerKids, 0, idx);
		return smallerKids;
	}

	
	protected IASTNode[] makeChildListWith(IASTNode other, IASTNode other2, IASTNode other3) {
		IASTNode[] kids = new IASTNode[4];
		int idx = 0; 
		kids[idx++] = arguments;
		if (other != null)
			kids[idx++] = other;
		if (other2 != null)
			kids[idx++] = other2;
		if (other3 != null)
			kids[idx++] = other3;
		if (idx == 4)
			return kids;
		
		IASTNode[] smallerKids = new IASTNode[idx];
		System.arraycopy(kids, 0, smallerKids, 0, idx);
		return smallerKids;
	}

	protected IASTNode[] makeChildListWith(IASTNode other, IASTNode other2, IASTNode other3, IASTNode other4) {
		IASTNode[] kids = new IASTNode[5];
		int idx = 0; 
		kids[idx++] = arguments;
		if (other != null)
			kids[idx++] = other;
		if (other2 != null)
			kids[idx++] = other2;
		if (other3 != null)
			kids[idx++] = other3;
		if (other4 != null)
			kids[idx++] = other4;
		if (idx == 5)
			return kids;
		
		IASTNode[] smallerKids = new IASTNode[idx];
		System.arraycopy(kids, 0, smallerKids, 0, idx);
		return smallerKids;
	}
}
