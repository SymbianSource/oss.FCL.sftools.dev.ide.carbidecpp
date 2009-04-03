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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.carbide.cpp.internal.codescanner.config.CSProjectSettings;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;

import junit.framework.TestCase;

/**
 * Test cases for Class CSProjectSettings.
 *
 */
public class CSProjectSettingsTest extends TestCase {

	private static IProgressMonitor monitor;
	private static IProject project;

	protected void setUp() throws Exception {
		super.setUp();
		try {
			if (monitor == null) {
				monitor = new NullProgressMonitor();				
			}
			if (project == null) {
				project = ProjectCorePlugin.createProject("CSProjectSettingsTest", null);
			}
			project.open(monitor);
		} catch (CoreException e) {
			fail();
		}
	}

	protected void tearDown() throws Exception {
		try {
			project.close(monitor);
		} catch (CoreException e) {
			fail();
		}
		super.tearDown();
	}

	public void testCSProjectSettings() {
		CSProjectSettings projectSettings = new CSProjectSettings(project);
		assertNotNull(projectSettings);
	}

	public void testGetProject() {
		CSProjectSettings projectSettings = new CSProjectSettings(project);
		assertNotNull(projectSettings);
		assertTrue(projectSettings.getProject().equals(project));
	}

	public void testGetDialogSettings() {
		CSProjectSettings projectSettings = new CSProjectSettings(project);
		assertNotNull(projectSettings);
		assertNotNull(projectSettings.getDialogSettings());
	}

	public void testSaveDialogSettings() {
		CSProjectSettings projectSettings = new CSProjectSettings(project);
		assertNotNull(projectSettings);
		assertNotNull(projectSettings.getDialogSettings());
		assertTrue(projectSettings.saveDialogSettings());
	}

}
