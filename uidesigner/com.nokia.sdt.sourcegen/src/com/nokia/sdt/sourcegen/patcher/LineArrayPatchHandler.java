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
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.List;

/**
 * This implements IPatchHandler by modifying an array of lines.
 * 
 *
 */
public class LineArrayPatchHandler extends BaseSourceGenPatchHandler {

	protected List<String> currentLines;

	public LineArrayPatchHandler(List<String> currentLines) {
		this.currentLines = currentLines;
	}
	
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
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#hasLine(int)
	 */
	public boolean hasLine(int index) {
		return index < currentLines.size();
	}
	
	public String getLine(int index) {
		if (index >= currentLines.size())
			return null;
		return currentLines.get(index);
	}
	
	public int getEndLineNumber() {
		return currentLines.size();
	}

	public int replaceLines(ISourceGenPatch patch, int fromIndex, int toIndex, String[] lines) {
		Check.checkArg(fromIndex <= toIndex && toIndex <= currentLines.size());
		int index = fromIndex;
		while (fromIndex < toIndex) {
			currentLines.remove(index);
			fromIndex++;
		}
		if (lines != null) {
			for (String line : lines) {
				currentLines.add(index, line);
				index++;
			}
			// next changes occur after these lines
			return index;
		} else {
			return index;
		}
	}
		
	
}