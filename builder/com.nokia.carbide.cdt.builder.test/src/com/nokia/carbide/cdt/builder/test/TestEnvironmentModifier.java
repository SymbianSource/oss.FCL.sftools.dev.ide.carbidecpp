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
package com.nokia.carbide.cdt.builder.test;

import java.util.ArrayList;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.extension.IEnvironmentModifier;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;


public class TestEnvironmentModifier extends TestCase implements IEnvironmentModifier {
	static IProject project;
	static ISymbianSDK symbianSDKS60_30;
	
	protected static final String PROJECT_NAME = "test-env-modifier-project";
	
	private final String ENVMODETEST = "ENVMODETEST";
	private final String ENVMODETESTVALUE = "ENVMODETESTVALUE";

	
	// First thing we have to do is actually create a project in a workspace...
	protected void setUp() throws Exception {
		if (project == null){
			// turn off the indexer
			CCorePlugin.getIndexManager().setDefaultIndexerId(IPDOMManager.ID_NO_INDEXER);

			project = ProjectCorePlugin.createProject(PROJECT_NAME, null);

			ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", TestPlugin.getUsableBuildConfigs(), new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());
		}
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	// Make sure the project nature is there
	public void testModifier() throws Exception{
		assertNotNull(project);

        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        assertNotNull(cpi);
        String[] envVars = cpi.getDefaultConfiguration().getEnvironmentVarsInfo().getResolvedEnvironmentVariables();
        
        boolean found = false;
        
        for (String envVar : envVars) {
        	if (envVar.equals(ENVMODETEST + "=" + ENVMODETESTVALUE)) {
        		found = true;
        		break;
        	}
        }
        
        assertTrue(found);
	}

	public Map<String, String> getModifiedEnvironment(
			ICarbideBuildConfiguration buildContext,
			Map<String, String> environment) {
		assertNotNull(buildContext.getCarbideProject().getProject());
		environment.put(ENVMODETEST, ENVMODETESTVALUE);
		return environment;
	}
}
