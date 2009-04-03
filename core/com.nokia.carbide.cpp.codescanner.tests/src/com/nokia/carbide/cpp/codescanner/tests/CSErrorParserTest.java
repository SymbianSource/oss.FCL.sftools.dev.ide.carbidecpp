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

package com.nokia.carbide.cpp.codescanner.tests;

import java.io.*;

import com.nokia.carbide.cpp.internal.codescanner.error.parsers.*;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import org.eclipse.cdt.core.ErrorParserManager;
import org.eclipse.cdt.core.IMarkerGenerator;
import org.eclipse.cdt.core.ProblemMarkerInfo;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;

import junit.framework.TestCase;

/**
 * Test cases for class CSErrorParser.
 *
 */
public class CSErrorParserTest extends TestCase {
	
	private class testMarkerGenerator implements IMarkerGenerator {
		public void addMarker(ProblemMarkerInfo problemMarkerInfo) {
		}
		public void addMarker(IResource file, int lineNumber, String errorDesc, int severity, String errorVar) {
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testProcessLine() {
		
		try {
			IProject project = ProjectCorePlugin.createProject("codescanner_test1", null);
			assertNotNull(project);
			IPath workingDir = project.getLocation();
			IPath filePath = workingDir.append("MyTest.cpp");
			testMarkerGenerator markGen = new testMarkerGenerator();
			NullProgressMonitor monitor = new NullProgressMonitor();
			String[] errorParserIds = new String[0];				
			ErrorParserManager errorParserManager = new ErrorParserManager(project, workingDir, markGen, errorParserIds);
			CSErrorParser errorParser = new CSErrorParser();

			createTestFile(filePath);
			
			// test with different messages
			String msg = filePath.toString() + "(1) : warning: this is a warning message";
			assertTrue(errorParser.processLine(msg, errorParserManager));
			msg = filePath.toString() + "(2) : error: this is an error message";
			assertTrue(errorParser.processLine(msg, errorParserManager));
			assertFalse(errorParser.processLine("the system cannot find the path specified", errorParserManager));
			assertFalse(errorParser.processLine("a random message", errorParserManager));
			
			project.delete(true, true, monitor);
		} catch (CoreException e) {
			fail();
		}
	}

	private void createTestFile(IPath filePath) {
		try {
			FileOutputStream fout = new FileOutputStream (filePath.toOSString());
		    new PrintStream(fout).println ("This is only a test.");
		    new PrintStream(fout).println ("There is no need to panic.");
		    fout.close();		
		}
		catch (IOException e) {
			fail();
		}
	}
}
