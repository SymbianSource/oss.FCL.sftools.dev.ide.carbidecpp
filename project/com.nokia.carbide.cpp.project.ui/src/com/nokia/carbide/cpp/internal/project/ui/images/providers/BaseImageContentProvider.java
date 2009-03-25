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

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import java.util.Iterator;
import java.util.List;

/**
 * The content provider for the thumbnail viewer.  This provides a way
 * to filter out images not already referenced by the editor or illegal for
 * bmconv/mifconv.
 *
 */
public abstract class BaseImageContentProvider implements IStructuredContentProvider {

	protected boolean allowSVG;
	protected boolean allowBMP;
	protected IMultiImageSource multiImageSource;
	protected IMMPAIFInfo aifInfo;

	/**
	 * @param allowSVG 
	 * @param allowBMP 
	 */
	public BaseImageContentProvider(IMultiImageSource multiImageSource) {
		this.multiImageSource = multiImageSource;
		this.allowBMP = multiImageSource.isBitmapSourceAllowed();
		this.allowSVG = multiImageSource.isSVGSourceAllowed();
	}

	/**
	 * @param allowSVG 
	 * @param allowBMP 
	 */
	public BaseImageContentProvider(IMMPAIFInfo aifInfo) {
		this.aifInfo = aifInfo;
		this.allowBMP = true;
		this.allowSVG = false;
	}

	/**
	 * @param allowSVG 
	 * @param allowBMP 
	 */
	public BaseImageContentProvider(boolean allowBMP, boolean allowSVG) {
		this.multiImageSource = null;
		this.allowBMP = allowBMP;
		this.allowSVG = allowSVG;
	}

	/**
	 * Filter out unavailable image files.  We remove those which are
	 * already represented in the editor and those which have illegal
	 * filenames.
	 * @param files
	 */
	protected void filterImageFiles(List files, boolean removeReferenced) {
		for (Iterator iter = files.iterator(); iter.hasNext(); ) {
			Object entry = iter.next();
			IPath path = null;
			if (entry instanceof IPath)
				path = (IPath) entry;
			else if (entry instanceof IFileImageModel)
				path = ((IFileImageModel) entry).getSourceLocation();
			else
				continue;
			
			if (removeReferenced && multiImageSource != null && multiImageSource.findMatchingSource(path) != null) {
				iter.remove();
			} else if (!isLegalFilename(path.lastSegment())) {
				iter.remove();
			}
		}
	}
	

	/** 
	 * Tell whether the string forms a legal filename for Symbian tools.
	 * @param string
	 * @return
	 */
	protected boolean isLegalFilename(String string) {
		return string.matches("[a-zA-Z_0-9.]+"); //$NON-NLS-1$
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
	}

}
