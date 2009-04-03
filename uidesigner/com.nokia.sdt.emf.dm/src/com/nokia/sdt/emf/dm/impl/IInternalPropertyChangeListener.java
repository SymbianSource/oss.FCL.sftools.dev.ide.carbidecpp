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

import com.nokia.sdt.emf.dm.IPropertyValue;

	/**
	 * Internal interface for propagating property change events
	 *
	 */
interface IInternalPropertyChangeListener {

	/**
	 * Query whether a property value is allowed to change. This is
	 * meant as an internal way to enforce invariants, not as a user
	 * interace validation mechanism. Typically values are entered
	 * and checked with CellEditor and ICellEditorValidator and this is
	 * not meant to replace those mechanisms.
	 */
	boolean queryPropertyChange(Object propertyId, Object newValue);
	
	/**
	 * Notification that a property has changed
	 */
	void propertyChanged(Object propertyId, IPropertyValue oldValue, IPropertyValue newValue);
}
