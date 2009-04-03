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

import java.util.Iterator;

import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;


public class TestListNode extends BaseTest {

	public void testBasic() {
		IASTListNode<IASTNode> list = ASTFactory.createListNode(" ");
		validateNewNode(list);
		
		IASTLiteralTextExpression expr = ASTFactory.createPreprocessorLiteralTextExpression("1");
		list.add(expr);
		assertTrue(list.isDirty());
		assertEquals(1, list.size());
		assertTrue(list.contains(expr));
	
		IASTLiteralTextExpression expr2 = ASTFactory.createPreprocessorLiteralTextExpression("1");
		list.add(expr2);

		assertEquals(2, list.size());
		assertTrue(list.contains(expr2));

		assertEquals(list, expr.getParent());
		assertEquals(list, expr2.getParent());
		
		IASTNode[] nodes = list.toArray(new IASTNode[0]);
		assertEquals(2, nodes.length);

	}
	
	public void testIterator1() {
		IASTListNode<IASTNode> list = ASTFactory.createListNode("-");
		validateNewNode(list);
		
		IASTLiteralTextExpression expr = ASTFactory.createPreprocessorLiteralTextExpression("1");
		list.add(expr);
		IASTLiteralTextExpression expr2 = ASTFactory.createPreprocessorLiteralTextExpression("1");
		list.add(expr2);

		Iterator<IASTNode> iter = list.iterator();
		assertTrue(iter.hasNext());
		assertEquals(expr, iter.next());
		assertEquals(expr2, iter.next());
		assertFalse(iter.hasNext());
		
	}
	public void testIterator2() {
		IASTListNode<IASTNode> list = ASTFactory.createListNode(null);
		validateNewNode(list);
		
		IASTLiteralTextExpression expr = ASTFactory.createPreprocessorLiteralTextExpression("1");
		list.add(expr);
		IASTLiteralTextExpression expr2 = ASTFactory.createPreprocessorLiteralTextExpression("1");
		list.add(expr2);

		Iterator<IASTNode> iter = list.iterator();
		assertTrue(iter.hasNext());
		assertEquals(expr, iter.next());
		iter.remove();
		
		assertEquals(1, list.size());
		
		assertTrue(iter.hasNext());
		assertEquals(expr2, iter.next());
		assertFalse(iter.hasNext());
		
	}
	
	// more tests in TestTranslationUnit
	
}
