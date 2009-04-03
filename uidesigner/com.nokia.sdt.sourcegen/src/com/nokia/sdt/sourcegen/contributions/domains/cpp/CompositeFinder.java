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

import org.eclipse.cdt.core.dom.ast.*;

class CompositeFinder extends LinearNodeVisitor {
    /**
     * 
     */
    private final CppClassSegment segment;
    private CodeRange parent;
    IASTCompositeTypeSpecifier match;
    
    CompositeFinder(CppClassSegment segment, CodeRange parent) {
        this.segment = segment;
        this.parent = parent;
        this.match = null;
        this.shouldVisitDeclSpecifiers = true;
    }
    
    public IASTCompositeTypeSpecifier getMatch() {
        return match;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.LinearNodeVisitor#visit(org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier)
     */
    public int visit(IASTDeclSpecifier declSpec) {
        if (declSpec.getParent().getParent() != parent.getNode())
            return PROCESS_SKIP;
        
        if (!(declSpec instanceof IASTCompositeTypeSpecifier))
            return PROCESS_SKIP;

        // validate source range
        IASTFileLocation loc = declSpec.getFileLocation();
        if (loc.getNodeOffset() < parent.getOffset()
                || loc.getNodeOffset() >= parent.getOffset() + parent.getLength())
            return PROCESS_SKIP;
        
        IASTCompositeTypeSpecifier comp = (IASTCompositeTypeSpecifier) declSpec;
        if (!comp.getName().toString().equals(this.segment.getName())) 
            return PROCESS_SKIP;

        match = comp;
        return PROCESS_ABORT;
    }
}