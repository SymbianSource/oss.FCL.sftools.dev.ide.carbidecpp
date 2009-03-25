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

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.testsupport.TestDataModelHelpers;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.osgi.framework.Bundle;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

public class LoadedModelNotificationTest extends TestCase {

	static final String PROJECT_NAME = "loader-notify-tests";
	
	IProject project;
	IDesignerDataModel model;
	
	protected void setUp() throws Exception {
		project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		
		Bundle bundle = TestsPlugin.getDefault().getBundle();
		Path path = new Path("/data/test-model.nxd");
		URL url = Platform.find(bundle, path);
		url = Platform.asLocalURL(url);
		File srcFile = new File(url.getFile());
		File destFile = new File(projectDir, "test-model.nxd");
		FileUtils.copyFile(srcFile, destFile);

		projectDir = new File(project.getLocation().toOSString());
		File dmFile = new File(projectDir, "test-model.nxd");

		model = TestDataModelHelpers.loadDataModel(dmFile);
		assertNotNull(model);
	}

	protected void tearDown() throws Exception {
		model.dispose();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);	
	}
	
	IComponentInstance getCI(EObject object) {
		IComponentInstance result = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
				object, IComponentInstance.class);
		return result;
	}
	
	EObject findChild(EObject obj, String name) {
		EObject result = null;
		IComponentInstance ci = getCI(obj);
		EObject[] children = ci.getChildren();
		for (int i = 0; i < children.length; i++) {
			IComponentInstance childCI = getCI(children[i]);
			if (childCI.getName().equals(name)) {
				result = children[i];
				break;
			}
		}
		return result;
	}
	
	
	public void testNoFireOnGet() {
		// regression test that getting a property does not fire a property changed event
		EObject rootContainer = model.getRootContainers()[0];
	
		IComponentInstance rootCI = getCI(rootContainer);
	
		TestListener listener = new TestListener();
		rootCI.addChildListener(listener);		
		rootCI.addPropertyListener(listener);		
		
		String name = rootCI.getName();
		
		assertTrue(listener.propertyChangedCount == 0);
		assertEquals("RootComponent", name);
	}


	class TestListener implements 
			IComponentInstanceChildListener, IComponentInstancePropertyListener {
		
		int anyChangeCount;
		int childAddedCount;
		EObject lastChildAdded;
		
		int childRemovedCount;
		EObject lastChildRemoved;
		
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
			// unused
		}

		public void propertyChanged(EObject componentInstance, Object propertyId) {
			++anyChangeCount;
			++propertyChangedCount;
			lastPropertyId = propertyId;
		}
	}

}
