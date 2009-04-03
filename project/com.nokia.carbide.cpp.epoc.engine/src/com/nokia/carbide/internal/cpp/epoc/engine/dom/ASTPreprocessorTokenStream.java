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

import java.util.*;


public class ASTPreprocessorTokenStream extends ASTNode
		implements IASTPreprocessorTokenStream {

	private List<IToken> tokens;

	public ASTPreprocessorTokenStream(List<IToken> tokens) {
		setTokens(tokens);
		dirty = false;
	}
	
	/**
	 * @param node
	 */
	public ASTPreprocessorTokenStream(ASTPreprocessorTokenStream node) {
		super(node);
		setTokens(new ArrayList<IToken>(node.getTokens()));
		dirty = node.dirty;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorTokenStream))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorTokenStream node = (ASTPreprocessorTokenStream) obj;
		
		//return node.tokens.equalValue(tokens);
		
		// loose comparison of tokens
		if (node.tokens.size() != tokens.size())
			return false;
		
		Iterator<IToken> nodeIter = node.tokens.iterator();
		Iterator<IToken> iter = tokens.iterator();
		while (nodeIter.hasNext() && iter.hasNext()) {
			IToken nT = nodeIter.next();
			IToken t = iter.next();
			if (nT.followsSpace() != t.followsSpace()
					|| !nT.getText().equals(t.getText()))
				return false;
		}
		return (nodeIter.hasNext() == iter.hasNext());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int code = super.hashCode();
		
		Iterator<IToken> iter = tokens.iterator();
		while (iter.hasNext()) {
			IToken token = iter.next();
			int tokenCode = (token.followsSpace() ? 1 : 0) ^ token.getText().hashCode();
			code = (code ^ tokenCode) + 190283;
		}
		return code;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStreamNode#getTokens()
	 */
	public List<IToken> getTokens() {
		return tokens;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStreamNode#setTokens(java.util.List)
	 */
	public void setTokens(List<IToken> tokens) {
		Check.checkArg(tokens);
		this.tokens = tokens;
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return NO_CHILDREN;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPreprocessorTokenStream(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		for (IToken token : tokens) {
			//if (token.followsNewline())
			//	handler.emitNewline();
			if (token.followsSpace())
				handler.emitSpace();
			handler.emitText(token.getText());
		}
	}

}
