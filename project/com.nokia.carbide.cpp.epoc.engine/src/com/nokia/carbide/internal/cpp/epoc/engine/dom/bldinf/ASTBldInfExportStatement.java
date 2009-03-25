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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTBldInfExportStatement extends ASTBldInfStatement implements
		IASTBldInfExportStatement {

	private IASTLiteralTextNode zipModifier;
	private IASTLiteralTextNode targetPath;
	private IASTLiteralTextNode sourcePath;

	public ASTBldInfExportStatement(IASTLiteralTextNode srcPath, 
			IASTLiteralTextNode targetPath,
			IASTLiteralTextNode zipModifier) {
		setSourcePath(srcPath);
		setTargetPath(targetPath);
		setZipModifier(zipModifier);
		dirty = false;
	}

	/**
	 * @param other
	 */
	public ASTBldInfExportStatement(ASTBldInfExportStatement other) {
		super(other);
		setSourcePath((IASTLiteralTextNode) other.getSourcePath().copy());
		if (other.getTargetPath() != null)
			setTargetPath((IASTLiteralTextNode) other.getTargetPath().copy());
		if (other.getZipModifier() != null)
			setZipModifier((IASTLiteralTextNode) other.getZipModifier().copy());
		dirty = other.dirty;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTBldInfExportStatement))
			return false;
		if (!super.equalValue(obj))
			return false;
		
		ASTBldInfExportStatement node = (ASTBldInfExportStatement) obj;
		return equalValue(node.zipModifier, zipModifier)
		&& equalValue(node.targetPath, targetPath)
		&& node.sourcePath.equalValue(sourcePath);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode() ^ hashCodeOr0(zipModifier)
		^ hashCodeOr0(targetPath) ^ sourcePath.hashCode() ^ 0x3211;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement#getSourcePath()
	 */
	public IASTLiteralTextNode getSourcePath() {
		return sourcePath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement#getTargetPath()
	 */
	public IASTLiteralTextNode getTargetPath() {
		return targetPath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement#getZipModifier()
	 */
	public IASTLiteralTextNode getZipModifier() {
		return zipModifier;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement#setSourcePath(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setSourcePath(IASTLiteralTextNode path) {
		Check.checkArg(path);
		unparent(this.sourcePath);
		parent(path);
		this.sourcePath = path;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement#setTargetPath(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setTargetPath(IASTLiteralTextNode path) {
		unparent(this.targetPath);
		parent(path);
		this.targetPath = path;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfExportStatement#setZipModifier(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setZipModifier(IASTLiteralTextNode modifier) {
		unparent(this.zipModifier);
		parent(modifier);
		this.zipModifier = modifier;
		fireChanged();
		dirty = true;

	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTBldInfExportStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		if (targetPath != null && zipModifier != null)
			return new IASTNode[] { sourcePath, targetPath, zipModifier };
		else if (targetPath != null)
			return new IASTNode[] { sourcePath, targetPath };
		else if (zipModifier != null)
			return new IASTNode[] { sourcePath, zipModifier };
		else
			return new IASTNode[] { sourcePath };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		if (zipModifier != null) { 
			handler.emitNode(zipModifier);
			handler.emitSpace();
		}
		handler.emitNode(sourcePath);
		if (targetPath != null) {
			handler.emitSpace();
			handler.emitNode(targetPath);
		}
		handler.emitNewline();
	}

}
