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

package com.nokia.sdt.sourcegen.doms.rss.dom;

import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorDefineDirective;


/**
 * This represents the contributions of a *.l<i>xx</i>
 * file in Series 60.  It should be populated with
 * IAstPreprocessorDefineDirective nodes representing
 * macros defining string constants. 
 * 
 * 
 *
 */
public interface IAstLxxSourceFile extends IAstRssSourceFile {
    /** Get the language code this file encodes */
    public int getLanguageCode();
    
    /** Set the language code this file encodes */
    public void setLanguageCode(int languageCode);
    
    /** Get the string macros, which are #defines a certain form:
     * #define NAME "string"
     */
    public IAstPreprocessorDefineDirective[] getStringMacros();
}
