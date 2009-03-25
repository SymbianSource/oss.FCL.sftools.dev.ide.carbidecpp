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
package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.properties.AbstractPropertyDescriptor;
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.ui.views.properties.*;

import junit.framework.TestCase;

public class PropertyOverrideTest extends TestCase {

	static final String BASE_COMPONENT_NAME = "com.nokia.examples.baseComponent";
	static final String OVERRIDE_COMPONENT_NAME = "com.nokia.test.propertyOverrideTest";
	static final String sdkName = "com.nokia.series60";

	IComponentSet componentSet;
	
	protected void setUp() throws Exception {
		componentSet = ComponentHelpers.querySDKFilter(
				TestDataModelHelpers.getDefaultComponentProvider(), 
				sdkName, "2.0");
	}

	IPropertySource getPropertySource(IComponent component) {
		IPropertySourceProvider psp = (IPropertySourceProvider) component.getAdapter(IPropertySourceProvider.class);
		return psp.getPropertySource(new MockPropertyValueSource(null));
	}
	
	IPropertySource getPropertySource(IPropertySource ps, Object propertyId) {
		Object pv = ps.getPropertyValue(propertyId);
		assertNotNull(pv);
		assertTrue(pv instanceof IPropertySource);
		return (IPropertySource) pv;
	}
	IPropertyDescriptor getPropertyDescriptor(IPropertySource ps, Object propertyId) {
		IPropertyDescriptor pds[] = ps.getPropertyDescriptors();
		IPropertyDescriptor result = null;
		for (int i = 0; i < pds.length; i++) {
			if (pds[i].getId().equals(propertyId)) {
				result = pds[i];
				break;
			}
		}
		return result;
	}

	public void testOverrides() {
		IComponent baseComponent = componentSet.lookupComponent(BASE_COMPONENT_NAME);
		assertNotNull(baseComponent);
		IComponent overrideComponent = componentSet.lookupComponent(OVERRIDE_COMPONENT_NAME);
		assertNotNull(overrideComponent);
						
		IPropertySource basePS = getPropertySource(baseComponent);
		IPropertySource overridePS = getPropertySource(overrideComponent);
		
		// this "flag" property is overriden to be read-only
		String id = "flag";
		AbstractPropertyDescriptor basePD = (AbstractPropertyDescriptor) getPropertyDescriptor(basePS, id);
		assertFalse(basePD.isReadOnly());
		AbstractPropertyDescriptor overridePD = (AbstractPropertyDescriptor) getPropertyDescriptor(overridePS, id);
		assertTrue(overridePD.isReadOnly());
		
		// the category of the "location" property is overriden
		id = "location";
		basePD = (AbstractPropertyDescriptor) getPropertyDescriptor(basePS, id);
		assertEquals("Layout", basePD.getCategory());
		overridePD = (AbstractPropertyDescriptor) getPropertyDescriptor(overridePS, id);
		assertEquals("Hidden", overridePD.getCategory());
		
		// the default value of the "text" property is overriden
		id = "text";
		Object value = basePS.getPropertyValue(id);
		assertEquals("", value);
		value = overridePS.getPropertyValue(id);
		assertEquals("overriden value", value);
		
	}
}
