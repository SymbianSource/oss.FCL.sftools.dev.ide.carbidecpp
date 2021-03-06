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

import com.nokia.carbide.remoteconnections.discovery.pccs.Messages;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

/**
 * This file was originally autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * but modified for use in Carbide
 */
public class GUID extends Structure {
	public NativeLong Data1;
	public short Data2;
	public short Data3;
	/// C type : unsigned char[8]
	public byte[] Data4 = new byte[(8)];
	public GUID() {
		super();
	}
	/// @param Data4 C type : unsigned char[8]
	public GUID(NativeLong Data1, short Data2, short Data3, byte Data4[]) {
		super();
		this.Data1 = Data1;
		this.Data2 = Data2;
		this.Data3 = Data3;
		if (Data4.length != this.Data4.length) 
			throw new java.lang.IllegalArgumentException(Messages.GUID_0);
		this.Data4 = Data4;
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected GUID newInstance() { return new GUID(); }
	public static GUID[] newArray(int arrayLength) {
		return null;
//		return com.ochafik.lang.jnaerator.runtime.Structure.newArray(GUID.class, arrayLength);
	}
	public static class ByReference extends GUID implements com.sun.jna.Structure.ByReference {}
	public static class ByValue extends GUID implements com.sun.jna.Structure.ByValue {}

}
