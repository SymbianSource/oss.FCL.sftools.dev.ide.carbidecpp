/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.utils.StatusHolder;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class Container extends AdapterImpl implements IContainer, IValidateContainment {

	protected IDisplayModel displayModel;
	protected IComponentInstance instance;
	protected final static String ERROR_MESSAGE_DELIM = " "; //$NON-NLS-1$
	private boolean containmentValidationEnabled;
	
	Container(IDisplayModel displayModel, IComponentInstance instance) {
		Check.checkArg(displayModel);
		Check.checkArg(instance);
		this.displayModel = displayModel;
		this.instance = instance;
		this.containmentValidationEnabled = true;
	}
	
	public boolean canReorderChild(EObject child) {
		if (Utilities.isNonLayoutObject(child))
			return true;
		
		IAttributes attributes = Utilities.getAttributes(displayModel, getEObject());
		return !attributes.getBooleanAttribute(CommonAttributes.FIXED_CHILD_ORDER, false);
	}
	
	public boolean canContainComponent(IComponent component, StatusHolder statusHolder) {
		if (ContainmentUtilities.allowIfNonLayoutComponent(displayModel, instance.getEObject(), component))
			return true;

		// first check static component constraints
		boolean result = 
			ContainmentUtilities.canAddComponentToContainer(displayModel, component, this, false, statusHolder);
		
		// then check to see if this component requires a query containment implementation
		StatusHolder tempHolder = new StatusHolder();
		boolean requiresContainmentCheck = 
			ContainmentUtilities.requiresContainmentQuery(component, this, tempHolder);
		if (statusHolder != null)
			statusHolder.appendStatus(tempHolder.getStatus(), ERROR_MESSAGE_DELIM);
		
		// See if this container has a IQueryContainment adapter
		IQueryContainment queryContainment =
			(IQueryContainment) EcoreUtil.getRegisteredAdapter(getEObject(), IQueryContainment.class);
		if ((queryContainment == null) && requiresContainmentCheck)
			result = false;
		
		if (queryContainment != null) {
			tempHolder = new StatusHolder();
			result = queryContainment.canContainComponent(component, tempHolder) && result;
			if (statusHolder != null)
				statusHolder.appendStatus(tempHolder.getStatus(), ERROR_MESSAGE_DELIM);
		}
		
		return result;
	}

	public boolean canContainChild(IComponentInstance child, StatusHolder statusHolder) {
		IComponent component = child.getComponent();
		if (ContainmentUtilities.allowIfNonLayoutComponent(displayModel, instance.getEObject(), component))
			return true;

		// first check static component constraints
		boolean result = 
			ContainmentUtilities.canAddComponentToContainer(displayModel, component, this, false, statusHolder);
		
		// then check to see if this component requires a query containment implementation
		StatusHolder tempHolder = new StatusHolder();
		boolean requiresContainmentCheck = 
			ContainmentUtilities.requiresContainmentQuery(component, this, statusHolder);
		if (statusHolder != null)
			statusHolder.appendStatus(tempHolder.getStatus(), ERROR_MESSAGE_DELIM);
		
		// See if this container has a IQueryContainment adapter
		IQueryContainment queryContainment =
			(IQueryContainment) EcoreUtil.getRegisteredAdapter(getEObject(), IQueryContainment.class);
		if ((queryContainment == null) && requiresContainmentCheck)
			result = false;

		if (queryContainment != null) {
			tempHolder = new StatusHolder();
			result = queryContainment.canContainChild(child, statusHolder) && result;
			if (statusHolder != null)
				statusHolder.appendStatus(tempHolder.getStatus(), ERROR_MESSAGE_DELIM);
		}

		return result;
	}

	public boolean isValidComponentInPalette(IComponent component) {
		if (ContainmentUtilities.allowIfNonLayoutComponent(displayModel, null, component))
			return true;
		
		// first see if this container has a IQueryContainment adapter
		IQueryContainment queryContainment =
			(IQueryContainment) EcoreUtil.getRegisteredAdapter(getEObject(), IQueryContainment.class);
		if (queryContainment != null) {
			return queryContainment.isValidComponentInPalette(component);
		}
		
		// then check static component constraints
		boolean result = ContainmentUtilities.canAddComponentToContainer(displayModel, component, this, true, null);

		return result;
	}

	public void validateContainment(IComponent component, EObject child) throws ContainmentException {
		StatusHolder holder = new StatusHolder();
		if (containmentValidationEnabled &&(component != null) && !canContainComponent(component,  holder))
			throw new ContainmentException(holder.getStatus());
	}
	
	public void validateRemoval(EObject child) throws ContainmentException {
		if (containmentValidationEnabled && !canRemoveChild(Utilities.getComponentInstance(child)))
			throw new ContainmentException(Logging.newStatus(null, IStatus.ERROR, 
					Messages.getString("Container.DeleteError"))); //$NON-NLS-1$
	}
	
	public void setEnabled(boolean enabled) {
		this.containmentValidationEnabled = enabled;
	}

	public EObject getEObject() {
		return instance.getEObject();
	}
	public boolean isAdapterForType(Object type) {
		return IContainer.class.equals(type) ||
			IValidateContainment.class.equals(type);
	}

	public boolean canRemoveChild(IComponentInstance child) {
		if (ContainmentUtilities.allowIfNonLayoutComponent(displayModel, null, child.getComponent()))
			return true;

		boolean canRemove = ContainmentUtilities.canRemoveChildrenOfTypeFromContainer(child.getComponent(), this);
		
		if (canRemove) {
			// see if this container has a IQueryContainment adapter
			IQueryContainment queryContainment =
				(IQueryContainment) EcoreUtil.getRegisteredAdapter(getEObject(), IQueryContainment.class);
			if (queryContainment != null) {
				canRemove = queryContainment.canRemoveChild(child);
			}
		}
		
		return canRemove;
	}

	public boolean collapsedInOutline() {
		IAttributes attributes = Adapters.getAttributes(getEObject());
		return attributes.getBooleanAttribute(CommonAttributes.COLLAPSED_IN_OUTLINE, false);
	}

}
