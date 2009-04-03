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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

import com.nokia.sdt.sourcegen.contributions.ILocation;


/**
 * A C/C++ location segment.  These are the semantic values of the
 * textual segments of a location identifier in XML.  They are not
 * tied to any specific location and may be reused.
 * 
 *
 */
public interface ICppLocationSegment {
    /** A class declaration */
    static final int L_CLASS = 1;
    /** A function definition */
    static final int L_FUNCTION = 2;
    /** An enumerator declaration */
    static final int L_ENUM = 3;
    /** A namespace */
    static final int L_NAMESPACE = 4;
    /** A commented region of text */
    static final int L_REGION = 5;
    /** An action that reduces the location to its file */  
    static final int L_TO_FILE = 6;
    /** The base-class list of a class declaration */
    static final int L_BASES = 7;
     
    /** Get the type 
     * 
     * @return type
     * @see #L_CLASS
     * @see #L_NAMESPACE
     * @see #L_FUNCTION
     * @see #L_REGION
     * @see #L_ENUM
     * @see #L_TO_FILE
     * @see #L_BASES
     */
    public int getType();
    
    /** Get the string used to define this segment (e.g. "class(foo)") */
    public String getSegment();
    
    /** Locate the code range for this location in the given source
     * range of the parent.  This is the range encompassing the entire
     * location, which can be deleted or replaced as a chunk.
     * @param parent owning range
     * @return CodeRange, or null
     */
    public CodeRange getChildRange(CodeRange parent);
    
    /**
     * Provide the file-relative position where a contribution
     * for the given child segment should be inserted.  
     * This should point to a character which is moved aside
     * (i.e. position 0 for start of file, or the position of a '}').
     * <p>
     * The offset points inside the receiver's range.
     * The child may or may not be used to determine the insertion
     * position.  If null, no specific type of child is being inserted.
     * @param text the file text
     * @param range the range owned by the receiver's range.
     * @param child the child to insert, or null for generic text
     */
    public int getInsertPosition(char[] text, CodeRange range, ICppLocationSegment child);

    /**
     * Get the generated comments surrounding a region
     * @param location
     * @param owned if true, get the comment indicating the region is owned and should
     * not be modified, else get comment indicating non-owned region
     * @param header true if head of region, else false for footer
     * @return comment text -- this should not contain embedded newlines
     */
    public String getComment(ILocation location, boolean owned, boolean header);
 
}
