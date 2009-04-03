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
 * Match a list of lines
 * 
 *
 */
public class MatchLinesPatchInstruction extends AbstractPatchInstruction {

	
	protected List<String> contextLines;

	/**
	 * Create instruction and pass modifiable list of lines to match.
	 */
	public MatchLinesPatchInstruction(List<String> contextLines) {
		this.contextLines = contextLines;
	}

	/**
	 * Try to match the context lines against the provided lines,
	 * skipping blank lines, starting from the given line.
	 * @return number of lines matched (0 == failed)
	 */
	protected int matchContext(IPatchHandler handler, int fromLine) {
		int matched = 0;
		int j = 0;
		while (j < contextLines.size()) {
			if (!handler.hasLine(fromLine))
				return 0;
			String line = handler.getLine(fromLine);
			Check.checkState(line != null);
			String ctxLine = contextLines.get(j);
			if (handler.matchLines(line, ctxLine)) {
				matched++;
				fromLine++;
				j++;
			} else if (handler.isLineEmpty(line) && !handler.isLineEmpty(ctxLine)) {
				fromLine++;
				matched++;
			} else {
				return 0;
			}
		}
		return matched;
	}

	/* (non-Javadoc)
	 * @see src.IPatchInstruction#match(java.util.List)
	 */
	public int match(ISourceGenPatch patch, int startLine, IPatchHandler handler) {
		int matched = matchContext(handler, startLine);
		if (matched != 0)
			return startLine + matched;
		return -1;
	}

	/* (non-Javadoc)
	 * @see src.IPatchInstruction#apply(int, java.util.List)
	 */
	public int apply(ISourceGenPatch patch, int index, IPatchHandler handler) {
		// next changes occur after the match
		return match(patch, index, handler);
	}

}
