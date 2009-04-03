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
 * Representation of an SVG source file.  This has an
 * implicit mask file.  
 *
 */
public interface ISVGSourceReference extends IImageSourceReference {
	/** provide the implicit mask project-relative filepath */
	IPath getImpliedMaskPath();
}
