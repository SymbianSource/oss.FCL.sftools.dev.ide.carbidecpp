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

package com.nokia.sdt.component.symbian.initializer;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;

/**
 * This script interface is used to initialize an instance
 *
 */
public interface IScriptInitializer {
    /**
     * Initialize the instance
     * @param isConfigured TODO
     */
    public void initialize(WrappedInstance instance, boolean isConfigured);
}
