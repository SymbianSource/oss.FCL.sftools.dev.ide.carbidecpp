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

import java.util.List;

/**
 * 
 *
 */
public class InsertLinesPatchInstruction extends AbstractPatchInstruction {

	private List<String> newLines;
	private final boolean keepContext;

	/**
	 * Create instruction and pass modifiable list of lines to match.
	 * @param keepContext if true, other instructions had context, so insert in place,
	 * else, insert at EOF
	 */
	public InsertLinesPatchInstruction(List<String> newLines, boolean keepContext) {
		this.newLines = newLines;
		this.keepContext = keepContext;
	}

	/* (non-Javadoc)
	 * @see src.IPatchInstruction#match(java.util.List)
	 */
	public int match(ISourceGenPatch patch, int startLine, IPatchHandler handler) {
		// insert at beginning
		return startLine;
	}

	/* (non-Javadoc)
	 * @see src.IPatchInstruction#apply(int, java.util.List)
	 */
	public int apply(ISourceGenPatch patch, int index, IPatchHandler handler) {
		String[] lineArray = (String[]) newLines.toArray(new String[newLines.size()]);
		if (!keepContext) {
			index = handler.getEndLineNumber();
		}
		return handler.replaceLines(patch, index, index, lineArray);
	}

}
