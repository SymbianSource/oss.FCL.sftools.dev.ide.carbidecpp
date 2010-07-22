/*
* Copyright (c) 2009-2010 Nokia Corporation and/or its subsidiary(-ies). 
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

import java.io.File;
import java.util.List;
import java.util.Set;

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

	/**
	 * Returns build info for a particular builder.
	 * @param builderId id string of a builder
	 * @return build info
	 */
	ISDKBuildInfo getBuildInfo(String builderId);
	
	/**
	 * Returns the absolute path to the epoc32 directory of this SDK. This method is guaranteed to
	 * return the path with a trailing File.separator.
	 *
	 * @return the absolute path to the epoc32 directory.
	 */
	String getEPOCROOT();
		
	/**
	 * Returns an IPath for the epoc32\include directory of a SDK.
	 * @return an IPath for the epoc32\include directory, or <code>null</code>.
	 */
	IPath getIncludePath();

	/**
	 * Returns the OS version string of this SDK.
	 *
	 * @return the OS Version object. If the version cannot be determined it will be "0.0".
	 * @deprecated - use ISymbianSDK{@link #getSupportedFeatures()} to figure out the properties of an SDK
	 */
	Version getOSVersion();

	/**
	 * Returns the prefix file for a particular builder.
	 * @param builderId id string of a builder
	 * @return the File object for the prefix file, or
	 * <code>null</code> if there isn't one for the SDK.
	 */
	File getPrefixFile(String builderId);

	/**
	 * Returns an IPath for the epoc32\release directory of a SDK.
	 * @return an IPath for the epoc32\release directory, or <code>null</code>.
	 */
	IPath getReleaseRoot();

	/**
	 * Returns a set of features supported by the SDK. 
	 * Feature IDs are defined in ISymbianSDKFeatures.
	 * @return set of features
	 */
	@SuppressWarnings("rawtypes")
	Set getSupportedFeatures();
	
	/**
	 * Get a list of supported targettypes listed by this SDK. This routine parses the 
	 * \epoc32\tools\trgttype.pm file to build it's list.
	 * @return A list of targettype names that can be used in an MMP file
	 */
	List<String> getSupportedTargetTypes();
	
	/**
	 * Returns an IPath for the epoc32\tools directory of a SDK.
	 * @return an IPath for the epoc32\tools directory, or <code>null</code>.
	 */
	IPath getToolsPath();

	/**
	 * Returns the unique id of this SDK. This is the devices.xml 'id' attribute.
	 *
	 * @return the id string of this SDK.
	 */
	String getUniqueId();
	
	/**
	 * Returns a list of the macros defined in the variant.cfg file. This is NOT the macros
	 * in the HRH file, but the actual maros written to the variant.cfg file.
	 * @return A String list of macros found as is, or an empty list if none.
	 */
	List<String> getVariantCFGMacros();
	
	/**
	 * Returns true if the SDK is enabled, false otherwise.
	 *
	 * @return <code>true</code> if the SDK is enabled, and
	 *   <code>false</code> otherwise
	 */
	boolean isEnabled();
	
	/**
	 * Scans/Rescans the SDK for info such as prefix file, variant macros, manifest.xml, etc.
	 */
	void scanSDK();

}
