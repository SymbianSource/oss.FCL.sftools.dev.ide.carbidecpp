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

import com.nokia.sdt.sourcegen.core.ParseUtils;

import org.eclipse.cdt.core.dom.ast.*;

/**
 * 
 *
 */
public class CppEnumSegment extends CppLocationSegmentBase {

    public CppEnumSegment(String segment, String name) {
        super(ICppLocationSegment.L_ENUM, segment, name);
    }

    public CppEnumSegment(String name) {
        this(null, name);
    }

    class EnumFinder extends LinearNodeVisitor {
        private CodeRange parent;
        private IASTEnumerationSpecifier match;
        
        EnumFinder(CodeRange parent) {
            this.parent = parent;
            this.match = null;
            this.shouldVisitDeclSpecifiers = true;
        }
        
        public IASTEnumerationSpecifier getMatch() {
            return match;
        }
        
        /* (non-Javadoc)
         * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.LinearNodeVisitor#visit(org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier)
         */
        public int visit(IASTDeclSpecifier declSpec) {
            if (declSpec.getParent().getParent() != parent.getNode())
                return PROCESS_CONTINUE;
            
            if (!(declSpec instanceof IASTEnumerationSpecifier))
                return PROCESS_CONTINUE;

            // validate source range
            IASTFileLocation loc = declSpec.getFileLocation();
            if (loc.getNodeOffset() < parent.getOffset()
                    || loc.getNodeOffset() >= parent.getOffset() + parent.getLength())
                return PROCESS_CONTINUE;
            
            IASTEnumerationSpecifier enm = (IASTEnumerationSpecifier) declSpec;
            if (!enm.getName().toString().equals(getName())) 
                return PROCESS_CONTINUE;

            match = enm;
            return PROCESS_ABORT;
        }
    }

    public CodeRange getChildRange(CodeRange parent) {
        EnumFinder finder = new EnumFinder(parent);
        parent.node.accept(finder);
        
        IASTEnumerationSpecifier spec = (IASTEnumerationSpecifier) finder.getMatch();
        if (spec == null)
            return null;
        
        return expandChildRange(parent, new CodeRange(parent, spec));

        //return CdtUtils.expandRange(text, parent.offset, parent.offset + parent.length,
        //new CodeRange(spec), true);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ICppLocationSegment#getInsertPosition(char[], int, int)
     */
    public int getInsertPosition(char[] text, CodeRange range, ICppLocationSegment segment) {
        return ParseUtils.getInsertPositionAtEnd(text, range.offset, range.length, '}');
    }

}
