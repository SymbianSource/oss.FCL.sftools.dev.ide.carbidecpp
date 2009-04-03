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


package com.nokia.sdt.component.symbian.implementations;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.scripting.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;

public interface IScriptImplAdapter extends Adapter {
	
    /**
     * Initialize the adapter with the given component, instance, and implementation
     * @param context 
     * @param scriptObject the script object which implements the desired prototype
     * @param instance the component instance to use as the global context when running the script
     * @throws ScriptException
     */
	void init(IScriptContext context, IScriptObject scriptObject, EObject instance, IComponent component) throws ScriptException;
	
    /**
     * Get the component
     */
    IComponent getComponent();
    
    /**
     * Get the implementation (script object) back
     * @return the implementation
     */
	IScriptObject getScriptObject();
	
}
