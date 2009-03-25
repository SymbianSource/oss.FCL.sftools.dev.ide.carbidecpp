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
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.io.File;

import junit.framework.TestCase;

	/**
	 * Tests for basic property support
	 *
	 */
public class PropertyDataModelTest extends TestCase {

	DesignerDataModel model;
	
	static final String PROJECT_NAME = "propertydm";
	static final String FILE_NAME = "model.nxd";
	static final String ROOT_COMPONENT_NAME = "com.nokia.examples.baseComponent";

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
		model.createRootContainerInstance(rootComponent);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		model.dispose();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}
	
	public void testIPropertySource() {
		EObject rootContainer = model.getRootContainers()[0];
		assertNotNull(rootContainer);
		IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(rootContainer, IPropertySource.class);
		assertNotNull(ps);
		
		IPropertyDescriptor pdList[] = ps.getPropertyDescriptors();
		assertNotNull(pdList);
		assertTrue(pdList.length > 1);
	}
	
	public void testMoveAcrossContainers() {
		// check the moving an instance across containers copies
		// property values, regression test for Bugzilla #86.
		IComponentSet cs = model.getComponentSet();
		IComponent component = cs.lookupComponent(ROOT_COMPONENT_NAME);
		INode rootContainer = (INode) model.getRootContainers()[0];
		TestDataModelHelpers.assignUniqueName(model, rootContainer);
		
		INode child = (INode) model.createNewComponentInstance(component);
		Command command = model.createAddNewComponentInstanceCommand(rootContainer, child, IDesignerDataModel.AT_END);
		assertTrue(command.canExecute());
		command.execute();
		TestDataModelHelpers.assignUniqueName(model, child);

		INode childContainer = (INode) model.createNewComponentInstance(component);
		command = model.createAddNewComponentInstanceCommand(rootContainer, childContainer, IDesignerDataModel.AT_END);
		assertTrue(command.canExecute());
		command.execute();
		TestDataModelHelpers.assignUniqueName(model, childContainer);
		
		assertTrue(rootContainer.getChildren().size() == 2);

		StringValue sv = child.getProperties().createLocalized("test");
		child.getProperties().set("testproperty", sv);
		
		command = model.createMoveComponentInstanceCommand(child, childContainer, IDesignerDataModel.AT_END);
		assertTrue(command.canExecute());
		command.execute();
		
		assertTrue(rootContainer.getChildren().size() == 1);
		assertTrue(childContainer.getChildren().size() == 1);
		child = (INode) childContainer.getChildren().get(0);
		Object opv = child.getProperties().getProperties().get("testproperty");
		assertNotNull(opv);
		assertTrue(opv instanceof IPropertyValue);
		IPropertyValue pv = (IPropertyValue) opv;
		sv = pv.getStringValue();
		assertTrue(sv.getType() == StringValue.LOCALIZED);
		
		String localizedString = model.getDesignerData().getStringBundle().getLocalizedStringDefault(sv.getValue());
		assertEquals(localizedString, "test");
	}

}
