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


package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.component.IComponent;

import org.eclipse.emf.ecore.EObject;

/**
 * 
 *
 */
public interface IValidateContainment extends IModelAdapter {
	
	/**
	 * If child can not be legally contained, throws ContainmentException
	 * @param component IComponent of the child
	 * @param child EObject
	 * @throws ContainmentException 
	 */
	void validateContainment(IComponent component, EObject child) throws ContainmentException;

	
	/**
	 * If child can not be legally removed, throws RemovalException
	 * @param child EObject
	 * @throws ContainmentException 
	 */
	void validateRemoval(EObject child) throws ContainmentException;
	
	/**
	 * Can use to temporarily disable validation of adds and removes
	 * @param enabled
	 */
	void setEnabled(boolean enabled);
}
