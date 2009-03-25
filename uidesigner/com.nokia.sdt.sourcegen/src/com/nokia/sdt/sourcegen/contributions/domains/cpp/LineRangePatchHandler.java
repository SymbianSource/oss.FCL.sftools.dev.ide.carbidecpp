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
import com.nokia.sdt.sourcegen.contributions.IContribution;
import com.nokia.sdt.sourcegen.contributions.SourceGenPatch;
import com.nokia.sdt.sourcegen.patcher.IPatchHandler;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * Handler for applying patches to locations expressed as a range of
 * lines within a file.
 * 
 *
 */
public class LineRangePatchHandler implements IPatchHandler {

	private int offset;
	private int length;
	private FileLinePatchHandler fileHandler;

	/**
	 * Create a handler which alters the subset of lines in the given range
	 * of lines.
	 */
	public LineRangePatchHandler(FileLinePatchHandler fileHandler, int offset, int length) {
		this.fileHandler = fileHandler;
		fileHandler.addLineRangePatchHandler(this);
		this.offset = offset;
		this.length = length;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#isLineEmpty(java.lang.String)
	 */
	public boolean isLineEmpty(String srcLine) {
		return fileHandler.isLineEmpty(srcLine);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#matchLines(java.lang.String, java.lang.String)
	 */
	public boolean matchLines(String srcLine, String patchLine) {
		return fileHandler.matchLines(srcLine, patchLine);
	}
	

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#hasLine(int)
	 */
	public boolean hasLine(int index) {
		return fileHandler.hasLine(index + offset);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#getLine(int)
	 */
	public String getLine(int index) {
		return fileHandler.getLine(index + offset);
	}
	
	public int getEndLineNumber() {
		return fileHandler.getEndLineNumber() - offset;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#replaceLines(int, int, java.lang.String[])
	 */
	public int replaceLines(ISourceGenPatch patch, int fromIndex, int toIndex, String[] lines) {
		// ensure the lines all have the correct terminator and indentation
		if (lines != null) {
			for (int i = 0; i < lines.length; i++)
				 lines[i] = formatLine(((SourceGenPatch) patch).getContribution(), lines[i]);
		}
		
		int newLine = fileHandler.replaceLines(patch, fromIndex + offset, toIndex + offset, lines);
		return newLine - offset;
	}

	private String formatLine(IContribution contrib, String string) {
		if (!string.matches(".*(\r|\n|\r\n)")) { //$NON-NLS-1$
			string += "\n"; //$NON-NLS-1$
		}
		String line = fileHandler.domain.getIndentedLine(contrib.getLocation(), string, contrib.getIndentAdjust(), contrib.isContinuation());
		return line;
	}



	/**
	 * Adjust our range for a change in the file (same args/semantics as
	 * #replaceLines)
	 * @param fromIndex
	 * @param toIndex
	 * @param linesReplaced
	 */
	public void adjustFileLineShift(int fromIndex, int toIndex, int linesReplaced) {
		// ignore if changes happen past our block
		if (fromIndex >= offset + length)
			return;

		if (toIndex <= offset) {
			// if changes happen entirely in front of our block, only offset changes
			offset += linesReplaced - (toIndex - fromIndex); 
		} else {
			// if this fails, changes occurred starting outside our block and
			// came into our block -- which violates the location nesting
			Check.checkState(fromIndex >= offset && toIndex <= offset + length);
			
			// changes must have happened inside our block, meaning
			// the offset is the same but the length changes
			length += linesReplaced - (toIndex - fromIndex);
		}
	}

}
