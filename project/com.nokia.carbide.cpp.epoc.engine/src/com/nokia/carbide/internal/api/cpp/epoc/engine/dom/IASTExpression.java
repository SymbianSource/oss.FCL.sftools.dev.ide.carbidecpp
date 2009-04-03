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

package com.nokia.carbide.internal.api.cpp.epoc.engine.dom;

/**
 * Root interface for expression nodes
 *
 */
public interface IASTExpression extends IASTNode {
    /** 
     * Get a simplified version of the expression, by removing
     * meaningless nodes and attempting to replace constant operations 
     * with literal values (IAstLiteralExpression).
     * <p>
     * This does NOT necessarily return a unique IAstExpression!
     * 
     * @return IAstExpression
     */
    public IASTExpression simplify();
    
    /** 
     * Compare equality, value-wise.  This assumes the
     * expressions are of the same structure -- call
     * simplify() first if necessary.
     * 
     * @see #simplify()
     */
    public boolean equalValue(IASTExpression expr);
}
