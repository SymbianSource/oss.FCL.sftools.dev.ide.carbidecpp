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
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ASTBldInfMakMakeStatement extends ASTBldInfStatement implements
		IASTBldInfMakMakeStatement {
	private IASTListNode<IASTLiteralTextNode> attributes;
	private IASTLiteralTextNode path;

	public ASTBldInfMakMakeStatement(IASTLiteralTextNode path,
			IASTListNode<IASTLiteralTextNode> attributes) {
		setPath(path);
		setAttributes(attributes);
		dirty = false;
	}
	
	public ASTBldInfMakMakeStatement(ASTBldInfMakMakeStatement other) {
		super(other);
		setPath((IASTLiteralTextNode) other.getPath().copy());
		setAttributes((IASTListNode<IASTLiteralTextNode>) other.getAttributes().copy());
		dirty = other.dirty;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBldInfMakMakeStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTBldInfMakMakeStatement node = (ASTBldInfMakMakeStatement) obj;
		return node.attributes.equalValue(attributes)
		&& node.path.equalValue(path);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ attributes.hashCode() 
			^ path.hashCode() ^ 0x13849903;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return null;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakMakeStatement#getAttributes()
	 */
	public IASTListNode<IASTLiteralTextNode> getAttributes() {
		return attributes;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakMakeStatement#getPath()
	 */
	public IASTLiteralTextNode getPath() {
		return path;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakMakeStatement#setAttributes(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode)
	 */
	public void setAttributes(IASTListNode<IASTLiteralTextNode> list) {
		Check.checkArg(list);
		unparent(this.attributes);
		parent(list);
		this.attributes = list;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfMakMakeStatement#setPath(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setPath(IASTLiteralTextNode path) {
		Check.checkArg(path);
		unparent(this.path);
		parent(path);
		this.path = path;
		fireChanged();
		dirty = true;
	}
}
