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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public class AstEnumerator extends AstNode implements IAstEnumerator {
    
    private IAstName name;
    private IAstInitializerExpression init;

    public AstEnumerator(IAstName name, IAstInitializerExpression init) {
        super();
        setName(name);
        setInitializerExpression(init);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        if (init != null)
            return new IAstNode[] { name, init };
        else
            return new IAstNode[] { name };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
    	// see AstEnumDeclaration: we associate the comma with
    	// the enumerator
    	List segments = new ArrayList(3);
    	segments.add(name);
        if (init != null) {
        	segments.add(ISourceFormatter.SEGMENT_FORMATTING_SPACE);
        	segments.add(init);
        }
        if (getParent() != null) {
        	if (getParent().getParent() != null) {
	        	IAstEnumerator last = ((IAstEnumDeclaration) getParent().getParent()).getLastEnumerator();
	        	if (this != last)
	        		segments.add(","); //$NON-NLS-1$
        	}
        	segments.add(ISourceFormatter.SEGMENT_NEWLINE);
        }
        
        return segments.toArray();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumerator#getName()
     */
    public IAstName getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumerator#setName(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void setName(IAstName name) {
        Check.checkArg(name);
        if (this.name != null)
            this.name.setParent(null);
        if (this.name != name)
        	dirty = true;
        this.name = name;
        name.setParent(this);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumerator#getInitializerExpression()
     */
    public IAstInitializerExpression getInitializerExpression() {
        return init;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumerator#setInitializerExpression(com.nokia.sdt.sourcegen.doms.rss.dom.IAstInitializerExpression)
     */
    public void setInitializerExpression(IAstInitializerExpression init) {
        if (this.init != null)
            this.init.setParent(null);
        if ((this.init != null && init != null && !this.init.getExpression().equalValue(init.getExpression()))
        		|| (this.init == null) != (init == null))
        	dirty = true;
        this.init = init;
        if (init != null)
            init.setParent(this);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameHolder#getRoleForName()
     */
    public int getRoleForName() {
        return IAstNameHolder.NAME_DEFINED;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#getEnumeration()
     */
    public IAstEnumDeclaration getEnumeration() {
    	if (getParent() == null)
    		return null;
        return (IAstEnumDeclaration) getParent().getParent();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstListElement#removeFromParent()
     */
    public void removeFromParent() {
    	getEnumeration().removeEnumerator(this);
    }
    
}
