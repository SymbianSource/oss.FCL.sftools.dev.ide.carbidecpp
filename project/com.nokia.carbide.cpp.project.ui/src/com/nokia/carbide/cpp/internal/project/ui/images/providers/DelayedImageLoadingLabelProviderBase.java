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
package com.nokia.carbide.cpp.internal.project.ui.images.providers;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.editors.images.Messages;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.IToolTipLabelProvider;

import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import java.io.File;
import java.util.*;

/**
 * This class is a label provider that manages a collection of images that it loads one at a time
 * in a background thread or job, sending out an event when images are ready.
 * <p>
 * This provider must be disposed when finished, or else threads remain running.
 *
 */
public abstract class DelayedImageLoadingLabelProviderBase extends LabelProvider implements IToolTipLabelProvider {
	private static final String CURSOR_ICON = "icons/busycursor.png"; //$NON-NLS-1$
	private static final String BROKEN_ICON = "icons/brokenimage.png"; //$NON-NLS-1$
	private List<Pair<IImageModel, Point>> pendingImages;
	private Object pendingImageMutex;
	
	private Thread loaderThread;
	private Job loaderJob;
	
	private Image stubImage;
	
	private Set<IImageModel> deadImages;
	private Map<Pair<IImageModel, Point>, Image> loadedImages;
	private Image brokenImage;
	private Map<IImageModel, String> errorMap;
	
	public DelayedImageLoadingLabelProviderBase() {
		this.pendingImageMutex = new Object();
		this.loaderThread = null;
		this.loadedImages = new HashMap<Pair<IImageModel,Point>, Image>();
		this.errorMap = new HashMap<IImageModel, String>();
		
		// get the image used for files while being loaded 
		// and for broken images
		if (ProjectUIPlugin.getDefault() != null) {
			this.stubImage = ProjectUIPlugin.getImageDescriptor(CURSOR_ICON).createImage();
			this.brokenImage = ProjectUIPlugin.getImageDescriptor(BROKEN_ICON).createImage();
		} else {
			File cursorImageFile = new File(CURSOR_ICON);
			File brokenImageFile = new File(BROKEN_ICON);
			if (cursorImageFile.exists())
				this.stubImage = new Image(Display.getDefault(), cursorImageFile.getAbsolutePath());
			if (brokenImageFile.exists())
				this.brokenImage = new Image(Display.getDefault(), brokenImageFile.getAbsolutePath());
		}
		
		if (this.stubImage == null)
			this.stubImage = new Image(Display.getDefault(), 1, 1);
		if (this.brokenImage == null)
			this.brokenImage = new Image(Display.getDefault(), 1, 1);
		
		// images known not to exist (so stop trying!!!)
		this.deadImages = new HashSet<IImageModel>();
	}
	
	/**
	 * Override to convert the path from an element (IImageSourceReference) to an absolute path
	 * @param elementPath
	 * @return
	 */
	protected IPath resolvePath(IPath elementPath) {
		return elementPath;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		interrupt();
		flush();
		stubImage.dispose();
		brokenImage.dispose();
		super.dispose();
	}
	
	/**
	 * Flush any cached images
	 */
	public void flush() {
		synchronized (pendingImageMutex) {
			for (Image image : loadedImages.values()) {
				image.dispose();
			}
			loadedImages.clear();
		}
	}

	protected synchronized Image findOrLoadImage(IImageModel model, Point size) {
		if (model == null || deadImages.contains(model)) {
			return brokenImage;
		}
		
		Pair<IImageModel, Point> key = new Pair<IImageModel, Point>(model, size);
		Image image;
		synchronized (pendingImageMutex) {
			image = loadedImages.get(key); 
		} 
		
		if (image != null) {
			return image;
		}
		
		loadImageInBackground(key);
		return stubImage;
	}
	
	/** Get the hourglass image */
	protected Image getStubImage() {
		return stubImage;
	}
	

	
	/**
	 * @param path full path to image
	 * @param object
	 */
	private void loadImageInBackground(final Pair<IImageModel, Point> key) {
		if (Platform.isRunning()) {
			if (loaderJob == null || loaderJob.getResult() != null) {
				loaderJob = new Job(Messages.getString("DelayedImageLoadingLabelProviderBase.ImageLoadingJobLabel")) { //$NON-NLS-1$

					@Override
					protected IStatus run(IProgressMonitor monitor) {
						monitor.beginTask("", IProgressMonitor.UNKNOWN); //$NON-NLS-1$
						try {
							loadImagesLoop(monitor);
						} finally {
							monitor.done();
						}
						return Status.OK_STATUS;
					}
					
				};
				loaderJob.setRule(null);
				loaderJob.setSystem(true);
				loaderJob.setUser(false);
				loaderJob.schedule();
			}
		} else {
			if (loaderThread == null) {
				Runnable runnable = new Runnable() {
	
					public void run() {
						try {
							loadImagesLoop(null);
						} finally {
						}
					}
					
				};
				loaderThread = new Thread(runnable);
				loaderThread.start();
			}
		}
		synchronized (pendingImageMutex) {
			if (pendingImages == null)
				pendingImages = new LinkedList<Pair<IImageModel,Point>>();

			if (!pendingImages.contains(key)) {
				pendingImages.add(0, key);
				pendingImageMutex.notify();
			}
		}
	}

	/**
	 * Load images from the pending images list.
	 * @param monitor
	 */
	protected void loadImagesLoop(IProgressMonitor monitor) {
		int worked = 0;
		final List<Object> updated = new ArrayList<Object>();
		while (!Thread.interrupted()) {
			Pair<IImageModel, Point> pair = null;
			synchronized (pendingImageMutex) {
				// pendingImages == null is a hint that we should quit
				while (pendingImages != null && pendingImages.size() == 0) {
					try {
						pendingImageMutex.wait();
					} catch (InterruptedException e) {
						break;
					}
				}
				if (pendingImages == null || pendingImages.size() == 0)
					break;
				
				pair = pendingImages.remove(0);
			}
			
			if (pair == null)
				continue;
			
			final IImageModel model = pair.first;
			
			// now, load the image
			Image image = null;
			try {
				//System.out.println(this+" Loading " + pair.second.first); //$NON-NLS-1$
				image = model.getImageDescriptor(pair.second).createImage();
				synchronized (pendingImageMutex) {
					loadedImages.put(pair, image);
				}
			} catch (CoreException e) {
				errorMap.put(model, e.getLocalizedMessage());
			} catch (Throwable t) {
				// to avoid crashing this thread
			}
			
			if (image == null) {
				// don't try to load this again
				deadImages.add(model);
			} else {
				worked++;
			}
			
			updated.add(model);
			int remaining = 0;
			synchronized(pendingImageMutex) {
				if (pendingImages != null)
					remaining = pendingImages.size();
			}
			if (!Thread.interrupted() && (worked >= 10 || remaining == 0)) {
				// do this in UI thread
				Display.getDefault().asyncExec(new Runnable() {
	
					public void run() {
						fireLabelProviderChanged(new LabelProviderChangedEvent(
								DelayedImageLoadingLabelProviderBase.this, 
								updated.toArray()));
						updated.clear();
					}
				});
				worked = 0;
			}
			
			if (monitor != null)
				monitor.worked(1);
		}
		
		// final notification
		if (!Thread.interrupted() && pendingImages != null) {
			try {
				Display.getDefault().asyncExec(new Runnable() {
		
					public void run() {
						fireLabelProviderChanged(new LabelProviderChangedEvent(
								DelayedImageLoadingLabelProviderBase.this, 
								updated.toArray()));
					}
				});
			} catch (SWTError e) {
				
			}
		}	
		
	}
	

	/**
	 * Interrupt the provider early (before being disposed)
	 */
	public void interrupt() {
		synchronized (pendingImageMutex) {
			if (pendingImages != null)
				pendingImages.clear();
			pendingImages = null;
			pendingImageMutex.notify();
			
		}
		if (loaderThread != null) {
			loaderThread.interrupt();
			try {
				loaderThread.join();
			} catch (InterruptedException e) {
			}
			loaderThread = null;
		}
		if (loaderJob != null) {
			loaderJob.cancel();
			try {
				loaderJob.join();
			} catch (InterruptedException e) {
			}
			loaderJob = null;
		}
	}
	
	public String getToolTipText(Object element) {
		return errorMap.get(element);
	}
}
