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
package com.nokia.sdt.datamodel.adapter;

import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.swt.graphics.Point;

/**
 * Component implementation interface to provide information
 * about image properties to indicate how it will appear on screen.
 * <p>
 * The propertyId passed to these routines is not intended for
 * use in reading the current image but for distinguishing
 * multiple image properties in a component.
 * <p>
 * If an image property is a multi-image property, then only the top-level
 * image property will be queried.  No abstract image ids will be passed
 * here.  All must have the same rendering info.
 * 
 * 
 */
public interface IImagePropertyRenderingInfo extends IModelAdapter {

    /**
     * Provide the actual viewable size for an image.
     * Usually this matches the clickable area from IDirectImageEdit.
     * Usually this is a static check, but may depend on component state --
     * but it cannot depend on the image property state!  Otherwise, the image
     * editor will get information from the previous setting.
     * @return Point rendered pixel bounds
     */
    public Point getViewableSize(String propertyId, ILookAndFeel laf);

    /**
     * Provide the alignment of an image, in other words, how
     * the contents of a cropped image are selected, or how a small
     * image fits in a larger area.  
     * Usually this is a static check, but may depend on component state
     * (though not image state).
     * @return Point describing weights in the x and y dimensions, 
     * where -1 indicates left/top, 0 indicates center, 1 indicates
     * center or left on overflow, 2 indicates right/bottom.
     * @see com.nokia.sdt.utils.ImageUtils#ALIGN_LEFT, etc
     */
    public Point getAlignmentWeights(String propertyId, ILookAndFeel laf);

    /**
     * Tell if a bitmap image will be scaled to fit or not
     * (SVG images are always assumed to scale).
     * Usually this is a static check, but may depend on component state --
     * but it cannot depend on the image property state!  Otherwise, the image
     * editor will get information from the previous setting.
     * @param propertyId
     * @param laf
     * @return true if it could be scaled, false if it could be cropped
     */
    public boolean isScaling(String propertyId, ILookAndFeel laf);
    
    /**
     * Tell whether the aspect ratio is preserved for a scaled bitmap or SVG image.
     * Usually this is a static check, but may depend on component state
     * (though not image state).
     * For now, yes/no is the only choice.
     */
    public boolean isPreservingAspectRatio(String propertyId, ILookAndFeel laf);
    
}
