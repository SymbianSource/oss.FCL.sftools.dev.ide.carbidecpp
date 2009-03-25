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

package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;


public class ConditionalBlockUtils {

	/**
	 * Find the region containing the given node.
	 * @param node
	 * @return
	 */
	public static IConditionalBlock findBlockForNode(IConditionalBlock rootBlock, final IASTNode node) {
		return findBlockContaining(rootBlock, node.getSourceRegion());
	}

	/**
	 * Find the view region containing the given region.
	 * @param region
	 * @return
	 */
	public static IConditionalBlock findBlockContaining(IConditionalBlock rootBlock, final ISourceRegion region) {
		if (region == null)
			return null;
		
		final IConditionalBlock[] theRegion = new IConditionalBlock[1];
		rootBlock.accept(new IConditionalBlockVisitor() {

			public int visit(IConditionalBlock block) {
				if (block.containsRegion(region)) {
					theRegion[0] = block;
					// keep searching until we find the smallest
				}
				return VISIT_CHILDREN;
			}
		});
		return theRegion[0];
	}

	/**
	 * Tell if the given set of nodes span a conditional block (i.e. whether an #if is
	 * contained inside).
	 * @param ppStmts
	 * @return
	 */
	public static boolean nodesSpanRegions(IConditionalBlock rootBlock, IASTNode[] ppStmts) {
		if (ppStmts.length < 2)
			return false;
		
		IConditionalBlock firstBlock = null;
		for (IASTNode ppStmt : ppStmts) {
			IConditionalBlock block = findBlockContaining(rootBlock, ppStmt.getSourceRegion());
			if (firstBlock == null)
				firstBlock = block;
			else if (firstBlock != block)
				return true;
		}
		return false;
	}

}
