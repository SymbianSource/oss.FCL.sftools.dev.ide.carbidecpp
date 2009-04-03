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
* This interface maps strings in a legacy *.xxxdef file into 
* valid IPaths.  The implementation especially has to respect
* the possibility that full paths may be passed in, which 
* may point to a workspace on a different machine, and apply
* appropriate logic to get a valid local path in this case.
* 
*
*
*/
package com.nokia.carbide.internal.api.cpp.epoc.engine.image;

import org.eclipse.core.runtime.IPath;
public interface IPathResolver {
	/**
	 * Take the given path, which may represent a full path to some other
	 * machine's workspace, or a valid local full path, or a project-relative
	 * path, and return a valid local path.    
	 * @param path incoming string
	 * @return canonical project-relative or absolute path, never null
	 */
	IPath resolvePath(String path);
}
