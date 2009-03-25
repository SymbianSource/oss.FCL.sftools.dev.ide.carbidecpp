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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGElseContainer;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGElseContainer extends ASTPKGStatement implements
		IASTPKGElseContainer {
	IASTListNode<IASTPKGStatement> statements;

	public ASTPKGElseContainer(IASTListNode<IASTPKGStatement> statements) {
		super();
		setStatements(statements);
		dirty = false;
	}

	public ASTPKGElseContainer(ASTPKGElseContainer statement) {
		super(statement);
		setStatements((IASTListNode<IASTPKGStatement>) statement
				.getStatements().copy());
		dirty = statement.dirty;
	}

	public IASTListNode<IASTPKGStatement> getStatements() {
		return statements;
	}

	public void setStatements(IASTListNode<IASTPKGStatement> statements) {
		Check.checkArg(statements);
		unparent(this.statements);
		parent(statements);
		this.statements = statements;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGElseContainer(this);
	}

	public IASTNode[] getChildren() {
		return new IASTNode[] { statements };
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitNode(statements);
		// statement is a EOL separated list, need new line at the end
		handler.emitNewline();
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
				+ ((statements == null) ? 0 : statements.hashCode());
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
		final ASTPKGElseContainer other = (ASTPKGElseContainer) obj;
		if (statements == null) {
			if (other.statements != null)
				return false;
		} else if (!statements.equals(other.statements))
			return false;
		return true;
	}

}
