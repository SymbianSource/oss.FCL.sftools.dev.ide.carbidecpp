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
package com.nokia.sdt.displaymodel;


import com.nokia.sdt.utils.drawing.IFont;

import org.eclipse.swt.graphics.*;

/**
 * ILookAndFeel provides parameters and SWT resources used
 * to layout and render components.
 *
 */
public interface ILookAndFeel extends com.nokia.cpp.internal.api.utils.core.IDisposable {

    /** This key references a string which, if defined,
     * is the implicit prefix for image searches
     */
    public static final String KEY_IMAGE_DIR = "image.dir"; //$NON-NLS-1$
    
	/**
	 * Colors are owned by the look and feel and should 
	 * not be deleted.
	 */
	Color getColor(String key);
	
	/**
	 * Fonts are owned by the look and feel and should not be deleted
	 */
	IFont getFont(String key);
	
	Point getPosition(String key);
	
	Point getDimension(String key);
	
	Rectangle getRectangle(String key);
	
	int getInteger(String key, int defaultValue);
	
	boolean getBoolean(String key, boolean defaultValue);
	
	String getString(String key);
    
	/**
	 * Images are owned by the look and feel and should not be deleted
	 */
	Image getImage(String key);
    
    /**
     * Get an image, scaled to the given size.  Images are
     * still owned by the LAF and should not be deleted.
     */
    Image getImage(String key, Point size);
    
}
