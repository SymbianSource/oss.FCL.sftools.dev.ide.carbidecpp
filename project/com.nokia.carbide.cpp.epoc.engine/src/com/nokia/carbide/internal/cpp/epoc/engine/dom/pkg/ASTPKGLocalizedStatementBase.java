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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGLocalizedStatementBase;
import com.nokia.cpp.internal.api.utils.core.*;


public abstract class ASTPKGLocalizedStatementBase extends ASTPKGStatement
		implements IASTPKGLocalizedStatementBase {

	private IASTListNode<IASTLiteralTextNode> languageVariants;

	/**
	 * @param languageVariants
	 */
	public ASTPKGLocalizedStatementBase(
			IASTListNode<IASTLiteralTextNode> languageVariants) {
		super();
		setLanguageVariants(languageVariants);
		dirty = false;
	}

	/**
	 * @param statement
	 */
	public ASTPKGLocalizedStatementBase(ASTPKGLocalizedStatementBase statement) {
		super(statement);
		setLanguageVariants((IASTListNode<IASTLiteralTextNode>) statement
				.getLanguageVariants().copy());
		dirty = statement.dirty;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.pkg.dom.IASTPKGLocalizedStatementBase#getLanguageVariants()
	 */
	public IASTListNode<IASTLiteralTextNode> getLanguageVariants() {
		return getArguments();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.pkg.dom.IASTPKGLocalizedStatementBase#setLanguageVariants(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode)
	 */
	public void setLanguageVariants(IASTListNode<IASTLiteralTextNode> languages) {
		setArguments(languages);
	}

	public IASTListNode<IASTLiteralTextNode> getArguments() {
		return languageVariants;
	}

	public void setArguments(IASTListNode<IASTLiteralTextNode> arguments) {
		Check.checkArg(arguments);
		unparent(this.languageVariants);
		parent(arguments);
		this.languageVariants = arguments;
		fireChanged();
		dirty = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListHolder#getList()
	 */
	public IASTListNode<IASTLiteralTextNode> getList() {
		return languageVariants;
	}

	protected IASTNode[] makeChildListWith() {
		return new IASTNode[] { languageVariants };
	}
	
	protected IASTNode[] makeChildListWith(IASTNode other) {
		IASTNode[] kids = new IASTNode[2];
		int idx = 0;
		kids[idx++] = languageVariants;
		if (other != null)
			kids[idx++] = other;
		if (idx == 2)
			return kids;

		IASTNode[] smallerKids = new IASTNode[idx];
		System.arraycopy(kids, 0, smallerKids, 0, idx);
		return smallerKids;
	}

	/*
	 * not used, nor tested protected IASTNode[] makeChildListWith(IASTNode
	 * other) { if (other != null) { IASTNode[] kids = new IASTNode[2]; kids[0] =
	 * languageVariants; kids[1] = other; return kids; } else { return new
	 * IASTNode[] { languageVariants }; } }
	 */

	protected IASTNode[] makeChildListWith(IASTNode other, IASTNode other2) {
		IASTNode[] kids = new IASTNode[3];
		int idx = 0;
		kids[idx++] = languageVariants;
		if (other != null)
			kids[idx++] = other;
		if (other2 != null)
			kids[idx++] = other2;
		if (idx == 3)
			return kids;

		IASTNode[] smallerKids = new IASTNode[idx];
		System.arraycopy(kids, 0, smallerKids, 0, idx);
		return smallerKids;
	}

	protected IASTNode[] makeChildListWith(IASTNode other, IASTNode other2,
			IASTNode other3) {
		IASTNode[] kids = new IASTNode[4];
		int idx = 0;
		kids[idx++] = languageVariants;
		if (other != null)
			kids[idx++] = other;
		if (other2 != null)
			kids[idx++] = other2;
		if (other3 != null)
			kids[idx++] = other3;
		if (idx == 4)
			return kids;

		IASTNode[] smallerKids = new IASTNode[idx];
		System.arraycopy(kids, 0, smallerKids, 0, idx);
		return smallerKids;
	}

	/*
	 * not used, nor tested protected IASTNode[] makeChildListWith(IASTNode
	 * other, IASTNode other2, IASTNode other3, IASTNode other4) { IASTNode[]
	 * kids = new IASTNode[5]; int idx = 0; kids[idx++] = languageVariants; if
	 * (other != null) kids[idx++] = other; if (other2 != null) kids[idx++] =
	 * other2; if (other3 != null) kids[idx++] = other3; if (other4 != null)
	 * kids[idx++] = other4; if (idx == 5) return kids;
	 * 
	 * IASTNode[] smallerKids = new IASTNode[idx]; System.arraycopy(kids, 0,
	 * smallerKids, 0, idx); return smallerKids; }
	 */

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
				+ ((languageVariants == null) ? 0 : languageVariants.hashCode());
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
		final ASTPKGLocalizedStatementBase other = (ASTPKGLocalizedStatementBase) obj;
		if (languageVariants == null) {
			if (other.languageVariants != null)
				return false;
		} else if (!languageVariants.equals(other.languageVariants))
			return false;
		return true;
	}
}
