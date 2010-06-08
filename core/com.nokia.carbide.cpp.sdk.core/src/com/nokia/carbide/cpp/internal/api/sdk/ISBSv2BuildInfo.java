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

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

/**
 * Interface for SBSv2 specific build information.
 *
 */
public interface ISBSv2BuildInfo extends ISDKBuildInfo {

	/**
	 * Returns the list of all platform macros for a SDK.
	 * <p>
	 * This is somewhat equivalent to calling "bldmake plat" on the command line
	 * These are the macros that can be used in MMP and INF files. They are only given by name (no value)
	 * </p>
	 * @param sdk Symbian SDK
	 * @param platform the platform name
	 * @return a list of macros, which may be empty.
	 */
	List<String> getPlatformMacros(ISymbianSDK sdk, String platform);

	/**
	 * Get a list of macros specific to the given target type, e.g. "__EXE__" or "__DLL__"
	 * @param sdk Symbian SDK
	 * @param targettype
	 * @return list of macro strings, may be empty
	 */
	List<String> getTargetTypeMacros(ISymbianSDK sdk, String targettype);

	/**
	 * Returns the list of all available platforms for a SDK.
	 * @param sdk Symbian SDK
	 * @return a list of platform names which may be empty.
	 */
	List<String> getAvailablePlatforms(ISymbianSDK sdk);

	/**
	 * Returns an IPath for the epoc32\include directory of a SDK.
	 * @param sdk Symbian SDK
	 * @return an IPath for the epoc32\include directory, or <code>null</code>.
	 */
	IPath getIncludePath(ISymbianSDK sdk);

	/**
	 * Returns the File object for the prefix file for a SDK.
	 * @param sdk Symbian SDK
	 * @return the File object for the prefix file, or
	 * <code>null</code> if there isn't one for the SDK.
	 */
	File getPrefixFile(ISymbianSDK sdk);

	/**
	 * Get the BSF catalog for a SDK.
	 * @param sdk Symbian SDK
	 */
	IBSFCatalog getBSFCatalog(ISymbianSDK sdk);

	/**
	 * Get the Symbian Binary Variation (SBV) catalog for a SDK.
	 * @param sdk Symbian SDK
	 * @since 2.0
	 */
	ISBVCatalog getSBVCatalog(ISymbianSDK sdk);

	/**
	 * Tells whether or not the plug-in installer has sniffed this SDK for eclipse plug-ins to install.
	 * @param sdk Symbian SDK
	 * @return true if the SDK was scanned.
	 * @since 2.0
	 */
	boolean isPreviouslyScanned(ISymbianSDK sdk);

	void setPreviouslyScanned(ISymbianSDK sdk, boolean wasScanned);
	void setPrefixFile(ISymbianSDK sdk, IPath prefixFile);

}
