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

import com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor.IAstPreprocessorIncludeDirective;

/**
 * This node represents the contents of a .loc file.
 * <p>  
 * The content is a sequence of IAstPreprocessorIncludeDirective
 * nodes of IAstLxxSourceFile entries.  These contents are emitted
 * with #ifdef LANGUAGE_xx/#endif text around them (they are not
 * emitted as separate nodes).
 * 
 * 
 *
 */
public interface IAstLocSourceFile extends IAstRssSourceFile {
    /**
     * Get all the .lxx files
     */
    public IAstLxxSourceFile[] getLxxSourceFiles();

    /**
     * Add an .lxx file to the mix.
     * @return the include directive created
     * @throws AssertionError if already registered
     */
    public IAstPreprocessorIncludeDirective addLxxSourceFile(IAstLxxSourceFile file);

    /**
     * Remove an .lxx file
     * @throws AssertionError if not registered
     */
    public void removeLxxSourceFile(IAstLxxSourceFile file);

	/**
	 * Find a language file
	 * @param langCode
	 * @return
	 */
	public IAstLxxSourceFile findLxxFile(int langCode);

}
