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
package com.nokia.sdt.component.symbian.properties;

import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.component.*;
import com.nokia.sdt.component.property.*;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentValidator;
import com.nokia.sdt.emf.component.AbstractPropertyType;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.osgi.framework.Bundle;

public abstract class AbstractPropertyDescriptor implements IPropertyDescriptor, IPropertyDescriptorExtensions {
	
	private AbstractPropertyType apt;
	private ITypeDescriptor typeDescriptor;
	private IPropertyValueSource valueSource;
	private ILocalizedStrings strings;
	private String category;
	private boolean readOnly;
	private Object defaultValueInitializer;
	private IPropertyEditorFactory editorFactory;
		// Note: we cache the label provider. The property sheet never
		// calls dispose on it. It also, by the contract of the
		// ILabelProvider API, cannot dispose of images returned by
		// a label provider.
	private ILabelProvider labelProvider;

	protected AbstractPropertyDescriptor(AbstractPropertyType propertyType, 
			ITypeDescriptor typeDescriptor,
			IPropertyValueSource valueSource,
			ILocalizedStrings strings, boolean forceReadOnly) {
		Check.checkArg(propertyType);
		Check.checkArg(typeDescriptor);
		Check.checkArg(valueSource);
		Check.checkArg(strings);
		this.apt = propertyType;
		this.typeDescriptor = typeDescriptor;
		this.valueSource = valueSource;
		this.strings = strings;
		this.readOnly = propertyType.isReadOnly() || forceReadOnly;
		
		// initialize local copy of category string, since it can be overriden
		setCategory(strings.checkPercentKey(apt.getCategory()));
		
		// Look for an IPropertyEditorFactory implementation, first
		// on the property declaration and then on the type.
		String editorClass = propertyType.getEditorClass();
		if (TextUtils.isEmpty(editorClass)) {
			editorClass = typeDescriptor.getEditorFactoryClass();
		}
		if (!TextUtils.isEmpty(editorClass)) {
			IComponentInstance ci = getComponentInstance(valueSource);
			if (ci != null) {
				editorFactory = createPropertyEditorFactory(editorClass, ci.getComponent());
			}
		}
	}
		
	/**
	 * Convert a default value to an internal form, 
	 * e.g convert a string to a structured object.
	 */
	protected Object convertDefaultValue(Object value) {
		if (value != null) {
			value = getTypeDescriptor().valueOf(value.toString());
		}
		return value;
	}
		
	public Object getId() {
		return apt.getName();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + getId() + "]";
	}

	protected AbstractPropertyType getPropertyType() {
		return apt;
	}
	
	public String getPropertyPath() {
		return getValueSource().getPropertyPath(getId());
	}

	public String getDisplayName() {
		String result = strings.checkPercentKey(apt.getDisplayName());
		if (result == null) {
			// in the absence of a specified display name before
			// using the internal name look for a key with the internal
			// name.
			String defaultKey = apt.getName();
			if (strings.hasString(defaultKey)) {
				result = strings.getString(defaultKey);
			}
			else {
				result = apt.getName();
			}
		}
		return result;
	}
	
	void setCategory(String category) {
		// the caller should have resolved whether the string is a literal or component-specific
		// property file string. We look up the input to see if it's localized by the component provider.
		this.category = category;
		if (!TextUtils.isEmpty(category)) {
			IComponentSet cs = null;
			if (valueSource.getDesignerDataModel() != null) {
				cs = valueSource.getDesignerDataModel().getComponentSet();
			}
			if (cs != null) {
				this.category = cs.getProvider().getCategoryText(category);
			}
		}
	}

	public String getCategory() {
		return category;
	}
	
	public String getDescription() {
		return strings.getString(apt.getDescriptionKey());
	}
	
	public IPropertyEditorFactory getEditorFactory() {
		return editorFactory;
	}

	public boolean isReadOnly() {
		return readOnly;
	}
	
	void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public boolean isPropertyLocalizable() {
		return false;
	}

	/**
	 * Return a default value for the property, if any
	 *
	 */
	public abstract Object getDefaultValue();
	
	/**
	 * Set the object value used to create default values
	 * @param initializer
	 */
	public void setDefaultValueInitializer(Object initializer) {
		this.defaultValueInitializer = initializer;
	}
	
	public Object getDefaultValueInitializer() {
		return defaultValueInitializer;
	}

	public ILocalizedStrings getStrings() {
		return strings;
	}

	public ITypeDescriptor getTypeDescriptor() {
		return typeDescriptor;
	}

	public IPropertyValueSource getValueSource() {
		return valueSource;
	}

	// This is final to help insure that we can bottleneck access
	// to property editors and install our validator on them.
	// Subclasses should override doCreatePropertyEditor
	public final CellEditor createPropertyEditor(Composite parent) {
		if (readOnly) return null;
		CellEditor result = doCreatePropertyEditor(parent, SWT.SINGLE);
		if (result != null) {
			IPropertyValueSource valueSource = getValueSource();
			EObject obj = valueSource.getEObject();
			String propertyPath = valueSource.getPropertyPath(getId());
			ITypeDescriptor typeDescriptor = getTypeDescriptor();
				
			enableComponentValidatorOnCellEditor(obj, propertyPath, typeDescriptor, result);
		}
		return result;
	}
	
	// This is final to help insure that we can bottleneck access
	// to property editors and install our validator on them.
	// Subclasses should override doCreatePropertyEditor
	public final CellEditor createPropertyEditor(Composite parent, int style) {
		if (readOnly) return null;
		CellEditor result = doCreatePropertyEditor(parent, style);
		if (result != null) {
			IPropertyValueSource valueSource = getValueSource();
			EObject obj = valueSource.getEObject();
			String propertyPath = valueSource.getPropertyPath(getId());
			ITypeDescriptor typeDescriptor = getTypeDescriptor();
			
			enableComponentValidatorOnCellEditor(obj, propertyPath, typeDescriptor, result);
		}
		return result;
	}
	
	protected CellEditor doCreatePropertyEditor(Composite parent, int style) {
		CellEditor result = null;
		EObject obj = getValueSource().getEObject();
		String propertyPath = valueSource.getPropertyPath(getId());
		if (editorFactory != null) {
			result = editorFactory.createCellEditor(parent, obj, propertyPath);
		}
		if (result == null) {
			result = typeDescriptor.createPropertyEditor(parent, style, 
							obj, propertyPath);
		}
		return result;		
	}
	
	/**
	 * Checks for IComponentValidator adapter on the instance. If available
	 * hooks up a cell editor validator for it.
	 * @param instance
	 * @param cellEditor
	 */
	void enableComponentValidatorOnCellEditor(EObject instance,
					String propertyPath, ITypeDescriptor typeDescriptor,
					CellEditor cellEditor) {
		IComponentValidator validator = (IComponentValidator) 
			EcoreUtil.getRegisteredAdapter(instance, IComponentValidator.class);
		ComposedCellEditorValidator composedValidator = new
		ComposedCellEditorValidator(this,
				validator, cellEditor.getValidator());
		cellEditor.setValidator(composedValidator);
	}
	
	public String[] getFilterFlags() {
		// I believe this is only used to filter on standard vs. expert properties
		return null;
	}

	public Object getHelpContextIds() {
		return apt.getHelpKey();
	}

	public ILabelProvider getLabelProvider() {
		if (labelProvider == null) {
			ILabelProvider newProvider = null;
			if (editorFactory != null) {
				EObject obj = getValueSource().getEObject();
				newProvider = editorFactory.createLabelProvider(obj, getPropertyPath());
			}
			if (newProvider == null) {
				newProvider = new LabelProvider() {
					public String getText(Object object)
					{
						return typeDescriptor.toDisplayString(object);
					}
					
					public Image getImage(Object object)
					{
						return typeDescriptor.getImage();
					}
				};
			}
			labelProvider = new LabelProviderDelegate(newProvider, this);
		}
		return labelProvider;
	}
	
	public void labelProviderDisposed(ILabelProvider delegate) {
		Check.checkState(labelProvider == delegate);
		labelProvider = null;
	}

	public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
		// We're only called if the other property has the same ID as this
		boolean isCompatible = false;
		if (getClass().isInstance(anotherProperty)) {
			AbstractPropertyDescriptor other = (AbstractPropertyDescriptor) anotherProperty;
			isCompatible = other.apt.equals(apt);
		}
		return isCompatible;
	}
	
	static IComponentInstance getComponentInstance(IPropertyValueSource valueSource) {
		IComponentInstance result = null;
		EObject eobj = valueSource.getEObject();
		if (eobj != null) {
			result = (IComponentInstance) EcoreUtil.getRegisteredAdapter(eobj, IComponentInstance.class);
		}
		return result;
	}
	
	static IPropertyEditorFactory createPropertyEditorFactory(String className, Bundle bundle) {
		IPropertyEditorFactory result = null;
		if (bundle != null) {
			String errorFormat = Messages.getString("Utilities.0"); //$NON-NLS-1$
			Object errorParams[] = {className};
			Object createdObj = ClassUtils.loadAndCreateInstance(
					bundle, className, IPropertyEditorFactory.class,
					ComponentSystemPlugin.getDefault(), errorFormat, errorParams);
			result = (IPropertyEditorFactory) createdObj;
		}
		return result;
	}
	
	static Class loadPropertyEditorClass(IComponent component, String className) {
		Class cls = null;
		
		while (component != null) {
			try {
				cls = ClassUtils.loadClass(component.getBundle(), className, IPropertyEditorFactory.class);
				// will thow before the break, if couldn't load class
				break;
			} catch (Throwable e) {}
			component = component.getComponentBase();
		}
		
		return cls;
	}

	static IPropertyEditorFactory createPropertyEditorFactory(String className, IComponent component) {
		Check.checkArg(component);
		IPropertyEditorFactory result = null;
		String errorFormat = Messages.getString("Utilities.0"); //$NON-NLS-1$
		Object errorParams[] = {className};
		Class cls = loadPropertyEditorClass(component, className);
		
		Object createdObj = ClassUtils.createInstanceFromClass(cls,
				ComponentSystemPlugin.getDefault(), errorFormat, errorParams);
		result = (IPropertyEditorFactory) createdObj;
		return result;
	}
	
	public TextCellEditor createDirectLabelPropertyEditor(Composite parent, int style) {
		CellEditor cellEditor = createPropertyEditor(parent, style);
		if (cellEditor instanceof TextCellEditor)
			return (TextCellEditor) cellEditor;
		
		// default implementation returns a string cell editor
		return new TypeDescriptors.StringCellEditor(parent, style);
	}
}
