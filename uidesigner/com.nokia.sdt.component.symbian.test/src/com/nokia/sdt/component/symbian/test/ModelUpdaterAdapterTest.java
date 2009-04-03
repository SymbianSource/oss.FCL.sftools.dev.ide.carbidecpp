/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.component.ComponentSetResult;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;

import org.eclipse.emf.ecore.EObject;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class ModelUpdaterAdapterTest extends TestCase {

	static ComponentProvider provider;
	private IComponentSet set;
	private IDesignerDataModel dataModel;
	private EObject root;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
        if (provider == null)
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/modelUpdaterTest");

        ScriptingManager.getInstance().registerClassLoaderFor(PluginTest.getDefault());

        ComponentSetResult result = provider.queryComponents(null);
		set = result.getComponentSet();
		assertNotNull(set);
		
		dataModel = TestDataModelHelpers.loadDataModel("data/modelUpdaterTest/modelUpdaterTest.uidesign");
		dataModel.setComponentSet(set);

        root = dataModel.findByNameProperty("root");
		assertNotNull(root);

	}
	
	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testAdapterCode() {
		doAdapterTest("code");
	}
	
	public void testAdapterScript() {
		doAdapterTest("script");
	}

	private void doAdapterTest(String name) {
		EObject object = dataModel.findByNameProperty(name);
		EObject[] children = ModelUtils.getComponentInstance(object).getChildren();
		assertNotNull(children);
		assertEquals(1, children.length);
	}

}
