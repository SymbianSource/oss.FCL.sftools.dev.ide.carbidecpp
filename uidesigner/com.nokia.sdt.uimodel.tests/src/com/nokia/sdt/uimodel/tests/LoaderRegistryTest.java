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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.Bundle;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

public class LoaderRegistryTest extends TestCase {

	static final String PROJECT_NAME = "loadertests";
	
	IProject project;
	
	protected void setUp() throws Exception {
		super.setUp();
		project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		
		Bundle bundle = TestsPlugin.getDefault().getBundle();
		Path path = new Path("/data/minimal-model.nxd");
		URL url = Platform.find(bundle, path);
		url = Platform.asLocalURL(url);
		File srcFile = new File(url.getFile());
		File destFile = new File(projectDir, "minimal-model.nxd");
		FileUtils.copyFile(srcFile, destFile);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}

	public void testLoadMinimalModel() throws Exception {
		File projectDir = new File(project.getLocation().toOSString());
		File dmFile = new File(projectDir, "minimal-model.nxd");
		
		IDesignerDataModel model = TestDataModelHelpers.loadDataModel(dmFile);
		assertNotNull(model);
		EObject rootContainer = model.getRootContainers()[0];
		assertNotNull(rootContainer);
		
		model.dispose();
	}
}
