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
	 * Refresh the configuration cache that contains Symbian SDK info such as 'id' and EPOCROOT
	 *
	 */
	public void updateCarbideSDKCache();
	
	/**
	 * Get the absolute path to the devices.xml file. 
	 * This first scans the windows registry under 'SOFTWARE\Symbian\EPOC SDKs\CommonPath'.
	 * If  CommonPath is not defined then the system drive spec is used with the folder location at:
	 * '\Program Files\Common Files\Symbian'.
	 * @return File object. Clients should check File.exists() to make sure the file exists on disk.
	 */
	public File getDevicesXMLFile();
		
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
	
}
