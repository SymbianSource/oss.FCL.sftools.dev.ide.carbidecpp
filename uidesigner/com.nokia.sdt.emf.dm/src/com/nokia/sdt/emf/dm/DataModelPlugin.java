/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.emf.dm;

import org.eclipse.core.runtime.Plugin;

public class DataModelPlugin extends Plugin {

    private static DataModelPlugin instance;

    public DataModelPlugin() {
        super();
        instance = this;
    }

    public static DataModelPlugin getDefault() {
        return instance;
    }
}
