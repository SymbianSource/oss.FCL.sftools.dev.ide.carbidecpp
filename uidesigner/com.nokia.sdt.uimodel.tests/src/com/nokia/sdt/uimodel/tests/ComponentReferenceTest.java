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
import com.nokia.sdt.component.property.ISequencePropertyValue;
import com.nokia.sdt.component.symbian.properties.AbstractPropertySource;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.emf.dm.impl.SequencePropertyValueSource;
import com.nokia.sdt.testsupport.FileHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.sdt.testsupport.TestHelpers;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.views.properties.IPropertySource;

import java.io.File;
import java.util.Collections;

import junit.framework.TestCase;

public class ComponentReferenceTest extends TestCase {
	
	static final String CONTAINER_COMPONENT = "com.nokia.examples.container";
	static final String REFTEST_COMPONENT = "com.nokia.test.componentReferenceTest";
	static final String CONSTRAINED_PROPERTY = "constrained-reference";
	static final String UNCONSTRAINED_PROPERTY="unconstrained-reference";
	static final String CHILDREF_PROPERTY = "childRef";
	static final String SIBLINGREF_PROPERTY = "siblingRef";
	static final String BASE_COMPONENT = "com.nokia.examples.baseComponent";
	static final String RENDER_COMPONENT="com.nokia.examples.renderTest";
	static final String LIST_COMPONENT="com.nokia.test.componentReferenceListTest";
	
	
	IProject project;
	IDesignerDataModel model;
	EObject container, baseChild, renderChild, reftestChild, reflisttestChild;
	IPropertySource containerPS, basePS, renderPS, reftestPS, reflisttestPS;
	
	protected void setUp() throws Exception {
		TestDataModelHelpers.setupResourceFactory();
		TestDataModelHelpers.setupTestComponents();
		
		TestHelpers.setPlugin(TestsPlugin.getDefault());
		File srcFile = FileHelpers.projectRelativeFile("data/minimal-model.nxd");
		
		model = TestDataModelHelpers.loadDataModel(srcFile);
		assertNotNull(model);
		
		EObject root = model.getRootContainers()[0];
		TestDataModelHelpers.assignUniqueName(model, root);
		
		IComponentSet cs = model.getComponentSet();
		
		IComponent containerComponent = cs.lookupComponent(CONTAINER_COMPONENT);
		container = model.createNewComponentInstance(containerComponent);
		Command cmd = model.createAddNewComponentInstanceCommand(root, container, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();
		TestDataModelHelpers.assignUniqueName(model, container);
		
		IComponent baseComponent = cs.lookupComponent(BASE_COMPONENT);
		baseChild = model.createNewComponentInstance(baseComponent);
		cmd = model.createAddNewComponentInstanceCommand(container, baseChild, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();
		TestDataModelHelpers.assignUniqueName(model, baseChild);
	
		IComponent renderComponent = cs.lookupComponent(RENDER_COMPONENT);
		renderChild = model.createNewComponentInstance(renderComponent);
		cmd = model.createAddNewComponentInstanceCommand(container, renderChild, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();
		TestDataModelHelpers.assignUniqueName(model, renderChild);

		IComponent refComponent = cs.lookupComponent(REFTEST_COMPONENT);
		reftestChild = model.createNewComponentInstance(refComponent);
		cmd = model.createAddNewComponentInstanceCommand(container, reftestChild, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();

		IComponent reflistComponent = cs.lookupComponent(LIST_COMPONENT);
		reflisttestChild = model.createNewComponentInstance(reflistComponent);
		cmd = model.createAddNewComponentInstanceCommand(container, reflisttestChild, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();
		
		containerPS = getPS(container);
		basePS = getPS(baseChild);
		renderPS = getPS(renderChild);
		reftestPS = getPS(reftestChild);
		reflisttestPS = getPS(reflisttestChild);
	}

	protected void tearDown() throws Exception {
		model.dispose();
	}
	
	IComponentInstance getCI(EObject object) {
		IComponentInstance result = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
				object, IComponentInstance.class);
		return result;
	}
	
	IPropertySource getPS(EObject object) {
		IPropertySource result = (IPropertySource) EcoreUtil.getRegisteredAdapter(object, IPropertySource.class);
		return result;
	}
	
	String name(IPropertySource ps) {
		return (String) ps.getPropertyValue("name");
	}
	
	public void testUnconstrainedSet() {
		reftestPS.setPropertyValue(UNCONSTRAINED_PROPERTY, name(basePS));
		assertEquals(name(basePS), reftestPS.getPropertyValue(UNCONSTRAINED_PROPERTY));
	}
	
	public void testConstrainedSet() {
		reftestPS.setPropertyValue(CONSTRAINED_PROPERTY, name(renderPS));
		assertEquals(name(renderPS), reftestPS.getPropertyValue(CONSTRAINED_PROPERTY));
	
		try {
			// does not meet constraint
			reftestPS.setPropertyValue(CONSTRAINED_PROPERTY, name(basePS));
			fail();
		}
		catch (Exception x) {
		}
		// should be unchanged by failed set
		assertEquals(name(renderPS), reftestPS.getPropertyValue(CONSTRAINED_PROPERTY));
	}
	
	public void testRemoveReference() {
		reftestPS.setPropertyValue(CONSTRAINED_PROPERTY, name(renderPS));
		assertEquals(name(renderPS), reftestPS.getPropertyValue(CONSTRAINED_PROPERTY));		
	
		// remove render instance, reference property on reftest should be automatically cleared
		Command cmd = model.createRemoveComponentInstancesCommand(Collections.singletonList(renderChild));
		assertTrue(cmd.canExecute());
		cmd.execute();
		
		assertEquals("", reftestPS.getPropertyValue(CONSTRAINED_PROPERTY));
	}
	
	public void testRemoveReferenceFromSequence() {
		// remove component from sequence
		ISequencePropertySource sps = (ISequencePropertySource) reflisttestPS.getPropertyValue("refs");
		assertNotNull(sps);

		// add component reference members
		SequencePropertyValueSource spvs = (SequencePropertyValueSource) ((AbstractPropertySource) sps).getPropertyValueSource();
		spvs.addComponentReferenceString(ISequencePropertyValue.AT_END, name(basePS));
		spvs.addComponentReferenceString(ISequencePropertyValue.AT_END, name(renderPS));
		assertEquals(2, sps.size());
		
		// remove base instance
		Command cmd = model.createRemoveComponentInstancesCommand(Collections.singletonList(baseChild));
		assertTrue(cmd.canExecute());
		cmd.execute();  // this would crash with IllegalArgumentException before	
		
		assertEquals(1, sps.size());
	}

	public void testRenameReference() {
		reftestPS.setPropertyValue(CONSTRAINED_PROPERTY, name(renderPS));
		assertEquals(name(renderPS), reftestPS.getPropertyValue(CONSTRAINED_PROPERTY));			

		// rename child1, reference property on child2 should be changed to match
		String newName = "new-name";
		renderPS.setPropertyValue("name", newName);
		assertEquals(newName, renderPS.getPropertyValue("name"));
		assertEquals(newName, reftestPS.getPropertyValue(CONSTRAINED_PROPERTY));
	}
	
	public void testChildScope() {
	    // make a sibling of the container. its child reference should not be allowed to refer to the sibling
		EObject root = model.getRootContainers()[0];
		IComponentSet cs = model.getComponentSet();
		IComponent baseComponent = cs.lookupComponent(BASE_COMPONENT);
		EObject obj = model.createNewComponentInstance(baseComponent);
		Command cmd = model.createAddNewComponentInstanceCommand(root, obj, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();
		TestDataModelHelpers.assignUniqueName(model, obj);
		
		IComponentInstance sibling = ModelUtils.getComponentInstance(obj);
		try {
			containerPS.setPropertyValue(CHILDREF_PROPERTY, sibling.getName());
			fail("setPropertyValue should not have succeeed");
		} catch (IllegalArgumentException x) {
			// expected
		}
		
		// set to valid reference
		String validName = (String) basePS.getPropertyValue("name");
		containerPS.setPropertyValue(CHILDREF_PROPERTY, validName);
		assertEquals(validName, containerPS.getPropertyValue(CHILDREF_PROPERTY));
	}


	public void testSiblingScope() {
	    // make a non-sibling of the container. its sibling reference should not be allowed to refer to the sibling
		EObject root = model.getRootContainers()[0];
		IComponentSet cs = model.getComponentSet();
		IComponent baseComponent = cs.lookupComponent(BASE_COMPONENT);
		EObject container = model.createNewComponentInstance(baseComponent);
		Command cmd = model.createAddNewComponentInstanceCommand(root, container, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();
		TestDataModelHelpers.assignUniqueName(model, container);

		// add another child which is the one we're testing 
		EObject nonSiblingObj = model.createNewComponentInstance(baseComponent);
		cmd = model.createAddNewComponentInstanceCommand(root, nonSiblingObj, -1);
		assertTrue(cmd.canExecute());
		cmd.execute();
		TestDataModelHelpers.assignUniqueName(model, nonSiblingObj);
		
		/*
		 * root
		 * +  container
		 * 		+ base
		 * +  nonSibling
		 */
		
		IPropertySource nonSiblingPS = ModelUtils.getPropertySource(nonSiblingObj);

		// should work, base child of container
		containerPS.setPropertyValue(SIBLINGREF_PROPERTY, basePS.getPropertyValue("name"));

		// should work, 'non'Sibling is sibling of container
		IComponentInstance sibling = ModelUtils.getComponentInstance(nonSiblingObj);
		containerPS.setPropertyValue(SIBLINGREF_PROPERTY, sibling.getName());

		// set to invalid reference (non-sibling)
		try {
			basePS.setPropertyValue(SIBLINGREF_PROPERTY, nonSiblingPS.getPropertyValue("name"));
			fail("setPropertyValue should have failed");
		} catch (IllegalArgumentException e) {
			// pass
		}
		// set to invalid reference (parent)
		try {
			basePS.setPropertyValue(SIBLINGREF_PROPERTY, containerPS.getPropertyValue("name"));
			fail("setPropertyValue should have failed");
		} catch (IllegalArgumentException e) {
			// pass
		}
	}
}
