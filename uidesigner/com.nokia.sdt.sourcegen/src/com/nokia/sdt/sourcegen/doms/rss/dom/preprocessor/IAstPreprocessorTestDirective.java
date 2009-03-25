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

/**
 * A node representing preprocessor directive which tests
 * a compile-time evaluated expression (#if, #ifdef, #elif)
 * 
 * 
 *
 */
public interface IAstPreprocessorTestDirective extends IAstPreprocessorDirective {
    /** Get the expression tested */
    public IAstPreprocessorExpression getExpression();

    /** Set the expression */
    public void setExpression(IAstPreprocessorExpression expr);
    
    /** Tell whether this branch was taken in the current parse */
    public boolean isTaken();
    
    /** Tell whether this branch was taken in the current parse */
    public void setTaken(boolean taken);
}
