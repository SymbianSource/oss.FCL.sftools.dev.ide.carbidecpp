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

package com.nokia.carbide.cpp.epoc.engine.image;

import org.eclipse.core.runtime.IPath;

/**
 * Representation of a source image file.
 *
 */
public interface IImageSourceReference {
	/** tell if the entry is valid (e.g., initialized): path must be set and depth must be nonzero */
	boolean isValid();
	
	/** Copy self */
	IImageSourceReference copy();
	
	/** get the filepath, either relative to the owning view's project location, or absolute in filesystem */
	IPath getPath();
	/** set the filepath, either relative to the owning view's project location, or absolute in filesystem */
	void setPath(IPath path);
	
	/** provide the default mask filepath */
	IPath getDefaultMaskPath(int maskDepth);
}
