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
package com.nokia.sdt.emf.dm;

import org.eclipse.emf.ecore.EObject;

/**
 * @model
 */
public interface IEventBinding extends EObject{

	/**
	 * The internal event name as given by the component
	 * @model
	 */
	String getEventID();
	
	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IEventBinding#getEventID <em>Event ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event ID</em>' attribute.
	 * @see #getEventID()
	 * @generated
	 */
	void setEventID(String value);

	/**
	 * Displayable text for the event handler method
	 * @model
	 */
	String getEventHandlerDisplayText();
	
	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IEventBinding#getEventHandlerDisplayText <em>Event Handler Display Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Handler Display Text</em>' attribute.
	 * @see #getEventHandlerDisplayText()
	 * @generated
	 */
	void setEventHandlerDisplayText(String value);

	/**
	 * Text used to store a reference to the event handler method so
	 * we can navigate to it
	 * @model
	 */
	String getEventHandlerInfo();
	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.IEventBinding#getEventHandlerInfo <em>Event Handler Info</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Handler Info</em>' attribute.
	 * @see #getEventHandlerInfo()
	 * @generated
	 */
	void setEventHandlerInfo(String value);

}
