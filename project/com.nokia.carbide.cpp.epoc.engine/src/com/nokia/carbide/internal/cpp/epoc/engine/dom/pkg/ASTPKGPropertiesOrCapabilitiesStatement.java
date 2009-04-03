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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGPropertiesOrCapabilitiesStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGPropertiesOrCapabilitiesStatement extends ASTPKGStatement
		implements IASTPKGPropertiesOrCapabilitiesStatement {
	IASTListNode<IASTLiteralTextNode> propertiesOrCapabilities;

	public ASTPKGPropertiesOrCapabilitiesStatement(
			IASTListNode<IASTLiteralTextNode> propertiesOrCapabilities) {
		super();
		setPropertiesOrCapabilities(propertiesOrCapabilities);
		dirty = false;
	}

	public ASTPKGPropertiesOrCapabilitiesStatement(
			ASTPKGPropertiesOrCapabilitiesStatement statement) {
		super(statement);
		setPropertiesOrCapabilities((IASTListNode<IASTLiteralTextNode>) statement
				.getPropertiesOrCapabilities().copy());
		dirty = statement.dirty;
	}

	public IASTListNode<IASTLiteralTextNode> getPropertiesOrCapabilities() {
		return propertiesOrCapabilities;
	}

	public void setPropertiesOrCapabilities(
			IASTListNode<IASTLiteralTextNode> propertiesOrCapabilities) {
		Check.checkArg(propertiesOrCapabilities);
		unparent(this.propertiesOrCapabilities);
		parent(propertiesOrCapabilities);
		this.propertiesOrCapabilities = propertiesOrCapabilities;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGPropertiesOrCapabilitiesStatement(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith();
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitText("+"); //$NON-NLS-1$
		handler.emitText("("); //$NON-NLS-1$
		handler.emitNode(getPropertiesOrCapabilities());
		handler.emitText(")"); //$NON-NLS-1$
	}

	protected IASTNode[] makeChildListWith() {
		return new IASTNode[] { propertiesOrCapabilities };
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
		result = prime
				* result
				+ ((propertiesOrCapabilities == null) ? 0
						: propertiesOrCapabilities.hashCode());
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
		final ASTPKGPropertiesOrCapabilitiesStatement other = (ASTPKGPropertiesOrCapabilitiesStatement) obj;
		if (propertiesOrCapabilities == null) {
			if (other.propertiesOrCapabilities != null)
				return false;
		} else if (!propertiesOrCapabilities
				.equals(other.propertiesOrCapabilities))
			return false;
		return true;
	}
}
