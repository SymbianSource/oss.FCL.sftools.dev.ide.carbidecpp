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
/* START_USECASES: CU5 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.vieweditor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This is an static class that manages all the localizable strings.
 *
 */
public class Messages {
	private static final String BUNDLE_NAME = "com.nokia.carbide.cpp.uiq.ui.vieweditor.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * Class constructor.
	 */
	private Messages() {
	}

	/**
	 * This method search the localizable string in  the messages.properties file.
	 * @param key - The key for the string.
	 * @return - If the key is found it returns the value for the key, otherwise return
	 * 			 the key surrounded by ! !. 
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
