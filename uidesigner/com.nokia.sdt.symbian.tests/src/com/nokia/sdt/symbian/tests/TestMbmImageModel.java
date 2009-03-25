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

package com.nokia.sdt.symbian.tests;

import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.internal.project.ui.images.*;
import com.nokia.carbide.cpp.ui.images.*;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.*;

/**
 * Test basic IImageModel variants
 *
 */
public class TestMbmImageModel extends BaseTest {

	private IImageLoader fileImageLoader;
	private IPath projectPath;
	private IPath imageDirPath;
	private IImageConverterFactory stdImageConverterFactory;
	private IImageConverterFactory s60ImageConverterFactory;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@SuppressWarnings("deprecation")
	protected void setUp() throws Exception {

		fileImageLoader = ImageModelFactory.createImageLoader(false);
		projectPath = getProjectPath();
		imageDirPath = projectPath.append("data/images/sample");
		stdImageConverterFactory = new SymbianImageConverterFactory();
		s60ImageConverterFactory = new Series60ImageConverterFactory();
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		fileImageLoader.dispose();
		super.tearDown();
	}

	

	
	/**
	 * Get MBM model data, which may be reduced in depth or treated as a mask
	 * @param imagePath
	 * @param depth
	 * @param isColor
	 * @param size
	 * @return
	 * @throws CoreException
	 */
	protected ImageData getMbmImageDataBase(
			IImageConverterFactory converterFactory,
			IPath imagePath, int depth,
			 boolean isColor, Point size) throws CoreException {
		ISymbianImageContainerModel container = CarbideImageModelFactory.createNullSymbianImageContainerModel(
				imageDirPath, fileImageLoader, converterFactory);
		
		ISymbianMaskedFileImageModel model = CarbideImageModelFactory.createSymbianMaskedFileImageModel(
				container,
				imagePath, null,
				new ImageFormat(isColor, depth, 0));
		assertNotNull(model);
		assertEquals(IMaskedFileImageModel.MaskCompositionMethod.TILING, model.getMaskCompositionMethod());
		
		ImageDescriptor descriptor;
		descriptor = model.getImageDescriptor(size);
		assertNotNull(descriptor);
		ImageDescriptor descriptor2 = model.getImageDescriptor(size);
		
		ImageData data = descriptor.getImageData();
		ImageData data2 = descriptor2.getImageData();
		if (size == null) {
			assertSame(data, data2);
		}
		
		return data;
	}
	
	protected ImageData getMbmImageData(
			IPath imagePath, int depth,
			 boolean isColor, Point size) throws CoreException {
		return getMbmImageDataBase(stdImageConverterFactory, imagePath, depth, isColor, size);
	}
	protected ImageData getS60MbmImageData(
			IPath imagePath, int depth,
			boolean isColor, Point size) throws CoreException {
		return getMbmImageDataBase(s60ImageConverterFactory, imagePath, depth, isColor, size);
	}
	/**
	 * Get composed MBM model data, which may be reduced in depth or have a mask
	 * @param imageFile
	 * @param colorDepth
	 * @param isColor
	 * @param maskFile
	 * @param maskDepth
	 * @param isSoftMask
	 * @param size
	 * @return
	 * @throws CoreException
	 */
	protected ImageData getComposedMbmImageDataBase(
			IImageConverterFactory converterFactory,
			IPath imageFile, int colorDepth, boolean isColor, 
		IPath maskFile, int maskDepth, boolean isSoftMask, 
		Point size) throws CoreException {
		ISymbianImageContainerModel container = CarbideImageModelFactory.createNullSymbianImageContainerModel(
				imageDirPath, fileImageLoader, converterFactory);
		
		ISymbianMaskedFileImageModel model = CarbideImageModelFactory.createSymbianMaskedFileImageModel(
				container,
				imageFile, maskFile,
				new ImageFormat(isColor, colorDepth, maskDepth));
		assertNotNull(model);
		assertEquals(IMaskedFileImageModel.MaskCompositionMethod.TILING, model.getMaskCompositionMethod());
		
		ImageDescriptor descriptor;
		descriptor = model.getImageDescriptor(size);
		assertNotNull(descriptor);
		ImageDescriptor descriptor2 = model.getImageDescriptor(size);
		
		ImageData data = descriptor.getImageData();
		ImageData data2 = descriptor2.getImageData();
		if (size == null) {
			assertSame(data, data2);
		}
		
		return data;	
	}
	
	protected ImageData getComposedMbmImageData(IPath imageFile, int colorDepth, boolean isColor, 
			IPath maskFile, int maskDepth, boolean isSoftMask, 
			Point size) throws CoreException {
		return getComposedMbmImageDataBase(stdImageConverterFactory, imageFile, colorDepth, isColor, maskFile, maskDepth, isSoftMask, size);
	}
	protected ImageData getComposedS60MbmImageData(IPath imageFile, int colorDepth, boolean isColor, 
			IPath maskFile, int maskDepth, boolean isSoftMask, 
			Point size) throws CoreException {
		return getComposedMbmImageDataBase(s60ImageConverterFactory, imageFile, colorDepth, isColor, maskFile, maskDepth, isSoftMask, size);
	}
	
	/**
	 * Test standard loading w/o color changes
	 * @throws Exception
	 */
	public void testStd32Bit() throws Exception {
		ImageData imgData = getMbmImageData(new Path("list_icon.bmp"), 
				32, true, null);
		checkImage(imgData, 42, 29, 
				new Pixel[] {
				
					new Pixel(0, 0, new RGB(221, 0, 221)),
					new Pixel(15, 14, new RGB(255, 255, 102)),
			}
		);

		ImageData imgDataSmall = getMbmImageData(new Path("list_icon.bmp"), 
				32, true, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
				
					new Pixel(0, 0, new RGB(221, 0, 221)),
					new Pixel(0, 1, new RGB(221, 0, 221)),
					new Pixel(7, 7, new RGB(255, 255, 102)),
			}
		);

	}

	/**
	 * Test 8-bit color reduction
	 * @throws Exception
	 */
	public void testStd8Bit() throws Exception {
		ImageData imgData = getMbmImageData(new Path("list_icon.bmp"), 
				8, true, null);
		checkImage(imgData, 42, 29, 
				new Pixel[] {
					// border color mapped to palette 
					new Pixel(0, 0, new RGB(204, 0, 204)),
					new Pixel(0, 1, new RGB(204, 0, 204)),
					// still native color
					new Pixel(15, 14, new RGB(255, 255, 102)),
			}
		);

		ImageData imgDataSmall = getMbmImageData(new Path("list_icon.bmp"), 
				8, true, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
					// border color mapped to palette 
					new Pixel(0, 0, new RGB(204, 0, 204)),
					new Pixel(0, 1, new RGB(204, 0, 204)),
					// still native color
					new Pixel(7, 7, new RGB(255, 255, 102)),
			}
		);

	}

	/**
	 * Test 8-bit color reduction with S60 palette.  Ensure we can get a mask
	 * file without palette conversion.
	 * @throws Exception
	 */
	public void testStd8BitPalette() throws Exception {
		ImageData imgData = getS60MbmImageData(new Path("qgn_note_warning_mask_soft.bmp"), 
				8, true, null);
		checkImage(imgData, 30, 40, 
				new Pixel[] {
					// central color mapped 
					new Pixel(15, 15, new RGB(204, 0, 204)),
			}
		);

		ImageData imgDataMask = getS60MbmImageData(new Path("qgn_note_warning_mask_soft.bmp"), 
				8, false, null);
		checkImage(imgDataMask, 30, 40, 
				new Pixel[] {
					// central color not mapped 
					new Pixel(15, 15, new RGB(255, 255, 255)),
			}
		);

	}

	/**
	 * Test 4-bit color reduction
	 * @throws Exception
	 */
	public void testStd4Bit() throws Exception {
		ImageData imgData = getMbmImageData(new Path("list_icon.bmp"), 
				4, true, null);
		checkImage(imgData, 42, 29, 
				new Pixel[] {
					// mapped to 16-bit palette
					new Pixel(0, 0, new RGB(255, 0, 255)),
					new Pixel(15, 14, new RGB(255, 255, 0)),
			}
		);
		ImageData imgDataSmall = getMbmImageData(new Path("list_icon.bmp"), 
				4, true, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
					// mapped to 16-bit palette
					new Pixel(0, 0, new RGB(255, 0, 255)),
					new Pixel(7, 7, new RGB(255, 255, 0)),
			}
		);
	}

	/**
	 * Test 1-bit B&W reduction
	 * @throws Exception
	 */
	public void testStd1BitGrey() throws Exception {
		ImageData imgData = getMbmImageData(new Path("list_icon.bmp"), 
				1, false, null);
		checkImage(imgData, 42, 29, 
				new Pixel[] {
					// mapped to B&W palette
					new Pixel(0, 0, new RGB(0, 0, 0)),
					new Pixel(15, 14, new RGB(255, 255, 255)),
			}
		);
		ImageData imgDataSmall = getMbmImageData(new Path("list_icon.bmp"), 
				1, false, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
					// mapped to B&W palette
					new Pixel(0, 0, new RGB(0, 0, 0)),
					new Pixel(7, 7, new RGB(255, 255, 255)),
			}
		);
	}

	/**
	 * Test 2-bit grey reduction
	 * @throws Exception
	 */
	public void testStd2BitGrey() throws Exception {
		ImageData imgData = getMbmImageData(new Path("list_icon.bmp"), 
				2, false, null);
		checkImage(imgData, 42, 29, 
				new Pixel[] {
					// mapped to 4-grey palette
					new Pixel(0, 0, new RGB(85, 85, 85)),
					new Pixel(15, 14, new RGB(255, 255, 255)),
					new Pixel(23, 17, new RGB(170, 170, 170)),
			}
		);
		ImageData imgDataSmall = getMbmImageData(new Path("list_icon.bmp"), 
				2, false, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
					// mapped to 4-grey palette
					new Pixel(0, 0, new RGB(85, 85, 85)),
					new Pixel(7, 7, new RGB(255, 255, 255)),
					new Pixel(11, 8, new RGB(170, 170, 170)),
			}
		);
	}

	/**
	 * Test 4-bit grey reduction
	 * @throws Exception
	 */
	public void testStd4BitGrey() throws Exception {
		ImageData imgData = getMbmImageData(new Path("list_icon.bmp"), 
				4, false, null);
		checkImage(imgData, 42, 29, 
				new Pixel[] {
					// mapped to 16-grey palette
					new Pixel(0, 0, new RGB(85, 85, 85)),
					new Pixel(15, 14, new RGB(238, 238, 238)),
					new Pixel(23, 17, new RGB(187, 187, 187)),
			}
		);
		ImageData imgDataSmall = getMbmImageData(new Path("list_icon.bmp"), 
				4, false, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
					// mapped to 16-grey palette
					new Pixel(0, 0, new RGB(85, 85, 85)),
					new Pixel(7, 7, new RGB(238, 238, 238)),
					new Pixel(11, 8, new RGB(187, 187, 187)),
			}
		);
	}

	/**
	 * Test 8-bit grey reduction
	 * @throws Exception
	 */
	public void testStd8BitGrey() throws Exception {
		ImageData imgData = getMbmImageData(new Path("list_icon.bmp"), 
				8, false, null);
		checkImage(imgData, 42, 29, 
				new Pixel[] {
					// mapped to 256-grey palette
					new Pixel(0, 0, new RGB(82, 82, 82)),
					new Pixel(0, 1, new RGB(82, 82, 82)),
					new Pixel(15, 14, new RGB(235, 235, 235)),
					new Pixel(23, 17, new RGB(191, 191, 191)),
			}
		);
		ImageData imgDataSmall = getMbmImageData(new Path("list_icon.bmp"), 
				8, false, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
					// mapped to 256-grey palette
					new Pixel(0, 0, new RGB(82, 82, 82)),
					new Pixel(0, 1, new RGB(82, 82, 82)),
					new Pixel(7, 7, new RGB(235, 235, 235)),
					new Pixel(11, 8, new RGB(191, 191, 191)),
			}
		);
	}

	/**
	 * Test composing images
	 * @throws Exception
	 */
	public void testComposing() throws Exception {
		ImageData imgData = getComposedMbmImageData(new Path("list_icon.bmp"), 32, true,
				new Path("list_icon_mask.bmp"), 1, 
				false, null);
		checkImage(imgData, 42, 29, 
				new Pixel[] {
				
					new Pixel(0, 0, transparent),
					new Pixel(15, 14, new RGB(255, 255, 102)),
			}
		);

		ImageData imgDataSmall = getComposedMbmImageData(new Path("list_icon.bmp"), 32, true, 
				new Path("list_icon_mask.bmp"), 1, 
				false, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
				
					new Pixel(0, 0, transparent),
					new Pixel(7, 7, new RGB(255, 255, 102)),
			}
		);

	}


	/**
	 * Test composing images
	 * @throws Exception
	 */
	public void testComposing8Bit() throws Exception {
		ImageData imgData = getComposedMbmImageData(new Path("list_icon.bmp"), 
				8, true,
				new Path("list_icon_mask.bmp"), 1, 
				false, null);
		
		checkImage(imgData, 42, 29, 
				new Pixel[] {
				
					new Pixel(0, 0, transparent),
					new Pixel(15, 14, new RGB(255, 255, 102)),
					new Pixel(24, 15, new RGB(255, 204, 0)),
					new Pixel(24, 15, new RGB(255, 204, 0)),
			}
		);

		ImageData imgDataSmall = getComposedMbmImageData(new Path("list_icon.bmp"), 
				8, true, 
				new Path("list_icon_mask.bmp"), 1, 
				false, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
					new Pixel(0, 0, transparent),
					new Pixel(7, 7, new RGB(255, 255, 102)),
					new Pixel(12, 7, new RGB(255, 204, 0)),
			}
		);

	}

	/**
	 * Test composing images
	 * @throws Exception
	 */
	public void testComposing8BitS60() throws Exception {
		ImageData imgData = getComposedS60MbmImageData(new Path("list_icon.bmp"), 
				8, true,
				new Path("list_icon_mask.bmp"), 1, 
				false, null);
		
		checkImage(imgData, 42, 29, 
				new Pixel[] {
				
					new Pixel(0, 0, transparent),
					new Pixel(15, 14, new RGB(153, 0, 0)),
					new Pixel(24, 15, new RGB(255, 51, 0)),
			}
		);

		ImageData imgDataSmall = getComposedS60MbmImageData(new Path("list_icon.bmp"), 
				8, true, 
				new Path("list_icon_mask.bmp"), 1, 
				false, new Point(20, 15));
		checkImage(imgDataSmall, 20, 15, 
				new Pixel[] {
					new Pixel(0, 0, transparent),
					new Pixel(7, 7, new RGB(153, 0, 0)),
					new Pixel(12, 7, new RGB(255, 51, 0)),
			}
		);

	}

	/**
	 * Test composing images
	 * @throws Exception
	 */
	public void testComposingSoftMask() throws Exception {
		ImageData imgData = getComposedMbmImageData(new Path("bar.bmp"), 
				8, true,
				new Path("bar_mask_soft.bmp"), 8, 
				true, null);
		checkImage(imgData, 153, 10, 
				new Pixel[] {
				
					new Pixel(0, 0, null, 0),
					new Pixel(4, 5, new RGB(102, 153, 255)),
			}
		);
		ImageData imgData32 = getComposedMbmImageData(new Path("bar.bmp"), 
				32, true,
				new Path("bar_mask_soft.bmp"), 8, 
				true, null);
		checkImage(imgData32, 153, 10, 
				new Pixel[] {
				
					new Pixel(0, 0, null, 0),
					new Pixel(4, 5, new RGB(93, 169, 225)),
			}
		);
	}

	/**
	 * Test composing images
	 * @throws Exception
	 */
	public void testComposingSoftMaskS60() throws Exception {
		ImageData imgData = getComposedS60MbmImageData(new Path("bar.bmp"), 
				8, true,
				new Path("bar_mask_soft.bmp"), 8, 
				true, null);
		checkImage(imgData, 153, 10, 
				new Pixel[] {
				
					new Pixel(0, 0, null, 0),
					new Pixel(4, 5, new RGB(0, 4, 41)),
			}
		);
		ImageData imgData32 = getComposedS60MbmImageData(new Path("bar.bmp"), 
				32, true,
				new Path("bar_mask_soft.bmp"), 8, 
				true, null);
		checkImage(imgData32, 153, 10, 
				new Pixel[] {
				
					new Pixel(0, 0, null, 0),
					new Pixel(4, 5, new RGB(93, 169, 225)),
			}
		);
	}

	public void testSVG() throws Exception {
		ImageData imgData = getComposedMbmImageData(new Path("box.svg"), 
				32, true,
				null, 8, 
				false, null);
		
		checkImage(imgData, 88, 88, 
				new Pixel[] {
					// must be transparent
					new Pixel(0, 0, null, 0),
					new Pixel(24, 24, new RGB(51, 102, 204)),
					new Pixel(44, 44, new RGB(207, 236, 255)),
			}
		);

		ImageData imgDataOnWhite = getComposedMbmImageData(new Path("box.svg"), 
				32, true,
				null, 0, 
				false, null);
		checkImage(imgDataOnWhite, 88, 88, 
				new Pixel[] {
					// must be white
					new Pixel(0, 0, new RGB(255, 255, 255)),
					new Pixel(24, 24, new RGB(51, 102, 204)),
					new Pixel(44, 44, new RGB(207, 236, 255)),
			}
		);

	}

	public void testSVGColorDepth() throws Exception {
		// 8-bit color
		ImageData imgData = getComposedMbmImageData(new Path("box.svg"), 
				8, true,
				null, 8, 
				false, null);
		checkImage(imgData, 88, 88, 
				new Pixel[] {
					// must be transparent
					new Pixel(0, 0, null, 0),
					new Pixel(24, 24, new RGB(51, 102, 204)),
					new Pixel(44, 44, new RGB(207, 236, 255)),
			}
		);

		// 4-bit
		imgData = getComposedMbmImageData(new Path("box.svg"), 
				4, true,
				null, 4, 
				false, null);
		checkImage(imgData, 88, 88, 
				new Pixel[] {
					// must be transparent
					new Pixel(0, 0, null, 0),
					// 4-bit depth ignored
					new Pixel(24, 24, new RGB(51, 102, 204)),
					new Pixel(44, 44, new RGB(207, 236, 255)),
					//new Pixel(24, 24, new RGB(0, 128, 128)),
					//new Pixel(44, 44, new RGB(255, 255, 255)),
			}
		);

		// test with white background
		
		ImageData imgDataOnWhite = getComposedMbmImageData(new Path("box.svg"), 
				8, true,
				null, 0, 
				false, null);
		checkImage(imgDataOnWhite, 88, 88, 
				new Pixel[] {
					// must be white
					new Pixel(0, 0, new RGB(255, 255, 255)),
					new Pixel(24, 24, new RGB(51, 102, 204)),
					new Pixel(44, 44, new RGB(207, 236, 255)),
			}
		);

		// 4-bit color
		imgDataOnWhite = getComposedMbmImageData(new Path("box.svg"), 
				4, true,
				null, 0, 
				false, null);
		checkImage(imgDataOnWhite, 88, 88, 
				new Pixel[] {
					// must be white
					new Pixel(0, 0, new RGB(255, 255, 255)),
					// 4-bit depth ignred
					new Pixel(24, 24, new RGB(51, 102, 204)),
					new Pixel(44, 44, new RGB(207, 236, 255)),
					//new Pixel(24, 24, new RGB(0, 128, 128)),
					//new Pixel(44, 44, new RGB(255, 255, 255)),
			}
		);

	}

	public void testSVGGreyDepth() throws Exception {
		// 4-bit B&W
		ImageData imgData = getComposedMbmImageData(new Path("box.svg"), 
				4, false,
				null, 4, 
				false, null);
		checkImage(imgData, 88, 88, 
				new Pixel[] {
					// must be transparent
					new Pixel(0, 0, null, 0),
					new Pixel(24, 24, new RGB(102, 102, 102)),
					new Pixel(44, 44, new RGB(231, 231, 231)),
			}
		);

		// 4-bit B&W
		ImageData imgDataOnWhite = getComposedMbmImageData(new Path("box.svg"), 
				4, false,
				null, 0, 
				false, null);
		checkImage(imgDataOnWhite, 88, 88, 
				new Pixel[] {
					// must be white
				new Pixel(0, 0, new RGB(255, 255, 255)),
					new Pixel(24, 24, new RGB(102, 102, 102)),
					new Pixel(44, 44, new RGB(231, 231, 231)),
			}
		);

	}
	
}
