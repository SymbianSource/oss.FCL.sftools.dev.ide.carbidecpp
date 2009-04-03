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
public class AstEnumDeclaration extends AstNode implements IAstEnumDeclaration {

    private IAstName name;
    /** List of IAstEnumerator */
    private IAstListNode enums;
    private IScope scope;
    
    public AstEnumDeclaration(IAstName name) {
        super();
        this.enums = new AstListNode(IAstEnumerator.class,
        		new Object[] {  },
        		new Object[] { });
        this.enums.setParent(this);
        this.enums.setDirty(false);
        this.scope = new Scope(this, name, null);
        setName(name);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#toString()
     */
    public String toString() {
        return "AstEnumDeclaration: " + name + "\n" + dump(); //$NON-NLS-1$
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#getName()
     */
    public IAstName getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#setName(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void setName(IAstName name) {
        if (this.name!= null)
            this.name.setParent(null);
        this.name = name;
        if (name != null)
            name.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#getEnumeratorList()
     */
    public IAstListNode getEnumeratorList() {
    	return enums;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#getEnumerators()
     */
    public IAstEnumerator[] getEnumerators() {
        return (IAstEnumerator[]) enums.getChildren();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#addEnumerator(com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumerator)
     */
    public void addEnumerator(IAstEnumerator enumerator) {
        IAstEnumerator last = getLastEnumerator();
        if (last != null)
        	last.setDirty(true);	// the comma changes
        enums.addItem(enumerator);
        scope.add(enumerator.getName());
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#findEnumerator(java.lang.String)
     */
    public IAstEnumerator findEnumerator(String name) {
        IAstName astName = scope.find(name);
        if (astName == null)
            return null;
        
        for (Iterator iter = enums.listIterator(); iter.hasNext();) {
            IAstEnumerator enm = (IAstEnumerator) iter.next();
            if (enm.getName().equals(astName))
                return enm;
        }
        
        return null;
    }


    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#removeEnumerator(com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumerator)
     */
    public void removeEnumerator(IAstEnumerator enumerator) {
    	enums.removeItem(enumerator);
        scope.remove(enumerator.getName());
        IAstEnumerator last = getLastEnumerator();
        if (last != null)
        	last.setDirty(true);	// the comma changes
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameHolder#getRoleForName()
     */
    public int getRoleForName() {
        return IAstNameHolder.NAME_DEFINED;
    }
    
     /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        if (name != null)
            return new IAstNode[] { name, enums };
        else
            return new IAstNode[] { enums };
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
        List list = new ArrayList();
        
        list.add("enum "); //$NON-NLS-1$
        if (name != null)
            list.add(name);
        list.add(ISourceFormatter.SEGMENT_FORMATTING_LBRACE);
        list.add(enums);
        list.add(ISourceFormatter.SEGMENT_FORMATTING_RBRACE);
        list.add(";"); //$NON-NLS-1$
        list.add(ISourceFormatter.SEGMENT_NEWLINE);
        
        return list.toArray();
    }

	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#getScope()
     */
    public IScope getScope() {
        return scope;
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumerator#getEffectiveValue()
	 */
	public IAstLiteralExpression getEffectiveValue(IAstEnumerator enm) {
		int currentValue = 0;
		boolean bValueKnown = true;
		for (Iterator iter = enums.listIterator(); iter.hasNext();) {
			IAstEnumerator anEnum = (IAstEnumerator) iter.next();
			IAstInitializerExpression init = anEnum.getInitializerExpression();
			if (init != null) {
				IAstExpression value = init.getExpression().simplify();
				if (value instanceof IAstLiteralExpression) {
					try {
						currentValue = ((IAstLiteralExpression) value).getIntValue();
						bValueKnown = true;
					} catch (NumberFormatException e) {
						bValueKnown = false;
					}
				} else {
					bValueKnown = false;
				}
			}
			if (anEnum == enm) {
				if (!bValueKnown)
					return null;
				return new AstLiteralExpression(IAstLiteralExpression.K_INTEGER, "" + currentValue); //$NON-NLS-1$
			}
			currentValue++;
		}
		
		// anEnum is not in the list!
		Check.checkArg(false);
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#getLastEnumerator()
	 */
	public IAstEnumerator getLastEnumerator() {
		return (IAstEnumerator) enums.getLastItem();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumDeclaration#removeEnumeratorPreservingStartValues(com.nokia.sdt.sourcegen.doms.rss.dom.IAstEnumerator)
	 */
	public void removeEnumeratorPreservingStartValues(IAstEnumerator enm) {
		int idx = enums.indexOf(enm);
		Check.checkArg(idx >= 0);
		IAstEnumerator next = idx + 1 < enums.size() ? (IAstEnumerator) enums.get(idx + 1) : null;
		IAstInitializerExpression initExpr = enm.getInitializerExpression();
		removeEnumerator(enm);
 		if (initExpr != null && next != null && next.getInitializerExpression() == null) {
 			initExpr.setParent(null);
 			next.setInitializerExpression(initExpr);
 		}
	}

}
