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
package com.nokia.sdt.component.event;

import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.uimodel.Messages;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;

abstract public class EventUtils {

	/**
	 * Define the name of the event handler class, if any.
	 * This is the 'className' property from the nearest enclosing
	 * component with the EVENT_HANDLER_TARGET attribute.
	 * @param instance
	 * @return the className or null
	 */
	public static String getHandlerClassName(IComponentInstance instance) {
	    boolean found = false;
	    IComponentInstance parentInstance = instance;
	    while (!found && parentInstance != null) {
	    	if (parentInstance.getComponent() != null) {
	    		IAttributes attrs = (IAttributes) parentInstance.getComponent().getAdapter(IAttributes.class);
	    		if (attrs != null && attrs.getBooleanAttribute(
	    				CommonAttributes.EVENT_HANDLER_TARGET, false)) {
	                IPropertySource propSource = ModelUtils.getPropertySource(parentInstance.getEObject());
	                if (propSource != null) {
	                    Object val = propSource.getPropertyValue("className"); //$NON-NLS-1$
	                    if (val != null) {
	                        if (val.equals("")) //$NON-NLS-1$
	                        	return "CUnnamedClass"; //$NON-NLS-1$ //$NON-NLS-2$
	                        else
	                        	return (String) val; //$NON-NLS-1$
	                    } else {
	                    	Logging.log(UIModelPlugin.getDefault(),
	                    			Logging.newStatus(UIModelPlugin.getDefault(),
	                    					IStatus.ERROR,
	                    					MessageFormat.format(
	                    							Messages.getString("EventUtils.NoClassNameInEventHandlerTarget"), //$NON-NLS-1$
	                    							new Object[] { parentInstance.getComponentId(), 
	                    									CommonAttributes.EVENT_HANDLER_TARGET })));
	                    	return "CMissingClassNameProperty"; //$NON-NLS-1$
	                    }
	                }
	                found = true;
	    		}
	    	}
	        EObject parentObj = parentInstance.getParent();
	        if (parentObj != null)
	            parentInstance = ModelUtils.getComponentInstance(parentObj);
	        else
	            parentInstance = null;
	    }
	    return null;
	}

	/*
	 * Find the component instance which is the event handler for the given
	 * instance.
	 * @param instance the event source instance
	 * @return the event handler
	 */
	public static IComponentInstance getHandlerInstance(IComponentInstance instance) {
		IComponentInstance result = null;
	    IComponentInstance parentInstance = instance;
	    while (parentInstance != null) {
	    	if (parentInstance.getComponent() != null) {
	    		IAttributes attrs = (IAttributes) parentInstance.getComponent().getAdapter(IAttributes.class);
	    		if (attrs != null && attrs.getBooleanAttribute(
	    				CommonAttributes.EVENT_HANDLER_TARGET, false)) {
	    			result = parentInstance;
	    			break;
	    		}
	    	}
	        EObject parentObj = parentInstance.getParent();
	        if (parentObj != null)
	            parentInstance = ModelUtils.getComponentInstance(parentObj);
	        else
	            parentInstance = null;
	    }
	    return result;
	}

}
