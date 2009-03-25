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
import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Collections;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class ChildListenerAdapterTest extends TestCase {

	static ComponentProvider provider;

	private IComponentSet set;

	private IComponent codeComponent;
	private IComponent codeContainer;
	private IComponent scriptComponent;
	private IComponent scriptContainer;

	private IDesignerDataModel dataModel;
	
	private EObject root;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
        if (provider == null)
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/display");

		ComponentSetResult result = provider.queryComponents(null);
		set = result.getComponentSet();
		assertNotNull(set);
		
		dataModel = TestDataModelHelpers.createTemporaryModel();
		dataModel.setComponentSet(set);
        ScriptingManager.getInstance().registerClassLoaderFor(PluginTest.getDefault());

        codeComponent = set.lookupComponent("com.nokia.examples.codeComp");
        assertNotNull(codeComponent);

        codeContainer = set.lookupComponent("com.nokia.examples.codeLayoutContainer");
        assertNotNull(codeContainer);
        
        scriptComponent = set.lookupComponent("com.nokia.examples.scriptComp");
        assertNotNull(scriptComponent);

        scriptContainer = set.lookupComponent("com.nokia.examples.scriptLayoutContainer");
        assertNotNull(scriptContainer);

        root = dataModel.createRootContainerInstance(codeContainer);
		assertNotNull(root);
		setName(root, "root");
	}
	
	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testChildAdded() {
		EObject child = dataModel.createNewComponentInstance(codeComponent);
		assertNotNull(child);
		Command command = dataModel.createAddNewComponentInstanceCommand(root, child, 0);
		if (command.canExecute())
			command.execute();
		
		Object value = getProperty(child, "test");
		assertEquals("added", value);
		
		EObject scriptParent = dataModel.createNewComponentInstance(scriptContainer);
		assertNotNull(scriptParent);
		command = dataModel.createAddNewComponentInstanceCommand(root, scriptParent, 0);
		if (command.canExecute())
			command.execute();
		
		child = dataModel.createNewComponentInstance(scriptComponent);
		assertNotNull(child);
		command = dataModel.createAddNewComponentInstanceCommand(scriptParent, child, 0);
		if (command.canExecute())
			command.execute();

		value = getProperty(child, "test");
		assertEquals("added", value);	
	}
	
	public void testChildRemoved() {
		EObject scriptParent = dataModel.createNewComponentInstance(scriptContainer);
		assertNotNull(scriptParent);
		Command command = dataModel.createAddNewComponentInstanceCommand(root, scriptParent, 0);
		if (command.canExecute())
			command.execute();
		setName(scriptParent, "scriptContainer");
		
		EObject child = dataModel.createNewComponentInstance(scriptComponent);
		assertNotNull(child);
		command = dataModel.createAddNewComponentInstanceCommand(scriptParent, child, 0);
		if (command.canExecute())
			command.execute();
		setName(child, "scriptComponent");
		
		command = dataModel.createRemoveComponentInstancesCommand(Collections.singletonList(child));
		if (command.canExecute())
			command.execute();

		Object value = getProperty(scriptParent, "test");
		assertEquals("removed", value);
		
		command = dataModel.createRemoveComponentInstancesCommand(Collections.singletonList(scriptParent));
		if (command.canExecute())
			command.execute();
		
		value = getProperty(root, "test");
		assertEquals("removed", value);
		
	}
	
	private void setName(EObject obj, String name) {
		INode node = (INode) obj;
		IPropertyContainer properties = node.getProperties();
		properties.set("name", properties.createLiteral(name));
	}
	
	private Object getProperty(EObject obj, String propertyName) {
		IPropertySource ps = Utilities.getPropertySource(obj);
		return ps.getPropertyValue(propertyName);
	}
	
	public void testScriptChildrenReordered() {
		EObject scriptParent = dataModel.createNewComponentInstance(scriptContainer);
		assertNotNull(scriptParent);
		Command command = dataModel.createAddNewComponentInstanceCommand(root, scriptParent, 0);
		if (command.canExecute())
			command.execute();
		setName(scriptParent, "scriptContainer");
		
		EObject child1 = dataModel.createNewComponentInstance(scriptComponent);
		assertNotNull(child1);
		command = dataModel.createAddNewComponentInstanceCommand(scriptParent, child1, 0);
		if (command.canExecute())
			command.execute();
		setName(child1, "scriptComponent1");
		
		EObject child2 = dataModel.createNewComponentInstance(scriptComponent);
		assertNotNull(child2);
		command = dataModel.createAddNewComponentInstanceCommand(scriptParent, child2, 0);
		if (command.canExecute())
			command.execute();
		setName(child2, "scriptComponent2");
		
		command = dataModel.createMoveComponentInstanceCommand(child1, scriptParent, 0);
		if (command.canExecute())
			command.execute();
		
		Object value = getProperty(scriptParent, "test");
		assertEquals("reordered", value);
	}
	
	public void testCodeChildrenReordered() {
		if (true) return;
		EObject codeParent = dataModel.createNewComponentInstance(codeContainer);
		assertNotNull(codeParent);
		Command command = dataModel.createAddNewComponentInstanceCommand(root, codeParent, 0);
		if (command.canExecute())
			command.execute();
		setName(codeParent, "codeContainer");
		
		EObject child1 = dataModel.createNewComponentInstance(codeComponent);
		assertNotNull(child1);
		command = dataModel.createAddNewComponentInstanceCommand(codeParent, child1, 0);
		if (command.canExecute())
			command.execute();
		setName(child1, "codeComponent1");
		
		EObject child2 = dataModel.createNewComponentInstance(codeComponent);
		assertNotNull(child2);
		command = dataModel.createAddNewComponentInstanceCommand(codeParent, child2, 0);
		if (command.canExecute())
			command.execute();
		setName(child2, "codeComponent2");
		
		command = dataModel.createMoveComponentInstanceCommand(child1, codeParent, 0);
		if (command.canExecute())
			command.execute();
		
		Object value = getProperty(codeParent, "test");
		assertEquals("reordered", value);
	}
	
}
