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
package com.nokia.sdt.component.symbian.childListener;

import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstanceChildListener;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.ScriptException;

import org.eclipse.emf.ecore.EObject;

public class ChildListenerAdapterScript extends ScriptAdapterImplBase implements IComponentInstanceChildListener {
	
	public ChildListenerAdapterScript() {
        super(IComponentInstanceChildListener.class, IScriptChildListener.class);
	}
	
    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
    
	public void childAdded(final EObject parent, final EObject child) {
        try {
            invokeScriptCode(parent, new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) {
                    // nothing new
                }
                
                public Object run() {
                	WrappedInstance wrappedParent = getWrappedInstance(parent);
                	WrappedInstance wrappedChild = getWrappedInstance(child);
                	ILookAndFeel laf = null;
                	IDisplayModel displayModel = ModelUtils.getExistingDisplayModel(parent);
                	if (displayModel != null) {
                		laf = displayModel.getLookAndFeel();
                	}
                	
                	((IScriptChildListener)getImpl()).childAdded(wrappedParent, wrappedChild, laf);
                	return null;
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
	}

	public void childRemoved(final EObject parent, final EObject child) {
        try {
            invokeScriptCode(parent, new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) {
                    // nothing new
                }
                
                public Object run() {
                	WrappedInstance wrappedParent = getWrappedInstance(parent);
                	// use parent's model to get wrapped child instance because child has already been removed
                	IDesignerDataModel model = wrappedParent.getDesignerDataModel();
                	WrappedInstance wrappedChild = getWrappedInstance(model, child);
                	ILookAndFeel laf = null;
                	IDisplayModel displayModel = ModelUtils.getExistingDisplayModel(parent);
                	if (displayModel != null) {
                		laf = displayModel.getLookAndFeel();
                	}
                	
                	((IScriptChildListener)getImpl()).childRemoved(wrappedParent, wrappedChild, laf);
                	return null;
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
	}

	public void childrenReordered(final EObject parent) {
        try {
            invokeScriptCode(parent, new IScriptCodeWrapper() {

                public void registerTransientGlobals(INameRegistrar registrar) {
                    // nothing new
                }
                
                public Object run() {
                	WrappedInstance wrappedParent = getWrappedInstance(parent);
                	ILookAndFeel laf = null;
                	IDisplayModel displayModel = ModelUtils.getExistingDisplayModel(parent);
                	if (displayModel != null) {
                		laf = displayModel.getLookAndFeel();
                	}
                	
                	((IScriptChildListener)getImpl()).childrenReordered(wrappedParent, laf);
                	return null;
                }

            });
        } catch (ScriptException e) {
            // already logged
        }
	}
}
