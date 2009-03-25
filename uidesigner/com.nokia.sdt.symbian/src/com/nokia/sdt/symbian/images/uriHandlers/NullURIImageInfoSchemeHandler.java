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

import com.nokia.carbide.cpp.ui.images.IImageModel;
import com.nokia.sdt.symbian.SymbianPlugin;
import com.nokia.sdt.symbian.images.IURIImageSchemeHandler;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * A scheme handler which does not know how to find images but validates them.
 * 
 *
 */
public class NullURIImageInfoSchemeHandler implements
		IURIImageSchemeHandler {

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IURIImageInfoSchemeHandler#validate(com.nokia.sdt.datamodel.images.IProjectImageInfo, java.lang.String, java.lang.String, java.lang.String)
	 */
	public IStatus validate(String scheme, String path, String query) {
		return Status.OK_STATUS;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.IURIImageSchemeHandler#getImageModel(java.lang.String, java.lang.String, java.lang.String)
	 */
	public IImageModel getImageModel(String scheme, String path, String query) {
		return null;
	}

	protected IStatus createStatus(int severity, String message) {
		return Logging.newStatus(SymbianPlugin.getDefault(), 
				severity, message);
	}
}
