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

package com.nokia.sdt.symbian.images;

import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.internal.project.ui.images.IMultiImageSourceImageContainerModel;
import com.nokia.carbide.cpp.internal.project.ui.images.SymbianMaskedFileImageModel;
import com.nokia.carbide.cpp.ui.images.IURIRepresentableImageModel;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.text.MessageFormat;

public class ImageInfoImageModel extends SymbianMaskedFileImageModel implements IURIRepresentableImageModel {

	private ImageInfo info;
	private String uri;
	private ImageInfo maskInfo;
	private final MultiImageInfo multiImageInfo;

	public ImageInfoImageModel(
			IMultiImageSourceImageContainerModel container,
			IPath projectLocation, MultiImageInfo multiImageInfo, 
			ImageInfo info,
			ImageInfo maskInfo) {
		super(container,
				projectLocation,
				info != null ? new Path(info.getFilePath()) : null, 
				maskInfo != null ? new Path(maskInfo.getFilePath()) : null,
				info != null ?
					maskInfo != null ?
						new ImageFormat(info.isColor(), info.getBitDepth(), maskInfo.getBitDepth()) :
						new ImageFormat(info.isColor(), info.getBitDepth()) :
					new ImageFormat(false, 1)
				);
		Check.checkArg(container);
		Check.checkArg(projectLocation);
		Check.checkArg(multiImageInfo);
		this.multiImageInfo = multiImageInfo;
		this.info = info;

		this.maskInfo = maskInfo;
		if (info != null) {
			if (maskInfo != null) {
				this.uri = MessageFormat.format("{0}?idx={1}&mask={2}", //$NON-NLS-1$
						new Object[] { container.getTargetPath().toOSString(),
						info.getIndex(), maskInfo.getIndex() });
			} else {
				this.uri = MessageFormat.format("{0}?idx={1}", //$NON-NLS-1$
						new Object[] { container.getTargetPath().toOSString(),
						info.getIndex() });
			}
		} else {
			this.uri = ""; //$NON-NLS-1$
		}
	}
	
	public ImageInfoImageModel(
			IMultiImageSourceImageContainerModel container, IPath projectLocation, 
			MultiImageInfo multiImageInfo,
			ImageInfo info) {
		this(container, projectLocation, multiImageInfo, info, null);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + ((maskInfo == null) ? 0 : maskInfo.hashCode());
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
		final ImageInfoImageModel other = (ImageInfoImageModel) obj;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (maskInfo == null) {
			if (other.maskInfo != null)
				return false;
		} else if (!maskInfo.equals(other.maskInfo))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.project.ui.images.IImageModel#getTargetPath()
	 */
	public IPath getTargetPath() {
		return ((MultiImageInfoImageContainerModel) getImageContainerModel()).getTargetPath();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IURIRepresentableImageModel#getURI()
	 */
	public String getURI() {
		return uri;
	}


	/**
	 * @return
	 */
	public MultiImageInfo getMultiImageInfo() {
		return multiImageInfo;
	}
	
}
