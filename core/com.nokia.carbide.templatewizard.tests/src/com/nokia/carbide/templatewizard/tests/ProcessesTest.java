/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.templatewizard.tests;

import com.nokia.carbide.internal.api.template.engine.*;
import com.nokia.carbide.internal.template.gen.Template.TemplateType;
import com.nokia.carbide.internal.templatewizard.tests.TestsPlugin;
import com.nokia.carbide.template.engine.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import junit.framework.TestCase;

public class ProcessesTest extends TestCase {
	
	/**
	 * 
	 */
	private static final String PROJECT_NAME = "Foo";
	private String BASE = "Data/TestTemplate/";
	private String FILEPATH = BASE + "template.xml";
	private String FILEPATH_BADPROCESS_1 = BASE + "bad_process_1.xml";
	private String FILEPATH_BADPROCESS_2 = BASE + "bad_process_2.xml";
	private String FILEPATH_BADPROCESS_3 = BASE + "bad_process_3.xml";
	private String FILEPATH_BADPROCESS_4 = BASE + "bad_process_4.xml";
	private Map<String, Object> valuesMap = new HashMap<String, Object>();

	protected void setUp() throws Exception {
		super.setUp();

		valuesMap.put("baseNameLower", "foo");
		valuesMap.put("aifDir", "aif");
		valuesMap.put("uid3", "0x09DC8317");
		valuesMap.put("sourceDir", "src");
		valuesMap.put("sisDir", "sis");
		valuesMap.put("baseNameUpper", "FOO");
		valuesMap.put("groupDir", "group");
		valuesMap.put("incDir", "inc");
		valuesMap.put("baseName", PROJECT_NAME);
		valuesMap.put("dataDir", "data");
		valuesMap.put("location", "C:/USERS/runtime-New_configuration");
		valuesMap.put("projectName", PROJECT_NAME);
		valuesMap.put("author", "ddubrow");
		valuesMap.put("copyright", "(c) 2008 Nokia Inc.\r\nAll rights reserved");
		valuesMap.put("useDefaultLocation", new Boolean(true));
		//valuesMap.put("selectedBuildConfigs", TestsPlugin.getUsableBuildConfigs()); 
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProjectDescription description = workspace.newProjectDescription(PROJECT_NAME);
		
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		// in case it exists...
		deleteProject(project);
		
		project.create(description, null);
		project.open(null);
	}
	
	private ILoadedTemplate loadTemplate(File file, boolean validate) throws CoreException, IOException {
		assertNotNull(file);
		assertTrue(file.exists());
        Bundle bundle = TestsPlugin.getDefault().getBundle();
		URL templateUrl = file.toURL();
		TemplateType templateType = TemplateLoader.loadTemplate(bundle, templateUrl, validate);
        assertNotNull(templateType);
		ITemplate template = new Template(templateUrl, bundle, null, null, null, null, null, null, null);
		template.getTemplateValues().putAll(valuesMap);
		return template.getLoadedTemplate();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		deleteProject(project);
	}
	
	/**
	 * @param project
	 */
	private void deleteProject(final IProject project) throws Exception {
		if (project.exists()) {
			WorkspaceJob job = new WorkspaceJob("deleting project") {
	
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor)
						throws CoreException {
					project.delete(true, null);
					return Status.OK_STATUS;
				}
				
			};
			job.setRule(ResourcesPlugin.getWorkspace().getRoot());
			job.schedule();
			job.join();
		}
		
	}
	/**
	 * Test running all (or most) of the known processes, testing the loading
	 * of processes as well as the passing of the value store to them.
	 * @throws Exception
	 */
	public void testRunProcesses() throws Exception {
		File templateFile;
		templateFile = FileUtils.pluginRelativeFile(TestsPlugin.getDefault(), FILEPATH);
		ILoadedTemplate template = loadTemplate(templateFile, true);

		IStatus status = TemplateEngine.runProcesses(template.getTemplate(), new NullProgressMonitor());
		assertTrue(status.getMessage(), status.isOK());
		
		// validate value map
		Map<String, Object> values = template.getTemplate().getTemplateValues();
		assertNotNull(values);
		
		// make sure something persisted
		assertEquals(PROJECT_NAME, values.get("projectName"));
		assertEquals("inc", values.get("incDir"));
		
		// a CreateVariables process ran
		assertEquals("1", values.get("FOO"));
		assertEquals("2", values.get("BAR"));
	}
	
	public void testBadProcess1() throws Exception {
		_testBadProcess(FILEPATH_BADPROCESS_1);
	}
	
	private void _testBadProcess(String filepath) throws Exception {
		File templateFile = FileUtils.pluginRelativeFile(TestsPlugin.getDefault(), filepath);
		IStatus status = null;
		try {
			/* should fail here */
			ILoadedTemplate bad_process_template = loadTemplate(templateFile, false);
			
			/* but just in case we don't test processes immediately.. */
			status = TemplateEngine.runProcesses(bad_process_template.getTemplate(), new NullProgressMonitor());

			fail("Bad template should not have passed validation");
		} catch (CoreException e) {
			status = e.getStatus();
		}
		assertFalse(status.isOK());
		System.out.println(status);
	}
	
	public void testBadProcess2() throws Exception {
		_testBadProcess(FILEPATH_BADPROCESS_2);
	}

	public void testBadProcess3() throws Exception {
		_testBadProcess(FILEPATH_BADPROCESS_3);
	}
	public void testBadProcess4() throws Exception {
		_testBadProcess(FILEPATH_BADPROCESS_4);
	}

}
