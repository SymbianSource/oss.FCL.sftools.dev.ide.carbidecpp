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
import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Bundle;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

	/**
	 * Some array tests are in com.nokia.sdt.component.symbian.test.ArrayPropertyTest
	 * These are some additional tests to check integration with the real, rather
	 * than mock, data model
	 */
public class ArrayComponentTest extends TestCase {

	static final String COMPONENT = "com.nokia.examples.baseComponent";
	
	IDesignerDataModel model;
	EObject testObject;
	IPropertySource fixture;
	
	protected void setUp() throws Exception {
		
		Bundle bundle = TestsPlugin.getDefault().getBundle();
		Path path = new Path("/data/minimal-model.nxd");
		URL url = Platform.find(bundle, path);
		url = Platform.asLocalURL(url);
		File srcFile = new File(url.getFile());
		model = TestDataModelHelpers.loadDataModel(srcFile);
		
		IComponent testComponent = model.getComponentSet().lookupComponent("com.nokia.test.arrayTestComponent");

		EObject root = model.getRootContainers()[0];
		TestDataModelHelpers.assignUniqueName(model, root);
		EObject child = model.createNewComponentInstance(testComponent);
		Command cmd = model.createAddNewComponentInstanceCommand(root, child, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();
		
		testObject = child;
		fixture = ModelUtils.getPropertySource(testObject);
	}
	
	public void testGetSequencePropertySource() {
		// regression test - ensure the property value returned
		// for an array is an ISequencePropertySource
		IPropertySource ps = AdapterHelpers.getPropertySource(testObject);
		
		Object intArrayValue = ps.getPropertyValue("int-array");
		assertNotNull(intArrayValue);
		assertTrue(intArrayValue instanceof IPropertySource);
		assertTrue(intArrayValue instanceof ISequencePropertySource);
		
		Object stringArrayValue = ps.getPropertyValue("string-array");
		assertNotNull(stringArrayValue);
		assertTrue(stringArrayValue instanceof IPropertySource);
		assertTrue(stringArrayValue instanceof ISequencePropertySource);
		
		Object fooArrayValue = ps.getPropertyValue("string-array");
		assertNotNull(fooArrayValue);
		assertTrue(fooArrayValue instanceof IPropertySource);
		assertTrue(fooArrayValue instanceof ISequencePropertySource);
		
		Object nestedValue = ps.getPropertyValue("nested");
		assertNotNull(nestedValue);
		assertTrue(nestedValue instanceof IPropertySource);
		IPropertySource nestedPS = (IPropertySource) nestedValue;
		Object nestFooArrayValue = nestedPS.getPropertyValue("b");
		assertNotNull(nestFooArrayValue);
		assertTrue(nestFooArrayValue instanceof IPropertySource);
		assertTrue(nestFooArrayValue instanceof ISequencePropertySource);

        Object refsArrayValue = ps.getPropertyValue("ref-array");
        assertNotNull(refsArrayValue);
        assertTrue(refsArrayValue instanceof IPropertySource);
        assertTrue(refsArrayValue instanceof ISequencePropertySource);

	}
	
	public void testFooArray() {
		Object fooArray = fixture.getPropertyValue("foo-array");
		assertNotNull(fooArray);
		assertTrue(fooArray instanceof ISequencePropertySource);
		
		ISequencePropertySource sps = (ISequencePropertySource) fooArray;
		
		IPropertySource value1 = sps.addCompoundProperty(ISequencePropertySource.AT_END);
		value1.setPropertyValue("f1", "99");
		value1.setPropertyValue("f2", "true");
		
		IPropertySource  value2 = sps.addCompoundProperty(ISequencePropertySource.AT_END);
		value2.setPropertyValue("f1", "3");
		assertTrue(sps.size() == 2);
		
		Object v1 = sps.get(0);
		assertTrue(v1 instanceof IPropertySource);
		IPropertySource testV1 = (IPropertySource) v1;
		assertEquals(new Integer(99), testV1.getPropertyValue("f1"));
		assertEquals(Boolean.TRUE, testV1.getPropertyValue("f2"));
		assertNull(testV1.getPropertyValue("fake"));
		
		// access via property source
		Object value = sps.getPropertyValue("0");
		assertNotNull(value);
		assertTrue(value instanceof IPropertySource);
		testV1 = (IPropertySource) value;
		assertEquals(new Integer(99), testV1.getPropertyValue("f1"));
		assertEquals(Boolean.TRUE, testV1.getPropertyValue("f2"));
		assertNull(testV1.getPropertyValue("fake"));
	}
}
