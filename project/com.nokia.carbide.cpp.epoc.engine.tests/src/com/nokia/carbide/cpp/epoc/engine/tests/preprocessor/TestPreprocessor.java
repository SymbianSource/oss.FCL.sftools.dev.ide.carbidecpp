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

package com.nokia.carbide.cpp.epoc.engine.tests.preprocessor;

import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.MacroExpandingTokenIterator;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.core.runtime.IPath;

import java.util.*;

public class TestPreprocessor extends BaseTest {
	final String EMPTY = "";
	final String ONE_LINE = "hi there\n";
	final String IF_0_BLOCK = "#if 0\nfoo\n#endif\n";
	final String IF_1_BLOCK = "#if 1\nfoo\n#endif\n";
	final String IF_X_EQ_4_BLOCK = "#if X4==4\nfoo\n#endif\n";
	final String IF_NOT_X_EQ_4_BLOCK = "#if !(X4==4)\nfoo\n#endif\n";
	final String IF_DEFINED_FOO_BLOCK = "#if defined(FOO)\nfoo\n#endif\n";
	final String IF_DEFINED_FOO_OR_DEFINED_BAR_ELSE_BLOCK = "#if defined(FOO)||defined(BAR)\nfoo\n#else\nnotFooOrBar\n#endif\n";
	final String X_IF_DEFINED_FOO_ELIF_DEFINED_BAR_ELSE_BLOCK_Y = "x\n#if defined(FOO)\nfoo\n#elif defined(BAR)\nbar\n#else\nnotFooOrBar\n#endif\ny\n";
	
	private AcceptedNodesViewFilter acceptedFilter;
	private AllNodesViewFilter allFilter;
	private IViewConfiguration allConfig;
	private ArrayList<IDefine> macros;
	private IViewConfiguration currentConfig;
	private IPath path;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		path = projectPath.append("test.txt");
		macros = new ArrayList<IDefine>();
		
		macros.add(DefineFactory.createDefine("X4", "4"));


		allFilter = new AllNodesViewFilter();
		acceptedFilter = new AcceptedNodesViewFilter();
		allConfig = new IViewConfiguration() {

			public IViewFilter getViewFilter() {
				return allFilter;
			}

			public Collection<IDefine> getMacros() {
				return macros;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}
		};
		currentConfig = new IViewConfiguration() {

			public IViewFilter getViewFilter() {
				return acceptedFilter;
			}

			public Collection<IDefine> getMacros() {
				return macros;
			}

			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}

		};
		
	}
	
	protected IASTTranslationUnit parse(String text) {
		return parse(path, text);
	}

	protected IASTTranslationUnit preprocess(String text, IViewConfiguration viewConfig) {
		IASTTranslationUnit ppTu = parse(text);
		ASTPreprocessor preprocessor = createPreprocessor(parserConfig);
		IPreprocessorResults results = preprocessor.preprocess(ppTu, viewConfig.getViewFilter(), viewConfig.getMacros());
		IASTTranslationUnit tu = results.getFilteredTranslationUnit();
		return tu;
	}
	
	public void testEmpty() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		IASTTranslationUnit tu = preprocess(EMPTY, currentConfig);
		testParsedTu(refTu, tu);
	}
	
	public void testOneLine() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("hi there\n"));
		IASTTranslationUnit tu = preprocess(ONE_LINE, currentConfig);
		testParsedTu(refTu, tu);
	}

	public void testIf0Block() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		IASTTranslationUnit tu = preprocess(IF_0_BLOCK, currentConfig);
		testParsedTu(refTu, tu, true);
		
		// all configs == both branches
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("foo\n"));
		tu = preprocess(IF_0_BLOCK, allConfig);
		testParsedTu(refTu, tu);

	}
	public void testIf1Block() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("foo\n"));

		IASTTranslationUnit tu = preprocess(IF_1_BLOCK, currentConfig);
		testParsedTu(refTu, tu);
		
		// all configs == both branches
		tu = preprocess(IF_1_BLOCK, allConfig);
		testParsedTu(refTu, tu);

	}

	public void testIfXeq4Block() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("foo\n"));

		IASTTranslationUnit tu = preprocess(IF_X_EQ_4_BLOCK, currentConfig);
		testParsedTu(refTu, tu);
		
		// all configs == both branches
		tu = preprocess(IF_X_EQ_4_BLOCK, allConfig);
		testParsedTu(refTu, tu);
	}
	public void testIfNotXeq4Block() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();

		IASTTranslationUnit tu = preprocess(IF_NOT_X_EQ_4_BLOCK, currentConfig);
		testParsedTu(refTu, tu, true);
		
		// all configs == both branches
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("foo\n"));
		tu = preprocess(IF_NOT_X_EQ_4_BLOCK, allConfig);
		testParsedTu(refTu, tu);

	}

	public void testIfDefinedFooBlock() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();

		// not...
		IASTTranslationUnit tu = preprocess(IF_DEFINED_FOO_BLOCK, currentConfig);
		testParsedTu(refTu, tu, true);
		
		// is...
		macros.add(DefineFactory.createDefine("FOO"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("foo\n"));
		tu = preprocess(IF_DEFINED_FOO_BLOCK, currentConfig);
		testParsedTu(refTu, tu);
		
		// all configs == both sides
		tu = preprocess(IF_DEFINED_FOO_BLOCK, allConfig);
		testParsedTu(refTu, tu);
		macros.clear();
		tu = preprocess(IF_DEFINED_FOO_BLOCK, allConfig);
		testParsedTu(refTu, tu);
		

	}
	public void testIfDefinedFooOrBarElseBlock() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("notFooOrBar\n"));

		// not...
		IASTTranslationUnit tu = preprocess(IF_DEFINED_FOO_OR_DEFINED_BAR_ELSE_BLOCK, currentConfig);
		testParsedTu(refTu, tu);
		
		// is...
		macros.add(DefineFactory.createDefine("FOO"));
		refTu.getNodes().clear();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("foo\n"));
		tu = preprocess(IF_DEFINED_FOO_OR_DEFINED_BAR_ELSE_BLOCK, currentConfig);
		testParsedTu(refTu, tu);

		// all configs == both sides of branch
		refTu.getNodes().clear();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("foo\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("notFooOrBar\n"));
		tu = preprocess(IF_DEFINED_FOO_OR_DEFINED_BAR_ELSE_BLOCK, allConfig);
		testParsedTu(refTu, tu, true);

	}
	
	public void testXIfDefinedFooElifDefinedBarElseBlockY() {
//		final String X_IF_DEFINED_FOO_ELIF_DEFINED_BAR_ELSE_BLOCK_Y = "x\n#if defined(FOO)\nfoo\n#elif defined(BAR)\nbar\n#else\nnotFooOrBar\n#endif\ny\n";
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();

		// neither...
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("x\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("notFooOrBar\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("y\n"));
		IASTTranslationUnit tu = preprocess(X_IF_DEFINED_FOO_ELIF_DEFINED_BAR_ELSE_BLOCK_Y, currentConfig);
		testParsedTu(refTu, tu, true);
		
		// foo...
		macros.clear();
		macros.add(DefineFactory.createDefine("FOO"));
		refTu.getNodes().clear();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("x\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("foo\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("y\n"));
		tu = preprocess(X_IF_DEFINED_FOO_ELIF_DEFINED_BAR_ELSE_BLOCK_Y, currentConfig);
		testParsedTu(refTu, tu, true);

		// bar...
		macros.clear();
		macros.add(DefineFactory.createDefine("BAR"));
		refTu.getNodes().clear();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("x\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("bar\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("y\n"));
		tu = preprocess(X_IF_DEFINED_FOO_ELIF_DEFINED_BAR_ELSE_BLOCK_Y, currentConfig);
		testParsedTu(refTu, tu, true);
		
		// all configs == all sides of branch
		refTu.getNodes().clear();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("x\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("foo\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("bar\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("notFooOrBar\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("y\n"));
		tu = preprocess(X_IF_DEFINED_FOO_ELIF_DEFINED_BAR_ELSE_BLOCK_Y, allConfig);
		testParsedTu(refTu, tu, true);
	}
	
	final String USE_MACRO = ";EMPTY;\n";
	final String USE_MACRO_2 = ";EMPTY;EMPTY;\n";
	public void testMacroExpansion1() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		IASTPreprocessorTokenStreamStatement tokenStmt = ASTFactory.createPreprocessorTokenStreamStatement(";;\n");
		// ensure the "empty token" is here, because this is the only indication a macro was expanded
		tokenStmt.getTokenStream().getTokens().add(1, MacroExpandingTokenIterator.EMPTY_TOKEN);
		refTu.getNodes().add(tokenStmt);
		macros.add(DefineFactory.createDefine("EMPTY", ""));
		IASTTranslationUnit tu = preprocess(USE_MACRO, currentConfig);
		testParsedTu(refTu, tu, true);

		//
		tokenStmt = ASTFactory.createPreprocessorTokenStreamStatement(";;;\n");
		refTu.getNodes().add(tokenStmt);
		// ensure the "empty token" is here, because this is the only indication a macro was expanded
		tokenStmt.getTokenStream().getTokens().add(2, MacroExpandingTokenIterator.EMPTY_TOKEN);
		tokenStmt.getTokenStream().getTokens().add(1, MacroExpandingTokenIterator.EMPTY_TOKEN);
		tu = preprocess(USE_MACRO + USE_MACRO_2, currentConfig);
		testParsedTu(refTu, tu, true);

	}
	public void testMacroExpansion2() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(";CoNtEnT;CoNtEnT;\n"));
		
		macros.add(DefineFactory.createDefine("EMPTY", "CoNtEnT"));
		IASTTranslationUnit tu = preprocess(USE_MACRO_2, currentConfig);
		testParsedTu(refTu, tu, true);

	}
	public void testMacroExpansion3() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(";23;23;\n"));
		
		macros.add(DefineFactory.createDefine("EMPTY", "CoNtEnT"));
		macros.add(DefineFactory.createDefine("CoNtEnT", "23"));
		IASTTranslationUnit tu = preprocess(USE_MACRO_2, currentConfig);
		testParsedTu(refTu, tu, true);

	}
	public void testMacroExpansion4() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(";EMPTY;\n"));
		
		// avoid infinite recursion
		macros.add(DefineFactory.createDefine("EMPTY", "CoNtEnT"));
		macros.add(DefineFactory.createDefine("CoNtEnT", "EMPTY"));
		IASTTranslationUnit tu = preprocess(USE_MACRO, currentConfig);
		testParsedTu(refTu, tu);

	}
	
	static final String USE_MACRO_ARGS0 =
		"#define VariantTarget(name,ext) name##.##ext\n"+
		"TARGET VariantTarget(filename,exe)\n";
	public void testMacroExpansionArgs0() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("TARGET filename.exe\n"));
		
		IASTTranslationUnit tu = preprocess(USE_MACRO_ARGS0, currentConfig);
		testParsedTu(refTu, tu, true);
		
	}
	static final String USE_MACRO_ARGS1 = 
		"#define FOO(a) [a]\n"+
		"#define NONE() 123\n"+
		"#define TWICE(a,b) a NONE() FOO(b) NONE TWICE(-a,-b)\n"+
		"TARGET TWICE(filename,exe)\n";
	public void testMacroExpansionArgs1() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				"TARGET filename 123 [exe] NONE TWICE(-filename,-exe)\n")); 
		IASTTranslationUnit tu = preprocess(USE_MACRO_ARGS1, currentConfig);
		testParsedTu(refTu, tu, true);
		
	}

	final String DEFINE_1 = 
		"#define FOO 1\n#define BAR FOO\n#define BAZ 3\n"+
		"FOO+BAR+BAZ!=BAZ?!!\n";

	public void testDefineMacros() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				"1+1+3!=3?!!\n"));
		System.out.println(DEFINE_1);
		IASTTranslationUnit tu = preprocess(DEFINE_1, currentConfig);
		testParsedTu(refTu, tu, true);
		
	}
	
	final String DEFINE_2 = 
		"#define FOO 1\n#define BAR FOO==1\n"+
		"#if BAR\nOkay\n#else\nERROR\n#endif\n";
	public void testDefineMacros2() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				"Okay\n"));
		IASTTranslationUnit tu = preprocess(DEFINE_2, currentConfig);
		testParsedTu(refTu, tu, true);
		
	}

	final String UNDEF_1 = 
		"#define FOO 1\nFOO\n"+
		"#undef FOO\n"+
		"#if FOO\nERROR\n#endif\n"+
		"#if !FOO\nOkay\n#endif\n";

	public void testUndefMacros() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				"1\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				"Okay\n"));
		IASTTranslationUnit tu = preprocess(UNDEF_1, currentConfig);
		testParsedTu(refTu, tu, true);
		
	}
	
	final String TEST_H =
		"#define WINS 1\n#define CW32 1\n";
	final String TEST_INCLUDES_0 =
		"#if WINS\nERROR\n#endif\n"+
		"#include \"test.h\"\n"+
		"#if WINS\nGood\n#else\nError\n#endif\n";
	public void testIncludes() {
		parserConfig.getFilesystem().put("test.h", TEST_H);
		
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				"Good\n"));
		IASTTranslationUnit tu = preprocess(TEST_INCLUDES_0, currentConfig);
		testParsedTu(refTu, tu, true);

	}
	
	final String NESTED_IFS =
		"#if 0\n" +
		"#if MY_TARGET\n" + 
		"HEADERONLY\n" + 
		"#else\n" + 
		"HEADER\n" + 
		"#endif\n" + 
		"END\n" + 
		"#endif\n" + 
		"START RESOURCE ..\\data\\uidesign.rss\n" + 
		"\tHEADER\n" + 
		"END\n" + 
		"";
	public void testNestedIfs() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				"START RESOURCE ..\\data\\uidesign.rss\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
		"\tHEADER\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
		"END\n"));
		IASTTranslationUnit tu = preprocess(NESTED_IFS, currentConfig);
		testParsedTu(refTu, tu, true);
		
	}
	
	final String IFELSEIF_1 =
		"#if defined(WINS)\n"+
	    "deffile .\\CreateStaticDLLWINS.def\n"+
	    "#else if defined(ARM)\n"+ // note: the #else is just an else, since this is an apparent misunderstanding of the syntax
	    "deffile .\\CreateStaticDLLARM.def\n"+
	    "#endif\n";
	public void testIfElseif() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		IASTTranslationUnit tu = preprocess(IFELSEIF_1, currentConfig);
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				"deffile .\\CreateStaticDLLARM.def\n"));
		testParsedTu(refTu, tu, true);
		
	}
	
	final String TRAILING_GARBAGE_1 =
		"#ifdef FOO ^ 3\n"+
		"A\n"+
		"#else ? BAR & 1\n"+
		"B\n"+
		"#endif ! wow !\n"+
		"C\n";

	public void testTrailingGarbage() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
		"B\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
		"C\n"));
		IASTTranslationUnit tu = preprocess(TRAILING_GARBAGE_1, currentConfig);
		testParsedTu(refTu, tu, true);
		
		
	}
	
	/*
	final String NO_EOL_INCLUDE =
		"FIRST";

	final String NO_EOL_SOURCE =
		"#include \"header.h\"\n"+
		"SECOND";

	public void testNoEolAtEof() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
		"FIRST"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
		"SECOND"));
		parserConfig.getFilesystem().put("header.h", NO_EOL_INCLUDE);
		IASTTranslationUnit tu = preprocess(NO_EOL_SOURCE, currentConfig);
		testParsedTu(refTu, tu, true);

	}
*/	
	final static String TSR_SOURCE_1 =
		"#define A\n"+
		"#define B\n"+
		"stuff\n"+
		"more stuff\n"+
		"stuff\n"+
		"more stuff\n"+
		"stuff\n"+
		"more stuff\n"+
		"#include \"header1.h\"\n";
		
	final static String TSR_HEADER_1 =
		"#ifndef HEADER1_H\n"+
		"#define HEADER1_H\n"+
		"\n"+
		"#define FOO 3\n"+
		"#include \"header2.h\"\n"+
		"// \n"+
		"#endif\n";

	final static String TSR_HEADER_2 =
		"#ifndef HEADER2_H\n"+
		"#define HEADER2_H\n"+
		"\n"+
		"blah 2\n"+
		"// \n"+
		"#endif\n";

	public void testSourceReferences() {
		parserConfig.getFilesystem().put(projectPath.append("header1.h").toOSString(), TSR_HEADER_1);
		parserConfig.getFilesystem().put(projectPath.append("header2.h").toOSString(), TSR_HEADER_2);
		IASTTranslationUnit tu = preprocess(TSR_SOURCE_1, currentConfig);
		IASTTopLevelNode node = tu.getNodes().get(8);
		assertEquals("blah 2\n", node.getNewText());
		assertEquals(projectPath.append("header2.h").toOSString()+":4", node.getSourceReference());
	}
	
	/**
	 * Other directives like #define, #undef, #include, and #error should not
	 * be seen in filtered-out blocks.
	 */
	public void testFilteringDirectives() {
		IASTTranslationUnit tu = preprocess("#if 0\n#define FOO 29383792\n#endif\nThis is FOO\n", currentConfig);
		IASTTopLevelNode node = tu.getNodes().get(0);
		assertEquals("This is FOO\n", node.getNewText());
	}
	
	final String TEST_IF_MACRO_LIT = "#define FOO(a,b) a+b\n#if FOO(1,2)\npass\n#else\nfail\n#endif\n";
	final String TEST_IF_MACRO_LIT_2= "#define FOO(a,b) a+b\n#if FOO(-1,1)\nfail\n#else\npass\n#endif\n";
	public void testIfMacroLit() {
		IASTTranslationUnit tu = preprocess(TEST_IF_MACRO_LIT, currentConfig);
		IASTTopLevelNode node = tu.getNodes().get(0);
		assertEquals("pass\n", node.getNewText());
		
		tu = preprocess(TEST_IF_MACRO_LIT_2, currentConfig);
		node = tu.getNodes().get(0);
		assertEquals("pass\n", node.getNewText());
	}
	

	final String TEST_MISSING_IF = "foo\n#endif\nstuff\n";
	public void testMissingIfProblems() {
		IASTTranslationUnit tu = preprocess(TEST_MISSING_IF, currentConfig);
		IASTProblemNode[] problems = getProblems(tu);
		assertNotNull(problems);
		assertEquals(1, problems.length);
		checkMessages(problems);
		assertEquals("foo\nstuff\n", tu.getNewText());

	}
	final String TEST_MISSING_ENDIF = "#if 1\nfoo\n";
	public void testMissingEndifProblems() {
		IASTTranslationUnit tu = preprocess(TEST_MISSING_ENDIF, currentConfig);
		IASTProblemNode[] problems = getProblems(tu);
		assertNotNull(problems);
		assertEquals(1, problems.length);
		checkMessages(problems);
		assertEquals("foo\n", tu.getNewText());
	}

	final String TEST_EXTRA_ELSE = "#if 1\nfoo\n#else\nbar\n#else\nbaz\n#endif\nblah\n";
	public void testExtraElseProblems() {
		IASTTranslationUnit tu = preprocess(TEST_EXTRA_ELSE, currentConfig);
		IASTProblemNode[] problems = getProblems(tu);
		assertNotNull(problems);
		assertEquals(1, problems.length);
		checkMessages(problems);
		assertEquals("foo\nblah\n", tu.getNewText());
	}
	final String TEST_ERROR_DIRECTIVE = "foo\n#error bad mojo\n#warning not so bad\nbar\n";
	public void testErrorDirective() {
		IASTTranslationUnit tu = preprocess(TEST_ERROR_DIRECTIVE, currentConfig);
		IASTProblemNode[] problems = getProblems(tu);
		assertNotNull(problems);
		assertEquals(2, problems.length);
		assertEquals(IMessage.ERROR, problems[0].getMessage().getSeverity());
		assertEquals(IMessage.WARNING, problems[1].getMessage().getSeverity());
		checkMessages(problems);
		assertEquals("foo\nbar\n", tu.getNewText());
	}
	final String TEST_ERROR_DIRECTIVE_COND = "#if FOO\nfoo\n#else\n#error bad mojo\n#endif\nbar\n";
	public void testErrorDirectiveCond() {
		// don't accept errors in merged branch case, because 
		// we can't tell if they're valid or not
		IASTTranslationUnit tu = preprocess(TEST_ERROR_DIRECTIVE_COND, allConfig);
		IASTProblemNode[] problems = getProblems(tu);
		assertNotNull(problems);
		assertEquals(0, problems.length);
		checkMessages(problems);
		assertEquals("foo\nbar\n", tu.getNewText());
		
		// in this case, the bad case really evaluated
		tu = preprocess(TEST_ERROR_DIRECTIVE_COND, currentConfig);
		problems = getProblems(tu);
		assertNotNull(problems);
		assertEquals(1, problems.length);
		checkMessages(problems);
		assertEquals("bar\n", tu.getNewText());
		
		// in this case, the bad case is skipped
		macros.add(DefineFactory.createDefine(
				"FOO", 
				null, // just for the hell of it
				"1"));
		tu = preprocess(TEST_ERROR_DIRECTIVE_COND, currentConfig);
		problems = getProblems(tu);
		assertNotNull(problems);
		assertEquals(0, problems.length);
		checkMessages(problems);
		assertEquals("foo\nbar\n", tu.getNewText());

	}

	public void testBadIfElseFiltering() {
		String textOrig =  
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
				"#endif\r\n";
		macros.add(DefineFactory.createDefine("ARMCC"));
		IASTTranslationUnit tu = preprocess(textOrig, currentConfig);
		assertEquals(3, tu.getNodes().size()); // two newlines and DEFFILE
	}
	
	public void testBadIfElseFiltering2() {
		String textClean =  
			"#if defined(ARMCC)\r\n" + 
			"#if defined(RD_MUSIC_COLLECTION_AUTO_REFRESH)\r\n" + 
			"deffile ..\\eabi\\new\\  \r\n" + 
			"#else\r\n" + 
			"deffile ..\\eabi\\ \r\n" + 
			"#endif\r\n" + 
			"#elif defined(WINSCW)\r\n" + 
			"#if defined(RD_MUSIC_COLLECTION_AUTO_REFRESH)\r\n" + 
			"deffile ..\\bwinscw\\new\\ \r\n" + 
			"#else\r\n" + 
			"deffile ..\\bwinscw\\ \r\n" + 
			"#endif\r\n" + 
			"#endif\r\n";
		macros.add(DefineFactory.createDefine("ARMCC"));
		IASTTranslationUnit tu = preprocess(textClean, currentConfig);
		assertEquals(1, tu.getNodes().size()); // DEFFILE
	}

	public void testBug2825() {
		// ensure surrounding spaces are not included when expanding argument
		String text = "PRJ_EXPORTS\r\n" + 
				"..\\SrcData\\feeds_view_template.html   \\epoc32\\data\\c\\feeds_view_template.html\r\n" + 
				"#define BR_EXPORT_(fname) ..\\SrcData\\##fname  \\epoc32\\winscw\\c\\private\\10008d39\\##fname\r\n" + 
				"..\\SrcData\\DefaultHpTemplate.html  \\epoc32\\winscw\\c\\private\\10008d39\\DefaultHpTemplate.html\r\n" + 
				"BR_EXPORT_( pics\\arrow_down.gif )\r\n" + 
				"BR_EXPORT_(pics\\arrow_left.gif )\r\n" + 
				"BR_EXPORT_( pics\\arrow_right.gif)\r\n" + 
				"#undef BR_EXPORT_\r\n"; 
		IASTTranslationUnit tu = preprocess(text, currentConfig);
		assertEquals(6, tu.getNodes().size());
		assertEquals("..\\SrcData\\pics\\arrow_down.gif \\epoc32\\winscw\\c\\private\\10008d39\\pics\\arrow_down.gif\r\n", 
				tu.getNodes().get(3).getNewText());
		assertEquals("..\\SrcData\\pics\\arrow_left.gif \\epoc32\\winscw\\c\\private\\10008d39\\pics\\arrow_left.gif\r\n", 
				tu.getNodes().get(4).getNewText());
		assertEquals("..\\SrcData\\pics\\arrow_right.gif \\epoc32\\winscw\\c\\private\\10008d39\\pics\\arrow_right.gif\r\n", 
				tu.getNodes().get(5).getNewText());
	}

	// this never failed... likely a path resolution issue, but keep the test anyway
	public void testBug2919() {
		/*
With the above MMP file, wstop.cpp will be parsed correctly but all
bitmap.copp, capkey.cpp and client.cpp are not found. They will be listed up in
Missing C/C++ sources as ../src/common/generic/graphics/
wserv/server/bitmap.cpp and so on.
It should be "..\..\..\..\src\common\generic\graphics\wserv\group\..
\server\bitmap.cpp".
		 */
		String text = "#define ToOriginalPath(path)  ..\\..\\..\\..\\src\\common\\generic\\graphics\\wserv\\group\\##path\r\n" + 
				"SOURCEPATH\tToOriginalPath(..\\server)\r\n" + 
				"SOURCE\tbitmap.cpp capkey.cpp client.cpp\r\n" + 
				"SOURCEPATH\t..\\server\r\n" + 
				"SOURCE\twstop.cpp\r\n";
		IASTTranslationUnit tu = preprocess(text, currentConfig);
		assertEquals(4, tu.getNodes().size());
		String exp = "SOURCEPATH ..\\..\\..\\..\\src\\common\\generic\\graphics\\wserv\\group\\..\\server\r\n"; 
		String got = tu.getNodes().get(0).getNewText(); 
		assertEquals(exp, got);

	}
	
	public void testBadIf() {
		String text = 
				"#if MACRO\n"+
				"START BITMAP foo.mbm\n"+
				"#elif ALTMAC\n"+
				"START BITMAP foo.mbm\n"+
				"#else\n"+
				"START BITMAP bar.mbm\n"+
				"#endif\n";
			
		// this is tested as a value but it's blank -- should not cause IllegalStateException
		macros.add(DefineFactory.createDefine("ALTMAC", ""));
		IASTTranslationUnit tu = preprocess(text, allConfig);
		
		// three stmts, one problem
		assertEquals(4, tu.getNodes().size());

		IASTProblemNode[] problems = getProblems(tu);
		assertNotNull(problems);
		assertEquals(1, problems.length);
		checkMessages(problems);

	}
	
	public void testBug3170() {
		String text =
			"#define ORIG 0x123\n"+
			"#define COPY ORIG\n" +
			"0x456 COPY\n";
		
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(1, tu.getNodes().size());
		assertEquals("0x456 0x123\n", tu.getNodes().get(0).getNewText());
		
	}

	public void testDotDotMacro() {
		String text =
			"#define SRCDIR ..\\src\n"+
			"SRCDIR\n";
		
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(1, tu.getNodes().size());
		assertEquals("..\\src\n", tu.getNodes().get(0).getNewText());
		
	}
	
	public void testCatenation1() {
		String text = 
			"#define PATH(x,y) x ## \\ ## y\n"+
			"include PATH(src,file.cpp)\n";
		
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(1, tu.getNodes().size());
		assertEquals("include src\\file.cpp\n", tu.getNodes().get(0).getNewText());
		
			
	}
	public void testCatenation2() {
		String text = 
			"#define concat2(a,b) concatx(a,b,,,,,,)\r\n" + 
			"#define concat3(a,b,c) concatx(a,b,c,,,,,)\r\n" + 
			"#define concat4(a,b,c,d) concatx(a,b,c,d,,,,)\r\n" + 
			"#define concat5(a,b,c,d,e) concatx(a,b,c,d,e,,,)\r\n" + 
			"#define concat6(a,b,c,d,e,f) concatx(a,b,c,d,e,f,,)\r\n" + 
			"#define concat7(a,b,c,d,e,f,g) concatx(a,b,c,d,e,f,g,)\r\n" + 
			"#define concat8(a,b,c,d,e,f,g,h) concatx(a,b,c,d,e,f,g,h)\r\n" + 
			"#define concatx(x1,x2,x3,x4,x5,x6,x7,x8) x1 ## x2 ## x3 ## x4 ## x5 ## x6 ## x7 ## x8 \r\n" + 
			"#define AbsPathUnderCedarGenericBase(x)         \\src\\cedar\\generic\\base ## x\r\n" + 
			"include AbsPathUnderCedarGenericBase(\\e32\\drivers\\pbus\\mmc\\sdcard\\sdcard3c)\n";
		
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(1, tu.getNodes().size());
		assertEquals("include \\src\\cedar\\generic\\base\\e32\\drivers\\pbus\\mmc\\sdcard\\sdcard3c\n", tu.getNodes().get(0).getNewText());
		
		
	}
	public void testBug4822() {
		String text =
			"#define VariantMediaDefIncludePath \\epoc32\\include\\rapido\r\n" + 
			"#define VariantIncludePath         VariantMediaDefIncludePath\r\n" + 
			"#define AbsPathUnderCedarGenericBase(x)         \\src\\cedar\\generic\\base ## x\r\n" +
			"SYSTEMINCLUDE VariantIncludePath\r\n"+
			"SYSTEMINCLUDE       AbsPathUnderCedarGenericBase(\\e32\\drivers\\pbus\\mmc\\sdcard\\sdcard3c) // Include SD specific files\r\n" + 
			"";
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(2, tu.getNodes().size());
		assertEquals("SYSTEMINCLUDE \\src\\cedar\\generic\\base\\e32\\drivers\\pbus\\mmc\\sdcard\\sdcard3c // Include SD specific files\n", 
				tu.getNodes().get(1).getNewText());
		
	}
	
	// This part passes
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
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(1, tu.getNodes().size());
		
		IASTPreprocessorTokenStreamStatement stmt = ((IASTPreprocessorTokenStreamStatement) tu.getNodes().get(0));
		assertEquals("TARGET myappserver3.dll\r\n", 
				stmt.getNewText());
		
		List<IToken> tokens = stmt.getTokenStream().getTokens();
		assertEquals(6, tokens.size());

	}
	
	public void testBug6176() {
		String inclText = "#define _GENERIC_BLD_       \"test.inf\"\r\n";
		parserConfig.getFilesystem().put(projectPath.append("camproductconfig.mmh").toOSString(), inclText);
		String inclText2 = "TARGET FOO\r\n";
		parserConfig.getFilesystem().put(projectPath.append("test.inf").toOSString(), inclText2);

		String text = 
			"#include \"camproductconfig.mmh\"\r\n"+
			"#include _GENERIC_BLD_\r\n";
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(1, tu.getNodes().size());
		
		IASTPreprocessorTokenStreamStatement stmt = ((IASTPreprocessorTokenStreamStatement) tu.getNodes().get(0));
		assertEquals("TARGET FOO\r\n", 
				stmt.getNewText());

	}
	
	public void testBug6343() {
		String text =
			"#define concat2(a,b) concatx(a,b,,,,,,)\r\n" + 
			"#define concat3(a,b,c) concatx(a,b,c,,,,,)\r\n" + 
			"#define concat4(a,b,c,d) concatx(a,b,c,d,,,,)\r\n" + 
			"#define concat5(a,b,c,d,e) concatx(a,b,c,d,e,,,)\r\n" + 
			"#define concat6(a,b,c,d,e,f) concatx(a,b,c,d,e,f,,)\r\n" + 
			"#define concat7(a,b,c,d,e,f,g) concatx(a,b,c,d,e,f,g,)\r\n" + 
			"#define concat8(a,b,c,d,e,f,g,h) concatx(a,b,c,d,e,f,g,h)\r\n" + 
			"#define concatx(x1,x2,x3,x4,x5,x6,x7,x8) x1 ## x2 ## x3 ## x4 ## x5 ## x6 ## x7 ## x8 \r\n" +
			"#define BAPPEA_FEA1 0\n"+
			"#define BAPPEA_FEA2 1\n"+
			"#define BAPPEA_FEA3 0\n"+
			"#define BAPPEA_FEA4 1\n"+
			"#define BAPPEA_FEA5 0\n"+
			"#define BAPPEA_FEA6 1\n"+
			"#define BAPPEA_FEA7 0\n"+
			"#define BAPPEA_FEA8 1\n"+
			"#define BAPPEA_FEA9 0\n"+
			"#define BAPPEA_FEA10 1\n"+
			"#define TMP1 concat5(BAPPEA_FEA1, BAPPEA_FEA2, BAPPEA_FEA3, BAPPEA_FEA4,BAPPEA_FEA5)\n" + 
			"#define TMP2 concat5(BAPPEA_FEA6, BAPPEA_FEA7, BAPPEA_FEA8, BAPPEA_FEA9,BAPPEA_FEA10)\n" + 
			"TMP1\n" + 
			"TMP2\n" + 
			"#define BAPPEA_VAR concat2(TMP1, TMP2)\n" + 
			"BAPPEA_VAR\n";
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(3, tu.getNodes().size());
		
		IASTPreprocessorTokenStreamStatement stmt;
		stmt = ((IASTPreprocessorTokenStreamStatement) tu.getNodes().get(0));
		assertEquals("01010", 
				stmt.getNewText().trim());
		stmt = ((IASTPreprocessorTokenStreamStatement) tu.getNodes().get(1));
		assertEquals("10101", 
				stmt.getNewText().trim());
		stmt = ((IASTPreprocessorTokenStreamStatement) tu.getNodes().get(2));
		assertEquals("0101010101", 
				stmt.getNewText().trim());
	}
	
	public void testBug6596() {
		String text = 
			"#define help_database_path \"my string\"\n"+
			"STRUCT TBUF256 { LTEXT buf; }\n"+
			"RESOURCE TBUF256 { r_path = \"my string\"; }\n"+
			"RESOURCE TBUF256 { r_path = help_database_path; }\n"
			;
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(3, tu.getNodes().size());
		
		IASTPreprocessorTokenStreamStatement stmt = ((IASTPreprocessorTokenStreamStatement) tu.getNodes().get(0));
		assertEquals("STRUCT TBUF256 { LTEXT buf; }\n", 
				stmt.getNewText());

		// normal string
		stmt = ((IASTPreprocessorTokenStreamStatement) tu.getNodes().get(1));
		assertEquals("RESOURCE TBUF256 { r_path = \"my string\"; }\n", 
				stmt.getNewText());
		// now be sure the tokens are as expected
		List<IToken> tokens = stmt.getTokenStream().getTokens();
		assertEquals(9, tokens.size());
		assertEquals(IToken.STRING, tokens.get(5).getType());
		
		// macro-expanded string
		stmt = ((IASTPreprocessorTokenStreamStatement) tu.getNodes().get(2));
		assertEquals("RESOURCE TBUF256 { r_path = \"my string\"; }\n", 
				stmt.getNewText());
		// now be sure the tokens are as expected
		tokens = stmt.getTokenStream().getTokens();
		assertEquals(9, tokens.size());
		assertEquals(IToken.STRING, tokens.get(5).getType());
	}

	/** Don't allow spaces between a macro and its argument declarations! */
	public void testBug6600() throws Exception {
		String text = 
			"#define MACRO (a|b)\n"+
			"MACRO MACRO";
			
		IASTTranslationUnit tu = preprocess(text, allConfig);
		assertEquals(1, tu.getNodes().size());
		
		IASTPreprocessorTokenStreamStatement stmt = ((IASTPreprocessorTokenStreamStatement) tu.getNodes().get(0));
		assertEquals("(a|b) (a|b)", stmt.getNewText()); 
	}
}