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
package com.nokia.carbide.cpp.internal.sdk.core.model;

import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesFactory;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SymbianSDKFactory;

public class SymbianMissingSDKFactory extends SymbianSDKFactory {
	
	public static ISymbianSDK createInstance(String id){
		DeviceType newDeviceEntry = DevicesFactory.eINSTANCE.createDeviceType();
		newDeviceEntry.setId(id);
		newDeviceEntry.setEpocroot(java.io.File.separator);	//$NON-NLS-1$
		newDeviceEntry.setName("BAD_EPOCROOT");
		newDeviceEntry.setDefault(DefaultType.NO_LITERAL);

		SymbianMissingSDK sdk = new SymbianMissingSDK(newDeviceEntry); // create SDK and set the attribs found in devices.xml
		// Set other essential paramaters not in devices.xml
		if (sdk.getOSVersion().getMajor() != 0){
		// use the version detected from the SDK creation
		} else {
			sdk.setOSVersion(new Version("0.0"));	//$NON-NLS-1$
		}
		sdk.setSDKVersion(new Version("0.0"));	//$NON-NLS-1$
		return sdk;
	}

}
