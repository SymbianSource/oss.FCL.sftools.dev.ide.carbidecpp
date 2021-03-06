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
package com.nokia.carbide.cdt.builder.test.errorParsers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.builder.CarbideCPPBuilder;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.test.TestPlugin;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv1BuildContext;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

public class TestRVCTErrorParser extends TestCase {
	private static final String PROJECT_NAME = "TestRVCTErrorParser";
	private static final String PROJECT_PATH = "group/bld.inf";
	private static final String TEST_DATA_INPUT_FILE = "data/errorpatterns/rvct.errors.input.1.txt";
	private static final String CONTROL_DATA_FILE    = "data/errorpatterns/rvct.errors.regression.1.xml";
	
	CarbideErrorParserTestHarness harness;
	private IProject project;
	
	protected void setUp() throws Exception {
		super.setUp();
		project = ProjectCorePlugin.createProject(PROJECT_NAME, null);
		List<ISymbianBuildContext> contextList = new ArrayList<ISymbianBuildContext>();
		
		// You need to set the proper default configuration so the correct set of error parsers is called
		List<ISymbianSDK> sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		for (ISymbianSDK currSDK : sdkList){
			List<ISymbianBuildContext> contexts = currSDK.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER).getAllBuildConfigurations();
			for (ISymbianBuildContext context : contexts) {
				if (context.getPlatformString().equals(ISBSv1BuildContext.ARMV5_PLATFORM)) {
					contextList.add(context);
					break;
				}
			}
			if (contextList.size() > 0) {
				break;
			}
		}
		
		assertTrue(contextList.size() > 0);		// if this fail, you don't have any SDK installed supporting GCCE
		
		// Don't do this... Because it will just default to WINSCW target
//		ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", 
//				TestPlugin.getUsableBuildConfigs(), new ArrayList<String>(), 
//				"Debug MMP", null, new NullProgressMonitor());
		
		ProjectCorePlugin.postProjectCreatedActions(project, PROJECT_PATH, 
				contextList, new ArrayList<String>(), 
				"Debug MMP", null, new NullProgressMonitor());
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
		harness = new CarbideErrorParserTestHarness(project, 
												new NullProgressMonitor(),
												buildConfig.getErrorParserList(),  
												cpi.getINFWorkingDirectory());
	}

	protected void tearDown() throws Exception {
		project.delete(true, null);
		super.tearDown();
	}
	
	protected FileInputStream pluginRelativeControlFile(String filePath) {
		FileInputStream fileInputStream = null;
		try {
			java.io.File file = FileUtils.pluginRelativeFile(TestPlugin.getDefault(), filePath);
			fileInputStream = new FileInputStream(file);
		} catch (IOException e) {
			fail("Loading control data file " + filePath + " gives IOException");
		}
		return fileInputStream;
	}


	/**
	 * Test for multiple types of compiler errors
	 */
	public void testRVCTCompilerErrors() {
		//argument 1 is console output you get from the tool
		//argument 2 is control XML with the marker data
		try {
			harness.parseStringAndTestAgainstXML(FileUtils.pluginRelativeFile(TestPlugin.getDefault(), TEST_DATA_INPUT_FILE), 
												FileUtils.pluginRelativeFile(TestPlugin.getDefault(), CONTROL_DATA_FILE));
		} catch (IOException e) {
			fail();
		}
	}
	
	/**
	 * Regress Bugzilla issues by entries
	 */
	public void testBugzillaRegression() {
		final String reportID[] = {"3053"};
		//argument 1 is console output you get from the tool
		//argument 2 is control XML with the marker data
		try {
			for (int i = 0; i < reportID.length; i++) {
				harness.parseStringAndTestAgainstXML(FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/bugzilla/" + reportID[i] +".rvct.input.txt"), 
						FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/bugzilla/" + reportID[i] +".rvct.regression.xml"));
			}
		} catch (IOException e) {
			fail();
		}
	}

}
