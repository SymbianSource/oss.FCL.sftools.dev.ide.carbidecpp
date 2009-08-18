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

import com.nokia.carbide.cpp.internal.ui.Messages;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Point;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A model for an image that only exists on disk or in the project but which
 * is not yet restricted by an image format.  
 *
 */
public class FileImageModel implements IFileImageModel, IImageDataProviderListener {

	private IPath sourceLocation;
	protected IPath basePath;
	private IPath relativePath; 
	private final IImageContainerModel containerModel;
	private IImageDataProvider imageDataProvider;

	/**
	 * Construct with an file path, with an optional base path
	 * for displaying relative to a path
	 * @param containerModel
	 * @param basePath base path
	 * @param filePath relative path to base
	 */
	public FileImageModel(IImageContainerModel containerModel, IPath basePath, IPath filePath) {
		Check.checkArg(containerModel);
		this.containerModel = containerModel;
		this.basePath = basePath;
		this.relativePath = filePath;
		if (filePath != null) {
			this.sourceLocation = basePath != null && filePath.getDevice() == null && !filePath.isAbsolute()
				? basePath.append(filePath) : filePath;
		}
		
		if (sourceLocation.getDevice() == null){
			sourceLocation = sourceLocation.setDevice(basePath.getDevice());
		}
		
	}
	
	/**
	 * 
	 */
	public FileImageModel(IImageContainerModel containerModel, IProject project, IPath projectPath) {
		Check.checkArg(containerModel);
		this.containerModel = containerModel;
		this.basePath = ProjectUtils.getRealProjectLocation(project);
		this.relativePath = projectPath;
		if (projectPath != null) {
			if (projectPath.isAbsolute() || projectPath.getDevice() != null)
				this.sourceLocation = projectPath;
			else
				this.sourceLocation = basePath.append(projectPath);
		}
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sourceLocation == null) ? 0 : sourceLocation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FileImageModel other = (FileImageModel) obj;
		if (sourceLocation == null) {
			if (other.sourceLocation != null)
				return false;
		} else if (!sourceLocation.equals(other.sourceLocation))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return sourceLocation != null ? sourceLocation.toString() : Messages.getString("FileImageModel.NoImageLabel"); //$NON-NLS-1$
	} 
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IFileImageModel#getProjectPath()
	 */
	public IPath getSourcePath() {
		return relativePath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IFileImageModel#getSourceLocation()
	 */
	public IPath getSourceLocation() {
		return sourceLocation;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IFileImageModel#getTargetPath()
	 */
	public IPath getTargetPath() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageModel#getDisplayText()
	 */
	public String getDisplayText() {
		return relativePath != null ? relativePath.toPortableString() : Messages.getString("FileImageModel.NoImageLabel"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageModel#getImageContainerModel()
	 */
	public IImageContainerModel getImageContainerModel() {
		return containerModel;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageModel#getImageData(org.eclipse.swt.graphics.Point)
	 */
	public ImageDescriptor getImageDescriptor(Point size) throws CoreException {
		if (imageDataProvider == null) {
			imageDataProvider = containerModel.getImageLoader().createImageDataProvider(sourceLocation);
			if (imageDataProvider != null) {
				imageDataProvider.addListener(this);
			}
		}
		return ImageDescriptor.createFromImageData(imageDataProvider.getImageData(size));
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageModel#validate()
	 */
	public IStatus validate() {
		if (sourceLocation != null) {
			File file = sourceLocation.toFile();
			if (!file.exists()) {
				return Logging.newStatus(CarbideUIPlugin.getDefault(),
						new FileNotFoundException(file.getAbsolutePath()));
			}
		}
		return Status.OK_STATUS;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageDataProviderListener#changed(com.nokia.carbide.cpp.ui.images.IImageDataProvider)
	 */
	public void changed(IImageDataProvider provider) {
		containerModel.fireListeners();
	}

}
