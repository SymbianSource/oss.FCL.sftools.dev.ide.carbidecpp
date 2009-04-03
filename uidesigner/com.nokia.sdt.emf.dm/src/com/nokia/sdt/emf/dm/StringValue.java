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
package com.nokia.sdt.emf.dm;

import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * Container for string values in the data model. Encapsulates
 * the four types of string values supported:
 * <ul>
 * <li>a literal string</li>
 * <li>a localized string</li>
 * <li>a non-localized string, e.g. a string defined via a macro, but not localized</li>
 * <li>a component reference, e.g the value is the name of another component in the same model</li>
 * </ul><br>
 * StringValues are immutable once created
 */
public class StringValue {
	
		// legal values for the 'type' field
			// value is a string literal
	public static final int LITERAL = 0;
			// value is a key into the localized string bundle
	public static final int LOCALIZED = 1;
			// value is a key into the non-localized macro string map
	public static final int MACRO = 2;
			// value is a component instance reference
	public static final int REFERENCE = 3;
	
	private int type;
	
		// literal, or key. May not be null
	private String value;

	public StringValue(int type, String value) {
		Check.checkArg(type >= LITERAL && type <= REFERENCE);
		Check.checkArg(value);
		this.type = type;
		this.value = value;
	}
	
	public int getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isLiteral() {
		return type == LITERAL;
	}
	
	public boolean isLocalized() {
		return type == LOCALIZED;
	}
	
	public boolean isMacro() {
		return type == MACRO;
	}
	
	public boolean isReference() {
		return type == REFERENCE;
	}
	
	public boolean isKey() {
		return type == LOCALIZED || type == MACRO;
	}
	
	public boolean equals(Object o) {
		if (o == this) return true;
		boolean result = false;
		if (o instanceof StringValue) {
			StringValue other = (StringValue) o;
			result = type == other.type && value.equals(other.value);
		}
		return result;
	}
}
