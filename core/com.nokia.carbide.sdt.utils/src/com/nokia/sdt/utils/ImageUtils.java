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
package com.nokia.sdt.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.ui.IColorConverter;

import java.awt.image.BufferedImage;

/**
 * Symbian-specific image utilities
 * 
 */
public abstract class ImageUtils {

    public static final int ALIGN_LEFT = -1;
    public static final int ALIGN_TOP = -1;
    public static final int ALIGN_CENTER = 0;
    /** Center if image fits, else left. 
     * Also used in MultiImageInfo to indicate an SVG
     * image is pushed to the left side. */
    public static final int ALIGN_CENTER_OR_LEFT = 1;
    /** Center if image fits, else top. */
    public static final int ALIGN_CENTER_OR_TOP = 1;
    public static final int ALIGN_RIGHT = 2;
    public static final int ALIGN_BOTTOM = 2;
	private static boolean alphaSupportValidated;
	private static boolean alphaSupported;

    /**
     * Takes image data for the source and mask image of Symbian-compatible
     * bitmaps, and returns the masked image. Uses the transparent
     * and substitute colors values as defined in com.nokia.sdt.utils.drawing.GC
     * @param device
     * @param srcData
     * @param maskData
     * @return the masked image -- you own it
     * @see com.nokia.sdt.utils.drawing.GC
     */
    public static Image createMaskedImage(Device device, 
            ImageData srcData, ImageData maskData) {
    	return new Image(device, createMaskedImageData(srcData, maskData));
    }
    
    /**
     * Takes image data for the source and mask image of Symbian-compatible
     * bitmaps, and returns the masked image. Uses the transparent
     * and substitute colors values as defined in com.nokia.sdt.utils.drawing.GC
     * @param srcData
     * @param maskData
     * @return the masked image data
     * @see com.nokia.sdt.utils.drawing.GC
     */
    public static ImageData createMaskedImageData( 
            ImageData srcData, ImageData maskData) {
    	if (maskData == null)
    		return srcData;
    	if (srcData == null)
    		return null;
        
        int width = srcData.width;
        int height = srcData.height;
        
        RGB transparentRGB = com.nokia.sdt.utils.drawing.GC.TRANSPARENT_RGB_VALUE;
        RGB substituteRGB = com.nokia.sdt.utils.drawing.GC.SUBSTITUTE_RGB_VALUE;
    
        // The pixel value to detect if src image collides with our transparent color.
        // For indexed palettes an exception will be throw if the palette doesn't
        // contain this color. If it doesn't we can skip checking for it.
        int srcTransparentPixel = 0;
        boolean checkForCollisions = false;
        if (paletteContainsColor(srcData.palette, transparentRGB)) {
            srcTransparentPixel = srcData.palette.getPixel(transparentRGB);
            checkForCollisions = true;
        }
        
        // the pixel value for white in the mask image
        int maskWhitePixel = maskData.palette.getPixel(new RGB(255, 255, 255));

        // Initialize the image data to be used for the result image
        PaletteData pal = ImageUtils.createStandardPaletteData();

        ImageData destData = new ImageData(width, height, 32, pal);
        
        // The pixel value for the transparent color in the destination image
        // Since it's a direct palette it has all RGB values
        int destTransparentPixel = destData.palette.getPixel(transparentRGB);
        int destSubstituePixel = destData.palette.getPixel(substituteRGB);
        destData.transparentPixel = destTransparentPixel;

        // The mask should be white wherever the image is transparent
        // and black where it is opaque. Because we're not trying for 
        // maximum compatibility we'll take anything not white to opaque.
        // Loop over source and mask data. Wherever the mask is
        // transparent we set the source pixel to the transparent color
        // Wherever the source pixel is meant to be opaque, if it 
        // matches the transparent color we'll nudge it to the subsitute
        // color.
                            
        int srcLine[] = new int[width];
        int maskLine[] = new int[maskData.width];
        int destLine[] = new int[width];
        int maskY = 0;
        for (int line = 0; line < height; line++) {
            srcData.getPixels(0, line, width, srcLine, 0);
            maskData.getPixels(0, maskY, maskData.width, maskLine, 0);
            int maskX = 0;
            for (int x = 0; x < srcLine.length; x++) {
                int srcPixel = srcLine[x];
                int maskPixel = maskLine[maskX];
                if (maskPixel != maskWhitePixel) {
                    if (checkForCollisions && srcPixel == srcTransparentPixel)
                        destLine[x] = destSubstituePixel;
                    else {
                        RGB pixelColor = srcData.palette.getRGB(srcPixel);
                        destLine[x] = destData.palette.getPixel(pixelColor);
                    }
                }
                else {
                    destLine[x] = destTransparentPixel;
                }
                maskX++;
                if (maskX == maskData.width)
                    maskX = 0;
            }
            maskY++;
            if (maskY == maskData.height)
                maskY = 0;
            destData.setPixels(0, line, destLine.length, destLine, 0);
        }
    
        return destData;
    }

    static boolean paletteContainsColor(PaletteData pd, RGB color) {
        boolean result = true;
        try {
            pd.getPixel(color);
        } catch (IllegalArgumentException x) {
            result = false;
        }
        return result;
    }

    /**
     * Create an image from a bitmap and an alpha mask.
     * This takes the color value of the mask and uses
     * it for the alpha of the image.  This is not what
     * S60 does.
     * @param device
     * @param imageData
     * @param maskData
     * @return new image -- you own it
     */
    public static Image createAlphaMaskedImage(Device device,
            ImageData imageData, ImageData maskData) {

        // copy image
        ImageData data = new ImageData(imageData.width, imageData.height,
                imageData.depth, imageData.palette, imageData.scanlinePad,
                imageData.data);

        // substitute alpha values
        byte[] alphas;
        int perLine;
        int perPixel;
        if (maskData.depth != 8) {
            
            perLine = maskData.width;
            perPixel = maskData.bytesPerLine / maskData.width;
            int totalPixels = maskData.width * maskData.height;
            alphas = new byte[totalPixels];
            if (maskData.depth > 8) {
                int shift = maskData.depth - 8;
                int[] intAlphas = new int[totalPixels];
                maskData.getPixels(0, 0, totalPixels, intAlphas, 0);
                for (int i = 0; i < totalPixels; i++) {
                    alphas[i] = (byte)(intAlphas[i] >>> shift);
                }
            } else {
                maskData.getPixels(0, 0, maskData.width * maskData.height, alphas, 0);
            }
            
            /*
            int srcIdx = 0;
            int dstIdx = 0;
            int bytesPerPixel = maskData.depth / 8;
            for (int y = 0; y < maskData.height; y++) {
                srcIdx = y * maskData.bytesPerLine;
                for (int x = 0; x < maskData.width; x++) {
                    alphas[dstIdx] = maskData.data[srcIdx];
                    srcIdx += bytesPerPixel;
                    dstIdx++;
                }
            }*/
        } else {
            perLine = maskData.bytesPerLine;
            perPixel = 1;
            alphas = maskData.data;
        }

        if (imageData.width == maskData.width && imageData.height == maskData.height) {
            for (int y = 0; y < data.height; y++) {
                data.setAlphas(0, y, maskData.width, alphas, perLine * y);
            }
        } else {
            // dissimilar sizes: need to wrap (or take part of) the mask
            int maskY = 0;
            
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x += maskData.width) {
                    int toWrite = Math.min(data.width - x, maskData.width);
                    data.setAlphas(x, y, toWrite, 
                            alphas, maskData.width * perPixel * maskY);
                }
                maskY++;
                if (maskY == maskData.height)
                    maskY = 0;
            }
            
        }
        
        Image img = new Image(Display.getDefault(), data);
        return img;
    }

    /**
     * Create an image from a bitmap and a mask.
     * This uses the color value of the mask as the alpha 
     * of the image and combines the color with the bitmap's color.
     * @param imageData
     * @param maskData
     * @return new image -- you own it
     */
    public static Image createCombinedImage(Device device,
            ImageData imageData, ImageData maskData, boolean softMask) {
    	return new Image(device, createCombinedImageData(imageData, maskData, softMask));
    }
    
    /**
	 * Create an image from a bitmap and a mask.
	 * This uses the color value of the mask as the alpha 
	 * of the image and combines the color with the bitmap's color.
	 * @param imageData
	 * @param maskData
	 * @return new image data
	 */
	public static ImageData createCombinedImageData(
	        ImageData imageData, ImageData maskData, boolean softMask) {
	
	    // copy image
		byte[] rgbs = new byte[imageData.data.length];
	    ImageData data = new ImageData(imageData.width, imageData.height,
	            imageData.depth, imageData.palette, imageData.scanlinePad,
	            rgbs);
	
	    // substitute alpha values
	    byte[] alphas;
	    int perLine;
	    if (maskData.depth != 8) {
	        
	        perLine = maskData.width;
	        int totalPixels = maskData.width * maskData.height;
	        alphas = new byte[totalPixels];
	        if (maskData.depth > 8) {
	        	// Convert the RGB alpha data to the byte format,
	        	// taking the greyscale component (.3R + .59G + .11B,
	        	// or 3*r+6*g+b / 10
	            int[] intAlphas = new int[totalPixels];
	            maskData.getPixels(0, 0, totalPixels, intAlphas, 0);
	            for (int i = 0; i < totalPixels; i++) {
	            	RGB rgb = maskData.palette.getRGB(intAlphas[i]);
	                alphas[i] = (byte) ((rgb.red * 3 + rgb.green*6 + rgb.blue) / 10);
	            }
	        } else {
	            maskData.getPixels(0, 0, maskData.width * maskData.height, alphas, 0);
	        }
	    } else {
	        perLine = maskData.bytesPerLine;
	        alphas = maskData.data;
	    }
	
	    if (imageData.width == maskData.width && imageData.height == maskData.height) {
	        for (int y = 0; y < data.height; y++) {
	            data.setAlphas(0, y, maskData.width, alphas, perLine * y);
	        }
	    } else {
	        // dissimilar sizes: need to wrap (or take part of) the mask
	        int maskY = 0;
	        
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x += maskData.width) {
	                int toWrite = Math.min(data.width - x, maskData.width);
	                data.setAlphas(x, y, toWrite, 
	                        alphas, maskData.width * maskY);
	            }
	            maskY++;
	            if (maskY == maskData.height)
	                maskY = 0;
	        }
	        
	    }
	    
	    // combine pixels
	    int[] pixels = new int[imageData.width];
	    int maskY = 0;
	    for (int y = 0; y < imageData.height; y++) {
	    	if (maskY >= maskData.height)
	    		maskY = 0;
	    	imageData.getPixels(0, y, imageData.width, pixels, 0);
	    	int maskX = 0;  
	        for (int x = 0; x < imageData.width; x++) {
	        	if (maskX >= maskData.width)
	        		maskX = 0;
	        	RGB rgb = imageData.palette.getRGB(pixels[x]);
	        	RGB maskRgb = maskData.palette.getRGB(maskData.getPixel(maskX, maskY));
	        	int alpha;
	        	if (!softMask) {
	        		alpha = data.getAlpha(x, y);
	        		alpha = 255 - alpha;
	        		data.setAlpha(x, y, alpha);
	        		rgb.red = ((rgb.red * alpha) + (maskRgb.red * (255 - alpha))) / 255;
	        		rgb.green = ((rgb.green * alpha) + (maskRgb.green * (255 - alpha))) / 255;
	        		rgb.blue = ((rgb.blue * alpha) + (maskRgb.blue * (255 - alpha))) / 255;
	        	} else {
	        		/*alpha = data.getAlpha(x, y);
	        		rgb.red = (rgb.red * alpha) / 255;
	        		rgb.green = (rgb.green * alpha ) / 255;
	        		rgb.blue = (rgb.blue * alpha) / 255;*/
	        		/*if (alpha != 0) {
	        			rgb.red = 255 * (rgb.red - (255 - alpha)) / alpha;
	        			rgb.green = 255 * (rgb.green - (255 - alpha)) / alpha;
	        			rgb.blue = 255 * (rgb.blue - (255 - alpha)) / alpha;
	        		}*/
	        	}
	        	pixels[x] = data.palette.getPixel(rgb);
	        	maskX++;
	        }
	        maskY++;
	    	data.setPixels(0, y, imageData.width, pixels, 0);
	    }
	    return data;
	}

	/**
     * Create a tiled version of an image.  The destination may be smaller, the
     * same size, or larger than the original.
     * @param imageData
     * @param newSize the new size of the tiled image
     * @return new image data
     */
    public static ImageData createTiledImageData(
            ImageData imageData, Point newSize) {

        // new image
        ImageData data = new ImageData(newSize.x, newSize.y,
                imageData.depth, imageData.palette);
        
        data.transparentPixel = imageData.transparentPixel;
        data.alpha = imageData.alpha;
        
        if (imageData.alphaData != null) {
        	data.alphaData = new byte[newSize.x * newSize.y];
        }
        
        byte[] bytes = new byte[imageData.width * 4];
        int[] ints = new int[imageData.width];
        int srcY = 0;
        for (int dstY = 0; dstY < newSize.y; dstY++) {
        	int srcX = 0;
        	for (int dstX = 0; dstX < newSize.x; dstX += imageData.width) {
        		int widthToUse = Math.min(newSize.x - dstX, imageData.width);
        		if (imageData.depth <= 8) {
        			imageData.getPixels(srcX, srcY, widthToUse, bytes, 0);
        			data.setPixels(dstX, dstY, widthToUse, bytes, 0);
        		} else {
        			imageData.getPixels(srcX, srcY, widthToUse, ints, 0);
        			data.setPixels(dstX, dstY, widthToUse, ints, 0);
        		}
        		
        		if (data.alphaData != null) {
        			imageData.getAlphas(srcX, srcY, widthToUse, bytes, 0);
        			data.setAlphas(dstX, dstY, widthToUse, bytes, 0);
        		}

        		// this always remains zero
        		srcX = 0;
        	}
        	srcY++;
        	if (srcY >= imageData.height) {
        		srcY = 0;
        	}
        }
        return data;
    }

    
    /**
     * Scale the given image to the new size.
     * A new image is always returned.<p>
     * Note: the ILookAndFeel has a getImage() call which also scales
     * and caches the scaled image.
     * @param source
     * @param newSize
     * @return new image which must be disposed
     */
    public static Image scaleImage(Device device, Image source, Point newSize) {
    	return scaleImage(device, source, newSize, false);
    }

    /**
     * Scale the given image to the new size.
     * A new image is always returned.<p>
     * Note: the ILookAndFeel has a getImage() call which also scales
     * and caches the scaled image.
     * @param source
     * @param newSize
     * @param preserveAspect if true, preserve aspect ratio
     * @return new image which must be disposed
     */
    public static Image scaleImage(Device device, Image source, Point newSize, boolean preserveAspect) {
    	return scaleImage(device, source, newSize, preserveAspect, false);
    }

    /**
     * Scale the given image to the new size.
     * A new image is always returned.<p>
     * Note: the ILookAndFeel has a getImage() call which also scales
     * and caches the scaled image.
     * @param source
     * @param newSize
     * @param preserveAspect if true, preserve aspect ratio
     * @param padToSize if preserveAspect is true and this is true, return an 
     * image of exactly newSize.  This is useful for tables, which on Windows
     * force image sizes to be the same for every row.  
     * @return new image which must be disposed
     */
    public static Image scaleImage(Device device, Image source, Point newSize, boolean preserveAspect, boolean padToSize) {
        ImageData scaledImageData = scaleImageData(source.getImageData(), newSize, preserveAspect, padToSize);
        Image newImage = new Image(device, scaledImageData);
        return newImage;
    }

    /**
     * Scale the given image data to the new size.
     * @param source
     * @param newSize size to scale to
     * @param preserveAspect if true, preserve aspect ratio
     * @param padToSize if preserveAspect is true and this is true, return an 
     * image of exactly newSize.  This is useful for tables, which on Windows
     * force image sizes to be the same for every row.  
     * @return new image data
     * @throw {@link IllegalArgumentException} for invalid size
     */
	public static ImageData scaleImageData(ImageData source, Point newSize,
			boolean preserveAspect, boolean padToSize) {
		Check.checkArg(source);
		Check.checkArg(newSize);
		if (!(newSize.x > 0 && newSize.y > 0))
			throw new IllegalArgumentException("Invalid scaling size: " + newSize); //$NON-NLS-1$
    	Point scaledSize = newSize; 
    	if (preserveAspect) {
    		Point curSize = new Point(source.width, source.height);
    		scaledSize = scaleSizeToSize(curSize, newSize);
    	}
		
		if (!(scaledSize.x > 0 && scaledSize.y > 0))
			throw new IllegalArgumentException("Invalid scaled size: " + scaledSize + " from " + newSize); //$NON-NLS-1$
    	
		ImageData scaledImageData = source.scaledTo(scaledSize.x, scaledSize.y);
        if (padToSize && !newSize.equals(scaledSize)) {
        	Check.checkState(scaledSize.x < newSize.x || scaledSize.y < newSize.y);
        	ImageData paddedImageData = new ImageData(newSize.x, newSize.y,
        			scaledImageData.depth,
        			scaledImageData.palette);
        	paddedImageData.transparentPixel = scaledImageData.transparentPixel;
        	paddedImageData.alpha = scaledImageData.alpha;
        	int[] pixels = new int[newSize.x];
        	byte[] alphas = new byte[newSize.x];
        	
        	int white = -1; //scaledImageData.transparentPixel != -1 ? scaledImageData.transparentPixel :
        	try {
        		white = scaledImageData.palette.getPixel(new RGB(255, 255, 255));
        	} catch (IllegalArgumentException e) {
        		white = -1;
        	}
        	for (int i = 0; i < pixels.length; i++) {
        		pixels[i] = white;
        	}
        	// alphas are all 0
        	
        	for (int y = 0; y < newSize.y; y++) {
        		paddedImageData.setPixels(0, y, newSize.x, pixels, 0);
        	}
        	if (paddedImageData.alphaData != null) {
            	for (int y = 0; y < newSize.y; y++) {
            		paddedImageData.setAlphas(0, y, newSize.x, alphas, 0);
            	}
        	}
        		
        	if (scaledSize.x < newSize.x) {
        		int offs = (newSize.x - scaledSize.x) / 2;
    			for (int y = 0; y < newSize.y; y++) {
    				scaledImageData.getPixels(0, y, scaledSize.x, pixels, 0);
    				paddedImageData.setPixels(offs, y, scaledSize.x, pixels, 0);
    				if (scaledImageData.alphaData != null) {
        				scaledImageData.getAlphas(0, y, scaledSize.x, alphas, 0);
        				paddedImageData.setAlphas(offs, y, scaledSize.x, alphas, 0);
    				}
    			}
        	} else {
        		int offs = (newSize.y - scaledSize.y) / 2;
    			for (int y = 0; y < scaledSize.y; y++) {
    				scaledImageData.getPixels(0, y, scaledSize.x, pixels, 0);
    				paddedImageData.setPixels(0, y + offs, scaledSize.x, pixels, 0);
    				if (scaledImageData.alphaData != null) {
        				scaledImageData.getAlphas(0, y, scaledSize.x, alphas, 0);
        				paddedImageData.setAlphas(0, y + offs, scaledSize.x, alphas, 0);
    				}
    			}
        	}
        	
        	scaledImageData = paddedImageData;
        }
		return scaledImageData;
	}

    /** Copy an image, which must be disposed
     */
    public static Image copyImage(Device device, Image image) {
        return new Image(device, image, SWT.IMAGE_COPY);
    }

    /**
     * Constrain the incoming size to be within the minimum and maximum
     * @param imgSize
     * @param minimumSize
     * @param maximumSize
     * @return new size
     */
    public static Point constrainSizePreservingAspect(Point imgSize, Point minimumSize, Point maximumSize) {
        Point curSize = new Point(0, 0);
        if ((imgSize.x >= minimumSize.x && imgSize.y >= minimumSize.y)
        		&& (imgSize.x <= maximumSize.x && imgSize.y <= maximumSize.y)) {
            curSize.x = imgSize.x;
            curSize.y = imgSize.y;
            return curSize;
        }
        
        boolean scaledUp = false;
        if (imgSize.x < minimumSize.x || imgSize.y < minimumSize.y) {
            // scale the image up by powers of two until it fits
        	curSize.x = imgSize.x;
        	curSize.y = imgSize.y;
        	scaledUp = true;
        	do {
        		curSize.x *= 2;
        		curSize.y *= 2;
        	} while (curSize.x < minimumSize.x && curSize.y < minimumSize.y);
        }
        
        /*
            // 8x4 in 16x16 
            if (imgSize.x > imgSize.y) {
                // __x16
                curSize.y = minimumSize.y;
                // 16*8/4 == 32x16
                curSize.x = minimumSize.y * imgSize.x / imgSize.y;
            } else {
                // 4x8 in 16x16
                // 16x___
                curSize.x = minimumSize.x;
                // 16*8/4 == 16x32
                curSize.y = minimumSize.x * imgSize.y / imgSize.x;
            }*/
        if (!scaledUp && (imgSize.x >= maximumSize.x || imgSize.y >= maximumSize.y)) {
            // scale the image down by powers of two until it fits
        	curSize.x = imgSize.x;
        	curSize.y = imgSize.y;
        	scaledUp = true;
        	do {
        		curSize.x /= 2;
        		curSize.y /= 2;
        	} while (curSize.x > maximumSize.x && curSize.y > maximumSize.y);
        	
        	/*
            // 2048x1024 in 256x256 
            if (imgSize.x > imgSize.y) {
                // 256x___
                curSize.x = maximumSize.x;
                // 256*1024/2048 = 256x128
                curSize.y = maximumSize.x * imgSize.y / imgSize.x;
            } else {
                // 1024x2048 in 256x256
                // ___x256
                curSize.y = maximumSize.y;
                // 256*1024/2048 = 128x256
                curSize.x = maximumSize.y * imgSize.x / imgSize.y;
            }*/
        	
        }
        return curSize;
    }

    /**
     * Constrain the incoming size in an aspect ratio preserving
     * way 
     * @param insize the incoming size
     * @param size the container size
     * @return size scaled to fit
     */
    public static Point scaleSizeToSize(Point insize, Point size) {
        if (size.x == 0 || size.y == 0)
            return size;
        
        if (insize.x == 0 || insize.y == 0)
            return insize;
            
        int iw, ih;
        if ((float)size.x / size.y > (float)insize.x / insize.y) {
            iw = insize.x * size.y / insize.y;
            if (iw == 0)
            	iw = 1;
            ih = size.y;
        } else {
            iw = size.x;
            ih = insize.y * size.x / insize.x;
            if (ih == 0)
            	ih = 1;
        }
        return new Point(iw, ih);
    }

    /**
     * Convert the given image to another bit depth.  This takes
     * shortcuts and does not make a new image if there are no changes.
     * @param device
     * @param image incoming image
     * @param depth target bit depth
     * @param isColor true: keep color, false: convert to greyscale
     * @param converter handler for changing bit depths
     * @param outColors output color palette, or null for no conversion
     * @return null if no conversion occured, else new image, you own it
     */
    public static Image convertToDepth(Device device, Image image, int depth, boolean isColor, 
            IColorConverter converter, RGB[] outColors) {
        ImageData imageData = convertToDepth(image.getImageData(), depth, converter, outColors);
        image = new Image(device, imageData);
        return image;
    }

    /**
     * Convert the given image data to another bit depth 
     * @param image incoming image
     * @param depth target bit depth
     * @param converter handler for changing bit depths or <code>null</code>
     * @param outColors output color palette, or null for no conversion
     * @return image data, never null; may be the same as the incoming data
     */
	public static ImageData convertToDepth(ImageData imageData, 
			int depth, 
			IColorConverter converter, RGB[] outColors) {
		if (imageData == null)
			return null;
		
        // see if the image has a different bit depth
        // than the desired bit depth
		PaletteData palette = imageData.palette;
        boolean hasTransparentPixel = (imageData.transparentPixel != -1);

        // use this palette for uniformity with hardcoded tables used in the converters
        PaletteData newPalette = new PaletteData(0xff, 0x00ff00, 0xff0000);
        ImageData newData = new ImageData(imageData.width, imageData.height, 32, newPalette);
        if (hasTransparentPixel)
        	newData.transparentPixel = imageData.transparentPixel;
        
        // Converting to low bit depths means using a palette.
        //
        // NOTE: Series60 has an additional interpretation of 256-color palettes,
        // which is that certain key colors in positions 253 and 254 mean that
        // the range 226-241 are "scheme colors" and replaced according to the skin.
        
        if (depth <= 8) {
        	
       		int[] scanline = new int[imageData.width];
       		byte[] alphaLine = new byte[imageData.width];
    		for (int y = 0; y < imageData.height; y++) {
	            imageData.getPixels(0, y, imageData.width, scanline, 0);
	            imageData.getAlphas(0, y, imageData.width, alphaLine, 0);
	            for (int x = 0; x < imageData.width; x++) {
	            	RGB inRgb = palette.getRGB(scanline[x]);
	            	if (hasTransparentPixel && scanline[x] == imageData.transparentPixel)
	            		scanline[x] = newData.transparentPixel;
	            	else
	            		scanline[x] = converter.convertPixel(newPalette, outColors, inRgb);
	            }
	            newData.setPixels(0, y, imageData.width, scanline, 0);
	            newData.setAlphas(0, y, imageData.width, alphaLine, 0);
	        }
	        return newData;
        }
    
        // Convert the palette to use fewer bits, by
        // selecting upper bits of existing image.
        // Since we're still displaying in a higher bitdepth,
        // we need to convert the masked-off bits to all zeroes 
        // or all ones to preserve luminosity.
        
        int redBits = countBits(palette.redMask);
        int greenBits = countBits(palette.blueMask);
        int blueBits = countBits(palette.greenMask);
        
        // exit early if converting up
        if (!palette.isDirect || redBits + greenBits + blueBits <= depth) 
            return imageData;
        
        int redMask = palette.redMask;
        int greenMask = palette.greenMask;
        int blueMask = palette.blueMask;
        int redBias = 0;
        int greenBias = 0;
        int blueBias = 0;
        switch (depth) {
            
        case 12:
            // use 4, 4, 4
            redBias = (redMask >>> 4) & redMask;
            greenBias = (greenMask >>> 4) & greenMask;
            blueBias = (blueMask >>> 4) & blueMask;
            redMask &= redMask << (redBits - 4);
            greenMask &= greenMask << (greenBits - 4);
            blueMask &= blueMask << (blueBits - 4);
            break;
        case 16:
            // use 5, 5, 5
            redBias = (redMask >>> 5) & redMask;
            greenBias = (greenMask >>> 5) & greenMask;
            blueBias = (blueMask >>> 5) & blueMask;
            redMask &= redMask << (redBits - 5);
            greenMask &= greenMask << (greenBits - 5);
            blueMask &= blueMask << (blueBits - 5);
            break;
        case 24:
        case 32:
            return imageData;
        }
        
        /* Convert the pixels. */
        Check.checkState((redBias & redMask) == 0);
        Check.checkState((greenBias & greenMask) == 0);
        Check.checkState((blueBias & blueMask) == 0);
        int colorMask = (redMask | greenMask | blueMask);
        int redTest = (redBias >>> 1) & redBias;
        int greenTest = (greenBias >>> 1) & greenBias;
        int blueTest = (blueBias >>> 1) & blueBias;
        
        int[] scanline = new int[imageData.width];
        byte[] alphas = new byte[imageData.width];
        for (int y=0; y<imageData.height; y++) {
            imageData.getPixels(0, y, imageData.width, scanline, 0);
            imageData.getAlphas(0, y, imageData.width, alphas, 0);
            for (int x=0; x<imageData.width; x++) {
                int pixel = scanline[x];
                if (pixel != imageData.transparentPixel) {
                    // tell if the masked-off red, green, or blue parts are 
                    // closer to on (>= 0.5) or off (< 0.5), and select
                    // to round them up if necessary
                    int rb = (pixel & redTest) != 0 ? redBias : 0;
                    int gb = (pixel & greenTest) != 0 ? greenBias : 0;
                    int bb = (pixel & blueTest) != 0 ? blueBias : 0;
                    
                    // mask out the lower bits, but round the components if necessary
                    pixel = (pixel & colorMask) | (rb | gb | bb);
                    
                    if (converter != null) {
	                    RGB inRgb = palette.getRGB(pixel);
	                    scanline[x] = converter.convertPixel(newPalette, outColors, inRgb);
                    } else {
                    	scanline[x] = newPalette.getPixel(palette.getRGB(pixel));
                    }
                }
            }
            newData.setPixels(0, y, imageData.width, scanline, 0);
            newData.setAlphas(0, y, imageData.width, alphas, 0);
        }
        return newData;
	}   

    /**
     * Determine the bit depth of the image.
     * @param image incoming image
     * @return bit depth (may be larger than actual represented depth0
     */
    public static int getImageBitDepth(Image image) {
        ImageData imageData = image.getImageData();
        PaletteData palette = imageData.palette;
        if (!palette.isDirect)
        	return palette.colors.length;
    
        int redBits = countBits(palette.redMask);
        int greenBits = countBits(palette.blueMask);
        int blueBits = countBits(palette.greenMask);

        return redBits + greenBits + blueBits; 
    }   

    /**
     * Get a greyscale version of the image data.  
     * @param imageData source image data
     * @return new image data
     */
    public static ImageData convertToGreyscale(ImageData imageData) {
    	if (imageData == null)
    		return null;
    	Image temp = new Image(null, imageData);
        Image newImage = new Image(null, temp, SWT.IMAGE_GRAY);
        temp.dispose();
        ImageData data = newImage.getImageData();
        newImage.dispose();
        return data;
    }

    /**
     * Get a greyscale version of the image.  
     * @param device
     * @param image source image
     * @return new image, you own it
     */
    public static Image convertToGreyscale(Device device, Image image) {
        Image newImage = new Image(device, image, SWT.IMAGE_GRAY);
        return newImage;
    }
    
    /**
     * Count bits in mask
     */
    private static int countBits(int mask) {
        int count = 0;
        while (mask != 0) {
            count++;
            mask = mask & (mask - 1);
        }
        return count;
    }

    /**
     * This flattens an RGBA image to the transparent-pixel model, optionally
     * pre-blending it with the expected background.
     * <p>
     * If blending, then for each pixel, if the alpha component for a pixel is
     * translucent, the color is blended with the background and the outgoing
     * pixel is opaque; otherwise, the pixel takes the
     * transparent pixel value.
     * 
     * @param img
     * @param background background color, or null for white
     * @param blend true: blend with background
     * @param keepTransparent true: keep transparency via a transparent pixel, else set to background
     * @return new Image (even if the img is not RGBA)
     */
    public static Image flattenAlphaMaskedImage(Device device, Image img, Color background, boolean blend, boolean keepTransparent) {
    	Check.checkArg(keepTransparent || background != null);
    	
        if (img == null)
            return null;
        
        ImageData newData = flattenAlphaMaskedImageData(img.getImageData(), background.getRGB(),
        		blend, keepTransparent);
        
        return new Image(device, newData);
    }

    /**
     * This flattens RGBA image data to the transparent-pixel model, optionally
     * pre-blending it with the expected background.
     * <p>
     * If blending, then for each pixel, if the alpha component for a pixel is
     * translucent, the color is blended with the background and the outgoing
     * pixel is opaque; otherwise, the pixel takes the
     * transparent pixel value.
     * 
     * @param imgData
     * @param bgRGB background color, or null for white
     * @param blend true: blend with background
     * @param keepTransparent true: keep transparency via a transparent pixel, else set to background
     * @return new ImageData
     */
    public static ImageData flattenAlphaMaskedImageData(ImageData imgData, RGB bgRGB, boolean blend, boolean keepTransparent) {
    	Check.checkArg(keepTransparent || bgRGB != null);

    	if (imgData == null)
    		return null;
    	
        if (imgData.alphaData == null)
            return imgData;
        
        // match the Win32 expectation for 24-bit images (R,G,B)
        PaletteData pal = new PaletteData(0xFF, 0xFF00, 0xFF0000);

        ImageData newData = new ImageData(imgData.width, imgData.height,
                24, pal);

        RGB transparentRGB = com.nokia.sdt.utils.drawing.GC.TRANSPARENT_RGB_VALUE;
        int transparentPixel = newData.palette.getPixel(transparentRGB);
        newData.transparentPixel = transparentPixel;
        
        if (bgRGB == null)
        	bgRGB = new RGB(255, 255, 255);
        
        int backgroundPixel = newData.palette.getPixel(bgRGB);
        
        int w = imgData.width;
        int h = imgData.height;
        if (blend) {
            // Get the background color and blend it with 
            // not-fully-on pixels in order to emit a 
            // non-alpha-masked image.
            //
            // Pixels which are transparent enough become
            // the transparent pixel or the background, depending on keepTransparent
            int bgRed = 255, bgGreen = 255, bgBlue = 255;
            if (bgRGB != null) {
                bgRed = bgRGB.red;
                bgGreen = bgRGB.green;
                bgBlue = bgRGB.blue;
            }
            //System.out.println("blending with background "+bgRed+"/"+bgGreen+"/"+bgBlue);
            
            int[] pixels = new int[w];
            byte[] alphas = new byte[w];
            for (int r = 0; r < h; r++) {
                imgData.getPixels(0, r, w, pixels, 0);
                imgData.getAlphas(0, r, w, alphas, 0);
                for (int c = 0; c < w; c++) {
                    RGB rgb = imgData.palette.getRGB(pixels[c]);
                    int alpha = alphas[c] & 0xff;
         
                    if (alpha > 240) {
                        // keep original pixel
                    } else if (alpha < 16) {
                    	if (keepTransparent) {
                    		// transparent
                    		rgb = transparentRGB;
                    	} else {
                    		rgb = bgRGB;
                    	}
                    } else {
                        // blend component-wise

                        if (true) {
                            // not premultiplied
                            rgb.red = ((bgRed * (255 - alpha)) + (rgb.red * alpha)) / 255;
                            rgb.green = ((bgGreen * (255 - alpha)) + (rgb.green * alpha)) / 255;
                            rgb.blue = ((bgBlue * (255 - alpha)) + (rgb.blue * alpha)) / 255;
                        } else {
                            // premultiplied
                            rgb.red = ((bgRed * (255 - alpha)) + (rgb.red * 255)) / 255; 
                            rgb.green = ((bgGreen * (255 - alpha)) + (rgb.green * 255)) / 255; 
                            rgb.blue = ((bgBlue * (255 - alpha)) + (rgb.blue * 255)) / 255;
                        }
                    }
                    pixels[c] = pal.getPixel(rgb);
                }
                newData.setPixels(0, r, w, pixels, 0);
            }
        } else {
            // simply pick some level of not-so-transparent
            // as solid pixels and treat the rest as transparent
            int[] pixels = new int[w];
            byte[] alphas = new byte[w];
            for (int r = 0; r < h; r++) {
                imgData.getPixels(0, r, w, pixels, 0);
                imgData.getAlphas(0, r, w, alphas, 0);
                for (int c = 0; c < w; c++) { 
                    int alpha = alphas[c] & 0xff;
         
                    if (alpha < 64) {
                    	if (keepTransparent)
                    		pixels[c] = transparentPixel;
                    	else
                    		pixels[c] = backgroundPixel;
                    } else {
                        RGB rgb = imgData.palette.getRGB(pixels[c]);
                        pixels[c] = newData.palette.getPixel(rgb);
                    }
                }
                newData.setPixels(0, r, w, pixels, 0);
            }
        }
        
        return newData;
    }

    /**
     * Convert an AWT BufferedImage into an SWT image
     * @param device
     * @param img_
     * @return an RGBA image, owned by caller
     */
    public static Image convertAwtImage(Device device, BufferedImage img_) {
    	ImageData data = convertAwtImageData(img_);
        return new Image(device, data);            
    }

    /**
     * Convert an AWT BufferedImage into an SWT image
     * @param img_
     * @return RGBA image data
     */
    public static ImageData convertAwtImageData(BufferedImage img_) {
        // Get all the pixels
        int w = img_.getWidth();
        int h = img_.getHeight();

		// this is the palette AWT uses
        PaletteData pal = new PaletteData(0xFF00, 0xFF0000, 0xFF000000);

        ImageData data = new ImageData(w, h, 32, pal);

        int[] pixels = new int[w];
        byte[] alphas = new byte[w];
        
        boolean isPremultiplied = img_.isAlphaPremultiplied();
        
        for (int r = 0; r < h; r++) {
            img_.getRGB(0, r, w, 1, pixels, 0, w);
            for (int c = 0; c < w; c++) {
                /*
                 0x00ff0000, // Red
                  0x0000ff00,   // Green
                  0x000000ff,   // Blue
                  0xff000000 // Alpha
                  */
                int defaultRGB = pixels[c];
                int alpha = defaultRGB >>> 24;
        
                // move color components around
                int R = ((defaultRGB >> 16) & 0xff); 
                int G = ((defaultRGB >> 8) & 0xff); 
                int B = (defaultRGB & 0xff);
                
                // The colors are premultiplied.  Undo that.
                if (isPremultiplied && alpha > 0) {
                    R = R * 255 / alpha;
                    G = G * 255 / alpha;
                    B = B * 255 / alpha;
                }
                
                pixels[c] = (R << 8) | (G << 16) | (B << 24);
                
                alphas[c] = (byte) (defaultRGB >>> 24);
            }
            data.setPixels(0, r, w, pixels, 0);
            data.setAlphas(0, r, w, alphas, 0);
        }
            
        return data;            
    }


    /**
	 * Create PaletteData for the standard 32-bit palette.
	 */
	public static PaletteData createStandardPaletteData() {
		return new PaletteData(0xff000000, 0x00ff0000, 0x0000ff00);
	}

	/**
	 * Create ImageData for the standard 32-bit palette.
	 */
	public static ImageData createStandard32BitImageData(int width, int height) {
		if (width <= 0)
			width = 1;
		if (height <= 0)
			height = 1;
		return new ImageData(width, height, 32, createStandardPaletteData());
	}

    /**
     * Crop an image to the given size (upper-left hand corner)
     * @param device
     * @param image
     * @param cropSize
     * @return new Image, you own it
     */
    public static Image cropImage(Device device, Image image, Point cropSize) {
        ImageData data = image.getImageData();
        ImageData newData = new ImageData(cropSize.x, cropSize.y, data.depth, 
                data.palette);
        Image ret = new Image(device, newData);
        GC gc = new GC(ret);
        gc.setBackground(device.getSystemColor(SWT.COLOR_WHITE));
        gc.fillRectangle(0, 0, cropSize.x, cropSize.y);
        gc.drawImage(image, 0, 0);
        gc.dispose();
        return ret;
    }

    /**
     * Create an image reflecting the rendered appearance of the
     * input.  If isScaled, then the image is scaled to the given
     * size.  Otherwise, it is cropped to the given size, using
     * the alignment as a guide of which portion to show.
     * @param gc
     * @param image
     * @param x
     * @param y
     * @param size size in which to render the image
     * @param alignment weights, -1 = left/top, 0 = center, 1 = right/bottom
     * @param isScaled true: scaled, false: cropped
     * @param isPreservingAspectRatio true: when scaling, preserve aspect
     * @return
     */
    public static void renderImage(GC gc, Image image, int x, int y,
            Point size, Point alignment, boolean isScaled, boolean isPreservingAspectRatio) {
        Check.checkArg(gc);
        Check.checkArg(image);
        Check.checkArg(size);
        Check.checkArg(alignment);
        
        Rectangle imgBounds = image.getBounds();
        if (isScaled) {
            gc.drawImage(image, 
                    0, 0, imgBounds.width, imgBounds.height, 
                    x, y, size.x, size.y);
        } else {
            int srcX = align(imgBounds.width, size.x, alignment.x);
            int srcY = align(imgBounds.height, size.y, alignment.y);
            Rectangle oldClip = gc.getClipping();
            gc.setClipping(x, y, size.x, size.y);
            gc.drawImage(image, srcX + x, srcY + y);
            gc.setClipping(oldClip);
        }
    }

    /**
     * Return the coordinate where something of size 'size' starts
     * in a space of size 'space', with 'weight' as the alignment.  
     * @param size size to align 
     * @param space space to align in
     * @param weight -1 for align left/top, 0 for center, 1 for right/bottom
     * @return
     */
    private static int align(int size, int space, int weight) {
        if (weight == ALIGN_LEFT) {
            return 0;
        } else if (weight == ALIGN_CENTER) {
            return (space - size) / 2;
        } else if (weight == ALIGN_CENTER_OR_TOP) {
            return size <= space ? (space - size) / 2 : 0;
        } else /* ALIGN_RIGHT */ {
            return space - size;
        }
    }

	/**
	 * Mask out the image with a solid color at the given alpha blend.
	 * @param display
	 * @param image
	 * @param alpha
	 * @param color
	 * @return new image, you must dispose
	 */
	public static Image maskWithSolidColor(Device device, Image image, int alpha, Color color) {
		ImageData imageData = image.getImageData();
		PaletteData palette = imageData.palette;
		RGB maskColor = color.getRGB();
		int negAlpha = 255 - alpha;
		int divAlpha = 255;
		int alphaRed = alpha * maskColor.red;
		int alphaGreen = alpha * maskColor.green;
		int alphaBlue = alpha * maskColor.blue;

		// cache the blending information (4096 steps vs. minimum 36608 for 176x208 screen)
		int[] colorTable = new int[16*16*16];
		int idx = 0;
		for(int r = 0; r < 256; r += 16) {
			for(int g = 0; g < 256; g += 16) {
				for(int b = 0; b < 256; b += 16) {
					int pixel = palette.getPixel(new RGB(r * 240 / 255, g * 240 / 255, b * 240 / 255));
					RGB rgb = palette.getRGB(pixel);
					rgb.red = (rgb.red * negAlpha + alphaRed) / divAlpha;
					rgb.green = (rgb.green * negAlpha + alphaGreen) / divAlpha;
					rgb.blue = (rgb.blue * negAlpha + alphaBlue) / divAlpha;
					colorTable[idx++] = palette.getPixel(rgb);
				}
			}
		}
					
		int[] pixels = new int[imageData.width];
		for (int y = 0; y < imageData.height; y++) {
			imageData.getPixels(0, y, imageData.width, pixels, 0);
			for (int x = 0; x < pixels.length; x++) {
				int pixel = pixels[x];
				RGB rgb = palette.getRGB(pixel);
				pixels[x] = colorTable[((rgb.red&0xf0)<<4)+((rgb.green&0xf0))+(rgb.blue>>4)];
			}
			imageData.setPixels(0, y, imageData.width, pixels, 0);
		}
		return new Image(device, imageData);
	}

	/**
	 * Tell whether SWT supports alpha blending.  
	 * @return flag
	 */
	public static boolean isAlphaBlendingSupported() {
		if (!alphaSupportValidated) {
			GC gc = new GC(Display.getDefault());
			alphaSupported = false;
			try {
				// this will throw SWTException if not supported
				gc.setAlpha(1);
				// force drawing to happen in case setAlpha() is lazy -- 
				// yes, this touches the screen (a little, with alpha==1)!
				gc.drawPoint(0, 0);
				alphaSupported = true;
			} catch (SWTException e) {
			}
			gc.dispose();
			alphaSupportValidated = true;
		}
		return alphaSupported;
	}


}
