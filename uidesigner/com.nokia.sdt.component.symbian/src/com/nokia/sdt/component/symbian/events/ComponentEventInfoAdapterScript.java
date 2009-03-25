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
package com.nokia.sdt.component.symbian.events;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IComponentEventInfo;
import com.nokia.sdt.scripting.ScriptException;

public class ComponentEventInfoAdapterScript extends ScriptAdapterImplBase implements IComponentEventInfo {
	public ComponentEventInfoAdapterScript() {
        super(IComponentEventInfo.class, IScriptComponentEventInfo.class);
	}

    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
    
	public String[] getEventGroups() {
		String[] groups = null;
        try {
    	    groups = (String[]) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                	WrappedInstance instance = getWrappedInstance();
                    return ((IScriptComponentEventInfo) getImpl()).getEventGroups(instance);
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
        return groups;
	}

	public String getDefaultEventName() {
		String defaultEvent = null;
        try {
        	defaultEvent = (String) invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                	WrappedInstance instance = getWrappedInstance();
                    return ((IScriptComponentEventInfo) getImpl()).getDefaultEventName(instance);
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
        return defaultEvent;
	}
}
