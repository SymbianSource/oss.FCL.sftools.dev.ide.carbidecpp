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


public class ASTPreprocessorIncludeStatement extends ASTPreprocessorStatement
		implements IASTPreprocessorIncludeStatement {

	private IASTPreprocessorTokenStream name;

	/**
	 * @param fileName
	 * @param isUser
	 */
	public ASTPreprocessorIncludeStatement(IASTPreprocessorTokenStream name) {
		setName(name);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTPreprocessorIncludeStatement(ASTPreprocessorIncludeStatement statement) {
		super(statement);
		setName((IASTPreprocessorTokenStream) statement.getName().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorIncludeStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorIncludeStatement node = (ASTPreprocessorIncludeStatement) obj;
		return node.name.equalValue(name);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ name.hashCode() ^ 4;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { name };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPreprocessorIncludeStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText("#include"); //$NON-NLS-1$
		handler.emitSpace();
		handler.emitNode(name);
		handler.emitNewline();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#getName()
	 */
	public IASTPreprocessorTokenStream getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#setName(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream)
	 */
	public void setName(IASTPreprocessorTokenStream name) {
		Check.checkArg(name);
		unparent(this.name);
		parent(name);
		this.name = name;
		fireChanged();
		dirty = true;
	}

}
