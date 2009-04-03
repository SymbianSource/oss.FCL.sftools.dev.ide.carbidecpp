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

package com.nokia.sdt.sourcegen.templates.frontend;


/**
 * A contained block of nodes.  Each node's type is identified
 * by its type (getType()).
 * 
 * 
 *
 */
public class BlockNode extends Node {
    static public final int BLOCK_EXPR = 1;
    static public final int BLOCK_RAW = 2;
    static public final int BLOCK_SCRIPT = 3;
    
    Node children;
    int type;
    
    public BlockNode(int type, Node children) {
        super();
        this.type = type;
        this.children = children;
    }
    
    public BlockNode(int type, Node children, Node next) {
        super(next);
        this.type = type;
        this.children = children;
    }
  
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.Node#traverseShallow(com.nokia.sdt.sourcegen.templates.NodeVisitor)
     */
    public void traverseShallow(IShallowNodeVisitor visitor) {
        accept(visitor);
        if (next != null)
            next.traverseShallow(visitor);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.Node#traverseDeep(com.nokia.sdt.sourcegen.templates.NodeVisitor)
     */
    public void traverseDeep(IDeepNodeVisitor visitor) {
        accept(visitor, true);
        if (children != null)
            children.traverseDeep(visitor);
        accept(visitor, false);
        
        if (next != null)
            next.traverseDeep(visitor);
    }

    public void accept(IShallowNodeVisitor visitor) {
        visitor.visitBlockNode(this);
    }

    public void accept(IDeepNodeVisitor visitor, boolean open) {
        visitor.visitBlockNode(this, open);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.Node#getName()
     */
    public String getName() {
        switch (type) {
        case BLOCK_EXPR:
            return "EXPR"; //$NON-NLS-1$
        case BLOCK_RAW:
            return "RAW"; //$NON-NLS-1$
        case BLOCK_SCRIPT:
            return "SCRIPT"; //$NON-NLS-1$
        }
        throw new IllegalStateException();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.frontend.Node#accept(com.nokia.sdt.sourcegen.templates.frontend.NodeVisitor)
     */
    public void accept(INodeVisitor visitor) {
    }

    public Node getChildren() {
        return children;
    }
    

    public int getType() {
        return type;
    }
    
}
