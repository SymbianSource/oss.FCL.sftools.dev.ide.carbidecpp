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

import com.nokia.sdt.component.event.IEventDescriptor;

import org.eclipse.emf.ecore.EObject;

/**
 * Represents a single event binding
 */
public interface IEventBinding {

	/**
	 * The data model object owning the binding
	 */
	EObject getOwner();
	
	/**
	 * The component's event descriptor
	 */
	IEventDescriptor getEventDescriptor();

	/**
	 * User displayed method name for the event handler
	 */
	String getHandlerName();

	void setHandlerName(String text);

	/**
	 * Internal text for the event handler, can be used
	 * for source generation and/or navigating to the handler
	 * code
	 */
	String getHandlerSymbolInformation();
	
	void setHandlerSymbolInformation(String symbolInformation);
	
	/**
	 * Returns true if the given binding is bound to the
	 * same handler as this one.
	 */
	boolean isSameHandler(IEventBinding other);
}
