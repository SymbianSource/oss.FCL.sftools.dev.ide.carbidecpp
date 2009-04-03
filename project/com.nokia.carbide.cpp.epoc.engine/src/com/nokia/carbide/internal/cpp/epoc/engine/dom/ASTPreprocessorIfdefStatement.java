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


public class ASTPreprocessorIfdefStatement extends ASTPreprocessorStatement
		implements IASTPreprocessorIfdefStatement {

	private IASTLiteralTextNode macroName;

	public ASTPreprocessorIfdefStatement(IASTLiteralTextNode macroName) {
		setMacroName(macroName);
		dirty = false;
	}
	
	public ASTPreprocessorIfdefStatement(ASTPreprocessorIfdefStatement statement) {
		super(statement);
		setMacroName((IASTLiteralTextNode) statement.getMacroName().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPreprocessorIfdefStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTPreprocessorIfdefStatement node = (ASTPreprocessorIfdefStatement) obj;
		return node.macroName.equalValue(macroName);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ macroName.hashCode() ^ 38134;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIfdefStatement#getMacroName()
	 */
	public IASTLiteralTextNode getMacroName() {
		return macroName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIfdefStatement#setMacroName(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression)
	 */
	public void setMacroName(IASTLiteralTextNode expr) {
		Check.checkArg(expr);
		if (expr == macroName)
			return;
		unparent(macroName);
		parent(expr);
		this.macroName = expr;
		fireChanged();
		dirty = false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { macroName };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTPreprocessorIfdefStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText("#ifdef "); //$NON-NLS-1$
		handler.emitNode(macroName);
		handler.emitNewline();
	}

}
