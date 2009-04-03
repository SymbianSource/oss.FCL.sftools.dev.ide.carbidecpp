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
import com.nokia.cpp.internal.api.utils.core.Pair;

import java.util.List;

/**
 * Instruction that replaces one set of lines with another.
 * 
 *
 */
public class ReplaceLinesPatchInstruction extends MatchLinesPatchInstruction {

	private List<String> newLines;

	/**
	 * Create instruction and pass modifiable list of lines to match.
	 */
	public ReplaceLinesPatchInstruction(List<String> contextLines, List<String> newLines) {
		super(contextLines);
		this.newLines = newLines;
	}

	/* (non-Javadoc)
	 * @see src.IPatchInstruction#apply(int, java.util.List)
	 */
	public int apply(ISourceGenPatch patch, int index, IPatchHandler handler) {
		Pair<Integer, Integer> range = DeleteLinesPatchInstruction.getAppliedLineRange(contextLines, index, handler);
		String[] lineArray = null;
		if (newLines.size() != 0)
			lineArray = (String[]) newLines.toArray(new String[newLines.size()]);
		return handler.replaceLines(patch, range.first, range.second, lineArray);
	}

}
