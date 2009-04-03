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

package com.nokia.carbide.cpp.epoc.engine.preprocessor;

/**
 * This interface, implemented by a client or by a standard implementation, 
 * is used to determine how to interpret preprocessor conditionals and macros 
 * when determining what content to expose from a preprocessor DOM.  
 * 
 * @see com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration
 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessor
 * 
 */
public interface IViewFilter {
	/**
	 * if true, then nodes outside #if/etc are kept, else, they are skipped
	 * entirely
	 * 
	 * @return
	 */
	boolean evaluateUnconditionalStatements();

	/**
	 * if true, use macro values to evaluate #if/etc nodes and include the
	 * contents in the succeeding branch; else, conditionals are skipped
	 * entirely
	 * 
	 * @return
	 */
	boolean evaluateConditionalStatements();

	/**
	 * when #evaluteConditionals() returns true, then, if true, filter out
	 * contents from successful tests and keep contents from failing tests
	 * 
	 * @return
	 */
	boolean invertSuccess();

	/**
	 * when #evaluteConditionals() returns true, then if true, then both true
	 * and false branches of a test are included.
	 * 
	 * @return
	 */
	boolean combineBranches();

	/**
	 * • variant macro: a macro whose value is determined to differ between
	 * configurations, either by coming from a set of fixed macros or by being
	 * defined inside #ifs in the translation unit
	 * <p> 
	 * • if true, expand variant
	 * macros when encountered in macro expressions and statements; else,
	 * statements containing variant macros will be filtered out. (Macros in #if
	 * expressions are outside the scope of this query.)
	 * 
	 * @return
	 */
	boolean expandVariantMacros();

}
