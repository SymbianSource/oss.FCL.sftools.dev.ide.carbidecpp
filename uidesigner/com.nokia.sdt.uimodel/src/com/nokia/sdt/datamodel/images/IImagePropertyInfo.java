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

package com.nokia.sdt.datamodel.images;


/**
 * Generic interface to image information derived from a property.
 * <p>
 * This is the type created from and applied to compound properties via the
 * converterClass="<instance of ICompoundPropertyValueConverter>" on a 
 * compound type. 
 * <p>
 * <b>NOTE:</b> This is not the component implementation which used to have this name. That
 * has been renamed IImagePropertyRenderingInfo.
 * 
 *
 */
public interface IImagePropertyInfo extends IImagePropertyInfoBase {
	/**
	 * Tell whether the image is empty, i.e. never set.
	 * @return true: image is empty
	 */
	boolean isEmptyImage();
	
	/**
	 * Get the text describing the image, as used by a label provider.  This describes
	 * the source info, if possible, else the property info.
	 * @return text, never null
	 */
	String getText();	
	
	/**
	 * Get an image descriptor for the image. 
	 * @return new image descriptor
	 */
	//ImageDescriptor createImageDescriptor();
	
	/**
	 * Get any #include file needed to reference this image in C/C++ source.
	 * @return filename or null
	 */
	String getIncludeFilename();
}
