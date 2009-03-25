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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;

public class ChangeModification implements IViewChangeModification {
	private final IASTStatement stmt;

	public ChangeModification(IASTStatement stmt) {
		this.stmt = stmt;
	}

	public IASTNode[] getNodes() {
		return new IASTNode[] { stmt };
	}

	public IASTNode[] getReplacementNodes() {
		return null;
	}

	public IASTNode getParent() {
		return null;
	}
}
