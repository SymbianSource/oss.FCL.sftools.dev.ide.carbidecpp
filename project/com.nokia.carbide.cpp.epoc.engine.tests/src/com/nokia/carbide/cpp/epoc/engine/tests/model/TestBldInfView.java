/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference.EMakeEngine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBldInfView extends BaseBldInfViewTest {

	
	public void testPlatformsParsing() throws Exception {
		makeModel("PRJ_PLATFORMS\nwinscw armv5\n");

		IBldInfView view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);

		match(new String[] { "winscw", "armv5" }, view.getPlatforms());
		match(new String[] { "winscw", "armv5" }, view.getData().getPlatforms());
		
		view.dispose();
		model.dispose();

		////
		makeModel("PRJ_PLATFORMS\ndefault -armv5\n");

		view = getView(config);
		assertNotNull(view);

		match(new String[] { "default", "-armv5" }, view.getPlatforms());
		match(new String[] { "default", "-armv5" }, view.getData().getPlatforms());
		view.dispose();
		model.dispose();

	}

	public void testPlatformsChanging1() throws Exception {
		makeModel("PRJ_PLATFORMS\nDEFAULTS\n");

		IBldInfView view = getView(config);
		assertNotNull(view);

		List<String> platforms = view.getPlatforms();
		match(new String[] { "DEFAULTS" }, platforms);

		platforms.remove(0);
		platforms.add("WINSCW");
		platforms.add("ARMV5");
		
		commitTest(view, "PRJ_PLATFORMS\nWINSCW ARMV5\n");
		
		///
		
		platforms = view.getPlatforms();
		platforms.remove(1);
		commitTest(view, "PRJ_PLATFORMS\nWINSCW\n");
		
		///
		view.getPlatforms().clear();
		commitTest(view, "PRJ_PLATFORMS\n\n");
		
		view.dispose();
		model.dispose();

	}

	public void testMmpfilesParsing1() throws Exception {
		makeModel("PRJ_MMPFILES\n"+
				"basic.mmp TIDY\n"+
				"another.mmp Build_as_ARM\n"+
				"PRJ_TESTMMPFILES\n"+
				"test.mmp MANUAL\n");

		IBldInfView view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);

		_testMmpfilesParsing1(view);
		_testMmpfilesParsing1(view.getData());
		
		view.dispose();
		model.dispose();

	}

	private void _testMmpfilesParsing1(IBldInfData bldInfData) {
		List<IMakMakeReference> maks = bldInfData.getMakMakeReferences();
		assertEquals(2, maks.size());
		
		IMMPReference mmp = (IMMPReference) maks.get(0);
		assertEquals(new Path("group/basic.mmp"), mmp.getPath());
		assertFalse(mmp.isBuildAsArm());
		assertFalse(mmp.isManual());
		assertFalse(mmp.isSupport());
		assertTrue(mmp.isTidy());

		mmp = (IMMPReference) maks.get(1);
		assertEquals(new Path("group/another.mmp"), mmp.getPath());
		assertTrue(mmp.isBuildAsArm());
		assertFalse(mmp.isManual());
		assertFalse(mmp.isSupport());
		assertFalse(mmp.isTidy());

		
		////
		maks = bldInfData.getTestMakMakeReferences();
		assertEquals(1, maks.size());
		
		mmp = (IMMPReference) maks.get(0);
		assertEquals(new Path("group/test.mmp"), mmp.getPath());
		assertFalse(mmp.isBuildAsArm());
		assertTrue(mmp.isManual());
		assertFalse(mmp.isSupport());
		assertFalse(mmp.isTidy());
		
	////
		
		IMMPReference[] mmps = bldInfData.getAllMMPReferences();
		assertEquals(3, mmps.length);
		assertEquals(bldInfData.getMakMakeReferences().get(0), mmps[0]);
		assertEquals(bldInfData.getMakMakeReferences().get(1), mmps[1]);
		assertEquals(maks.get(0), mmps[2]);
	}

	public void testMmpfilesParsing2() throws Exception {
		// not a normally valid case, but used to validate
		// that these are all considered equal and all expose .mmp extensions
		makeModel("prj_mmpfiles\n"+
				"basic TIDY\n"+
				"baSic.MMP TIDY\n"+
				"BasIC.kkk TIDY\n");
		IBldInfView view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);

		_testMmpfilesParsing2(view);
		_testMmpfilesParsing2(view.getData());
	}

	private void _testMmpfilesParsing2(IBldInfData bldInfData) {
		IMMPReference[] mmps = bldInfData.getAllMMPReferences();
		assertEquals(3, mmps.length);
		assertEquals(new Path("group/basic.mmp"), mmps[0].getPath());
		assertEquals(new Path("group/baSic.mmp"), mmps[1].getPath());
		assertEquals(new Path("group/BasIC.mmp"), mmps[2].getPath());
		assertEquals(mmps[0], mmps[1]);
		assertEquals(mmps[0], mmps[2]);
	}
	
	public void testMakefilesParsing1() throws Exception {
		makeModel("PRJ_mmpFILES\n"+
				"makefile ..\\simple.mk build_as_arm\n"+
				"another.mmp Build_as_ARM\n"+
				"gnumakefile Icons_scalable_dc.mk tidy\n"+
				"PRJ_TESTMMPFILES\n"+
				"#ifdef WINSCW\n"+
				"nmakefile msbuild.make\n"+
				"#endif\n"
				);

		macros = new ArrayList<IDefine>();
		macros.add(DefineFactory.createDefine("WINSCW"));

		IBldInfView view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);

		_testMakefilesParsing1(view);
		_testMakefilesParsing1(view.getData());
		
		view.dispose();
		model.dispose();

	}

	private void _testMakefilesParsing1(IBldInfData bldInfData) {
		List<IMakMakeReference> maks = bldInfData.getMakMakeReferences();
		assertEquals(3, maks.size());
		
		
		IMakefileReference makefile;
		IMMPReference mmp;
		
		makefile = (IMakefileReference) maks.get(0);
		assertEquals(EMakeEngine.MAKEFILE, makefile.getMakeEngine());
		assertEquals(new Path("simple.mk"), makefile.getPath());
		assertTrue(makefile.isBuildAsArm());
		assertFalse(makefile.isTidy());
		
		mmp = (IMMPReference) maks.get(1);
		assertEquals(new Path("group/another.mmp"), mmp.getPath());
		assertTrue(mmp.isBuildAsArm());

		makefile = (IMakefileReference) maks.get(2);
		assertEquals(new Path("group/Icons_scalable_dc.mk"), makefile.getPath());
		assertEquals(EMakeEngine.GNUMAKEFILE, makefile.getMakeEngine());
		assertTrue(makefile.isTidy());
		assertFalse(makefile.isBuildAsArm());


		///
		
		List<IMakMakeReference> testmaks = bldInfData.getTestMakMakeReferences();
		assertEquals(1, testmaks.size());
		
		makefile = (IMakefileReference) testmaks.get(0);
		assertEquals(new Path("group/msbuild.make"), makefile.getPath());
		assertEquals(EMakeEngine.NMAKEFILE, makefile.getMakeEngine());
		assertFalse(makefile.isTidy());
		assertFalse(makefile.isBuildAsArm());
		///
		
		IMMPReference[] mmps = bldInfData.getAllMMPReferences();
		assertEquals(1, mmps.length);
		assertEquals(maks.get(1), mmps[0]);

		IMakefileReference[] makefiles = bldInfData.getAllMakefileReferences();
		assertEquals(3, makefiles.length);
		assertEquals(maks.get(0), makefiles[0]);
		assertEquals(maks.get(2), makefiles[1]);
		assertEquals(testmaks.get(0), makefiles[2]);
		
		assertEquals(4, bldInfData.getAllMakMakeReferences().length);
	}
	
	public void testMmpfilesChangingCond() throws Exception {
		makeModel("PRJ_MMPFILES\n"+
				"#ifdef MYTARGET\n"+
				"foo.mmp\n"+
				"#endif\n");

		IBldInfView view = getView(config);
		assertNotNull(view);

		// don't remove apparently empty statements
		commitTest(view, "PRJ_MMPFILES\n"+
				"#ifdef MYTARGET\n"+
				"foo.mmp\n"+
				"#endif\n");
	}

	public void testMmpfilesChanging() throws Exception {
		makeModel("PRJ_MMPFILES\n");

		IBldInfView view = getView(config);
		assertNotNull(view);

		IMMPReference mmp = view.createMMPReference();
		assertFalse(mmp.isValid());
		
		// don't emit invalid entries
		view.getMakMakeReferences().add(mmp);
		
		commitTest(view, "PRJ_MMPFILES\n");
		
		///
		
		mmp = view.createMMPReference();
		
		mmp.setPath(new Path("group/program.mmp"));
		view.getMakMakeReferences().add(mmp);
		
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"program.mmp\n");
		
		//
		
		mmp = view.createMMPReference();
		mmp.setPath(new Path("another.mmp"));
		mmp.setBuildAsArm(true);
		mmp.setManual(false);
		assertTrue(mmp.isBuildAsArm());
		assertFalse(mmp.isManual());
		view.getMakMakeReferences().add(mmp);

		// bug 4726: new format is fwd slash
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"program.mmp\n"+
				"../another.mmp BUILD_AS_ARM\n");
		
		//
		
		view.getMakMakeReferences().remove(0);
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"../another.mmp BUILD_AS_ARM\n");

		///
		
		IMakefileReference makefile = view.createMakefileReference();
		view.getMakMakeReferences().add(makefile);

		// no empty nodes
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"../another.mmp BUILD_AS_ARM\n");

		///
		
		makefile = view.createMakefileReference();
		view.getMakMakeReferences().add(makefile);
		makefile.setMakeEngine(EMakeEngine.GNUMAKEFILE);
		makefile.setPath(new Path("gfx/MakeGraphics.makefile"));

		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"../another.mmp BUILD_AS_ARM\n"+
				"gnumakefile ../gfx/MakeGraphics.makefile\n");

		
		view.dispose();
		model.dispose();

	}
	
	public void testMmpfilesChanging2() throws Exception {
		makeModel("PRJ_MMPFILES\n");

		IBldInfView view = getView(config);
		assertNotNull(view);

		IMMPReference mmp = view.createMMPReference();
		mmp = view.createMMPReference();
		
		// ensure user intent preserved
		mmp.setPath(new Path("group/program"));
		view.getMakMakeReferences().add(mmp);
		
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"program\n");

		// ensure change matches existing mmp and results in no change
		mmp = view.getAllMMPReferences()[0];
		mmp.setPath(new Path("group/program.mmp"));

		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"program\n");

		// ensure matches existing mmp
		mmp = view.getAllMMPReferences()[0];
		mmp.setManual(true);
		
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"program MANUAL\n");

		// ensure matches...
		view.getMakMakeReferences().remove(view.getAllMMPReferences()[0]);
		
		// leave empty sections
		commitTest(view, 
				"PRJ_MMPFILES\n");

		
		view.dispose();
		model.dispose();

	}
	
	public void testExportsParsing() {
		makeModel("PRJ_EXPORTS\n"+
				"../src/MyFile.txt\n"+
				"// user mistake below, should be full path\n"+
				"..\\gfx\\myfile.mbm sys\\resources\\MyApp\\myfile.mbm\n"+
				"..\\group\\here.exe c:\\sys\\bin\\virus.exe\n"+
				":zip v28archive.zip \\epoc32\\releases\\winscw\\patches\n");

		IBldInfView view = getView(config);
		assertNotNull(view);
		checkNoProblems(view);

		_testExportsParsing(view);
		_testExportsParsing(view.getData());

	}

	private void _testExportsParsing(IBldInfData bldInfData) {
		List<IExport> exports = bldInfData.getExports();
		assertEquals(4, exports.size());
		
		IExport exp = exports.get(0);
		assertEquals(new Path("src/MyFile.txt"), exp.getSourcePath());
		// implicit
		assertEquals(new Path("/epoc32/include/MyFile.txt"), exp.getTargetPath());
		assertFalse(exp.isZipped());
		
		exp = exports.get(1);
		assertEquals(new Path("gfx/myfile.mbm"), exp.getSourcePath());
		// user mistake
		assertEquals(new Path("/epoc32/include/sys/resources/MyApp/myfile.mbm"), exp.getTargetPath());
		assertFalse(exp.isZipped());
		
		exp = exports.get(2);
		assertEquals(new Path("group/here.exe"), exp.getSourcePath());
		assertEquals(PathUtils.createPath("c:/sys/bin/virus.exe"), exp.getTargetPath());
		assertFalse(exp.isZipped());

		exp = exports.get(3);
		assertEquals(new Path("group/v28archive.zip"), exp.getSourcePath());
		assertEquals(new Path("/epoc32/releases/winscw/patches"), exp.getTargetPath());
		assertTrue(exp.isZipped());
	}
	
	public void testExportsChanging() {
		makeModel("// my empty bld.inf\n"+
				"PRJ_PLATFORMS DEFAULTS\n"+
				"PRJ_MMPFILES\n"+
				"PRJ_EXPORTS\n");
		IBldInfView view = getView(config);
		assertNotNull(view);

		IExport exp = view.createExport();
		exp.setSourcePath(new Path("src/MyFile.txt"));
		assertEquals(new Path("src/MyFile.txt"), exp.getSourcePath());
		view.getExports().add(exp);
		
		exp = view.createExport();
		exp.setSourcePath(new Path("gfx/myfile.mbm"));
		exp.setTargetPath(new Path("/sys/resources/MyApp/myfile.mbm"));
		assertEquals(new Path("/sys/resources/MyApp/myfile.mbm"), exp.getTargetPath());
		view.getExports().add(exp);

		exp = view.createExport();
		exp.setSourcePath(new Path("inc/header2.h"));
		exp.setTargetPath(new Path("/epoc32/include/header2.h"));
		view.getExports().add(exp);

		exp = view.createExport();
		exp.setSourcePath(new Path("group/here.exe"));
		exp.setTargetPath(PathUtils.createPath("c:/sys/bin/virus.exe"));
		assertFalse(exp.isZipped());
		view.getExports().add(exp);
		
		exp = view.createExport();
		exp.setSourcePath(new Path("group/v28archive.zip"));
		exp.setTargetPath(new Path("/epoc32/releases/winscw/patches"));
		exp.setZipped(true);
		assertTrue(exp.isZipped());
		view.getExports().add(exp);

		
		commitTest(view, "// my empty bld.inf\n"+
				"PRJ_PLATFORMS DEFAULTS\n"+
				"PRJ_MMPFILES\n"+ 
				"PRJ_EXPORTS\n"+
				"../src/MyFile.txt\n"+
				"../gfx/myfile.mbm /sys/resources/MyApp/myfile.mbm\n"+
				"../inc/header2.h\n"+ // implicit path
				"here.exe c:\\sys\\bin\\virus.exe\n"+  // backslash needed because of use of DOS 'copy' command
				":zip v28archive.zip /epoc32/releases/winscw/patches\n");

		///
		
		view.getExports().get(0).setZipped(true);
		view.getExports().get(0).setTargetPath(new Path("/epoc32/include"));
		commitTest(view, "// my empty bld.inf\n"+
				"PRJ_PLATFORMS DEFAULTS\n"+
				"PRJ_MMPFILES\n"+ 
				"PRJ_EXPORTS\n"+
				":zip ../src/MyFile.txt\n"+
				"../gfx/myfile.mbm /sys/resources/MyApp/myfile.mbm\n"+
				"../inc/header2.h\n"+ // implicit path
				"here.exe c:\\sys\\bin\\virus.exe\n"+
				":zip v28archive.zip /epoc32/releases/winscw/patches\n");
		
		view.dispose();
		model.dispose();
	}
	
	public void testExportsChanging2() {
		makeModel("PRJ_EXPORTS\n"+
				"../src/MyFile.txt\n"+
				"// user mistake below, should be full path\n"+
				"..\\gfx\\myfile.mbm sys\\resources\\MyApp\\myfile.mbm\n"+
				"..\\group\\here.exe c:\\sys\\bin\\virus.exe\n"+
				"// unchanged entry keeps comment\n"+
				":zip v28archive.zip \\epoc32\\releases\\winscw\\patches\n");

		IBldInfView view = getView(config);
		assertNotNull(view);
	
		view.getExports().get(1).setTargetPath(new Path("/sys/resources/MyApp/myfile.mbm"));
		
		commitTest(view,
				"PRJ_EXPORTS\n"+
				"../src/MyFile.txt\n"+
				"// user mistake below, should be full path\n"+
				"..\\gfx\\myfile.mbm \\sys\\resources\\MyApp\\myfile.mbm\n"+ // keeps predom slash fmt
				"..\\group\\here.exe c:\\sys\\bin\\virus.exe\n"+
				"// unchanged entry keeps comment\n"+
				":zip v28archive.zip \\epoc32\\releases\\winscw\\patches\n");
		view.dispose();
		model.dispose();
	}

	public void testExportsChanging3() {
		makeModel("PRJ_EXPORTS\n"+
				"../src/MyFile.txt\n"+
				"..\\gfx\\myfile.mbm\n"+
				":zip v28archive.zip\n");

		IBldInfView view = getView(config);
		assertNotNull(view);
	
		assertEquals(new Path("/epoc32/include/MyFile.txt"), view.getExports().get(0).getTargetPath());
		assertEquals(new Path("/epoc32/include/myfile.mbm"), view.getExports().get(1).getTargetPath());
		commitTest(view,
				"PRJ_EXPORTS\n"+
				"../src/MyFile.txt\n"+
				"..\\gfx\\myfile.mbm\n"+
				":zip v28archive.zip\n");
		view.dispose();
		model.dispose();
	}
	public void testProblems() {
		makeModel("PRJ_UNKNOWN\n"+
				"../src/MyFile.txt\n"+
				"PRJ_PLATFORMS eee\n");

		IBldInfView view = getView(config);
		assertNotNull(view);
		assertEquals(2, view.getMessages().length);
	
		commitTest(view,"PRJ_UNKNOWN\n"+
				"../src/MyFile.txt\n"+
				"PRJ_PLATFORMS eee\n");
		assertEquals(2, view.getMessages().length);
		
		view.getPlatforms().add("ooo");

		
		commitTest(view,"PRJ_UNKNOWN\n"+
				"../src/MyFile.txt\n"+
				"PRJ_PLATFORMS eee ooo\n");
		assertEquals(2, view.getMessages().length);
	}
	
	private static IPath insideSDKPath;
	static {
		if (HostOS.IS_WIN32) {
			insideSDKPath = new Path("c:\\symbian\\9.1\\S60_3rd\\S60Ex\\Hello\\group\\bld.inf");
		} else {
			insideSDKPath = new Path("/home/user/symbian/9.1/S60_3rd/S60Ex/Hello/group/bld.inf");
		}
	}
	public void testRootProject() {
		// make sure we handle project root correctly when it's the root directory
		for (int i = 0; i < 2; i++){
			parserConfig.projectPath = i == 0 ? new Path("c:\\") : new Path("c:");
			this.path = insideSDKPath;
			makeModel("PRJ_MMPFILES\n"+
					"test.mmp\n");
			
			IBldInfView view =model.createView(config);
			_testRootProject(view);
			_testRootProject(view.getData());
		}
	}

	private void _testRootProject(IBldInfData bldInfData) {
		IMMPReference mmp = bldInfData.getAllMMPReferences()[0];
		// important to be relative and with no drive
		if (HostOS.IS_WIN32)
			assertEquals(insideSDKPath.makeRelative().setDevice(null).removeLastSegments(1).append("test.mmp"), mmp.getPath());
		else
			assertEquals(insideSDKPath.removeLastSegments(1).append("test.mmp"), mmp.getPath());
	}
	
	/**
	 * The base directory changes as files are #included
	 *
	 */
	private static IPath basePath;
	public void testBaseDirectory() {
		if (HostOS.IS_WIN32)
			basePath = new Path("c:/test/");
		else
			basePath = new Path("/tmp/test/");
		parserConfig.getFilesystem().put(basePath.append("bld.inf").toOSString(), 
				"PRJ_MMPFILES\n"+
				"base.mmp\n"+
				"gnumakefile sub\\base.mk\n"+
				"PRJ_EXPORTS\n"+
				"base.txt\n");

		parserConfig.getFilesystem().put(projectPath.append("utils").append("bld.inf").toOSString(),
				"PRJ_EXPORTS\n"+
				"utils.txt\n"+
				"PRJ_MMPFILES\n"+
				"utils.mmp\n"+
				"nmakefile ..\\utils.mk\n");

		makeModel(
				"PRJ_MMPFILES\n"+
				"first.mmp\n"+
				"#include \"" + basePath.toOSString() + "bld.inf\"\n"+
				"#include \"../utils/bld.inf\"\n"+
				"PRJ_MMPFILES\n"+
				"last.mmp\n" +
				"PRJ_EXPORTS\n"+
				"last.txt\n");

		IBldInfView view = model.createView(config);
		_testBaseDirectory(view);
		_testBaseDirectory(view.getData());
		
	}

	private void _testBaseDirectory(IBldInfData bldInfData) {
		assertEquals(6, bldInfData.getMakMakeReferences().size());
		
		assertEquals(new Path("group/first.mmp"), bldInfData.getMakMakeReferences().get(0).getPath());
		
		// note: should not be relative path when outside the project
		assertEquals(basePath.append("base.mmp"), bldInfData.getMakMakeReferences().get(1).getPath());
		assertEquals(basePath.append("sub/base.mk"), bldInfData.getMakMakeReferences().get(2).getPath());
		
		assertEquals(new Path("utils/utils.mmp"), bldInfData.getMakMakeReferences().get(3).getPath());
		assertEquals(new Path("utils.mk"), bldInfData.getMakMakeReferences().get(4).getPath());
		
		assertEquals(new Path("group/last.mmp"), bldInfData.getMakMakeReferences().get(5).getPath());

		assertEquals(3, bldInfData.getExports().size());
		assertEquals(basePath.append("base.txt"), bldInfData.getExports().get(0).getSourcePath());
		assertEquals(new Path("utils/utils.txt"), bldInfData.getExports().get(1).getSourcePath());
		assertEquals(new Path("group/last.txt"), bldInfData.getExports().get(2).getSourcePath());
	}
	
	public void testPreprocessorProblems() {
		String string = "PRJ_MMPFILES\n"+
						"#if 1\n"+
						"first.mmp\n"+
						"#else\n"+
						"#if\n";
		makeModel(string);

		IBldInfView view = model.createView(config);
		IASTProblemNode[] problemNodes = getProblems(view);
		assertEquals(1, problemNodes.length);
		checkMessages(problemNodes);
		
		assertEquals(1, view.getMakMakeReferences().size());

		// ensure no crash
		commitTest(view, string);
		
	}
	
	public void testIfNestingRecovery() {
		String string = "\n" + 
				"PRJ_PLATFORMS\n" + 
				"DEFAULT\n" + 
				"\n" + 
				"PRJ_EXPORTS\n" + 
				"\n" + 
				"#ifndef RD_EMAIL_DOMAIN_UTILS\n" + 
				"..\\Utils\\inc\\IMASettingsStorer.h \\epoc32\\include\\oem\\IMASettingsStorer.h\n" + 
				"\n" + 
				"PRJ_MMPFILES\n" + 
				"..\\utils\\group\\ImumUtils.mmp\n" + 
				"\n" + 
				"// ********** NEW INTERNAL API PART\n" + 
				"#else\n" + 
				"..\\Utils_newApi\\inc\\IMPICMDS.H \\epoc32\\include\\oem\\impicmds.h\n" + 
				"\n" + 
				"PRJ_MMPFILES\n" + 
				"..\\DomainApi\\group\\imumda.mmp\n" + 
				"\n" + 
				"#endif\n"; 
				
		makeModel(string);
		
		// normal case: macro not defined
		IBldInfView view = model.createView(config);
		checkNoProblems(view);
		testSourceRegions(view, true);
		
		assertEquals(1, view.getMakMakeReferences().size());
		assertEquals(1, view.getExports().size());
		
		commitTest(view, string);
		// SPN case: "all" filter
		//System.out.println("\\\\\\\\\\\\\\\\\\");
		view.dispose();
		view = model.createView(allConfig);
		testSourceRegions(view, true);
		
		// should not continue reading #else part as MMPS
		assertEquals(2, view.getMakMakeReferences().size());
		assertEquals(2, view.getExports().size());
		
		commitTest(view, string);
		
	}
	
	public void testIfNestingRecovery2() {
		String string = "\n" + 
				"PRJ_PLATFORMS\n" +
				"#ifdef RD_EMAIL_DOMAIN_UTILS\n" +
				"WINSCW\n"+
				"#endif\n"+
				"ARMV5\n"+
				"#if 1\n"+
				"PRJ_EXPORTS\n"+
				"..\\Utils\\inc\\IMASettingsStorer.h \\epoc32\\include\\oem\\IMASettingsStorer.h\n" +
				"#else\n"+
				"PRJ_MMPFILES\n"+
				"test1.mmp\n"+
				"	#if 1\n"+
				"	test2.mmp\n"+
				"	PRJ_EXPORTS\n"+
				"	foo.txt foo.txt\n"+
				"	#endif\n"+
				"test3.mmp\n"+ // yup, this is an export 
				"#endif\n"; 
				
		makeModel(string);
		
		// normal case: macro not defined
		IBldInfView view = model.createView(config);
		checkNoProblems(view);
		testSourceRegions(view, true);

		assertEquals(1, view.getPlatforms().size());
		assertEquals(0, view.getMakMakeReferences().size());
		assertEquals(1, view.getExports().size());
		
		commitTest(view, string);
		
		// SPN case: "all" filter
		view.dispose();
		
		view = model.createView(allConfig);
		testSourceRegions(view, true);

		assertEquals(2, view.getPlatforms().size());
		assertEquals(2, view.getMakMakeReferences().size());
		assertEquals(3, view.getExports().size());

		commitTest(view, string);

	}
	
	public void testIfNestingRecovery3() {
		String incl1 = "PRJ_PLATFORMS\n"+
		"DEFAULT\n"+
		"\n"+
		"PRJ_EXPORTS\n" +
		"foo1.txt foo1.txt\n"+
		"\n"+
		"PRJ_MMPFILES\n"+
		"\n"+
		"first.mmp\n"+
		"\n"+
		"#if 1\n"+
		"makefile icons1.mk\n"+
		"#else\n"+
		"makefile icons2.mk\n"+
		"#endif\n"+
		"PRJ_TESTMMPFILES\n"
		;
		String incl2 = "PRJ_PLATFORMS\n"+
		"\n"+
		"DEFAULT\n"+
		"\n"+
		"PRJ_MMPFILES\n"+
		"\n"+
		"second.mmp\n"+
		"PRJ_TESTMMPFILES\n";
		String string = "\n" + 
		"#if 1\n"+
				"#include \"inc1.h\"\n"+
				"#include \"inc2.h\"\n"+
				"#endif\n"+
				"PRJ_PLATFORMS\n"+
				""
		;
		
		parserConfig.getFilesystem().put("inc1.h", incl1);
		parserConfig.getFilesystem().put("inc2.h", incl2);
		makeModel(string);
		
		IBldInfView view = model.createView(allConfig);
		testSourceRegions(view, true);

		assertEquals(4, view.getMakMakeReferences().size());
		assertEquals(2, view.getPlatforms().size());
		assertEquals(1, view.getExports().size());
		
		commitTest(view, string);
		
	}
	
	public void testIfNestingRecovery4a() {
		// note lack of newline after PRJ_MMPFILES
		String inf = "PRJ_EXTENSIONS\r\n" + 
				"start		extension		base/genexec\r\n" + 
				"\r\n" + 
				"option		EXTRA_SRC_PATH		$(EXTENSION_ROOT)/kernel\r\n" + 
				"\r\n" + 
				"end\r\n" +
				"\r\n"+
				"PRJ_MMPFILES\r\n" + 
				"#if defined(GENERIC_MARM) || defined(WINS) || defined(GENERIC_X86)\r\n" + 
				"\r\n" + 
				"#if defined(GENERIC_MARM) && !defined(ARMCC)\r\n" + 
				"euser/epoc/egcc\r\n" + 
				"#endif\r\n" + 
				"\r\n" + 
				"euser/epoc/edllstub\r\n" + 
				"euser/edll\r\n" + 
				"euser/eexe\r\n" +
				"#endif\n";
		makeModel(inf);
		IBldInfView view = model.createView(allConfig);
		testSourceRegions(view, true);

		checkNoProblems(view);
		assertEquals(4, view.getMakMakeReferences().size());
		commitTest(view, inf);

	}

	public void testIfNestingRecovery4b() {
		// note extra newline after PRJ_MMPFILES
		String inf = "PRJ_EXTENSIONS\r\n" + 
				"start		extension		base/genexec\r\n" + 
				"\r\n" + 
				"option		EXTRA_SRC_PATH		$(EXTENSION_ROOT)/kernel\r\n" + 
				"\r\n" + 
				"end\r\n" +
				"\r\n"+
				"PRJ_MMPFILES\r\n" + 
				"\r\n"+
				"#if defined(GENERIC_MARM) || defined(WINS) || defined(GENERIC_X86)\r\n" + 
				"\r\n" + 
				"#if defined(GENERIC_MARM) && !defined(ARMCC)\r\n" + 
				"euser/epoc/egcc\r\n" + 
				"#endif\r\n" + 
				"\r\n" + 
				"euser/epoc/edllstub\r\n" + 
				"euser/edll\r\n" + 
				"euser/eexe\r\n" +
				"#endif\n";
		makeModel(inf);
		IBldInfView view = model.createView(allConfig);
		testSourceRegions(view, true);

		checkNoProblems(view);
		assertEquals(4, view.getMakMakeReferences().size());
		commitTest(view, inf);

	}
	
	/** These two test that PRJ_PLATFORMS properly has a newline or space
	 * between its token and its arguments, AND that the list is terminated
	 * by a newline.
	 *
	 */
	public void testAddToEmptyPlatforms() {
		makeModel("PRJ_PLATFORMS\n"+
				"\n"+
				"PRJ_MMPFILES\n"+
				"PRJ_EXPORTS\n");
		
		IBldInfView view = model.createView(allConfig);
		view.getPlatforms().add("WINSCW");
		view.getPlatforms().add("GCCE");
		
		IMMPReference mmp = view.createMMPReference();
		mmp.setPath(new Path("group/file.mmp"));
		view.getMakMakeReferences().add(mmp);
		
		// bug was a missing newline AND space
		commitTest(view,
				"PRJ_PLATFORMS\n"+
				"WINSCW GCCE\n"+
				"\n"+
				"PRJ_MMPFILES\n"+
				"file.mmp\n"+
				"PRJ_EXPORTS\n"+
				"");
								
	}
	/** These two test that PRJ_PLATFORMS properly has a newline or space
	 * between its token and its arguments, AND that the list is terminated
	 * by a newline.
	 *
	 */
	public void testAddToEmptyPlatforms2() {
		makeModel("PRJ_PLATFORMS\n"+
				"PRJ_MMPFILES\n"+
				"PRJ_EXPORTS\n");
		
		IBldInfView view = model.createView(allConfig);
		view.getPlatforms().add("WINSCW");
		view.getPlatforms().add("GCCE");

		IMMPReference mmp = view.createMMPReference();
		mmp.setPath(new Path("group/file.mmp"));
		view.getMakMakeReferences().add(mmp);

		// bug was a missing newline AND space
		commitTest(view,
				"PRJ_PLATFORMS\n"+
				"WINSCW GCCE\n"+
				"PRJ_MMPFILES\n"+
				"file.mmp\n"+
				"PRJ_EXPORTS\n");
								
	}
	
	public void testHugeMess() throws Exception {
		String spdiaText = 
				"#include <domain\\osextensions\\platform_paths.hrh>\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"..\\ControlInc\\SpdiaControl.h APP_LAYER_SDK_EXPORT_PATH(SpdiaControl.h)\r\n" + 
				"..\\inc\\sdm.hlp.hrh APP_LAYER_SDK_EXPORT_PATH(cshelp\\sdm.hlp.hrh)\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"\r\n" + 
				"\r\n" + 
				" \r\n" + 
				" #ifdef __SCALABLE_ICONS\r\n" + 
				"	gnumakefile icons_dc_scalable_speeddial.mk\r\n" + 
				"   gnumakefile icons_aif_scalable_dc_speeddial.mk\r\n" + 
				" #else\r\n" + 
				"	gnumakefile icons_dc_speeddial.mk\r\n" + 
				"   gnumakefile icons_aif_bitmaps_dc_speeddial.mk\r\n" + 
				" #endif\r\n" + 
				" \r\n" + 
				"gnumakefile icons_aif_scalable_speeddial.mk\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"SpdCtrl.mmp\r\n" + 
				"SpeedDial.mmp\r\n" + 
				"\r\n" + 
				"PRJ_TESTMMPFILES\r\n" + 
				"//..\\tsrc\\CVTSpeeddial.mmp  // CVT test case(s)\r\n" + 
				"//..\\TVoiceMailboxCallRemote\\group\\TVoiceMailboxCallRemote.mmp\r\n" + 
				"\r\n" + 
				"// End of File"; 
				
		String phonebookuiText =  
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"// phonebookui subsystem\r\n" + 
				"#include \"..\\Speeddial\\group\\bld.inf\"\r\n" +
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"\r\n" + 
				"PRJ_TESTMMPFILES\r\n" + 
				"\r\n" + 
				"PRJ_TESTEXPORTS\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"//  End of File  \r\n"; 
				
		Map<String, String> originalFiles = new HashMap<String, String>();
		originalFiles.put(new Path("../Speeddial/group/bld.inf").toOSString(), spdiaText);
		// this fails
		originalFiles.put("platform_paths.hrh", "\n"+"#ifndef MACRO\n"+"#define MACRO\n"+"\n"+"#endif\n"+"\n");
		// this passes
		//originalFiles.put("platform_paths.hrh", "#ifndef MACRO\n"+"#define MACRO\n"+"\n"+"#endif\n");
		/////originalFiles.put("platform_paths.hrh", loadFileText("data/s60/platform_paths.hrh"));
		/*
		originalFiles.put("data_caging_paths.hrh", loadFileText("data/s60/data_caging_paths.hrh"));
		originalFiles.put("bldvariant.hrh", loadFileText("data/s60/bldvariant.hrh"));
		originalFiles.put("defaultcaps.hrh", loadFileText("data/s60/defaultcaps.hrh"));
		originalFiles.put("features.hrh", loadFileText("data/s60/features.hrh"));
		originalFiles.put("Symbian_OS_v9.3.hrh", loadFileText("data/s60/Symbian_OS_v9.3.hrh"));
		originalFiles.put("ProductVariant.hrh", loadFileText("data/s60/ProductVariant.hrh"));
		originalFiles.put("PbkConfig.hrh", loadFileText("data/s60/PbkConfig.hrh"));
		*/
		parserConfig.getFilesystem().clear();
		for (Map.Entry<String, String> entry : originalFiles.entrySet()) {
			parserConfig.getFilesystem().put(entry.getKey(), entry.getValue());
		}
		makeModel(phonebookuiText);
		
		macros.add(DefineFactory.createDefine("__SCALABLE_ICONS"));
		
		IBldInfView view = model.createView(config);
		for (String s  : view.getPlatforms())
			assertEquals("DEFAULT", s);
		/*
		for (String p : view.getPlatforms()) {
			System.out.print(p + " ");
		}
		System.out.println();*/
		IMessage[] messages = view.getMessages();
		for (IMessage p : messages)
			System.out.println(p);
		assertEquals(0, messages.length);
		commitTest(view, phonebookuiText);
		//view.commit();

		for (Map.Entry<String, String> entry : originalFiles.entrySet()) {
			String curFile = parserConfig.getFilesystem().get(entry.getKey());
			assertEquals(entry.getValue(), curFile);
		}
		
		assertEquals(phonebookuiText, model.getDocument().get());

	}
	
	public void testAddNewMMP() {
		String text = 
			"PRJ_PLATFORMS\r\n" + 
			"DEFAULT\r\n" + 
			"\r\n" + 
			"PRJ_EXPORTS\r\n" + 
			"//..\\Common\\inc\\MPlayerConstants.h           \\epoc32\\include\\oem\\MPlayerConstants.h\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"\r\n" + 
			"#ifdef __ACTIVE_IDLE  \r\n" + 
			"gnumakefile ..\\ActiveIdlePlugin\\group\\PlayerPluginIcons.mk  \r\n" + 
			"#endif // __ACTIVE_IDLE\r\n" + 
			"\r\n" + 
			"gnumakefile icons_dc.mk\r\n" + 
			"\r\n" + 
			"gnumakefile ..\\CollectionUi\\group\\CollectionUiIcons_dc.mk\r\n" + 
			"gnumakefile ..\\PlaybackUi\\group\\PlaybackUiIcons_dc.mk\r\n" + 
			"\r\n" + 
			"#ifdef __SCALABLE_ICONS\r\n" + 
			"gnumakefile Icons_aif_scalable_dc.mk\r\n" + 
			"#else // __SCALABLE_ICONS\r\n" + 
			"gnumakefile Icons_aif_bitmaps_dc.mk\r\n" + 
			"#endif // __SCALABLE_ICONS\r\n" + 
			"\r\n" + 
			"..\\Collection\\group\\MCCommon.mmp\r\n" + 
			"..\\Collection\\group\\MCClient.mmp\r\n" + 
			"..\\Collection\\group\\MusicCollection.mmp\r\n" + 
			"..\\Collection\\group\\MCFileHandler.mmp\r\n" + 
			"..\\Collection\\group\\MCServer.mmp\r\n" + 
			"\r\n" + 
			"..\\DRMHelper\\group\\MPlayerDrmHelper.mmp\r\n" + 
			"..\\Engine\\group\\MusicPlayerEngine.mmp\r\n" + 
			"..\\CommonUi\\group\\MPlayerCommonUi.mmp\r\n" + 
			"..\\PlaylistEditor\\group\\MPlayerPlaylistEditor.mmp\r\n" + 
			"..\\CollectionUi\\group\\MPlayerCollectionUi.mmp\r\n" + 
			"..\\PlaybackUi\\group\\MPlayerPlaybackUi.mmp\r\n" + 
			"\r\n" + 
			"..\\App\\group\\MusicPlayer.mmp\r\n" + 
			"\r\n" + 
			"..\\ActiveIdleEngine\\group\\MPlayerRemoteControl.mmp\r\n" + 
			"..\\ActiveIdleEngine\\group\\RCtrlExtPluginInterface.mmp\r\n" + 
			"#ifdef __ACTIVE_IDLE\r\n" + 
			"..\\ActiveIdlePlugin\\group\\PlayerPlugin.mmp\r\n" + 
			"#endif // __ACTIVE_IDLE\r\n" + 
			"\r\n" + 
			"PRJ_TESTMMPFILES\r\n"; 
			
		String text2 = 
			"PRJ_PLATFORMS\r\n" + 
			"DEFAULT\r\n" + 
			"\r\n" + 
			"PRJ_EXPORTS\r\n" + 
			"//..\\Common\\inc\\MPlayerConstants.h           \\epoc32\\include\\oem\\MPlayerConstants.h\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"\r\n" + 
			"#ifdef __ACTIVE_IDLE  \r\n" + 
			"gnumakefile ..\\ActiveIdlePlugin\\group\\PlayerPluginIcons.mk  \r\n" + 
			"#endif // __ACTIVE_IDLE\r\n" + 
			"\r\n" + 
			"gnumakefile icons_dc.mk\r\n" + 
			"\r\n" + 
			"gnumakefile ..\\CollectionUi\\group\\CollectionUiIcons_dc.mk\r\n" + 
			"gnumakefile ..\\PlaybackUi\\group\\PlaybackUiIcons_dc.mk\r\n" + 
			"\r\n" + 
			"#ifdef __SCALABLE_ICONS\r\n" + 
			"gnumakefile Icons_aif_scalable_dc.mk\r\n" + 
			"#else // __SCALABLE_ICONS\r\n" + 
			"gnumakefile Icons_aif_bitmaps_dc.mk\r\n" + 
			"#endif // __SCALABLE_ICONS\r\n" + 
			"\r\n" + 
			"..\\Collection\\group\\MCCommon.mmp\r\n" + 
			"..\\Collection\\group\\MCClient.mmp\r\n" + 
			"..\\Collection\\group\\MusicCollection.mmp\r\n" + 
			"..\\Collection\\group\\MCFileHandler.mmp\r\n" + 
			"..\\Collection\\group\\MCServer.mmp\r\n" + 
			"\r\n" + 
			"..\\DRMHelper\\group\\MPlayerDrmHelper.mmp\r\n" + 
			"..\\Engine\\group\\MusicPlayerEngine.mmp\r\n" + 
			"..\\CommonUi\\group\\MPlayerCommonUi.mmp\r\n" + 
			"..\\PlaylistEditor\\group\\MPlayerPlaylistEditor.mmp\r\n" + 
			"..\\CollectionUi\\group\\MPlayerCollectionUi.mmp\r\n" + 
			"..\\PlaybackUi\\group\\MPlayerPlaybackUi.mmp\r\n" + 
			"\r\n" + 
			"..\\App\\group\\MusicPlayer.mmp\r\n" + 
			"\r\n" + 
			"..\\ActiveIdleEngine\\group\\MPlayerRemoteControl.mmp\r\n" + 
			"..\\ActiveIdleEngine\\group\\RCtrlExtPluginInterface.mmp\r\n" + 
			"second.mmp\r\n" + 
			"#ifdef __ACTIVE_IDLE\r\n" + 
			"..\\ActiveIdlePlugin\\group\\PlayerPlugin.mmp\r\n" + 
			"#endif // __ACTIVE_IDLE\r\n" + 
			"\r\n"+
			"PRJ_TESTMMPFILES\r\n" + 
			""; 
		
		makeModel(text);
		IBldInfView view = model.createView(config);
		IMMPReference mmp = view.createMMPReference();
		mmp.setPath(new Path("group/second.mmp"));
		view.getMakMakeReferences().add(mmp);
		commitTest(view, text2);
	}
	
	public void testEmptyStmts() {
		String text = 
			"PRJ_PLATFORMS\n"+
			"DEFAULT\n" +
			"PRJ_MMPFILES\n";
		makeModel(text);
		IBldInfView view = model.createView(config);
		commitTest(view, text);
		
		IMMPReference reference = view.createMMPReference();
		reference.setPath(new Path("group/foo.mmp"));
		view.getMakMakeReferences().add(reference);
		
		String text2 = 
			"PRJ_PLATFORMS\n"+
			"DEFAULT\n" +
			"PRJ_MMPFILES\n"+
			"foo.mmp\n";
		commitTest(view, text2);
		
	}
	
	public void testModifyingIncludes() {
		String inclText = 
			"//\r\n" + 
			"// Platforms\r\n" + 
			"//\r\n" + 
			"PRJ_PLATFORMS\r\n" + 
			"DEFAULT\r\n" + 
			"\r\n" + 
			"//\r\n" + 
			"// Phonebook exports\r\n" + 
			"//\r\n" + 
			"PRJ_EXPORTS\r\n" + 
			"\r\n" + 
			"// Export only when flag is not set\r\n" + 
			"#if !defined(RD_PHONEBOOK2)\r\n" + 
			"\r\n" + 
			"..\\PbkMMTelUtil\\Inc\\CPbkSimEntryCopyFactory.h 		\\epoc32\\include\\oem\\Phonebook\\CPbkSimEntryCopyFactory.h\r\n" + 
			"..\\PbkUsimUI\\Inc\\CSimPdDocumentBase.h			\\epoc32\\include\\oem\\phonebook\\CSimPdDocumentBase.h\r\n" + 
			"..\\PbkUsimUI\\Inc\\PbkUSimServAppDef.h			\\epoc32\\include\\oem\\phonebook\\PbkUSimServAppDef.h\r\n" + 
			"\r\n" + 
			"#endif // RD_PHONEBOOK2\r\n" + 
			"\r\n" + 
			"//\r\n" + 
			"// Phonebook SIM Bridge components\r\n" + 
			"//\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"\r\n" + 
			"#if !defined(RD_PHONEBOOK2)\r\n" + 
			"PbkMMTelUtil.mmp\r\n" + 
			"PbkUSimUI.mmp\r\n" + 
			"#endif // RD_PHONEBOOK2\r\n" + 
			"\r\n" + 
			"// End of file\r\n";
		String text = 
				"PRJ_PLATFORMS\n" +
				"DEFAULT\n" +
				"#include \"incl.inf\"\n";
				
		macros.add(DefineFactory.createDefine("RD_PHONEBOOK2", "1"));
		parserConfig.getFilesystem().put("incl.inf", inclText);
		makeModel(text);
		IBldInfView view = model.createView(config);
		commitTest(view, text);
		assertEquals(inclText, parserConfig.getFilesystem().get("incl.inf"));
		
	}
	
	public void testSpacing() {
		makeModel("");

		IBldInfView view = getView(config);
		assertNotNull(view);

		List<String> platforms = view.getPlatforms();

		platforms.add("WINSCW");
		platforms.add("ARMV5");

		IMMPReference mmp = view.createMMPReference();
		
		mmp.setPath(new Path("group/program.mmp"));
		view.getMakMakeReferences().add(mmp);

		IExport exp = view.createExport();
		exp.setSourcePath(new Path("src/MyFile.txt"));
		assertEquals(new Path("src/MyFile.txt"), exp.getSourcePath());
		view.getExports().add(exp);

		commitTest(view,
				"PRJ_PLATFORMS\n" +
				"WINSCW ARMV5\n" +
				"\n" +
				"PRJ_MMPFILES\n" +
				"program.mmp\n" +
				"\n" +
				"PRJ_EXPORTS\n" +
				"../src/MyFile.txt\n");
				
		mmp = view.createMMPReference();
		mmp.setPath(new Path("group/test.mmp"));
		view.getTestMakMakeReferences().add(mmp);

		mmp = view.createMMPReference();
		mmp.setPath(new Path("group/another.mmp"));
		view.getMakMakeReferences().add(mmp);

		commitTest(view,
				"PRJ_PLATFORMS\n" +
				"WINSCW ARMV5\n" +
				"\n" +
				"PRJ_MMPFILES\n" +
				"program.mmp\n" +
				"another.mmp\n" +
				"\n" +
				"PRJ_EXPORTS\n" +
				"../src/MyFile.txt\n"+
				"\n"+
				"PRJ_TESTMMPFILES\n" +
				"test.mmp\n");

	}
	
	/** 
	 * Bug 2770: don't add statements to blocks in other documents,
	 * since this copies the entire block to the main file.
	 *
	 */
	public void testLeaveNonMainBlocksAlone() {
		// Note: the minimal case for this is much smaller, but
		// might as well test with big real-life fileset.
		String origFile = 
			"/*\r\n" + 
			"* ==============================================================================\r\n" + 
			"*  Name        : bld.inf\r\n" + 
			"*  Part of     : \r\n" + 
			"*  Description : \r\n" + 
			"*  Version     : \r\n" + 
			"*\r\n" + 
			"*  Copyright (c) 2006 Nokia. All rights reserved.\r\n" + 
			"*  This material, including documentation and any related\r\n" + 
			"*  computer programs, is protected by copyright controlled by\r\n" + 
			"*  Nokia Corporation. All rights are reserved. Copying,\r\n" + 
			"*  including reproducing, storing, adapting or translating, any\r\n" + 
			"*  or all of this material requires the prior written consent of\r\n" + 
			"*  Nokia. This material also contains confidential\r\n" + 
			"*  information which may not be disclosed to others without the\r\n" + 
			"*  prior written consent of Nokia.\r\n" + 
			"* ==============================================================================\r\n" + 
			"*/\r\n" + 
			"\r\n" + 
			"#include <domain\\osextensions\\platform_paths.hrh>\r\n" + 
			"\r\n" + 
			"PRJ_PLATFORMS\r\n" + 
			"DEFAULT\r\n" + 
			"\r\n" + 
			"PRJ_EXPORTS\r\n" + 
			"..\\inc_domain\\mcdef.h 						APP_LAYER_DOMAIN_EXPORT_PATH(mcdef.h)\r\n" + 
			"..\\inc_domain\\MMusicShopBrViewCallback.h 			APP_LAYER_DOMAIN_EXPORT_PATH(MMusicShopBrViewCallback.h)\r\n" + 
			"..\\inc_domain\\MPlayerAudioUIController.h			APP_LAYER_DOMAIN_EXPORT_PATH(MPlayerAudioUIController.h)\r\n" + 
			"..\\inc_domain\\MPlayerConstants.h				APP_LAYER_DOMAIN_EXPORT_PATH(MPlayerConstants.h)\r\n" + 
			"..\\inc_domain\\MPlayerDrmHelper.h				APP_LAYER_DOMAIN_EXPORT_PATH(MPlayerDrmHelper.h)\r\n" + 
			"..\\inc_domain\\MPlayerPlaybackView.h				APP_LAYER_DOMAIN_EXPORT_PATH(MPlayerPlaybackView.h)\r\n" + 
			"..\\inc_domain\\MPlayerPlaylistEditor.h				APP_LAYER_DOMAIN_EXPORT_PATH(MPlayerPlaylistEditor.h)\r\n" + 
			"..\\inc_domain\\MPlayerPlaylistModel.h				APP_LAYER_DOMAIN_EXPORT_PATH(MPlayerPlaylistModel.h)\r\n" + 
			"..\\inc_domain\\MPlayerRemoteControl.h				APP_LAYER_DOMAIN_EXPORT_PATH(MPlayerRemoteControl.h)\r\n" + 
			"..\\inc_domain\\mplayersecondarydisplayapi.h			APP_LAYER_DOMAIN_EXPORT_PATH(mplayersecondarydisplayapi.h)\r\n" + 
			"..\\inc_domain\\MPlayerUIControllerObserver.h			APP_LAYER_DOMAIN_EXPORT_PATH(MPlayerUIControllerObserver.h)\r\n" + 
			"..\\inc_domain\\musicplayerdomaincrkeys.h				APP_LAYER_DOMAIN_EXPORT_PATH(musicplayerdomaincrkeys.h)\r\n" + 
			"..\\inc_domain\\MusicShopEmbeddedLauncher.h			APP_LAYER_DOMAIN_EXPORT_PATH(MusicShopEmbeddedLauncher.h)\r\n" + 
			"..\\inc_domain\\MusicShopExternalInterface.h			APP_LAYER_DOMAIN_EXPORT_PATH(MusicShopExternalInterface.h)\r\n" + 
			"..\\inc_domain\\MusicVisualizationPlugin.h			APP_LAYER_DOMAIN_EXPORT_PATH(MusicVisualizationPlugin.h)\r\n" + 
			"..\\inc_domain\\MusicPlayerInternalCRKeys.h 			APP_LAYER_DOMAIN_EXPORT_PATH(MusicPlayerInternalCRKeys.h)\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"#include \"..\\MusicPlayer\\group\\bld.inf\"\r\n" + 
			"#include \"..\\MusicShop\\group\\bld.inf\"\r\n" + 
			"#include \"..\\MusicVisualization\\group\\bld.inf\"\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"//  End of File  \r\n";
		
		String updatedFile = 
			origFile + 
			"\r\n"+
			"PRJ_MMPFILES\r\n" +
			"FMRadio.mmp\r\n";
		String musicPlayerBldInf = 
			"/*\r\n" + 
			"* ==============================================================================\r\n" + 
			"*  Name        : bld.inf\r\n" + 
			"*  Part of     : MusicPlayer\r\n" + 
			"*  Description : \r\n" + 
			"*    This file provides the information required for building the\r\n" + 
			"*    whole MusicPlayer application including all related libraries.\r\n" + 
			"*  Version     : \r\n" + 
			"*\r\n" + 
			"*  Copyright (c) 2002-2006 Nokia Corporation.\r\n" + 
			"*  This material, including documentation and any related \r\n" + 
			"*  computer programs, is protected by copyright controlled by \r\n" + 
			"*  Nokia Corporation. All rights are reserved. Copying, \r\n" + 
			"*  including reproducing, storing, adapting or translating, any \r\n" + 
			"*  or all of this material requires the prior written consent of \r\n" + 
			"*  Nokia Corporation. This material also contains confidential \r\n" + 
			"*  information which may not be disclosed to others without the \r\n" + 
			"*  prior written consent of Nokia Corporation.\r\n" + 
			"* ==============================================================================\r\n" + 
			"*/\r\n" + 
			"\r\n" + 
			"PRJ_PLATFORMS\r\n" + 
			"DEFAULT\r\n" + 
			"\r\n" + 
			"PRJ_EXPORTS\r\n" + 
			"//..\\Common\\inc\\MPlayerConstants.h           \\epoc32\\include\\oem\\MPlayerConstants.h\r\n" + 
			"//..\\Engine\\inc\\MPlayerPlaylistModel.h    \\epoc32\\include\\oem\\MPlayerPlaylistModel.h\r\n" + 
			"//..\\PlaylistEditor\\inc\\MPlayerPlaylistEditor.h  \\epoc32\\include\\oem\\MPlayerPlaylistEditor.h\r\n" + 
			"//..\\DRMHelper\\inc\\MPlayerDrmHelper.h     \\epoc32\\include\\oem\\MPlayerDrmHelper.h\r\n" + 
			"//..\\Common\\Inc\\MusicPlayerInternalCRKeys.h \\epoc32\\include\\oem\\MusicPlayerInternalCRKeys.h\r\n" + 
			"//..\\Common\\Inc\\musicplayerdomaincrkeys.h \\epoc32\\include\\oem\\musicplayerdomaincrkeys.h\r\n" + 
			"//..\\Common\\Inc\\MusicPlayerInternalPSKeys.h \\epoc32\\include\\oem\\MusicPlayerInternalPSKeys.h\r\n" + 
			"//..\\Engine\\inc\\MPlayerAudioUiController.h \\epoc32\\include\\oem\\MPlayerAudioUiController.h\r\n" + 
			"//..\\Engine\\inc\\MPlayerUIControllerObserver.h \\epoc32\\include\\oem\\MPlayerUIControllerObserver.h\r\n" + 
			"//..\\PlaybackUi\\inc\\MPlayerPlaybackView.h \\epoc32\\include\\oem\\MPlayerPlaybackView.h\r\n" + 
			"//..\\Common\\inc\\mplayersecondarydisplayapi.h           \\epoc32\\include\\oem\\secondarydisplay\\mplayersecondarydisplayapi.h\r\n" + 
			"//..\\ActiveIdleEngine\\Inc\\MPlayerRemoteControl.h \\epoc32\\include\\oem\\MPlayerRemoteControl.h \r\n" + 
			"\r\n" + 
			"//..\\collection\\inc\\mcdef.h \\epoc32\\include\\oem\\mcdef.h\r\n" + 
			"\r\n" + 
			"//Export the compiled DBMS policy file for the Music Collection DB...\r\n" + 
			"\r\n" + 
			"//for emulator\r\n" + 
			"..\\Collection\\data\\101FE031.spd \\epoc32\\release\\winscw\\udeb\\z\\private\\100012a5\\policy\\101FE031.spd\r\n" + 
			"..\\Collection\\data\\101FE031.spd \\epoc32\\release\\winscw\\urel\\z\\private\\100012a5\\policy\\101FE031.spd\r\n" + 
			"\r\n" + 
			"//for ROM building\r\n" + 
			"..\\Collection\\data\\101FE031.spd \\epoc32\\data\\z\\private\\100012a5\\policy\\101FE031.spd\r\n" + 
			"\r\n" + 
			"// Empty sound file\r\n" + 
			"..\\App\\data\\NullSound.mp3 \\epoc32\\release\\winscw\\udeb\\z\\private\\102072C3\\NullSound.mp3\r\n" + 
			"..\\App\\data\\NullSound.mp3 \\epoc32\\release\\winscw\\urel\\z\\private\\102072C3\\NullSound.mp3\r\n" + 
			"..\\App\\data\\NullSound.mp3 \\epoc32\\data\\z\\private\\102072C3\\NullSound.mp3\r\n" + 
			"\r\n" + 
			"// Secure backup & restore\r\n" + 
			"backup_registration.xml \\epoc32\\RELEASE\\winscw\\urel\\z\\private\\102072C3\\backup_registration.xml\r\n" + 
			"backup_registration.xml \\epoc32\\RELEASE\\winscw\\udeb\\z\\private\\102072C3\\backup_registration.xml\r\n" + 
			"backup_registration.xml \\epoc32\\data\\z\\private\\102072C3\\backup_registration.xml\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"\r\n" + 
			"#ifdef __ACTIVE_IDLE  \r\n" + 
			"gnumakefile ..\\ActiveIdlePlugin\\group\\PlayerPluginIcons.mk  \r\n" + 
			"#endif // __ACTIVE_IDLE\r\n" + 
			"\r\n" + 
			"gnumakefile icons_dc.mk\r\n" + 
			"\r\n" + 
			"gnumakefile ..\\CollectionUi\\group\\CollectionUiIcons_dc.mk\r\n" + 
			"gnumakefile ..\\PlaybackUi\\group\\PlaybackUiIcons_dc.mk\r\n" + 
			"\r\n" + 
			"#ifdef __SCALABLE_ICONS\r\n" + 
			"gnumakefile Icons_aif_scalable_dc.mk\r\n" + 
			"#else // __SCALABLE_ICONS\r\n" + 
			"gnumakefile Icons_aif_bitmaps_dc.mk\r\n" + 
			"#endif // __SCALABLE_ICONS\r\n" + 
			"\r\n" + 
			"..\\Collection\\group\\MCCommon.mmp\r\n" + 
			"..\\Collection\\group\\MCClient.mmp\r\n" + 
			"..\\Collection\\group\\MusicCollection.mmp\r\n" + 
			"..\\Collection\\group\\MCFileHandler.mmp\r\n" + 
			"..\\Collection\\group\\MCServer.mmp\r\n" + 
			"\r\n" + 
			"..\\DRMHelper\\group\\MPlayerDrmHelper.mmp\r\n" + 
			"..\\Engine\\group\\MusicPlayerEngine.mmp\r\n" + 
			"..\\CommonUi\\group\\MPlayerCommonUi.mmp\r\n" + 
			"..\\PlaylistEditor\\group\\MPlayerPlaylistEditor.mmp\r\n" + 
			"..\\CollectionUi\\group\\MPlayerCollectionUi.mmp\r\n" + 
			"..\\PlaybackUi\\group\\MPlayerPlaybackUi.mmp\r\n" + 
			"\r\n" + 
			"..\\App\\group\\MusicPlayer.mmp\r\n" + 
			"\r\n" + 
			"..\\ActiveIdleEngine\\group\\MPlayerRemoteControl.mmp\r\n" + 
			"..\\ActiveIdleEngine\\group\\RCtrlExtPluginInterface.mmp\r\n" + 
			"#ifdef __ACTIVE_IDLE\r\n" + 
			"..\\ActiveIdlePlugin\\group\\PlayerPlugin.mmp\r\n" + 
			"#endif // __ACTIVE_IDLE\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"PRJ_TESTMMPFILES\r\n" + 
			"";
		
		String musicShopBldInf =
			"/*\r\n" + 
			"* ==============================================================================\r\n" + 
			"*  Name		: bld.inf\r\n" + 
			"*  Part of	: MusicShop\r\n" + 
			"*  Description	: This is a top level bld file that drives all sub-components\r\n" + 
			"*                that make up the MusicShopEmbed, MusicShopApp and MusicShopLib\r\n" + 
			"*  Version	: %version: da1mmcf#15 %\r\n" + 
			"*\r\n" + 
			"*  Copyright (c) 2006 Nokia. All rights reserved.\r\n" + 
			"*  This material, including documentation and any related\r\n" + 
			"*  computer programs, is protected by copyright controlled by\r\n" + 
			"*  Nokia Corporation. All rights are reserved. Copying,\r\n" + 
			"*  including reproducing, storing, adapting or translating, any\r\n" + 
			"*  or all of this material requires the prior written consent of\r\n" + 
			"*  Nokia. This material also contains confidential\r\n" + 
			"*  information which may not be disclosed to others without the\r\n" + 
			"*  prior written consent of Nokia.\r\n" + 
			"* ==============================================================================\r\n" + 
			"*/\r\n" + 
			"\r\n" + 
			"PRJ_PLATFORMS\r\n" + 
			"DEFAULT\r\n" + 
			"\r\n" + 
			"PRJ_EXPORTS\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"\r\n" + 
			"#ifdef __SERIES60_NATIVE_BROWSER\r\n" + 
			"gnumakefile MusicShop_Icons_dc.mk\r\n" + 
			"\r\n" + 
			"#ifdef __SCALABLE_ICONS\r\n" + 
			"  gnumakefile MusicShop_Icons_aif_scalable_dc.mk\r\n" + 
			"#else // !__SCALABLE_ICONS\r\n" + 
			"  gnumakefile MusicShop_Icons_aif_bitmaps_dc.mk\r\n" + 
			"#endif // __SCALABLE_ICONS\r\n" + 
			"\r\n" + 
			"..\\musicshopembed\\group\\MusicShopEmbed.mmp\r\n" + 
			"..\\musicshoplib\\group\\musicshoplib.mmp\r\n" + 
			"..\\musicshopapp\\group\\musicshopapp.mmp\r\n" + 
			"\r\n" + 
			"#endif // __SERIES60_NATIVE_BROWSER\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"//  End of File\r\n" + 
			"";
		String musicVisualizationBldInf =
			"/*\r\n" + 
			"* ==============================================================================\r\n" + 
			"*  Name        : bld.inf\r\n" + 
			"*  Part of     : MusicVisualization\r\n" + 
			"*  Description : \r\n" + 
			"*    This file provides the information required for building the\r\n" + 
			"*    all MusicVisualization projects including plugins.\r\n" + 
			"*  Version     : \r\n" + 
			"*\r\n" + 
			"*  Copyright (c) 2005 Nokia Corporation.\r\n" + 
			"*  This material, including documentation and any related \r\n" + 
			"*  computer programs, is protected by copyright controlled by \r\n" + 
			"*  Nokia Corporation. All rights are reserved. Copying, \r\n" + 
			"*  including reproducing, storing, adapting or translating, any \r\n" + 
			"*  or all of this material requires the prior written consent of \r\n" + 
			"*  Nokia Corporation. This material also contains confidential \r\n" + 
			"*  information which may not be disclosed to others without the \r\n" + 
			"*  prior written consent of Nokia Corporation.\r\n" + 
			"* ==============================================================================\r\n" + 
			"*/\r\n" + 
			"\r\n" + 
			"PRJ_PLATFORMS\r\n" + 
			"DEFAULT\r\n" + 
			"\r\n" + 
			"PRJ_EXPORTS\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"\r\n" + 
			"gnumakefile ..\\Plugins\\MVPOscilloscope\\Group\\Icons_oscilloscope.mk\r\n" + 
			"gnumakefile ..\\Plugins\\MVPSpectrum\\Group\\Icons_spectrum.mk\r\n" + 
			"\r\n" + 
			"..\\Group\\MusicVisualizationPlugin.mmp\r\n" + 
			"..\\Plugins\\MVPOscilloscope\\Group\\MVPOscilloscope.mmp\r\n" + 
			"..\\Plugins\\MVPSpectrum\\Group\\MVPSpectrum.mmp\r\n" + 
			"\r\n" + 
			"PRJ_TESTMMPFILES\r\n" + 
			"\r\n" + 
			"// End of File\r\n" ; 
			
		String musicPlayerBldInfPath = FileUtils.createPossiblyRelativePath("../MusicPlayer/group/bld.inf").toOSString();
		parserConfig.getFilesystem().put(musicPlayerBldInfPath, musicPlayerBldInf);
		String musicShopBldInfPath = FileUtils.createPossiblyRelativePath("../MusicShop/group/bld.inf").toOSString();
		parserConfig.getFilesystem().put(musicShopBldInfPath, musicShopBldInf);
		String musicVisBldInfPath = FileUtils.createPossiblyRelativePath("../MusicVisualization/group/bld.inf").toOSString();
		parserConfig.getFilesystem().put(musicVisBldInfPath, musicVisualizationBldInf);
			
		makeModel(origFile);
		
		IBldInfView view = getView(config);
		assertNotNull(view);
		
		IMMPReference ref = view.createMMPReference();
		ref.setPath(new Path("group/FMRadio.mmp"));
		view.getMakMakeReferences().add(ref);
		
		assertEquals(parserConfig.getFilesystem().get(musicPlayerBldInfPath), musicPlayerBldInf);
		assertEquals(parserConfig.getFilesystem().get(musicShopBldInfPath), musicShopBldInf);
		assertEquals(parserConfig.getFilesystem().get(musicVisBldInfPath), musicVisualizationBldInf);
		commitTest(view, updatedFile);
 
	}
	
	
	/**
	 * When we have a change that would affect another document,
	 * then the entire node is changed and the node moves into
	 * the main document.  When deleting stuff, the case is even worse
	 * since updated nodes are replicated in main file, meaning
	 * delete doesn't happen at all. 
	 * <p>
	 *  Bug 2784
	 */
	public void testDeleteFromOtherDocument() {
		String incFile =
			"PRJ_MMPFILES\n" +
			"first.mmp\n"+
			"second.mmp\n";
		
		String mainFile = "#include \"inc.inf\"\n";
		
		String inclFile = "inc.inf";
		parserConfig.getFilesystem().put(inclFile, incFile);
		makeModel(mainFile);
		
		IBldInfView view = getView(config);
		assertEquals(2, view.getMakMakeReferences().size());
		
		view.getMakMakeReferences().remove(0);
		
		commitTest(view, 
				"#include \"inc.inf\"\n" +
				"");
		assertEquals(
				"PRJ_MMPFILES\n" +
				"second.mmp\n",
				parserConfig.getFilesystem().get(inclFile));
		
		assertEquals(1, view.getMakMakeReferences().size());
		view.getMakMakeReferences().remove(0);
		
		commitTest(view, 
				"#include \"inc.inf\"\n" +
				"");
		assertEquals("PRJ_MMPFILES\n" + 
				"", 
				parserConfig.getFilesystem().get(inclFile));
		
		
	}
	
	public void testBug2856() {
		String includedFile = 
			"PRJ_EXPORTS\r\n" + 
			"we_symbian_variant.h\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"#include \"..\\bld2.inf\"\r\n" + 
			"#include \"..\\..\\ui\\common\\ControlLibUIQ\\group\\bld.inf\"\r\n" + 
			"\r\n" + 
			"";
		String includedFile2 = 
			"PRJ_MMPFILES\r\n" +
			"other.mmp\r\n" +
			"\r\n" + 
			"";		
		String mainFile =  
				"PRJ_MMPFILES\r\n" + 
				"#include \"..\\bld1.inf\"\r\n" + 
				"app.mmp\r\n";
		
		path = projectPath.append("bld.inf");
		parserConfig.getFilesystem().put(projectPath.removeLastSegments(1).append("bld1.inf").toOSString(),
				includedFile);
		parserConfig.getFilesystem().put(projectPath.removeLastSegments(2).append("bld2.inf").toOSString(),
				includedFile2);
		makeModel(mainFile);

		IBldInfView view = getView(config);
		assertEquals(2, view.getMakMakeReferences().size());
		// outside proj, so absolute
		assertEquals(projectPath.removeLastSegments(2).append("other.mmp"), 
				view.getMakMakeReferences().get(0).getPath());
		// inside proj, so relative
		assertEquals(new Path("app.mmp"), 
				view.getMakMakeReferences().get(1).getPath());

	}
	
	public void testBug3060_1() {
		String text= "PRJ_MMPFILES\n"+
		"test.mmp\n"+
		"#if 0\n"+ 
		"@idiot\n"+
		"#endif\n"+
		"";
		
		makeModel(text);
		IBldInfView view = getView(config);
		commitTest(view, text);
		
	}
	public void testBug3060_2() {
		// this shouldn't crash or corrupt the rewritten file
		String text= "PRJ_MMPFILES\n"+
		"test.mmp\n"+
		"#ifdef 0\n"+ // yes, IFDEF 0!!! 
		"@idiot\n"+
		"#endif\n"+
		"";
		
		makeModel(text);
		IBldInfView view = getView(config);
		commitTest(view, text);
	}
	
	public void testBlockSyncsOverIncludes() {
		
		String text = "#include \"outer1.inf\"\r\n" + 
				"#include \"outer2.inf\"\r\n" + 
				"";
		String outer1 = 
				//"#include <domain\\osextensions\\platform_paths.hrh>\r\n" + 
				"\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"..\\inc\\cphonecntfactory.h                   |..\\..\\..\\inc\\cphonecntfactory.h\r\n" + 
				"..\\group\\phonecntfinder_stub.SIS            \\epoc32\\data\\z\\system\\install\\phonecntfinder_stub.SIS\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"..\\group\\phonecntfinder.mmp\r\n" + 
				"\r\n" + 
				"// PhoneCntFinderExt.\r\n" + 
				"#include \"inner1.inf\"\r\n" + 
				"\r\n" + 
				"// End of File\r\n" + 
				"";
		
		String outer2 = 
				// 2 - 23
				"\r\n" + 
				"#ifndef RD_PHONE_NG\r\n" +
				// 23-168
				"\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"\r\n" + 
				"// audio \r\n" + 
				"..\\inc\\audio\\MPhEngAudio.h                                    |..\\..\\..\\inc\\MPhEngAudio.h\r\n" + 
				"#ifndef RD_VOIP_REL_2_2\r\n" +
				// 193-289
				"..\\inc\\dtmfhandling\\CPhEngDtmfSender.h                        |..\\..\\..\\inc\\CPhEngDtmfSender.h\r\n" + 
				"#endif\r\n" + 
				// 297-408
				"\r\n" + 
				"\r\n" + 
				"// engine base\r\n" + 
				"..\\inc\\enginebase\\CPhEngModel.h                               |..\\..\\..\\inc\\CPhEngModel.h\r\n" + 
				"#if defined(__VOIP) && defined(RD_VOIP_REL_2_2)\r\n" +
				// 461-683
				"..\\inc\\extensions\\mctipluginuids.h                                     |..\\..\\..\\inc\\mctipluginuids.h\r\n" + 
				"..\\inc\\extensions\\phoneecomplugininterfaceuids.hrh                     |..\\..\\..\\inc\\phoneecomplugininterfaceuids.hrh\r\n" + 
				"#endif // __VOIP && RD_VOIP_REL_2_2\r\n" + 
				"\r\n" + 
				// 716-855
				// this line is corrupted due to the missync above
				"// sis stub file\r\n" + 
				"phoneengine_stub.sis   \\epoc32\\data\\z\\system\\install\\phoneengine_stub.sis\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"..\\Group\\PhoneEngine.mmp\r\n" + 
				"\r\n" + 
				"#endif // RD_PHONE_NG\r\n" + 
				"\r\n" + 
				// 878-896
				"// End of File\r\n" + 
				"";
		String inner1 = 
				"\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"..\\group\\phcntvoicerecoghandler_stub.SIS    \\epoc32\\data\\z\\system\\install\\phcntvoicerecoghandler_stub.SIS\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"..\\group\\PhCntVoiceRecogHandler.mmp\r\n" + 
				"\r\n" + 
				"PRJ_TESTMMPFILES\r\n" + 
				"\r\n" + 
				"// End of File\r\n" + 
				"";
		
		String inclFile = projectPath.append("outer1.inf").toOSString();
		parserConfig.getFilesystem().put(inclFile, outer1);
		inclFile = projectPath.append("inner1.inf").toOSString();
		parserConfig.getFilesystem().put(inclFile, inner1);
		inclFile = projectPath.append("outer2.inf").toOSString();
		parserConfig.getFilesystem().put(inclFile, outer2);

		makeModel(text);
		IBldInfView view = getView(config);
		checkNoProblems(view);
		assertEquals(0, view.getTestMakMakeReferences().size());
		
	}
	

}
