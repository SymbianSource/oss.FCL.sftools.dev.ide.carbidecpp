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
package com.nokia.sdt.emf.dm.tests;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;
import org.osgi.framework.Version;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.emf.dm.ComponentManifestDelta;
import com.nokia.sdt.emf.dm.DmFactory;
import com.nokia.sdt.emf.dm.IComponentManifest;
import com.nokia.sdt.emf.dm.IComponentManifestEntry;
import com.nokia.sdt.symbian.dm.DesignerDataModel;
import com.nokia.sdt.testsupport.AdapterHelpers;
import com.nokia.sdt.testsupport.TestDataModelHelpers;

import junit.framework.TestCase;

public class ComponentManifestTest extends TestCase {

	static final String COMPONENT = "com.nokia.examples.baseComponent";
	static final String LOCALIZED_COMPONENT = "com.nokia.examples.localizedComponent";
	
	DesignerDataModel model;
	EObject root;
	IComponentManifest manifest;
	
	protected void setUp() throws Exception {
		model = (DesignerDataModel) TestDataModelHelpers.createTemporaryModel();
		TestDataModelHelpers.initDefaultComponentSet(model);
		IComponent component = model.getComponentSet().lookupComponent(COMPONENT);
		root = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(root);
		IPropertySource ps = AdapterHelpers.getPropertySource(root);
		ps.setPropertyValue("name", "view1");
		
		manifest = model.getDesignerData().getComponentManifest();
	}
	
	public void testUninitializedManifest() {
		assertFalse(manifest.reflectsLastSave());
		assertTrue(manifest.getEntries().size()==0);
	}
	
	public void testBasicManifest() {
		manifest.update(model.getDesignerData());
		assertTrue(manifest.getEntries().size()==1);
		IComponentManifestEntry entry = (IComponentManifestEntry) manifest.getEntries().get(0);
		assertEquals(entry.getComponentID(),COMPONENT);
		assertEquals(entry.getVersion().toString(), "1.0.0");
	}
	
	public void testMultipleNodes() {
		IComponent component = model.getComponentSet().lookupComponent(LOCALIZED_COMPONENT);
		EObject obj = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(obj);
		obj = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(obj);
		
		manifest.update(model.getDesignerData());
		assertTrue(manifest.getEntries().size()==3);
		IComponentManifestEntry entry = manifest.lookup(COMPONENT);
		assertEquals(entry.getComponentID(), COMPONENT);
		assertEquals(entry.getVersion().toString(), "1.0.0");
		entry = manifest.lookup(LOCALIZED_COMPONENT);
		assertEquals(entry.getComponentID(), LOCALIZED_COMPONENT);
		assertEquals(entry.getVersion().toString(), "1.0.0");
	}
	
	public void testNoDeltas() {
		manifest.update(model.getDesignerData());
		Map<String, ComponentManifestDelta> deltas = manifest.getComponentDeltas(model.getDesignerData());
		assertNull(deltas);
	}
	
	public void testMissingComponent() {
		manifest.update(model.getDesignerData());
		// add a fake entry, check that it turns up missing
		IComponentManifestEntry entry = DmFactory.eINSTANCE.createIComponentManifestEntry();
		entry.setComponentID("missing");
		Version version = new Version("1.2.0");
		entry.setVersion(version);
		manifest.getEntries().add(entry);
		
		Map<String, ComponentManifestDelta> deltas = manifest.getComponentDeltas(model.getDesignerData());
		assertTrue(deltas.size() == 1);
		ComponentManifestDelta delta = deltas.get("missing");
		assertNotNull(delta);
		assertTrue(delta.getType() == ComponentManifestDelta.MISSING);
		assertEquals(delta.getOldVersion(), version);
		assertNull(delta.getNewVersion());
	}
	
	public void testUpdatedComponent() {
		IComponent component = model.getComponentSet().lookupComponent(LOCALIZED_COMPONENT);
		EObject obj = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(obj);
		obj = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(obj);

		manifest.update(model.getDesignerData());
		// make an entry mimicing an updated component
		IComponentManifestEntry entry = manifest.lookup(LOCALIZED_COMPONENT);
		Version version = new Version("0.5");
		entry.setVersion(version);
		Map<String, ComponentManifestDelta> deltas = manifest.getComponentDeltas(model.getDesignerData());
		assertTrue(deltas.size() == 1);
		ComponentManifestDelta delta = deltas.get(LOCALIZED_COMPONENT);
		assertNotNull(delta);
		assertTrue(delta.getType() == ComponentManifestDelta.NEWER);
		assertEquals(delta.getOldVersion(), version);
		assertEquals(delta.getNewVersion(), model.getComponentSet().lookupComponent(LOCALIZED_COMPONENT).getComponentVersion());
	}
	
	public void testDowngradedComponent() {
		IComponent component = model.getComponentSet().lookupComponent(LOCALIZED_COMPONENT);
		EObject obj = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(obj);
		obj = model.createNewComponentInstance(component);
		model.getDesignerData().getRootContainers().add(obj);

		manifest.update(model.getDesignerData());
		// make an entry mimicing a downgraded component
		IComponentManifestEntry entry = manifest.lookup(LOCALIZED_COMPONENT);
		Version version = new Version("3.0");
		entry.setVersion(version);
		Map<String, ComponentManifestDelta> deltas = manifest.getComponentDeltas(model.getDesignerData());
		assertTrue(deltas.size() == 1);
		ComponentManifestDelta delta = deltas.get(LOCALIZED_COMPONENT);
		assertNotNull(delta);
		assertTrue(delta.getType() == ComponentManifestDelta.OLDER);
		assertEquals(delta.getOldVersion(), version);
		assertEquals(delta.getNewVersion(), model.getComponentSet().lookupComponent(LOCALIZED_COMPONENT).getComponentVersion());
	}

}
