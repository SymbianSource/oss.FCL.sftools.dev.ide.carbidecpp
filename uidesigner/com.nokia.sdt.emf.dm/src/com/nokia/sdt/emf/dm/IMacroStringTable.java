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
package com.nokia.sdt.emf.dm;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

import java.util.Set;

/**
 * Management of non-localized strings referenced 
 * by key values. For Symbian there are strings defined
 * as macros which are not within the pattern for localized 
 * string macros.
 * @model
 */
public interface IMacroStringTable extends EObject{
	
	/**
	 * @model mapType="EStringToStringMapEntry"
	 */
	EMap getStringMacros();
	
	/**
	 * Add a new string macro. Returns the generated
	 * unique key
	 */
	StringValue addMacro(String value);
	
	/**
	 * Add a new string macro with a predefined key.
	 * Returns null if the key is already present but
	 * defined with a different value
	 */
	StringValue addMacroWithKey(String key, String value);
	
	/**
	 * Update an existing macro entry.
	 * @param keyValue StringValue containing the key
	 * as its value
	 * @param newValue the new string contents
	 */
	void updateMacro(StringValue keyValue, String newValue);

	/**
	 * Assigns a key provider which will generate
	 * key values for added strings
	 */
    void setKeyProvider(IStringKeyProvider provider);

    /**
     * Get key provider that generates
     * key values for added strings
     */
    IStringKeyProvider getKeyProvider();

    /**
     * Get the set of user-generated macros.
     */
    Set<String> getUserGeneratedStringKeys();
}
