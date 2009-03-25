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


import com.nokia.carbide.cpp.ui.images.IImageModel;

import org.eclipse.core.runtime.IStatus;

/**
 * This interface handles image lookup and resolution for images available during a build.
 * 
 *
 */
public interface IURIImageSchemeHandler {
	/**
	 * Validate the URI, which includes checking the scheme for validity
	 * and seeing if the path, if it resolves to a known entity,
	 * is valid for the scheme and query.  Does <b>not</b> detect file existence.
	 * @param scheme the scheme of the URI
	 * @param path the path portion of a URI
	 * @param query the query (after '?'), or null
	 * @return IStatus describing why path is invalid, or Status.OK_STATUS
	 */
	IStatus validate(String scheme, String path, String query);
	
	/**
	 * Get an image model for the image.  
	 * @param scheme the scheme of the URI
	 * @param path the path portion of a URI
	 * @param query the query (after '?'), or null
	 * @param size the target size, or null for native size
	 * @return IImageModel or <code>null</code> if the image does not exist in the build
	 */
	IImageModel getImageModel(String scheme, String path, String query);
}
