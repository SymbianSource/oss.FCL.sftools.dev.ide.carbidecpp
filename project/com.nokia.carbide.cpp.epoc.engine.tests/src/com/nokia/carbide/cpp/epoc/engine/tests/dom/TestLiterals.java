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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression;


public class TestLiterals extends BaseTest {

	public void testLiteralText() {
		IASTLiteralTextExpression expr = ASTFactory.createPreprocessorLiteralTextExpression("foo bar");
		validateNewNode(expr);
		assertEquals("foo bar", expr.getValue());
		
		expr.setValue("nar bar");
		assertEquals("nar bar", expr.getValue());
		assertTrue(expr.isDirty());
		assertTrue(expr.isDirtyTree());
	}
	
	public void testLiteralConversions() {
		IASTLiteralTextExpression expr = ASTFactory.createPreprocessorLiteralTextExpression(
				"0x12345");
		validateNewNode(expr);
		assertEquals("0x12345", expr.getValue());
		
		assertEquals(0x12345, expr.getIntValue());

		expr.setValue(3, 2);
		assertEquals("0b11", expr.getValue());
		assertEquals(3, expr.getIntValue());
		
		expr.setValue(255, 8);
		assertEquals("0377", expr.getValue());
		assertEquals(255, expr.getIntValue());

		expr.setValue(0x3039, 16);
		assertEquals("0x3039", expr.getValue());
		assertEquals(0x3039, expr.getIntValue());

	}
}
