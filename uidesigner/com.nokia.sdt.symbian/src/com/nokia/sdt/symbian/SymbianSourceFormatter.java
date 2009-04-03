/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian;

import com.nokia.sdt.sourcegen.*;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.SubString;

/**
 * Format text in Symbian RSS style.  
 * 
 *
 */
public class SymbianSourceFormatter extends SourceFormatterBase {
	/** current indentation level */
	protected int indentLevel;
	private CharSequence buffer;
	private int lastPosition;


	/**
	 * Create a formatter with default whitespace handling.
	 *
	 */
	public SymbianSourceFormatter() {
		this(new DefaultSourceFormatting());
	}
	
	/**
     * 
     */
    public SymbianSourceFormatter(ISourceFormatting formatting) {
    	super(formatting);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFormatter#setBuffer(java.lang.CharSequence)
     */
    public void setBuffer(CharSequence buffer) {
    	Check.checkArg(buffer);
    	this.buffer = buffer;
    	this.lastPosition = 0;
    	indentLevel = 0;
    }
    
	public synchronized CharSequence getFormattedText(Object strObj) {
    	
    	// clear the buffer
    	formatBuffer.setLength(0);
    	
    	// see if we're at a space or newline
    	findCurrentPositionInfo(buffer);

    	String str = strObj.toString();
		if (str.equals("\n")) //$NON-NLS-1$
			str = newLine;

    	if (strObj == ISourceFormatter.SEGMENT_FORMATTING_LBRACE) {
    		// formatting left brace:
    		// for Symbian, this indents one level and appears on a new line
        	findIndentLevel();
        	updateIndent(braceDelta + 1);
    		if (!atNewLine)
    			formatBuffer.append(newLine);
    		formatBuffer.append(indent);
    		formatBuffer.append("{"); //$NON-NLS-1$
    		formatBuffer.append(newLine); //$NON-NLS-1$
    	}
    	else if (strObj == ISourceFormatter.SEGMENT_FORMATTING_RBRACE) {
    		// formatting right brace
        	findIndentLevel();
        	if (braceDelta < 0)
        		updateIndent(braceDelta);
        	if (!atIndent) {
        		if (!atNewLine)
        			formatBuffer.append(newLine);
    			formatBuffer.append(indent);
        	}
    		formatBuffer.append("}"); //$NON-NLS-1$
    	}
    	else if (strObj == ISourceFormatter.SEGMENT_FORMATTING_COMMA) {
        	findIndentLevel();
        	if (braceDelta < 0)
        		updateIndent(braceDelta);
        	formatBuffer.append(","); //$NON-NLS-1$
        	formatBuffer.append(newLine);
    	}
    	else if (strObj == ISourceFormatter.SEGMENT_FORMATTING_INDENT) {
        	findIndentLevel();
    		if (!atNewLine)
    			formatBuffer.append(newLine);
        	updateIndent(braceDelta + 1);
    		formatBuffer.append(indent);
    	} else if (atNewLine) {
    		// add another indented line (yes, even if str=="\n")
    		findIndentLevel();
    		if (braceDelta < 0)
    			updateIndent(braceDelta);
    		formatBuffer.append(indent);
    		formatBuffer.append(str);
    	}
    	else {
    		// plain text inside a line
    		return str;
    	}

    	return formatBuffer;
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.ISourceFormatter#getReformattedText(java.lang.CharSequence)
	 */
	public CharSequence getReformattedText(CharSequence str) {
		findCurrentPositionInfo(buffer);
		if (atNewLine)
			return str;
		
		int idx = 0;
		char ch = 0;
		while (idx < str.length()) {
			ch = str.charAt(idx);
			if (!(ch == ' ' || ch == '\t')) {
				break;
			}
			idx++;
		}
		if (idx < str.length()) {
			// don't reformat spaces
			if (atIndent) {
				return new SubString(str, idx, str.length() - idx);
			}
		}
		return str;
	}
	
	/**
     * Find current indentation level for the given line,
     * where 'seq' is positioned at a new line.  This is only
     * needed upon a resync, and is not accurate.
     * We'd hope, but it's not guaranteed, that the indentation
     * matches the expected format.
     * @param seq
     */
    protected void findIndentLevel() {
    	int offs = buffer.length();

    	atIndent = true;
    	braceDelta = 0;
    	
    	if (atNewLine) {
	    	// skip the EOL (which may not exist in an empty buffer)
    		offs -= newLine.length();
    		atIndent = false;
    	}
    	
    	// find the beginning of the previous line, and 
    	// find the span of indentation text
    	boolean allSpaces = true;
    	boolean onOrigLine = true;
    	int wsOffs = -1;
    	while (offs > lastPosition) {
    		offs--;
    		char ch = buffer.charAt(offs);
    		if (ch == ' ' || ch == '\t') {
    			// remember where whitespace ends on line
    			if (wsOffs == -1)
    				wsOffs = offs + 1;
    		}
    		else if (ch == '\r' || ch == '\n') {
    			// don't finalize indent until we find a
    			// line with something besides whitespace
    			if (!allSpaces)
    				break;
    			onOrigLine = false;
    		}
    		else {
    			if (ch == '{')
    				braceDelta++;
    			else if (ch == '}')
    				braceDelta--;
    			wsOffs = -1;
    			if (onOrigLine)
    				atIndent = false;
    			allSpaces = false;
    		}
    	}
    	
    	// got to the beginning
		indent.setLength(0);
		if (wsOffs != -1) {
			indent.append(buffer, offs + 1, wsOffs);
		}
    }

}