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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStartBlockStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTMMPStartBlockStatement extends ASTMMPBlockStatement implements
		IASTMMPStartBlockStatement {

	private static final String START_TOKEN = "START"; //$NON-NLS-1$
	
	private IASTLiteralTextNode type;
	private IASTListNode<IASTLiteralTextNode> arguments;

	/**
	 * @param blockType
	 * @param statements
	 */
	public ASTMMPStartBlockStatement(IASTLiteralTextNode blockType, IASTListNode<IASTLiteralTextNode> arguments, IASTListNode<IASTMMPStatement> statements) {
		super(statements);
		setBlockType(blockType);
		setBlockArguments(arguments);
		dirty = false;
		if (blockType.getValue().equalsIgnoreCase("RESOURCE")) { //$NON-NLS-1$
			establishSourcePathDependence();
		}
	}

	/**
	 * @param statement
	 */
	public ASTMMPStartBlockStatement(ASTMMPStartBlockStatement statement) {
		super(statement);
		setBlockType((IASTLiteralTextNode) statement.getBlockType().copy());
		setBlockArguments((IASTListNode<IASTLiteralTextNode>) statement.getBlockArguments().copy());
		dirty = statement.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPStartBlockStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPStartBlockStatement node = (ASTMMPStartBlockStatement) obj;
		return node.type.equalValue(type)
			&& equalValue(node.arguments, arguments);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ type.hashCode() ^ 
			hashCodeOr0(arguments) ^ 23928;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStartBlockStatement#getBlockType()
	 */
	public IASTLiteralTextNode getBlockType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStartBlockStatement#setBlockType(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setBlockType(IASTLiteralTextNode type) {
		Check.checkArg(type);
		unparent(this.type);
		parent(type);
		this.type = type;
		fireChanged();
		dirty = true;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStartBlockStatement#getBlockArguments()
	 */
	public IASTListNode<IASTLiteralTextNode> getBlockArguments() {
		return arguments;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStartBlockStatement#setBlockArguments(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode)
	 */
	public void setBlockArguments(IASTListNode<IASTLiteralTextNode> arguments) {
		unparent(this.arguments);
		if (arguments != null)
			parent(arguments);
		this.arguments = arguments;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTMMPStartBlockStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText(getKeywordName());
		handler.emitSpace();
		handler.emitNode(getBlockType());
		if (arguments != null) {
			handler.emitSpace();
			handler.emitNode(arguments);
		}
		handler.emitNewline();
		
		rewriteBlock(1, handler);
		
		handler.emitText(END_TOKEN);
		handler.emitNewline();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return makeChildListWith(type, arguments);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return START_TOKEN;
	}
}
