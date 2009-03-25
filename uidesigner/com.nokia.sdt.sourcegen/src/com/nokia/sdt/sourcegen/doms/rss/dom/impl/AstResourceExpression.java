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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public class AstResourceExpression extends AstResource implements
        IAstResourceExpression {

    /**
     * @param structType
     */
    public AstResourceExpression(IAstStructDeclaration structType) {
        super(structType);
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstResource#getTextSegments()
     */
    public Object[] getTextSegments() {
        List list = new ArrayList();
        // we're not defining the type name, so provide its text
        list.add(getStructType().getStructName().getName());
        list.add(ISourceFormatter.SEGMENT_FORMATTING_LBRACE);
        list.add(initializers);
        list.add(ISourceFormatter.SEGMENT_FORMATTING_RBRACE);
        return list.toArray();
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
        return false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstResource#getReferencedNodes()
     */
    /*public IAstNode[] getReferencedNodes() {
        List kids = new ArrayList(Arrays.asList(super.getReferencedNodes()));
        kids.add(getStructType().getStructName());
        return (IAstNode[]) kids.toArray(new IAstNode[kids.size()]);
    }*/
}
