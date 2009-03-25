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

import com.nokia.sdt.component.*;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.ComponentSDKSelector;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import org.osgi.framework.Version;

import junit.framework.TestCase;

public class ComponentSDKSelectorTest extends TestCase {
	
	IComponentProvider provider;
	static final String sdkName = "com.nokia.series60";

	protected void setUp() throws Exception {
		super.setUp();
		TestDataModelHelpers.initDefaultComponentProvider();
		ComponentSystem cs = ComponentSystem.getComponentSystem();
		provider = cs.getProvider(ComponentProvider.PROVIDER_ID);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testTooOld() {
		Version version = new Version("0.1");
		ComponentSDKSelector filter = new ComponentSDKSelector(sdkName, version);
		ComponentSetResult csr = provider.queryComponents(filter);
		assertNotNull(csr);
		assertNotNull(csr.getComponentSet());
		assertTrue(csr.getComponentSet().numComponents() == 0);
	}
	
	public void testSDKVersion2() {
		// assumes the installed component set has components
		// for Series60 version 2.0
		Version version = new Version("2.0");
		ComponentSDKSelector filter = new ComponentSDKSelector(sdkName, version);
		ComponentSetResult csr = provider.queryComponents(filter);
		assertNotNull(csr);
		assertNotNull(csr.getComponentSet());
		assertTrue(csr.getComponentSet().numComponents() > 0);
	}

}
