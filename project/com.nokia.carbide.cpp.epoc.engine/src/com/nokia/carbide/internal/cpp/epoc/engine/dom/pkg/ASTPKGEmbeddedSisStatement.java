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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGEmbeddedSisStatement;


public class ASTPKGEmbeddedSisStatement extends ASTPKGLocalizedStatementBase implements
		IASTPKGEmbeddedSisStatement {

	IASTLiteralTextNode uid;
	boolean languageDependentSyntaxStatus;

	public ASTPKGEmbeddedSisStatement(IASTListNode<IASTLiteralTextNode> languageVariants,
			IASTLiteralTextNode uid) {
		super(languageVariants);
		setUid(uid);
		setLanguageDependentSyntaxStatus(languageVariants.size() > 1);
		dirty = false;
	}

	public ASTPKGEmbeddedSisStatement(ASTPKGEmbeddedSisStatement statement) {
		super(statement);
		if (statement.getUid() != null)
			setUid((IASTLiteralTextNode) statement.getUid().copy());
		else
			setUid(null);
		setLanguageDependentSyntaxStatus(statement
				.getLanguageDependentSyntaxStatus());
		dirty = statement.isDirty();
	}
	
	public boolean getLanguageDependentSyntaxStatus() {
		return languageDependentSyntaxStatus;
	}

	public IASTLiteralTextNode getUid() {
		return uid;
	}

	public void setLanguageDependentSyntaxStatus(boolean status) {
		this.languageDependentSyntaxStatus = status;
		fireChanged();
		dirty = true;
	}
	
	public void setUid(IASTLiteralTextNode uid) {
		unparent(uid);
		parent(uid);
		this.uid = uid;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGEmbeddedSisStatement(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith(uid);
	}

	public void rewrite(IRewriteHandler handler) {
		boolean languageDependant = getLanguageVariants().getChildren().length > 1;
		if (languageDependant) {
			handler.emitText("{"); //$NON-NLS-1$
		}
		handler.emitNode(getLanguageVariants());
		if (languageDependant) {
			handler.emitText("}"); //$NON-NLS-1$
		}
		if (uid != null) {
			handler.emitText(","); //$NON-NLS-1$
			handler.emitText("("); //$NON-NLS-1$
			handler.emitNode(uid);
			handler.emitText(")"); //$NON-NLS-1$
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		ASTPKGEmbeddedSisStatement other = (ASTPKGEmbeddedSisStatement) obj;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
}
