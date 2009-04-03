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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage
 * @generated
 */
public interface SymbianMacroStoreFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SymbianMacroStoreFactory eINSTANCE = com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStoreFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Document Root</em>'.
	 * @generated
	 */
	DocumentRoot createDocumentRoot();

	/**
	 * Returns a new object of class '<em>Os Macros Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Os Macros Type</em>'.
	 * @generated
	 */
	OsMacrosType createOsMacrosType();

	/**
	 * Returns a new object of class '<em>Os Version Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Os Version Type</em>'.
	 * @generated
	 */
	OsVersionType createOsVersionType();

	/**
	 * Returns a new object of class '<em>Platform Macros Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Platform Macros Type</em>'.
	 * @generated
	 */
	PlatformMacrosType createPlatformMacrosType();

	/**
	 * Returns a new object of class '<em>Platform Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Platform Type</em>'.
	 * @generated
	 */
	PlatformType createPlatformType();

	/**
	 * Returns a new object of class '<em>Sdk Vendor Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sdk Vendor Type</em>'.
	 * @generated
	 */
	SdkVendorType createSdkVendorType();

	/**
	 * Returns a new object of class '<em>Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Type</em>'.
	 * @generated
	 */
	SymbianMacroStoreType createSymbianMacroStoreType();

	/**
	 * Returns a new object of class '<em>Symbian OS Macros Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Symbian OS Macros Type</em>'.
	 * @generated
	 */
	SymbianOSMacrosType createSymbianOSMacrosType();

	/**
	 * Returns a new object of class '<em>Vendor Macros Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vendor Macros Type</em>'.
	 * @generated
	 */
	VendorMacrosType createVendorMacrosType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SymbianMacroStorePackage getSymbianMacroStorePackage();

} //SymbianMacroStoreFactory
