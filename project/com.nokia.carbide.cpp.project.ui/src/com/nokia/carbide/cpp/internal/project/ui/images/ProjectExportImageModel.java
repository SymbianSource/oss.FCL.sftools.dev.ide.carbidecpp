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
package com.nokia.carbide.cpp.internal.project.ui.images;

import com.nokia.carbide.cpp.internal.ui.images.FileImageModel;
import com.nokia.carbide.cpp.ui.images.IImageContainerModel;
import com.nokia.carbide.cpp.ui.images.IURIRepresentableImageModel;

import org.eclipse.core.runtime.IPath;

public class ProjectExportImageModel extends FileImageModel implements IURIRepresentableImageModel {

	private final IPath targetPath;
	private String uri;

	/**
	 * @param projectExportImageContainerModel
	 * @param realProjectLocation
	 * @param sourcePath
	 * @param targetPath
	 */
	public ProjectExportImageModel(
			IImageContainerModel containerModel,
			IPath realProjectLocation, IPath sourcePath, IPath targetPath) {
		super(containerModel, realProjectLocation, sourcePath);
		this.targetPath = targetPath;
		this.uri = targetPath.toOSString();
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((targetPath == null) ? 0 : targetPath.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ProjectExportImageModel other = (ProjectExportImageModel) obj;
		if (targetPath == null) {
			if (other.targetPath != null)
				return false;
		} else if (!targetPath.equals(other.targetPath))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageModel#getDisplayText()
	 */
	public String getDisplayText() {
		return uri;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.internal.project.ui.images.IImageModel#getTargetPath()
	 */
	public IPath getTargetPath() {
		return targetPath;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IURIRepresentableImageModel#getURI()
	 */
	public String getURI() {
		return uri;
	}

}
