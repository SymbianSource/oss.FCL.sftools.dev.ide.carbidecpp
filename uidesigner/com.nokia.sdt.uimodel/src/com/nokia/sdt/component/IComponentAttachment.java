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


package com.nokia.sdt.component;

import org.eclipse.emf.ecore.EObject;


/**
 * Interface for an adapter that may be added to components in order to
 * receive notifications about the lifetime of model objects created from them.
 * <p>
 * Creation notifications are given for objects at load time as well as while the model is open.
 * However, deletion notification is not given for objects remaining in the model when it is closed.
 */
public interface IComponentAttachment {
	
	/**
	 * Notification that an object was created in the model
	 * @param eobject
	 */
	void objectCreated(EObject eobject);

	/**
	 * Notification that an object was deleted from the model
	 * @param eobject
	 */
	void objectDeleted(EObject eobject);
	
}
