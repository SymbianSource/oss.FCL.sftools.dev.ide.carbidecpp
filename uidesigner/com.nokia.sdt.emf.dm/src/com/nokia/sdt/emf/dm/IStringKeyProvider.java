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

/**
 * Creates unique keys for localized strings and string macros.
 * Keys must be unique across both sets of strings.
 */
public interface IStringKeyProvider {
	
	String assignLocalizedStringKey();
	
	String assignMacroStringKey();
    
    /** Compare a string to another for optimal sorting 
     * 
     * @param key
     * @param otherKey
     * @return &lt; 0, 0, &gt; 0 depending on ordering
     * @see String#compareTo(java.lang.String) 
     */
    int compareKeys(String key, String otherKey);
}
