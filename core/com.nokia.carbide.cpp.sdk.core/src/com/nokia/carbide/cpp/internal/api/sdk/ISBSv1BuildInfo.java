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

/**
 * Interface for SBSv1 specific build information.
 *
 */
public interface ISBSv1BuildInfo extends ISDKBuildInfo {

	// Family Ids don't include the vendor
	public static final String SERIES60_FAMILY_ID = "series60"; //deprecated
	public static final String S60_FAMILY_ID = "s60";
	public static final String S80_FAMILY_ID = "Series80";
	public static final String UIQ_FAMILY_ID = "UIQ";
	public static final String TECHVIEW_FAMILY_ID = "TechView";
	public static final String SYMBIAN_FOUNDATION_FAMILY_ID = "symbian"; // Symbian Foundation, starting with Symbian^3

	// Unique Ids include the vendor
	public static final String SERIES60_SDK_NAME = "com.nokia." + SERIES60_FAMILY_ID;
	public static final String S60_SDK_NAME = "com.nokia." + S60_FAMILY_ID;
	public static final String NOKIA_SF_SDK_NAME = "com.nokia." + SYMBIAN_FOUNDATION_FAMILY_ID; // Nokia+Symbian Foundation SDK
	public static final String S80_SDK_NAME = "com.nokia." + S80_FAMILY_ID;
	public static final String UIQ_SDK_NAME = "com.symbian." + UIQ_FAMILY_ID;
	public static final String TECHVIEW_SDK_NAME = "com.symbian." + TECHVIEW_FAMILY_ID;

	public static final String PUBLISHER_NOKIA = "Nokia, Inc.";
	public static final String PUBLISHER_UIQ = "UIQ Technology";
	public static final String PUBLISHER_SYMBIAN = "Symbian, Ltd.";

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

	/**
	 * Tell if the SDK is Series60.
	 * @return true if the SDK is Series60
	 */
	boolean isS60();

	void setPreviouslyScanned(boolean wasScanned);

}
