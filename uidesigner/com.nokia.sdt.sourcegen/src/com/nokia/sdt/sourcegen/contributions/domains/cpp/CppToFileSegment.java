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


/**
 * 
 *
 */
public class CppToFileSegment extends CppLocationSegmentBase {

    public CppToFileSegment(String segment) {
        super(ICppLocationSegment.L_TO_FILE, segment, null);
    }

    public CppToFileSegment() {
        this(null);
    }

    public CodeRange getChildRange(CodeRange parent) {
        return new CodeRange(parent, parent.node.getTranslationUnit());
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ICppLocationSegment#getInsertPosition(char[], com.nokia.sdt.sourcegen.contributions.domains.cpp.CodeRange)
     */
    public int getInsertPosition(char[] text, CodeRange range, ICppLocationSegment segment) {
        return range.length;
    }
}
