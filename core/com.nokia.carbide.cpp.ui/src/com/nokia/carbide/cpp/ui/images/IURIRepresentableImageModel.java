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



/**
 * This is an image model which can represent itself as a URI.
 * Instances of this class may also implement {@link IFileImageModel}
 * (check with <b>instanceof</b>).
 *
 */
public interface IURIRepresentableImageModel extends IImageModel {
	/** Get the URI for this model 
	 * @return URI string, never null
	 */ 
	String getURI();
}
