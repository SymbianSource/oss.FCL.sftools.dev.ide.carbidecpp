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

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.ui.images.*;

import java.io.*;
import java.util.*;

/**
 * The content of the thumbnail to view filesystem images in a single directory.
 * This is fed a base File.
 * Only the images not already referenced by the editor are shown.
 *
 */
public class FilesystemImageContentProvider extends BaseImageContentProvider {

	private final IImageLoader imageLoader;

	public FilesystemImageContentProvider(IImageLoader loader, IMultiImageSource multiImageSource) {
		super(multiImageSource);
		this.imageLoader = loader;
	}

	public FilesystemImageContentProvider(IImageLoader loader, IMMPAIFInfo aifInfo) {
		super(aifInfo);
		this.imageLoader = loader;
	}

	public FilesystemImageContentProvider(IImageLoader loader, boolean allowBMP, boolean allowSVG) {
		super(allowBMP, allowSVG);
		this.imageLoader = loader;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		List<IImageModel> files = Collections.EMPTY_LIST;
		if (inputElement instanceof File) {
			IImageContainerModel containerModel = ImageModelFactory.createFileImageContainerModel(
					imageLoader, (File)inputElement, getFileNameFilter(), false);
			files = new ArrayList<IImageModel>();
			IImageModel[] models = containerModel.createImageModels();
			files.addAll(Arrays.asList(models));
			Collections.sort(files, new Comparator<IImageModel>() {

				public int compare(IImageModel o1, IImageModel o2) {
					if (o1 instanceof IFileImageModel && o2 instanceof IFileImageModel) {
						return ((IFileImageModel) o1).getSourceLocation().lastSegment().compareToIgnoreCase(
								((IFileImageModel) o2).getSourceLocation().lastSegment());
					} else if (o1 instanceof IFileImageModel)
						return -1;
					else if (o2 instanceof IFileImageModel) 
						return 1;
					else
						return 0;
				}
				
			});
		}
		filterImageFiles(files, true);
		return files.toArray();
	}
	
	/**
	 * @return
	 */
	private FilenameFilter getFileNameFilter() {
		return new FilenameFilter() {

			public boolean accept(File dir, String name) {
				if (new File(dir, name).isDirectory())
					return false;
				String lowerName = name.toLowerCase();
				if ((allowBMP && lowerName.endsWith(".bmp"))  //$NON-NLS-1$
						|| (allowSVG && lowerName.endsWith(".svg"))) { //$NON-NLS-1$
					return true;
				}
				return false;			
			}
		};
	}

}
