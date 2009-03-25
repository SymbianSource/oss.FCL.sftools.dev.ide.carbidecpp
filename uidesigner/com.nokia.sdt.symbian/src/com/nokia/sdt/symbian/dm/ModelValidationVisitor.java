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
package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.property.*;
import com.nokia.sdt.datamodel.IModelMessage;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.Messages;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Used to validate a data model when loaded and saved
 */
public class ModelValidationVisitor implements ComponentInstanceVisitor.Visitor,
											PropertyVisitor.Visitor {

	private DesignerDataModel model;
	private Collection<IModelMessage> messages = new ArrayList<IModelMessage>();
	private IComponentInstance currentInstance;
	
	ModelValidationVisitor(DesignerDataModel model) {
		this.model = model;
	}
	
	public Object visit(IComponentInstance ci) {
		currentInstance = ci;		
		IComponent component = ci.getComponent();
		if (component == null) {
			String fmt = Messages.getString("ModelValidationVisitor.missingComponentValidationMessage"); //$NON-NLS-1$
			Object params[] = {ci.getName(), ci.getComponentId()};
			String message = MessageFormat.format(fmt, params);
			addMessage(IStatus.ERROR, message, null);
		}
		
		IComponentValidator validator = (IComponentValidator) EcoreUtil.getRegisteredAdapter(ci.getEObject(), IComponentValidator.class);
		if (validator != null) {
			Collection<IModelMessage> msgs = validator.validate(ci.getEObject());
			if (msgs != null) {
				messages.addAll(msgs);
			}
		}
		
		PropertyVisitor.visit(ci.getEObject(), this);
		currentInstance = null;
		
		return null;
	}
	
	public Collection<IModelMessage> getMessages() {
		return messages;
	}
	
	private void addMessage(int severity, String messageText, Object propertyID) {
		ModelMessage msg = new ModelMessage(severity,
				model.getModelSpecifier().createMessageLocation(),
				"ModelValidator.ModelMessage", //$NON-NLS-1$
				messageText, currentInstance.getComponentId(), 
				currentInstance.getName(),
				propertyID, null);
		messages.add(msg);
	}

	public boolean visit(IPropertyDescriptor pd, Object value) {
		// handle any properties that can validate themselves 
		// (used to be image-specific but ICompoundPropertyValueConverter2 now
		// encapsulates this)
		if (value instanceof IPropertySource) {
			IPropertySource ps = (IPropertySource) value;
			ICompoundPropertyValueConverter converter = 
				ModelUtils.getCompoundPropertyValueConverter(currentInstance.getEObject(), ps);
			
			if (converter instanceof ICompoundPropertyValueConverter2) {
				((ICompoundPropertyValueConverter2)converter).validate(getMessages(), pd, ps);
			}
		}
		return true;
	}

	public Object getCompletionResult() {
		// we always iterate until the end
		return null;
	}
}
