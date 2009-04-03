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

import com.nokia.sdt.datamodel.adapter.IEventBinding;

import org.eclipse.emf.ecore.EObject;

import java.util.Map;

/**
 * This interface is used to provide variables to script
 * and sourcegen.
 * 
 * 
 *
 */
public interface IVariableProvider {

    /** Register any variables for use by one-way source generation.
     * These are visible globally for &lt;template&gt; and &lt;inline&gt;
     * use as well as for &lt;location&gt; attributes
     * @param variables a map of String(id) -> String(value) [update]
     * @param instanceObj the component instance 
     */
    public void defineInstanceVariables(Map variables, EObject instanceObj);

    /** Register any variables for use by one-way source generation
     * when an event is bound.  Add variables when the binding is
     * defined, and remove them if it is null.
     * @param variables a map of String(id) -> String(value) [update]
     * @param binding the bound event, or null 
     */
    public void defineEventVariables(Map variables, IEventBinding binding);
}
