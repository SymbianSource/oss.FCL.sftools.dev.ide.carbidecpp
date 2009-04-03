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
 * This represents the "#ifdef LANG_xx" + "#include 'foo_xx.rls'" + "#endif"
 * directives that bring in an RLS include.  We group these together
 * so other preprocessor nodes are not inadvertently inserted between them.
 * 
 *
 */
public interface IAstPreprocessorRlsIncludeNode extends
        IAstPreprocessorIncludeDirective {
    /** Get the language */
    public int getLanguageCode();

    /** Set the language */
    public void setLanguageCode(int langCode);
}
