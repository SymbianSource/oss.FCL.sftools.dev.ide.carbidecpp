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

package com.nokia.sdt.component.symbian.test.scripting;

import com.nokia.sdt.datamodel.adapter.IComponentInstance;

import org.eclipse.ui.views.properties.IPropertySource;

public interface IScriptingPropertiesGlobalsComponentAccessor {
    // returns the instance global
    public IComponentInstance getInstance();

    // returns name property plus "foo"
    public String getNamePlusFoo();

    // returns size property as "x,y"
    public String getSizeFormatted();

    public IPropertySource getProperties();
}