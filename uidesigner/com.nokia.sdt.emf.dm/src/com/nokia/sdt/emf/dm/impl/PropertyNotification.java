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
package com.nokia.sdt.emf.dm.impl;

import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * Specialization of Notification support property change information
 */
public class PropertyNotification extends NotificationImpl {

	private EObject owner;
	private Object propertyId;
	
	/**
	 * @param eventType
	 * @param oldValue
	 * @param newValue
	 */
	public PropertyNotification(EObject owner, int eventType, Object id) {
		super(eventType, null, null);
		this.owner = owner;
		this.propertyId = id;
	}
	
	public Object getNotifier() {
		return owner;
	}

	/**
	 * Returns the id of the changed property
	 */
	public Object getPropertyId() {
		return propertyId;
	}

}
