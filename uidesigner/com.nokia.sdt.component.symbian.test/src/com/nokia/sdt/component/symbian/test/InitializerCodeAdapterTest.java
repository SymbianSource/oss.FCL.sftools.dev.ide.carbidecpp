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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class InitializerCodeAdapterTest extends TestCase {

	static ComponentProvider provider;
	private IDesignerDataModel dataModel;
	private IComponentSet set;
	private IComponent component;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
        if (provider == null)
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/display");

		dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/display/codeLayout.nxd", provider); 
		set = dataModel.getComponentSet();
		
		component = set.lookupComponent("com.nokia.examples.codeLayoutContainer");
		assertNotNull(component);
	}
	
	public void testInitializerAdapter() {
        IComponentInstance root = AdapterHelpers.findComponentInstance(dataModel, "root");
        assertNotNull(root);
        
        EObject object = dataModel.createNewComponentInstance(component);
        Command command = dataModel.createAddNewComponentInstanceCommand(root.getEObject(), object, 0);
        if (command.canExecute())
        	command.execute();
        
        IPropertySource propertySource = AdapterHelpers.getPropertySource(object);
        assertNotNull(propertySource);
        Object propertyValue = propertySource.getPropertyValue("foo");
        assertTrue(((Integer) propertyValue).intValue() == -1);
	}
	
}
