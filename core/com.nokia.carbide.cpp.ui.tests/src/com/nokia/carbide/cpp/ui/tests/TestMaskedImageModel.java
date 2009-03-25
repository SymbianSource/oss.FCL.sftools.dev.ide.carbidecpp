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

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.*;

/**
 * Test basic IMaskedFileImageModel variants and behavior
 *
 */
public class TestMaskedImageModel extends BaseTest {

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

	public void testWithMask() throws CoreException {
		IImageContainerModel container = ImageModelFactory.createNullImageContainerModel(
				imageDirPath, fileImageLoader);
		
		IMaskedFileImageModel model = ImageModelFactory.createMaskedFileImageModel(
				container,
				new Path("list_icon.bmp"), new Path("list_icon_mask.bmp"),
				IMaskedFileImageModel.MaskCompositionMethod.TILING);
		assertNotNull(model);
		assertEquals(IMaskedFileImageModel.MaskCompositionMethod.TILING, model.getMaskCompositionMethod());
		
		ImageDescriptor descriptor;
		descriptor = model.getImageDescriptor(null);
		assertNotNull(descriptor);
		ImageDescriptor descriptor2 = model.getImageDescriptor(null);
		
		// check caching
		ImageData data = descriptor.getImageData();
		assertNotNull(data);
		ImageData data2 = descriptor2.getImageData();
		assertNotNull(data2);
		assertSame(data, data2);
		
		// make sure the expected pixels are transparent in the expected way
		checkImage(data, 42, 29,
				new Pixel[] {
				new Pixel(0, 0, transparent),
				new Pixel(15, 14, new RGB(255, 255, 102))
				});
		
		// check no caching
		descriptor = model.getImageDescriptor(new Point(42*2, 29*2));
		assertNotNull(descriptor);
		data = descriptor.getImageData();
		descriptor2 = model.getImageDescriptor(new Point(42*2, 29*2));
		data2 = descriptor2.getImageData();
		assertNotSame(data, data2);
		
		checkImage(data, 42*2, 29*2,
				new Pixel[] {
				new Pixel(0, 0, transparent),
				new Pixel(15*2, 14*2, new RGB(255, 255, 102))
				});
		
	}

	public void testNoMask() throws CoreException {
		IImageContainerModel container = ImageModelFactory.createNullImageContainerModel(imageDirPath, fileImageLoader);
		
		IMaskedFileImageModel model = ImageModelFactory.createMaskedFileImageModel(
				container,
				new Path("list_icon.bmp"), null,
				IMaskedFileImageModel.MaskCompositionMethod.TILING);
		assertNotNull(model);
		assertEquals(IMaskedFileImageModel.MaskCompositionMethod.TILING, model.getMaskCompositionMethod());
		
		ImageDescriptor descriptor;
		descriptor = model.getImageDescriptor(null);
		assertNotNull(descriptor);
		ImageDescriptor descriptor2 = model.getImageDescriptor(null);
		
		// check caching
		ImageData data = descriptor.getImageData();
		assertNotNull(data);
		ImageData data2 = descriptor2.getImageData();
		assertNotNull(data2);
		assertSame(data, data2);
		
		// make sure the expected pixels are not transparent
		checkImage(data, 42, 29,
				new Pixel[] {
				new Pixel(0, 0, new RGB(221, 0, 221)),
				new Pixel(15, 14, new RGB(255, 255, 102))
				});

		// check no caching
		descriptor = model.getImageDescriptor(new Point(42*2, 29*2));
		assertNotNull(descriptor);
		data = descriptor.getImageData();
		descriptor2 = model.getImageDescriptor(new Point(42*2, 29*2));
		data2 = descriptor2.getImageData();
		assertNotSame(data, data2);
		
		checkImage(data, 42*2, 29*2,
				new Pixel[] {
				new Pixel(0, 0, new RGB(221, 0, 221)),
				new Pixel(15*2, 14*2, new RGB(255, 255, 102))
				});
		
	}
	
	public void testTiledMask1() throws CoreException {
		IImageContainerModel container = ImageModelFactory.createNullImageContainerModel(imageDirPath, fileImageLoader);
		
		IMaskedFileImageModel model = ImageModelFactory.createMaskedFileImageModel(
				container,
				new Path("list_icon.bmp"), new Path("mark_icon_mask.bmp"),
				IMaskedFileImageModel.MaskCompositionMethod.TILING);
		assertNotNull(model);
		assertEquals(IMaskedFileImageModel.MaskCompositionMethod.TILING, model.getMaskCompositionMethod());
		
		ImageDescriptor descriptor;
		descriptor = model.getImageDescriptor(null);
		assertNotNull(descriptor);
		ImageDescriptor descriptor2 = model.getImageDescriptor(null);
		
		// check caching
		ImageData data = descriptor.getImageData();
		assertNotNull(data);
		ImageData data2 = descriptor2.getImageData();
		assertNotNull(data2);
		assertSame(data, data2);

		// check transparency... the checkmark is tiled 1.5 times horizontally, about,
		// and slightly more than once vertically
		checkImage(data, 42, 29,
				new Pixel[] {
				new Pixel(0, 0, transparent),
				new Pixel(8, 8, transparent),
				new Pixel(19, 10, new RGB(221, 0, 221)),
				new Pixel(15, 14, new RGB(255, 255, 102)),
				new Pixel(31, 14, new RGB(255, 204, 0)),
				new Pixel(29, 23, transparent),
				new Pixel(41, 28, transparent),
				});

		// scaled
		descriptor = model.getImageDescriptor(new Point(42*2, 29*2));
		assertNotNull(descriptor);
		
		data = descriptor.getImageData();
		assertNotNull(data);
		
		// check transparency... both the image and mask should be scaled
		checkImage(data, 42*2, 29*2,
				new Pixel[] {
				new Pixel(0, 0, transparent),
				new Pixel(8*2, 8*2, transparent),
				new Pixel(19*2, 10*2, new RGB(221, 0, 221)),
				new Pixel(15*2, 14*2, new RGB(255, 255, 102)),
				new Pixel(31*2, 14*2, new RGB(255, 204, 0)),
				new Pixel(29*2, 23*2, transparent),
				new Pixel(41*2, 28*2, transparent),
				});

	}

	public void testTiledMask2() throws CoreException {
		IImageContainerModel container = ImageModelFactory.createNullImageContainerModel(imageDirPath, fileImageLoader);
		
		IMaskedFileImageModel model = ImageModelFactory.createMaskedFileImageModel(
				container,
				new Path("four_dots.bmp"), new Path("mark_icon_mask.bmp"),
				IMaskedFileImageModel.MaskCompositionMethod.TILING);
		assertNotNull(model);
		assertEquals(IMaskedFileImageModel.MaskCompositionMethod.TILING, model.getMaskCompositionMethod());
		
		ImageDescriptor descriptor;
		descriptor = model.getImageDescriptor(null);
		assertNotNull(descriptor);
		ImageDescriptor descriptor2 = model.getImageDescriptor(null);
		
		// check caching
		ImageData data = descriptor.getImageData();
		assertNotNull(data);
		ImageData data2 = descriptor2.getImageData();
		assertNotNull(data2);
		assertSame(data, data2);

		// check transparency... the checkmark is much larger so the whole thing
		// becomes invisible
		checkImage(data, 2, 2,
				new Pixel[] {
				new Pixel(0, 0, transparent),
				new Pixel(1, 1, transparent),
				new Pixel(1, 0, transparent),
				new Pixel(0, 1, transparent),
				});
		
		// scaled
		descriptor = model.getImageDescriptor(new Point(2*24, 2*24));
		assertNotNull(descriptor);
		
		data = descriptor.getImageData();
		assertNotNull(data);
		
		// check transparency... both the image and mask should be scaled, and 
		// the result is still fully invisible
		checkImage(data, 2*24, 2*24,
				new Pixel[] {
				new Pixel(0, 0, transparent),
				new Pixel(23, 23, transparent),
				new Pixel(23, 0, transparent),
				new Pixel(0, 23, transparent),
				new Pixel(12, 12, transparent),
				});
	}
	
	public void testScaledMask1() throws CoreException {
		IImageContainerModel container = ImageModelFactory.createNullImageContainerModel(imageDirPath, fileImageLoader);
		
		IMaskedFileImageModel model = ImageModelFactory.createMaskedFileImageModel(
				container,
				new Path("four_dots.bmp"), new Path("mark_icon_mask.bmp"),
				IMaskedFileImageModel.MaskCompositionMethod.SCALING);
		assertNotNull(model);
		assertEquals(IMaskedFileImageModel.MaskCompositionMethod.SCALING, model.getMaskCompositionMethod());
		
		ImageDescriptor descriptor;
		descriptor = model.getImageDescriptor(new Point(26, 26));
		assertNotNull(descriptor);
		
		ImageData data = descriptor.getImageData();
		assertNotNull(data);

		// check transparency... the checkmark is much larger, but scaled up to fit
		checkImage(data, 26, 26,
				new Pixel[] {
				new Pixel(0, 0, transparent),
				new Pixel(25, 25, transparent),
				new Pixel(18, 11, new RGB(0, 255, 0)), //upper-right
				new Pixel(6, 18, new RGB(0, 0, 255)), //lower-left
				new Pixel(14, 16, new RGB(255, 255, 0)), //lower-right
				});
		
	}

}
