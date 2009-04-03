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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;

/**
 * Visitor for DOM nodes.
 *
 */
public interface IASTVisitor {
	/** Stop visiting entirely. */
	public static final int VISIT_ABORT = 0;
	/** Do not visit children, continue to siblings. */
	public static final int VISIT_SIBLINGS = 1;
	/** Visit children. */
	public static final int VISIT_CHILDREN = 2;
	
	/** Implementation of visitor.
	 * @param node
	 * @return VISIT_xxx constant
	 * @see #VISIT_ABORT
	 * @see #VISIT_SIBLINGS
	 * @see #VISIT_CHILDREN
	 */ 
	int visit(IASTNode node);
}
