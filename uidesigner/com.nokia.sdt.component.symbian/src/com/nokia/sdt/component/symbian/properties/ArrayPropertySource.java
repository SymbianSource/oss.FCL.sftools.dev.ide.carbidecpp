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
import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.property.*;
import com.nokia.sdt.emf.component.AbstractPropertyType;
import com.nokia.sdt.emf.component.ArrayPropertyType;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.*;

public class ArrayPropertySource extends AbstractPropertySource
							implements ISequencePropertySource, IUndoablePropertySource {

	private ArrayPropertyType apt;
	private ISequencePropertyValue sequencePropertyValue;
	private IPropertyDescriptor[] arrayPropertyDescriptors;
	private boolean elementIsLocalizable;
		// non-null if element type is a compound property
	private CompoundPropertyTypeDescriptor cpTypeDescriptor;
	
	public ArrayPropertySource(ArrayPropertyType apt, 
			IComponentSet componentSet,
			ISequencePropertyValue sequencePropertyValue,
			ILocalizedStrings strings) {
		super(componentSet, sequencePropertyValue.getValueSource(), strings);
		Check.checkArg(apt);
		Check.checkArg(sequencePropertyValue);
		this.apt = apt;
		this.sequencePropertyValue = sequencePropertyValue;
		
		ITypeDescriptor td = getComponentSet().lookupTypeDescriptor(apt.getType());
		elementIsLocalizable = td.isLocalizable();
		if (td instanceof CompoundPropertyTypeDescriptor) {
			cpTypeDescriptor = (CompoundPropertyTypeDescriptor) td;
		}
	 
		initialize();
	}
	
	@Override
	public Object getEditableValue() {
		return new ArrayEditableValue(this, cpTypeDescriptor);
	}

	protected String getLocalizedDescription() {
		String fmt = Messages.getString("ArrayPropertySource.0"); //$NON-NLS-1$
		Object params[] = {apt.getName()};
		return MessageFormat.format(fmt, params);
	}
	
	@Override
	protected boolean getInhibitDefaultValue(Object id) {
		// Never delete array entries, even when equal to the
		// default value.
		return false;
	}

	public Object get(int index) {
		Object element = sequencePropertyValue.get(index);
		Object result = internalToExternal(element);
		return result;
	}

	public List toList() {
		ArrayList result = new ArrayList();
		for (Iterator iter = iterator(); iter.hasNext();) {
			result.add(iter.next());
		}
		return result;
	}

	public void move(int index, int newIndex) {
		sequencePropertyValue.move(index, newIndex);
		sequenceChanged();
	}

	public void remove(int index) {
		sequencePropertyValue.remove(index);
		sequenceChanged();
	}
	
	public void clear() {
		sequencePropertyValue.clear();
		sequenceChanged();
	}
	
	public boolean isCompoundElement() {
		return cpTypeDescriptor != null;
	}

	public Iterator iterator() {
		final Iterator valueIter = sequencePropertyValue.iterator();
		return new Iterator() {
			public boolean hasNext() {
				return valueIter.hasNext();
			}
			public Object next() {
				Object pv = valueIter.next();
				return internalToExternal(pv);
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	private void sequenceChanged() {
		// release cached descriptors when elements added or removed
		arrayPropertyDescriptors = null;
	}
	
	private Object internalToExternal(Object element) {
		Object result = null;
		if (element instanceof String) {
			result = element;
		}
		else if (element instanceof IPropertyValueSource) {
			result = createCompoundPropertySource((IPropertyValueSource)element);
		}
		return result;
	}

	public int size() {
		return sequencePropertyValue.size();
	}

	@Override
	public String getPropertyIdentifier(Object id) {
		String fmt = Messages.getString("ArrayPropertySource.1"); //$NON-NLS-1$
		Object params[] = {apt.getName(),id.toString()};
		return MessageFormat.format(fmt, params);
	}

	ISequencePropertyValue getSequencePropertyValue() {
		return sequencePropertyValue;
	}

	public IPropertySource addCompoundProperty(int index) {
		if (cpTypeDescriptor == null) {
			throw new IllegalStateException();
		}
		IPropertyValueSource pvs = sequencePropertyValue.addChildValueSource(index);
		sequenceChanged();
		return (IPropertySource) internalToExternal(pvs);
	}

	public int addSimpleProperty(int index, Object value) {
		if (value instanceof IPropertySource) {
			throw new IllegalArgumentException();
		}
		if (cpTypeDescriptor != null) {
			throw new IllegalStateException();
		}
		
		if (value == null) {
			value = getDefaultValue(null);
		}
		// First create the value in the sequence, then
		// go through the normal property source to set the
		// desired value
		String initValue = "";
		if (elementIsLocalizable) {
			index = sequencePropertyValue.addLocalizedString(index, initValue);
		} else {
			index = sequencePropertyValue.addStringLiteral(index, initValue);
		}
		Object propertyID = Integer.toString(index);
		setPropertyValue(propertyID, value);
		sequenceChanged();
		return index;
	}

	IPropertySource createCompoundPropertySource(IPropertyValueSource pvs) {
		IPropertySource result = null;
		String elementType = apt.getType();
		ITypeDescriptor td = getComponentSet().lookupTypeDescriptor(elementType);
		if (td instanceof CompoundPropertyTypeDescriptor) {
			CompoundPropertyTypeDescriptor cpTypeDescriptor = (CompoundPropertyTypeDescriptor) td;
			result = new CompoundPropertySource(cpTypeDescriptor, getComponentSet(),
					pvs, cpTypeDescriptor.getLocalizedStrings(), apt.isReadOnly());
		}
		return result;
	}
/*
    public Object createElement(int index) {
        IPropertyValueSource pvs = sequencePropertyValue.getValueSource().createChildValueSource(null); 
        String elementType = apt.getType();
        ITypeDescriptor td = getComponentSet().lookupTypeDescriptor(elementType);
        Object result = td.defaultValue(pvs, null);
        
        return result;
    }
  */ 
	
    public boolean isElementLocalizable() {
        return elementIsLocalizable;
    }
	
	public ITypeDescriptor getPropertyTypeDescriptor(Object propertyID) {	
		ITypeDescriptor result = getComponentSet().lookupTypeDescriptor(apt.getType());
		return result;
	}
	
	public Object getDefaultValue(Object id) {
		ITypeDescriptor td = getComponentSet().lookupTypeDescriptor(apt.getType());
		Object result = td.defaultValue(getPropertyValueSource(), null);
		return result;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		if (arrayPropertyDescriptors == null) {
			Collection<IPropertyDescriptor> descriptors = doGetPropertyDescriptors(null, null);
			arrayPropertyDescriptors = (IPropertyDescriptor[]) descriptors.toArray(new IPropertyDescriptor[descriptors.size()]);
		}
		return arrayPropertyDescriptors;
	}

	protected Collection<IPropertyDescriptor> doGetPropertyDescriptors(Map<String, AbstractPropertyType> emfTypeMap, Collection<IPropertyDescriptor> promotedReferenceProperties) {
		ArrayList descriptors = new ArrayList();
		Object elementIDs[] = getPropertyValueSource().getIds();
		if (elementIDs != null) {
			ITypeDescriptor elementTypeDescriptor = getComponentSet().lookupTypeDescriptor(apt.getType());
			for (int i = 0; i < elementIDs.length; i++) {
				descriptors.add( new ElementPropertyDescriptor(Integer.toString(i), elementTypeDescriptor));
			}
		}
		return descriptors;
	}
	
	class ElementPropertyDescriptor implements IPropertyDescriptor {
		
		ITypeDescriptor typeDescriptor;
		Object id;

		public ElementPropertyDescriptor(Object id, ITypeDescriptor descriptor) {
			Check.checkArg(id);
			Check.checkArg(descriptor);
			this.id = id;
			typeDescriptor = descriptor;
		}

		public CellEditor createPropertyEditor(Composite parent) {
			String propertyPath = sequencePropertyValue.getValueSource().getPropertyPath(id);
			CellEditor result = typeDescriptor.createPropertyEditor(parent, 0,
					sequencePropertyValue.getValueSource().getEObject(), propertyPath);
			return result;
		}

		public String getCategory() {
			return null;
		}

		public String getDescription() {
			return null;
		}

		public String getDisplayName() {
			return id.toString();
		}

		public String[] getFilterFlags() {
			return null;
		}

		public Object getHelpContextIds() {
			return null;
		}

		public Object getId() {
			return id;
		}

		public ILabelProvider getLabelProvider() {
			return new LabelProvider() {
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

		public boolean isCompatibleWith(IPropertyDescriptor anotherProperty) {
			return false;
		}	
		
		public ITypeDescriptor getTypeDescriptor() {
			return typeDescriptor;
		}
	}

	public ArrayPropertyType getArrayPropertyType() {
		return apt;
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

	@Override
	public String getPropertyPath(Object propertyId) {
		return sequencePropertyValue.getValueSource().getPropertyPath(propertyId);
	}

	public String getElementPath(int index) {
		return sequencePropertyValue.getElementPath(index);
	}
}
