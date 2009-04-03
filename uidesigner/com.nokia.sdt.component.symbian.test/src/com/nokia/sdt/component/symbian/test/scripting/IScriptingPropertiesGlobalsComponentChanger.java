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

public interface IScriptingPropertiesGlobalsComponentChanger {
    // returns name property
    public String getName();
    
    // sets name property to "_" + name
    public void setName(String name);

    // returns size property as "x,y"
    public String getSizeFormatted();

    // set size property
    public void setSize(int x, int y);
}