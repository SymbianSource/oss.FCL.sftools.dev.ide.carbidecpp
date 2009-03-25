/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.model.dummy;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTStatement;
import com.nokia.cpp.internal.api.utils.core.Check;


public class ASTDummyStatement extends ASTStatement implements
		IASTDummyStatement {

	private IASTLiteralTextNode text;

	public ASTDummyStatement(IASTLiteralTextNode node) {
		super();
		setText(node);
		dirty = false;
	}

	/**
	 * @param other
	 */
	public ASTDummyStatement(ASTDummyStatement other) {
		super(other);
		setText((IASTLiteralTextNode) other.getText().copy());
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement#getKeywordName()
	 */
	public String getKeywordName() {
		return null;
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.model.IASTDummyStatement#getText()
	 */
	public IASTLiteralTextNode getText() {
		return text;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.model.IASTDummyStatement#setText(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode)
	 */
	public void setText(IASTLiteralTextNode text) {
		Check.checkArg(text);
		unparent(this.text);
		parent(text);
		this.text = text;
		fireChanged();
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#getChildren()
	 */
	public IASTNode[] getChildren() {
		return new IASTNode[] { text };
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#copy()
	 */
	public IASTNode copy() {
		return new ASTDummyStatement(this);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode#rewrite(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IRewriteHandler)
	 */
	public void rewrite(IRewriteHandler handler) {
		handler.emitText(text.getValue());
		handler.emitNewline();
	}

}
