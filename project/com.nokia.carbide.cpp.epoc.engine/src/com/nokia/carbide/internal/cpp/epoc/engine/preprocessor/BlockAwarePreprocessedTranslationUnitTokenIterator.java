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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.Token;

import java.util.Stack;

/**
 * Iterate over the tokens visible in a preprocessed translation unit, reporting 
 * IToken.BLOCK_xxx tokens when #if/#else/#elif/#endif blocks are traversed.
 *
 */
public class BlockAwarePreprocessedTranslationUnitTokenIterator extends PreprocessedTranslationUnitTokenIterator {

	private IPreprocessorResults ppResults;
	private IConditionalBlock block;
	private IConditionalBlock prevBlock;
	private Stack<IConditionalBlock> blockStack;
	private int prevBlockDepth;
	private boolean checkBlockDepth;
	
	public BlockAwarePreprocessedTranslationUnitTokenIterator(IPreprocessorResults ppResults, IASTTranslationUnit tu) {
		super(tu);
		this.ppResults = ppResults;
		block = ppResults.getRootBlock();
		prevBlock = block;
		blockStack = new Stack<IConditionalBlock>();
		blockStack.push(block);
		checkBlockDepth = true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.PreprocessedTranslationUnitTokenIterator#onNewTopLevelNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode)
	 */
	@Override
	protected IASTTopLevelNode filterTopLevelNode(IASTTopLevelNode currentNode) {
		block = this.ppResults.getNodeToBlockMap().get(currentNode);
		checkBlockDepth = true;
		return currentNode;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.PreprocessedTranslationUnitTokenIterator#injectToken()
	 */
	@Override
	protected IToken injectToken() {
		if (checkBlockDepth) {
			checkBlockDepth = false;
			int blockDepth = block.getIfDepth();
			// report entering/exiting "#if" OR an "#else"/"#elif" 
			if ((blockDepth != prevBlockDepth) || (block != prevBlock 
							&& block.getFirstDocument() == prevBlock.getFirstDocument()
							&& (block.getCondition() instanceof IASTPreprocessorElseStatement
									|| block.getCondition() instanceof IASTPreprocessorElifStatement))) {
				//System.out.println("switching block from " + prevBlock + "@" + prevBlockDepth
				//		+ " to " + block + "@" + blockDepth);
				Token blockToken;
				if (blockDepth < prevBlockDepth) {
					prevBlock = block;
					prevBlockDepth = blockDepth;
					blockToken = new Token(IToken.BLOCK_EXIT, "", false, true); //$NON-NLS-1$
				}
				else if (blockDepth > prevBlockDepth) {
					prevBlock = block;
					prevBlockDepth = blockDepth;
					blockToken = new Token(IToken.BLOCK_ENTER, "", false, true); //$NON-NLS-1$
				}
				else {
					prevBlock = block;
					prevBlockDepth = blockDepth;
					blockToken = new Token(IToken.BLOCK_SWITCH, "", false, true); //$NON-NLS-1$
				}
				if (lastToken != null)
					blockToken.setLocationAndOffset(lastToken.getLocation(), lastToken.getOffset());
				return blockToken;
			}
		}
		return null;
	}

	/**
	 * Get the last conditional block containing the statement returned by #next().
	 * @return
	 */

	public IConditionalBlock getLastBlock() {
		return block;
	}
}
