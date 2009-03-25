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
 * An rss UID2, UID3 statement
 * 
 *
 */
public interface IAstUidStatement extends IAstTopLevelNode {
    /**
     * Get the UID number (2 or 3)
     * @return number
     */
    public int getWhich();
    
    /**
     * Set the UID number
     * @param which 2 or 3
     */
    public void setWhich(int which);
    
    /**
     * Get the UID
     */
    public IAstLiteralExpression getUid();
    
    /**
     * Set the UID
     */
    public void setUid(IAstLiteralExpression uid);
}
