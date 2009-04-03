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
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentTokenLocation;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IToken;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.DocumentTokenLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

/**
 * Test the preprocessor-level parser. 
 *
 */
public class TestPreParser extends BaseTest {
	static final String TEST_EMPTY = "";
	static final String TEST_ONE_LINE = "\n";
	static final String TEST_C_COMMENT_LINE = "/** my test\n\tis cool\n*/\n";
	static final String TEST_CPP_COMMENT_LINE_1 = "// test\r\n";
	static final String TEST_CPP_COMMENT_LINE_2 = "// test";
	static final String TEST_TWO_LINES = "\n\n";
	static final String TEST_IF_1 = "#if 1\n";
	static final String TEST_IF_X = "#if X\n";
	static final String TEST_IF_1_C = "#if 1 // true, always\n";
	static final String TEST_IF_X_EQ_1 = "#if X == 1\n";
	static final String TEST_ELIF_X_EQ_1 = "#elif X == 1\n";
	static final String TEST_ELSE = "#else\r\n";
	static final String TEST_ENDIF = "#endif\r";
	static final String TEST_IFDEF_X23 = "#ifdef X23\n";
	static final String TEST_IFNDEF_X23 = "#ifndef X23\n";

	static final String TEST_PP_STATEMENTS = "// first\n#if X>6\n// true\n#else\n// false\n#endif\n// out\n";
	
	static final String TEST_STATEMENTS_1 = "// first\nsecond\nthird 1 2 3 \nfourth?  no, it''s fifth!\n";
	static final String TEST_STATEMENTS_2 = "// first\nsecond\nthird /* or */ 1 2 3 \nfourth/**/?  no, it/* apo */''s fifth!\n";
	
	static final String TEST_DEFINE_N_N = "#define FOO\n";
	static final String TEST_DEFINE_0_N = "#define FOO()\n";
	static final String TEST_DEFINE_1_N = "#define FOO(x)\n";
	static final String TEST_DEFINE_2_N = "#define FOO(x,y)\n";
	static final String TEST_DEFINE_V_N = "#define FOO(x,...)\n";
	static final String TEST_DEFINE_N = "#define FOO bar\n";
	static final String TEST_DEFINE_0 = "#define FOO() bar\n";
	static final String TEST_DEFINE_1 = "#define FOO(x) bar\n";
	static final String TEST_DEFINE_2 = "#define FOO(x,y) bar\n";
	static final String TEST_DEFINE_3 = "#define FOO(x) [x]\n";
	
	static final String TEST_CATENATION_0 = "// multi-line \\\ncomment\n";
	static final String TEST_STUFF_AND_CPP_COMMENT_LINE = "foo  // test\r\n";
	static final String TEST_CATENATED_NUMBERS = "12\\\n3 4\\\r\n5\\\r6";
	static final String TEST_CATENATED_IDENTIFIERS = "third\\\n_rate left\\\n5\n";
	static final String TEST_CATENATED_PUNC = "!\\\n= =\\\n=\n";
	
	private IDocument document;
	private IDocumentTokenLocation location;
	private Path path;
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.path = new Path("mydir/test.txt");
		this.document = DocumentFactory.createDocument();
		this.location = new DocumentTokenLocation(document, path);
	}	
	
	protected IASTTranslationUnit parse(String text) {
		IDocumentParser parser = ParserFactory.createPreParser();
		document.set(text);
		IASTTranslationUnit tu = parser.parse(new Path("test.txt"), document);
		if (parser.hadErrors()) {
			assertNoProblems(tu);
			assertFalse(parser.hadErrors());
		}
		return tu;
	}
	
	public void testEmpty() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_EMPTY);
		testParsedTu(refTu, tu);
	}
	
	public void testOneLine() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("\n"));
		IASTTranslationUnit tu = parse(TEST_ONE_LINE);
		testParsedTu(refTu, tu);
	}

	public void testCppCommentLine1() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("// test\r\n"));
		IASTTranslationUnit tu = parse(TEST_CPP_COMMENT_LINE_1);
		testParsedTu(refTu, tu);
	}
	public void testCppCommentLine2() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("// test"));
		IASTTranslationUnit tu = parse(TEST_CPP_COMMENT_LINE_2);
		testParsedTu(refTu, tu);
	}
	public void testCppCatenatedCommentLine() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("// multi-line comment\n"));
		IASTTranslationUnit tu = parse(TEST_CATENATION_0);
		testParsedTu(refTu, tu);
	}
	
	public void testCCommentLine() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("/** my test\n\tis cool\n*/\n"));
		IASTTranslationUnit tu = parse(TEST_C_COMMENT_LINE);
		testParsedTu(refTu, tu);
	}
	
	public void testIf() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorIfStatement(
				ASTFactory.createPreprocessorTokenStream("1")));
		IASTTranslationUnit tu = parse(TEST_IF_1);
		testParsedTu(refTu, tu);
	}
	public void testIfComment() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorIfStatement(
				ASTFactory.createPreprocessorTokenStream("1")));
		IASTTranslationUnit tu = parse(TEST_IF_1_C);
		assertEquals("#if 1 // true, always\n", tu.getOriginalText());
		testParsedTu(refTu, tu);
	}
	
	public void testIfX() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorIfStatement(
				ASTFactory.createPreprocessorTokenStream("X")));
		IASTTranslationUnit tu = parse(TEST_IF_X);
		testParsedTu(refTu, tu);
	}
	
	public void testIfdefX23() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorIfdefStatement(
				ASTFactory.createPreprocessorLiteralExpression("X23")));
		IASTTranslationUnit tu = parse(TEST_IFDEF_X23);
		testParsedTu(refTu, tu);
	}
	
	public void testIfndefX23() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorIfndefStatement(
				ASTFactory.createPreprocessorLiteralExpression("X23")));
		IASTTranslationUnit tu = parse(TEST_IFNDEF_X23);
		testParsedTu(refTu, tu);
	}
	
	public void testElifXeq1() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorElifStatement(
				ASTFactory.createPreprocessorTokenStream("X == 1")));
		IASTTranslationUnit tu = parse(TEST_ELIF_X_EQ_1);
		testParsedTu(refTu, tu);
	}
	
	
	public void testEndif() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorEndifStatement());
		IASTTranslationUnit tu = parse(TEST_ENDIF);
		testParsedTu(refTu, tu);
	}

	public void testElse() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorElseStatement());
		IASTTranslationUnit tu = parse(TEST_ELSE);
		testParsedTu(refTu, tu);
	}

	public void testPPStatements() {
		//	// first\n#if X>6\n// true\n#else\n// false\n#endif\n// out\n";
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("// first\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorIfStatement(
				ASTFactory.createPreprocessorTokenStream("X>6")));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("// true\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorElseStatement());
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("// false\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorEndifStatement());
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("// out\n"));
		
		IASTTranslationUnit tu = parse(TEST_PP_STATEMENTS);
		testParsedTu(refTu, tu);

	}
	
	public void testStuffAndCppCommentLine() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.IDENTIFIER, "foo", location, 0, false, true),
						ASTFactory.createToken(IToken.EOL, "  // test\r\n", location, 5, false, false),
				}
		));

		IASTTranslationUnit tu = parse(TEST_STUFF_AND_CPP_COMMENT_LINE);
		testParsedTu(refTu, tu);
	}


	public void testStatements1() {
		//	// first\nsecond\n\tthird 1 2 3\nfourth?  no, it's fifth!\n
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("// first\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.IDENTIFIER, "second", location, 0, false, true),
						ASTFactory.createToken(IToken.EOL, "\n", location, 6, false, false),
				}
		));
		// TODO: retain indents
		String template = "third 1 2 3 \n";
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.IDENTIFIER, "third", location, 0, false, true),
						ASTFactory.createToken(IToken.NUMBER, "1", location, template.indexOf("1"), true, false),
						ASTFactory.createToken(IToken.NUMBER, "2", location, template.indexOf("2"), true, false),
						ASTFactory.createToken(IToken.NUMBER, "3", location, template.indexOf("3"), true, false),
						ASTFactory.createToken(IToken.EOL, " \n", location, template.indexOf(" \n"), false, false),
				}
		));
		
		// ' is a char literal here
		template = "fourth? no, it''s fifth!\n";
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.IDENTIFIER, "fourth", location, 0, false, true),
						ASTFactory.createToken(IToken.PUNC, "?", location, template.indexOf("?"), false, false),
						ASTFactory.createToken(IToken.IDENTIFIER, "no", location, template.indexOf("no"), true, false),
						ASTFactory.createToken(IToken.PUNC, ",", location, template.indexOf(","), false, false),
						ASTFactory.createToken(IToken.IDENTIFIER, "it", location, template.indexOf("it"), true, false),
						ASTFactory.createToken(IToken.CHAR, "''", location, template.indexOf("'"), false, false),
						ASTFactory.createToken(IToken.IDENTIFIER, "s", location, template.indexOf("s"), false, false),
						ASTFactory.createToken(IToken.IDENTIFIER, "fifth", location, template.indexOf("fifth"), true, false),
						ASTFactory.createToken(IToken.PUNC, "!", location, template.indexOf("!"), false, false),
						ASTFactory.createToken(IToken.EOL, "\n", location, template.indexOf("\n"), false, false),
				}
		));
		
		IASTTranslationUnit tu = parse(TEST_STATEMENTS_1);
		testParsedTu(refTu, tu);

	}
	
	public void testStatements2() {
		//"// first\nsecond\nthird /* or */ 1 2 /*???*/ 3 \nfourth/**/?  no, it/* apo */'s fifth!\n";
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("// first\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.IDENTIFIER, "second", location, 0, false, true),
						ASTFactory.createToken(IToken.EOL, "\n", location, 6, false, false),
				}
		));
		// TODO: retain indents
		String template = "third 1 2 3 \n";
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.IDENTIFIER, "third", location, 0, false, true),
						ASTFactory.createToken(IToken.NUMBER, "1", location, template.indexOf("1"), true, false),
						ASTFactory.createToken(IToken.NUMBER, "2", location, template.indexOf("2"), true, false),
						ASTFactory.createToken(IToken.NUMBER, "3", location, template.indexOf("3"), true, false),
						ASTFactory.createToken(IToken.EOL, " \n", location, template.indexOf(" \n"), false, false),
				}
		));
		template = "fourth? no, it''s fifth!\n";
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.IDENTIFIER, "fourth", location, 0, false, true),
						ASTFactory.createToken(IToken.PUNC, "?", location, template.indexOf("?"), false, false),
						ASTFactory.createToken(IToken.IDENTIFIER, "no", location, template.indexOf("no"), true, false),
						ASTFactory.createToken(IToken.PUNC, ",", location, template.indexOf(","), false, false),
						ASTFactory.createToken(IToken.IDENTIFIER, "it", location, template.indexOf("it"), true, false),
						ASTFactory.createToken(IToken.CHAR, "''", location, template.indexOf("'"), false, false),
						ASTFactory.createToken(IToken.IDENTIFIER, "s", location, template.indexOf("s"), false, false),
						ASTFactory.createToken(IToken.IDENTIFIER, "fifth", location, template.indexOf("fifth"), true, false),
						ASTFactory.createToken(IToken.PUNC, "!", location, template.indexOf("!"), false, false),
						ASTFactory.createToken(IToken.EOL, "\n", location, template.indexOf("\n"), false, false),
				}
		));
		
		IASTTranslationUnit tu = parse(TEST_STATEMENTS_2);
		testParsedTu(refTu, tu);

	}
	
	public void testCatenatedNumbers() {
		//"12\\\n3 4\\\r\n5\\\r6"
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.NUMBER, "123", location, 0, false, true),
						ASTFactory.createToken(IToken.NUMBER, "456", location, TEST_CATENATED_NUMBERS.indexOf("4"), true, false),
						// phantom EOL before EOF
						ASTFactory.createToken(IToken.EOL, "", location, TEST_CATENATED_NUMBERS.length(), false, false),
				}));
		
		IASTTranslationUnit tu = parse(TEST_CATENATED_NUMBERS);
		testParsedTu(refTu, tu);
	}
	
	public void testCatenatedIdentifiers() {
		//"third\\\n_rate left\\\n5\n"
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.IDENTIFIER, "third_rate", location, 0, false, true),
						ASTFactory.createToken(IToken.IDENTIFIER, "left5", location, TEST_CATENATED_IDENTIFIERS.indexOf("left"), true, false),
						ASTFactory.createToken(IToken.EOL, "\n", location, TEST_CATENATED_IDENTIFIERS.length(), false, false),
				}));
		
		IASTTranslationUnit tu = parse(TEST_CATENATED_IDENTIFIERS);
		testParsedTu(refTu, tu);
	}
	
	public void testCatenatedPunc() {
		//"!\\\n= =\\\n=\n"
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.PUNC, "!", location, 0, false, true),
						// tokens are still single-char, but watch the spaces
						ASTFactory.createToken(IToken.PUNC, "=", location, TEST_CATENATED_PUNC.indexOf("\n=")+1, false, true),
						ASTFactory.createToken(IToken.PUNC, "=", location, TEST_CATENATED_PUNC.indexOf(" =")+1, true, false),
						ASTFactory.createToken(IToken.PUNC, "=", location, TEST_CATENATED_PUNC.lastIndexOf("=\n")+1, false, false),
						ASTFactory.createToken(IToken.EOL, "\n", location, TEST_CATENATED_PUNC.length(), false, false),
				}));
		
		IASTTranslationUnit tu = parse(TEST_CATENATED_PUNC);
		testParsedTu(refTu, tu);
	}
	public void testDefines() {
		IASTTranslationUnit refTu;
		
		IASTLiteralTextNode define_foo;
		IASTLiteralTextNode define_x;
		IASTLiteralTextNode define_y;
		IASTLiteralTextNode define_ell;
		IASTPreprocessorTokenStream define_bar;
		IASTTranslationUnit tu;

		////
		define_foo = ASTFactory.createPreprocessorLiteralTextNode("FOO");

		refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorDefineStatement(
				define_foo, null, null));
		tu = parse(TEST_DEFINE_N_N);
		testParsedTu(refTu, tu);
		
		/////
		define_foo = ASTFactory.createPreprocessorLiteralTextNode("FOO");
		
		refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorDefineStatement(
				define_foo, ASTFactory.createLiteralTextNodeList(), null));
		tu = parse(TEST_DEFINE_0_N);
		testParsedTu(refTu, tu);
		
		/////
		define_foo = ASTFactory.createPreprocessorLiteralTextNode("FOO");
		define_x = ASTFactory.createPreprocessorLiteralTextNode("x");
		define_y = ASTFactory.createPreprocessorLiteralTextNode("y");
		
		refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorDefineStatement(
				define_foo, ASTFactory.createLiteralTextNodeList(
						new IASTLiteralTextNode[] { define_x, define_y } ), null));
		tu = parse(TEST_DEFINE_2_N);
		testParsedTu(refTu, tu);

		////
		define_foo = ASTFactory.createPreprocessorLiteralTextNode("FOO");
		define_x = ASTFactory.createPreprocessorLiteralTextNode("x");
		define_y = ASTFactory.createPreprocessorLiteralTextNode("y");
		define_ell = ASTFactory.createPreprocessorLiteralTextNode("...");
		
		refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorDefineStatement(
				define_foo, ASTFactory.createLiteralTextNodeList(
						new IASTLiteralTextNode[] { define_x, define_ell } ), null));
		tu = parse(TEST_DEFINE_V_N);
		testParsedTu(refTu, tu);

		////
		////
		define_foo = ASTFactory.createPreprocessorLiteralTextNode("FOO");
		define_bar = createSimpleTokenStream("bar"); 

		refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorDefineStatement(
				define_foo, null, define_bar));
		tu = parse(TEST_DEFINE_N);
		testParsedTu(refTu, tu);
		
		/////
		define_foo = ASTFactory.createPreprocessorLiteralTextNode("FOO");
		define_bar = createSimpleTokenStream("bar");
		
		refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorDefineStatement(
				define_foo, ASTFactory.createLiteralTextNodeList(), define_bar));
		tu = parse(TEST_DEFINE_0);
		testParsedTu(refTu, tu);
		
		/////
		define_foo = ASTFactory.createPreprocessorLiteralTextNode("FOO");
		define_x = ASTFactory.createPreprocessorLiteralTextNode("x");
		define_y = ASTFactory.createPreprocessorLiteralTextNode("y");
		define_bar = createSimpleTokenStream("bar");
		
		refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorDefineStatement(
				define_foo, ASTFactory.createLiteralTextNodeList(
						new IASTLiteralTextNode[] { define_x, define_y } ), define_bar));
		tu = parse(TEST_DEFINE_2);
		testParsedTu(refTu, tu);

		////
		IASTPreprocessorTokenStream define_bracs_x = createSimpleTokenStream("[x]");
		assertEquals(3, define_bracs_x.getTokens().size());
		assertEquals("[", define_bracs_x.getTokens().get(0).getText());
		assertEquals("x", define_bracs_x.getTokens().get(1).getText());
		assertEquals("]", define_bracs_x.getTokens().get(2).getText());
		
		define_x.setParent(null);
		define_foo.setParent(null);
		refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorDefineStatement(
				define_foo, ASTFactory.createLiteralTextNodeList(
						new IASTLiteralTextNode[] { define_x } ), define_bracs_x));
		
		tu = parse(TEST_DEFINE_3);
		testParsedTu(refTu, tu);

	}
	
	
	final String TEST_INCLUDES = 
		"#include \"test.h\"\n#include <sys\\types.h>\n";
	public void testIncludes() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorIncludeStatement(
				"test.h", true));
		refTu.getNodes().add(ASTFactory.createPreprocessorIncludeStatement(
				"sys\\types.h", false));
		IASTTranslationUnit tu = parse(TEST_INCLUDES);
		testParsedTu(refTu, tu);

	}
	
	public void testLongFile() {
		// the old parser was recursive and hit stack overflows
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 50000; i++) {
			builder.append('\n');
		}
		try {
			parse(builder.toString());
		} catch (StackOverflowError e) {
			fail();
		}
	}
	public void testLongLine() {
		// the old parser was recursive and hit stack overflows
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 50000; i++) {
			builder.append("BASEADDRESS ");
		}
		try {
			parse(builder.toString());
		} catch (StackOverflowError e) {
			fail();
		}

	}
	
	static final String TEST_ERRORS = 
		"test 1\n"+
		"#error \"this file is unknown\"\n"+
		"text 2\n"+
		"#error\n"+
		"text 3\n";
	public void testError() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("test 1\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorErrorStatement(
				"\"this file is unknown\""));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("text 2\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorErrorStatement(
		""));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("text 3\n"));
		IASTTranslationUnit tu = parse(TEST_ERRORS);
		testParsedTu(refTu, tu);
	}
	
	static final String TEST_WARNINGS = 
		"test 1\n"+
		"#warning \"this file is unknown\"\n"+
		"text 2\n"+
		"#warning\n"+
		"text 3\n";
	public void testWarnings() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("test 1\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorWarningStatement(
				"\"this file is unknown\""));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("text 2\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorWarningStatement(
		""));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("text 3\n"));
		IASTTranslationUnit tu = parse(TEST_WARNINGS);
		testParsedTu(refTu, tu);
	}
	
	static final String TEST_PRAGMAS = 
		"test 1\n"+
		"#pragma \"this file is unknown\"\n"+
		"text 2\n"+
		"#pragma message(\"read this\")\n"+
		"text 3\n"+
		"#pragma foo";
	public void testPragmas() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("test 1\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorPragmaStatement(
				"\"this file is unknown\""));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("text 2\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorPragmaStatement(
		"message(\"read this\")"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("text 3\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorPragmaStatement(
		"foo"));
		IASTTranslationUnit tu = parse(TEST_PRAGMAS);
		testParsedTu(refTu, tu);
	}

	static final String TEST_UNKNOWN = 
		"test 1\n"+
		"#slop \"this file is unknown\"\n"+
		"text 2\n"+
		"#mop\n"+
		"text 3\n";
	public void testUnknownDirectives() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("test 1\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorUnknownStatement(
				"slop \"this file is unknown\""));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("text 2\n"));
		refTu.getNodes().add(ASTFactory.createPreprocessorUnknownStatement(
		"mop"));
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement("text 3\n"));
		IASTTranslationUnit tu = parse(TEST_UNKNOWN);
		testParsedTu(refTu, tu);
	}
	
	public void testLeadingSpace() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(" \tha ha\n"));
		IASTTranslationUnit tu = parse(" \tha ha\n");
		testParsedTu(refTu, tu);
		assertEquals(0, ((IDocumentSourceRegion)tu.getNodes().get(0).getSourceRegion())
				.getRegion().getOffset());
		
	}
	
	private static final String TEST_CHARACTERS = "3,'5', '\\n'; '\\u00A9'\n";

	public void testCharacters() {
		//" 3,'5', '\n'
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.NUMBER, "3", location, 0, false, true),
						ASTFactory.createToken(IToken.RAW, ",", location, TEST_CHARACTERS.indexOf(","), false, false),
						ASTFactory.createToken(IToken.CHAR, "'5'", location, TEST_CHARACTERS.indexOf("'"), false, false),
						ASTFactory.createToken(IToken.RAW, ",", location, TEST_CHARACTERS.lastIndexOf(","), false, false),
						ASTFactory.createToken(IToken.CHAR, "'\\n'", location, TEST_CHARACTERS.indexOf("'\\n"), true, false),
						ASTFactory.createToken(IToken.RAW, ";", location, TEST_CHARACTERS.lastIndexOf(","), false, false),
						ASTFactory.createToken(IToken.CHAR, "'\\u00A9'", location, TEST_CHARACTERS.indexOf("'\\u"), true, false),
						ASTFactory.createToken(IToken.EOL, "\n", location, TEST_CHARACTERS.length(), false, false),
				}));
		
		IASTTranslationUnit tu = parse(TEST_CHARACTERS);
		testParsedTu(refTu, tu);
	}

	private static final String TEST_ESCAPED_STRINGS = "\"my \\\"quoted\\\" string.\"\n";

	public void testEscapedStrings() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		refTu.getNodes().add(ASTFactory.createPreprocessorTokenStreamStatement(
				new IToken[] {
						ASTFactory.createToken(IToken.STRING, 
								"\"my \\\"quoted\\\" string.\"",
								location, 0, false, true),
						ASTFactory.createToken(IToken.EOL, "\n", location, TEST_ESCAPED_STRINGS.length(), false, false),
				}));
		
		IASTTranslationUnit tu = parse(TEST_ESCAPED_STRINGS);
		testParsedTu(refTu, tu);
	}
}
