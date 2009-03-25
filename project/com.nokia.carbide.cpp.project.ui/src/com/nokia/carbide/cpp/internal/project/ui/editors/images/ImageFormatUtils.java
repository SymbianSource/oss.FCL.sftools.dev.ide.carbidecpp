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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.internal.project.ui.images.IImageSourceReferenceModel;
import com.nokia.carbide.cpp.ui.images.IImageModel;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

public class ImageFormatUtils {

	public static ImageFormat getDefaultImageFormat(IImageSourceReferenceModel model) {

		ImageFormat format = new ImageFormat(false, 0, 0);

		if (model.getImageSourceReference() instanceof IBitmapSourceReference) {
			IImageModel bitmapModel = model.getBitmapImageModel(false); 
				
			ImageData data = null;
			try {
				data = bitmapModel.getImageDescriptor(null).getImageData();
			} catch (CoreException e) {
				return format;
			}

			format.isColor = isColor(data);
			format.depth = getColorDepth(data, format.isColor);
			
			IImageModel maskModel = model.getMaskImageModel(false); 
			if (maskModel != null) {
				ImageData maskData = null;
				try {
					maskData = maskModel.getImageDescriptor(null).getImageData();
					format.maskDepth = getMaskDepth(maskData, model.getMaskSourceLocation());
				} catch (CoreException e) {
				}
			} else {
				format.maskDepth = 0;
			}
		} else {
			// for SVG, no querying
			format.isColor = true;
			format.depth = 32;
			format.maskDepth = 8;
		}
		return format;
	}

	/**
	 * Simplest check based on the filename.
	 * @param maskPath
	 * @return
	 */
	private static boolean isSoftMask(IPath maskPath) {
		return maskPath.lastSegment().toLowerCase().matches(".*_mask_soft.bmp"); //$NON-NLS-1$
	}

	/**
	 * Tell if the image is likely to be color.
	 * @param data
	 * @return
	 */
	private static boolean isColor(ImageData data) {
		if (data == null)
			return false;
		
		// assume that non-palette image is color
		if (data.palette.isDirect)
			return true;
		// else, scan the palette for non-grey colors
		for (int i = 0; i < data.palette.colors.length; i++) {
			RGB rgb = data.palette.colors[i];
			if (!isGrey(rgb))
				return true;
		}
		return false;
	}

	/**
	 * Tell if the color is greyscale.  Check for the simple greyscale
	 * where r=g=b and also check for values derived from the formula
	 * 0.30*R + 0.59*G + 0.11*B.
	 * @param rgb
	 * @return
	 */
	private static boolean isGrey(RGB rgb) {
		if (rgb.red == rgb.green && rgb.green == rgb.blue) // dumb grey
			return true;
		
		// get presumed grey value, 0 - 999
		int g_base = rgb.green * 1000 / 256;
		// red should be 30 / 59 of green
		int r_base = rgb.red * g_base * 30 / 59 / 1000;
		// blue should be 11 / 59 of green
		int b_base = rgb.blue * g_base * 11 / 59 / 1000;
		if (Math.abs(r_base - g_base) < 10 && Math.abs(g_base - b_base) < 10)
			return true;
		
		return false;
	}

	private static int getColorDepth(ImageData data, boolean isColor) {
		if (data == null)
			return 1;
		
		if (isColor) {
			switch (data.depth) {
			case 4:
			case 8:
			case 12:
			case 16:
			case 24:
				return data.depth;
			case 1:
			case 2:
				return 4;
			case 32:
			default:
				return 24;
			}
		}
		else {
			switch (data.depth) {
			case 1:
			case 2:
			case 4:
			case 8:
				return data.depth;
			default:
				return 8;
			}
		}
	}

	private static int getMaskDepth(ImageData data, IPath maskPath) {
		if (data == null)
			return 1;
		
		if (isSoftMask(maskPath)) {
			return 8;
		}
		
		// these are less perfect checks -- plenty of 
		// counterexamples abound.
		if (data.depth == 1 || data.width == 0 || data.height == 0)
			return 1;
		
		// stupid check: take the primary color in the four corners
		// and if they're white, assume it's a hard mask (excluded)
		int whiteCount = 0;
		if (isWhitePixel(data, 0, 0))
			whiteCount++;
		if (isWhitePixel(data, data.width - 1, 0))
			whiteCount++;
		if (isWhitePixel(data, 0, data.height - 1))
			whiteCount++;
		if (isWhitePixel(data, data.width - 1, data.height - 1))
			whiteCount++;
		
		if (whiteCount >= 3)
			return 1;
		else
			return 8;
	}

	/**
	 * Tell whether the pixel at the given coordinates is white.
	 * @param data
	 * @return
	 */
	private static boolean isWhitePixel(ImageData data, int x, int y) {
		if (data == null)
			return false;
		int pixel = data.getPixel(x, y);
		RGB rgb = data.palette.getRGB(pixel);
		return (rgb.red == 255 && rgb.green == 255 && rgb.blue == 255);
	}

	/**
	 * 
	 */
	public ImageFormatUtils() {
		super();
	}

}