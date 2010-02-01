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

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 *  This file was originally autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 *  but modified to work with Carbide.
 */
public class UP_DATA_BUFFER extends Structure {
	/// Size of the structure
	public int dwSize;
	/// Request type
	public byte bRequest;
	/// Data value
	public short wValue;
	/// Parameter for the request
	public short wIndex;
	/// Length of the pbData in bytes
	public short wDataLength;
	/**
	 * Pointer to BYTE data buffer<br>
	 * C type : BYTE*
	 */
	public Pointer pbData;
	public UP_DATA_BUFFER() {
		super();
	}
	/**
	 * @param dwSize Size of the structure<br>
	 * @param bRequest Request type<br>
	 * @param wValue Data value<br>
	 * @param wIndex Parameter for the request<br>
	 * @param wDataLength Length of the pbData in bytes<br>
	 * @param pbData Pointer to BYTE data buffer<br>
	 * C type : BYTE*
	 */
	public UP_DATA_BUFFER(int dwSize, byte bRequest, short wValue, short wIndex, short wDataLength, Pointer pbData) {
		super();
		this.dwSize = dwSize;
		this.bRequest = bRequest;
		this.wValue = wValue;
		this.wIndex = wIndex;
		this.wDataLength = wDataLength;
		this.pbData = pbData;
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected UP_DATA_BUFFER newInstance() { return new UP_DATA_BUFFER(); }
	public static UP_DATA_BUFFER[] newArray(int arrayLength) {
		return null;
//		return com.ochafik.lang.jnaerator.runtime.Structure.newArray(UP_DATA_BUFFER.class, arrayLength);
	}
	public static class ByReference extends UP_DATA_BUFFER implements com.sun.jna.Structure.ByReference {}
	public static class ByValue extends UP_DATA_BUFFER implements com.sun.jna.Structure.ByValue {}

}
