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

import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IValidateContainment;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.*;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.osgi.framework.Bundle;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import junit.framework.TestCase;

public class DisplayModelSmokeTest extends TestCase {
	
	static final String PROJECT_NAME = "displaymodel-tests";
	static final String MODEL_FILE_NAME = "display-model-test.nxd";

	private IDesignerDataModel dataModel;

	protected void setUp() throws Exception {
		super.setUp();
		Logging.alwaysLogToConsole = true;
		
		IProject project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		
		Bundle bundle = TestsPlugin.getDefault().getBundle();
		Path path = new Path("/data/"+MODEL_FILE_NAME);
		URL url = Platform.find(bundle, path);
		url = Platform.asLocalURL(url);
		
		File srcFile = new File(url.getFile());
		File destFile = new File(projectDir, MODEL_FILE_NAME);
		FileUtils.copyFile(srcFile, destFile);
	
		File dmFile = new File(projectDir, MODEL_FILE_NAME);
		
		dataModel = TestDataModelHelpers.loadDataModel(dmFile);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		dataModel.dispose();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}
	
	IDisplayModel getDisplayModel() throws CoreException {
		EObject rootContainer = dataModel.getRootContainers()[0];
		return dataModel.getDisplayModelForRootContainer(rootContainer);
	}
	
	public void testCreateAndDispose() throws CoreException {
		IDisplayModel displayModel = getDisplayModel();
		assertNotNull(displayModel);
		displayModel.dispose();
	}
	
	public void testDuplicate() throws CoreException {
		IDisplayModel displayModel = getDisplayModel();
		assertNotNull(displayModel);
		
		IDisplayModel displayModel2 = getDisplayModel();
		assertNotNull(displayModel2);
		assertSame(displayModel, displayModel2);
	}
	
	public void testGetConfigurations() throws CoreException {
		IDisplayModel displayModel = getDisplayModel();
		
		Collection configs = displayModel.getLayoutAreaConfigurations();
		assertNotNull(configs);
		assertTrue(configs.size() >= 0);
		
		displayModel.dispose();
		
	}
	
	IDisplayObject getDisplayObject(EObject obj) {
		IDisplayObject result = (IDisplayObject)EcoreUtil.getRegisteredAdapter(obj, IDisplayObject.class);
		return result;
	}
	
	ILayoutObject getLayoutObject(EObject obj) {
		return (ILayoutObject) EcoreUtil.getRegisteredAdapter(obj, ILayoutObject.class);
	}
	
	ILayoutContainer getLayoutContainer(EObject obj) {
		return (ILayoutContainer) EcoreUtil.getRegisteredAdapter(obj, ILayoutContainer.class);
	}

	IValidateContainment getValidateContainment(EObject obj) {
		return (IValidateContainment) EcoreUtil.getRegisteredAdapter(obj, IValidateContainment.class);
	}

	public void testAdapters() throws CoreException {
		
		EObject rootContainer = dataModel.getRootContainers()[0];
		assertNotNull(rootContainer);
		assertNull(getDisplayObject(rootContainer));
		assertNull(getLayoutObject(rootContainer));
		assertNull(getLayoutContainer(rootContainer));
		assertNull(getValidateContainment(rootContainer));
		
		IDisplayModel displayModel = getDisplayModel();
		
		assertNotNull(getDisplayObject(rootContainer));
		assertNotNull(getLayoutObject(rootContainer));
		assertNotNull(getLayoutContainer(rootContainer));
		assertNotNull(getValidateContainment(rootContainer));

		displayModel.dispose();
			
	}

}
