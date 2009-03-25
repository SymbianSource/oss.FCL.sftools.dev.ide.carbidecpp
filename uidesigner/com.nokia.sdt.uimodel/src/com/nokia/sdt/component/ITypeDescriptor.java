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
package com.nokia.sdt.component;

import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import java.util.Collection;

public interface ITypeDescriptor {
	
		// these strings are the type names registered for the primitive
		// types and must much the string values as used in .component files.
	static final String BOOLEAN_TYPE = "boolean";
	static final String INTEGER_TYPE = "integer";
	static final String FLOAT_TYPE = "float";
	static final String STRING_TYPE = "string";
	static final String VOID_TYPE = "void";
	static final String UNIQUE_NAME_TYPE = "uniqueName";
	static final String LOCALIZED_STRING_TYPE= "localizedString";
    static final String COMPONENT_REFERENCE_TYPE= "componentRef";
	
	IComponentSet getComponentSet();
	void setComponentSet(IComponentSet componentSet);

	/**
	 * Convert a string to the appropriate object.
	 * This is intended for use when reading
	 * persisted values back into object form,
	 * not for converting display strings back to
	 * objects.
	 */
	Object	valueOf(String text);
	
	/**
	 * Provides a user-displayable string corresponding to
	 * the object value. This may be different from
	 * the result of value.toString(). The latter
	 * is what would be used to persist a value to
	 * a text file.
	 */
	String toDisplayString(Object value);
	
	/**
	 * Provides a string to use as the persistable
	 * value of a property. These strings do not need
	 * to vary with the locale.
	 */
	String toStorageString(Object value); 
	
	/**
	 * Convert a value coming from a cell editor to
	 * an internal property value type. Because a property can
	 * have any type of cell editor, any conversion is not possible.
	 * We basically assume simple objects can be any type, because we
	 * ultimately just convert to a string. Compound properties can use
	 * ICompoundPropertyValueConverters to convert arbitrary types.
	 * @param editableValue
	 * @param parentValueSource can be used to construct a new property value
	 * @return a simple object, or property source
	 */
	Object editableValueToPropertyValue(Object editableValue, 
			IPropertyValueSource parentValueSource);
	
	/**
	 * Are properties of this type localizable by default?
	 */
	boolean isLocalizable();
	
	/**
	 * Are properties of this type references to 
	 * component instances?
	 */
	boolean isReference();
	
	/**
	 * This is a generic default value for a type. A component may
	 * want to provide a different default value for a given property.
	 * The valueSource and propertyID are not needed for simple properties, but
	 * may be needed for more complex ones, such as compound properties.
	 * @param valueSource represents the container for the value.
	 * @param propertyId the id of the property for which the value will be used.
	 */
	Object  defaultValue(IPropertyValueSource valueSource, Object propertyId);
		
	Collection getChoiceOfValues();
	
	/**
	 * Create a property editor appropriate to the type
	 */
	CellEditor createPropertyEditor(Composite parent, int style, EObject object, String propertyPath);
	
	/**
	 * Return a cell editor for the property type.
	 * A null return indicates no validation is needed
	 * Validators need to accept any type also acceptable
	 * by the toStorageString method.
	 */
	ICellEditorValidator getValidator();
	
	Image getImage();
	
	Object clone();
	
	/**
	 * Return the class name of an IPropertyEditorFactory
	 * implementation for this type, if any.
	 * @return
	 */
	String getEditorFactoryClass();
	
	/**
	 * Get the localized strings associated with the type,
	 * or null if none are needed or known.
	 */
	ILocalizedStrings getStrings();
}
