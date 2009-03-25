/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.internal.ui.images;

import com.nokia.carbide.cpp.ui.images.*;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IPath;

/**
 * Base for image container models that are hosted in a file.  
 *
 */
public abstract class FileBasedImageContainerModelBase extends ImageContainerModelBase implements IFileBasedImageContainerModel {

	protected final IPath modelPath;

	/**
	 * 
	 */
	public FileBasedImageContainerModelBase(IPath projectPath, IImageLoader imageLoader, IPath modelFile) {
		super(projectPath, imageLoader);
		Check.checkArg(projectPath);
		Check.checkArg(modelFile);
		this.modelPath = modelFile;
	}

	public IPath getModelFilePath() {
		return modelPath;
	}
	

}