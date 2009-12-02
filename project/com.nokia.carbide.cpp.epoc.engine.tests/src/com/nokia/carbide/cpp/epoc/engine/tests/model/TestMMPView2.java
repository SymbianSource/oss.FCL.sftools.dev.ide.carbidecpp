/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.InvalidDriveInMMPPathException;
import com.nokia.carbide.cdt.builder.MMPViewPathHelper;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPResource;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStartBlockStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewASTBase;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

import java.io.File;
import java.util.List;

/**
 * Test MMP view manipulation
 *
 */
public class TestMMPView2 extends BaseMMPViewTest {
	
	static final String TEST_TRK_RESOURCE = 
		"START RESOURCE	trkapp.rss\r\n" + 
		"HEADER\r\n" + 
		"targetpath	\\resource\\apps\r\n" + 
		"lang		sc\r\n" + 
		"End\r\n" + 
		"";
	

	public void testTrkBug() {
		makeModel(TEST_TRK_RESOURCE);
		IMMPView view = model.createView(mmpConfig);
		assertEquals(0, view.getMessages().length);
		
	}
	
	static final String EMPTY_BITMAPS = 
		"start bitmap test.mbm\r\n" + 
		"end\r\n"; 
	public void testEmptyBitmaps() {
		makeModel(EMPTY_BITMAPS);
		IMMPView view = model.createView(mmpConfig);
		List<IMMPBitmap> bitmaps = view.getBitmaps();
		assertEquals(1, bitmaps.size());
		IMMPBitmap bm = bitmaps.get(0);
		assertNotNull(bm);
		assertNotNull(bm.getSources());
		assertEquals(0, bm.getSources().size());
	}
	
	
	public void testStartPlatform() {
		final String MMP = 
						"Start WINS\n"+
						"\t\twin32_headers\n"+
						"end";
		makeModel(MMP);

		IMMPView view = model.createView(mmpConfig);
		assertEquals(0, view.getMessages().length);
		
	}
	
	public void testAbsolutePaths() {
		// don't convert absolute or absolute-like paths
		final String MMP = 
			"USERINCLUDE +\\include\\oem \\epoc32\\foo " + getTokenAbsolutePath() + "foo\\bar\n";
		makeModel(MMP);
		
		IMMPView view = model.createView(mmpConfig);
		IPath path = view.getUserIncludes().get(0);
		assertEquals(new Path("+/include/oem"), path);
		path = view.getUserIncludes().get(1);
		assertEquals(new Path("/epoc32/foo"), path);
		path = view.getUserIncludes().get(2);
		assertEquals(new Path(getTokenAbsolutePath()).append("/foo/bar"), path);

		view.getSystemIncludes().add(new Path("+/epoc32/data"));
		view.getSystemIncludes().add(new Path("/epoc32/user"));
		// NOTE: absolute paths with drive letters not allowed, so c:\ is dropped
		// if a normal API using MMPViewPathHelper constructs the path.
		// We don't strip anything inside MMPView because we don't know
		// what future enhancements may need the letter.
		view.getSystemIncludes().add(new Path(getTokenAbsolutePath()).append("system/files"));
		
		// since the other paths are backslash, we keep this in this forward-slash path
		String expected = toDosPath(new Path(getTokenAbsolutePath()).append("system\\files"));
		commitTest(view, MMP +
				"SYSTEMINCLUDE +\\epoc32\\data \\epoc32\\user " + expected + "\n");
	}
	
	public void testRootProject() {
		// make sure we handle project root correctly when it's the root directory
		String root = HostOS.IS_WIN32 ? "C:" : "/"; 
		for (int i = 0; i < 2; i++){
			parserConfig.projectPath = new Path(i == 0 ? root : root + File.separator);
			IPath base = new Path(getTokenAbsolutePath());
			this.path = base.append("symbian/9.1/S60_3rd/S60Ex/Hello/group/hello.mmp");
			makeModel("SOURCEPATH ..\\src\n"+
					"SOURCE foo.cpp\n");
			
			IMMPView view = model.createView(mmpConfig);
			IPath[] srcPaths = view.getEffectiveSourcePaths();
			// important to be relative
			assertEquals(base.makeRelative().setDevice(null).append("symbian/9.1/S60_3rd/S60Ex/Hello/src"), srcPaths[0]);
	
			IPath src = view.getSources().get(0);
			// important to be relative
			assertEquals(base.makeRelative().setDevice(null).append("symbian/9.1/S60_3rd/S60Ex/Hello/src/foo.cpp"), src);
		}
	}
	
	final static String HELLOWORLD_MMP =
		"/*\r\n" + 
		"* ==============================================================================\r\n" + 
		"*  Name        : helloworldbasic.mmp\r\n" + 
		"*  Part of     : Helloworldbasic\r\n" + 
		"*  Interface   : \r\n" + 
		"*  Description : \r\n" + 
		"*  Version     : \r\n" + 
		"*\r\n" + 
		"*  Copyright (c) 2005-2006 Nokia Corporation.\r\n" + 
		"*  This material, including documentation and any related \r\n" + 
		"*  computer programs, is protected by copyright controlled by \r\n" + 
		"*  Nokia Corporation.\r\n" + 
		"* ==============================================================================\r\n" + 
		"*/\r\n" + 
		"\r\n" + 
		"TARGET            HelloWorldBasic.exe\r\n" + 
		"TARGETTYPE        exe\r\n" + 
		"UID		  0x100039CE 0xA000017F\r\n" + 
		"\r\n" + 
		"SECUREID	  0xA000017F\r\n" + 
		"EPOCSTACKSIZE	  0x5000\r\n" + 
		"\r\n" + 
		"SOURCEPATH        ..\\src\r\n" + 
		"SOURCE            HelloWorldBasic.cpp\r\n" + 
		"SOURCE            HelloWorldBasicApplication.cpp\r\n" + 
		"SOURCE            HelloWorldBasicAppView.cpp\r\n" + 
		"SOURCE            HelloWorldBasicAppUi.cpp\r\n" + 
		"SOURCE            HelloWorldBasicDocument.cpp\r\n" + 
		"\r\n" + 
		"SOURCEPATH        ..\\data\r\n" + 
		"\r\n" + 
		"START RESOURCE    HelloWorldBasic.rss\r\n" + 
		"HEADER\r\n" + 
		"TARGETPATH resource\\apps\r\n" + 
		"END //RESOURCE\r\n" + 
		"\r\n" + 
		"START RESOURCE    HelloWorldBasic_reg.rss\r\n" + 
		"#ifdef WINSCW\r\n" + 
		"TARGETPATH 	  \\private\\10003a3f\\apps\r\n" + 
		"#else\r\n" + 
		"TARGETPATH 	  \\private\\10003a3f\\import\\apps\r\n" + 
		"#endif\r\n" + 
		"END //RESOURCE\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"USERINCLUDE       ..\\inc\r\n" + 
		"\r\n" + 
		"SYSTEMINCLUDE     \\epoc32\\include\r\n" + 
		"\r\n" + 
		"LIBRARY           euser.lib\r\n" + 
		"LIBRARY           apparc.lib\r\n" + 
		"LIBRARY           cone.lib\r\n" + 
		"LIBRARY           eikcore.lib\r\n" + 
		"LIBRARY           avkon.lib\r\n" + 
		"LIBRARY           commonengine.lib\r\n" + 
		"LIBRARY		  efsrv.lib \r\n" + 
		"LIBRARY           estor.lib\r\n" + 
		"\r\n" + 
		"LANG SC\r\n" + 
		"\r\n" + 
		"VENDORID	  	  0\r\n" + 
		"CAPABILITY	NONE\r\n" + 
		"\r\n" + 
		"// End of File\r\n" + 
		"\r\n" + 
		"";
	public void testCommitBug() {
		makeModel(HELLOWORLD_MMP);
		
		IMMPView view = model.createView(mmpConfig);
		assertEquals(0, view.getMessages().length);
		
		commitTest(view, HELLOWORLD_MMP);
		
	}
	
	public void testMultiSourcePaths() {

		String txt = 
			"DOCUMENT group_doc.txt\n"+
			"SOURCEPATH ..\\src\n"+
			"SOURCE foo.cpp bar.cpp\n"+
			"RESOURCE src_rsrc.rss\n"+
			"SOURCEPATH ..\\data\n"+
			"SOURCE data_src.cpp\n"+
			"RESOURCE foo.rss\n"+
			"SOURCEPATH ..\\doc\n"+
			"DOCUMENT readme.txt\n"+
			"SYSTEMRESOURCE doc_rsrc.rss\n";
		
		makeModel(txt);
		IMMPView view = model.createView(mmpConfig);
		
		assertEquals(new Path("group/group_doc.txt"), view.getDocuments().get(0));
		assertEquals(new Path("src/foo.cpp"), view.getSources().get(0));
		assertEquals(new Path("src/bar.cpp"), view.getSources().get(1));
		assertEquals(new Path("src/src_rsrc.rss"), view.getUserResources().get(0));
		assertEquals(new Path("data/foo.rss"), view.getUserResources().get(1));
		assertEquals(new Path("data/data_src.cpp"), view.getSources().get(2));
		assertEquals(new Path("doc/readme.txt"), view.getDocuments().get(1));
		assertEquals(new Path("doc/doc_rsrc.rss"), view.getSystemResources().get(0));
		
		commitTest(view, txt);
	
	}
	public void testMultiSourcePathsChanging() {
		
		String txt = "SOURCEPATH ..\\src\n"+
		"SOURCE foo.cpp bar.cpp\n"+
		"SOURCEPATH ..\\data\n"+
		"RESOURCE foo.rss\n"+
		"SOURCEPATH ..\\doc\n"+
		"DOCUMENT readme.txt\n";
		makeModel(txt);
		IMMPView view = model.createView(mmpConfig);
		
		commitTest(view, txt);
		
		view.getSources().add(new Path("othersrc/uid.cpp"));
		view.getUserResources().add(new Path("otherdata/uid.rss"));
		view.getDocuments().add(new Path("otherdox/releasenotes.txt"));
		commitTest(view, "SOURCEPATH ..\\src\n" + 
				"SOURCE foo.cpp bar.cpp\n" + 
				"SOURCEPATH ..\\data\n" + 
				"RESOURCE foo.rss\n" + 
				"SOURCEPATH ..\\doc\n" + 
				"DOCUMENT readme.txt\n" + 
				"SOURCEPATH ..\\othersrc\n" + 
				"SOURCE uid.cpp\n" + 
				"SOURCEPATH ..\\otherdata\n" + 
				"RESOURCE uid.rss\n" + 
				"SOURCEPATH ..\\otherdox\n" + 
		"DOCUMENT releasenotes.txt\n");
		
		view.getSources().clear();
		view.getUserResources().clear();
		view.getDocuments().clear();
		view.getDocuments().add(new Path("data/foo_rss.txt"));

		commitTest(view, "SOURCEPATH ..\\data\n" + 
				"DOCUMENT foo_rss.txt\n");

	}
	
	public void testUIDChanging() {
		makeModel("");
		
		IMMPView view = model.createView(mmpConfig);
		view.setUid2("0x10000000");
		
		commitTest(view, "UID 0x10000000\n");
		
		view.setUid3("0xaaaaaaaa");
		commitTest(view, "UID 0x10000000 0xaaaaaaaa\n");
		
		view.setUid3(null);
		commitTest(view, "UID 0x10000000\n");

		view.setUid2(null);
		commitTest(view, "");

	}
	
	/**
	 * The base directory changes as files are #included
	 *
	 */
	public void testBaseDirectory() {
		IPath base = new Path(getTokenAbsolutePath()).append("test/basefile.mmp");
		parserConfig.getFilesystem().put(base.toOSString(), 
				"SOURCEPATH .\n"+
				"SOURCE base.cpp\n");

		parserConfig.getFilesystem().put(projectPath.append("utils").append("helper.mmh").toOSString(), 
				"SOURCEPATH .\n"+
				"SOURCE helper.cpp\n");

		makeModel(
				"SOURCEPATH .\n"+
				"SOURCE first.cpp\n"+
				"#include \"" + base.toOSString() + "\"\n"+
				"#include \"../utils/helper.mmh\"\n"+
				"SOURCEPATH ../src\n"+
				"SOURCE last.cpp\n");

		IMMPView view = model.createView(mmpConfig);
		assertEquals(4, view.getSources().size());
		
		assertEquals(new Path("group/first.cpp"), view.getSources().get(0));
		
		// note: should not be relative path when outside the project
		assertEquals(base.removeLastSegments(1).append("base.cpp"), view.getSources().get(1));
		
		assertEquals(new Path("utils/helper.cpp"), view.getSources().get(2));
		assertEquals(new Path("src/last.cpp"), view.getSources().get(3));
	}
	
	/** Sourcepath generation was buggy when mmp was at root. */
	public void testRootModelEmptySourcePaths() {
		path = new Path("test.mmp");
		
		makeModel("");
		IMMPView view = model.createView(mmpConfig);
		
		view.getSources().add(new Path("0"));
		// no SOURCEPATH . should be here
		commitTest(view,
				"SOURCE 0\n");
				
		view.getSources().add(new Path("1"));
		// no SOURCEPATH . should be here
		commitTest(view,
			"SOURCE 0 1\n");
		
	}
	
	public void testMbmChanging() {
		makeModel("START BITMAP test.mbm\n"+
				"SOURCE /c8 foo.bmp\n"+
				"END\n");
		IMMPView view = model.createView(mmpConfig);
		IMultiImageSource sources = view.getMultiImageSources().get(0);
		IBitmapSource source = sources.createBitmapSource();
		source.setPath(new Path("gfx/pic.bmp"));
		source.setColor(true);
		source.setDepth(16);
		sources.getSources().add(source);
		
		// bug 4726: new format is fwd slash
		commitTest(view,
				"START BITMAP test.mbm\n"+
				"\tSOURCE c8 foo.bmp\n"+
				"\tSOURCEPATH ../gfx\n"+
				"\tSOURCE c16 pic.bmp\n"+
				"END\n");
		
		sources = view.getMultiImageSources().get(0);
		source = sources.createBitmapSource();
		source.setPath(new Path("src/pic.bmp"));
		source.setColor(true);
		source.setDepth(8);
		source.setMaskDepth(1);
		source.setMaskPath(new Path("src/mask/pic.bmp"));
		sources.getSources().add(source);

		source = sources.createBitmapSource();
		source.setPath(new Path("src/pic2.bmp"));
		source.setColor(true);
		source.setDepth(8);
		source.setMaskDepth(1);
		source.setMaskPath(new Path("src/mask/pic2.bmp"));
		sources.getSources().add(source);

		commitTest(view,
				"START BITMAP test.mbm\n"+
				"\tSOURCE c8 foo.bmp\n"+
				"\tSOURCEPATH ../gfx\n"+
				"\tSOURCE c16 pic.bmp\n"+
				"\tSOURCEPATH ../src\n"+
				"\tSOURCE c8,1 pic.bmp mask/pic.bmp pic2.bmp mask/pic2.bmp\n"+
				"END\n");

	}
	
	/** This caused a ClassCastException */
	public void testResourceBlockScanning1() {
		makeModel("START RESOURCE target.rss\n"+
				"END\n"+
				"AIF target.aif ..\\aif\\ foo.rss\n");
		
		IMMPView view = model.createView(mmpConfig);
		assertEquals(1, view.getAifs().size());
		assertEquals(1, view.getResourceBlocks().size());
		
		view.getAifs().get(0).setTarget(new Path("newtarget.aif"));
		view.getResourceBlocks().get(0).setSource(new Path("group/newtarget.rss"));
		commitTest(view, "START RESOURCE newtarget.rss\n"+
				"END\n"+
				"AIF newtarget.aif ..\\aif foo.rss\n");
				
	}
	public void testResourceBlockScanning2() {
		makeModel("START RESOURCE target.rss\n"+
				"END\n"+
				"START BITMAP foo.mbm\n"+
				"END\n");
		
		IMMPView view = model.createView(mmpConfig);
		assertEquals(1, view.getBitmaps().size());
		assertEquals(1, view.getResourceBlocks().size());
		
		view.getResourceBlocks().get(0).setSource(new Path("group/newtarget.rss"));
		commitTest(view, "START RESOURCE newtarget.rss\n"+
				"END\n"+
				"START BITMAP foo.mbm\n"+
				"END\n");
				
	}
	
	public void testMissingNewlines() {
		makeModel("nostrictdef");
		
		IMMPView view = model.createView(mmpConfig);
		view.getSources().add(new Path("src/test.cpp"));
		commitTest(view, "nostrictdef\n"+
				"\n"+ // added newline 
				"SOURCEPATH ../src\n"+
				"SOURCE test.cpp\n");

	}
	
	public void addSpaceyFiles() {
		makeModel("SOURCEPATH ..\\src\n"
				+"SOURCE foo.cpp\n");
		
		IMMPView view = model.createView(mmpConfig);
		view.getSources().add(new Path("src/Copy of foo.cpp"));
		commitTest(view, 
				"SOURCEPATH ..\\src\n"+
				"SOURCE foo.cpp \"Copy of foo.cpp\"\n");
		
	}
	
	public void testPreprocessorProblems1() {
		String string = "#if 1\nSOURCE foo.cpp\n";
		makeModel(string);
		
		IMMPView view = model.createView(mmpConfig);
		
		IMessage[] problemNodes = view.getMessages();
		assertEquals(1, problemNodes.length);
		checkMessages(problemNodes);
		
		problemNodes = view.getMessages();
		assertEquals(1, problemNodes.length);
		checkMessages(problemNodes);
		
		assertEquals(1, view.getSources().size());
		
		// ensure no crash
		commitTest(view, string);
	}
	public void testPreprocessorProblems2() {
		String string = "BASEADDRESS 0\n#if 1\nSOURCE foo.cpp\nSRCDBG\n";
		makeModel(string);
		
		IMMPView view = model.createView(mmpConfig);
		IMessage[] problemNodes = view.getMessages();
		assertEquals(1, problemNodes.length);
		checkMessages(problemNodes);
		
		assertEquals(1, view.getSources().size());
		assertEquals("0", view.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));
		assertTrue(view.getFlags().contains(EMMPStatement.SRCDBG));
		
		// ensure no crash
		commitTest(view, string);
	}
	
	public void testEmptySingleArgStatementCleaning() {
		makeModel("TARGET foo.exe\n"+
				"TARGETTYPE dll\n"+
				"START RESOURCE foo.rss\n"+
				"TARGETPATH wow\\man\n"+
				"UID 0x2 0x3\n"+
				"END\n");
		IMMPView view = model.createView(mmpConfig);
		view.getSingleArgumentSettings().put(EMMPStatement.TARGET, "");
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETTYPE, "");
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETPATH, "");
		
		IMMPResource resource = view.getResourceBlocks().get(0);
		resource.setTargetFile("");
		resource.setUid2("");
		resource.setUid3("");

		makeModel(
				"START RESOURCE foo.rss\n"+
				"END\n");

		////

		makeModel("TARGET foo.exe\n"+
				"TARGETTYPE dll\n"+
				"START RESOURCE foo.rss\n"+
				"TARGETPATH wow\\man\n"+
				"UID 0x2 0x3\n"+
				"END\n");
		view = model.createView(mmpConfig);

		view.getSingleArgumentSettings().put(EMMPStatement.TARGET, null);
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETTYPE, null);
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETPATH, null);

		resource = view.getResourceBlocks().get(0);
		resource.setTargetFile(null);
		resource.setTargetPath(new Path(""));
		resource.setUid2(null);
		resource.setUid3(null);

		commitTest(view,
				"START RESOURCE foo.rss\n"+
				"END\n");
	}
	
	public void testMacros() {
		makeModel("MACRO    __HIDE_IPC_V1__\n");
		IMMPView view = model.createView(mmpConfig);
		List<String> macros = view.getListArgumentSettings().get(EMMPStatement.MACRO);
		assertEquals(1, macros.size());
		assertEquals("__HIDE_IPC_V1__", macros.get(0));
		commitTest(view, "MACRO    __HIDE_IPC_V1__\n");

		makeModel("#define __HIDE_IPC_V1__\n"+
				"MACRO    __HIDE_IPC_V1__\n");
		view = model.createView(mmpConfig);
		macros = view.getListArgumentSettings().get(EMMPStatement.MACRO);
		assertEquals(1, macros.size());
		assertEquals("__HIDE_IPC_V1__", macros.get(0));
		commitTest(view, "#define __HIDE_IPC_V1__\n"+
				"MACRO    __HIDE_IPC_V1__\n");
		
		macros = view.getListArgumentSettings().get(EMMPStatement.MACRO);
		macros.add("ANOTHER");
		commitTest(view, "#define __HIDE_IPC_V1__\n"+
		"MACRO    __HIDE_IPC_V1__ ANOTHER\n");
	}
	
	public void testRewritingWithProblems() {
		makeModel("SOURCE foo.cpp\n"+
				"END\n"+
				"START RESOURCE foo.rss\n"+
				"END\n");
		IMMPView view = model.createView(mmpConfig);
		commitTest(view,
				"SOURCE foo.cpp\n"+
				"END\n"+
				"START RESOURCE foo.rss\n"+
				"END\n");
		
	}
	
	public void testMessages() {
		String text= "BASEADDRESS\n"+
		"OPTION\n"+
		"UNKNOWN\n";
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		IMessage[] messages = view.getMessages();
		assertEquals(3, messages.length);
		assertEquals(IMessage.ERROR, messages[0].getSeverity());
		assertEquals(IMessage.ERROR, messages[1].getSeverity());
		assertEquals(IMessage.WARNING, messages[2].getSeverity());
		commitTest(view, text);
	}
	
	public void testEmptyTargetPath() {
		makeModel("");
		IMMPView view = model.createView(mmpConfig);
		IMMPBitmap bitmap = view.createMMPBitmap();
		bitmap.setTargetFile("foo.mbm");
		bitmap.setTargetPath(new Path(""));
		view.getMultiImageSources().add(bitmap);
		commitTest(view,
				"START BITMAP foo.mbm\n"+
				"END\n");
				
	}
	
	public void testCommitStartBlock() {
		// sanity check for next test, which sees this inside an #include
		String mainText =
			"source foo.cpp\n"+
			"start armcc\n"+
			"#ifdef FOO1\n"+
			"unpaged\n"+
			"#endif\n" +
			"end\n"+
			"source bar.cpp\n";
		makeModel(mainText);
		
		IMMPView view = model.createView(mmpConfig);
		commitTest(view, mainText);
	}
	
	public void testCommitIncludedFile() {
		// got bugs trying to commit this (bug 2408)
		String euserTxt =
			"source foo.cpp\n"+
			"start armcc\n"+
			"#ifdef FOO1\n"+
			"unpaged\n"+
			"#endif\n" +
			"end\n"+
			"source bar.cpp\n";
		parserConfig.getFilesystem().put("euser.mmh",
				euserTxt);
		
		String mainText = 
			"#include \"euser.mmh\"\n";
		makeModel(mainText);
		
		IMMPView view = model.createView(mmpConfig);
		commitTest(view, mainText);
		assertEquals(euserTxt, parserConfig.getFilesystem().get("euser.mmh"));
	}
	
	public void testBug2433() {
		String text = "// assabet\\milfsab.mmp\r\n" + 
				"//\r\n" + 
				"// Copyright (c) 1998-2001 Symbian Ltd. All rights reserved.\r\n" + 
				"//\r\n" + 
				"\r\n" + 
				"#include			<variant.mmh>\r\n" + 
				"#include			\"kernel\\kern_ext.mmh\"\r\n" + 
				"\r\n" + 
				"target				VariantTarget(medlfs,pdd)\r\n" + 
				"targettype			pdd\r\n" + 
				"linkas				medlfs.pdd\r\n" + 
				"\r\n" + 
				"systeminclude		inc\r\n" + 
				"systeminclude		\\epoc32\\include\\assp\\sa1100\r\n" + 
				"systeminclude		\\epoc32\\include\\drivers\r\n" + 
				"\r\n" + 
				"sourcepath			..\\e32\\drivers\\medlfs\r\n" + 
				"source				flash_media.cpp\r\n" + 
				"\r\n" + 
				"sourcepath			specific\r\n" + 
				"source				lffsdev.cpp lffsdev.cia\r\n" + 
				"\r\n" + 
				"library				ekern.lib elocd.lib\r\n" + 
				"library				VariantTarget(kasa1100,lib)\r\n" + 
				"\r\n" + 
				"epocallowdlldata\r\n" + 
				"\r\n" + 
				"capability			all\r\n" + 
				"\r\n" + 
				"MACRO 	ROLF_PARTITION\r\n" + 
				"VENDORID 0x70000001\r\n" + 
				""; 
				
		makeModel(text);

		IMMPView view = model.createView(mmpConfig);
		
		// we assume bld.inf is in ${project}/group
		IPath path = view.getSources().get(0);
		assertEquals(new Path("e32/drivers/medlfs/flash_media.cpp"), path);
		path = view.getSources().get(1);
		assertEquals(new Path("group/specific/lffsdev.cpp"), path);
		path = view.getSources().get(2);
		assertEquals(new Path("group/specific/lffsdev.cia"), path);
		
		
		commitTest(view, text);
		
	}
	
	public void testBug2079() {
		String text = "TARGET            Foo.exe\r\n" + 
				"TARGETTYPE        EXE\r\n" + 
				"UID               0x1000008d 0x200002EE\r\n" + 
				"\r\n" + 
				"EPOCSTACKSIZE 0x5000\r\n" + 
				"\r\n" + 
				"LANG			SC\r\n" + 
				"CAPABILITY		None\r\n" + 
				"\r\n" + 
				"SOURCEPATH        .\\..\\Code\\Src\r\n" + 
				"SOURCE            Main.cpp\r\n" + 
				"\r\n" + 
				"SYSTEMINCLUDE     \\epoc32\\include\r\n" + 
				"\r\n" + 
				"USERINCLUDE		  .\\..\\Code\\Inc\r\n" + 
				"\r\n" + 
				"LIBRARY     euser.lib\r\n" + 
				"\r\n";
		
		makeModel(text);

		IMMPView view = model.createView(mmpConfig);
		assertEquals(new Path("Code/Src/Main.cpp"), view.getSources().get(0));
		assertEquals(new Path("Code/Inc"), view.getUserIncludes().get(0));
		commitTest(view, text);
				
	}


	public void testMissingElses() {
		String text = "/*\r\n" + 
				"* ==============================================================================\r\n" + 
				"*  Name        : MCCommon.mmp\r\n" + 
				"*  Part of     : Music Player / Music Collection\r\n" + 
				"*  Description : Music Collection Common project specification\r\n" + 
				"*  Version     : 1.0\r\n" + 
				"*\r\n" + 
				"*  Copyright (c) 2004 Nokia. All rights reserved.\r\n" + 
				"*  This material, including documentation and any related \r\n" + 
				"*  computer programs, is protected by copyright controlled by \r\n" + 
				"*  Nokia. All rights are reserved. Copying, including \r\n" + 
				"*  reproducing, storing, adapting or translating, any \r\n" + 
				"*  or all of this material requires the prior written consent of \r\n" + 
				"*  Nokia. This material also contains confidential \r\n" + 
				"*  information which may not be disclosed to others without the \r\n" + 
				"*  prior written consent of Nokia.\r\n" + 
				"* ==============================================================================\r\n" + 
				"*/\r\n" + 
				"\r\n" + 
				"#include <bldvariant.hrh>\r\n" + 
				"#include <data_caging_paths.hrh>\r\n" + 
				"#include <domain\\osextensions\\platform_paths.hrh>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"TARGET          MCCommon.dll\r\n" + 
				"TARGETTYPE      dll\r\n" + 
				"UID             0x1000008d 0x101FFAE9\r\n" + 
				"VENDORID        VID_DEFAULT\r\n" + 
				"\r\n" + 
				"CAPABILITY      CAP_GENERAL_DLL\r\n" + 
				"\r\n" + 
				"SOURCEPATH       ..\\data\r\n" + 
				"START RESOURCE  MCRes.rss\r\n" + 
				"HEADER\r\n" + 
				"TARGETPATH      APP_RESOURCE_DIR\r\n" + 
				"LANG            SC\r\n" + 
				"END\r\n" + 
				"\r\n" + 
				"SOURCEPATH      ..\\src\\common\r\n" + 
				"SOURCE          MCTableView.cpp\r\n" + 
				"SOURCE          MCTable.cpp\r\n" + 
				"SOURCE          MCRowSet.cpp\r\n" + 
				"SOURCE          MCDatabase.cpp\r\n" + 
				"SOURCE          MCFileInfoUtility.cpp\r\n" + 
				"SOURCE          MCUtil.cpp\r\n" + 
				"SOURCE          MCResource.cpp\r\n" + 
				"SOURCE          MCDatabaseManager.cpp\r\n" + 
				"SOURCE          MCActiveTask.cpp\r\n" + 
				"SOURCE          MCGenreParser.cpp\r\n" + 
				"\r\n" + 
				"USERINCLUDE     ..\\inc\r\n" + 
				"USERINCLUDE     ..\\..\\..\\inc\r\n" + 
				"USERINCLUDE     ..\\inc\\common\r\n" + 
				"USERINCLUDE     ..\\..\\common\\inc\r\n" + 
				"\r\n" + 
				"APP_LAYER_SYSTEMINCLUDE\r\n" + 
				"SYSTEMINCLUDE   \\epoc32\\include\\oem\\SecondaryDisplay\r\n" + 
				"SYSTEMINCLUDE   \\epoc32\\include\\mmf\\common\r\n" + 
				"SYSTEMINCLUDE   \\epoc32\\include\\mmf\\server\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"LIBRARY         euser.lib \r\n" + 
				"LIBRARY         edbms.lib \r\n" + 
				"LIBRARY         efsrv.lib \r\n" + 
				"LIBRARY         ESTOR.LIB\r\n" + 
				"LIBRARY         MMFControllerFramework.lib   // CMMFMetaDataEntry\r\n" + 
				"LIBRARY         MMFSTANDARDCUSTOMCOMMANDS.lib \r\n" + 
				"LIBRARY         BAFL.LIB\r\n" + 
				"LIBRARY         ecom.lib // DestroyedImplemantation\r\n" + 
				"LIBRARY         cone.lib\r\n" + 
				"LIBRARY         METADATAUTILITY.lib\r\n" + 
				"LIBRARY         caf.lib\r\n" + 
				"LIBRARY         cafutils.lib\r\n" + 
				"LIBRARY         MMCommon.lib // TMMFileHandleSource\r\n" + 
				"\r\n" + 
				"DEBUGLIBRARY    flogger.lib\r\n" + 
				"\r\n" + 
				"#if defined(ARMCC)\r\n" + 
				"\r\n" + 
				"#if defined(RD_MUSIC_COLLECTION_AUTO_REFRESH)\r\n" + 
				"deffile ..\\eabi\\new\\  \r\n" + 
				"#else\r\n" + 
				"deffile ..\\eabi\\ \r\n" + 
				"#endif\r\n" + 
				"\r\n" + 
				"#elif defined(WINSCW)\r\n" + 
				"\r\n" + 
				"#if defined(RD_MUSIC_COLLECTION_AUTO_REFRESH)\r\n" + 
				"deffile ..\\bwinscw\\new\\ \r\n" + 
				"#else\r\n" + 
				"deffile ..\\bwinscw\\ \r\n" + 
				"#endif\r\n" + 
				"\r\n" + 
				"#endif\r\n" + 
				"\r\n" + 
				"// End of File\r\n";
		macros.add(DefineFactory.createDefine("ARMCC"));
		macros.add(DefineFactory.createDefine("RD_MUSIC_COLLECTION_AUTO_REFRESH"));
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		assertNotNull(view.getDefFile());
		commitTest(view, text);
		assertNotNull(view.getDefFile());
		view.dispose();
		
		view = model.createView(allConfig);
		assertNotNull(view.getDefFile());
		commitTest(view, text);
		assertNotNull(view.getDefFile());

	}
	
	public void testRetainTrailingLine1() {
		String text = 
			"/*\r\n" + 
			"* ==============================================================================\r\n" + 
			"*  Name        : MusicCollection.mmp\r\n" + 
			"*  Part of     : Music Player / Music Collection\r\n" + 
			"*  Description : Music Collection project specification\r\n" + 
			"*  Version     : \r\n" + 
			"*\r\n" + 
			"*  Copyright (c) 2004 Nokia. All rights reserved.\r\n" + 
			"*  This material, including documentation and any related \r\n" + 
			"*  computer programs, is protected by copyright controlled by \r\n" + 
			"*  Nokia. All rights are reserved. Copying, including \r\n" + 
			"*  reproducing, storing, adapting or translating, any \r\n" + 
			"*  or all of this material requires the prior written consent of \r\n" + 
			"*  Nokia. This material also contains confidential \r\n" + 
			"*  information which may not be disclosed to others without the \r\n" + 
			"*  prior written consent of Nokia.\r\n" + 
			"* ==============================================================================\r\n" + 
			"*/\r\n" + 
			"\r\n" + 
			"#include <domain\\osextensions\\platform_paths.hrh>\r\n" + 
			"\r\n" + 
			"TARGET          MusicCollection.dll\r\n" + 
			"TARGETTYPE      dll\r\n" + 
			"UID             0x1000008d 0x101FFAE5\r\n" + 
			"VENDORID        VID_DEFAULT\r\n" + 
			"\r\n" + 
			"CAPABILITY      CAP_GENERAL_DLL\r\n" + 
			"\r\n" + 
			"SOURCEPATH      ..\\src\r\n" + 
			"SOURCE          MCModel.cpp\r\n" + 
			"SOURCE          MCItemBase.cpp\r\n" + 
			"SOURCE          MCSong.cpp\r\n" + 
			"SOURCE          MCSongDetails.cpp \r\n" + 
			"SOURCE          MCItemArray.cpp \r\n" + 
			"SOURCE          MCCategory.cpp\r\n" + 
			"SOURCE          MCCategoryArray.cpp\r\n" + 
			"SOURCE          MCDbEventNotfier.cpp\r\n" + 
			"SOURCE          MCArtist.cpp\r\n" + 
			"SOURCE          MCItemId.cpp\r\n" + 
			"SOURCE          MCSongDetailsExtend.cpp\r\n" + 
			"SOURCE          MCDeleteHelper.cpp\r\n" + 
			"SOURCE          MCRenameCategoryHelper.cpp\r\n" + 
			"SOURCE          MCLatestHelper.cpp\r\n" + 
			"SOURCE          MCAlbumArtHelper.cpp\r\n" + 
			"\r\n" + 
			"USERINCLUDE     ..\\inc\r\n" + 
			"USERINCLUDE 	..\\..\\..\\inc\r\n" + 
			"USERINCLUDE     ..\\inc\\client\r\n" + 
			"USERINCLUDE     ..\\inc\\common\r\n" + 
			"USERINCLUDE     ..\\..\\common\\inc\r\n" + 
			"\r\n" + 
			"APP_LAYER_SYSTEMINCLUDE\r\n" + 
			"SYSTEMINCLUDE   \\epoc32\\include\\mmf\\common\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"LIBRARY         MCClient.lib\r\n" + 
			"\r\n" + 
			"LIBRARY         euser.lib \r\n" + 
			"LIBRARY         edbms.lib \r\n" + 
			"LIBRARY         efsrv.lib \r\n" + 
			"LIBRARY         MMFControllerFramework.lib   // CMMFMetaDataEntry\r\n" + 
			"LIBRARY         BAFL.LIB\r\n" + 
			"LIBRARY         MCCommon.lib\r\n" + 
			"LIBRARY         APGRFX.lib\r\n" + 
			"LIBRARY         APMIME.lib\r\n" + 
			"LIBRARY         METADATAUTILITY.lib\r\n" + 
			"LIBRARY         MGXMediaFileAPI.lib\r\n" + 
			"LIBRARY         cone.lib\r\n" + 
			"\r\n" + 
			"DEBUGLIBRARY    flogger.lib\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"#if defined(ARMCC)\r\n" + 
			"deffile ..\\eabi\\ \r\n" + 
			"#elif defined(WINSCW)\r\n" + 
			"deffile ..\\bwinscw\\ \r\n" + 
			"#endif\r\n" + 
			"\r\n" + 
			"// End of File"; 
			
		makeModel(text);
		
		IMMPView view = model.createView(mmpConfig);
		commitTest(view, text);

			
	}
	public void testRetainTrailingLine2() {
		String inclText = 
			"SOURCE a.cpp\n"+
			"\n" +
			"#if FOO\n"+
			"deffile a\n"+
			"#else\n" +
			"deffile b\n"+
			"#endif\n" +
			"\n"+
			"// end of file";
		
		String inclName = path.removeLastSegments(1).append("include.mmh").toOSString();
		parserConfig.getFilesystem().put(inclName, inclText);
		String text = "#include \"include.mmh\"\n";
		makeModel(text);
		
		IMMPView view = model.createView(mmpConfig);
		commitTest(view, text);
		assertEquals(inclText, parserConfig.getFilesystem().get(inclName));
	}
	
	public void testParentDirectory() {
		String text = "SOURCEPATH .\n"+
		"SOURCE file.cpp\n"+
		"SOURCEPATH ..\\outer\n"+
		"SOURCE file2.cpp\n";

		path = projectPath.append("test.mmp");
		makeModel(text);
		
		// don't repeat "."
		IMMPView view = model.createView(mmpConfig);
		assertEquals(new Path("file.cpp"), view.getSources().get(0));
		
		// resolve to something real now
		assertEquals(projectPath.append("../outer/file2.cpp"), view.getSources().get(1));
	}
	
	/**
	 * When changing contents in a block that uses conditionals,
	 * comment out the whole block and make a flattened version.
	 */
	public void testBlockReplacing1() {
		String text =  
				"START RESOURCE ..\\data\\AddressBook_reg.rss\r\n" + 
				"#ifdef WINSCW\r\n" + 
				"TARGETPATH \\private\\10003a3f\\apps\r\n" + 
				"#else\r\n" + 
				"TARGETPATH \\private\\10003a3f\\import\\apps\r\n" + 
				"#endif\r\n" + 
				"END\r\n";
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
	
		IMMPResource rsrc = view.getResourceBlocks().get(0);
		rsrc.setHeaderFlags(EGeneratedHeaderFlags.Header);
		
		commitTest(view,
				"#if 0\r\n" +
				"START RESOURCE ..\\data\\AddressBook_reg.rss\r\n" + 
				"#ifdef WINSCW\r\n" + 
				"TARGETPATH \\private\\10003a3f\\apps\r\n" + 
				"#else\r\n" + 
				"TARGETPATH \\private\\10003a3f\\import\\apps\r\n" + 
				"#endif\r\n" + 
				"END\r\n"+
				"#endif\r\n"+
				"START RESOURCE ..\\data\\AddressBook_reg.rss\r\n" + 
				"\tTARGETPATH \\private\\10003a3f\\import\\apps\r\n" + 
				"\tHEADER\r\n" + 
				"END\r\n");
		
	}
	
	/**
	 * When NOT changing contents in a block that uses conditionals, do nothing
	 */
	public void testBlockReplacing1Not() {
		String text =  
				"START RESOURCE ..\\data\\AddressBook_reg.rss\r\n" + 
				"#ifdef WINSCW\r\n" + 
				"TARGETPATH \\private\\10003a3f\\apps\r\n" + 
				"#else\r\n" + 
				"TARGETPATH \\private\\10003a3f\\import\\apps\r\n" + 
				"#endif\r\n" + 
				"END\r\n";
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		
		commitTest(view, text);
	}
	/**
	 * When changing contents in a block that uses macros,
	 * comment out the whole block and make a flattened version.
	 */
	public void testBlockReplacing2() {
		String text =  
				"#define TARGETDIR \\private\\10003a3f\\apps\r\n"+
				"START RESOURCE ..\\data\\AddressBook_reg.rss\r\n" + 
				"TARGETPATH TARGETDIR\r\n" + 
				"END\r\n";
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
	
		IMMPResource rsrc = view.getResourceBlocks().get(0);
		rsrc.setHeaderFlags(EGeneratedHeaderFlags.Header);
		
		commitTest(view, 
				"#define TARGETDIR \\private\\10003a3f\\apps\r\n" + 
				"#if 0\r\n" + 
				"START RESOURCE ..\\data\\AddressBook_reg.rss\r\n" + 
				"TARGETPATH TARGETDIR\r\n" + 
				"END\r\n" + 
				"#endif\r\n" + 
				"START RESOURCE ..\\data\\AddressBook_reg.rss\r\n" + 
				"\tTARGETPATH \\private\\10003a3f\\apps\r\n" + 
				"\tHEADER\r\n" + 
				"END\r\n");
		
	}
	
	/**
	 * When NOT changing contents in a block that uses macros, don't do anything
	 */
	public void testBlockReplacing2Not() {
		String text =  
				"#define TARGETDIR \\private\\10003a3f\\apps\r\n"+
				"START RESOURCE ..\\data\\AddressBook_reg.rss\r\n" + 
				"TARGETPATH TARGETDIR\r\n" + 
				"END\r\n";
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		
		commitTest(view, text);
	}
	
	/**
	 * Test that making removals through serial build-configuration-specific
	 * changes has the same effect as making changes all at once with 
	 * an "all" view.
	 *
	 */
	public void testSerialVsParallelRemoving() {
		String origFile =
			"SOURCEPATH ..\\src\n" +
			"#ifdef VARIANT_1\n" +
			"SOURCE file_common.cpp file1.cpp\n" +
			"#endif\n" +
			"#ifdef VARIANT_2\n" +
			"SOURCE file_common.cpp file2.cpp\n" +
			"#endif\n" +
			"RESOURCE foo.rss\n";
	
		// expected result removing a file for VARIANT_1
		String var1Chg = 
			"SOURCEPATH ..\\src\n" +
			"#ifdef VARIANT_1\n" +
			"SOURCE file1.cpp\n" +
			"#endif\n" +
			"#ifdef VARIANT_2\n" +
			"SOURCE file_common.cpp file2.cpp\n" +
			"#endif\n" +
			"RESOURCE foo.rss\n";

		// expected result removing a file for VARIANT_1 then VARIANT_2,
		// as well as removing both files at once under the "all" view
		String finalResult = 
			"SOURCEPATH ..\\src\n" +
			"#ifdef VARIANT_1\n" +
			"SOURCE file1.cpp\n" +
			"#endif\n" +
			"#ifdef VARIANT_2\n" +
			"SOURCE file2.cpp\n" +
			"#endif\n" +
			"RESOURCE foo.rss\n";

		makeModel(origFile);

		IMMPView view;
		
		// a configuration matching nothing
		macros.clear();
		view = model.createView(mmpConfig);

		assertEquals(0, view.getSources().size());
		commitTest(view, origFile);

		// variant 1
		macros.clear();
		macros.add(DefineFactory.createDefine("VARIANT_1"));
		view = model.createView(mmpConfig);
		
		assertEquals(2, view.getSources().size());
		view.getSources().remove(0);
		commitTest(view, var1Chg);

		// variant 2
		macros.clear();
		macros.add(DefineFactory.createDefine("VARIANT_2"));
		view = model.createView(mmpConfig);
		
		assertEquals(2, view.getSources().size());
		view.getSources().remove(0);
		commitTest(view, finalResult);

		///////////////
		
		makeModel(origFile);

		// all config
		macros.clear();
		view = model.createView(allConfig);
		
		// this contains dupes of file_common.cpp
		assertEquals(4, view.getSources().size());
		
		// remove backwards since indices change...
		view.getSources().remove(2);
		view.getSources().remove(0);
		
		commitTest(view, finalResult);

	}
	
	public void testDetectErrorsInBlocks() {
		String errText = "START RESOURCE foo.rss\n" +
		"* what? dir\n" +
		"END\n";
		makeModel(errText);
		IMMPView view = model.createView(mmpConfig);
		IMessage[] problems = view.getMessages(); 
		assertEquals(1, problems.length);
		assertEquals(1, view.getResourceBlocks().size());
		commitTest(view, errText);
		view.getResourceBlocks().get(0).setTargetPath(new Path("dir"));
		commitTest(view, "START RESOURCE foo.rss\n" +
		"* what? dir\n" +
		"\tTARGETPATH dir\n"+
		"END\n");
	}
	public void testDetectErroneouslyNestedResources() {
		makeModel("START RESOURCE foo.rss\n" +
				"TARGETPATH dir\n" +
				"START RESOURCE another.rss\n" +
				"END\n" +
				"END\n");
		IMMPView view = model.createView(mmpConfig);
		assertEquals(2, view.getMessages().length);

		IMessage[] messages = view.getMessages();
		assertEquals(IMessage.ERROR, messages[0].getSeverity());
		assertEquals(IMessage.WARNING, messages[1].getSeverity());

	}
	
	public void testNoDriveLettersAppear() {
		makeModel("USERINCLUDE ..\\group\n");
		IMMPView view = model.createView(mmpConfig);
		IPath fullpath = new Path(getTokenAbsolutePath()).append("temp");
		view.getUserIncludes().add(fullpath);
		// We don't strip drive letters inside the MMPView, only through the
		// canonicalization API MMPViewPathHelper.
		commitTest(view,
				"USERINCLUDE ..\\group " + toDosPath(fullpath) + "\n");
	}
	public void testNoDriveLettersAppear2() {
		// not going to have a drivey path here
		if (HostOS.IS_UNIX)
			return;
		
		makeModel("USERINCLUDE ..\\group\n");
		IMMPView view = model.createView(mmpConfig);
		MMPViewPathHelper helper = new MMPViewPathHelper(view, (IPath)null);
		try {
			// no SDK active here, so all full paths throw
			helper.convertProjectOrFullPathToMMP(
								EMMPPathContext.USERINCLUDE, new Path(getTokenAbsolutePath()).append("temp"));
			fail();
		} catch (InvalidDriveInMMPPathException e) {
			view.getUserIncludes().add(e.getPathNoDevice());
		}
		IPath fullpath = new Path(getTokenAbsolutePath()).setDevice(null).append("temp");
		commitTest(view,
				"USERINCLUDE ..\\group " + toDosPath(fullpath) + "\n");
	}
	
	public void testBug2817() {
		//  do NOT leave the macro parameters behind when replacing macro-expanded nodes
		makeModel("#define NAME(n,e) n##.##e\n" +
				"#define UIDVAL(suff) 0x1234##suff\n"+
				"UID UIDVAL(AA) UIDVAL(FF)\n"+
				"TARGET NAME(foo,exe)\n"+
				"#define IS_FOOING() 3\n"+
				"EPOCSTACKSIZE IS_FOOING()\n"
		);
		IMMPView view = model.createView(mmpConfig);
		assertEquals("foo.exe",
				view.getSingleArgumentSettings().get(EMMPStatement.TARGET));
		assertEquals("0x1234AA", view.getUid2());
		assertEquals("0x1234FF", view.getUid3());
		assertEquals("3", view.getSingleArgumentSettings().get(EMMPStatement.EPOCSTACKSIZE));
		view.getSingleArgumentSettings().put(EMMPStatement.TARGET,
				"bar.exe");
		view.setUid2("0");
		view.getSingleArgumentSettings().put(EMMPStatement.EPOCSTACKSIZE,
				"1");
		commitTest(view,
				"#define NAME(n,e) n##.##e\n" + 
				"#define UIDVAL(suff) 0x1234##suff\n" + 
				"#if 0\n" + 
				"UID UIDVAL(AA) UIDVAL(FF)\n" + 
				"#endif\n" + 
				"UID 0 0x1234FF\n" + 
				"#if 0\n" + 
				"TARGET NAME(foo,exe)\n" + 
				"#endif\n" + 
				"TARGET bar.exe\n" + 
				"#define IS_FOOING() 3\n" + 
				"#if 0\n" + 
				"EPOCSTACKSIZE IS_FOOING()\n" + 
				"#endif\n" + 
				"EPOCSTACKSIZE 1\n" + 
				"");
				
	}
	
	public void testBug2919() {
			/*
	With the above MMP file, wstop.cpp will be parsed correctly but all
	bitmap.copp, capkey.cpp and client.cpp are not found. They will be listed up in
	Missing C/C++ sources as ../src/common/generic/graphics/
	wserv/server/bitmap.cpp and so on.
	It should be "..\..\..\..\src\common\generic\graphics\wserv\group\..
	\server\bitmap.cpp".
	
	 My project is located in something like this.
>
> >   M:\src\common\generic\lots of projects here by Symbian incl. foo
> >   M:\src\Fujitsu\foo  branched from the foo above.

			 */
		String text = "#define ToOriginalPath(path)  ..\\..\\..\\..\\src\\common\\generic\\graphics\\wserv\\group\\##path\r\n" + 
				"SOURCEPATH\tToOriginalPath(..\\server)\r\n" + 
				"SOURCE\tbitmap.cpp capkey.cpp client.cpp\r\n" + 
				"SOURCEPATH\t..\\server\r\n" + 
				"SOURCE\twstop.cpp\r\n";

		projectPath = new Path("m:/src");
		path = projectPath.append("Fujitsu/foo/group/test.mmp");
		
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);

		List<IPath> sources = view.getSources();
		assertEquals(new Path("m:/src/common/generic/graphics/wserv/server/bitmap.cpp"),
				sources.get(0));
		assertEquals(new Path("m:/src/Fujitsu/foo/server/wstop.cpp"),
				sources.get(3));
	}
	
	public void testRenamingFoldersBug() {
		String origText =
			"TARGET test1.exe\r\n" + 
			"TARGETTYPE exe\r\n" + 
			"UID 0\r\n" + 
			"SOURCEPATH ..\\src1\r\n" + 
			"SOURCE Test1.cpp tEst2.cpp TEST3.cpp\r\n" + 
			"SOURCEPATH ..\\SRC2\r\n" + 
			"SOURCE tesT1.cpp TEST2.cpp TEst3.cpp\r\n" + 
			"SOURCEPATH ..\\commonsrc\r\n" + 
			"SOURCE testCommon1.cpp TestCommon2.cpp TESTcommon3.cpp\r\n" + 
			"USERINCLUDE .\r\n" + 
			"USERINCLUDE ..\\inc\r\n" + 
			"SYSTEMINCLUDE ..\\INC\r\n" + 
			"SOURCEPATH ..\\DATA\r\n" + 
			"RESOURCE testrss.RSS\r\n" + 
			"SYSTEMRESOURCE TESTsystemrss.rss\r\n" + 
			"START RESOURCE testblock.RSS\r\n" + 
			"	TARGETPATH resource\\apps\r\n" + 
			"	HEADER\r\n" + 
			"END\r\n" +
			"AIF testaif.aif .. AifRenamed\\TestAif.rss c12 AifRenamed\\TestAif.rss\r\n" + 
			"START BITMAP test1.mbm\r\n" + 
			"	SOURCEPATH ..\\bmp\r\n" + 
			"	SOURCE c8 Test1.bmp TEST2.bmp test3.Bmp\r\n" + 
			"END\r\n" + 
			"DEFFILE Test.DEF\r\n" + 
			"SOURCEPATH ..\\DocRenamed\r\n" + 
			"DOCUMENT TestDoc.txt\r\n" + 
			"\r\n" + 
			"";
		
		makeModel(origText);
		IMMPView view = model.createView(mmpConfig);

		view.getUserResources().set(0, new Path("dataRenamed/testrss.RSS"));
		view.getSystemResources().set(0, new Path("dataRenamed/TESTsystemrss.rss"));
		view.getResourceBlocks().get(0).setSource(new Path("dataRenamed/testblock.RSS"));
		
		String expText = 
			"TARGET test1.exe\r\n" + 
			"TARGETTYPE exe\r\n" + 
			"UID 0\r\n" + 
			"SOURCEPATH ..\\src1\r\n" + 
			"SOURCE Test1.cpp tEst2.cpp TEST3.cpp\r\n" + 
			"SOURCEPATH ..\\SRC2\r\n" + 
			"SOURCE tesT1.cpp TEST2.cpp TEst3.cpp\r\n" + 
			"SOURCEPATH ..\\commonsrc\r\n" + 
			"SOURCE testCommon1.cpp TestCommon2.cpp TESTcommon3.cpp\r\n" + 
			"USERINCLUDE .\r\n" + 
			"USERINCLUDE ..\\inc\r\n" + 
			"SYSTEMINCLUDE ..\\INC\r\n" + 
			"\r\n" + // NOTE: this is because SOURCEPATH is new, although it replaced an existing one
			"SOURCEPATH ..\\dataRenamed\r\n" + 
			"START RESOURCE testblock.RSS\r\n" + 
			"	TARGETPATH resource\\apps\r\n" + 
			"	HEADER\r\n" + 
			"END\r\n" + 
			"AIF testaif.aif .. AifRenamed\\TestAif.rss c12 AifRenamed\\TestAif.rss\r\n" + 
			"START BITMAP test1.mbm\r\n" + 
			"	SOURCEPATH ..\\bmp\r\n" + 
			"	SOURCE c8 Test1.bmp TEST2.bmp test3.Bmp\r\n" + 
			"END\r\n" + 
			"DEFFILE Test.DEF\r\n" + 
			"SOURCEPATH ..\\DocRenamed\r\n" + 
			"DOCUMENT TestDoc.txt\r\n" + 
			"\r\n" + 
			"SOURCEPATH ..\\dataRenamed\r\n" + 
			"RESOURCE testrss.RSS\r\n" + 
			"SYSTEMRESOURCE TESTsystemrss.rss\r\n" + 
			"";
		
		commitTest(view, expText);
		
		assertEquals(new Path("dataRenamed/testrss.RSS"), view.getUserResources().get(0));
		assertEquals(new Path("dataRenamed/TESTsystemrss.rss"), view.getSystemResources().get(0));
		assertEquals(new Path("dataRenamed/testblock.RSS"), view.getResourceBlocks().get(0).getSource());
		assertEquals(new Path("DocRenamed/TestDoc.txt"), view.getDocuments().get(0));
		
		commitTest(view, expText);
	}
	
	/**
	 * Verify that we have a correct map of pp-nodes to language nodes.
	 * Here, crossing a document boundary and introducing problems are
	 * intended to expose problem areas.
	 */
	public void testPpToLangNodeMap() {
		String defText = "/*\r\n" + 
				"* ============================================================================\r\n" + 
				"*  Name     : CodDefs.h \r\n" + 
				"*  Part of  : COD Handler\r\n" + 
				"*\r\n" + 
				"*  Description:\r\n" + 
				"*      Constants used both in code and resources of COD Handler.   \r\n" + 
				"*      \r\n" + 
				"*  Version:\r\n" + 
				"*\r\n" + 
				"*  Copyright (C) 2002 Nokia Corporation.\r\n" + 
				"*  This material, including documentation and any related \r\n" + 
				"*  computer programs, is protected by copyright controlled by \r\n" + 
				"*  Nokia Corporation. All rights are reserved. Copying, \r\n" + 
				"*  including reproducing, storing,  adapting or translating, any \r\n" + 
				"*  or all of this material requires the prior written consent of \r\n" + 
				"*  Nokia Corporation. This material also contains confidential \r\n" + 
				"*  information which may not be disclosed to others without the \r\n" + 
				"*  prior written consent of Nokia Corporation.\r\n" + 
				"*\r\n" + 
				"* ============================================================================\r\n" + 
				"*/\r\n" + 
				"\r\n" + 
				"#ifndef COD_DEFS_H\r\n" + 
				"#define COD_DEFS_H\r\n" + 
				"\r\n" + 
				"// ***************************************************************************\r\n" + 
				"// **  THIS FILE IS INCLUDED IN RESOURCES (.rss) AND PROJECT FILES (.mmp).  **\r\n" + 
				"// **                     ONLY MACROS ARE ALLOWED.                          **\r\n" + 
				"// ***************************************************************************\r\n" + 
				"\r\n" + 
				"// MACROS\r\n" + 
				"\r\n" + 
				"/// COD MIME type.\r\n" + 
				"#define COD_MIME_TYPE                   \"text/x-co-desc\"\r\n" + 
				"\r\n" + 
				"/// DD MIME type.\r\n" + 
				"#define DD_MIME_TYPE                    \"application/vnd.oma.dd+xml\"\r\n" + 
				"\r\n" + 
				"/// DD2 MIME type.\r\n" + 
				"#define DD2_MIME_TYPE                    \"application/vnd.oma.dd2+xml\"\r\n" + 
				"\r\n" + 
				"/// COD Engine UID value.\r\n" + 
				"#define COD_ENGINE_UID                  0x10008d48\r\n" + 
				"\r\n" + 
				"/// COD UI UID value.\r\n" + 
				"#define COD_UI_UID                      0x101fd66f\r\n" + 
				"\r\n" + 
				"/// COD Viewer UID value.\r\n" + 
				"#define COD_VIEWER_UID                  0x10008d4a\r\n" + 
				"\r\n" + 
				"/// COD Recognizer UID value.\r\n" + 
				"#define COD_RECOG_UID                   0x10008d49\r\n" + 
				"\r\n" + 
				"/// DD Viewer UID value.\r\n" + 
				"#define DD_VIEWER_UID                   0x10008d3f\r\n" + 
				"\r\n" + 
				"/// DD Recognizer UID value.\r\n" + 
				"#define DD_RECOG_UID                    0x10008d55\r\n" + 
				"\r\n" + 
				"/// COD Download UID value.\r\n" + 
				"#define COD_DOWNLOAD_UID                0x101fd670\r\n" + 
				"\r\n" + 
				"// COD Attribute length limits. Use these as maximum size in UI controls\r\n" + 
				"// displaying COD attributes.\r\n" + 
				"\r\n" + 
				"/// COD-Name maximum length.\r\n" + 
				"#define COD_NAME_MAX_LEN                40\r\n" + 
				"/// COD-Vendor maximum length.\r\n" + 
				"#define COD_VENDOR_MAX_LEN              40\r\n" + 
				"/// COD-Description maximum length.\r\n" + 
				"#define COD_DESCRIPTION_MAX_LEN         160\r\n" + 
				"/// COD-URL maximum length.\r\n" + 
				"#define COD_URL_MAX_LEN                 1024\r\n" + 
				"/// COD-Size maximum length.\r\n" + 
				"#define COD_SIZE_MAX_LEN                6\r\n" + 
				"/// COD-Type maximum length.\r\n" + 
				"#define COD_TYPE_MAX_LEN                40\r\n" + 
				"/// COD-Install-Notify maximum length.\r\n" + 
				"#define COD_INSTALL_NOTIFY_MAX_LEN      1024\r\n" + 
				"/// COD-Next-URL maximum length.\r\n" + 
				"#define COD_NEXT_URL_MAX_LEN            1024\r\n" + 
				"/// COD-Next-URLatError maximum length.\r\n" + 
				"#define COD_NEXT_URL_AT_ERROR_MAX_LEN   1024\r\n" + 
				"/// COD-Info-URL maximum length.\r\n" + 
				"#define COD_INFO_URL_MAX_LEN            1024\r\n" + 
				"/// COD-Price maximum length.\r\n" + 
				"#define COD_PRICE_MAX_LEN               32\r\n" + 
				"/// COD-Icon maximum length.\r\n" + 
				"#define COD_ICON_MAX_LEN                1024\r\n" + 
				"/// COD-Version maximum length.\r\n" + 
				"#define COD_VERSION_MAX_LEN             40\r\n" + 
				"/// COD-License maximum length (OMA 2)\r\n" + 
				"#define COD_LICENSE_MAX_LEN             1024\r\n" + 
				"/// COD-Order maximum length (OMA 2)\r\n" + 
				"#define COD_ORDER_MAX_LEN               32\r\n" + 
				"\r\n" + 
				"#endif /* def COD_DEFS_H */\r\n" + 
				"";
		String text = "/*\r\n" + 
				"* ============================================================================\r\n" + 
				"*  Name     : \r\n" + 
				"*  Part of  : COD Handler\r\n" + 
				"*\r\n" + 
				"*  Description:\r\n" + 
				"*      Project specification for COD Viewer.   \r\n" + 
				"*      \r\n" + 
				"*  Version:\r\n" + 
				"*\r\n" + 
				"*  Copyright (C) 2002 Nokia Corporation.\r\n" + 
				"*  This material, including documentation and any related \r\n" + 
				"*  computer programs, is protected by copyright controlled by \r\n" + 
				"*  Nokia Corporation. All rights are reserved. Copying, \r\n" + 
				"*  including reproducing, storing,  adapting or translating, any \r\n" + 
				"*  or all of this material requires the prior written consent of \r\n" + 
				"*  Nokia Corporation. This material also contains confidential \r\n" + 
				"*  information which may not be disclosed to others without the \r\n" + 
				"*  prior written consent of Nokia Corporation.\r\n" + 
				"*\r\n" + 
				"* ============================================================================\r\n" + 
				"*/\r\n" + 
				"#include <domain\\osextensions\\platform_paths.hrh>\r\n" + 
				"#include <data_caging_paths.hrh>\r\n" + 
				"#include \"..\\..\\CodEng\\inc\\CodDefs.h\"\r\n" + 
				"\r\n" + 
				"TARGET          CodViewer.exe\r\n" + 
				"TARGETTYPE      exe\r\n" + 
				"EPOCSTACKSIZE   0x5000\r\n" + 
				"UID             0x100039ce COD_VIEWER_UID\r\n" + 
				"CAPABILITY      CAP_APPLICATION\r\n" + 
				"VENDORID        VID_DEFAULT\r\n" + 
				"\r\n" + 
				"SOURCEPATH      ..\\src\r\n" + 
				"\r\n" + 
				"SOURCE          CodViewer.cpp \r\n" + 
				"\r\n" + 
				"START RESOURCE ..\\data\\CodViewer.rss\r\n" + 
				"HEADER\r\n" + 
				"TARGETPATH APP_RESOURCE_DIR\r\n" + 
				"LANG SC\r\n" + 
				"END\r\n" + 
				"\r\n" + 
				"SYSTEMINCLUDE   ..\\..\\CodEng\\inc\r\n" + 
				"SYSTEMINCLUDE   ..\\..\\CodUi\\inc\r\n" + 
				"APP_LAYER_SYSTEMINCLUDE\r\n" + 
				"\r\n" + 
				"LIBRARY         euser.lib\r\n" + 
				"LIBRARY         eikcore.lib\r\n" + 
				"LIBRARY         apparc.lib\r\n" + 
				"LIBRARY         CodUi.lib\r\n" + 
				"\r\n" + 
				"START RESOURCE ..\\data\\CodViewer_reg.rss\r\n" + 
				"// Do not change the UID below.\r\n" + 
				"TARGETPATH \\private\\10003a3f\\apps\r\n" + 
				"END\r\n" + 
				"";
		// this intentionally has #include errors
		//parserConfig.getFilesystem().put("coddefs.h", defText);
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		commitTest(view, text);
		
		parserConfig.getFilesystem().put("coddefs.h", defText);
		makeModel(text);
		view = model.createView(mmpConfig);
		commitTest(view, text);
		
	}
	
	public void testBug3051() {
		String text = "EPOCHEAPSIZE 100 200\n";
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		view.getListArgumentSettings().get(EMMPStatement.EPOCHEAPSIZE).set(0, null);
		view.getListArgumentSettings().get(EMMPStatement.EPOCHEAPSIZE).set(1, null);
		try {
			commitTest(view, "");
		} catch (Throwable e) {
			// expected
		}
		
		makeModel(text);
		view = model.createView(mmpConfig);
		view.getListArgumentSettings().get(EMMPStatement.EPOCHEAPSIZE).clear();
		commitTest(view, "");
		
		makeModel(text);
		view = model.createView(mmpConfig);
		view.getListArgumentSettings().put(EMMPStatement.EPOCHEAPSIZE, null);
		commitTest(view, "");

		makeModel(text);
		view = model.createView(mmpConfig);
		view.getListArgumentSettings().remove(EMMPStatement.EPOCHEAPSIZE);
		commitTest(view, "");
	}
	
	public void testBug3058() {
		String text = 
			"#if MACRO\n"+
			"START BITMAP foo.mbm\n"+
			"#elif ALTMAC\n"+
			"START BITMAP foo.mbm\n"+
			"#else\n"+
			"START BITMAP bar.mbm\n"+
			"#endif\n"+
			"\tSOURCE c8 file.bmp\n"+
			"END\n"+
			"#ifdef ALTMAC\n"+
			"START RESOURCE file.rss\n"+
			"#else\n"+
			"START RESOURCE another.rss\n"+
			"#endif\n"+
			"\tHEADER\n"+
			"END\n";
		
		macros.add(DefineFactory.createDefine("ALTMAC"));
		makeModel(text);
		IMMPView view = model.createView(allConfig);
		checkNoProblems(view);
		assertFalse(errors[0]);
		
		assertEquals(1, view.getBitmaps().size());
		assertEquals(1, view.getResourceBlocks().size());
	}
	
	public void testBug3060() {
		String text = "SOURCE a.cpp\n"+
		"#if 0\n"+
		"!*@@\n"+
		"#endif\n"+
		"#ifdef 0\n"+
		"!idiot\n"+
		"#endif\n";
		
		makeModel(text);
		IMMPView view = model.createView(allConfig);
		commitTest(view, text);
		view = model.createView(mmpConfig);
		commitTest(view, text);
		
	}
	
	public void testBug3148() {
		// don't prepend "./"
		this.path = projectPath.append("test.mmp");
		String text = "SOURCE file.cpp\n";
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		assertEquals(new Path("file.cpp"), view.getSources().get(0));
		commitTest(view, text);
		
		String text2 = 
			"SOURCEPATH    .\r\n" + 
			"SOURCE        HelloWorld_Main.cpp\r\n" + 
			"SOURCE        HelloWorld_Application.cpp\r\n" + 
			"SOURCE        HelloWorld_Document.cpp\r\n" + 
			"SOURCE        HelloWorld_AppUi.cpp\r\n" + 
			"SOURCE        HelloWorld_AppView.cpp\r\n" + 
			"";
		
		makeModel(text2);
		view = model.createView(mmpConfig);
		assertEquals(new Path("HelloWorld_Main.cpp"), view.getSources().get(0));
		commitTest(view, text2);
		
	}
	
	public void testCapChanges() {
		String text = "SOURCEPATH ..\\DATA\n"+
		"SOURCE fUNKYfILE.cpp\n"+
		"USERINCLUDE ..\\InClUDe ..\\OtheR\n";
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		// this looks like a delete and add of sources, so the
		// sources move to the end
		view.getSources().set(0, new Path("data/FunkyFile.cpp"));
		view.getUserIncludes().set(0, new Path("include"));
		commitTest(view, 
				"USERINCLUDE ..\\include ..\\OtheR\n"+
				"\n"+
				"SOURCEPATH ..\\data\n"+
				"SOURCE FunkyFile.cpp\n"+
				""
				);
				
	}
	
	public void testBug3170() {
		String text = 
				"#define AI_UID3_AIFW_COMMON 0x102750F0\r\n" + 
				"#define AI_UID3_AIFW_EXE AI_UID3_AIFW_COMMON\r\n" + 
				"UID 0xABCDABCD AI_UID3_AIFW_EXE\r\n";
		
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		assertEquals("0xABCDABCD", view.getUid2());
		assertEquals("0x102750F0", view.getUid3());
		commitTest(view, 
				"#define AI_UID3_AIFW_COMMON 0x102750F0\r\n" + 
				"#define AI_UID3_AIFW_EXE AI_UID3_AIFW_COMMON\r\n" + 
				"UID 0xABCDABCD AI_UID3_AIFW_EXE\r\n"
				);
		
		view.setUid3("123");
		commitTest(view, 
				"#define AI_UID3_AIFW_COMMON 0x102750F0\r\n" + 
				"#define AI_UID3_AIFW_EXE AI_UID3_AIFW_COMMON\r\n" +
				"#if 0\r\n" + 
				"UID 0xABCDABCD AI_UID3_AIFW_EXE\r\n" + 
				"#endif\r\n" + 
				"UID 0xABCDABCD 123\r\n"
				);
	}
	
	public void testBug3171() {
		String text = 
			"#define AI_UID3_AIFW_COMMON 0x102750F0\r\n" + 
			"#define AI_UID3_AIFW_EXE AI_UID3_AIFW_COMMON\r\n" + 
			"#define AI_UID3_SIFW_EXE AI_UID3_AIFW_EXE\r\n" + 
			"SECUREID AI_UID3_SIFW_EXE\r\n";
	
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		assertEquals("0x102750F0", view.getSingleArgumentSettings().get(EMMPStatement.SECUREID));
		commitTest(view, 
				"#define AI_UID3_AIFW_COMMON 0x102750F0\r\n" + 
				"#define AI_UID3_AIFW_EXE AI_UID3_AIFW_COMMON\r\n" + 
				"#define AI_UID3_SIFW_EXE AI_UID3_AIFW_EXE\r\n" + 
				"SECUREID AI_UID3_SIFW_EXE\r\n"
				);
	}
	
	public void testBug3356() {
		String text = 
			"start wins\n"+
			"baseaddress 0x70600000\n"+
			"end\n";
		String text2 = 
			"start wins\n"+
			"baseaddress 0\n"+
			"end\n";
	
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		assertNull(view.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));
		commitTest(view, text); 
		
		macros.add(DefineFactory.createDefine("WINS"));
		makeModel(text);
		view = model.createView(mmpConfig);
		assertEquals("0x70600000", view.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));
		commitTest(view, text); 
		
		view.getSingleArgumentSettings().put(EMMPStatement.BASEADDRESS, "0");
		commitTest(view, text2); 
		
	}
	
	public void testBug3227() {
		String text = 
			"#if defined(WINS)\n"+
			"deffile .\\foo.def\n"+
			"#endif\n";
	
		macros.add(DefineFactory.createDefine("WINS"));
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		commitTest(view, text); 

	}
	
	public void testBug3318() {
		String text = 
			"START BITMAP foo.mbm\n"+
			"SOURCE c8,1 .\\..\\file.bmp ..\\file_mask.bmp\n"+
			"END\n";
	
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);
		IBitmapSource imageSource = (IBitmapSource) view.getBitmaps().get(0).getSources().get(0);
		assertEquals(new Path("file.bmp"), imageSource.getPath());
		assertEquals(new Path("file_mask.bmp"), imageSource.getMaskPath());
		commitTest(view, text); 
	}
	
	public void testBug3443() {
		// the essential problem is the include right at the top
		String inclText = "SYSTEMINCLUDE \\epoc32\\include\n";
		String text = "\r\n" + 
				"#include \"generic.mmh\"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"target a.exe\r\n" + 
				"targettype exe\r\n" + 
				"source a.cpp\r\n" + 
				"\r\n" + 
				"UID 0x12345678 0x12345678\r\n" + 
				"\r\n" + 
				"";

		String inclName = path.removeLastSegments(1).append("generic.mmh").toOSString();
		parserConfig.getFilesystem().put(inclName, inclText);
		makeModel(text);
		
		IMMPView view = model.createView(mmpConfig);
		commitTest(view, text);

	}

	public void testBug3552() {
		// tolerate the extra 'c'
		String text = 
				"start bitmap foo.mbm\n"+
				"source c8,c8 foo.bmp mask.bmp\n"+
				"end\n";

		makeModel(text);
		
		IMMPView view = model.createView(mmpConfig);
		
		IBitmapSource bitmap = view.getBitmaps().get(0).getBitmapSources().get(0);
		assertTrue(bitmap.isColor());
		assertEquals(8, bitmap.getDepth());
		assertEquals(8, bitmap.getMaskDepth());
		
		commitTest(view, text);
		
		bitmap = view.getBitmaps().get(0).getBitmapSources().get(0);
		bitmap.setMaskDepth(4);

		String text2 = 
			"START bitmap foo.mbm\n"+
			"\tSOURCE c8,4 foo.bmp mask.bmp\n"+
			"END\n";

		commitTest(view, text2);

	}
	
	public void testBug3552_2() {
		// tolerate totally broken stuff w/o crashing
		String text = 
				"start bitmap foo.mbm\n"+
				"source c8,bat foo.bmp mask.bmp\n"+
				"end\n";

		makeModel(text);
		
		IMMPView view = model.createView(mmpConfig);
		
		IBitmapSource bitmap = view.getBitmaps().get(0).getBitmapSources().get(0);
		
		// defaults for bad parse
		assertTrue(bitmap.isColor());
		assertEquals(32, bitmap.getDepth());
		assertEquals(32, bitmap.getMaskDepth());
		
		commitTest(view, text);

	}
	
	// check for duplications of the TARGET line
	public void testBug3494_macro() {
		String incl =
			"#define VariantTarget(name,ext) _h4hrp_##name##.##ext\r\n";
		String incl2 =
			"SOURCE foo.cpp\n";
		String text = 
			"// e32\\kernel\\ekern.mmp\r\n" + 
			"//\r\n" + 
			"// Copyright (c) 1997-2006 Symbian Software Ltd. All rights reserved.\r\n" + 
			"//\r\n" + 
			"\r\n" + 
			"/**\r\n" + 
			"@file\r\n" + 
			"\r\n" + 
			"@SYMPurpose ekern.exe Kernel\r\n" + 
			"*/\r\n" + 
			"\r\n" + 
			"systeminclude	/epoc32/include /epoc32/include/kernel\r\n" + 
			"\r\n" + 
			"#include <variant.mmh>\r\n" + 
			"\r\n" + 
			"target					VariantTarget(ekern,exe)\r\n" + 
			"#include \"ekern.mmh\"\r\n" + 
			"";
		
		parserConfig.getFilesystem().put("variant.mmh", incl);
		parserConfig.getFilesystem().put("ekern.mmh", incl2);
		
		makeModel(text);
		
		IMMPView view = model.createView(mmpConfig);
		commitTest(view, text);
		view.revert();
		commitTest(view, text);

	}
	
	public void testBugDuplications2() {
		// code was duplicated here
		String text = 
			"TARGET          Test.exe\r\n" + 
			"TARGETTYPE      EXE\r\n" + 
			"UID		0x100039CE 0xA000017F\r\n" + 
			"VENDORID      	0\r\n" + 
			"\r\n" + 
			"// Heap size 4K minimum, 3MB maximum\r\n" + 
			"EPOCHEAPSIZE 4096 3145728\r\n" + 
			"// 18K stack size\r\n" + 
			"EPOCSTACKSIZE 18432\r\n" + 
			"\r\n" + 
			"#include \"test.mmpi\"\r\n" + 
			"LIBRARY aknicon.lib\r\n" + 
			"\r\n";
		String incl = 
			"START BITMAP a.mbm\r\n" + 
			"\r\n" + 
			"	HEADER\r\n" + 
			"\r\n" + 
			"	TARGETPATH \\private\\0xA000017F\r\n" + 
			"\r\n" + 
			"	SOURCEPATH a\\b\r\n" + 
			"		SOURCE c16	somebitmapname1.bmp\r\n" + 
			"		SOURCE c16	somebitmapname2.bmp\r\n" + 
			"		SOURCE c16	somebitmapname3.bmp\r\n" + 
			"		SOURCE c16	somebitmapname4.bmp\r\n" + 
			"END\r\n" + 
			"\r\n" + 
			"START BITMAP b.mbm\r\n" + 
			"\r\n" + 
			"	HEADER\r\n" + 
			"\r\n" + 
			"	TARGETPATH \\private\\0xA000017F\r\n" + 
			"\r\n" + 
			"    	SOURCEPATH a\\c\r\n" + 
			"		SOURCE c16	somebitmapname6.bmp\r\n" + 
			"END\r\n";

		parserConfig.getFilesystem().put("test.mmpi", incl);
		
		makeModel(text);
		IMMPView view = model.createView(mmpConfig);

		// the checking of documents and paths is paranoia --
		// this wasn't the root cause.  Instead, it was that
		// the ppstmt-to-statement map was missing some nodes
		// due to the document change boundary.  The parser
		// will now validate this itself.
		IASTTranslationUnit tu = null;
		tu = ((ViewASTBase)view).getFilteredTranslationUnit();
		IDocument document = tu.getMainDocument();
		assertTrue(tu.getMainLocation().lastSegment().equals(path.lastSegment()));
		for (IASTNode node : tu.getNodes()) {
			if (!(node instanceof IASTMMPStartBlockStatement)) {
				assertEquals(document, node.getSourceRegion().getFirstDocument());
				assertEquals(tu.getMainLocation(), node.getSourceRegion().getFirstLocation());
			}
		}
		
		commitTest(view, text);
		view.revert();
		tu = ((ViewASTBase)view).getFilteredTranslationUnit();
		testSourceRegions(tu, true);
		commitTest(view, text);

	}
	
	public void testBug3597() {
		
		String text = "#define __ASSERT_DEBUG(c,p)\n" + 
				"__ASSERT_DEBUG(0, 0);\n"; 
		makeModel(text);
		IMMPView view = getView(allConfig);
		commitTest(view, text);
	}
	

	public void testSimplePPMapping() {
		String text = 
			"\n"+
			"// comment line\n"+
			"\n"+
			"#if 0\n"+
			"foo\n"+
			"#endif\n"+
			"\n"+
			"SOURCE foo.cpp\n"+
			"\n"+
			"// another comment\n";
		makeModel(text);
		IMMPView view = getView(allConfig);
		commitTest(view, text);
		
	}
	
	public void testMessageStatement() {
		makeModel("BASEADDRESS 0x100\n" +
				"MESSAGE This is broken!\n"+
				"MESSAGE \"Quoted something\"\n"
		);
		IMMPView view = model.createView(mmpConfig);
		assertEquals(2, view.getMessages().length);

		IMessage[] messages = view.getMessages();
		assertEquals(IMessage.WARNING, messages[0].getSeverity());
		assertEquals("This is broken!", messages[0].getMessage());
		assertEquals(IMessage.WARNING, messages[1].getSeverity());
		assertEquals("\"Quoted something\"", messages[1].getMessage());

	}
	
}
