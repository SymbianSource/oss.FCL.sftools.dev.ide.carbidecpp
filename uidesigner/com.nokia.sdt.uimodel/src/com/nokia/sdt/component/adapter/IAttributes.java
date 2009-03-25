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
package com.nokia.sdt.component.adapter;

/**
 * Component adapter interface for loosely-typed attributes.
 * Attributes are defined as named strings. There are utility methods
 * for interpreting them as booleans or integers.
 */
public interface IAttributes extends IComponentAdapter {

	/**
	 * Get a named attribute. Searches attributes defined on the immediate
	 * component or its base components.
	 * @param key the attribute name
	 * @return the attribute value, or none if not defined. An attributed defined as 
	 * <attribute key="foo/> returns an empty string, not a null string.
	 */
	String getAttribute(String key);

	/**
	 * Indicates whether the specified attribute is defined
	 * @param key the attribute name
	 * @return true if defined
	 */
	boolean isAttributeDefined(String key);

	/**
	 * Get a named attribute as an integer. The attribute text
	 * is parsed as a base 10 integer. If the attribute is missing 
	 * the default value is returned. If the value is defined but not a
	 * valid integer the default value is returned and an error is logged.
	 * @param key the attribute name
	 * @param defaultValue value used when the attribute is undefined or not a valid integer
	 */
	int getIntegerAttribute(String key, int defaultValue);

	/**
	 * Get a named attribute as a boolean. If the attribute is missing 
	 * the default value is returned. The implementation is not required
	 * to be localized. It may use Boolean.valueOf, which only recognizes "true"
	 * and treats any other value as false.
	 * @param key the attribute name
	 * @param defaultValue value used when the attribute is undefined or not a valid integer
	 */
	boolean getBooleanAttribute(String key, boolean defaultValue);

}
