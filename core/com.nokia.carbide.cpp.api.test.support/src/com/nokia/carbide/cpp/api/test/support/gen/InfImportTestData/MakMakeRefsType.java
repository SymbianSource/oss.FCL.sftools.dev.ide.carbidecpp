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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mak Make Refs Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType#getMakMakeRef <em>Mak Make Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getMakMakeRefsType()
 * @model extendedMetaData="name='makMakeRefs_._type' kind='elementOnly'"
 * @generated
 */
public interface MakMakeRefsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Mak Make Ref</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mak Make Ref</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mak Make Ref</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getMakMakeRefsType_MakMakeRef()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='makMakeRef' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<MakMakeRefType> getMakMakeRef();

} // MakMakeRefsType
