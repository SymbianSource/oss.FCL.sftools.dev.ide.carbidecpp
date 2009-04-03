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
public class AstStructDeclaration extends AstCompositeTypeSpecifier implements
        IAstStructDeclaration {

    private IAstName name;
    private IAstLengthPrefixDeclarator lengthPrefix;

    /**
     */
    public AstStructDeclaration(IAstLengthPrefixDeclarator lengthPrefix, IAstName structName) {
        super();
        setLengthPrefixDeclarator(lengthPrefix);
        setStructName(structName);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstCompositeTypeSpecifier#toString()
     */
    public String toString() {
        return "AstStructDeclaration: " + name.getName() + "\n" + dump(); //$NON-NLS-1$
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        List list = new ArrayList();
        list.add("STRUCT "); //$NON-NLS-1$
        list.add(name);
        if (lengthPrefix != null) {
            list.add(" "); //$NON-NLS-1$
            list.add(lengthPrefix);
        }
        list.add(ISourceFormatter.SEGMENT_FORMATTING_LBRACE);
        list.add(members);
        list.add(ISourceFormatter.SEGMENT_FORMATTING_RBRACE);
        list.add(ISourceFormatter.SEGMENT_NEWLINE);
        return list.toArray();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstStructDeclaration#getStructName()
     */
    public IAstName getStructName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstStructDeclaration#setStructName(com.nokia.sdt.sourcegen.doms.rss.dom.IAstName)
     */
    public void setStructName(IAstName name) {
        Check.checkArg(name);
        if (this.name != null)
            this.name.setParent(null);
        this.name = name;
        name.setParent(this);
        scope.setScopeName(name);
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstStructDeclaration#getLengthPrefixDeclarator()
     */
    public IAstLengthPrefixDeclarator getLengthPrefixDeclarator() {
        return lengthPrefix;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstStructDeclaration#setLengthPrefixDeclarator(com.nokia.sdt.sourcegen.doms.rss.dom.IAstLengthPrefixDeclarator)
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
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNameHolder#getRoleForName()
     */
    public int getRoleForName() {
        return NAME_DEFINED;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstCompositeTypeSpecifier#getChildren()
     */
    public IAstNode[] getChildren() {
        List kids = new ArrayList();
        if (lengthPrefix != null)
            kids.add(lengthPrefix);
        kids.add(name);
        kids.add(members);
        return (IAstNode[]) kids.toArray(new IAstNode[kids.size()]);
    }
}
