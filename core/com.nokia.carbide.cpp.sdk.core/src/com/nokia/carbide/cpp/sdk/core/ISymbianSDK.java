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
	 * Value given for an SDK that is created when a build configuration
	 * references an non-existent SDK.
	 * @since 3.0
	 */
	public static String BAD_EPOCROOT = "/BAD_SDK_ROOT";
	
	/**
	 * Returns build info for a particular builder.
	 * @param builderId id string of a builder (ISymbianBuilderID constant)
	 * @return ISDKBuildInfo - may be null if the builderId is deprecated.
	 * @since 3.0
	 * @see {@link ISymbianBuilderID}
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
	 * @return the OS Version object. If the version cannot be determined it will be "9.5".
	 * @deprecated - use ISymbianSDK{@link #getSupportedFeatures()} to figure out the properties of an SDK
	 */
	Version getOSVersion();

//	/**
//	 * Returns the prefix file for a particular builder.
//	 * @param builderId id string of a builder
//	 * @return the File object for the prefix file, or
//	 * <code>null</code> if there isn't one for the SDK.
//	 */
//	File getPrefixFile(String builderId);

	/**
	 * Returns an IPath for the epoc32\release directory of a SDK.
	 * @return an IPath for the epoc32\release directory, or <code>null</code>.
	 */
	IPath getReleaseRoot();

	/**
	 * Returns a set of features supported by the SDK. 
	 * Feature IDs are defined in ISymbianSDKFeatures.
	 * @return set of features
	 * @since 3.0
	 * @see {@link ISymbianSDKFeatures}
	 */
	@SuppressWarnings("rawtypes")
	Set getSupportedFeatures();
		
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
