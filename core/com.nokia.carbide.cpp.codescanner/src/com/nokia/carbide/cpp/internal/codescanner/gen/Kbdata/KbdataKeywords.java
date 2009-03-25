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

package com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Keywords</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywords#getKbdataKeywords <em>Kbdata Keywords</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getKbdataKeywords()
 * @model extendedMetaData="name='kbdataKeywords' kind='elementOnly'"
 * @generated
 */
public interface KbdataKeywords extends EObject {
	/**
	 * Returns the value of the '<em><b>Kbdata Keywords</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kbdata Keywords</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kbdata Keywords</em>' containment reference.
	 * @see #setKbdataKeywords(KbdataKeywordsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage#getKbdataKeywords_KbdataKeywords()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='kbdataKeywords' namespace='##targetNamespace'"
	 * @generated
	 */
	KbdataKeywordsType getKbdataKeywords();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywords#getKbdataKeywords <em>Kbdata Keywords</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kbdata Keywords</em>' containment reference.
	 * @see #getKbdataKeywords()
	 * @generated
	 */
	void setKbdataKeywords(KbdataKeywordsType value);

} // KbdataKeywords
