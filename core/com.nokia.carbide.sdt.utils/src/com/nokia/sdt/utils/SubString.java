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
* Contributors:
*
* Description: 
*
*/
package com.nokia.sdt.utils;

import com.nokia.cpp.internal.api.utils.core.Check;


/**
 * This is a lightweight object that acts as a substring
 * over a larger string.  It can be used, for instance,
 * in regular expressions to limit the search scope
 * without copying character data around.
 *
 */
public class SubString implements CharSequence {

	private char[] chars;
	private int offset;
	private int length;

	/**
	 * 
	 */
	public SubString(char[] text, int offset, int length) {
		Check.checkArg(text != null && offset >= 0 && offset + length <= text.length);
		this.chars = text;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * 
	 */
	public SubString(CharSequence str, int offset, int length) {
		Check.checkArg(str != null && offset >= 0 && offset + length <= str.length());
		this.chars = new char[length];
		for (int i = 0; i < length; i++) {
			this.chars[i] = str.charAt(offset + i);
		}
		this.offset = 0;
		this.length = length;
	}

	/**
	 * 
	 */
	public SubString(String str, int offset, int length) {
		Check.checkArg(str != null && offset >= 0 && offset + length <= str.length());
		this.chars = str.toCharArray();
		this.offset = offset;
		this.length = length;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new String(chars, offset, length);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.CharSequence#length()
	 */
	public int length() {
		return length;
	}

	/* (non-Javadoc)
	 * @see java.lang.CharSequence#charAt(int)
	 */
	public char charAt(int index) {
		//Check.checkArg(index >= 0 && index < length); // paranoia
		return chars[index + offset];
	}

	/* (non-Javadoc)
	 * @see java.lang.CharSequence#subSequence(int, int)
	 */
	public CharSequence subSequence(int start, int end) {
		return new SubString(chars, offset + start, end - start);
	}
}
