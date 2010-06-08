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
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.sdk.core.IBSFCatalog;
import com.nokia.carbide.cpp.sdk.core.ISBVCatalog;
import com.nokia.carbide.cpp.sdk.core.ISDKBuildInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

/**
 * Interface for SBSv1 specific build information.
 *
 */
public interface ISBSv1BuildInfo extends ISDKBuildInfo {

	/** Branch identifier to note Beech branch, OS 8.1a and prior */
	public static final String EKA1_A_BRANCH_IDENTIFIER = "a";
	/** Branch identifier to note S branch, OS 7.0x */
	public static final String EKA1_S_BRANCH_IDENTIFIER = "s";
	/** Branch identifier to note Cedar branch, OS 8.0b and later (not used for OS 9.x and greater) */
	public static final String EKA2_B_BRANCH_IDENTIFIER = "b";

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
	 * Get a list of macros that are used to distinguish a SDK. Typically only
	 * S603rd edition SDKs contain this information and it's used to differentiate between
	 * varios S60 SDK releases. Other vendors, such as UIQ may put their macros in the variant HRH file.
	 * @param sdk Symbian SDK
	 * @return A list of macros just returned by name.
	 */
	public List<String> getVendorSDKMacros(ISymbianSDK sdk);

	/**
	 * Returns the list of all available platforms for a SDK.
	 * @param sdk Symbian SDK
	 * @return a list of platform names which may be empty.
	 */
	List<String> getAvailablePlatforms(ISymbianSDK sdk);

	/**
	 * Returns the display name of a SDK. This is the com.vendor.family identifier.
	 * @param sdk Symbian SDK
	 * @return the name of a sdk.
	 */
	String getName(ISymbianSDK sdk);  

	/**
	 * Returns the vendor name of this SDK. This is parsed from the 'name' attribute from devices.xml.
	 * @param sdk Symbian SDK
	 * @return the vendor name of a sdk.
	 */
	String getVendor(ISymbianSDK sdk);

	/**
	 * Returns the family name of a SDK. This is parsed from the 'vendor' attribute from devices.xml.
	 * @param sdk Symbian SDK
	 * @return the family name of a sdk.
	 */
	String getFamily(ISymbianSDK sdk);

	/**
	 * Returns the SDK version string of a SDK.
	 * @param sdk Symbian SDK
	 * @return the SDK Version object. If the version cannot be determined it will be "0.0".
	 */
	Version getSDKVersion(ISymbianSDK sdk);

	/**
	 * Returns the File object for the prefix file for a SDK.
	 * @param sdk Symbian SDK
	 * @return the File object for the prefix file, or
	 * <code>null</code> if there isn't one for the SDK.
	 */
	File getPrefixFile(ISymbianSDK sdk);

	/**
	 * Returns an IPath for the epoc32\tools directory of a SDK.
	 * @param sdk Symbian SDK
	 * @return an IPath for the epoc32\tools directory, or <code>null</code>.
	 */
	IPath getToolsPath(ISymbianSDK sdk);

	/**
	 * Returns an IPath for the epoc32\release directory of a SDK.
	 * @param sdk Symbian SDK
	 * @return an IPath for the epoc32\release directory, or <code>null</code>.
	 */
	IPath getReleaseRoot(ISymbianSDK sdk);

	/**
	 * Returns an IPath for the epoc32\include directory of a SDK.
	 * @param sdk Symbian SDK
	 * @return an IPath for the epoc32\include directory, or <code>null</code>.
	 */
	IPath getIncludePath(ISymbianSDK sdk);

	/**
	 * Get the human readable description of a SDK.
	 * @param sdk Symbian SDK
	 * @return The description string, empty string for none.
	 */
	String getSDKDescription(ISymbianSDK sdk);

	/**
	 * Get the creation date of the manifest.xml
	 * @param sdk Symbian SDK
	 * @return a Date object
	 */
	Date getCreationDate(ISymbianSDK sdk);

	/**
	 * Get the licese file location in a SDK. Comes from manifest.xml.
	 * @param sdk Symbian SDK
	 * @return File, full path to the license file.
	 */
	File getLicenseFile(ISymbianSDK sdk);

	/**
	 * Get the branch qualifier for the OS. Typically "a" or "b" to denote the Beech and Cedar code branchs respectively.
	 * This is typically used to denote EKA1 from EKA2.
	 * @param sdk Symbian SDK
	 * @return the branch, or empty string if none.
	 */
	String getSDKOSBranch(ISymbianSDK sdk);

	/**
	 * Get the HTTP location for the SDK publisher. Comes from manifest.xml.
	 * @param sdk Symbian SDK
	 * @return A (hopefully) valid URL.
	 */
	URL getPublisherURL(ISymbianSDK sdk);

	/**
	 * Get the publisher name present in manifest.xml
	 * @param sdk Symbian SDK
	 * @return The name entry or empty string if not present.
	 */
	String getPublisherName(ISymbianSDK sdk);

	/**
	 * Get a list of macros specific to the given target type, e.g. "__EXE__" or "__DLL__"
	 * @param sdk Symbian SDK
	 * @param targettype
	 * @return list of macro strings, may be empty
	 */
	List<String> getTargetTypeMacros(ISymbianSDK sdk, String targettype);

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
	 * Returns whether or not this is the default SDK in the devices.xml file.
	 * @param sdk Symbian SDK
	 * @return <code>true</code> if the SDK is the default, and
	 *   <code>false</code> otherwise
	 */
	boolean isDefaultSDK(ISymbianSDK sdk);

	/**
	 * Tell if the SDK is Series60.
	 * @param sdk Symbian SDK
	 * @return true if the SDK is Series60
	 */
	boolean isS60(ISymbianSDK sdk);

	/**
	 * Tells whether or not the plug-in installer has sniffed this SDK for eclipse plug-ins to install.
	 * @param sdk Symbian SDK
	 * @return true if the SDK was scanned
	 * @since 2.0
	 */
	boolean isPreviouslyScanned(ISymbianSDK sdk);

	void setLicenseFile(ISymbianSDK sdk, File licenseFile);
	void setPrefixFile(ISymbianSDK sdk, IPath prefixFile);
	void setSDKVersion(ISymbianSDK sdk, Version sdkVers);
	void setPublisherURL(ISymbianSDK sdk, URL pubURL);
	void setCreateDate(ISymbianSDK sdk, Date createDate);
	void setOSSDKBranch(ISymbianSDK sdk, String branch);
	void setSDKDescription(ISymbianSDK sdk, String descr);
	void setPublisherName(ISymbianSDK sdk, String pubName);
	void setName(ISymbianSDK sdk, String name);
	void setIsDefaultSDK(ISymbianSDK sdk, boolean isDefault);
	void setPreviouslyScanned(ISymbianSDK sdk, boolean wasScanned);

}
