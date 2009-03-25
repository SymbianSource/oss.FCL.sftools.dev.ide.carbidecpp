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
import com.nokia.sdt.component.event.IEventDescriptor;
import com.nokia.sdt.component.event.IEventDescriptorProvider;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Collection;

import junit.framework.TestCase;

public class EventsTest extends TestCase {

	static final String CONTAINER_COMPONENT = "com.nokia.examples.container";
	static final String SPECIAL_CONTAINER_COMPONENT = "com.nokia.examples.specialContainer";
	static final String CHILD_COMPONENT = "com.nokia.examples.baseComponent";
	static final String FILTERED_EVENTS_CHILD_COMPONENT = "com.nokia.examples.filteredEventsComponent";
	
	DesignerDataModel model;
	EObject root;
	EObject child;
	
	protected void setUp() throws Exception {
		
		model = (DesignerDataModel) TestDataModelHelpers.createTemporaryModel();
		TestDataModelHelpers.initDefaultComponentSet(model);
		IComponent component = model.getComponentSet().lookupComponent(CONTAINER_COMPONENT);
		root = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(root);
		IPropertySource ps = AdapterHelpers.getPropertySource(root);
		ps.setPropertyValue("name", "view1");
		ps.setPropertyValue("className", "CMyContainer");

		component = model.getComponentSet().lookupComponent(CHILD_COMPONENT);
		child = model.createNewComponentInstance(component);
		Command command = model.createAddNewComponentInstanceCommand(root, child, -1);
		assertTrue(command.canExecute());
		command.execute();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
		
	public void testAddDefaultBinding() {
		IComponentInstance ci = ModelUtils.getComponentInstance(child);
		IComponent component = ci.getComponent();
		IEventDescriptorProvider edp = (IEventDescriptorProvider) component.getAdapter(IEventDescriptorProvider.class);
		IEventDescriptor[] eventDescriptors = edp.getEventDescriptors();
		Command command = model.createAddEventBindingCommand(child, eventDescriptors[0], null);
		assertTrue(command.canExecute());
		command.execute();
		Collection result = command.getResult();
		Object[] objects = result.toArray();
		assertTrue(objects[0] instanceof IEventBinding);
		IEventBinding binding = (IEventBinding) objects[0];
		assertEquals(binding.getHandlerName(), "BaseComponent1_SomethingHappened");

		IEventBinding[] eventBindings = ci.getEventBindings();
		assertNotNull(eventBindings);
		assertTrue(eventBindings.length == 1);
		assertEquals(eventBindings[0].getEventDescriptor(), eventDescriptors[0]);
	}
	
	public void testAddUserSpecifiedBinding() {
		IComponentInstance ci = ModelUtils.getComponentInstance(child);
		IComponent component = ci.getComponent();
		IEventDescriptorProvider edp = (IEventDescriptorProvider) component.getAdapter(IEventDescriptorProvider.class);
		IEventDescriptor[] eventDescriptors = edp.getEventDescriptors();
		String userSpecifiedName = "MyEventHandler";
		Command command = model.createAddEventBindingCommand(child, eventDescriptors[0], userSpecifiedName);
		assertTrue(command.canExecute());
		command.execute();
		Collection result = command.getResult();
		Object[] objects = result.toArray();
		assertTrue(objects[0] instanceof IEventBinding);
		IEventBinding binding = (IEventBinding) objects[0];
		assertEquals(binding.getHandlerName(), userSpecifiedName);
	}
	
	public void testRemoveBinding() {
		IComponentInstance ci = ModelUtils.getComponentInstance(child);
		IComponent component = ci.getComponent();
		IEventDescriptorProvider edp = (IEventDescriptorProvider) component.getAdapter(IEventDescriptorProvider.class);
		IEventDescriptor[] eventDescriptors = edp.getEventDescriptors();
		Command command = model.createAddEventBindingCommand(child, eventDescriptors[0], null);
		assertTrue(command.canExecute());
		command.execute();
		Collection result = command.getResult();
		Object[] objects = result.toArray();
		assertTrue(objects[0] instanceof IEventBinding);
		IEventBinding binding = (IEventBinding) objects[0];
		assertEquals(binding.getHandlerName(), "BaseComponent1_SomethingHappened");

		IEventBinding[] eventBindings = ci.getEventBindings();
		assertNotNull(eventBindings);
		assertTrue(eventBindings.length == 1);

		command = model.createRemoveEventBindingCommand(binding);
		assertTrue(command.canExecute());
		command.execute();
		
		eventBindings = ci.getEventBindings();
		assertNull(eventBindings);
	}

	/**
	 * This case has no event info override so all the events are exposed
	 *
	 */
	public void testUnfilteredEvents() {
		// add to filter child to root, a container
		IComponent component = model.getComponentSet().lookupComponent(FILTERED_EVENTS_CHILD_COMPONENT);
		EObject filteredChild = model.createNewComponentInstance(component);
		Command command = model.createAddNewComponentInstanceCommand(root, filteredChild, -1);
		assertTrue(command.canExecute());
		command.execute();

		// everything visible on component
		IEventDescriptorProvider edp = (IEventDescriptorProvider) component.getAdapter(IEventDescriptorProvider.class);
		IEventDescriptor[] eventDescriptors = edp.getEventDescriptors();
		assertEquals(2, eventDescriptors.length);
		int defaultIndex = edp.getDefaultEventIndex();
		assertEquals(1, defaultIndex);
		
		// some may be filtered on child, but not in this case
		IComponentEventDescriptorProvider cedp = ModelUtils.getComponentEventDescriptorProvider(filteredChild);
		IEventDescriptor[] filteredEventDescriptors = cedp.getEventDescriptors();
		assertEquals(eventDescriptors.length, filteredEventDescriptors.length);
		
		int filteredDefaultIndex = cedp.getDefaultEventIndex();
		assertEquals(defaultIndex, filteredDefaultIndex);
	}

	/**
	 * This case has no event info override so all the events are exposed
	 *
	 */
	public void testFilteredEvents() {
		// add to a special container 
		IComponent specialContainerComponent = model.getComponentSet().lookupComponent(SPECIAL_CONTAINER_COMPONENT);
		EObject container = model.createNewComponentInstance(specialContainerComponent);
		Command command = model.createAddNewComponentInstanceCommand(root, container, -1);
		assertTrue(command.canExecute());
		command.execute();

		IComponent component = model.getComponentSet().lookupComponent(FILTERED_EVENTS_CHILD_COMPONENT);
		EObject filteredChild = model.createNewComponentInstance(component);
		command = model.createAddNewComponentInstanceCommand(container, filteredChild, -1);
		assertTrue(command.canExecute());
		command.execute();

		// filtered child is different here
		IEventDescriptorProvider edp = (IEventDescriptorProvider) component.getAdapter(IEventDescriptorProvider.class);
		IEventDescriptor[] eventDescriptors = edp.getEventDescriptors();
		assertEquals(2, eventDescriptors.length);
		int defaultIndex = edp.getDefaultEventIndex();
		assertEquals(1, defaultIndex);
		
		IComponentEventDescriptorProvider cedp = ModelUtils.getComponentEventDescriptorProvider(filteredChild);
		IEventDescriptor[] filteredEventDescriptors = cedp.getEventDescriptors();
		assertEquals(1, filteredEventDescriptors.length);
		
		int filteredDefaultIndex = cedp.getDefaultEventIndex();
		assertEquals(0, filteredDefaultIndex);
	}

}
