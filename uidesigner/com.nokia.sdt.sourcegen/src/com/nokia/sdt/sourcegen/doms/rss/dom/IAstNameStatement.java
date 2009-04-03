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
 * RSS NAME statement
 * 
 * 
 *
 */
public interface IAstNameStatement extends IAstTopLevelNode {
    /** Get the short name */
    public String getShortName();
    
    /** Set the short name (must be 1-4 alphabetic characters) */
    public void setShortName(String name);
}
