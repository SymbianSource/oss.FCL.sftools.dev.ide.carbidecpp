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

import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.emf.dm.impl.PropertyContainerCopier;

import junit.framework.TestCase;

public class PropertyContainerCopierTest extends TestCase {
	
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
		
		child1 = DmFactory.eINSTANCE.createINode();
		child1.getProperties().set("name", nodeProperties.createLiteral(NAME1));
		rootNode.getChildren().add(child1);
		child2 = DmFactory.eINSTANCE.createINode();
		rootNode.getChildren().add(child2);
		child2.getProperties().set("name", nodeProperties.createLiteral(NAME2));
	}
	
	String getKey(IPropertyContainer pc, String propertyID) {
		IPropertyValue pv = (IPropertyValue) pc.getProperties().get(propertyID);
		assertNotNull(pv);
		assertTrue(pv.hasStringValue());
		assertTrue(pv.getStringValue().isKey());
		return pv.getStringValue().getValue();
	}
	
	void checkString(IPropertyContainer pc, String propertyID, int type, String expected) {
		Object value = pc.get(propertyID);
		assertNotNull(value);
		assertTrue(value instanceof StringValue);
		StringValue sv = (StringValue) value;
		assertEquals(type, sv.getType());
		String text = pc.lookupString(sv);
		assertEquals(expected, text);
	}
	
	public void testUndo() {
		// simulate an undo scenario by initializing a compound
		// property, capturing undo state, changing the properties,
		// undoing, and checking the values
		IPropertyValue compoundPV = nodeProperties.createPropertyContainerForProperty("test");
		nodeProperties.getProperties().put("test", compoundPV);
		IPropertyContainer srcProperties = compoundPV.getCompoundValue();
		
		srcProperties.set("literal", srcProperties.createLiteral("literal1"));
		srcProperties.set("macro1", srcProperties.createMacro("macro1-value"));
		srcProperties.set("macro2", srcProperties.createMacro("macro2-value"));
		srcProperties.set("localized1", srcProperties.createLocalized("localized1-value"));
		srcProperties.set("localized2", srcProperties.createLocalized("localized2-value"));
		srcProperties.set("reference1", srcProperties.createReference(NAME1));
		srcProperties.set("reference2", srcProperties.createReference(NAME2));
		IPropertyValue nestedCompoundPV = srcProperties.createPropertyContainerForProperty("nested-compound1");
		srcProperties.getProperties().put("nested-compound1", nestedCompoundPV);
		IPropertyContainer nestedCompoundProperties = nestedCompoundPV.getCompoundValue();
		nestedCompoundProperties.set("nested-literal", nestedCompoundProperties.createLiteral("nested-literal1"));
		nestedCompoundProperties.set("nested-macro", nestedCompoundProperties.createMacro("nested-macro1"));
		nestedCompoundProperties.set("nested-localized", nestedCompoundProperties.createLocalized("nested-localized1"));
		IPropertyValue nestedCompound2PV = srcProperties.createPropertyContainerForProperty("nested-compound2");
		srcProperties.getProperties().put("nested-compound2", nestedCompound2PV);
		IPropertyContainer nestedCompound2Properties = nestedCompound2PV.getCompoundValue();
		nestedCompound2Properties.set("aaa", nestedCompoundProperties.createLiteral("xyz"));
		
		// record expected table keys, so we know new ones aren't assigned
		String topMacro1Key = getKey(srcProperties, "macro1");
		String topMacro2Key = getKey(srcProperties, "macro2");	
		String topLocalized1Key = getKey(srcProperties, "localized1");
		String topLocalized2Key = getKey(srcProperties, "localized2");
		String nestedMacroKey = getKey(nestedCompoundProperties, "nested-macro");
		String nestedLocalizedKey = getKey(nestedCompoundProperties, "nested-localized");
		
		PropertyContainerCopier copier = new PropertyContainerCopier(srcProperties);
		assertNotNull(copier.getCopy());
		
		// change values prior to undo
		srcProperties.set("newly-added", srcProperties.createLocalized("new localized"));
		srcProperties.set("literal", srcProperties.createLiteral("another literal"));
		srcProperties.set("macro1", srcProperties.createMacro("another macro"));
		srcProperties.reset("macro2");
		srcProperties.set("localized1", srcProperties.createLocalized("another localized"));
		srcProperties.reset("localized2");
		nestedCompoundProperties.set("nested-literal", nestedCompoundProperties.createLocalized("loc123"));
		nestedCompoundProperties.reset("nested-macro");
		nestedCompoundProperties.set("nested-localized", nestedCompoundProperties.createLiteral("literal123"));
		srcProperties.reset("nested-compound2");
		
		// remove child node 2. Upon undo the reference should not be restored
		rootNode.getChildren().remove(child2);
		assertNull(srcProperties.getProperties().get("reference2"));
		
		copier.undo(true, true);
		
		srcProperties = (IPropertyContainer) nodeProperties.get("test");
		assertNotNull(srcProperties);
		nestedCompoundProperties = (IPropertyContainer) srcProperties.get("nested-compound1");
		assertNotNull(nestedCompoundProperties);
		nestedCompound2Properties = (IPropertyContainer) srcProperties.get("nested-compound2");
		assertNotNull(nestedCompound2Properties);
		
		assertEquals(topMacro1Key, getKey(srcProperties, "macro1"));
		assertEquals(topMacro2Key, getKey(srcProperties, "macro2"));
		assertEquals(topLocalized1Key, getKey(srcProperties, "localized1"));
		assertEquals(topLocalized2Key, getKey(srcProperties, "localized2"));
		assertEquals(nestedMacroKey, getKey(nestedCompoundProperties, "nested-macro"));
		assertEquals(nestedLocalizedKey, getKey(nestedCompoundProperties, "nested-localized"));
		
		assertNull(srcProperties.getProperties().get("newly-added"));
		
		checkString(srcProperties, "literal", StringValue.LITERAL, "literal1");
		checkString(srcProperties, "macro1", StringValue.MACRO, "macro1-value");
		checkString(srcProperties, "macro2", StringValue.MACRO, "macro2-value");
		checkString(srcProperties, "localized1", StringValue.LOCALIZED, "localized1-value");
		checkString(srcProperties, "localized2", StringValue.LOCALIZED, "localized2-value");
		checkString(srcProperties, "reference1", StringValue.REFERENCE, NAME1);
		assertNull(srcProperties.getProperties().get("reference2"));
		checkString(nestedCompoundProperties, "nested-literal", StringValue.LITERAL, "nested-literal1");
		checkString(nestedCompoundProperties, "nested-macro", StringValue.MACRO, "nested-macro1");
		checkString(nestedCompoundProperties, "nested-localized", StringValue.LOCALIZED, "nested-localized1");
		checkString(nestedCompound2Properties, "aaa", StringValue.LITERAL, "xyz");
	}

}
