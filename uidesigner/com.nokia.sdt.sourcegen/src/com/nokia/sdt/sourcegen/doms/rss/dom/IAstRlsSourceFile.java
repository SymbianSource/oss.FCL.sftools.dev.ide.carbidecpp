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


/**
 * This represents the contributions of a *.rls
 * file in Series 60.  It should be populated with
 * IAstRlsString nodes defining the string
 * constants.
 * <p>
 * This is similar to IAstLxxSourceFile, but no
 * intermediary *.loc file is used, and the extension
 * does not encode the language (the filename does).
 * 
 * 
 *
 */
public interface IAstRlsSourceFile extends IAstRssSourceFile {
    /** Get the language code this file encodes */
    public int getLanguageCode();
    
    /** Set the language code this file encodes */
    public void setLanguageCode(int languageCode);
}
