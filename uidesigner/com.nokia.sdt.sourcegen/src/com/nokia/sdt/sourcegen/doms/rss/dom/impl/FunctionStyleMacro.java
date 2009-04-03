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

import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IFunctionStyleMacro;
import com.nokia.cpp.internal.api.utils.core.Check;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 */
public class FunctionStyleMacro extends Macro implements IFunctionStyleMacro {

    private List parameters;

    /**
     * @param name
     * @param expansion
     */
    public FunctionStyleMacro(String name, String[] parameters, String expansion) {
        this(name, expansion);
        for (int i = 0; i < parameters.length; i++) {
            addParameter(parameters[i]);
        }
    }

    /**
     * @param name
     * @param expansion
     */
    public FunctionStyleMacro(String name, String expansion) {
        super(name, expansion);
        this.parameters = new ArrayList();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IFunctionStyleMacro#getParameters()
     */
    public String[] getParameters() {
        return (String[]) parameters.toArray(new String[parameters.size()]);
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IFunctionStyleMacro#addParameter(java.lang.String)
     */
    public void addParameter(String name) {
        Check.checkArg(!parameters.contains(name));
        parameters.add(name);
    }

}
