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

import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.scripting.ScriptingManager;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import junit.framework.TestCase;

/**
 *
 */
public class ImplementationDelegateTest extends TestCase {

	static ComponentProvider provider;
	private IDesignerDataModel dataModel;


	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
        if (provider == null)
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/delegateTest");

        ScriptingManager.getInstance().registerClassLoaderFor(PluginTest.getDefault());
        
        dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/delegateTest/delegateTest.uidesign", provider); 
        
	}

	public void testGetImplementationFromCodeDelegate() {
		EObject code = dataModel.findByNameProperty("code");
		assertNotNull(code);
		ILayout adapter = (ILayout) EcoreUtil.getRegisteredAdapter(code, ILayout.class);
		assertNotNull(adapter);
		EObject delegate = dataModel.findByNameProperty("delegate");
		assertEquals(delegate, adapter.getEObject());
	}
	
	public void testGetImplementationFromScriptDelegate() {
		EObject script = dataModel.findByNameProperty("script");
		assertNotNull(script);
		ILayout adapter = (ILayout) EcoreUtil.getRegisteredAdapter(script, ILayout.class);
		assertNotNull(adapter);
		EObject delegate = dataModel.findByNameProperty("delegate");
		assertEquals(delegate, adapter.getEObject());
	}
}
