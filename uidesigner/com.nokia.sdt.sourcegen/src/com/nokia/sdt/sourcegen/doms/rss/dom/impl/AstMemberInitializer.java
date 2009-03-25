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
public class AstMemberInitializer extends AstInitializer implements
        IAstMemberInitializer {

    private IAstExpression memberExpr;
    private IAstMemberDeclaration member;
    private IAstInitializerExpression initExpr;
    private IAstExpression lengthLimit;

    /**
     */
    public AstMemberInitializer(IAstExpression memberExpr, IAstInitializerExpression initExpr) {
        super();
        setMemberExpression(memberExpr);
        setInitializerExpression(initExpr);
        dirty = false;
    }

    /**
     */
    public AstMemberInitializer(IAstExpression memberExpr, IAstExpression lengthLimit, IAstInitializerExpression initExpr) {
        super();
        setMemberExpression(memberExpr);
        setLengthLimit(lengthLimit);
        setInitializerExpression(initExpr);
        dirty = false;
    }

    /**
     * Short form to create an initializer
	 * @param name name of member
	 * @param expression the value to initialize
	 */
	public AstMemberInitializer(IAstStructDeclaration structDecl, String name, IAstExpression expression) {
		this(new AstIdExpression(structDecl.findMember(name).getMemberName()),
					new AstInitializerExpression(expression));
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#toString()
	 */
	@Override
	public String toString() {
		return member.getStructType().getStructName().getName() + "@" + dump();
	}
	
	/* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
    	List segments = new ArrayList();
    	segments.add(memberExpr);
    	segments.add(ISourceFormatter.SEGMENT_FORMATTING_SPACE);
   		segments.add(initExpr);
    	segments.add(";"); //$NON-NLS-1$
    	if (getParent() != null)
    		segments.add(ISourceFormatter.SEGMENT_NEWLINE);
    	return segments.toArray();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer#getMember()
     */
    public IAstMemberDeclaration getMember() {
        return member;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer#getMemberExpression()
     */
    public IAstExpression getMemberExpression() {
        return memberExpr;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer#setMemberExpression(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setMemberExpression(IAstExpression expr) {
        Check.checkArg(expr);
        
        // make sure we can find the IAstMemberDeclaration
        if (expr instanceof IAstIdExpression) {
            this.member = findMember((IAstIdExpression) expr);
            Check.checkContract(this.member != null);
        } else if (expr instanceof IAstBinaryExpression && 
                ((IAstBinaryExpression) expr).getOperator() == IAstBinaryExpression.K_SUBSCRIPT) {
            IAstNode lhs = ((IAstBinaryExpression) expr).getLeftOperand();
            Check.checkContract(lhs instanceof IAstIdExpression);
            this.member = findMember((IAstIdExpression) lhs);
            Check.checkContract(this.member != null);
        } else {
            // unrecognized form of memberExpr
            Check.checkContract(false);
        }
        if (this.memberExpr != null)
            this.memberExpr.setParent(null);
        if (this.memberExpr == null || !this.memberExpr.equalValue(expr))
        	dirty = true;
        this.memberExpr = expr;
        expr.setParent(this);
    }

    /**
     * @param expr
     * @return the referenced IAstMemberDeclaration, or null
     */
    private IAstMemberDeclaration findMember(IAstIdExpression expr) {
        IAstName name = expr.getName();
        if (name.getScope() == null)
            return null;
        IAstNode owner = name.getScope().getOwner();
        if (owner != null && owner instanceof IAstCompositeTypeSpecifier)
            return ((IAstCompositeTypeSpecifier) owner).findMember(name.getName());
        else
            return null;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer#getInitializerExpression()
     */
    public IAstInitializerExpression getInitializerExpression() {
        return initExpr;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer#setInitializerExpression(com.nokia.sdt.sourcegen.doms.rss.dom.IAstInitializerExpression)
     */
    public void setInitializerExpression(IAstInitializerExpression expr) {
        Check.checkArg(expr);
        if (this.initExpr != null)
        	this.initExpr.setParent(null);
        if (this.initExpr == null || !this.initExpr.getExpression().equalValue(expr.getExpression()))
        	dirty = true;
        this.initExpr = expr;
        expr.setParent(this);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer#getLengthLimit()
     */
    public IAstExpression getLengthLimit() {
        return lengthLimit;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstMemberInitializer#setLengthLimit()
     */
    public void setLengthLimit(IAstExpression lengthLimit) {
        if (this.lengthLimit != null)
            lengthLimit.setParent(null);
        this.lengthLimit = lengthLimit;
        if (lengthLimit != null)
            lengthLimit.setParent(this);
        dirty = true;
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { initExpr, memberExpr };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }
}
