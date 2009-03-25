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
import com.nokia.sdt.sourcegen.patcher.BaseSourceGenPatchHandler;
import com.nokia.sdt.sourcegen.patcher.DocumentPatchHandler;

/**
 * Handler for applying patches to locations expressed as a range of
 * lines within a file, where patches are formatted appropriately for
 * the location.
 * 
 *
 */
public class SourceGenDocumentLineRangePatchHandler extends BaseSourceGenPatchHandler {

	private int offset;
	private int length;
	private DocumentPatchHandler fileHandler;
	private CppDomain domain;

	/**
	 * Create a handler which alters the subset of lines in the given range
	 * of lines.
	 */
	public SourceGenDocumentLineRangePatchHandler(CppDomain domain, DocumentPatchHandler fileHandler, int offset, int length) {
		this.domain = domain;
		this.fileHandler = fileHandler;
		this.offset = offset;
		this.length = length;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.LineArrayPatchHandler#removeAllWhitespace(java.lang.String)
	 */
	@Override
	protected String removeAllWhitespace(String line) {
		if (line == null)
			return null;
		line = CdtUtils.stripComments(line).toString();
		return super.removeAllWhitespace(line);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#hasLine(int)
	 */
	public boolean hasLine(int index) {
		return index < length && fileHandler.hasLine(index + offset);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#getLine(int)
	 */
	public String getLine(int index) {
		if (index < 0 || index >= length)
			return null;
		return fileHandler.getLine(index + offset);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.patcher.IPatchHandler#getEndLineNumber()
	 */
	public int getEndLineNumber() {
		return length;
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
		String line = domain.getIndentedLine(contrib.getLocation(), string, contrib.getIndentAdjust(), contrib.isContinuation());
		return line;
	}
}
