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
 * A conditional expression (cond ? trueExpr : falseExpr)
 *
 */
public interface IASTPreprocessorTrinaryExpression extends IASTPreprocessorExpression {
    
    /** Get the condition */
    public IASTPreprocessorExpression getCondition();

    /** Set the condition */
    public void setCondition(IASTPreprocessorExpression cond);

    /** Get the true branch */
    public IASTPreprocessorExpression getTrueBranch();

    /** Set the true branch */
    public void setTrueBranch(IASTPreprocessorExpression cond);

    /** Get the false branch */
    public IASTPreprocessorExpression getFalseBranch();

    /** Set the false branch */
    public void setFalseBranch(IASTPreprocessorExpression cond);

}
