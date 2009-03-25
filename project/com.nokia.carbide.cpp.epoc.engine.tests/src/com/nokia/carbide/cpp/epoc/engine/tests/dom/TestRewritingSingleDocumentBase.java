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

package com.nokia.carbide.cpp.epoc.engine.tests.dom;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorElifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIfStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIfdefStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStreamStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;

public abstract class TestRewritingSingleDocumentBase extends BaseTest {

	abstract protected void run_(IASTTranslationUnit tu, String refText);
	
	/**
	 * Run the rewriting test on the given tu, expecting a single document
	 * with the given text on output.  We run the test twice, once on
	 * the original tu and once on a copy, ensuring the results are the
	 * same, as a double-check of the copying of the dirty state.
	 * @param tu
	 * @param refText
	 */
	protected void run(IASTTranslationUnit tu, String refText) {
		IDocumentSourceRegion mainRegion = (IDocumentSourceRegion) tu.getSourceRegion();
		IASTTranslationUnit tuCopy = (IASTTranslationUnit) tu.copy();
		IDocument docCopy = DocumentFactory.createDocument(mainRegion.getDocument().get());
		run_(tu, refText);
		mainRegion.getDocument().set(docCopy.get());
		run_(tuCopy, refText);
	}
	
	public void testNoChanges() {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#ifdef FOO // extra stuff\n");
		assertNotNull(tu);
		 run(tu, "#ifdef FOO // extra stuff\n");
	
	}
	
	public void testReplaceText() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#ifdef FOO // extra stuff\n");
		assertNotNull(tu);
		IASTPreprocessorIfdefStatement stmt = (IASTPreprocessorIfdefStatement) tu.getNodes().get(0);
		stmt.getMacroName().setValue("BAR");
		
		run(tu, "#ifdef BAR // extra stuff\n");
	}
	
	public void testReplaceText2() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), 
				"#if /* the expr */ X == 4\nFoo Stuff\n#else\n#endif\n");
		assertNotNull(tu);
		IASTPreprocessorIfStatement stmt = (IASTPreprocessorIfStatement) tu.getNodes().get(0);
		ITokenLocation location = stmt.getTokenStream().getTokens().get(0).getLocation();
		stmt.getTokenStream().setTokens(ASTFactory.createTokenList(location, "Y"));
		
		IASTPreprocessorTokenStreamStatement tokenstmt = (IASTPreprocessorTokenStreamStatement) tu.getNodes().get(1);
		tokenstmt.getTokenStream().setTokens(ASTFactory.createTokenList(location, "Never read that again\n"));
		
		run(tu, "#if /* the expr */ Y\nNever read that again\n#else\n#endif\n");
}

	public void testInsertText() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#if X == 4\n#endif\n");
		assertNotNull(tu);

		IASTPreprocessorTokenStreamStatement tokenstmt = ASTFactory.createPreprocessorTokenStreamStatement("New text\n"); 
		tu.getNodes().add(1, tokenstmt);
		
		run(tu, "#if X == 4\nNew text\n#endif\n");
	}

	public void testInsertText2() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#if X == 4\n#endif\n");
		assertNotNull(tu);

		IASTPreprocessorTokenStreamStatement tokenstmt = ASTFactory.createPreprocessorTokenStreamStatement("New text\n"); 
		tu.getNodes().add(0, tokenstmt);
		
		run(tu, "New text\n#if X == 4\n#endif\n");
	}
	public void testInsertText3() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#if X == 4\n#endif\n");
		assertNotNull(tu);

		IASTPreprocessorTokenStreamStatement tokenstmt = ASTFactory.createPreprocessorTokenStreamStatement("New text\n"); 
		tu.getNodes().add(2, tokenstmt);
		
		run(tu, "#if X == 4\n#endif\nNew text\n");
	}
	
	public void testDeleteText() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#if X == 4\nFoo Stuff\n#else\n#endif\n");
		assertNotNull(tu);
		tu.getNodes().remove(1);
		
		run(tu, "#if X == 4\n#else\n#endif\n");
	}

	public void testDeleteText2() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#if X == 4\nFoo Stuff\n#else\n#endif\n");
		assertNotNull(tu);
		tu.getNodes().remove(3);
		tu.getNodes().remove(2);
		tu.getNodes().remove(0);
		
		run(tu, "Foo Stuff\n");
	}

	public void testDeleteInsertText() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#if X == 4\nFoo Stuff\n#else\n#endif\n");
		assertNotNull(tu);
		tu.getNodes().remove(1);
		tu.getNodes().remove(1);
		IASTPreprocessorElifStatement elif = ASTFactory.createPreprocessorElifStatement(
				ASTFactory.createPreprocessorTokenStream("X == 1"));
		tu.getNodes().add(1, elif);
		
		run(tu, "#if X == 4\n#elif X == 1\n#endif\n");
	}

	public void testReorderText1() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#if X == 4\nFoo Stuff\n#else\n#endif\n");
		assertNotNull(tu);
		IASTTopLevelNode node = tu.getNodes().remove(1);
		tu.getNodes().add(2, node);
		
		run(tu, "#if X == 4\n#else\nFoo Stuff\n#endif\n");
	}
	public void testReorderText2() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "Third //comment\nFourth //c#2\nFirst //foist\nSecond //2nd\n");
		assertNotNull(tu);
		IASTTopLevelNode node = tu.getNodes().remove(3);
		tu.getNodes().add(0, node);
		node = tu.getNodes().remove(3);
		tu.getNodes().add(0, node);
		
		run(tu, "First //foist\nSecond //2nd\nThird //comment\nFourth //c#2\n");
	}
	
	public void testNestedChanges1() throws MalformedTreeException, BadLocationException {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "#if X == 4 //a\nFoo /*B*/ Stuff\n#else\n#endif //#if\n");
		assertNotNull(tu);
		
		// (1) reorder
		IASTPreprocessorIfStatement stmt = (IASTPreprocessorIfStatement) tu.getNodes().remove(0);
		tu.getNodes().add(1, stmt);
		
		// (2) change
		ITokenLocation location = stmt.getTokenStream().getTokens().get(0).getLocation();
		stmt.getTokenStream().setTokens(ASTFactory.createTokenList(location, "Y"));

		run(tu, "Foo /*B*/ Stuff\n#if Y //a\n#else\n#endif //#if\n");
	}
	
	public void testChangeAndDelete() {
		IASTTranslationUnit tu = parse(new Path("test.txt"), "line one\nline two\n");
		assertNotNull(tu);
		
		// (1) change
		tu.getNodes().remove(1);
		tu.getNodes().add(0, ASTFactory.createPreprocessorTokenStreamStatement("new line one\n"));
						
		// (2) remove
		tu.getNodes().remove(1);

		run(tu, "new line one\n");
		
	}
	
}
