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

import java.util.List;

/**
 * This node represents C/C++ preprocessor tokens analyzed
 * from raw text.  It is expected to be expanded with macro
 * substitution and used as input for a language-specific parser.
 *
 */
public interface IASTPreprocessorTokenStream extends IASTNode {
	/** Get the list of tokens (modifiable) */
	List<IToken> getTokens();
	
	/** Set the list of tokens */
	void setTokens(List<IToken> tokens);
}
