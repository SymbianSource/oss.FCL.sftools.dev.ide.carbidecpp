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

package com.nokia.sdt.sourcegen.templates.core;

import com.nokia.sdt.sourcegen.templates.frontend.Node;


/**
 * This class encapsulates the template parser and translator.
 * Clients should create an IFrontEnd and IBackEnd object,
 * an IMessageHandler class, register them with this 
 * class, and then invoke compile().
 * 
 * 
 *
 */
public class TemplateCompiler {
    private IFrontEnd frontend;
    private IBackEnd backend;
    
    public TemplateCompiler(IFrontEnd fe, IBackEnd be) {
        if (fe == null || be == null)
            throw new IllegalArgumentException();

        frontend = fe;
        backend = be;
    }
    
    /** 
     * Compile by parsing with the frontend and translating with
     * the backend.
     * <p>
     * frontend and backend should be configured prior to calling.
     */
    public void compile() {
        if (frontend == null)
            throw new IllegalStateException("no frontend defined"); //$NON-NLS-1$
        if (backend == null)
            throw new IllegalStateException("no backend defined"); //$NON-NLS-1$

        Node nodes = frontend.parse();
        backend.translate(nodes);
    }

}
