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
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

/**
 * Image data provider interface.  This provides ImageData for images scaled
 * to a given size, where <code>null</code> is the native size.  A provider
 * may cache images, so the {@link IDisposable#dispose()} method may be used
 * to flush any data.
 *
 */
public interface IImageDataProvider extends IDisposable {
	/**
	 * Get the image, optionally scaled to the given size, and return an {@link ImageData}.
	 * Make a copy if you intend to change the data.
	 * The original size of the image is always cached.
     * @param size the size to scale to, or null for natural size
     * @return {@link ImageData}, owned by this instance
     * @throws CoreException for image loading/scaling failure (wraps other exceptions)	 
     */
	ImageData getImageData(Point size) throws CoreException;

	/**
	 * Add listener to changes in the provider.
	 */
	void addListener(IImageDataProviderListener listener);

	/**
	 * Remove listener to changes in the provider.
	 */
	void removeListener(IImageDataProviderListener listener);

	/**
	 * Fire listeners when change detected. 
	 */
	void fireListeners();

	/**
	 * Check for external modification
	 * @return true if changed and listeners fired
	 */
	boolean notifyIfChanged();
}
