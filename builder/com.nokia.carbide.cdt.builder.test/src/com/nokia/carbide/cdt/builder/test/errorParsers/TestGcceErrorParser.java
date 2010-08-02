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

public class TestGcceErrorParser extends TestCase {

	public TestGcceErrorParser(String name) {
		super(name);
	}

	private static final String PROJECT_NAME = "TestGcceErrorParser";
	private static final String PROJECT_PATH = "group/bld.inf";
	
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
				if (context.getPlatformString().equals(ISBSv1BuildContext.GCCE_PLATFORM)) {
					contextList.add(context);
					break;
				}
			}
			if (contextList.size() > 0) {
				break;
			}
		}
		
		assertTrue(contextList.size() > 0);		// if this fail, you don't have any SDK installed supporting GCCE
		
		ProjectCorePlugin.postProjectCreatedActions(project, PROJECT_PATH, 
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
	
	/**
	 * Test for assembler errors/warnings
	 */
	/*
	public void testGnuAsMessages() {
		//argument 1 is console output you get from the tool
		//argument 2 is control XML with the marker data
		try {
			harness.parseStringAndTestAgainstXML(FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/gnu_as.input.txt"), 
												FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/gnu_as.regression.xml"));
		} catch (IOException e) {
			fail();
		}
	}
	*/
	
	/*
	 * Test for AbstractGCCErrorParser
	 */
	public void testAbstractGCCErrorParser() {
		//argument 1 is console output you get from the tool
		//argument 2 is control XML with the marker data
		try {
			harness.parseStringAndTestAgainstXML(FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/abstractgcce.input.txt"), 
												FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/abstractgcce.regression.xml"));
		} catch (IOException e) {
			fail();
		}
	}

	/**
	 * Regress Bugzilla issues by entries
	 */
	public void testBugzillaRegression() {
		final String reportID[] = {"2969", "4541", "5618", "5824", "6417"};
		//argument 1 is console output you get from the tool
		//argument 2 is control XML with the marker data
		try {
			for (int i = 0; i < reportID.length; i++) {
				harness.parseStringAndTestAgainstXML(FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/bugzilla/" + reportID[i] +".gcce.input.txt"), 
						FileUtils.pluginRelativeFile(TestPlugin.getDefault(), "data/errorpatterns/bugzilla/" + reportID[i] +".gcce.regression.xml"));
			}
		} catch (IOException e) {
			fail();
		}
	}
	
}
