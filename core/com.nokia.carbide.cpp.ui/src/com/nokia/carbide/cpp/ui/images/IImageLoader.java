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

package com.nokia.carbide.cpp.ui.images;

import com.nokia.cpp.internal.api.utils.core.IDisposable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * Image loader interface.  This provides IImageDataProvider instances
 * for a particular image file.  This allows a given image to be scaled
 * to multiple sizes, preserving quality for SVG images, without incurring
 * the expense of reloading the image from disk.
 * <p>
 * An image loader may cache data, so the {@link IDisposable#dispose()} method
 * may be used to release any data.
 *
 */
public interface IImageLoader extends IDisposable {

	/**
	 * Create an image data provider for an image from the given absolute path 
	 * @param path a bitmap or SVG file
     * @return IImageDataProvider instance
     * @throws CoreException for image location/loading failure (wraps other exceptions)	 
     */
	IImageDataProvider createImageDataProvider(IPath path) throws CoreException;
}
