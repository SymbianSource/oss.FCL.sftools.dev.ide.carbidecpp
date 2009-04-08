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

import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.core.runtime.Path;

public class TestBldInfView4 extends BaseBldInfViewTest {

	// src/common/generic/syslibs/charconv/ongoing
	static String ongoingText = "PRJ_EXTENSIONS // this must be built *before* the MMPs depending on generated CPP files (which is most of them)\r\n" + 
			"\r\n" + 
			"// Extension makefiles - use the following to get abld to autogenerate cpp data files.\r\n" + 
			"// STEMS - the stem name used to resolve data and code files related to a plugin\r\n" + 
			"//         eg, gb2312 resolves to gb2312.ctl, gb2312.dat, g_gb2312.cpp, etc.\r\n" + 
			"// TYPE - used to specify if the data for this plugin should generate dat files,\r\n" + 
			"//        cpp files (default) or both\r\n" + 
			"// EXTRA_PARAMS - use this to specify any extra parameters that the convtool requires\r\n" + 
			"// EXTRA_DEP - extra make dependancy.  put in especially for gbk which is a superset\r\n" + 
			"//             that includes gb2312\r\n" + 
			"// CTL_EXT - allows different ctl files for dat and cpp generation. specifies extra\r\n" + 
			"//           characters to be appended to the ctl file stem name\r\n" + 
			"// When adding a new plugin, where possible add to existing \'start extension\' blocks\r\n" + 
			"\r\n" + 
			"	start extension syslibs/generate_cpp //cpp s for plugins and dat files for charconv.bat\r\n" + 
			"		option STEMS \\\r\n" + 
			"			gb2312 \\\r\n" + 
			"			gb12345 \\\r\n" + 
			"			big5 \\\r\n" + 
			"			iso88592 \\\r\n" + 
			"			iso88593 \\\r\n" + 
			"			iso88594 \\\r\n" + 
			"			iso88595 \\\r\n" + 
			"			iso88596 \\\r\n" + 
			"			iso88597 \\\r\n" + 
			"			iso88598 \\\r\n" + 
			"			iso88599 \\\r\n" + 
			"			iso885910 \\\r\n" + 
			"			iso885913 \\\r\n" + 
			"			iso885914 \\\r\n" + 
			"			iso885915\r\n" + 
			"		option TYPE all\r\n" + 
			"	end\r\n" + 
			"\r\n" + 
			"	start extension syslibs/generate_cpp //just cpp\r\n" + 
			"		option STEMS cp850\r\n" + 
			"	end\r\n" + 
			"\r\n" + 
			"	start extension syslibs/generate_cpp //just dat\r\n" + 
			"		option STEMS shiftjis iso88591 cp1252\r\n" + 
			"		option TYPE dat\r\n" + 
			"	end\r\n" + 
			"\r\n" + 
			"	start extension syslibs/generate_cpp //cpp with extra parameter to cnvtool\r\n" + 
			"		option STEMS jisx0201 jisx0212\r\n" + 
			"		option EXTRA_PARAMS -omitReplacementForUnconvertibleUnicodeCharacters\r\n" + 
			"	end\r\n" + 
			"\r\n" + 
			"	start extension syslibs/generate_cpp\r\n" + 
			"		option STEMS jisx0208\r\n" + 
			"		option EXTRA_PARAMS -columns(3: 2, 3) -omitReplacementForUnconvertibleUnicodeCharactersrs\r\n" + 
			"	end\r\n" + 
			"\r\n" + 
			"	start extension syslibs/generate_cpp //cpp with extra make dependancy etc.\r\n" + 
			"		option STEMS gbk\r\n" + 
			"		option EXTRA_DEP $(SOURCE_DIRECTORY)$/gb2312.txt\r\n" + 
			"		option EXTRA_PARAMS -cutOutAnyPrivateUseUnicodeCharacterSlotsBeingUsed -sourceFilesToSubtract($(EXTRA_DEP))\r\n" + 
			"	end\r\n" + 
			"\r\n" + 
			"	start extension syslibs/generate_cpp //dat using extended ctl file name\r\n" + 
			"		option STEMS gbk\r\n" + 
			"		option CTL_EXT f\r\n" + 
			"		option TYPE dat\r\n" + 
			"	end\r\n" + 
			"\r\n" + 
			"	start extension syslibs/generate_snm\r\n" + 
			"		TARGET	basic.snm\r\n" + 
			"		SOURCES	../data/snm/basic.txt\r\n" + 
			"	end\r\n" + 
			"";

	static String ongoingText1 = "PRJ_EXTENSIONS // this must be built *before* the MMPs depending on generated CPP files (which is most of them)\r\n" + 
	"\r\n" + 
	"// Extension makefiles - use the following to get abld to autogenerate cpp data files.\r\n" + 
	"// STEMS - the stem name used to resolve data and code files related to a plugin\r\n" + 
	"//         eg, gb2312 resolves to gb2312.ctl, gb2312.dat, g_gb2312.cpp, etc.\r\n" + 
	"// TYPE - used to specify if the data for this plugin should generate dat files,\r\n" + 
	"//        cpp files (default) or both\r\n" + 
	"// EXTRA_PARAMS - use this to specify any extra parameters that the convtool requires\r\n" + 
	"// EXTRA_DEP - extra make dependancy.  put in especially for gbk which is a superset\r\n" + 
	"//             that includes gb2312\r\n" + 
	"// CTL_EXT - allows different ctl files for dat and cpp generation. specifies extra\r\n" + 
	"//           characters to be appended to the ctl file stem name\r\n" + 
	"// When adding a new plugin, where possible add to existing \'start extension\' blocks\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //cpp s for plugins and dat files for charconv.bat\r\n" +
	// TODO: fix indentation loss for rewritten indented statements
	"option STEMS big5 iso88592 iso88593\r\n" + 
	"		option TYPE all\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //just cpp\r\n" + 
	"		option STEMS cp850\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //just dat\r\n" + 
	"		option STEMS shiftjis iso88591 cp1252\r\n" + 
	"		option TYPE dat\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //cpp with extra parameter to cnvtool\r\n" + 
	"		option STEMS jisx0201 jisx0212\r\n" + 
	"		option EXTRA_PARAMS -omitReplacementForUnconvertibleUnicodeCharacters\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp\r\n" + 
	"		option STEMS jisx0208\r\n" + 
	"		option EXTRA_PARAMS -columns(3: 2, 3) -omitReplacementForUnconvertibleUnicodeCharactersrs\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //cpp with extra make dependancy etc.\r\n" + 
	"		option STEMS gbk\r\n" + 
	"		option EXTRA_DEP $(SOURCE_DIRECTORY)$/gb2312.txt\r\n" + 
	"		option EXTRA_PARAMS -cutOutAnyPrivateUseUnicodeCharacterSlotsBeingUsed -sourceFilesToSubtract($(EXTRA_DEP))\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //dat using extended ctl file name\r\n" + 
	"		option STEMS gbk\r\n" + 
	"		option CTL_EXT f\r\n" + 
	"		option TYPE dat\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_snm\r\n" + 
	"		TARGET	basic.snm\r\n" + 
	"		SOURCES	../data/snm/basic.txt\r\n" + 
	"	end\r\n" + 
	"";

	static String ongoingText2 = "PRJ_EXTENSIONS // this must be built *before* the MMPs depending on generated CPP files (which is most of them)\r\n" + 
	"\r\n" + 
	"// Extension makefiles - use the following to get abld to autogenerate cpp data files.\r\n" + 
	"// STEMS - the stem name used to resolve data and code files related to a plugin\r\n" + 
	"//         eg, gb2312 resolves to gb2312.ctl, gb2312.dat, g_gb2312.cpp, etc.\r\n" + 
	"// TYPE - used to specify if the data for this plugin should generate dat files,\r\n" + 
	"//        cpp files (default) or both\r\n" + 
	"// EXTRA_PARAMS - use this to specify any extra parameters that the convtool requires\r\n" + 
	"// EXTRA_DEP - extra make dependancy.  put in especially for gbk which is a superset\r\n" + 
	"//             that includes gb2312\r\n" + 
	"// CTL_EXT - allows different ctl files for dat and cpp generation. specifies extra\r\n" + 
	"//           characters to be appended to the ctl file stem name\r\n" + 
	"// When adding a new plugin, where possible add to existing \'start extension\' blocks\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //cpp s for plugins and dat files for charconv.bat\r\n" +
	// TODO: fix indentation loss for rewritten indented statements
	"option STEMS big5 iso88592 iso88593\r\n" + 
	"		option TYPE all\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //just cpp\r\n" + 
	"		option STEMS cp850\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //just dat\r\n" + 
	"		option STEMS shiftjis iso88591 cp1252\r\n" + 
	"		option TYPE dat\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //cpp with extra parameter to cnvtool\r\n" + 
	"		option STEMS jisx0201 jisx0212\r\n" + 
	"		option EXTRA_PARAMS -omitReplacementForUnconvertibleUnicodeCharacters\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp\r\n" + 
	"		option STEMS jisx0208\r\n" + 
	"		option EXTRA_PARAMS -columns(3: 2, 3) -omitReplacementForUnconvertibleUnicodeCharactersrs\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //cpp with extra make dependancy etc.\r\n" + 
	"		option STEMS gbk\r\n" + 
	"		option EXTRA_DEP $(SOURCE_DIRECTORY)$/gb2312.txt\r\n" + 
	"		option EXTRA_PARAMS -cutOutAnyPrivateUseUnicodeCharacterSlotsBeingUsed -sourceFilesToSubtract($(EXTRA_DEP))\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension syslibs/generate_cpp //dat using extended ctl file name\r\n" + 
	"		option STEMS gbk\r\n" + 
	"		option CTL_EXT f\r\n" + 
	"		option TYPE dat\r\n" + 
	"	end\r\n" + 
	"\r\n" + 
	"	start extension foo/bar\r\n" + 
	"		TARGET	basic.snm\r\n" + 
	"		SOURCES	../data/snm/basic.txt\r\n" +
	"	end\r\n" + 
	"";

	public void testExtensionModel2() {
		String text= "PRJ_MMPFILES\n"+
		"first.mmp\n"+
		"\n"+
		"PRJ_TESTEXTENSIONS\r\n" + 
		"start       extension        base\\config\r\n" +
		"tool armcc\r\n"+
		"target zap_ma_ma\r\n"+
		"sources ..\\src\\file1.cpp sub\\file2.cpp\r\n"+
		"dependencies ..\\src\\file1 sub\\file2\r\n"+
		"\r\n" + 
		"end";
		
		makeModel(text);
		IBldInfView view = getView(config);
		checkNoProblems(view);
	
		_testExtensionModel2(view);
		_testExtensionModel2(view.getData());
		
		commitTest(view, text);
	}

	private void _testExtensionModel2(IBldInfData bldInfData) {
		assertEquals(0, bldInfData.getExtensions().size());
		assertEquals(1, bldInfData.getTestExtensions().size());
		IExtension ext = bldInfData.getTestExtensions().get(0);
		assertNotNull(ext);
		assertTrue(ext.isValid());
		
		assertEquals(new Path("base/config"), ext.getTemplatePath());
		assertEquals("armcc", ext.getToolName());
		assertEquals(new Path("zap_ma_ma\\"), ext.getTargetPath());
		assertNotNull(ext.getSources());
		assertEquals(2, ext.getSources().size());
		assertEquals(new Path("src/file1.cpp"), ext.getSources().get(0));
		assertEquals(new Path("group/sub/file2.cpp"), ext.getSources().get(1));
		
		assertNotNull(ext.getDependencies());
		assertEquals(2, ext.getDependencies().size());
		assertEquals(new Path("../src/file1"), ext.getDependencies().get(0));
		assertEquals(new Path("sub/file2"), ext.getDependencies().get(1));
	
		
		assertNotNull(ext.getOptions());
		assertEquals(0, ext.getOptions().size());
	}

	public void testExtensionModel1() {
		String text= "PRJ_MMPFILES\n"+
		"first.mmp\n"+
		"\n"+
		"PRJ_EXTENSIONS\r\n" + 
		"start       extension        base\\config\r\n" + 
		"\r\n" + 
		"option      PREFIX         _SH2_\r\n" + 
		"option      HALPATH        \\..\\..\\..\r\n" + 
		"option          SOURCE            \\..\\hal\r\n" + 
		"\r\n" + 
		"end";
		
		makeModel(text);
		IBldInfView view = getView(config);
		checkNoProblems(view);

		_testExtensionModel1(view);
		_testExtensionModel1(view.getData());
		
		commitTest(view, text);
	}

	private void _testExtensionModel1(IBldInfData bldInfData) {
		// extensions not combined here
		assertEquals(1, bldInfData.getMakMakeReferences().size());
		// extensions not counted here
		assertEquals(0, bldInfData.getAllMakefileReferences().length);
		
		assertEquals(1, bldInfData.getExtensions().size());
		assertEquals(0, bldInfData.getTestExtensions().size());
		IExtension ext = bldInfData.getExtensions().get(0);
		assertNotNull(ext);
		assertTrue(ext.isValid());
		
		assertEquals(new Path("base/config"), ext.getTemplatePath());
		assertNull(ext.getToolName());
		assertNull(ext.getTargetPath());
		assertNotNull(ext.getSources());
		assertEquals(0, ext.getSources().size());
		assertNotNull(ext.getDependencies());
		assertEquals(0, ext.getDependencies().size());
		assertNotNull(ext.getOptions());
		assertEquals(3, ext.getOptions().size());

		assertEquals("_SH2_", ext.getOptions().get("PREFIX"));
		assertEquals("\\..\\..\\..", ext.getOptions().get("HALPATH"));
		assertEquals("\\..\\hal", ext.getOptions().get("SOURCE"));
	}
	
	public void testExtensionModel3() {
		String text= "\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"../group/word.iby	/epoc32/rom/include/word.iby\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"../group/WORD.MMP\r\n" + 
				"\r\n" + 
				"PRJ_EXTENSIONS\r\n" + 
				"start extension syslibs/word_template\r\n" + 
				"end\r\n" +
				"PRJ_EXTENSIONS\r\n" + 
				"start extension app-services/buildstubsis\r\n" + 
				"	option SISNAME	timezone_stub\r\n" + 
				"	option SRCDIR	./	\r\n" + 
				"end\r\n" + 
				"";
		
		makeModel(text);
		IBldInfView view = getView(config);
		checkNoProblems(view);

		_testExtensionModel3(view);
		_testExtensionModel3(view.getData());
		commitTest(view, text);
	}

	private void _testExtensionModel3(IBldInfData bldInfData) {
		assertEquals(2, bldInfData.getExtensions().size());
		
		IExtension ext;
		
		ext = bldInfData.getExtensions().get(0);
		assertNotNull(ext);
		assertTrue(ext.isValid());
		
		assertEquals(new Path("syslibs/word_template"), ext.getTemplatePath());
		assertNull(ext.getToolName());
		assertNull(ext.getTargetPath());
		assertNotNull(ext.getSources());
		assertEquals(0, ext.getSources().size());
		
		assertNotNull(ext.getDependencies());
		assertEquals(0, ext.getDependencies().size());

		assertNotNull(ext.getOptions());
		assertEquals(0, ext.getOptions().size());
		
		///
		
		ext = bldInfData.getExtensions().get(1);
		assertNotNull(ext);
		assertTrue(ext.isValid());
		
		assertEquals(new Path("app-services/buildstubsis"), ext.getTemplatePath());
		assertNull(ext.getToolName());
		assertNull(ext.getTargetPath());
		assertNotNull(ext.getSources());
		assertEquals(0, ext.getSources().size());
		
		assertNotNull(ext.getDependencies());
		assertEquals(0, ext.getDependencies().size());

		assertNotNull(ext.getOptions());
		assertEquals(2, ext.getOptions().size());
		assertEquals("timezone_stub", ext.getOptions().get("SISNAME"));
		assertEquals("./", ext.getOptions().get("SRCDIR"));
	}
	
	public void testExtensionModel4() {
		makeModel(ongoingText);
		IBldInfView view = getView(config);
		
		checkNoProblems(view);
		
		_testExtensionModel4(view);
		_testExtensionModel4(view.getData());
		
		commitTest(view, ongoingText);
		
	}

	private void _testExtensionModel4(IBldInfData bldInfData) {
		assertEquals(8, bldInfData.getExtensions().size());
		
		IExtension ext;
		
		ext = bldInfData.getExtensions().get(0);
		assertNotNull(ext);
		assertTrue(ext.isValid());

		assertEquals("gb2312 gb12345 big5 iso88592 iso88593 iso88594 iso88595 iso88596 iso88597 iso88598 iso88599 iso885910 iso885913 iso885914 iso885915", 
				ext.getOptions().get("STEMS"));
		
		
		ext = bldInfData.getExtensions().get(4);
		assertNotNull(ext);
		assertTrue(ext.isValid());
		
		assertEquals("-columns(3: 2, 3) -omitReplacementForUnconvertibleUnicodeCharactersrs", 
				ext.getOptions().get("EXTRA_PARAMS"));
		
		ext = bldInfData.getExtensions().get(5);
		assertNotNull(ext);
		assertTrue(ext.isValid());
		
		assertEquals("$(SOURCE_DIRECTORY)$/gb2312.txt", 
				ext.getOptions().get("EXTRA_DEP"));
	}
	
	public void testModifyExtensionModel1() {
		makeModel(ongoingText);
		IBldInfView view = getView(config);
		
		checkNoProblems(view);
		
		assertEquals(8, view.getExtensions().size());
		
		IExtension ext;
		
		ext = view.getExtensions().get(0);
		assertNotNull(ext);
		assertTrue(ext.isValid());

		assertEquals("gb2312 gb12345 big5 iso88592 iso88593 iso88594 iso88595 iso88596 iso88597 iso88598 iso88599 iso885910 iso885913 iso885914 iso885915", 
				ext.getOptions().get("STEMS"));

		ext.getOptions().put("STEMS", "big5 iso88592 iso88593");
		
		commitTest(view, ongoingText1);

		//////
		
		ext = view.getExtensions().get(7);
		assertNotNull(ext);
		
		ext.setTemplatePath(new Path("foo/bar"));

		commitTest(view, ongoingText2);
		/////
		
	}
	
	public void testModifyExtensionModel2() {
		makeModel("");
		IBldInfView view = getView(config);
		
		checkNoProblems(view);
		
		assertEquals(0, view.getExtensions().size());
		
		IExtension ext;
		
		ext = view.createExtension();
		
		assertFalse(ext.isValid());
		
		ext.setTemplatePath(new Path("base/graphics/svg"));
		
		view.getExtensions().add(ext);
		
		commitTest(view,
				"PRJ_EXTENSIONS\n"+
				"START EXTENSION base/graphics/svg\n"+
				"END\n");
		
		/////
		
		ext = view.getExtensions().get(0);
		
		ext.getDependencies().add(new Path("../inc/foo"));

		commitTest(view,
				"PRJ_EXTENSIONS\n"+
				"START EXTENSION base/graphics/svg\n"+
				"DEPENDENCIES ../inc/foo\n"+
				"END\n");
		/////
		
		ext = view.getExtensions().get(0);
		
		ext.setToolName("armcc");

		commitTest(view,
				"PRJ_EXTENSIONS\n"+
				"START EXTENSION base/graphics/svg\n"+
				"DEPENDENCIES ../inc/foo\n"+
				"TOOL armcc\n"+
				"END\n");

		/////
		
		view.getExtensions().remove(0);

		commitTest(view,
				"PRJ_EXTENSIONS\n");
		
	}

	public void testModifyExtensionModel3() {
		makeModel("PRJ_EXTENSIONS\n"+
				"START EXTENSION base/graphics/svg\n"+
				"DEPENDENCIES ../inc/foo\n"+
				"END\n");
		IBldInfView view = getView(config);
		
		checkNoProblems(view);
		
		assertEquals(1, view.getExtensions().size());
		assertEquals(0, view.getTestExtensions().size());
		
		IExtension ext = view.getExtensions().remove(0);
		view.getTestExtensions().add(ext);
		commitTest(view,
				"PRJ_EXTENSIONS\n"+
				"PRJ_TESTEXTENSIONS\n"+
				"START EXTENSION base/graphics/svg\n"+
				"\tDEPENDENCIES ../inc/foo\n"+
				"END\n");
		
		ext = view.getTestExtensions().remove(0);
		view.getExtensions().add(ext);

		commitTest(view,
				"PRJ_EXTENSIONS\n" + 
				"START EXTENSION base/graphics/svg\n" + 
				"	DEPENDENCIES ../inc/foo\n" + 
				"END\n" + 
				"PRJ_TESTEXTENSIONS\n" + 
				"");
		
	}

	public void testSwitchExportsSections() {
		
		String text = "\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"..\\inc\\TSEngInterface.h APP_LAYER_DOMAIN_EXPORT_PATH( SecondaryDisplay\\TSEngInterface.h )\r\n" + 
				"\r\n" + 
				"..\\data\\telephonyserviceengine_stub.SIS\\\r\n" + 
				"    \\epoc32\\data\\z\\system\\install\\telephonyserviceengine_stub.SIS\r\n" + 
				"\r\n" + 
				"PRJ_TESTEXPORTS";
		
		makeModel(text);
		IBldInfView view = getView(config);
		
		checkNoProblems(view);
		
		assertEquals(2, view.getExports().size());
		assertEquals(0, view.getTestExports().size());
		
		IExport export = view.getExports().remove(0);
		view.getTestExports().add(export);
		
		// keep predom slash fmt
		commitTest(view,
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"\r\n" + 
				"..\\data\\telephonyserviceengine_stub.SIS\\\r\n" + 
				"    \\epoc32\\data\\z\\system\\install\\telephonyserviceengine_stub.SIS\r\n" + 
				"\r\n" + 
				"PRJ_TESTEXPORTS\r\n"+
				// TODO: add code to ensure we don't rewrite when moved
				"..\\inc\\TSEngInterface.h APP_LAYER_DOMAIN_EXPORT_PATH( SecondaryDisplay\\TSEngInterface.h )\r\n" + 
				"");
		
		export = view.getTestExports().remove(0);
		view.getExports().add(export);

		commitTest(view,
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"\r\n" + 
				"..\\data\\telephonyserviceengine_stub.SIS\\\r\n" + 
				"    \\epoc32\\data\\z\\system\\install\\telephonyserviceengine_stub.SIS\r\n" + 
				"..\\inc\\TSEngInterface.h APP_LAYER_DOMAIN_EXPORT_PATH( SecondaryDisplay\\TSEngInterface.h )\r\n" + 
				"\r\n" + 
				"PRJ_TESTEXPORTS\r\n" + 
				"");

	}
	
	public void testExtensionErrors() {
		IBldInfView view;
		IMessage[] messages ;
		makeModel("PRJ_EXTENSIONS\n"+
				"START barbitol base/graphics/svg\n"+
				"DEPENDENCIES ../inc/foo\n"+
				"END\n");

		view = getView(config);
		messages = view.getMessages();
		assertEquals(1, messages.length);
		checkMessages(messages);
		assertEquals(0, view.getExtensions().size());
		
		makeModel("PRJ_EXTENSIONS\n"+
				"START EXTENSION\n"+
				"DEPENDENCIES ../inc/foo\n"+
				"END\n"+
				"");

		view = getView(config);
		messages = view.getMessages();
		assertEquals(1, messages.length);
		checkMessages(messages);
		assertEquals(0, view.getExtensions().size());
		
		makeModel("PRJ_EXTENSIONS\n"+
				"START EXTENSION foo/bar\n"+
				"unknown\n"+
				"END\n"+
				"");

		view = getView(config);
		messages = view.getMessages();
		assertEquals(1, messages.length);
		checkMessages(messages);
		assertEquals(1, view.getExtensions().size());

	}
	
	public void testAllExtensions() {
		IBldInfView view;
		makeModel("PRJ_EXTENSIONS\n"+
				"START extension base/graphics/svg\n"+
				"DEPENDENCIES ../inc/foo\n"+
				"END\n"+
				"\n"+
				"PRJ_TESTEXTENSIONS\n"+
				"START extension base/foo\n"+
				"END\n"+
				"START extension base/foo2\n"+
				"END\n"+
				"");
		view = getView(config);
		_testAllExtensions(view);
		_testAllExtensions(view.getData());
	}

	private void _testAllExtensions(IBldInfData bldInfData) {
		assertEquals(2, bldInfData.getTestExtensions().size());
		assertEquals(1, bldInfData.getExtensions().size());
		IExtension[] extensions = bldInfData.getAllExtensions();
		assertEquals(3, extensions.length);
		assertEquals(bldInfData.getExtensions().get(0), extensions[0]);
		assertEquals(bldInfData.getTestExtensions().get(0), extensions[1]);
		assertEquals(bldInfData.getTestExtensions().get(1), extensions[2]);
	}
	
	public void testBug4935() {
		IBldInfView view;
		makeModel("PRJ_EXPORTS\n"+
				"..\\inc\\foo.h |..\\exports\\foo.h\n"+
				"");
		view = getView(config);
		assertEquals(1, view.getExports().size());
		IExport export = view.getExports().get(0);
		assertEquals(new Path("exports/foo.h"), export.getTargetPath());
		
		IExport export2 = view.createExport();
		export2.setSourcePath(new Path("group/file.exx"));
		export2.setTargetPath(new Path("README.txt"));
		view.getExports().add(export2);
		
		// keep predom slash fmt
		commitTest(view, "PRJ_EXPORTS\n"+
				"..\\inc\\foo.h |..\\exports\\foo.h\n"+
				"file.exx |..\\README.txt\n"+
				"");
	}
	
	public void testBug4144() {
		IBldInfView view;
		String text = "\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"ARM4 ARM4T ARMV4 ARMV5\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"rom\\kernel_fsl.iby                      \\epoc32\\rom\\mxc30030topaz\\                 //\r\n" + 
				"\r\n" + 
				"// CPA Log kernel\r\n" + 
				"\\test\\mxc_drivers\\cpa_log_kernel\\hdr\\cpa_log_kernel.h \\epoc32\\include\\cpa_log_kernel.h\r\n" + 
				"//..\\..\\mxc_drivers\\cpa_log_kernel\\hdr\\cpa_log_kernel.h \\epoc32\\include\\cpa_log_kernel.h\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"\r\n" + 
				"// CPA log kernel\r\n" + 
				"\\test\\drivers\\cpa_log_kernel.mmp\r\n" + 
				"//..\\..\\drivers\\cpa_log_kernel.mmp\r\n" + 
				"";
		String text2 = "\r\n" + 
		"PRJ_PLATFORMS\r\n" + 
		"ARM4 ARM4T ARMV4 ARMV5\r\n" + 
		"\r\n" + 
		"PRJ_EXPORTS\r\n" + 
		"rom\\kernel_fsl.iby                      \\epoc32\\rom\\mxc30030topaz\\                 //\r\n" + 
		"\r\n" + 
		"// CPA Log kernel\r\n" + 
		"\\test\\mxc_drivers\\cpa_log_kernel\\hdr\\cpa_log_kernel.h \\epoc32\\include\\cpa_log_kernel.h\r\n" + 
		"//..\\..\\mxc_drivers\\cpa_log_kernel\\hdr\\cpa_log_kernel.h \\epoc32\\include\\cpa_log_kernel.h\r\n" + 
		"\r\n" + 
		"PRJ_MMPFILES\r\n" + 
		"\r\n" + 
		"// CPA log kernel\r\n" + 
		"\\test\\drivers\\kernel.mmp\r\n" + 
		"//..\\..\\drivers\\cpa_log_kernel.mmp\r\n" + 
		"";
		
		makeModel(text);
		view = getView(config);
		assertEquals(1, view.getMakMakeReferences().size());
		IMakMakeReference mak = view.getMakMakeReferences().get(0);
		assertEquals(new Path("\\test\\drivers\\cpa_log_kernel.mmp"), mak.getPath());
		
		mak.setPath(new Path("\\test\\drivers\\kernel.mmp"));
		commitTest(view, text2);
	}
	public void testBug3696() {
		String text = "//\r\n" + 
				"// Copyright (c) 2005-2007 Symbian Software Ltd.  All rights reserved.\r\n" + 
				"//\r\n" + 
				"\r\n" + 
				"// include test code\r\n" + 
				"\r\n" + 
				"//Comment in or out the macro to select DBMS or SQLite implementations\r\n" + 
				"//#define DSCSTORE_SQL\r\n" + 
				"#include \"../test/group/bld.inf\"\r\n" + 
				"\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"../group/SysStart.IBY			/epoc32/rom/include/sysstart.iby\r\n" + 
				"../group/sysstartconfig.iby		/epoc32/rom/include/sysstartconfig.iby\r\n" + 
				"../inc/startup.hrh 				/epoc32/include/startup.hrh\r\n" + 
				"../inc/startup.rh 				/epoc32/include/startup.rh\r\n" + 
				"../dominc/StartUpDomainDefs.h	/epoc32/include/startupdomaindefs.h\r\n" + 
				"../inc/SysStartDefs.h			/epoc32/include/sysstartdefs.h\r\n" + 
				"\r\n" + 
				"#ifdef SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"../inc/startupproperties.h		/epoc32/include/startupproperties.h\r\n" + 
				"../inc/dscitem.h				/epoc32/include/dscitem.h\r\n" + 
				"../inc/dscstore.h				/epoc32/include/dscstore.h\r\n" + 
				"../inc/dscstoredefs.h			/epoc32/include/dscstoredefs.h\r\n" + 
				"../inc/sysmonclisess.h			/epoc32/include/sysmonclisess.h\r\n" + 
				"../inc/apastarter.h				/epoc32/include/apastarter.h\r\n" + 
				"\r\n" + 
				"../group/2000836d.spd	z:/private/100012a5/policy/2000836d.spd\r\n" + 
				"\r\n" + 
				"#endif // SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"#ifdef SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"\r\n" + 
				"../group/restartsys.mmp\r\n" + 
				"../group/startupproperties.mmp\r\n" + 
				"../group/sysmoncli.mmp\r\n" + 
				"../group/startsafe.mmp\r\n" + 
				"../group/sysmonsrv.mmp\r\n" + 
				"\r\n" + 
				"#ifdef DSCSTORE_SQL\r\n" + 
				"../group/dscstore_sql.mmp\r\n" + 
				"#else\r\n" + 
				"../group/dscstore.mmp\r\n" + 
				"#endif // DSCSTORE_SQL\r\n" + 
				"\r\n" + 
				"../group/amastartdll.mmp\r\n" + 
				"../group/amastartexe.mmp\r\n" + 
				"\r\n" + 
				"#endif // SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"";
		// in this version, the export is added but none of the macros are set;
		// thus, the #ifdef text is invisible and the export is added before
		String text2a = "//\r\n" + 
			"// Copyright (c) 2005-2007 Symbian Software Ltd.  All rights reserved.\r\n" + 
			"//\r\n" + 
			"\r\n" + 
			"// include test code\r\n" + 
			"\r\n" + 
			"//Comment in or out the macro to select DBMS or SQLite implementations\r\n" + 
			"//#define DSCSTORE_SQL\r\n" + 
			"#include \"../test/group/bld.inf\"\r\n" + 
			"\r\n" + 
			"PRJ_PLATFORMS\r\n" + 
			"DEFAULT\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"PRJ_EXPORTS\r\n" + 
			"../group/SysStart.IBY			/epoc32/rom/include/sysstart.iby\r\n" + 
			"../group/sysstartconfig.iby		/epoc32/rom/include/sysstartconfig.iby\r\n" + 
			"../inc/startup.hrh 				/epoc32/include/startup.hrh\r\n" + 
			"../inc/startup.rh 				/epoc32/include/startup.rh\r\n" + 
			"../dominc/StartUpDomainDefs.h	/epoc32/include/startupdomaindefs.h\r\n" + 
			"../inc/SysStartDefs.h			/epoc32/include/sysstartdefs.h\r\n" + 
			"../newExport.txt\r\n"+
			"\r\n" + 
			"#ifdef SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
			"../inc/startupproperties.h		/epoc32/include/startupproperties.h\r\n" + 
			"../inc/dscitem.h				/epoc32/include/dscitem.h\r\n" + 
			"../inc/dscstore.h				/epoc32/include/dscstore.h\r\n" + 
			"../inc/dscstoredefs.h			/epoc32/include/dscstoredefs.h\r\n" + 
			"../inc/sysmonclisess.h			/epoc32/include/sysmonclisess.h\r\n" + 
			"../inc/apastarter.h				/epoc32/include/apastarter.h\r\n" + 
			"\r\n" + 
			"../group/2000836d.spd	z:/private/100012a5/policy/2000836d.spd\r\n" + 
			"\r\n" + 
			"#endif // SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
			"\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"#ifdef SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
			"\r\n" + 
			"../group/restartsys.mmp\r\n" + 
			"../group/startupproperties.mmp\r\n" + 
			"../group/sysmoncli.mmp\r\n" + 
			"../group/startsafe.mmp\r\n" + 
			"../group/sysmonsrv.mmp\r\n" + 
			"\r\n" + 
			"#ifdef DSCSTORE_SQL\r\n" + 
			"../group/dscstore_sql.mmp\r\n" + 
			"#else\r\n" + 
			"../group/dscstore.mmp\r\n" + 
			"#endif // DSCSTORE_SQL\r\n" + 
			"\r\n" + 
			"../group/amastartdll.mmp\r\n" + 
			"../group/amastartexe.mmp\r\n" + 
			"\r\n" + 
			"#endif // SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
			"";
		// in this version, the export is added and the macro is set;
		// thus, the #ifdef text is visible.  The export is added outside 
		// the conditional block.
		String text2b = "//\r\n" + 
				"// Copyright (c) 2005-2007 Symbian Software Ltd.  All rights reserved.\r\n" + 
				"//\r\n" + 
				"\r\n" + 
				"// include test code\r\n" + 
				"\r\n" + 
				"//Comment in or out the macro to select DBMS or SQLite implementations\r\n" + 
				"//#define DSCSTORE_SQL\r\n" + 
				"#include \"../test/group/bld.inf\"\r\n" + 
				"\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"../group/SysStart.IBY			/epoc32/rom/include/sysstart.iby\r\n" + 
				"../group/sysstartconfig.iby		/epoc32/rom/include/sysstartconfig.iby\r\n" + 
				"../inc/startup.hrh 				/epoc32/include/startup.hrh\r\n" + 
				"../inc/startup.rh 				/epoc32/include/startup.rh\r\n" + 
				"../dominc/StartUpDomainDefs.h	/epoc32/include/startupdomaindefs.h\r\n" + 
				"../inc/SysStartDefs.h			/epoc32/include/sysstartdefs.h\r\n" + 
				"\r\n" + 
				"#ifdef SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"../inc/startupproperties.h		/epoc32/include/startupproperties.h\r\n" + 
				"../inc/dscitem.h				/epoc32/include/dscitem.h\r\n" + 
				"../inc/dscstore.h				/epoc32/include/dscstore.h\r\n" + 
				"../inc/dscstoredefs.h			/epoc32/include/dscstoredefs.h\r\n" + 
				"../inc/sysmonclisess.h			/epoc32/include/sysmonclisess.h\r\n" + 
				"../inc/apastarter.h				/epoc32/include/apastarter.h\r\n" + 
				"\r\n" + 
				"../group/2000836d.spd	z:/private/100012a5/policy/2000836d.spd\r\n" + 
				"\r\n" + 
				"#endif // SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"../newExport.txt\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"#ifdef SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"\r\n" + 
				"../group/restartsys.mmp\r\n" + 
				"../group/startupproperties.mmp\r\n" + 
				"../group/sysmoncli.mmp\r\n" + 
				"../group/startsafe.mmp\r\n" + 
				"../group/sysmonsrv.mmp\r\n" + 
				"\r\n" + 
				"#ifdef DSCSTORE_SQL\r\n" + 
				"../group/dscstore_sql.mmp\r\n" + 
				"#else\r\n" + 
				"../group/dscstore.mmp\r\n" + 
				"#endif // DSCSTORE_SQL\r\n" + 
				"\r\n" + 
				"../group/amastartdll.mmp\r\n" + 
				"../group/amastartexe.mmp\r\n" + 
				"\r\n" + 
				"#endif // SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"";
		// in this version, an mmp is added and the macro is set;
		// thus, the #ifdef text is visible.  There is no next block so
		// the mmp is added at the end.
		String text2c = "//\r\n" + 
				"// Copyright (c) 2005-2007 Symbian Software Ltd.  All rights reserved.\r\n" + 
				"//\r\n" + 
				"\r\n" + 
				"// include test code\r\n" + 
				"\r\n" + 
				"//Comment in or out the macro to select DBMS or SQLite implementations\r\n" + 
				"//#define DSCSTORE_SQL\r\n" + 
				"#include \"../test/group/bld.inf\"\r\n" + 
				"\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"../group/SysStart.IBY			/epoc32/rom/include/sysstart.iby\r\n" + 
				"../group/sysstartconfig.iby		/epoc32/rom/include/sysstartconfig.iby\r\n" + 
				"../inc/startup.hrh 				/epoc32/include/startup.hrh\r\n" + 
				"../inc/startup.rh 				/epoc32/include/startup.rh\r\n" + 
				"../dominc/StartUpDomainDefs.h	/epoc32/include/startupdomaindefs.h\r\n" + 
				"../inc/SysStartDefs.h			/epoc32/include/sysstartdefs.h\r\n" + 
				"\r\n" + 
				"#ifdef SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"../inc/startupproperties.h		/epoc32/include/startupproperties.h\r\n" + 
				"../inc/dscitem.h				/epoc32/include/dscitem.h\r\n" + 
				"../inc/dscstore.h				/epoc32/include/dscstore.h\r\n" + 
				"../inc/dscstoredefs.h			/epoc32/include/dscstoredefs.h\r\n" + 
				"../inc/sysmonclisess.h			/epoc32/include/sysmonclisess.h\r\n" + 
				"../inc/apastarter.h				/epoc32/include/apastarter.h\r\n" + 
				"\r\n" + 
				"../group/2000836d.spd	z:/private/100012a5/policy/2000836d.spd\r\n" + 
				"\r\n" + 
				"#endif // SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"#ifdef SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"\r\n" + 
				"../group/restartsys.mmp\r\n" + 
				"../group/startupproperties.mmp\r\n" + 
				"../group/sysmoncli.mmp\r\n" + 
				"../group/startsafe.mmp\r\n" + 
				"../group/sysmonsrv.mmp\r\n" + 
				"\r\n" + 
				"#ifdef DSCSTORE_SQL\r\n" + 
				"../group/dscstore_sql.mmp\r\n" + 
				"#else\r\n" + 
				"../group/dscstore.mmp\r\n" + 
				"#endif // DSCSTORE_SQL\r\n" + 
				"\r\n" + 
				"../group/amastartdll.mmp\r\n" + 
				"../group/amastartexe.mmp\r\n" + 
				"\r\n" + 
				"#endif // SYMBIAN_PROCESS_MONITORING_AND_STARTUP\r\n" + 
				"\r\n" + 
				"newmmp.mmp\r\n"+
				"";
		String inclFile = "//\r\n" + 
				"// Copyright (c) Symbian Software Ltd 2005-2007.  All rights reserved.\r\n" + 
				"//\r\n" + 
				"\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"// test code only\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"// test code only\r\n" + 
				"\r\n" + 
				"PRJ_TESTMMPFILES\r\n" + 
				"appfwk_sysstart_test_appUtils.mmp\r\n" + 
				"appfwk_sysstart_test_app1.mmp\r\n" + 
				"appfwk_sysstart_test_app2.mmp\r\n" + 
				"appfwk_sysstart_test_app3.mmp\r\n" + 
				"appfwk_sysstart_test_app4.mmp\r\n" + 
				"appfwk_sysstart_test_app5.mmp\r\n" + 
				"appfwk_sysstart_test_app6.mmp\r\n" + 
				"appfwk_sysstart_test_app7.mmp\r\n" + 
				"appfwk_sysstart_test_app8.mmp\r\n" + 
				"appfwk_sysstart_test_app9.mmp\r\n" + 
				"appfwk_sysstart_test_app10.mmp\r\n" + 
				"appfwk_sysstart_test_app11.mmp\r\n" + 
				"appfwk_sysstart_test_app12.mmp\r\n" + 
				"appfwk_sysstart_test_app13.mmp\r\n" + 
				"appfwk_sysstart_test_app14.mmp\r\n" + 
				"appfwk_sysstart_test_app15.mmp\r\n" + 
				"appfwk_sysstart_test_app16.mmp\r\n" + 
				"appfwk_sysstart_test_app17.mmp\r\n" + 
				"appfwk_sysstart_test_app18.mmp\r\n" + 
				"appfwk_sysstart_test_app19.mmp\r\n" + 
				"appfwk_sysstart_test_app20.mmp\r\n" + 
				"appfwk_sysstart_test_app21.mmp\r\n" + 
				"appfwk_sysstart_test_app22.mmp\r\n" + 
				"appfwk_sysstart_test_app23.mmp\r\n" + 
				"appfwk_sysstart_test_app24.mmp\r\n" + 
				"appfwk_sysstart_test_app25.mmp\r\n" + 
				"appfwk_sysstart_test_dll.mmp\r\n" + 
				"appfwk_sysstart_test_server.mmp\r\n" + 
				"appfwk_sysstart_test_stfp.mmp\r\n" + 
				"\r\n" + 
				"PRJ_TESTEXPORTS\r\n" + 
				"../inc/appfwk_sysstart_test_dll.h  \r\n" + 
				"../inc/appfwk_sysstart_test_dll.rh\r\n" + 
				"\r\n" + 
				"// SysStart test IBY containing scripts for test execution on hardware\r\n" + 
				"../rom/appfwk_sysstart_test.iby                              /epoc32/rom/include/sysstarttest.iby\r\n" + 
				"\r\n" + 
				"// SysStart batch files for hardware test execution\r\n" + 
				"../scripts/hardware/sysstarttest_run.bat                     /epoc32/data/z/sysstarttest/sysstarttest_run.bat\r\n" + 
				"../scripts/hardware/sysstarttest_buildrom.bat                /epoc32/data/z/sysstarttest/sysstarttest_buildrom.bat\r\n" + 
				"../scripts/hardware/sysstarttest_checkEpocWind.bat           /epoc32/data/z/sysstarttest/sysstarttest_checkepocwind.bat\r\n" + 
				"../scripts/hardware/sysstarttest_insertlog.pl                /epoc32/data/z/sysstarttest/sysstarttest_insertlog.pl\r\n" + 
				"\r\n" + 
				"// SysStart batch files for automated emulator (WINSCW UDEB) test execution \r\n" + 
				"../scripts/emulator/sysstarttest_run.bat                     /epoc32/release/winscw/udeb/sysstarttest_run.bat \r\n" + 
				"../scripts/emulator/sysstarttest_setup.bat                   /epoc32/release/winscw/udeb/z/sysstarttest/sysstarttest_setup.bat\r\n" + 
				"../scripts/emulator/sysstarttest_checkEpocWind.bat           /epoc32/release/winscw/udeb/z/sysstarttest/sysstarttest_checkepocwind.bat\r\n" + 
				"\r\n" + 
				"// SysStart batch files for automated emulator (WINSCW UREL) test execution \r\n" + 
				"../scripts/emulator/sysstarttest_run.bat                     /epoc32/release/winscw/urel/sysstarttest_run.bat \r\n" + 
				"../scripts/emulator/sysstarttest_setup.bat                   /epoc32/release/winscw/urel/z/sysstarttest/sysstarttest_setup.bat\r\n" + 
				"../scripts/emulator/sysstarttest_checkEpocWind.bat           /epoc32/release/winscw/urel/z/sysstarttest/sysstarttest_checkepocwind.bat\r\n" + 
				"\r\n" + 
				"// Start App test scripts\r\n" + 
				"../scripts/appfwk_sysstart_test_AppFailRetry0Ignore.script            z:/sysstarttest/sysstarttest_appfailretry0ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_AppFailRetry0Panic.script             z:/sysstarttest/sysstarttest_appfailretry0panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_AppFailRetry1Ignore.script            z:/sysstarttest/sysstarttest_appfailretry1ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_AppFailRetry1Panic.script             z:/sysstarttest/sysstarttest_appfailretry1panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_AppStartCd.script                     z:/sysstarttest/sysstarttest_appstartcd.script\r\n" + 
				"../scripts/appfwk_sysstart_test_AppStartCs.script                     z:/sysstarttest/sysstarttest_appstartcs.script\r\n" + 
				"../scripts/appfwk_sysstart_test_AppStartFailPanic.script              z:/sysstarttest/sysstarttest_appstartfailpanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_AppStartNc.script                     z:/sysstarttest/sysstarttest_appstartnc.script\r\n" + 
				"../scripts/appfwk_sysstart_test_AppTimeoutRetry3Ignore.script         z:/sysstarttest/sysstarttest_apptimeoutretry3ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_AppTimeoutRetry3Panic.script          z:/sysstarttest/sysstarttest_apptimeoutretry3panic.script\r\n" + 
				"\r\n" + 
				"// EDeferredWaitForStart test scripts\r\n" + 
				"../scripts/appfwk_sysstart_test_DefInvalidAppRetry.script             z:/sysstarttest/sysstarttest_definvalidappretry.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefInvalidAppTimeout.script           z:/sysstarttest/sysstarttest_definvalidapptimeout.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefInvalidMultFail.script             z:/sysstarttest/sysstarttest_definvalidmultfail.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefInvalidMultWait.script             z:/sysstarttest/sysstarttest_definvalidmultwait.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefInvalidProcRetry.script            z:/sysstarttest/sysstarttest_definvalidprocretry.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefInvalidProcTimeout.script          z:/sysstarttest/sysstarttest_definvalidproctimeout.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefMultGrp.script                     z:/sysstarttest/sysstarttest_defmultgrp.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefNoMult1.script                     z:/sysstarttest/sysstarttest_defnomult1.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefNoMult2.script                     z:/sysstarttest/sysstarttest_defnomult2.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefNoMult3.script                     z:/sysstarttest/sysstarttest_defnomult3.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefRendFailAppPanic.script            z:/sysstarttest/sysstarttest_defrendfailapppanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefRendFailIgnore.script              z:/sysstarttest/sysstarttest_defrendfailignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefRendFailProcPanic.script           z:/sysstarttest/sysstarttest_defrendfailprocpanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefStartAllState.script               z:/sysstarttest/sysstarttest_defstartallstate.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefStartCd.script                     z:/sysstarttest/sysstarttest_defstartcd.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefStartCs.script                     z:/sysstarttest/sysstarttest_defstartcs.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefStartFailAppPanic.script           z:/sysstarttest/sysstarttest_defstartfailapppanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefStartFailIgnore.script             z:/sysstarttest/sysstarttest_defstartfailignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefStartFailProcPanic.script          z:/sysstarttest/sysstarttest_defstartfailprocpanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefStartNc.script                     z:/sysstarttest/sysstarttest_defstartnc.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefTimeoutAppIgnore.script            z:/sysstarttest/sysstarttest_deftimeoutappignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefTimeoutAppPanic.script             z:/sysstarttest/sysstarttest_deftimeoutapppanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefTimeoutProcIgnore.script           z:/sysstarttest/sysstarttest_deftimeoutprocignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DefTimeoutProcPanic.script            z:/sysstarttest/sysstarttest_deftimeoutprocpanic.script\r\n" + 
				"\r\n" + 
				"// Dll Function call test scripts\r\n" + 
				"../scripts/appfwk_sysstart_test_DllCdFailIgnore.script                z:/sysstarttest/sysstarttest_dllcdfailignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllCdFailPanic.script                 z:/sysstarttest/sysstarttest_dllcdfailpanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllCdNormal.script                    z:/sysstarttest/sysstarttest_dllcdnormal.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllCsFailIgnore.script                z:/sysstarttest/sysstarttest_dllcsfailignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllCsFailPanic.script                 z:/sysstarttest/sysstarttest_dllcsfailpanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllCsFnInvalidFail.script             z:/sysstarttest/sysstarttest_dllcsfninvalidfail.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllCsFnMultipleRetry.script           z:/sysstarttest/sysstarttest_dllcsfnmultipleretry.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllCsNormal.script                    z:/sysstarttest/sysstarttest_dllcsnormal.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllFnSharedData.script                z:/sysstarttest/sysstarttest_dllfnshareddata.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidCustomData.script           z:/sysstarttest/sysstarttest_dllinvalidcustomdata.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidOrdinal0.script             z:/sysstarttest/sysstarttest_dllinvalidordinal0.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidOrdinalRetry0Ignore.script  z:/sysstarttest/sysstarttest_dllinvalidordinalretry0ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidOrdinalRetry1Ignore.script  z:/sysstarttest/sysstarttest_dllinvalidordinalretry1ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidOrdinalRetry0Panic.script   z:/sysstarttest/sysstarttest_dllinvalidordinalretry0panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidOrdinalRetry1Panic.script   z:/sysstarttest/sysstarttest_dllinvalidordinalretry1panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidRetry0Ignore.script         z:/sysstarttest/sysstarttest_dllinvalidretry0ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidRetry0Panic.script          z:/sysstarttest/sysstarttest_dllinvalidretry0panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidRetry1Ignore.script         z:/sysstarttest/sysstarttest_dllinvalidretry1ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllInvalidRetry1Panic.script          z:/sysstarttest/sysstarttest_dllinvalidretry1panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllNcFailIgnore.script                z:/sysstarttest/sysstarttest_dllncfailignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllNcFailPanic.script                 z:/sysstarttest/sysstarttest_dllncfailpanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_DllNcNormal.script                    z:/sysstarttest/sysstarttest_dllncnormal.script\r\n" + 
				"\r\n" + 
				"// INIT_APPARC test scripts\r\n" + 
				"../scripts/appfwk_sysstart_test_InitApparc.script		      z:/sysstarttest/sysstarttest_initapparc.script\r\n" + 
				"../scripts/appfwk_sysstart_test_InitApparcBeforeServer.script	      z:/sysstarttest/sysstarttest_initapparcbeforeserver.script\r\n" + 
				"\r\n" + 
				"// Start Proc test scripts\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcFailRetry0Ignore.script           z:/sysstarttest/sysstarttest_procfailretry0ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcFailRetry0Panic.script            z:/sysstarttest/sysstarttest_procfailretry0panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcFailRetry1Ignore.script           z:/sysstarttest/sysstarttest_procfailretry1ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcFailRetry1Panic.script            z:/sysstarttest/sysstarttest_procfailretry1panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcStartCd.script                    z:/sysstarttest/sysstarttest_procstartcd.script\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcStartCs.script                    z:/sysstarttest/sysstarttest_procstartcs.script\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcStartFailPanic.script             z:/sysstarttest/sysstarttest_procstartfailpanic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcStartNc.script                    z:/sysstarttest/sysstarttest_procstartnc.script\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcTimeoutRetry3Ignore.script        z:/sysstarttest/sysstarttest_proctimeoutretry3ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_ProcTimeoutRetry3Panic.script         z:/sysstarttest/sysstarttest_proctimeoutretry3panic.script\r\n" + 
				"\r\n" + 
				"// SysStart re-running protection test\r\n" + 
				"../scripts/appfwk_sysstart_test_ReRunSysStart.script                  z:/sysstarttest/sysstarttest_rerunsysstart.script\r\n" + 
				"\r\n" + 
				"// State Transition Policy Failure test scripts\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCdToNcDefault.script              z:/sysstarttest/sysstarttest_stfpcdtoncdefault.script\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCdToNcRetry0Ignore.script         z:/sysstarttest/sysstarttest_stfpcdtoncretry0ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCdToNcRetry0Panic.script          z:/sysstarttest/sysstarttest_stfpcdtoncretry0panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCdToNcRetry1Ignore.script         z:/sysstarttest/sysstarttest_stfpcdtoncretry1ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCdToNcRetry1Panic.script          z:/sysstarttest/sysstarttest_stfpcdtoncretry1panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCsToCdDefault.script              z:/sysstarttest/sysstarttest_stfpcstocddefault.script\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCsToCdRetry0Ignore.script         z:/sysstarttest/sysstarttest_stfpcstocdretry0ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCsToCdRetry0Panic.script          z:/sysstarttest/sysstarttest_stfpcstocdretry0panic.script\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCsToCdRetry1Ignore.script         z:/sysstarttest/sysstarttest_stfpcstocdretry1ignore.script\r\n" + 
				"../scripts/appfwk_sysstart_test_StfpCsToCdRetry1Panic.script          z:/sysstarttest/sysstarttest_stfpcstocdretry1panic.script\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"";
		String inclFileName = projectPath.append("../test/group/bld.inf")
				.toOSString();
		parserConfig.getFilesystem().put(inclFileName, inclFile);
		makeModel(text);
		IBldInfView view = getView(config);
		commitTest(view, text);
		checkNoProblems(view);
		// ///
		view = getView(config);
		IExport newExport = view.createExport();
		newExport.setSourcePath(new Path("newExport.txt"));
		view.getExports().add(newExport);
		commitTest(view, text2a);
		checkNoProblems(view);
		assertEquals(inclFile, parserConfig.getFilesystem().get(inclFileName));
		// ///
		makeModel(text);
		macros
				.add(DefineFactory
						.createSimpleFreeformDefine("SYMBIAN_PROCESS_MONITORING_AND_STARTUP"));
		view = getView(config);
		newExport = view.createExport();
		newExport.setSourcePath(new Path("newExport.txt"));
		view.getExports().add(newExport);
		view.commit();
		assertEquals(text2b, ((IOwnedModel) view.getModel()).getDocument()
				.get());
		IMessage[] messages = view.getMessages();
		assertEquals(0, messages.length);
		checkMessages(messages);
		assertEquals(inclFile, parserConfig.getFilesystem().get(inclFileName));
		// ///
		makeModel(text);
		view = getView(config);
		IMMPReference newMmp;
		newMmp = view.createMMPReference();
		newMmp.setPath(new Path("group/newmmp.mmp"));
		view.getMakMakeReferences().add(newMmp);
		view.commit();
		assertEquals(text2c, ((IOwnedModel) view.getModel()).getDocument()
				.get());
		messages = view.getMessages();
		assertEquals(0, messages.length);
		checkMessages(messages);
		assertEquals(inclFile, parserConfig.getFilesystem().get(inclFileName));
	}
	// the add would look like it goes inside a conditional
	// but we force it outside
	public void testAddInsideConditionals() throws Exception {
		String text = "PRJ_PLATFORMS\n" + "WINSCW\n" + "\n"
				+ "#ifdef FEATURE\n" + "PRJ_MMPFILES\n" + "test1.mmp\n"
				+ "#else\n" + "PRJ_MMPFILES\n" + "gnumakefile myfile.mk\n"
				+ "#endif\n";
		String text2a = "PRJ_PLATFORMS\n" + "WINSCW\n" + "\n"
				+ "#ifdef FEATURE\n" + "PRJ_MMPFILES\n" + "test1.mmp\n"
				+ "#else\n" + "PRJ_MMPFILES\n" + "gnumakefile myfile.mk\n"
				+ "#endif\n" + "\n" + "new.mmp\n";
		IBldInfView view;
		makeModel(text);
		macros.add(DefineFactory.createSimpleFreeformDefine("FEATURE"));
		view = getView(config);
		IMMPReference ref;
		ref = view.createMMPReference();
		ref.setPath(new Path("group/new.mmp"));
		view.getMakMakeReferences().add(ref);
		view.commit();
		assertEquals(text2a, ((IOwnedModel) view.getModel()).getDocument()
				.get());
		IMessage[] messages = view.getMessages();
		assertEquals(0, messages.length);
		///
		macros.clear();
		makeModel(text);
		view = getView(config);
		ref = view.createMMPReference();
		ref.setPath(new Path("group/new.mmp"));
		view.getMakMakeReferences().add(ref);
		view.commit();
		assertEquals(text2a, ((IOwnedModel) view.getModel()).getDocument()
				.get());
		messages = view.getMessages();
		assertEquals(0, messages.length);
	}

	// here, forced to add inside conditionals
	public void testAddInsideConditionals2() throws Exception {
		String text = "PRJ_PLATFORMS\n" + "WINSCW\n" + "\n"
				+ "#ifdef FEATURE\n" + "PRJ_MMPFILES\n" + "test1.mmp\n"
				+ "PRJ_EXPORTS\n" + "foo\n" + "#else\n" + "PRJ_MMPFILES\n"
				+ "gnumakefile myfile.mk\n" + "PRJ_EXPORTS\n" + "bar\n"
				+ "#endif\n" + "";
		String text2a = "PRJ_PLATFORMS\n" + "WINSCW\n" + "\n"
				+ "#ifdef FEATURE\n" + "PRJ_MMPFILES\n" + "test1.mmp\n"
				+ "new.mmp\n" + "PRJ_EXPORTS\n" + "foo\n" + "#else\n"
				+ "PRJ_MMPFILES\n" + "gnumakefile myfile.mk\n"
				+ "PRJ_EXPORTS\n" + "bar\n" + "#endif\n" + "";
		String text2b = "PRJ_PLATFORMS\n" + "WINSCW\n" + "\n"
				+ "#ifdef FEATURE\n" + "PRJ_MMPFILES\n" + "test1.mmp\n"
				+ "PRJ_EXPORTS\n" + "foo\n" + "#else\n" + "PRJ_MMPFILES\n"
				+ "gnumakefile myfile.mk\n" + "new.mmp\n" + "PRJ_EXPORTS\n"
				+ "bar\n" + "#endif\n" + "";
		IBldInfView view;
		makeModel(text);
		macros.add(DefineFactory.createSimpleFreeformDefine("FEATURE"));
		view = getView(config);
		IMMPReference ref;
		ref = view.createMMPReference();
		ref.setPath(new Path("group/new.mmp"));
		view.getMakMakeReferences().add(ref);
		view.commit();
		assertEquals(text2a, ((IOwnedModel) view.getModel()).getDocument()
				.get());
		IMessage[] messages = view.getMessages();
		assertEquals(1, messages.length);
		assertTrue(messages[0].getMessageKey().contains(
				"AddingInsideConditional"));
		///
		macros.clear();
		makeModel(text);
		view = getView(config);
		ref = view.createMMPReference();
		ref.setPath(new Path("group/new.mmp"));
		view.getMakMakeReferences().add(ref);
		view.commit();
		assertEquals(text2b, ((IOwnedModel) view.getModel()).getDocument()
				.get());
		messages = view.getMessages();
		assertEquals(1, messages.length);
		assertTrue(messages[0].getMessageKey().contains(
				"AddingInsideConditional"));

	}

	public void testNamedExtension() throws Exception {
		String text= "PRJ_MMPFILES\n"+
		"first.mmp\n"+
		"\n"+
		"PRJ_TESTEXTENSIONS\r\n" + 
		"start       extension        base\\config       myExtension\r\n" +
		"tool armcc\r\n"+
		"target zap_ma_ma\r\n"+
		"sources ..\\src\\file1.cpp sub\\file2.cpp\r\n"+
		"dependencies ..\\src\\file1 sub\\file2\r\n"+
		"\r\n" + 
		"end";
		
		makeModel(text);
		IBldInfView view = getView(config);
		checkNoProblems(view);
	
		_testNamedExtension(view);
		_testNamedExtension(view.getData());
		
		commitTest(view, text);
	}

	private void _testNamedExtension(IBldInfData bldInfData) {
		IExtension ext = bldInfData.getTestExtensions().get(0);
		assertNotNull(ext);
		assertTrue(ext.isValid());
		assertNotNull(ext.getName());
		assertEquals("myExtension", ext.getName());
	}

	public void testModifyNamedExtension() {
		makeModel("PRJ_EXTENSIONS\n"+
				"START EXTENSION base/graphics/svg\n"+
				"END\n");
		IBldInfView view = getView(config);
		checkNoProblems(view);

		IExtension ext = view.getExtensions().get(0);
		ext.setName("myExtension");	
		assertNotNull(ext.getName());
		assertEquals("myExtension", ext.getName());
		commitTest(view,
				"PRJ_EXTENSIONS\n"+
				"START EXTENSION base/graphics/svg myExtension\n"+
				"END\n");
	}

}

