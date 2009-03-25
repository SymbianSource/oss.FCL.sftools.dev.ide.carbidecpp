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

import com.nokia.sdt.component.adapter.IDesignerImages;
import com.nokia.sdt.component.symbian.Component;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.emf.component.*;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.ProjectUtils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

public class DesignerImageAdapterTest extends TestCase {
	
	static final String PROJECT_NAME = "DesignerImages";

	static ComponentProvider provider;
	static Component component;

	protected void setUp() throws Exception {
		super.setUp();
		
		// We make a test component by making a temp project, copying an
		// image file to it, and then initializing a component with
		// the DesignerImagesType pointing to the file.
		
		IProject project = ProjectUtils.createAndOpenProject(PROJECT_NAME);
		File projectDir = new File(project.getLocation().toOSString());
		
		Bundle bundle = PluginTest.getDefault().getBundle();
		Path path = new Path("/data/images/test-image.bmp");
		URL url = Platform.find(bundle, path);
		url = Platform.asLocalURL(url);
		
		File srcFile = new File(url.getFile());
		File destFile = new File(projectDir, "test-image.bmp");
		FileUtils.copyFile(srcFile, destFile);
    		
   		if (provider == null) {
    		MockLocalizedStrings strings = new MockLocalizedStrings();
    		provider = new ComponentProvider();
    		ComponentType emfComponent = ComponentFactory.eINSTANCE.createComponentType();
    		component = new Component(provider, projectDir, bundle, emfComponent, strings);
    		ComponentType ct = component.getEMFComponent();
    		ct.setQualifiedName("com.nokia.component.test");
    		
    		DesignerImagesType dit = ComponentFactory.eINSTANCE.createDesignerImagesType();
    		ct.setDesignerImages(dit);
    	
    		dit.setSmallIconFile("test-image.bmp");
    		dit.setLargeIconFile("test-image.bmp");
    		dit.setLayoutImageFile("test-image.bmp");
    		dit.setThumbnailFile("test-image.bmp");
        }
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		ProjectUtils.closeAndDeleteProject(PROJECT_NAME);
	}

	public void testDesignerImageAdapter() {
		IDesignerImages di = (IDesignerImages) component.getAdapter(IDesignerImages.class);
		assertNotNull(di);
	}

	public void testGetSmallIcon() {
		IDesignerImages di = (IDesignerImages) component.getAdapter(IDesignerImages.class);
		Image image = di.getSmallIcon();
		assertNotNull(image);
	}

	public void testGetLargeIcon() {
		IDesignerImages di = (IDesignerImages) component.getAdapter(IDesignerImages.class);
		Image image = di.getLargeIcon();
		assertNotNull(image);
	}

	public void testGetStaticLayoutImage() {
		IDesignerImages di = (IDesignerImages) component.getAdapter(IDesignerImages.class);
		Image image = di.getStaticLayoutImage();
		assertNotNull(image);
	}

	public void testGetThumbnailImage() {
		IDesignerImages di = (IDesignerImages) component.getAdapter(IDesignerImages.class);
		Image image = di.getThumbnailImage();
		assertNotNull(image);
	}

	public void testGetComponent() {
		IDesignerImages di = (IDesignerImages) component.getAdapter(IDesignerImages.class);
		assertEquals(component, di.getComponent());
	}

}
