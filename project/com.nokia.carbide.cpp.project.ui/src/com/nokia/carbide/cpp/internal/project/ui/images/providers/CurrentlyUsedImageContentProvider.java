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

import com.nokia.carbide.cpp.epoc.engine.image.*;

import org.eclipse.core.runtime.IPath;

import java.util.*;

/**
 * The content of the thumbnail for view project images.  This is fed
 * a base directory (e.g. a project's location).  
 * Any image fles in the project (optionally excluding those
 * already referenced by the editor) and not illegal for bmconv/mifconv are shown.
 *
 */
public class CurrentlyUsedImageContentProvider extends BaseImageContentProvider {

	public CurrentlyUsedImageContentProvider(IMultiImageSource multiImageSource) {
		super(multiImageSource);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		List<IPath> files = Collections.EMPTY_LIST;
		if (inputElement instanceof IMultiImageSource) {
			files = new ArrayList<IPath>();
			for (IImageSource imageSource : ((IMultiImageSource) inputElement).getSources()) {
				IPath path = imageSource.getPath();
				if (path != null)
					files.add(path);
				if (imageSource instanceof IBitmapSource) {
					IBitmapSource bmSource = (IBitmapSource) imageSource;
					path = bmSource.getMaskPath();
					if (path != null)
						files.add(path);
				}
			}
		}
		filterImageFiles(files, false);
		return (IPath[]) files.toArray(new IPath[files.size()]);
	}
}
