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
 * An expression defining an initializer.  This implicitly contains
 * an "=".
 * 
 *
 */
public interface IAstInitializerExpression extends IAstInitializer {
    /** Get the initializer expression (never null) */
    public IAstExpression getExpression();
    
    /** Set the initializer expression (must not be null) */
    public void setExpression(IAstExpression expr);
}
