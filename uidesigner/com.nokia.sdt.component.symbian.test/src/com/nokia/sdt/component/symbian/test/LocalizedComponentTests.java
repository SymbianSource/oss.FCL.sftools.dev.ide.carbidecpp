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
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.views.properties.*;

import java.util.Locale;

import junit.framework.TestCase;

public class LocalizedComponentTests extends TestCase {
	
	static final String COMPONENT_NAME = "com.nokia.examples.localizedComponent";
	static final String sdkName = "com.nokia.series60";

	Locale defaultLocale;
	IComponentSet componentSet;
	
	protected void setUp() throws Exception {
		defaultLocale = Locale.getDefault();
		componentSet = ComponentHelpers.querySDKFilter(
				TestDataModelHelpers.getDefaultComponentProvider(), sdkName, "2.0");
	}
	
	protected void tearDown() throws Exception {
		Locale.setDefault(defaultLocale);
	}
	
	private void assertLocalized(String s) {
		assertNotNull(s);
		assertTrue(!s.startsWith("!"));
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
	
	private void testPropertyDisplayName(IPropertySource ps,
			Object propertyId, String expected) {
		IPropertyDescriptor pd = getPropertyDescriptor(ps, propertyId);
		assertNotNull(pd);
		String displayName = pd.getDisplayName();
		assertLocalized(displayName);
		assertEquals(displayName, expected);		
	}

	public void testUS_English() {
		IComponent testComponent = componentSet.lookupComponent(COMPONENT_NAME);
		assertTrue(testComponent != null);
		
		Locale l = Locale.US;
		Locale.setDefault(l);
				
		String friendlyName = testComponent.getFriendlyName();
		assertLocalized(friendlyName);
		assertEquals(friendlyName, "english friendly name");
		
		IPropertySource ps = getPropertySource(testComponent);
		testPropertyDisplayName(ps, "baseProperty", "base localized property");
		testPropertyDisplayName(ps, "name", "english name property");
		
		IPropertySource compoundPS = getPropertySource(ps, "compound1");
		testPropertyDisplayName(compoundPS, "abc", "english abc property");
		
		IPropertyDescriptor enumProp = getPropertyDescriptor(ps, "enum1");
		Object enumVal = ps.getPropertyValue("enum1");
		assertEquals(enumVal, "blue");
		ILabelProvider lp = enumProp.getLabelProvider();
		String blueDisplay = lp.getText(enumVal);
		assertEquals(blueDisplay, "Blue-color");
		
		ps.setPropertyValue("enum1", "red");
		enumVal = ps.getPropertyValue("enum1");
		assertEquals(enumVal, "red");
		String redDisplay = lp.getText(enumVal);
		assertEquals(redDisplay, "english red enum value");
	}

	public void testGenericFrench() {
		IComponent testComponent = componentSet.lookupComponent(COMPONENT_NAME);
		assertTrue(testComponent != null);
		
		Locale l = Locale.FRENCH;
		Locale.setDefault(l);
				
		String friendlyName = testComponent.getFriendlyName();
		assertLocalized(friendlyName);
		assertEquals(friendlyName, "generic french friendly name");
		
		IPropertySource ps = getPropertySource(testComponent);
		testPropertyDisplayName(ps, "baseProperty", "base localized property");
		testPropertyDisplayName(ps, "name", "generic french name property");
		
		IPropertySource compoundPS = getPropertySource(ps, "compound1");
		testPropertyDisplayName(compoundPS, "abc", "generic french abc property");
		
		IPropertyDescriptor enumProp = getPropertyDescriptor(ps, "enum1");
		Object enumVal = ps.getPropertyValue("enum1");
		assertEquals(enumVal, "blue");
		ILabelProvider lp = enumProp.getLabelProvider();
		String blueDisplay = lp.getText(enumVal);
		assertEquals(blueDisplay, "Blue-color");
		
		ps.setPropertyValue("enum1", "red");
		enumVal = ps.getPropertyValue("enum1");
		assertEquals(enumVal, "red");
		String redDisplay = lp.getText(enumVal);
		assertEquals(redDisplay, "generic french red enum value");
	}

	public void testCanadianFrench() {
		IComponent testComponent = componentSet.lookupComponent(COMPONENT_NAME);
		assertTrue(testComponent != null);
		
		Locale l = Locale.CANADA_FRENCH;
		Locale.setDefault(l);
				
		String friendlyName = testComponent.getFriendlyName();
		assertLocalized(friendlyName);
		assertEquals(friendlyName, "Canadian french friendly name");
		
		IPropertySource ps = getPropertySource(testComponent);
		testPropertyDisplayName(ps, "name", "Canadian french name property");
		
		IPropertySource compoundPS = getPropertySource(ps, "compound1");
		testPropertyDisplayName(compoundPS, "abc", "Canadian french abc property");
		
		IPropertyDescriptor enumProp = getPropertyDescriptor(ps, "enum1");
		Object enumVal = ps.getPropertyValue("enum1");
		assertEquals(enumVal, "blue");
		ILabelProvider lp = enumProp.getLabelProvider();
		String blueDisplay = lp.getText(enumVal);
		assertEquals(blueDisplay, "Blue-color");
		
		ps.setPropertyValue("enum1", "red");
		enumVal = ps.getPropertyValue("enum1");
		assertEquals(enumVal, "red");
		String redDisplay = lp.getText(enumVal);
		assertEquals(redDisplay, "Canadian french red enum value");
	}
	
	public void testCanadianFrenchWithVariant() {
		IComponent testComponent = componentSet.lookupComponent(COMPONENT_NAME);
		assertTrue(testComponent != null);
		
		Locale l = new Locale("fr", "CA", "win");
		Locale.setDefault(l);
				
		String friendlyName = testComponent.getFriendlyName();
		assertLocalized(friendlyName);
		assertEquals(friendlyName, "Canadian french (win) friendly name");
		
		IPropertySource ps = getPropertySource(testComponent);
		testPropertyDisplayName(ps, "baseProperty", "base localized property");
		testPropertyDisplayName(ps, "name", "Canadian french name property");
		
		IPropertySource compoundPS = getPropertySource(ps, "compound1");
		testPropertyDisplayName(compoundPS, "abc", "Canadian french abc property");
		
		IPropertyDescriptor enumProp = getPropertyDescriptor(ps, "enum1");
		Object enumVal = ps.getPropertyValue("enum1");
		assertEquals(enumVal, "blue");
		ILabelProvider lp = enumProp.getLabelProvider();
		String blueDisplay = lp.getText(enumVal);
		assertEquals(blueDisplay, "Blue-color");
		
		ps.setPropertyValue("enum1", "red");
		enumVal = ps.getPropertyValue("enum1");
		assertEquals(enumVal, "red");
		String redDisplay = lp.getText(enumVal);
		assertEquals(redDisplay, "Canadian french (win) red enum value");
	}
}
