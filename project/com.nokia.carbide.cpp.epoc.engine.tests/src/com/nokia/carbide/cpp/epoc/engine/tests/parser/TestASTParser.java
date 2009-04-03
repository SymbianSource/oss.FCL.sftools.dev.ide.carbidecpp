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

import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorBinaryExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorUnaryExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IMacroProvider;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IPreprocessorExpressionParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;

/**
 * Test the preprocessor expression parser. 
 *
 */
public class TestASTParser extends BaseTest {
	static final String TEST_EXPR_1 = "1\n";
	static final String TEST_EXPR_X = "X\n";
	static final String TEST_EXPR_X_EQ_1 = "X == 1\n";
	static final String TEST_ELIF_X_EQ_1 = "X == 1\n";
	static final String TEST_EXPR_DEFINED_X23_1 = "defined X23\n";
	static final String TEST_EXPR_DEFINED_X23_2 = "defined(X23)\n";
	static final String TEST_EXPR_NOT_DEFINED_X23_1 = "!defined(X23)\n";
	static final String TEST_EXPR_NOT_DEFINED_X23_2 = "!defined X23\n";

	static final String TEST_EXPR_COMPLEX = "(defined(WINS) && defined(CW32)) ||\\\ndefined(ARMV5)\n";

	static final String TEST_EXPR_COMPLEX_1 = "123|456.0*0x12FC";
	static final String TEST_EXPR_COMPLEX_2 = "123|456.0*0x12FC/077&Y_I_P^!defined(X)==34\\\n?RT\\\n:~LAX<67&&!(V*Y>=2)\n";


	private IMacroProvider macroProvider;
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		macroProvider = new IMacroProvider() {

			public IASTPreprocessorDefineStatement lookupMacro(String name) {
				return null;
			}
			
		};
	}	
	
	/**
	 * 
	 */
	private IASTPreprocessorExpression createDefinedExpr(String macroName) {
		IASTPreprocessorExpression definedWINS = ASTFactory.createPreprocessorUnaryExpression(
				IASTPreprocessorUnaryExpression.K_DEFINED, 
				ASTFactory.createPreprocessorLiteralExpression(macroName));
		return definedWINS;
	}

	protected IASTPreprocessorExpression parse(String text) {
		IPreprocessorExpressionParser parser = ParserFactory.createPreprocessorExpressionParser();
		IASTPreprocessorTokenStream stream = ASTFactory.createPreprocessorTokenStream(text);
		IASTPreprocessorExpression expr = parser.parse(stream, macroProvider);
		assertFalse(parser.hadErrors());
		return expr;
	}
	
	protected void testParsedExpr(IASTPreprocessorExpression refExpr, IASTPreprocessorExpression expr) {
		if (!refExpr.equals(expr))
			assertEquals(refExpr.getNewText(), expr.getNewText()); // ensure a diff dialog
		checkHashCodes(refExpr, expr);
		assertEquals(refExpr.getNewText(), expr.getNewText());

	}
	
	public void testExpr1() {
		IASTPreprocessorExpression refExpr = ASTFactory.createPreprocessorLiteralExpression(1);
		IASTPreprocessorExpression expr = parse(TEST_EXPR_1);
		testParsedExpr(refExpr, expr);
	}
	
	public void testExprX() {
		IASTPreprocessorExpression refExpr = 
				ASTFactory.createPreprocessorLiteralExpression("X");
		IASTPreprocessorExpression expr = parse(TEST_EXPR_X);
		testParsedExpr(refExpr, expr);
	}
	
	public void testExprXeq1() {
		IASTPreprocessorExpression refExpr = 
				ASTFactory.createPreprocessorBinaryExpression(IASTPreprocessorBinaryExpression.K_EQUALS, 
						ASTFactory.createPreprocessorLiteralExpression("X"),
						ASTFactory.createPreprocessorLiteralExpression(1));
		IASTPreprocessorExpression expr = parse(TEST_EXPR_X_EQ_1);
		testParsedExpr(refExpr, expr);
	}

	
	public void testElifXeq1() {
		IASTPreprocessorExpression refExpr = 
				ASTFactory.createPreprocessorBinaryExpression(IASTPreprocessorBinaryExpression.K_EQUALS, 
						ASTFactory.createPreprocessorLiteralExpression("X"),
						ASTFactory.createPreprocessorLiteralExpression(1));
		IASTPreprocessorExpression expr = parse(TEST_ELIF_X_EQ_1);
		testParsedExpr(refExpr, expr);
	}
	
	
	public void testExprDefinedX23_1() {
		IASTPreprocessorExpression refExpr = createDefinedExpr("X23");
		IASTPreprocessorExpression expr = parse(TEST_EXPR_DEFINED_X23_1);
		testParsedExpr(refExpr, expr);
	}

	public void testExprDefinedX23_2() {
		IASTPreprocessorExpression refExpr = createDefinedExpr("X23");
		IASTPreprocessorExpression expr = parse(TEST_EXPR_DEFINED_X23_2);
		testParsedExpr(refExpr, expr);
	}
	public void testExprNotDefinedX23_1() {
		IASTPreprocessorExpression refExpr =
			ASTFactory.createPreprocessorUnaryExpression(IASTPreprocessorUnaryExpression.K_NOT, 
					createDefinedExpr("X23"));
		IASTPreprocessorExpression expr = parse(TEST_EXPR_NOT_DEFINED_X23_1);
		testParsedExpr(refExpr, expr);
	}
	public void testExprNotDefinedX23_2() {
		IASTPreprocessorExpression refExpr =
			ASTFactory.createPreprocessorUnaryExpression(IASTPreprocessorUnaryExpression.K_NOT, 
					createDefinedExpr("X23"));
		IASTPreprocessorExpression expr = parse(TEST_EXPR_NOT_DEFINED_X23_2);
		testParsedExpr(refExpr, expr);
	}

	
	public void testExprComplex() {
		IASTPreprocessorExpression definedWINS = createDefinedExpr("WINS");
		IASTPreprocessorExpression definedCW32 = createDefinedExpr("CW32");
		IASTPreprocessorExpression definedARMV5 = createDefinedExpr("ARMV5");
		IASTPreprocessorExpression andExpr = ASTFactory.createPreprocessorBinaryExpression(IASTPreprocessorBinaryExpression.K_LOG_AND,
				definedWINS, definedCW32);
		
		IASTPreprocessorExpression test = ASTFactory.createPreprocessorBinaryExpression(
				IASTPreprocessorBinaryExpression.K_LOG_OR,
				ASTFactory.createPreprocessorUnaryExpression(IASTPreprocessorUnaryExpression.K_PARENTHESIS, andExpr),
				definedARMV5);
		
		IASTPreprocessorExpression refExpr = test;
		IASTPreprocessorExpression expr = parse(TEST_EXPR_COMPLEX);
		testParsedExpr(refExpr, expr);
	}
	
	public void testExprComplex1() {
		// just be sure it parses
		IASTPreprocessorExpression expr = parse(TEST_EXPR_COMPLEX_1);
		assertEquals("123 | 456.0 * 0x12FC", 
				expr.getNewText());
	}
	public void testExprComplex2() {
		// just be sure it parses
		IASTPreprocessorExpression expr = parse(TEST_EXPR_COMPLEX_2);
		assertEquals("123 | 456.0 * 0x12FC / 077 & Y_I_P ^ !defined X == 34 ? RT : ~LAX < 67 && !( V * Y >= 2 )", 
				expr.getNewText());
	}

}
