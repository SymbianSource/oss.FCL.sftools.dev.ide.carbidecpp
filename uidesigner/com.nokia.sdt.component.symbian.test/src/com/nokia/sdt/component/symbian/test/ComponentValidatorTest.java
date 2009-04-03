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

import com.nokia.sdt.component.ComponentSetResult;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentValidator;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import junit.framework.TestCase;

public class ComponentValidatorTest extends TestCase {
	
	EObject fixture;
	
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
		ComponentProvider provider = TestDataModelHelpers.createProviderForUserComponents("data/componentValidatorTest");
		ComponentSetResult result = provider.queryComponents(null);
		assertNotNull(result.getComponentSet());
		
		IDesignerDataModel model = TestDataModelHelpers.createTemporaryModel();
		model.setComponentSet(result.getComponentSet());
	
		Component component = (Component) result.getComponentSet().lookupComponent("com.nokia.test.componentValidator");
		assertNotNull(component);
		
		fixture = model.createRootContainerInstance(component);
		assertNotNull(fixture);
	}

	public void testPropertyEditValidator() {
		IComponentValidator validator = (IComponentValidator) EcoreUtil.getRegisteredAdapter(fixture, IComponentValidator.class);
		assertNotNull(validator);
		
		assertNull(validator.queryPropertyChange(fixture, "always", "a"));
		assertEquals("not allowed", validator.queryPropertyChange(fixture, "never", ""));
		assertNull(validator.queryPropertyChange(fixture, "notnull", "abc"));
		assertEquals("not null", validator.queryPropertyChange(fixture, "notnull", null));
		assertNull(validator.queryPropertyChange(fixture, "compound.oddonly", "3"));
		assertEquals("odd only", validator.queryPropertyChange(fixture, "compound.oddonly", "2"));
	}
}

