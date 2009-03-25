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


public class ASTPreprocessorPragmaStatement extends ASTPreprocessorStatement
		implements IASTPreprocessorPragmaStatement {

	private IASTPreprocessorTokenStream pragma;

	/**
	 * @param fileName
	 * @param isUser
	 */
	public ASTPreprocessorPragmaStatement(IASTPreprocessorTokenStream name) {
		setPragma(name);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTPreprocessorPragmaStatement(ASTPreprocessorPragmaStatement statement) {
		super(statement);
		setPragma((IASTPreprocessorTokenStream) statement.getPragma().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorPragmaStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorPragmaStatement node = (ASTPreprocessorPragmaStatement) obj;
		return node.pragma.equalValue(pragma);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ pragma.hashCode() ^ 4;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { pragma };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPreprocessorPragmaStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText("#pragma"); //$NON-NLS-1$
		if (pragma.getTokens().size() > 0) {
			handler.emitSpace();
			handler.emitNode(pragma);
		}
		handler.emitNewline();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#getName()
	 */
	public IASTPreprocessorTokenStream getPragma() {
		return pragma;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#setName(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream)
	 */
	public void setPragma(IASTPreprocessorTokenStream pragma) {
		Check.checkArg(pragma);
		unparent(this.pragma);
		parent(pragma);
		this.pragma = pragma;
		fireChanged();
		dirty = true;
	}

}
