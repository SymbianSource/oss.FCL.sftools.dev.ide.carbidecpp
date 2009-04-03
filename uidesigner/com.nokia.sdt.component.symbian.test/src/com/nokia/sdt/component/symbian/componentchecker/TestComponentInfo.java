/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.componentchecker;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.properties.CompoundPropertyTypeDescriptor;
import com.nokia.sdt.component.symbian.properties.EnumPropertyTypeDescriptor;
import com.nokia.sdt.emf.component.*;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;
import com.nokia.cpp.internal.api.utils.core.ObjectUtils;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.text.MessageFormat;
import java.util.*;

public class TestComponentInfo extends BaseComponentTest {
	private static final String TEST_PROPERTY_INFO_DISPLAY_NAME = 
		"property-info-displayName";
	private static final String TEST_PROPERTY_INFO_DESCRIPTION_KEY = 
		"property-info-descriptionKey";
	private static final String TEST_PROPERTY_INFO_HELP_KEY = 
		"property-info-helpKey";
	private static final String TEST_EVENT_INFO_DISPLAY_NAME = 
		"event-info-displayName";
	private static final String TEST_EVENT_INFO_DESCRIPTION_KEY = 
		"event-info-descriptionKey";
	private boolean checkHelp;


	/**
	 * @param checker
	 */
	TestComponentInfo(ComponentChecker checker, IComponent component, boolean checkHelp) {
		super(checker, component);
		this.checkHelp = checkHelp;
	}

	public void runTests() {
		ComponentType ct = ((Component) component).getEMFComponent();
		PropertiesType properties = ct.getProperties();
		if (properties != null && properties.getAbstractProperty() != null) {
			for (Iterator iter = properties.getAbstractProperty().iterator(); iter.hasNext();) {
				AbstractPropertyType ap	= (AbstractPropertyType) iter.next();
				checkPropertyInfo(component, ap, null, null, null);
			}
		}
		
		EList extensionPropertiesSet = ct.getExtensionProperties();
		if (extensionPropertiesSet != null) { 
			for (Iterator iter = extensionPropertiesSet.iterator(); iter.hasNext();) {
				ExtensionPropertiesType extensionProperties = (ExtensionPropertiesType) iter.next();
				for (Iterator iterator = extensionProperties.getAbstractProperty().iterator(); iterator
						.hasNext();) {
					AbstractPropertyType ap	= (AbstractPropertyType) iterator.next();
					checkPropertyInfo(component, ap, extensionProperties.getName(), null, null);
				}
			}
		}
		
		EventsType events = ct.getEvents();
		if (events != null) {
			for (Iterator iter = events.getEvent().iterator(); iter.hasNext();) {
				EventType et = (EventType) iter.next();
				checkEventInfo(component, et);
			}
		}

	}

	/**
	 * @param ap
	 * @param parentPropertyName TODO
	 */
	public void checkPropertyInfo(IComponent component, AbstractPropertyType ap, String extensionSetName, String parentPropertyName, ILocalizedStrings typeStrings) {
		if (ObjectUtils.equals(ap.getCategory(), "Hidden"))
			return;
		
		String propPath = parentPropertyName != null ? parentPropertyName +"."+ap.getName() : ap.getName();

		// make sure each property's name has a key
		if (ap.getDisplayName() != null)
			checkStringKey(ap.getDisplayName().substring(1), "displayName", 
					TEST_PROPERTY_INFO_DISPLAY_NAME+"+"+propPath, 
					typeStrings,
					ap.getName(), parentPropertyName, extensionSetName);
		else
			checkStringKey(ap.getName(), "displayName", 
				TEST_PROPERTY_INFO_DISPLAY_NAME+"+"+propPath, 
				typeStrings,
				ap.getName(), parentPropertyName, extensionSetName);
		
		checkStringKey(ap.getDescriptionKey(), "descriptionKey", 
				TEST_PROPERTY_INFO_DESCRIPTION_KEY+"+"+propPath, 
				typeStrings,
				ap.getName(), parentPropertyName, extensionSetName);
		
		checkEnumPropertyDisplayValues(ap);
		
		if (checkHelp) 
			checkStringKey(ap.getHelpKey(), "helpKey", 
					TEST_PROPERTY_INFO_HELP_KEY+"+"+propPath, 
					typeStrings,
					ap.getName(), parentPropertyName, extensionSetName);
		
		CompoundPropertyTypeDescriptor cpd = getCompoundProperties(ap);
		if (cpd != null) {
			for (Iterator iter = cpd.getCompoundPropertyDeclarationType().eContents().iterator(); iter.hasNext();) {
				EObject obj = (EObject) iter.next();
				if (obj instanceof AbstractPropertyType)
					checkPropertyInfo(component, 
							(AbstractPropertyType)obj, 
							extensionSetName,
							ap.getName(),
							cpd.getLocalizedStrings()
							);
			}
			
		}
	}

	private void checkEnumPropertyDisplayValues(AbstractPropertyType ap) {
		// make sure that there is no internal value in an enum that maps to a different display value

		EnumPropertyTypeDescriptor enumProperty = getEnumProperty(ap);
		if (enumProperty == null)
			return;
		
		Collection values = enumProperty.getChoiceOfValues();
		List<Object> valuesList = Arrays.asList(values.toArray()); // ordered list of values
		List<String> displayStringsList = new ArrayList<String>(); // ordered list of display strings
		for (Object value : values) {
			String displayString = enumProperty.toDisplayString(value);
			displayStringsList.add(displayString);
		}
		
		for (int i = 0; i < valuesList.size(); i++) {
			Object value = valuesList.get(i);
			for (int j = 0; j < displayStringsList.size(); j++) {
				String displayString = displayStringsList.get(j);
				check(!displayString.equals(value) || i == j, Severity.ERROR, component, 
						"checkEnumPropertyDisplayValues", 
						MessageFormat.format("In property ''{0}'', found display value ''{1}'' matching a different value than its own.", 
								new Object[] { ap.getName(), displayString }));
			}
			
		}
	}

	private void checkEventInfo(IComponent component, EventType et) {
		// make sure each event's name has a key
		if (et.getDisplayName() != null)
			checkStringKey(et.getDisplayName().substring(1), "displayName", 
					TEST_EVENT_INFO_DISPLAY_NAME+"+"+et.getName(), 
					null,
					et.getName(), null, null);	
		else
			checkStringKey(et.getName(), "displayName", 
					TEST_EVENT_INFO_DISPLAY_NAME+"+"+et.getName(), 
				null,
				et.getName(), null, null);
		
		checkStringKey(et.getDescriptionKey(), "descriptionKey", 
				TEST_EVENT_INFO_DESCRIPTION_KEY+"+"+et.getName(), 
				null,
				et.getName(), null, null);

	}
	
}

