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
 * High-level information about a macro definition.
 *
 */
public interface IDefine {
	/** 
	 * Get the macro name (never null)
	 */
	String getName();
	
	/** 
	 * Get the macro argument names, or null for parameter-less macros (an empty
	 * array is returned for, e.g., #define FOO() ... )
	 */
	String[] getArgumentNames();
	
	/**
	 * Get the text in the macro expansion, excluding newline.  
	 * Never returns null.  An empty macro returns "".  Macros
	 * defined as if from a command line -DMACRO argument return "1".
	 */
	String getExpansion();
	
	/**
	 * Get the macro definition as text, as it would follow a '#define ' in C
	 * @return String form of macro defintion
	 */
	String getDefinitionText();
	
	/**
	 * Get the macro name with arguments appended, if a function macro.
	 * @return String in the form "macroName" or "macroName(arguments)"
	 */
	String getNameAndArguments();
}
