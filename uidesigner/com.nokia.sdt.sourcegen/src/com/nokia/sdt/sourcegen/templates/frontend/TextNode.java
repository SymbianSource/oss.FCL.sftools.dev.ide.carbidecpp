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
 * A node that contains text
 * 
 *
 */
public class TextNode extends Node {
    /** The text of the node */
    String text;
 
    /**
     * Create a new text node
     * @param ref source reference
     * @param text contents of node
     */
    public TextNode(MessageLocation ref, String text) {
        super();
        if (text == null)
            throw new IllegalArgumentException();
        setRef(ref);
        this.text = text;
    }

    public String getName() {
        return "TEXT"; //$NON-NLS-1$
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return super.toString() + " (" + getText() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void traverseShallow(IShallowNodeVisitor visitor) {
        accept(visitor);
        if (next != null)
            next.traverseShallow(visitor);
    }

    public void traverseDeep(IDeepNodeVisitor visitor) {
        accept((INodeVisitor)visitor);
        if (next != null)
            next.traverseDeep(visitor);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.Node#accept(com.nokia.sdt.sourcegen.templates.ShallowNodeVisitor)
     */
    public void accept(IShallowNodeVisitor visitor) {
        visitor.visitTextNode(this);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.Node#accept(com.nokia.sdt.sourcegen.templates.DeepNodeVisitor, boolean)
     */
    public void accept(IDeepNodeVisitor visitor, boolean open) {
        visitor.visitTextNode(this);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.templates.Node#accept(com.nokia.sdt.sourcegen.templates.NodeVisitor)
     */
    public void accept(INodeVisitor visitor) {
        visitor.visitTextNode(this);
                
    }
}
