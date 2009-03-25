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

package com.nokia.carbide.cpp.epoc.engine.model.makefile;

import org.eclipse.cdt.make.core.makefile.*;

/**
 * This is the interface to reading and modifying the Makefile contents.
 * <p>
 * This is radically simplified and stupid.  CDT's IMakefile doesn't provide write
 * access, so instead of implementing a parallel layer, we expose only
 * operations that query the model or make simple text-based operations.
 * Changes are made to the backing text and the model is reparsed.
 * Obviously, this is meant only for occasional operations. 
 *
 */
public interface IMakefileViewBase {

	/** Get CDT representation of makefile, with read-only access. */
	IMakefile getMakefile(); 
	
	/**
	 * Find all the macro definitions, recursively.
	 */
	IMacroDefinition[] getAllMacroDefinitions();

	/**
	 * Find all the macro definitions for the given name, recursively.
	 * 
	 */
	IMacroDefinition[] getAllMacroDefinitions(String name);

	/**
	 * Expand macro definition references in string.  This searches 
	 * for any applicable macro, even those in conditionals.
	 * @param text
	 * @return string with macros expanded -- other shell commands will be left
	 */
	String expandAllMacrosInString(String text);

	/**
	 * Expand macro definition references in string.  This searches 
	 * for any applicable macro, even those in conditionals.
	 * Additionally, it replaces references to "$&lt;" with the target
	 * and "$^" and "$@" with the appropriate sources.
	 * @param target
	 * @param rule
	 * @return
	 */
	String expandAllMacrosInRuleString(String target, ITargetRule rule);

	/**
	 * Try to replace literal text in the given string with macros.
	 * <p>
	 * If exhaustive is true, then conditionalized macros in the Makefile are replaced too.
	 */
	String unexpandMacros(String text, boolean exhaustive);
	
	/**
	 * Get (last) rule with the given target
	 * @param target filename or filepath
	 * @param exactMatch if true, verify same filepath; else, just check filename
	 * @return last matching rule, or null
	 */
	ITargetRule findRuleForTarget(String target, boolean exactMatch);
	
	/**
	 * Get commands in any rule that run this program (either literal filename or $(var))
	 * @param program
	 * @return
	 */
	ICommand[] findCommandsInvoking(String program);

	/**
	 * Get end-of-line terminator used in the makefile.
	 */
	String getEOL();
	
	/**
	 * Append new text to the file.
	 * <p>Note: this invalidates any directives read from the model.
	 * @param text text to add (add #getEOL() if a full line)
	 */
	void appendText(String text);
	
	/**
	 * Insert a new line after the given directive.
	 * <p>Note: this invalidates any directives read from the model.
	 * @param directive directive, or null for start of file
	 * @param text text to add (add #getEOL() if a full line)
	 */
	void insertText(IDirective directive, String text);
	
	/**
	 * Insert a new line before the given directive.<p>
	 * Note: this invalidates any directives read from the model.
	 * @param text text to add (add #getEOL() if a full line)
	 * @param directive directive, or null for end of file
	 */
	void insertTextBefore(String text, IDirective directive);

	/**
	 * Delete a directive and any children.  
	 * <p>Note: this invalidates any directives read from the model.
	 * directives read from the model.
	 */
	void deleteDirective(IDirective directive);
	
	/**
	 * Replace a directive with alternate text.
	 * <p>Note: this invalidates any directives read from the model.
	 */
	void replaceDirective(IDirective directive, String text);
	
	/**
	 * Try to replace literal text in the given string with the given macros.
	 */
	String unexpandMacros(String text, String[] macroNames);
	
	
}
