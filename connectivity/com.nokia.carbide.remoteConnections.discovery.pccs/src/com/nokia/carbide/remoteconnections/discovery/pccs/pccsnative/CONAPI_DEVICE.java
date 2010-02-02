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

public class CONAPI_DEVICE extends Structure {
	/// C type : WCHAR*
	public com.sun.jna.ptr.ShortByReference pstrSerialNumber;
	/// C type : WCHAR*
	public com.sun.jna.ptr.ShortByReference pstrFriendlyName;
	/// C type : WCHAR*
	public com.sun.jna.ptr.ShortByReference pstrModel;
	/// C type : WCHAR*
	public com.sun.jna.ptr.ShortByReference pstrManufacturer;
	public int dwNumberOfItems;
	/// C type : CONAPI_CONNECTION_INFO*
	public CONAPI_CONNECTION_INFO.ByReference pItems;
	public CONAPI_DEVICE() {
		super();
	}
	/**
	 * @param pstrSerialNumber C type : WCHAR*<br>
	 * @param pstrFriendlyName C type : WCHAR*<br>
	 * @param pstrModel C type : WCHAR*<br>
	 * @param pstrManufacturer C type : WCHAR*<br>
	 * @param pItems C type : CONAPI_CONNECTION_INFO*
	 */
	public CONAPI_DEVICE(com.sun.jna.ptr.ShortByReference pstrSerialNumber, com.sun.jna.ptr.ShortByReference pstrFriendlyName, com.sun.jna.ptr.ShortByReference pstrModel, com.sun.jna.ptr.ShortByReference pstrManufacturer, int dwNumberOfItems, CONAPI_CONNECTION_INFO.ByReference pItems) {
		super();
		this.pstrSerialNumber = pstrSerialNumber;
		this.pstrFriendlyName = pstrFriendlyName;
		this.pstrModel = pstrModel;
		this.pstrManufacturer = pstrManufacturer;
		this.dwNumberOfItems = dwNumberOfItems;
		this.pItems = pItems;
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected CONAPI_DEVICE newInstance() { return new CONAPI_DEVICE(); }
	public static CONAPI_DEVICE[] newArray(int arrayLength) {
		return null;
		
//		return com.ochafik.lang.jnaerator.runtime.Structure.newArray(CONAPI_DEVICE.class, arrayLength);
	}
	public static class ByReference extends CONAPI_DEVICE implements com.sun.jna.Structure.ByReference {}
	public static class ByValue extends CONAPI_DEVICE implements com.sun.jna.Structure.ByValue {}

}
