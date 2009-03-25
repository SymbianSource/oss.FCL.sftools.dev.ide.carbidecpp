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

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier.ICPPASTBaseSpecifier;

/**
 * 
 *
 */
public class CppBasesSegment extends CppLocationSegmentBase {

    public CppBasesSegment(String segment) {
        super(ICppLocationSegment.L_BASES, segment, null);
    }

    public CppBasesSegment() {
        this(null);
    }

    static class BasesFinder extends LinearNodeVisitor {
        private CodeRange parent;
        private ICPPASTCompositeTypeSpecifier match;
        
        BasesFinder(CodeRange parent) {
            this.parent = parent;
            this.match = null;
            this.shouldVisitDeclSpecifiers = true;
        }
        
        public ICPPASTCompositeTypeSpecifier getMatch() {
            return match;
        }
        
        /* (non-Javadoc)
         * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.LinearNodeVisitor#visit(org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier)
         */
        public int visit(IASTDeclSpecifier declSpec) {
            if (declSpec != parent.getNode())
                return PROCESS_CONTINUE;
            
            if (!(declSpec instanceof ICPPASTCompositeTypeSpecifier))
                return PROCESS_CONTINUE;

            match = (ICPPASTCompositeTypeSpecifier) declSpec;
            return PROCESS_ABORT;
        }
    }

    public CodeRange getChildRange(CodeRange parent) {
    	char[] text = parent.text;
    	
        BasesFinder finder = new BasesFinder(parent);
        parent.node.accept(finder);
        
        ICPPASTCompositeTypeSpecifier spec = finder.getMatch();
        if (spec == null)
            return null;
        
        // if the bases list is empty, it doesn't exist 
        ICPPASTBaseSpecifier[] bases = spec.getBaseSpecifiers();
        if (bases.length == 0)
            return null;
        
        // the range is from the first to the last specifier 
        
        CodeRange range = expandChildRange(parent, new CodeRange(parent, spec, bases[0], bases[bases.length-1]));
        int ptr = range.offset;
        while (ptr < parent.offset + parent.length && text[ptr] != '{')
            ptr++;
        
        // flush to line
        while (ptr > parent.offset && (text[ptr-1] == ' ' || text[ptr-1] == '\t'))
            ptr--;
        range.length = ptr - range.offset;
        return range;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ICppLocationSegment#getInsertPosition(char[], int, int)
     */
    public int getInsertPosition(char[] text, CodeRange range, ICppLocationSegment segment) {
        
        return range.offset + range.length;
    }

}
