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
package com.nokia.sdt.uimodel.tests;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.testsupport.FileHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

import java.io.File;

import junit.framework.TestCase;

public class ExtendWithEnumPropertyTest extends TestCase {
	
	private static final String ENUM_PROPERTY = "emphasis";


	private static final String INTEGER_PROPERTY = "pixelGapBetweenLines";
	private static final String BOOLEAN_PROPERTY = "underline";
	private static final String STRING_PROPERTY = "text";


	static final String LABEL_COMPONENT = "com.nokia.examples.CEikLabelTest";
	
	
	IProject project;
	IDesignerDataModel model;
	EObject label;
	IPropertySource labelPS;
	
	protected void setUp() throws Exception {
		TestDataModelHelpers.setupResourceFactory();
		TestDataModelHelpers.setupTestComponents();
		
		TestHelpers.setPlugin(TestsPlugin.getDefault());
		File srcFile = FileHelpers.projectRelativeFile("data/minimal-model.nxd");
		
		model = TestDataModelHelpers.loadDataModel(srcFile);
		assertNotNull(model);
		
		EObject root = model.getRootContainers()[0];
		TestDataModelHelpers.assignUniqueName(model, root);
		
		IComponentSet cs = model.getComponentSet();
		
		IComponent labelComponent = cs.lookupComponent(LABEL_COMPONENT);
		label = model.createNewComponentInstance(labelComponent);
		Command cmd = model.createAddNewComponentInstanceCommand(root, label, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();
		TestDataModelHelpers.assignUniqueName(model, label);
		
		labelPS = getPS(label);
	}

	protected void tearDown() throws Exception {
		model.dispose();
	}
	
	IComponentInstance getCI(EObject object) {
		IComponentInstance result = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
				object, IComponentInstance.class);
		return result;
	}
	
	IPropertySource getPS(EObject object) {
		IPropertySource result = (IPropertySource) EcoreUtil.getRegisteredAdapter(object, IPropertySource.class);
		return result;
	}
	
	String name(IPropertySource ps) {
		return (String) ps.getPropertyValue("name");
	}

	/** that that non-extended properties work */
	public void testSimple() {
		assertEquals("NormalFont", labelPS.getPropertyValue("font"));
		labelPS.setPropertyValue("font", "LegendFont");
		assertEquals("LegendFont", labelPS.getPropertyValue("font"));
		labelPS.resetPropertyValue("font");
		assertEquals("NormalFont", labelPS.getPropertyValue("font"));
		
	}
	
	public void testExtendedEnum() {
		assertEquals("EPartialEmphasis", labelPS.getPropertyValue(ENUM_PROPERTY));
		labelPS.setPropertyValue(ENUM_PROPERTY, "ENoEmphasis");
		assertEquals("ENoEmphasis", labelPS.getPropertyValue(ENUM_PROPERTY));
		
		labelPS.resetPropertyValue(ENUM_PROPERTY);
		assertEquals("EPartialEmphasis", labelPS.getPropertyValue(ENUM_PROPERTY));
		
		// test base value with localized string	
		labelPS.setPropertyValue(ENUM_PROPERTY, "no emphasis");
		assertEquals("ENoEmphasis", labelPS.getPropertyValue(ENUM_PROPERTY));
		
		// extended value
		labelPS.setPropertyValue(ENUM_PROPERTY, "0");
		assertEquals("0", labelPS.getPropertyValue(ENUM_PROPERTY));
		labelPS.setPropertyValue(ENUM_PROPERTY, "One");
		assertEquals("1", labelPS.getPropertyValue(ENUM_PROPERTY));
		
		try {
			// illegal enum either as base or extended
			labelPS.setPropertyValue(ENUM_PROPERTY, "barf");
			fail();
		} catch (Exception e) {
			// good
		}
	}
	
	public void testExtendedInteger() {
		assertEquals("0", labelPS.getPropertyValue(INTEGER_PROPERTY));
		labelPS.setPropertyValue(INTEGER_PROPERTY, new Integer(3));
		assertEquals("3", labelPS.getPropertyValue(INTEGER_PROPERTY));
		// either integer or string or double accepted due to
		// script, data model, or direct manipulation inputs being different
		labelPS.setPropertyValue(INTEGER_PROPERTY, "-8");
		assertEquals("-8", labelPS.getPropertyValue(INTEGER_PROPERTY));
		labelPS.setPropertyValue(INTEGER_PROPERTY, new Double(6.0));
		assertEquals("6", labelPS.getPropertyValue(INTEGER_PROPERTY));
		
		labelPS.resetPropertyValue(INTEGER_PROPERTY);
		assertEquals("0", labelPS.getPropertyValue(INTEGER_PROPERTY));

		labelPS.setPropertyValue(INTEGER_PROPERTY, "NegOne");
		assertEquals("-1", labelPS.getPropertyValue(INTEGER_PROPERTY));
		
		labelPS.setPropertyValue(INTEGER_PROPERTY, "Zero");
		assertEquals("0", labelPS.getPropertyValue(INTEGER_PROPERTY));
		
		try {
			// illegal value either as base or extended
			labelPS.setPropertyValue(INTEGER_PROPERTY, "barf");
			fail();
		} catch (Exception e) {
			// good
		}

	}
	
	public void testExtendedBoolean() {
		assertEquals("false", labelPS.getPropertyValue(BOOLEAN_PROPERTY));
		labelPS.setPropertyValue(BOOLEAN_PROPERTY, Boolean.TRUE);
		assertEquals("true", labelPS.getPropertyValue(BOOLEAN_PROPERTY));
		// either integer or string or double accepted due to
		// script, data model, or direct manipulation inputs being different
		labelPS.setPropertyValue(BOOLEAN_PROPERTY, "false");
		assertEquals("false", labelPS.getPropertyValue(BOOLEAN_PROPERTY));

		labelPS.setPropertyValue(BOOLEAN_PROPERTY, "Zero");
		assertEquals("0", labelPS.getPropertyValue(BOOLEAN_PROPERTY));
		labelPS.setPropertyValue(BOOLEAN_PROPERTY, "NegOne");
		assertEquals("-1", labelPS.getPropertyValue(BOOLEAN_PROPERTY));
		
		try {
			// illegal value either as base or extended
			labelPS.setPropertyValue(BOOLEAN_PROPERTY, "barf");
			fail();
		} catch (Exception e) {
			// good
		}

	}

	public void testExtendedString() {
		assertEquals("label", labelPS.getPropertyValue(STRING_PROPERTY));
		labelPS.setPropertyValue(STRING_PROPERTY, "foo");
		assertEquals("foo", labelPS.getPropertyValue(STRING_PROPERTY));

		labelPS.resetPropertyValue(STRING_PROPERTY);
		assertEquals("label", labelPS.getPropertyValue(STRING_PROPERTY));

		labelPS.setPropertyValue(STRING_PROPERTY, "Zero");
		assertEquals("0", labelPS.getPropertyValue(STRING_PROPERTY));
		labelPS.setPropertyValue(STRING_PROPERTY, "NegOne");
		assertEquals("-1", labelPS.getPropertyValue(STRING_PROPERTY));
	}

}
