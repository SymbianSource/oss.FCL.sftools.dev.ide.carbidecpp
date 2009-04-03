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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTStatement;


public abstract class ASTPKGStatement extends ASTStatement implements
		IASTPKGStatement {

	public ASTPKGStatement() {
		super();
	}

	/**
	 * @param statement
	 */
	public ASTPKGStatement(ASTPKGStatement statement) {
		super(statement);
		// do anything for local if needed
	}

	/**
	 * Must implement to compare all variant fields (including document, path,
	 * region).
	 * 
	 * @see Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		// no static, nothing to do
		return super.equals(obj);
	}

	/**
	 * Must implement to satisfy requirement that if a.equals(b), then
	 * a.hashCode() == b.hashCode().
	 * 
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		// no static, nothing to do
		return super.hashCode();
	}
}
