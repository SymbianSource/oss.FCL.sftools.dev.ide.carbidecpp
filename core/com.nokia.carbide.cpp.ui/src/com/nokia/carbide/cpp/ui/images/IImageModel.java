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

package com.nokia.carbide.cpp.ui.images;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Point;

/**
 * This interface represents an image which exists somewhere on the host
 * system (usually the project, but always the filesystem) and 
 * which may exist somewhere on the target as well.
 * <p>
 * All these models are read-only.  Once constructed they are not expected to change.
 *
 */
public interface IImageModel {
	/**
	 * Get the container model.
	 * @return IImageContainerModel or null
	 */
	IImageContainerModel getImageContainerModel();

	/*
	 * Must be implemented
	 */
	boolean equals(Object obj);
	
	/*
	 * Must be implemented
	 */
	int hashCode();
	
	/**
	 * Get the display label for the image
	 * @return String
	 */
	String getDisplayText();

	/**
	 * Get the image descriptor for this model, optionally scaled to a given size.
	 * <p>
	 * Scaling is implemented at this level since some image types, like SVGs,
	 * must be scaled at the lowest level, and the original bitmap cannot 
	 * be scaled and maintain fidelity.
	 * @param size the size to scale the image to, or <code>null</code> for original size
	 * @return ImageDescriptor, not guaranteed to be the same between calls, though 
	 * as per {@link IImageDescriptorProvider#getImageDescriptor(Point)}, the provided 
	 * {@link ImageDescriptor#getImageData()} will be the same for the <code>null</code> size
	 * @throws CoreException 
	 */
	ImageDescriptor getImageDescriptor(Point size) throws CoreException;
	
	/**
	 * Validate self.
	 * @return IStatus (never null or Status.OK_STATUS allowed)
	 */
	IStatus validate();
}
