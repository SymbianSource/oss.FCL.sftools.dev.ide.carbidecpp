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
 * A representation of the model object '<em><b>Severities Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType#getHigh <em>High</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType#getMedium <em>Medium</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType#getLow <em>Low</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getSeveritiesType()
 * @model extendedMetaData="name='severities_._type' kind='elementOnly'"
 * @generated
 */
public interface SeveritiesType extends EObject {
	/**
	 * Returns the value of the '<em><b>High</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>High</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>High</em>' containment reference.
	 * @see #setHigh(HighType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getSeveritiesType_High()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='high' namespace='##targetNamespace'"
	 * @generated
	 */
	HighType getHigh();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType#getHigh <em>High</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>High</em>' containment reference.
	 * @see #getHigh()
	 * @generated
	 */
	void setHigh(HighType value);

	/**
	 * Returns the value of the '<em><b>Medium</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Medium</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Medium</em>' containment reference.
	 * @see #setMedium(MediumType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getSeveritiesType_Medium()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='medium' namespace='##targetNamespace'"
	 * @generated
	 */
	MediumType getMedium();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType#getMedium <em>Medium</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Medium</em>' containment reference.
	 * @see #getMedium()
	 * @generated
	 */
	void setMedium(MediumType value);

	/**
	 * Returns the value of the '<em><b>Low</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Low</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Low</em>' containment reference.
	 * @see #setLow(LowType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getSeveritiesType_Low()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='low' namespace='##targetNamespace'"
	 * @generated
	 */
	LowType getLow();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType#getLow <em>Low</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Low</em>' containment reference.
	 * @see #getLow()
	 * @generated
	 */
	void setLow(LowType value);

} // SeveritiesType