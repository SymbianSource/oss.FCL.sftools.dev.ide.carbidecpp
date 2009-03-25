/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Language codes, as defined in Symbian (eikon.rh, aiftool.rh)
 * 
 */
public class Language {
    public static final int LANG_English = 1;
    public static final int LANG_French = 2;
    public static final int LANG_German = 3;
    public static final int LANG_Spanish = 4;
    public static final int LANG_Italian = 5;
    public static final int LANG_Swedish = 6;
    public static final int LANG_Danish = 7;
    public static final int LANG_Norwegian = 8;
    public static final int LANG_Finnish = 9;
    public static final int LANG_American = 10;
    public static final int LANG_SwissFrench = 11;
    public static final int LANG_SwissGerman = 12;
    public static final int LANG_Portuguese = 13;
    public static final int LANG_Turkish = 14;
    public static final int LANG_Icelandic = 15;
    public static final int LANG_Russian = 16;
    public static final int LANG_Hungarian = 17;
    public static final int LANG_Dutch = 18;
    public static final int LANG_BelgianFlemish = 19;
    public static final int LANG_Australian = 20;
    public static final int LANG_BelgianFrench = 21;
    public static final int LANG_Austrian = 22;
    public static final int LANG_NewZealand = 23;
    public static final int LANG_InternationalFrench = 24;
    public static final int LANG_Czech = 25;
    public static final int LANG_Slovak = 26;
    public static final int LANG_Polish = 27;
    public static final int LANG_Slovenian = 28;
    public static final int LANG_TaiwanChinese = 29;
    public static final int LANG_HongKongChinese = 30;
    public static final int LANG_PrcChinese = 31;
    public static final int LANG_Japanese = 32;
    public static final int LANG_Thai = 33;
    public static final int LANG_Afrikaans = 34;
    public static final int LANG_Albanian = 35;
    public static final int LANG_Amharic = 36;
    public static final int LANG_Arabic = 37;
    public static final int LANG_Armenian = 38;
    public static final int LANG_Azerbaijani = 39;
    public static final int LANG_Belarussian = 40;
    public static final int LANG_Bengali = 41;
    public static final int LANG_Bulgarian = 42;
    public static final int LANG_Burmese = 43;
    public static final int LANG_Catalan = 44;
    public static final int LANG_Croatian = 45;
    public static final int LANG_CanadianEnglish = 46;
    public static final int LANG_InternationalEnglish = 47;
    public static final int LANG_SouthAfricanEnglish = 48;
    public static final int LANG_Estonian = 49;
    public static final int LANG_Farsi = 50;
    public static final int LANG_CanadianFrench = 51;
    public static final int LANG_ScotsGaelic = 52;
    public static final int LANG_Georgian = 53;
    public static final int LANG_Greek = 54;
    public static final int LANG_CyprusGreek = 55;
    public static final int LANG_Gujarati = 56;
    public static final int LANG_Hebrew = 57;
    public static final int LANG_Hindi = 58;
    public static final int LANG_Indonesian = 59;
    public static final int LANG_Irish = 60;
    public static final int LANG_SwissItalian = 61;
    public static final int LANG_Kannada = 62;
    public static final int LANG_Kazakh = 63;
    public static final int LANG_Khmer = 64;
    public static final int LANG_Korean = 65;
    public static final int LANG_Lao = 66;
    public static final int LANG_Latvian = 67;
    public static final int LANG_Lithuanian = 68;
    public static final int LANG_Macedonian = 69;
    public static final int LANG_Malay = 70;
    public static final int LANG_Malayalam = 71;
    public static final int LANG_Marathi = 72;
    public static final int LANG_Moldavian = 73;
    public static final int LANG_Mongolian = 74;
    public static final int LANG_NorwegianNynorsk = 75;
    public static final int LANG_BrazilianPortuguese = 76;
    public static final int LANG_Punjabi = 77;
    public static final int LANG_Romanian = 78;
    public static final int LANG_Serbian = 79;
    public static final int LANG_Sinhalese = 80;
    public static final int LANG_Somali = 81;
    public static final int LANG_InternationalSpanish = 82;
    public static final int LANG_LatinAmericanSpanish = 83;
    public static final int LANG_Swahili = 84;
    public static final int LANG_FinlandSwedish = 85;
    public static final int LANG_Tajik = 86;
    public static final int LANG_Tamil = 87;
    public static final int LANG_Telugu = 88;
    public static final int LANG_Tibetan = 89;
    public static final int LANG_Tigrinya = 90;
    public static final int LANG_CyprusTurkish = 91;
    public static final int LANG_Turkmen = 92;
    public static final int LANG_Ukrainian = 93;
    public static final int LANG_Urdu = 94;
    public static final int LANG_Uzbek = 95;
    public static final int LANG_Vietnamese = 96;
    public static final int LANG_Welsh = 97;
    public static final int LANG_Zulu = 98;
    public static final int LANG_Other = 99;
    
    private int languageCode;
    
    {
    	initMaps();
    }
    
    public Language(int languageCode) {
    	this.languageCode = languageCode;
    }

    /** 
     * Get the language 
     */
    public int getLanguageCode() {
    	return languageCode;
    }

	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Language) {
			Language other = (Language) obj;
			result = languageCode == other.languageCode;
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		return languageCode;
	}

	public String toString() {
		return (String) valueToIdentifier.get(new Integer(languageCode));
	}
	
	public static Language createFromString(String s) {
		Language result = null;
		Integer value = (Integer) identifierToValue.get(s);
		if (value != null) {
			result = new Language(value.intValue());
		}
		return result;
	}
	
	private static Map valueToIdentifier;
	private static Map identifierToValue;
	
	private static void initMaps() {
		
		valueToIdentifier = new HashMap();
		identifierToValue = new HashMap();
		Field[] fields = Language.class.getFields();
		if (fields != null) {
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				int m = f.getModifiers();
				if (Modifier.isPublic(m) && Modifier.isFinal(m) && 
					Modifier.isStatic(m) && f.getName().startsWith("LANG_")) {
					try {
						Integer value = new Integer(f.getInt(null));
						String identifier = f.getName();
						valueToIdentifier.put(value, identifier);
						identifierToValue.put(identifier, value);
					}
					catch (IllegalAccessException x) {
						// shouldn't happen since we filtered on public fields
					}
				}
			}
		}
	}
}
