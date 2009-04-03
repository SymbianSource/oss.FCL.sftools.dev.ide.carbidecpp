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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Bundle;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

public class SaveTest extends TestCase {

	static final String PROJECT_NAME = "save-tests";
	static final String CHILD_COMPONENT_NAME = "com.nokia.examples.example1";
	static final String NAME_PROPERTY = "name";
	static final String LOCATION_PROPERTY = "location";
	static final String X_PROPERTY = "x";
	static final String Y_PROPERTY = "y";

	IDesignerDataModel model;

	protected void setUp() throws Exception {
		super.setUp();
		IProject project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		
		Bundle bundle = TestsPlugin.getDefault().getBundle();
		Path path = new Path("/data/minimal-model.nxd");
		URL url = Platform.find(bundle, path);
		url = Platform.asLocalURL(url);
		
		File srcFile = new File(url.getFile());
		File destFile = new File(projectDir, "minimal-model.nxd");
		FileUtils.copyFile(srcFile, destFile);
	
		File dmFile = new File(projectDir, "minimal-model.nxd");
		
		model = TestDataModelHelpers.loadDataModel(dmFile);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		model.dispose();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}
	
	public void testSave() throws Exception {
		// this method simply tests that a save succeeds
		EObject rootContainer = model.getRootContainers()[0];
		
		IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(rootContainer, 
				IPropertySource.class);
		
		ps.setPropertyValue("name", "aaaaaa");
	
		Object location = ps.getPropertyValue(LOCATION_PROPERTY);
		
		IPropertySource locationPS = (IPropertySource) location;
		locationPS.setPropertyValue(X_PROPERTY, "100");
		locationPS.setPropertyValue(X_PROPERTY, "200");
	
		model.saveModel(new NullProgressMonitor());
	}

}
