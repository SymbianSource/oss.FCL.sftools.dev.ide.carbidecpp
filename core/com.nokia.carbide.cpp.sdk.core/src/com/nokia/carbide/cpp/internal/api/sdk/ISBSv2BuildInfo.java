/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.carbide.cpp.internal.api.sdk;

import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;

/**
 * Interface for SBSv2 specific build information.
 *
 */
public interface ISBSv2BuildInfo extends ISDKBuildInfo {

	void clearPlatformMacros();

	/**
	 * Returns the list of all platform macros for a SDK.
	 * <p>
	 * This is somewhat equivalent to calling "bldmake plat" on the command line
	 * These are the macros that can be used in MMP and INF files. They are only given by name (no value)
	 * </p>
	 * @param platform the platform name
	 * @return a list of macros, which may be empty.
	 */
	List<String> getPlatformMacros(String platform);

	/**
	 * Get the full path to the prefix file defined under \epoc32\tools\variant\variant.cfg
	 * @return A path object, or null if the variant.cfg does not exist. This routine does not check to see if the returned path exists.
	 */
	public IPath getPrefixFromVariantCfg();

	/**
	 * Get a list of macros specific to the given target type, e.g. "__EXE__" or "__DLL__"
	 * @param targettype
	 * @return list of macro strings, may be empty
	 */
	List<String> getTargetTypeMacros(String targettype);

	/**
	 * Tells whether or not the plug-in installer has sniffed this SDK for eclipse plug-ins to install.
	 * @return true if the SDK was scanned.
	 * @since 2.0
	 */
	boolean isPreviouslyScanned();

	void setPreviouslyScanned(boolean wasScanned);

}
