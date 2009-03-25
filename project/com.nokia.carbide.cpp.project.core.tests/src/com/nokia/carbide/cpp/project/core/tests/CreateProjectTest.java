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
package com.nokia.carbide.cpp.project.core.tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

public class CreateProjectTest extends TestCase {

	private static final String BLD_INF = "bld.inf";

	public void testCreateProject() {
		// create a simple project
		try {
			IProject project = ProjectCorePlugin.createProject("test1", null);
			assertNotNull(project);
		} catch (CoreException e) {
			fail();
		}
		
		// create a project that already exists
		try {
			ProjectCorePlugin.createProject("test1", null);
			fail("should not get here - previous line should have thrown exception");
		} catch (CoreException e) {
		}
		
		// test that auto-building is preserved correctly
		try {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceDescription workspaceDesc = workspace.getDescription();
			workspaceDesc.setAutoBuilding(false);
			workspace.setDescription(workspaceDesc);

			List<ISymbianBuildContext> configs = TestsPlugin.getUsableBuildConfigs();

			IProject project2 = ProjectCorePlugin.createProject("test2", null);
			ProjectCorePlugin.postProjectCreatedActions(project2, BLD_INF, configs, new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());

			workspaceDesc = workspace.getDescription();
			assertFalse(workspaceDesc.isAutoBuilding());
			

			workspaceDesc.setAutoBuilding(true);
			workspace.setDescription(workspaceDesc);

			IProject project3 = ProjectCorePlugin.createProject("test3", null);
			ProjectCorePlugin.postProjectCreatedActions(project3, BLD_INF, configs, new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());

			workspaceDesc = workspace.getDescription();
			assertTrue(workspaceDesc.isAutoBuilding());

			// make sure the build info is set
	        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project3);
			assertNotNull(cpi);
			
			assertEquals(BLD_INF, cpi.getProjectRelativeBldInfPath().toOSString());
			assertTrue(cpi.isBuildingFromInf());
			
		} catch (CoreException e) {
			fail();
		}

	}
	
 }
