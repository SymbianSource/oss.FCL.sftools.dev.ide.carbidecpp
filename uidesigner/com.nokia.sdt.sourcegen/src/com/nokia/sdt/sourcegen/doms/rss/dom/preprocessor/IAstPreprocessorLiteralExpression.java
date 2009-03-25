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

import com.nokia.sdt.sourcegen.doms.rss.dom.IAstLiteralExpression;

/**
 * A constant literal in a preprocessor expression
 * 
 *
 */
public interface IAstPreprocessorLiteralExpression extends IAstLiteralExpression {
    public static final int K_BOOLEAN = IAstLiteralExpression.K_LAST+1;
    public static final int K_LAST = K_BOOLEAN;
}
