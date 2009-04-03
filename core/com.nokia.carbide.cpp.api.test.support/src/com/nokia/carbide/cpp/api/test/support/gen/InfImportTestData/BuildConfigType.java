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
package com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Build Config Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBuildConfigType()
 * @model extendedMetaData="name='buildConfig_._type' kind='empty'"
 * @generated
 */
public interface BuildConfigType extends EObject {
	/**
	 * Returns the value of the '<em><b>Platform</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Platform</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Platform</em>' attribute.
	 * @see #setPlatform(String)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBuildConfigType_Platform()
	 * @model dataType="com.nokia.carbide.cpp.project.core.gen.InfImportTestData.PlatformType" required="true"
	 *        extendedMetaData="kind='attribute' name='platform' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPlatform();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getPlatform <em>Platform</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Platform</em>' attribute.
	 * @see #getPlatform()
	 * @generated
	 */
	void setPlatform(String value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' attribute.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' attribute.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType
	 * @see #isSetTarget()
	 * @see #unsetTarget()
	 * @see #setTarget(TargetType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBuildConfigType_Target()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='target' namespace='##targetNamespace'"
	 * @generated
	 */
	TargetType getTarget();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getTarget <em>Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' attribute.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType
	 * @see #isSetTarget()
	 * @see #unsetTarget()
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(TargetType value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getTarget <em>Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTarget()
	 * @see #getTarget()
	 * @see #setTarget(TargetType)
	 * @generated
	 */
	void unsetTarget();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getTarget <em>Target</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Target</em>' attribute is set.
	 * @see #unsetTarget()
	 * @see #getTarget()
	 * @see #setTarget(TargetType)
	 * @generated
	 */
	boolean isSetTarget();

} // BuildConfigType
