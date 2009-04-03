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
import com.nokia.carbide.cpp.epoc.engine.tests.ParserConfigurationBase;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.pkg.*;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.*;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.pkg.IPKGParserConfiguration;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import java.io.File;

/**
 * Test the PKG parser
 *
 */
public class TestPKGParser extends BaseTest {
	static final String TEST_DIR = "data/pkg/";
	static final String TEST_EMPTY_TU = "";
	static final String TEST_ONE_LINE = "\n";
	static final String TEST_EMPTY_LINE1 = " \n";
	static final String TEST_EMPTY_LINE2 = "\t\n";
	static final String TEST_EMPTY_LINE3 = "\t \t \t \n";
	static final String TEST_COMMENT = "; hi mom\n";
	static final String TEST_LANGUAGE1 = "&EN,FR";
	static final String TEST_MULTILANGUAL_VENDOR = "%{\"Vendor-EN\",\"Vendor-FR\"}";
	static final String TEST_UNIQUE_VENDOR = ":\"Vendor\"";
	static final String TEST_PACKAGE_HEADER1 = "#{\"MyApp-EN\",\"MyApp-FR\"},(0x1000001F),1,2,3,TYPE=SA";
	static final String TEST_PACKAGE_HEADER2 = "#{\"ShowAll\"<169>\"-EN\",\"ShowAll-FR\"<0xA9>},(0x10000004),1,2,3";
	static final String TEST_LOGO1 = "=\"logo.jpg\",\"image/jpeg\",\"target.jpg\"";
	static final String TEST_LOGO2 = "=\"logo.jpg\",\"image/jpeg\"";
	static final String TEST_PACKAGE_SIGNATURE = "*\"files\\private.key\",\"files\\cert.cer\"";
	static final String TEST_HARDWARE_DEPENDENCY = "[0x1000001F],1,2,3,{\"UIQ20ProductID\"}";
	static final String TEST_HARDWARE_DEPENDENCY2 = "[0x1000001F],1,2,3,{\"UIQ20ProductID-EN\",\"UIQ20ProductID-FR\"}";
	static final String TEST_VERSION_COMPATIBILITY = "(0x1000001F),2,3,4,{\"UIQ20ProductID\"}";
	static final String TEST_SOFTWARE_DEPENDENCY1 = "(0x1000001F),2,3,4,{\"Depend-EN\",\"Depend-FR\"}";
	static final String TEST_SOFTWARE_DEPENDENCY2 = "(0x1000001F),2,3,4~5,6,7,{\"Depend-EN\",\"Depend-FR\"}";
	static final String TEST_SOFTWARE_DEPENDENCY3 = "(0x1000001F),*,3,4~5,6,7,{\"Depend-EN\",\"Depend-FR\"}";
	static final String TEST_PROPERTIES = "+(0=1,1=2,3=-1)";
	static final String TEST_PROBLEM1 = "&EN,FR+";
	static final String TEST_PROBLEM1_P = "+";
	
	private IPKGParserConfiguration pkgConfig;
	private IPKGParserConfiguration happyConfig;
	
	protected ParserConfigurationBase parserConfig;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		happyConfig = new IPKGParserConfiguration() {			
		};
		
		pkgConfig = happyConfig;
		
	}

	protected IASTTranslationUnit parse(String text, boolean expectErrors) {
		IDocumentParser parser = ParserFactory.createPKGParser(pkgConfig);
		IASTTranslationUnit pkgTu = parser.parse(this.modelPath, DocumentFactory.createDocument(text));
		if (expectErrors) {
			assertTrue(parser.hadErrors());
		}
		else {
			assertNoProblems(pkgTu);
			if (parser.hadErrors())
				fail("Errors reported in parser but not recorded in TU -- fix this!");
		}
		return pkgTu;
	}
	
	protected IASTTranslationUnit parse(String text) {
		return parse(text, false);
	}

	// load testdir-relative file
	protected String load(String name) throws Exception {
		File file = projectRelativeFile(TEST_DIR + name);
		char[] text = FileUtils.readFileContents(file, null);
		
		return new String(text);
	}

	public void testEmptyTu() {
		IASTTranslationUnit refTu = ASTPKGFactory.createPKGTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_EMPTY_TU);
		testParsedTu(refTu, tu);
	}
	
	public void testOneLine() {
		// whitespace, so removed
		IASTTranslationUnit refTu = ASTPKGFactory.createPKGTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_ONE_LINE);
		testParsedTu(refTu, tu);
	}

	public void testEOLLine() {
		// whitespace, so removed
		IASTTranslationUnit refTu = ASTPKGFactory.createPKGTranslationUnit();
		IASTTranslationUnit tu = parse(TEST_EMPTY_LINE1);
		testParsedTu(refTu, tu);
		
		IASTTranslationUnit tu2 = parse(TEST_EMPTY_LINE2);
		testParsedTu(refTu, tu2);
		
		IASTTranslationUnit tu3 = parse(TEST_EMPTY_LINE3);
		testParsedTu(refTu, tu3);
	}

	public void testComment() {
		IASTTranslationUnit refTu = ASTPKGFactory.createPKGTranslationUnit();
		refTu.getNodes().add(ASTPKGFactory.createPKGCommentTest(" hi mom"));
		IASTTranslationUnit tu = parse(TEST_COMMENT);
		testParsedTu(refTu, tu);
	}

	public void testLanguage() {
		IASTTranslationUnit refTu1 = ASTPKGFactory.createPKGTranslationUnit();
		String[] languages1 = {"EN", "FR"};
		refTu1.getNodes().add(ASTPKGFactory.createPKGLanguageTest(languages1));
		IASTTranslationUnit tu1 = parse(TEST_LANGUAGE1);
		testParsedTu(refTu1, tu1);
	}

	public void testVendor() {
		IASTTranslationUnit refTu1 = ASTPKGFactory.createPKGTranslationUnit();
		IASTTranslationUnit tu1 = parse(TEST_MULTILANGUAL_VENDOR);
		String[] vendor1 = {"\"Vendor-EN\"", "\"Vendor-FR\""};
		refTu1.getNodes().add(ASTPKGFactory.createPKGVendorTest(vendor1, true));
		testParsedTu(refTu1, tu1);
		
		IASTTranslationUnit refTu2 = ASTPKGFactory.createPKGTranslationUnit();
		IASTTranslationUnit tu2 = parse(TEST_UNIQUE_VENDOR);
		String[] vendor2 = {"\"Vendor\""};
		refTu2.getNodes().add(ASTPKGFactory.createPKGVendorTest(vendor2, false));
		testParsedTu(refTu2, tu2);
	}
	
	public void testPackageHeader() {
		IASTTranslationUnit refTu1 = ASTPKGFactory.createPKGTranslationUnit();
		IASTTranslationUnit tu1 = parse(TEST_PACKAGE_HEADER1);
		String[] headersLocalized1 = {"\"MyApp-EN\"","\"MyApp-FR\""};
		String uid1 = "0x1000001F";
		String[] version1 = {"1","2","3"};
		String[] options1 = {"TYPE=SA"};
		refTu1.getNodes().add(ASTPKGFactory.createPKGPackageHeaderTest(headersLocalized1, uid1, version1, options1));
		testParsedTu(refTu1, tu1);
		
		IASTTranslationUnit refTu2 = ASTPKGFactory.createPKGTranslationUnit();
		IASTTranslationUnit tu2 = parse(TEST_PACKAGE_HEADER2);
		String[] headersLocalized2 = {"\"ShowAll\"<169>\"-EN\"" , "\"ShowAll-FR\"<0xA9>"};
		String uid2 = "0x10000004";
		String[] version2 = {"1","2","3"};
		String[] options2 = {};
		refTu2.getNodes().add(ASTPKGFactory.createPKGPackageHeaderTest(headersLocalized2, uid2, version2, options2));
		testParsedTu(refTu2, tu2);
	}
	
	public void testLogo() {
		IASTTranslationUnit refTu1 = ASTPKGFactory.createPKGTranslationUnit();
		refTu1.getNodes().add(ASTPKGFactory.createPKGLogoTest("\"logo.jpg\"", "\"image/jpeg\"", "\"target.jpg\""));
		IASTTranslationUnit tu1 = parse(TEST_LOGO1);
		testParsedTu(refTu1, tu1);

		IASTTranslationUnit refTu2 = ASTPKGFactory.createPKGTranslationUnit();
		refTu2.getNodes().add(ASTPKGFactory.createPKGLogoTest("\"logo.jpg\"", "\"image/jpeg\"", null));
		IASTTranslationUnit tu2 = parse(TEST_LOGO2);
		testParsedTu(refTu2, tu2);
	}
	
	public void testPackageSignature() {
		IASTTranslationUnit refTu = ASTPKGFactory.createPKGTranslationUnit();
		refTu.getNodes().add(ASTPKGFactory.createPKGPackageSignatureTest("\"files\\private.key\"", "\"files\\cert.cer\""));
		IASTTranslationUnit tu = parse(TEST_PACKAGE_SIGNATURE);
		testParsedTu(refTu, tu);
	}
	
	public void testHardwareDependency() {
		IASTTranslationUnit refTu = ASTPKGFactory.createPKGTranslationUnit();
		String[] version = {"1","2","3"};
		refTu.getNodes().add(ASTPKGFactory.createPKGHardwareDependencyTest("0x1000001F", version, "\"UIQ20ProductID\""));
		IASTTranslationUnit tu = parse(TEST_HARDWARE_DEPENDENCY);
		testParsedTu(refTu, tu);

		IASTTranslationUnit refTu2 = ASTPKGFactory.createPKGTranslationUnit();
		String[] version2 = {"1","2","3"};
		String[] components2 = {"\"UIQ20ProductID-EN\"","\"UIQ20ProductID-FR\""};
		refTu2.getNodes().add(ASTPKGFactory.createPKGHardwareDependencyTest("0x1000001F", version2, components2));
		IASTTranslationUnit tu2 = parse(TEST_HARDWARE_DEPENDENCY2);
		testParsedTu(refTu2, tu2);
	}

	public void testVersionCompatibility() {
		IASTTranslationUnit refTu = ASTPKGFactory.createPKGTranslationUnit();
		String[] version = {"2","3","4"};
		refTu.getNodes().add(ASTPKGFactory.createPKGVersionCompatibilityTest("0x1000001F", version, "\"UIQ20ProductID\""));
		IASTTranslationUnit tu = parse(TEST_VERSION_COMPATIBILITY);
		testParsedTu(refTu, tu);
	}

	public void testSoftwareDependency() {
		IASTTranslationUnit refTu = ASTPKGFactory.createPKGTranslationUnit();
		String[] version = {"2","3","4"};
		String[] components = {"\"Depend-EN\"","\"Depend-FR\""};
		refTu.getNodes().add(ASTPKGFactory.createPKGSoftwareDependencyStatementTest("0x1000001F", version, components));
		IASTTranslationUnit tu = parse(TEST_SOFTWARE_DEPENDENCY1);
		testParsedTu(refTu, tu);

		IASTTranslationUnit refTu2 = ASTPKGFactory.createPKGTranslationUnit();
		String[] versionLowerBound2 = {"2","3","4"};
		String[] versionUpperBound2 = {"5","6","7"};
		String[] components2 = {"\"Depend-EN\"","\"Depend-FR\""};
		refTu2.getNodes().add(ASTPKGFactory.createPKGSoftwareDependencyStatementTest("0x1000001F", versionLowerBound2, versionUpperBound2, components2));
		IASTTranslationUnit tu2 = parse(TEST_SOFTWARE_DEPENDENCY2);
		testParsedTu(refTu2, tu2);

		IASTTranslationUnit refTu3 = ASTPKGFactory.createPKGTranslationUnit();
		String[] versionLowerBound3 = {"*","3","4"};
		String[] versionUpperBound3 = {"5","6","7"};
		String[] components3 = {"\"Depend-EN\"","\"Depend-FR\""};
		refTu3.getNodes().add(ASTPKGFactory.createPKGSoftwareDependencyStatementTest("0x1000001F", versionLowerBound3, versionUpperBound3, components3));
		IASTTranslationUnit tu3 = parse(TEST_SOFTWARE_DEPENDENCY3);
		testParsedTu(refTu3, tu3);
		
	}

	public void testPropertiesOrCapabilities() {
		IASTTranslationUnit refTu1 = ASTPKGFactory.createPKGTranslationUnit();
		String[] properties = {"0=1", "1=2", "3=-1"};
		refTu1.getNodes().add(ASTPKGFactory.createPKGPropertiesOrCapabilitiesTest(properties));
		IASTTranslationUnit tu1 = parse(TEST_PROPERTIES);
		testParsedTu(refTu1, tu1);
	}

	public void testConditional1() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		
		IASTListNode<IASTPKGStatement> innerIfList = ASTFactory.createListNode("\n");
		innerIfList.add(ASTPKGFactory.createPKGCommentTest("ifcomment3"));
		IASTPKGIfElseifContainer innerIfChoice = ASTPKGFactory.createPKGIfElseifContainer(ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond2")), innerIfList);
		
		IASTListNode<IASTPKGStatement> innerElseList = ASTFactory.createListNode("\n");
		innerElseList.add(ASTPKGFactory.createPKGCommentTest("elsecomment1"));
		IASTPKGElseContainer innerElseChoice = ASTPKGFactory.createPKGElseContainer(innerElseList);
		
		IASTListNode<IASTPKGConditionalContainer> innerBlockList = ASTFactory.createListNode("\n");
		innerBlockList.add(innerIfChoice);
		innerBlockList.add(innerElseChoice);
		IASTPKGConditionalBlock innerBlock = ASTPKGFactory.createPKGConditionalBlock(innerBlockList);
		
		IASTListNode<IASTPKGStatement> outerIfList = ASTFactory.createListNode("\n");
		outerIfList.add(ASTPKGFactory.createPKGCommentTest("ifcomment1"));
		outerIfList.add(ASTPKGFactory.createPKGCommentTest("ifcomment2"));
		outerIfList.add(innerBlock);
		
		IASTPKGIfElseifContainer outerIfChoice = ASTPKGFactory.createPKGIfElseifContainer(ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond1")), outerIfList);
		
		IASTListNode<IASTPKGStatement> outerElseifList = ASTFactory.createListNode("\n");
		outerElseifList.add(ASTPKGFactory.createPKGCommentTest("elseifcomment1"));
		
		IASTPKGIfElseifContainer outerElseifChoice = ASTPKGFactory.createPKGIfElseifContainer(ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond3")), outerElseifList);
		
		IASTListNode<IASTPKGStatement> outerElseList = ASTFactory.createListNode("\n");
		outerElseList.add(ASTPKGFactory.createPKGCommentTest("elsecomment3"));
		outerElseList.add(ASTPKGFactory.createPKGCommentTest("elsecomment4"));

		IASTPKGElseContainer outerElseChoice = ASTPKGFactory.createPKGElseContainer(outerElseList);

		IASTListNode<IASTPKGConditionalContainer> outerBlockList = ASTFactory.createListNode("\n");
		outerBlockList.add(outerIfChoice);
		outerBlockList.add(outerElseifChoice);
		outerBlockList.add(outerElseChoice);
		IASTPKGConditionalBlock outerBlock = ASTPKGFactory.createPKGConditionalBlock(outerBlockList);
		refTu.getNodes().add(outerBlock);

		IASTTranslationUnit tu = parse("IF cond1\n;ifcomment1\n;ifcomment2\nIF cond2\n;ifcomment3\nELSE\n;elsecomment1\nENDIF\nELSEIF cond3\n;elseifcomment1\nELSE\n;elsecomment3\n;elsecomment4\nENDIF\n");
		testParsedTu(refTu, tu);
	}
	
	public void testConditional2() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		
		IASTListNode<IASTPKGStatement> outerIfList = ASTFactory.createListNode("\n");
		outerIfList.add(ASTPKGFactory.createPKGCommentTest("ifcomment1"));
		
		IASTPKGIfElseifContainer outerIfChoice = ASTPKGFactory.createPKGIfElseifContainer(ASTPKGFactory.createPKGUnaryConditionExpression(true, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond1"))), outerIfList);

		IASTListNode<IASTPKGConditionalContainer> outerBlockList = ASTFactory.createListNode("\n");
		outerBlockList.add(outerIfChoice);
		IASTPKGConditionalBlock outerBlock = ASTPKGFactory.createPKGConditionalBlock(outerBlockList);
		refTu.getNodes().add(outerBlock);

		IASTTranslationUnit tu = parse("IF NOT(cond1)\n;ifcomment1\nENDIF\n");
		testParsedTu(refTu, tu);
	}
	
	public void testConditional3() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		
		IASTListNode<IASTPKGStatement> outerIfList = ASTFactory.createListNode("\n");
		outerIfList.add(ASTPKGFactory.createPKGCommentTest("ifcomment1"));
		
		IASTPKGIfElseifContainer outerIfChoice = ASTPKGFactory.createPKGIfElseifContainer(ASTPKGFactory.createPKGBinaryConditionExpression(true, ASTPKGFactory.createPKGUnaryConditionExpression(false, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond1"))), ASTPKGFactory.createPKGUnaryConditionExpression(false, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond2")))), outerIfList);

		IASTListNode<IASTPKGConditionalContainer> outerBlockList = ASTFactory.createListNode("\n");
		outerBlockList.add(outerIfChoice);
		IASTPKGConditionalBlock outerBlock = ASTPKGFactory.createPKGConditionalBlock(outerBlockList);
		refTu.getNodes().add(outerBlock);

		IASTTranslationUnit tu = parse("IF (cond1) AND (cond2)\n;ifcomment1\nENDIF\n");
		testParsedTu(refTu, tu);
	}

	public void testConditional4() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		
		IASTListNode<IASTPKGStatement> outerIfList = ASTFactory.createListNode("\n");
		outerIfList.add(ASTPKGFactory.createPKGCommentTest("ifcomment1"));
		
		IASTPKGIfElseifContainer outerIfChoice = ASTPKGFactory.createPKGIfElseifContainer(ASTPKGFactory.createPKGBinaryConditionExpression(false, ASTPKGFactory.createPKGUnaryConditionExpression(false, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond1"))), ASTPKGFactory.createPKGUnaryConditionExpression(false, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond2")))), outerIfList);

		IASTListNode<IASTPKGConditionalContainer> outerBlockList = ASTFactory.createListNode("\n");
		outerBlockList.add(outerIfChoice);
		IASTPKGConditionalBlock outerBlock = ASTPKGFactory.createPKGConditionalBlock(outerBlockList);
		refTu.getNodes().add(outerBlock);

		IASTTranslationUnit tu = parse("IF (cond1) OR (cond2)\n;ifcomment1\nENDIF\n");
		testParsedTu(refTu, tu);
	}

	public void testConditional5() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		
		IASTListNode<IASTPKGStatement> outerIfList = ASTFactory.createListNode("\n");
		outerIfList.add(ASTPKGFactory.createPKGCommentTest("ifcomment1"));
		
		IASTPKGIfElseifContainer outerIfChoice = ASTPKGFactory.createPKGIfElseifContainer(ASTPKGFactory.createPKGBinaryConditionExpression(false, ASTPKGFactory.createPKGUnaryConditionExpression(true, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond1"))), ASTPKGFactory.createPKGUnaryConditionExpression(false, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond2")))), outerIfList);

		IASTListNode<IASTPKGConditionalContainer> outerBlockList = ASTFactory.createListNode("\n");
		outerBlockList.add(outerIfChoice);
		IASTPKGConditionalBlock outerBlock = ASTPKGFactory.createPKGConditionalBlock(outerBlockList);
		refTu.getNodes().add(outerBlock);

		IASTTranslationUnit tu = parse("IF NOT (cond1) OR (cond2)\n;ifcomment1\nENDIF\n");
		testParsedTu(refTu, tu);
	}

	public void testConditional6() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		
		IASTListNode<IASTPKGStatement> outerIfList = ASTFactory.createListNode("\n");
		outerIfList.add(ASTPKGFactory.createPKGCommentTest("ifcomment1"));
		
		IASTPKGIfElseifContainer outerIfChoice = ASTPKGFactory.createPKGIfElseifContainer(ASTPKGFactory.createPKGBinaryConditionExpression(false, ASTPKGFactory.createPKGUnaryConditionExpression(false, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond1"))), ASTPKGFactory.createPKGUnaryConditionExpression(true, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond2")))), outerIfList);

		IASTListNode<IASTPKGConditionalContainer> outerBlockList = ASTFactory.createListNode("\n");
		outerBlockList.add(outerIfChoice);
		IASTPKGConditionalBlock outerBlock = ASTPKGFactory.createPKGConditionalBlock(outerBlockList);
		refTu.getNodes().add(outerBlock);

		IASTTranslationUnit tu = parse("IF (cond1) OR NOT (cond2)\n;ifcomment1\nENDIF\n");
		testParsedTu(refTu, tu);
	}

	public void testConditional7() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		
		IASTListNode<IASTPKGStatement> outerIfList = ASTFactory.createListNode("\n");
		outerIfList.add(ASTPKGFactory.createPKGCommentTest("ifcomment1"));
		
		IASTPKGIfElseifContainer outerIfChoice = ASTPKGFactory.createPKGIfElseifContainer(ASTPKGFactory.createPKGUnaryConditionExpression(true, ASTPKGFactory.createPKGBinaryConditionExpression(false, ASTPKGFactory.createPKGUnaryConditionExpression(false, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond1"))), ASTPKGFactory.createPKGUnaryConditionExpression(false, ASTPKGFactory.createPKGConditionPrimitiveExpression(ASTPKGFactory.createLiteralTextNode("cond2"))))), outerIfList);

		IASTListNode<IASTPKGConditionalContainer> outerBlockList = ASTFactory.createListNode("\n");
		outerBlockList.add(outerIfChoice);
		IASTPKGConditionalBlock outerBlock = ASTPKGFactory.createPKGConditionalBlock(outerBlockList);
		refTu.getNodes().add(outerBlock);

		IASTTranslationUnit tu = parse("IF NOT((cond1) OR (cond2))\n;ifcomment1\nENDIF\n");
		testParsedTu(refTu, tu);
	}

	public void testOptionsList() {
		IASTTranslationUnit refTu = ASTFactory.createTranslationUnit();
		IASTListNode<IASTLiteralTextNode> one = ASTFactory.createListNode(",");
		one.add(ASTFactory.createRawLiteralTextNode("\"en 1 (20KB)\""));
		one.add(ASTFactory.createRawLiteralTextNode("\"fr 1 (21KB)\""));
		IASTPKGOptionsListOption optionOne = ASTPKGFactory.createPKGOptionsListOption(one);
		IASTListNode<IASTLiteralTextNode> two = ASTFactory.createListNode(",");
		two.add(ASTFactory.createRawLiteralTextNode("\"en 2 (75KB)\""));
		two.add(ASTFactory.createRawLiteralTextNode("\"fr 2 (76KB)\""));
		IASTPKGOptionsListOption optionTwo = ASTPKGFactory.createPKGOptionsListOption(two);
		IASTListNode<IASTPKGOptionsListOption> option = ASTFactory.createListNode(",");
		option.add(optionOne);
		option.add(optionTwo);
		refTu.getNodes().add(ASTPKGFactory.createPKGOptionsListStatement(option));
		IASTTranslationUnit tu = parse("!({\"en 1 (20KB)\",\"fr 1 (21KB)\"}, {\"en 2 (75KB)\",\"fr 2 (76KB)\"})");
		testParsedTu(refTu, tu);
	}

	public void testInstallFile() {
		IASTTranslationUnit refTu1 = ASTFactory.createTranslationUnit();
		IASTListNode<IASTLiteralTextNode> src1 = ASTFactory.createListNode(",");
		src1.add(ASTFactory.createRawLiteralTextNode("\"files\\myFile.txt\""));
		IASTListNode<IASTLiteralTextNode> option1 = ASTFactory.createListNode(",");
		option1.add(ASTFactory.createRawLiteralTextNode("FF"));
		refTu1.getNodes().add(ASTPKGFactory.createPKGInstallFileStatement(src1,ASTFactory.createRawLiteralTextNode("\"!:\\Documents\\myFile.txt\""), option1));
		IASTTranslationUnit tu1 = parse("\"files\\myFile.txt\"-\"!:\\Documents\\myFile.txt\",FF");
		testParsedTu(refTu1, tu1);
		
		IASTTranslationUnit refTu2 = ASTFactory.createTranslationUnit();
		IASTListNode<IASTLiteralTextNode> src2 = ASTFactory.createListNode(",");
		src2.add(ASTFactory.createRawLiteralTextNode("\"files\\myFile-EN.txt\""));
		src2.add(ASTFactory.createRawLiteralTextNode("\"files\\myFile-FR.txt\""));
		IASTListNode<IASTLiteralTextNode> option2 = ASTFactory.createListNode(",");
		option2.add(ASTFactory.createRawLiteralTextNode("FF"));
		refTu2.getNodes().add(ASTPKGFactory.createPKGInstallFileStatement(src2,ASTFactory.createRawLiteralTextNode("\"!:\\Documents\\myFile.txt\""), option2));
		IASTTranslationUnit tu2 = parse("{\"files\\myFile-EN.txt\",\"files\\myFile-FR.txt\"}-\"!:\\Documents\\myFile.txt\",FF");
		testParsedTu(refTu2, tu2);

		IASTTranslationUnit refTu3 = ASTFactory.createTranslationUnit();
		IASTListNode<IASTLiteralTextNode> src3 = ASTFactory.createListNode(",");
		src3.add(ASTFactory.createRawLiteralTextNode("\"files\\myFile-EN.txt\""));
		src3.add(ASTFactory.createRawLiteralTextNode("\"files\\myFile-FR.txt\""));
		IASTListNode<IASTLiteralTextNode> dummy = ASTFactory.createListNode(",");
		refTu3.getNodes().add(ASTPKGFactory.createPKGInstallFileStatement(src3,ASTFactory.createRawLiteralTextNode("\"!:\\Documents\\myFile.txt\""), dummy));
		IASTTranslationUnit tu3 = parse("{\"files\\myFile-EN.txt\",\"files\\myFile-FR.txt\"}-\"!:\\Documents\\myFile.txt\"");
		testParsedTu(refTu3, tu3);
	}

	public void testEmbeddedSis() {
		IASTTranslationUnit refTu1 = ASTFactory.createTranslationUnit();
		IASTListNode<IASTLiteralTextNode> sis1 = ASTFactory.createListNode(",");
		IASTPKGEmbeddedSisEntry sisEntry = ASTPKGFactory.createEmbeddedSisEntry("@\"depend.sis\"");
		assertTrue(sisEntry.getEmbeddedSis().equals("\"depend.sis\""));
		sis1.add(sisEntry);
		refTu1.getNodes().add(ASTPKGFactory.createPKGEmbeddedSisStatement(sis1, ASTFactory.createRawLiteralTextNode("0x10000002")));
		IASTTranslationUnit tu1 = parse("@\"depend.sis\", (0x10000002)");
		testParsedTu(refTu1, tu1);
		
		IASTTranslationUnit refTu2 = ASTFactory.createTranslationUnit();
		IASTListNode<IASTLiteralTextNode> sis2 = ASTFactory.createListNode(",");
		sis2.add(ASTPKGFactory.createEmbeddedSisEntry("@\"depend-EN.sis\""));
		sis2.add(ASTPKGFactory.createEmbeddedSisEntry("@\"depend-FR.sis\""));
		IASTListNode<IASTLiteralTextNode> option2 = ASTFactory.createListNode(",");
		option2.add(ASTFactory.createRawLiteralTextNode("FF"));
		refTu2.getNodes().add(ASTPKGFactory.createPKGEmbeddedSisStatement(sis2,ASTFactory.createRawLiteralTextNode("0x10000003")));
		IASTTranslationUnit tu2 = parse("{@\"depend-EN.sis\",@\"depend-FR.sis\"},(0x10000003)");
		testParsedTu(refTu2, tu2);
	}

	public void testCarbidePKG() throws Exception {
		IASTTranslationUnit tu1 = parse(load("carbide_1_2_eka2.pkg"));

		IASTTranslationUnit tu2 = parse(load("example/all_items.pkg"));

		IASTTranslationUnit tu3 = parse(load("example/conditional.pkg"));

		IASTTranslationUnit tu4 = parse(load("example/dependency.pkg"));
		
		IASTTranslationUnit tu5 = parse(load("example/dialects.pkg"));
		
		IASTTranslationUnit tu6 = parse(load("example/embedded.pkg"));
		
		IASTTranslationUnit tu7 = parse(load("example/embedding.pkg"));
		
		IASTTranslationUnit tu8 = parse(load("example/ifelse.pkg"));
		
		IASTTranslationUnit tu9 = parse(load("example/mime.pkg"));
		
		IASTTranslationUnit tu10 = parse(load("example/multilangual.pkg"));
		
		IASTTranslationUnit tu11 = parse(load("example/optionlist.pkg"));
		
		IASTTranslationUnit tu12 = parse(load("example/partialupgrade.pkg"));
		
		IASTTranslationUnit tu13 = parse(load("example/patch.pkg"));
		
		IASTTranslationUnit tu14 = parse(load("example/platformdependency.pkg"));

	}
	
	public void testProblem1() {
		IASTTranslationUnit tu1 = parse(TEST_PROBLEM1, true);
		IASTProblemNode[] ps1 = getProblems(tu1);
		assertEquals(1, ps1.length);
		assertEquals(TEST_PROBLEM1_P, ps1[0].getTokenStream().getNewText());
		assertNotNull(ps1[0].getMessage());
		assertEquals(IMessage.ERROR, ps1[0].getMessage().getSeverity());
		System.out.println(ps1[0].getMessage());
	}
	
	public void testBugRegression() throws Exception {
		IASTTranslationUnit tu2659_1 = parse(load("bugs/2659_1.pkg"));
		IASTTranslationUnit tu5244_1 = parse(load("bugs/5244_1.pkg"));
	}
	
	/** Be sure we don't throw any exceptions parsing code which is not PKG syntax */
	public void testParsingGarbage() throws Exception {
		String text = "/*\r\n" + 
				"========================================================================\r\n" + 
				" Name        : UID28Document.cpp\r\n" + 
				" Author      : \r\n" + 
				" Copyright   : Your copyright notice\r\n" + 
				" Description : \r\n" + 
				"========================================================================\r\n" + 
				"*/\r\n" + 
				"// [[[ begin generated region: do not modify [Generated User Includes]\r\n" + 
				"#include \"UID28Document.h\"\r\n" + 
				"#include \"UID28AppUi.h\"\r\n" + 
				"// ]]] end generated region [Generated User Includes]\r\n" + 
				"\r\n" + 
				"/**\r\n" + 
				" * @brief Constructs the document class for the application.\r\n" + 
				" * @param anApplication the application instance\r\n" + 
				" */\r\n" + 
				"CUID28Document::CUID28Document( CEikApplication& anApplication )\r\n" + 
				"	: CAknDocument( anApplication )\r\n" + 
				"	{\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"/**\r\n" + 
				" * @brief Completes the second phase of Symbian object construction. \r\n" + 
				" * Put initialization code that could leave here.  \r\n" + 
				" */ \r\n" + 
				"void CUID28Document::ConstructL()\r\n" + 
				"	{\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"/**\r\n" + 
				" * Symbian OS two-phase constructor.\r\n" + 
				" *\r\n" + 
				" * Creates an instance of CUID28Document, constructs it, and\r\n" + 
				" * returns it.\r\n" + 
				" *\r\n" + 
				" * @param aApp the application instance\r\n" + 
				" * @return the new CUID28Document\r\n" + 
				" */\r\n" + 
				"CUID28Document* CUID28Document::NewL( CEikApplication& aApp )\r\n" + 
				"	{\r\n" + 
				"	CUID28Document* self = new ( ELeave ) CUID28Document( aApp );\r\n" + 
				"	CleanupStack::PushL( self );\r\n" + 
				"	self->ConstructL();\r\n" + 
				"	CleanupStack::Pop( self );\r\n" + 
				"	return self;\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"/**\r\n" + 
				" * @brief Creates the application UI object for this document.\r\n" + 
				" * @return the new instance\r\n" + 
				" */	\r\n" + 
				"CEikAppUi* CUID28Document::CreateAppUiL()\r\n" + 
				"	{\r\n" + 
				"	return new ( ELeave ) CUID28AppUi;\r\n" + 
				"	}\r\n" + 
				"				\r\n" + 
				"/*\r\n" + 
				" * Copyright (C) 2006 Nokia Corporation. All rights reserved.\r\n" + 
				" */\r\n" + 
				"\r\n" + 
				"#include \"VerticalLabel.h\"\r\n" + 
				"#include <eikenv.h>\r\n" + 
				"#include <barsread.h>\r\n" + 
				"\r\n" + 
				"CVerticalLabel::CVerticalLabel() \r\n" + 
				" : iText(NULL), iLineSize(1) \r\n" + 
				" {}\r\n" + 
				" \r\n" + 
				"CVerticalLabel::~CVerticalLabel()\r\n" + 
				"	{\r\n" + 
				" 	delete iText;\r\n" + 
				" 	}\r\n" + 
				" \r\n" + 
				"void CVerticalLabel::ConstructFromResourceL(TResourceReader& aReader)\r\n" + 
				"	{\r\n" + 
				"		iLineSize = aReader.ReadInt16();\r\n" + 
				"		iText = aReader.ReadHBufCL();	\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"void CVerticalLabel::Draw(const TRect& aRect) const\r\n" + 
				"	{\r\n" + 
				"		if (iText != NULL) \r\n" + 
				"			{\r\n" + 
				"			CWindowGc &gc = SystemGc();\r\n" + 
				"			const CFont *normalFont = iEikonEnv->NormalFont();\r\n" + 
				"			gc.UseFont(normalFont);\r\n" + 
				"							\r\n" + 
				"			TRect r = Rect();\r\n" + 
				"			r.Shrink(iLineSize+1, iLineSize+1);\r\n" + 
				"			int baselineOffset = normalFont->AscentInPixels();\r\n" + 
				"			gc.DrawTextVertical(*iText, r, baselineOffset, ETrue);\r\n" + 
				"			\r\n" + 
				"			if (iLineSize > 0)\r\n" + 
				"				{\r\n" + 
				"				TSize penSize(iLineSize, iLineSize);\r\n" + 
				"				gc.SetPenSize(penSize);\r\n" + 
				"				gc.DrawRect(Rect());\r\n" + 
				"				}\r\n" + 
				"			}\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"\r\n" + 
				"void CVerticalLabel::SizeChanged()\r\n" + 
				"	{\r\n" + 
				"		\r\n" + 
				"	}";
		IASTTranslationUnit tu = parse(text, true);
		ProblemVisitor visitor = new ProblemVisitor();
		tu.accept(visitor);
		assertTrue(visitor.getProblems().length > 80);
	}
	
	// main for code pro test coverage
	public static void main(String[] args) {
		TestPKGParser myParser = new TestPKGParser();
		try {
			myParser.setUp();
			myParser.testEmptyTu();
			myParser.testOneLine();
			myParser.testEOLLine();
			myParser.testComment();
			myParser.testLanguage();
			myParser.testVendor();
			myParser.testPackageHeader();
			myParser.testLogo();
			myParser.testPackageHeader();
			myParser.testHardwareDependency();
			myParser.testVersionCompatibility();
			myParser.testSoftwareDependency();
			myParser.testPropertiesOrCapabilities();
			myParser.testConditional1();
			myParser.testConditional2();
			myParser.testConditional3();
			myParser.testConditional4();
			myParser.testConditional5();
			myParser.testConditional6();
			myParser.testConditional7();
			myParser.testOptionsList();
			myParser.testInstallFile();
			myParser.testEmbeddedSis();
			myParser.testCarbidePKG();
			myParser.testProblem1();
			myParser.testBugRegression();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
