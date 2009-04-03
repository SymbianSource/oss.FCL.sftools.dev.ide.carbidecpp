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

package com.nokia.carbide.internal.cpp.epoc.engine.dom.rewriter;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;


public class DocumentUpdater {

	/**
	 * Update a node's documents.  Usually initialized with an IASTTranslationUnit.
	 * <p>
	 * The node must have a document, but needn't have a region.
	 * <p> 
	 * Subnodes in the tree which refer to different documents are allowed
	 * (e.g. for #includes).
	 * @param node
	 * @return true if changes made
	 */
	public static boolean updateDocuments(IPreprocessorResults ppResults, IASTNode node) {
		if (!node.hasDirtySourceTree())
			return false;

		boolean changed = false;
		ISourceRegion region = node.getSourceRegion();
		for (IDocumentSourceRegion docRegion : region.getDocumentSourceRegions()) {
			changed |= updateDocumentSourceRegion(ppResults, node, docRegion);
		}
		return changed;
	}

	private static boolean updateDocumentSourceRegion(IPreprocessorResults ppResults, IASTNode node, IDocumentSourceRegion docRegion) {
		DocumentNodeUpdater updater = new DocumentNodeUpdater(ppResults, docRegion.getDocument());
		node.rewrite(updater);
		
		String newText = updater.toString();
		
		String oldText = docRegion.getDocument().get();
		if (newText.equals(oldText)) 
			return false;
		
		// don't rewrite file with only a change in newlines:
		// we try to ensure a final newline when rewriting,
		// but we don't want to cause spurious changes.
		if (newText.startsWith(oldText) && newText.substring(oldText.length()).matches("\\s+")) //$NON-NLS-1$ 
			return false;
		
		docRegion.getDocument().set(newText);
		return true;
	}
}
