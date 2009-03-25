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

package com.nokia.carbide.cpp.internal.codescanner.config;

/**
 * Enumeration for all existing severity types of CodeScanner rules.
 *
 */
public enum CSSeverity {
	severity_high("high"),
	severity_medium("medium"),
	severity_low("low"),
	severity_unknown("unknown");

	private String name;
	
	/**
	 * Constructor
	 */
	CSSeverity(String str) {
		name = str;
	}
	
	/**
	 * Return the name of a CSSeverity enum constant.
	 */
	public String toString() {
		return name;
	}

	/**
	 * Return the CSSeverity enum constant with the specified name.
	 * @param name - name of the constant to return
	 * @return the CSSeverity enum constant with the specified name
	 */
	public static CSSeverity toSeverity(String name) {
        try {
            return valueOf(name);
        } 
        catch (Exception e) {
            return severity_unknown;
        }
	}

}
