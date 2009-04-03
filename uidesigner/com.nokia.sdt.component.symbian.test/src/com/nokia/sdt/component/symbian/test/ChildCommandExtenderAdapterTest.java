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


package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class ChildCommandExtenderAdapterTest extends TestCase {

	static ComponentProvider provider;

	private IComponentSet set;

	private IComponent component;

	private IDesignerDataModel dataModel;
	
	private EObject root;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
        if (provider == null)
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/childExtenderTest");

		ComponentSetResult result = provider.queryComponents(null);
		set = result.getComponentSet();
		assertNotNull(set);
		
		dataModel = TestDataModelHelpers.loadDataModel("data/childExtenderTest/childExtenderTest.uidesign");
		dataModel.setComponentSet(set);

        component = set.lookupComponent("com.nokia.examples.extenderContainer");
        assertNotNull(component);

		root = dataModel.findByNameProperty("root");
		assertNotNull(root);

	}
	
	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAddNewComponentInstanceCommand() {
		Object value = getProperty(root, "test");
		assertEquals("uninitialized", value);

		EObject child = dataModel.createNewComponentInstance(component);
		assertNotNull(child);
		Command command = dataModel.createAddNewComponentInstanceCommand(root, child, 0);
		if (command.canExecute())
			command.execute();
		
		value = getProperty(root, "test");
		assertEquals("added", value);
		
		command.undo();
		value = getProperty(root, "test");
		assertEquals("uninitialized", value);
		
		command.redo();
		value = getProperty(root, "test");
		assertEquals("added", value);
	}
	
	public void testRemoveComponentInstancesCommand() {
		doRemoveVariantTest(false);
	}

	public void testCutComponentInstancesCommand() {
		doRemoveVariantTest(true);
	}
	
	private void doRemoveVariantTest(boolean doCutCommand) {
		Object value = getProperty(root, "test");
		assertEquals("uninitialized", value);
		
		EObject child = dataModel.findByNameProperty("child1");
		
		List<EObject> objList = Collections.singletonList(child);
		Command command = doCutCommand ?
			dataModel.createCutComponentInstancesCommand(objList) :
			dataModel.createRemoveComponentInstancesCommand(objList);
		if (command.canExecute())
			command.execute();
		
		value = getProperty(root, "test");
		assertEquals("removed", value);
		
		command.undo();
		value = getProperty(root, "test");
		assertEquals("uninitialized", value);
		
		command.redo();
		value = getProperty(root, "test");
		assertEquals("removed", value);
	}
	
	public void testMoveComponentInstancesCommand() {
		Object value = getProperty(root, "test");
		assertEquals("uninitialized", value);
		
		EObject child = dataModel.findByNameProperty("child2");
		
		Command command = 
			dataModel.createMoveComponentInstanceCommand(child, root, 0);
		if (command.canExecute())
			command.execute();
		
		value = getProperty(root, "test");
		assertEquals("moved", value);
		
		command.undo();
		value = getProperty(root, "test");
		assertEquals("uninitialized", value);
		
		command.redo();
		value = getProperty(root, "test");
		assertEquals("moved", value);
	}
	
	private Object getProperty(EObject obj, String propertyName) {
		IPropertySource ps = Utilities.getPropertySource(obj);
		return ps.getPropertyValue(propertyName);
	}
	
}
