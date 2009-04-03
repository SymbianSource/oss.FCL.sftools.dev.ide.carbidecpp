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

package com.nokia.sdt.sourcegen;

import java.util.List;


/**
 * Information about a sourcegen template defined for a component
 * 
 *
 */
public interface ISourceGenTemplate {
    /** Get the name of the function defined */
    String getFunction();
    
    /** Get the template id */
    String getId();

    /** Get the template group id */
    String getGroup();
    
    /** Get the template forms as a space-separated list (or null) */
    String getForms();
    
    /** Get the ifEvents attribute, or null */
    List getIfEvents();
}
