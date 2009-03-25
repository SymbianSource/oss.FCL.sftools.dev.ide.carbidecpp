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

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.property.*;
import com.nokia.sdt.datamodel.adapter.IReconcileProperty;
import com.nokia.sdt.emf.component.AbstractPropertyType;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource2;

import java.text.MessageFormat;
import java.util.*;


public class CompoundPropertySource extends AbstractPropertySource implements IUndoablePropertySource {

	private CompoundPropertyTypeDescriptor compoundPropertyType;
	private boolean readOnly;
	
	public CompoundPropertySource(
					CompoundPropertyTypeDescriptor compoundPropertyType,
					IComponentSet componentSet,
					IPropertyValueSource valueSource,
					ILocalizedStrings strings, 
					boolean forceReadOnly) {
		super(componentSet, valueSource, strings);
		this.compoundPropertyType = compoundPropertyType;
		this.readOnly = forceReadOnly;
		initialize();
	}
	
	protected String getLocalizedDescription() {
		String fmt = Messages.getString("CompoundPropertySource.0"); //$NON-NLS-1$
		Object params[] = {compoundPropertyType.getCompoundPropertyDeclarationType().getQualifiedName()};
		return MessageFormat.format(fmt, params);
	}
	
	@Override
	public String getPropertyIdentifier(Object id) {
		String fmt = Messages.getString("CompoundPropertySource.1"); //$NON-NLS-1$
		Object params[] = {compoundPropertyType.getCompoundPropertyDeclarationType().getQualifiedName(),
						 id.toString()};
		return MessageFormat.format(fmt, params);
	}

	protected Collection<IPropertyDescriptor> doGetPropertyDescriptors(Map<String, AbstractPropertyType> emfTypeMap, Collection<IPropertyDescriptor> promotedReferenceProperties) {
		ArrayList pdList = new ArrayList();
		List emfProperties = compoundPropertyType.getCompoundPropertyDeclarationType().getAbstractProperty();
		addProperties(pdList, emfTypeMap, emfProperties, getStrings(), readOnly, promotedReferenceProperties, null);
		return pdList;
	}

	public Object getEditableValue() {
		Object result = null;
		// first look for a value converter for the property,
		// next look for a property reconciler on the owning component
		ICompoundPropertyValueConverter valueConverter = compoundPropertyType.getValueConverter();
		EObject owner = getPropertyValueSource().getEObject();
		if (valueConverter != null) {
			result = valueConverter.getEditableValue(owner, this);
		}
		else {
			if (owner != null) {
				IReconcileProperty reconcileProperty = 
					(IReconcileProperty) EcoreUtil.getRegisteredAdapter(owner, IReconcileProperty.class);
				
				if (reconcileProperty != null)
					result = reconcileProperty.createDisplayValue(
							compoundPropertyType.getCompoundPropertyDeclarationType().getQualifiedName(), 
							this);
			}
		}
		if (result == null) {
			result = super.getEditableValue();
		}
		
		return result;
	}

	public Object getUndoValue() {
		Object result = null;
		IPropertyValueSource valueSource = getPropertyValueSource();
		if (valueSource != null) {
			result = valueSource.createUndoValue();
		}
		return result;
	}

	public void setFromUndoValue(Object undoValue, boolean preserveLocalizedStringKeys) {
		Check.checkArg(undoValue instanceof IPropertyValueSource.UndoValue);
		IPropertyValueSource valueSource = getPropertyValueSource();
		if (valueSource != null) {
			valueSource.setFromUndoValue((IPropertyValueSource.UndoValue)undoValue, preserveLocalizedStringKeys);
		}	
	}
    
    public IPropertySource2 createPropertySource() {
    	IPropertyValueSource valueSource = getPropertyValueSource().createChildValueSource(null, false);
        CompoundPropertySource cps = new CompoundPropertySource(
        		compoundPropertyType, 
                getComponentSet(),
                valueSource,
                getStrings(),
                false);
        return cps;
    }
    
    /**
     * Override in order to handle returning the compound property type
     */
    public String getPropertyTypeName(Object id) {
    	String result;
    	if (id == null) {
    		result = compoundPropertyType.getCompoundPropertyDeclarationType().getQualifiedName();
    	} else {
    		result = super.getPropertyTypeName(id);
    	}
    	return result;
    }

    @Override
    public ICompoundPropertyValueConverter getCompoundPropertyValueConverter() {
    	return compoundPropertyType.getValueConverter();
    }
    
}