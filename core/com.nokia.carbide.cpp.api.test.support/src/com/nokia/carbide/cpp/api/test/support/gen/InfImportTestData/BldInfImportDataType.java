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
 * A representation of the model object '<em><b>Bld Inf Import Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType#getBldInfFiles <em>Bld Inf Files</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfImportDataType()
 * @model extendedMetaData="name='BldInfImportData_._type' kind='elementOnly'"
 * @generated
 */
public interface BldInfImportDataType extends EObject {
	/**
	 * Returns the value of the '<em><b>Bld Inf Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bld Inf Files</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bld Inf Files</em>' containment reference.
	 * @see #setBldInfFiles(BldInfFilesType)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getBldInfImportDataType_BldInfFiles()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='bldInfFiles' namespace='##targetNamespace'"
	 * @generated
	 */
	BldInfFilesType getBldInfFiles();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType#getBldInfFiles <em>Bld Inf Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bld Inf Files</em>' containment reference.
	 * @see #getBldInfFiles()
	 * @generated
	 */
	void setBldInfFiles(BldInfFilesType value);

} // BldInfImportDataType
