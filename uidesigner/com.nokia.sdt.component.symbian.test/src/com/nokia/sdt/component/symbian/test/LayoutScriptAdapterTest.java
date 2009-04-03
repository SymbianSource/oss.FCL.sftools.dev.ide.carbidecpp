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
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.testsupport.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.IPropertySource;

import junit.framework.TestCase;

/**
 * 
 *
 */
public class LayoutScriptAdapterTest extends TestCase {

	static ComponentProvider provider;
	
	private IComponentSet set;

	private IComponent containerComponent;
	private IComponent layoutableComponent;

	private IDesignerDataModel dataModel;
	private IDisplayModel displayModel;

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TestHelpers.setPlugin(PluginTest.getDefault());
        if (provider == null)
            provider = TestDataModelHelpers.findOrCreateProviderForUserComponents("data/display");

		dataModel = TestDataModelHelpers.loadDataModelWithTestComponents("data/display/scriptLayout.nxd", provider); 
		set = dataModel.getComponentSet();
		
		containerComponent = set.lookupComponent("com.nokia.examples.scriptLayoutContainer");
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
	
	public void testLayoutAdapter() {
        IComponentInstance root = AdapterHelpers.findComponentInstance(dataModel, "root");
        IPropertySource rootProperties = 
        	(IPropertySource) EcoreUtil.getRegisteredAdapter(root.getEObject(), IPropertySource.class);
        IPropertySource rootLocation = (IPropertySource) rootProperties.getPropertyValue("location");
        IPropertySource rootSize = (IPropertySource) rootProperties.getPropertyValue("size");

        IComponentInstance child = AdapterHelpers.findComponentInstance(dataModel, "child");
        IPropertySource childProperties = 
        	(IPropertySource) EcoreUtil.getRegisteredAdapter(child.getEObject(), IPropertySource.class);
        IPropertySource childLocation = (IPropertySource) childProperties.getPropertyValue("location");
        IPropertySource childSize = (IPropertySource) childProperties.getPropertyValue("size");
       
        ILayout layout = (ILayout) EcoreUtil.getRegisteredAdapter(root.getEObject(), ILayout.class);
		assertNotNull(layout);

		try {
			displayModel = dataModel.getDisplayModelForRootContainer(dataModel.getRootContainers()[0]);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		layout.layout(displayModel.getLookAndFeel());

		assertEquals(((Integer) childLocation.getPropertyValue("x")).intValue(),
				((Integer) rootLocation.getPropertyValue("x")).intValue());

		assertEquals(((Integer) childLocation.getPropertyValue("y")).intValue(),
				((Integer) rootLocation.getPropertyValue("y")).intValue() + 
				LayoutCodeImplFactoryStub.TEST_OFFSET);
		
		assertEquals(((Integer) childSize.getPropertyValue("width")).intValue(),
				((Integer) rootSize.getPropertyValue("width")).intValue());

		assertEquals(((Integer) childSize.getPropertyValue("height")).intValue(),
				((Integer) rootSize.getPropertyValue("height")).intValue() - 
				LayoutCodeImplFactoryStub.TEST_OFFSET);

		Point p = layout.getPreferredSize(10, 11, null);
		assertEquals(10, p.x);
		assertEquals(11, p.y);
	}
	
}
