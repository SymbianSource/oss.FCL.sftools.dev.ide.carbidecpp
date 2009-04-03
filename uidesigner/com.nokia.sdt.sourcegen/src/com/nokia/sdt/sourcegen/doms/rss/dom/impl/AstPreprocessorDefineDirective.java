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
import com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode;
import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public class AstPreprocessorDefineDirective extends AstPreprocessorDirective
        implements IAstPreprocessorDefineDirective {

	private IMacro macro;
	private IAstPreprocessorTextNode value;

    /**
     * Create a #define directive that defines given macro
     */
    public AstPreprocessorDefineDirective(IMacro macro) {
        super();
        setMacro(macro);
        dirty = false;
    }

    /**
     * Create a #define directive that defines given macro
     */
    public AstPreprocessorDefineDirective(IMacro macro, IAstPreprocessorTextNode value) {
        super();
        setMacro(macro);
        setMacroValue(value);
        dirty = false;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.impl.AstNode#getTextSegments()
     */
    public Object[] getTextSegments() {
        if (macro instanceof IFunctionStyleMacro) {
            List segs = new ArrayList();
            String[] args = ((IFunctionStyleMacro) macro).getParameters();
            segs.add("#define "); //$NON-NLS-1$
            segs.add(macro.getName());
            segs.add("("); //$NON-NLS-1$
            for (int i = 0; i < args.length; i++) {
                segs.add(args[i]);
                if (i + 1 < args.length)
                    segs.add(","); //$NON-NLS-1$
            }
            segs.add(") "); //$NON-NLS-1$
            segs.add(value);
            segs.add(ISourceFormatter.SEGMENT_NEWLINE);
            return segs.toArray();
        } else
            return new Object[] { 
        		"#define ", //$NON-NLS-1$ 
        		macro.getName(), 
        		" ", //$NON-NLS-1$
        		value, 
        		ISourceFormatter.SEGMENT_NEWLINE 
        		};
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective#getMacro()
     */
    public IMacro getMacro() {
        return macro;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective#setMacro(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IMacro)
     */
    public void setMacro(IMacro macro) {
        Check.checkArg(macro);
        this.macro = macro;
        setMacroValue(new AstPreprocessorTextNode(macro.getExpansion()));
        dirty = true;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getChildren()
     */
    public IAstNode[] getChildren() {
        return new IAstNode[] { value };
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.IAstNode#getReferencedNodes()
     */
    public IAstNode[] getReferencedNodes() {
        return getChildren();
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective#getMacroValue()
	 */
	public IAstPreprocessorTextNode getMacroValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective#setMacroValue(com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorTextNode)
	 */
	public void setMacroValue(IAstPreprocessorTextNode value) {
		Check.checkArg(value);
		Check.checkArg(value.getParent() == null);
		this.value = value;
		this.value.setParent(this);
		dirty = true;
	}

}
