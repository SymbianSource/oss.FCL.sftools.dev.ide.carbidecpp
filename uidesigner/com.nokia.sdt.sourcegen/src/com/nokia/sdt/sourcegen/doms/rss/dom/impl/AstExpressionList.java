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
 * 
 *
 */
public class AstExpressionList extends AstNode implements IAstExpressionList {

    List nodes;
    
    /**
     */
    public AstExpressionList() {
        super();
        nodes = new ArrayList();
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        List items = new ArrayList();
        for (Iterator iter = nodes.iterator(); iter.hasNext();) {
            IAstExpression expr = (IAstExpression) iter.next();

            if (expr instanceof IAstResourceExpression) {
            	// each item on a new line
                items.add(expr);
                if (iter.hasNext()) {
                    items.add(ISourceFormatter.SEGMENT_FORMATTING_COMMA);
                }
            } else {
            	// all items on the same line
                items.add(expr);
                if (iter.hasNext()) {
                    items.add(","); //$NON-NLS-1$
                    items.add(ISourceFormatter.SEGMENT_FORMATTING_SPACE);
                }
            }
        }
        return items.toArray();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#getList()
     */
    public IAstExpression[] getList() {
        return (IAstExpression[]) nodes.toArray(new IAstExpression[nodes.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#addExpression(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void addExpression(IAstExpression expr) {
        Check.checkArg(expr);
        Check.checkArg(!nodes.contains(expr));
        nodes.add(expr);
        expr.setParent(this);
        dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#addAllExpressions(java.util.List)
     */
    public void addAllExpressions(List<IAstExpression> elist) {
    	for (Iterator iter = elist.iterator(); iter.hasNext();) {
			IAstExpression fExpr = (IAstExpression) iter.next();
			addExpression(fExpr);
		}
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#simplify()
     */
    public IAstExpression simplify() {
        return this;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression#equalValue(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public boolean equalValue(IAstExpression expr) {
        if (!(expr instanceof AstExpressionList))
            return false;
        return ((AstExpressionList)expr).nodes.equals(nodes);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return (IAstNode[]) nodes.toArray(new IAstNode[nodes.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#get(int)
     */
    public IAstExpression getExpression(int index) {
    	return (IAstExpression) nodes.get(index);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#set(int, com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setExpression(int index, IAstExpression expr) {
    	Check.checkArg(index < nodes.size());
    	Check.checkArg(nodes.get(index) == expr || !nodes.contains(expr));
    	IAstExpression old = (IAstExpression) nodes.get(index);
    	if (old != expr) {
    		old.setParent(null);
    		expr.setParent(this);
    		dirty = true;
    	}
    	nodes.set(index, expr);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#size()
     */
    public int size() {
    	return nodes.size();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#removeExpression(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void removeExpression(IAstExpression expression) {
    	Check.checkArg(nodes.contains(expression));
    	nodes.remove(expression);
    	expression.setParent(null);
    	dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#iterator()
     */
    public Iterator<IAstExpression> iterator() {
    	return new DirtyTrackingIterator(nodes.iterator());
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#listIterator()
     */
    public ListIterator<IAstExpression> listIterator() {
    	return new DirtyTrackingListIterator(nodes.listIterator());
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpressionList#clear()
     */
    public void clear() {
    	for (Iterator iter = nodes.iterator(); iter.hasNext();) {
			IAstExpression expr = (IAstExpression) iter.next();
			expr.setParent(null);
		}
    	nodes.clear();
    	dirty = true;
    }
}
