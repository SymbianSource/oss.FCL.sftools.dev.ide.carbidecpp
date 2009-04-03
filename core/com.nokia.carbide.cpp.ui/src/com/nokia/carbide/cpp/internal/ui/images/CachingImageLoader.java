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

package com.nokia.carbide.cpp.internal.ui.images;

import com.nokia.carbide.cpp.ui.images.*;

import org.eclipse.core.runtime.*;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a cache for the image providers and ImageData created for given files.
 *
 */
public class CachingImageLoader implements IImageLoader {

	private Map<IPath, IImageDataProvider> pathToImageDataProviderMap = new HashMap<IPath, IImageDataProvider>();
	private ImageLoader loader = new ImageLoader();
	
	public CachingImageLoader() {
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageLoader#createImageDataProvider(org.eclipse.core.runtime.IPath)
	 */
	public synchronized IImageDataProvider createImageDataProvider(IPath origPath)
			throws CoreException {
		
		if (origPath == null)
			return null;
		
		final IPath path = canonicalizePath(origPath);
		IImageDataProvider provider = pathToImageDataProviderMap.get(path);
		
		// check for changes before returning anything cached
		if (provider != null) {
			provider.notifyIfChanged();
		}
		
		if (provider == null) {
			final IImageDataProvider baseProvider = loader.createImageDataProvider(path);
			provider = new BaseImageDataProvider(path.toFile()) {

				/** Cache of Pair<IPath, Point> to ImageData */
				private Map<Point, WeakReference<ImageData>> imageDataCache = new HashMap<Point, WeakReference<ImageData>>();

				public ImageData getImageData(Point size) throws CoreException {
					if (notifyIfChanged() || size == null) {
						// original size always cached inside provider
						return baseProvider.getImageData(size);
					}
					
					ImageData data = null;
					WeakReference<ImageData> ref = imageDataCache.get(size);
					if (ref != null) {
						data = ref.get();
					}
					if (data == null) {
						data = baseProvider.getImageData(size);
						imageDataCache.put(size, new WeakReference<ImageData>(data));
					}
					return data;
				}
				
				/* (non-Javadoc)
				 * @see com.nokia.sdt.utils.IDisposable#dispose()
				 */
				public void dispose() {
					super.dispose();
					imageDataCache.clear();
					baseProvider.dispose();
				}
			};
			
			pathToImageDataProviderMap.put(path, provider);
			
			provider.addListener(new IImageDataProviderListener() {

				public void changed(IImageDataProvider provider) {
					unregister(path);
				}
				
			});
		}
		return provider;
	}

	private void disposeProvider(IImageDataProvider provider, IPath path) {
		provider.dispose();
		provider = null;  
		pathToImageDataProviderMap.remove(path);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public synchronized void dispose() {
		for (IImageDataProvider provider : pathToImageDataProviderMap.values()) {
			provider.dispose();
		}
		pathToImageDataProviderMap.clear();
		loader.dispose();
	}
	
	/**
	 * Get the canonical path so we don't load the same image under different
	 * cases or paths.
	 * @param path full path
	 * @return canonical path (full path)
	 */
	private IPath canonicalizePath(IPath path) {
		try {
			return new Path(path.toFile().getCanonicalPath());
		} catch (IOException e) {
			return path;
		}
	}

    /**
     * Unregister a cached image when its underlying resource changes
     */
    private synchronized void unregister(IPath path) {
        // remove from cache
    	IImageDataProvider provider = pathToImageDataProviderMap.get(path);
    	disposeProvider(provider, path);
    }
    
}
