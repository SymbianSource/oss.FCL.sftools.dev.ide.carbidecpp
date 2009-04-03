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
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IMacroProvider;

import java.util.*;

/**
 * A class that handles expanding a list of ITokens and expanding macros along
 * the way.
 * 
 */
public class MacroExpandingStream implements Iterable<IToken> {


	private IMacroProvider macroProvider;
	private List<IToken> tokens;

	/**
	 * @param macros
	 * @param tokens
	 */
	public MacroExpandingStream(
			IMacroProvider macroProvider,
			List<IToken> tokens) {
		this.macroProvider = macroProvider;
		this.tokens = tokens;
	}

	public MacroExpandingTokenIterator iterator() {
		return new MacroExpandingTokenIterator(macroProvider, tokens);
	}
}
