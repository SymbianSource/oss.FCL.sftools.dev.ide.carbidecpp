/*
* Copyright (c) 2008-2009 Nokia Corporation and/or its subsidiary(-ies).
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


public abstract class ASTPreprocessorTokenStreamProviderStatement extends
		ASTPreprocessorStatement {

	protected IASTPreprocessorTokenStream tokenStream;

	/**
	 * 
	 */
	public ASTPreprocessorTokenStreamProviderStatement() {
		super();
	}

	/**
	 * @param other
	 */
	public ASTPreprocessorTokenStreamProviderStatement(
			ASTPreprocessorTokenStreamProviderStatement other) {
		super(other);
		setTokenStream((IASTPreprocessorTokenStream) other.getTokenStream().copy());
		dirty = other.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorTokenStreamProviderStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		return tokenStream.equalValue(((ASTPreprocessorTokenStreamProviderStatement)obj).tokenStream);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ tokenStream.hashCode() ^ 876;
	}

	public IASTPreprocessorTokenStream getTokenStream() {
		return tokenStream;
	}

	public void setTokenStream(IASTPreprocessorTokenStream tokenStream) {
		Check.checkArg(tokenStream);
		unparent(this.tokenStream);
		parent(tokenStream);
		this.tokenStream = tokenStream;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(tokenStream);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { tokenStream };
	}

}