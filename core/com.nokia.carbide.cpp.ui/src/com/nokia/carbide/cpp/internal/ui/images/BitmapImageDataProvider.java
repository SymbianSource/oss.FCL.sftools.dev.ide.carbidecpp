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
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.MessageFormat;

/**
 * Basic implementation for bitmap data, where the initial data is scaled
 * to the desired size.
 *
 */
public class BitmapImageDataProvider extends BaseImageDataProvider implements IImageDataProvider {
	private static org.eclipse.swt.graphics.ImageLoader imageLoader =
		new org.eclipse.swt.graphics.ImageLoader();

	//private ImageData data;
	private WeakReference<ImageData> dataRef;

	/**
	 * @param file
	 */
	public BitmapImageDataProvider(File file) {
		super(file);
	}

	public ImageData getImageData(Point size) throws CoreException {
		ImageData data = null;
		if (dataRef != null) {
			if (notifyIfChanged()) {
				dataRef = null;
			} else {
				data = dataRef.get();
			}
		}
		if (data == null) {
			try {
				data = loadImageData();
				dataRef = new WeakReference<ImageData>(data);
			} catch (Throwable t) {
				throw new CoreException(Logging.newStatus(
						CarbideUIPlugin.getDefault(), 
						IStatus.ERROR,
						MessageFormat.format(
								Messages.getString("BitmapImageDataProvider.CouldNotLoadImageFormat"), //$NON-NLS-1$
								new Object[] { file }),
						t));
			}
		}
		
		ImageData newData = data;
		if (size != null) {
			// scale to desired size
			try {
				ImageData scaledImageData = ImageUtils.scaleImageData(data, size,
						false, false);
				if (scaledImageData != null) {
					newData = scaledImageData;
				}
			} catch (IllegalArgumentException e) {
				throw new CoreException(Logging.newStatus(
						CarbideUIPlugin.getDefault(), 
						IStatus.ERROR,
						"Invalid image scaling: " + size, //$NON-NLS-1$
						e));
			}
			
		}
		return newData;
	}
	
	/**
	 * Load the image data from disk.
	 * @return
	 */
	private ImageData loadImageData() {
		ImageData[] datas = imageLoader.load(file.getAbsolutePath());
		if (datas.length == 0) {
			throw new IllegalArgumentException(
					MessageFormat.format(
							Messages.getString("BitmapImageDataProvider.ImageHasNoContentFormat"), //$NON-NLS-1$
							new Object[] { file }));
		}
		Check.checkArg(datas[0]);
		
		// choose first image from animated sequence
		ImageData data = datas[0];
		return data;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public void dispose() {
		super.dispose();
		dataRef = null;
	}
}