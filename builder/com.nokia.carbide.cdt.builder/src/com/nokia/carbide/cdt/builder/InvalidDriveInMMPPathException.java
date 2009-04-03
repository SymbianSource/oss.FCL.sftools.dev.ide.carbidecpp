/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
* This exception is thrown from MMPViewPathHelper when an
* attempt is made to convert a full path into an MMP path and the
* path's drive differs from that of the current SDK.  This may
* either be intentional or an error.  Clients should decide.
*
*
*/
package com.nokia.carbide.cdt.builder;

import org.eclipse.core.runtime.IPath;

import java.text.MessageFormat;

public class InvalidDriveInMMPPathException extends Exception {
	private final IPath path;

	InvalidDriveInMMPPathException(IPath deviceFullPath) {
		super(MessageFormat.format(
				"A path with a drive letter cannot be added to an MMP file ({0})",
				new Object[] { deviceFullPath.toOSString() }));
		this.path = deviceFullPath;
	}
	
	/**
	 * Get the converted full path with the device/drive in place.
	 * @return path, never null
	 */
	public IPath getPath() {
		return path;
	}

	/**
	 * Get the converted full path without a device/drive, as it should
	 * appear in MMP.
	 * @return path, never null
	 */
	public IPath getPathNoDevice() {
		return path.setDevice(null);
	}
}
