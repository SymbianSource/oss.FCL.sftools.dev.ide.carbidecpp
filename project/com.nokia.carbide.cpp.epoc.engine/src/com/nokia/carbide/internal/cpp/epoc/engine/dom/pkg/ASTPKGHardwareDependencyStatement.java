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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGHardwareDependencyStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGHardwareDependencyStatement extends
		ASTPKGLocalizedStatementBase implements
		IASTPKGHardwareDependencyStatement {

	IASTLiteralTextNode uid;
	IASTListNode<IASTLiteralTextNode> version;

	public ASTPKGHardwareDependencyStatement(IASTLiteralTextNode key,
			IASTListNode<IASTLiteralTextNode> version,
			IASTListNode<IASTLiteralTextNode> languageVariants) {
		super(languageVariants);
		setUid(key);
		setVersion(version);
	}

	public ASTPKGHardwareDependencyStatement(
			ASTPKGHardwareDependencyStatement statement) {
		super(statement);
		if (statement.getUid() != null)
			setUid((IASTLiteralTextNode) statement.getUid().copy());
		else
			setUid(null);
		if (statement.getVersion() != null)
			setVersion((IASTListNode<IASTLiteralTextNode>) statement
					.getVersion().copy());
		else
			setVersion(null);

		dirty = statement.dirty;
	}

	public IASTLiteralTextNode getUid() {
		return uid;
	}

	public void setUid(IASTLiteralTextNode uid) {
		unparent(uid);
		parent(uid);
		this.uid = uid;
		fireChanged();
		dirty = true;
	}

	public IASTListNode<IASTLiteralTextNode> getVersion() {
		return version;
	}

	public void setVersion(IASTListNode<IASTLiteralTextNode> version) {
		Check.checkArg(version);
		unparent(this.version);
		parent(version);
		this.version = version;
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
		if (!(obj instanceof ASTPKGHardwareDependencyStatement))
			return false;
		if (!super.equalValue(obj))
			return false;

		ASTPKGHardwareDependencyStatement node = (ASTPKGHardwareDependencyStatement) obj;
		return equalValue(node.uid, uid) && equalValue(node.version, version)
				&& equalValue(node.getArguments(), getArguments());
	}

	public IASTNode copy() {
		return new ASTPKGHardwareDependencyStatement(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith(uid, version);
	}

	public void rewrite(IRewriteHandler handler) {
		if (uid != null) {
			handler.emitText("["); //$NON-NLS-1$
			handler.emitNode(uid);
			handler.emitText("]"); //$NON-NLS-1$
		}
		if (version != null) {
			handler.emitText(","); //$NON-NLS-1$
			handler.emitNode(getVersion());
		}
		handler.emitText(","); //$NON-NLS-1$
		handler.emitText("{"); //$NON-NLS-1$
		handler.emitNode(getLanguageVariants());
		handler.emitText("}"); //$NON-NLS-1$
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ASTPKGHardwareDependencyStatement other = (ASTPKGHardwareDependencyStatement) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
