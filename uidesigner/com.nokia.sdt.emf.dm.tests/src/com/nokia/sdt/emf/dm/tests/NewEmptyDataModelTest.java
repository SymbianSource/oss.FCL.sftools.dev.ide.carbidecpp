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

import com.nokia.sdt.emf.dm.IDesignerData;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;

import java.io.File;

import junit.framework.TestCase;

/**
 * Simple tests on a newly created data model with no root container
 *
 */
public class NewEmptyDataModelTest extends TestCase {
	
	DesignerDataModel model;
	
	static final String PROJECT_NAME = "newemptydm";
	static final String FILE_NAME = "model.nxd";

	protected void setUp() throws Exception {
		super.setUp();
		IProject project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		File dmFile = new File(projectDir, FILE_NAME);
		
		// make a file local to this plugin, in the testdata directory
		model = (DesignerDataModel) TestDataModelHelpers.createTestModel(dmFile, null);
		TestDataModelHelpers.initDefaultComponentSet(model);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		model.dispose();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}

	public void testGetDesignerData() {
		// we expect the root object to be automatically created
		EObject root = model.getRoot();
		assertNotNull(root);
		// not required by IDesignerDataModel, but it's what we expect
		assertTrue(root instanceof IDesignerData);
	}

	public void testGetEditingDomain() {
		EditingDomain domain = model.getEditingDomain();
		assertNotNull(domain);
	}

	public void testGetNullRootContainer() {
		// no root container is added by default
		assertEquals(0, model.getRootContainers().length);
	}

	public void testGetComponentSet() {
		assertNotNull(model.getComponentSet());
	}
}
