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

import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.customizer.IComponentCustomizerCommandFactory;
import com.nokia.sdt.component.customizer.ICustomizeComponentCommand;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentCustomizerUI;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.testsupport.*;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class CustomizerUICodeAdapterTest extends TestCase {

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
	
	public void testCustomizerUIAdapter() {
        IComponentInstance root = AdapterHelpers.findComponentInstance(dataModel, "root");
        IComponentCustomizerUI customizerUI = 
        	(IComponentCustomizerUI) EcoreUtil.getRegisteredAdapter(root.getEObject(), IComponentCustomizerUI.class);
       
		assertNotNull(customizerUI);

		Composite parent = WorkbenchUtils.getSafeShell();
		Composite composite = customizerUI.getCustomizerComposite(parent);
		assertNotNull(composite);
		IComponentCustomizerCommandFactory factory = customizerUI.getCommandFactory();
		assertNotNull(factory);
		ICustomizeComponentCommand command = factory.createCustomizeComponentCommand(root.getEObject());
		assertNotNull(command);
		assertTrue(command.canExecute());
		assertEquals(TestCustomizerCommand.EXECUTED, command.execute());
		assertEquals(TestCustomizerCommand.UNDONE, command.undo());
		assertEquals(TestCustomizerCommand.REDONE, command.redo());
	}
	
}
