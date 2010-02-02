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

import com.sun.jna.Structure;

public class CONAPI_CONNECTION_INFO extends Structure {
	public int dwDeviceID;
	/// See definitions for media types in PCCSTypeDefinitions.h
	public int dwMedia;
	/// C type : WCHAR*
	public com.sun.jna.ptr.ShortByReference pstrDeviceName;
	/// C type : WCHAR*
	public com.sun.jna.ptr.ShortByReference pstrAddress;
	/// See definitions for State values
	public int dwState;
	public CONAPI_CONNECTION_INFO() {
		super();
	}

	/**
	 * @param dwMedia See definitions for media types in PCCSTypeDefinitions.h<br>
	 * @param pstrDeviceName C type : WCHAR*<br>
	 * @param pstrAddress C type : WCHAR*<br>
	 * @param dwState See definitions for State values
	 */
	public CONAPI_CONNECTION_INFO(int dwDeviceID, int dwMedia, com.sun.jna.ptr.ShortByReference pstrDeviceName, com.sun.jna.ptr.ShortByReference pstrAddress, int dwState) {
		super();
		this.dwDeviceID = dwDeviceID;
		this.dwMedia = dwMedia;
		this.pstrDeviceName = pstrDeviceName;
		this.pstrAddress = pstrAddress;
		this.dwState = dwState;
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected CONAPI_CONNECTION_INFO newInstance() { return new CONAPI_CONNECTION_INFO(); }
	public static CONAPI_CONNECTION_INFO[] newArray(int arrayLength) {
		return null;
//		return com.ochafik.lang.jnaerator.runtime.Structure.newArray(CONAPI_CONNECTION_INFO.class, arrayLength);
	}
	public static class ByReference extends CONAPI_CONNECTION_INFO implements com.sun.jna.Structure.ByReference {}
	public static class ByValue extends CONAPI_CONNECTION_INFO implements com.sun.jna.Structure.ByValue {}

}
