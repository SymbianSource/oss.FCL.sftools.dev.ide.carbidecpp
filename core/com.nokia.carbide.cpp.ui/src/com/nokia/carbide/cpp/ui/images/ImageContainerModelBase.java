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

import org.eclipse.core.runtime.IPath;

import java.util.ArrayList;
import java.util.List;

/**
 * Base for image containers.
 *
 */
public abstract class ImageContainerModelBase implements IImageContainerModel {

	private final IImageLoader imageLoader;
	protected List<IImageContainerModelListener> listeners;
	protected final IPath baseLocation;
	
	public ImageContainerModelBase(IPath baseLocation, IImageLoader imageLoader) {
		this.baseLocation = baseLocation;
		this.imageLoader = imageLoader;
		this.listeners = new ArrayList<IImageContainerModelListener>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		while (!listeners.isEmpty())
			removeListener(listeners.get(0));
		super.finalize();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageContainerModel#getBaseLocation()
	 */
	public IPath getBaseLocation() {
		return baseLocation;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageContainerModel#getImageLoader()
	 */
	public IImageLoader getImageLoader() {
		return imageLoader;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageContainerModel#addListener(com.nokia.carbide.cpp.project.ui.images.IImageContainerModelListener)
	 */
	public synchronized void addListener(IImageContainerModelListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageContainerModel#removeListener(com.nokia.carbide.cpp.project.ui.images.IImageContainerModelListener)
	 */
	public synchronized void removeListener(IImageContainerModelListener listener) {
		listeners.remove(listener);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageContainerModel#fireListeners()
	 */
	public synchronized void fireListeners() {
		IImageContainerModelListener[] array = (IImageContainerModelListener[]) listeners.toArray(new IImageContainerModelListener[listeners.size()]);
		for (IImageContainerModelListener listener : array) {
			listener.changed(this);
		}
	}
}
