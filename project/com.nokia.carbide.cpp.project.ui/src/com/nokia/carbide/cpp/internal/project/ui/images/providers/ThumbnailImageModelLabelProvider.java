package com.nokia.carbide.cpp.internal.project.ui.images.providers;

import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.utils.ImageUtils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.graphics.*;

import java.io.File;

/**
 * Label provider for image models, scaled to a common size.
 *
 */
public class ThumbnailImageModelLabelProvider extends DelayedImageLoadingLabelProviderBase {

	private Point thumbSize;

	/**
	 * 
	 */
	public ThumbnailImageModelLabelProvider(Point thumbSize) {
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
		Image image = null;
		if (element instanceof IImageModel) {
			IImageModel model = (IImageModel) element;
			image = getThumbnailImage(model);
		}
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
