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

package com.nokia.carbide.internal.api.cpp.epoc.engine.image;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;

/**
 * Interface for converting a .mbmdef or .mifdef file's contents
 * to model contents. 
 *
 */
public interface IMbmMifDefFileConverter {
	/** 
	 * Create multi-image source from the given file text.
	 * @param targetPath the Z:-relative target directory for the .mbm or .mif
	 * @param defName name of the .mbmdef or .mifdef file  
	 * @param fileText contents of the file  
	 * @param resolver resolver for full paths
	 * @throws CoreException
	 * @return IMultiImageSource, never null
	 */
	IMultiImageSource convert(IPath targetPath, String defName, String fileText, IPathResolver resolver) throws CoreException;
	
}
