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

import com.nokia.sdt.component.adapter.IInfoItems;
import com.nokia.sdt.component.symbian.*;
import com.nokia.sdt.emf.component.*;
import com.nokia.sdt.testsupport.ComponentHelpers;

import org.osgi.framework.Bundle;

import junit.framework.TestCase;

public class InfoItemsAdapterTest extends TestCase {

	static ComponentProvider provider;
	static ComponentSet componentSet;
	static Component component;

	private final static String testClassName = "CClassName";
	private final static String testClassHelpTopic = "Test Help Topic";
	private final static String testResourceType = "RESOURCENAME";
	private final static String testResourceHelpTopic = "Resource Help Topic";
	private final static int testNumItems = 2;

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
    		emfComponent.setQualifiedName("com.nokia.component.testDocumentation");
    		SymbianType symbianInfo = ComponentFactory.eINSTANCE.createSymbianType();
    		emfComponent.setSymbian(symbianInfo);
    		symbianInfo.setClassHelpTopic(testClassHelpTopic);
    		symbianInfo.setClassName(testClassName);
    		symbianInfo.setResourceType(testResourceType);
    		symbianInfo.setResourceHelpTopic(testResourceHelpTopic);

        }
    }
    
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetNumItems() {
		IInfoItems infos = (IInfoItems) component.getAdapter(IInfoItems.class);
		assertNotNull(infos);
		int numItems = infos.getNumItems(testNumItems);
		assertEquals(testNumItems, numItems);
	}
	
	public void testGetItemTypeLabel() {
		IInfoItems infos = (IInfoItems) component.getAdapter(IInfoItems.class);
		assertNotNull(infos);
		String itemTypeLabel0 = infos.getItemTypeLabel(0);
		assertNotNull(itemTypeLabel0);
		String itemTypeLabel1 = infos.getItemTypeLabel(1);
		assertNotNull(itemTypeLabel1);
	}
	
	public void testGetItemLabel() {
		IInfoItems infos = (IInfoItems) component.getAdapter(IInfoItems.class);
		assertNotNull(infos);
		String itemLabel0 = infos.getItemLabel(0);
		assertEquals(testClassName, itemLabel0);
		String itemLabel1 = infos.getItemLabel(1);
		assertEquals(testResourceType, itemLabel1);
	}

	public void testGetItemHelpTopic() {
		IInfoItems infos = (IInfoItems) component.getAdapter(IInfoItems.class);
		assertNotNull(infos);
		String itemTopic0 = infos.getItemHelpTopic(0);
		assertEquals(testClassHelpTopic, itemTopic0);
		String itemTopic1 = infos.getItemHelpTopic(1);
		assertEquals(testResourceHelpTopic, itemTopic1);
	}

	public void testGetComponent() {
		IInfoItems infos = (IInfoItems) component.getAdapter(IInfoItems.class);
		assertEquals(component, infos.getComponent());
	}

}
