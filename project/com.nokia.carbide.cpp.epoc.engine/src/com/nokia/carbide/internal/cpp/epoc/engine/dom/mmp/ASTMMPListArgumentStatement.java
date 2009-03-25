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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPListArgumentStatement;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.regex.Pattern;


public class ASTMMPListArgumentStatement extends ASTMMPListArgumentStatementBase implements
		IASTMMPListArgumentStatement {

	private IASTLiteralTextNode keyword;

	private static Pattern SOURCEPATH_STATEMENT_PATTERN = Pattern.compile("(?i)SOURCE|RESOURCE|SYSTEMRESOURCE|DOCUMENT"); //$NON-NLS-1$ 
	/**
	 * @param arguments
	 */
	public ASTMMPListArgumentStatement(IASTLiteralTextNode keyword, IASTListNode<IASTLiteralTextNode> arguments) {
		super(arguments);
		setKeyword(keyword);
		dirty = false;
		if (SOURCEPATH_STATEMENT_PATTERN.matcher(keyword.getValue()).matches()) {
			establishSourcePathDependence();
		}
	}

	/**
	 * @param statement
	 */
	public ASTMMPListArgumentStatement(ASTMMPListArgumentStatement statement) {
		super(statement);
		setKeyword((IASTLiteralTextNode) statement.getKeyword().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPListArgumentStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPListArgumentStatement node = (ASTMMPListArgumentStatement) obj;
		return node.keyword.equalValue(keyword);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ keyword.hashCode() ^ 0x3397161;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPKeywordStatement#getKeyword()
	 */
	public IASTLiteralTextNode getKeyword() {
		return keyword;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPKeywordStatement#setKeyword(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setKeyword(IASTLiteralTextNode keyword) {
		Check.checkArg(keyword);
		unparent(this.keyword);
		parent(keyword);
		this.keyword = keyword;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return keyword.getValue();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return makeChildListWith(keyword);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTMMPListArgumentStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(keyword);
		handler.emitSpace();
		handler.emitNode(getArguments());
		handler.emitNewline();
	}

}
