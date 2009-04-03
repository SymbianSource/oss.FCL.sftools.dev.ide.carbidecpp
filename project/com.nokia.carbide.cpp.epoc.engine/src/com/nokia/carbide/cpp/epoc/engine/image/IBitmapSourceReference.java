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
 * Model for a bitmap source file without color information.
 *
 */
public interface IBitmapSourceReference extends IImageSourceReference {
	/** get the mask filepath (may be null), either relative to the owning view's project location, or absolute in filesystem */
	IPath getMaskPath();
	/** set the mask filepath (may be null), either relative to the owning view's project location, or absolute in filesystem */
	void setMaskPath(IPath filepath);
}
