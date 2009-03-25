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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import com.nokia.sdt.sourcegen.ISourceGenPatch;
import com.nokia.sdt.sourcegen.patcher.LineArrayPatchHandler;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.ArrayList;
import java.util.List;

/**
 * This handler tracks the lines for a file as a whole, onto which
 * LineRangePatchHandlers provide a view.  
 * <p>
 * This handler ignores comments when matching lines.
 * 
 *
 */
public class FileLinePatchHandler extends LineArrayPatchHandler {

	private List<LineRangePatchHandler> lineRangePatchHandlers;
	CppDomain domain;

	/**
	 * @param eol 
	 * 
	 */
	public FileLinePatchHandler(List<String> lines, CppDomain domain) {
		super(lines);
		this.domain = domain;
		this.lineRangePatchHandlers = new ArrayList<LineRangePatchHandler>();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.LineArrayPatchHandler#removeAllWhitespace(java.lang.String)
	 */
	@Override
	protected String removeAllWhitespace(String line) {
		line = CdtUtils.stripComments(line).toString();
		return super.removeAllWhitespace(line);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.LineArrayPatchHandler#replaceLines(int, int, java.lang.String[])
	 */
	public int replaceLines(ISourceGenPatch patch, int fromIndex, int toIndex, String[] lines) {
		int newLine = super.replaceLines(patch, fromIndex, toIndex, lines);
		
		// notify handlers if their ranges shifted
		for (LineRangePatchHandler handler : lineRangePatchHandlers) {
			handler.adjustFileLineShift(fromIndex, toIndex, (lines != null) ? lines.length : 0);
		}
		
		return newLine;
	}

	/**
	 */
	public void addLineRangePatchHandler(LineRangePatchHandler handler) {
		Check.checkArg(!lineRangePatchHandlers.contains(handler));
		lineRangePatchHandlers.add(handler);
	}

	/**
	 * Get the file text.
	 * @return
	 */
	public String getText() {
		StringBuffer buffer = new StringBuffer();
		for (String line : currentLines) {
			buffer.append(line);
		}
		return buffer.toString();
	}
}
