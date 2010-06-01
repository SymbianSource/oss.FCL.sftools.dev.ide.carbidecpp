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

import com.nokia.carbide.cdt.builder.ImageMakefileViewPathHelper;
import com.nokia.carbide.cpp.epoc.engine.image.*;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.image.IImageMakefileView;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.editors.images.*;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.sdt.utils.ImageUtils;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import java.util.HashMap;
import java.util.Map;

public class ImageTableLabelProvider extends DelayedImageLoadingLabelProviderBase implements ITableLabelProvider {
	private int COLUMN_THUMB, COLUMN_FORMAT, COLUMN_FILES, COLUMN_ENUMERATORS;
	public static final int THUMB_SIZE_X = 32;
	public static final int THUMB_SIZE_Y = 24;
	
	private IMultiImageSource multiImageSource;
	private Map<Pair<IImageSourceReference, ImageFormat>, Image> thumbImages;
	private Image scaledStub;
	private final IMMPAIFInfo aifInfo;
	private ImageMakefileViewPathHelper imageViewPathHelper;
	private MultiImageEditorContextBase editorContext;

	public ImageTableLabelProvider(MultiImageEditorContext editorContext) { 
		super();
		this.editorContext = editorContext;
		this.thumbImages = new HashMap<Pair<IImageSourceReference, ImageFormat>, Image>();
		this.scaledStub = null;
		this.multiImageSource = editorContext.getMultiImageSource();
		this.aifInfo = null;
		IView view = editorContext.getView();
		if (view instanceof IImageMakefileView) {
			this.imageViewPathHelper = new ImageMakefileViewPathHelper(
					(IImageMakefileView) view, 
					new ISymbianBuildContext[] { editorContext.getCarbideBuildConfiguration().getBuildContext() });
		}
		COLUMN_THUMB = 0;
		COLUMN_FORMAT = 1;
		COLUMN_FILES = 2;
		COLUMN_ENUMERATORS = 3;
	}

	public ImageTableLabelProvider(AIFEditorContext editorContext) {
		super();
		this.editorContext = editorContext;
		this.thumbImages = new HashMap<Pair<IImageSourceReference, ImageFormat>, Image>();
		this.scaledStub = null;
		this.multiImageSource = null;
		this.aifInfo = editorContext.getMMPAIFInfo();
		COLUMN_THUMB = 0;
		COLUMN_FILES = 1;
		COLUMN_FORMAT = -1;
		COLUMN_ENUMERATORS = -1;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.providers.DelayedImageLoadingLabelProviderBase#dispose()
	 */
	@Override
	public void dispose() {
		for (Image image : thumbImages.values()) {
			image.dispose();
		}
		if (scaledStub != null)
			scaledStub.dispose();
		thumbImages.clear();
		super.dispose();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		if (!(element instanceof IImageSourceReference))
			return null;
		
		final IImageSourceReference imageSourceReference = (IImageSourceReference) element;

		if (columnIndex == COLUMN_THUMB) {
			// get image at default size
			ImageFormat imageFormat = null;
			if (imageSourceReference instanceof IImageSource) {
				imageFormat = ((IImageSource) imageSourceReference).getImageFormat();
			}
			else if (aifInfo != null) {
				imageFormat = aifInfo.getImageFormat();
			} else {
				return null;
			}
	
			Image image = null;
			try {
				image = editorContext.getComposedImage(imageSourceReference, imageFormat, null);
			} catch (CoreException e) {
				ProjectUIPlugin.log(e);
			}
			
			if (image == null)
				return null;
			
			Image scaled = image;
			// scale to thumb size -- everything must be uniformly scaled
			// or else they all take on the size of the first image (e.g.
			// the hourglass).
			Pair<IImageSourceReference, ImageFormat> scaledImageKey = null; 
			if (image == getStubImage()) {
				scaled = scaledStub;
			} else {
				scaledImageKey = new Pair<IImageSourceReference, ImageFormat>(imageSourceReference, imageFormat);
				scaled = thumbImages.get(scaledImageKey);
			}
			if (scaled == null) {
				scaled = ImageUtils.scaleImage(Display.getDefault(), image, 
						new Point(THUMB_SIZE_X, THUMB_SIZE_Y), true, true);
				if (image == getStubImage()) {
					scaledStub = scaled;
				} else {
					thumbImages.put(scaledImageKey, scaled);
				}
			}
			image.dispose();
			return scaled;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		if (!(element instanceof IImageSourceReference))
			return "???"; //$NON-NLS-1$
		IImageSourceReference imageSourceReference = (IImageSourceReference) element;
		IImageSource imageSource = null;
		if (imageSourceReference instanceof IImageSource) {
			imageSource = (IImageSource) imageSourceReference;
		}
		if (columnIndex == COLUMN_FORMAT) {
			if (imageSource != null) {
				return new ImageFormat(imageSource.isColor(), imageSource.getDepth(), imageSource.getMaskDepth()).toString();
			}
			return ""; //$NON-NLS-1$
		} else if (columnIndex == COLUMN_FILES) {
			return getFileColumnText(imageSourceReference);
		} else if (columnIndex == COLUMN_ENUMERATORS) {
			if (multiImageSource != null && imageSource != null) {
				String imageEnum = ""; //$NON-NLS-1$
				String maskEnum = ""; //$NON-NLS-1$
				String enm = multiImageSource.getGeneratedImageEnumerator(imageSource);
				if (enm != null)
					imageEnum = enm;
				
				if (imageSourceReference instanceof IBitmapSourceReference) {
					enm = multiImageSource.getGeneratedMaskEnumerator(imageSource);
					if (enm != null)
						maskEnum = enm;
				}
				return imageEnum + (maskEnum.length() > 0 ? ", " + maskEnum : ""); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		return null;
	}

	private String getFileColumnText(IImageSourceReference imageSourceReference) {
		IPath sourcePath = imageSourceReference.getPath();
		if (imageViewPathHelper != null) {
			sourcePath = imageViewPathHelper.findCandidateImagePath(sourcePath);
		}
		String filename = sourcePath != null ? sourcePath.toString() : ""; //$NON-NLS-1$
		
		IPath maskPath = null;
		String maskname = ""; //$NON-NLS-1$
		if (imageSourceReference instanceof IBitmapSourceReference) {
			maskPath = ((IBitmapSourceReference) imageSourceReference).getMaskPath();
			if (imageViewPathHelper != null) {
				maskPath = imageViewPathHelper.findCandidateImagePath(maskPath);
			}
		}
		if (maskPath != null) {
			maskname = "," + maskPath.toString(); //$NON-NLS-1$
		}
		return filename + maskname;
	}

	/**
	 * @return
	 */
	public int getThumbColumn() {
		return COLUMN_THUMB;
	}

	/**
	 * @return
	 */
	public int getFilesColumn() {
		return COLUMN_FILES;
	}

	/**
	 * @return
	 */
	public int getEnumeratorColumn() {
		return COLUMN_ENUMERATORS;
	}
	
	
}
