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


public class ASTPreprocessorWarningStatement extends ASTPreprocessorStatement
		implements IASTPreprocessorWarningStatement {

	private IASTPreprocessorTokenStream warning;

	/**
	 * @param fileName
	 * @param isUser
	 */
	public ASTPreprocessorWarningStatement(IASTPreprocessorTokenStream name) {
		setWarning(name);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTPreprocessorWarningStatement(ASTPreprocessorWarningStatement statement) {
		super(statement);
		setWarning((IASTPreprocessorTokenStream) statement.getWarning().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorWarningStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorWarningStatement node = (ASTPreprocessorWarningStatement) obj;
		return node.warning.equalValue(warning);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ warning.hashCode() ^ 454;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { warning };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPreprocessorWarningStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText("#warning"); //$NON-NLS-1$
		if (warning.getTokens().size() > 0) {
			handler.emitSpace();
			handler.emitNode(warning);
		}
		handler.emitNewline();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#getName()
	 */
	public IASTPreprocessorTokenStream getWarning() {
		return warning;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement#setName(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream)
	 */
	public void setWarning(IASTPreprocessorTokenStream warning) {
		Check.checkArg(warning);
		unparent(this.warning);
		parent(warning);
		this.warning = warning;
		fireChanged();
		dirty = true;
	}

}
