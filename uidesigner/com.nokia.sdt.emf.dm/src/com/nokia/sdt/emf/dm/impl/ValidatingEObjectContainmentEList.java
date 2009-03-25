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


package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.datamodel.adapter.ContainmentException;
import com.nokia.sdt.datamodel.adapter.IValidateContainment;
import com.nokia.sdt.emf.dm.ComponentHelper;
import com.nokia.sdt.emf.dm.INode;
import com.nokia.sdt.emf.dm.util.Messages;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 *
 */
public class ValidatingEObjectContainmentEList extends EObjectContainmentEList {
	private static final long serialVersionUID = 5135306077021582107L;

	public ValidatingEObjectContainmentEList(Class dataClass, InternalEObject owner, int featureID) {
		super(dataClass, owner, featureID);
	}

	protected void preValidateAdd(Object object) throws ContainmentException {
		IValidateContainment validator = 
			(IValidateContainment) EcoreUtil.getRegisteredAdapter(getEObject(), IValidateContainment.class);
		
		if (validator != null) {
			IComponent component = null;
			ComponentHelper helper = ComponentHelper.getComponentHelper(getEObject());
			if (helper != null)
				component = helper.lookupComponent(((INode) object).getComponentId());
			EObject eobject = (EObject) object;
			validator.validateContainment(component, eobject);
		}
	}

	@Override
	public Object setUnique(int index, Object object) {
		try {
			preValidateAdd(object);
			return super.setUnique(index, object);
		} 
		catch (ContainmentException e) {
			handleException(e);
		}
		
		return null;
	}
	  
	@Override
	public boolean addAllUnique(int index, Collection collection) {
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			addUnique(index++, iter.next());
		}
		return true;
	}

	@Override
	public boolean addAllUnique(Collection collection) {
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			addUnique(iter.next());
		}
		return true;
	}
	
	@Override
	public void addUnique(int index, Object object) {
		try {
			preValidateAdd(object);
			super.addUnique(index, object);
		} 
		catch (ContainmentException e) {
			handleException(e);
		}
	}

	@Override
	public void addUnique(Object object) {
		try {
			preValidateAdd(object);
			super.addUnique(object);
		} 
		catch (ContainmentException e) {
			handleException(e);
		}
	}

	protected void preValidateRemove(Object object) throws ContainmentException {
		IValidateContainment validator = 
			(IValidateContainment) EcoreUtil.getRegisteredAdapter(getEObject(), IValidateContainment.class);
		
		if (validator != null) {
			EObject eobject = (EObject) object;
			validator.validateRemoval(eobject);
		}
	}

	@Override
	public Object remove(int index) {
		try {
			preValidateRemove(get(index));
			return super.remove(index);
		} 
		catch (ContainmentException e) {
			handleException(e);
		}
		return null;
	}

	@Override
	public boolean remove(Object object) {
		int index = indexOf(object);
		if (index >= 0) {
			return remove(index) != null;
		} 
		else {
			return false;
		}
	}

	@Override
	public boolean removeAll(Collection collection) {
		return super.removeAll(collection);
	}

	private static void handleException(ContainmentException e) {
		IStatus status = e.getStatus();
		Logging.showErrorDialog(Messages.getString("ValidatingEObjectContainmentEList.ErrorTitle"), Messages.getString("ValidatingEObjectContainmentEList.ContainmentOperationError"), status); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public NotificationImpl createNotification(int eventType,
			Object oldObject, Object newObject, int index, boolean wasSet) {
		return super.createNotification(eventType, oldObject, newObject, index, wasSet);
	}

	@Override
	public void dispatchNotification(Notification notification) {
		super.dispatchNotification(notification);
	}
}
