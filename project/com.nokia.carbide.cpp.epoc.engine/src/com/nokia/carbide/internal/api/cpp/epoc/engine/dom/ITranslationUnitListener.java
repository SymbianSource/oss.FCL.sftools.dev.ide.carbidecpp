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
 * This listener reports changes against any node in a
 * IASTTranslationUnit DOM.
 *
 */
public interface ITranslationUnitListener {
	/** Basic report when node changed */
	void changedNode(IASTNode node);

	/**  report change to IASTLiteralTextNode-derived node */
	//void changedLiteralTextNode(IASTLiteralTextNode node, String oldValue, String newValue);
	
	/** Report added list item */
	//void addedListItem(IASTListNode<? extends IASTNode> list, IASTNode node, int index);

	/** Report removed list item */
	//void removedListItem(IASTListNode<? extends IASTNode> list, IASTNode node, int index);

}
