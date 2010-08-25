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

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesFactory;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianSDK;
/**
 * Factory to create new instances of SDK object. This adds new SDK to the devices.xml file
 * but does not add to the SDK map
 *
 */
public class SymbianSDKFactory {
	
	/**
	 * Create an new ISymbian SDK object
	 * @param id - The unique id. This is the devices.xml 'id' attribute and should be unique.
	 * @param epocRoot - Location of epoc32 folder (not including it)
	 * @param name - The com.vendor.sdk name. The 'name' attribute from devices.xml
	 * @param osVersion - The version identifier
	 * @param osBranch - The branch idenfitier (can be empty string if none)
	 * @param sdkVersion - The SDK version identifier
	 * @param isDefault - The 'default' attribute from devices.xml.
	 * @return An ISymbianSDK object with its data added to devices.xml
	 */
	public static ISymbianSDK createInstance(String id, 
											 String epocRoot, 
											 Version osVersion) {
		DeviceType newDeviceEntry = DevicesFactory.eINSTANCE.createDeviceType();
		newDeviceEntry.setId(id);
		newDeviceEntry.setEpocroot(epocRoot);
		newDeviceEntry.setDefault(DefaultType.NO_LITERAL);
		
		SymbianSDK sdk = new SymbianSDK(newDeviceEntry); // create SDK and set the attribs found in devices.xml
		// Set other essential parameters not in devices.xml
		if (sdk.getOSVersion().getMajor() != 0) {
			// use the version detected from the SDK creation
		} else {
			sdk.setOSVersion(osVersion);
		}
		
		return sdk;
	}
	
}
