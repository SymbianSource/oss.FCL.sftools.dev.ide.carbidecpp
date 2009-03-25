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

package com.nokia.sdt.sourcegen.patcher;

import java.util.regex.Pattern;

/**
 * This implements a subset of IPatchHandler which canonicalizes 
 * lines for comparison. 
 * 
 *
 */
public abstract class BaseSourceGenPatchHandler implements IPatchHandler {
	protected static Pattern WHITESPACE_REMOVAL = Pattern.compile("\\s+"); //$NON-NLS-1$

	/**
	 * Routine to get a line in the canonical format for determining
	 * if it is empty or can match another line in the same format.
	 * Override in subclasses if needed.
	 * @param line
	 * @return line with whitespace removed
	 */
	protected String removeAllWhitespace(String line) {
		return WHITESPACE_REMOVAL.matcher(line).replaceAll(""); //$NON-NLS-1$  
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#isLineEmpty(java.lang.String)
	 */
	final public boolean isLineEmpty(String srcLine) {
		return removeAllWhitespace(srcLine).length() == 0;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#matchLines(java.lang.String, java.lang.String)
	 */
	final public boolean matchLines(String from, String to) {
		// we match with no whitespace comparison
		String from0 = removeAllWhitespace(from); //$NON-NLS-1$  
		String to0 = removeAllWhitespace(to); //$NON-NLS-1$  
		if (from0.length() > 0 && to0.length() > 0) {
			return from0.equals(to0);
		} else {
			// must be comments, so just delete surrounding whitespace
			return from.trim().equals(to.trim());
		}
	}
	
}
