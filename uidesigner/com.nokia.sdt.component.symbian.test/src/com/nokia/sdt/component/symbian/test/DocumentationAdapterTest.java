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

import com.nokia.sdt.component.adapter.IDocumentation;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.testsupport.ComponentHelpers;

import org.osgi.framework.Bundle;

import junit.framework.TestCase;

public class DocumentationAdapterTest extends TestCase {

	static ComponentProvider provider;
	static ComponentSet componentSet;
	static Component component;
	
	private final static String testHelpTopic = "Test HelpTopic";
	private final static String testInformation = "Test Information";
	private final static String testWizardDescription = "This should be a pretty long string because it will hold a multi-line description in the wizard. This will often describe various aspects of using this component for a novice user.";

	protected void setUp() throws Exception {
		super.setUp();
        if (provider == null) {
    		provider = new ComponentProvider();
    		provider.inhibitPluginScan();
    		componentSet = ComponentHelpers.queryAllComponents(provider);
    		Bundle bundle = PluginTest.getDefault().getBundle();
    		MockLocalizedStrings strings = new MockLocalizedStrings();		
    		ComponentType emfComponent = ComponentFactory.eINSTANCE.createComponentType();
    		component = new Component(provider, null, bundle, emfComponent, strings);
    		ComponentType ct = component.getEMFComponent();
    		ct.setQualifiedName("com.nokia.component.testDocumentation");
    		DocumentationType documentation = ComponentFactory.eINSTANCE.createDocumentationType();
    		ct.setDocumentation(documentation);
    		documentation.setHelpTopic(testHelpTopic);
    		documentation.setInformation(testInformation);
    		documentation.setWizardDescription(testWizardDescription);
        }
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testDocumentationAdapter() {
		IDocumentation doc = (IDocumentation) component.getAdapter(IDocumentation.class);
		assertNotNull(doc);
	}

	public void testGetHelpTopic() {
		IDocumentation doc = (IDocumentation) component.getAdapter(IDocumentation.class);
		assertEquals(testHelpTopic, doc.getHelpTopic());
	}

	public void testGetInformation() {
		IDocumentation doc = (IDocumentation) component.getAdapter(IDocumentation.class);
		assertEquals(testInformation, doc.getInformation());
	}

	public void testGetWizardDescription() {
		IDocumentation doc = (IDocumentation) component.getAdapter(IDocumentation.class);
		assertEquals(testWizardDescription, doc.getWizardDescription());
	}

	public void testGetComponent() {
		IDocumentation doc = (IDocumentation) component.getAdapter(IDocumentation.class);
		assertEquals(component, doc.getComponent());
	}

}
