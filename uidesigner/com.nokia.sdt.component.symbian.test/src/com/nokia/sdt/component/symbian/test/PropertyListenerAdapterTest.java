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
import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.testsupport.*;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class PropertyListenerAdapterTest extends TestCase {

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

        ScriptingManager.getInstance().registerClassLoaderFor(PluginTest.getDefault());

        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/display/display.nxd", provider); 

        set = dataModel.getComponentSet();

        codeComponent = set.lookupComponent("com.nokia.examples.codeComp");
        assertNotNull(codeComponent);

        scriptComponent = set.lookupComponent("com.nokia.examples.scriptComp");
        assertNotNull(scriptComponent);
	}
	
	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	private void doSetBarPropertyFromListener(String instanceName) {
        IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, instanceName);
		IComponentInstancePropertyListener listener = 
			(IComponentInstancePropertyListener) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IComponentInstancePropertyListener.class);
		assertNotNull(listener);
		IPropertySource properties = (IPropertySource) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IPropertySource.class);
		
		properties.setPropertyValue("bar", "");
		String barValue = (String) properties.getPropertyValue("bar");
		assertEquals("", barValue);

		properties.setPropertyValue("foo", "TEST");
		listener.propertyChanged(instance.getEObject(), "foo");
		
		barValue = (String) properties.getPropertyValue("bar");
		assertEquals("TEST", barValue);
	}
	
	public void testCodePropertyListenerAdapter() {
		doSetBarPropertyFromListener("code1");
	}
	
	public void testScriptPropertyListenerAdapter() {
		doSetBarPropertyFromListener("script1");
	}
}
