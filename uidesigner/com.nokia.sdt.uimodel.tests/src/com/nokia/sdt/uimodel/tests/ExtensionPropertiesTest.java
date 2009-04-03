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
package com.nokia.sdt.uimodel.tests;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.ArrayList;

import junit.framework.TestCase;

public class ExtensionPropertiesTest extends TestCase {

	static final String CONTAINER_COMPONENT = "com.nokia.examples.extContainer";
	static final String TARGET_COMPONENT = "com.nokia.examples.baseComponent";
	static final String EXTENDING_CHILD_COMPONENT = "com.nokia.examples.extensionChild";
	static final String EXTENDING_CHILD_MULTIPLE_COMPONENT = "com.nokia.examples.extensionChildMultiple";
	static final String EXTENDED_DERIVED_COMPONENT = "com.nokia.examples.derivedExtensions";
	static final String CONDITIONAL_EXTENDED_COMPONENT = "com.nokia.examples.conditionalExtensions";
	
	DesignerDataModel model;
	EObject root;
	EObject targetChild;
	EObject derivedChild;
	EObject conditionalChild;
	
	protected void setUp() throws Exception {
		model = (DesignerDataModel) TestDataModelHelpers.createTemporaryModel();
		TestDataModelHelpers.initDefaultComponentSet(model);
		IComponent component = model.getComponentSet().lookupComponent(CONTAINER_COMPONENT);
		root = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(root);
		IPropertySource ps = AdapterHelpers.getPropertySource(root);
		ps.setPropertyValue("name", "view1");

		component = model.getComponentSet().lookupComponent(TARGET_COMPONENT);
		targetChild = model.createNewComponentInstance(component);
		Command command = model.createAddNewComponentInstanceCommand(root, targetChild, -1);
		assertTrue(command.canExecute());
		command.execute();

		component = model.getComponentSet().lookupComponent(EXTENDED_DERIVED_COMPONENT);
		derivedChild = model.createNewComponentInstance(component);
		command = model.createAddNewComponentInstanceCommand(root, derivedChild, -1);
		assertTrue(command.canExecute());
		command.execute();
		
		component = model.getComponentSet().lookupComponent(CONDITIONAL_EXTENDED_COMPONENT);
		conditionalChild = model.createNewComponentInstance(component);
		command = model.createAddNewComponentInstanceCommand(root, conditionalChild, -1);
		assertTrue(command.canExecute());
		command.execute();
	}
	
	public void testContainerAddedOnly() {
		IPropertySource ps = ModelUtils.getPropertySource(targetChild);
		assertNotNull(ps);
		String value = (String) ps.getPropertyValue("containerAdded");
		assertNotNull(value);
		assertEquals(value, "containerAddedValue");
		
		// check that this extension property, which is a localized string, got populated into
		// localized string table, not treated as an unlocalized literal
		assertTrue(ps instanceof IPropertyInformation);
		IPropertyInformation pi = (IPropertyInformation) ps;
		String propertyValueSymbol = pi.getPropertyValueSymbol("containerAdded");
		assertTrue(propertyValueSymbol != null);
	}
	
	private EObject addExtensionChild() {
		IComponent component = model.getComponentSet().lookupComponent(EXTENDING_CHILD_COMPONENT);
		EObject child = model.createNewComponentInstance(component);
		Command command = model.createAddNewComponentInstanceCommand(root, child, -1);
		assertTrue(command.canExecute());
		command.execute();
		return child;
	}
	private EObject addExtensionChildMultiple() {
		IComponent component = model.getComponentSet().lookupComponent(EXTENDING_CHILD_MULTIPLE_COMPONENT);
		EObject child = model.createNewComponentInstance(component);
		Command command = model.createAddNewComponentInstanceCommand(root, child, -1);
		assertTrue(command.canExecute());
		command.execute();
		return child;
	}
	
	public void testContainerWithChild() {
		IPropertySource ps = ModelUtils.getPropertySource(targetChild);
		assertNotNull(ps);

		Object value =  ps.getPropertyValue("childAdded");
		assertNull(value);
		
		EObject extendingChild = addExtensionChild();
		value = ps.getPropertyValue("containerAdded");
		assertNotNull(value);
		assertEquals(value, "containerAddedValue");
		value =  ps.getPropertyValue("childAdded");
		assertNotNull(value);
		assertEquals(value.toString(), "99");
		
		// test that property goes away after the child is removed
		ArrayList list = new ArrayList();
		list.add(extendingChild);
		Command cmd = model.createRemoveComponentInstancesCommand(list);
		assertTrue(cmd.canExecute());
		cmd.execute();
		value = ps.getPropertyValue("containerAdded");
		assertNotNull(value);
		assertEquals(value, "containerAddedValue");
		value =  ps.getPropertyValue("childAdded");
		assertNull(value);		
	}

	public void testContainerWithChildMultiple() {
		final IStatus[] statuses = { null };
		Logging.addListener(new ILogListener() {

			public void logging(IStatus status, String plugin) {
				statuses[0] = status;
			}
			
		});
		IPropertySource ps = ModelUtils.getPropertySource(targetChild);
		assertNotNull(ps);

		Object value =  ps.getPropertyValue("childAdded");
		assertNull(value);
		value = ps.getPropertyValue("childAdded2");
		assertNull(value);
		
		int numDescriptors = ps.getPropertyDescriptors().length;
		
		EObject extendingChild = addExtensionChildMultiple();
		
		assertEquals(numDescriptors + 2, ps.getPropertyDescriptors().length);

		// do not show errors
		assertNull(statuses[0]);

		value = ps.getPropertyValue("containerAdded");
		assertNotNull(value);
		assertEquals(value, "containerAddedValue");
		value =  ps.getPropertyValue("childAdded");
		assertNotNull(value);
		assertEquals(value.toString(), "99");
		value =  ps.getPropertyValue("childAdded2");
		assertNotNull(value);
		assertEquals(value.toString(), "true");
		
		// test that property goes away after the child is removed
		ArrayList list = new ArrayList();
		list.add(extendingChild);
		Command cmd = model.createRemoveComponentInstancesCommand(list);
		assertTrue(cmd.canExecute());
		cmd.execute();
		value = ps.getPropertyValue("containerAdded");
		assertNotNull(value);
		assertEquals(value, "containerAddedValue");
		value =  ps.getPropertyValue("childAdded");
		assertNull(value);		
		value =  ps.getPropertyValue("childAdded2");
		assertNull(value);		
	}

	public void testDerivedExtensions() {
		IPropertySource ps = ModelUtils.getPropertySource(derivedChild);
		assertNotNull(ps);

		Object value =  ps.getPropertyValue("baseExtensionProperty");
		assertEquals("valueFromBase", value);		
	}
	
	public void testConditionalExtensions() {
		// The com.nokia.examples.conditionalExtensions has a property listener
		// script that listens for a change on the "controlConditionalExtensions" boolean property and
		// adds extension property when it's true
		IPropertySource parentPS = ModelUtils.getPropertySource(root);
		assertNotNull(parentPS);
		IPropertySource childPS = ModelUtils.getPropertySource(conditionalChild);
		assertNotNull(childPS);
		
		Object value = parentPS.getPropertyValue("controlConditionalExtensions");
		assertEquals(Boolean.FALSE, value);
		Object conditionalProperty = childPS.getPropertyValue("existsIfControlTrue");
		assertNull(conditionalProperty);
		
		parentPS.setPropertyValue("controlConditionalExtensions", Boolean.TRUE);
		conditionalProperty = childPS.getPropertyValue("existsIfControlTrue");
		assertEquals("control is true", conditionalProperty);
		
		parentPS.resetPropertyValue("controlConditionalExtensions");
		conditionalProperty = childPS.getPropertyValue("existsIfControlTrue");
		assertNull(conditionalProperty);
	}

	/**
	 * Test that when dynamically extending a property source with localized
	 * strings, we initialize them with keys rather than literals 
	 */
	public void testConditionalExtensionsLocalizedStrings() {
		IPropertySource parentPS = ModelUtils.getPropertySource(root);
		assertNotNull(parentPS);
		IPropertySource childPS = ModelUtils.getPropertySource(conditionalChild);
		assertNotNull(childPS);
		
		// check that this extension property, which is a localized string, got populated into
		// localized string table, not treated as an unlocalized literal
		childPS.getPropertyValue("containerAdded"); // need to read before established
		assertTrue(childPS instanceof IPropertyInformation);
		IPropertyInformation pi = (IPropertyInformation) childPS;
		String propertyValueSymbol = pi.getPropertyValueSymbol("containerAdded");
		assertTrue(propertyValueSymbol != null);
		
		// now check that the new property isn't present
		assertNull(childPS.getPropertyValue("containerAdded2"));
		
		// toggle control flag
		parentPS.setPropertyValue("controlConditionalExtensions", Boolean.TRUE);
		
		// now check that the new property is present
		childPS = ModelUtils.getPropertySource(conditionalChild);
		assertNotNull(childPS);
		
		assertEquals("containerAddedValue2", childPS.getPropertyValue("containerAdded2"));
		
		// and that there is a symbol assigned to this string 
		pi = (IPropertyInformation) childPS;
		propertyValueSymbol = pi.getPropertyValueSymbol("containerAdded2");
		assertTrue(propertyValueSymbol != null);
		
		
	}
}
