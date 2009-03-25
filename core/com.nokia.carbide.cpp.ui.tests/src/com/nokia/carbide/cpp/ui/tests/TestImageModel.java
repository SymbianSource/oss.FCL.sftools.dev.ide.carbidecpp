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

package com.nokia.carbide.cpp.ui.tests;

import com.nokia.carbide.cpp.ui.images.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Test basic IImageModel variants
 *
 */
public class TestImageModel extends BaseTest {

	// update this when new images dropped into 'data/images'
	private final static int KNOWN_IMAGE_DIRECTORY_IMAGES = 12;
	// update this when new images dropped into subdirectories under 'data/images'
	private final static int KNOWN_IMAGE_DIRECTORY_IMAGES_RECURSIVE = 13;
	
	private IImageLoader fileImageLoader;
	private IPath projectPath;
	private IPath imageDirPath;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		fileImageLoader = ImageModelFactory.createImageLoader(false);
		projectPath = getProjectPath();
		imageDirPath = projectPath.append("data/images");
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		fileImageLoader.dispose();
		super.tearDown();
	}

	public void testFileContainerModelNonRecursive() {
		IImageContainerModel fileContainerModel = ImageModelFactory.createFileImageContainerModel(
				fileImageLoader, imageDirPath.toFile(), null, false);
		IImageModel[] models = fileContainerModel.createImageModels();
		
		// update this when new images dropped into 'data'
		assertEquals(KNOWN_IMAGE_DIRECTORY_IMAGES, models.length);
		
		for (IImageModel model : models) {
			assertTrue(model instanceof IFileImageModel);
			doTestFileImageModel((IFileImageModel) model);
		}
	}
	
	protected void doTestFileImageModel(IFileImageModel model) {
		assertNotNull(model.getSourcePath());
		assertNull(model.getTargetPath());
		assertTrue(model.getSourceLocation().toFile().exists());
		assertEquals(imageDirPath, model.getImageContainerModel().getBaseLocation());
		assertTrue(imageDirPath.append(model.getSourcePath()).toFile().exists());
		doTestImageModel(model);

	}

	private void doTestImageModel(IImageModel model) {
		try {
			// the descriptors may differ in identity but the data should be the same
			ImageDescriptor data = model.getImageDescriptor(null);
			assertNotNull(data);
			assertNotNull(data.getImageData());
			ImageDescriptor data2 = model.getImageDescriptor(null);
			assertEquals(data, data2);
			assertNotNull(data2);
			assertNotNull(data2.getImageData());
			assertSame(data.getImageData(), data2.getImageData());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	public void testFileContainerModelRecursive() {
		IImageContainerModel fileContainerModel = ImageModelFactory.createFileImageContainerModel(
				fileImageLoader, imageDirPath.toFile(), 
				new FilenameFilter() {

					public boolean accept(File dir, String name) {
						if (dir.getName().equals("CVS"))
							return false;
						return true;
					}
					
				},
				true);
		IImageModel[] models = fileContainerModel.createImageModels();
		
		assertEquals(KNOWN_IMAGE_DIRECTORY_IMAGES_RECURSIVE, models.length);
		
		for (IImageModel model : models) {
			assertTrue(model instanceof IFileImageModel);
			doTestFileImageModel((IFileImageModel) model);
		}
	}
	
	
	public void testNullImageModel() throws CoreException {
		IImageModel model = ImageModelFactory.createNullImageModel();
		assertNotNull(model);
		assertNotNull(model.getDisplayText());
		assertNull(model.getImageContainerModel());
		ImageDescriptor descriptor = model.getImageDescriptor(null);
		assertNotNull(descriptor);
		assertNull(descriptor.getImageData());
		assertNull(descriptor.createImage(false));
	}
	
}
