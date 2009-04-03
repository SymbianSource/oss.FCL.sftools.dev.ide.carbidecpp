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


public class ASTPreprocessorElifStatement extends ASTPreprocessorTokenStreamStatement
		implements IASTPreprocessorElifStatement {

	public ASTPreprocessorElifStatement(IASTPreprocessorTokenStream tokenStream) {
		super(tokenStream);
		dirty = false;
	}

	/*public ASTPreprocessorElifStatement(IASTPreprocessorExpression expression) {
		setFromExpression(expression);
	}*/
	
	public ASTPreprocessorElifStatement(ASTPreprocessorElifStatement statement) {
		super(statement);
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorElifStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		return true;
		/*
		ASTPreprocessorElifStatement node = (ASTPreprocessorElifStatement) obj;
		return node.expression.equalValue(expression);
		*/
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode(); // ^ expression.hashCode() ^ 1029383;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPreprocessorElifStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText("#elif "); //$NON-NLS-1$
		handler.emitNode(getTokenStream());
		handler.emitNewline();
	}

}
