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

import com.nokia.carbide.cpp.ui.images.IImageContainerModel;
import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Point;

/**
 * This is a model wrapper that 
 *
 */
public class FixedSizeImageModelWrapper implements IImageModel {

	private final IImageModel model;
	private final Point size;

	/**
	 * @param model
	 * @param size
	 */
	public FixedSizeImageModelWrapper(IImageModel model, Point size) {
		Check.checkArg(model);
		Check.checkArg(size);
		this.model = model;
		this.size = size;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageModel#getDisplayText()
	 */
	public String getDisplayText() {
		return model.getDisplayText();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageModel#getImageContainerModel()
	 */
	public IImageContainerModel getImageContainerModel() {
		return model.getImageContainerModel();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageModel#getImageDescriptor(org.eclipse.swt.graphics.Point)
	 */
	public ImageDescriptor getImageDescriptor(Point ignoredSize) throws CoreException {
		return model.getImageDescriptor(this.size);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageModel#validate()
	 */
	public IStatus validate() {
		return model.validate();
	}

}
