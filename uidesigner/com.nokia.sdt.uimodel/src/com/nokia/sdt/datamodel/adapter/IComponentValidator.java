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

import com.nokia.sdt.datamodel.IModelMessage;

import org.eclipse.emf.ecore.EObject;

import java.util.Collection;

public interface IComponentValidator extends IModelAdapter {

	/**
	 * Validate that a component's current state is valid
	 * This may be invoked when the model may have changed
	 * outside the user interface, e.g. from reloading a model
	 * or reparsing source code. All generic checks will have
	 * already been performed, such as checking the property values
	 * are valid for their type. Implementations needs only do 
	 * additional checking, such as cross-property checking, or
	 * additional constraints on children.
	 * @param componentInstance
	 * @return null for no errors.
	 */
	Collection<IModelMessage> validate(EObject componentInstance);
	
	/**
	 * Notification that a property is about to change from a user edit. 
	 * The change may be vetoed by returning a non-null result. This method 
	 * is called after individual property validation has occurred, so
	 * implementations need only worry about additional validation, such
	 * as cross-property validation.
	 * 
	 * @param componentInstance the owning component instance
	 * @param propertyPath the dotted path to the property
	 * @param newValue the proposed new value, which has already
	 * passed validation by its cell editor
	 * @return null to accept the change. Returned strings should be
	 * user-displayable, localized error messages.
	 */
	String queryPropertyChange(EObject componentInstance, 
			String propertyPath, Object newValue);
}
