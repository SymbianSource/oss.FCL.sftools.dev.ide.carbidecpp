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
 * Enumeration for all existing category types of CodeScanner rules.
 *
 */
public enum CSCategory {
	category_legal("legal"),
	category_panic("panic"),
	category_canpanic("canpanic"),
	category_functionality("functionality"),
	category_localisation("localisation"),
	category_performance("performance"),
	category_codingstandards("codingstandards"),
	category_documentation("documentation"),
	category_codereview("codereview"),
	category_other("other"),
	category_unknown("unknown");

	private String name;
	
	/**
	 * Constructor
	 */
	CSCategory(String str) {
		name = str;
	}
	
	/**
	 * Return the name of a CSCategory enum constant.
	 */
	public String toString() {
		return name;
	}

	/**
	 * Return the CSCategory enum constant with the specified name.
	 * @param name - name of the constant to return
	 * @return the CSCategory enum constant with the specified name
	 */
	public static CSCategory toCategory(String name) {
        try {
            return valueOf(name);
        } 
        catch (Exception e) {
            return category_unknown;
        }
	}

}
