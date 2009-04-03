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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.util.Map;
import java.util.Set;

/**
 * This interface gathers a collection of tables, presumably
 * sharing the same keys, each which map to strings in different
 * languages.
 * 
 * 
 * @model
 */
public interface ILocalizedStringBundle extends EObject {
	
	public interface IListener {
		void stringTableAdded(Language language);
		void stringTableRemoved(Language language);
		void defaultLanguageChanged(Language newDefaultLanguage);
	}
	
	void addListener(IListener listener);
	void removeListener(IListener listener);
	
    /** 
     * Get all the language-specific string Tables 
     * @model type="com.nokia.sdt.emf.dm.ILocalizedStringTable" 
     * 		  containment="true"
     */
    EList getLocalizedStringTables();
    
    /**
     * Adds a new table for the given language. Empty strings are
     * added for all existing keys, i.e. keys present in the other
     * tables.
     * @throws IllegalArgumentException if the language is already present
     * @return the new, initialized string table
     */
    ILocalizedStringTable addLocalizedStringTable(Language language);

    /** Find a language table
     * 
     * @param language the language to look up
     * @return table, or null
     */
    ILocalizedStringTable findLocalizedStringTable(Language language);

    /**
     * Find a string in the given language with the given key.
     */
    String findString(Language language, String key);

     /**
     * Get an map of strings among the tables matching the given key.
     * The returned map has keys of type Language and values of type String.
     * Null is returned if there are no matches
     */
    Map findStrings(String key);

    /**
     * Tell if the table is in the bundle
     */
    boolean hasStringTable(ILocalizedStringTable table);

    /**
     * Tell whether the bundle has a table defining this key
     */
    boolean hasStringKey(String key);


    /** Register a string.  If it already exists, that
     * definition is deleted and replaced with this one; otherwise, 
     * the string is added.  A language table for the string's
     * language is added if necessary.  
     * @param language a language table for this language is added if necessary
     * @param key key value for the string. If null a new one is generated. If
     * the key value exists the existing entry is updated
     * @param value
     */
    StringValue registerString(Language language, String key, String value);
    
    void setKeyProvider(IStringKeyProvider provider);
    
    /**
     * Establish the default language. The default
     * language is implied by methods that don't have
     * any ILanguage context and whose method name ends in 'Default'
     * @throws IllegalArgumentException if null is passed
     */
    void setDefaultLanguage(Language language);
    
    Language getDefaultLanguage();
    
    /**
     * Returns any existing entry for the key
     * in the default language
     */
    String getLocalizedStringDefault(String key);

    /**
     * Adds the current string in the default language
     * A new key is generated. Empty string values are
     * entered for the same key in all other languages 
     * in the string table.
     * @param value
     * @return the localized string entry
     */
    StringValue addLocalizedStringDefault(String value);
    
    /**
     * Updates the localized value for the string with the given
     * key in the default language. If the key is not present
     * a new entry is made with the given key and value and empty
     * values inserted for other languages
     * @return the updated localized string entry
     */
    StringValue updateLocalizedStringDefault(String key, String value);
    
    /**
     * Removes entries for the given key from all languages.
     * @param key
     */
    void removeLocalizedStringAllLanguages(String key);

    /**
     * Get user-generated string keys.
     */
    Set<String> getUserGeneratedStringKeys();
}
