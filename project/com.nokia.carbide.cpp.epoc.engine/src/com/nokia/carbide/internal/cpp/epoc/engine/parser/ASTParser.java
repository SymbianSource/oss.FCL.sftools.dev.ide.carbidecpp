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

package com.nokia.carbide.internal.cpp.epoc.engine.parser;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IMacroProvider;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.ASTParserCore;

/**
 * A parser for preprocessor expressions.
 *
 */
public class ASTParser implements IPreprocessorExpressionParser {

	protected boolean hadErrors;
	
	public IASTPreprocessorExpression parse(IASTPreprocessorTokenStream tokenStream, IMacroProvider macroProvider) {
		ASTTokenManager tokenManager = new ASTTokenManager(tokenStream, macroProvider);
		ASTParserCore parser = new ASTParserCore(tokenManager);
		IASTPreprocessorExpression expr = parser.parse();
		hadErrors = parser.hadErrors;
		if (expr != null)
			expr.setDirtyTree(false);
		return expr;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.parser.IParser#hadErrors()
	 */
	public boolean hadErrors() {
		return hadErrors;
	}
}
