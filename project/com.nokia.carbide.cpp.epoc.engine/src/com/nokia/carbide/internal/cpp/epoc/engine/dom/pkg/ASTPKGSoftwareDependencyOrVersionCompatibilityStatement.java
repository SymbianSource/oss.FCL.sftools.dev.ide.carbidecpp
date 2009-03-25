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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGSoftwareDependencyOrVersionCompatibilityStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGSoftwareDependencyOrVersionCompatibilityStatement extends
		ASTPKGLocalizedStatementBase implements
		IASTPKGSoftwareDependencyOrVersionCompatibilityStatement {

	IASTLiteralTextNode uid;
	IASTListNode<IASTLiteralTextNode> versionLowerBound;
	IASTListNode<IASTLiteralTextNode> versionUpperBound;
	boolean versionRangeStatus;

	public ASTPKGSoftwareDependencyOrVersionCompatibilityStatement(
			ASTPKGSoftwareDependencyOrVersionCompatibilityStatement statement) {
		super(statement);
		setUid((IASTLiteralTextNode) statement.getUid().copy());
		setVersionLowerBound((IASTListNode<IASTLiteralTextNode>) statement
				.getVersionLowerBound().copy());
		setVersionUpperBound((IASTListNode<IASTLiteralTextNode>) statement
				.getVersionUpperBound().copy());
		setVersionRangeStatus(statement.getVersionRangeStatus());
		dirty = statement.isDirty();
	}

	public ASTPKGSoftwareDependencyOrVersionCompatibilityStatement(
			IASTLiteralTextNode uid,
			IASTListNode<IASTLiteralTextNode> versionLowerBound,
			IASTListNode<IASTLiteralTextNode> versionUpperBound,
			IASTListNode<IASTLiteralTextNode> languageVariants) {
		super(languageVariants);
		setUid(uid);
		setVersionLowerBound(versionLowerBound);
		setVersionUpperBound(versionUpperBound);
		setVersionRangeStatus(true);
		dirty = false;
	}

	public ASTPKGSoftwareDependencyOrVersionCompatibilityStatement(
			IASTLiteralTextNode uid, IASTListNode<IASTLiteralTextNode> version,
			IASTListNode<IASTLiteralTextNode> languageVariants) {
		super(languageVariants);
		setUid(uid);
		setVersionLowerBound(version);
		IASTListNode<IASTLiteralTextNode> dummy = ASTFactory
				.createListNode(",");
		// a dummy at the void after the end
		if (version.getSourceRegion() != null) {
			dummy.setSourceRegion(version.getSourceRegion()
					.getExclusiveTailRegion());
		}
		setVersionUpperBound(dummy);
		setVersionRangeStatus(false);
		dirty = false;
	}

	public IASTLiteralTextNode getUid() {
		return uid;
	}

	public IASTListNode<IASTLiteralTextNode> getVersionLowerBound() {
		return versionLowerBound;
	}

	public IASTListNode<IASTLiteralTextNode> getVersionUpperBound() {
		return versionUpperBound;
	}

	public boolean getVersionRangeStatus() {
		return versionRangeStatus;
	}

	public void setUid(IASTLiteralTextNode uid) {
		Check.checkArg(uid);
		unparent(uid);
		parent(uid);
		this.uid = uid;
		fireChanged();
		dirty = true;
	}

	public void setVersionLowerBound(
			IASTListNode<IASTLiteralTextNode> versionLowerBound) {
		Check.checkArg(versionLowerBound);
		unparent(versionLowerBound);
		parent(versionLowerBound);
		this.versionLowerBound = versionLowerBound;
		fireChanged();
		dirty = true;
	}

	public void setVersionUpperBound(
			IASTListNode<IASTLiteralTextNode> versionUpperBound) {
		Check.checkArg(versionUpperBound);
		unparent(versionUpperBound);
		parent(versionUpperBound);
		this.versionUpperBound = versionUpperBound;
		fireChanged();
		dirty = true;
	}

	public void setVersionRangeStatus(boolean status) {
		versionRangeStatus = status;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGSoftwareDependencyOrVersionCompatibilityStatement(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith(uid, versionLowerBound, versionUpperBound);
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitText("("); //$NON-NLS-1$
		handler.emitNode(uid);
		handler.emitText(")"); //$NON-NLS-1$
		handler.emitText(","); //$NON-NLS-1$
		if (versionRangeStatus) {
			handler.emitNode(versionLowerBound);
			handler.emitText("~"); //$NON-NLS-1$
			handler.emitNode(versionUpperBound);
		} else {
			handler.emitNode(versionLowerBound);
		}
		handler.emitText(","); //$NON-NLS-1$
		handler.emitText("{"); //$NON-NLS-1$
		handler.emitNode(getLanguageVariants());
		handler.emitText("}"); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime
				* result
				+ ((versionLowerBound == null) ? 0 : versionLowerBound
						.hashCode());
		result = prime * result + (versionRangeStatus ? 1231 : 1237);
		result = prime
				* result
				+ ((versionUpperBound == null) ? 0 : versionUpperBound
						.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ASTPKGSoftwareDependencyOrVersionCompatibilityStatement other = (ASTPKGSoftwareDependencyOrVersionCompatibilityStatement) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		if (versionLowerBound == null) {
			if (other.versionLowerBound != null)
				return false;
		} else if (!versionLowerBound.equals(other.versionLowerBound))
			return false;
		if (versionRangeStatus != other.versionRangeStatus)
			return false;
		if (versionUpperBound == null) {
			if (other.versionUpperBound != null)
				return false;
		} else if (!versionUpperBound.equals(other.versionUpperBound))
			return false;
		return true;
	}

}
