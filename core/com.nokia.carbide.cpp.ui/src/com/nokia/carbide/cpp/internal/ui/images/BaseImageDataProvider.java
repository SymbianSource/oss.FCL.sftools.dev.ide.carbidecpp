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

import com.nokia.carbide.cpp.ui.images.IImageDataProvider;
import com.nokia.carbide.cpp.ui.images.IImageDataProviderListener;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.TrackedResource;
import com.nokia.cpp.internal.api.utils.core.TrackedResource.IListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

import java.io.File;
import java.util.LinkedList;

/**
 * This base image data provider manages the listener list.
 *
 */
public abstract class BaseImageDataProvider implements IImageDataProvider, IListener {

	private LinkedList<IImageDataProviderListener> listeners;
	private TrackedResource trackedResource;
	protected File file;
	protected long timestamp;

	public BaseImageDataProvider(File file) {
		this.file = file;
		this.timestamp = file.lastModified();
		
		listeners = null;
		
		// track workspace changes to file
		IFile wsFile = FileUtils.convertFileToIFile(file);
		if (wsFile != null) {
			trackedResource = imageTracker(wsFile);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageDataProvider#addListener(com.nokia.carbide.cpp.ui.images.IImageDataProvider.IImageDataProviderListener)
	 */
	public synchronized void addListener(IImageDataProviderListener listener) {
		if (listeners == null) {
			 listeners = new LinkedList<IImageDataProviderListener>();
		}
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageDataProvider#removeListener(com.nokia.carbide.cpp.ui.images.IImageDataProvider.IImageDataProviderListener)
	 */
	public synchronized void removeListener(IImageDataProviderListener listener) {
		listeners.remove(listener);
		if (listeners.size() == 0) {
			listeners = null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageDataProvider#fireListeners()
	 */
	public synchronized void fireListeners() {
		if (listeners == null)
			return;
		IImageDataProviderListener[] array = (IImageDataProviderListener[]) listeners.toArray(new IImageDataProviderListener[listeners.size()]);
		for (IImageDataProviderListener listener : array) {
			listener.changed(this);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.utils.IDisposable#dispose()
	 */
	public synchronized void dispose() {
		listeners = null;
		if (trackedResource != null) {
			trackedResource.dispose();
			trackedResource = null;
		}
	}
	
    /**
     * Create a tracker for an image
     * @param wsFile
     * @return tracker
     */
    private TrackedResource imageTracker(IFile wsFile) {
        TrackedResource tracked = new TrackedResource(wsFile);
        tracked.addListener(this);
        return tracked;
    }

    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.TrackedResource.IListener#resourceChanged(com.nokia.sdt.utils.TrackedResource)
     */
    public void resourceChanged(TrackedResource resource) {
        fireListeners();
    }
    
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.TrackedResource.IListener#resourceDeleted(com.nokia.sdt.utils.TrackedResource)
     */
    public void resourceDeleted(TrackedResource resource) {
    	fireListeners();
    }
     
    /* (non-Javadoc)
     * @see com.nokia.sdt.utils.TrackedResource.IListener#resourceMoved(com.nokia.sdt.utils.TrackedResource)
     */
    public void resourceMoved(TrackedResource resource, IPath oldPath) {
    	fireListeners();
    }

    public boolean notifyIfChanged() {
		long lastModified = file.lastModified();
		if (lastModified != timestamp) {
			fireListeners();
			timestamp = lastModified;
			return true;
		}
		return false;
    }
}
