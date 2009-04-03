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

package com.nokia.sdt.emf.dm.tests;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.component.property.ISequencePropertySource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IComponentInstancePropertyListener;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.dm.IComponentManifest;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

/**
 * Test reference property promotion 
 * 
 *
 */
public class ReferencePropertyPromotionTest extends TestCase {

	static final String MAIN_COMPONENT = "com.nokia.test.rptMainComponent";
	static final String SUB_COMPONENT = "com.nokia.test.rptSubComponent";
	static final String SUB_COMPONENT_CONFLICTS_1 = "com.nokia.test.rptSubComponentConflicts1";
	static final String SUB_COMPONENT_CONFLICTS_2 = "com.nokia.test.rptSubComponentConflicts2";
	static final String REFERENCE_PROPERTY = "reference";
	static final String REFERENCE2_PROPERTY = "reference2";
	
	DesignerDataModel model;
	EObject root;
	IComponentManifest manifest;
	
	protected void setUp() throws Exception {
		Logging.alwaysLogToConsole = true;
		TestDataModelHelpers.setupResourceFactory();
		TestDataModelHelpers.setupTestComponents();
		model = (DesignerDataModel) TestDataModelHelpers.createTemporaryModel();
		TestDataModelHelpers.initDefaultComponentSet(model);
		
		IComponent component = model.getComponentSet().lookupComponent(MAIN_COMPONENT);
		root = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(root);
		
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		rootPs.setPropertyValue("name", "root");
		
	}
	
	protected EObject addChild(EObject parent, String componentId, String name) {
		IComponent subComponent = model.getComponentSet().lookupComponent(componentId);
		EObject sub = model.createNewComponentInstance(subComponent);
		Command addCommand = model.createAddNewComponentInstanceCommand(parent, sub, IDesignerDataModel.AT_END);
		assertNotNull(addCommand.canExecute());
		addCommand.execute();
		IPropertySource ps = ModelUtils.getPropertySource(sub);
		ps.setPropertyValue("name", name);
		return sub;
	}

	protected EObject addChild(EObject parent, String name) {
		return addChild(parent, SUB_COMPONENT, name);
	}

	protected void setReference(EObject from, EObject to) {
		IPropertySource toPs = ModelUtils.getPropertySource(to);
		String name = toPs.getPropertyValue("name").toString();
		IPropertySource ps = ModelUtils.getPropertySource(from);
		ps.setPropertyValue(REFERENCE_PROPERTY, name);
	}
	
	/** No reference from root to sub. */
	public void testUnconnected() {
		EObject sub = addChild(root, "child");
		IPropertySource ps = ModelUtils.getPropertySource(root);
		assertNull(ps.getPropertyValue("intValue"));
		assertNull(ps.getPropertyValue("stringValue"));
		assertNull(ps.getPropertyValue("refValue"));
		assertNull(ps.getPropertyValue("locationValue"));
		assertNull(ps.getPropertyValue("arrayValue"));
		
		ps = ModelUtils.getPropertySource(sub);
		assertNotNull(ps.getPropertyValue("intValue"));
		assertNotNull(ps.getPropertyValue("stringValue"));
		assertNotNull(ps.getPropertyValue("refValue"));
		assertNotNull(ps.getPropertyValue("locationValue"));
		assertNotNull(ps.getPropertyValue("arrayValue"));
	}

	/** Adding a reference from root to sub. */
	public void testConnected() {
		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");

		IPropertySource ps = ModelUtils.getPropertySource(sub);
		assertNotNull(ps.getPropertyValue("intValue"));
		assertNotNull(ps.getPropertyValue("stringValue"));
		assertNotNull(ps.getPropertyValue("refValue"));
		assertNotNull(ps.getPropertyValue("locationValue"));
		assertNotNull(ps.getPropertyValue("arrayValue"));

		assertNotNull(rootPs.getPropertyValue("intValue"));
		assertNotNull(rootPs.getPropertyValue("stringValue"));
		assertNotNull(rootPs.getPropertyValue("refValue"));
		assertNotNull(rootPs.getPropertyValue("locationValue"));
		assertNotNull(rootPs.getPropertyValue("arrayValue"));

		assertEquals("child", rootPs.getPropertyValue(REFERENCE_PROPERTY));

	}

	/** Test that the values are properly promoted */
	public void testPromotingPropertyValues() {
		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		assertFalse(rootPs.isPropertySet("intValue"));
		assertFalse(rootPs.isPropertySet("stringValue"));
		assertFalse(rootPs.isPropertySet("refValue"));
		assertFalse(rootPs.isPropertySet("locationValue"));
		assertFalse(rootPs.isPropertySet("arrayValue"));
		
		assertFalse(subPs.isPropertySet("intValue"));
		assertFalse(subPs.isPropertySet("stringValue"));
		assertFalse(subPs.isPropertySet("refValue"));
		assertFalse(subPs.isPropertySet("locationValue"));
		assertFalse(subPs.isPropertySet("arrayValue"));

		// verify info
		IPropertyInformation info = (IPropertyInformation) rootPs;
		assertEquals(root, info.getPropertyOwner("name"));
		assertEquals(root, info.getPropertyOwner(REFERENCE_PROPERTY));
		assertNull(info.getPropertyOwner("intValue"));
		assertNull(info.getPropertyOwner("stringValue"));

		info = (IPropertyInformation) subPs;
		assertEquals(sub, info.getPropertyOwner("name"));
		assertNull(info.getPropertyOwner(REFERENCE_PROPERTY));
		assertEquals(sub, info.getPropertyOwner("intValue"));
		assertEquals(sub, info.getPropertyOwner("stringValue"));

		subPs.setPropertyValue("intValue", 23);
		subPs.setPropertyValue("stringValue", "flargle");
		subPs.setPropertyValue("refValue", "child"); // self-ref... so what?
		IPropertySource compound = (IPropertySource) subPs.getPropertyValue("locationValue"); 
		compound.setPropertyValue("x", 1);
		compound.setPropertyValue("y", 2);
		ISequencePropertySource seq = (ISequencePropertySource) subPs.getPropertyValue("arrayValue");
		seq.addSimpleProperty(0, true);
		seq.addSimpleProperty(1, false);
		assertEquals("true", seq.get(0));
		assertEquals("false", seq.get(1));
		
		
		// ensure they're promoted
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");
		assertEquals("child", rootPs.getPropertyValue(REFERENCE_PROPERTY));
		
		assertTrue(rootPs.isPropertySet("intValue"));
		assertTrue(rootPs.isPropertySet("stringValue"));
		assertTrue(rootPs.isPropertySet("refValue"));
		assertTrue(rootPs.isPropertySet("locationValue"));
		assertTrue(rootPs.isPropertySet("arrayValue"));
		
		assertTrue(subPs.isPropertySet("intValue"));
		assertTrue(subPs.isPropertySet("stringValue"));
		assertTrue(subPs.isPropertySet("refValue"));
		assertTrue(subPs.isPropertySet("locationValue"));
		assertTrue(subPs.isPropertySet("arrayValue"));

		// verify info
		info = (IPropertyInformation) rootPs;
		assertEquals(root, info.getPropertyOwner("name"));
		assertEquals(root, info.getPropertyOwner(REFERENCE_PROPERTY));
		assertEquals(sub, info.getPropertyOwner("intValue"));
		assertEquals(sub, info.getPropertyOwner("stringValue"));
		
		info = (IPropertyInformation) subPs;
		assertEquals(sub, info.getPropertyOwner("name"));
		assertNull(info.getPropertyOwner(REFERENCE_PROPERTY));
		assertEquals(sub, info.getPropertyOwner("intValue"));
		assertEquals(sub, info.getPropertyOwner("stringValue"));

		assertEquals(23, rootPs.getPropertyValue("intValue"));
		assertEquals("flargle", rootPs.getPropertyValue("stringValue"));
		assertEquals("child", rootPs.getPropertyValue("refValue"));
		IPropertySource rootCompound = (IPropertySource) rootPs.getPropertyValue("locationValue"); 
		assertEquals(1, rootCompound.getPropertyValue("x"));
		assertEquals(2, rootCompound.getPropertyValue("y"));
		ISequencePropertySource rootArray = (ISequencePropertySource) rootPs.getPropertyValue("arrayValue");
		assertEquals(2, rootArray.size());
		assertEquals("true", rootArray.get(0));
		assertEquals("false", rootArray.get(1));
	}
	

	/** Test that properties from the reference do not alias properties of the instance */
	public void testAliasedPropertyValues() {
		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		assertEquals("root", rootPs.getPropertyValue("name"));
		
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");

		assertEquals("root", rootPs.getPropertyValue("name"));

		rootPs.setPropertyValue("name", "rootie");
		assertEquals("rootie", rootPs.getPropertyValue("name"));
		assertEquals("child", subPs.getPropertyValue("name"));
	}

	/** Test that we track promoted values properly */
	public void testChangingPromotingPropertyValues() {
		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		subPs.setPropertyValue("intValue", 23);
		
		// ensure they're promoted
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");
		
		assertTrue(rootPs.isPropertySet("intValue"));

		assertEquals(23, rootPs.getPropertyValue("intValue"));
		
		////
		
		subPs.setPropertyValue("stringValue", "flargle");
		
		assertEquals("flargle", rootPs.getPropertyValue("stringValue"));
		
		///
		
		rootPs.resetPropertyValue(REFERENCE_PROPERTY);
		
		assertFalse(rootPs.isPropertySet("intValue"));
		assertFalse(rootPs.isPropertySet("stringValue"));
		
		///
		
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");
		
		assertEquals("flargle", rootPs.getPropertyValue("stringValue"));
		assertEquals(23, rootPs.getPropertyValue("intValue"));
		
	}
	
	/** Test that a change to the referenced instance (but not its name) is detected */
	public void testChangingReferencedInstance() {
		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");
		
		subPs.setPropertyValue("intValue", 23);
		subPs.setPropertyValue("stringValue", "flargle");
		IPropertySource compound = (IPropertySource) subPs.getPropertyValue("locationValue"); 
		compound.setPropertyValue("x", 1);
		compound.setPropertyValue("y", 2);
		
		// sanity
		assertTrue(rootPs.isPropertySet("intValue"));
		assertEquals(23, rootPs.getPropertyValue("intValue"));
		IPropertySource rootCompound = (IPropertySource) rootPs.getPropertyValue("locationValue"); 
		assertEquals(1, rootCompound.getPropertyValue("x"));
		assertEquals(2, rootCompound.getPropertyValue("y"));
		
		////

		// replace the child
		
		List objectsToRemove = Collections.singletonList(sub);
		Command command = model.createRemoveComponentInstancesCommand(objectsToRemove);
		assertTrue(command.canExecute());
		command.execute();
		
		assertFalse(rootPs.isPropertySet("reference"));
		
		
		sub = addChild(root, "child");

		subPs = ModelUtils.getPropertySource(sub);

		// sanity
		assertFalse(subPs.isPropertySet("intValue"));
		assertFalse(subPs.isPropertySet("stringValue"));
		assertFalse(subPs.isPropertySet("refValue"));
		assertFalse(subPs.isPropertySet("locationValue"));
		assertFalse(subPs.isPropertySet("arrayValue"));

		// check root again
		assertFalse(rootPs.isPropertySet("intValue"));
		assertFalse(rootPs.isPropertySet("stringValue"));
		assertFalse(rootPs.isPropertySet("refValue"));
		assertFalse(rootPs.isPropertySet("locationValue"));
		assertFalse(rootPs.isPropertySet("arrayValue"));

	}

	/** Test that properties are not promoted multiple times  */
	public void testConflictingPropertyPromotion1() {
		// this one aliases 'another'
		EObject sub = addChild(root, SUB_COMPONENT_CONFLICTS_1, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);

		rootPs.setPropertyValue("another", "string");
		subPs.setPropertyValue("another", 42);
		
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");
		
		assertEquals("string", rootPs.getPropertyValue("another"));
		assertEquals(42, subPs.getPropertyValue("another"));
		
		rootPs.setPropertyValue(REFERENCE_PROPERTY, null);
		
		assertEquals("string", rootPs.getPropertyValue("another"));

	}

	/** Test that properties are not promoted multiple times  */
	public void testConflictingPropertyPromotion2() {
		// this one aliases 'another'
		EObject sub1 = addChild(root, SUB_COMPONENT_CONFLICTS_2, "child1");
		EObject sub2 = addChild(root, SUB_COMPONENT_CONFLICTS_2, "child2");
		
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource sub1Ps = ModelUtils.getPropertySource(sub1);
		IPropertySource sub2Ps = ModelUtils.getPropertySource(sub2);

		sub1Ps.setPropertyValue("intValue", 4);
		sub2Ps.setPropertyValue("intValue", 5);
		
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child1");
		rootPs.setPropertyValue(REFERENCE2_PROPERTY, "child2");
		
		// we can't be sure what order they're added
		int val = (Integer) rootPs.getPropertyValue("intValue");
		assertTrue(val == 4 || val == 5);
		boolean got4 = (val == 4);
		
		rootPs.setPropertyValue(got4 ? REFERENCE_PROPERTY : REFERENCE2_PROPERTY, null);

		assertEquals(got4 ? 5 : 4, rootPs.getPropertyValue("intValue"));
		
		rootPs.setPropertyValue(got4 ? REFERENCE2_PROPERTY : REFERENCE_PROPERTY, null);
		
		assertNull(rootPs.getPropertyValue("intValue"));

	}

	/** Test that we handle when a reference property's name changes */
	public void testRenamedReferences() {
		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");
		
		subPs.setPropertyValue("intValue", 23);
		assertEquals(23, rootPs.getPropertyValue("intValue"));

		subPs.setPropertyValue("name", "foo");
		subPs.setPropertyValue("intValue", 42);
		
		assertEquals(42, rootPs.getPropertyValue("intValue"));
		
		
	}
	
	/** test that promoted references inside compound properties works */
	public void testPromotionInCompoundProperty() {
		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		IPropertySource rootCompound = (IPropertySource) rootPs.getPropertyValue("refCompound");
		rootCompound.setPropertyValue(REFERENCE_PROPERTY, "child");
		
		subPs.setPropertyValue("intValue", 23);
		
		// ensure not promoted to root
		assertNull(rootPs.getPropertyValue("intValue"));
		
		assertEquals(23, rootCompound.getPropertyValue("intValue"));
		assertEquals("child", rootCompound.getPropertyValue(REFERENCE_PROPERTY));

		subPs.setPropertyValue("intValue", 23);
		assertEquals(23, rootCompound.getPropertyValue("intValue"));
	}
	
	/** Test sanity if reference is broken */
	public void testBadReference() {
		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "foo");
		
		subPs.setPropertyValue("intValue", 23);
		assertNull(rootPs.getPropertyValue("intValue"));
	}
	
	/** Test sanity for recursive reference */
	public void testRecursiveReference() {
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "root");
		
		rootPs.setPropertyValue("another", "string");
		assertEquals("string", rootPs.getPropertyValue("another"));
	}
	
	class PropertyChangeListener implements IComponentInstancePropertyListener {
		public EObject firedInstance;
		public String firedProperty;

		public void propertyChanged(EObject componentInstance,
				Object propertyId) {
			firedInstance = componentInstance;
			firedProperty = propertyId.toString();
		}

		public void reset() {
			firedInstance = null;
			firedProperty = null;
		}
		
	}
	/** Test that property listeners get fired on both the referenced instance
	 * and the referencing instance */
	public void testPropertyChangeListeners() {

		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		
		IComponentInstance rootInstance = ModelUtils.getComponentInstance(root);
		PropertyChangeListener rootListener = new PropertyChangeListener();
		rootInstance.addPropertyListener(rootListener);
		IComponentInstance subInstance = ModelUtils.getComponentInstance(sub);
		PropertyChangeListener subListener = new PropertyChangeListener();
		subInstance.addPropertyListener(subListener);
		
		// wire them up
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");
		
		// sanity for listeners
		assertEquals(root, rootListener.firedInstance);
		assertEquals(REFERENCE_PROPERTY, rootListener.firedProperty);
		assertNull(subListener.firedInstance);
		assertNull(subListener.firedProperty);
		
		rootListener.reset();
		
		// set on the root, and sub should know
		rootPs.setPropertyValue("intValue", 23);
		assertEquals(root, rootListener.firedInstance);
		assertEquals(sub, subListener.firedInstance);
		assertEquals("intValue", rootListener.firedProperty);
		assertEquals("intValue", subListener.firedProperty);
		rootListener.reset();
		subListener.reset();
		
		// set on sub, and root should know
		subPs.setPropertyValue("intValue", 23);
		assertEquals(root, rootListener.firedInstance);
		assertEquals(sub, subListener.firedInstance);
		assertEquals("intValue", rootListener.firedProperty);
		assertEquals("intValue", subListener.firedProperty);
		
		// remove reference and make sure notifications stop
		rootPs.setPropertyValue(REFERENCE_PROPERTY, null);

		rootListener.reset();
		subListener.reset();

		subPs.setPropertyValue("intValue", 0);
		assertNull(rootListener.firedInstance);
		assertNull(rootListener.firedProperty);
		assertEquals(sub, subListener.firedInstance);
		assertEquals("intValue", subListener.firedProperty);


		// add again
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");

		rootListener.reset();
		subListener.reset();

		// now delete the referent, which implicitly resets the reference
		List objectsToRemove = Collections.singletonList(sub);
		Command command = model.createRemoveComponentInstancesCommand(objectsToRemove);
		assertTrue(command.canExecute());
		command.execute();
		
		assertFalse(rootPs.isPropertySet("reference"));

		assertEquals(REFERENCE_PROPERTY, rootListener.firedProperty);
		rootListener.reset();
	}

	/** Test that property listeners get fired on both the referenced instance
	 * and the referencing instance, when the name property changes */
	public void testPropertyChangeListeners2() {

		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		IComponentInstance rootInstance = ModelUtils.getComponentInstance(root);
		PropertyChangeListener rootListener = new PropertyChangeListener();
		rootInstance.addPropertyListener(rootListener);
		IComponentInstance subInstance = ModelUtils.getComponentInstance(sub);
		PropertyChangeListener subListener = new PropertyChangeListener();
		subInstance.addPropertyListener(subListener);
		
		// wire them up
		rootPs.setPropertyValue(REFERENCE_PROPERTY, "child");
		
		rootListener.reset();
		
		// set on the root, and sub should know
		rootPs.setPropertyValue("intValue", 23);
		assertEquals(root, rootListener.firedInstance);
		assertEquals(sub, subListener.firedInstance);

		// rename child
		subPs.setPropertyValue("name", "child_renamed");

		// sanity
		assertEquals("child_renamed", rootPs.getPropertyValue(REFERENCE_PROPERTY));

		rootListener.reset();
		subListener.reset();

		// set on the root, and sub should know
		rootPs.setPropertyValue("intValue", 23);
		assertEquals(root, rootListener.firedInstance);
		assertEquals(sub, subListener.firedInstance);
	}

	/** Test that property listeners get fired on all references */
	public void testPropertyChangeListeners3() {

		String promotedName = "child";
		EObject sub = addChild(root, promotedName);
		EObject sub2 = addChild(root, MAIN_COMPONENT, "child2");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		IPropertySource sub2Ps = ModelUtils.getPropertySource(sub2);
		
		
		IComponentInstance rootInstance = ModelUtils.getComponentInstance(root);
		PropertyChangeListener rootListener = new PropertyChangeListener();
		rootInstance.addPropertyListener(rootListener);
		IComponentInstance subInstance = ModelUtils.getComponentInstance(sub);
		PropertyChangeListener subListener = new PropertyChangeListener();
		subInstance.addPropertyListener(subListener);
		IComponentInstance sub2Instance = ModelUtils.getComponentInstance(sub2);
		PropertyChangeListener sub2Listener = new PropertyChangeListener();
		sub2Instance.addPropertyListener(sub2Listener);
		
		// wire them up
		rootPs.setPropertyValue(REFERENCE_PROPERTY, promotedName);
		sub2Ps.setPropertyValue(REFERENCE_PROPERTY, promotedName);
		
		// sanity for listeners
		assertEquals(root, rootListener.firedInstance);
		assertEquals(REFERENCE_PROPERTY, rootListener.firedProperty);
		assertEquals(sub2, sub2Listener.firedInstance);
		assertEquals(REFERENCE_PROPERTY, sub2Listener.firedProperty);
		assertNull(subListener.firedInstance);
		assertNull(subListener.firedProperty);
		
		rootListener.reset();
		sub2Listener.reset();
		
		// set on the root, and sub and child2 should know
		rootPs.setPropertyValue("intValue", 23);
		assertEquals(root, rootListener.firedInstance);
		assertEquals(sub, subListener.firedInstance);
		assertEquals(sub2, sub2Listener.firedInstance);
		assertEquals("intValue", rootListener.firedProperty);
		assertEquals("intValue", subListener.firedProperty);
		assertEquals("intValue", sub2Listener.firedProperty);
		rootListener.reset();
		subListener.reset();
		sub2Listener.reset();
		
		// set on sub, and root and child2 should know
		subPs.setPropertyValue("intValue", 23);
		assertEquals(root, rootListener.firedInstance);
		assertEquals(sub, subListener.firedInstance);
		assertEquals(sub2, sub2Listener.firedInstance);
		assertEquals("intValue", rootListener.firedProperty);
		assertEquals("intValue", subListener.firedProperty);
		assertEquals("intValue", sub2Listener.firedProperty);
		
		// remove reference and make sure only one notification stops
		rootPs.setPropertyValue(REFERENCE_PROPERTY, null);

		rootListener.reset();
		subListener.reset();
		sub2Listener.reset();

		subPs.setPropertyValue("intValue", 0);
		assertNull(rootListener.firedInstance);
		assertNull(rootListener.firedProperty);
		assertEquals(sub, subListener.firedInstance);
		assertEquals("intValue", subListener.firedProperty);
		assertEquals(sub2, sub2Listener.firedInstance);
		assertEquals("intValue", sub2Listener.firedProperty);


		// and the other
		sub2Ps.setPropertyValue(REFERENCE_PROPERTY, null);

		rootListener.reset();
		subListener.reset();
		sub2Listener.reset();

		subPs.setPropertyValue("intValue", 123);
		assertNull(rootListener.firedInstance);
		assertNull(rootListener.firedProperty);
		assertEquals(sub, subListener.firedInstance);
		assertEquals("intValue", subListener.firedProperty);
		assertNull(sub2Listener.firedInstance);
		assertNull(sub2Listener.firedProperty);
		
		// and restore one
		rootPs.setPropertyValue(REFERENCE_PROPERTY, promotedName);

		subPs.setPropertyValue("intValue", 0);
		assertNull(sub2Listener.firedInstance);
		assertNull(sub2Listener.firedProperty);
		assertEquals(sub, subListener.firedInstance);
		assertEquals("intValue", subListener.firedProperty);
		assertEquals(root, rootListener.firedInstance);
		assertEquals("intValue", rootListener.firedProperty);

		rootListener.reset();
		subListener.reset();
		sub2Listener.reset();

		// restore the other
		sub2Ps.setPropertyValue(REFERENCE_PROPERTY, promotedName);

		// now delete the referent, which implicitly resets the references
		List objectsToRemove = Collections.singletonList(sub);
		Command command = model.createRemoveComponentInstancesCommand(objectsToRemove);
		assertTrue(command.canExecute());
		command.execute();
		
		assertFalse(rootPs.isPropertySet("reference"));
		assertFalse(sub2Ps.isPropertySet("reference"));

		assertEquals(REFERENCE_PROPERTY, rootListener.firedProperty);
		rootListener.reset();
	}

	/** Ensure that a normal reference property doesn't invoke
	 * property change events
	 */
	public void testNoListeningForNonPromotedProperties() {
		EObject sub = addChild(root, "child");
		IPropertySource rootPs = ModelUtils.getPropertySource(root);
		IPropertySource subPs = ModelUtils.getPropertySource(sub);
		
		IComponentInstance rootInstance = ModelUtils.getComponentInstance(root);
		PropertyChangeListener rootListener = new PropertyChangeListener();
		rootInstance.addPropertyListener(rootListener);
		IComponentInstance subInstance = ModelUtils.getComponentInstance(sub);
		PropertyChangeListener subListener = new PropertyChangeListener();
		subInstance.addPropertyListener(subListener);
		
		// wire them up
		rootPs.setPropertyValue("plainReference", "child");
		
		rootListener.reset();
		
		// set on the root, and no one else should know
		rootPs.setPropertyValue("another", "lala");
		assertEquals(root, rootListener.firedInstance);
		assertEquals("another", rootListener.firedProperty);
		assertNull(subListener.firedInstance);
		assertNull(subListener.firedProperty);
		rootListener.reset();
		subListener.reset();

		// set on the child, and no one else should know
		subPs.setPropertyValue("stringValue", "lala");
		assertEquals(sub, subListener.firedInstance);
		assertEquals("stringValue", subListener.firedProperty);
		assertNull(rootListener.firedInstance);
		assertNull(rootListener.firedProperty);
		rootListener.reset();
		subListener.reset();

				
	}
}
