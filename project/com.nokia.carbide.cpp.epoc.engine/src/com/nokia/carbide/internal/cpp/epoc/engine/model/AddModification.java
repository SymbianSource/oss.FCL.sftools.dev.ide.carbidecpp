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
import com.nokia.cpp.internal.api.utils.core.*;

public class AddModification implements IViewAddModification {
	/**
	 * 
	 */
	private final IASTNode parent;
	private final IASTStatement stmt;
	private Pair<IASTNode, IASTNode> anchors;

	public AddModification(IASTNode parent, IASTStatement stmt) {
		this.parent = parent;
		this.stmt = stmt;
		this.anchors = null;
	}

	public AddModification(IASTNode parent, IASTStatement stmt, IASTStatement before, IASTStatement after) {
		this.parent = parent;
		this.stmt = stmt;
		this.anchors = new Pair(before, after);
	}
	public AddModification(IASTNode parent, IASTStatement stmt, Pair<IASTNode, IASTNode> anchors) {
		this.parent = parent;
		this.stmt = stmt;
		this.anchors = anchors;
	}


	public IASTNode[] getNodes() {
		return new IASTNode[] { stmt };
	}

	public IASTNode getParent() {
		return parent;
	}
	
	public Pair<IASTNode, IASTNode> getInsertAnchors() {
		return anchors;
	}
}
