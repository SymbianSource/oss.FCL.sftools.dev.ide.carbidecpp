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
 * Interface for nodes which hold IAstNames telling how the name
 * is used.
 * <p>
 * When IAstNameHolder#getRoleForName() returns
 * NAME_DEFINED, then IAstName#getParent() refers to the node
 * that owns the name.  Otherwise (NAME_REFERENCED), IAstName
 * is not owned by the node holding the name.
 * 
 * 
 *
 */
public interface IAstNameHolder {
    static public final int NAME_DEFINED = 0;
    static public final int NAME_REFERENCED = 1;
    
    /** Tell how the name is used */
    public int getRoleForName();
}
