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

package com.nokia.carbide.internal.cpp.epoc.engine.parser.bldinf;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.IASTBldInfTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.BaseASTParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.generated.BldInfParserCore;

/**
 * This is a parser for BldInf. 
 *
 */
public class BldInfParser extends BaseASTParser {
	
	/**
	 * @param config
	 */
	public BldInfParser() {
	}

	public IASTTranslationUnit parse(IPreprocessorResults preprocessorResults) {
		BldInfTokenManager tokenManager = new BldInfTokenManager(preprocessorResults);
		BldInfParserCore parser = new BldInfParserCore(tokenManager);
		IASTBldInfTranslationUnit bldInfTu = parser.parse();
		hadErrors = parser.hadErrors() || bldInfTu == null;
		
		finalizeTu(preprocessorResults.getFilteredTranslationUnit(), bldInfTu);
		
		return bldInfTu;
	}


}
