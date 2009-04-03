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

import com.nokia.cpp.internal.api.utils.core.MessageLocation;


/**
 * A reference to a range of text in source.<p>  
 * This refers to the original, unpreprocessed file text.
 * <pre>
 *   123456789
 * 1:  atoken
 * </pre>
 * The text "atoken" has start line 1, offset 3 
 * and end line 1, offset 9 (one past the end)
 * <p>
  * 
 * 
 *
 */
public interface ISourceRange {
    /** Get start file */
    public ISourceFile getFile();
    /** Get start character offset (inclusive) */
    public int getOffset();
    /** Get start line number */
    public int getLine();

    /** Get end file (may be different from start file!) */
    public ISourceFile getEndFile();
    /** Get end character offset (exclusive) */
    public int getEndOffset();
    /** Get end line number */
    public int getEndLine();
   
    /** Get the source text for this range
     */
    public String getText();

    /** Get the source text for this range
     * with reference to different file buffers
     */
    public String getText(char[] fileText, char[] endFileText);

    /**
     * Get length of text
     */
    public int getLength();

     /**
     * Tell whether this range contains the given range (inclusive)
     * @param range
     * @return true: all of range contained
     */
    public boolean contains(ISourceRange range);
    
    /** Set start file */
    public void setFile(ISourceFile file);
    /** Set end file */
    public void setEndFile(ISourceFile file);
    
    /** 
     * Update offsets, updating lines at the same time.
     * 
     * @param offset start offset in characters
     * @param endOffset end offset in characters (exclusive); if
     * file==endFile, must be >= offset. 
     */
    public void setOffsetRange(int offset, int endOffset);
    
    /** Update lines, updating offsets at the same time.
     * 
     * @param line start line number (starting from 1)
     * @param endLine end line number (inclusive); if
     * file==endFile, must be >= line. 
     * 
     */
    public void setLineRange(int line, int endLine);
    
	/**
	 * Tell whether one range intersects another.
	 */
	public boolean intersects(ISourceRange range);
	
	/**
	 * Create a emssage location
	 * @return
	 */
	public MessageLocation createMessageLocation();
	
	/**
	 * Compare two ranges, returning -1 if this is before
	 * than other, 0 if ANY intersection occurs, and 1 if
	 * this is after.
	 * @param other
	 * @return -1: this &lt; other, 0: this overlaps other, 1: this &gt; other
	 * @throws IllegalArgumentException if nodes are in different files
	 */
	public int compareTo(ISourceRange other);
}
