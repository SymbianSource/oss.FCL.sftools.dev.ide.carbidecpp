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
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType#getSymbianOSMacros <em>Symbian OS Macros</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType#getVendorMacros <em>Vendor Macros</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getSymbianMacroStoreType()
 * @model extendedMetaData="name='SymbianMacroStore_._type' kind='elementOnly'"
 * @generated
 */
public interface SymbianMacroStoreType extends EObject {
	/**
	 * Returns the value of the '<em><b>Symbian OS Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbian OS Macros</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbian OS Macros</em>' containment reference.
	 * @see #setSymbianOSMacros(SymbianOSMacrosType)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getSymbianMacroStoreType_SymbianOSMacros()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='SymbianOSMacros' namespace='##targetNamespace'"
	 * @generated
	 */
	SymbianOSMacrosType getSymbianOSMacros();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType#getSymbianOSMacros <em>Symbian OS Macros</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbian OS Macros</em>' containment reference.
	 * @see #getSymbianOSMacros()
	 * @generated
	 */
	void setSymbianOSMacros(SymbianOSMacrosType value);

	/**
	 * Returns the value of the '<em><b>Vendor Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vendor Macros</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vendor Macros</em>' containment reference.
	 * @see #setVendorMacros(VendorMacrosType)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#getSymbianMacroStoreType_VendorMacros()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='VendorMacros' namespace='##targetNamespace'"
	 * @generated
	 */
	VendorMacrosType getVendorMacros();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType#getVendorMacros <em>Vendor Macros</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vendor Macros</em>' containment reference.
	 * @see #getVendorMacros()
	 * @generated
	 */
	void setVendorMacros(VendorMacrosType value);

} // SymbianMacroStoreType