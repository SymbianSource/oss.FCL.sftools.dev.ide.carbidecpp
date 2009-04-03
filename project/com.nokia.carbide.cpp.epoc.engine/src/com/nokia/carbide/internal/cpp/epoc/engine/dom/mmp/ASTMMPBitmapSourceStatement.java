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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPBitmapSourceStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTMMPBitmapSourceStatement extends ASTMMPListArgumentStatementBase implements
		IASTMMPBitmapSourceStatement {

	private IASTLiteralTextNode format;

	/**
	 * @param keyword
	 */
	public ASTMMPBitmapSourceStatement(IASTLiteralTextNode format, IASTListNode<IASTLiteralTextNode> arguments) {
		super(arguments);
		setFormat(format);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTMMPBitmapSourceStatement(ASTMMPBitmapSourceStatement statement) {
		super(statement);
		setFormat((IASTLiteralTextNode) statement.getFormat().copy());
		dirty = statement.dirty;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPBitmapSourceStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPBitmapSourceStatement node = (ASTMMPBitmapSourceStatement) obj;
		return node.format.equalValue(format);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ format.hashCode() ^ 0x8172615;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPBitmapSourceStatement#getFormat()
	 */
	public IASTLiteralTextNode getFormat() {
		return format;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPBitmapSourceStatement#setFormat(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression)
	 */
	public void setFormat(IASTLiteralTextNode format) {
		Check.checkArg(format);
		unparent(this.format);
		parent(format);
		this.format = format;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTMMPBitmapSourceStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText(getKeywordName());
		handler.emitSpace();
		handler.emitNode(getFormat());
		//rewriteArguments(handler);
		handler.emitSpace();
		handler.emitNode(getArguments());
		handler.emitNewline();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return makeChildListWith(format);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return SOURCE_KEYWORD;
	}
}
