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
 * 
 *
 */
public interface IAstLengthPrefixDeclarator extends IAstDeclarator {
    /** Type uses BYTE prefix */
    public static final int K_BYTE_PREFIXED = 1;
    /** Type uses WORD prefix */
    public static final int K_WORD_PREFIXED = 2;

    /** Get the length prefix
     * 
     * @return one of K_BYTE_PREFIXED or K_WORD_PREFIXED
     */
    public int getLengthPrefix();
    
    /** Set the length prefix 
     * 
     * @param prefix one of K_BYTE_PREFIXED or K_WORD_PREFIXED
     */
    public void setLengthPrefix(int prefix);
}
