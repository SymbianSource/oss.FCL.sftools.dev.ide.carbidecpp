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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.cpp.internal.api.utils.core.*;

public class ASTProblemNode extends ASTNode implements IASTProblemNode {

	private IASTPreprocessorTokenStream tokenStream;

	private IMessage message;

	public ASTProblemNode(IASTPreprocessorTokenStream tokenStream,
			IMessage message) {
		super();
		setTokenStream(tokenStream);
		setMessage(message);
		dirty = false;
	}
	
	
	/**
	 * @param node
	 */
	public ASTProblemNode(ASTProblemNode node) {
		super(node);
		if (node.getTokenStream() != null)
			setTokenStream((IASTPreprocessorTokenStream) node.getTokenStream().copy());
		setMessage(node.getMessage());
		dirty = false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTNode#toString()
	 */
	@Override
	public String toString() {
		return "Problem: " + message; //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTProblemNode))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTProblemNode node = (ASTProblemNode) obj;
		return /*node.message.equals(message) &&*/ 
			equalValue(tokenStream, node.tokenStream);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ /*hashCodeOr0(message) ^*/ 
			hashCodeOr0(tokenStream) ^ 999;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode#getTokenStream()
	 */
	public IASTPreprocessorTokenStream getTokenStream() {
		return tokenStream;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode#setTokenStream(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream)
	 */
	public void setTokenStream(IASTPreprocessorTokenStream tokenStream) {
		unparent(this.tokenStream);
		parent(tokenStream);
		this.tokenStream = tokenStream;
		fireChanged();
		dirty = true;
	}
	

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode#getMessage()
	 */
	public IMessage getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode#setMessage(com.nokia.cpp.internal.api.utils.core.*
	 */
	public void setMessage(IMessage message) {
		this.message = message;
		// NOT DIRTY
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		if (tokenStream != null)
			return new IASTNode[] { tokenStream };
		else
			return NO_CHILDREN;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTProblemNode(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		if (tokenStream != null)
			handler.emitNode(tokenStream);
	}

	@Override
	public boolean hasDirtySource() {
		// don't worry about problem nodes without source
		if (getSourceRegion() == null)
			return false;
		return super.hasDirtySource();
	}
	
	@Override
	public String getOriginalText() {
		// don't rewrite anything for problem nodes without source
		if (getSourceRegion() == null)
			return ""; //$NON-NLS-1$
		return super.getOriginalText();
	}
}
