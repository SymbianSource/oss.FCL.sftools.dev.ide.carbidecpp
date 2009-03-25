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

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * Interface for converting an .aifdef file's contents
 * to model contents. 
 *
 */
public interface IAifDefFileConverter {
	/** 
	 * Create AIF model info from the given file text.
	 * @param targetPath Z: relative directory of target  
	 * @param defName filename of .aifdef file
	 * @param fileText contents of .aifdef file
	 * @param resolver resolver for full paths  
	 * @throws CoreException
	 * @return IMMPAifInfo, never null
	 */
	IMMPAIFInfo convert(IPath targetPath, String defName, String fileText, IPathResolver resolver) throws CoreException;
	
}
