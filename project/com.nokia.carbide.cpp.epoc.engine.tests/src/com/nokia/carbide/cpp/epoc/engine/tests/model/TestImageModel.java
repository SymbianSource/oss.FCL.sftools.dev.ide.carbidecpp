/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.ISVGSource;
import com.nokia.carbide.cpp.epoc.engine.image.MultiImageSourceFactory;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;


public class TestImageModel extends BaseTest {

	private IMultiImageSource svgSource;
	private IMultiImageSource bmpSource;

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bmpSource = MultiImageSourceFactory.createMultiImageSource(false, true, false);
		svgSource = MultiImageSourceFactory.createMultiImageSource(true, true, true);
		
	}
	
	public void testBitmapSource() {
		bmpSource.setTargetPath(new Path("\\system\\apps"));
		bmpSource.setTargetFile("MyImages.mbm");
		IBitmapSource bmp = bmpSource.createBitmapSource();
		bmp.setPath(new Path("gfx/MyImage.bmp"));
		
		// note capitalization
		assertEquals("EMbmMyimagesMyimage", 
				bmpSource.getGeneratedImageEnumerator(bmp));
		
		bmp.setPath(new Path("myimage"));
		assertEquals("EMbmMyimagesMyimage", 
				bmpSource.getGeneratedImageEnumerator(bmp));
		
		assertNull(bmpSource.getGeneratedMaskEnumerator(bmp));
		bmp.setMaskPath(new Path("gfx/image_mask_soft.bmp"));
		assertNull(bmpSource.getGeneratedMaskEnumerator(bmp));	// no depth
		bmp.setMaskDepth(8);
		assertEquals("EMbmMyimagesImage_mask_soft", 
				bmpSource.getGeneratedMaskEnumerator(bmp));
		
		
	}
	public void testBitmap() {
		IBitmapSource bmp = bmpSource.createBitmapSource();
		assertNotNull(bmp);
		assertNull(bmpSource.createSVGSource());
		
		assertEquals(bmp, bmp);
		assertFalse(bmp.isValid());
		bmp.setPath(new Path("gfx/foo.bmp"));
		assertFalse(bmp.isValid()); // path but no depth
		bmp.setDepth(2);
		assertTrue(bmp.isValid());
		
		IBitmapSource bmp2 = bmpSource.createBitmapSource();
		assertNotNull(bmp2);
		bmp2.setPath(new Path("gfx/foo.bmp"));
		bmp2.setDepth(2);
		assertEquals(bmp, bmp2);
		bmp2.setMaskDepth(1);
		assertFalse(bmp.equals(bmp2));
		bmp2.setMaskDepth(0);
		assertEquals(bmp, bmp2);
		
		bmp.setMaskPath(new Path("gfx/foo_mask.bmp"));
		assertFalse(bmp.isValid()); // path but no depth
		bmp.setMaskDepth(8);
		assertTrue(bmp.isValid());
		
	}
	
	public void testSVG() {
		ISVGSource svg = svgSource.createSVGSource();
		assertNotNull(svg);
		
		assertEquals(svg, svg);
		assertFalse(svg.isValid());
		svg.setPath(new Path("gfx/foo.svg"));
		assertFalse(svg.isValid());
		svg.setColor(true);
		svg.setDepth(32);
		assertTrue(svg.isValid());
		
		ISVGSource svg2 = svgSource.createSVGSource();
		assertNotNull(svg2);
		svg2.setPath(new Path("gfx/foo.svg"));
		svg2.setDepth(32);
		svg2.setColor(true);
		assertEquals(svg, svg2);
		svg2.setMaskDepth(1);
		assertFalse(svg.equals(svg2));
		svg2.setMaskDepth(0);
		assertEquals(svg, svg2);
		
		svg.setMaskDepth(8);
		assertTrue(svg.isValid());


	}
}
