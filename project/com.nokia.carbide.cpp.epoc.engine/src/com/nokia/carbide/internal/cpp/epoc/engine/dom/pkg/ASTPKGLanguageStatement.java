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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGLanguageStatement;


public class ASTPKGLanguageStatement extends ASTPKGLocalizedStatementBase
		implements IASTPKGLanguageStatement {

	public ASTPKGLanguageStatement(
			IASTListNode<IASTLiteralTextNode> languageVariants) {
		super(languageVariants);
		dirty = false;
	}

	public ASTPKGLanguageStatement(ASTPKGLocalizedStatementBase statement) {
		super(statement);
		dirty = statement.isDirty();
	}

	public IASTNode copy() {
		return new ASTPKGLanguageStatement(this);
	}

	public IASTNode[] getChildren() {
		return makeChildListWith();
	}

	public void rewrite(IRewriteHandler handler) {
		handler.emitText("&"); //$NON-NLS-1$
		handler.emitNode(getLanguageVariants());
	}

}
