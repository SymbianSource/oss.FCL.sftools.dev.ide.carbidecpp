/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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

import junit.framework.TestCase;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.dom.IPDOMManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.extension.ICarbidePrefsModifier;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;


public class TestCarbideProjectSettingsModifier extends TestCase {
	static IProject project;
	static ISymbianSDK symbianSDKS60_30;
	
	protected static final String PROJECT_NAME = "test-prj-modifier-project";
	
	private static final String ABLD_BUILD_ARG_SETTING = "ABLD_BUILD_ARG_SETTING";
	
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
	@SuppressWarnings("deprecation")
	public void testModifier() throws Exception{
		assertNotNull(project);

        ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
        assertNotNull(cpi);
        
        // get 
		ICarbidePrefsModifier extProvider = CarbideBuilderPlugin.getBuildManager().getPrefsModifier();
        
		// this will be false once abld is deprecated
		assertTrue(extProvider.isSupportedConfigurationPrefId(cpi.getDefaultConfiguration(), ICarbidePrefsModifier.ABLD_BUILD_ARG_SETTING));
		
		assertFalse(extProvider.isSupportedConfigurationPrefId(cpi.getDefaultConfiguration(), "bad_id"));
		
        assertNotNull(extProvider); // will be null when SBSv1 support is removed
        
		String original = extProvider.getConfigurationValue(cpi.getDefaultConfiguration(), ABLD_BUILD_ARG_SETTING);
		assertTrue(original.equals(""));

		extProvider.setConfigurationValue(cpi.getDefaultConfiguration(), "FOO", ABLD_BUILD_ARG_SETTING);

		cpi.getDefaultConfiguration().saveConfiguration(false);

		String test = extProvider.getConfigurationValue(cpi.getDefaultConfiguration(), ABLD_BUILD_ARG_SETTING);
		assertTrue(test.equals("FOO"));

		extProvider.setConfigurationValue(cpi.getDefaultConfiguration(), original, ABLD_BUILD_ARG_SETTING);

		cpi.getDefaultConfiguration().saveConfiguration(false);
		test = extProvider.getConfigurationValue(cpi.getDefaultConfiguration(), ABLD_BUILD_ARG_SETTING);
		assertTrue(original.equals(test));
	}
	

}
