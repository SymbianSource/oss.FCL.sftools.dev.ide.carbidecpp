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

import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.testsupport.ComponentHelpers;

import org.osgi.framework.Bundle;

import junit.framework.TestCase;

public class AttributeAdapterTest extends TestCase {
	
	static ComponentProvider provider;
	static ComponentSet componentSet;
	Component component;

	protected void setUp() throws Exception {
		super.setUp();
        
		if (provider == null) {
			provider = new ComponentProvider();
			provider.inhibitPluginScan();
			componentSet = ComponentHelpers.queryAllComponents(provider);
		}
		Bundle bundle = PluginTest.getDefault().getBundle();
		
		MockLocalizedStrings strings = new MockLocalizedStrings();
		
		ComponentType emfComponent;
		emfComponent = ComponentFactory.eINSTANCE.createComponentType();
		Component base = new Component(provider, null, bundle, emfComponent, strings);
		ComponentType ct = base.getEMFComponent();
		ct.setQualifiedName("com.nokia.component.base");
		AttributesType attributes = ComponentFactory.eINSTANCE.createAttributesType();
		ct.setAttributes(attributes);
		addAttribute(attributes, "inherited", "true");
		componentSet.addComponent(base);
		
		emfComponent = ComponentFactory.eINSTANCE.createComponentType();
		component = new Component(provider, null, bundle, emfComponent, strings);
		ct = component.getEMFComponent();
		ct.setQualifiedName("com.nokia.component.derived");
		ct.setBaseComponent("com.nokia.component.base");
		
		attributes = ComponentFactory.eINSTANCE.createAttributesType();
		ct.setAttributes(attributes);
		addAttribute(attributes, "empty", "");
		addAttribute(attributes, "validInt", "100");
		addAttribute(attributes, "invalidInt", "hi");
		addAttribute(attributes, "validTrueBool", "true");
		addAttribute(attributes, "validFalseBool", "false");
		addAttribute(attributes, "invalidBool", "yo");
		addAttribute(attributes, "normal", "yes");
		componentSet.addComponent(component);
	}
	
	void addAttribute(AttributesType attributes, String key, String value) {
		AttributeType at = ComponentFactory.eINSTANCE.createAttributeType();
		at.setKey(key);
		at.setValue(value);
		attributes.getAttribute().add(at);		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAttributeAdapter() {
		IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
		assertNotNull(attr);
	}

	public void testGetAttribute() {
		IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
		assertEquals(attr.getAttribute("empty"), "");
		assertEquals(attr.getAttribute("validInt"), "100");
		assertEquals(attr.getAttribute("invalidInt"), "hi");
		assertEquals(attr.getAttribute("validTrueBool"), "true");
		assertEquals(attr.getAttribute("invalidBool"), "yo");
		assertEquals(attr.getAttribute("normal"), "yes");
		assertNull(attr.getAttribute("missing"));
		assertEquals(attr.getAttribute("inherited"), "true");
	}

	public void testIsAttributeDefined() {
		IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
		assertTrue(attr.isAttributeDefined("empty"));
		assertTrue(attr.isAttributeDefined("validInt"));
		assertTrue(attr.isAttributeDefined("invalidInt"));
		assertTrue(attr.isAttributeDefined("validTrueBool"));
		assertTrue(attr.isAttributeDefined("invalidBool"));
		assertTrue(attr.isAttributeDefined("normal"));
		assertTrue(attr.isAttributeDefined("missing") == false);
		assertTrue(attr.isAttributeDefined("inherited"));
	}

	public void testGetIntegerAttribute() {
		IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
		assertTrue(attr.getIntegerAttribute("validInt", 0) == 100);
		assertTrue(attr.getIntegerAttribute("invalidInt", 11) == 11);
		assertTrue(attr.getIntegerAttribute("missing", 11) == 11);
	}

	public void testGetBooleanAttribute() {
		IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
		assertTrue(attr.getBooleanAttribute("validTrueBool", false) == true);
		assertTrue(attr.getBooleanAttribute("validFalseBool", true) == false);
		assertTrue(attr.getBooleanAttribute("invalidBool", false) == false);
		assertTrue(attr.getBooleanAttribute("missing", true) == true);
		assertTrue(attr.getBooleanAttribute("inherited", false) == true);
	}

	public void testGetComponent() {
		IAttributes attr = (IAttributes) component.getAdapter(IAttributes.class);
		assertEquals(component, attr.getComponent());
	}

}
