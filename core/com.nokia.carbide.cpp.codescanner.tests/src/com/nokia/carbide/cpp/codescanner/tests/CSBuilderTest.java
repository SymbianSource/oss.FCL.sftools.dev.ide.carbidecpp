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

package com.nokia.carbide.cpp.codescanner.tests;

import junit.framework.TestCase;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.carbide.cpp.internal.codescanner.CSBuilder;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;

/**
 * Test cases for Class CSBuilder.
 *
 */
public class CSBuilderTest extends TestCase {

	private static CSBuilder builder;
	private static IProgressMonitor monitor;
	private static IProject project;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			if (builder == null) {
				builder = new CSBuilder();
			}
			if (monitor == null) {
				monitor = new NullProgressMonitor();				
			}
			if (project == null) {
				project = ProjectCorePlugin.createProject("CSBuilderTest", null);
			}
			project.open(monitor);
		} catch (CoreException e) {
			fail();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		try {
			project.close(monitor);
		} catch (CoreException e) {
			fail();
		}
		super.tearDown();
	}

	public void testAddBuilderToProject() {
		CSBuilder.addBuilderToProject(project);
		try {
			assertTrue(projectHasBuilder());
		}
		catch(CoreException e) {
			fail();
		}
	}

	private boolean projectHasBuilder() throws CoreException {
		IProjectDescription description;
		description = project.getDescription();
		ICommand[] commands = description.getBuildSpec();
		int index = -1;
		for (int i = 0; i < commands.length; i++) {
			if (commands[i].getBuilderName().equals(CSBuilder.BUILDER_ID)) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			return false;
		}
		else {
			return true;
		}
	}

}
