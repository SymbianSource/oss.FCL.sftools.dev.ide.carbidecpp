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
package com.nokia.sdt.emf.dm.tests;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

public class NewRootDataModelTest extends TestCase {

	DesignerDataModel model;
	
	static final String PROJECT_NAME = "newrootdm";
	static final String FILE_NAME = "model.nxd";
	static final String ROOT_COMPONENT_NAME = "com.nokia.examples.baseComponent";
	static final String CHILD_COMPONENT_NAME = "com.nokia.examples.example1";

	protected void setUp() throws Exception {
		super.setUp();
		IProject project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		File dmFile = new File(projectDir, FILE_NAME);
		
		// make a file local to this plugin, in the testdata directory
		model = (DesignerDataModel) TestDataModelHelpers.createTestModel(dmFile, null);
		TestDataModelHelpers.initDefaultComponentSet(model);
		
		// establish a root container
		IComponentSet cs = model.getComponentSet();
		IComponent rootComponent = cs.lookupComponent(ROOT_COMPONENT_NAME);
		EObject root = model.createRootContainerInstance(rootComponent);
		TestDataModelHelpers.assignUniqueName(model, root);
		
		model.saveModel(new NullProgressMonitor());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		model.dispose();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}
	
	EObject getRootContainer() {
		return model.getRootContainers()[0];
	}

	public void testGetRootContainer() {
		EObject rootContainer = getRootContainer();
		assertNotNull(rootContainer);
		IComponentInstance ci = (IComponentInstance) EcoreUtil.getRegisteredAdapter(rootContainer, IComponentInstance.class);
		assertNotNull(ci);
		assertEquals(ci.getComponentId(), ROOT_COMPONENT_NAME);
	}
	
	Command makeAppendChildCommand(EObject parent, String componentName) {
		IComponentSet cs = model.getComponentSet();
		IComponent component = cs.lookupComponent(componentName);
		
		EObject child = model.createNewComponentInstance(component);
		Command cmd = model.createAddNewComponentInstanceCommand(parent, child, IDesignerDataModel.AT_END);
		assertNotNull(cmd);
		assertTrue(cmd.canExecute());
		return cmd;	
	}
	
	Object getSingleAffectedObject(Command cmd) {
		Object result = null;
		Collection cmdResult = cmd.getResult();
		assertNotNull(cmdResult);
		assertTrue(cmdResult.size() == 1);
		result = cmdResult.toArray()[0];
		return result;
	}
	
	public void testAddChildWithCommand() {
		
		EObject rootContainer = getRootContainer();
		IComponentInstance rootCI = getCI(rootContainer);
		Command cmd = makeAppendChildCommand(rootContainer, CHILD_COMPONENT_NAME);
		cmd.execute();
		assertTrue(cmd.canUndo());
		
		// result of the command should be the new object
		Object item = getSingleAffectedObject(cmd);
		assertNotNull(item);
		assertTrue(item instanceof EObject);
		IComponentInstance ci = (IComponentInstance) EcoreUtil.getRegisteredAdapter((EObject)item, IComponentInstance.class);
		assertNotNull(ci);
		assertEquals(ci.getComponentId(), CHILD_COMPONENT_NAME);
		
		// object should be on the child list
		EObject[] children = rootCI.getChildren();
		assertNotNull(children);
		assertTrue(children.length == 1);
		assertEquals(item, children[0]);
	}
	
	IComponentInstance getCI(EObject object) {
		IComponentInstance result = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
				object, IComponentInstance.class);
		return result;
	}
	
	public void testRemoveChildCommand() {
		Command cmd = makeAppendChildCommand(getRootContainer(), CHILD_COMPONENT_NAME);
		cmd.execute();
	
		EObject item = (EObject) getSingleAffectedObject(cmd);
		TestDataModelHelpers.assignUniqueName(model, item);
		ArrayList<EObject> list = new ArrayList<EObject>();
		list.add(item);
		cmd = model.createRemoveComponentInstancesCommand(list);
		cmd.execute();
		assertTrue(cmd.canUndo());
		
		IComponentInstance rootCI = getCI(getRootContainer());
		assertNotNull(rootCI);
	
		EObject[] children = rootCI.getChildren();
		assertEquals(0, children.length);
	}
	
	public void testMoveChildWithinParentCommand() {
		EObject rootContainer = getRootContainer();
		
		// first add two children to the root
		Command cmd = makeAppendChildCommand(rootContainer, CHILD_COMPONENT_NAME);
		cmd.execute();
		cmd = makeAppendChildCommand(rootContainer, ROOT_COMPONENT_NAME);
		cmd.execute();

		IComponentInstance rootCI = getCI(getRootContainer());
		assertNotNull(rootCI);
		EObject children[] = rootCI.getChildren();
		assertTrue(children.length == 2);
		IComponentInstance child1 = getCI(children[0]);
		IComponentInstance child2 = getCI(children[1]);
		assertEquals(child1.getComponentId(), CHILD_COMPONENT_NAME);
		assertEquals(child2.getComponentId(), ROOT_COMPONENT_NAME);
		
		// now move the 2nd to the front
		cmd = model.createMoveComponentInstanceCommand(children[1], rootContainer, IDesignerDataModel.IN_FRONT);
		assertTrue(cmd.canExecute());
		cmd.execute();
		assertTrue(cmd.canUndo());
		children = rootCI.getChildren();
		assertTrue(children.length == 2);
		child1 = getCI(children[0]);
		child2 = getCI(children[1]);
		assertEquals(child1.getComponentId(), ROOT_COMPONENT_NAME);
		assertEquals(child2.getComponentId(), CHILD_COMPONENT_NAME);
		
		// now undo and check that the order was restored
		cmd.undo();
		children = rootCI.getChildren();
		assertTrue(children.length == 2);
		assertEquals(getCI(children[0]).getComponentId(), CHILD_COMPONENT_NAME);
		assertEquals(getCI(children[1]).getComponentId(), ROOT_COMPONENT_NAME);
	}

	public void testMoveChildDifferentParentCommand() {
		// make two subcontainers
		EObject rootContainer = getRootContainer();
		Command cmd = makeAppendChildCommand(rootContainer, CHILD_COMPONENT_NAME);
		cmd.execute();
		EObject container1 = (EObject) getSingleAffectedObject(cmd);
		cmd = makeAppendChildCommand(rootContainer, CHILD_COMPONENT_NAME);
		cmd.execute();	
		EObject container2 = (EObject) getSingleAffectedObject(cmd);	
		
		// first add child to the first container
		cmd = makeAppendChildCommand(container1, CHILD_COMPONENT_NAME);
		cmd.execute();

		IComponentInstance container1CI = getCI(container1);
		assertNotNull(container1CI);
		EObject children[] = container1CI.getChildren();
		assertTrue(children.length == 1);
		assertEquals(getCI(children[0]).getComponentId(), CHILD_COMPONENT_NAME);
		
		// now move to the second container
		cmd = model.createMoveComponentInstanceCommand(children[0], container2, 
						IDesignerDataModel.IN_FRONT);
		assertTrue(cmd.canExecute());
		cmd.execute();
		assertTrue(cmd.canUndo());
		
		// verify it's gone from the first container
		children = container1CI.getChildren();
		assertEquals(0, children.length);
		
		// verify it's in the second container
		IComponentInstance container2CI = getCI(container2);
		children = container2CI.getChildren();
		assertTrue(children.length == 1);
		assertEquals(getCI(children[0]).getComponentId(), CHILD_COMPONENT_NAME);
		
		// now undo and check that the previous state was restored
		cmd.undo();
		children = container1CI.getChildren();
		assertTrue(children.length == 1);
		assertEquals(getCI(children[0]).getComponentId(), CHILD_COMPONENT_NAME);
	
		children = container2CI.getChildren();
		assertEquals(0, children.length);
	}
	
	public void testGetDataModelFromInstance() {
		IComponentInstance rootCI = getCI(getRootContainer());
		assertEquals(model, rootCI.getDesignerDataModel());
		
		Command cmd = makeAppendChildCommand(getRootContainer(), CHILD_COMPONENT_NAME);
		cmd.execute();

		EObject object = (EObject) getSingleAffectedObject(cmd);
		IComponentInstance childCI = getCI(object);
		assertEquals(model, childCI.getDesignerDataModel());
	}
}
