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
package com.nokia.sdt.component.symbian.initializer;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IInitializer;
import com.nokia.sdt.scripting.ScriptException;

public class InitializerAdapterScript extends ScriptAdapterImplBase implements IInitializer {
	public InitializerAdapterScript() {
        super(IInitializer.class, IScriptInitializer.class);
	}

    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
    
	public void initialize(final boolean isConfigured) {
        try {
    	    invokeScriptCode(getEObject(), new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) throws ScriptException {
                }

                public Object run() {
                    WrappedInstance instance = getWrappedInstance();
                    ((IScriptInitializer) getImpl()).initialize(instance, isConfigured);
                    return null;
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
	}

    

}
