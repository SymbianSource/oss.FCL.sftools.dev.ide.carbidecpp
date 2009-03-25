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
 * A preprocessor #define statement
 * 
 * 
 * 
 */
public interface IAstPreprocessorDefineDirective extends
        IAstPreprocessorDirective {
    /** Get the macro defined */
    public IMacro getMacro();
    
    /** Set the macro defined */
    public void setMacro(IMacro macro);
    
    /** Get the value of the macro (never null) */
    public IAstPreprocessorTextNode getMacroValue();
    
    /** Set the value of the macro (may not be null) */
    public void setMacroValue(IAstPreprocessorTextNode value);

}
