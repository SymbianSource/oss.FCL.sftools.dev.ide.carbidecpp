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

package com.nokia.sdt.sourcegen;


/**
 * Client-implemented interface describing the user preferences
 * for whitespace in source text.
 * 
 *
 */
public interface ISourceFormatting {
   
	/**
     * Get the end-of-line string
     */
    String getEOL();
    
    /**
     * Set the end-of-line string.  If null, default is used.
     */
    void setEOL(String eol);
    
	/** 
     * Get the tabs-vs-spaces setting
     */
    boolean isUsingTabs();
    
    /**
     * Get the indent amount when not using tabs
     */
    int getIndentSpaces();
}
