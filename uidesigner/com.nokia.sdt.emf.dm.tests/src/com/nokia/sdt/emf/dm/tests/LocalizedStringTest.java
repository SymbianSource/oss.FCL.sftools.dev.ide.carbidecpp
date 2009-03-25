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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.emf.dm.*;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Map;

import junit.framework.TestCase;

public class LocalizedStringTest extends TestCase {
	
	static final String COMPONENT = "com.nokia.examples.baseComponent";
	
	DesignerDataModel model;
	EObject root;
	
	protected void setUp() throws Exception {
		
		model = (DesignerDataModel) TestDataModelHelpers.createTemporaryModel();
		TestDataModelHelpers.initDefaultComponentSet(model);
		IComponent component = model.getComponentSet().lookupComponent(COMPONENT);
		root = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(root);
		IPropertySource ps = AdapterHelpers.getPropertySource(root);
		ps.setPropertyValue("name", "view1");
	}
	
	public void testAddLocalizedString() {
		IPropertySource ps = AdapterHelpers.getPropertySource(root);
		String value = "abc";
		ps.setPropertyValue("text", value);
		assertEquals(value, ps.getPropertyValue("text"));
		
		assertTrue(ps instanceof IPropertyInformation);
		IPropertyInformation locProp = (IPropertyInformation) ps;
		String key = locProp.getPropertyValueSymbol("text");
		assertNotNull(key);
		assertTrue(!key.equals(value));
		
		assertNull(locProp.getPropertyValueSymbol("name"));
		
		INode node = (INode) root;
		IPropertyValue pv = (IPropertyValue) node.getProperties().getProperties().get("text");
		assertTrue(pv.hasStringValue());
		assertTrue(pv.getStringValue().isLocalized());
		
		assertEquals(key, pv.getStringValue().getValue());
		
		ILocalizedStringBundle bundle = model.getDesignerData().getStringBundle();
		assertTrue(bundle.hasStringKey(key));
		Map values = bundle.findStrings(key);
		assertNotNull(values);
		assertTrue(values.size() == 1); // only default language expected
		assertEquals(value, values.get(bundle.getDefaultLanguage()));
	}
	
	public void testRemoveLocalizedStringOneLang() {
		IPropertySource ps = AdapterHelpers.getPropertySource(root);
		String value = "abc";
		ps.setPropertyValue("text", value);
		
		INode node = (INode) root;
		IPropertyValue pv = (IPropertyValue) node.getProperties().getProperties().get("text");
		
		StringValue sv = pv.getStringValue();
		ps.resetPropertyValue("text");
		
		ILocalizedStringBundle bundle = model.getDesignerData().getStringBundle();
		assertFalse(bundle.hasStringKey(sv.getValue()));
	}
	
	public void testRemoveLocalizedStringTwoLang() {
		ILocalizedStringBundle bundle = model.getDesignerData().getStringBundle();
		Language lang2 = new Language(Language.LANG_Spanish);
		bundle.addLocalizedStringTable(lang2);
		
		IPropertySource ps = AdapterHelpers.getPropertySource(root);
		String value = "abc";
		ps.setPropertyValue("text", value);
		
		INode node = (INode) root;
		IPropertyValue pv = (IPropertyValue) node.getProperties().getProperties().get("text");
		
		StringValue sv = pv.getStringValue();
		bundle.findLocalizedStringTable(lang2).getStrings().put(sv.getValue(), "spanish");

		
		ps.resetPropertyValue("text");

		// When >1 language resetting the property value should
		// not delete for all languages. It should reset the current
		// language to the default value and leave values for other 
		// languages in place.

		assertTrue(bundle.hasStringKey(sv.getValue()));
		String s = bundle.findString(lang2, sv.getValue());
		assertEquals("spanish", s);

		String str2 = bundle.findString(bundle.getDefaultLanguage(), sv.getValue());
		assertTrue(str2 == null);
	}
	
	public void testModifyLocalizedString() {
		IPropertySource ps = AdapterHelpers.getPropertySource(root);
		String value = "abc";
		ps.setPropertyValue("text", value);
		
		INode node = (INode) root;
		IPropertyValue pv = (IPropertyValue) node.getProperties().getProperties().get("text");
		StringValue sv = pv.getStringValue();
		
		String newValue = "def";
		ps.setPropertyValue("text", newValue);
	
		ILocalizedStringBundle bundle = model.getDesignerData().getStringBundle();
		assertTrue(bundle.hasStringKey(sv.getValue()));
		Map values = bundle.findStrings(sv.getValue());
		assertNotNull(values);
		assertTrue(values.size() == 1); // only default language expected
		assertEquals(newValue, values.get(bundle.getDefaultLanguage()));
	}

}
