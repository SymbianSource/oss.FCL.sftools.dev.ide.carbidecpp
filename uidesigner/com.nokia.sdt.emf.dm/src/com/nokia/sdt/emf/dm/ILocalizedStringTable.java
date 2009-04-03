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

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * A collection of localized strings.
 * For a given language, this defines a list of (key, value) pairs.
 * This corresponds to the contents of a *.lxx (i.e. .l01, .l02, ...)
 * file.
 * 
 * 
 * @model
 */
public interface ILocalizedStringTable extends EObject{
 
	/** 
     * Get the language for which the string is defined 
     * @model dataType="com.nokia.sdt.emf.dm.Language"
     */
 
	Language getLanguage();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.dm.ILocalizedStringTable#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(Language value);

    /** 
     * Get the list of strings 
     * @model mapType="EStringToStringMapEntry"
      */
    EMap getStrings();
}
