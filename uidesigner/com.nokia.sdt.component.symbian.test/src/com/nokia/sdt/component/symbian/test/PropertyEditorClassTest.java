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
package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.properties.ComponentPropertySource;
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.ui.views.properties.IPropertySource;

import junit.framework.TestCase;

public class PropertyEditorClassTest extends TestCase {

	IComponentSet componentSet;
	IPropertySource fixture;


	protected void setUp() throws Exception {
		componentSet = ComponentHelpers.querySDKFilter(
				TestDataModelHelpers.getDefaultComponentProvider(),
				"com.nokia.series60", "2.0");
		Component testComponent = (Component) componentSet.lookupComponent("com.nokia.test.editFactoryTest");

		ComponentPropertySource cps = new ComponentPropertySource(
				testComponent,
				new MockPropertyValueSource(null),
				new MockLocalizedStrings());
		fixture = cps;
	}
	
	public void testEditableValueConversion() {
		// the property is a fake compound property comprised
		// of four sides of a square. The editable value
		// is the area. Retrieving it should get the area
		// from the sides, setting it should update the sides
		// to reflect the desired area.
		// This is a silly way to represent a square, but it's just a test
		IPropertySource square = (IPropertySource) fixture.getPropertyValue("square");
		assertNotNull(square);
		fixture.setPropertyValue("square", square);
		
		square.setPropertyValue("leftSide", "10");
		square.setPropertyValue("rightSide", "10");
		square.setPropertyValue("topSide", "10");
		square.setPropertyValue("bottomSide", "10");
		
		Object area = square.getEditableValue();
		assertEquals(Integer.valueOf((100)), area);
		
		fixture.setPropertyValue("square", Integer.valueOf(25));
		square = (IPropertySource) fixture.getPropertyValue("square");
		assertEquals("5", square.getPropertyValue("leftSide").toString());
		assertEquals("5", square.getPropertyValue("rightSide").toString());
		assertEquals("5", square.getPropertyValue("topSide").toString());
		assertEquals("5", square.getPropertyValue("bottomSide").toString());
	}

	public void testEditableValueInitFromEditable() {
		IPropertySource square = (IPropertySource) fixture.getPropertyValue("square");
		assertNotNull(square);
		fixture.setPropertyValue("square", Integer.valueOf(100));
		
		square = (IPropertySource) fixture.getPropertyValue("square");
		assertEquals("10", square.getPropertyValue("leftSide").toString());
		assertEquals("10", square.getPropertyValue("rightSide").toString());
		assertEquals("10", square.getPropertyValue("topSide").toString());
		assertEquals("10", square.getPropertyValue("bottomSide").toString());
	}
}
