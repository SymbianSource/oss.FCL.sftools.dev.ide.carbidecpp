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

import java.util.List;

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
	 * Get the platform's name, from the VARIANT keyword value.
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
	 * Get the name of the variant this SBV platform extends
	 * @return
	 */
	String getExtendedVariantName();
	
	/**
	 * Get the variant platform this platform extends
	 * @return The ISBVPlatform, or null if it does not extend a platform
	 */
	public ISBVPlatform getExtendedVariant();
	
	/**
	 * Is the VIRTUAL keyword present?
	 * @return
	 */
	public boolean isVirtual();
	
	/**
	 * Get the 
	 * @return The path to the defined HRH file, null if none.
	 */
	public IPath getBuildVariantHRHFile();
	
	/**
	 * The list of BUILD_INCLUDE paths from the variant and all it's parents
	 * @return list of paths
	 */
	List<IPath> getBuildIncludePaths();
	
	/**
	 * The list of ROM_INCLUDE paths from the variant and all it's parents
	 * @return list of paths
	 */
	List<IPath> getROMBuildIncludePaths();
	
}
