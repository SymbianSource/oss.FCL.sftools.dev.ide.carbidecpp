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
* Test the EpocEnginePathHelper class. 
*
*/
package com.nokia.carbide.cdt.builder.test;

import java.io.File;
import java.io.FileFilter;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cdt.builder.EpocEnginePathHelper;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

public class TestEpocEnginePathHelper extends BaseTest {
	final static String PROJECT1_NAME = "TestEngine";
	final static String PROJECT2_NAME = "TestEngineImported";
	private IProject project1;
	private IPath project1Root;
	private IProject project2;
	private IPath project2Root;
	private File tmpDir;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tmpDir = FileUtils.getTemporaryDirectory();
		project1 = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT1_NAME);
		if (project1.exists())
			project1.delete(true, null);
		project1.create(null);
		project1Root = project1.getRawLocation();
		if (project1Root == null)
			project1Root = project1.getLocation();
		//System.out.println("project1: " + project1Root);
		project1.open(null);
		
		project2 = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT2_NAME);
		if (project2.exists())
			project2.delete(true, null);
		IProjectDescription desc = ResourcesPlugin.getWorkspace().newProjectDescription(PROJECT2_NAME);
		desc.setLocation(new Path(tmpDir.getAbsolutePath()).append(PROJECT2_NAME));
		project2.create(desc, null);
		project2Root = project2.getRawLocation();
		if (project2Root == null)
			project2Root = project2.getLocation();
		//System.out.println("project2: " + project2Root);
		project2.open(null);
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		project1.delete(true, null);
		project2.delete(true, null);
	}
	
	public void testBasic() {
		// assert no crash, etc.
		new EpocEnginePathHelper(project1);
		new EpocEnginePathHelper(project2);
		
	}
	
	private void __testToWorkspacePaths(String projectName, IProject project) {
		// test that workspace-relative paths in a normal project work
		
		EpocEnginePathHelper helper = new EpocEnginePathHelper(project);
		IPath path;
		path = helper.convertToWorkspace(new Path("src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(projectName).append("src"), path);
		
		path = helper.convertToWorkspace(new Path("../src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("src"), path);

		path = helper.convertToWorkspace(new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(projectName).append("foo.cpp"), path);

		path = helper.convertToWorkspace(new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(projectName).append("foo.cpp"), path);

		path = helper.convertToWorkspace(new Path("/sys/bin"));
		assertNull(path);

		path = helper.convertToWorkspace(getStockFullPath().append("data/aif.rss"));
		assertNull(path);

	}
	
	public void testToWorkspacePaths() {
		// test that workspace-relative paths in a normal project work
		__testToWorkspacePaths(PROJECT1_NAME, project1);
		// test that workspace-relative paths in a non-local project work
		__testToWorkspacePaths(PROJECT2_NAME, project2);
	}

	private void __testToProjectPaths(IProject project) {

		EpocEnginePathHelper helper = new EpocEnginePathHelper(project);
		IPath path;
		path = helper.convertToProject(new Path("src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("src"), path);
	
		path = helper.convertToProject(new Path("../src"));
		assertNull(path);
		
		path = helper.convertToProject(new Path("../foo.cpp"));
		assertNull(path);

		path = helper.convertToProject(new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("foo.cpp"), path);

		path = helper.convertToProject(new Path("../src"));

		path = helper.convertToProject(new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("foo.cpp"), path);
		
		path = helper.convertToProject(new Path("/sys/bin"));
		assertNull(path);

		path = helper.convertToProject(getStockFullPath().append("data/aif.rss"));
		assertNull(path);

		path = helper.convertToProject(new Path("/epoc32/include/oem"));
		assertNull(path);
	}

	public void testToProjectPaths() {
		__testToProjectPaths(project1);
		__testToProjectPaths(project2);
	}

	/**
	 * @param helper
	 */
	private void baseFilesystemPathTests(IPath projectRoot, EpocEnginePathHelper helper) {
		IPath path;
		path = helper.convertToFilesystem(new Path("src"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(projectRoot.append("src"), path);
		
		path = helper.convertToFilesystem(new Path("../src"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(projectRoot.removeLastSegments(1).append("src"), path);
	
		path = helper.convertToFilesystem(new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(projectRoot.append("foo.cpp"), path);
		
		// not a real EPOCROOT filesystem path, so it gets the drive letter
		path = helper.convertToFilesystem(new Path("/sys/bin"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(new Path("/sys/bin").setDevice(projectRoot.getDevice()), path);
	
		path = helper.convertToFilesystem(getStockFullPath().append("data/aif.rss"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(getStockFullPath().append("data/aif.rss"), path);
	}

	private void __testToFilesystemPaths(IProject project, IPath projectRoot) {
		// test that filesystem paths work
		
		EpocEnginePathHelper helper = new EpocEnginePathHelper(project);
		baseFilesystemPathTests(projectRoot, helper);
	}
	
	public void testToFilesystemPaths() {
		__testToFilesystemPaths(project1, project1Root);
		__testToFilesystemPaths(project2, project2Root);
	}

	private void setupProjectContent(IProject project, IPath projectRoot) throws Exception {
		FileUtils.copyTreeNoParent(FileUtils.pluginRelativeFile(TestPlugin.getDefault(),
				"data/test-path-helper"), 
				projectRoot.toFile(),
				new FileFilter() {

					public boolean accept(File pathname) {
						if (pathname.getName().equals("CVS"))
							return false;
						return true;
					}
				});
		project.refreshLocal(IResource.DEPTH_INFINITE, null);
	}
	
	/**
	 * Here, we deliberately pass incorrectly-cased paths and ensure that
	 * the resolved paths have the right case AND can be resolved to IResource.
	 * @param project
	 * @param projectRoot
	 */
	private void __testResolvedPaths(IProject project, IPath projectRoot) {
		EpocEnginePathHelper helper = new EpocEnginePathHelper(project);
		IPath path;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		// sanity check: the whole point of resolving is because
		// Eclipse doesn't handle caps correctly
		path = new Path(project.getName().toUpperCase()).append("src").append("cppfile.Cpp");
		assertNull(root.findMember(path));
		
		////////////
		
		path = helper.convertToWorkspace(new Path("src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC"), path);
		assertNotNull(root.findMember(path));
		
		path = helper.convertToWorkspace(new Path("../src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("src"), path);
		assertNull(root.findMember(path));

		path = helper.convertToWorkspace(new Path("src/file/../../base.mmp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("Base.Mmp"), path);
		assertNotNull(root.findMember(path));

		path = helper.convertToWorkspace(new Path("SrC/CPPFILE.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC").append("CPPFILE.CPP"), path);
		assertNotNull(root.findMember(path));

		path = helper.convertToWorkspace(new Path("SrC/krazycaps.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC").append("KrAzYCaPs.Cpp"), path);
		assertNotNull(root.findMember(path));

		path = helper.convertToWorkspace(new Path("SrC/noN/existenT/krazycaps.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC").append("noN").append("existenT").append("krazycaps.cpp"), path);
		assertNull(root.findMember(path));

		path = helper.convertToWorkspace(new Path("src/file/../../Base.Mmp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("Base.Mmp"), path);
		assertNotNull(root.findMember(path));

		path = helper.convertToWorkspace(new Path("/sys/bin"));
		assertNull(path);

		path = helper.convertToWorkspace(getStockFullPath().append("data/aif.rss"));
		assertNull(path);
		
		/////
		//test full paths
		path = helper.convertToWorkspace(projectRoot.append("base.mmp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("Base.Mmp"), path);
		assertNotNull(root.findMember(path));
		
		path = helper.convertToWorkspace(projectRoot.append("src/cppfile.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC").append("CPPFILE.CPP"), path);
		assertNotNull(root.findMember(path));
		
		////////
		
		path = helper.convertFilesystemToWorkspace(getStockFullPath().append("not/in/workspace"));
		assertNull(path);
		
		/*
		 // this test is suspicious because a nonexistent project ISN'T in the workspace! 
		path = helper.convertFilesystemToWorkspace(projectRoot.append("../AnotherProject"));
		if (project == project1) {
			assertNotNull(path);
			assertEquals(new Path("AnotherProject"), path);
		} else {
			// linked project's parallel is not necessarily in the workspace
			assertNull(path);
		}
		*/
		// nonexistent retains caps
		path = helper.convertFilesystemToWorkspace(projectRoot.append("src").append("File.c"));
		assertNotNull(path);
		assertEquals(new Path(project.getName()).append("SrC").append("File.c"), path);

		path = helper.convertFilesystemToWorkspace(projectRoot.append("SrC").append("cppfile.CPP"));
		assertNotNull(path);
		assertEquals(new Path(project.getName()).append("SrC").append("CPPFILE.CPP"), path);
	}
	
	public void testResolvedPaths() throws Exception {
		setupProjectContent(project1, project1Root);
		
		__testResolvedPaths(project1, project1Root);
		
		setupProjectContent(project2, project2Root);

		__testResolvedPaths(project2, project2Root);

	}
}
