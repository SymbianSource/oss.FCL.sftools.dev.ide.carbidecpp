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
* Test the ImageMakefileViewPathHelper class.
*
*/
package com.nokia.carbide.cdt.builder.test;

import com.nokia.carbide.cdt.builder.DefaultImageMakefileViewConfiguration;
import com.nokia.carbide.cdt.builder.ImageMakefileViewPathHelper;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileModel;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.cpp.internal.api.utils.core.FileUtils;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import junit.framework.TestCase;

public class TestImageMakefileViewPathHelper extends TestCase {
	final static String PROJECT1_NAME = "TestMk";
	final static String PROJECT2_NAME = "TestMkImported";
	private static final IPath IMAGE1_MAKEFILE_PATH = new Path("gfx/Images1.mk");
	private static final IPath IMAGE2_MAKEFILE_PATH = new Path("Images2.mk");
	private static final IPath PROJECT_ICON_PATH = new Path("gfx/qgn_menu_Birthdays.svg");
	private static final IPath STD_ICON_PATH = new Path("stdfile1.svg");
	private static final IPath STD_BITMAP_PATH = new Path("stdfile2.bmp");
	private static final IPath STD_BITMAP_MASK_PATH = new Path("stdfile2_mask.bmp");
	private static final String IMAGE1_MAKEFILE = 
		"# ============================================================================\r\n" + 
			"#  Name     : Icons_aif_scalable_dc.mk\r\n" + 
			"#  Part of  : Birthdays\r\n" + 
			"#\r\n" + 
			"#  Description:\r\n" + 
			"# \r\n" + 
			"# ============================================================================\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
			"ZDIR=$(EPOCROOT)epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
			"else\r\n" + 
			"ZDIR=$(EPOCROOT)epoc32\\data\\z\r\n" + 
			"endif\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"# ----------------------------------------------------------------------------\r\n" + 
			"# TODO: Configure these\r\n" + 
			"# ----------------------------------------------------------------------------\r\n" + 
			"\r\n" + 
			"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
			"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" +
			"\r\n" + 
			"HEADERDIR=$(EPOCROOT)epoc32\\include\r\n" + 
			"HEADERFILENAME=$(HEADERDIR)\\Birthdays_aif.mbg\r\n" + 
			"\r\n" + 
			"ICONDIR=..\\gfx\r\n" + 
			"\r\n" + 
			"do_nothing :\r\n" + 
			"	@rem do_nothing\r\n" + 
			"\r\n" + 
			"MAKMAKE : do_nothing\r\n" + 
			"\r\n" + 
			"BLD : do_nothing\r\n" + 
			"\r\n" + 
			"CLEAN : do_nothing\r\n" + 
			"\r\n" + 
			"LIB : do_nothing\r\n" + 
			"\r\n" + 
			"CLEANLIB : do_nothing\r\n" + 
			"\r\n" + 
			"RESOURCE :	\r\n" + 
			"	mifconv $(ICONTARGETFILENAME) /h$(HEADERFILENAME) \\\r\n" + 
			"		/c32 $(ICONDIR)\\" + PROJECT_ICON_PATH.lastSegment() + "\r\n" + 
			"		\r\n" + 
			"FREEZE : do_nothing\r\n" + 
			"\r\n" + 
			"SAVESPACE : do_nothing\r\n" + 
			"\r\n" + 
			"RELEASABLES :\r\n" + 
			"	@echo $(ICONTARGETFILENAME)\r\n" + 
			"\r\n" + 
			"FINAL : do_nothing\r\n" + 
			"\r\n" + 
			"";
	private static final String IMAGE2_MAKEFILE = 
			"ifeq (WINS,$(findstring WINS, $(PLATFORM)))\r\n" + 
			"ZDIR=\\epoc32\\release\\$(PLATFORM)\\$(CFG)\\Z\r\n" + 
			"else\r\n" + 
			"ZDIR=\\epoc32\\data\\z\r\n" + 
			"endif\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"TARGETDIR=$(ZDIR)\\resource\\apps\r\n" + 
			"ICONTARGETFILENAME=$(TARGETDIR)\\Birthdays_aif.mif\r\n" +
			"\r\n" + 
			"HEADERDIR=\\epoc32\\include\r\n" + 
			"HEADERFILENAME=$(HEADERDIR)\\Birthdays_aif.mbg\r\n" + 
			"\r\n" + 
			"ICONDIR=..\\gfx\r\n" + 
			"\r\n" + 
			"do_nothing :\r\n" + 
			"	@rem do_nothing\r\n" + 
			"\r\n" + 
			"MAKMAKE : do_nothing\r\n" + 
			"\r\n" + 
			"BLD : do_nothing\r\n" + 
			"\r\n" + 
			"CLEAN : do_nothing\r\n" + 
			"\r\n" + 
			"LIB : do_nothing\r\n" + 
			"\r\n" + 
			"CLEANLIB : do_nothing\r\n" + 
			"\r\n" + 
			"RESOURCE :	\r\n" + 
			"	mifconv $(ICONTARGETFILENAME) /h$(HEADERFILENAME) \\\r\n" + 
			"		/c32 " + STD_ICON_PATH + 
			"		/c8,8 " + STD_BITMAP_PATH + " "+ STD_BITMAP_MASK_PATH + "\r\n" + 
			"		\r\n" + 
			"FREEZE : do_nothing\r\n" + 
			"\r\n" + 
			"SAVESPACE : do_nothing\r\n" + 
			"\r\n" + 
			"RELEASABLES :\r\n" + 
			"	@echo $(ICONTARGETFILENAME)\r\n" + 
			"\r\n" + 
			"FINAL : do_nothing\r\n" + 
			"\r\n"; 
			
	
	private IPath epocRoot;
	private IProject project1;
	private IPath project1Root;
	private IProject project2;
	private IPath project2Root;
	private File tmpDir;
	private File epocDir;
	private IImageMakefileModel makefilemodel1a;
	private IImageMakefileModel makefilemodel1b;
	private IImageMakefileModel makefilemodel2a;
	private IImageMakefileModel makefilemodel2b;
	private DefaultImageMakefileViewConfiguration defaultConfiguration1;
	private DefaultImageMakefileViewConfiguration defaultConfiguration2;
	private DefaultImageMakefileViewConfiguration allConfiguration1;
	private DefaultImageMakefileViewConfiguration allConfiguration2;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tmpDir = FileUtils.getTemporaryDirectory();
		epocDir = new File(tmpDir, "epocroot");
		epocDir.mkdir();
		epocRoot = new Path(epocDir.getAbsolutePath());
		
		// setup standard images and directories
		File iconDir = new File(epocDir, "s60/icons");
		iconDir.mkdirs();
		new File(iconDir, STD_ICON_PATH.toString()).createNewFile();
		
		File bitmapDir = new File(epocDir, "s60/bitmaps2");
		bitmapDir.mkdirs();
		new File(bitmapDir, STD_BITMAP_PATH.toString()).createNewFile();
		new File(bitmapDir, STD_BITMAP_MASK_PATH.toOSString()).createNewFile();
		
		// non-imported project
		project1 = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT1_NAME);
		if (project1.exists())
			project1.delete(true, null);
		project1.create(null);
		project1Root = project1.getRawLocation();
		if (project1Root == null)
			project1Root = project1.getLocation();
		//System.out.println("project1: " + project1Root);
		project1.open(null);
		
		// imported project
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

		// create makefiles and images
		project1.getFolder("gfx").create(true, true, new NullProgressMonitor());
		createFile(project1, IMAGE1_MAKEFILE_PATH, IMAGE1_MAKEFILE);
		createFile(project1, IMAGE2_MAKEFILE_PATH, IMAGE2_MAKEFILE);
		createFile(project1, PROJECT_ICON_PATH, null);

		project2.getFolder("gfx").create(true, true, new NullProgressMonitor());
		createFile(project2, IMAGE1_MAKEFILE_PATH, IMAGE1_MAKEFILE);
		createFile(project2, IMAGE2_MAKEFILE_PATH, IMAGE2_MAKEFILE);
		createFile(project2, PROJECT_ICON_PATH, null);
		
		
		makefilemodel1a = EpocEnginePlugin.getImageMakefileModelProvider().getSharedModel(
				new Path(PROJECT1_NAME).append(IMAGE1_MAKEFILE_PATH));
		makefilemodel1b = EpocEnginePlugin.getImageMakefileModelProvider().getSharedModel(
				new Path(PROJECT1_NAME).append(IMAGE2_MAKEFILE_PATH));
		makefilemodel2a = EpocEnginePlugin.getImageMakefileModelProvider().getSharedModel(
				new Path(PROJECT2_NAME).append(IMAGE1_MAKEFILE_PATH));
		makefilemodel2b = EpocEnginePlugin.getImageMakefileModelProvider().getSharedModel(
				new Path(PROJECT2_NAME).append(IMAGE2_MAKEFILE_PATH));
		assertNotNull(makefilemodel1a);
		assertNotNull(makefilemodel1b);
		assertNotNull(makefilemodel2a);
		assertNotNull(makefilemodel2b);
		
		defaultConfiguration1 = new DefaultImageMakefileViewConfiguration(
				project1, null, new AcceptedNodesViewFilter());
		defaultConfiguration2 = new DefaultImageMakefileViewConfiguration(
				project2, null, new AcceptedNodesViewFilter());
		allConfiguration1 = new DefaultImageMakefileViewConfiguration(
				project1, null, new AllNodesViewFilter());
		allConfiguration2 = new DefaultImageMakefileViewConfiguration(
				project2, null, new AllNodesViewFilter());
	}
	
	/**
	 */
	private void createFile(IProject project, IPath path, String contents) throws CoreException {
		IFile file = project.getFile(path);
		file.create(new ByteArrayInputStream(
				contents != null ? contents.getBytes() : new byte[0]), 
				true, new NullProgressMonitor());
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		EpocEnginePlugin.getImageMakefileModelProvider().releaseSharedModel(makefilemodel1a);
		EpocEnginePlugin.getImageMakefileModelProvider().releaseSharedModel(makefilemodel1b);
		EpocEnginePlugin.getImageMakefileModelProvider().releaseSharedModel(makefilemodel2a);
		EpocEnginePlugin.getImageMakefileModelProvider().releaseSharedModel(makefilemodel2b);
		project1.delete(true, null);
		project2.delete(true, null);
	}
	
	public void testBasic() {
		// assert no crash, etc.
		IImageMakefileView view = makefilemodel1a.createView(defaultConfiguration1);
		new ImageMakefileViewPathHelper(view, new IPath[] { epocRoot });
		view.dispose();
		view = makefilemodel1a.createView(allConfiguration1);
		new ImageMakefileViewPathHelper(view, new IPath[] { epocRoot });
		view.dispose();
	}

	public void testLookupImages() {
		__testLookupImages(project1, new Path("gfx"), makefilemodel1a, defaultConfiguration1);
		__testLookupImages(project1, new Path("gfx"), makefilemodel1a, allConfiguration1);
		__testLookupImages(project1, new Path(""), makefilemodel1b, defaultConfiguration1);
		__testLookupImages(project1, new Path(""), makefilemodel1b, allConfiguration1);
		__testLookupImages(project2, new Path("gfx"), makefilemodel2a, defaultConfiguration2);
		__testLookupImages(project2, new Path("gfx"), makefilemodel2a, allConfiguration2);
		__testLookupImages(project2, new Path(""), makefilemodel2b, defaultConfiguration2);
		__testLookupImages(project2, new Path(""), makefilemodel2b, allConfiguration2);
	}

	/**
	 * @param project12
	 * @param defaultConfiguration12
	 */
	private void __testLookupImages(IProject project, IPath makefileRelPath, IImageMakefileModel model, IViewConfiguration configuration) {
		IImageMakefileView view = model.createView(configuration);
		ImageMakefileViewPathHelper helper = new ImageMakefileViewPathHelper(view, new IPath[] { epocRoot });
		IPath result;
		
		result = helper.findCandidateImagePath(PROJECT_ICON_PATH);
		assertNotNull(result);
		assertEquals(PROJECT_ICON_PATH, result);

		// this is how it will look in IMultiImageSource since it looks relative
		result = helper.findCandidateImagePath(makefileRelPath.append(STD_ICON_PATH));
		assertNotNull(result);
		assertEquals(epocRoot.append("s60/icons").append(STD_ICON_PATH), result);

		result = helper.findCandidateImagePath(makefileRelPath.append(STD_BITMAP_PATH));
		assertNotNull(result);
		assertEquals(epocRoot.append("s60/bitmaps2").append(STD_BITMAP_PATH), result);

		result = helper.findCandidateImagePath(makefileRelPath.append(STD_BITMAP_MASK_PATH));
		assertNotNull(result);
		assertEquals(epocRoot.append("s60/bitmaps2").append(STD_BITMAP_MASK_PATH), result);

		// not makefile-relative, so will not match
		if (makefileRelPath.segmentCount() > 0) {
			result = helper.findCandidateImagePath(STD_ICON_PATH);
			assertNotNull(result);
			assertEquals(STD_ICON_PATH, result);
	
			result = helper.findCandidateImagePath(STD_BITMAP_PATH);
			assertNotNull(result);
			assertEquals(STD_BITMAP_PATH, result);
	
			result = helper.findCandidateImagePath(STD_BITMAP_MASK_PATH);
			assertNotNull(result);
			assertEquals(STD_BITMAP_MASK_PATH, result);
		} else {
			// here, the makefile is at project root anyway, so these are indistinguishable
			result = helper.findCandidateImagePath(STD_ICON_PATH);
			assertNotNull(result);
			assertEquals(epocRoot.append("s60/icons").append(STD_ICON_PATH), result);
	
			result = helper.findCandidateImagePath(STD_BITMAP_PATH);
			assertNotNull(result);
			assertEquals(epocRoot.append("s60/bitmaps2").append(STD_BITMAP_PATH), result);
	
			result = helper.findCandidateImagePath(STD_BITMAP_MASK_PATH);
			assertNotNull(result);
			assertEquals(epocRoot.append("s60/bitmaps2").append(STD_BITMAP_MASK_PATH), result);
			
		}
		
		// test missing extension
		result = helper.findCandidateImagePath(makefileRelPath.append(STD_ICON_PATH).removeFileExtension());
		assertNotNull(result);
		assertEquals(epocRoot.append("s60/icons").append(STD_ICON_PATH), result);

		result = helper.findCandidateImagePath(makefileRelPath.append(STD_BITMAP_PATH).removeFileExtension());
		assertNotNull(result);
		assertEquals(epocRoot.append("s60/bitmaps2").append(STD_BITMAP_PATH), result);

		result = helper.findCandidateImagePath(makefileRelPath.append(STD_BITMAP_MASK_PATH).removeFileExtension());
		assertNotNull(result);
		assertEquals(epocRoot.append("s60/bitmaps2").append(STD_BITMAP_MASK_PATH), result);
		
		
		result = helper.findCandidateImagePath(new Path("dummy.bmp"));
		assertNotNull(result);
		assertEquals(new Path("dummy.bmp"), result);
	
		result = helper.findCandidateImagePath(new Path("gfx/dummy.bmp"));
		assertNotNull(result);
		assertEquals(new Path("gfx/dummy.bmp"), result);

		result = helper.findCandidateImagePath(new Path("c:/non/existent/dummy.bmp"));
		assertNotNull(result);
		assertEquals(new Path("c:/non/existent/dummy.bmp"), result);

		view.dispose();

	}
	
	public void testResolveImages() {
		__testResolveImages(project1, new Path("gfx"), makefilemodel1a, defaultConfiguration1);
		__testResolveImages(project1, new Path("gfx"), makefilemodel1a, allConfiguration1);
		__testResolveImages(project1, new Path(""), makefilemodel1b, defaultConfiguration1);
		__testResolveImages(project1, new Path(""), makefilemodel1b, allConfiguration1);
		__testResolveImages(project2, new Path("gfx"), makefilemodel2a, defaultConfiguration2);
		__testResolveImages(project2, new Path("gfx"), makefilemodel2a, allConfiguration2);
		__testResolveImages(project2, new Path(""), makefilemodel2b, defaultConfiguration2);
		__testResolveImages(project2, new Path(""), makefilemodel2b, allConfiguration2);
	}

	/**
	 * @param project12
	 * @param defaultConfiguration12
	 */
	private void __testResolveImages(IProject project, IPath makefileRelPath, IImageMakefileModel model, IViewConfiguration configuration) {
		IImageMakefileView view = model.createView(configuration);
		ImageMakefileViewPathHelper helper = new ImageMakefileViewPathHelper(view, new IPath[] { epocRoot });
		IPath result;
		
		result = helper.simplifyStandardImagePath(PROJECT_ICON_PATH);
		assertNotNull(result);
		assertEquals(PROJECT_ICON_PATH, result);

		result = helper.simplifyStandardImagePath(epocRoot.append("s60/icons").append(STD_ICON_PATH));
		assertNotNull(result);
		assertEquals(makefileRelPath.append(STD_ICON_PATH), result);

		result = helper.simplifyStandardImagePath(epocRoot.append("s60/bitmaps2").append(STD_BITMAP_PATH));
		assertNotNull(result);
		assertEquals(makefileRelPath.append(STD_BITMAP_PATH), result);

		result = helper.simplifyStandardImagePath(epocRoot.append("s60/bitmaps2").append(STD_BITMAP_MASK_PATH));
		assertNotNull(result);
		assertEquals(makefileRelPath.append(STD_BITMAP_MASK_PATH), result);

		result = helper.simplifyStandardImagePath(new Path("dummy.bmp"));
		assertNotNull(result);
		assertEquals(new Path("dummy.bmp"), result);
		
		result = helper.simplifyStandardImagePath(new Path("c:/non/existent/dummy.bmp"));
		assertNotNull(result);
		assertEquals(new Path("c:/non/existent/dummy.bmp"), result);

		view.dispose();

	}
}
