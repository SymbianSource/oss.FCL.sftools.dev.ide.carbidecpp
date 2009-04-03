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
*/
package com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Platform Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType#getMacro <em>Macro</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getPlatformType()
 * @model extendedMetaData="name='platform_._type' kind='elementOnly'"
 * @generated
 */
public interface PlatformType extends EObject {
	/**
	 * Returns the value of the '<em><b>Macro</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Macro</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Macro</em>' attribute list.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getPlatformType_Macro()
	 * @model type="java.lang.String" unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.MacroType" required="true"
	 *        extendedMetaData="kind='element' name='macro' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getMacro();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getPlatformType_Name()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.NameType1" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // PlatformType