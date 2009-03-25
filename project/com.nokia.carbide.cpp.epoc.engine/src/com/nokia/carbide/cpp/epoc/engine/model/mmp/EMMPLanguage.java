/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.internal.cpp.epoc.engine.Messages;
import com.nokia.cpp.internal.api.utils.core.*;

/**
 * The known Symbian languages (from epoc32\include\e32lang.h of Symbian 9.5 2009 week 6 )
 * Enum naming should follow ELang... convention
 */
public enum EMMPLanguage {
    English(Messages.getString("EMMPLanguage.01"), 1), //$NON-NLS-1$
    French(Messages.getString("EMMPLanguage.02"), 2), //$NON-NLS-1$
    German(Messages.getString("EMMPLanguage.03"), 3), //$NON-NLS-1$
    Spanish(Messages.getString("EMMPLanguage.04"), 4), //$NON-NLS-1$
    Italian(Messages.getString("EMMPLanguage.05"), 5), //$NON-NLS-1$
    Swedish(Messages.getString("EMMPLanguage.06"), 6), //$NON-NLS-1$
    Danish(Messages.getString("EMMPLanguage.07"), 7), //$NON-NLS-1$
    Norwegian(Messages.getString("EMMPLanguage.08"), 8), //$NON-NLS-1$
    Finnish(Messages.getString("EMMPLanguage.09"), 9), //$NON-NLS-1$
    American(Messages.getString("EMMPLanguage.10"), 10), //$NON-NLS-1$
    SwissFrench(Messages.getString("EMMPLanguage.11"), 11), //$NON-NLS-1$
    SwissGerman(Messages.getString("EMMPLanguage.12"), 12), //$NON-NLS-1$
    Portuguese(Messages.getString("EMMPLanguage.13"), 13), //$NON-NLS-1$
    Turkish(Messages.getString("EMMPLanguage.14"), 14), //$NON-NLS-1$
    Icelandic(Messages.getString("EMMPLanguage.15"), 15), //$NON-NLS-1$
    Russian(Messages.getString("EMMPLanguage.16"), 16), //$NON-NLS-1$
    Hungarian(Messages.getString("EMMPLanguage.17"), 17), //$NON-NLS-1$
    Dutch(Messages.getString("EMMPLanguage.18"), 18), //$NON-NLS-1$
    BelgianFlemish(Messages.getString("EMMPLanguage.19"), 19), //$NON-NLS-1$
    Australian(Messages.getString("EMMPLanguage.20"), 20), //$NON-NLS-1$
    BelgianFrench(Messages.getString("EMMPLanguage.21"), 21), //$NON-NLS-1$
    Austrian(Messages.getString("EMMPLanguage.22"), 22), //$NON-NLS-1$
    NewZealand(Messages.getString("EMMPLanguage.23"), 23), //$NON-NLS-1$
    InternationalFrench(Messages.getString("EMMPLanguage.24"), 24), //$NON-NLS-1$
    Czech(Messages.getString("EMMPLanguage.25"), 25), //$NON-NLS-1$
    Slovak(Messages.getString("EMMPLanguage.26"), 26), //$NON-NLS-1$
    Polish(Messages.getString("EMMPLanguage.27"), 27), //$NON-NLS-1$
    Slovenian(Messages.getString("EMMPLanguage.28"), 28), //$NON-NLS-1$
    TaiwanChinese(Messages.getString("EMMPLanguage.29"), 29), //$NON-NLS-1$
    HongKongChinese(Messages.getString("EMMPLanguage.30"), 30), //$NON-NLS-1$
    PrcChinese(Messages.getString("EMMPLanguage.31"), 31), //$NON-NLS-1$
    Japanese(Messages.getString("EMMPLanguage.32"), 32), //$NON-NLS-1$
    Thai(Messages.getString("EMMPLanguage.33"), 33), //$NON-NLS-1$
    Afrikaans(Messages.getString("EMMPLanguage.34"), 34), //$NON-NLS-1$
    Albanian(Messages.getString("EMMPLanguage.35"), 35), //$NON-NLS-1$
    Amharic(Messages.getString("EMMPLanguage.36"), 36), //$NON-NLS-1$
    Arabic(Messages.getString("EMMPLanguage.37"), 37), //$NON-NLS-1$
    Armenian(Messages.getString("EMMPLanguage.38"), 38), //$NON-NLS-1$
    Tagalog(Messages.getString("EMMPLanguage.39"), 39), //$NON-NLS-1$
    Belarussian(Messages.getString("EMMPLanguage.40"), 40), //$NON-NLS-1$
    Bengali(Messages.getString("EMMPLanguage.41"), 41), //$NON-NLS-1$
    Bulgarian(Messages.getString("EMMPLanguage.42"), 42), //$NON-NLS-1$
    Burmese(Messages.getString("EMMPLanguage.43"), 43), //$NON-NLS-1$
    Catalan(Messages.getString("EMMPLanguage.44"), 44), //$NON-NLS-1$
    Croatian(Messages.getString("EMMPLanguage.45"), 45), //$NON-NLS-1$
    CanadianEnglish(Messages.getString("EMMPLanguage.46"), 46), //$NON-NLS-1$
    InternationalEnglish(Messages.getString("EMMPLanguage.47"), 47), //$NON-NLS-1$
    SouthAfricanEnglish(Messages.getString("EMMPLanguage.48"), 48), //$NON-NLS-1$
    Estonian(Messages.getString("EMMPLanguage.49"), 49), //$NON-NLS-1$
    Farsi(Messages.getString("EMMPLanguage.50"), 50), //$NON-NLS-1$
    CanadianFrench(Messages.getString("EMMPLanguage.51"), 51), //$NON-NLS-1$
    ScotsGaelic(Messages.getString("EMMPLanguage.52"), 52), //$NON-NLS-1$
    Georgian(Messages.getString("EMMPLanguage.53"), 53), //$NON-NLS-1$
    Greek(Messages.getString("EMMPLanguage.54"), 54), //$NON-NLS-1$
    CyprusGreek(Messages.getString("EMMPLanguage.55"), 55), //$NON-NLS-1$
    Gujarati(Messages.getString("EMMPLanguage.56"), 56), //$NON-NLS-1$
    Hebrew(Messages.getString("EMMPLanguage.57"), 57), //$NON-NLS-1$
    Hindi(Messages.getString("EMMPLanguage.58"), 58), //$NON-NLS-1$
    Indonesian(Messages.getString("EMMPLanguage.59"), 59), //$NON-NLS-1$
    Irish(Messages.getString("EMMPLanguage.60"), 60), //$NON-NLS-1$
    SwissItalian(Messages.getString("EMMPLanguage.61"), 61), //$NON-NLS-1$
    Kannada(Messages.getString("EMMPLanguage.62"), 62), //$NON-NLS-1$
    Kazakh(Messages.getString("EMMPLanguage.63"), 63), //$NON-NLS-1$
    Khmer(Messages.getString("EMMPLanguage.64"), 64), //$NON-NLS-1$
    Korean(Messages.getString("EMMPLanguage.65"), 65), //$NON-NLS-1$
    Lao(Messages.getString("EMMPLanguage.66"), 66), //$NON-NLS-1$
    Latvian(Messages.getString("EMMPLanguage.67"), 67), //$NON-NLS-1$
    Lithuanian(Messages.getString("EMMPLanguage.68"), 68), //$NON-NLS-1$
    Macedonian(Messages.getString("EMMPLanguage.69"), 69), //$NON-NLS-1$
    Malay(Messages.getString("EMMPLanguage.70"), 70), //$NON-NLS-1$
    Malayalam(Messages.getString("EMMPLanguage.71"), 71), //$NON-NLS-1$
    Marathi(Messages.getString("EMMPLanguage.72"), 72), //$NON-NLS-1$
    Moldavian(Messages.getString("EMMPLanguage.73"), 73), //$NON-NLS-1$
    Mongolian(Messages.getString("EMMPLanguage.74"), 74), //$NON-NLS-1$
    NorwegianNynorsk(Messages.getString("EMMPLanguage.75"), 75), //$NON-NLS-1$
    BrazilianPortuguese(Messages.getString("EMMPLanguage.76"), 76), //$NON-NLS-1$
    Punjabi(Messages.getString("EMMPLanguage.77"), 77), //$NON-NLS-1$
    Romanian(Messages.getString("EMMPLanguage.78"), 78), //$NON-NLS-1$
    Serbian(Messages.getString("EMMPLanguage.79"), 79), //$NON-NLS-1$
    Sinhalese(Messages.getString("EMMPLanguage.80"), 80), //$NON-NLS-1$
    Somali(Messages.getString("EMMPLanguage.81"), 81), //$NON-NLS-1$
    InternationalSpanish(Messages.getString("EMMPLanguage.82"), 82), //$NON-NLS-1$
    LatinAmericanSpanish(Messages.getString("EMMPLanguage.83"), 83), //$NON-NLS-1$
    Swahili(Messages.getString("EMMPLanguage.84"), 84), //$NON-NLS-1$
    FinlandSwedish(Messages.getString("EMMPLanguage.85"), 85), //$NON-NLS-1$
    Reserved1(Messages.getString("EMMPLanguage.86"), 86), //$NON-NLS-1$
    Tamil(Messages.getString("EMMPLanguage.87"), 87), //$NON-NLS-1$
    Telugu(Messages.getString("EMMPLanguage.88"), 88), //$NON-NLS-1$
    Tibetan(Messages.getString("EMMPLanguage.89"), 89), //$NON-NLS-1$
    Tigrinya(Messages.getString("EMMPLanguage.90"), 90), //$NON-NLS-1$
    CyprusTurkish(Messages.getString("EMMPLanguage.91"), 91), //$NON-NLS-1$
    Turkmen(Messages.getString("EMMPLanguage.92"), 92), //$NON-NLS-1$
    Ukrainian(Messages.getString("EMMPLanguage.93"), 93), //$NON-NLS-1$
    Urdu(Messages.getString("EMMPLanguage.94"), 94), //$NON-NLS-1$
    Reserved2(Messages.getString("EMMPLanguage.95"), 95), //$NON-NLS-1$
    Vietnamese(Messages.getString("EMMPLanguage.96"), 96), //$NON-NLS-1$
    Welsh(Messages.getString("EMMPLanguage.97"), 97), //$NON-NLS-1$
    Zulu(Messages.getString("EMMPLanguage.98"), 98), //$NON-NLS-1$
    Other(Messages.getString("EMMPLanguage.99"), 99),  //$NON-NLS-1$
	ManufacturerEnglish(Messages.getString("EMMPLanguage.100"), 100), //$NON-NLS-1$
	SouthSotho(Messages.getString("EMMPLanguage.101"), 101), //$NON-NLS-1$
	Basque(Messages.getString("EMMPLanguage.102"), 102), //$NON-NLS-1$
	Galician(Messages.getString("EMMPLanguage.103"), 103), //$NON-NLS-1$
	Javanese(Messages.getString("EMMPLanguage.104"), 104), //$NON-NLS-1$
	Maithili(Messages.getString("EMMPLanguage.105"), 105), //$NON-NLS-1$
	Azerbaijani_Latin(Messages.getString("EMMPLanguage.106"), 106), //$NON-NLS-1$
	Azerbaijani_Cyrillic(Messages.getString("EMMPLanguage.107"), 107), //$NON-NLS-1$
	Oriya(Messages.getString("EMMPLanguage.108"), 108), //$NON-NLS-1$
	Bhojpuri(Messages.getString("EMMPLanguage.109"), 109), //$NON-NLS-1$
	Sundanese(Messages.getString("EMMPLanguage.110"), 110), //$NON-NLS-1$
	Kurdish_Latin(Messages.getString("EMMPLanguage.111"), 111), //$NON-NLS-1$
	Kurdish_Arabic(Messages.getString("EMMPLanguage.112"), 112), //$NON-NLS-1$
	Pashto(Messages.getString("EMMPLanguage.113"), 113), //$NON-NLS-1$
	Hausa(Messages.getString("EMMPLanguage.114"), 114), //$NON-NLS-1$
	Oromo(Messages.getString("EMMPLanguage.115"), 115), //$NON-NLS-1$
	Uzbek_Latin(Messages.getString("EMMPLanguage.116"), 116), //$NON-NLS-1$
	Uzbek_Cyrillic(Messages.getString("EMMPLanguage.117"), 117), //$NON-NLS-1$
	Sindhi_Arabic(Messages.getString("EMMPLanguage.118"), 118), //$NON-NLS-1$
	Sindhi_Devanagari(Messages.getString("EMMPLanguage.119"), 119), //$NON-NLS-1$
	Yoruba(Messages.getString("EMMPLanguage.120"), 120), //$NON-NLS-1$
	Cebuano(Messages.getString("EMMPLanguage.121"), 121), //$NON-NLS-1$
	Igbo(Messages.getString("EMMPLanguage.122"), 122), //$NON-NLS-1$
	Malagasy(Messages.getString("EMMPLanguage.123"), 123), //$NON-NLS-1$
	Nepali(Messages.getString("EMMPLanguage.124"), 124), //$NON-NLS-1$
	Assamese(Messages.getString("EMMPLanguage.125"), 125), //$NON-NLS-1$
	Shona(Messages.getString("EMMPLanguage.126"), 126), //$NON-NLS-1$
	Zhuang(Messages.getString("EMMPLanguage.127"), 127), //$NON-NLS-1$
	Madurese(Messages.getString("EMMPLanguage.128"), 128), //$NON-NLS-1$
	English_Apac(Messages.getString("EMMPLanguage.129"), 129), //$NON-NLS-1$
	/*
	 * Discontinuity
	 */
	English_Taiwan(Messages.getString("EMMPLanguage.157"), 157), //$NON-NLS-1$
	English_HongKong(Messages.getString("EMMPLanguage.158"), 158), //$NON-NLS-1$
	English_Prc(Messages.getString("EMMPLanguage.159"), 159), //$NON-NLS-1$
	English_Japan(Messages.getString("EMMPLanguage.160"), 160), //$NON-NLS-1$
	English_Thailand(Messages.getString("EMMPLanguage.161"), 161), //$NON-NLS-1$
	Fulfulde(Messages.getString("EMMPLanguage.162"), 162), //$NON-NLS-1$
	Tamazight(Messages.getString("EMMPLanguage.163"), 163), //$NON-NLS-1$
	BolivianQuechua(Messages.getString("EMMPLanguage.164"), 164), //$NON-NLS-1$
	PeruQuechua(Messages.getString("EMMPLanguage.165"), 165), //$NON-NLS-1$
	EcuadorQuechua(Messages.getString("EMMPLanguage.166"), 166), //$NON-NLS-1$
	Tajik_Cyrillic(Messages.getString("EMMPLanguage.167"), 167), //$NON-NLS-1$
	Tajik_PersoArabic(Messages.getString("EMMPLanguage.168"), 168), //$NON-NLS-1$
	Nyanja(Messages.getString("EMMPLanguage.169"), 169), //$NON-NLS-1$
	HaitianCreole(Messages.getString("EMMPLanguage.170"), 170), //$NON-NLS-1$
	Lombard(Messages.getString("EMMPLanguage.171"), 171), //$NON-NLS-1$
	Koongo(Messages.getString("EMMPLanguage.172"), 172), //$NON-NLS-1$
	Akan(Messages.getString("EMMPLanguage.173"), 173), //$NON-NLS-1$
	Hmong(Messages.getString("EMMPLanguage.174"), 174), //$NON-NLS-1$
	Yi(Messages.getString("EMMPLanguage.175"), 175), //$NON-NLS-1$
	Tshiluba(Messages.getString("EMMPLanguage.176"), 176), //$NON-NLS-1$
	Ilocano(Messages.getString("EMMPLanguage.177"), 177), //$NON-NLS-1$
	Uyghur(Messages.getString("EMMPLanguage.178"), 178), //$NON-NLS-1$
	Neapolitan(Messages.getString("EMMPLanguage.179"), 179), //$NON-NLS-1$
	Rwanda(Messages.getString("EMMPLanguage.180"), 180), //$NON-NLS-1$
	Xhosa(Messages.getString("EMMPLanguage.181"), 181), //$NON-NLS-1$
	Balochi(Messages.getString("EMMPLanguage.182"), 182), //$NON-NLS-1$
	Hiligaynon(Messages.getString("EMMPLanguage.183"), 183), //$NON-NLS-1$
	Minangkabau(Messages.getString("EMMPLanguage.184"), 184), //$NON-NLS-1$
	Makhuwa(Messages.getString("EMMPLanguage.185"), 185), //$NON-NLS-1$
	Santali(Messages.getString("EMMPLanguage.186"), 186), //$NON-NLS-1$
	Gikuyu(Messages.getString("EMMPLanguage.187"), 187), //$NON-NLS-1$
	Moore(Messages.getString("EMMPLanguage.188"), 188), //$NON-NLS-1$
	Guarani(Messages.getString("EMMPLanguage.189"), 189), //$NON-NLS-1$
	Rundi(Messages.getString("EMMPLanguage.190"), 190), //$NON-NLS-1$
	Romani_Latin(Messages.getString("EMMPLanguage.191"), 191), //$NON-NLS-1$
	Romani_Cyrillic(Messages.getString("EMMPLanguage.192"), 192), //$NON-NLS-1$
	Tswana(Messages.getString("EMMPLanguage.193"), 193), //$NON-NLS-1$
	Kanuri(Messages.getString("EMMPLanguage.194"), 194), //$NON-NLS-1$
	Kashmiri_Devanagari(Messages.getString("EMMPLanguage.195"), 195), //$NON-NLS-1$
	Kashmiri_PersoArabic(Messages.getString("EMMPLanguage.196"), 196), //$NON-NLS-1$
	Umbundu(Messages.getString("EMMPLanguage.197"), 197), //$NON-NLS-1$
	Konkani(Messages.getString("EMMPLanguage.198"), 198), //$NON-NLS-1$
	Balinese(Messages.getString("EMMPLanguage.199"), 199), //$NON-NLS-1$
	NorthernSotho(Messages.getString("EMMPLanguage.200"), 200), //$NON-NLS-1$
	Wolof(Messages.getString("EMMPLanguage.201"), 201), //$NON-NLS-1$
	Bemba(Messages.getString("EMMPLanguage.202"), 202), //$NON-NLS-1$
	Tsonga(Messages.getString("EMMPLanguage.203"), 203), //$NON-NLS-1$
	Yiddish(Messages.getString("EMMPLanguage.204"), 204), //$NON-NLS-1$
	Kirghiz(Messages.getString("EMMPLanguage.205"), 205), //$NON-NLS-1$
	Ganda(Messages.getString("EMMPLanguage.206"), 206), //$NON-NLS-1$
	Soga(Messages.getString("EMMPLanguage.207"), 207), //$NON-NLS-1$
	Mbundu(Messages.getString("EMMPLanguage.208"), 208), //$NON-NLS-1$
	Bambara(Messages.getString("EMMPLanguage.209"), 209), //$NON-NLS-1$
	CentralAymara(Messages.getString("EMMPLanguage.210"), 210), //$NON-NLS-1$
	Zarma(Messages.getString("EMMPLanguage.211"), 211), //$NON-NLS-1$
	Lingala(Messages.getString("EMMPLanguage.212"), 212), //$NON-NLS-1$
	Bashkir(Messages.getString("EMMPLanguage.213"), 213), //$NON-NLS-1$
	Chuvash(Messages.getString("EMMPLanguage.214"), 214), //$NON-NLS-1$
	Swati(Messages.getString("EMMPLanguage.215"), 215), //$NON-NLS-1$
	Tatar(Messages.getString("EMMPLanguage.216"), 216), //$NON-NLS-1$
	SouthernNdebele(Messages.getString("EMMPLanguage.217"), 217), //$NON-NLS-1$
	Sardinian(Messages.getString("EMMPLanguage.218"), 218), //$NON-NLS-1$
	Scots(Messages.getString("EMMPLanguage.219"), 219), //$NON-NLS-1$
	Meitei(Messages.getString("EMMPLanguage.220"), 220), //$NON-NLS-1$
	Walloon(Messages.getString("EMMPLanguage.221"), 221), //$NON-NLS-1$
	Kabardian(Messages.getString("EMMPLanguage.222"), 222), //$NON-NLS-1$
	Mazanderani(Messages.getString("EMMPLanguage.223"), 223), //$NON-NLS-1$
	Gilaki(Messages.getString("EMMPLanguage.224"), 224), //$NON-NLS-1$
	Shan(Messages.getString("EMMPLanguage.225"), 225), //$NON-NLS-1$
	Luyia(Messages.getString("EMMPLanguage.226"), 226), //$NON-NLS-1$
	Luo(Messages.getString("EMMPLanguage.227"), 227), //$NON-NLS-1$
	Sukuma(Messages.getString("EMMPLanguage.228"), 228), //$NON-NLS-1$
	Aceh(Messages.getString("EMMPLanguage.229"), 229), //$NON-NLS-1$
	English_India(Messages.getString("EMMPLanguage.230"), 230),	//$NON-NLS-1$
	Malay_Apac(Messages.getString("EMMPLanguage.326"), 326), //$NON-NLS-1$
    Indonesian_APAC(Messages.getString("EMMPLanguage.327"), 327),  //$NON-NLS-1$
    /*
     * Discontinuity
     */
    SC_NonLocalized(Messages.getString("EMMPLanguage.NonLocalized"), 0); //$NON-NLS-1$

	private String key;
	private int langCode;
	
	private EMMPLanguage(String key, int langCode) {
		this.key = key;
		this.langCode = langCode;
	}
	
	public int getCode() {
		return langCode;
	}
	
	public String getName() {
		return key;
	}
	public String toString() {
		return key;
	}

	/**
	 * Get a language from a language code.
	 * @return language, never <code>null</code> 
	 * @throws IllegalArgumentException for unknown value
	 * @throws NumberFormatException for non-integral value
	 */
	public static EMMPLanguage fromCode(String value) {
		if (value.equalsIgnoreCase("SC")) //$NON-NLS-1$
			return SC_NonLocalized;
		
		int code = Integer.parseInt(value);
		return forLangCode(code);
	}

	/**
	 * Return string value of language code (two-digit number or SC)
	 * @return the string for the language's code, never <code>null</code>
	 */
	public String getCodeString() {
		if (langCode == 0)
			return "SC"; //$NON-NLS-1$
		String codeString = Integer.toString(langCode);
		if (langCode < 10) // must be minimum 2 digits
			return "0" + codeString; //$NON-NLS-1$
		return codeString;
	}

	/**
	 * Get the EMMPLanguage for the given language code
	 * @param code
	 * @return an MMPLanguage
	 * @throws IllegalArgumentException for unknown value
	 */
	public static EMMPLanguage forLangCode(int code) {
		Check.checkArg(code >= 0);
		
		for (EMMPLanguage lang : values()) {
			if (lang.getCode() == code)
				return lang;
		}
		throw new IllegalArgumentException("Unknown language code: " + code); //$NON-NLS-1$
	}
}
