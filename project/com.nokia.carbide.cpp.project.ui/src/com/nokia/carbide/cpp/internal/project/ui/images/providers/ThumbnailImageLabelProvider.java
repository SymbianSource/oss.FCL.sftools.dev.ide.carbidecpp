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

import com.nokia.carbide.cpp.internal.project.ui.images.CarbideImageModelFactory;
import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.graphics.*;

import java.io.File;

/**
 * Label provider for images, scaled to a common size, and loading on demand.
 * Supports models of IFile, IPath, or IImageModel.  For IPath models, the {@link #resolvePath(IPath)}
 * method should be overridden to provide absolute filesystem paths.
 *
 */
public class ThumbnailImageLabelProvider extends DelayedImageLoadingLabelProviderBase {

	private Point thumbSize;
	private IImageContainerModel standaloneImageContainerModel;
	private final IImageLoader imageLoader;

	/**
	 * Create provider that uses the given image loader and scales images
	 * to the given thumbnail size.  Does not own image loader -- caller should dispose.
	 */
	public ThumbnailImageLabelProvider(IImageLoader imageLoader, Point thumbSize) {
		super();
		this.imageLoader = imageLoader;
		this.thumbSize = thumbSize;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.providers.DelayedImageLoadingLabelProviderBase#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		IImageModel model = null;
		IPath path = null;
		if (element instanceof IFile) {
			path = ((IFile) element).getLocation();
		} else if (element instanceof IPath) {
			path = resolvePath((IPath) element);
		} else if (element instanceof File) {
			path = new org.eclipse.core.runtime.Path(((File)element).getAbsolutePath());
		} else if (element instanceof IImageModel) {
			model = (IImageModel) element;
		} else {
			return null;
		}
	
		if (model == null) {
			if (standaloneImageContainerModel == null) {
				standaloneImageContainerModel = CarbideImageModelFactory.createNullImageContainerModel(
						null, 
						imageLoader); 
			}
			model = CarbideImageModelFactory.createFileImageModel(
					standaloneImageContainerModel,
					path);
		}
		
		Image image = getThumbnailImage(model);
		return image;
	}

	protected Image getThumbnailImage(IImageModel model) {
		Image image = findOrLoadImage(model, null);
		if (image == null)
			return null;  // non-existent or not yet loaded
		
		// then scale to thumbnail size if necessary
		Rectangle bounds = image.getBounds();
		if (bounds.width > thumbSize.x || bounds.height > thumbSize.y) {
			Point newSize = ImageUtils.scaleSizeToSize(new Point(bounds.width, bounds.height), 
					thumbSize);
			return findOrLoadImage(model, newSize);
		}
		return image;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof IFile) {
			return ((IFile) element).getProjectRelativePath().toString();
		} else if (element instanceof IPath) {
			return ((IPath) element).toString();
		} else if (element instanceof File) {
			File file = (File) element;
			return file.getParentFile().getName() + "/" + file.getName(); //$NON-NLS-1$
		} else if (element instanceof IImageModel) {
			return ((IImageModel) element).getDisplayText();
		}
		return super.getText(element);
	}

	/**
	 * @param thumbSize the thumbSize to set
	 */
	public void setThumbSize(Point thumbSize) {
		this.thumbSize = thumbSize;
	}

	/**
	 * @return the thumbSize
	 */
	public Point getThumbSize() {
		return thumbSize;
	}

}
