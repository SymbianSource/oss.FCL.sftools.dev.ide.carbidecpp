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

import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.Define;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Factory providing instances of IDefine.
 *
 */
public abstract class DefineFactory {
	private static Pattern MACRO_SPLIT_PATTERN = Pattern.compile("\\s*([A-Za-z0-9_]+)\\s*(=?)\\s*(.*)"); //$NON-NLS-1$

	/**
	 * Create a new argument-less macro definition whose expansion is implicitly "1"
	 * @param macroName macro name, e.g. "MYMAC"
	 * @return new IDefine
	 * @throws IllegalArgumentException if macro name is null or invalid 
	 */
	public static IDefine createDefine(String macroName) {
		return new Define(macroName, null, null);
	}
	
	/**
	 * Create a new argument-less macro definition with the given expansion.  No newlines
	 * should be in the expansion.
	 * @param macroName macro name, e.g. "MYMAC"
	 * @param macroArgs list of argument names, e.g. { "a", "b" }
	 * @param expansion value of macro; may be null to explicitly expand to "1"
	 * @return new IDefine
	 * @throws IllegalArgumentException if macro name is null or invalid 
	 */
	public static IDefine createDefine(String macroName, String expansion) {
		return new Define(macroName, null, expansion);
	}
	
	/**
	 * Create a function-like macro definition with the given argument
	 * names and expansion.
	 * @param macroName macro name, e.g. "MYMAC"
	 * @param macroArgs list of argument names, e.g. { "a", "b" }
	 * @param expansion value of macro; may be null to explicitly expand to "1" 
	 * @return new IDefine
	 * @throws IllegalArgumentException if macro name is null or invalid 
	 */
	public static IDefine createDefine(String macroName, String[] macroArgs, String expansion) {
		return new Define(macroName, macroArgs, expansion);
	}

	/**
	 * Create a define from an argument-less macro definition, which may include an "=" or a space
	 * and an expansion.  Extra whitespace is trimmed.  If no value is provided, the macro
	 * has an implicit value of "1".
	 * @param macro string in the form "FOO=value", "FOO value", or "MACRO"
	 * @return new IDefine
	 * @throws IllegalArgumentException if macro name is null or invalid 
	 */
	public static IDefine createSimpleFreeformDefine(String macro) {
		// if it has an expansion then it will be separated by an = or space char
		Matcher matcher = MACRO_SPLIT_PATTERN.matcher(macro);
		if (matcher.matches()) {
			// check whether = is present
			if (matcher.group(2).length() > 0)
				return createDefine(matcher.group(1), matcher.group(3).trim());
			// check whether we just have a bare macro with extra spaces
			else if (matcher.group(3).length() > 0) 
				return createDefine(matcher.group(1), matcher.group(3).trim());
		}
		return createDefine(macro.trim());
	}
	
	
}
