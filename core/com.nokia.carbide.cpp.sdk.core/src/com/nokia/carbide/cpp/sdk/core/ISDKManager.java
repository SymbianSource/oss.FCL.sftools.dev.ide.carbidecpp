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

import java.io.File;
import java.util.List;

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.api.sdk.BuildPlat;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianMacroStore;

/**
 * Interface to Symbian OS SDK's. Use this interface to get the list of all SDKs.
 * @see ISymianSDK
 * @noimplement
 */

public interface ISDKManager {
	
	/**
	 * Default windows directory where devices.xml resides (without the drive spec)
	 */
	public static final String DEFAULT_DEVICES_XML_DIR = "\\Program Files\\Common Files\\Symbian\\";
	
	/**
	 * The default drive spec where devices.xml resides when it cannot be determined through the windows registry.
	 * @since 2.0
	 */
	public static final String DEFAULT_DEVICES_DRIVE_SPEC = "C:";
	
	
	/**
	 * File name for devices.xml, the Symbian SDK file used to define all SDK locations on a machine.
	 */
	public static final String DEVICES_FILE_NAME = "devices.xml";

	/**
	 * Scan devices.xml and build the SDK list. Call this routine clears
	 * all current SDK entries.
	 *
	 */
	void scanSDKs();
	
	/**
	 * Add an new SDK to the devices.xml
	 * @param sdk
	 */
	void addSDK(ISymbianSDK sdk);
	
	/**
	 * Delete an SDK from devices.xml
	 * @param sdkId
	 * @return
	 */
	boolean removeSDK(String sdkId);
	
	/**
	 * Get a list of all loaded SDKs
	 * @return List of ISymbianSDK objects, which may be empty.
	 */
	List<ISymbianSDK> getSDKList();
	
	/**
	 * Get an SDK from it's unique 'id' attribute.
	 * @param sdkId
	 * @param scanIfNecessary build SDK list if not done already
	 * @return
	 */
	ISymbianSDK getSDK(String sdkId, boolean scanIfNecessary);
	
	/**
	 * Update an existing SDK to the devices.xml
	 * @param sdkId
	 */
	void updateSDK(ISymbianSDK sdkId);
	
	/**
	 * Retrieves the instance of the Symbian Macro Store
	 * @return
	 */
	SymbianMacroStore getSymbianMacroStore();
	
	/**
	 * Refresh the configuration cache that contains additional information that the devices.xml
	 * data does not have, such as OS and SDK version.
	 *
	 */
	public void updateCarbideSDKCache();
	
	/**
	 * Get whether or not the UI has enabled BSF scanning.
	 * @return true if BSF scanning is enabled.
	 */
	public boolean getBSFScannerEnabled();
	
	/**
	 * Set whether or not build platforms should be added for SDKs that contain .bsf extensions.
	 * @param enabled - When true, add BSF platforms.
	 */
	public void enableBSFScanner(boolean enabled);
	
	/**
	 * Sets the list of available build platforms
	 * @param platList, a list of BuildPlat objects
	 * @see BuildPlat
	 */
	public void setPlatformList(List<BuildPlat> platList);
	
	/**
	 * Get the list of all available built-in platforms
	 * @return A list of BuildPlat objects
	 * @see BuildPlat
	 */
	public List<BuildPlat> getPlatformList();
	
	/**
	 * Get the absolute path to the devices.xml file. 
	 * This first scans the windows registry under 'SOFTWARE\Symbian\EPOC SDKs\CommonPath'.
	 * If  CommonPath is not defined then the system drive spec is used with the folder location at:
	 * '\Program Files\Common Files\Symbian'.
	 * @return File object. Clients should check File.exists() to make sure the file exists on disk.
	 */
	public File getDevicesXMLFile();
	
	/**
	 * Getting installation path of CSL (GCCE) Arm Toolchain from the registry.
	 * The method also check that the all required tools exist.
	 * @return Path to tool binaries under installation path directory, 
	 *         successful. Otherwise throws an exception.
	 * @throws SDKEnvInfoFailureException 
	 */
	public String getCSLArmToolchainInstallPathAndCheckReqTools() throws SDKEnvInfoFailureException;
		
	/**
	 * Returns toolchain info for all detected RVCT tools.
	 * @return Array of toolchain information objects.
	 */
	public IRVCTToolChainInfo[] getInstalledRVCTTools();
	
	/**
	 * Add an ICarbideInstalledSDKChangeListener listener
	 * @param listener - An instance of ICarbideInstalledSDKChangeListener
	 */
	public void addInstalledSdkChangeListener(ICarbideInstalledSDKChangeListener listener);
	
	/**
	 * Remove an ICarbideInstalledSDKChangeListener listener
	 * @param listener - An instance of ICarbideInstalledSDKChangeListener
	 */
	public void removeInstalledSdkChangeListener(ICarbideInstalledSDKChangeListener listener);
	
	/**
	 * Checks to see if the devices.xml on disk contains the same current information
	 * as what we have in the sdk list. When not synchronized, when an SDK is add or removed
	 * outside of Carbide for example, this means an SDK rescan operation is needed.
	 * @return true if synchronized (no rescan needed), otherwise false (not up to date). Will also return true when devices.xml does not exist
	 * @see ISDKManager.fireDevicesXMLChanged
	 * @since 2.0
	 */ 
	public boolean checkDevicesXMLSynchronized();
	
	/**
	 * Get the version of SBSv2 installed on the PATH.
	 * @param boolean forceScan - forceScan even if version already determined.
	 * @return A Verion object of the installed SBSv2 version. Version will be 0.0.0 if not found.
	 * @since 2.3
	 */
	public Version getSBSv2Version(boolean forceScan);
	
	/**
	 * Retrieve the minimum supported version of SBSv2 for Carbide.c++
	 * @return Version
	 * @since 2.3
	 */
	public Version getMinimumSupportedSBSv2Version();
}
