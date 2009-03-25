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

package com.nokia.sdt.sourcegen.doms.rss.dom.preprocessor;

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstExpression;

/**
 * A conditional expression (cond ? trueExpr : falseExpr)
 * 
 * 
 *
 */
public interface IAstPreprocessorTrinaryExpression extends
        IAstExpression {
    
    /** Get the condition */
    public IAstExpression getCondition();

    /** Set the condition */
    public void setCondition(IAstExpression cond);

    /** Get the true branch */
    public IAstExpression getTrueBranch();

    /** Set the true branch */
    public void setTrueBranch(IAstExpression cond);

    /** Get the false branch */
    public IAstExpression getFalseBranch();

    /** Set the false branch */
    public void setFalseBranch(IAstExpression cond);

}
