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

package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

/**
 * Visit regions.
 *
 */
public interface IConditionalBlockVisitor {
	public final static int VISIT_ABORT = 1;
	public final static int VISIT_SIBLINGS = 2;
	public final static int VISIT_CHILDREN = 3;
	
	/**
	 * Visit the region. 
	 * @param block
	 * @return VISIT_xxx code
	 */
	int visit(IConditionalBlock block);
}
