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
package com.nokia.sdt.referenceprojects.test;

import com.nokia.sdt.sourcegen.SourceGenUtils;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.eclipse.core.resources.IProject;

import java.io.File;

import junit.framework.Assert;
import junit.framework.Test;

/**
 * TestSetup to establish an imported project around
 * a TestSuite.
 *
 */
public class ProjectTestSetup extends StatefulTestSetup implements ITestStateConstants {

	private File projectFolder;
	
	public ProjectTestSetup(Test test, File projectFolder) {
		super(test);
		this.projectFolder = projectFolder;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[" + projectFolder.getName() + "]";
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestDataModelHelpers.initDefaultComponentProvider();
		SourceGenUtils.setSourceFileHeaderTemplate(null);
		IProject project = ProjectUtils.importProject(projectFolder);
		Assert.assertNotNull(project);
		putState(PROJECT, project);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		IProject project = (IProject) getState(PROJECT);
		if (project != null) {
			ProjectUtils.closeAndDeleteProject(project.getName(), false);
		}
	}

}
