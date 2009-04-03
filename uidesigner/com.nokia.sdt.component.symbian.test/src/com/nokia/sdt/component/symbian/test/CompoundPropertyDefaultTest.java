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

import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.properties.ComponentPropertySource;
import com.nokia.sdt.testsupport.ComponentHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import junit.framework.TestCase;

public class CompoundPropertyDefaultTest extends TestCase {

	IComponentSet componentSet;
	IPropertySource fixture;	

	protected void setUp() throws Exception {
		componentSet = ComponentHelpers.querySDKFilter(
				TestDataModelHelpers.getDefaultComponentProvider(), 
				"com.nokia.series60", "2.0");
		Component testComponent = (Component) componentSet.lookupComponent("com.nokia.test.compoundWithDefault");

		ComponentPropertySource cps = new ComponentPropertySource(
				testComponent,
				new MockPropertyValueSource(null),
				new MockLocalizedStrings());
		fixture = cps;
	}
	
	public void testGetDefault() {
		IPropertySource ps = (IPropertySource) fixture.getPropertyValue("testWithDefault");
		assertNotNull(ps);
		// we're expecting that int1 and string1 are set via the default value and
		// int2 and string2 are not
		assertEquals("111", ps.getPropertyValue("int1").toString());
		assertEquals("0", ps.getPropertyValue("int2").toString());
		assertEquals("magic", ps.getPropertyValue("string1"));
		assertEquals("", ps.getPropertyValue("string2"));
	}

	public void testNoDefault() {
		// verify that when no default value is specified that the values are the default
		// for their data type
		IPropertySource ps = (IPropertySource) fixture.getPropertyValue("testNoDefault");
		assertNotNull(ps);
		// we're expecting that int1 and string1 are set via the default value and
		// int2 and string2 are not
		assertEquals("0", ps.getPropertyValue("int1").toString());
		assertEquals("0", ps.getPropertyValue("int2").toString());
		assertEquals("", ps.getPropertyValue("string1"));
		assertEquals("", ps.getPropertyValue("string2"));
	}
}
