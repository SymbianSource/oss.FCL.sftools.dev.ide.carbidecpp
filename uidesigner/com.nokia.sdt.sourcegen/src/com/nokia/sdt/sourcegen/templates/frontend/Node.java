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

import com.nokia.cpp.internal.api.utils.core.MessageLocation;

/**
 * The basic node in the template IR.
 * <p>
 * A node contains a link to the 'next' node. When read from the start, all
 * nodes appear once on a linked list iterated by the 'next' nodes, and this
 * defines the entire contents of a file.
 * <p>
 * Additional node types add tree structure to group nodes (BlockNode)
 * but that does not supplant the intrinsic ordering in this class.
 * 
 * 
 * 
 */
public abstract class Node {
    Node next;
    private MessageLocation ref;
    
    public Node() {
        this.next = null;
    }

    public Node(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    
    public MessageLocation getRef() {
        return ref;
    }
    

    public void setRef(MessageLocation ref) {
        this.ref = ref;
    }
    

    public abstract String getName();
 
    public String toString() {
        if (getRef() != null)
            return getName() + " [" + getRef() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
        else
            return getName();
    }

    public abstract void accept(INodeVisitor visitor);
    public abstract void accept(IShallowNodeVisitor visitor);
    public abstract void accept(IDeepNodeVisitor visitor, boolean open);
    public abstract void traverseShallow(IShallowNodeVisitor visitor);
    public abstract void traverseDeep(IDeepNodeVisitor visitor);
}
