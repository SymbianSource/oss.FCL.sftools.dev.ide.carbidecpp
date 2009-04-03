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
 * A simple type
 * 
 * 
 */
public interface IAstSimpleDeclaration extends IAstDeclaration {
    public static final int K_BYTE = 1;
    public static final int K_WORD = 2;
    public static final int K_LONG = 3;
    public static final int K_TEXT = 4;
    public static final int K_DOUBLE = 5;
    public static final int K_LTEXT = 6;
    public static final int K_BUF = 7;
    public static final int K_BUF8 = 8;
    public static final int K_LINK = 9;
    public static final int K_SRLINK = 10;
    public static final int K_STRUCT = 11;
    public static final int K_LLINK = 12;
    public static final int K_LTEXT8 = 13;
    public static final int K_TEXT16 = 14;
    public static final int K_LTEXT16 = 15;
    public static final int K_LAST = 15;
    
    /** Get the type kind */
    public int getKind();
    
    /** Set the type kind */
    public void setKind(int kind);
}
