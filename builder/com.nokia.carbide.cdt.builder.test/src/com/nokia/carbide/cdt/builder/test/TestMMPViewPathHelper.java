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
* Test the MMPViewPathHelper class.
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

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.InvalidDriveInMMPPathException;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.HostOS;

public class TestMMPViewPathHelper extends BaseTest {
	final static String PROJECT1_NAME = "TestMmp";
	final static String PROJECT2_NAME = "TestMmpImported";
	private IPath epocRoot;
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
		ISymbianSDK sdk = SDKCorePlugin.getSDKManager().getSDKList().get(0);
		//epocRoot = new Path("c:/symbian/9.1/S60_3rd/");
		epocRoot = new Path(sdk.getEPOCROOT());
		project1 = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT1_NAME);
		if (project1.exists())
			project1.delete(true, null);
		project1.create(null);
		project1Root = project1.getRawLocation();
		if (project1Root == null)
			project1Root = project1.getLocation();
		System.out.println("project1: " + project1Root);
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
		System.out.println("project2: " + project2Root);
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
		new MMPViewPathHelper(project1, epocRoot.toOSString());
		new MMPViewPathHelper(project2, epocRoot.toOSString());
		
	}
	
	private void __testToWorkspacePaths(String projectName, IProject project) {
		// test that workspace-relative paths in a normal project work
		
		MMPViewPathHelper helper = new MMPViewPathHelper(project, epocRoot.toOSString());
		IPath path;
		path = helper.convertMMPToWorkspace(EMMPPathContext.SOURCEPATH, new Path("src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(projectName).append("src"), path);
		
		path = helper.convertMMPToWorkspace(EMMPPathContext.SOURCEPATH, new Path("../src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("src"), path);

		path = helper.convertMMPToWorkspace(EMMPPathContext.SOURCE, new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(projectName).append("foo.cpp"), path);

		path = helper.convertMMPToWorkspace(EMMPPathContext.START_BITMAP_SOURCE, new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(projectName).append("foo.cpp"), path);

		path = helper.convertMMPToWorkspace(EMMPPathContext.USERINCLUDE, 
				new Path("/sys/bin"));
		assertNull(path);

		path = helper.convertMMPToWorkspace(EMMPPathContext.AIF_SOURCE, 
				getStockFullPath().append("data/aif.rss"));
		assertNull(path);
		
		path = helper.convertMMPToWorkspace(EMMPPathContext.SYSTEMINCLUDE, 
				new Path("+/include/oem"));
		assertNull(path);

	}
	
	public void testToWorkspacePaths() {
		// test that workspace-relative paths in a normal project work
		__testToWorkspacePaths(PROJECT1_NAME, project1);
		// test that workspace-relative paths in a non-local project work
		__testToWorkspacePaths(PROJECT2_NAME, project2);
	}

	private void __testToProjectPaths(IProject project) {

		MMPViewPathHelper helper = new MMPViewPathHelper(project, epocRoot.toOSString());
		IPath path;
		path = helper.convertMMPToProject(EMMPPathContext.SOURCEPATH, 
				new Path("src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("src"), path);
	
		path = helper.convertMMPToProject(EMMPPathContext.SOURCEPATH, 
				new Path("../src"));
		assertNull(path);
		
		path = helper.convertMMPToProject(EMMPPathContext.SOURCE, 
				new Path("../foo.cpp"));
		assertNull(path);

		path = helper.convertMMPToProject(EMMPPathContext.START_BITMAP_SOURCE, 
				new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("foo.cpp"), path);

		path = helper.convertMMPToProject(EMMPPathContext.SOURCEPATH, 
				new Path("../src"));

		path = helper.convertMMPToProject(EMMPPathContext.SOURCEPATH, 
				new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("foo.cpp"), path);
		
		path = helper.convertMMPToProject(EMMPPathContext.USERINCLUDE, 
				new Path("/sys/bin"));
		assertNull(path);

		path = helper.convertMMPToWorkspace(EMMPPathContext.AIF_SOURCE, 
				getStockFullPath().append("data/aif.rss"));
		assertNull(path);

		path = helper.convertMMPToWorkspace(EMMPPathContext.USERINCLUDE, 
				new Path("/epoc32/include/oem"));
		assertNull(path);

		path = helper.convertMMPToWorkspace(EMMPPathContext.SYSTEMINCLUDE, 
				new Path("+/include/oem"));
		assertNull(path);
		
	}

	public void testToProjectPaths() {
		__testToProjectPaths(project1);
		__testToProjectPaths(project2);
	}

	/**
	 * @param helper
	 */
	private void baseFilesystemPathTests(IPath projectRoot, MMPViewPathHelper helper) {
		IPath path;
		path = helper.convertMMPToFilesystem(EMMPPathContext.SOURCEPATH, 
				new Path("src"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(projectRoot.append("src"), path);
		
		path = helper.convertMMPToFilesystem(EMMPPathContext.SOURCE, 
				new Path("../src"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(projectRoot.removeLastSegments(1).append("src"), path);
	
		path = helper.convertMMPToFilesystem(EMMPPathContext.START_BITMAP_SOURCE, 
				new Path("src/file/../../foo.cpp"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(projectRoot.append("foo.cpp"), path);
		
		// this is not a valid path so it will get a drive letter from the project 
		path = helper.convertMMPToFilesystem(EMMPPathContext.USERINCLUDE, 
				new Path("/sys/bin"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(new Path("/sys/bin").setDevice(projectRoot.getDevice()), path);
	
		path = helper.convertMMPToFilesystem(EMMPPathContext.AIF_SOURCE, 
				getStockFullPath().append("data/aif.rss"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		assertEquals(getStockFullPath().append("data/aif.rss"), path);
	}

	private void __testToFilesystemPaths(IProject project, IPath projectRoot) {
		// test that filesystem paths work
		
		MMPViewPathHelper helper = new MMPViewPathHelper(project, epocRoot.toOSString());
		IPath path;
		
		baseFilesystemPathTests(projectRoot, helper);
		
		path = helper.convertMMPToFilesystem(EMMPPathContext.USERINCLUDE, 
				new Path("/epoc32/include/oem"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		// the caps may change for the real FS
		//assertEquals(epocRoot.append("/epoc32/include/oem"), path);
		assertEquals(epocRoot.append("/epoc32/include/oem").toOSString().toLowerCase(), path.toOSString().toLowerCase());
		
		path = helper.convertMMPToFilesystem(EMMPPathContext.SYSTEMINCLUDE, 
				new Path("+/include/oem"));
		assertNotNull(path);
		assertTrue(path.isAbsolute());
		//assertEquals(epocRoot.append("/epoc32/include/oem"), path);
		assertEquals(epocRoot.append("/epoc32/include/oem").toOSString().toLowerCase(), path.toOSString().toLowerCase());
		
	}
	public void testToFilesystemPaths() {
		__testToFilesystemPaths(project1, project1Root);
		__testToFilesystemPaths(project2, project2Root);
	}


	private void __testToFilesystemPathsWithoutEPOCROOT(IPath projectRoot) {
		// test that filesystem paths work

		MMPViewPathHelper helper = new MMPViewPathHelper(projectRoot, null);
		IPath path;

		baseFilesystemPathTests(projectRoot, helper);

		// no EPOCROOT, so no answers
		path = helper.convertMMPToFilesystem(EMMPPathContext.USERINCLUDE, 
				new Path("/epoc32/include/oem"));
		assertNull(path);

		path = helper.convertMMPToFilesystem(EMMPPathContext.SYSTEMINCLUDE, 
				new Path("+/include/oem"));
		assertNull(path);
	}
	
	public void testToFilesystemPathsWithoutEPOCROOT() {
		__testToFilesystemPathsWithoutEPOCROOT(project1Root);
		__testToFilesystemPathsWithoutEPOCROOT(project2Root);
	}
	
	
	private void __testToMMPPaths(IProject project) {
		MMPViewPathHelper helper = new MMPViewPathHelper(project, epocRoot.toOSString());
		IPath path;
		try {
			path = helper.convertProjectOrFullPathToMMP(EMMPPathContext.SOURCEPATH, 
					new Path("src"));
			assertEquals(new Path("src"), path);
		} catch (InvalidDriveInMMPPathException e) {
			fail();
		}
		try {
			path = helper.convertProjectOrFullPathToMMP(EMMPPathContext.SYSTEMRESOURCE, 
					new Path("src/foo.cpp"));
			assertEquals(new Path("src/foo.cpp"), path);
		} catch (InvalidDriveInMMPPathException e) {
			fail();
		}

		try {
			path = helper.convertProjectOrFullPathToMMP(EMMPPathContext.SOURCEPATH, 
					new Path("/sys/bin"));
			assertEquals(new Path("/sys/bin"), path);
		} catch (InvalidDriveInMMPPathException e) {
			fail();
		}

		IPath driveLessEpocRoot = epocRoot.setDevice(null);
		try {
			path = helper.convertProjectOrFullPathToMMP(EMMPPathContext.SOURCEPATH, 
					epocRoot.append("epoc32").append("include").append("oem"));
			assertEquals(new Path("/epoc32/include/oem"), path);
		} catch (InvalidDriveInMMPPathException e) {
			fail();
		}

		try {
			path = helper.convertProjectOrFullPathToMMP(EMMPPathContext.AIF_SOURCE, 
					epocRoot.append("epoc32").append("data").append("foo.rss"));
			assertEquals(driveLessEpocRoot.append("epoc32").append("data").append("foo.rss"), path);
		} catch (InvalidDriveInMMPPathException e) {
			fail();
		}

		if (HostOS.IS_WIN32) {
			try {
				path = helper.convertProjectOrFullPathToMMP(EMMPPathContext.AIF_SOURCE, 
						new Path("b:/schnozz/foo.rss"));
				fail();
			} catch (InvalidDriveInMMPPathException e) {
				assertEquals(new Path("b:/schnozz/foo.rss"), e.getPath());
				assertEquals(new Path("/schnozz/foo.rss"), e.getPathNoDevice());
			}
		}
		
	}
	
	public void testToMMPPaths() {
		__testToMMPPaths(project1);
		__testToMMPPaths(project2);
	}
	
	public void testTwoWay1() {
		// make sure conversion to and back works
		MMPViewPathHelper helper = new MMPViewPathHelper(project1, epocRoot.toOSString());

		twoWayTest(helper, EMMPPathContext.SOURCEPATH, new Path("src"));
		twoWayTest(helper, EMMPPathContext.SOURCE, new Path("src/file/../../foo.cpp"));
		twoWayTest(helper, EMMPPathContext.START_BITMAP_SOURCE, new Path("src/file/../../foo.cpp"));
		
		twoWayTest(helper, EMMPPathContext.USERINCLUDE, new Path("/sys/bin"));
		// this case won't pass since drive letters are dropped.
		//twoWayTest(helper, EMMPPathContext.AIF_SOURCE, new Path("c:/data/aif.rss"));
		twoWayTest(helper, EMMPPathContext.AIF_SOURCE, new Path("/data/aif.rss"));
		twoWayTest(helper, EMMPPathContext.SYSTEMINCLUDE, new Path("epoc32/include/oem"));
	}

	private void twoWayTest(MMPViewPathHelper helper, EMMPPathContext context, IPath path) {
		IPath wsPath = helper.convertMMPToProject(context, path);
		if (wsPath != null) {
			try {
				IPath rePath = helper.convertProjectOrFullPathToMMP(context, wsPath);
				assertEquals(path, rePath);
			} catch (InvalidDriveInMMPPathException e) {
				fail("could not convert path="+path+", wsPath="+wsPath);
			}
		} else {
			try {
				IPath fullPath = helper.convertMMPToFilesystem(context, path);
				if (path.getDevice() == null)
					fullPath = fullPath.setDevice(null);
				IPath rePath = helper.convertProjectOrFullPathToMMP(context, fullPath);
				assertEquals(path, rePath);
			} catch (InvalidDriveInMMPPathException e) {
				fail("could not two-way convert path="+path+ " w/EPOCROOT="+epocRoot);
			}
		}
		
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
		MMPViewPathHelper helper = new MMPViewPathHelper(project, epocRoot.toOSString());
		IPath path;
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		
		// sanity check: the whole point of resolving is because
		// Eclipse doesn't handle caps correctly
		path = new Path(project.getName().toUpperCase()).append("src").append("cppfile.Cpp");
		assertNull(root.findMember(path));
		
		path = helper.convertMMPToWorkspace(EMMPPathContext.SOURCEPATH, 
				new Path("src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC"), path);
		assertNotNull(root.findMember(path));
		
		path = helper.convertMMPToWorkspace(EMMPPathContext.SOURCEPATH, 
				new Path("../src"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path("src"), path);
		assertNull(root.findMember(path));

		path = helper.convertMMPToWorkspace(EMMPPathContext.SOURCE, 
				new Path("src/file/../../base.mmp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("Base.Mmp"), path);
		assertNotNull(root.findMember(path));

		path = helper.convertMMPToWorkspace(EMMPPathContext.SOURCE, 
				new Path("SrC/CPPFILE.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC").append("CPPFILE.CPP"), path);
		assertNotNull(root.findMember(path));

		path = helper.convertMMPToWorkspace(EMMPPathContext.SOURCE, 
				new Path("SrC/krazycaps.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC").append("KrAzYCaPs.Cpp"), path);
		assertNotNull(root.findMember(path));

		path = helper.convertMMPToWorkspace(EMMPPathContext.SOURCE, 
				new Path("SrC/noN/existenT/krazycaps.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC").append("noN").append("existenT").append("krazycaps.cpp"), path);
		assertNull(root.findMember(path));

		path = helper.convertMMPToWorkspace(EMMPPathContext.START_BITMAP_SOURCE, 
				new Path("src/file/../../Base.Mmp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("Base.Mmp"), path);
		assertNotNull(root.findMember(path));

		path = helper.convertMMPToWorkspace(EMMPPathContext.USERINCLUDE, 
				new Path("/sys/bin"));
		assertNull(path);

		path = helper.convertMMPToWorkspace(EMMPPathContext.AIF_SOURCE, 
				getStockFullPath().append("data/aif.rss"));
		assertNull(path);
		
		path = helper.convertMMPToWorkspace(EMMPPathContext.SYSTEMINCLUDE, 
				new Path("+/include/oem"));
		assertNull(path);
		
		/////
		//test full paths.  
		path = helper.convertMMPToWorkspace(EMMPPathContext.START_BITMAP_SOURCE, 
				projectRoot.append("base.mmp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("Base.Mmp"), path);
		assertNotNull(root.findMember(path));
		
		path = helper.convertMMPToWorkspace(EMMPPathContext.START_BITMAP_SOURCE, 
				projectRoot.append("src/cppfile.cpp"));
		assertNotNull(path);
		assertFalse(path.isAbsolute());
		assertEquals(new Path(project.getName()).append("SrC").append("CPPFILE.CPP"), path);
		assertNotNull(root.findMember(path));
	}
	
	public void testResolvedPaths() throws Exception {
		setupProjectContent(project1, project1Root);
		
		__testResolvedPaths(project1, project1Root);
		
		setupProjectContent(project2, project2Root);

		__testResolvedPaths(project2, project2Root);

	}
}
