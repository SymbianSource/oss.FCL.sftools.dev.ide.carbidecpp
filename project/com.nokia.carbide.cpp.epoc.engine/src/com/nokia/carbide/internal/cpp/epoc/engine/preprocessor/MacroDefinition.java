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

package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;

public class MacroDefinition extends Define implements IDefine {

	/**
	 * @param macroName
	 * @param macroArgs
	 * @param expansion
	 */
	public MacroDefinition(String macroName, String[] macroArgs,
			String expansion) {
		super(macroName, macroArgs, expansion);
	}

	public static MacroDefinition createMacroDefinition(IASTPreprocessorDefineStatement define) {
		String name = define.getMacroName().getValue();
		String[] arguments;
		IASTListNode<IASTLiteralTextNode> args = define.getMacroArgs();
		if (args == null) {
			arguments = null;
		} else {
			arguments = new String[args.size()];
			int idx = 0;
			for (IASTLiteralTextNode node : args) {
				arguments[idx++] = node.getValue();
			}
		}
		String expansion = null;
		IASTPreprocessorTokenStream macroExpansion = define.getMacroExpansion();
		expansion = macroExpansion != null ? macroExpansion.getNewText().trim() : "1"; //$NON-NLS-1$
		
		return new MacroDefinition(name, arguments, expansion);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.Define#toString()
	 */
	@Override
	public String toString() {
		return "#define " + getDefinitionText(); //$NON-NLS-1$
	}
	
}
