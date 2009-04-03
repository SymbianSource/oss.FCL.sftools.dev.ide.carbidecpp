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


/**
 * Listener to check for modifications to the sdk list. Changes include adding, removing or rescanning for SDKs.
 * These events are typically trigged through user manipulation via the SDK Preferences page.
 * @see ISymbianSDK
 */
public interface ICarbideInstalledSDKChangeListener {
	
	/**
	 * Type of event occuring on the sdk list
	 * @see ICarbideInstalledSDKChangeListener#installedSdkChanged
	 */
	public enum SDKChangeEventType {
		eSDKAdded,
		eSDKRemoved,
		eSDKScanned
	}
	
	/**
	 * The type of change that occurred in the SDK list
	 * @param sdkEventType - SDK_CHANGE_ADDED, SDK_CHANGE_REMOVED, or SDK_CHANGE_RESCAN
	 */
	public void installedSdkChanged(SDKChangeEventType eventType);
	
}
