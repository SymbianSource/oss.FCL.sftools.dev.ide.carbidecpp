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
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.SharedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.ASTMMPFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPAifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPListArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStartBlockStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.Token;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.rewriter.DocumentUpdater;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ITranslationUnitParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp.IMMPParserConfiguration;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;

import org.eclipse.core.runtime.Path;

import java.util.ArrayList;

/**
 * Test the MMP parser, which involves preprocessing.
 *
 */
public class TestMMPParser extends BaseTest {
	static final String TEST_EMPTY = "";
	static final String TEST_COMMENT = "// hi mom\n";
	static final String TEST_ONE_LINE = "\n";
	static final String TEST_FLAG = "SRCDBG\n";
	static final String TEST_SINGLEARG = "BASEADDRESS 0x12345678\n";
	static final String TEST_UNKNOWN_0 = "SURCE foo.cpp bar.cpp\n";
	static final String TEST_UNKNOWN_1 = "HEADERSOURCEPATH ..\\gfx\nSOURCE a.cpp";
	static final String TEST_SOURCE = "SOURCE foo.cpp bar.cpp\n";
	static final String TEST_AIF_0 =
		"AIF target.aif ..\\aif\\ project_aif.rss \\\n"+
			"c12,1 ../gfx/list_icon.bmp ../gfx/list_icon_mask.bmp\n";
	static final String TEST_AIF_1 =
		"Aif target.aif ..\\aif\\ project_aif.rss\n";
	static final String TEST_OPTION_0 =
		"Option WINS -O4 -g -DFoo=33";
	static final String TEST_OPTION_1 =
		"LINKEROPTION WINS \"-Lc:\\program files\\foo\" -lmylib.lib";
	static final String TEST_ROMTARGET_0 =
		"ROMTARGET \\sys\\bin\\ \n";
	static final String TEST_ROMTARGET_1 =
		"RomTarget +\\sys\\bin\\ \\foo\\bar\\ \n";
	static final String TEST_ROMTARGET_2 =
		"ROMTARGET + \\sys\\bin\\ \\foo\\bar\\ \n";
	static final String TEST_UID_0 =
		"#define MYUID 0xe0f0b0c0\n"+
		"UID 0x100009CE MYUID\n";
	static final String TEST_UID_1 =
		"UID 0x100009CE\n";
	static final String TEST_IF_TARGET = "#if defined(WINS)\nSOURCE foo.cpp bar.cpp\n#else\nSOURCE foo.cpp bar.cpp baz.cpp\n#endif\nSOURCE common.cpp\n";
	
	static final String TEST_BLOCK_0 = 
		"START PLATFORM WINS\n"+
		"\tSRCDBG\n"+
		"\tBASEADDRESS 0xA0000000\n"+
		"END\n";
	static final String TEST_START_BITMAP_0 = 
		"START BITMAP \\resource\\apps\\output.mbm\n"+
		"\tSOURCE c16,4 ../gfx/list_icon.bmp ../gfx/list_icon_mask.bmp\n"+
		"\tSOURCE c1 ..\\gfx\\foo_icon.bmp ..\\gfx\\bar_icon.bmp\n"+
		"END\n"+
		"SOURCE file.cpp another.cpp\n";

	static final String TEST_INCLUDES_1 = 
		"SOURCE foo.cpp bar.cpp\n"+
		"#include \"incfile.h\"\n";
	static final String TEST_INCLUDES_1_incfile_h = 
		"SOURCE inc.cpp other.cpp\n";
	
	static final String TEST_LIST_EMPTY = "SOURCE\nfoo.cpp\n";
	static final String TEST_BLOCK_EMPTY = "START BITMAP\nEND\n";
	
	static final String TEST_PLATFORM = "START WINS\nSRCDBG\nEND\n";
	private static final String TEST_ROMRAM_TARGET = 
		"ROMTARGET\n"+ 
		"ROMTARGET +\n"+ 
		"ROMTARGET \\sys\\bin\n"+ 
		"RAMTARGET\n"+ 
		"RAMTARGET +\n"+ 
		"RAMTARGET \\sys\\bin\n";
	private static final String TEST_STARTENDTRAILER = 
		"START WINS\n" + 
		"END WINS\n" +
		"VENDORID 0x1000000\n";
	private static final String TEST_VERSIONS = 
		"VERSION 1.1 explicit\n"+
		"VERSION 1.0\n";
	private static final String TEST_GARBAGE_AT_EOL = 
		"SRCDBG on\n"+
		"SOURCEPATH ..\\src ..\\wtf\n";
	private static final String TEST_QUOTED_STRINGS = 
		"SOURCEPATH ..\\\"My Stupid Sources\"\n" + 
		"SOURCE foo.cpp \"Copy of foo.cpp\" \"bar.cpp\"\n";
	
	private IMMPParserConfiguration mmpConfig;
	private IMMPParserConfiguration happyConfig;
	private IMMPParserConfiguration angryConfig;
	private IViewFilter viewFilter;
	private ArrayList<IDefine> macros;
	private IPreprocessorResults ppResults;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		happyConfig = new IMMPParserConfiguration() {

			public boolean isAifStatementRecognized() {
				return true;
			}
			
			public boolean isBitmapSourceStatementRecognized() {
				return true;
			}

			public boolean isOptionStatementRecognized() {
				return true;
			}

			public boolean isStartBlockStatementRecognized() {
				return true;
			}

			public boolean isUidStatementRecognized() {
				return true;
			}

			public int categorizeStatement(String keyword) {
				try {
					return EMMPStatement.valueOf(keyword).getCategory();
				} catch (IllegalArgumentException e) {
					return UNKNOWN_STATEMENT;
				}
			}
			
		};
		
		angryConfig = new IMMPParserConfiguration() {

			public boolean isAifStatementRecognized() {
				return false;
			}

			public boolean isBitmapSourceStatementRecognized() {
				return false;
			}
			
			public boolean isOptionStatementRecognized() {
				return false;
			}

			public boolean isStartBlockStatementRecognized() {
				return false;
			}

			public boolean isUidStatementRecognized() {
				return false;
			}

			public int categorizeStatement(String keyword) {
				return UNKNOWN_STATEMENT;
			}
			
		};
		
		mmpConfig = happyConfig;
		viewFilter = new AcceptedNodesViewFilter();
		macros = new ArrayList<IDefine>();
	}
	
	protected IASTTranslationUnit parse(String text, boolean expectErrors) {
		IDocumentParser preParser = ParserFactory.createPreParser();
		IASTTranslationUnit ppTu = preParser.parse(new Path("test.mmp"), DocumentFactory.createDocument(text));
		
		// preprocess
		ASTPreprocessor preprocessor = createPreprocessor(parserConfig);

		ppResults = preprocessor.preprocess(ppTu, viewFilter, macros);
			
		ITranslationUnitParser parser = ParserFactory.createMMPParser(mmpConfig);
		IASTTranslationUnit mmpTu = parser.parse(ppResults);
		if (expectErrors)
			assertTrue(parser.hadErrors());
		else
			assertFalse(parser.hadErrors());
		return mmpTu;
	}
	
	protected IASTTranslationUnit parse(String text) {
		return parse(text, false);
	}
	public void testEmpty() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_EMPTY);
		testParsedTu(refTu, tu);
	}
	
	public void testOneLine() {
		// whitespace, so removed
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_ONE_LINE);
		testParsedTu(refTu, tu);
	}
	
	public void testComment() {
		// whitespace, so removed
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_COMMENT);
		testParsedTu(refTu, tu);
	}
	
	public void testFlag_0() {
		IASTTranslationUnit refTu =ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPFlagStatement(
				ASTMMPFactory.createPreprocessorLiteralTextNode("SRCDBG")));
		
		IASTTranslationUnit tu = parse(TEST_FLAG);
		testParsedTu(refTu, tu);
	
	}
	
	public void testSingleArg_0() {
		IASTTranslationUnit refTu =ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPSingleArgumentStatement(
				ASTMMPFactory.createPreprocessorLiteralTextNode("BASEADDRESS"),
				ASTMMPFactory.createPreprocessorLiteralTextNode("0x12345678")
				));
		
		IASTTranslationUnit tu = parse(TEST_SINGLEARG);
		testParsedTu(refTu, tu);
	
	}

	public void testSource() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "foo.cpp",  "bar.cpp" }));
		
		IASTTranslationUnit tu = parse(TEST_SOURCE);
		testParsedTu(refTu, tu);
	
	}
	public void testUnknown_0() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPUnknownStatement(
				"SURCE", new String[] { "foo.cpp", "bar.cpp" }));
		
		IASTTranslationUnit tu = parse(TEST_UNKNOWN_0);
		testParsedTu(refTu, tu);
	
	}
	public void testUnknown_1() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPUnknownStatement(
				"HEADERSOURCEPATH", new String[] { "..\\gfx" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "a.cpp" }));
		
		IASTTranslationUnit tu = parse(TEST_UNKNOWN_1);
		testParsedTu(refTu, tu);
		
	}
	
	public void testAif() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		String colorDepth = ASTMMPFactory.createBitmapFormat(12, 1, true);
		IASTMMPAifStatement aif = ASTMMPFactory.createMMPAifStatement(
				"target.aif", "..\\aif\\", "project_aif.rss", 
				colorDepth, 
				new String[] {
						"../gfx/list_icon.bmp",
					"../gfx/list_icon_mask.bmp" }
				);
		refTu.getNodes().add(aif);
		
		IASTTranslationUnit tu = parse(TEST_AIF_0);
		testParsedTu(refTu, tu);
	
		///
		
		refTu.getNodes().clear();
		aif = ASTMMPFactory.createMMPAifStatement(
				"target.aif", "..\\aif\\", "project_aif.rss", 
				null, (String[])null); 
		refTu.getNodes().add(aif);
		
		tu = parse(TEST_AIF_1);
		testParsedTu(refTu, tu);
		
		/// negative test
		mmpConfig = angryConfig;

		refTu.getNodes().clear();
		refTu.getNodes().add(ASTMMPFactory.createMMPUnknownStatement(
				"Aif", new String[] { "target.aif", "..\\aif\\", "project_aif.rss" }));

		tu = parse(TEST_AIF_1);
		testParsedTu(refTu, tu);

	}
	
	public void testOption() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPOptionStatement(
				"Option",
				"WINS",
				new String[] {"-O4","-g","-DFoo=33" }));
		
		IASTTranslationUnit tu = parse(TEST_OPTION_0);
		testParsedTu(refTu, tu);
		
		refTu.getNodes().clear();
		refTu.getNodes().add(ASTMMPFactory.createMMPOptionStatement(
				"LINKEROPTION",
				"WINS",
				new String[] {"\"-Lc:\\program files\\foo\"", "-lmylib.lib"}));
		
		tu = parse(TEST_OPTION_1);
		testParsedTu(refTu, tu);

	}

	public void testUID() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPUidStatement(
				"0x100009CE",
				"0xe0f0b0c0" ));
		
		IASTTranslationUnit tu = parse(TEST_UID_0);
		testParsedTu(refTu, tu, true);

		///
		
		refTu.getNodes().clear();
		refTu.getNodes().add(ASTMMPFactory.createMMPUidStatement(
				"0x100009CE", null ));
		
		tu = parse(TEST_UID_1);
		testParsedTu(refTu, tu);

	}
	
	public void testIfTarget() {
	// "#if defined(WINS)\nSOURCE foo.cpp bar.cpp\n#else\nSOURCE foo.bar bar.cpp baz.cpp\n#endif\nSOURCE common.cpp\n";
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "foo.cpp",  "bar.cpp", "baz.cpp" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "common.cpp" }));
		
		IASTTranslationUnit tu = parse(TEST_IF_TARGET);
		testParsedTu(refTu, tu, true);
		
		/// all nodes
		viewFilter = new AllNodesViewFilter();
		refTu.getNodes().clear();
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "foo.cpp",  "bar.cpp" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "foo.cpp",  "bar.cpp", "baz.cpp" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "common.cpp" }));

		tu = parse(TEST_IF_TARGET);
		testParsedTu(refTu, tu, true);

		// common nodes
		viewFilter = new SharedNodesViewFilter();
		refTu.getNodes().clear();
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "common.cpp" }));

		tu = parse(TEST_IF_TARGET);
		testParsedTu(refTu, tu, true);

	}
	
	public void testBlockStatement() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		IASTListNode<IASTMMPStatement> stmts = ASTMMPFactory.createMMPStatementListNode();
		stmts.add(ASTMMPFactory.createMMPFlagStatement("SRCDBG"));
		stmts.add(ASTMMPFactory.createMMPSingleArgumentStatement("BASEADDRESS", "0xA0000000"));
		refTu.getNodes().add(ASTMMPFactory.createMMPStartBlockStatement(
				ASTFactory.createPreprocessorLiteralTextNode("PLATFORM"),
				ASTFactory.createPreprocessorLiteralTextNodeList(new String[] { "WINS" }),
				stmts));
		
		IASTTranslationUnit tu = parse(TEST_BLOCK_0);
		testParsedTu(refTu, tu);

	}
	
	public void testStartBitmap() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		IASTListNode<IASTMMPStatement> stmts = ASTMMPFactory.createMMPStatementListNode();
		stmts.add(ASTMMPFactory.createMMPBitmapSourceStatement(
				"c16,4", new String[] { "../gfx/list_icon.bmp", "../gfx/list_icon_mask.bmp" }));
		stmts.add(ASTMMPFactory.createMMPBitmapSourceStatement(
				"c1", new String[] { "..\\gfx\\foo_icon.bmp", "..\\gfx\\bar_icon.bmp" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPStartBlockStatement(
				ASTFactory.createPreprocessorLiteralTextNode("BITMAP"),
				ASTFactory.createPreprocessorLiteralTextNodeList(new String[] { "\\resource\\apps\\output.mbm" }),
				stmts));
		
		// ensure other SOURCE is distinct
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "file.cpp", "another.cpp" }));
		
		IASTTranslationUnit tu = parse(TEST_START_BITMAP_0);
		testParsedTu(refTu, tu);
	
	}
	
	public void testDeleteInList() {
		mmpConfig = happyConfig;
		IASTTranslationUnit tu = parse(
				"SOURCEPATH foo\nSOURCE one two three\n");
		assertNotNull(tu);

		IASTMMPListArgumentStatement stmt = (IASTMMPListArgumentStatement) tu.getNodes().get(1);
		stmt.getArguments().remove(2);
		stmt.getArguments().remove(1);

		DocumentUpdater.updateDocuments(ppResults, tu);
		assertEquals( "SOURCEPATH foo\nSOURCE one\n", tu.getNewText());
		
	}

	public void testIncludes() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "foo.cpp", "bar.cpp" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "inc.cpp", "other.cpp" }));

		parserConfig.getFilesystem().put("incfile.h",
				TEST_INCLUDES_1_incfile_h);
		
		IASTTranslationUnit tu = parse(TEST_INCLUDES_1);
		
		testParsedTu(refTu, tu, true);
		
		// the text is an amalgam of all the text from the
		// translation unit, including stuff in the #included file
		assertEquals("SOURCE foo.cpp bar.cpp\n" + TEST_INCLUDES_1_incfile_h, 
				tu.getOriginalText());
	
	}
	
	public void testListEmpty() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE"));
		refTu.getNodes().add(ASTMMPFactory.createMMPProblemStatement(
				ASTFactory.createPreprocessorTokenStream(
						new IToken[] {
								new Token(IToken.IDENTIFIER, "foo.cpp", null, 7, 7, false, false),
								new Token(IToken.EOL, "\n", null, 14, 1, false, false)
								
						}), null));
		
		IASTTranslationUnit tu = parse(TEST_LIST_EMPTY, true);
		testParsedTu(refTu, tu);
	
	}
	public void testBlockEmpty() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPStartBlockStatement(
				"BITMAP", null));
		
		IASTTranslationUnit tu = parse(TEST_BLOCK_EMPTY);
		testParsedTu(refTu, tu);
		
	}
	public void testPlatform() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		IASTMMPStartBlockStatement block = ASTMMPFactory.createMMPStartBlockStatement(
						"WINS", null);
		block.getStatements().add(ASTMMPFactory.createMMPFlagStatement("SRCDBG"));
		refTu.getNodes().add(block);
		
		macros.add(DefineFactory.createDefine("WINS", "WINS"));
		IASTTranslationUnit tu = parse(TEST_PLATFORM);
		testParsedTu(refTu, tu);
		
	}
	
	private String TEST_PROBLEM_5_p =
		"UNKNOWN\n";
	private String TEST_PROBLEM_5 =
		"#include \"header.mmh\"\n"+
		"MORE BLAH\n";


	public void testUnknownInInclude() {
		// assert no crash
		parserConfig.getFilesystem().put("header.mmh", TEST_PROBLEM_5_p);
		parse(TEST_PROBLEM_5);
	}

	public void testUnknownInStart() {
		// make sure it doesn't consume the END
		final String MMP = 
						"Start WINS\n"+
						"\t\tstrange_keyword\n"+
						"end\n"+
						"STARt wins\n"+
						"* foo\n"+
						"end\n";
		IASTTranslationUnit tu = parse(MMP, true);
		assertTrue(tu.getNodes().get(0) instanceof IASTMMPStartBlockStatement);
		assertTrue(tu.getNodes().get(1) instanceof IASTMMPStartBlockStatement);
	}
	public void testIllegalInStart() {
		// make sure it doesn't consume the END
		final String MMP = 
						"STARt wins\n"+
						"* foo\n"+
						"end\n";
		IASTTranslationUnit tu = parse(MMP, true);
		assertTrue(tu.getNodes().get(0) instanceof IASTMMPStartBlockStatement);
	}

	public void testUnknownInStartResource() {
		// make sure it doesn't consume the END
		final String MMP = 
						"Start resource test.rss\n"+
						"\t\tstrange_keyword\n"+
						"end";
		IASTTranslationUnit tu = parse(MMP);
		assertTrue(tu.getNodes().get(0) instanceof IASTMMPStartBlockStatement);
	}

	public void testRomRamTarget() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"ROMTARGET", new String[0]));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"ROMTARGET", new String[] { "+" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"ROMTARGET", new String[] { "\\sys\\bin" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"RAMTARGET", new String[0]));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"RAMTARGET", new String[] { "+" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"RAMTARGET", new String[] { "\\sys\\bin" }));
		
		IASTTranslationUnit tu = parse(TEST_ROMRAM_TARGET);
		testParsedTu(refTu, tu);
		
	}

	public void testStartEndNonsense() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPStartBlockStatement(
				ASTFactory.createPreprocessorLiteralTextNode("WINS"), 
				ASTFactory.createPreprocessorLiteralTextNodeList(new String[0]), 
				ASTMMPFactory.createMMPStatementListNode()));
		refTu.getNodes().add(ASTMMPFactory.createMMPSingleArgumentStatement(
				"VENDORID", "0x1000000"));
		IASTTranslationUnit tu = parse(TEST_STARTENDTRAILER);
		
		// use PP-style modification because of trailing junk not represented in new text
		testParsedTu(refTu, tu, true);
		
				
	}
	
	public void testVersionStuff() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"VERSION",
				new String[] { "1.1", "explicit" }));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"VERSION",
				new String[] { "1.0" }));

		IASTTranslationUnit tu = parse(TEST_VERSIONS);
		
		testParsedTu(refTu, tu);
		
	}
	
	public void testGarbageAtEOL() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPFlagStatement(
				"SRCDBG"));
		refTu.getNodes().add(ASTMMPFactory.createMMPSingleArgumentStatement(
				"SOURCEPATH", "..\\src"));

		IASTTranslationUnit tu = parse(TEST_GARBAGE_AT_EOL);
		
		testParsedTu(refTu, tu, true);

	}
	
	public void testQuotedStrings() {
		IASTTranslationUnit refTu = ASTMMPFactory.createMMPTranslationUnit();
		refTu.getNodes().add(ASTMMPFactory.createMMPSingleArgumentStatement(
				"SOURCEPATH", "..\\\"My Stupid Sources\""));
		refTu.getNodes().add(ASTMMPFactory.createMMPListArgumentStatement(
				"SOURCE", new String[] { "foo.cpp", "\"Copy of foo.cpp\"", "\"bar.cpp\"" }));

		IASTTranslationUnit tu = parse(TEST_QUOTED_STRINGS);
		
		testParsedTu(refTu, tu, true);

	
	}

	public void testLongFile() {
		// the old parser was recursive and hit stack overflows
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 5000; i++) {
			builder.append("BASEADDRESS "+i+"\n");
		}
		try {
			parse(builder.toString());
		} catch (StackOverflowError e) {
			fail();
		}
	
	}
	
	public void testCatenatedLine() {
		String text = "" +
				"SYSTEMINCLUDE \\\n"+
				"\tfoo\n";
		IASTTranslationUnit tu = parse(text);
		assertEquals(1, tu.getNodes().size());
		assertEquals("SYSTEMINCLUDE foo\n", tu.getNodes().get(0).getNewText());
		
	}
	
	public void testStringMacro() {
		String text = "" +
			"#define macro1 \"mytarget.exe\"\r\n"+
			"#define macro2 \"my target.exe\"\r\n"+
			"TARGET \"my target.exe\"\r\n"+
			"TARGET macro1\r\n"+
			"TARGET macro2\r\n";
		IASTTranslationUnit tu = parse(text);
		assertEquals(3, tu.getNodes().size());
		assertEquals("TARGET \"my target.exe\"\n", tu.getNodes().get(0).getNewText());
		assertEquals("TARGET \"mytarget.exe\"\n", tu.getNodes().get(1).getNewText());
		assertEquals("TARGET \"my target.exe\"\n", tu.getNodes().get(2).getNewText());
		
	}

}
