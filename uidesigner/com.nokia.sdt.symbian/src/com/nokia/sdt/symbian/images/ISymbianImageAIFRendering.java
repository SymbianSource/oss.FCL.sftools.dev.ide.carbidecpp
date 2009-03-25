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

import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IImagePropertyRenderingInfo;
import com.nokia.sdt.datamodel.images.IImageRendering;
import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.core.runtime.IPath;

/**
 * Extension to generic image rendering allowing for rendering
 * from AIF files. 
 * 
 *
 */
public interface ISymbianImageAIFRendering extends IImageRendering {
	/**
	 * Select the image to render from an AIF file.  The initial
	 * parameters are used to determine {@link IImagePropertyRenderingInfo} information
	 * and the last two parameters provide the index of the image
	 * (bitmap and mask) to use.  
	 * 
	 * @param instance the instance holding the property
	 * @param propertyPath the node path to the property, may be null
	 * @param laf
	 *            the look and feel instance for the current layout, may be null
	 * @param projectAifFile the path to the RSS file for the AIF entry in question,
	 * or null for the primary (project) AIF file, else the first if not obvious
	 * @param imageIndex 0-based index of full image in file (bitmap + mask) 
	 * @see IImageRendering#setRenderingModel(String)
	 */
	void setImageFromAIF(IComponentInstance instance, String propertyPath, ILookAndFeel laf,
			IPath projectAifFile,
			int imageIndex);
}
