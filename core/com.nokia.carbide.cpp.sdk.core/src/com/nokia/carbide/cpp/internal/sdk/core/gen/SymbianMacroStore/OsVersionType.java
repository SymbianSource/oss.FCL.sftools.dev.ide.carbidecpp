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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Os Version Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getPlatformMacros <em>Platform Macros</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getOsMacros <em>Os Macros</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getOsVersionType()
 * @model extendedMetaData="name='osVersion_._type' kind='elementOnly'"
 * @generated
 */
public interface OsVersionType extends EObject {
	/**
	 * Returns the value of the '<em><b>Platform Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Platform Macros</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Platform Macros</em>' containment reference.
	 * @see #setPlatformMacros(PlatformMacrosType)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getOsVersionType_PlatformMacros()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='platformMacros' namespace='##targetNamespace'"
	 * @generated
	 */
	PlatformMacrosType getPlatformMacros();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getPlatformMacros <em>Platform Macros</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Platform Macros</em>' containment reference.
	 * @see #getPlatformMacros()
	 * @generated
	 */
	void setPlatformMacros(PlatformMacrosType value);

	/**
	 * Returns the value of the '<em><b>Os Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os Macros</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os Macros</em>' containment reference.
	 * @see #setOsMacros(OsMacrosType)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getOsVersionType_OsMacros()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='osMacros' namespace='##targetNamespace'"
	 * @generated
	 */
	OsMacrosType getOsMacros();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getOsMacros <em>Os Macros</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os Macros</em>' containment reference.
	 * @see #getOsMacros()
	 * @generated
	 */
	void setOsMacros(OsMacrosType value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getOsVersionType_Version()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VersionType1" required="true"
	 *        extendedMetaData="kind='attribute' name='version' namespace='##targetNamespace'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // OsVersionType