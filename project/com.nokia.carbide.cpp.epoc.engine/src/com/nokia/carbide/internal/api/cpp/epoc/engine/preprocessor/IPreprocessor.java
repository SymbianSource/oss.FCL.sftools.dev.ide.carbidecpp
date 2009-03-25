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

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;

import java.util.Collection;

/**
 * An interface to a preprocessor.
 *
 */
public interface IPreprocessor {
	/**
	 * Provide a filter for the nodes handled by the preprocessor.
	 * @param filter IPreprocessorFilter
	 */
	void setFilter(IPreprocessorFilter filter);
	
	/**
	 * Preprocess a translation unit, evaluating macros and conditionals
	 * to return a filtered view of the file. 
	 * @param ppTu the preprocessor-level TU (from IParser#parse)
	 * @param viewFilter filter for interpreting #ifdef structure
	 * @param macros predefined macros; not modified during parse
	 * @return preprocessing results
	 */
	IPreprocessorResults preprocess(IASTTranslationUnit ppTu, 
			IViewFilter viewFilter, Collection<IDefine> defines);
}
