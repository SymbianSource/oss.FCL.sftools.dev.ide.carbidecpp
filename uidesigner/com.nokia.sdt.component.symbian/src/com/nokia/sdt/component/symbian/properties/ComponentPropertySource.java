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

package com.nokia.sdt.component.symbian.properties;

import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IPropertyExtenders;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NamePropertySupport;
import com.nokia.sdt.emf.component.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import java.text.MessageFormat;
import java.util.*;

public class ComponentPropertySource extends AbstractPropertySource {

	private Component component;
	
	public ComponentPropertySource(Component component, IPropertyValueSource valueSource,
				ILocalizedStrings strings) {
		super(component.getComponentSet(), valueSource, strings);
		this.component = component;
		initialize();
	}
	
	protected String getLocalizedDescription() {
		String fmt = Messages.getString("ComponentPropertySource.0"); //$NON-NLS-1$
		Object params[] = {component.getId()};
		return MessageFormat.format(fmt, params);
	}
	
	@Override
	public String getPropertyIdentifier(Object id) {
		String fmt = Messages.getString("ComponentPropertySource.2"); //$NON-NLS-1$
		Object params[] = {component.getId(), id.toString()};
		return MessageFormat.format(fmt, params);
	}

	void addComponentProperties(ArrayList pdList, Map emfTypeMap, Component currComponent, Collection<IPropertyDescriptor> promotedReferenceProperties) {
		Component base = currComponent.getBaseComponent();
		if (base != null) {
			addComponentProperties(pdList, emfTypeMap, base, promotedReferenceProperties);
		}

		PropertiesType properties = currComponent.getEMFComponent().getProperties();
		if (properties != null) {
			ILocalizedStrings strings = currComponent.getLocalizedStrings();
			List emfProperties = properties.getAbstractProperty();
			addProperties(pdList, emfTypeMap, emfProperties, strings, false, promotedReferenceProperties, null);
		}
	}
	
	protected Collection<IPropertyDescriptor> doGetPropertyDescriptors(Map<String, AbstractPropertyType> emfTypeMap, Collection<IPropertyDescriptor> promotedReferenceProperties) {
		ArrayList pdList = new ArrayList();
		addComponentProperties(pdList, emfTypeMap, component, promotedReferenceProperties);
		addExtensionProperties(pdList, emfTypeMap, promotedReferenceProperties);
		applyPropertyOverrides(pdList, component);
		return pdList;
	}

	static boolean isNameProperty(AbstractPropertyType apt) {
		boolean result = false;
		if (apt instanceof SimplePropertyType) {
			SimplePropertyType spt = (SimplePropertyType) apt;
			if (spt.getType().getValue() == PropertyDataType.UNIQUE_NAME) {
				result = true;
			}
		}
		return result;
	}

	public Object getDefaultValue(Object id) {
		Object result = null;
		AbstractPropertyType apt = getProperty(id);
		if (isNameProperty(apt)) {
			IPropertyValueSource valueSource = getPropertyValueSource();
			if (valueSource.getDesignerDataModel() != null) {
				result = NamePropertySupport.generateDefaultName(
						valueSource.getDesignerDataModel(), component);
			}
		}
		else {
			result = super.getDefaultValue(id);
		}
		return result;
	}
	
	protected boolean getInhibitDefaultValue(Object id) {
		boolean result;
		AbstractPropertyType apt = getProperty(id);
		if (isNameProperty(apt)) {
			result = false;
		}
		else {
			result = super.getInhibitDefaultValue(id);
		}
		return result;
	}

	private static IPropertyExtenders getExtenderInterface(EObject instance) {
		IPropertyExtenders result = (IPropertyExtenders) EcoreUtil.getRegisteredAdapter(instance, IPropertyExtenders.class);	
		return result;
	}
	
	/**
	 * Build the set of instances to query for extension properties.
	 * @param instance the instance to query for a list of extending instances
	 * @param targetInstance the instance that is to receive extension properties
	 * @param set holds the list of instances that should be queried
	 */
	private static void checkPropertyExtenders(EObject instance, EObject targetInstance, Set set) {
		IPropertyExtenders pe = getExtenderInterface(instance);
		if (pe != null) {
			EObject[] newObjects = pe.getPropertyExtenders(targetInstance);
			if (newObjects != null) {
				for (int i = 0; i < newObjects.length; i++) {
					if (!set.contains(newObjects[i])) {
						set.add(newObjects[i]);
						checkPropertyExtenders(newObjects[i], targetInstance, set);
					}
				}
			}
		}
	}
	
	private ExtensionPropertiesType findExtensionPropertySet(String setName, Component extenderComponent) {
		ExtensionPropertiesType result = null;
		List extensionSets = extenderComponent.getEMFComponent().getExtensionProperties();
		if (extensionSets != null) {
			for (Iterator iter = extensionSets.iterator(); iter.hasNext();) {
				ExtensionPropertiesType extensionProperties = (ExtensionPropertiesType) iter.next();
				if (ObjectUtils.equals(extensionProperties.getName(), setName)) {
					result = extensionProperties;
					break;
				}
			}
			if (result == null) {
				Component baseComponent = extenderComponent.getBaseComponent();
				if (baseComponent != null) {
					result = findExtensionPropertySet(setName, baseComponent);
				}
			}
		}
		return result;
	}
	
	// look up the named extension property set in the given instance, and add the property
	// descriptors to the pdList parameter.  Ignore duplicate extension properties added via other extension properties.
	private void addOneExtensionPropertySet(EObject extenderInstance, String setName,
			List<IPropertyDescriptor> pdList, Map<String, AbstractPropertyType> emfTypeMap, Collection<IPropertyDescriptor> promotedReferenceProperties, Set<String> extendedProperties) {
		IComponentInstance extenderCI = ModelUtils.getComponentInstance(extenderInstance);
		if (extenderCI != null) {
			Component extenderComponent = (Component) extenderCI.getComponent();
			if (extenderComponent != null) {
				ExtensionPropertiesType extensionProperties = findExtensionPropertySet(setName, extenderComponent);
				if (extensionProperties != null) {
					List emfProperties = extensionProperties.getAbstractProperty();
					ILocalizedStrings strings = extenderComponent.getLocalizedStrings();
					addProperties(pdList, emfTypeMap, emfProperties, strings, false, promotedReferenceProperties, extendedProperties);
				}
				else {
					String fmt = Messages.getString("ComponentPropertySource.1"); //$NON-NLS-1$
					Object params[] = {setName, extenderComponent.getId()};
					String msg = MessageFormat.format(fmt, params);
					ComponentSystemPlugin.log(null, msg);
				}
			}
		}
	}

	// Add extension properties. 
	// Starting with this instance itself, we use the IPropertyExtenders interface
	// to first build a set of all instances that should be queried for extension properties
	// Then we ask each instance in the set for the name of the extension property set to use for this
	// instance.
	private void addExtensionProperties(List<IPropertyDescriptor> pdList, Map emfTypeMap, Collection<IPropertyDescriptor> promotedReferenceProperties) {
		// start with the instance represented by the component source and add each
		// referenced instance to the set
		HashSet set = new HashSet();
		EObject thisInstance = getPropertyValueSource().getEObject();
		// can be null in unit tests
		if (thisInstance != null) {
			checkPropertyExtenders(thisInstance, thisInstance, set);
			
			Set<String> extendedProperties = new HashSet<String>();
			for (Iterator iter = set.iterator(); iter.hasNext();) {
				EObject currInstance = (EObject) iter.next();
				IPropertyExtenders pe = getExtenderInterface(currInstance);
				if (pe != null) {
					String[] extensionSetNames = pe.getExtensionSetNames(thisInstance);
					if (extensionSetNames != null) {
						for (String extensionSetName : extensionSetNames) {
							if (TextUtils.strlen(extensionSetName) > 0) {
								addOneExtensionPropertySet(currInstance, extensionSetName, pdList, emfTypeMap, promotedReferenceProperties, extendedProperties);
							}
						}
					}
				}
			}
		}
	}
	
	private void applyPropertyOverrides(List<IPropertyDescriptor> pdList, Component currComponent) {
		Component base = currComponent.getBaseComponent();
		if (base != null) {
			applyPropertyOverrides(pdList, base);
		}

		PropertyOverridesType overrides = currComponent.getEMFComponent().getPropertyOverrides();
		if (overrides != null && overrides.getPropertyOverride() != null) {
			for (Iterator iter = overrides.getPropertyOverride().iterator(); iter.hasNext();) {
				PropertyOverrideType pot = (PropertyOverrideType) iter.next();
				// It's ok to have an override for a property not in the list. This allows
				// an override for an extension property that's not currently active
				// 
				// Most our our property descriptors are derived from IPropertyDescriptor, with the
				// exception being array elements. Since it doesn't make sense to override an array element,
				// we can ignore those.
				IPropertyDescriptor overrideTarget = findDescriptorInDescriptorList(pot.getName(), pdList);
				if (overrideTarget instanceof AbstractPropertyDescriptor) {
					AbstractPropertyDescriptor pd = (AbstractPropertyDescriptor) overrideTarget;
					if (pot.isSetReadOnly()) {
						pd.setReadOnly(pot.isReadOnly());
					}
					if (pot.getCategory() != null) {
						String category = currComponent.getLocalizedStrings().checkPercentKey(pot.getCategory());
						pd.setCategory(category);
					}
					if (pot.getDefault() != null) {
						pd.setDefaultValueInitializer(pot.getDefault());
					}
				}
			}
		}
	}
	
	private static IPropertyDescriptor findDescriptorInDescriptorList(String name, List<IPropertyDescriptor> pdList) {
		IPropertyDescriptor result = null;
		for (IPropertyDescriptor pd : pdList) {
			if (pd.getId().equals(name)) {
				result = pd;
				break;
			}
		}
		return result;
	}
	
	public ITypeDescriptor getTypeDescriptor() {
		return null;
	}
}
