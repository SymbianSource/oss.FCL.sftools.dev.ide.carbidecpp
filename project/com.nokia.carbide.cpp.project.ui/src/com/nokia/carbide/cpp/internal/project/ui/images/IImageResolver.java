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
*
*/
package com.nokia.carbide.cpp.internal.project.ui.images;

import org.eclipse.core.runtime.IPath;

/**
 * Resolve a relative path to an absolute path
 *
 */
public interface IImageResolver {
	/** Take a relative or absolute path -- or a null one -- and return
	 * the absolute path.
	 * @param path
	 * @return
	 */
	IPath resolvePath(IPath path);
}
