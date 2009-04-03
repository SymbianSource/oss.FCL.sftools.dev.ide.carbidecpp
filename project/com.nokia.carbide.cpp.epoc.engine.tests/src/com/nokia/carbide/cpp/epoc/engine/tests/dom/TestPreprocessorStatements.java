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

import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorBinaryExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorElifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorElseStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorEndifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIfStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIfdefStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIfndefStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorLiteralExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTrinaryExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorUnaryExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorUndefStatement;


public class TestPreprocessorStatements extends BaseTest {

	public void testIntegerExpressions() {
		IASTPreprocessorLiteralExpression expr = ASTFactory
				.createPreprocessorLiteralExpression("123");
		assertEquals(123, expr.getIntValue());

		IASTPreprocessorLiteralExpression expr2 = ASTFactory
				.createPreprocessorLiteralExpression(123);
		assertEquals(123, expr2.getIntValue());

	}

	public void testBooleanExpressions() {
		IASTPreprocessorLiteralExpression expr = ASTFactory
				.createPreprocessorLiteralExpression("true");
		validateNewNode(expr);
		assertEquals(true, expr.getBooleanValue());

		expr.setValue(false);
		assertEquals(false, expr.getBooleanValue());
		assertTrue(expr.isDirty());

		IASTPreprocessorLiteralExpression expr2 = ASTFactory
				.createPreprocessorLiteralExpression(false);
		validateNewNode(expr2);
		assertEquals(false, expr2.getBooleanValue());

		expr2.setValue(true);
		assertEquals(true, expr2.getBooleanValue());
	}

	public void testUnaryExpressions() {
		IASTPreprocessorLiteralExpression expr = ASTFactory.createPreprocessorLiteralExpression(1);
		IASTPreprocessorUnaryExpression negExpr = ASTFactory.createPreprocessorUnaryExpression(IASTPreprocessorUnaryExpression.K_NEGATE, expr);
		validateNewNode(negExpr);
		
		assertEquals("-1", negExpr.getNewText());
		expr.setValue(30);
		assertTrue(negExpr.isDirtyTree());
		assertEquals("-30", negExpr.getNewText());
		
		negExpr.setOperator(IASTPreprocessorUnaryExpression.K_PARENTHESIS);
		assertEquals("( 30 )", negExpr.getNewText());
		assertEquals("( 30 )", negExpr.copy().getNewText());
		
		IASTPreprocessorLiteralExpression macro = ASTFactory.createPreprocessorLiteralExpression("FOO");
		IASTPreprocessorUnaryExpression defMacro = ASTFactory.createPreprocessorUnaryExpression(
				IASTPreprocessorUnaryExpression.K_DEFINED, macro);
		validateNewNode(defMacro);
		
		assertEquals("defined FOO", defMacro.getNewText());

	}	

	public void testBinaryExpressions() {
		IASTPreprocessorLiteralExpression expr1 = ASTFactory
				.createPreprocessorLiteralExpression(1);
		IASTPreprocessorLiteralExpression expr2 = ASTFactory
				.createPreprocessorLiteralExpression(2);
		IASTPreprocessorBinaryExpression binExpr = ASTFactory
				.createPreprocessorBinaryExpression(
						IASTPreprocessorBinaryExpression.K_ADD, expr1, expr2);
		validateNewNode(binExpr);

		assertEquals("1 + 2", binExpr.getNewText());
		assertEquals("1 + 2", binExpr.copy().getNewText());
		expr1.setValue(30);
		assertTrue(binExpr.isDirtyTree());

		assertEquals("30 + 2", binExpr.getNewText());

		binExpr.setOperator(IASTPreprocessorBinaryExpression.K_SUBSCRIPT);
		assertEquals("30[ 2 ]", binExpr.getNewText());
	}

	public void testTrinaryExpressions() {
		IASTPreprocessorLiteralExpression expr1 = 
			ASTFactory.createPreprocessorLiteralExpression(1);
		IASTPreprocessorLiteralExpression expr2 = 
			ASTFactory.createPreprocessorLiteralExpression(2);
		IASTPreprocessorBinaryExpression test = 
			ASTFactory.createPreprocessorBinaryExpression(
					IASTPreprocessorBinaryExpression.K_EQUALS,
					ASTFactory.createPreprocessorLiteralExpression(45),
					ASTFactory.createPreprocessorLiteralExpression("FOO"));
		IASTPreprocessorTrinaryExpression triExpr = 
			ASTFactory.createPreprocessorTrinaryExpression(
					test, expr1, expr2);
		validateNewNode(triExpr);
		assertEquals("45 == FOO ? 1 : 2", triExpr.getNewText());
		assertEquals("45 == FOO ? 1 : 2", triExpr.copy().getNewText());
		
		test.setOperator(IASTPreprocessorBinaryExpression.K_LESS_THAN);
		assertTrue(triExpr.isDirtyTree());
		assertEquals("45 < FOO ? 1 : 2", triExpr.getNewText());
	}
	
	public void testDefineDirective() {
		IASTPreprocessorDefineStatement define =
			ASTFactory.createPreprocessorDefineStatement(
						"FOO(a,b) a+3");
		validateNewNode(define);
		assertEquals("#define FOO(a,b) a+3\n", define.getNewText());
		
		assertEquals("a,b", define.getMacroArgs().getNewText());
		assertEquals("FOO", define.getMacroName().getNewText());
		assertEquals("a+3", define.getMacroExpansion().getNewText());
		
		IASTPreprocessorDefineStatement define2 =
			ASTFactory.createPreprocessorDefineStatement(
					ASTFactory.createPreprocessorLiteralTextNode("BAR"),
					ASTFactory.createPreprocessorLiteralTextNodeList(new String[] { "a1", "b2", "..." }),
					createSimpleTokenStream("schnozz+a1+b2"));
		validateNewNode(define2);
		assertEquals("#define BAR(a1,b2,...) schnozz+a1+b2\n", define2.getNewText());
		
		assertEquals("a1,b2,...", define2.getMacroArgs().getNewText());
		assertEquals("BAR", define2.getMacroName().getNewText());
		assertEquals("schnozz+a1+b2", define2.getMacroExpansion().getNewText());
		
		define2.getMacroName().setValue("fork");
		assertTrue(define2.isDirtyTree());
		assertEquals("#define fork(a1,b2,...) schnozz+a1+b2\n", define2.getNewText());

		define2.setDirtyTree(false);
		define2.getMacroArgs().clear();
		assertTrue(define2.isDirtyTree());
		assertEquals("#define fork() schnozz+a1+b2\n", define2.getNewText());

		define2.setDirtyTree(false);
		define2.setMacroArgs(null);
		assertTrue(define2.isDirty());
		assertEquals("#define fork schnozz+a1+b2\n", define2.getNewText());

	}
	
	public void testDefineUndefDirective() {
		IASTPreprocessorUndefStatement undef =
			ASTFactory.createPreprocessorUndefStatement(
					ASTFactory.createPreprocessorLiteralTextExpression("RAZOO"));
		validateNewNode(undef);
		assertEquals("#undef RAZOO\n", undef.getNewText());
	}
	
	public void testConditionalDirectives() {
		/*IASTPreprocessorBinaryExpression expr = 
			ASTFactory.createPreprocessorBinaryExpression(
					IASTPreprocessorBinaryExpression.K_GREATER_THAN_OR_EQUALS,
					ASTFactory.createPreprocessorLiteralExpression(44),
					ASTFactory.createPreprocessorLiteralExpression("RAZOO"));
		IASTPreprocessorIfStatement ifStmt =
			ASTFactory.createPreprocessorIfStatement(expr);*/
		IASTPreprocessorIfStatement ifStmt =
			ASTFactory.createPreprocessorIfStatement(
					ASTFactory.createPreprocessorTokenStream("44 >= RAZOO"));
		validateNewNode(ifStmt);
		assertEquals("#if 44 >= RAZOO\n", ifStmt.getNewText());
		
/*		IASTPreprocessorElifStatement elifStmt =
			ASTFactory.createPreprocessorElifStatement(
					(IASTPreprocessorExpression) expr.copy());*/
		IASTPreprocessorElifStatement elifStmt =
			ASTFactory.createPreprocessorElifStatement(
					(IASTPreprocessorTokenStream) ifStmt.getTokenStream().copy());
		validateNewNode(elifStmt);
		assertEquals("#elif 44 >= RAZOO\n", elifStmt.getNewText());
		assertEquals("#elif 44 >= RAZOO\n", elifStmt.copy().getNewText());

		IASTPreprocessorIfdefStatement ifdefStmt = 
			ASTFactory.createPreprocessorIfdefStatement(ASTFactory.createPreprocessorLiteralTextNode("RAZOO"));
		validateNewNode(ifdefStmt);
		assertEquals("#ifdef RAZOO\n", ifdefStmt.getNewText());

		IASTPreprocessorIfndefStatement ifndefStmt = 
			ASTFactory.createPreprocessorIfndefStatement(ASTFactory.createPreprocessorLiteralTextNode("RABID"));
		validateNewNode(ifndefStmt);
		assertEquals("#ifndef RABID\n", ifndefStmt.getNewText());

		IASTPreprocessorElseStatement elseStmt = 
			ASTFactory.createPreprocessorElseStatement();
		validateNewNode(elseStmt);
		assertEquals("#else\n", elseStmt.getNewText());
		
		IASTPreprocessorEndifStatement endifStmt = 
			ASTFactory.createPreprocessorEndifStatement();
		validateNewNode(endifStmt);
		assertEquals("#endif\n", endifStmt.getNewText());
		
	}
	
	public void testInclude() {
		IASTPreprocessorIncludeStatement stmt = 
			ASTFactory.createPreprocessorIncludeStatement("test.h", true);
		validateNewNode(stmt);
		assertEquals("#include \"test.h\"\n", stmt.getNewText());
	}
	
}
