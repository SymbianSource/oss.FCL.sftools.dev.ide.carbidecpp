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

import com.nokia.cpp.internal.api.utils.core.*;

/**
 * This is a catalog of all the BSF files detected for a given SDK.
 *
 */
public interface IBSFCatalog {
	/**
	 * Get any problems detected while parsing the BSF files.
	 * @return array of messages, never null
	 */
	IMessage[] getMessages();
	
	/** 
	 * Get the array of BSF platforms detected.  Each corresponds to
	 * a *.bsf file.  This does not include the built-in platforms.
	 * @return array, never null
	 */
	IBSFPlatform[] getPlatforms();
	
	/**
	 * Get the variant platforms (but not virtual variants) among
	 * the platforms detected.  These are the ones that will be available
	 * when, e.g., "PRJ_PLATFORMS VARIANT" is used.
	 * @return array, never null
	 */
	IBSFPlatform[] getVariantPlatforms();
	
	/**
	 * Find a platform with the given name.  
	 * @param name platform name, case doesn't matter
	 * @return platform or null
	 */
	IBSFPlatform findPlatform(String platform);
	
	/**
	 * Get the platforms to compile with this one, not including this one.
	 * @return array of IBSFPlatform included when this platform is built;
	 * never null
	 */
	IBSFPlatform[] getAdditionalBuiltPlatforms(String platform);

	/**
	 * Get the release platform name for the given platform.  This is the directory
	 * name used for builds, e.g. \epoc32\release\&lt;..&gt;.
	 * <p>
	 * A variant target always builds into the same directory as a built-in platform.
	 * Only the filename varies.  For example, CONFIG is a virtual variant of ARMV5 and VARIANT
	 * is a variant of CONFIG.  All three have ARMV5 as their release platform.
	 * @param platformName
	 * @return name of release platform
	 */
	String getReleasePlatform(String platformName);
	
	/**
	 * Get the virtual variant platforms from the platforms detected. This returns only platforms explicitly quoting VIRTUALVARIANT, not
	 * platforms customizing that variant.  This does not include entries returned by '{@link #getVariantPlatforms()}'
	 * @return array, never null
	 */
	IBSFPlatform[] getVirtualVariantPlatforms();
	
}
