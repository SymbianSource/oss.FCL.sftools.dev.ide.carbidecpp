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

import java.util.Map;


/**
 * Generic interface to a set of images represented by one property
 * (e.g. a file and multiple image indices).
 * 
 *
 */
public interface IMultiImagePropertyInfo extends IImagePropertyInfoBase {
	/**
	 * Get the image property info map for the represented images.
	 * <p>
	 * The keys are abstract image ids, which are only meaningful to this interface; they do
	 * not need to map to any property ids.  E.g. for a type which has normal and dimmed images, 
	 * this could provide "normal" and "dimmed".
	 * <p>
	 * This contains entries for every possible image in the container;
	 * depending on the implementation, additions to the map may be allowed.
	 * @return map of abstract image id to image
	 */
	Map<String, IImagePropertyInfo> getImagePropertyInfoMap();

	/**
	 * Get a human-readable representation of the multi-image property
	 * @return
	 */
	String getText();
}
