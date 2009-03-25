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

import org.eclipse.core.runtime.IPath;

/**
 * This interface represents a masked image which has an optional mask image
 * paired with the bitmap of {@link IFileImageModel}.  A masked image may
 * have an image and a mask of different sizes; this will return an image
 * descriptor that tiles the mask over the image (or vice versa).
 *
 */
public interface IMaskedFileImageModel extends IFileImageModel {
	/** Method for composing a mask with an image */
	enum MaskCompositionMethod {
		/** Mask is scaled to fit image */
		SCALING,
		/** Mask is tiled to fit image */
		TILING
	};
	
	/** Get the mask composition method
	 * 
	 */
	MaskCompositionMethod getMaskCompositionMethod();
	
	/** Get the absolute host filesystem path to the image. 
	 * @return IPath or <code>null</code>
	 */
	IPath getMaskSourceLocation();
	
	/** Get the project-relative path to the image. 
	 * @return IPath or <code>null</code> if not in the project
	 */
	IPath getMaskProjectPath();
	
	
}
