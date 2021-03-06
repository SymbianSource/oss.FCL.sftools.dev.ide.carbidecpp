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

package com.nokia.sdt.symbian.images.uriHandlers;

import com.nokia.sdt.symbian.images.*;

/**
 * 
 *
 */
public class UIQThumbURIImageInfoSchemeHandlerFactory implements
		IURIImageInfoSchemeHandlerFactory {

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IURIImageInfoSchemeHandlerFactory#createHandler(com.nokia.sdt.symbian.images.ProjectImageInfo)
	 */
	public IURIImageSchemeHandler createHandler(ProjectImageInfo info) {
		return new UIQThumbURIImageInfoSchemeHandler(info);
	}

}
