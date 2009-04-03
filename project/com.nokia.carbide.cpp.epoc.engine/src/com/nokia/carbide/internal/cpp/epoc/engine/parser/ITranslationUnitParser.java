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

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;

/**
 * Interface to a translation unit parser. <p>
 * This takes one translation unit and converts it into a language translation unit.<p>
 * It is usually used with a translation unit constructed initially from an
 * {@link IDocumentParser} implementation.
 * @see com.nokia.carbide.cpp.epoc.engine.parser.IDocumentParser
 *
 */
public interface ITranslationUnitParser {
	/**
	 * Take the given preprocessed translation unit (only IASTPreprocessorTokenStreamStatement) and
	 * convert it into the given language
	 * @param preprocessorResults the preprocessor that created the preprocessed tu 
	 * @return new translation unit
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessor
	 */
	IASTTranslationUnit parse(IPreprocessorResults preprocessorResults);

	/**
	 * After a parse, tell if errors detected (problems
	 * indicated by IASTProblem nodes)
	 * @return
	 */
	boolean hadErrors();
}
