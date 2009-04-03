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
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.cpp.internal.api.utils.core.Triple;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.*;

/**
 * Test IImageLoader implementations
 *
 */
public class TestImageLoader extends BaseTest {

	private IImageLoader nonCachingImageLoader;
	private IImageLoader cachingImageLoader;
	private IPath projectPath;
	private IPath imagePath;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		nonCachingImageLoader = ImageModelFactory.createImageLoader(false);
		cachingImageLoader = ImageModelFactory.createImageLoader(true);
		projectPath = getProjectPath();
		imagePath = projectPath.append("data/images");
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		nonCachingImageLoader.dispose();
		cachingImageLoader.dispose();
		super.tearDown();
	}
	
	/** Test solid bitmap */
	public void testBasicNonCachingProviderBMP() {
		// file doesn't exist
		try {
			nonCachingImageLoader.createImageDataProvider(
					imagePath.append("unknown.bmp"));
			fail("File should not exist");
		} catch (CoreException e) {
			//System.out.println(e);
		}
		
		// file does exist
		IImageDataProvider provider = null;
		try {
			provider = nonCachingImageLoader.createImageDataProvider(
					imagePath.append("four_dots.bmp"));
			
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		
		assertNotNull(provider);
		
	}

	/** Test PNG with alpha channel */
	public void testBasicNonCachingProviderPNG() {
		IImageDataProvider provider = null;
		try {
			provider = nonCachingImageLoader.createImageDataProvider(
					imagePath.append("four_dots_transp.png"));
			
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		
		assertNotNull(provider);
		
	}

	public void testBasicNonCachingProviderSVG() {
		IImageDataProvider provider = null;
		try {
			provider = nonCachingImageLoader.createImageDataProvider(
					imagePath.append("CEikImage.svg"));
			
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		
		assertNotNull(provider);
	}

	public void testBasicCachingProviderBMP() {
		// file doesn't exist
		try {
			cachingImageLoader.createImageDataProvider(
					imagePath.append("unknown.bmp"));
			fail("File should not exist");
		} catch (CoreException e) {
			//System.out.println(e);
		}
		
		// file does exist
		IImageDataProvider provider = null;
		try {
			provider = cachingImageLoader.createImageDataProvider(
					imagePath.append("four_dots.bmp"));
			
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		
		assertNotNull(provider);
	}
	
	public void testBasicCachingProviderPNG() {
		// file doesn't exist
		try {
			cachingImageLoader.createImageDataProvider(
					imagePath.append("unknown.png"));
			fail("File should not exist");
		} catch (CoreException e) {
			//System.out.println(e);
		}
		
		// file does exist
		IImageDataProvider provider = null;
		try {
			provider = cachingImageLoader.createImageDataProvider(
					imagePath.append("four_dots_transp.png"));
			
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		
		assertNotNull(provider);
	}
	
	public void testBasicCachingProviderSVG() {
		// file doesn't exist
		try {
			cachingImageLoader.createImageDataProvider(
					imagePath.append("unknown.svg"));
			fail("File should not exist");
		} catch (CoreException e) {
			//System.out.println(e);
		}
		IImageDataProvider provider = null;
		try {
			provider = cachingImageLoader.createImageDataProvider(
					imagePath.append("CEikImage.svg"));
			
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		
		assertNotNull(provider);
	}

	/**
	 * Make sure images aren't loaded more than once at their native size.
	 */
	protected void doTestEssentialCaching(IImageDataProvider provider) {
		ImageData imageData = null;
		try {
			imageData = provider.getImageData(null);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		assertNotNull(imageData);
		
		// make sure we don't load more than once for basic 
		ImageData imageData2 = null;
		try {
			imageData2 = provider.getImageData(null);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		assertNotNull(imageData2);
		
		assertSame(imageData, imageData2);
		
		provider.dispose();
		
		// dispose and ensure we get new data
		ImageData imageData3 = null;
		try {
			imageData3 = provider.getImageData(null);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		assertNotNull(imageData3);
		
		assertNotSame(imageData, imageData3);
	}

	public void testBasicNonCachingSemanticsBitmap() {
		IImageDataProvider provider = null;
		try {
			provider = nonCachingImageLoader.createImageDataProvider(
						imagePath.append("four_dots.bmp"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		doTestEssentialCaching(provider);

		// making a second provider should be distinct
		IImageDataProvider provider2 = null;
		try {
			provider2 = nonCachingImageLoader.createImageDataProvider(
						imagePath.append("four_dots.bmp"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotSame(provider, provider2);
		
		doTestEssentialCaching(provider2);

		
	}

	public void testBasicNonCachingSemanticsSVG() {
		IImageDataProvider provider = null;
		try {
			provider = nonCachingImageLoader.createImageDataProvider(
						imagePath.append("CEikImage.svg"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		doTestEssentialCaching(provider);

		// making a second provider should be distinct
		IImageDataProvider provider2 = null;
		try {
			provider2 = nonCachingImageLoader.createImageDataProvider(
						imagePath.append("CEikImage.svg"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertNotSame(provider, provider2);
		
		doTestEssentialCaching(provider2);

		
	}

	public void testBasicCachingSemanticsBitmap() {
		IImageDataProvider provider = null;
		try {
			provider = cachingImageLoader.createImageDataProvider(
						imagePath.append("four_dots.bmp"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		doTestEssentialCaching(provider);

		// making a second provider should be the same
		IImageDataProvider provider2 = null;
		try {
			provider2 = cachingImageLoader.createImageDataProvider(
						imagePath.append("four_dots.bmp"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertSame(provider, provider2);
		
		doTestEssentialCaching(provider2);

		// make sure 
		
	}
	
	public void testBasicCachingSemanticsSVG() {
		IImageDataProvider provider = null;
		try {
			provider = cachingImageLoader.createImageDataProvider(
						imagePath.append("CEikImage.svg"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		doTestEssentialCaching(provider);

		// making a second provider should be the same
		IImageDataProvider provider2 = null;
		try {
			provider2 = cachingImageLoader.createImageDataProvider(
						imagePath.append("CEikImage.svg"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		assertSame(provider, provider2);
		
		doTestEssentialCaching(provider2);

		// make sure 
		
	}
	
	protected void doTestBasicLoadingSizes(IImageDataProvider provider,
			Point expectedSize, int expectedDepth,
			Pair<Point, RGB>[] pixels,
			Pair<Point, Integer>[] alphas) throws CoreException {
		
		doTestImageMatching(provider.getImageData(null), 
				expectedSize, expectedDepth, pixels, alphas);

		// now get double size
		Point dblSize = new Point(expectedSize.x * 2, expectedSize.y * 2);
		doTestImageMatching(provider.getImageData(dblSize), 
				dblSize, expectedDepth, scalePixels(pixels, expectedSize, dblSize),
				scaleAlphas(alphas, expectedSize, dblSize));

		// now get four times size
		Point fourSize = new Point(expectedSize.x * 4, expectedSize.y * 4);
		doTestImageMatching(provider.getImageData(fourSize), 
				fourSize, expectedDepth, scalePixels(pixels, expectedSize, fourSize),
				scaleAlphas(alphas, expectedSize, fourSize));

		// get double scaled width, to ensure we don't respect aspect
		Point dblWidth = new Point(expectedSize.x * 2, expectedSize.y);
		doTestImageMatching(provider.getImageData(dblWidth), 
				dblWidth, expectedDepth, scalePixels(pixels, expectedSize, dblWidth),
				scaleAlphas(alphas, expectedSize, dblWidth));

		// get double scaled height, to ensure we don't respect aspect
		Point dblHeight = new Point(expectedSize.x, expectedSize.y * 2);
		doTestImageMatching(provider.getImageData(dblHeight), 
				dblHeight, expectedDepth, scalePixels(pixels, expectedSize, dblHeight),
				scaleAlphas(alphas, expectedSize, dblHeight));

		// get half size, if legal
		if (expectedSize.x > 2 && expectedSize.y > 2) {
			Point halfSize = new Point(expectedSize.x / 2, expectedSize.y / 2);
			doTestImageMatching(provider.getImageData(halfSize), 
					halfSize, expectedDepth, scalePixels(pixels, expectedSize, halfSize),
					scaleAlphas(alphas, expectedSize, halfSize));
			
			// get quarter size, if legal
			if (expectedSize.x > 4 && expectedSize.y > 4) {
				Point quarterSize = new Point(expectedSize.x / 4, expectedSize.y / 4);
				doTestImageMatching(provider.getImageData(quarterSize), 
						quarterSize, expectedDepth, scalePixels(pixels, expectedSize, quarterSize),
						scaleAlphas(alphas, expectedSize, quarterSize));
			}
		}
		
		// error case: should be caught cleanly
		try {
			provider.getImageData(new Point(-1, -1));
		} catch (CoreException e) {
			
		} catch (Throwable t) {
			fail("Did not catch bad size");
		}
		try {
			provider.getImageData(new Point(0, 0));
		} catch (CoreException e) {
			
		} catch (Throwable t) {
			fail("Did not catch bad size");
		}
		
	}

	protected void doTestBasicLoadingSizes(IImageDataProvider provider,
			Point expectedSize, int expectedDepth,
			Pair<Point, RGB>[] pixels) throws CoreException {
		doTestBasicLoadingSizes(provider, expectedSize, expectedDepth, pixels, null);
	}

	protected void doTestBasicLoadingSizes(IImageDataProvider provider,
			Point expectedSize, int expectedDepth,
			Triple<Point, RGB, Integer>[] pixels) throws CoreException {
		
		doTestBasicLoadingSizes(provider, expectedSize, expectedDepth,
				removeAlpha(pixels), extractAlpha(pixels));

	}
	

	public void doTestLoadingSizesBMP(IImageLoader imageLoader) {
		IImageDataProvider provider = null;
		try {
			provider = imageLoader.createImageDataProvider(
						imagePath.append("four_dots.bmp"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		try {
			doTestBasicLoadingSizes(provider,
					new Point(2, 2), 24,
					new Pair[] {
						new Pair<Point, RGB>(new Point(0, 0), new RGB(255, 0, 0)),
						new Pair<Point, RGB>(new Point(1, 1), new RGB(255, 255, 0)) 
			});
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
	
	public void doTestLoadingSizesPNG(IImageLoader imageLoader) {
		// this image has transparency, but the image should not be flattened here
		IImageDataProvider provider = null;
		try {
			provider = imageLoader.createImageDataProvider(
						imagePath.append("four_dots_transp.png"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		try {
			doTestBasicLoadingSizes(provider,
					new Point(2, 2), 24,
					new Triple[] {
						new Triple<Point, RGB, Integer>(
								new Point(0, 0), new RGB(255, 0, 0), 255),
						new Triple<Point, RGB, Integer>(
								new Point(1, 0), new RGB(0, 255, 0), 255), 
						new Triple<Point, RGB, Integer>(
								new Point(0, 1), new RGB(0, 0, 255), 128),
						new Triple<Point, RGB, Integer>(
								new Point(1, 1), new RGB(255, 255, 0), 0) 
			});
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
	
	public void doTestLoadingSizesSVG(IImageLoader imageLoader) {
		IImageDataProvider provider = null;
		try {
			provider = imageLoader.createImageDataProvider(
						imagePath.append("four_rect.svg"));
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		try {
			doTestBasicLoadingSizes(provider,
					new Point(16, 16), 32,
					new Pair[] {
						new Pair<Point, RGB>(new Point(0, 0), new RGB(255, 0, 0)),
						new Pair<Point, RGB>(new Point(15, 0), new RGB(0, 255, 0)),
						new Pair<Point, RGB>(new Point(0, 15), new RGB(0, 0, 255)),
						new Pair<Point, RGB>(new Point(15, 15), new RGB(255, 255, 0)) 
			});
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
	

	public void testNonCachingSizes() {
		doTestLoadingSizesBMP(nonCachingImageLoader);
		doTestLoadingSizesPNG(nonCachingImageLoader);
		doTestLoadingSizesSVG(nonCachingImageLoader);
	}
	
	public void testCachingSizes() {
		doTestLoadingSizesBMP(cachingImageLoader);
		doTestLoadingSizesPNG(cachingImageLoader);
		doTestLoadingSizesSVG(cachingImageLoader);
	}
	
	public void testCaching() {
		cachingImageLoader.dispose();
		
		IImageDataProvider imageDataProvider = null;
		try {
			imageDataProvider = cachingImageLoader.createImageDataProvider(
					projectPath.append("data/extremeImages/huge.svg"));
			// this should be VERY fast after the first load; to account for
			// random system lag and/or garbage collections, assume that 10000 
			// more loads won't take more time than reloading from scratch
			// ten more times
			long start = System.currentTimeMillis();
			ImageData data = imageDataProvider.getImageData(null);
			assertNotNull(data);
			
			long loadTime = System.currentTimeMillis() - start;
			
			long deadLine = System.currentTimeMillis() + loadTime * 10;
			for (int i = 0; i < 10000; i++) {
				data = imageDataProvider.getImageData(null);
				assertNotNull(data);
				if (System.currentTimeMillis() > deadLine)
					fail("This is taking too long... is caching broken?");
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		
	}
	
	public void testBug5363() {
		nonCachingImageLoader.dispose();
		
		// this image suggests a viewbox much too large to render,
		// but its actual width and height are sensible.
		IImageDataProvider imageDataProvider = null;
		try {
			imageDataProvider = nonCachingImageLoader.createImageDataProvider(
					projectPath.append("data/extremeImages/canvas_huge_viewBox.svg"));
			ImageData data = imageDataProvider.getImageData(null);
			assertNotNull(data);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// This image suggests a viewbox and a width/height much too large to render.
		// The image's default size should be sensible. 
		try {
			imageDataProvider = nonCachingImageLoader.createImageDataProvider(
					projectPath.append("data/extremeImages/canvas_huge_width_height.svg"));
			ImageData data = imageDataProvider.getImageData(null);
			assertNotNull(data);
			assertTrue(data.width <= 2048);
			assertTrue(data.height <= 2048);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// Honor misguided requests to render a too-large image.
		try {
			imageDataProvider = nonCachingImageLoader.createImageDataProvider(
					projectPath.append("data/extremeImages/canvas_huge_viewBox.svg"));
			ImageData data = imageDataProvider.getImageData(new Point(10000, 10000));
			assertNotNull(data);
			// what is going on? it worked
			assertEquals(10000, data.width);
			assertEquals(10000, data.height);
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (OutOfMemoryError e) {
			// expected
		} catch (SWTError e) {
			// expected
		}

		// This image suggests a viewbox and a width/height much too large to render.
		// The image's default size should be sensible. 
		try {
			imageDataProvider = nonCachingImageLoader.createImageDataProvider(
					projectPath.append("data/extremeImages/canvas_huge_width_height.svg"));
			ImageData data = imageDataProvider.getImageData(null);
			assertNotNull(data);
			assertTrue(data.width <= 2048);
			assertTrue(data.height <= 2048);
		} catch (Exception e) {
			fail(e.getMessage());
		}

		// Honor misguided requests to render a too-large image.
		try {
			imageDataProvider = nonCachingImageLoader.createImageDataProvider(
					projectPath.append("data/extremeImages/canvas_huge_width_height.svg"));
			ImageData data = imageDataProvider.getImageData(new Point(10000, 10000));
			assertNotNull(data);
			// what is going on? it worked
			assertEquals(10000, data.width);
			assertEquals(10000, data.height);
		} catch (CoreException e) {
			fail(e.getMessage());
		} catch (OutOfMemoryError e) {
			// expected
		} catch (SWTError e) {
			// expected
		}

	}
}
