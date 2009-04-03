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

import com.nokia.carbide.cpp.ui.images.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/**
 * This provides IFilesystemImageModel elements for image-like files under the
 * input.
 *
 */
public class FilesystemImageModelContentProvider implements
		IStructuredContentProvider {

	private IImageLoader imageLoader = ImageModelFactory.createImageLoader(true);
	private final Pattern imageFilePattern;
	private final boolean recursive;

	/**
	 * Create with the regex for filenames to consider as image files.  In addition
	 * to this check, {@link #accept(File)} may be overridden to filter files.
	 * @param pattern
	 * @param recursive 
	 */
	public FilesystemImageModelContentProvider(Pattern pattern, boolean recursive) {
		this.imageFilePattern = pattern;
		this.recursive = recursive;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		File baseDir = null;
		if (inputElement instanceof File)
			baseDir = (File) inputElement;
		else if (inputElement instanceof IPath)
			baseDir = ((IPath)inputElement).toFile();
		else
			return null;
		
		IImageContainerModel directoryModel = ImageModelFactory.createFileImageContainerModel(
				imageLoader, baseDir, 
				new FilenameFilter() {
					public boolean accept(File dir, String name) {
						boolean match = imageFilePattern.matcher(name).matches();
						return match && FilesystemImageModelContentProvider.this.accept(new File(dir, name));
					}
					
				}, recursive);
		return directoryModel.createImageModels();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		if (imageLoader != null)
			imageLoader.dispose();
		imageLoader = null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/**
	 * In addition to the filtering on filenames, you may implement this to 
	 * filter images.  This test is performed only if the filename filter passes.
	 * @param file
	 * @return true to accept file, false to reject
	 */
	protected boolean accept(File file) {
		return true;
	}
}
