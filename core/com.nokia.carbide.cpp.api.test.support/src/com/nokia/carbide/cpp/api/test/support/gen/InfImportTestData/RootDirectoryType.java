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
 * A representation of the model object '<em><b>Root Directory Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType#getPath <em>Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getRootDirectoryType()
 * @model extendedMetaData="name='rootDirectory_._type' kind='empty'"
 * @generated
 */
public interface RootDirectoryType extends EObject {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#getRootDirectoryType_Path()
	 * @model dataType="com.nokia.carbide.cpp.project.core.gen.InfImportTestData.PathType1" required="true"
	 *        extendedMetaData="kind='attribute' name='path' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

} // RootDirectoryType
