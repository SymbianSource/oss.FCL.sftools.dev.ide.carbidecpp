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
import com.nokia.sdt.datamodel.adapter.ISetValueCommandExtender;
import com.nokia.sdt.testsupport.*;
import com.nokia.sdt.uidesigner.ui.command.SetValueCommand;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.views.properties.IPropertySource;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class SetValueCommandExtenderAdapterTest extends TestCase {

	static ComponentProvider provider;
	
	private IComponentSet set;

	private IComponent containerComponent;
	private IComponent layoutableComponent;

	private IDesignerDataModel dataModel;

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
		
		containerComponent = set.lookupComponent("com.nokia.examples.codeLayoutContainer");
		assertNotNull(containerComponent);
		
		layoutableComponent = set.lookupComponent("com.nokia.examples.layoutable");
		assertNotNull(layoutableComponent);
	}
	
	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	private static void assertHasNoChildren(IComponentInstance instance) {
		EObject[] children = instance.getChildren();
		assertTrue((children == null) || (children.length == 0));
	}
	
	private static void assertHasChildren(IComponentInstance instance) {
		EObject[] children = instance.getChildren();
		assertTrue((children != null) && (children.length > 0));
	}
	
	private static void assertNewFooValue(IPropertySource properties) {
		Integer value = (Integer) properties.getPropertyValue("foo");
		assertTrue(value.intValue() == 1);
	}
	
	private static void assertDefaultFooValue(IPropertySource properties) {
		Integer value = (Integer) properties.getPropertyValue("foo");
		assertTrue(value.intValue() == 0);
	}
	
	public void testSetPropertyExtensionAdapter() {
        IComponentInstance root = AdapterHelpers.findComponentInstance(dataModel, "root");
        assertHasChildren(root);
		
        ISetValueCommandExtender commandExtender = 
        	(ISetValueCommandExtender) EcoreUtil.getRegisteredAdapter(root.getEObject(), ISetValueCommandExtender.class);
        assertNotNull(commandExtender);
       
        IPropertySource propertySource = AdapterHelpers.getPropertySource(root.getEObject());
        assertNotNull(propertySource);
        assertDefaultFooValue(propertySource);

        SetValueCommand setValueCommand = new SetValueCommand();
		setValueCommand.setPropertyId("foo");
		setValueCommand.setTarget(propertySource);
		setValueCommand.setPropertyValue(new Integer(1));
		
		Command extendedCommand = 
			commandExtender.getExtendedCommand("foo", setValueCommand.getPropertyValue(), setValueCommand);
		if (extendedCommand.canExecute())
			extendedCommand.execute();
		
		assertHasNoChildren(root);
		assertNewFooValue(propertySource);
        
        extendedCommand.undo();
        
        assertHasChildren(root);
        assertDefaultFooValue(propertySource);
        
        extendedCommand.redo();
        
		assertHasNoChildren(root);
		assertNewFooValue(propertySource);
	}
	
}
