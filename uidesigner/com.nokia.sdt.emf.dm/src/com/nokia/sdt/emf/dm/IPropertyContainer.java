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

import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.datamodel.IDesignerDataModel;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.List;


/**
 * @model
 */
public interface IPropertyContainer extends EObject{
	
	/**
	 * Returns an IPropertyContainer that is a deep copy
	 * of this container. The returned property container
	 * is not owned by any EObject, is not associated with any
	 * data model, and has no listeners.
	 */
	Object clone();

	/**
	 * @model mapType="EStringToIPropertyValueMapEntry"
	 */
	EMap	getProperties();
	
	/**
	 * Get the EObject that logically owns the
	 * properties.
	 */
	EObject	getOwner();
	
	/**
	 * Set the EObject that owns the properties
	 */
	void	setOwner(EObject owner);
	
	IDesignerDataModel getDesignerDataModel();	
	
	// convenience methods
	Object get(String id);
	// set methods return the property value, or null if
	// the property was cleared, i.e. null was passed as the value
	IPropertyValue set(String id, StringValue value);
	IPropertyValue set(String id, StringValue value, boolean overrideCurrentState);
	IPropertyValue set(String id, IPropertyContainer value);
	IPropertyValue set(String id, List value);
	
	void setFromPropertyContainer(IPropertyContainer container);
	void setFromPropertySource(IPropertySource source);
	
	void reset(String id);
	
	StringValue createLiteral(String value);
	StringValue createLocalized(String value);
	StringValue createMacro(String value);
	StringValue createReference(String value);
	String lookupString(StringValue stringValue);
	
	IPropertyValue createPropertyContainerForProperty(Object propertyId);
	
	IPropertyValue createSequenceForProperty(Object propertyId);
	
	IPropertyValueSource getPropertyValueSource();
	
	// try to find the property the has the given value
	String findPropertyIDForValue(IPropertyValue pv);
	
	/**
	 * Visit properties of this node.  Visitors returns can
	 * terminate traversal by returning a non-null result,
	 * which is in turn returned by this method. That could
	 * be a useful value, or a placeholder value like Boolean.TRUE.
	 * If all visitors returned null then this method returns null.
	 * @return result of last visitor visited
	 */
	Object visitProperties(IPropertyVisitor visitor);
	
	StringValue conserveStringType(StringValue oldValue, StringValue newValue);
	
	void releasePropertyValue(IPropertyValue value);

	/**
	 * Return the path to this container
	 */
	String getPropertyPath();

}
