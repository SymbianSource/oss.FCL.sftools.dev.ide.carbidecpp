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

import com.nokia.sdt.sourcegen.doms.rss.dom.*;

import java.util.Iterator;

/**
 * 
 *
 */
public abstract class AstCompositeTypeSpecifier extends AstNode implements
        IAstCompositeTypeSpecifier {

	protected IAstListNode members;
    protected IScope scope;
    
    /**
     */
    public AstCompositeTypeSpecifier() {
        super();
        this.scope = new Scope(this, null, null);
        this.members = new AstListNode(IAstMemberDeclaration.class,
        		new Object[] { },
        		new Object[] { });
        this.members.setParent(this);
        this.members.setDirty(false);
        dirty = false;
   }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstCompositeTypeSpecifier#getMemberList()
     */
    public IAstListNode getMemberList() {
    	return members;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstCompositeTypeSpecifier#getMembers()
     */
    public IAstMemberDeclaration[] getMembers() {
        return (IAstMemberDeclaration[]) members.getChildren(); 
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstCompositeTypeSpecifier#addMember(com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration)
     */
    public void addMember(IAstMemberDeclaration decl) {
    	members.addItem(decl);
        scope.add(decl.getMemberName());
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstCompositeTypeSpecifier#findMember(java.lang.String)
     */
    public IAstMemberDeclaration findMember(String name) {
        for (Iterator iter = members.listIterator(); iter.hasNext();) {
            IAstMemberDeclaration member = (IAstMemberDeclaration) iter.next();
            if (member.getMemberName().getName().equals(name))
                return member;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstCompositeTypeSpecifier#getScope()
     */
    public IScope getScope() {
        return scope;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { members };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }

}
