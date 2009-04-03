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

/**
 * 
 *
 */
public class AstInitializerExpression extends AstInitializer implements
        IAstInitializerExpression {

    private IAstExpression expr;

    /**
     */
    public AstInitializerExpression(IAstExpression expr) {
        super();
        setExpression(expr);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#constructText()
     */
    public Object[] getTextSegments() {
        if (expr instanceof IAstExpressionList)
        	if (getParent() instanceof IAstMemberInitializer
        			&& (((IAstMemberInitializer) getParent()).getMember().getMemberType().getKind()
        					== IAstSimpleDeclaration.K_STRUCT)) {
	            return new Object[] { 
		        		//SEGMENT_FORMATTING_SPACE, 
		        		"=", //$NON-NLS-1$
		        		ISourceFormatter.//SEGMENT_NEWLINE, 
		        		//SEGMENT_FORMATTING_INDENT,
		        		//"{", //$NON-NLS-1$
		        		//SEGMENT_NEWLINE,
		        		SEGMENT_FORMATTING_LBRACE,
		        		expr, 
		        		ISourceFormatter.//SEGMENT_NEWLINE, 
		        		//"}" //$NON-NLS-1$
		        		SEGMENT_FORMATTING_RBRACE
		        		};
        	} else {
                return new Object[] { 
                		//SEGMENT_FORMATTING_SPACE, 
                		"=", //$NON-NLS-1$
                		ISourceFormatter.SEGMENT_FORMATTING_SPACE, 
                		"{", //$NON-NLS-1$
                		ISourceFormatter.SEGMENT_FORMATTING_SPACE, 
                		expr, 
                		ISourceFormatter.SEGMENT_FORMATTING_SPACE, 
                		"}" //$NON-NLS-1$
                		};
        		
        	}
        else
            return new Object[] { 
        		//SEGMENT_FORMATTING_SPACE, 
        		"=", //$NON-NLS-1$
        		ISourceFormatter.SEGMENT_FORMATTING_SPACE, 
        		expr 
        		};
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstInitializerExpression#getExpression()
     */
    public IAstExpression getExpression() {
        return expr;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstInitializerExpression#setExpression(com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression)
     */
    public void setExpression(IAstExpression expr) {
        Check.checkArg(expr);
        if (this.expr != null)
            this.expr.setParent(null);
        if (this.expr == null || !this.expr.equalValue(expr)) 
        	dirty = true;
        this.expr = expr;
        expr.setParent(this);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { expr };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }


}
