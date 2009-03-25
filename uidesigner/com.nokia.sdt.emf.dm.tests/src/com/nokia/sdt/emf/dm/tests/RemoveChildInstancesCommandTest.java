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


package com.nokia.sdt.emf.dm.tests;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class RemoveChildInstancesCommandTest extends TestCase {

	static final String PROJECT_NAME = "propertydm";
	static final String FILE_NAME = "model.nxd";
	static final String COMPONENT_NAME = "com.nokia.examples.baseComponent";
	DesignerDataModel model;
	IDesignerData data;
	IPropertyContainer nodeProperties;
	INode rootNode, obj1, obj11, obj111, obj12, obj2;
	
	static final String ROOTNAME = "root";
	static final String NAME1 = "obj1";
	static final String NAME11 = "obj11";
	static final String NAME111 = "obj111";
	static final String NAME12 = "obj12";
	static final String NAME2 = "obj2";
	static final String REF_ID = "refId";
	static final String MACRO_ID = "macroId";
	static final String MACRO_STRING = "macro1";
	static final String LOC_ID = "locId";
	static final String LOC_STRING = "localized1";

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
		IComponent rootComponent = cs.lookupComponent(COMPONENT_NAME);
		rootNode = (INode) model.createRootContainerInstance(rootComponent);
		nodeProperties = rootNode.getProperties();
		nodeProperties.set("name", nodeProperties.createLiteral(ROOTNAME));
		
		obj1 = createNode(rootNode, NAME1);
		obj11 = createNode(obj1, NAME11);
		obj111 = createNode(obj11, NAME111);
		obj12 = createNode(obj1, NAME12);
		obj2 = createNode(rootNode, NAME2);

		nodeProperties.getPropertyValueSource().setReferencePropertyValue(REF_ID, obj111.getName());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		model.dispose();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}
	
	private INode createNode(INode owner, String name) {
		INode node = DmFactory.eINSTANCE.createINode();
		node.setComponentId(COMPONENT_NAME);
		node.getProperties().set("name", nodeProperties.createLiteral(name));
		owner.getChildren().add(node);
		IEventBinding binding = DmFactory.eINSTANCE.createIEventBinding();
		binding.setEventID(name + ".event");
		binding.setEventHandlerDisplayText(binding.getEventID());
		binding.setEventHandlerInfo(binding.getEventID());
		node.getEventBindings().add(binding);
		
		return node;
	}
	
	class ChildCountVisitor implements INodeVisitor {
		public int count = 0;
		private INode root;
		
		public ChildCountVisitor(INode root) {
			this.root = root;
		}
		
		public Object visit(INode node) {
			if (!node.equals(root)) {
				count++;
			}
			return null;
		}
	}
	
	private int getNumChildren(INode root) {
		ChildCountVisitor childCountVisitor = new ChildCountVisitor(root);
		root.visitPreorder(childCountVisitor);
		return childCountVisitor.count;
	}
	
	private boolean testNames() {
		return 
			obj1.getName().equals(NAME1) &&
			obj11.getName().equals(NAME11) &&
			obj111.getName().equals(NAME111) &&
			obj12.getName().equals(NAME12) &&
			obj2.getName().equals(NAME2);
	}
	
	private boolean testReferenceProperty() {
		return obj111.getName().equals(
				((StringValue) rootNode.getProperties().get(REF_ID)).getValue());
	}
	
	String getKey(IPropertyContainer pc, String propertyID) {
		IPropertyValue pv = (IPropertyValue) pc.getProperties().get(propertyID);
		assertNotNull(pv);
		assertTrue(pv.hasStringValue());
		assertTrue(pv.getStringValue().isKey());
		return pv.getStringValue().getValue();
	}
	
	boolean testLocString(INode node, String key, String expected) {
		String string = node.getDesignerData().getStringBundle().getLocalizedStringDefault(key);
		return expected.equals(string);
	}
	
	boolean testMacroString(INode node, String key, String expected) {
		String string = (String) node.getDesignerData().getMacroTable().getStringMacros().get(key);
		return expected.equals(string);
	}
	
	private void testEventBinding(INode node) {
		assertFalse(node.getEventBindings().isEmpty());
		
		Object o = node.getEventBindings().iterator().next();
		IEventBinding binding = (IEventBinding) o;
		assertEquals(node.getName() + ".event", binding.getEventID());
	}
	
	private void testEventBindingsRecursive(INode root) {
		testEventBinding(root);
		
		for (Object object : root.getChildren()) {
			testEventBindingsRecursive((INode) object);
		}
	}
	
	public void testRemoveChildInstancesCommand() {
		assertEquals(5, getNumChildren(rootNode));

		List objectsToRemove = new ArrayList();
		objectsToRemove.add(obj11);
		objectsToRemove.add(obj12);
		objectsToRemove.add(obj2);

		IPropertyContainer pc = obj111.getProperties();
		pc.set(LOC_ID, pc.createLocalized(LOC_STRING));
		String locKey = getKey(pc, LOC_ID);
		pc.set(MACRO_ID, pc.createMacro(MACRO_STRING));
		String macroKey = getKey(pc, MACRO_ID);
		
		assertTrue(testLocString(rootNode, locKey, LOC_STRING));
		assertTrue(testMacroString(rootNode, macroKey, MACRO_STRING));
		
		Command command = model.createRemoveComponentInstancesCommand(objectsToRemove);
		assertNotNull(command);
		assertTrue(command.canExecute());
		command.execute();
		assertEquals(1, getNumChildren(rootNode));
		
		assertTrue(!testLocString(rootNode, locKey, LOC_STRING));
		assertTrue(!testMacroString(rootNode, macroKey, MACRO_STRING));
		
		
		assertTrue(command.canUndo());
		command.undo();
		assertEquals(5, getNumChildren(rootNode));
		assertTrue(testNames());
		assertTrue(testReferenceProperty());
		
		command.redo();
		assertEquals(1, getNumChildren(rootNode));
		
		command.undo();
		assertEquals(5, getNumChildren(rootNode));
		assertTrue(testNames());
		assertTrue(testReferenceProperty());
		
		assertTrue(testLocString(rootNode, locKey, LOC_STRING));
		assertTrue(testMacroString(rootNode, macroKey, MACRO_STRING));
	}
	
	public void testNestedUndoRedoCommand() {
		assertEquals(5, getNumChildren(rootNode));

		List objectsToRemove = new ArrayList();
		objectsToRemove.add(obj111);
		Command command1 = model.createRemoveComponentInstancesCommand(objectsToRemove);
		assertNotNull(command1);
		assertTrue(command1.canExecute());
		command1.execute();

		objectsToRemove.clear();
		objectsToRemove.add(obj11);
		Command command2 = model.createRemoveComponentInstancesCommand(objectsToRemove);
		assertNotNull(command2);
		assertTrue(command2.canExecute());
		command2.execute();
		
		objectsToRemove.clear();
		objectsToRemove.add(obj1);
		Command command3 = model.createRemoveComponentInstancesCommand(objectsToRemove);
		assertNotNull(command3);
		assertTrue(command3.canExecute());
		command3.execute();
		
		command3.undo();
		command2.undo();
		command1.undo();
		
		assertEquals(5, getNumChildren(rootNode));
		
		for (Object object : rootNode.getChildren())
			testEventBindingsRecursive((INode) object);
	}

}
