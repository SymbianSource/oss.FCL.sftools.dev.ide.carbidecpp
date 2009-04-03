/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
/**
 * 
 */
package com.nokia.sdt.sourcegen;

/**
 * This is a base class for special formatting segments,
 * which act as instructions to a formatter.
 * 
 * @see ISourceFormatting
 *
 */
public class FormattingSegment {
    String value;
    public FormattingSegment(String value) {
        this.value = value;
    }
    public String toString() {
        return this.value;
    }
}