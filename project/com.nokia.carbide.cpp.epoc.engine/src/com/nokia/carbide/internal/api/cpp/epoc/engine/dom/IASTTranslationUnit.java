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

import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnit;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

/**
 * Top-level unit for a file's contents.
 * <p>
 * A translation unit's top-level nodes comprise the entirety of the
 * source being managed. 
 *
 */
public interface IASTTranslationUnit extends IASTNode, ITranslationUnit {

	/**
	 * Get the main document for the translation unit.
	 * @return IDocument or null
	 */
	IDocument getMainDocument();
	/**
	 * Set the main document for the translation unit.
	 * @param document IDocument or null
	 */
	void setMainDocument(IDocument document);
	/**
	 * Get the main location (full path) to the translation unit.
	 * @return IPath or null
	 */
	IPath getMainLocation();
	/**
	 * Set the main location (full path) to the translation unit.
	 * @param location IPath or null
	 */
	void setMainLocation(IPath location);
	
	/** Get modifiable list of top-level nodes */
	IASTListNode<IASTTopLevelNode> getNodes();
	void setNodes(IASTListNode<IASTTopLevelNode> nodes);
	
	void addListener(ITranslationUnitListener listener);
	void removeListener(ITranslationUnitListener listener);

	/**
	 * Fire a node change event.
	 * @param node
	 */
	void fireNodeChanged(IASTNode node);
	/**
	 * Fire a literal text expression change event.
	 * @param node
	 */
	void fireLiteralTextNodeChanged(IASTLiteralTextNode node, String oldText, String newText);
	/**
	 * Fire a list node added event.
	 * @param node
	 */
	void fireListNodeAdded(IASTListNode<? extends IASTNode> list, IASTNode node, int index);
	/**
	 * Fire a list node removed event.
	 * @param node
	 */
	void fireListNodeRemoved(IASTListNode<? extends IASTNode> list, IASTNode node, int index);
	
	/**
	 * Recursively find statements of the given type
	 * @return return array (never null); always of IASTStatement[] type
	 */
	IASTStatement[] findStatementsOfType(Class<? extends IASTStatement> klazz);
	/**
	 * Recursively find first statement of the given type
	 * @param klazz
	 * @return first statement or null; always of IASTStatement[] type
	 */
	IASTStatement findFirstStatementOfType(Class<? extends IASTStatement> klazz);
	/**
	 * Recursively find the first statement with the given keyword pattern
	 * @param keywordPattern
	 * @return array (never null); always of IASTStatement[] type
	 */
	IASTStatement findFirstStatementOfType(String keywordPattern);
	/**
	 * Recursively find statements with the given keyword pattern
	 * @param keywordPattern
	 * @return array (never null); always of IASTStatement[] type
	 */
	IASTStatement[] findStatementsOfType(String keywordPattern);
	
}
