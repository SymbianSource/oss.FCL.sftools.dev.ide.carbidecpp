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
 * Enumeration for all existing CodeScanner arguments.
 *
 */
public enum CSArgument {
	argument_input("input"),
	argument_lxr("lxr"),
	argument_lxrversion("lxrversion"),
	argument_outputformat("outputformat"),
	argument_timestampedoutput("timestampedoutput"),
	argument_unknown("unknown");

	private String name;
	
	/**
	 * Constructor
	 */
	CSArgument(String str) {
		name = str;
	}
	
	/**
	 * Return the name of a CSArgument enum constant.
	 */
	public String toString() {
		return name;
	}

	/**
	 * Return the CSArguments enum constant with the specified name.
	 * @param name - name of the constant to return
	 * @return the CSArguments enum constant with the specified name
	 */
	public static CSArgument toArgument(String name) {
        try {
            return valueOf(name);
        } 
        catch (Exception e) {
            return argument_unknown;
        }
	}

};
