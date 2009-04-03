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

package com.nokia.sdt.sourcegen.contributions.domains.cpp;

/**
 * Vastly simplified interface to manipulate source text
 * by replacing spans of existing text with new text.
 * This can insert and delete text.
 * 
 * 
 *
 */
public interface ITextReplacer {
    /**
     * Replace the text at the given offset and length with
     * the new text.  
     * @param offset
     * @param length
     * @param text
     */
    void replaceText(int offset, int length, String text);
}
