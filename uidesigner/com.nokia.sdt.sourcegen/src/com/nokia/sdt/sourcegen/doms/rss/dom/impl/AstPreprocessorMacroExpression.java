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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression;
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public class AstPreprocessorMacroExpression extends AstPreprocessorExpression
        implements IAstPreprocessorMacroExpression {

    private IMacro macro;
    private IAstPreprocessorTextNode[] parameters;
    
    /**
     * 
     */
    public AstPreprocessorMacroExpression(IMacro macro, IAstPreprocessorTextNode[] parameters) {
        super();
        setMacro(macro);
        setParameters(parameters);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        List ret = new ArrayList();
        ret.add(macro.getName());
        if (macro instanceof IFunctionStyleMacro) {
            ret.add("("); //$NON-NLS-1$
            for (int i = 0; i < parameters.length; i++) {
                ret.add(parameters[i]);
                if (i+1 < parameters.length) {
                    ret.add(","); //$NON-NLS-1$
                    ret.add(ISourceFormatter.SEGMENT_FORMATTING_SPACE);
                }
            }
            ret.add(")"); //$NON-NLS-1$
        }
        return ret.toArray();
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorMacroExpression#getMacro()
     */
    public IMacro getMacro() {
        return macro;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorMacroExpression#setMacro(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IMacro)
     */
    public void setMacro(IMacro macro) {
        Check.checkArg(macro);
        this.macro = macro;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorMacroExpression#getParameters()
     */
    public IAstPreprocessorTextNode[] getParameters() {
        return parameters;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorMacroExpression#setParameters(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTextNode[])
     */
    public void setParameters(IAstPreprocessorTextNode[] expr) {
        Check.checkArg(expr);
        this.parameters = expr;
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorMacroExpression#getAstNodesProduced()
     */
    public IAstNode[] getAstNodesProduced() {
        return IAstNode.NO_CHILDREN;
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
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return parameters;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return parameters;
    }

}
