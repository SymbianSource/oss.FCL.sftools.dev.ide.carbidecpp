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

package com.nokia.sdt.sourcegen.doms.rss.dom.impl;

import com.nokia.sdt.sourcegen.ISourceFormatter;
import com.nokia.sdt.sourcegen.doms.rss.dom.*;

import java.util.*;

/**
 * 
 *
 */
public class AstResourceDefinition extends AstResource implements
        IAstResourceDefinition {

    private IAstName name;

    /**
     * @param structType
     */
    public AstResourceDefinition(IAstStructDeclaration structType, IAstName name) {
        super(structType);
        setName(name);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        List list = new ArrayList();
        list.add("RESOURCE "); //$NON-NLS-1$
        // we're not defining the type name, so provide its text
        list.add(getStructType().getStructName().getName());
        if (name != null) {
            list.add(" "); //$NON-NLS-1$
            list.add(name);
        }
        list.add(ISourceFormatter.SEGMENT_FORMATTING_LBRACE);
        list.add(initializers);
        list.add(ISourceFormatter.SEGMENT_FORMATTING_RBRACE);
        list.add(ISourceFormatter.SEGMENT_NEWLINE);
        return list.toArray();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResourceDefinition#getName()
     */
    public IAstName getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResourceDefinition#setName(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void setName(IAstName name) {
        if (this.name != null)
            name.setParent(null);
        if (this.name == null || !this.name.getName().equals(name.getName()))
        	dirty = true;
        this.name = name;
        if (name != null)
            name.setParent(this);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameHolder#getRoleForName()
     */
    public int getRoleForName() {
        return NAME_DEFINED;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstResource#getChildren()
     */
    public IAstNode[] getChildren() {
    	if (name != null)
    		return new IAstNode[] { name, initializers };
    	else
    		return new IAstNode[] { initializers };
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstResource#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        List kids = new ArrayList(Arrays.asList(super.getReferencedNodes()));
        if (name != null)
            kids.add(name);
        return (IAstNode[]) kids.toArray(new IAstNode[kids.size()]);
    }
}
