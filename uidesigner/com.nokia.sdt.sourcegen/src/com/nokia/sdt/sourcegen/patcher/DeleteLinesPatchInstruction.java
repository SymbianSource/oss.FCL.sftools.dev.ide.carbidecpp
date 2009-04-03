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
import com.nokia.cpp.internal.api.utils.core.Pair;

import java.util.List;

/**
 * 
 *
 */
public class DeleteLinesPatchInstruction extends MatchLinesPatchInstruction {

	/**
	 */
	public DeleteLinesPatchInstruction(List<String> removedLines) {
		super(removedLines);
	}

	/* (non-Javadoc)
	 * @see src.IPatchInstruction#apply(int, java.util.List)
	 */
	public int apply(ISourceGenPatch patch, int index, IPatchHandler handler) {
		Pair<Integer, Integer> info = getAppliedLineRange(contextLines, index, handler);
		return handler.replaceLines(patch, info.first, info.second, null);
	}
	
	static Pair<Integer, Integer> getAppliedLineRange(List<String> contextLines, int index, IPatchHandler handler) {
		// remove the lines at this index
		int i = 0;
		int lastIndex = index;
		while (i < contextLines.size()) {
			Check.checkState(handler.hasLine(index));
			String line = handler.getLine(index);
			Check.checkState(line != null);
			String ctxLine = contextLines.get(i);
			if (handler.matchLines(line, ctxLine)) {
				index++;
				i++;
			} else if (handler.isLineEmpty(line) && !handler.isLineEmpty(ctxLine)) {
				// skip blank lines in the source between context lines,
				// but not blank lines before the context lines
				if (lastIndex == index)
					lastIndex++;
				index++;
				continue;
			} else {
				Check.checkState(false);
			}
		}
		return new Pair(lastIndex, index);
	}

}
