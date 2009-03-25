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
 * Array declarator, e.g. "[67]" or "[]"
 * 
 * 
 *
 */
public interface IAstArrayDeclarator extends IAstDeclarator {
    /** Get size of array 
     * 
     * @return expression defining size of array, or null for unbounded ([])
     */
    public IAstExpression getArraySize();

    /** Set size of array
     * 
     * @param expr expression indicating size, or null for []
     */
    public void setArraySize(IAstExpression expr);
}
