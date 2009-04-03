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
 * This interface signifies an allowed top-level node.
 *
 */
public interface IASTTopLevelNode extends IASTNode {

	/** Set the following node in a sibling sequence.
	 * This is not copied if a node is duplicated.
	 * @param node next node, may be null 
	 */
	void setNext(IASTTopLevelNode node);
	
	/** Get the next node in a sibling sequence
	 * @return next node, may be null 
	 */
	IASTTopLevelNode getNext();

}
