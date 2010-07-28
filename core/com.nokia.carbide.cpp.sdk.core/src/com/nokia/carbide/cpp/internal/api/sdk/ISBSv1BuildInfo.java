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

import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;

/**
 * Interface for SBSv1 specific build information.
 *
 */
public interface ISBSv1BuildInfo extends ISDKBuildInfo {

	void clearPlatformMacros();

	/**
	 * Returns the list of all available platforms for a SDK.
	 * @return a list of platform names which may be empty.
	 */
	List<String> getAvailablePlatforms();

	/**
	 * Get the BSF catalog for a SDK.
	 */
	IBSFCatalog getBSFCatalog();

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
	 * Get the Symbian Binary Variation (SBV) catalog for a SDK.
	 */
	ISBVCatalog getSBVCatalog();

	/**
	 * Get a list of macros specific to the given target type, e.g. "__EXE__" or "__DLL__"
	 * @param targettype
	 * @return list of macro strings, may be empty
	 */
	List<String> getTargetTypeMacros(String targettype);

	/**
	 * Get a list of macros that are used to distinguish a SDK. Typically only
	 * S603rd edition SDKs contain this information and it's used to differentiate between
	 * varios S60 SDK releases. Other vendors, such as UIQ may put their macros in the variant HRH file.
	 * @return A list of macros just returned by name.
	 */
	public List<String> getVendorSDKMacros();

	/**
	 * Tells whether or not the plug-in installer has sniffed this SDK for eclipse plug-ins to install.
	 * @return true if the SDK was scanned
	 */
	boolean isPreviouslyScanned();

	void setPreviouslyScanned(boolean wasScanned);

	/**
	 * Returns the list of all built in macros for this configuration. This is ABLD specific. SBSv2 gets these macros from the --query=config.
	 * <p>
	 * Macros will be just a name, e.g. "_DEBUG", "__SYMBIAN32__", etc..
	 * </p>
	 *
	 * @return a list of macros which may be empty.
	 */
	List<String> getBuiltinMacros(ISymbianBuildContext context);

}
