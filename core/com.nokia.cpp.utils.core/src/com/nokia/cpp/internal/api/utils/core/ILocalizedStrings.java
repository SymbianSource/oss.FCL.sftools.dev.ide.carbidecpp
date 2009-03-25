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
package com.nokia.cpp.internal.api.utils.core;

import java.util.Locale;

	/**
	 * This interfaced is designed so unit tests don't need
	 * a LocalizedStrings instance
	 *
	 */
public interface ILocalizedStrings {

	/**
	 * @param key
	 * @return the string associated with key or "!key!" if not found
	 */
	String getString(String key);

	String getString(String key, Locale l);
	
	boolean hasString(String key);
	
	boolean hasStringForLocale(String key, Locale l);

	/**
	 * Treats strings beginning with a single % as a key.
	 * The % is stripped before lookup.
	 * The prefix %% is an escape for a single %
	 */
	String checkPercentKey(String s);

}
