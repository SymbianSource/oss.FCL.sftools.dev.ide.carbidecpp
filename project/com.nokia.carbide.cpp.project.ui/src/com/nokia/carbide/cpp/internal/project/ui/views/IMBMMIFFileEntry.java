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
package com.nokia.carbide.cpp.internal.project.ui.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

/**
 * Interface used to attach actions to *.mbm or *.mif entries in the SPN view.
 *
 */
public interface IMBMMIFFileEntry {
	/** Get the path to the MMP or image makefile */
	IFile getModelFile();

	/**
	 * Get the target file path for the multi-image source represented by the MBM/MIF.
	 * @return
	 */
	IPath getTargetFilePath();
}
