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
* This enumeration describes the contexts in which IPath elements
* are used in the IMMPView.  Pass it as a parameter to {@link MMPViewPathHelper}
* instances. 
*
*
*/
package com.nokia.carbide.cdt.builder;

public enum EMMPPathContext {
	SOURCEPATH,
	START_BITMAP_SOURCEPATH,
	USERINCLUDE,
	SYSTEMINCLUDE,
	TARGETPATH,
	DEFFILE,
	AIF_SOURCE,
	AIF_BITMAP,
	SOURCE,
	START_BITMAP_SOURCE,
	/** START RESOURCE block */
	START_RESOURCE,
	/** RESOURCE or SYSTEMRESOURCE statements */
	RESOURCE,
	SYSTEMRESOURCE,
	DOCUMENT;
	
	/**
	 * Tell whether the path is interpreted such that full paths
	 * are EPOCROOT-relative.
	 * @return
	 */
	public boolean useMakeEAbs() {
		return this.equals(SOURCEPATH)
		|| this.equals(DEFFILE)
		|| this.equals(SYSTEMINCLUDE)
		|| this.equals(USERINCLUDE);
	}

	/**
	 * Tell whether the path is interpreted to allow full paths
	 * (in the local filesystem).
	 * @return
	 */
	public boolean useMakeAbs() {
		return this.equals(START_BITMAP_SOURCEPATH)
		|| this.equals(AIF_SOURCE)
		|| this.equals(AIF_BITMAP)
		|| this.equals(SOURCE)
		|| this.equals(START_BITMAP_SOURCE)
		|| this.equals(START_RESOURCE)
		|| this.equals(RESOURCE)
		|| this.equals(SYSTEMRESOURCE)
		|| this.equals(DOCUMENT);
	}

	/**
	 * Tell whether the context specifies files rather than directories.
	 * @return
	 */
	public boolean isFile() {
		return this.equals(DEFFILE) 
		|| this.equals(AIF_SOURCE) 
		|| this.equals(AIF_BITMAP)
		|| this.equals(SOURCE)
		|| this.equals(START_BITMAP_SOURCE)
		|| this.equals(START_RESOURCE)
		|| this.equals(RESOURCE)
		|| this.equals(SYSTEMRESOURCE)
		|| this.equals(DOCUMENT); 
	}
}
