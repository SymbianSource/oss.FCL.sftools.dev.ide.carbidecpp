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

import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;

// we could override various members to throw CoreException?

public class SymbianMissingSDK extends SymbianSDK {

	public SymbianMissingSDK(DeviceType device) {
		super(device);
	}

}
