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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.bsf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bsf.IASTBSFStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTStatement;


public abstract class ASTBSFStatement extends ASTStatement implements IASTBSFStatement {

	public ASTBSFStatement() {
		super();
	}
	
	public ASTBSFStatement(ASTBSFStatement other) {
		super(other);
	}
	
}
