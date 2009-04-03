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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGPackageHeaderStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGPackageHeaderStatement extends ASTPKGLocalizedStatementBase
		implements IASTPKGPackageHeaderStatement {
	IASTLiteralTextNode uid;
	IASTListNode<IASTLiteralTextNode> version;
	IASTListNode<IASTLiteralTextNode> options;

	public ASTPKGPackageHeaderStatement(
			IASTListNode<IASTLiteralTextNode> languageVariants,
			IASTLiteralTextNode uid, IASTListNode<IASTLiteralTextNode> version,
			IASTListNode<IASTLiteralTextNode> options) {
		super(languageVariants);
		setUid(uid);
		setVersion(version);
		setOptions(options);
		dirty = false;
	}

	public ASTPKGPackageHeaderStatement(ASTPKGPackageHeaderStatement statement) {
		super(statement);
		setUid((IASTLiteralTextNode) statement.getUid().copy());
		setVersion((IASTListNode<IASTLiteralTextNode>) statement.getVersion()
				.copy());
		if (statement.getOptions() != null) {
			setOptions((IASTListNode<IASTLiteralTextNode>) statement
					.getOptions().copy());
		} else {
			setOptions(null);
		}
		dirty = statement.isDirty();
	}

	public IASTNode copy() {
		return new ASTPKGPackageHeaderStatement(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith(uid, version, options);
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitText("#"); //$NON-NLS-1$
		handler.emitText("{"); //$NON-NLS-1$
		handler.emitNode(getLanguageVariants());
		handler.emitText("}"); //$NON-NLS-1$
		handler.emitText(","); //$NON-NLS-1$
		handler.emitText("("); //$NON-NLS-1$
		handler.emitNode(getUid());
		handler.emitText(")"); //$NON-NLS-1$
		handler.emitText(","); //$NON-NLS-1$
		handler.emitNode(getVersion());
		IASTListNode<IASTLiteralTextNode> optionNode = getOptions();
		if (optionNode != null && optionNode.getChildren().length > 0) {
			handler.emitText(","); //$NON-NLS-1$
			handler.emitNode(getOptions());
		}
	}

	public IASTListNode<IASTLiteralTextNode> getOptions() {
		return options;
	}

	public IASTLiteralTextNode getUid() {
		return uid;
	}

	public IASTListNode<IASTLiteralTextNode> getVersion() {
		return version;
	}

	public void setOptions(IASTListNode<IASTLiteralTextNode> options) {
		Check.checkArg(options);
		unparent(this.options);
		parent(options);
		this.options = options;
		fireChanged();
		dirty = true;
	}

	public void setUid(IASTLiteralTextNode uid) {
		Check.checkArg(uid);
		unparent(this.uid);
		parent(uid);
		this.uid = uid;
		fireChanged();
		dirty = true;
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		final ASTPKGPackageHeaderStatement other = (ASTPKGPackageHeaderStatement) obj;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
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
