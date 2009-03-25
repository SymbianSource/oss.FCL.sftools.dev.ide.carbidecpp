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
package com.nokia.sdt.symbian;

/**
 * A utility class to provide the list of Symbian languages and
 * their localized names.
 *
 */
public class SymbianLanguage {
	
	public static final int DEFAULT_LANGUAGE_ID;
	
	private static final int MAX_LANGS = 99;
	private static final String LANG_RES_PREFIX = "SymbianLanguages."; //$NON-NLS-1$
	private static final String DEFAULT_SUFFIX = "DEFAULT"; //$NON-NLS-1$

	private static SymbianLanguage[] languages;
	
	public final int code;
	public final String displayName;
		
	private SymbianLanguage(int code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}
	
	public String toString() {
		return displayName;
	}
	
	public boolean equals(Object o) {
		// since ctor is private, only this class can construct instances, and
		// we only create one instance of each language.
		return (o == this);
	}
	
	static {
		DEFAULT_LANGUAGE_ID = Integer.parseInt(Messages.getString(LANG_RES_PREFIX + DEFAULT_SUFFIX));
		languages = new SymbianLanguage[MAX_LANGS];
		for (int i = 0; i < MAX_LANGS; i++) {
			String s = Messages.getString(LANG_RES_PREFIX + getLanguageIDFromIndex(i));
			SymbianLanguage l = new SymbianLanguage(getLanguageIDFromIndex(i), s);
			languages[i] = l;
		}
	};
	
	public static SymbianLanguage[] getLanguages() {
		return languages;
	}
	
	public static String[] getDisplayNames() {
		String[] result = new String[languages.length];
		for (int i = 0; i < languages.length; i++) {
			result[i] = languages[i].displayName;
		}
		return result;
	}
	
	public static SymbianLanguage getFromLanguageID(int languageID) {
		SymbianLanguage result = null;
		int index = getIndexFromLanguageID(languageID);
		if (index >= 0 && index < languages.length) {
			result = languages[index];
		}
		return result;
	}
	
	/**
	 * Return the language ID for an index into the full
	 * langagues array
	 */
	public static int getLanguageIDFromIndex(int index) {
		return index + 1;
	}
	
	public static int getIndexFromLanguageID(int languageID) {
		return languageID - 1;
	}
}
