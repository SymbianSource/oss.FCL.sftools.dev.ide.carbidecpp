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

package com.nokia.sdt.component.property;

import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.emf.ecore.EObject;

/**
 * Abstraction for a container of property values. Eclipse property 
 * support couples type and value information into the same classes. 
 * We want to avoid coupling a particular component provider to a 
 * particular data model implementation, hence
 * there must be an abstraction layer.
 */
public interface IPropertyValueSource {
		
	/**
	 * Returns true if a value is available for the given property.
	 */
	boolean	hasPropertyValue(Object propertyId);
	
	/** 
	 * @param propertyId unique property ID
	 * @return for a simple property, the actual value. 
	 * For a compound property returns another IPropertyValueSource
	 * For a sequence property returns an ISequencePropertyValue
	 */
	Object	getPropertyValue(Object propertyId);
	
	/**
	 * Returns true if values can be set for the property
	 */
	boolean canSetPropertyValue(Object propertyId);
	
	/**
	 * Returns the number of immediate child values
	 */
	int		numberOfProperties();	

	/**
	 * Returns all the contained property ids
	 */
	Object[] getIds();
	
	/**
	 * Sets the property to the given value. Implementations
	 * are not required to validate values, generally
	 * the component provider using this interface should do so.
	 */
	void	setPropertyValue(Object propertyId, Object newValue);
	
	/**
	 * Set a property to a given value and indicate it is localizable
	 * This method sets the value for the "current" language. Access
	 * to other language values is not available through this API.
	 * Note that when 2-way support is available then the localization
	 * status also depends on how a value is defined in source code.
	 * In other words, a given property may be intended to be localizable,
	 * but that may be overriden via source code.
	 */
	void 	setLocalizableStringPropertyValue(Object propertyId, String newValue, boolean overrideCurrentState);

	/**
	 * Localizable string properties and non-localized strings
	 * have a symbolic key used to store values. This symbol
	 * may also be used in source generation. This method
	 * retrieves the key for a property if it is defined, or
	 * returns null if none is defined.
	 */
	String getStringPropertyValueSymbol(Object propertyId);
		
	
	/**
	 * Sets a property to be a reference to a component
	 * instance. The value must be the name property value
	 * of the target componetn instance.
	 */
	void setReferencePropertyValue(Object propertyId, String newValue);
	
	/**
	 * Returns true if the property has a value which is a
	 * component instance reference.
	 */
	boolean	isReferenceProperty(Object propertyId);
		
	/**
	 * Removes the property value, implying the property takes
	 * the default value, if any.
	 */
	void	resetPropertyValue(Object propertyId);
	
	/**
	 * When a compound property is initially unset getValue returns
	 * null. This method provides an new, empty provider for the
	 * compound property and establishes it as the current property value
	 * @param propertyId the unique property ID
	 * @param storeValue if true, the new value source is stored as the property value
	 * @return new, empty provider. returns null if the given property 
	 * already had a value
	 */
	IPropertyValueSource createChildValueSource(Object propertyId, boolean storeValue);
	
	ISequencePropertyValue createChildSequence(Object propertyId, boolean storeValue);
	
	/**
	 * Return the EObject corresponding to this value source.
	 * Returns the ultimate owner of this, even for nested compound
	 * properties.
	 */
	EObject	getEObject();
	
	/**
	 * Get the dataModel for the property value source.
	 */
	IDesignerDataModel getDesignerDataModel();
	
	/**
	 * Return a property path reflecting the nesting
	 * of the given property. Paths are separated by '.'.
	 * Examples of paths are ".x", and "x.y.z". Array elements
	 * are returned as <path to array>.<index>, e.g. a.0, not a[0].
	 * Note that since IPropertyValueSource does not have an idea
	 * of unknown properties any id is allowed.
	 */
	String getPropertyPath();
	
	/**
	 * Return the property path for a child property
	 */
	String getPropertyPath(Object propertyId);
	
	/**
	 * Make a copy of this IPropertyValueSource. Because a deep copy
	 * may involve references to other objects a clone operation is 
	 * not sufficient.
	 */
	UndoValue createUndoValue();
	
	/**
	 * Restores values from a previously created undo value.
	 */
	void setFromUndoValue(UndoValue value, boolean preserveLocalizedStringKeys);

	interface UndoValue {
	}

	/** Look up the target of a reference and return its property value source.
	 * @param id property id of the reference
	 * @return {@link IPropertyValueSource} or null
	 */
	IPropertyValueSource lookupReferencePropertyValueSource(Object id);
}
