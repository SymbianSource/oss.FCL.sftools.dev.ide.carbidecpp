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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGPackageSignatureStatement;


public class ASTPKGPackageSignatureStatement extends ASTPKGStatement implements
		IASTPKGPackageSignatureStatement {

	IASTLiteralTextNode key;
	IASTLiteralTextNode cert;

	public ASTPKGPackageSignatureStatement(IASTLiteralTextNode key,
			IASTLiteralTextNode cert) {
		super();
		setKey(key);
		setCert(cert);
	}

	public ASTPKGPackageSignatureStatement(
			ASTPKGPackageSignatureStatement statement) {
		super(statement);
		if (statement.getKey() != null)
			setKey((IASTLiteralTextNode) statement.getKey().copy());
		else
			setKey(null);
		if (statement.getCert() != null)
			setCert((IASTLiteralTextNode) statement.getCert().copy());
		else
			setCert(null);

		dirty = statement.dirty;
	}

	public IASTLiteralTextNode getKey() {
		return key;
	}

	public void setKey(IASTLiteralTextNode key) {
		unparent(key);
		parent(key);
		this.key = key;
		fireChanged();
		dirty = true;
	}

	public IASTLiteralTextNode getCert() {
		return cert;
	}

	public void setCert(IASTLiteralTextNode cert) {
		unparent(cert);
		parent(cert);
		this.cert = cert;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGPackageSignatureStatement(this);
	}

	public IASTNode[] getChildren() {
		ArrayList<IASTNode> childrenArray = new ArrayList<IASTNode>();

		if (key != null) {
			childrenArray.add(key);
		}
		if (cert != null) {
			childrenArray.add(cert);
		}

		if (childrenArray.size() > 0) {
			return childrenArray.toArray(new IASTNode[childrenArray.size()]);
		} else {
			return NO_CHILDREN;
		}
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitText("*"); //$NON-NLS-1$
		if (key != null) {
			handler.emitNode(key);
		}
		if (cert != null) {
			handler.emitText(","); //$NON-NLS-1$
			handler.emitNode(cert);
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
		result = prime * result + ((cert == null) ? 0 : cert.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		final ASTPKGPackageSignatureStatement other = (ASTPKGPackageSignatureStatement) obj;
		if (cert == null) {
			if (other.cert != null)
				return false;
		} else if (!cert.equals(other.cert))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
