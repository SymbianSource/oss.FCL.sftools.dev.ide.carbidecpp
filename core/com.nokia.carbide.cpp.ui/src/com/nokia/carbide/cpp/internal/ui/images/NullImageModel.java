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
import com.nokia.carbide.cpp.ui.images.IImageContainerModel;
import com.nokia.carbide.cpp.ui.images.IImageModel;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

/**
 * Image model that doesn't do anything.
 *
 */
public class NullImageModel implements IImageModel {

	/**
	 * @param createNullImageContainerModel
	 */
	public NullImageModel() {
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageModel#getDisplayText()
	 */
	public String getDisplayText() {
		return Messages.getString("NullImageModel.NullImageText"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageModel#getImageContainerModel()
	 */
	public IImageContainerModel getImageContainerModel() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageModel#getImageDescriptor(org.eclipse.swt.graphics.Point)
	 */
	public ImageDescriptor getImageDescriptor(Point size) throws CoreException {
		return new ImageDescriptor() {

			@Override
			public ImageData getImageData() {
				return null;
			}
			
		};
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.ui.images.IImageModel#validate()
	 */
	public IStatus validate() {
		return Status.OK_STATUS;
	}

}
