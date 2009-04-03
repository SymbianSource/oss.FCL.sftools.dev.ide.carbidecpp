/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.pkg;

import java.util.ArrayList;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGLogoStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGLogoStatement extends ASTPKGStatement implements
		IASTPKGLogoStatement {

	IASTLiteralTextNode sourcePath;
	IASTLiteralTextNode mimeType;
	IASTLiteralTextNode destPath;

	public ASTPKGLogoStatement(IASTLiteralTextNode sourcePath,
			IASTLiteralTextNode mimeType, IASTLiteralTextNode destPath) {
		super();
		setSourcePath(sourcePath);
		setMimeType(mimeType);
		setDestPath(destPath);
	}

	public ASTPKGLogoStatement(ASTPKGLogoStatement statement) {
		super(statement);
		setSourcePath((IASTLiteralTextNode) statement.getSourcePath().copy());
		setMimeType((IASTLiteralTextNode) statement.getMimeType().copy());
		if (statement.getDestPath() != null)
			setDestPath((IASTLiteralTextNode) statement.getDestPath().copy());
		else
			setDestPath(null);

		dirty = statement.dirty;
	}

	public IASTLiteralTextNode getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(IASTLiteralTextNode sourcePath) {
		Check.checkArg(sourcePath);
		unparent(sourcePath);
		parent(sourcePath);
		this.sourcePath = sourcePath;
		fireChanged();
		dirty = true;
	}

	public IASTLiteralTextNode getDestPath() {
		return destPath;
	}

	public IASTLiteralTextNode getMimeType() {
		return mimeType;
	}

	public void setMimeType(IASTLiteralTextNode mimeType) {
		Check.checkArg(mimeType);
		unparent(mimeType);
		parent(mimeType);
		this.mimeType = mimeType;
		fireChanged();
		dirty = true;
	}

	public void setDestPath(IASTLiteralTextNode destPath) {
		unparent(destPath);
		parent(destPath);
		this.destPath = destPath;
		fireChanged();
		dirty = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equalValue(IASTNode obj) {
		if (!(obj instanceof ASTPKGLogoStatement))
			return false;
		if (!super.equalValue(obj))
			return false;

		ASTPKGLogoStatement node = (ASTPKGLogoStatement) obj;
		return equalValue(node.sourcePath, sourcePath)
				&& equalValue(node.mimeType, mimeType)
				&& equalValue(node.destPath, destPath);
	}

	public IASTNode copy() {
		return new ASTPKGLogoStatement(this);
	}

	public IASTNode[] getChildren() {
		ArrayList<IASTNode> childrenArray = new ArrayList<IASTNode>();

		childrenArray.add(sourcePath);
		childrenArray.add(mimeType);
		if (destPath != null) {
			childrenArray.add(destPath);
		}

		if (childrenArray.size() > 0) {
			return childrenArray.toArray(new IASTNode[childrenArray.size()]);
		} else {
			return NO_CHILDREN;
		}
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitText("="); //$NON-NLS-1$
		handler.emitNode(sourcePath);
		handler.emitText(","); //$NON-NLS-1$
		handler.emitNode(mimeType);
		if (destPath != null) {
			handler.emitText(","); //$NON-NLS-1$
			handler.emitNode(destPath);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((destPath == null) ? 0 : destPath.hashCode());
		result = prime * result
				+ ((mimeType == null) ? 0 : mimeType.hashCode());
		result = prime * result
				+ ((sourcePath == null) ? 0 : sourcePath.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ASTPKGLogoStatement other = (ASTPKGLogoStatement) obj;
		if (destPath == null) {
			if (other.destPath != null)
				return false;
		} else if (!destPath.equals(other.destPath))
			return false;
		if (mimeType == null) {
			if (other.mimeType != null)
				return false;
		} else if (!mimeType.equals(other.mimeType))
			return false;
		if (sourcePath == null) {
			if (other.sourcePath != null)
				return false;
		} else if (!sourcePath.equals(other.sourcePath))
			return false;
		return true;
	}

}
