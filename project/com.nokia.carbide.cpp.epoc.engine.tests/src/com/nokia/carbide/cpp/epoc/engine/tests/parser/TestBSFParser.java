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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.bsf.ASTBSFFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;

/**
 * Test the BSF parser
 *
 */
public class TestBSFParser extends BaseTest {
	static final String TEST_EMPTY = "";
	static final String TEST_ONE_LINE = "\n";
	static final String TEST_COMMENT = "# hi mom\n";
	static final String TEST_HEADER = "#<bsf>#\n";
	static final String TEST_KEYWORD_1 = 
		"VARIANT\n";
	static final String TEST_KEYWORD_2 =
		"\n" + "\n" + "\tVARIANT\t\n" + "VIRTUALVARIANT\n";
	static final String TEST_ARGUMENTS_1 = 
		"CUSTOMIZES ARMV5\n";
	static final String TEST_ARGUMENTS_2 =  
		"  303foo 8* \"3\" 11\n" + "  argle_bargle raztenberger \n"+
		"\n"+
		"  new_options\t\t\t-opt all -I\"c:\\a b c\" -w all";
	
	protected IASTTranslationUnit parse(String text, boolean expectErrors) {
		IDocumentParser parser = ParserFactory.createBSFParser();
		IASTTranslationUnit bsfTu = parser.parse(this.modelPath, DocumentFactory.createDocument(text));
		if (expectErrors) {
			assertTrue(parser.hadErrors());
		}
		else {
			assertNoProblems(bsfTu);
			assertFalse(parser.hadErrors());
		}
		return bsfTu;
	}
	
	protected IASTTranslationUnit parse(String text) {
		return parse(text, false);
	}

	public void testEmpty() {
		IASTTranslationUnit refTu = ASTBSFFactory.createBSFTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_EMPTY);
		testParsedTu(refTu, tu);
	}
	
	public void testOneLine() {
		// whitespace, so removed
		IASTTranslationUnit refTu = ASTBSFFactory.createBSFTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_ONE_LINE);
		testParsedTu(refTu, tu);
	}
	
	public void testComment() {
		IASTTranslationUnit refTu = ASTBSFFactory.createBSFTranslationUnit();
		refTu.getNodes().add(ASTBSFFactory.createBSFCommentStatement("# hi mom"));
		IASTTranslationUnit tu = parse(TEST_COMMENT);
		testParsedTu(refTu, tu);
	}
	
	public void testHeader() {
		IASTTranslationUnit refTu = ASTBSFFactory.createBSFTranslationUnit();
		refTu.getNodes().add(ASTBSFFactory.createBSFCommentStatement("#<bsf>#"));
		IASTTranslationUnit tu = parse(TEST_HEADER);
		testParsedTu(refTu, tu);
	}
	
	public void testFlagStatements1() {
		IASTTranslationUnit refTu = ASTBSFFactory.createBSFTranslationUnit();
		refTu.getNodes().add(ASTBSFFactory.createBSFFlagStatement("VARIANT"));
		IASTTranslationUnit tu = parse(TEST_KEYWORD_1);
		testParsedTu(refTu, tu);
	}
	public void testFlagStatements2() {
		IASTTranslationUnit refTu = ASTBSFFactory.createBSFTranslationUnit();
		refTu.getNodes().add(ASTBSFFactory.createBSFFlagStatement("VARIANT"));
		refTu.getNodes().add(ASTBSFFactory.createBSFFlagStatement("VIRTUALVARIANT"));
		IASTTranslationUnit tu = parse(TEST_KEYWORD_2);
		testParsedTu(refTu, tu);
	}
	public void testArgumentStatements1() {
		IASTTranslationUnit refTu = ASTBSFFactory.createBSFTranslationUnit();
		refTu.getNodes().add(ASTBSFFactory.createBSFArgumentStatement("CUSTOMIZES", "ARMV5"));
		IASTTranslationUnit tu = parse(TEST_ARGUMENTS_1);
		testParsedTu(refTu, tu);
	}
	public void testArgumentStatements2() {
		//"  303foo 8* \"3\" 11\n" + "  argle_bargle raztenbeger \n"
		//		"  new_options\t\t\t-opt all -I\"c:\\a b c\" -w all";

		IASTTranslationUnit refTu = ASTBSFFactory.createBSFTranslationUnit();
		refTu.getNodes().add(ASTBSFFactory.createBSFArgumentStatement("303FOO", "8* \"3\" 11"));
		refTu.getNodes().add(ASTBSFFactory.createBSFArgumentStatement("ARGLE_BARGLE", "raztenberger"));
		refTu.getNodes().add(ASTBSFFactory.createBSFArgumentStatement("NEW_OPTIONS", "-opt all -I\"c:\\a b c\" -w all"));
		IASTTranslationUnit tu = parse(TEST_ARGUMENTS_2);
		testParsedTu(refTu, tu);
	}

}
