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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;

import java.util.Map;

/**
 * This defines the context at the time contributions are generated.
 * 
 *
 */
public interface IContributionContext {
    /** Get the current instance */
    public IComponentInstance getInstance();
    /** Get the current form */
    public String getForm();
    /** Get the current referencing instance */
    public IComponentInstance getRefInstance();
    /** Get the map of String(id) -> String(value) pairs */ 
    public Map getVariables();
    /** Get the sourcegen */
    public IComponentSourceGen getComponentSourceGen();
}
