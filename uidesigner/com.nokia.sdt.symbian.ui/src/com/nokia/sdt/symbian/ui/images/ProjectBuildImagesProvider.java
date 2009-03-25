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

package com.nokia.sdt.symbian.ui.images;

import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.symbian.images.ProjectImageInfo;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import java.util.*;

/**
 * The content provider for the thumbnail viewer which shows only images
 * available in the build.  
 * 
 *
 */
public class ProjectBuildImagesProvider implements IStructuredContentProvider {

	public ProjectBuildImagesProvider() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		viewer.refresh();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		List<IImageModel> files = Collections.EMPTY_LIST;
		if (inputElement instanceof ProjectImageInfo) {
			ProjectImageInfo info = (ProjectImageInfo) inputElement;
			files = new ArrayList<IImageModel>();
			for (IImageModel model : info.getProjectImageModels()) {
				files.add(model);
			}
		}
		return (IImageModel[]) files.toArray(new IImageModel[files.size()]);
	}

	
}
