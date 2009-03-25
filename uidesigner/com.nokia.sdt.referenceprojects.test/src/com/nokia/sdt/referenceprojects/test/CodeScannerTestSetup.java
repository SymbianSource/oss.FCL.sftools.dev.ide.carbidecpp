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
package com.nokia.sdt.referenceprojects.test;

import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.sdt.testsupport.TestSupportPlugin;

import org.eclipse.core.resources.IProject;

import java.io.File;

import junit.framework.Assert;
import junit.framework.Test;

/**
 * TestSetup to establish an imported project around
 * a TestSuite.
 *
 */
public class CodeScannerTestSetup extends StatefulTestSetup implements ITestStateConstants {

	private static final String CODESCANNER_PLUGIN_PATH = "tools/codescanner/codescanner.exe";
	private static final String CONFIGFILE_PLUGIN_PATH = "tools/codescanner/cs_config.xml";
	private static File codescanner;
	private static File configFile;
	
	private File projectFolder;
	
	public CodeScannerTestSetup(Test test, File projectFolder) {
		super(test);
		this.projectFolder = projectFolder;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[" + projectFolder.getName() + "]";
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		IProject project = (IProject) getState(PROJECT);
		Assert.assertNotNull(project);
		if (codescanner == null) {
			codescanner = 
				FileUtils.pluginRelativeFile(TestSupportPlugin.getDefault(), CODESCANNER_PLUGIN_PATH);
        	assertTrue(codescanner.exists());
		}
        putState(CODESCANNER_FILE, codescanner);
        if (configFile == null) {
        	configFile = 
        		FileUtils.pluginRelativeFile(TestPlugin.getDefault(), CONFIGFILE_PLUGIN_PATH);
        	assertTrue(configFile.exists());
        }
        putState(CODESCANNER_CONFIG_FILE, configFile);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}