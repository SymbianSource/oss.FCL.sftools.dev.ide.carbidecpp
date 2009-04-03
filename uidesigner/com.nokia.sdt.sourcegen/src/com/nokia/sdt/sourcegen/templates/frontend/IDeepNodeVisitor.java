/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.templates.frontend;

/**
 * Depth-first visitor of a node tree
 * 
 *
 */
public interface IDeepNodeVisitor extends INodeVisitor {
    /**
     * Visit a block node upon open and close
     * 
     * @param node the block
     * @param open true: first entering block, false: leaving block
     */
    public void visitBlockNode(BlockNode node, boolean open);
}
