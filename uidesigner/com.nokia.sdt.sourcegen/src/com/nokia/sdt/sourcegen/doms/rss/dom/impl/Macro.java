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

import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IMacro;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public abstract class Macro implements IMacro {

    private String name;
    private String expansion;

    /**
     * 
     */
    public Macro(String name, String expansion) {
        setName(name);
        setExpansion(expansion);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IMacro#getName()
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IMacro#setName(java.lang.String)
     */
    public void setName(String name) {
        Check.checkArg(name);
        this.name = name;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IMacro#getExpansion()
     */
    public String getExpansion() {
        return expansion;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IMacro#setExpansion(java.lang.String)
     */
    public void setExpansion(String exp) {
    	Check.checkArg(exp);
        this.expansion = exp;
    }

}
