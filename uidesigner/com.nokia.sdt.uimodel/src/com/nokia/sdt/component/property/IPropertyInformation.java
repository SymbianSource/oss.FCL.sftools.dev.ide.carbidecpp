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
package com.nokia.sdt.component.property;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface supporting access to property information
 * outside the scope of Eclipse's standard property APIs.
 * 
 * This interface is intended to be implemented on objects
 * implementing IPropertySource. Given the property source, use 
 * instanceof to see if IPropertyInformation is also implemented.
 */
public interface IPropertyInformation {
	 
	/**
	 * Localizable string properties and non-localized strings
	 * have a symbolic key used to store values. This symbol
	 * may also be used in source generation. This method
	 * retrieves the key for a property if it is defined, or
	 * returns null if none is defined.
	 */
	String getPropertyValueSymbol(Object propertyId);
	
	/**
	 * Return the name of the property's type. This can return
	 * the name of the property source's type, or of a child.
	 * Names are returned for simple types, a compound property types, 
	 * and enum types. Null is returned for array properties.
	 * @param propertyId null for the property source type name, or
	 * non-null for a child element's type name.
	 */
	String getPropertyTypeName(Object propertyId);
	
	/**
	 * Return the property path for the receiver
	 */
	String getPropertyPath();
	
	/**
	 * Return a property path for a child of this property.
	 * of the given property.  Paths are separated by '.',
	 * even for array elements.
	 * <p>
	 * If the property is promoted, its path points to its position in the promoted
	 * instance, not the original location.
	 */
	String getPropertyPath(Object propertyId);
	
	/**
	 * Get the original EObject which provided this property.
	 * For most cases, this is the same as the owner of the 
	 * property source.  In the case of promoted reference properties,
	 * this will be the referenced component instance.
	 * @param propertyId the property id
	 * @return EObject that owns the property, or null for unknown property
	 */
	EObject getPropertyOwner(Object propertyId);
	
	
	/**
	 * Notification that something has changed requiring properties to be regenerated
	 */
	void refresh();
	
	/**
	 * Get the value converter for a compound property, if this is one, as specified
	 * in the "converterClass" attribute.
	 * @return {@link ICompoundPropertyValueConverter} or <code>null</code>
	 */
	ICompoundPropertyValueConverter getCompoundPropertyValueConverter();
}
