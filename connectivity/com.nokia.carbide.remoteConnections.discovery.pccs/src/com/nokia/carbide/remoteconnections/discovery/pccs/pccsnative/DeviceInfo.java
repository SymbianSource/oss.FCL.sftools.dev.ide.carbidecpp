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
* Contributors:
*
* Description: 
*
*/
package com.nokia.carbide.remoteconnections.discovery.pccs.pccsnative;

import java.util.ArrayList;
import java.util.Collection;

public class DeviceInfo {
	public String serialNumber;
	public String friendlyName;
	public String model;
	public String mfr;
	public int numberOfConnections;
	public Collection<DeviceConnectionInfo> connections = new ArrayList<DeviceConnectionInfo>();
	
	public DeviceInfo() {
		// TODO Auto-generated constructor stub
	}

}
