/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.testsupport.*;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class ReconcilePropertyScriptAdapterTest extends TestCase {

	static ComponentProvider provider;
	
	private IComponentSet set;

	private IComponent component;

	private IDesignerDataModel dataModel;

	private IComponentInstance root;

	private IPropertySource rootProperties;
	private IPropertySource compoundProperty;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
        if (provider == null)
            provider = TestDataModelHelpers.createProviderForUserComponents("data/reconcilePropertyTest");

		dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/reconcilePropertyTest/reconcileProperty.nxd", provider); 
		set = dataModel.getComponentSet();
		
		component = set.lookupComponent("com.nokia.examples.reconcilePropertyScript");
		assertNotNull(component);
		
        root = AdapterHelpers.findComponentInstance(dataModel, "root");
        rootProperties = (IPropertySource) EcoreUtil.getRegisteredAdapter(root.getEObject(), IPropertySource.class);
		assertNotNull(rootProperties);
        
        compoundProperty = (IPropertySource) rootProperties.getPropertyValue("compound");
        assertNotNull(compoundProperty);
	}
	
	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCreateDisplayValue() {
        compoundProperty.setPropertyValue("first", new Integer(0));
        compoundProperty.setPropertyValue("second", new Integer(0));
        
        assertEquals("Zero", compoundProperty.getEditableValue());
	
        compoundProperty.setPropertyValue("first", new Integer(2));
        compoundProperty.setPropertyValue("second", new Integer(1));
        
        assertEquals("Three", compoundProperty.getEditableValue());
	}
	
	public void testApplyDisplayValue() {
		rootProperties.setPropertyValue("compound", "One");

		assertEquals(new Integer(0), compoundProperty.getPropertyValue("first"));
		assertEquals(new Integer(1), compoundProperty.getPropertyValue("second"));

		rootProperties.setPropertyValue("compound", "Many");

		assertEquals(new Integer(2), compoundProperty.getPropertyValue("first"));
		assertEquals(new Integer(2), compoundProperty.getPropertyValue("second"));
	}
	
}
