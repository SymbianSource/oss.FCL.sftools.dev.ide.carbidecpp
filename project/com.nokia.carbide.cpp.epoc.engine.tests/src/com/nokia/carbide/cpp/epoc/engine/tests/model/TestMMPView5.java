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
package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMultiDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPTranslationUnit;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewASTBase;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.core.runtime.Path;

import java.util.*;

public class TestMMPView5 extends BaseMMPViewTest {
	
	/**
	 * when changing an item straddling an #include, 
	 * we comment out the original and rewrite from scratch
	 * (DocumentUpdater doesn't want to handle the multi-file thing)
	 */
	public void testMultiDocumentChange1() {
		String text = "" +
				"START BITMAP foo.mbm\n"+
				"#include \"contents.h\"\n"+
				"END\n";
		String text2 = "#if 0\n" + 
				"START BITMAP foo.mbm\n" + 
				"#include \"contents.h\"\n" + 
				"END\n" + 
				"#endif\n" + 
				"START BITMAP foo.mbm\n" + 
				"	SOURCE 8,1 file1.bmp file1_mask.bmp\n" + 
				"	SOURCE 1,1 icon.bmp icon_mask.bmp\n" + 
				"END\n" + 
				"";
		addRefFile(null, text, text2);
		
		String contents_h = 
			"SOURCE 8,1 file1.bmp file1_mask.bmp\n"+
			"SOURCE c32 picture.bmp\n"+
			"SOURCE 1,1 icon.bmp icon_mask.bmp\n";
		addRefFile("contents.h", contents_h, contents_h);
		
		makeModel();
		
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		// ensure the source ranges are as expected
		IASTMMPTranslationUnit tu = (IASTMMPTranslationUnit) ((ViewASTBase) view).getFilteredTranslationUnit();
		IDocumentSourceRegion[] documentSourceRegions = ((IMultiDocumentSourceRegion) tu.getSourceRegion()).getDocumentSourceRegions();
		assertEquals(3, documentSourceRegions.length);
		IASTMMPStatement stmt = (IASTMMPStatement) tu.getNodes().get(0);
		assertEquals("START BITMAP foo.mbm\n"+contents_h + "END\n",
				stmt.getOriginalText());
		
		
		view.getBitmaps().get(0).getSources().remove(1);
		
		runRefTest(view);
		
	}
	
	/**
	 * simple delete-from-header test
	 */
	public void  testDeleteFromHeader1() {
		String text = "" +
			"#include \"contents.h\"\n"+
			"";
		String text2 =
			"#include \"contents.h\"\n"+
			"";
		addRefFile(null, text, text2);
		
		String contents_h = 
			"START BITMAP foo.mbm\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"START BITMAP foo.mbm\n" + 
			"	SOURCE 8,1 file1.bmp file1_mask.bmp\n" + 
			"	SOURCE 1,1 icon.bmp icon_mask.bmp\n" + 
			"END\n" +
			"";
		String contents_h_2 = 
			"START BITMAP foo.mbm\n" + 
			"	SOURCE 8,1 file1.bmp file1_mask.bmp\n" + 
			"	SOURCE 1,1 icon.bmp icon_mask.bmp\n" + 
			"END\n" +
			"";
		addRefFile("contents.h", contents_h, contents_h_2);
		
		makeModel();
		
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		view.getBitmaps().remove(0);
		
		runRefTest(view);
	}
	
	/**
	 * ensure deleting from header is sensible
	 */
	public void  testDeleteFromHeader2() {
		// 
		String text = "" +
			"#include \"contents.h\"\n"+
			"DEFFILE a\n"+
			"";
		String text2 =
			"#include \"contents.h\"\n"+
			"DEFFILE a\n"+
			"";
		addRefFile(null, text, text2);
		
		String contents_h = 
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH b\n"+
			"END\n" +
			"";
		String contents_h_2 = 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH b\n"+
			"END\n" +
			"";
		addRefFile("contents.h", contents_h, contents_h_2);
		
		makeModel();
		
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		view.getResourceBlocks().remove(0);
		
		runRefTest(view);
	}

	/**
	 * reordering modification shouldn't occur if ordering not actually changed
	 */
	public void  testMultiDocumentNonReordering1() {
		String text = "" +
			"START RESOURCE first.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"#include \"contents.h\"\n"+
			"DEFFILE a\n"+
			"";
		addRefFile(null, text, text);
		
		String contents_h = 
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH b\n"+
			"END\n" +
			"";
		addRefFile("contents.h", contents_h, contents_h);
		
		makeModel();
		
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		
		runRefTest(view);
	}
	
	/**
	 * We don't reorder items across file boundaries, since this means the implicit
	 * sourcepath and file-relative references will break.  We only reorder within a file.
	 * NOT: by reordering an item, one of which is in a header, all items are moved to the main file
	 */
	public void testMultiDocumentReorder1() {
		// 
		String text = "" +
			"START RESOURCE first.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"#include \"contents.h\"\n"+
			"DEFFILE a\n"+
			"";
		/*String text2 = "" +
		"#include \"contents.h\"\n" + 
		"DEFFILE a\n" + 
		"START RESOURCE bar.rss\n" + 
		"	TARGETPATH b\n" + 
		"END\n" + 
		"START RESOURCE first.rss\n" + 
		"	TARGETPATH a\n" + 
		"END\n" + 
		"START RESOURCE foo.rss\n" + 
		"	TARGETPATH a\n" + 
		"END\n" + 
		"";*/
		addRefFile(null, text, text);
		
		String contents_h = 
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH b\n"+
			"END\n" +
			"";
		String contents_h_2 = 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH b\n"+
			"END\n" +
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"";
		addRefFile("contents.h", contents_h, contents_h_2);
		
		makeModel();
		
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		IMMPResource bar = view.getResourceBlocks().remove(2);
		view.getResourceBlocks().add(0, bar);
		
		runRefTest(view);
	}
	
	/**
	 * by reordering an item, one of which is in a header, all items are moved to the main file
	 */
	public void  testMultiDocumentReorder2() {
		String text = "" +
			"START RESOURCE first.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"#include \"contents.h\"\n"+
			"DEFFILE a\n"+
			"";
		/*String text2 = "" +
		"#include \"contents.h\"\n" + 
		"DEFFILE a\n" + 
		"START RESOURCE foo.rss\n" + 
		"	TARGETPATH a\n" + 
		"END\n" + 
		"START RESOURCE bar.rss\n" + 
		"	TARGETPATH b\n" + 
		"END\n" + 
		"START RESOURCE first.rss\n" + 
		"	TARGETPATH a\n" + 
		"END\n" + 
		"";*/
		addRefFile(null, text, text);
		
		String contents_h = 
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH b\n"+
			"END\n" +
			"";
		String contents_h_2 = 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH b\n"+
			"END\n" +
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"";
		addRefFile("contents.h", contents_h, contents_h_2);
		
		makeModel();
		
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		IMMPResource bar = view.getResourceBlocks().remove(2);
		IMMPResource foo = view.getResourceBlocks().remove(1);
		view.getResourceBlocks().add(0, foo);
		view.getResourceBlocks().add(0, bar);
		
		runRefTest(view);
	}

	/**
	 * by reordering an item, all of which is in a header,items stay in place
	 */
	public void  testMultiDocumentReorder3() {
		String text = "" +
			"START RESOURCE first.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"#include \"contents.h\"\n"+
			"DEFFILE a\n"+
			"";
		addRefFile(null, text, text);
		
		String contents_h = 
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH b\n"+
			"END\n" +
			"";
		String contents_h_2 = 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH b\n"+
			"END\n" +
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH a\n"+
			"END\n" + 
			"";
		addRefFile("contents.h", contents_h, contents_h_2);
		
		makeModel();
		
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		IMMPResource bar = view.getResourceBlocks().remove(2);
		view.getResourceBlocks().add(1, bar);
		
		runRefTest(view);
	}

	// This corollary to the same test in TestPreprocessor fails because the
	// space-before token flags were incorrectly handled.
	public void testBug5368() {
		String inclText =
			"#define CONFIG_DLL_PREFIX\r\n"+
			"#define MYTYPEDLL(NAME) _MYTYPEDLL(CONFIG_DLL_PREFIX, NAME )\r\n"+
			"#define _MYTYPEDLL(PREFIX, NAME) __MYTYPEDLL(PREFIX, NAME )\r\n"+
			"#define __MYTYPEDLL(PREFIX, NAME) PREFIX ## NAME\r\n" + 
			"";
		parserConfig.getFilesystem().put(projectPath.append("header.mmh").toOSString(), inclText);
		String text = 
			"#include \"header.mmh\"\r\n"+
			"TARGET     MYTYPEDLL(myappserver3.dll)\r\n";
		
		makeModel(text);
		
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
	
		assertEquals("myappserver3.dll", view.getSingleArgumentSettings().get(EMMPStatement.TARGET)); 
	
	}

	public void testBug5529() {
		// we were losing another space between TARGET and the macro expansion
		String inclText =
			"#define concatx(x1,x2,x3,x4,x5,x6,x7,x8) x1 ## x2 ## x3 ## x4 ## x5 ## x6 ## x7 ## x8\n"+
			"#define concat3(a,b,c) concatx(a,b,c,,,,,)\n"+
			"";
		parserConfig.getFilesystem().put(projectPath.append("header.mmh").toOSString(), inclText);
		String text = 
			"#include \"header.mmh\"\r\n"+
			"#define IVEDECODER_VAR 1\n"+
			"TARGET concat3( ivevideodecodehwdevice., IVEDECODER_VAR, .rsc )\r\n";
		
		makeModel(text);
		
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
	
		assertEquals("ivevideodecodehwdevice.1.rsc", view.getSingleArgumentSettings().get(EMMPStatement.TARGET)); 
	
	}

	// parallel to test in bldinf view ... 
	// MMP is a little smarter and doesn't add to lines inside conditionals
	public void testAddInsideConditionals() throws Exception {
		String text =
			"// (c) 2007\n"+
			"#ifdef EKA2\n"+
			"TARGETTYPE exe\n"+
			"TARGET myfile.exe\n"+
			"#else\n"+
			"TARGETTYPE dll\n"+
			"TARGET myfile.dll\n"+
			"#endif\n"+
			"SOURCEPATH ..\\src\n"+
			"#ifdef EKA2\n"+
			"SOURCE file.cpp mainexe.cpp\n"+
			"#else\n"+
			"SOURCE file.cpp maindll.cpp\n"+
			"#endif\n";
		String text2a =
			"// (c) 2007\n"+
			"#ifdef EKA2\n"+
			"TARGETTYPE exe\n"+
			"TARGET myfile.exe\n"+
			"#else\n"+
			"TARGETTYPE dll\n"+
			"TARGET myfile.dll\n"+
			"#endif\n"+
			"SOURCEPATH ..\\src\n"+
			"#ifdef EKA2\n"+
			"SOURCE file.cpp mainexe.cpp\n"+
			"#else\n"+
			"SOURCE file.cpp maindll.cpp\n"+
			"#endif\n"+
			"BASEADDRESS 0xA000\n"+
			"SOURCE foo.cpp\n"+
			"";
		String text2b =
			"// (c) 2007\n"+
			"#ifdef EKA2\n"+
			"TARGETTYPE exe\n"+
			"TARGET myfile.exe\n"+
			"#else\n"+
			"TARGETTYPE dll\n"+
			"TARGET myfile.dll\n"+
			"#endif\n"+
			"SOURCEPATH ..\\src\n"+
			"#ifdef EKA2\n"+
			"SOURCE file.cpp mainexe.cpp\n"+
			"#else\n"+
			"SOURCE file.cpp maindll.cpp\n"+
			"#endif\n"+
			"BASEADDRESS 0xA000\n"+
			"SOURCE foo.cpp\n"+
			"";
		IMMPView view;
		makeModel(text);
		macros.add(DefineFactory.createSimpleFreeformDefine("EKA2"));
		view = getView(mmpConfig);
		view.getSources().add(new Path("src/foo.cpp"));
		view.getSingleArgumentSettings().put(EMMPStatement.BASEADDRESS,
				"0xA000");
		view.commit();
		assertEquals(text2a, ((IOwnedModel) view.getModel()).getDocument()
				.get());
		IMessage[] messages = view.getMessages();
		assertEquals(0, messages.length);
		// /
		macros.clear();
		makeModel(text);
		view = getView(mmpConfig);
		view.getSources().add(new Path("src/foo.cpp"));
		view.getSingleArgumentSettings().put(EMMPStatement.BASEADDRESS,
				"0xA000");
		view.commit();
		assertEquals(text2b, ((IOwnedModel) view.getModel()).getDocument()
				.get());
		messages = view.getMessages();
		assertEquals(0, messages.length);
		checkMessages(messages);
	}
	
	public void testMissingLanguagesBug6590() {
		String text = 
			"LANG 01 02 1000 1001 33\n";
		IMMPView view;
		makeModel(text);
		view = getView(mmpConfig);
		List<EMMPLanguage> languages = view.getLanguages();
		Set<EMMPLanguage> seen = new HashSet<EMMPLanguage>();
		for (EMMPLanguage language : languages) {
			seen.add(language);
			String extension = ".R" + language.getCodeString(); //$NON-NLS-1$
			assertNotNull(extension);
		}
		// ensure the unknown languages don't abort processing
		assertEquals(3, seen.size());
	}
	
	public void testUnterminatedCrossDocumentParseBug7886() {
		String text =
			"START BITMAP foo.mbm\n" +
			"SOURCE c24,8 foo_44x44.bmp foo_mask_44x44.bmp foo_29x29.bmp foo_mask_29x29.bmp END\n\n" +
			"#include \"contents.h\"\n\n";
		
		addRefFile(null, text, null);
		
		String contents_h =
			"SOURCEPATH ..\\src\n" +
			"SOURCE  Application.cpp\n" +
			"SOURCE  AppUi.cpp\n" +
			"SOURCE  View.cpp\n" +
			"SOURCE  Container.cpp\n\n" +
			"START BITMAP bar.mbm\n" +
			"HEADER\n" +
			"SOURCEPATH ..\bitmaps\n" +
			"SOURCE 1 c.bmp\n" +
			"SOURCE 1 c_not.bmp\n" +
			"SOURCE 1 p.bmp\n" +
			"SOURCE 1 p_not.bmp\n" +
			"SOURCE 1 l.bmp\n" +
			"SOURCE 1 l_not.bmp\n" +
			"END\n\n";

		addRefFile("contents.h", contents_h, null);
		makeModel();
		
		getView(mmpConfig);
	}
}
