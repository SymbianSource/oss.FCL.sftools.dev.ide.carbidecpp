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
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStreamStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITranslationUnitListener;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

import java.util.Iterator;
import java.util.ListIterator;


public class TestTranslationUnit extends BaseTest {

	public void testBasic() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();
		assertNotNull(tu.getNodes());
		assertEquals(0,tu.getNodes().size());

		IASTPreprocessorTokenStreamStatement expr1 = ASTFactory.createPreprocessorTokenStreamStatement("1");
		tu.getNodes().add(expr1);
		
		assertEquals(tu.getNodes(), expr1.getParent());
		assertTrue(tu.getNodes().isDirty());
		
		assertNull(tu.getMainDocument());
		assertNull(tu.getMainLocation());
		
		IDocument doc = DocumentFactory.createDocument();
		tu.setMainDocument(doc);
		assertEquals(doc, tu.getMainDocument());
		
		IPath path = new Path("c:/foo/bar.txt");
		tu.setMainLocation(path);
		assertEquals(path, tu.getMainLocation());
	}
	
/*	static class MyListener implements ITranslationUnitListener {

		IASTLiteralTextNode lastTextNode;
		IASTListNode<IASTNode> lastListNode;
		IASTNode lastAddedNode;
		IASTNode lastRemovedNode;
		IASTNode lastChangedNode;
		
		int nodes;
		int litNodes;
		int addedItems;
		int removedItems;
		
		 (non-Javadoc)
		 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITranslationUnitListener#changedLiteralTextNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextExpression, java.lang.String, java.lang.String)
		 
		public void changedLiteralTextNode(IASTLiteralTextNode node, String oldValue, String newValue) {
			litNodes++;
			lastTextNode = node;
			
			// assert ordering: fire after change
			assertEquals(newValue, node.getValue());
		}

		 (non-Javadoc)
		 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITranslationUnitListener#addedListItem(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode, int)
		 
		public void addedListItem(IASTListNode<? extends IASTNode> list, IASTNode node, int index) {
			addedItems++;
			lastAddedNode = node;
			// assert ordering: fire after change
			assertTrue(list.contains(node));
		}

		 (non-Javadoc)
		 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITranslationUnitListener#removedListItem(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode, int)
		 
		public void removedListItem(IASTListNode<? extends IASTNode> list, IASTNode node, int index) {
			removedItems++;
			lastRemovedNode = node;
			assertTrue(!list.contains(node));
		}

		 (non-Javadoc)
		 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITranslationUnitListener#changedNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
		 
		public void changedNode(IASTNode node) {
			nodes++;
			lastChangedNode = node;
			
		}
		
	}
*/	
	
	class MyListener implements ITranslationUnitListener {

		IASTNode lastChangedNode;

		/* (non-Javadoc)
		 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITranslationUnitListener#changedNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
		 */
		public void changedNode(IASTNode node) {
			lastChangedNode = node;
		}
		
	}
	public void testRemoveListeners() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();

		IASTPreprocessorTokenStreamStatement expr1 = ASTFactory.createPreprocessorTokenStreamStatement("1");
		IASTPreprocessorTokenStreamStatement expr2 = ASTFactory.createPreprocessorTokenStreamStatement("2");
		IASTPreprocessorTokenStreamStatement expr3 = ASTFactory.createPreprocessorTokenStreamStatement("3");
		tu.getNodes().add(expr1);
		tu.getNodes().add(expr2);
		tu.getNodes().add(expr3);
		
		MyListener listener = new MyListener();
		tu.addListener(listener);
		tu.getNodes().remove(expr2);
		assertEquals(tu.getNodes(), listener.lastChangedNode);
		/*
		assertEquals(expr2, listener.lastRemovedNode);
		tu.getNodes().remove(expr1);
		assertEquals(expr1, listener.lastRemovedNode);
		tu.getNodes().remove(expr3);
		assertEquals(expr3, listener.lastRemovedNode);
		*/
	}

	public void testIteratorRemoveListeners() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();

		IASTPreprocessorTokenStreamStatement expr1 = ASTFactory.createPreprocessorTokenStreamStatement("1");
		IASTPreprocessorTokenStreamStatement expr2 = ASTFactory.createPreprocessorTokenStreamStatement("2");
		IASTPreprocessorTokenStreamStatement expr3 = ASTFactory.createPreprocessorTokenStreamStatement("3");
		tu.getNodes().add(expr1);
		tu.getNodes().add(expr2);
		tu.getNodes().add(expr3);
		
		MyListener listener = new MyListener();
		tu.addListener(listener);
		
		Iterator<IASTTopLevelNode> iter = tu.getNodes().iterator();
		assertEquals(expr1, iter.next());
		iter.remove();
		
		assertEquals(tu.getNodes(), listener.lastChangedNode);
		/*
		assertEquals(1, listener.removedItems);
		assertEquals(expr1, listener.lastRemovedNode);
		
		assertEquals(expr2, iter.next());
		iter.remove();
		assertEquals(2, listener.removedItems);
		assertEquals(expr2, listener.lastRemovedNode);
		*/
		
		// no more listening
		tu.removeListener(listener);
		
		listener.lastChangedNode = null;
		tu.getNodes().add(expr1);
		assertNull(listener.lastChangedNode);
		
		//assertNull(listener.lastAddedNode);
	}

	public void testListIteratorRemoveListeners() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();

		IASTPreprocessorTokenStreamStatement expr1 = ASTFactory.createPreprocessorTokenStreamStatement("1");
		IASTPreprocessorTokenStreamStatement expr2 = ASTFactory.createPreprocessorTokenStreamStatement("2");
		IASTPreprocessorTokenStreamStatement expr3 = ASTFactory.createPreprocessorTokenStreamStatement("3");
		tu.getNodes().add(expr1);
		tu.getNodes().add(expr2);
		tu.getNodes().add(expr3);
		
		MyListener listener = new MyListener();
		tu.addListener(listener);
		
		ListIterator<IASTTopLevelNode> iter = tu.getNodes().listIterator();
		assertEquals(expr1, iter.next());
		iter.remove();
		
		/*
		assertEquals(expr1, listener.lastRemovedNode);
		assertEquals(1, listener.removedItems);

		assertEquals(expr2, iter.next());
		iter.remove();
		assertEquals(expr2, listener.lastRemovedNode);
		assertEquals(2, listener.removedItems);
*/
		assertEquals(tu.getNodes(), listener.lastChangedNode);
		
		tu.removeListener(listener);
		
		
	}

	public void testListIteratorRemoveListeners2() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();

		IASTPreprocessorTokenStreamStatement expr1 = ASTFactory.createPreprocessorTokenStreamStatement("1");
		IASTPreprocessorTokenStreamStatement expr2 = ASTFactory.createPreprocessorTokenStreamStatement("2");
		IASTPreprocessorTokenStreamStatement expr3 = ASTFactory.createPreprocessorTokenStreamStatement("3");
		tu.getNodes().add(expr1);
		tu.getNodes().add(expr2);
		tu.getNodes().add(expr3);
		
		MyListener listener = new MyListener();
		tu.addListener(listener);
		
		ListIterator<IASTTopLevelNode> iter = tu.getNodes().listIterator(3);
		assertEquals(expr3, iter.previous());
		iter.remove();
		/*
		assertEquals(1, listener.removedItems);

		assertEquals(expr3, listener.lastRemovedNode);
		
		assertEquals(expr2, iter.previous());
		iter.remove();
		assertEquals(2, listener.removedItems);

		assertEquals(expr2, listener.lastRemovedNode);
		*/
		assertEquals(tu.getNodes(), listener.lastChangedNode);
		tu.removeListener(listener);
		
		
	}

	public void testAddListeners() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();

		IASTPreprocessorTokenStreamStatement expr1 = ASTFactory.createPreprocessorTokenStreamStatement("1");
		IASTPreprocessorTokenStreamStatement expr2 = ASTFactory.createPreprocessorTokenStreamStatement("2");
		
		MyListener listener = new MyListener();
		tu.addListener(listener);
		tu.getNodes().add(expr1);
		/*
		assertEquals(expr1, listener.lastAddedNode);
		assertEquals(1, listener.addedItems);
		*/
		tu.getNodes().add(expr2);
		/*
		assertEquals(expr2, listener.lastAddedNode);
		assertEquals(2, listener.addedItems);
		*/
		assertEquals(tu.getNodes(), listener.lastChangedNode);
		
	}
	
	public void testListIteratorAddListeners() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();

		IASTPreprocessorTokenStreamStatement expr1 = ASTFactory.createPreprocessorTokenStreamStatement("1");
		IASTPreprocessorTokenStreamStatement expr2 = ASTFactory.createPreprocessorTokenStreamStatement("2");
		
		MyListener listener = new MyListener();
		tu.addListener(listener);
		
		ListIterator<IASTTopLevelNode> iter = tu.getNodes().listIterator();
		iter.add(expr1);
		/*
		assertEquals(expr1, listener.lastAddedNode);
		assertEquals(1, listener.addedItems);
*/
		iter.add(expr2);
		
		//assertEquals(expr2, listener.lastAddedNode);
		//assertEquals(2, listener.addedItems);

		assertEquals(tu.getNodes(), listener.lastChangedNode);

		assertEquals(2, tu.getNodes().size());
	}
	
	/*
	public void testLiteralTextListener() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();

		IASTPreprocessorTokenStreamStatement expr1 = ASTFactory.createPreprocessorTokenStreamStatement("1");

		tu.getNodes().add(expr1);
		
		MyListener listener = new MyListener();
		tu.addListener(listener);
		
		expr1.setTokenStream(ASTFactory.createPreprocessorTokenStream("slurm"));
		
		assertEquals(expr1, listener.lastChangedNode);
		listener.lastChangedNode = null;
		
		// no change
		expr1.setTokenStream(ASTFactory.createPreprocessorTokenStream("slurm"));
		assertNull(listener.lastChangedNode);

	}*/
	
	public void testOtherNodeListener() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();
		IASTLiteralTextExpression expr1 = ASTFactory.createPreprocessorLiteralTextExpression("text");

		IASTTestStatement stmt = new ASTTestStatement(expr1);

		tu.getNodes().add(stmt);

		MyListener listener = new MyListener();
		tu.addListener(listener);

		expr1.setValue(10, 1);
		assertEquals(expr1, listener.lastChangedNode);
		//assertEquals(expr1, listener.lastTextNode);
	}

	public void testCopy() {
		IASTTranslationUnit tu = ASTFactory.createTranslationUnit();
		IASTLiteralTextExpression expr1 = ASTFactory.createPreprocessorLiteralTextExpression("text");

		IASTTestStatement stmt = new ASTTestStatement(expr1);
		tu.getNodes().add(stmt);

		IASTPreprocessorTokenStreamStatement stmt2 = ASTFactory.createPreprocessorTokenStreamStatement("stmt 2");

		tu.getNodes().add(stmt2);
		
		IASTTranslationUnit tu2 = (IASTTranslationUnit) tu.copy();
		
		testCopiedTree(tu, tu2);
		
	}
}
