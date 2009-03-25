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
 * An #else statement
 * 
 * For this, getExpression() always returns <code>null</code>,
 * which should be interpreted as "true"
 * 
 * 
 *
 */
public interface IAstPreprocessorElseDirective extends IAstPreprocessorTestDirective {
    /** Tell whether the #else was taken in the current tree */
    public boolean isTaken();
    
    /** Tell whether the #else is taken in the current tree */
    public void setTaken(boolean taken);
    
}
