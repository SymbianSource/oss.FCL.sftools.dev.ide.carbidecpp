/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.remoteconnections.tests.extensions;

import com.nokia.carbide.remoteconnections.interfaces.IExtensionFilter;
import com.nokia.cpp.internal.api.utils.core.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Filters all test extensions when not running in JUnit
 */
public class TestFilter implements IExtensionFilter {
	
	private static List<String> connectionTypeIds = new ArrayList<String>();
	private static List<String> serviceIds = new ArrayList<String>();
	private static List<Pair<String, String>> connectedServiceIdPairs = new ArrayList<Pair<String,String>>();
	
	public static boolean isTest = true;

	public static void reset() {
		connectionTypeIds.clear();
		serviceIds.clear();
		connectedServiceIdPairs.clear();
	}

	public static void addConnectionTypeId(String connectionTypeId) {
		connectionTypeIds.add(connectionTypeId);
	}
	
	public static void addServiceId(String serviceId) {
		serviceIds.add(serviceId);
	}
	
	public static void addConnectedServiceIdPair(String connectionTypeId, String serviceId) {
		connectedServiceIdPairs.add(new Pair<String, String>(connectionTypeId, serviceId));
	}
	
	public boolean acceptConnectionType(String connectionTypeId) {
		return rejectTestExtensions(connectionTypeId) && !connectionTypeIds.contains(connectionTypeId);
	}

	public boolean acceptService(String serviceId) {
		return rejectTestExtensions(serviceId) && !serviceIds.contains(serviceId);
	}

	public boolean acceptConnectedService(String connectionTypeId, String serviceId) {
		return !connectedServiceIdPairs.contains(new Pair<String, String>(connectionTypeId, serviceId));
	}

	private boolean rejectTestExtensions(String className) {
		if (!isTest) {
			if (className.startsWith("com.nokia.carbide.remoteconnections.tests.extensions"))
				return false;
		}
		return true;
	}

}
