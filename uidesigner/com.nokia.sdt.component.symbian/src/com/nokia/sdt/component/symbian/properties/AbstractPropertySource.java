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
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IReconcileProperty;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.emf.component.util.PropertySwitch;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.*;

import java.text.MessageFormat;
import java.util.*;

public abstract class AbstractPropertySource implements IPropertySource2, IPropertyInformation {

	private IComponentSet componentSet;
	private IPropertyValueSource valueSource;
	private ILocalizedStrings strings;
	private IPropertyDescriptor[] propertyDescriptors;
	private HashMap<String, AbstractPropertyType> propertyTypes = new HashMap<String, AbstractPropertyType>();
	
	/** Map of property ID to the name of the promoted component reference property providing it.
	 * <p>
	 * Note: we don't cache the IPropertyValueSource, like we could, to be more optimal,
	 * because the instance referenced may change out from under us.  INodeImpl#updateNodeReferences()
	 * directly modifies component reference property values and we can't detect that here. 
	 * So instead, we keep the indirect information about the reference so we can dynamically
	 * look up the instance both if its IPropertyValueSource changes or if the name of the instance changes. */
	private Map<Object, Object> promotedReferencePropertyReferenceNames;
	private Set<Object> promotingReferencePropertyDescriptors;
	private IDesignerDataModel model;
	
	public AbstractPropertySource(IComponentSet componentSet,
				IPropertyValueSource valueSource, ILocalizedStrings strings) {
		Check.checkArg(componentSet);
		Check.checkArg(valueSource);
		Check.checkArg(strings);
		this.componentSet = componentSet;
		this.valueSource = valueSource;
		this.model = valueSource.getDesignerDataModel();
		this.strings = strings;
		this.promotedReferencePropertyReferenceNames = Collections.EMPTY_MAP;
		this.promotingReferencePropertyDescriptors = Collections.EMPTY_SET;
	}
	
	protected void initialize() {
		getPropertyDescriptors();
	}
	
	/**
	 * Returns a displayable string that fully identifies a
	 * property. 
	 */
	public abstract String getPropertyIdentifier(Object id);
			
	protected AbstractPropertyType getProperty(Object propertyID) {
		AbstractPropertyType result = propertyTypes.get(propertyID);
		return result;
	}
	
	public Object getEditableValue() {
		return this;
	}
	
	protected ITypeDescriptor typeDescriptorForSimpleProperty(
				ComponentProvider provider, SimplePropertyType simpleProperty) {
		ITypeDescriptor result = null;
		// make an instance if the property has min/max values, otherwise use existing instances
		String minStr = simpleProperty.getMinValue();
		String maxStr = simpleProperty.getMaxValue();
		if (TextUtils.strlen(minStr) > 0 || TextUtils.strlen(maxStr) > 0) {
			int dataType = simpleProperty.getType().getValue();
			if (dataType == PropertyDataType.INTEGER) {
				int min = TextUtils.parseInt(minStr, Integer.MIN_VALUE);
				int max = TextUtils.parseInt(maxStr, Integer.MAX_VALUE);
				TypeDescriptors.IntegerTypeDescriptor itd = new TypeDescriptors.IntegerTypeDescriptor();
				itd.setMinValue(min);
				itd.setMaxValue(max);
				result = itd;
			}
			else if (dataType == PropertyDataType.FLOAT) {
				float min = TextUtils.parseFloat(minStr, java.lang.Float.MIN_VALUE);
				float max = TextUtils.parseFloat(maxStr, java.lang.Float.MAX_VALUE);
				TypeDescriptors.FloatTypeDescriptor ftd = new TypeDescriptors.FloatTypeDescriptor();
				ftd.setMinValue(min);
				ftd.setMaxValue(max);
				result = ftd;
			}
			
			if (result != null) {
				ITypeDescriptor extendingDescriptor = componentSet.lookupTypeDescriptor(simpleProperty.getExtendWithEnum());
				if (extendingDescriptor != null) {
					result = TypeDescriptors.getExtendedPropertyDescriptor(result, extendingDescriptor);
				}
			}
		}
		else {
			result = TypeDescriptors.lookupPrimitiveDescriptor(
				(ComponentProvider)componentSet.getProvider(), 
				simpleProperty.getType(),
				componentSet.lookupTypeDescriptor(simpleProperty.getExtendWithEnum()));
		}
		
        if (result == null) {
            IStatus status = Logging.newStatus(ComponentSystemPlugin.getDefault(), 
                            IStatus.ERROR, 
                            MessageFormat.format(Messages.getString("AbstractPropertySource.NoSuchSimpleProperty"), //$NON-NLS-1$
                                    new Object[] { simpleProperty.getType() }));
            ComponentSystemPlugin.log(status);
            Logging.showErrorDialog(Messages.getString("AbstractPropertySource.InvalidComponent"), null, status); //$NON-NLS-1$
        }
		
		return result;
	}
	
	protected void addSimpleProperty(Collection<IPropertyDescriptor> pdList, 
			SimplePropertyType simpleProperty, ILocalizedStrings strings, boolean forceReadOnly) {
		ITypeDescriptor typeDescriptor = typeDescriptorForSimpleProperty((ComponentProvider)componentSet.getProvider(), simpleProperty);
		if (typeDescriptor == null) {
			// message logged
			return;
		}
		
		IPropertyDescriptor pd;
		if (simpleProperty.getType().getValue() == PropertyDataType.UNIQUE_NAME) {
			pd = new UniqueNamePropertyDescriptor(
					simpleProperty, typeDescriptor, valueSource, strings, forceReadOnly);
		}
		else {
			pd = new SimplePropertyDescriptor(
					simpleProperty, typeDescriptor, valueSource, strings, forceReadOnly);
		}
		pdList.add(pd);
	}

	protected void addCompoundProperty(Collection<IPropertyDescriptor> pdList, 
			CompoundPropertyType compoundProperty, ILocalizedStrings strings, boolean forceReadOnly) {
		ITypeDescriptor typeDescriptor = componentSet.lookupTypeDescriptor(compoundProperty.getType());
        if (typeDescriptor == null) {
            IStatus status = Logging.newStatus(ComponentSystemPlugin.getDefault(), 
                            IStatus.ERROR, 
                            MessageFormat.format(Messages.getString("AbstractPropertySource.NoSuchCompoundProperty"), //$NON-NLS-1$
                                    new Object[] { compoundProperty.getType() }));
            ComponentSystemPlugin.log(status);
            return;
        } else if (!(typeDescriptor instanceof CompoundPropertyTypeDescriptor)) {
        	IStatus status = Logging.newStatus(ComponentSystemPlugin.getDefault(), 
        			IStatus.ERROR, 
        			MessageFormat.format(Messages.getString("AbstractPropertySource.InvalidCompoundPropertyType"), //$NON-NLS-1$
        					new Object[] { compoundProperty.getType() }));
        	ComponentSystemPlugin.log(status);
        	return;   	
        }
        
		pdList.add(new CompoundPropertyDescriptor(
				compoundProperty, (CompoundPropertyTypeDescriptor) typeDescriptor, valueSource, strings, forceReadOnly));
	}
	
	protected void addEnumProperty(Collection<IPropertyDescriptor> pdList, 
			EnumPropertyType enumProperty, ILocalizedStrings strings, boolean forceReadOnly) {
		ITypeDescriptor typeDescriptor = componentSet.lookupTypeDescriptor(enumProperty.getType());
        if (typeDescriptor == null) {
            IStatus status = Logging.newStatus(ComponentSystemPlugin.getDefault(), 
                            IStatus.ERROR, 
                            MessageFormat.format(Messages.getString("AbstractPropertySource.NoSuchEnumProperty"), //$NON-NLS-1$
                                    new Object[] { enumProperty.getType() }));
            ComponentSystemPlugin.log(status);
            Logging.showErrorDialog(Messages.getString("AbstractPropertySource.InvalidComponent"), null, status); //$NON-NLS-1$
            return;
        }
        ITypeDescriptor extendingEnum = componentSet.lookupTypeDescriptor(enumProperty.getExtendWithEnum());
        if (extendingEnum != null) {
        	typeDescriptor = TypeDescriptors.getExtendedPropertyDescriptor(typeDescriptor, extendingEnum);
        }
		pdList.add(new EnumPropertyDescriptor(
				enumProperty, typeDescriptor, valueSource, strings, forceReadOnly));
	}
	
	protected void addArrayProperty(Collection<IPropertyDescriptor> pdList, 
			ArrayPropertyType arrayProperty, ILocalizedStrings strings, boolean forceReadOnly) {
		ITypeDescriptor arrayTypeDescriptor = getPropertyTypeDescriptor(arrayProperty);
		ITypeDescriptor elementTypeDescriptor = componentSet.lookupTypeDescriptor(arrayProperty.getType());
        if (elementTypeDescriptor == null) {
            IStatus status = Logging.newStatus(ComponentSystemPlugin.getDefault(), 
                            IStatus.ERROR, 
                            MessageFormat.format(Messages.getString("AbstractPropertySource.NoSuchArrayProperty"), //$NON-NLS-1$
                                    new Object[] { arrayProperty.getType() }));
            ComponentSystemPlugin.log(status);
            Logging.showErrorDialog(Messages.getString("AbstractPropertySource.InvalidComponent"), null, status); //$NON-NLS-1$
            return;
        }
		pdList.add(new ArrayPropertyDescriptor(
				arrayProperty, arrayTypeDescriptor,
				elementTypeDescriptor, valueSource, strings, forceReadOnly));
	}
	
	protected void addComponentReferenceProperty(Collection<IPropertyDescriptor> pdList, 
			ComponentReferencePropertyType refProperty, ILocalizedStrings strings, boolean forceReadOnly,
			Collection<IPropertyDescriptor> promotedReferenceProperties) {
		ITypeDescriptor td = getPropertyTypeDescriptor(refProperty);
		ComponentReferencePropertyDescriptor componentReferencePropertyDescriptor = new ComponentReferencePropertyDescriptor(
				refProperty, (ComponentReferenceTypeDescriptor) td, valueSource, strings, forceReadOnly);
		pdList.add(componentReferencePropertyDescriptor);
		if (refProperty.isPromoteReferenceProperties()) {
			promotedReferenceProperties.add(componentReferencePropertyDescriptor);
		}
	}
	
	protected void addProperty(final Collection<IPropertyDescriptor> pdList, Map emfMap, AbstractPropertyType apt, 
			final ILocalizedStrings strings, final boolean forceReadOnly, final Collection<IPropertyDescriptor> promotedReferenceProperties) {
	
		if (emfMap != null) {
			// check and log message for duplicate properties
			if (emfMap.containsKey(apt.getName())) {
				String fmt = Messages.getString("AbstractPropertySource.0"); //$NON-NLS-1$
				Object params[] = {apt.getName(), getLocalizedDescription()};
				String msg = MessageFormat.format(fmt, params);
				ComponentSystemPlugin.log(null, msg);
				return;
			}
			emfMap.put(apt.getName(), apt);
		}

		PropertySwitch pswitch = new PropertySwitch() {
			protected Object doArrayProperty(ArrayPropertyType type) {
				addArrayProperty(pdList, type, strings, forceReadOnly);
				return null;
			}

			protected Object doCompoundProperty(CompoundPropertyType type) {
				addCompoundProperty(pdList, type, strings, forceReadOnly);
				return null;
			}

			protected Object doEnumProperty(EnumPropertyType type) {
				addEnumProperty(pdList, type, strings, forceReadOnly);
				return null;
			}

			protected Object doSimpleProperty(SimplePropertyType type) {
				addSimpleProperty(pdList, type, strings, forceReadOnly);
				return null;
			}
			
			protected Object doComponentReferenceProperty(ComponentReferencePropertyType type) {
				addComponentReferenceProperty(pdList, type, strings, forceReadOnly, promotedReferenceProperties);
				return null;
			}
		};
	
		pswitch.doSwitch(apt);
	}
	
	protected void addProperties(Collection<IPropertyDescriptor> pdList, Map emfMap, 
			List emfProperties, ILocalizedStrings strings, boolean forceReadOnly, Collection<IPropertyDescriptor> promotedReferenceProperties, Set<String> extendedProperties) {
		if (emfProperties != null) {
			for (Iterator iter = emfProperties.iterator(); iter.hasNext();) {
				AbstractPropertyType propertyType = (AbstractPropertyType)iter.next();
				// do not complain if the same property is added twice via extension properties
				if (extendedProperties == null 
						|| !extendedProperties.contains(propertyType.getName())) {
					addProperty(pdList, emfMap, propertyType, strings, forceReadOnly, promotedReferenceProperties);
					if (extendedProperties != null) {
						extendedProperties.add(propertyType.getName());
					}
				}
			}
		}
	}

	/** 
	 * Add the properties exposed through promoted component reference property values.
	 * Note that this is a dynamic operation, which can make the descriptors change as properties
	 * are set and reset.
	 * @param baseDescriptors
	 * @param propertyTypes 
	 * @param promotedReferenceProperties 
	 */
	protected void setupPromotedProperties(Collection<IPropertyDescriptor> baseDescriptors, HashMap<String,AbstractPropertyType> propertyTypes, Collection<IPropertyDescriptor> promotedReferenceProperties) {
		promotedReferencePropertyReferenceNames = new HashMap<Object, Object>();
		promotingReferencePropertyDescriptors = new HashSet<Object>(); 

		// gather base property names so we don't override them
		Set<Object> basePropertyNames = new HashSet<Object>();
		for (IPropertyDescriptor baseDescriptor : baseDescriptors) {
			basePropertyNames.add(baseDescriptor.getId());
		}
		
		// add promoted properties
		for (IPropertyDescriptor promotingDescriptor : promotedReferenceProperties) {
			promotingReferencePropertyDescriptors.add(promotingDescriptor.getId());
			IPropertyValueSource referenceValueSource = valueSource.lookupReferencePropertyValueSource(promotingDescriptor.getId());
			
			if (referenceValueSource != null && referenceValueSource != valueSource) {
				EObject referenceEObject = referenceValueSource.getEObject();
				IPropertySource referencePS = ModelUtils.getPropertySource(referenceEObject);
				if (referencePS != null) {
					IPropertyDescriptor[] descriptors = referencePS.getPropertyDescriptors();
					for (IPropertyDescriptor descriptor : descriptors) {
						if (basePropertyNames.contains(descriptor.getId())) {
							// suppress message for known conflicts
							if (descriptor.getId().equals(model.getNamePropertyId()))
								continue;

							String fmt = "Property ''{0}'' promoted through reference property ''{1}'' to instance ''{2}'' ignored since it conflicts with a property defined in instance ''{3}''";
							String msg = MessageFormat.format(fmt, new Object[] {
								descriptor.getId(), promotingDescriptor.getId(),
								formatInstanceEObject(referenceEObject), 
								formatInstanceEObject(valueSource.getEObject())	
							});
							ComponentSystemPlugin.log(null, msg); 

						} else if (promotedReferencePropertyReferenceNames.containsKey(descriptor.getId())) {
							String fmt = "Property ''{0}'' promoted through reference property ''{1}'' to instance ''{2}'' ignored since it conflicts with a property defined in another promoted reference through property ''{3}'' on instance ''{4}''";  
							String msg = MessageFormat.format(fmt, new Object[] {
									descriptor.getId(), promotingDescriptor.getId(),
									formatInstanceEObject(referenceEObject), 
									promotedReferencePropertyReferenceNames.get(descriptor.getId()),
									formatInstanceEObject(valueSource.getEObject()) });
							ComponentSystemPlugin.log(null, msg); 
						} else {
							// record the promoted property and the reference that provides it
							promotedReferencePropertyReferenceNames.put(descriptor.getId(), promotingDescriptor.getId()); 
							baseDescriptors.add(descriptor);
							
							// promote the referenced types as well
							if (descriptor instanceof AbstractPropertyDescriptor) {
								propertyTypes.put(descriptor.getId().toString(), ((AbstractPropertyDescriptor)descriptor).getPropertyType());
							}
						}
					}
				}
			}
		}
	}
	

	private String formatInstanceEObject(EObject object) {
		IComponentInstance instance = ModelUtils.getComponentInstance(object);
		return MessageFormat.format("{0} ({1})", new Object[] { 
				instance.getName(), instance.getComponentId() });
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		if (propertyDescriptors == null) {
			propertyTypes.clear();
			Set<IPropertyDescriptor> promotedReferenceProperties = new HashSet<IPropertyDescriptor>();
			Collection<IPropertyDescriptor> descriptors = doGetPropertyDescriptors(propertyTypes, promotedReferenceProperties);
			setupPromotedProperties(descriptors, propertyTypes, promotedReferenceProperties);
			propertyDescriptors = (IPropertyDescriptor[]) descriptors.toArray(new IPropertyDescriptor[descriptors.size()]);
		}
		return propertyDescriptors;
	}
	
	protected abstract Collection<IPropertyDescriptor> doGetPropertyDescriptors(Map<String, AbstractPropertyType> emfTypeMap, Collection<IPropertyDescriptor> promotedReferenceProperties);
		// should return a string used in message describing this property source
	protected abstract String getLocalizedDescription();

	public Object getPropertyValue(Object id) {
		IPropertyDescriptor pd = getPropertyDescriptor(id);
		if (pd == null)
			return null;
		
		IPropertyValueSource propertyValueSource = getPropertyValueSourceFor(id);
		Object result = propertyValueSource.getPropertyValue(id);
		if (result == null) {
			result = getDefaultValue(id);
			
			// ensure localized string has a key
			if (result != null && pd instanceof SimplePropertyDescriptor) {
				SimplePropertyDescriptor spd = (SimplePropertyDescriptor) pd;
				SimplePropertyType spt = spd.getPropertyType();
				if (spt.getType().equals(PropertyDataType.LOCALIZED_STRING_LITERAL)) {
					setPropertyValue(id, result);
				}
			}
		}
		else if (result instanceof String) {
			// Data in the model are represented as strings, so attempt to get a value
			// from that.  Note, however, that for enum properties, the internal values
			// are already strings, so ignore a failure to convert in that case.
			ITypeDescriptor td = getPropertyTypeDescriptor(id);
			if (td != null) {
				Object value = td.valueOf((String)result);
				if (value != null) {
					result = value;
				}
			}
		}
		else if (result instanceof ISequencePropertyValue) {
			ArrayPropertyType apt = (ArrayPropertyType) getProperty(id);
			result = new ArrayPropertySource(apt, componentSet, (ISequencePropertyValue)result, strings);
		}
		else if (result instanceof IPropertyValueSource) {
			IPropertyValueSource childValueSource = (IPropertyValueSource) result;
			ITypeDescriptor td = getPropertyTypeDescriptor(id);
			if (td != null && td instanceof CompoundPropertyTypeDescriptor) {
				CompoundPropertyTypeDescriptor cptd = (CompoundPropertyTypeDescriptor) td;
				AbstractPropertyType propertyType = getProperty(id);
				boolean readOnly = propertyType != null? propertyType.isReadOnly() : false;
				result = new CompoundPropertySource(
						cptd, 
						componentSet, childValueSource,
						cptd.getLocalizedStrings(), readOnly);
			}
		}
		return result;
	}
	
	boolean isPropertyReadOnly(Object id) {
		boolean result = false;
		IPropertyDescriptor pd = getPropertyDescriptor(id);
		if (pd instanceof AbstractPropertyDescriptor) {
			AbstractPropertyDescriptor apd = (AbstractPropertyDescriptor) pd;
			result = apd.isReadOnly();
		}
		return result;
	}

	public boolean isPropertySet(Object id) {
		IPropertyValueSource propertyValueSource = getPropertyValueSourceFor(id);
		return propertyValueSource.hasPropertyValue(id);
	}

	public void resetPropertyValue(Object id) {
		IPropertyValueSource propertyValueSource = getPropertyValueSourceFor(id);
		if (getInhibitDefaultValue(id)) {
			propertyValueSource.resetPropertyValue(id);
		}
		else {
			// restore the default, also restoring the default localization state
			setPropertyValue(id, getDefaultValue(id), true);
		}
		if (promotingReferencePropertyDescriptors.contains(id)) {
			propertyDescriptors = null;
		}
		
	}
	
	public Object getDefaultValue(final Object id) {
		Object result = null;
		IPropertyDescriptor pd = getPropertyDescriptor(id);
		if (pd instanceof AbstractPropertyDescriptor) {
			AbstractPropertyDescriptor apd = (AbstractPropertyDescriptor) pd;
			result = apd.getDefaultValue();
		}
		return result;
	}
	
	/**
	 * Returns the name of the primitive, compound, or enum
	 * property type. Null is returned for array properties
	 */
	public String getPropertyTypeName(Object id) {
		if (id == null) return null;
		String result = null;
		
		PropertySwitch pswitch = new PropertySwitch() {
			protected Object doSimpleProperty(SimplePropertyType simpleProperty) {
				return simpleProperty.getType().getName();
			}

			protected Object doCompoundProperty(CompoundPropertyType compoundProperty) {
				return compoundProperty.getType();
			}

			protected Object doEnumProperty(EnumPropertyType enumProperty) {
				return enumProperty.getType();
			}

			protected Object doArrayProperty(ArrayPropertyType arrayProperty) {
				return null;
			}

			protected Object doComponentReferenceProperty(ComponentReferencePropertyType type) {
				return ITypeDescriptor.STRING_TYPE;
			}
		};

		AbstractPropertyType apt = getProperty(id);
		if (apt != null) {
			result = (String) pswitch.doSwitch(apt);
		}
		return result;
	}
	
	private void setCompoundPropertyFromEditableValue(Object id, Object editableValue) {
		IPropertyValueSource propertyValueSource = getPropertyValueSourceFor(id);
		boolean wasSet = isPropertySet(id);
		Object currValue = getPropertyValue(id);
		if (!wasSet) {
			// for compound properties the default is not initially
			// stored back to the value source
			setPropertyValue(id, currValue);
		}
		
		// caller has already checked that this is a valid compound property
		Check.checkState(currValue instanceof IPropertySource);
		IPropertySource ps = (IPropertySource) currValue;
		// first check for a value converter on the compound property type,
		// then a reconciler on the component
		
		CompoundPropertyTypeDescriptor cptd = (CompoundPropertyTypeDescriptor) getPropertyTypeDescriptor(id);
		ICompoundPropertyValueConverter valueConverter = cptd.getValueConverter();
		if (valueConverter != null) {
			valueConverter.applyEditableValue(propertyValueSource.getEObject(), editableValue, ps);
		}
		else {
			String propertyTypeName = getPropertyTypeName(id);
			if (propertyTypeName != null) {
				IReconcileProperty reconcileProperty = 
					(IReconcileProperty) EcoreUtil.getRegisteredAdapter(propertyValueSource.getEObject(), IReconcileProperty.class);
				
				if (reconcileProperty != null)
					reconcileProperty.applyDisplayValue(propertyTypeName, editableValue, ps);
				else {
					String fmt = Messages.getString("AbstractPropertySource.1"); //$NON-NLS-1$
					String propertyDisplayName = getPropertyDescriptor(id).getDisplayName();
					Object params[] = {propertyDisplayName, editableValue.toString()};
					String msg = MessageFormat.format(fmt, params);
					ComponentSystemPlugin.log(null, msg);
				}
			}
		}
	}
	
	protected void setPropertyFromCollection(Object id, Collection collectionValue) {
		boolean wasSet = isPropertySet(id);
		Object currValue = getPropertyValue(id);
		if (currValue instanceof ArrayPropertySource) {
			ArrayPropertySource aps = (ArrayPropertySource) currValue;
			int currSize = aps.size();
			boolean localized = aps.isElementLocalizable();

			int index = 0;
			for (Iterator iter = collectionValue.iterator(); iter.hasNext();) {
				Object curr = iter.next();
				if (index < currSize) {
					aps.setPropertyValue(Integer.toString(index), curr);
				}
				else {
					if (curr instanceof IPropertySource) {
						IPropertySource currPS = (IPropertySource) curr;
						// add a new compound property element, then replace
						// with the new value
						aps.addCompoundProperty(index);
						aps.setPropertyValue(Integer.toString(index), currPS);
					} else {
						aps.addSimpleProperty(index, curr);
					}
				}
				++index;
			}
			// remove any excess items
			int removeCount = currSize-index;
			while (removeCount-- > 0) {
				aps.remove(index);
			}
			
			// if there are localized strings the re-set the
			// values for setPropertyValue. This is needed because the sequence
			// oriented API doesn't support localized values, only the property-source API.
			if (localized) {
				int indexID = 0;
				for (Iterator iter = collectionValue.iterator(); iter.hasNext();) {
					Object curr = iter.next();
					aps.setPropertyValue(Integer.valueOf(indexID++).toString(), curr, true);
				}
			
			}
			
			if (!wasSet) {
				setPropertyValue(id, currValue);
			}
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	private void setPropertyFromPropertySource(Object id, IPropertySource value) {
		IPropertyValueSource propertyValueSource = getPropertyValueSourceFor(id);
		boolean wasSet = isPropertySet(id);
		Object currValue = getPropertyValue(id);
		if (!wasSet || value != currValue) {
			propertyValueSource.setPropertyValue(id, value);
		}
	}
	
	private void setPropertyFromArrayEditableValue(Object id, ArrayEditableValue value) {
		boolean wasSet = isPropertySet(id);
		Object currValue = getPropertyValue(id);
		if (currValue instanceof ArrayPropertySource) {
			if (currValue instanceof ArrayPropertySource) {
				ArrayPropertySource destPS = (ArrayPropertySource) currValue;
				int currSize = destPS.size();
				boolean localized = destPS.isElementLocalizable();
				
				ISequencePropertySource sourcePS = value.getValue();

				int index = 0;
				for (Iterator iter = sourcePS.iterator(); iter.hasNext();) {
					Object curr = iter.next();
					if (index < currSize) {
						destPS.setPropertyValue(Integer.toString(index), curr);
					}
					else {
						if (curr instanceof IPropertySource) {
							IPropertySource currPS = (IPropertySource) curr;
							// add a new compound property element, then replace
							// with the new value
							destPS.addCompoundProperty(index);
							destPS.setPropertyValue(Integer.toString(index), currPS);
						} else {
							destPS.addSimpleProperty(index, curr);
						}
					}
					++index;
				}
				// remove any excess items
				int removeCount = currSize-index;
				while (removeCount-- > 0) {
					destPS.remove(index);
				}
				
				// if there are localized strings the re-set the
				// values for setPropertyValue. This is needed because the sequence
				// oriented API doesn't support localized values, only the property-source API.
				if (localized) {
					int indexID = 0;
					for (Iterator iter = sourcePS.iterator(); iter.hasNext();) {
						Object curr = iter.next();
						destPS.setPropertyValue(Integer.valueOf(indexID++).toString(), curr, true);
					}
				
				}
				
				if (!wasSet) {
					setPropertyValue(id, currValue);
				}
			}
			else {
				throw new IllegalArgumentException();
			}
		}
	}
	
	public void setPropertyValue(Object id, Object value) {
		setPropertyValue(id, value, false);
	}
	
	
	void setPropertyValue(Object id, Object value, boolean overrideLocalizedState) {
		ITypeDescriptor td = getPropertyTypeDescriptor(id);
		if (td == null) {
			String fmt = Messages.getString("AbstractPropertySource.3"); //$NON-NLS-1$
			Object params[] = {getPropertyIdentifier(id)};
			String msg = MessageFormat.format(fmt, params);
			ComponentSystemPlugin.log(null, msg);
			return;
		}
		
		IPropertyValueSource propertyValueSource = getPropertyValueSourceFor(id);
		Check.checkState(propertyValueSource != null);
		
		if (td instanceof CompoundPropertyTypeDescriptor &&
			(value != null && !(value instanceof IPropertySource))) {
			
			setCompoundPropertyFromEditableValue(id, value);
			
		} else if (value instanceof ArrayPropertySource) {
			
			ArrayPropertySource aps = (ArrayPropertySource) value;
			ISequencePropertyValue spv = aps.getSequencePropertyValue();
			propertyValueSource.setPropertyValue(id, spv);
		}
		else if (value instanceof IPropertySource) {
			setPropertyFromPropertySource(id, (IPropertySource)value);
		} else if (value instanceof ArrayEditableValue) {
			setPropertyFromArrayEditableValue(id, (ArrayEditableValue)value);
		}
		else if (value instanceof Collection) {
			setPropertyFromCollection(id, (Collection) value);
		}
		else if (value != null) {
			// we'll check if the new value is the default
			// value both before and after conversion to text
			Object defaultValue = getDefaultValue(id);
			boolean inhibitDefaultValue = getInhibitDefaultValue(id);

			// Values coming in might be from the data model and not already internalized,
			// but then again, they might be enums, which use strings for both formats.
			// Don't balk if the conversion fails.
			if (value instanceof String) {
				Object valueObj = td.valueOf((String)value);
				if (valueObj != null) {
					value = valueObj;
				}
				
	            ICellEditorValidator validator = getCellEditorValidator(id);
	            if (validator != null) {
	            	String msg = validator.isValid(value);
	            	if (msg != null ) {
	            		throw new IllegalArgumentException(msg);
	            	}
	            }
			}

            if (inhibitDefaultValue && value.equals(defaultValue))
				resetPropertyValue(id);
			else {
				String strValue = td.toStorageString(value);
				Check.checkState(strValue != null);
				if (inhibitDefaultValue && strValue.equals(defaultValue))
					resetPropertyValue(id);
				else {
					if (td.isLocalizable()) {
						propertyValueSource.setLocalizableStringPropertyValue(id, strValue, overrideLocalizedState);
					}
					else if (td.isReference()) {
						propertyValueSource.setReferencePropertyValue(id, strValue);
						
					}
					else {
						propertyValueSource.setPropertyValue(id, strValue);
					}
				}
			}
            
			if (promotingReferencePropertyDescriptors.contains(id)) {
				// promoting reference property changed, so reset property descriptors
				propertyDescriptors = null;
			}
            
		}
		else
			resetPropertyValue(id);			
	}

		/**
		 * Return true if default values should be filtered from the
		 * data model by replacing with null. When false is returned
		 * values equal to the default are explicitly stored.
		 */
	protected boolean getInhibitDefaultValue(Object id) {
		boolean result = true;
		AbstractPropertyType apt = getProperty(id);
		if (apt instanceof SimplePropertyType) {
			SimplePropertyType spt = (SimplePropertyType) apt;
			if (spt.getType().equals(PropertyDataType.LOCALIZED_STRING_LITERAL) &&
				spt.getDefault() != null) {
				result = false;
			}
		}
		return result;
	}

	public boolean isPropertyResettable(Object id) {
		boolean result = isPropertySet(id) && !isPropertyReadOnly(id);
		if (result) {
			if (id.equals(model.getNamePropertyId())) {
				result = false;
			} else {
				AbstractPropertyType apt = getProperty(id);
				result = apt.isResettable();
			}
		}
		return result;
	}

	protected ITypeDescriptor getPropertyTypeDescriptor(final AbstractPropertyType propertyType) {
		ITypeDescriptor result = null;
		if (propertyType != null) {
			
			PropertySwitch pswitch = new PropertySwitch() {
				protected Object doSimpleProperty(SimplePropertyType simpleProperty) {
					return TypeDescriptors.lookupPrimitiveDescriptor(
							(ComponentProvider)componentSet.getProvider(), 
							simpleProperty.getType(),
							componentSet.lookupTypeDescriptor(simpleProperty.getExtendWithEnum()));
				}

				protected Object doCompoundProperty(CompoundPropertyType compoundProperty) {
					ITypeDescriptor result = null;
					if (componentSet != null) {
						String typeID = compoundProperty.getType();
						result = componentSet.lookupTypeDescriptor(typeID);
					}
					return result;
				}

				protected Object doEnumProperty(EnumPropertyType enumProperty) {
					ITypeDescriptor result = null;
					if (componentSet != null) {
						String typeID = enumProperty.getType();
						result = componentSet.lookupTypeDescriptor(typeID);
						ITypeDescriptor extendingEnum = componentSet.lookupTypeDescriptor(enumProperty.getExtendWithEnum());
						if (extendingEnum != null) {
							result = TypeDescriptors.getExtendedPropertyDescriptor(result, extendingEnum);
						}
					}				
					return result;
				}

				protected Object doArrayProperty(ArrayPropertyType arrayProperty) {
					ArrayPropertyTypeDescriptor aptd = new ArrayPropertyTypeDescriptor(arrayProperty, strings);
					aptd.setComponentSet(componentSet);
					return aptd;
				}

				protected Object doComponentReferenceProperty(ComponentReferencePropertyType type) {
					ComponentReferenceTypeDescriptor crtd = new ComponentReferenceTypeDescriptor();
					crtd.setComponentSet(componentSet);
					return crtd;
				}
			};
		
			result = (ITypeDescriptor) pswitch.doSwitch(propertyType);
		}
		return result;	
	}
	
	IPropertyDescriptor getPropertyDescriptor(Object propertyID) {
		IPropertyDescriptor result = null;
		IPropertyDescriptor pds[] = getPropertyDescriptors();
		if (pds != null) {
			for (int i=0 ; i < pds.length; i++) { 
				if (pds[i].getId().equals(propertyID)) {
					result = pds[i];
					break;
				}
			}
		}
		return result;
	}
	
	public ITypeDescriptor getPropertyTypeDescriptor(Object propertyID) {
		// refresh as needed
		getPropertyDescriptors();
		
		AbstractPropertyType propertyType = getProperty(propertyID);
		return getPropertyTypeDescriptor(propertyType);
	}
	
	protected IPropertyEditorFactory getPropertyEditorFactory(Object propertyID) {
		IPropertyEditorFactory result = null;
		IPropertyDescriptor pd = getPropertyDescriptor(propertyID);
		if (pd instanceof AbstractPropertyDescriptor) {
			result = ((AbstractPropertyDescriptor)pd).getEditorFactory();
		}
		return result;
	}
	
	protected ICellEditorValidator getCellEditorValidator(final Object propertyID) {
		ICellEditorValidator result = null;
		
		IPropertyEditorFactory factory = getPropertyEditorFactory(propertyID);
		if (factory != null) {
			IPropertyValueSource propertyValueSource = getPropertyValueSourceFor(propertyID);
			EObject obj = propertyValueSource.getEObject();
			result = factory.createCellEditorValidator(obj, getPropertyPath(propertyID));
		}
	
		if (result == null) {
			final ITypeDescriptor td = getPropertyTypeDescriptor(propertyID);
			
			PropertySwitch ps = new PropertySwitch() {
	
				protected Object doSimpleProperty(SimplePropertyType simpleProperty) {
					return td.getValidator();
				}
	
				protected Object doCompoundProperty(CompoundPropertyType compoundProperty) {
					return td.getValidator();			}
	
				protected Object doEnumProperty(EnumPropertyType enumProperty) {
					return td.getValidator();
				}
	
				protected Object doArrayProperty(ArrayPropertyType arrayProperty) {
					return td.getValidator();
				}
	
				protected Object doComponentReferenceProperty(ComponentReferencePropertyType type) {
					ICellEditorValidator result = null;
					IPropertyDescriptor pd = getPropertyDescriptor(propertyID);
					if (pd instanceof ComponentReferencePropertyDescriptor) {
						ComponentReferencePropertyDescriptor crpd = (ComponentReferencePropertyDescriptor) pd;
						result = crpd.getValidator();
					}
					return result;
				}
			};
			AbstractPropertyType apt = getProperty(propertyID);
			if (apt != null) {
				result = (ICellEditorValidator) ps.doSwitch(apt);
			}
			else if (td != null) {
				result = td.getValidator();
			}
		}
		
		return result;
	}

	public IPropertyValueSource getPropertyValueSource() {
		return valueSource;
	}

	public IComponentSet getComponentSet() {
		return componentSet;
	}

	public ILocalizedStrings getStrings() {
		return strings;
	}

	public String getPropertyValueSymbol(Object propertyId) {
		IPropertyValueSource propertyValueSource = getPropertyValueSourceFor(propertyId);
		return propertyValueSource.getStringPropertyValueSymbol(propertyId);
	}

	private IPropertyValueSource getPropertyValueSourceFor(Object propertyId) {
		// refresh as needed
		getPropertyDescriptors();

		Object referencePropertyId = promotedReferencePropertyReferenceNames.get(propertyId);
		if (referencePropertyId != null) {
			IPropertyValueSource referenceValueSource = valueSource.lookupReferencePropertyValueSource(referencePropertyId);
			if (referenceValueSource != null) {
				return referenceValueSource;
			}
		}
		return valueSource;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		
		boolean result = true;
		if (obj instanceof AbstractPropertySource) {
			AbstractPropertySource other = (AbstractPropertySource) obj;
			IPropertyDescriptor descriptors[] = getPropertyDescriptors();
			IPropertyDescriptor otherDescriptors[] = other.getPropertyDescriptors();
			if (otherDescriptors != null &&
				descriptors.length == otherDescriptors.length) {
				for (int i = 0; i < descriptors.length; i++) {
					Object id = descriptors[i].getId();
					boolean currEqual = false;
					if (other.getPropertyDescriptor(id) != null) {
						Object thisValue = getPropertyValue(id);
						Object otherValue = other.getPropertyValue(id);
						currEqual = ObjectUtils.equals(thisValue, otherValue);
					}
					if (!currEqual) {
						result = false;
						break;
					}
				}
			}
			else {
				result = false;
			}
		}
		else {
			result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		// class not intended for use in maps, hashCode not implemented
		assert false;
		return super.hashCode();
	}

	public String getPropertyPath() {
		return valueSource.getPropertyPath();
	}
	
	public String getPropertyPath(Object propertyId) {
		return valueSource.getPropertyPath(propertyId);
	}
	
	public EObject getPropertyOwner(Object propertyId) {
		if (propertyId == null)
			return valueSource.getEObject();
		
		// see if it's known at all first
		if (getPropertyTypeDescriptor(propertyId) == null)
			return null;
		// then get the value source owner
		IPropertyValueSource propertyValueSource = getPropertyValueSourceFor(propertyId);
		if (propertyValueSource == null)
			return null;
		return propertyValueSource.getEObject();
	}

	public void refresh() {
		propertyDescriptors = null;
	}
	
	public ICompoundPropertyValueConverter getCompoundPropertyValueConverter() {
		return null;
	}
}
