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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;

/**
 * Basis for a node that contains a block list of contents.
 *
 */
public interface IASTBldInfBlockStatement<ElementType extends IASTNode> 
		extends IASTBldInfStatement, IASTListHolder<ElementType> {
	IASTLiteralTextNode getKeyword();
	void setKeyword(IASTLiteralTextNode node);
	void setList(IASTListNode<ElementType> list);
}
