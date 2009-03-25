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
package com.nokia.carbide.cpp.internal.ui.images;

import com.nokia.carbide.cpp.internal.ui.Messages;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.images.IImageDataProvider;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.utils.svg.SVGLoader;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;

/**
 * Implementation of a provider that takes the SVG loader and uses it to
 * generate scaled images at exact resolution.
 *
 */
public class SVGImageDataProvider extends BaseImageDataProvider implements IImageDataProvider {
	private SVGLoader loader;
	private WeakReference<ImageData> basicImageDataRef;
	
	public SVGImageDataProvider(File file) {
		super(file);
	}

	public ImageData getImageData(Point size) throws CoreException {
		if (loader != null) {
			if (notifyIfChanged())
				loader = null;
		}
		if (loader == null) {
			try {
				loader = loadSVG();
			} catch (Throwable t) {
				throw new CoreException(Logging.newStatus(
						CarbideUIPlugin.getDefault(), 
						IStatus.ERROR,
						MessageFormat.format(
								Messages.getString("SVGImageDataProvider.CannotLoadSVGFormat"), //$NON-NLS-1$
								new Object[] { file }),
						t));
			}
		}
		if (size == null) {
			ImageData basicImageData = null;
			if (basicImageDataRef != null) {
				basicImageData = basicImageDataRef.get();
			}
			if (basicImageData == null) {
				basicImageData = loader.getImageData(null);
				basicImageDataRef = new WeakReference<ImageData>(basicImageData);
			}
			return basicImageData;
	 	} else {
	 		return loader.getImageData(size);
	 	}
	}
	
	/**
	 * @return
	 */
	private SVGLoader loadSVG() {
		return new SVGLoader(file); 
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public void dispose() {
		super.dispose();
		loader = null;
		basicImageDataRef = null;
	}
	
}