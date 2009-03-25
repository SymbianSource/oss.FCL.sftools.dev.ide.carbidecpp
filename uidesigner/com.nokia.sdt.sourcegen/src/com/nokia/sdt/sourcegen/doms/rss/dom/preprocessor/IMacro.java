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

package com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor;

/**
 * A preprocessor macro
 * 
 * 
 * @see IObjectStyleMacro
 * @see IFunctionStyleMacro
 */
public interface IMacro {
    /**
     * Get the macro name
     * 
     * @return name
     */
    public String getName();

    /**
     * Set the macro name
     * 
     * @param name the name
     */
    public void setName(String name);
    
    /** 
     * Get the macro expansion (the literal text of the macro,
     * including continuation characters and newlines)
     */
    public String getExpansion();
    
    /**
     * Set the macro expansion
     */
    public void setExpansion(String exp);
}
