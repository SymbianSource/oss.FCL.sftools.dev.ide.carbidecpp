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

package com.nokia.sdt.component.symbian.sourcegen;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;

import java.util.List;

/**
 * Script interface implementing sourcegen
 * 
 *
 */
public interface IScriptSourceGen {
    /** Generate contributions defining this location */ 
    public List generateLocation(WrappedInstance instance, WrappedInstance refInstance, String form, String locationId);

    /** Generate contributions */ 
    public List generate(WrappedInstance instance, WrappedInstance refInstance, String form);
}