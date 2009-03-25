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

import org.eclipse.core.runtime.Path;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for a directory (optionally recursive) providing image models.
 *
 */
public class FileImageContainerModel extends ImageContainerModelBase implements IImageContainerModel {

	private final File baseDir;
	private final boolean recursive;
	private final FilenameFilter fileNameFilter;

	public FileImageContainerModel(File baseDir, IImageLoader imageLoader,
			FilenameFilter fileNameFilter, boolean recursive) {
		super(new Path(baseDir.getAbsolutePath()), imageLoader);
		this.baseDir = baseDir;
		this.fileNameFilter = fileNameFilter;
		this.recursive = recursive;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageContainerModel#createEditorProvider()
	 */
	public IImageContainerEditorProvider createEditorProvider() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageContainerModel#createImageModels()
	 */
	public IImageModel[] createImageModels() {
		List<IImageModel> models = new ArrayList<IImageModel>();
		getImageModels(baseDir, models);
		return (IImageModel[]) models.toArray(new IImageModel[models.size()]);
	}
	
	private void getImageModels(File dir, List<IImageModel> models) {
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				if (recursive) {
					getImageModels(file, models);
				}
			} else if (fileNameFilter == null || fileNameFilter.accept(dir, file.getName())){
				models.add(createFileImageModel(file));
			}
		}
	}

	protected IImageModel createFileImageModel(File file) {
		String relativePath = file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
		return new FileImageModel(this, new Path(baseDir.getAbsolutePath()),
				new Path(relativePath).makeRelative());
				
	}

}
