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
 * Interface used to add properties beyond those
 * directly declared by a component. 
 * 
 * The means for describing those additional properties is
 * specific to the component system.
 *
 */
public interface IPropertyExtenders extends IModelAdapter {

	/**
	 * Returns a list of instances to be queried for extension properties
	 * This method will first be called on the component instance itself.
	 * Based on that return value, a list of all transitively referenced
	 * instances is built. For example, the instance may return the container, which
	 * may respond with a layout manager object
	 * @param instance the instance to receive the additional properties
	 * @return the instances providing the extension properties. 
	 */
	EObject[] getPropertyExtenders(EObject instance);
	
	/**
	 * Return an array of names of the extension property sets to use for
	 * the given component. Allows components to have more than
	 * one set of extension properties and select the appropriate
	 * ones for a given instance.
	 * @param instance the instance to receive the additional properties
	 * @return an array of extension sets or null or empty array for none
	 * @since Carbide 1.3 -- removed #getExtensionSetName(EObject)
	 */
	String[] getExtensionSetNames(EObject instance);

}
