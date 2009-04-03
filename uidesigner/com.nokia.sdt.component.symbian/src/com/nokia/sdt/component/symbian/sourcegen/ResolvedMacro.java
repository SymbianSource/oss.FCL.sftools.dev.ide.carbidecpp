/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.sdt.component.symbian.sourcegen;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.emf.component.DefineMacroType;
import com.nokia.sdt.emf.component.MacroArgumentType;

import java.util.Map;
import java.util.Set;

/** A resolved macro where importArguments and macroArguments are merged */
public class ResolvedMacro {
	/** Create the macro and store references to the maps (which may
	 * later be modifed)
	 * @param component
	 * @param type
	 * @param arguments
	 * @param argumentOrigins
	 * @param includedMacros 
	 */
	public ResolvedMacro(IComponent component, DefineMacroType type, 
			Map<String, MacroArgumentType> arguments,
			Map<String, ResolvedMacro> argumentOrigins, Set<ResolvedMacro> includedMacros) {
		this.component = component;
		this.macro = type;
		this.arguments = arguments;
		this.argumentOrigins = argumentOrigins;
		this.includedMacros = includedMacros;
	}
	public IComponent component;
	public DefineMacroType macro;
	public Map<String, MacroArgumentType> arguments;
	public Map<String, ResolvedMacro> argumentOrigins;
	public Set<ResolvedMacro> includedMacros;
}