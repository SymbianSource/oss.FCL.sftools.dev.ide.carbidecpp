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

package com.nokia.sdt.component.symbian.test.srcmapping;

import java.io.UnsupportedEncodingException;

/**
 * Encapsulate strings that the compiler can mess up when configured incorrectly
 * 
 *
 */
public class UTF8Strings {
	//public static String RUSSIAN_GOODBYE = UTF8Strings.RUSSIAN_GOODBYE; 
	//public static String RUSSIAN_HELLO = UTF8Strings.RUSSIAN_HELLO; 
	
	public static String RUSSIAN_GOODBYE;
	static
	{
		try {
			RUSSIAN_GOODBYE = new String(new byte[] { (byte) 0xd0, (byte) 0x94,
					(byte) 0xd0, (byte) 0xbe, 0x20, (byte) 0xd1, (byte) 0x81,
					(byte) 0xd0, (byte) 0xb2, (byte) 0xd0, (byte) 0xb8,
					(byte) 0xd0, (byte) 0xb4, (byte) 0xd0, (byte) 0xb0,
					(byte) 0xd0, (byte) 0xbd, (byte) 0xd0, (byte) 0xb8,
					(byte) 0xd1, (byte) 0x8f, }, "UTF-8"); // До свидания
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String RUSSIAN_HELLO;
	static
	{
		try {
			RUSSIAN_HELLO = new String(new byte[] { (byte) 0xd0, (byte) 0x97,
					(byte) 0xd0, (byte) 0xb4, (byte) 0xd1, (byte) 0x80,
					(byte) 0xd0, (byte) 0xb0, (byte) 0xd0, (byte) 0xb2,
					(byte) 0xd1, (byte) 0x81, (byte) 0xd1, (byte) 0x82,
					(byte) 0xd0, (byte) 0xb2, (byte) 0xd1, (byte) 0x83,
					(byte) 0xd0, (byte) 0xbb, (byte) 0xd1, (byte) 0x82,
					(byte) 0xd0, (byte) 0xb5, }, "UTF-8"); // Здравствулте
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}


	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//writeCharString(RUSSIAN_GOODBYE);
		//writeCharString(RUSSIAN_HELLO);
		
		for (String arg : args) {
			writeCharString(arg);
		}
		
	}

	/**
	 * @param arg
	 * @throws UnsupportedEncodingException 
	 */
	private static void writeCharString(String arg) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		builder.append("public static String STR;\n");
		builder.append("static {\ntry {\n" + "STR = new String(new byte[] {");
		byte[] bytes = arg.getBytes("UTF-8");
		for (int i = 0; i < bytes.length; i++) {
			if (i % 16 == 0)
				builder.append('\n');
			byte b = bytes[i];
			if (b < 0)
				builder.append("(byte) 0x" + Integer.toHexString(b & 0xff) + ", ");
			else
				builder.append("0x" + Integer.toHexString(b) + ", ");
		}
		
		builder.append("\n}, \"UTF-8\"); // " + arg + "\n");
		builder.append("} catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }\n");
		System.out.println(builder.toString());
	}
}
