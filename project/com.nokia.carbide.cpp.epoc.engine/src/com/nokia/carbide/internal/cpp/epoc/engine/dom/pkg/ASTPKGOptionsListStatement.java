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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGOptionsListOption;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGOptionsListStatement;
import com.nokia.cpp.internal.api.utils.core.*;


public class ASTPKGOptionsListStatement extends ASTPKGStatement implements
		IASTPKGOptionsListStatement {

	private IASTListNode<IASTPKGOptionsListOption> optionsList;

	public ASTPKGOptionsListStatement(
			IASTListNode<IASTPKGOptionsListOption> optionsList) {
		super();
		setOptionsList(optionsList);
		dirty = false;
	}

	public ASTPKGOptionsListStatement(ASTPKGOptionsListStatement statement) {
		super(statement);
		setOptionsList((IASTListNode<IASTPKGOptionsListOption>) statement
				.getOptionsList().copy());
		dirty = statement.dirty;
	}

	public IASTListNode<IASTPKGOptionsListOption> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(
			IASTListNode<IASTPKGOptionsListOption> optionsList) {
		Check.checkArg(optionsList);
		unparent(this.optionsList);
		parent(optionsList);
		this.optionsList = optionsList;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGOptionsListStatement(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith();
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitText("!"); //$NON-NLS-1$
		handler.emitText("("); //$NON-NLS-1$
		handler.emitNode(optionsList);
		handler.emitText(")"); //$NON-NLS-1$
	}

	protected IASTNode[] makeChildListWith() {
		return new IASTNode[] { optionsList };
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
				+ ((optionsList == null) ? 0 : optionsList.hashCode());
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
		final ASTPKGOptionsListStatement other = (ASTPKGOptionsListStatement) obj;
		if (optionsList == null) {
			if (other.optionsList != null)
				return false;
		} else if (!optionsList.equals(other.optionsList))
			return false;
		return true;
	}
}
