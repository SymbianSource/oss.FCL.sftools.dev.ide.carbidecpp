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
 * This interface is used by IASTNode#rewrite() implementations
 * to tell how to construct the source text for a given node.
 *
 */
public interface IRewriteHandler {
	/** Emit literal text. */
	void emitText(String text);
	
	/** Emit at least one space. */
	void emitSpace();
	
	/** Emit a newline. */
	void emitNewline();
	
	/** Tell the rewriter that this is a good place to wrap lines. */
	void emitWrappingHint();
	
	/** Emit the rewritten contents of the given node. */
	void emitNode(IASTNode node);
	
	/** Enter or exit an indented block.  Indentation is applied to new lines only. */
	void indent(int amount);
}
