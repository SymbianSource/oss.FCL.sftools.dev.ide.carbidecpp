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
public class AstMemberDeclaration extends AstNode implements
        IAstMemberDeclaration {

    private IAstSimpleDeclaration memberType;
    private IAstName name;
    private IAstInitializerExpression init;
    private IAstArrayDeclarator arrayDecl;
    private IAstLengthPrefixDeclarator lengthPrefix;
    private IAstExpression lengthLimit;
    private boolean templateStyle;

    /**
     */
    public AstMemberDeclaration( 
            IAstLengthPrefixDeclarator lengthPrefix,
            IAstSimpleDeclaration memberType, IAstArrayDeclarator arrayDecl,
            IAstName memberName, 
            IAstExpression lengthLimit, boolean templateStyle,
            IAstInitializerExpression init) {
        super();
        setLengthPrefixDeclarator(lengthPrefix);
        setMemberType(memberType);
        setArrayDeclarator(arrayDecl);
        setMemberName(memberName);
        setLengthLimit(lengthLimit);
        setTemplateStyle(templateStyle);
        setInitializerExpression(init);
        dirty = false;
    }

    /**
     */
    public AstMemberDeclaration( 
            IAstSimpleDeclaration memberType, IAstName memberName) {
        this(null, memberType, null, memberName, null, false, null);
    }

    /**
     * 
     */
    public AstMemberDeclaration(IAstLengthPrefixDeclarator lengthPrefix,
            IAstSimpleDeclaration memberType, IAstArrayDeclarator arrayDecl,
            IAstName memberName, IAstInitializerExpression init) {
        this(lengthPrefix, memberType, arrayDecl, 
                memberName, null, false, init);
    }

    /*
     * (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        List list = new ArrayList();
        if (lengthPrefix != null) { 
            list.add("LEN "); //$NON-NLS-1$
            list.add(lengthPrefix);
            list.add(" "); //$NON-NLS-1$
        }
        list.add(memberType);
        if (lengthLimit != null && templateStyle) {
            list.add("<"); //$NON-NLS-1$
            list.add(lengthLimit);
            list.add(">"); //$NON-NLS-1$
        }
        list.add(" "); //$NON-NLS-1$
        list.add(name);
        if (lengthLimit != null && !templateStyle) {
            list.add("("); //$NON-NLS-1$
            list.add(lengthLimit);
            list.add(")"); //$NON-NLS-1$
        }
        if (arrayDecl != null)
            list.add(arrayDecl);
        if (init != null) {
        	list.add(ISourceFormatter.SEGMENT_FORMATTING_SPACE);
            list.add(init);
        }
        list.add(";"); //$NON-NLS-1$
       
        if (getParent() != null) {
        	list.add(ISourceFormatter.SEGMENT_NEWLINE);
        }
        return list.toArray();
   }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#getMemberType()
     */
    public IAstSimpleDeclaration getMemberType() {
        return memberType;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#setMemberType(com.nokia.sdt.sourcegen.doms.rss.dom.IAstDeclaration)
     */
    public void setMemberType(IAstSimpleDeclaration decl) {
        Check.checkArg(decl);
        this.memberType = decl;
        // not owned
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#getMemberName()
     */
    public IAstName getMemberName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#setMemberName(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void setMemberName(IAstName name) {
        Check.checkArg(name);
        this.name = name;
        name.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#getInitializer()
     */
    public IAstInitializerExpression getInitializerExpression() {
        return init;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#setInitializerExpression(com.nokia.sdt.sourcegen.doms.rss.dom.IAstInitializerExpression)
     */
    public void setInitializerExpression(IAstInitializerExpression expr) {
        if (this.init != null)
            this.init.setParent(null);
        this.init = expr;
        if (expr != null)
            expr.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#getArrayDeclarator()
     */
    public IAstArrayDeclarator getArrayDeclarator() {
        return arrayDecl;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#setArrayDeclarator(com.nokia.sdt.sourcegen.doms.rss.dom.IAstArrayDeclarator)
     */
    public void setArrayDeclarator(IAstArrayDeclarator decl) {
        if (this.arrayDecl != null)
            this.arrayDecl.setParent(null);
        this.arrayDecl = decl;
        if (decl != null)
            decl.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#getLengthPrefixDeclarator()
     */
    public IAstLengthPrefixDeclarator getLengthPrefixDeclarator() {
        return lengthPrefix;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#setLengthPrefixDeclarator(com.nokia.sdt.sourcegen.doms.rss.dom.IAstLengthPrefixDeclarator)
     */
    public void setLengthPrefixDeclarator(IAstLengthPrefixDeclarator decl) {
        if (this.lengthPrefix != null)
            this.lengthPrefix.setParent(null);
        this.lengthPrefix = decl;
        if (decl != null)
            decl.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstStringMemberDeclaration#getLengthLimit()
     */
    public IAstExpression getLengthLimit() {
        return lengthLimit;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstStringMemberDeclaration#setLengthLimit(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setLengthLimit(IAstExpression expr) {
        if (this.lengthLimit != null)
            this.lengthLimit.setParent(null);
        this.lengthLimit = expr;
        if (expr != null)
            expr.setParent(this);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstStringMemberDeclaration#hasTemplateStyle()
     */
    public boolean hasTemplateStyle() {
        return templateStyle;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstStringMemberDeclaration#setTemplateStyle(boolean)
     */
    public void setTemplateStyle(boolean templateStyle) {
        this.templateStyle = templateStyle;
        dirty = true;
    }

    protected void getChildrenList(List list) {
        if (lengthPrefix != null) 
            list.add(lengthPrefix);
        if (lengthLimit != null) 
            list.add(lengthLimit);
        //list.add(memberType); // not owned
        list.add(name);
        if (arrayDecl != null)
            list.add(arrayDecl);
        if (init != null)
            list.add(init);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        List list = new ArrayList();
        getChildrenList(list);
        return (IAstNode[]) list.toArray(new IAstNode[list.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        List list = new ArrayList();
        getChildrenList(list);
        list.add(memberType);
        return (IAstNode[]) list.toArray(new IAstNode[list.size()]);
    }


    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameHolder#getRoleForName()
     */
    public int getRoleForName() {
        return IAstNameHolder.NAME_DEFINED;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberDeclaration#getStructType()
     */
    public IAstStructDeclaration getStructType() {
    	IAstNode parentParent;
    	if (getParent() == null)
    		return null;
    	parentParent = getParent().getParent();
    	if (parentParent == null)
    		return null;
        Check.checkState(parentParent instanceof IAstStructDeclaration);
        return (IAstStructDeclaration) parentParent;
    }

}
