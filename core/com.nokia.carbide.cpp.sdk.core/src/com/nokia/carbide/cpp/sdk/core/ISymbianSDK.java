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

import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Version;

/**
 * This interface provides details on a single Symbian OS SDK.
 * 
 * Notes on return values for unknown/undefined items:
 *  - String value are empty ""
 *  - IPath and IFile objects are null
 *  - containers have zero size
 *  
 *  @noimplement This interface is not intended to be implemented by clients.
 */

public interface ISymbianSDK {

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
	
	// Unique Ids include the vendor
	public static final String SERIES60_SDK_NAME = "com.nokia." + SERIES60_FAMILY_ID;
	public static final String S60_SDK_NAME = "com.nokia." + S60_FAMILY_ID;
	public static final String S80_SDK_NAME = "com.nokia." + S80_FAMILY_ID;
	public static final String UIQ_SDK_NAME = "com.symbian." + UIQ_FAMILY_ID;
	public static final String TECHVIEW_SDK_NAME = "com.symbian." + TECHVIEW_FAMILY_ID;
	
	public static final String PUBLISHER_NOKIA = "Nokia, Inc.";
	public static final String PUBLISHER_UIQ = "UIQ Technology";
	public static final String PUBLISHER_SYMBIAN = "Symbian, Ltd.";
	
	/**
	 * Returns true if the SDK is configured properly, false otherwise.
	 *
	 * @return <code>true</code> if the SDK is configured properly, and
	 *   <code>false</code> otherwise
	 */
	boolean isValid();
	
	/**
	 * Returns a list of human readable error strings desribing why the
	 * SDK is not configured properly.
	 *
	 * @return a list of strings which may be empty.
	 * @see #isValid()
	 */
	List<String> validationErrors();

	/**
	 * Returns true if the SDK is enabled, false otherwise.
	 *
	 * @return <code>true</code> if the SDK is enabled, and
	 *   <code>false</code> otherwise
	 */
	boolean isEnabled();
	
	/**
	 * Marks the SDK as enabled or disabled
	 *
	 * @param enable whether to enable or disable the SDK
	 */
	void setEnabled(boolean enable);
	
	/**
	 * Returns the list of all platform macros for this SDK.
	 * <p>
	 * This is somewhat equivalent to calling "bldmake plat" on the command line
	 * These are the macros that can be used in MMP and INF files. They are only given by name (no value)
	 * </p>
	 * @param platform the platform name
	 * @return a list of macros, which may be empty.
	 */
	List<String> getPlatformMacros(String platform);
	
	
	/**
	 * Returns the list of all vendor specific C/C++ macros for this SDK.  The list of macros is defined in
	 * the variant configuration file inside \epoc32\tools\variant.cfg (or \epoc32\tools\spp_variant.cfg
	 * for platform variation SDKs).  The file itself contains an HRH file entry and may also contain macro definitions
	 * thereafter.
	 * <p>
	 * Macros follow the form of a #define statement, excluding the "#define", e.g.:<br>
	 * "FOO", "BAR=1", "_INCLUDE_=\"\\mydir\\prefix.hrh\\\"", "INCLUDE(x)=\epoc32\include\##x"
	 * </p>
	 *
	 * @return a list of macros which may be empty.
	 * @deprecated use #getProjectVariantHRHDefines() which returns a list of IDefine instead.
	 */
	List<String> getProjectVariantHRHMacros();
	
	
	/**
	 * Returns the list of all vendor specific C/C++ macros for this SDK.  The list of macros is defined in
	 * the variant configuration file inside \epoc32\tools\variant.cfg (or \epoc32\tools\spp_variant.cfg
	 * for platform variation SDKs).  The file itself contains an HRH file entry and may also contain macro definitions
	 * thereafter.
	 * @return a list of macros which may be empty.
	 * @deprecated use {@link ISymbianBuildContext#getVariantHRHDefines()} instead.  This was moved because the include paths
	 * in which to find any header files included from the main .hrh file may be build configuration specific.
	 */
	List<IDefine> getProjectVariantHRHDefines();
	
	/**
	 * Returns a list of the macros defined in the variant.cfg file. This is NOT the macros
	 * in the HRH file, but the actual maros written to the variant.cfg file.
	 * @return A String list of macros found as is, or an empty list if none.
	 */
	List<String> getVariantCFGMacros();
		
	/**
	 * Get a list of macros that are used to distinguish this SDK. Typically only
	 * S603rd edition SDKs contain this information and it's used to differentiate between
	 * varios S60 SDK releases. Other vendors, such as UIQ may put their macros in the variant HRH file.
	 * @return A list of macros just returned by name.
	 */
	public List<String> getVendorSDKMacros();
		
	/**
	 * Returns the list of all available platforms for this SDK.
	 *
	 * @return a list of platform names which may be empty.
	 */
	List<String> getAvailablePlatforms();
	
	/**
	 * Returns the filtered list of build configurations for this SDK.
	 * <p>
	 * Build configs to disply can be filtered by the user using a
	 * global preference page.
	 * </p>
	 *
	 * @return a list build configuration names which may be empty.
	 */
	List<ISymbianBuildContext> getFilteredBuildConfigurations();
	
	/**
	 * Returns the unfiltered list of build configurations for this SDK.
	 * <p>
	 * Build configs to disply can be filtered by the user using a
	 * global preference page.
	 * </p>
	 *
	 * @return a list build configuration names which may be empty.
	 */
	List<ISymbianBuildContext> getUnfilteredBuildConfigurations();
	
	/**
	 * Returns the unique id of this SDK. This is the devices.xml 'id' attribute.
	 *
	 * @return the id string of this sdk.
	 */
	String getUniqueId();
	
	/**
	 * Returns the display name of this SDK. This is the com.vendor.family identifier.
	 *
	 * @return the name of this sdk.
	 */
	String getName();  
	
	/**
	 * Returns the vendor name of this SDK. This is parsed from the 'name' attribute from devices.xml.
	 *
	 * @return the vendor name of this sdk.
	 */
	String getVendor();
	
	/**
	 * Returns the family name of this SDK. This is parsed from the 'vendor' attribute from devices.xml.
	 *
	 * @return the family name of this sdk.
	 */
	String getFamily();
	
	/**
	 * Returns the absolute path to the epoc32 directory of this SDK. This method is guaranteed to
	 * return the path with a trailing File.separator.
	 *
	 * @return the absolute path to the epoc32 directory.
	 */
	String getEPOCROOT();
	
	/**
	 * Returns whether or not this is the default SDK in the devices.xml file.
	 *
	 * @return <code>true</code> if the SDK is the default, and
	 *   <code>false</code> otherwise
	 */
	boolean isDefaultSDK();
	
	/**
	 * Returns the OS version string of this SDK.
	 *
	 * @return the OS Version object. If the version cannot be determined it will be "0.0".
	 */
	Version getOSVersion();
	
	/**
	 * Returns the SDK version string of this SDK.
	 *
	 * @return the SDK Version object. If the version cannot be determined it will be "0.0".
	 */
	Version getSDKVersion();
	
	/**
	 * Returns the File object for the prefix file for this SDK.
	 *
	 * @return the File object for the prefix file, or
	 * <code>null</code> if there isn't one for this SDK.
	 */
	File getPrefixFile();
	
	/**
	 * Returns an IPath for the epoc32\tools directory of this SDK.
	 *
	 * @return an IPath for the epoc32\tools directory, or <code>null</code>.
	 */
	IPath getToolsPath();
	
	/**
	 * Returns an IPath for the epoc32\release directory of this SDK.
	 *
	 * @return an IPath for the epoc32\release directory, or <code>null</code>.
	 */
	IPath getReleaseRoot();
	
	/**
	 * Returns an IPath for the epoc32\include directory of this SDK.
	 *
	 * @return an IPath for the epoc32\include directory, or <code>null</code>.
	 */
	IPath getIncludePath();
	
	/**
	 * Get the human readable description of the SDK.
	 * @return The description string, empty string for none.
	 */
	String getSDKDescription();
	
	/**
	 * Get the creation date of the manifest.xml
	 * @return a Date object
	 */
	Date getCreationDate();
	
	/**
	 * Get the licese file location in the SDK. Comes from manifest.xml.
	 * @return File, full path to the license file.
	 */
	File getLicenseFile();
	
	/**
	 * Get the branch qualifier for the OS. Typically "a" or "b" to denote the Beech and Cedar code branchs respectively.
	 * This is typically used to denote EKA1 from EKA2.
	 * @return the branch, or empty string if none.
	 */
	String getSDKOSBranch();
	
	/**
	 * Get the HTTP location for the SDK publisher. Comes from manifest.xml.
	 * @return A (hopefully) valid URL.
	 */
	URL getPublisherURL();
	
	/**
	 * Get the publisher name present in manifest.xml
	 * @return The name entry or empty string if not present.
	 */
	String getPublisherName();
	
	/**
	 * Get a list of supported targettypes listed by this SDK. This routine parses the 
	 * \epoc32\tools\trgttype.pm file to build it's list.
	 * @return A list of targettype names that can be used in an MMP file
	 */
	List<String> getSupportedTargetTypes();
	
	/**
	 * Get a list of macros specific to the given target type, e.g. "__EXE__" or "__DLL__"
	 * @param targettype
	 * @return list of macro strings, may be empty
	 */
	List<String> getTargetTypeMacros(String targettype);
	
	@Deprecated
	public boolean getRequiresRestart();
	void setLicenseFile(File licenseFile);
	void setPrefixFile(IPath prefixFile);
	void setOSVersion(Version osVer);
	void setSDKVersion(Version sdkVers);
	void setPublisherURL(URL pubURL);
	void setCreateDate(Date createDate);
	void setOSSDKBranch(String branch);
	void setIsDefaultSDK(boolean isDefault);
	void setSDKDescription(String descr);
	void setPublisherName(String pubName);
	
	void setUniqueID(String id);
	void setEPOCROOT(String epocRoot);
	void setName(String name);
	
	/** Tell if the SDK is EKA1 */
	boolean isEKA1();
	
	/** Tell if the SDK is EKA2 */
	boolean isEKA2();
	
	/** Tell if the SDK is Series60 */
	boolean isS60();
	
	/**
	 * Get whether or not this SDK has WINSCW UREL binary support
	 * @return true if the SDK has the WINSCW/UREL folder with epoc.exe components.
	 */
	boolean supportsWINSCW_UREL();
	
	/**
	 * Set the flag wheter or not this SDK has WINSCW UREL binary support
	 * @param isSuported
	 * @return
	 */
	void setSupportsWINSCW_UREL(boolean isSupported);
	
	/**
	 * Scans/Rescans the SDK for info such as prefix file, variant macros, manifest.xml, etc.
	 */
	void scanSDK();
	
	/**
	 * Get the BSF catalog for the SDK.
	 */
	IBSFCatalog getBSFCatalog();
	
	/**
	 * Get the Symbian Binary Variation (SBV) catalog for the SDK.
	 * @since 2.0
	 */
	ISBVCatalog getSBVCatalog();
	
	/**
	 * Tells whether or not the plug-in installer has sniffed this SDK for eclipse plug-ins to install.
	 * @return true if the SDK was scanned.
	 * @since 2.0
	 */
	boolean isPreviouslyScanned();
	
	/**
	 * Set flag to tell whether or not the SDK was scanned for eclipse plugins to install.
	 * @param wasScanned was the SDK scanned for plugins?
	 * @since 2.0
	 */
	void setPreviouslyScanned(boolean wasScanned);
	
}
