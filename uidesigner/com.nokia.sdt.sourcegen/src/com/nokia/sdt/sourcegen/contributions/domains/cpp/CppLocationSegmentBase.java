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
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.core.ParseUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base for any kind of location in C++ source
 * 
 *
 */
public abstract class CppLocationSegmentBase implements ICppLocationSegment {

    /** The location type (ICppLocationSegment#L_xxx) */
    private int type;
    /** The path segment defining this location */
    private String segment;
    /** Any name associated with the segment */
    private String name;
    
    private static Pattern commentRegex;
    private static Pattern beginSectionRegex;
    private static Pattern endSectionRegex;
    static {
        beginSectionRegex = Pattern.compile("//\\s*\\[\\[\\[.*(\r\n|\r|\n)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        endSectionRegex = Pattern.compile("//\\s*\\]\\]\\].*(\r\n|\r|\n)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        commentRegex = Pattern.compile("(\\s*(//.*|/\\*([^*]|(\\*(?!/)))*\\*/)?)*(\r\n|\r|\n)?", Pattern.MULTILINE); //$NON-NLS-1$
    }
    
    /**
     * 
     */
    public CppLocationSegmentBase(int type, String segement, String name) {
        this.type = type;
        this.segment = segement;
        this.name = name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.impl.ICppLocationSegment#getType()
     */
    public int getType() {
        return type;
    }
    
    public String getSegment() {
        return segment;
    }
    
    private String getTypeString() {
        switch (type) {
        case L_CLASS: return Messages.getString("CppLocationSegment.Class"); //$NON-NLS-1$
        case L_ENUM: return Messages.getString("CppLocationSegment.Enum"); //$NON-NLS-1$
        case L_FUNCTION: return Messages.getString("CppLocationSegment.Function"); //$NON-NLS-1$
        case L_NAMESPACE: return Messages.getString("CppLocationSegment.Namespace"); //$NON-NLS-1$
        case L_BASES: return Messages.getString("CppLocationSegment.ClassBases"); //$NON-NLS-1$
        }
        return null;
    }
    public String getName() {
        return name;
    }
    
    static Pattern matchNewlines = Pattern.compile("(\\s*\n*)+", Pattern.MULTILINE); //$NON-NLS-1$
    static Pattern matchWhitespace = Pattern.compile("([\\t\\v ]+)", Pattern.MULTILINE); //$NON-NLS-1$
    
    /**
     * Convert a string of source to a canonical form that has whitespace
     * and newlines removed -- relatively useful for comparing source for
     * equality.
     */
    public static String canonicalizeSource(String text) {
        String munged;
        
        Matcher matcher = matchNewlines.matcher(text);
        munged = matcher.replaceAll(""); //$NON-NLS-1$
        matcher = matchWhitespace.matcher(munged);
        munged = matcher.replaceAll(" "); //$NON-NLS-1$
        
        // remove leading & trailing whitespace
        munged = munged.trim();
        return munged;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ICppLocationSegment#getNonOwnedComment(com.nokia.sdt.sourcegen.contributions.ILocation, boolean)
     */
    public String getComment(ILocation loc, boolean owned, boolean header) {
        if (!owned) {
            // don't use non-owned comments
            return ""; //$NON-NLS-1$
        }
        
        // suppress "do not modify" comments inside owned locations:
        // the entire thing will be regenerated, so no need to find
        // the location again.
        boolean insideOwned = (loc.getParent() != null && loc.getParent().isOwned());

        if (insideOwned)
            return ""; //$NON-NLS-1$

        // although CDT is cool enough to tell us the entire range
        // of a function, class, etc., we need to emit the header and footer
        // comments to find the full range of sourcegen-generated code,
        // so we can cull the correct amount of leading and trailing
        // whitespace/comments when regenerating owned sections.
        if (header)
            return MessageFormat.format( 
                    Messages.getString("CppLocation.OwnedSectionHeader"), //$NON-NLS-1$
                    new Object[] { getTypeString() });
        else
            return MessageFormat.format( 
                    Messages.getString("CppLocation.OwnedSectionFooter"), //$NON-NLS-1$
                    new Object[] { getTypeString() });
            
    }
    
    /**
     * Expand the child range around the CDT-defined syntactic section
     * to include the beginning and end comments.  This allows contributions
     * to add comments and whitespace to owned sections and be assured
     * they will be removed properly.
     * @param parent range of parent, which limits how far we search
     * @param exactChildRange CDT-defined child range
     * @return new range of child
     */
    public CodeRange expandChildRange(CodeRange parent, CodeRange exactChildRange) {
        Check.checkArg(parent);
        Check.checkArg(exactChildRange);
        Check.checkArg(parent.offset <= exactChildRange.offset);
        Check.checkArg(parent.offset + parent.length >= exactChildRange.offset + exactChildRange.length);
        
        char[] text = parent.text;
        
        // scan for begin and end comments: it is okay if they are missing
        
        // go backwards, line by line, from exact beginning, to find a
        // line containing the start section comment, stopping if
        // any of the lines in between is not whitespace 
        int start = exactChildRange.offset;
        int startLine = start, nextLine = start;
        while (startLine > parent.offset) {
            int line = ParseUtils.skipToLogicalBeginningOfLineBackward(text, startLine, parent.offset);
            if (line < startLine) {
                String subText = new String(text, line, nextLine - line);
                Matcher m = beginSectionRegex.matcher(subText);
                if (m.find()) {
                    start = m.end() + line;
                    break;
                }
                
                // make sure there is no syntax or ending comment on the line
                m = commentRegex.matcher(subText);
                Matcher m2 = endSectionRegex.matcher(subText); 
                if (!m.matches() || m2.find()) {
                    start = exactChildRange.offset;
                    // include any whitespace in the start range 
                    while (start > parent.offset 
                            && (text[start - 1] == ' ' || text[start - 1] == '\t')) {
                        start--;
                    }
                    break;
                }
            }
            nextLine = line;
            startLine = line - ParseUtils.newlineLengthBefore(text, line);
        }

        // go forwards, line by line, from exact end, to find a
        // line containing the end section comment, stopping if
        // any of the lines in between is not whitespace 
        int end = exactChildRange.offset + exactChildRange.length;
        
        // first, consume any semicolon
        while (end < parent.offset + parent.length) {
            if (Character.isWhitespace(text[end])) {
                end++;
            } else if (text[end] == ';') {
                end++;
                break;
            } else 
                break;
        }
              
        int endLine = end,  // end of line, before any terminators  
            prevLineStart = end; // start of previous line after terminators
        while (endLine < parent.offset + parent.length) {
            int eolPos = ParseUtils.skipToLogicalEndOfLineForward(text, prevLineStart, parent.offset + parent.length);
            if (eolPos > prevLineStart) {
                String subText = new String(text, prevLineStart, (eolPos - prevLineStart) + ParseUtils.newlineLength(text, eolPos));
                Matcher m = endSectionRegex.matcher(subText);
                if (m.find()) {
                    end = prevLineStart;
                    break;
                }
                
                // make sure there is no syntax or beginning comment on the line
                m = commentRegex.matcher(subText);
                Matcher m2 = beginSectionRegex.matcher(subText);
                if (!m.matches() || m2.find()) {
                    end = ParseUtils.skipToLogicalEndOfLineForward(text, exactChildRange.offset + exactChildRange.length, parent.offset + parent.length);
                    end += ParseUtils.newlineLength(text, end);
                    break;
                }
            }
            prevLineStart = eolPos + ParseUtils.newlineLength(text, eolPos);
            endLine = eolPos;
        }

        return new CodeRange(parent, exactChildRange.getNode(), start, end - start);
    }

}
