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

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.model.ETristateFlag;
import com.nokia.carbide.cpp.epoc.engine.model.bsf.IBSFView;

import org.eclipse.core.runtime.IPath;

import java.util.Map;

/**
 * This interface defines a single BSF platform.
 * <p>
 *  Once a platform is available
 * from a catalog, it is contained in a hierarchy of platforms, terminating at
 * built-in platforms (like ARMV5).
 * <p>
 *  A platform's "customized platform name" in
 * {@link #getCustomizedPlatformName()} tells what platform this one customizes,
 * i.e. the parent platform. If the parent is also a BSF platform, then that
 * will be available from {@link #getCustomizedPlatform()}.
 * 
 * 
 */
public interface IBSFPlatform {
	/**
	 * Get the catalog this platform is contained in.
	 * @return IBSFCatalog, never null
	 */
	IBSFCatalog getCatalog();
	
	/**
	 * Get the platform's name, as seen in the .bsf filename.
	 * 
	 * @return name, never null
	 */
	String getName();

	/**
	 * Get the full filesystem path to the BSF used.
	 * <p>
	 * This may be used
	 * to retrieve the {@link IBSFView} via {@link EpocEnginePlugin#runWithBSFView(IPath, com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration, com.nokia.carbide.cpp.epoc.engine.IBSFViewRunnable)}
	 * to get more information about the BSF. 
	 * 
	 * @return path, never null
	 */
	IPath getBSFPath();

	/**
	 * Get the name of the customized platform, which matches the case of
	 * a known platform if possible, else is all-caps.
	 * @return platform name or null
	 */
	String getCustomizedPlatformName();
	
	/**
	 * Get the customized (parent) platform, if it is a BSF platform.
	 * 
	 * @return parent, or null for non-BSF platform
	 */
	IBSFPlatform getCustomizedPlatform();
	
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

	/**
	 * Tell if the platform is a variant (but not a virtual variant).
	 * @return
	 */
	boolean isVariant();
	
	/**
	 * Tell if the platform is a virtual variant
	 * @return
	 */
	boolean isVirtualVariant();
	
	/**
	 * Get the compile with parent flag for this BSF in isolation.
	 * @return {@link ETristateFlag} describing whether this BSF specifies the flag
	 * and if it's COMPILEWITHPARENT or COMPILEALONE
	 */
	ETristateFlag getCompileWithParent();
	
	/**
	 * Get the disposition of the "compile with parent" setting, as inherited
	 * by parents or specified in this platform.
	 */
	boolean getEffectiveCompileWithParent();
	
	/**
	 * Get the map of customization options for this BSF in isolation.
	 * @return map of key to value
	 */
	Map<String, String> getCustomizationOptions();
	
}
