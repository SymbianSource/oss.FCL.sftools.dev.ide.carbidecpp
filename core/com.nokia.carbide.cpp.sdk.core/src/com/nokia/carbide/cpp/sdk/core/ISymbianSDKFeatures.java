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
*/

package com.nokia.carbide.cpp.sdk.core;

/**
 * A collection of Symbian SDK feature IDs.
 * @since 3.0
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ISymbianSDKFeatures {

	public static final String IS_QT_INSTALLED = "isQtInstalled";
	public static final String IS_AVKON_SUPPORTED = "isAvkonSupported";
	public static final String IS_WINSCW_UREL_SUPPORTED = "isWINSCWURELSupported";
	public static final String IS_WINSCW_UDEB_SUPPORTED = "isWINSCWUDEBSupported";
	public static final String IS_EKA1 = "isEKA1";
	public static final String IS_EKA2 = "isEKA2";
	public static final String IS_FROM_DEVICES_XML = "isFromDevicesXml";

}
