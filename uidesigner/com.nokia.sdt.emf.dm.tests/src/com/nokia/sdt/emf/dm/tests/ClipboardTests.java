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
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CopyToClipboardCommand;

import java.io.File;
import java.util.*;

import junit.framework.TestCase;

public class ClipboardTests extends TestCase {

	DesignerDataModel model;
	EObject child;
	
	static final String PROJECT_NAME = "ClipboardTest";
	static final String FILE_NAME = "model.nxd";
	static final String ROOT_COMPONENT_NAME = "com.nokia.examples.baseComponent";
	static final String CHILD_COMPONENT_NAME = "com.nokia.examples.example1";

	protected void setUp() throws Exception {
		super.setUp();
		IProject project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		File dmFile = new File(projectDir, FILE_NAME);
		
		model = (DesignerDataModel) TestDataModelHelpers.createTestModel(dmFile, null);
		TestDataModelHelpers.initDefaultComponentSet(model);
		
		// establish a root container with a single child
		IComponentSet cs = model.getComponentSet();
		IComponent rootComponent = cs.lookupComponent(ROOT_COMPONENT_NAME);
		model.createRootContainerInstance(rootComponent);

		EObject rootContainer = model.getRootContainers()[0];
		TestDataModelHelpers.assignUniqueName(model, rootContainer);
		Command cmd = makeAppendChildCommand(rootContainer, CHILD_COMPONENT_NAME);
		cmd.execute();
		child = (EObject) getSingleAffectedObject(cmd);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		model.dispose();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}
	
	IComponentInstance getCI(EObject object) {
		IComponentInstance result = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
				object, IComponentInstance.class);
		return result;
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
	
	public void testClipboardAdapters() {
		// copy child to clipboard
		ArrayList list = new ArrayList();
		list.add(child);
		
		Command cmd = model.getEditingDomain().createCommand(CopyToClipboardCommand.class, 
				new CommandParameter(null, null, list));
		assertTrue(cmd.canExecute());
		cmd.execute();
		
		Collection clipboard = model.getEditingDomain().getClipboard();
		assertNotNull(clipboard);
		assertTrue(clipboard.size() == 1);
		EObject obj = null;
		for (Iterator iter = clipboard.iterator(); iter.hasNext();) {
			obj = (EObject) iter.next();
		}
		assertNotNull(obj);
		
		IComponentInstance ci = getCI(obj);
		assertNotNull(ci);
		
		String componentID = ci.getComponentId();
		assertEquals(CHILD_COMPONENT_NAME, componentID);
	}

}
