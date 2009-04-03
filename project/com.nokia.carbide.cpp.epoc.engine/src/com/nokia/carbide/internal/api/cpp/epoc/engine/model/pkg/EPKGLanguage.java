/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.api.cpp.epoc.engine.model.pkg;

import com.nokia.carbide.internal.cpp.epoc.engine.Messages;
import com.nokia.cpp.internal.api.utils.core.*;

import java.text.MessageFormat;

/**
 * The Symbian languages in PKG (2 letter) format
 */
public enum EPKGLanguage {
	AF(Messages.getString("EPKGLanguage.0"), "AF"), //$NON-NLS-1$ //$NON-NLS-2$
	AH(Messages.getString("EPKGLanguage.2"), "AH"), //$NON-NLS-1$ //$NON-NLS-2$
	AM(Messages.getString("EPKGLanguage.4"), "AM"), //$NON-NLS-1$ //$NON-NLS-2$
	AR(Messages.getString("EPKGLanguage.6"), "AR"), //$NON-NLS-1$ //$NON-NLS-2$
	AS(Messages.getString("EPKGLanguage.8"), "AS"), //$NON-NLS-1$ //$NON-NLS-2$
	AU(Messages.getString("EPKGLanguage.10"), "AU"), //$NON-NLS-1$ //$NON-NLS-2$
	BE(Messages.getString("EPKGLanguage.12"), "BE"), //$NON-NLS-1$ //$NON-NLS-2$
	BF(Messages.getString("EPKGLanguage.14"), "BF"), //$NON-NLS-1$ //$NON-NLS-2$
	BG(Messages.getString("EPKGLanguage.16"), "BG"), //$NON-NLS-1$ //$NON-NLS-2$
	BL(Messages.getString("EPKGLanguage.18"), "BL"), //$NON-NLS-1$ //$NON-NLS-2$
	BN(Messages.getString("EPKGLanguage.20"), "BN"), //$NON-NLS-1$ //$NON-NLS-2$
	BO(Messages.getString("EPKGLanguage.22"), "BO"), //$NON-NLS-1$ //$NON-NLS-2$
	BP(Messages.getString("EPKGLanguage.24"), "BP"), //$NON-NLS-1$ //$NON-NLS-2$
	CA(Messages.getString("EPKGLanguage.26"), "CA"), //$NON-NLS-1$ //$NON-NLS-2$
	CE(Messages.getString("EPKGLanguage.28"), "CE"), //$NON-NLS-1$ //$NON-NLS-2$
	CF(Messages.getString("EPKGLanguage.30"), "CF"), //$NON-NLS-1$ //$NON-NLS-2$
	CS(Messages.getString("EPKGLanguage.32"), "CS"), //$NON-NLS-1$ //$NON-NLS-2$
	CT(Messages.getString("EPKGLanguage.34"), "CT"), //$NON-NLS-1$ //$NON-NLS-2$
	CY(Messages.getString("EPKGLanguage.36"), "CY"), //$NON-NLS-1$ //$NON-NLS-2$
	DA(Messages.getString("EPKGLanguage.38"), "DA"), //$NON-NLS-1$ //$NON-NLS-2$
	DU(Messages.getString("EPKGLanguage.40"), "DU"), //$NON-NLS-1$ //$NON-NLS-2$
	EL(Messages.getString("EPKGLanguage.42"), "EL"), //$NON-NLS-1$ //$NON-NLS-2$
	EN(Messages.getString("EPKGLanguage.44"), "EN"), //$NON-NLS-1$ //$NON-NLS-2$
	ET(Messages.getString("EPKGLanguage.46"), "ET"), //$NON-NLS-1$ //$NON-NLS-2$
	FA(Messages.getString("EPKGLanguage.48"), "FA"), //$NON-NLS-1$ //$NON-NLS-2$
	FI(Messages.getString("EPKGLanguage.50"), "FI"), //$NON-NLS-1$ //$NON-NLS-2$
	FR(Messages.getString("EPKGLanguage.52"), "FR"), //$NON-NLS-1$ //$NON-NLS-2$
	FS(Messages.getString("EPKGLanguage.54"), "FS"), //$NON-NLS-1$ //$NON-NLS-2$
	GA(Messages.getString("EPKGLanguage.56"), "GA"), //$NON-NLS-1$ //$NON-NLS-2$
	GD(Messages.getString("EPKGLanguage.58"), "GD"), //$NON-NLS-1$ //$NON-NLS-2$
	GE(Messages.getString("EPKGLanguage.60"), "GE"), //$NON-NLS-1$ //$NON-NLS-2$
	GU(Messages.getString("EPKGLanguage.62"), "GU"), //$NON-NLS-1$ //$NON-NLS-2$
	HE(Messages.getString("EPKGLanguage.64"), "HE"), //$NON-NLS-1$ //$NON-NLS-2$
	HI(Messages.getString("EPKGLanguage.66"), "HI"), //$NON-NLS-1$ //$NON-NLS-2$
	HK(Messages.getString("EPKGLanguage.68"), "HK"), //$NON-NLS-1$ //$NON-NLS-2$
	HR(Messages.getString("EPKGLanguage.70"), "HR"), //$NON-NLS-1$ //$NON-NLS-2$
	HU(Messages.getString("EPKGLanguage.72"), "HU"), //$NON-NLS-1$ //$NON-NLS-2$
	HY(Messages.getString("EPKGLanguage.74"), "HY"), //$NON-NLS-1$ //$NON-NLS-2$
	IC(Messages.getString("EPKGLanguage.76"), "IC"), //$NON-NLS-1$ //$NON-NLS-2$
	IE(Messages.getString("EPKGLanguage.78"), "IE"), //$NON-NLS-1$ //$NON-NLS-2$
	IF(Messages.getString("EPKGLanguage.80"), "IF"), //$NON-NLS-1$ //$NON-NLS-2$
	IN(Messages.getString("EPKGLanguage.82"), "IN"), //$NON-NLS-1$ //$NON-NLS-2$
	IT(Messages.getString("EPKGLanguage.84"), "IT"), //$NON-NLS-1$ //$NON-NLS-2$
	JA(Messages.getString("EPKGLanguage.86"), "JA"), //$NON-NLS-1$ //$NON-NLS-2$
	KA(Messages.getString("EPKGLanguage.88"), "KA"), //$NON-NLS-1$ //$NON-NLS-2$
	KK(Messages.getString("EPKGLanguage.90"), "KK"), //$NON-NLS-1$ //$NON-NLS-2$
	KM(Messages.getString("EPKGLanguage.92"), "KM"), //$NON-NLS-1$ //$NON-NLS-2$
	KN(Messages.getString("EPKGLanguage.94"), "KN"), //$NON-NLS-1$ //$NON-NLS-2$
	KO(Messages.getString("EPKGLanguage.96"), "KO"), //$NON-NLS-1$ //$NON-NLS-2$
	LO(Messages.getString("EPKGLanguage.98"), "LO"), //$NON-NLS-1$ //$NON-NLS-2$
	LS(Messages.getString("EPKGLanguage.100"), "LS"), //$NON-NLS-1$ //$NON-NLS-2$
	LT(Messages.getString("EPKGLanguage.102"), "LT"), //$NON-NLS-1$ //$NON-NLS-2$
	LV(Messages.getString("EPKGLanguage.104"), "LV"), //$NON-NLS-1$ //$NON-NLS-2$
	MK(Messages.getString("EPKGLanguage.106"), "MK"), //$NON-NLS-1$ //$NON-NLS-2$
	ML(Messages.getString("EPKGLanguage.108"), "ML"), //$NON-NLS-1$ //$NON-NLS-2$
	MN(Messages.getString("EPKGLanguage.110"), "MN"), //$NON-NLS-1$ //$NON-NLS-2$
	MO(Messages.getString("EPKGLanguage.112"), "MO"), //$NON-NLS-1$ //$NON-NLS-2$
	MR(Messages.getString("EPKGLanguage.114"), "MR"), //$NON-NLS-1$ //$NON-NLS-2$
	MS(Messages.getString("EPKGLanguage.116"), "MS"), //$NON-NLS-1$ //$NON-NLS-2$
	MY(Messages.getString("EPKGLanguage.118"), "MY"), //$NON-NLS-1$ //$NON-NLS-2$
	NN(Messages.getString("EPKGLanguage.120"), "NN"), //$NON-NLS-1$ //$NON-NLS-2$
	NO(Messages.getString("EPKGLanguage.122"), "NO"), //$NON-NLS-1$ //$NON-NLS-2$
	NZ(Messages.getString("EPKGLanguage.124"), "NZ"), //$NON-NLS-1$ //$NON-NLS-2$
	OS(Messages.getString("EPKGLanguage.126"), "OS"), //$NON-NLS-1$ //$NON-NLS-2$
	PA(Messages.getString("EPKGLanguage.128"), "PA"), //$NON-NLS-1$ //$NON-NLS-2$
	PL(Messages.getString("EPKGLanguage.192"), "PL"), //$NON-NLS-1$ //$NON-NLS-2$
	PO(Messages.getString("EPKGLanguage.132"), "PO"), //$NON-NLS-1$ //$NON-NLS-2$
	RO(Messages.getString("EPKGLanguage.134"), "RO"), //$NON-NLS-1$ //$NON-NLS-2$
	RU(Messages.getString("EPKGLanguage.136"), "RU"), //$NON-NLS-1$ //$NON-NLS-2$
	SA(Messages.getString("EPKGLanguage.138"), "SA"), //$NON-NLS-1$ //$NON-NLS-2$
	SF(Messages.getString("EPKGLanguage.140"), "SF"), //$NON-NLS-1$ //$NON-NLS-2$
	SG(Messages.getString("EPKGLanguage.142"), "SG"), //$NON-NLS-1$ //$NON-NLS-2$
	SH(Messages.getString("EPKGLanguage.144"), "SH"), //$NON-NLS-1$ //$NON-NLS-2$
	SI(Messages.getString("EPKGLanguage.146"), "SI"), //$NON-NLS-1$ //$NON-NLS-2$
	SK(Messages.getString("EPKGLanguage.148"), "SK"), //$NON-NLS-1$ //$NON-NLS-2$
	SL(Messages.getString("EPKGLanguage.150"), "SL"), //$NON-NLS-1$ //$NON-NLS-2$
	SO(Messages.getString("EPKGLanguage.152"), "SO"), //$NON-NLS-1$ //$NON-NLS-2$
	SP(Messages.getString("EPKGLanguage.154"), "SP"), //$NON-NLS-1$ //$NON-NLS-2$
	SQ(Messages.getString("EPKGLanguage.156"), "SQ"), //$NON-NLS-1$ //$NON-NLS-2$
	SR(Messages.getString("EPKGLanguage.193"), "SR"), //$NON-NLS-1$ //$NON-NLS-2$
	SW(Messages.getString("EPKGLanguage.160"), "SW"), //$NON-NLS-1$ //$NON-NLS-2$
	SZ(Messages.getString("EPKGLanguage.162"), "SZ"), //$NON-NLS-1$ //$NON-NLS-2$
	TA(Messages.getString("EPKGLanguage.164"), "TA"), //$NON-NLS-1$ //$NON-NLS-2$
	TC(Messages.getString("EPKGLanguage.166"), "TC"), //$NON-NLS-1$ //$NON-NLS-2$
	TE(Messages.getString("EPKGLanguage.168"), "TE"), //$NON-NLS-1$ //$NON-NLS-2$
	TH(Messages.getString("EPKGLanguage.170"), "TH"), //$NON-NLS-1$ //$NON-NLS-2$
	TI(Messages.getString("EPKGLanguage.172"), "TI"), //$NON-NLS-1$ //$NON-NLS-2$
	TK(Messages.getString("EPKGLanguage.174"), "TK"), //$NON-NLS-1$ //$NON-NLS-2$
	TL(Messages.getString("EPKGLanguage.176"), "TL"), //$NON-NLS-1$ //$NON-NLS-2$
	TU(Messages.getString("EPKGLanguage.178"), "TU"), //$NON-NLS-1$ //$NON-NLS-2$
	UK(Messages.getString("EPKGLanguage.180"), "UK"), //$NON-NLS-1$ //$NON-NLS-2$
	UR(Messages.getString("EPKGLanguage.182"), "UR"), //$NON-NLS-1$ //$NON-NLS-2$
	VI(Messages.getString("EPKGLanguage.184"), "VI"), //$NON-NLS-1$ //$NON-NLS-2$
	ZH(Messages.getString("EPKGLanguage.186"), "ZH"), //$NON-NLS-1$ //$NON-NLS-2$
	ZU(Messages.getString("EPKGLanguage.188"), "ZU"), //$NON-NLS-1$ //$NON-NLS-2$
	INDEPENDENT(Messages.getString("EPKGLanguage.190"), ""); //$NON-NLS-1$ //$NON-NLS-2$

	private String langName;
	private String langCode;
	private int dialect;

	public final static int NO_DIALECT = -1;

	private EPKGLanguage(String langName, String langCode) {
		this.langName = langName;
		this.langCode = langCode;
		dialect = NO_DIALECT;
	}

	public String getLanguageName() {
		return langName;
	}

	public String getCode() {
		return langCode;
	}

	public int getDialect() {
		return dialect;
	}

	public void setDialect(int dialect) {
		this.dialect = dialect;
	}

	public String toString() {
		if (this.equals(INDEPENDENT))
			return langName;
		if (dialect == NO_DIALECT)
			return MessageFormat.format("{0} [{1}]", //$NON-NLS-1$
					new Object[] { langName, langCode });

		return MessageFormat.format("{0} [{1}({2})]", //$NON-NLS-1$
				new Object[] { langName, langCode, dialect });
	}

	/**
	 * Get the EPKGLanguage for the given language code
	 * 
	 * @param langCode
	 *            String
	 * @return EPKGLanguage
	 */
	public static EPKGLanguage forLangCode(String langCode) {
		for (EPKGLanguage lang : values()) {
			if (lang.getCode().equals(langCode))
				return lang;
		}
		Check.checkArg(false);
		return null;
	}

}
