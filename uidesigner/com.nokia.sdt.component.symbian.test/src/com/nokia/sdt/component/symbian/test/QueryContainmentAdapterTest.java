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
import com.nokia.sdt.datamodel.adapter.IQueryContainment;
import com.nokia.sdt.testsupport.*;
import com.nokia.sdt.utils.StatusHolder;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.util.EcoreUtil;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class QueryContainmentAdapterTest extends TestCase {

	static ComponentProvider provider;

	private IComponentSet set;

	private IComponent codeComponent;
	private IComponent scriptComponent;

	private IDesignerDataModel dataModel;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
        if (provider == null)
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/display");

        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/display/containmentAdapterTest.nxd", provider); 

        set = dataModel.getComponentSet();

        codeComponent = set.lookupComponent("com.nokia.examples.codeLayoutContainer");
        assertNotNull(codeComponent);

        scriptComponent = set.lookupComponent("com.nokia.examples.scriptLayoutContainer");
        assertNotNull(scriptComponent);
	}
	
	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCodeQueryContainmentAdapter() {
        IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "codeContainer");
        IComponentInstance scriptInstance = AdapterHelpers.findComponentInstance(dataModel, "scriptContainer");
        IQueryContainment queryContainment = 
        	(IQueryContainment) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IQueryContainment.class);
        assertNotNull(queryContainment);
        boolean shouldContain = queryContainment.canContainComponent(codeComponent, null);
        assertTrue(shouldContain);
        StatusHolder holder = new StatusHolder();
        boolean shouldNotContain = queryContainment.canContainComponent(scriptComponent, holder);
        assertTrue(!shouldNotContain);
        IStatus status = holder.getStatus();
        assertNotNull(status.getMessage());
        boolean shouldRemove = queryContainment.canRemoveChild(instance);
        assertTrue(shouldRemove);
        boolean shouldNotRemove = queryContainment.canRemoveChild(scriptInstance);
        assertTrue(!shouldNotRemove);
	}
	
	public void testScriptQueryContainmentAdapter() {
        IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "scriptContainer");
        IComponentInstance codeInstance = AdapterHelpers.findComponentInstance(dataModel, "codeContainer");
        IQueryContainment queryContainment = 
        	(IQueryContainment) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IQueryContainment.class);
        assertNotNull(queryContainment);
        boolean shouldContain = queryContainment.canContainComponent(scriptComponent, null);
        assertTrue(shouldContain);
        StatusHolder holder = new StatusHolder();
        boolean shouldNotContain = queryContainment.canContainComponent(codeComponent, holder);
        assertTrue(!shouldNotContain);
        IStatus status = holder.getStatus();
        assertNotNull(status.getMessage());
        boolean shouldRemove = queryContainment.canRemoveChild(instance);
        assertTrue(shouldRemove);
        boolean shouldNotRemove = queryContainment.canRemoveChild(codeInstance);
        assertTrue(!shouldNotRemove);
	}
}
