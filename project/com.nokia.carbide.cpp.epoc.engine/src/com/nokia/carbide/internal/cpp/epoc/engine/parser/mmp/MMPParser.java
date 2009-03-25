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

package com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.BaseASTParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.MMPParserCore;
import com.nokia.cpp.internal.api.utils.core.*;

/**
 * This is a parser for MMP. 
 *
 */
public class MMPParser extends BaseASTParser {

	private IMMPParserConfiguration config;
	
	/**
	 * @param config
	 */
	public MMPParser(IMMPParserConfiguration config) {
		Check.checkArg(config);
		this.config = config;
	}

	public IASTTranslationUnit parse(IPreprocessorResults preprocessorResults) {
		MMPTokenManager tokenManager = new MMPTokenManager(config, preprocessorResults);
		MMPParserCore parser = new MMPParserCore(tokenManager);
		IASTMMPTranslationUnit mmpTu = parser.parse();

		hadErrors = parser.hadErrors() || mmpTu == null;
		
		finalizeTu(preprocessorResults.getFilteredTranslationUnit(), mmpTu);
		return mmpTu;
	}

}
