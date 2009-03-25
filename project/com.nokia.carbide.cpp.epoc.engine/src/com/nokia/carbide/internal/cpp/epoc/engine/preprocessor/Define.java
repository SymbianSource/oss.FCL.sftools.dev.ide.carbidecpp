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
package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.cpp.internal.api.utils.core.*;

public class Define implements IDefine {

	private final String macroName;
	private final String[] macroArgs;
	private final String expansion;

	public Define(String macroName, String[] macroArgs, String expansion) {
		Check.checkArg(macroName);
		Check.checkArg(TextUtils.isLegalIdentifier(macroName));
		this.macroName = macroName;
		this.macroArgs = macroArgs;
		this.expansion = expansion != null ? expansion : "1"; //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Define))
			return false;
		Define other = (Define) obj;
		if (!(other.macroName.equals(macroName) && other.expansion.equals(expansion)))
			return false;
		if ((macroArgs != null) != (other.macroArgs != null))
			return false;
		if (macroArgs == null)
			return true;
		if (macroArgs.length != other.macroArgs.length)
			return false;
		for (int i = 0; i < macroArgs.length; i++) {
			if (!macroArgs[i].equals(other.macroArgs[i]))
				return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int code = 38228302;
		code ^= macroName.hashCode() ^ (expansion != null ? expansion.hashCode() : 0);
		if (macroArgs != null) {
			for (String arg : macroArgs) {
				code = (code ^ arg.hashCode() << 1) ^ 3921; 
			}
		}
		return code;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "#define " + getDefinitionText(); //$NON-NLS-1$
	}
	
	public String getDefinitionText() {
		if (expansion != null && expansion.length() > 0) {
			return getNameAndArguments() + " " + expansion; //$NON-NLS-1$
		} else {
			return getNameAndArguments(); 
		}
	}
	
	public String[] getArgumentNames() {
		return macroArgs;
	}

	public String getExpansion() {
		return expansion;
	}

	public String getName() {
		return macroName;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine#getNameAndArguments()
	 */
	public String getNameAndArguments() {
		if (macroArgs == null)
			return macroName;
		return macroName + "(" + TextUtils.catenateStrings(macroArgs, ",") + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
}
