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
*/
package com.nokia.carbide.cpp.sdk.core;

import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;

/**
 * This interface defines a single Symbian Binary Variation (SBV) platform.
 * <p>
 *  Once a platform is available
 * from a catalog, it is contained in a hierarchy of platforms, terminating at
 * built-in platforms (like ARMV5). 
 */
public interface ISBVPlatform {
	/**
	 * Get the catalog this platform is contained in.
	 * @return ISBVCatalog, never null
	 */
	ISBVCatalog getCatalog();
	
	/**
	 * Get the platform's name, as seen in the .var filename.
	 * 
	 * @return name, never null
	 */
	String getName();

	/**
	 * Get the full file system path to the .var used.
	 * <p>
	 * This may be used
	 * to retrieve the {@link ISBVView} via {@link EpocEnginePlugin#runWithSBVView(IPath, com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration, com.nokia.carbide.cpp.epoc.engine.ISBVViewRunnable)}
	 * to get more information about the SBV. 
	 * 
	 * @return path, never null
	 */
	IPath getSBVPath();

	/**
	 * Get the system include path for this variant.
	 * @return full filesystem path to the include directory for the variant,
	 * which may be the parent/customized platform's directory for virtual
	 * variant, or null if no non-virtual parent exists.
	 */
	IPath getSystemIncludePath();
	
	/**
	 * Get the system include file paths needed for this variant and all its parents.
	 * This does not include the epoc32\include\oem directory, which is presumed
	 * for all platforms.
	 * @return array of full filesystem paths, never null 
	 */
	IPath[] getSystemIncludePaths();
}
