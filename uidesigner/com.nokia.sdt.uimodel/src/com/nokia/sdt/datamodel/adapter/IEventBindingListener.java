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
package com.nokia.sdt.datamodel.adapter;

import org.eclipse.emf.ecore.EObject;


/**
 * Listener for additional/removal of event bindings
 */
public interface IEventBindingListener {

	/**
	 * A new binding was added
	 */
	void bindingAdded(EObject instance, IEventBinding eventBinding);

	/**
	 * An existing binding is being removed
	 */
	void bindingRemoved(EObject instance, IEventBinding eventBinding);
}
