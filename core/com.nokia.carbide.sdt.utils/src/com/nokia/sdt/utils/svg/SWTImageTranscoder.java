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
package com.nokia.sdt.utils.svg;

import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.ImageUtils;

import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.keys.BooleanKey;
import org.eclipse.swt.graphics.*;

import java.awt.image.BufferedImage;

/**
 * This class transcodes the output from the transcoder 
 * from an AWT into an SWT-compatible image.
 *
 */
public class SWTImageTranscoder extends ImageTranscoder {

    public static final TranscodingHints.Key KEY_USE_TRANSPARENT_PIXEL
    	= new BooleanKey();
    
    public SWTImageTranscoder() {
        super();
    }

    /* save rendered image */
    protected java.awt.image.BufferedImage img_;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.batik.transcoder.image.ImageTranscoder#createImage(int,
     *      int)
     */
    public BufferedImage createImage(int width, int height) {
        BufferedImage img = new java.awt.image.BufferedImage(width, height,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        return img;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.batik.transcoder.image.ImageTranscoder#writeImage(java.awt.image.BufferedImage,
     *      org.apache.batik.transcoder.TranscoderOutput)
     * 
     * IGNORE output in this broken API
     */
    public void writeImage(BufferedImage img, TranscoderOutput output)
            throws TranscoderException {
        
        // TODO: try using the OutputStream 
    	Check.checkArg(output == null);
        img_ = img;
    }

    /**
     * Get the image generated from the transcode operation.
     * This constructs a new, non-owned Image each time it is called. 
     * @param device
     * @return Image, owned by caller
     */
    public Image getImage(Device device) {
    	ImageData data = getImageData();
    	if (data == null)
    		return null;
    	return new Image(device, data);
    }

    /**
     * Get the image generated from the transcode operation.
     * This constructs a new, non-owned Image each time it is called. 
     * @param device
     * @return Image, owned by caller
     */
    public ImageData getImageData() {
        ImageData imageData = ImageUtils.convertAwtImageData(img_);
        boolean flattenAlpha = false;
        if (hints.containsKey(KEY_USE_TRANSPARENT_PIXEL)) {
            flattenAlpha = ((Boolean) hints
                    .get(KEY_USE_TRANSPARENT_PIXEL)).booleanValue();
        }
        if (flattenAlpha) {
            boolean blend = false;
            /*
            if (hints.containsKey(KEY_FORCE_TRANSPARENT_WHITE)) {
                blend = !((Boolean) hints
                        .get(KEY_FORCE_TRANSPARENT_WHITE)).booleanValue();
            }*/
            ImageData flattenedData = ImageUtils.flattenAlphaMaskedImageData(imageData, null, blend, true /*transparent*/);
            return flattenedData;
        } else {
            return imageData;
        }
    }
}