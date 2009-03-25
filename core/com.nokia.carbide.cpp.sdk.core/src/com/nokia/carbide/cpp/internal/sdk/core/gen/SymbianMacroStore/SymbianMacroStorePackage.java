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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface SymbianMacroStorePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "SymbianMacroStore";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/com.nokia.carbide.cpp.sdk.core/schema/symbianMacroStore.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "SymbianMacroStore";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SymbianMacroStorePackage eINSTANCE = com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Macro</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MACRO = 3;

	/**
	 * The feature id for the '<em><b>Os Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__OS_MACROS = 4;

	/**
	 * The feature id for the '<em><b>Os Version</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__OS_VERSION = 5;

	/**
	 * The feature id for the '<em><b>Platform</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PLATFORM = 6;

	/**
	 * The feature id for the '<em><b>Platform Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PLATFORM_MACROS = 7;

	/**
	 * The feature id for the '<em><b>Sdk Vendor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SDK_VENDOR = 8;

	/**
	 * The feature id for the '<em><b>Symbian Macro Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SYMBIAN_MACRO_STORE = 9;

	/**
	 * The feature id for the '<em><b>Symbian OS Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SYMBIAN_OS_MACROS = 10;

	/**
	 * The feature id for the '<em><b>Vendor Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__VENDOR_MACROS = 11;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 12;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsMacrosTypeImpl <em>Os Macros Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsMacrosTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getOsMacrosType()
	 * @generated
	 */
	int OS_MACROS_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Macro</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS_MACROS_TYPE__MACRO = 0;

	/**
	 * The number of structural features of the '<em>Os Macros Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS_MACROS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsVersionTypeImpl <em>Os Version Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsVersionTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getOsVersionType()
	 * @generated
	 */
	int OS_VERSION_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Platform Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS_VERSION_TYPE__PLATFORM_MACROS = 0;

	/**
	 * The feature id for the '<em><b>Os Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS_VERSION_TYPE__OS_MACROS = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS_VERSION_TYPE__VERSION = 2;

	/**
	 * The number of structural features of the '<em>Os Version Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OS_VERSION_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.PlatformMacrosTypeImpl <em>Platform Macros Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.PlatformMacrosTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getPlatformMacrosType()
	 * @generated
	 */
	int PLATFORM_MACROS_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Platform</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLATFORM_MACROS_TYPE__PLATFORM = 0;

	/**
	 * The number of structural features of the '<em>Platform Macros Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLATFORM_MACROS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.PlatformTypeImpl <em>Platform Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.PlatformTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getPlatformType()
	 * @generated
	 */
	int PLATFORM_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Macro</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLATFORM_TYPE__MACRO = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLATFORM_TYPE__NAME = 1;

	/**
	 * The number of structural features of the '<em>Platform Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLATFORM_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SdkVendorTypeImpl <em>Sdk Vendor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SdkVendorTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getSdkVendorType()
	 * @generated
	 */
	int SDK_VENDOR_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Macro</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDK_VENDOR_TYPE__MACRO = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDK_VENDOR_TYPE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDK_VENDOR_TYPE__VERSION = 2;

	/**
	 * The number of structural features of the '<em>Sdk Vendor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDK_VENDOR_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStoreTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStoreTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getSymbianMacroStoreType()
	 * @generated
	 */
	int SYMBIAN_MACRO_STORE_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Symbian OS Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS = 0;

	/**
	 * The feature id for the '<em><b>Vendor Macros</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS = 1;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_MACRO_STORE_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianOSMacrosTypeImpl <em>Symbian OS Macros Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianOSMacrosTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getSymbianOSMacrosType()
	 * @generated
	 */
	int SYMBIAN_OS_MACROS_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Os Version</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_OS_MACROS_TYPE__OS_VERSION = 0;

	/**
	 * The number of structural features of the '<em>Symbian OS Macros Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMBIAN_OS_MACROS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.VendorMacrosTypeImpl <em>Vendor Macros Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.VendorMacrosTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getVendorMacrosType()
	 * @generated
	 */
	int VENDOR_MACROS_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Sdk Vendor</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENDOR_MACROS_TYPE__SDK_VENDOR = 0;

	/**
	 * The number of structural features of the '<em>Vendor Macros Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENDOR_MACROS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '<em>Macro Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getMacroType()
	 * @generated
	 */
	int MACRO_TYPE = 9;

	/**
	 * The meta object id for the '<em>Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getNameType()
	 * @generated
	 */
	int NAME_TYPE = 10;

	/**
	 * The meta object id for the '<em>Name Type1</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getNameType1()
	 * @generated
	 */
	int NAME_TYPE1 = 11;

	/**
	 * The meta object id for the '<em>Version Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getVersionType()
	 * @generated
	 */
	int VERSION_TYPE = 12;

	/**
	 * The meta object id for the '<em>Version Type1</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getVersionType1()
	 * @generated
	 */
	int VERSION_TYPE1 = 13;


	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getMacro <em>Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Macro</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getMacro()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Macro();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getOsMacros <em>Os Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Os Macros</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getOsMacros()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OsMacros();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getOsVersion <em>Os Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Os Version</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getOsVersion()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OsVersion();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getPlatform <em>Platform</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Platform</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getPlatform()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Platform();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getPlatformMacros <em>Platform Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Platform Macros</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getPlatformMacros()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_PlatformMacros();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getSdkVendor <em>Sdk Vendor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sdk Vendor</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getSdkVendor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SdkVendor();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getSymbianMacroStore <em>Symbian Macro Store</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Symbian Macro Store</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getSymbianMacroStore()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SymbianMacroStore();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getSymbianOSMacros <em>Symbian OS Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Symbian OS Macros</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getSymbianOSMacros()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SymbianOSMacros();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getVendorMacros <em>Vendor Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Vendor Macros</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot#getVendorMacros()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_VendorMacros();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType <em>Os Macros Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Os Macros Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType
	 * @generated
	 */
	EClass getOsMacrosType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType#getMacro <em>Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Macro</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType#getMacro()
	 * @see #getOsMacrosType()
	 * @generated
	 */
	EAttribute getOsMacrosType_Macro();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType <em>Os Version Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Os Version Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType
	 * @generated
	 */
	EClass getOsVersionType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getPlatformMacros <em>Platform Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Platform Macros</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getPlatformMacros()
	 * @see #getOsVersionType()
	 * @generated
	 */
	EReference getOsVersionType_PlatformMacros();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getOsMacros <em>Os Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Os Macros</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getOsMacros()
	 * @see #getOsVersionType()
	 * @generated
	 */
	EReference getOsVersionType_OsMacros();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType#getVersion()
	 * @see #getOsVersionType()
	 * @generated
	 */
	EAttribute getOsVersionType_Version();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType <em>Platform Macros Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Platform Macros Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType
	 * @generated
	 */
	EClass getPlatformMacrosType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType#getPlatform <em>Platform</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Platform</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType#getPlatform()
	 * @see #getPlatformMacrosType()
	 * @generated
	 */
	EReference getPlatformMacrosType_Platform();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType <em>Platform Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Platform Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType
	 * @generated
	 */
	EClass getPlatformType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType#getMacro <em>Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Macro</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType#getMacro()
	 * @see #getPlatformType()
	 * @generated
	 */
	EAttribute getPlatformType_Macro();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType#getName()
	 * @see #getPlatformType()
	 * @generated
	 */
	EAttribute getPlatformType_Name();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType <em>Sdk Vendor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sdk Vendor Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType
	 * @generated
	 */
	EClass getSdkVendorType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType#getMacro <em>Macro</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Macro</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType#getMacro()
	 * @see #getSdkVendorType()
	 * @generated
	 */
	EAttribute getSdkVendorType_Macro();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType#getName()
	 * @see #getSdkVendorType()
	 * @generated
	 */
	EAttribute getSdkVendorType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType#getVersion()
	 * @see #getSdkVendorType()
	 * @generated
	 */
	EAttribute getSdkVendorType_Version();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType
	 * @generated
	 */
	EClass getSymbianMacroStoreType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType#getSymbianOSMacros <em>Symbian OS Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Symbian OS Macros</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType#getSymbianOSMacros()
	 * @see #getSymbianMacroStoreType()
	 * @generated
	 */
	EReference getSymbianMacroStoreType_SymbianOSMacros();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType#getVendorMacros <em>Vendor Macros</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Vendor Macros</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType#getVendorMacros()
	 * @see #getSymbianMacroStoreType()
	 * @generated
	 */
	EReference getSymbianMacroStoreType_VendorMacros();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType <em>Symbian OS Macros Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symbian OS Macros Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType
	 * @generated
	 */
	EClass getSymbianOSMacrosType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType#getOsVersion <em>Os Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Os Version</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType#getOsVersion()
	 * @see #getSymbianOSMacrosType()
	 * @generated
	 */
	EReference getSymbianOSMacrosType_OsVersion();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType <em>Vendor Macros Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vendor Macros Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType
	 * @generated
	 */
	EClass getVendorMacrosType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType#getSdkVendor <em>Sdk Vendor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sdk Vendor</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType#getSdkVendor()
	 * @see #getVendorMacrosType()
	 * @generated
	 */
	EReference getVendorMacrosType_SdkVendor();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Macro Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Macro Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='macro_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getMacroType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='name_._1_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getNameType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Name Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Name Type1</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='name_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getNameType1();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Version Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='version_._1_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getVersionType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Version Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version Type1</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='version_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getVersionType1();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SymbianMacroStoreFactory getSymbianMacroStoreFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Macro</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MACRO = eINSTANCE.getDocumentRoot_Macro();

		/**
		 * The meta object literal for the '<em><b>Os Macros</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__OS_MACROS = eINSTANCE.getDocumentRoot_OsMacros();

		/**
		 * The meta object literal for the '<em><b>Os Version</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__OS_VERSION = eINSTANCE.getDocumentRoot_OsVersion();

		/**
		 * The meta object literal for the '<em><b>Platform</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PLATFORM = eINSTANCE.getDocumentRoot_Platform();

		/**
		 * The meta object literal for the '<em><b>Platform Macros</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PLATFORM_MACROS = eINSTANCE.getDocumentRoot_PlatformMacros();

		/**
		 * The meta object literal for the '<em><b>Sdk Vendor</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SDK_VENDOR = eINSTANCE.getDocumentRoot_SdkVendor();

		/**
		 * The meta object literal for the '<em><b>Symbian Macro Store</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SYMBIAN_MACRO_STORE = eINSTANCE.getDocumentRoot_SymbianMacroStore();

		/**
		 * The meta object literal for the '<em><b>Symbian OS Macros</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SYMBIAN_OS_MACROS = eINSTANCE.getDocumentRoot_SymbianOSMacros();

		/**
		 * The meta object literal for the '<em><b>Vendor Macros</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__VENDOR_MACROS = eINSTANCE.getDocumentRoot_VendorMacros();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsMacrosTypeImpl <em>Os Macros Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsMacrosTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getOsMacrosType()
		 * @generated
		 */
		EClass OS_MACROS_TYPE = eINSTANCE.getOsMacrosType();

		/**
		 * The meta object literal for the '<em><b>Macro</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OS_MACROS_TYPE__MACRO = eINSTANCE.getOsMacrosType_Macro();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsVersionTypeImpl <em>Os Version Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsVersionTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getOsVersionType()
		 * @generated
		 */
		EClass OS_VERSION_TYPE = eINSTANCE.getOsVersionType();

		/**
		 * The meta object literal for the '<em><b>Platform Macros</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OS_VERSION_TYPE__PLATFORM_MACROS = eINSTANCE.getOsVersionType_PlatformMacros();

		/**
		 * The meta object literal for the '<em><b>Os Macros</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OS_VERSION_TYPE__OS_MACROS = eINSTANCE.getOsVersionType_OsMacros();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OS_VERSION_TYPE__VERSION = eINSTANCE.getOsVersionType_Version();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.PlatformMacrosTypeImpl <em>Platform Macros Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.PlatformMacrosTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getPlatformMacrosType()
		 * @generated
		 */
		EClass PLATFORM_MACROS_TYPE = eINSTANCE.getPlatformMacrosType();

		/**
		 * The meta object literal for the '<em><b>Platform</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLATFORM_MACROS_TYPE__PLATFORM = eINSTANCE.getPlatformMacrosType_Platform();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.PlatformTypeImpl <em>Platform Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.PlatformTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getPlatformType()
		 * @generated
		 */
		EClass PLATFORM_TYPE = eINSTANCE.getPlatformType();

		/**
		 * The meta object literal for the '<em><b>Macro</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLATFORM_TYPE__MACRO = eINSTANCE.getPlatformType_Macro();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLATFORM_TYPE__NAME = eINSTANCE.getPlatformType_Name();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SdkVendorTypeImpl <em>Sdk Vendor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SdkVendorTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getSdkVendorType()
		 * @generated
		 */
		EClass SDK_VENDOR_TYPE = eINSTANCE.getSdkVendorType();

		/**
		 * The meta object literal for the '<em><b>Macro</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SDK_VENDOR_TYPE__MACRO = eINSTANCE.getSdkVendorType_Macro();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SDK_VENDOR_TYPE__NAME = eINSTANCE.getSdkVendorType_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SDK_VENDOR_TYPE__VERSION = eINSTANCE.getSdkVendorType_Version();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStoreTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStoreTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getSymbianMacroStoreType()
		 * @generated
		 */
		EClass SYMBIAN_MACRO_STORE_TYPE = eINSTANCE.getSymbianMacroStoreType();

		/**
		 * The meta object literal for the '<em><b>Symbian OS Macros</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS = eINSTANCE.getSymbianMacroStoreType_SymbianOSMacros();

		/**
		 * The meta object literal for the '<em><b>Vendor Macros</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS = eINSTANCE.getSymbianMacroStoreType_VendorMacros();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianOSMacrosTypeImpl <em>Symbian OS Macros Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianOSMacrosTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getSymbianOSMacrosType()
		 * @generated
		 */
		EClass SYMBIAN_OS_MACROS_TYPE = eINSTANCE.getSymbianOSMacrosType();

		/**
		 * The meta object literal for the '<em><b>Os Version</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYMBIAN_OS_MACROS_TYPE__OS_VERSION = eINSTANCE.getSymbianOSMacrosType_OsVersion();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.VendorMacrosTypeImpl <em>Vendor Macros Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.VendorMacrosTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getVendorMacrosType()
		 * @generated
		 */
		EClass VENDOR_MACROS_TYPE = eINSTANCE.getVendorMacrosType();

		/**
		 * The meta object literal for the '<em><b>Sdk Vendor</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VENDOR_MACROS_TYPE__SDK_VENDOR = eINSTANCE.getVendorMacrosType_SdkVendor();

		/**
		 * The meta object literal for the '<em>Macro Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getMacroType()
		 * @generated
		 */
		EDataType MACRO_TYPE = eINSTANCE.getMacroType();

		/**
		 * The meta object literal for the '<em>Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getNameType()
		 * @generated
		 */
		EDataType NAME_TYPE = eINSTANCE.getNameType();

		/**
		 * The meta object literal for the '<em>Name Type1</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getNameType1()
		 * @generated
		 */
		EDataType NAME_TYPE1 = eINSTANCE.getNameType1();

		/**
		 * The meta object literal for the '<em>Version Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getVersionType()
		 * @generated
		 */
		EDataType VERSION_TYPE = eINSTANCE.getVersionType();

		/**
		 * The meta object literal for the '<em>Version Type1</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStorePackageImpl#getVersionType1()
		 * @generated
		 */
		EDataType VERSION_TYPE1 = eINSTANCE.getVersionType1();

	}

} //SymbianMacroStorePackage
