/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.emf.dm.tests;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.impl.NodeCopier;

import org.eclipse.emf.common.util.EList;

import junit.framework.TestCase;

public class NodeCopierTest extends TestCase {

	IDesignerData root;
	IPropertyContainer nodeProperties;
	INode rootNode, child1, child2;
	
	static final String NAME1 = "child1";
	static final String NAME2 = "child2";

	protected void setUp() throws Exception {
		root = DmFactory.eINSTANCE.createIDesignerData();
		rootNode = DmFactory.eINSTANCE.createINode();
		root.getRootContainers().add(rootNode);
		nodeProperties = rootNode.getProperties();
		
	}
	
	private void checkString(IPropertyContainer pc, String propertyID, int type, String expected) {
		Object value = pc.get(propertyID);
		assertNotNull(value);
		assertTrue(value instanceof StringValue);
		StringValue sv = (StringValue) value;
		assertEquals(type, sv.getType());
		String text = pc.lookupString(sv);
		assertEquals(expected, text);
	}

	private String getKey(IPropertyContainer pc, String propertyID) {
		IPropertyValue pv = (IPropertyValue) pc.getProperties().get(propertyID);
		assertNotNull(pv);
		assertTrue(pv.hasStringValue());
		assertTrue(pv.getStringValue().isKey());
		return pv.getStringValue().getValue();
	}
	
	public void testNodeCopy() {
		child1 = DmFactory.eINSTANCE.createINode();
		child1.getProperties().set("name", nodeProperties.createLiteral(NAME1));
		child1.setComponentId(NAME1);
		rootNode.getChildren().add(child1);
		
		IPropertyContainer pc1 = child1.getProperties();
		pc1.set("loc1", pc1.createLocalized("localized1"));
		String loc1Key = getKey(pc1, "loc1");
		
		child2 = DmFactory.eINSTANCE.createINode();
		child1.getChildren().add(child2);
		child2.getProperties().set("name", nodeProperties.createLiteral(NAME2));
		child2.setComponentId(NAME2);
	
		IPropertyContainer pc2 = child2.getProperties();
		pc2.set("macro1", pc2.createMacro("macro1"));
		String macro1Key = getKey(pc2, "macro1");
		
		NodeCopier copier = new NodeCopier(child1);
		
		rootNode.getChildren().clear();
	
		NodeCopier.copyNode(copier.getCopy(), rootNode, IDesignerDataModel.AT_END, true);
		
		assertTrue(rootNode.getChildren().size() == 1);
		INode testChild1 = (INode) rootNode.getChildren().get(0);
		assertNotNull(testChild1);
		assertEquals(NAME1, testChild1.getComponentId());
		assertTrue(rootNode.getChildren().size() == 1);
		INode testChild2 = (INode) testChild1.getChildren().get(0);
		assertNotNull(testChild2);
		assertEquals(NAME2, testChild2.getComponentId());
		
		pc1 = testChild1.getProperties();
		pc2 = testChild2.getProperties();
		
		checkString(pc1, "loc1", StringValue.LOCALIZED, "localized1");
		checkString(pc2, "macro1", StringValue.MACRO, "macro1");
		 
		assertEquals(loc1Key, getKey(pc1, "loc1"));
		assertEquals(macro1Key, getKey(pc2, "macro1"));
	}
	
	public void testNoSharedStrings() {
		child1 = DmFactory.eINSTANCE.createINode();
		child1.getProperties().set("name", nodeProperties.createLiteral(NAME1));
		child1.setComponentId(NAME1);
		rootNode.getChildren().add(child1);
		
		IPropertyContainer pc = child1.getProperties();
		pc.set("loc1", pc.createLocalized("localized1"));
		String loc1Key = getKey(pc, "loc1");
		
		pc.set("macro1", pc.createMacro("macro1"));
		String macro1Key = getKey(pc, "macro1");
		
		NodeCopier copier = new NodeCopier(child1);
		copier.getCopy().getProperties().set(INode.NAME_PROPERTY, (StringValue) null);
		child2 = NodeCopier.copyNode(copier.getCopy(), rootNode, IDesignerDataModel.AT_END, true);
		pc = child2.getProperties();

		String loc1Key2 = getKey(pc, "loc1");
		assertNotNull(loc1Key2);
		assertNotSame(loc1Key, loc1Key2);
		
		String macro1Key2 = getKey(pc, "macro1");
		assertNotNull(macro1Key2);
		assertNotSame(macro1Key, macro1Key2);
	}
	
	public void testPreserveEvents() {
		child1 = DmFactory.eINSTANCE.createINode();
		child1.getProperties().set("name", nodeProperties.createLiteral(NAME1));
		child1.setComponentId(NAME1);
		rootNode.getChildren().add(child1);

		IEventBinding binding = DmFactory.eINSTANCE.createIEventBinding();
		binding.setEventID("foo.event");
		binding.setEventHandlerDisplayText("Foo Event");
		binding.setEventHandlerInfo(null);
		child1.getEventBindings().add(binding);
		assertFalse(child1.getEventBindings().isEmpty());
		
		NodeCopier copier = new NodeCopier(child1, true, null);
		assertFalse(copier.getCopy().getEventBindings().isEmpty());
		copier.getCopy().getProperties().set(INode.NAME_PROPERTY, (StringValue) null);
		
		INode copy1 = NodeCopier.copyNode(copier.getCopy(), rootNode, IDesignerDataModel.AT_END, false);
		assertTrue(copy1.getEventBindings().isEmpty());
		
		INode copy2 = NodeCopier.copyNode(copier.getCopy(), rootNode, IDesignerDataModel.AT_END, false, true);
		assertFalse(copy2.getEventBindings().isEmpty());
	}
	
	public void testFilter() {
		child1 = DmFactory.eINSTANCE.createINode();
		child1.getProperties().set("name", nodeProperties.createLiteral(NAME1));
		child1.setComponentId(NAME1);
		rootNode.getChildren().add(child1);
		
		child2 = DmFactory.eINSTANCE.createINode();
		child1.getChildren().add(child2);
		child2.getProperties().set("name", nodeProperties.createLiteral(NAME2));
		child2.setComponentId(NAME2);
	
		NodeCopier copier = new NodeCopier(child1);
		
		rootNode.getChildren().clear();
	
		NodeCopier.copyNode(copier.getCopy(), rootNode, IDesignerDataModel.AT_END, true, 
				new NodeCopier.IFilter() {
					public boolean test(INode node) {
						return !node.getName().equals(NAME2);
					}
				});
		
		assertTrue(rootNode.getChildren().size() == 1);
		INode testChild1 = (INode) rootNode.getChildren().get(0);
		assertNotNull(testChild1);
		assertEquals(NAME1, testChild1.getComponentId());
		EList children = testChild1.getChildren();
		assertTrue(children.isEmpty());
	}

}
