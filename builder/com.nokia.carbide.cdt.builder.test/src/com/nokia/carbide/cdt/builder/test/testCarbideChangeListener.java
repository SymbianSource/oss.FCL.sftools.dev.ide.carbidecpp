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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideConfigurationChangedListener;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectPropertyChangedListener;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;


public class testCarbideChangeListener extends TestCase {
	static IProject project;
	
	protected static final String PROJECT_NAME = "test-config-listener-project";
	protected List<ISymbianBuildContext> configs;
	protected ICarbideConfigurationChangedListener listener;
	protected ICarbideProjectPropertyChangedListener ppListener;
	protected static int iteration = 1; // not zero because the first config is already the default
			
	// First thing we have to do is actually create a project in a workspace...
	protected void setUp() throws Exception {
		if (project == null){
			project = ProjectCorePlugin.createProject(PROJECT_NAME, null);

			configs = TestPlugin.getUsableBuildConfigs();

			// sort the configs list like CarbideProjectInfo does
			Collections.sort(configs, new Comparator<ISymbianBuildContext>() {

				public int compare(ISymbianBuildContext o1, ISymbianBuildContext o2) {
					return o1.getDisplayString().compareTo(o2.getDisplayString());
				}
	    		
	    	});

			ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", configs, new ArrayList<String>(), "Debug MMP", null, new NullProgressMonitor());
		}
		
		listener = new ICarbideConfigurationChangedListener() {
			public void buildConfigurationChanged(ICarbideBuildConfiguration config){
				System.out.print("\nConfiguration listener, new default config is: " + config.getDisplayString());
				assertNotNull(config);
				assertTrue(config.getDisplayString().compareTo(configs.get(iteration).getDisplayString()) == 0);
				iteration++;
			}
		};
			
		CarbideBuilderPlugin.addBuildConfigChangedListener(listener);

		ppListener = new ICarbideProjectPropertyChangedListener() {
			public void projectPropertyChanged(ICarbideProjectInfo cpi) {
				assertNotNull(cpi);
				Object[] buildComponents = cpi.getInfBuildComponents().toArray();
				System.out.print("\nProject property listener, building components : ");
				for (int i = 0; i < buildComponents.length; i++) {
					System.out.print(buildComponents[i] + " ");
				}
			}
		};

		CarbideBuilderPlugin.addProjectPropertyChangedListener(ppListener);

		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();

		CarbideBuilderPlugin.removeBuildConfigChangedListener(listener);
		CarbideBuilderPlugin.removeProjectPropertyChangedListener(ppListener);

		project.delete(true, new NullProgressMonitor());
	}
	
	// Create a project, change configs, and the listener will check for valid config names
	// This should be the only routine in this test.
	public void testCarbideConfigurationChangedListener() throws Exception{
		assertNotNull(project);

        ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(project);
		assertNotNull(cpm);
		
		// Now actually test the listener where it will check for valid config names...
		List<ICarbideBuildConfiguration> configList = new ArrayList<ICarbideBuildConfiguration>();
		configList = cpm.getBuildConfigurations();
		for (ICarbideBuildConfiguration currConfig : configList){
			cpm.setDefaultConfiguration(currConfig);
			cpm.saveChanges();
			CarbideBuilderPlugin.fireProjectPropertyChanged(cpm);
		}
		
	}
	

}
