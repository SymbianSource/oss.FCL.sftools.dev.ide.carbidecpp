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
package com.nokia.sdt.uimodel.tests;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Bundle;

import java.io.File;
import java.net.URL;
import java.util.*;

import junit.framework.TestCase;

public class NotificationTest extends TestCase {

	static final String PROJECT_NAME = "notification-tests";
	static final String CHILD_COMPONENT_NAME = "com.nokia.examples.example1";
	static final String NAME_PROPERTY = "name";
	static final String LOCATION_PROPERTY = "location";
	static final String X_PROPERTY = "x";
	static final String Y_PROPERTY = "y";

	IDesignerDataModel model;
	
	protected void setUp() throws Exception {
		super.setUp();
		IProject project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		
		Bundle bundle = TestsPlugin.getDefault().getBundle();
		Path path = new Path("/data/minimal-model.nxd");
		URL url = Platform.find(bundle, path);
		url = Platform.asLocalURL(url);
		
		File srcFile = new File(url.getFile());
		File destFile = new File(projectDir, "minimal-model.nxd");
		FileUtils.copyFile(srcFile, destFile);
	
		File dmFile = new File(projectDir, "minimal-model.nxd");
		
		model = TestDataModelHelpers.loadDataModel(dmFile);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		model.dispose();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}
	
	Object getSingleAffectedObject(Command cmd) {
		Object result = null;
		Collection cmdResult = cmd.getResult();
		assertNotNull(cmdResult);
		assertEquals( 1, cmdResult.size() );
		result = cmdResult.toArray()[0];
		return result;
	}

	IComponentInstance getCI(EObject object) {
		IComponentInstance result = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
				object, IComponentInstance.class);
		return result;
	}
	
	EObject getRootContainer() {
		return model.getRootContainers()[0];
	}

	public void testAddNotification() {
	
		EObject rootContainer = getRootContainer();
		assertNotNull(rootContainer);
		
		TestListener listener = new TestListener();
		getCI(rootContainer).addChildListener(listener);
		getCI(rootContainer).addPropertyListener(listener);
	
		IComponentSet cs = model.getComponentSet();
		IComponent component = cs.lookupComponent(CHILD_COMPONENT_NAME);

		EObject child = model.createNewComponentInstance(component);
		TestDataModelHelpers.assignUniqueName(model, rootContainer);
		Command cmd = model.createAddNewComponentInstanceCommand(rootContainer, child, 
				IDesignerDataModel.AT_END);
		assertNotNull(cmd);
		assertTrue(cmd.canExecute());
		cmd.execute();
		
		assertEquals( 1, listener.childAddedCount );
		assertEquals(getSingleAffectedObject(cmd), listener.lastChildAdded);
	}
	
	public void testRemoveNotification() {
		EObject rootContainer = getRootContainer();
		assertNotNull(rootContainer);
		TestDataModelHelpers.assignUniqueName(model, rootContainer);
		
		IComponentSet cs = model.getComponentSet();
		IComponent component = cs.lookupComponent(CHILD_COMPONENT_NAME);

		EObject child = model.createNewComponentInstance(component);
		Command cmd = model.createAddNewComponentInstanceCommand(rootContainer, child, 
				IDesignerDataModel.AT_END);
		assertNotNull(cmd);
		assertTrue(cmd.canExecute());
		cmd.execute();
		
		TestListener listener = new TestListener();
		getCI(rootContainer).addChildListener(listener);
		getCI(rootContainer).addPropertyListener(listener);
	
		child = (EObject) getSingleAffectedObject(cmd);
		TestDataModelHelpers.assignUniqueName(model, child);
		cmd = model.createRemoveComponentInstancesCommand(Collections.singletonList(child));
		assertTrue(cmd.canExecute());
		cmd.execute();
		
		assertEquals( 1, listener.childRemovedCount );
		assertEquals(child, listener.lastChildRemoved);
	}
	
	public void testReorderNotification() {
		EObject rootContainer = getRootContainer();
		assertNotNull(rootContainer);
		TestDataModelHelpers.assignUniqueName(model, rootContainer);
		
		IComponentSet cs = model.getComponentSet();
		IComponent component = cs.lookupComponent(CHILD_COMPONENT_NAME);

		EObject child0 = model.createNewComponentInstance(component);
		Command cmd = model.createAddNewComponentInstanceCommand(rootContainer, child0, 
				IDesignerDataModel.AT_END);
		assertNotNull(cmd);
		assertTrue(cmd.canExecute());
		cmd.execute();
		TestDataModelHelpers.assignUniqueName(model, child0);
		
		EObject child1 = model.createNewComponentInstance(component);
		cmd = model.createAddNewComponentInstanceCommand(rootContainer, child1, 
				IDesignerDataModel.AT_END);
		assertNotNull(cmd);
		assertTrue(cmd.canExecute());
		cmd.execute();
		TestDataModelHelpers.assignUniqueName(model, child1);
		
		TestListener listener = new TestListener();
		getCI(rootContainer).addChildListener(listener);
		getCI(rootContainer).addPropertyListener(listener);
	
		cmd = model.createMoveComponentInstanceCommand(child1, rootContainer, 0);
		assertNotNull(cmd);
		assertTrue(cmd.canExecute());
		cmd.execute();
		
		assertEquals(1, listener.childrenReorderedCount);
		assertEquals(rootContainer, listener.lastReorderedParent);

		cmd = model.createMoveComponentInstanceCommand(child1, rootContainer, 0);
		assertNotNull(cmd);
		assertTrue(cmd.canExecute());
		cmd.execute();
		
		assertEquals(2, listener.childrenReorderedCount);
		assertEquals(rootContainer, listener.lastReorderedParent);
	}
	
	public void testPropertyChangeFromDefault() {
		
		EObject rootContainer = getRootContainer();
		assertNotNull(rootContainer);
			
		TestListener listener = new TestListener();
		getCI(rootContainer).addChildListener(listener);
		getCI(rootContainer).addPropertyListener(listener);
		
		IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(rootContainer, IPropertySource.class);
		assertNotNull(ps);
		
		Object oldName = ps.getPropertyValue(NAME_PROPERTY);
		ps.setPropertyValue(NAME_PROPERTY, "abcdefg");

		assertEquals(1, listener.propertyChangedCount);
		assertEquals(listener.lastPropertyId, NAME_PROPERTY);
	}
	
	public void testPropertyChangeFromNonDefault() {
		
		EObject rootContainer = getRootContainer();
		assertNotNull(rootContainer);
			
		IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(rootContainer, IPropertySource.class);
		assertNotNull(ps);
		
		// first set from default to something
		Object oldName = ps.getPropertyValue(NAME_PROPERTY);
		ps.setPropertyValue(NAME_PROPERTY, "abcdefg");
		
		// add listener and set again
		TestListener listener = new TestListener();
		getCI(rootContainer).addChildListener(listener);
		getCI(rootContainer).addPropertyListener(listener);
		ps.setPropertyValue(NAME_PROPERTY, "zzzz");	
	
		assertEquals(1, listener.propertyChangedCount);
		assertEquals(listener.lastPropertyId, NAME_PROPERTY);
	}

	public void testCompoundPropertyChangeFromDefault() {
		
		EObject rootContainer = getRootContainer();
		assertNotNull(rootContainer);
			
		TestListener listener = new TestListener();
		getCI(rootContainer).addChildListener(listener);
		getCI(rootContainer).addPropertyListener(listener);
		
		IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(rootContainer, IPropertySource.class);
		assertNotNull(ps);
		
		Object location = ps.getPropertyValue(LOCATION_PROPERTY);
		assertNotNull(location);
	
		assertEquals(0, listener.anyChangeCount);
		assertEquals(0, listener.propertyChangedCount);
			
		assertTrue(location instanceof IPropertySource);
		IPropertySource locationPS = (IPropertySource) location;
		locationPS.setPropertyValue(X_PROPERTY, "100");
		
		assertEquals(1, listener.propertyChangedCount);
		assertEquals(LOCATION_PROPERTY, listener.lastPropertyId);
	}
	
	public void testCompoundPropertyChangeFromNonDefault() {
		
		EObject rootContainer = getRootContainer();
		assertNotNull(rootContainer);
			
		TestListener listener = new TestListener();
		getCI(rootContainer).addChildListener(listener);
		getCI(rootContainer).addPropertyListener(listener);
	
		IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(rootContainer, IPropertySource.class);
		assertNotNull(ps);
		
		Object location = ps.getPropertyValue(LOCATION_PROPERTY);
		assertNotNull(location);
	
		assertEquals(0, listener.anyChangeCount);
		assertEquals(0, listener.propertyChangedCount);
			
		assertTrue(location instanceof IPropertySource);
		IPropertySource locationPS = (IPropertySource) location;
		locationPS.setPropertyValue(X_PROPERTY, "100");

		assertEquals(1, listener.propertyChangedCount);
		assertEquals(LOCATION_PROPERTY, listener.lastPropertyId);
	
		locationPS.setPropertyValue(X_PROPERTY, "200");

		assertEquals(2, listener.propertyChangedCount);
		assertEquals(LOCATION_PROPERTY, listener.lastPropertyId);

		locationPS.setPropertyValue(Y_PROPERTY, "1");

		assertEquals(3, listener.propertyChangedCount);
		assertEquals(LOCATION_PROPERTY, listener.lastPropertyId);

	}
	
	public void testNoFireOnGet_NewModel() {
		// regression test that getting a property does not fire a property changed event
		EObject rootContainer = getRootContainer();
	
		IComponentSet cs = model.getComponentSet();
		IComponent component = cs.lookupComponent(CHILD_COMPONENT_NAME);

		EObject child = model.createNewComponentInstance(component);
		TestDataModelHelpers.assignUniqueName(model, rootContainer);
		Command cmd = model.createAddNewComponentInstanceCommand(rootContainer, child, 
				IDesignerDataModel.AT_END);
		assertTrue(cmd.canExecute());
		cmd.execute();
			
		IComponentInstance childCI = getCI(child);
		assertNotNull(childCI);
		
		TestListener listener = new TestListener();
		childCI.addChildListener(listener);
		childCI.addPropertyListener(listener);
		
		String name = childCI.getName();
		
		assertEquals(0, listener.propertyChangedCount);
	}
	
	public void testArrayNotification() {
		EObject rootContainer = getRootContainer();
		
		IComponentSet cs = model.getComponentSet();
		IComponent component = cs.lookupComponent("com.nokia.test.arrayTestComponent");

		EObject child = model.createNewComponentInstance(component);
		TestDataModelHelpers.assignUniqueName(model, rootContainer);
		Command cmd = model.createAddNewComponentInstanceCommand(rootContainer, child, 
				IDesignerDataModel.AT_END);
		assertTrue(cmd.canExecute());
		cmd.execute();
			
		IComponentInstance childCI = getCI(child);
		assertNotNull(childCI);
		
		TestListener listener = new TestListener();
		childCI.addPropertyListener(listener);
		
		IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(child, IPropertySource.class);
		assertNotNull(ps);
		
		Object pv = ps.getPropertyValue("loc-string-array");
		assertNotNull(pv);
		assertTrue(pv instanceof ISequencePropertySource);
		ISequencePropertySource sps = (ISequencePropertySource) pv;
		
		assertEquals(0, listener.propertyChangedCount);
		
		sps.addSimpleProperty(ISequencePropertySource.AT_END, "test");
		assertEquals(1, listener.propertyChangedCount);
		
		List l = new ArrayList();
		l.add("foo");
		l.add("bar");
		ps.setPropertyValue("loc-string-array", l);
		// number of notifications is not currently 
		// guaranteed. It depends on whether items are localizable
		assertTrue(listener.propertyChangedCount > 1);
	
		pv = ps.getPropertyValue("loc-string-array");
		sps = (ISequencePropertySource) pv;
		assertEquals(2, sps.size());
	}


	class TestListener implements 
		IComponentInstanceChildListener, IComponentInstancePropertyListener {

		
		int anyChangeCount;
		int childAddedCount;
		EObject lastChildAdded;
		
		int childRemovedCount;
		EObject lastChildRemoved;
		
		int childrenReorderedCount;
		EObject lastReorderedParent;

		int propertyChangedCount;
		Object lastPropertyId;
		
		public void childAdded(EObject parent, EObject child) {
			++anyChangeCount;
			++childAddedCount;
			lastChildAdded = child;
		}

		public void childRemoved(EObject parent, EObject child) {
			++anyChangeCount;
			++childRemovedCount;
			lastChildRemoved = child;
		}

		public void childrenReordered(EObject parent) {
			++childrenReorderedCount;
			lastReorderedParent = parent;
		}

		public void propertyChanged(EObject componentInstance, Object propertyId) {
			++anyChangeCount;
			++propertyChangedCount;
			lastPropertyId = propertyId;
		}
		
	}
}
