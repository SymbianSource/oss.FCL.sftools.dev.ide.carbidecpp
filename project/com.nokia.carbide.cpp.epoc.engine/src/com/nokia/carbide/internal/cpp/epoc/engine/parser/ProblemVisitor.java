/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.parser;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.cpp.internal.api.utils.core.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Visit problems in a tree.
 *
 */
public class ProblemVisitor implements IASTVisitor {

	private List<IASTProblemNode> problems;

	public ProblemVisitor() {
		this.problems = new ArrayList<IASTProblemNode>();
	}
	
	public IASTProblemNode[] getProblems() {
		return (IASTProblemNode[]) problems.toArray(new IASTProblemNode[problems.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor#visit(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public int visit(IASTNode node) {
		if (node instanceof IASTProblemNode) {
			problems.add((IASTProblemNode) node);
		}
		return VISIT_CHILDREN;
	}

	public IMessage[] getMessages() {
		IMessage[] messages = new IMessage[problems.size()];
		int idx = 0;
		for (IASTProblemNode problem : problems) {
			messages[idx++] = problem.getMessage();
		}
		return messages;
	}

}
