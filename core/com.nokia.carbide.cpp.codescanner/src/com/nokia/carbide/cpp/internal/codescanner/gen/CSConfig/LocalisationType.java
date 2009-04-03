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

package com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Localisation Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LocalisationType#isEnable <em>Enable</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getLocalisationType()
 * @model extendedMetaData="name='localisation_._type' kind='empty'"
 * @generated
 */
public interface LocalisationType extends EObject {
	/**
	 * Returns the value of the '<em><b>Enable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enable</em>' attribute.
	 * @see #isSetEnable()
	 * @see #unsetEnable()
	 * @see #setEnable(boolean)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getLocalisationType_Enable()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 *        extendedMetaData="kind='attribute' name='enable' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isEnable();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LocalisationType#isEnable <em>Enable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enable</em>' attribute.
	 * @see #isSetEnable()
	 * @see #unsetEnable()
	 * @see #isEnable()
	 * @generated
	 */
	void setEnable(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LocalisationType#isEnable <em>Enable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetEnable()
	 * @see #isEnable()
	 * @see #setEnable(boolean)
	 * @generated
	 */
	void unsetEnable();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LocalisationType#isEnable <em>Enable</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Enable</em>' attribute is set.
	 * @see #unsetEnable()
	 * @see #isEnable()
	 * @see #setEnable(boolean)
	 * @generated
	 */
	boolean isSetEnable();

} // LocalisationType