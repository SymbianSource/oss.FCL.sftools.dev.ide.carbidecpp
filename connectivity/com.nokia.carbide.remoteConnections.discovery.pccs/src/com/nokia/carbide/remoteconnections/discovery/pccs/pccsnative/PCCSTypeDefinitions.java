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

public class PCCSTypeDefinitions {
	// Type definition for API_VARIANT
	public static final int API_VARIANT_TYPE_BYTE = 0x00000001;
	public static final int API_VARIANT_TYPE_WORD = 0x00000002;
	public static final int API_VARIANT_TYPE_DWORD = 0x00000004;
	public static final int API_VARIANT_TYPE_INT64 = 0x00000008;
	public static final int API_VARIANT_TYPE_DOUBLE = 0x00000010;
	public static final int API_VARIANT_TYPE_LPBYTE = 0x00000100;
	public static final int API_VARIANT_TYPE_LPWORD = 0x00000200;
	public static final int API_VARIANT_TYPE_LPDWORD = 0x00000400;
	public static final int API_VARIANT_TYPE_LPVOID = 0x00000800;
	public static final int API_VARIANT_TYPE_LPAPIWCHAR = 0x00010000;
	public static final int API_VARIANT_TYPE_DATETIME = 0x01000000;
	
	// Values used in API notification registration methods
	public static final int API_REGISTER = 0x10;
	public static final int API_UNREGISTER = 0x20;

	// Media types used in APIs 
	public static final int API_MEDIA_ALL = 0x01;
	public static final int API_MEDIA_IRDA = 0x02;
	public static final int API_MEDIA_SERIAL = 0x04;
	public static final int API_MEDIA_BLUETOOTH = 0x08;
	public static final int API_MEDIA_USB = 0x10;
}
