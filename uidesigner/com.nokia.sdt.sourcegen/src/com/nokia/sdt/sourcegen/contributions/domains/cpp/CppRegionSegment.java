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

import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.sourcegen.contributions.ILocation;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.utils.SubString;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CppRegionSegment extends CppLocationSegmentBase {

	/** generic pattern that can be used to identify a region */
	static final Pattern BEGIN_REGION_SECTION_PATTERN = Pattern.compile(CppDomain.BEGIN_SECTION_REGEX + ".*\\[(.*)\\].*(\r\n|\r|\n)"); //$NON-NLS-1$

	/** generic pattern that can be used to identify an owned region */
	static final Pattern BEGIN_OWNED_REGION_SECTION_PATTERN = Pattern.compile(CppDomain.BEGIN_SECTION_REGEX + ".*:.*\\[(.*)\\].*(\r\n|\r|\n)"); //$NON-NLS-1$

    /** This regex is used to match the start comment added to identify a region */
    private Pattern beginRegex;
    private Pattern endRegex;

    public CppRegionSegment(String segment, String name) {
        super(ICppLocationSegment.L_REGION, segment, name);
        this.beginRegex = Pattern.compile(CppDomain.BEGIN_SECTION_REGEX + ".*\\[" + TextUtils.regexEscape(name) + "\\].*(\r\n|\r|\n)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        this.endRegex = Pattern.compile(CppDomain.END_SECTION_REGEX + ".*\\[" + TextUtils.regexEscape(name) + "\\].*(\r\n|\r|\n)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public CppRegionSegment(String name) {
        this(null, name);
    }

    public CodeRange getChildRange(CodeRange parent) {
        // scan for begin and end comments
    	char[] text = parent.text;
    	
        SubString subText = new SubString(text, parent.offset, parent.length);
        Matcher m = beginRegex.matcher(subText);
        int start = -1, end = -1;
        if (m.find()) {
            start = m.end() + parent.offset;
            m = endRegex.matcher(subText);
            if (m.find()) {
                end = m.start() + parent.offset;
                if (start <= end) {
                    // scan backwards to beginning of line
                    while (end > start && text[end - 1] != '\n')
                        end--;
                    return new CodeRange(parent, parent.node, start, end - start);
                }
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ICppLocationSegment#getInsertPosition(char[], int, int)
     */
    public int getInsertPosition(char[] text, CodeRange range, ICppLocationSegment segment) {
        int offset = range.offset + range.length;
        
        // scan backward to first non-whitespace character (hopefully a newline)
        while (offset > range.offset && (text[offset-1] == ' ' || text[offset-1] == '\t'))
            offset--;
        // don't stick next to other text
        if (offset > range.offset && text[offset-1] != '\n')
            offset++;
        return offset;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.CppLocationSegmentBase#getComment()
     */
    public String getComment(ILocation loc, boolean owned, boolean header) {
        // alter comments in non-owned or inside owned locations:
        // we need a comment to find the region in the first place,
        // but they don't need to say "do not modify"
        boolean subRegionComment = !owned || (loc.getParent() != null && loc.getParent().isOwned());

        if (header)
            return MessageFormat.format(
                    Messages.getString(subRegionComment ? "CppLocation.SubRegionHeader" //$NON-NLS-1$
                            : "CppLocation.OwnedRegionHeader"), //$NON-NLS-1$
                    new Object[] { getName() }); //$NON-NLS-1$
        else
            return MessageFormat.format(
                    Messages.getString(subRegionComment ? "CppLocation.SubRegionFooter" //$NON-NLS-1$
                            : "CppLocation.OwnedRegionFooter"), //$NON-NLS-1$
                    new Object[] { getName() }); //$NON-NLS-1$    
    }
}
