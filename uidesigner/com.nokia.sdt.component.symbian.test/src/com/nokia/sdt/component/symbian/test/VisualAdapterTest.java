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

import junit.framework.TestCase;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IDirectLabelEdit;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;
import com.nokia.sdt.utils.drawing.GC;
import com.nokia.sdt.utils.drawing.IFont;

/**
 * 
 *
 */
public class VisualAdapterTest extends TestCase {

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
	
	public void testNoRenderAdapter() {
		IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "norender");
		IVisualAppearance render = (IVisualAppearance) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IVisualAppearance.class);
		assertNull(render);
	}
	
	public void testCodeVisualAdapter() {
        IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "code1");
		IVisualAppearance visual = (IVisualAppearance) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IVisualAppearance.class);
		assertNotNull(visual);
		Device device = Display.getDefault();
		Image image = new Image(device, 10, 10);
        GC gc = new GC(device, image);
		visual.draw(gc, null);
		assertEquals(gc.getBackground(), Display.getCurrent().getSystemColor(VisualCodeImplFactoryStub.TEST_COLOR));
		gc.dispose();
		Point p = visual.getPreferredSize(5, 5, null);
		assertEquals(15, p.x);
		assertEquals(25, p.y);
		
		IVisualAppearance visualExtra = (IVisualAppearance) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IVisualAppearance.class);
		assertEquals(visual, visualExtra);
	}
	
	public void testScriptVisualAdapter() {
        IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "script1");
		IVisualAppearance visual = (IVisualAppearance) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IVisualAppearance.class);
		assertNotNull(visual);
		Device device = Display.getDefault();
		Image image = new Image(device, 10, 10);
        GC gc = new GC(device, image);
		visual.draw(gc, null);
		assertEquals(gc.getBackground(), Display.getCurrent().getSystemColor(VisualCodeImplFactoryStub.TEST_COLOR));
		gc.dispose();
		Point p = visual.getPreferredSize(5, 5, null);
		assertEquals(15, p.x);
		assertEquals(25, p.y);
		
		IVisualAppearance visualExtra = (IVisualAppearance) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IVisualAppearance.class);
		assertEquals(visual, visualExtra);
	}
	
	private void testDirectLabelEdit(IComponentInstance instance) {
		IDirectLabelEdit edit = (IDirectLabelEdit) EcoreUtil.getRegisteredAdapter(instance.getEObject(), IDirectLabelEdit.class);
		assertNotNull(edit);
		String[] propertyPaths = edit.getPropertyPaths();
		assertNotNull(propertyPaths);
		assertEquals(1, propertyPaths.length);
		Rectangle labelBounds = edit.getVisualBounds(propertyPaths[0], null);
		assertEquals(0, labelBounds.x);
		assertEquals(0, labelBounds.y);
		assertEquals(10, labelBounds.width);
		assertEquals(10, labelBounds.height);
		IFont labelFont = edit.getLabelFont(propertyPaths[0], null);
		assertEquals(null, labelFont);
	}

	public void testCodeDirectLabelEditData() {
		IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "code1");
		testDirectLabelEdit(instance);
	}
	
	public void testScriptDirectLabelEditData() {
		IComponentInstance instance = AdapterHelpers.findComponentInstance(dataModel, "script1");
		testDirectLabelEdit(instance);
	}
}
