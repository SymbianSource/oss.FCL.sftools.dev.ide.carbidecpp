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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGVendorStatement;


public class ASTPKGVendorStatement extends ASTPKGLocalizedStatementBase
		implements IASTPKGVendorStatement {

	boolean languageDependentSyntaxStatus;

	public ASTPKGVendorStatement(
			IASTListNode<IASTLiteralTextNode> languageVariants,
			boolean languageDependentSyntaxStatus) {
		super(languageVariants);
		setLanguageDependentSyntaxStatus(languageDependentSyntaxStatus);
		dirty = false;
	}

	public ASTPKGVendorStatement(ASTPKGVendorStatement statement) {
		super(statement);
		setLanguageDependentSyntaxStatus(statement
				.getLanguageDependentSyntaxStatus());
		dirty = statement.isDirty();
	}

	public boolean getLanguageDependentSyntaxStatus() {
		return languageDependentSyntaxStatus;
	}

	public void setLanguageDependentSyntaxStatus(boolean status) {
		this.languageDependentSyntaxStatus = status;
		fireChanged();
		dirty = true;
	}

	public IASTNode copy() {
		return new ASTPKGVendorStatement(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith();
	}

	public void rewrite(IRewriteHandler handler) {
		if (getLanguageDependentSyntaxStatus()) {
			handler.emitText("%"); //$NON-NLS-1$
			handler.emitText("{"); //$NON-NLS-1$
		} else {
			handler.emitText(":"); //$NON-NLS-1$
		}
		handler.emitNode(getLanguageVariants());
		if (getLanguageDependentSyntaxStatus()) {
			handler.emitText("}"); //$NON-NLS-1$
		}
	}

}
