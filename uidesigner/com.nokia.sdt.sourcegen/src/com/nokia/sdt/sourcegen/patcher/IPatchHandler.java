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

import com.nokia.sdt.sourcegen.ISourceGenPatch;

/**
 * Interface used to abstract away access to applying the changes
 * from a patch.
 * 
 *
 */
public interface IPatchHandler {
	/**
	 * Tell whether the line is considered empty (possibly after
	 * whitespace is removed).
	 * @param srcLine
	 * @return
	 */
	boolean isLineEmpty(String srcLine);
	
	/**
	 * Match the two lines.  This may involve massaging the source
	 * so that 
	 */
	boolean matchLines(String srcLine, String patchLine);
	
	/**
	 * Tell whether the given line index exists.
	 * @param index zero-based index
	 * @return true if line exists 
	 */
	boolean hasLine(int index);
	
	/** 
	 * Retrieve a line.
	 * Line indices reference the current text including any modifications.
	 * @param index zero-based index
	 * @return line text or null if no such line
	 */
	String getLine(int index);
	
	/**
	 * Replace a span of lines with the given lines.  Line numbers
	 * reference the current text including any modifications.<p>
	 * Insert: replaceLines(N, N, lines)<br>
	 * Delete: replaceLines(N, M, null)<br>
	 * Replace: replaceLines(N, M, lines)
	 * @param patch the patch
	 * @param fromIndex the first zero-based line index to replace (inclusive)
	 * @param toIndex the last zero-based line index to replace (exclusive)
	 * @param lines the lines to replace (or null); may be modified
	 * @return new line number for further matches/context (depends on handler impl)
	 */
	int replaceLines(ISourceGenPatch patch, int fromIndex, int toIndex, String[] lines);

	/**
	 * Get the line number for the end of file.
	 * @return last line number + 1 ( ==  number of lines )
	 */
	int getEndLineNumber();
}
