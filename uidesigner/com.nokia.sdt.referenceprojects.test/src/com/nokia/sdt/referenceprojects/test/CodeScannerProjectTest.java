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

import org.eclipse.cdt.core.CommandLauncher;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CodeScannerProjectTest extends StatefulTestCase implements ITestStateConstants {
	
	private IProject project;
	private IPath csCommand;
	private String csConfigPath;

	protected void setUp() throws Exception {
		super.setUp();
		project = (IProject) getState(PROJECT);
		assertNotNull(project);
		File codescanner = (File) getState(CODESCANNER_FILE);
		assertNotNull(codescanner);
		csCommand = new Path(codescanner.getCanonicalPath());
		File configFile = (File) getState(CODESCANNER_CONFIG_FILE);
		assertNotNull(configFile);
		csConfigPath = configFile.getCanonicalPath();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testScanProject() throws Exception {

        // construct the arguments...
        List<String> argList = new ArrayList<String>();

        // configuration file
       	argList.add("-c");
       	argList.add(csConfigPath);

       	// output
       	argList.add("-o"); 
       	argList.add("std");
       	
        // project path
        String projectLocation = project.getLocation().toOSString();
		argList.add(projectLocation);

		String[] args = new String[argList.size()];
		argList.toArray(args);

        // set up command launcher class to handle calling CodeScanner and parse output
        CommandLauncher cmdLauncher = new CommandLauncher();
		cmdLauncher.execute(csCommand, args, null, new Path(projectLocation));

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		int exitCode = cmdLauncher.waitAndRead(out, err);

		String stdOutStr = postProcessStdOut(out.toString());
		
		assertEquals("stdout: " + stdOutStr, 0, stdOutStr.length());
		assertEquals("stderr: " + err, 0, err.toString().length());
		assertEquals(0, exitCode);
	}
	
	private boolean isNotErrorLine(String line) {
		return line.startsWith("Note:") || line.startsWith("Scanning");
	}

	private final static String[] KNOWN_ERROR_PATTERNS = {
		".*lists_3_0ListBox.*: warning: resourcesonheap: low: codingstandards: resource objects on the heap"
	};
	private boolean isKnownAllowedError(String line) {
		for (String pattern : KNOWN_ERROR_PATTERNS) {
			if (line.matches(pattern))
				return true;
		}
		
		return false;
	}
	
	private String postProcessStdOut(String s) {
		StringBuilder result = new StringBuilder();
		String[] lines = s.split("\r\n|\r|\n");
		// ignore the first line "Nokia CodeScanner version 2.0.9"
		boolean first = true;
		for (String line : lines) {
			// ignore notes and scanning start and end lines
			if (!first && !isNotErrorLine(line) && !isKnownAllowedError(line)) {
				result.append(line);
				result.append("\n");
			}
			first = false;
		}
		return result.toString().trim();
	}		
}
