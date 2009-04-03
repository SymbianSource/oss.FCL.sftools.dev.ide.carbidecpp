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
package com.nokia.sdt.component.symbian.propertyListener;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.ScriptException;

import org.eclipse.emf.ecore.EObject;

public class PropertyListenerAdapterScript extends ScriptAdapterImplBase implements IComponentInstancePropertyListener {
	
	public PropertyListenerAdapterScript() {
        super(IComponentInstancePropertyListener.class, IScriptPropertyListener.class);
	}
	
    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
    
	public void propertyChanged(final EObject eObject, final Object propertyId) {
        try {
            invokeScriptCode(eObject, new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) {
                    // nothing new
                }
                
                public Object run() {
                	WrappedInstance wrappedInstance = getWrappedInstance(eObject);
                	ILookAndFeel laf = null;
                	IDisplayModel displayModel = ModelUtils.getExistingDisplayModel(eObject);
                	if (displayModel != null) {
                		laf = displayModel.getLookAndFeel();
                	}
                	
                	((IScriptPropertyListener)getImpl()).propertyChanged(wrappedInstance, propertyId, laf);
                	return null;
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
	}
}
