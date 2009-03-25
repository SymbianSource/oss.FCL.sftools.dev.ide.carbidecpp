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
 * Base declarator for a type (i.e. something which modifies it) 
 * 
 *
 */
public interface IAstDeclarator extends IAstNode {
    public static final IAstDeclarator[] EMPTY_ARRAY = new IAstDeclarator[0];
}
