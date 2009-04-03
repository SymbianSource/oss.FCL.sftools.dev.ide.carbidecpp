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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPAifStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTMMPAifStatement extends ASTMMPListArgumentStatementBase implements
		IASTMMPAifStatement {

	private IASTLiteralTextNode targetFile;
	private IASTLiteralTextNode sourcePath;
	private IASTLiteralTextNode resource;
	private IASTLiteralTextNode colorDepth;

	/**
	 * @param keyword
	 * @param arguments
	 */
	public ASTMMPAifStatement(
			IASTLiteralTextNode targetFile,
			IASTLiteralTextNode sourcePath,
			IASTLiteralTextNode resource,
			IASTLiteralTextNode colorDepth,
			IASTListNode<IASTLiteralTextNode> sourceBitmaps) {
		super(sourceBitmaps);
		setTargetFile(targetFile);
		setSourcePath(sourcePath);
		setResource(resource);
		setColorDepth(colorDepth);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTMMPAifStatement(ASTMMPAifStatement statement) {
		super(statement);
		setTargetFile((IASTLiteralTextNode) statement.getTargetFile().copy());
		setSourcePath((IASTLiteralTextNode) statement.getSourcePath().copy());
		setResource((IASTLiteralTextNode) statement.getResource().copy());
		if (statement.getColorDepth() != null)
			setColorDepth((IASTLiteralTextNode) statement.getColorDepth().copy());
		dirty = statement.dirty;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTMMPAifStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTMMPAifStatement node = (ASTMMPAifStatement) obj;
		return node.targetFile.equalValue(targetFile)
		&& node.sourcePath.equalValue(sourcePath)
		&& node.resource.equalValue(resource)
		&& equalValue(node.colorDepth, colorDepth);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ targetFile.hashCode() ^ sourcePath.hashCode()
				^ resource.hashCode() ^ hashCodeOr0(colorDepth) 
				^ 0x982761;
	}



	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPAifStatement#getTargetFile()
	 */
	public IASTLiteralTextNode getTargetFile() {
		return targetFile;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPAifStatement#setTargetFile(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression)
	 */
	public void setTargetFile(IASTLiteralTextNode targetFile) {
		Check.checkArg(targetFile);
		unparent(this.targetFile);
		parent(targetFile);
		this.targetFile = targetFile;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPAifStatement#getSourcePath()
	 */
	public IASTLiteralTextNode getSourcePath() {
		return sourcePath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPAifStatement#setSourcePath(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression)
	 */
	public void setSourcePath(IASTLiteralTextNode sourcePath) {
		Check.checkArg(sourcePath);
		unparent(this.sourcePath);
		parent(sourcePath);
		this.sourcePath = sourcePath;
		fireChanged();
		dirty = true;

	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPAifStatement#getResource()
	 */
	public IASTLiteralTextNode getResource() {
		return resource;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPAifStatement#setResource(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression)
	 */
	public void setResource(IASTLiteralTextNode resource) {
		Check.checkArg(resource);
		unparent(this.resource);
		parent(resource);
		this.resource = resource;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPAifStatement#getColorDepth()
	 */
	public IASTLiteralTextNode getColorDepth() {
		return colorDepth;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPAifStatement#setColorDepth(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression)
	 */
	public void setColorDepth(IASTLiteralTextNode colorDepth) {
		unparent(this.colorDepth);
		parent(colorDepth);
		this.colorDepth = colorDepth;
		fireChanged();
		dirty = true;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ASTMMPListArgumentStatement#copy()
	 */
	public IASTNode copy() {
		return new ASTMMPAifStatement(this);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.internal.ASTMMPListArgumentStatement#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText(getKeywordName());
		handler.emitSpace();
		handler.emitNode(targetFile);
		handler.emitSpace();
		handler.emitNode(sourcePath);
		handler.emitSpace();
		handler.emitNode(resource);
		if (colorDepth != null) {
			handler.emitSpace();
			handler.emitNode(colorDepth);
			handler.emitSpace();
			handler.emitNode(getArguments());
		}
		handler.emitNewline();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return makeChildListWith(targetFile, sourcePath, resource, colorDepth);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.mmp.dom.IASTMMPStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return AIF_KEYWORD;
	}
}
