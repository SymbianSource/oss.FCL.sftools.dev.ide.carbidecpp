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


package com.nokia.carbide.internal.cpp.epoc.engine.model.pkg;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.IASTPKGConditionalContainer;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg.IPKGStatementContainer;

import java.util.ArrayList;
import java.util.List;

public class PKGConditionalContainer implements IPKGStatementContainer {

	private List<IPKGStatement> statements;

	private IASTPKGConditionalContainer astContainer;

	public PKGConditionalContainer(IASTPKGConditionalContainer astContainer) {
		this.astContainer = astContainer;
		statements = new ArrayList<IPKGStatement>();
	}

	public List<IPKGStatement> getStatements() {
		return statements;
	}
	
	public IASTPKGConditionalContainer getASTPKGConditionalContainer() {
		return astContainer;
	}

}
