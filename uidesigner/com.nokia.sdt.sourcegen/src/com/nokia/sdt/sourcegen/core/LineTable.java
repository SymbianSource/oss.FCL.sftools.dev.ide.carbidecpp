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

package com.nokia.sdt.sourcegen.core;


import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.*;

/**
 * A class which tracks the line<-->offset mappings of a file
 * 
 *
 */
public class LineTable {

    /** index 0 == line 1 */
    List offsets = new ArrayList();
    
    public LineTable(char[] buf) {
        offsets.add(new Integer(0));
        for (int i = 0; i < buf.length; i++) {
            if (buf[i] == '\n') {
                offsets.add(new Integer(i+1));
            } else if (buf[i] == '\r') {
                if (i+1 < buf.length && buf[i+1] == '\n')
                    i++;
                offsets.add(new Integer(i+1));
            }
        }
        offsets.add(new Integer(buf.length+1));
    }

    /**
     * Get the 1-based line number for the given offset
     * @param offset
     * @return line number (1-based)
     */
    public int getLineForOffset(int offset) {
        int idx = Collections.binarySearch(offsets, new Integer(offset));
        if (idx < 0)
            return (-idx) - 1;
        else
            return idx + 1;
    }
    
    /**
     * Get the line number for the given end range of an offset
     */
    public int getLineForEndOffset(int offset) {
        int idx = Collections.binarySearch(offsets, new Integer(offset));
        if (idx < 0)
            return (-idx) - 1;
        else
            return idx == 0 ? 1 : idx;
    }


    /**
     * Return the offset for the given 1-based line
     * @param line
     * @return offset in characters
     */
    public int getOffsetForLine(int line) {
        Check.checkArg(line > 0);
        line--;
        if (line >= offsets.size())
            line = offsets.size() - 1;
        return ((Integer)offsets.get(line)).intValue();
    }
    
    /**
     * Return the end offset for the given 1-based line
     * @param line
     * @return offset in characters
     */
    public int getEndOffsetForLine(int line) {
    	// gets the start offset of the next line, except for last line which is bogus
        Check.checkArg(line > 0);
        if (line >= offsets.size()) {
        	line = offsets.size() - 1;
        }
        int val = ((Integer)offsets.get(line)).intValue();
        if (line == offsets.size() - 1)
        	val--;
        return val;
    }
    
    /**
     * Get the 1-based column for the given offset
     * 
     */
    public int getColumnForOffset(int offset) {
        int line = getLineForOffset(offset);
        return offset - ((Integer)offsets.get(line - 1)).intValue() + 1;
    }

    /**
     * Get the 1-based column for the given end range of an offset
     */
    public int getColumnForEndOffset(int offset) {
        int line = getLineForEndOffset(offset);
        return offset - ((Integer)offsets.get(line - 1)).intValue() + 1;
    }
    
    public int getLineCount() {
        return offsets.size()-1;
    }

	/**
	 * Get the text of the given line
	 * @param line one-based line number
	 * @return text, or null
	 */
	public String getLine(char[] text, int line) {
		Check.checkArg(line > 0);
		int offset = getOffsetForLine(line);
		int endOffset = getEndOffsetForLine(line);
		return new String(text, offset, endOffset - offset);
	}
}
