/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.imagePropertyRenderingInfo;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;
import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.swt.graphics.Point;

/**
 * This script interface is used to get information controlling
 * the use of image properties in a component.
 *
 */
public interface IScriptImagePropertyRenderingInfo {

    /**
     * @see com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo#isScaling(String, ILookAndFeel)
     */
    public boolean isScaling(WrappedInstance instance, String propertyId, ILookAndFeel laf);
    /**
     * @see com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo#isPreservingAspectRatio(String, ILookAndFeel)
     */
    public boolean isPreservingAspectRatio(WrappedInstance instance, String propertyId, ILookAndFeel laf);
    /**
     * @see com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo#getViewableSize(String, ILookAndFeel)
     */
    public Point getViewableSize(WrappedInstance instance, String propertyId, ILookAndFeel laf);
    /**
     * @see com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo#getAlignmentWeights(String, ILookAndFeel)
     */
    public Point getAlignmentWeights(WrappedInstance instance, String propertyId, ILookAndFeel laf);
}
