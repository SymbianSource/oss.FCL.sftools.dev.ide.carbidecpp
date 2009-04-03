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

package com.nokia.sdt.sourcegen.doms.rss.dom;

/**
 * Visitor class for nodes in the DOM.
 * 
 * 
 *
 */
public class AstVisitor {
    /**
     * return continue to continue visiting, abort to stop, skip to not descend
     *         into this node.
     */
    public final static int PROCESS_SKIP = 1;

    public final static int PROCESS_ABORT = 2;

    public final static int PROCESS_CONTINUE = 3;

    /** Visit the node and its children */
    public int visit(IAstNode node) {
        
        return PROCESS_CONTINUE;
    }

    /** Visit a referenced node */
    public int visitReference(IAstNode node) {
        return PROCESS_CONTINUE;
    }
    
    public void traverseChildren(IAstNode node) {
        IAstNode[] refs = node.getReferencedNodes();
        IAstNode[] kids = node.getChildren();
        for (int i = 0; i < refs.length; i++) {
            boolean isKid = false;
            for (int j = 0; j < kids.length; j++) {
                if (kids[j] == refs[i]) {
                    isKid = true;
                    refs[i].accept(this);
                }
            }
            if (!isKid)
                refs[i].acceptReference(this);
        }
    }

}
