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

import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.cpp.internal.api.utils.core.Triple;
import com.nokia.sdt.utils.drawing.ImageDump;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

public abstract class BaseTest extends TestCase {

	
	public BaseTest() {
		super();
	}

	/**
	 * @param name
	 */
	public BaseTest(String name) {
		super(name);
	}

	/**
	 * @return
	 */
	protected IPath getProjectPath() throws IOException {
		if (Platform.isRunning()) {
			URL url = FileLocator.find(TestsPlugin.getDefault().getBundle(), new Path("."), null);
			return new Path(FileLocator.toFileURL(url).getPath());
		} else {
			return new Path(".");
		}
	}

	/**
	 * @param pixels
	 * @return
	 */
	protected Pair<Point, RGB>[] removeAlpha(Triple<Point, RGB, Integer>[] pixels) {
		Pair[] pairs = new Pair[pixels.length];
		for (int idx = 0; idx <pixels.length; idx++)
			pairs[idx] = new Pair(pixels[idx].first, pixels[idx].second);
		return pairs;
	}

	/**
	 * @param pixels
	 * @return
	 */
	protected Pair<Point, Integer>[] extractAlpha(Triple<Point, RGB, Integer>[] pixels) {
		Pair<Point, Integer>[] alphas = new Pair[pixels.length];
		for (int idx = 0; idx <pixels.length; idx++)
			alphas[idx] = new Pair<Point, Integer>(pixels[idx].first, pixels[idx].third);
		return alphas;
	}

	/**
	 * Scale pixel data up.  Due to blending, test the extreme edges.
	 * A pixel of 1,1 inside a size of 2,2 really is at 1.9999, 1.9999, for instance.
	 * @param pixels
	 * @param i
	 * @param j
	 * @return
	 */
	protected Pair<Point, RGB>[] scalePixels(Pair<Point, RGB>[] pixels, 
			Point origSize, Point newSize) {
		Pair[] newPixels = new Pair[pixels.length];
		int idx = 0;
		for (Pair<Point, RGB> pixel : pixels) {
			float virtualX = (float)pixel.first.x * origSize.x / (origSize.x - 1);
			float virtualY = (float)pixel.first.y * origSize.y / (origSize.y - 1);
			int newX = (int) Math.floor(virtualX * (newSize.x - 1) / origSize.x);
			int newY = (int) Math.floor(virtualY * (newSize.y - 1) / origSize.y);
			Pair<Point, RGB> newPixel = new Pair<Point, RGB>(
					new Point(newX, newY),
					pixel.second);
			newPixels[idx++] = newPixel;
		}
		return newPixels;
	}

	protected Pair<Point, Integer>[] scaleAlphas(Pair<Point, Integer>[] alphas, 
			Point origSize, Point newSize) {
		if (alphas == null)
			return null;
		Pair[] newAlphas = new Pair[alphas.length];
		int idx = 0;
		for (Pair<Point, Integer> pixel : alphas) {
			float virtualX = (float)pixel.first.x * origSize.x / (origSize.x - 1);
			float virtualY = (float)pixel.first.y * origSize.y / (origSize.y - 1);
			int newX = (int) Math.floor(virtualX * (newSize.x - 1) / origSize.x);
			int newY = (int) Math.floor(virtualY * (newSize.y - 1) / origSize.y);
			Pair<Point, Integer> newPixel = new Pair<Point, Integer>(
					new Point(newX, newY),
					pixel.second);
			newAlphas[idx++] = newPixel;
		}
		return newAlphas;
	}
	protected void doTestImageMatching(ImageData imageData,
			Point expectedSize, int expectedDepth, 
			Pair<Point, RGB>[] pixels,
			Pair<Point, Integer>[] alphas) {
		assertEquals(expectedSize.x, imageData.width);
		assertEquals(expectedSize.y, imageData.height);
		assertEquals(expectedDepth, imageData.depth);
		
		for (Pair<Point, RGB> pixel : pixels) {
			RGB rgb = imageData.palette.getRGB(imageData.getPixel(pixel.first.x, pixel.first.y));
			if (!pixel.second.equals(rgb)) {
				fail("pixel mismatch at " + pixel.first + "; expected " + pixel.second + " but got " + rgb);
			}
		}
		
		if (alphas != null) {
			for (Pair<Point, Integer> pixel : alphas) {
				int alpha = imageData.getAlpha(pixel.first.x, pixel.first.y);
				if (!pixel.second.equals(alpha)) {
					fail("alpha mismatch at " + pixel.first + "; expected " + pixel.second + " but got " + alpha);
				}
			}
			
		}
	}

	// obejct to check for transparent (color) pixel
	static RGB transparent = new RGB(0, 0, 0);
	
	static class Pixel {
		private int alpha;
		int x, y;
		RGB rgb;
		public Pixel(int i, int j, RGB theRgb, int theAlpha) {
			x = i;
			y = j;
			rgb = theRgb;
			alpha = theAlpha;
		}

		public Pixel(ImageData data, int x2, int y2) {
			x = x2;
			y = y2;
			rgb = data.palette.getRGB(data.getPixel(x2, y2));
			alpha = data.getAlpha(x2, y2);
		}

		public Pixel(int i, int j, RGB rgb) {
			this(i, j, rgb, 255);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Pixel("+x+","+y+"@"+rgb+"/"+alpha+")";
		}
		
//
		public void match(ImageData data) {
			int thePixel = data.getPixel(x,y);
			int theAlpha = data.getAlpha(x,y);
			if (rgb == transparent) {
				if (data.transparentPixel != -1) {
					assertEquals(thePixel, data.transparentPixel);
				} 
				else
					fail("no transparency");
			} else {
				if (rgb != null)
					assertEquals(rgb, data.palette.getRGB(thePixel));
				assertEquals(alpha, theAlpha);
			}
		}
	}

	protected void checkImage(ImageData data, int x, int y, Pixel[] pixels) {
		assertNotNull(data);
		assertEquals(x, data.width);
		assertEquals(y, data.height);
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < pixels.length; i++) {
			Pixel p = pixels[i];
			try {
				p.match(data);
			} catch (Throwable t) {
				buffer.append(t.getMessage());
				buffer.append("\n");
			}
		}
		if (buffer.length() > 0)
			fail(buffer.toString());
	}

	class ImageDumpWindow extends ApplicationWindow {
        private ImageData data;

		public ImageDumpWindow(ImageData data) {
            super(null);
            this.data = data;
        }
        
        public void run() {
            setBlockOnOpen(false);
            Image image = new Image(Display.getCurrent(), data); //$NON-NLS-1$
            ImageDump dump = new ImageDump(getShell(), image);
            dump.open();
            
        }
    }
}