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

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstBinaryExpression;

/**
 * A binary expression used in the preprocessor
 * 
 * 
 *
 */
public interface IAstPreprocessorBinaryExpression extends IAstPreprocessorExpression, IAstBinaryExpression {
    /** expr1&&expr2 */
    public static final int K_LOG_AND = IAstBinaryExpression.K_LAST+1;
    /** expr1||expr2 */
    public static final int K_LOG_OR = IAstBinaryExpression.K_LAST+2;
    /** last value for subclass extensions */
    public static final int K_LAST = K_LOG_OR;
    
}
