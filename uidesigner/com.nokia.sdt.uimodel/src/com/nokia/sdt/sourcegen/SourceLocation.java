/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.sourcegen;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

/**
 * Encapsulate info about a location in source, for use by
 * event handler lookup
 * 
 *
 */
public class SourceLocation {
	/** resolved file */
	public IFile file;
	/** project-relative path */
	public IPath filePath;
	/** location path */
	public String locationPath;
	/** start offset in file for location */
	public int offset;
	/** length of location in chars */
	public int length;
	/** the insertion offset (file-relative) */
	public int insertOffset;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SourceLocation)) 
			return false;
		SourceLocation loc = (SourceLocation) obj;
		return loc.filePath.equals(filePath) && loc.locationPath.equals(locationPath);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return filePath.hashCode() ^ locationPath.hashCode();
	}
}