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

package com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement;

/**
 * Provider of macro definitions, used while expanding macros.
 *
 */
public interface IMacroProvider {
	/** 
	 * Get a #define which provides the definition of the macro.
	 * 
	 * @param name macro name
	 * @return #define or null
	 */
	IASTPreprocessorDefineStatement lookupMacro(String name);
}
