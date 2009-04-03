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

package com.nokia.carbide.cpp.epoc.engine.tests.parser;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bldinf.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ITranslationUnitParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;

import org.eclipse.core.runtime.Path;

import java.util.ArrayList;

/**
 * Test the bld.inf parser, which involves preprocessing.
 *
 */
public class TestBldInfParser extends BaseTest {
	static final String TEST_EMPTY = "";
	static final String TEST_COMMENT = "// hi mom\n";
	static final String TEST_ONE_LINE = "\n";
	static final String TEST_PRJ_PLATFORMS_1 = 
		"PRJ_PLATFORMS winscw armv5\n";
	static final String TEST_PRJ_PLATFORMS_2 = 
		"PRJ_PLATFORMS\nwinscw armv5\n";
	static final String TEST_PRJ_PLATFORMS_3 = 
		"PRJ_PLATFORMS\nwinscw\narmv5\n";
	static final String TEST_PRJ_PLATFORMS_0 = 
		"PRJ_PLATFORMS\n";
	final String TEST_PRJ_EXPORTS_0 =
		"PRJ_EXPORTS\n";
	final String TEST_PRJ_EXPORTS_1 =
		"PRJ_EXPORTS\n"+
		"\tdata\\myfile.txt\n"+
		"\tdata\\myfile.txt\te:\\appdata\\myfile.txt\n"+
		"\t:zip data\\myfile.zip\n"+
		"\t:zip myfile.zip epoc32\\include\\ \n";
	final String TEST_PRJ_EXPORTS_2 =
		"PRJ_EXPORTS\n"+
		"\tdata\\myfile.txt\te:\\appdata\\myfile.txt\n"+
		"PRJ_TESTEXPORTS\n"+
		"\t:zip data\\myfile.zip\n";

	final String TEST_PRJ_MMPFILES_0 =
		"PRJ_MMPFILES\n";

	final String TEST_PRJ_MMPFILES_1 =
		"PRJ_MMPFILES\n"+
		"mainfile.mmp\n"+
		"another.mmp build_as_arm\n"+
		"another.mmp tidy build_as_arm\n"+
		"gnumakefile Icons_scalable_dc.mk\n"+
		"nmakefile Icons_scalable_dc.mk build_as_arm\n"+
		"makefile Icons_scalable_dc.mk build_as_arm tidy\n";

	final String TEST_PRJ_COMPLEX_1 =
		"// for now, need more platforms later\n"+
		"PRJ_PLATFORMS\nwinscw\narmv5\n"+
		"\n"+
		"PRJ_MMPFILES\n"+
		"\tmainfile.mmp\n"+
		"\tanother.mmp build_as_arm\n"+
		"\tanother.mmp tidy build_as_arm\n"+
		"\tgnumakefile Icons_scalable_dc.mk\n"+
		"\tnmakefile Icons_scalable_dc.mk build_as_arm\n"+
		"\tmakefile Icons_scalable_dc.mk build_as_arm tidy\n"+
		"\n"+
		"// TODO: review this list!\n"+
		"// I don't think the phone has space for myfile.zip\n"+
		"PRJ_EXPORTS\n"+
		"\tdata\\myfile.txt\te:\\appdata\\myfile.txt\n"+
		"\t:zip data\\myfile.zip\n"+
		"//\tfoo.txt bar.txt\n"
		;

	final String TEST_EXTENSIONS_0 =
		"PRJ_EXTENSIONS\n"+
		"START foo bar\n"+
		"OPTION stuff -O2\n"+
		"END\n";
	final String TEST_EXTENSIONS_0b =
		"PRJ_TESTEXTENSIONS\n";
	
	final String TEST_EXTENSIONS_1 = 
		"PRJ_PLATFORMS\r\n" + 
		"ARM4 ARMV5\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"PRJ_EXTENSIONS\r\n" + 
		"start		extension		base/genexec\r\n" + 
		"\r\n" + 
		"option		EXTRA_SRC_PATH		$(EXTENSION_ROOT)/../../e32/kernel\r\n" + 
		"\r\n" + 
		"end\r\n" + 
		"\r\n" + 
		"// Project files *************************************************************\r\n" + 
		"// External components\r\n" + 
		"\r\n" + 
		"PRJ_EXTENSIONS\r\n" + 
		"start		extension		base/h2_genbootinc\r\n" + 
		"\r\n" + 
		"end\r\n" + 
		"\r\n" + 
		"PRJ_EXTENSIONS\r\n" + 
		"start		extension		base/config\r\n" + 
		"   \r\n" + 
		"#ifdef SINGLE\r\n" + 
		"option 		PREFIX 			_SH2_\r\n" + 
		"#else\r\n" + 
		"option 		PREFIX			_h2_\r\n" + 
		"#endif\r\n" + 
		"option 		HALPATH			$(EXTENSION_ROOT)/../..\r\n" + 
		"option		SOURCE			$(EXTENSION_ROOT)/hal\r\n" + 
		"\r\n" + 
		"end\r\n" + 
		"\r\n" + 
		"PRJ_MMPFILES\r\n" + 
		"hal/hal\r\n" + 
		"rebootdrv								// Reboot driver for the NAND loader\r\n" + 
		"usbboot								// H2 USB mass storage bootloader app\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"#ifndef GCCXML \r\n" + 
		"#ifndef NO_BOOT\r\n" + 
		"\r\n" + 
		"PRJ_EXTENSIONS\r\n" + 
		"start		extension		base/h2_coreldr\r\n" + 
		"\r\n" + 
		"end\r\n" + 
		"\r\n" + 
		"PRJ_EXTENSIONS\r\n" + 
		"start		extension		base/bootstrap\r\n" + 
		"\r\n" + 
		"option		NAME			_h2_bootrom\r\n" + 
		"option		CPU			arm\r\n" + 
		"option		MEMMODEL		moving\r\n" + 
		"option		ASM_ARM211_VARIANTFLAGS	-arch 4T\r\n" + 
		"option		SOURCES			16xx_common.s variant.s \r\n" + 
		"option		INCLUDES 		config.inc variant.inc general.inc uart.inc mux.inc 16xx_common.inc\r\n" + 
		"option		GENINCLUDES		omapconst.inc\r\n" + 
		"option		EXTRA_INC_PATH		$(EXTENSION_ROOT)/../shared/bootstrap $(EPOCROOT)epoc32/include/assp/omap1610 $(EXTENSION_ROOT)/bootstrap\r\n" + 
		"option		EXTRA_SRC_PATH		$(EXTENSION_ROOT)/../shared/bootstrap $(EXTENSION_ROOT)/bootstrap\r\n" + 
		"option		E32PATH			$(EXTENSION_ROOT)/../..\r\n" + 
		"\r\n" + 
		"end\r\n" + 
		"\r\n" + 
		"#endif\r\n" + 
		"#endif\r\n" + 
		"";
	private IViewFilter viewFilter;
	private ArrayList<IDefine> macros;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		viewFilter = new AcceptedNodesViewFilter();
		macros = new ArrayList<IDefine>();
	}
	
	protected IASTTranslationUnit parse(String text, boolean expectErrors) {
		IDocumentParser preParser = ParserFactory.createPreParser();
		IASTTranslationUnit ppTu = preParser.parse(new Path("test.bldInf"), DocumentFactory.createDocument(text));
		
		// preprocess
		ASTPreprocessor preprocessor = createPreprocessor(parserConfig);
		IPreprocessorResults results = preprocessor.preprocess(ppTu, viewFilter, macros);
		
		ITranslationUnitParser parser = ParserFactory.createBldInfParser();
		IASTTranslationUnit bldInfTu = parser.parse(results);
		if (expectErrors) {
			assertTrue(parser.hadErrors());
		}
		else {
			assertNoProblems(bldInfTu);
			assertFalse(parser.hadErrors());
		}
		return bldInfTu;
	}
	
	protected IASTTranslationUnit parse(String text) {
		return parse(text, false);
	}

	public void testEmpty() {
		IASTTranslationUnit refTu = ASTBldInfFactory.createBldInfTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_EMPTY);
		testParsedTu(refTu, tu);
	}
	
	public void testOneLine() {
		// whitespace, so removed
		IASTTranslationUnit refTu = ASTBldInfFactory.createBldInfTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_ONE_LINE);
		testParsedTu(refTu, tu);
	}
	
	public void testComment() {
		// whitespace, so removed
		IASTTranslationUnit refTu = ASTBldInfFactory.createBldInfTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_COMMENT);
		testParsedTu(refTu, tu);
	}
	
	public void testPrjPlatforms0() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjPlatformsBlockStatement(
				new String[0]));
		
		IASTTranslationUnit tu;
		
		tu = parse(TEST_PRJ_PLATFORMS_0);
		testParsedTu(refTu, tu);

	}
	
	public void testPrjPlatforms1() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjPlatformsBlockStatement(
				new String[] { "winscw", "armv5" }));
		
		IASTTranslationUnit tu;
		
		tu = parse(TEST_PRJ_PLATFORMS_1);
		testParsedTu(refTu, tu);

		tu = parse(TEST_PRJ_PLATFORMS_2);
		testParsedTu(refTu, tu);

		tu = parse(TEST_PRJ_PLATFORMS_3);
		testParsedTu(refTu, tu);

	}
	
	public void testPrjExports0() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjExportsBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_EXPORTS"), null));
		
		IASTTranslationUnit tu = parse(TEST_PRJ_EXPORTS_0);
		testParsedTu(refTu, tu);

	}
	public void testPrjExports1() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		IASTBldInfExportStatement exp1 = 
			ASTBldInfFactory.createBldInfExportStatement(
					"data\\myfile.txt", null, null);
		IASTBldInfExportStatement exp2 = 
			ASTBldInfFactory.createBldInfExportStatement(
					"data\\myfile.txt", "e:\\appdata\\myfile.txt", null);
		IASTBldInfExportStatement exp3 = 
			ASTBldInfFactory.createBldInfExportStatement(
					"data\\myfile.zip", null, ":zip");
		IASTBldInfExportStatement exp4 = 
			ASTBldInfFactory.createBldInfExportStatement(
					"myfile.zip", "epoc32\\include\\", ":zip");
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjExportsBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_EXPORTS"), null));
		refTu.getNodes().add(exp1);
		refTu.getNodes().add(exp2);
		refTu.getNodes().add(exp3);
		refTu.getNodes().add(exp4);
		
		IASTTranslationUnit tu = parse(TEST_PRJ_EXPORTS_1);
		testParsedTu(refTu, tu);

	}


	public void testPrjExports2() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		IASTBldInfExportStatement exp2 = 
			ASTBldInfFactory.createBldInfExportStatement(
					"data\\myfile.txt", "e:\\appdata\\myfile.txt", null);
		IASTBldInfExportStatement exp3 = 
			ASTBldInfFactory.createBldInfExportStatement(
					"data\\myfile.zip", null, ":zip");
		
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjExportsBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_EXPORTS"), null));
		refTu.getNodes().add(exp2);

		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjExportsBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_TESTEXPORTS"), null));
		refTu.getNodes().add(exp3);

		IASTTranslationUnit tu = parse(TEST_PRJ_EXPORTS_2);
		testParsedTu(refTu, tu);

	}

	public void testPrjMakMake0() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjMmpfilesBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_MMPFILES"), null));
		
		IASTTranslationUnit tu = parse(TEST_PRJ_MMPFILES_0);
		testParsedTu(refTu, tu);

	}

	
	public void testPrjMakMake() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		IASTBldInfMakMakeStatement mmp1 = 
			ASTBldInfFactory.createBldInfMmpfileStatement(
					"mainfile.mmp", (String[])null);
		IASTBldInfMakMakeStatement mmp1b = 
			ASTBldInfFactory.createBldInfMmpfileStatement(
					"another.mmp", new String[] { "build_as_arm" });
		IASTBldInfMakMakeStatement mmp1c = 
			ASTBldInfFactory.createBldInfMmpfileStatement(
					"another.mmp", new String[] { "tidy", "build_as_arm" });
		IASTBldInfMakMakeStatement mmp2 = 
			ASTBldInfFactory.createBldInfMakefileStatement(
					"gnumakefile", "Icons_scalable_dc.mk",
					(String[]) null);
		IASTBldInfMakMakeStatement mmp2b = 
			ASTBldInfFactory.createBldInfMakefileStatement(
					"nmakefile", "Icons_scalable_dc.mk",
					new String[] { "build_as_arm"} );
		IASTBldInfMakMakeStatement mmp2c = 
			ASTBldInfFactory.createBldInfMakefileStatement(
					"makefile", "Icons_scalable_dc.mk",
					new String[] { "build_as_arm", "tidy" } );
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjMmpfilesBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_MMPFILES"), null));
		refTu.getNodes().add(mmp1);
		refTu.getNodes().add(mmp1b);
		refTu.getNodes().add(mmp1c);
		refTu.getNodes().add(mmp2);
		refTu.getNodes().add(mmp2b);
		refTu.getNodes().add(mmp2c);
		
		IASTTranslationUnit tu = parse(TEST_PRJ_MMPFILES_1);
		testParsedTu(refTu, tu);

	}

	
	public void testPrjComplex() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjPlatformsBlockStatement(
				new String[] { "winscw", "armv5" }));

		
		IASTBldInfMakMakeStatement mmp1 = 
			ASTBldInfFactory.createBldInfMmpfileStatement(
					"mainfile.mmp", (String[])null);
		IASTBldInfMakMakeStatement mmp1b = 
			ASTBldInfFactory.createBldInfMmpfileStatement(
					"another.mmp", new String[] { "build_as_arm" });
		IASTBldInfMakMakeStatement mmp1c = 
			ASTBldInfFactory.createBldInfMmpfileStatement(
					"another.mmp", new String[] { "tidy", "build_as_arm" });
		IASTBldInfMakMakeStatement mmp2 = 
			ASTBldInfFactory.createBldInfMakefileStatement(
					"gnumakefile", "Icons_scalable_dc.mk",
					(String[]) null);
		IASTBldInfMakMakeStatement mmp2b = 
			ASTBldInfFactory.createBldInfMakefileStatement(
					"nmakefile", "Icons_scalable_dc.mk",
					new String[] { "build_as_arm"} );
		IASTBldInfMakMakeStatement mmp2c = 
			ASTBldInfFactory.createBldInfMakefileStatement(
					"makefile", "Icons_scalable_dc.mk",
					new String[] { "build_as_arm", "tidy" } );
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjMmpfilesBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_MMPFILES"), null));
		refTu.getNodes().add(mmp1);
		refTu.getNodes().add(mmp1b);
		refTu.getNodes().add(mmp1c);
		refTu.getNodes().add(mmp2);
		refTu.getNodes().add(mmp2b);
		refTu.getNodes().add(mmp2c);
		
		
		IASTBldInfExportStatement exp2 = 
			ASTBldInfFactory.createBldInfExportStatement(
					"data\\myfile.txt", "e:\\appdata\\myfile.txt", null);
		IASTBldInfExportStatement exp3 = 
			ASTBldInfFactory.createBldInfExportStatement(
					"data\\myfile.zip", null, ":zip");
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjExportsBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_EXPORTS"), null));
		refTu.getNodes().add(exp2);
		refTu.getNodes().add(exp3);

		
		IASTTranslationUnit tu = parse(TEST_PRJ_COMPLEX_1);
		testParsedTu(refTu, tu);

	}
	
	static final String HELLO_WORLD = 
		"/*\r\n" + 
		"* ==============================================================================\r\n" + 
		"*  Name        : bld.inf\r\n" + 
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
		"PRJ_PLATFORMS\r\n" + 
		"\r\n" + 
		"PRJ_EXPORTS\r\n" + 
		"..\\inc\\hello.txt ..\\winscw\\c\\private\\A000017F\\hello.txt\r\n" + 
		"\r\n" + 
		"PRJ_MMPFILES\r\n" + 
		"\r\n" + 
		"gnumakefile icons_scalable_dc.mk\r\n" + 
		"\r\n" + 
		"HelloWorldBasic.mmp\r\n" + 
		"\r\n" + 
		"";
	public void testHelloWorld() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();

		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjPlatformsBlockStatement(
				new String[0]));

		
		IASTBldInfExportStatement exp2 = 
			ASTBldInfFactory.createBldInfExportStatement(
					"..\\inc\\hello.txt", "..\\winscw\\c\\private\\A000017F\\hello.txt", null);
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjExportsBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_EXPORTS"), null));
		refTu.getNodes().add(exp2);

		
		IASTBldInfMakMakeStatement mak1 = 
			ASTBldInfFactory.createBldInfMakefileStatement(
					"gnumakefile", "icons_scalable_dc.mk",
					new String[0] );
		IASTBldInfMakMakeStatement mmp1 = 
			ASTBldInfFactory.createBldInfMmpfileStatement(
					"HelloWorldBasic.mmp",
					new String[0]  );
		refTu.getNodes().add(ASTBldInfFactory.createBldInfPrjMmpfilesBlockStatement(
				ASTBldInfFactory.createPreprocessorLiteralTextNode("PRJ_MMPFILES"), null));
		refTu.getNodes().add(mak1);
		refTu.getNodes().add(mmp1);
	
		
		IASTTranslationUnit tu = parse(HELLO_WORLD);
		assertNotNull(tu);
		

	}

	static final String PROBLEMS_1 =
		"PRJ_STRANGEDAYS\n"+
		"blah\n"+
		"fork\n"+
		"PRJ_MMPFILES\n"+
		"test.mmp\n"+
		"PRJ_PLATFORMS winscw\n";

	public void testProblems() {
		IASTTranslationUnit tu = parse(PROBLEMS_1, true);
		assertNotNull(tu);

		// ensure we can still see the working stuff
		IASTProblemNode[] ps = getProblems(tu);
		assertEquals(3, ps.length);
		assertEquals(1, tu.findStatementsOfType("PRJ_MMPFILES").length);
		assertEquals(1, tu.findStatementsOfType("PRJ_PLATFORMS").length);
	}

	public void testPrjExtensions0() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		IASTBldInfPrjExtensionsBlockStatement prjExtBlock = 
			ASTBldInfFactory.createBldInfPrjExtensionsBlockStatement("PRJ_EXTENSIONS");
		IASTBldInfExtensionBlockStatement extBlock =
			ASTBldInfFactory.createBldInfExtensionBlockStatement(new String[] { "foo" ,"bar" });
		IASTBldInfExtensionStatement extStmt =
			ASTBldInfFactory.createBldInfExtensionStatement("OPTION", new String[] { "stuff", "-O2" });
		refTu.getNodes().add(prjExtBlock);
		refTu.getNodes().add(extBlock);
		extBlock.getList().add(extStmt);
		
		IASTTranslationUnit tu = parse(TEST_EXTENSIONS_0);
		assertNotNull(tu);
		testParsedTu(refTu, tu);
	}

	public void testPrjExtensions0b() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		IASTBldInfPrjExtensionsBlockStatement prjExtBlock = 
			ASTBldInfFactory.createBldInfPrjExtensionsBlockStatement("PRJ_TESTEXTENSIONS");
		refTu.getNodes().add(prjExtBlock);
		
		IASTTranslationUnit tu = parse(TEST_EXTENSIONS_0b);
		assertNotNull(tu);
		testParsedTu(refTu, tu);
	}
	public void testPrjExtensions1() {
		IASTTranslationUnit tu = parse(TEST_EXTENSIONS_1);
		assertNotNull(tu);
		
		IASTTopLevelNode[] nodes = tu.findStatementsOfType(IASTBldInfPrjExtensionsBlockStatement.class);
		assertEquals(5, nodes.length);
		IASTTopLevelNode[] extBlockNodes = tu.findStatementsOfType(IASTBldInfExtensionBlockStatement.class);
		assertEquals(5, extBlockNodes.length);
		
		IASTBldInfPrjExtensionsBlockStatement pblk = (IASTBldInfPrjExtensionsBlockStatement) nodes[0];
		//assertEquals(1, pblk.getList().size());
		//IASTBldInfExtensionBlockStatement blk = pblk.getList().get(0);
		assertEquals(0, pblk.getList().size());
		IASTBldInfExtensionBlockStatement blk = (IASTBldInfExtensionBlockStatement) extBlockNodes[0];
		assertEquals("START", blk.getKeywordName());
		IASTListNode<IASTLiteralTextNode> args= blk.getArguments();
		assertEquals(2, args.size());
		assertEquals("extension", args.get(0).getValue());
		assertEquals("base/genexec", args.get(1).getValue());
		
		assertEquals(1, blk.getList().size());
		IASTBldInfExtensionStatement stmt = blk.getList().get(0);
		assertEquals("option", stmt.getKeywordName());
		args= stmt.getArguments();
		assertEquals(2, args.size());
		assertEquals("EXTRA_SRC_PATH", args.get(0).getValue());
		assertEquals("$(EXTENSION_ROOT)/../../e32/kernel", args.get(1).getValue());
		
		///
		
		pblk = (IASTBldInfPrjExtensionsBlockStatement) nodes[2];
		//assertEquals(1, pblk.getList().size());
		//blk = pblk.getList().get(0);
		assertEquals(0, pblk.getList().size());
		blk = (IASTBldInfExtensionBlockStatement) extBlockNodes[2];
		
		assertEquals("START", blk.getKeywordName());
		
		args= blk.getArguments();
		assertEquals(2, args.size());
		assertEquals("extension", args.get(0).getValue());
		assertEquals("base/config", args.get(1).getValue());
		
		assertEquals(3, blk.getList().size());
	}
	
	public void testPrjExtensions2() {
		IASTTranslationUnit refTu =ASTBldInfFactory.createBldInfTranslationUnit();
		IASTBldInfPrjMmpfilesBlockStatement prjMmpFilesBlock = 
			ASTBldInfFactory.createBldInfPrjMmpfilesBlockStatement("PRJ_MMPFILES");
		refTu.getNodes().add(prjMmpFilesBlock);
		IASTBldInfMakMakeStatement stmt = ASTBldInfFactory.createBldInfMmpfileStatement("first.mmp", new String[0]);
		refTu.getNodes().add(stmt);
		
		IASTBldInfPrjExtensionsBlockStatement prjExtBlock = 
			ASTBldInfFactory.createBldInfPrjExtensionsBlockStatement("PRJ_TESTEXTENSIONS");
		refTu.getNodes().add(prjExtBlock);

		IASTBldInfExtensionBlockStatement extBlk = ASTBldInfFactory.createBldInfExtensionBlockStatement
			(new String[] { "extension", "base\\config" } );
		refTu.getNodes().add(extBlk);
		
		IASTBldInfExtensionStatement ext = ASTBldInfFactory.createBldInfExtensionStatement("tool", 
				new String[] { "armcc" } );
		extBlk.getList().add(ext);
		ext = ASTBldInfFactory.createBldInfExtensionStatement("target", 
				new String[] { "zap_ma_ma" } );
		extBlk.getList().add(ext);
		ext = ASTBldInfFactory.createBldInfExtensionStatement("sources", 
				new String[] { "..\\src\\file1.cpp", "sub\\file2.cpp" } );
		extBlk.getList().add(ext);
		ext = ASTBldInfFactory.createBldInfExtensionStatement("dependencies", 
				new String[] { "..\\src\\file1", "sub\\file2" } );
		extBlk.getList().add(ext);
		
		IASTTranslationUnit tu = parse("PRJ_MMPFILES\n"+
				"first.mmp\n"+
				"\n"+
				"PRJ_TESTEXTENSIONS\r\n" + 
				"start       extension        base\\config\r\n" +
				"tool armcc\r\n"+
				"target zap_ma_ma\r\n"+
				"sources ..\\src\\file1.cpp sub\\file2.cpp\r\n"+
				"dependencies ..\\src\\file1 sub\\file2\r\n"+
				"\r\n" + 
				"end");
		assertNotNull(tu);
		
		testParsedTu(refTu, tu);

	}
	public void testEmptyStatementSource() {
		parserConfig.getFilesystem().put("incl.inf",
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
			"// End of file\r\n"
				);
		macros.add(DefineFactory.createDefine("RD_PHONEBOOK2", "1"));
		IASTTranslationUnit tu = parse(
				"PRJ_PLATFORMS\n"+
				"DEFAULT\n"+
				"#include \"incl.inf\"\n");
		
		testSourceRegions(tu, true);
	}
	
	public void testCatenatedLineBug4828() {
		String text = 
			"PRJ_EXPORTS\r\n" + 
			"..\\data\\silence.rul\\\r\n" + 
			"	\\epoc32\\RELEASE\\winscw\\UDEB\\Z\\private\\10282BC4\\Rules\\silence.rul\r\n" + 
			"\r\n" + 
			"..\\data\\silence.rul\\\r\n" + 
			"	\\epoc32\\data\\Z\\private\\10282BC4\\Rules\\silence.rul\r\n" + 
			"";
		IASTTranslationUnit tu = parse(text);
		assertNotNull(tu);
		assertNoProblems(tu);
		
		assertEquals(3, tu.getNodes().size());
		IASTBldInfExportStatement stmt = (IASTBldInfExportStatement) tu.getNodes().get(2);
		assertEquals(new Path("\\epoc32\\data\\Z\\private\\10282BC4\\Rules\\silence.rul"),
				new Path(stmt.getTargetPath().getValue()));
		
	}
	
	public void testAposBug7846() throws Exception {
		String text =
			"/*\r\n" + 
			"============================================================================\r\n" + 
			" Name		: bld.inf\r\n" + 
			" Author	  : \r\n" + 
			" Copyright   : Your copyright notice\r\n" + 
			" Description : This file provides the information required for building the\r\n" + 
			"				whole of a b7846.\r\n" + 
			"============================================================================\r\n" + 
			"*/\r\n" + 
			"\r\n" + 
			"PRJ_PLATFORMS\r\n" + 
			"DEFAULT\r\n" + 
			"\r\n" + 
			"PRJ_MMPFILES\r\n" + 
			"b7846.mmp\r\n" + 
			"\r\n" + 
			"PRJ_EXPORTS\r\n" + 
			"../data/test'me'.txt\r\n" + 
			"";
		IASTTranslationUnit tu = parse(text);
		assertNotNull(tu);
		assertNoProblems(tu);
		assertEquals(5, tu.getNodes().size());
		IASTBldInfExportStatement stmt = (IASTBldInfExportStatement) tu.getNodes().get(4);
		assertEquals(new Path("../data/test'me'.txt"),
				new Path(stmt.getSourcePath().getValue()));
	}
}
