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

package com.nokia.sdt.component.symbian.directImageEdit;

import com.nokia.sdt.component.symbian.scripting.WrappedInstance;
import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * This script interface is used to layout the children of a layout container
 *
 */
public interface IScriptDirectImageEdit {

	/**
     * Note: this is named getImagePropertyPaths() to distinguish from possible
     * implementations of direct label and image editing on the same component.
	 * @see com.nokia.sdt.datamodel.adapter.IDirectEdit#getPropertyPaths()
	 */
	String[] getImagePropertyPaths(WrappedInstance instance);

	/**
     * Note: this is named getImageBounds() to distinguish from possible
     * implementations of direct label and image editing on the same component.
	 * @see com.nokia.sdt.datamodel.adapter.IDirectEdit#getVisualBounds(String, ILookAndFeel)
	 */
	Rectangle getImageBounds(WrappedInstance instance, String propertyPath, ILookAndFeel laf);

    /**
     * @see com.nokia.sdt.datamodel.adapter.IDirectImageEdit#isScaling(String, ILookAndFeel)
     */
    boolean isScaling(WrappedInstance instance, String propertyPat, ILookAndFeel laf);
    
    /**
     * @see com.nokia.sdt.datamodel.adapter.IDirectImageEdit#validateImage(String, ILookAndFeel, String, Point)
     */
	IStatus validateImage(WrappedInstance instance, String propertyId, ILookAndFeel laf, String filePath, Point size);

	/**
	 * @see com.nokia.sdt.datamodel.adapter.IDirectImageEdit#getRenderedImageSize(String, ILookAndFeel, Point)
	 */
    Point getRenderedImageSize(WrappedInstance instance, String propertyId, ILookAndFeel laf, Point size);
}
