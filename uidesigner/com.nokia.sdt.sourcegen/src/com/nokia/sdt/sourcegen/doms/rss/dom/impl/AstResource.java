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
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.*;

/**
 * This class is the basis of a resource definition.  It
 * is non-abstract merely for testing purposes.  
 * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstResourceExpression
 * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstResourceDefinition
 * 
 *
 */
public class AstResource extends AstNode implements IAstResource {

    protected IAstStructDeclaration structType;
    protected IAstListNode initializers;

    /**
     * 
     */
    public AstResource(IAstStructDeclaration structType) {
        super();
        this.initializers = new AstListNode(IAstMemberInitializer.class,
        		new Object[] {  },
        		new Object[] {  });
        this.initializers.setParent(this);
        this.initializers.setDirty(false);
        setStructType(structType);
        dirty = false;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstResource#getTextSegments()
     */
    public Object[] getTextSegments() {
        List list = new ArrayList();
        list.add(ISourceFormatter.SEGMENT_FORMATTING_LBRACE);
        list.add(initializers);
        list.add(ISourceFormatter.SEGMENT_FORMATTING_RBRACE);
        return list.toArray();
    }


    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource#getStructType()
     */
    public IAstStructDeclaration getStructType() {
        return structType;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource#setStructType(com.nokia.sdt.sourcegen.doms.rss.dom.IAstStructDeclaration)
     */
    public void setStructType(IAstStructDeclaration structType) {
        Check.checkArg(structType);
        this.structType = structType;
        initializers.clearItems();
        // structType not owned
        dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource#getInitializerList()
     */
    public IAstListNode getInitializerList() {
    	return initializers;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource#getInitializers()
     */
    public IAstMemberInitializer[] getInitializers() {
        return (IAstMemberInitializer[]) initializers.getChildren();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource#clearInitializers()
     */
    public void clearInitializers() {
    	initializers.clearItems();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource#findInitializer(java.lang.String)
     */
    public IAstMemberInitializer findMemberInitializer(String memberName) {
        Check.checkArg(memberName);
        for (Iterator iter = initializers.listIterator(); iter.hasNext();) {
            IAstMemberInitializer init = (IAstMemberInitializer) iter.next();
            if (init.getMember().getMemberName().getName().equals(memberName))
                return init;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource#addInitializer(com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer)
     */
    public void addInitializer(IAstMemberInitializer init) {
        Check.checkArg(init);
        Check.checkArg(!initializers.containsItem(init));
        Check.checkArg(init.getMember().getStructType() == structType);
        for (ListIterator iter = initializers.listIterator(); iter.hasNext();) {
            IAstMemberInitializer oldInit = (IAstMemberInitializer) iter.next();
            if (oldInit.getMember() == init.getMember()) {
            	// add the new initializer in the same spot
            	iter.remove();
            	iter.add(init);
                init.setParent(initializers);
                initializers.setDirty(true);
                return;
            }
        }
        initializers.addItem(init);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource#removeInitializer(com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer)
     */
    public void removeInitializer(IAstMemberInitializer init) {
    	initializers.removeItem(init);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstResource#insertInitializer(com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer, com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer)
     */
    public void insertInitializer(IAstMemberInitializer after, IAstMemberInitializer init) {
    	Check.checkArg(init.getMember().getStructType() == structType);
    	initializers.insertItem(after, init);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { initializers };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
    	return new IAstNode[] { initializers, structType };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListElement#removeFromParent()
     */
    public void removeFromParent() {
    	IAstNode parent = getParent();
		if (parent instanceof IAstListNode)
    		((IAstListNode) parent).removeItem(this);
    	else if (parent instanceof IAstExpressionList)
    		((IAstExpressionList) parent).removeExpression((IAstResourceExpression) this);
    	/*else if (parent instanceof IAstInitializerExpression &&
    			parent.getParent() instanceof IAstMemberInitializer &&
    			parent.getParent().getParent() instanceof IAstListNode)
    		((IAstListNode) parent.getParent().getParent()).removeItem(parent.getParent());*/
    	else
    		Check.checkState(false);
    }
}
