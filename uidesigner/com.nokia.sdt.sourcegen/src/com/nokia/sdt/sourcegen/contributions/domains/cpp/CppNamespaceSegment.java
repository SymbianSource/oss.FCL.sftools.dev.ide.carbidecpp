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

import org.eclipse.cdt.core.dom.ast.IASTFileLocation;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;


/**
 * 
 *
 */
public class CppNamespaceSegment extends CppLocationSegmentBase {

    public CppNamespaceSegment(String segment, String name) {
        super(ICppLocationSegment.L_NAMESPACE, segment, name);
    }

    public CppNamespaceSegment(String name) {
        this(null, name);
    }

    class NamespaceFinder extends LinearNodeVisitor {
        private CodeRange parent;
        private ICPPASTNamespaceDefinition match;
        
        NamespaceFinder(CodeRange parent) {
            this.parent = parent;
            this.match = null;
            this.shouldVisitNamespaces = true;
        }
        
        public ICPPASTNamespaceDefinition getMatch() {
            return match;
        }
        
        /* (non-Javadoc)
         * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.LinearNodeVisitor#visit(org.eclipse.cdt.core.dom.ast.IASTDeclaration)
         */
        public int visit(ICPPASTNamespaceDefinition ns) {
            if (ns.getParent() != parent.getNode())
                return PROCESS_CONTINUE;
            
            // validate source range
            IASTFileLocation loc = ns.getFileLocation();
            if (loc.getNodeOffset() < parent.getOffset()
                    || loc.getNodeOffset() >= parent.getOffset() + parent.getLength())
                return PROCESS_CONTINUE;
            
            if (!ns.getName().toString().equals(getName())) 
                return PROCESS_CONTINUE;

            match = ns;
            return PROCESS_ABORT;
        }
    }
    
    public CodeRange getChildRange(CodeRange parent) {
        NamespaceFinder finder = new NamespaceFinder(parent);
        parent.node.accept(finder);
        
        ICPPASTNamespaceDefinition spec = (ICPPASTNamespaceDefinition) finder.getMatch();
        if (spec == null)
            return null;
        
        return expandChildRange(parent, new CodeRange(parent, spec));

        //return CdtUtils.expandRange(text, parent.offset, parent.offset + parent.length,
        //        new CodeRange(spec), true /* although it's ignored */);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.contributions.domains.cpp.ICppLocationSegment#getInsertPosition(char[], int, int)
     */
    public int getInsertPosition(char[] text, CodeRange range, ICppLocationSegment segment) {
        return ParseUtils.getInsertPositionAtEnd(text, range.offset, range.length, '}');
    }
   
}
