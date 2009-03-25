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
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import java.io.*;
import java.util.*;

import junit.framework.*;

public class TestCases extends Assert {
	
	public static final String CVS_FOLDER = "CVS";
	public static final String PROJECT_FOLDER = "project";
	public static final String REFERENCE_FOLDER = "reference";
	public static final String UIDESIGN_EXTENSION = "uidesign";
	public static final String APPLICATION_UIDESIGN = "application." + UIDESIGN_EXTENSION;
	private static final String DATA = "data";
	private static final String DATA_APPLAST = "data2";
	private static boolean applicationLast;

	public static Test suite(boolean applicationLast) {
		TestCases.applicationLast = applicationLast;
		TestSuite suite = new TestSuite(
				"Test for com.nokia.sdt.referenceprojects.test");
		
		createReferenceProjectTests(suite);
		//$JUnit-BEGIN$
		//$JUnit-END$
		return suite;
	}
	
	static void createReferenceProjectTests(TestSuite mainSuite) {
		try {
			File dataFolder = FileUtils.pluginRelativeFile(TestPlugin.getDefault(), applicationLast ? DATA_APPLAST : DATA);
			assertNotNull("Unable to find data folder for reference project tests. Is the plugin unpacked?", dataFolder);
		
			File[] files = dataFolder.listFiles();
			for (File f : files) {
				if (f.isDirectory() && !CVS_FOLDER.equals(f.getName())) {
					createReferenceProjectTest(f, mainSuite);
				}
			}
		} catch (IOException x) {
			x.printStackTrace();
		}
	}
		
	static void createReferenceProjectTest(File containingFolder, TestSuite mainSuite) {

		// The project folder is the sole folder under the folder named "project"
		File projFolderParent= new File(containingFolder, PROJECT_FOLDER);
		FileFilter foldersOnly = new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory() && !CVS_FOLDER.equals(file.getName());
			}
		};
		File[] folders = projFolderParent.listFiles(foldersOnly);
		assertTrue("the 'project' folder must contain a single Carbide.c++ project folder",
				folders != null && folders.length == 1);
		File projectFolder = folders[0];

		File refFolder = new File(containingFolder, REFERENCE_FOLDER);
		String refFolderPath = refFolder.getPath();
		
		String name = "Tests for project:" + projectFolder.getName();
		StatefulTestSuite testsForProject = new StatefulTestSuite(name);
		// add a test for each ui design in the project
		SortedSet<File> uiDesigns = new TreeSet<File>();
		FileFilter uiDesignFilter = new FileFilter() {
			public boolean accept(File file) {
				String extension = TextUtils.getExtension(file.getName());
				return file.isFile() && UIDESIGN_EXTENSION.equalsIgnoreCase(extension);
			}
		};
		recursiveFileSearch(projectFolder, uiDesignFilter, uiDesigns);
		File applicationDesign = null;
		for (File designFile : uiDesigns) {
			if (APPLICATION_UIDESIGN.equalsIgnoreCase(designFile.getName()))
				applicationDesign = designFile;
			TestSuite suite = new StatefulTestSuite(UIDesignTest.class, "UI design tests");
			testsForProject.addTest(new UIDesignTestSetup(suite, designFile));
		}
		// add application design again
		if (applicationLast && applicationDesign != null) { 
			StatefulTestSuite suite = new StatefulTestSuite(UIDesignTest.class, "UI design tests");
			testsForProject.addTest(new UIDesignTestSetup(suite, applicationDesign));
		}
		
		// add a test for each file in the reference folder
		ArrayList<File> referenceFiles = new ArrayList<File>();
		FileFilter referenceFileFilter = new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		};
		recursiveFileSearch(refFolder, referenceFileFilter, referenceFiles);
		for (File referenceFile : referenceFiles) {
			// get relative path from reference folder
			String refFilePath = referenceFile.getPath();
			String relPath = refFilePath.substring(refFolderPath.length());
			TestSuite suite = new StatefulTestSuite(CompareReferenceFileTestCase.class, "Compare source with reference files");
			testsForProject.addTest(new ReferenceFileTestSetup(suite, referenceFile, relPath));
		}
		
		testsForProject.addTest(new CodeScannerTestSetup(
				new StatefulTestSuite(CodeScannerProjectTest.class, "Run codescanner on project"), projectFolder));
		
		mainSuite.addTest(new ProjectTestSetup(testsForProject, projectFolder));
	}

	private static void recursiveFileSearch(File startDir, FileFilter filter, Collection<File> files) {
		File[] currFiles = startDir.listFiles();
		if (currFiles != null) {
			// add matching files
			for (File f: currFiles) {
				if (filter.accept(f)) {
					files.add(f);
				}
			}
			// recurse into subdirectories
			for (File f: currFiles) {
				if (f.isDirectory() && !CVS_FOLDER.equals(f.getName())) {
					recursiveFileSearch(f, filter, files);
				}
			}
		}
	}
}
