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

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterate over the tokens visible in a preprocessed translation unit.
 * This expects that every statement in the TU is a IASTPreprocessorTokenStreamStatement
 * or an IASTProblemStatement.
 * <p>
 * This is not an Iterator because we want to allow subclasses to override
 * {@link #filterTopLevelNode(IASTTopLevelNode)}, which may have side effects.
 * A classic Iterator needs to expect calls to #hasNext() at any time, 
 * and this filter function would need to be called there, where
 * such side effects may come too early.
 *
 */
public class PreprocessedTranslationUnitTokenIterator {

	Iterator<IASTTopLevelNode> nodeIter;
	Iterator<IToken> currentIter;
	private IASTTopLevelNode currentNode;
	protected IToken lastToken;
	
	public PreprocessedTranslationUnitTokenIterator(IASTTranslationUnit tu) {
		this.nodeIter = tu.getNodes().iterator();
	}
	
	/**
	 * Override to filter the next current node.  This should either return the same
	 * node or return <code>null</code> to ignore it.  
	 * @param node 
	 * @return {@link IASTTopLevelNode} the same input node, or <code>null</code> to skip
	 */
	protected IASTTopLevelNode filterTopLevelNode(IASTTopLevelNode node) {
		return node;
	}
	
	/**
	 * Find the next token stream statement and get its token iterator.
	 */
	private void nextTokenStream() {
		currentIter = null;
		while (nodeIter.hasNext()) {
			IASTTopLevelNode nextNode = nodeIter.next();

			currentNode = filterTopLevelNode(nextNode);
			if (!(currentNode instanceof IASTPreprocessorTokenStreamStatement))
				continue;
			
			// don't use the statement unless its token stream is non-empty
			Iterator<IToken> iter = ((IASTPreprocessorTokenStreamStatement) currentNode)
				.getTokenStream().getTokens().iterator();
			if (iter.hasNext()) {
				//System.out.println("stmt: " + currentNode);
				currentIter = iter;
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	/*
	public boolean hasNext() {
		if (currentIter != null && currentIter.hasNext())
			return true;
		nextTokenStream();
		return currentIter != null;
	}
	*/

	/**
	 * Override to inject another IToken before the current token, if needed.
	 * @return IToken or <code>null</code>
	 */
	protected IToken injectToken() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public IToken next() {
		
		while (currentIter == null) {
			nextTokenStream();
			if (currentIter == null)
				throw new NoSuchElementException();
		}
		
		IToken token = injectToken();
		if (token != null)
			return token;
		
		lastToken = currentIter.next();
		if (!currentIter.hasNext()) {
			currentIter = null;
		}
		return lastToken;
	}

	/**
	 * Swap the current line with the unpreprocessed one.  This
	 * removes consumedTokens tokens from that line as well.
	 * @param ppResults
	 * @param consumedTokens
	 */
	public void swapLineForUnpreprocessedLine(IPreprocessorResults ppResults, int consumedTokens) {
		// eat everything in the current line
		IASTNode[] nodes = ASTUtils.getOriginalSourceNodes(currentNode); 
		if (nodes == null || nodes.length > 1 || !(nodes[0] instanceof IASTPreprocessorTokenStreamStatement)) {
			EpocEnginePlugin.log(new IllegalArgumentException("Could not re-parse "+currentNode)); //$NON-NLS-1$
			return;
		}
		
		// ignore the fact that the current line may have macro expansion
		ppResults.unsetNodeUsesMacros(nodes[0]);
		
		// eat up current line (in case anyone looks at it again)
		if (currentIter != null) {
			while (currentIter.hasNext())
				currentIter.next();
		}
		
		// replace the iterator with the unpreprocessed one (note: may now itself be a macro expansion -- oh well)
		currentIter = ((IASTPreprocessorTokenStreamStatement) nodes[0]).getTokenStream().getTokens().iterator();
		while (consumedTokens > 0 && currentIter.hasNext()) {
			currentIter.next();
			consumedTokens--;
		}
	}

}
