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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPOptionStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTMMPOptionStatement extends ASTMMPKeywordStatement
		implements IASTMMPOptionStatement {

	private IASTLiteralTextNode compiler;
	private IASTListNode<IASTLiteralTextNode> options;

	/**
	 * @param keyword
	 * @param arguments
	 */
	public ASTMMPOptionStatement(IASTLiteralTextNode keyword, IASTLiteralTextNode compiler, IASTListNode<IASTLiteralTextNode> options) {
		super(keyword);
		setCompiler(compiler);
		setOptions(options);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTMMPOptionStatement(ASTMMPOptionStatement statement) {
		super(statement);
		setCompiler((IASTLiteralTextNode) statement.getCompiler().copy());
		setOptions((IASTListNode<IASTLiteralTextNode>) statement.getOptions().copy());
		dirty = statement.dirty;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPOptionStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPOptionStatement node = (ASTMMPOptionStatement) obj;
		return node.compiler.equalValue(compiler)
			&& node.options.equalValue(options);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ compiler.hashCode() ^ 
			options.hashCode() ^ -29283;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPOptionStatement#getCompiler()
	 */
	public IASTLiteralTextNode getCompiler() {
		return compiler;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPOptionStatement#setCompiler(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setCompiler(IASTLiteralTextNode compiler) {
		Check.checkArg(compiler);
		unparent(this.compiler);
		parent(compiler);
		this.compiler = compiler;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPOptionStatement#getOptions()
	 */
	public IASTListNode<IASTLiteralTextNode> getOptions() {
		return options;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPOptionStatement#setOptions(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setOptions(IASTListNode<IASTLiteralTextNode> options) {
		Check.checkArg(options);
		unparent(this.options);
		parent(options);
		this.options = options;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTMMPOptionStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(getKeyword());
		handler.emitSpace();
		handler.emitNode(compiler);
		handler.emitSpace();
		handler.emitNode(options);
		handler.emitNewline();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		List<IASTNode> kids = new ArrayList<IASTNode>(Arrays.asList(super.getChildren()));
		kids.add(compiler);
		kids.add(options);
		return (IASTNode[]) kids.toArray(new IASTNode[kids.size()]);
	}
}
