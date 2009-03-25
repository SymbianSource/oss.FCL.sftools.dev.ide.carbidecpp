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
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ITranslationUnitParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp.IMMPParserConfiguration;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.core.runtime.Path;

import java.util.ArrayList;

/**
 * Test problem handlding in the MMP parser.
 *
 */
public class TestMMPParserProblems extends BaseTest {
	// NOT a problem
	private String TEST_NO_PROBLEM_JUST_UNKNOWN =
		"STRANGE KEYWORD\n";

	private String TEST_PROBLEM_1_p =
		"BASEADDRESS\n";
	private String TEST_PROBLEM_1 = TEST_PROBLEM_1_p;
	
	private String TEST_PROBLEM_2_p =
		"BASEADDRESS\n";
	private String TEST_PROBLEM_2 = 
		"SOURCEPATH ..\\src\n"+
		TEST_PROBLEM_2_p +
		"SOURCE foo.cpp\n";

	private String TEST_PROBLEM_3_p =
		"*** bogus comment\n";
	private String TEST_PROBLEM_3 = 
		"SOURCEPATH ..\\src\n"+
		TEST_PROBLEM_3_p +
		"SOURCE foo.cpp\n";

	private String TEST_PROBLEM_4_p =
		"START\n";
	private String TEST_PROBLEM_4 = 
		TEST_PROBLEM_4_p +
		"BASEADDRESS 0x600\n";

	private IMMPParserConfiguration mmpConfig;
	private IViewFilter viewFilter;
	private ArrayList<IDefine> macros;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		mmpConfig = new IMMPParserConfiguration() {

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
		viewFilter = new AcceptedNodesViewFilter();
		macros = new ArrayList<IDefine>();
	}
	
	protected IASTTranslationUnit parse(String text, boolean expectErrors) {
		IDocumentParser preParser = ParserFactory.createPreParser();
		IASTTranslationUnit ppTu = preParser.parse(new Path("test.mmp"), DocumentFactory.createDocument(text));
		
		// preprocess
		ASTPreprocessor preprocessor = createPreprocessor(parserConfig);
		IPreprocessorResults results = preprocessor.preprocess(ppTu, viewFilter, macros);

		ITranslationUnitParser parser = ParserFactory.createMMPParser(mmpConfig);
		IASTTranslationUnit mmpTu = parser.parse(results);
		if (expectErrors)
			assertTrue(parser.hadErrors());
		else
			assertFalse(parser.hadErrors());
		return mmpTu;
	}

	protected IASTTranslationUnit parse(String text) {
		return parse(text, true);
	}

	public void testNoProblem() {
		IASTTranslationUnit tu = parse(TEST_NO_PROBLEM_JUST_UNKNOWN, false);
		IASTProblemNode[] ps = getProblems(tu);
		assertEquals(0, ps.length);
		
	}
	public void testProblem1() {
		IASTTranslationUnit tu = parse(TEST_PROBLEM_1);
		IASTProblemNode[] ps = getProblems(tu);
		assertEquals(1, ps.length);
		assertEquals(TEST_PROBLEM_1_p, ps[0].getTokenStream().getNewText());
		assertNotNull(ps[0].getMessage());
		assertEquals(IMessage.ERROR, ps[0].getMessage().getSeverity());
		System.out.println(ps[0].getMessage());
		
	}
	
	public void testProblem2() {
		IASTTranslationUnit tu = parse(TEST_PROBLEM_2);
		IASTProblemNode[] ps = getProblems(tu);
		assertEquals(1, ps.length);
		assertEquals(TEST_PROBLEM_2_p, ps[0].getTokenStream().getNewText());
		assertNotNull(ps[0].getMessage());
		System.out.println(ps[0].getMessage());
		
	}
	public void testProblem3() {
		IASTTranslationUnit tu = parse(TEST_PROBLEM_3);
		IASTProblemNode[] ps = getProblems(tu);
		assertEquals(1, ps.length);
		assertEquals(TEST_PROBLEM_3_p, ps[0].getTokenStream().getNewText());
		assertNotNull(ps[0].getMessage());
		System.out.println(ps[0].getMessage());
		
	}

	public void testProblem4() {
		IASTTranslationUnit tu = parse(TEST_PROBLEM_4);
		IASTProblemNode[] ps = getProblems(tu);
		assertEquals(1, ps.length);
		assertEquals(TEST_PROBLEM_4_p, ps[0].getTokenStream().getNewText());
		assertNotNull(ps[0].getMessage());
		System.out.println(ps[0].getMessage());
		
	}
	
	final String BAD_JOHNNY_MMP = 
		"SOURCEPATH ..\\aif\r\n" + 
		"SOURCE c12,1 list_icon.bmp list_icon_mask.bmp\r\n" + 
		"END\r\n" + 
		"";
	public void testBadJohnnyMMP() {
		IASTTranslationUnit tu = parse(BAD_JOHNNY_MMP);
		IASTProblemNode[] ps = getProblems(tu);
		assertEquals(1, ps.length);
		assertNotNull(ps[0].getMessage());
		System.out.println(ps[0].getMessage());

	}

	final String JAVA_SOURCE_TEST =
		"/**\r\n" + 
		" * (c) 2009 Test\r\n" + 
		" */\r\n" + 
		"package com.nokia.carbide.cpp.epoc.engine.preprocessor;\r\n" + 
		"\r\n" + 
		"import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;\r\n" + 
		"\r\n" + 
		"import org.eclipse.jface.text.*;\r\n" + 
		"\r\n" + 
		"import java.util.*;\r\n" + 
		"\r\n" + 
		"/**\r\n" + 
		" * A span of text, possibly covering multiple files.\r\n" + 
		" * @author Quintessence\r\n" + 
		" *\r\n" + 
		" */\r\n" + 
		"public class TextSpan {\r\n" + 
		"	List<TextSpanNode> nodes;\r\n" + 
		"	public TextSpan() {\r\n" + 
		"		this.nodes = new ArrayList<TextSpanNode>(1);\r\n" + 
		"	}\r\n" + 
		"	public TextSpan(TextSpanNode node) {\r\n" + 
		"		this();\r\n" + 
		"		this.nodes.add(node);\r\n" + 
		"		simplify();\r\n" + 
		"	}\r\n" + 
		"	public TextSpan(TextSpanNode[] nodes) {\r\n" + 
		"		this();\r\n" + 
		"		for (TextSpanNode node : nodes)\r\n" + 
		"			this.nodes.add(node); \r\n" + 
		"		simplify();\r\n" + 
		"	}\r\n" + 
		"	public TextSpan(IASTNode node) {\r\n" + 
		"		this();\r\n" + 
		"		this.nodes.add(new TextSpanNode(node)); \r\n" + 
		"		simplify();\r\n" + 
		"	}\r\n" + 
		"	public TextSpan(IDocument document, IRegion region) {\r\n" + 
		"		this();\r\n" + 
		"		this.nodes.add(new TextSpanNode(document, region));\r\n" + 
		"		simplify();\r\n" + 
		"	}\r\n" + 
		"	/**\r\n" + 
		"	 * Create a span that lies between two nodes, which themselves\r\n" + 
		"	 * encompass a list of nodes.  If start and end are not null,\r\n" + 
		"	 * then includeBookends says whether the spans for start and\r\n" + 
		"	 * end are themselves included, or whether their end and start\r\n" + 
		"	 * (respectively) are used.\r\n" + 
		"	 * @param start \r\n" + 
		"	 * @param kids\r\n" + 
		"	 * @param end\r\n" + 
		"	 * @param includeBookends\r\n" + 
		"	 */\r\n" + 
		"	public TextSpan(IASTNode start, IASTNode[] kids, IASTNode end, boolean includeBookends) {\r\n" + 
		"		this();\r\n" + 
		"		if (start != null && start.getRegion() != null) {\r\n" + 
		"			if (includeBookends) {\r\n" + 
		"				this.nodes.add(new TextSpanNode(start));\r\n" + 
		"			} else {\r\n" + 
		"				IRegion startRegion = start.getRegion();\r\n" + 
		"				IASTNode ref = null;\r\n" + 
		"				if (kids.length > 0)\r\n" + 
		"					ref = kids[0];\r\n" + 
		"				else\r\n" + 
		"					ref = end;\r\n" + 
		"				if (ref != null && ref.getRegion() != null\r\n" + 
		"						&& ref.getDocument() == start.getDocument()) {\r\n" + 
		"					IRegion refRegion = ref.getRegion();\r\n" + 
		"					int beginp = startRegion.getOffset() + startRegion.getLength();\r\n" + 
		"					int endp = refRegion.getOffset();\r\n" + 
		"					this.nodes.add(new TextSpanNode(start.getDocument(),\r\n" + 
		"							new Region(beginp, endp - beginp)));\r\n" + 
		"				}\r\n" + 
		"			}\r\n" + 
		"		}\r\n" + 
		"		for (IASTNode kid : kids) {\r\n" + 
		"			this.nodes.add(new TextSpanNode(kid));\r\n" + 
		"		}\r\n" + 
		"		if (end != null && end.getRegion() != null) {\r\n" + 
		"			if (includeBookends) {\r\n" + 
		"				this.nodes.add(new TextSpanNode(end));\r\n" + 
		"			} else {\r\n" + 
		"				IRegion endRegion = end.getRegion();\r\n" + 
		"				IASTNode ref = null;\r\n" + 
		"				if (kids.length > 0)\r\n" + 
		"					ref = kids[kids.length - 1];\r\n" + 
		"				else\r\n" + 
		"					ref = start;\r\n" + 
		"				if (ref != null && ref.getRegion() != null\r\n" + 
		"						&& ref.getDocument() == end.getDocument()) {\r\n" + 
		"					IRegion refRegion = ref.getRegion();\r\n" + 
		"					int beginp = refRegion.getOffset() + refRegion.getLength();\r\n" + 
		"					int endp = endRegion.getOffset();\r\n" + 
		"					this.nodes.add(new TextSpanNode(end.getDocument(),\r\n" + 
		"							new Region(beginp, endp - beginp)));\r\n" + 
		"				}\r\n" + 
		"			}\r\n" + 
		"		}\r\n" + 
		"		simplify();\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	/* (non-Javadoc)\r\n" + 
		"	 * @see java.lang.Object#toString()\r\n" + 
		"	 */\r\n" + 
		"	@Override\r\n" + 
		"	public String toString() {\r\n" + 
		"		StringBuilder builder = new StringBuilder();\r\n" + 
		"		for (TextSpanNode node : nodes) {\r\n" + 
		"			builder.append(node);\r\n" + 
		"			builder.append(\',\');\r\n" + 
		"		}\r\n" + 
		"		return builder.toString();\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	/**\r\n" + 
		"	 *	Simplify the span by removing null span nodes, span nodes without\r\n" + 
		"	 *	documents or regions, and combining span nodes from the same document\r\n" + 
		"	 *  (we assume that any empty space between them is part of the span).  \r\n" + 
		"	 */\r\n" + 
		"	private void simplify() {\r\n" + 
		"		TextSpanNode prevNode = null;\r\n" + 
		"		for (ListIterator<TextSpanNode> iter = nodes.listIterator(); iter.hasNext();) {\r\n" + 
		"			TextSpanNode node = iter.next();\r\n" + 
		"			if (node == null) {\r\n" + 
		"				iter.remove();\r\n" + 
		"				continue;\r\n" + 
		"			}\r\n" + 
		"			if (node.getDocument() == null || node.getRegion() == null) {\r\n" + 
		"				iter.remove();\r\n" + 
		"				continue;\r\n" + 
		"			}\r\n" + 
		"			if (prevNode != null) {\r\n" + 
		"				if (prevNode.getDocument() == node.getDocument()) {\r\n" + 
		"					iter.remove();\r\n" + 
		"					iter.previous();\r\n" + 
		"					iter.remove();\r\n" + 
		"					int beginp = prevNode.getRegion().getOffset();\r\n" + 
		"					int endp = node.getRegion().getOffset() + node.getRegion().getLength(); \r\n" + 
		"					iter.add(new TextSpanNode(prevNode.getDocument(), \r\n" + 
		"							new Region(beginp, endp - beginp)));\r\n" + 
		"					continue;\r\n" + 
		"				}\r\n" + 
		"			}\r\n" + 
		"			prevNode = node;\r\n" + 
		"		}\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	/**\r\n" + 
		"	 * Test whether this span contains a span node.\r\n" + 
		"	 * @param node\r\n" + 
		"	 * @return\r\n" + 
		"	 */\r\n" + 
		"	public boolean containsSpan(TextSpanNode node) {\r\n" + 
		"		// look for one node containing the other\r\n" + 
		"		for (TextSpanNode n : nodes) {\r\n" + 
		"			if (n.contains(node))\r\n" + 
		"				return true;\r\n" + 
		"		}\r\n" + 
		"		return false;\r\n" + 
		"	}\r\n" + 
		"	\r\n" + 
		"	/**\r\n" + 
		"	 * Test whether this span contains a span.\r\n" + 
		"	 * @param span\r\n" + 
		"	 * @return\r\n" + 
		"	 */\r\n" + 
		"	public boolean containsSpan(TextSpan span) {\r\n" + 
		"		// see if all the nodes in the other are contained\r\n" + 
		"		for (TextSpanNode n : span.nodes) {\r\n" + 
		"			if (!containsSpan(n))\r\n" + 
		"				return false;\r\n" + 
		"		}\r\n" + 
		"		return true;\r\n" + 
		"	}\r\n" + 
		"}\r\n" + 
		"";
		
	/** Acid test to ensure no crashes */
	public void testNotMmpButJava() {
		IASTTranslationUnit tu = parse(JAVA_SOURCE_TEST);
		// if we get this far, we at least didn't crash...
		IASTProblemNode[] ps = getProblems(tu);
		assertTrue(ps.length > 0);

	}
}
