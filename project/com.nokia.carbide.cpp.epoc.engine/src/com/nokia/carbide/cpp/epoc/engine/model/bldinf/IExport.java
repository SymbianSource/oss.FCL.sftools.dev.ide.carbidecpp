/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.model.bldinf;

import org.eclipse.core.runtime.IPath;

/**
 * Representation of an export in a PRJ_EXPORTS/PRJ_TESTEXPORTS block.
 *
 */
public interface IExport {
	/** tell if valid, e.g. initialized */
	boolean isValid();
	
	/** Get project-relative path (never null) */
	IPath getSourcePath();
	/** Set project-relative path (never null) */
	void setSourcePath(IPath path);
	/** Get EPOCROOT-relative path. 
	 * <p>
	 * If full path with a drive letter, it refers to an EPOC-hosted drive 
	 * (e.g. c:\private --> $(EPOCROOT)\data\c\private).
	 * <p>
	 * If absolute path with no drive letter, it is EPOCROOT-relative.
	 * <p>
	 * Else, if relative, it is project-relative.
	 * <p>
	 * Finally, it may be null for the default \epoc32\include location.
	*/
	IPath getTargetPath(); 
	/** Set EPOCROOT-relative path.
	 * <p>
	 * If full path with a drive letter, it refers to an EPOC-hosted drive 
	 * (e.g. c:\private --> $(EPOCROOT)\data\c\private).
	 * <p>
	 * If absolute path with no drive letter, it is EPOCROOT-relative.
	 * <p>
	 * Else, if relative, it is project-relative.
	 * <p>
	 * Finally, it may be null for the default \epoc32\include location.
	*/
	void setTargetPath(IPath path);
	
	/**
	 * Tell whether :zip applies
	 * @return
	 */
	boolean isZipped();
	/**
	 * Tell whether :zip applies
	 */
	void setZipped(boolean zipped);

	/**
	 * @return a copy of the data
	 */
	IExport copy();

}
