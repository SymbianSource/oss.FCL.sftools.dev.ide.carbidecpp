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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.bldinf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExtensionBlockStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExtensionStatement;


public class ASTBldInfExtensionBlockStatement 
		extends ASTBldInfBlockStatement<IASTBldInfExtensionStatement>
		implements IASTBldInfExtensionBlockStatement {
	
	private static final String END_KEYWORD = "END"; //$NON-NLS-1$
	private IASTListNode<IASTLiteralTextNode> args;

	/**
	 * 
	 */
	public ASTBldInfExtensionBlockStatement(IASTLiteralTextNode keyword,
			IASTListNode<IASTLiteralTextNode> arguments,
			IASTListNode<IASTBldInfExtensionStatement> stmts) {
		super(keyword, stmts);
		setArguments(arguments);
		dirty = false;
	}

	/**
	 * @param other
	 */
	public ASTBldInfExtensionBlockStatement(ASTBldInfExtensionBlockStatement other) {
		super(other);
		setArguments((IASTListNode<IASTLiteralTextNode>) other.args.copy());
		dirty = other.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBldInfExtensionBlockStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		ASTBldInfExtensionBlockStatement other = (ASTBldInfExtensionBlockStatement) obj;
		return other.args.equalValue(args);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ args.hashCode() ^ 249982333;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTBldInfExtensionBlockStatement(this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExtensionStatement#getArguments()
	 */
	public IASTListNode<IASTLiteralTextNode> getArguments() {
		return args;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement#setTargetPath(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setArguments(IASTListNode<IASTLiteralTextNode> args) {
		unparent(this.args);
		parent(args);
		this.args = args;
		fireChanged();
		dirty = true;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.dom.bldinf.ASTBldInfBlockStatement#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	@Override
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(getKeyword());
		handler.emitSpace();
		handler.emitNode(getArguments());
		handler.emitNewline();
		
		handler.indent(1);
		for (IASTStatement statement : getList()) {
			// only add indentation for new lines
			//if (statement.getRegion() == null)
			//	handler.emitText(indent);
			handler.emitNode(statement);
		}
		handler.indent(-1);
		
		handler.emitText(END_KEYWORD);
		handler.emitNewline();
	}
	
	@Override
	public IASTNode[] getChildren() {
		List<IASTNode> kids = new ArrayList<IASTNode>(Arrays.asList(super.getChildren()));
		kids.add(args);
		return (IASTNode[]) kids.toArray(new IASTNode[kids.size()]);
	}
}
