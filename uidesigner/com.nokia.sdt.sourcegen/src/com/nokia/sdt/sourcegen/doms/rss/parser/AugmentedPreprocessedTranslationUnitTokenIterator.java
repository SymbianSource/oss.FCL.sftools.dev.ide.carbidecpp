/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen.doms.rss.parser;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.PreprocessedTranslationUnitTokenIterator;

/**
 * This iterator notices the augmented include statements and the
 * presence of directive nodes in the stream.
 * 
 *
 */
public class AugmentedPreprocessedTranslationUnitTokenIterator extends
		PreprocessedTranslationUnitTokenIterator {

	private IAugmentedPreprocessorCallbacks callbacks;

	/**
	 * @param tu
	 */
	public AugmentedPreprocessedTranslationUnitTokenIterator(
			IASTTranslationUnit tu, IAugmentedPreprocessorCallbacks callbacks) {
		super(tu);
		this.callbacks = callbacks;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.PreprocessedTranslationUnitTokenIterator#onNewTopLevelNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode)
	 */
	@Override
	protected IASTTopLevelNode filterTopLevelNode(IASTTopLevelNode currentNode) {
		if (currentNode instanceof IASTPreprocessorTokenStreamStatement) {
			//System.out.println(currentNode);
			return currentNode;
		}

		if (currentNode instanceof AugmentedIncludeStatement) {
			AugmentedIncludeStatement incl = (AugmentedIncludeStatement) currentNode;
			callbacks.handleIncludeEntry(incl.getInclude(), incl.getPath());
			return null;
		}
		
		if (currentNode instanceof AugmentedIncludeExitStatement) {
			callbacks.handleIncludeExit();
			return null;
		}
		
		if (currentNode instanceof IASTProblemNode) {
			callbacks.handleProblem((IASTProblemTopLevelNode) currentNode);
			return null;
		}
		
		IASTPreprocessorStatement directive = (IASTPreprocessorStatement) currentNode;
		callbacks.handleDirective(directive);
		
		return null;
	}
}
