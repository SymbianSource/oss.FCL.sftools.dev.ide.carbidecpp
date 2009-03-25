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
 * A macro taking arguments, like a function.  Zero arguments are allowed. 
 * 
 *
 */
public interface IFunctionStyleMacro extends IMacro {
    /** Get the formal macro parameter */
    public String[] getParameters();
    
    /** Add a formal macro parameter */
    public void addParameter(String name);
}
