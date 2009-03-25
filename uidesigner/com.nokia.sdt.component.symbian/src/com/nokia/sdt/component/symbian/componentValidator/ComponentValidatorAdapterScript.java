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
package com.nokia.sdt.component.symbian.componentValidator;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.scripting.*;
import com.nokia.sdt.component.symbian.scripting.impl.ScriptAdapterImplBase;
import com.nokia.sdt.datamodel.IModelMessage;
import com.nokia.sdt.datamodel.adapter.IComponentValidator;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.scripting.ScriptException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;
import org.mozilla.javascript.Context;

import java.util.Collection;

public class ComponentValidatorAdapterScript extends ScriptAdapterImplBase
				implements IComponentValidator {

	public ComponentValidatorAdapterScript() {
        super(IComponentValidator.class, IScriptComponentValidator.class);
	}
	
    protected void registerScriptGlobals(INameRegistrar registrar) throws ScriptException {
        // none
    }
   
    public Collection<IModelMessage> validate(final EObject eObject) {
    	Collection<IModelMessage> result = null;
    	try {
    		result = (Collection<IModelMessage>) invokeScriptCode(eObject, new IScriptCodeWrapper() {
    			
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
    				Collection<IModelMessage> result = null;
    				Object obj = ((IScriptComponentValidator)getImpl()).validate(wrappedInstance, laf);
    				if (obj != null) {
    					result = ScriptingUtils.unwrapCollection(obj, false);
    					if (result == null)
    						Context.throwAsScriptRuntimeEx(new ScriptException("expected array or collection of IModelMessage", null));
    				}
    				return result;
    			}
    		});
    	} catch (ScriptException e) {
    		// already logged
    	}
    	return result;
    }

	public String queryPropertyChange(final EObject eObject, 
						final String propertyPath, 
						final Object newValue) {
		String result = null;
		try {
			Object objResult = invokeScriptCode(eObject, new IScriptCodeWrapper() {
				
				public void registerTransientGlobals(INameRegistrar registrar) {
					// nothing new
				}
				
				public Object run() {
					WrappedInstance wrappedInstance = getWrappedInstance(eObject);
					Object newValueForScript = null;
					if (newValue instanceof IPropertySource) {
				          newValueForScript = getWrappedProperties((IPropertySource) newValue);
					}
					else if (newValue != null) {
						newValueForScript = newValue.toString();
					}
					ILookAndFeel laf = null;
					try {
						IDisplayModel displayModel = ModelUtils.getDisplayModel(eObject);
						if (displayModel != null) {
							laf = displayModel.getLookAndFeel();
						}
					}
					catch (CoreException x) {
						ComponentSystemPlugin.log(x);
					}
					Object result = ((IScriptComponentValidator)getImpl()).queryPropertyChange(wrappedInstance, propertyPath, newValueForScript, laf);
					return result;
				}
			}
			);
			if (objResult != null) {
				result = objResult.toString();
			}
		} catch (ScriptException e) {
			// already logged
		}	
		return result;
	}
}
