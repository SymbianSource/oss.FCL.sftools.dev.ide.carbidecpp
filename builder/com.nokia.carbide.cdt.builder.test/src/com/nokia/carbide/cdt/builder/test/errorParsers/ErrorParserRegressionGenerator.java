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
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

public class ErrorParserRegressionGenerator extends TestCase {
	public ErrorParserRegressionGenerator(String name) {
		super(name);
	}

	private static final String PROJECT_NAME = "TestErrorParser";

	// Platform matters, set this before you build
	private static final String PLATFORM_STRING = ISymbianBuildContext.ARMV5_PLATFORM;

	CarbideErrorParserTestHarness harness;
	private IProject project;
	
	protected void setUp() throws Exception {
		super.setUp();
		project = ProjectCorePlugin.createProject(PROJECT_NAME, null);
		
		// look for first SDK with the platform
		List<ISymbianBuildContext> contextList = new ArrayList<ISymbianBuildContext>();
		// You need to set the proper default configuration so the correct set of error parsers is called
		List<ISymbianSDK> sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		for (ISymbianSDK currSDK : sdkList){
			List<ISymbianBuildContext> contexts = currSDK.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER).getAllBuildConfigurations();
			for (ISymbianBuildContext context : contexts) {
				if (context.getPlatformString().equals(PLATFORM_STRING)) {
					contextList.add(context);
					break;
				}
			}
			if (contextList.size() > 0) {
				break;
			}
		}

		assertTrue(contextList.size() > 0);		// this means you don't have any SDK installe with the said platform
		
//		ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", 
//				TestPlugin.getUsableBuildConfigs(), new ArrayList<String>(), 
//				"Debug MMP", null, new NullProgressMonitor());
		
		ProjectCorePlugin.postProjectCreatedActions(project, "group/bld.inf", 
				contextList, new ArrayList<String>(), 
				"Debug MMP", null, new NullProgressMonitor());
		
		ICarbideProjectInfo cpi = CarbideBuilderPlugin.getBuildManager().getProjectInfo(project);
		ICarbideBuildConfiguration buildConfig = cpi.getDefaultConfiguration();
		harness = new CarbideErrorParserTestHarness(project, 
												new NullProgressMonitor(),
												CarbideCPPBuilder.getParserIdArray(buildConfig.getErrorParserId()), 
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

	/*
	 * This is a template example for how you would write your console input and generate an XML file with the expected results.
	 * In the real test, we will compare the actual parsed results from the text file contents with the expected results in the XML
	 * we generate from this stage.
	 * 1. Fix bug
	 * 2. create your data/???.console.txt from the console output
	 * 3. run this function and get the stdout console
	 * 		harness.parseStringWithAllErrorParsers(java.io.File);
	 * 		harness.getCheckMarkers();
	 *		harness.printAllMakersFromIDE(System.out);
	 * 4. Get the resulting XML output file and save for regression data/???.xml
	 * 5. Write your actual test test 
	 */

	public void testGenerateRegression() {
		// This is how we capture a control case
		try {
			harness.writeFileContentsToStdout(FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/regression_capture/your.input.errorparser.txt"));
			java.io.File file = FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/regression_capture/regression.capture.xml");
			if (!file.exists()) {
				file.createNewFile();
			}
			harness.writeRegressionXMLFile(new java.io.PrintStream(file));	// you can use System.out to capture output in console
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Just a basic test to show how the input is compared to the control for an actual test.
	 */
	public void testBasic() {
		//argument 1 is console output you get from customer
		//argument 2 is control XML
		try {
			harness.parseStringAndTestAgainstXML(FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/example_test/example.errorparser.txt"), 
												FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/example_test/example.errorparser.xml"));
		} catch (IOException e) {
			fail();
		}
	}
}
