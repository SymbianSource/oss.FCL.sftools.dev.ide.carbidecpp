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

package com.nokia.sdt.sourcegen.core;

import com.nokia.sdt.component.event.EventUtils;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IEventBinding;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.sourcegen.ISourceGenSession;
import com.nokia.sdt.sourcegen.IVariableProvider;
import com.nokia.sdt.symbian.ISymbianSourceGenSession;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Map;

/**
 * Provide variables for script and sourcegen. 
 * 
 *
 */
public class VariableProvider implements IVariableProvider {
	private ISourceGenSession sourceGenSession;
	
	public VariableProvider(ISourceGenSession session, String projectName) {
		Check.checkArg(session);
		Check.checkArg(projectName);
		this.sourceGenSession = session;
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.sourcegen.INameGenerator#defineInstanceVariables(java.util.Map, org.eclipse.emf.ecore.EObject)
     */
    public void defineInstanceVariables(Map variables, EObject instanceObj) {
        IComponentInstance instance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(instanceObj, IComponentInstance.class);
        Check.checkArg(instance);
        IDesignerDataModel dataModel = instance.getDesignerDataModel();
        
        variables.put("projectName", sourceGenSession.getSourceGenProvider().getProjectName()); //$NON-NLS-1$
        
        variables.put("instanceName", instance.getName()); //$NON-NLS-1$
        variables.put("instanceMemberName", "i" + TextUtils.titleCase(instance.getName())); //$NON-NLS-1$ //$NON-NLS-2$
        variables.put("instanceName$title", TextUtils.titleCase(instance.getName())); //$NON-NLS-1$
        variables.put("instanceName$upper", instance.getName().toUpperCase()); //$NON-NLS-1$
        variables.put("instanceName$lower", instance.getName().toLowerCase()); //$NON-NLS-1$
       
        if (sourceGenSession instanceof ISymbianSourceGenSession) {
            // define the name of the resource, if any, created for this instance
            ISymbianSourceGenSession symSession = (ISymbianSourceGenSession) sourceGenSession;
            String name = symSession.getGeneratedResource(instance);
            variables.put("resourceName", name); //$NON-NLS-1$
            variables.put("resourceName$upper", name != null ? name.toUpperCase() : null); //$NON-NLS-1$
            
            // define the filename for this file's resources
            name = symSession.getResourceFileNameBase();
            variables.put("resourceFileNameBase", name); //$NON-NLS-1$
        }

        // Define the name of the class, if there is one.
        String className = getClassName(instance);
        if (className != null)
        	variables.put("className", className); //$NON-NLS-1$

        // Define the parent's class name, if there is one. 
        // This is in case the instance has a class, and it wants to define a location
        // in its parent's class. Otherwise, it would always find its own class.
        EObject parent = instance.getParent();
        if (parent != null) {
        	IComponentInstance parentInstance = ModelUtils.getComponentInstance(parent);
        	if (parentInstance != null) {
        		String parentClassName = getClassName(parentInstance);
        		if (parentClassName != null)
        			variables.put("parentClassName", parentClassName); //$NON-NLS-1$
        	}
        }
        
        
        
        // Define the name of the event handler class, if any.
        String handlerClassName = EventUtils.getHandlerClassName(instance);
        if (handlerClassName != null)
        	variables.put("handlerClassName", handlerClassName); //$NON-NLS-1$
        IComponentInstance handlerInstance = EventUtils.getHandlerInstance(instance);
        if (handlerInstance != null) {
        	// TODO: I wanted to expose "handlerInstance" as a WrappedInstance, but
        	// that's not currently accessible from this plugin
        	variables.put("handlerInstanceName", handlerInstance.getName()); //$NON-NLS-1$
        }
        
        // directory variables for locations
        variables.put("src", getPropertyOrDot(dataModel, DesignerDataModel.SOURCE_DIRECTORY_ID)); //$NON-NLS-1$
        variables.put("inc", getPropertyOrDot(dataModel, DesignerDataModel.INCLUDE_DIRECTORY_ID)); //$NON-NLS-1$
        variables.put("build", getPropertyOrDot(dataModel, DesignerDataModel.BUILD_DIRECTORY_ID)); //$NON-NLS-1$
        variables.put("resource", getPropertyOrDot(dataModel, DesignerDataModel.RESOURCE_DIRECTORY_ID)); //$NON-NLS-1$

    }
    
    
    private String getClassName(IComponentInstance instance) {
        // get the name of the class, if there is one.
        // This is either the instance's own "className" property or that of an enclosing parent.
        IComponentInstance ci = instance;
        while (ci != null) {
            IPropertySource propSource = ModelUtils.getPropertySource(ci.getEObject());
            if (propSource != null) {
                Object val = propSource.getPropertyValue("className"); //$NON-NLS-1$
                if (val != null) {
                    if (val.equals("")) //$NON-NLS-1$
                        return "CUnnamedClass"; //$NON-NLS-1$
                    else
                        return val.toString();
                }
            }
            EObject parentObj = ci.getParent();
            if (parentObj != null)
                ci = ModelUtils.getComponentInstance(parentObj);
            else
                ci = null;
        }
    	
        return null;
    }

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.INameGenerator#defineEventVariables(java.util.Map, com.nokia.sdt.datamodel.adapter.IEventBinding)
	 * 
	 */
	public void defineEventVariables(Map variables, IEventBinding binding) {
		// @see WrappedEventBinding for naming convention
		if (binding != null) {
			variables.put("event.eventId", binding.getEventDescriptor().getId()); //$NON-NLS-1$
			variables.put("event.eventName", binding.getEventDescriptor().getDisplayText()); //$NON-NLS-1$
			variables.put("event.handlerName", binding.getHandlerName()); //$NON-NLS-1$
			variables.put("event.handlerSymbol", binding.getHandlerSymbolInformation()); //$NON-NLS-1$
		} else {
			variables.remove("event.eventId"); //$NON-NLS-1$
			variables.remove("event.eventName"); //$NON-NLS-1$
			variables.remove("event.handlerName"); //$NON-NLS-1$
			variables.remove("event.handlerSymbol"); //$NON-NLS-1$
		}
	}

	protected String getPropertyOrDot(IDesignerDataModel dataModel, String propertyId) {
	    String value = dataModel.getProperty(propertyId);
	    if (value != null)
	        return value;
	    else
	        return "."; //$NON-NLS-1$
	}
}
