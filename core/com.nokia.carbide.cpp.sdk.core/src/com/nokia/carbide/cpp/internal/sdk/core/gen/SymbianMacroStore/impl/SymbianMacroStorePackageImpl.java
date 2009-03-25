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
package com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl;

import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreFactory;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SymbianMacroStorePackageImpl extends EPackageImpl implements SymbianMacroStorePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass osMacrosTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass osVersionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass platformMacrosTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass platformTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sdkVendorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbianMacroStoreTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass symbianOSMacrosTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vendorMacrosTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType macroTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nameTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType nameType1EDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType versionTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType versionType1EDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SymbianMacroStorePackageImpl() {
		super(eNS_URI, SymbianMacroStoreFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SymbianMacroStorePackage init() {
		if (isInited) return (SymbianMacroStorePackage)EPackage.Registry.INSTANCE.getEPackage(SymbianMacroStorePackage.eNS_URI);

		// Obtain or create and register package
		SymbianMacroStorePackageImpl theSymbianMacroStorePackage = (SymbianMacroStorePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof SymbianMacroStorePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new SymbianMacroStorePackageImpl());

		isInited = true;

		// Create package meta-data objects
		theSymbianMacroStorePackage.createPackageContents();

		// Initialize created meta-data
		theSymbianMacroStorePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSymbianMacroStorePackage.freeze();

		return theSymbianMacroStorePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Macro() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_OsMacros() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_OsVersion() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Platform() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_PlatformMacros() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SdkVendor() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SymbianMacroStore() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SymbianOSMacros() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_VendorMacros() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOsMacrosType() {
		return osMacrosTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOsMacrosType_Macro() {
		return (EAttribute)osMacrosTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOsVersionType() {
		return osVersionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOsVersionType_PlatformMacros() {
		return (EReference)osVersionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOsVersionType_OsMacros() {
		return (EReference)osVersionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOsVersionType_Version() {
		return (EAttribute)osVersionTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlatformMacrosType() {
		return platformMacrosTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlatformMacrosType_Platform() {
		return (EReference)platformMacrosTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlatformType() {
		return platformTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlatformType_Macro() {
		return (EAttribute)platformTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlatformType_Name() {
		return (EAttribute)platformTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSdkVendorType() {
		return sdkVendorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSdkVendorType_Macro() {
		return (EAttribute)sdkVendorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSdkVendorType_Name() {
		return (EAttribute)sdkVendorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSdkVendorType_Version() {
		return (EAttribute)sdkVendorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbianMacroStoreType() {
		return symbianMacroStoreTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSymbianMacroStoreType_SymbianOSMacros() {
		return (EReference)symbianMacroStoreTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSymbianMacroStoreType_VendorMacros() {
		return (EReference)symbianMacroStoreTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSymbianOSMacrosType() {
		return symbianOSMacrosTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSymbianOSMacrosType_OsVersion() {
		return (EReference)symbianOSMacrosTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVendorMacrosType() {
		return vendorMacrosTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVendorMacrosType_SdkVendor() {
		return (EReference)vendorMacrosTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getMacroType() {
		return macroTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getNameType() {
		return nameTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getNameType1() {
		return nameType1EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getVersionType() {
		return versionTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getVersionType1() {
		return versionType1EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianMacroStoreFactory getSymbianMacroStoreFactory() {
		return (SymbianMacroStoreFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MACRO);
		createEReference(documentRootEClass, DOCUMENT_ROOT__OS_MACROS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__OS_VERSION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PLATFORM);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PLATFORM_MACROS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SDK_VENDOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SYMBIAN_MACRO_STORE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SYMBIAN_OS_MACROS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__VENDOR_MACROS);

		osMacrosTypeEClass = createEClass(OS_MACROS_TYPE);
		createEAttribute(osMacrosTypeEClass, OS_MACROS_TYPE__MACRO);

		osVersionTypeEClass = createEClass(OS_VERSION_TYPE);
		createEReference(osVersionTypeEClass, OS_VERSION_TYPE__PLATFORM_MACROS);
		createEReference(osVersionTypeEClass, OS_VERSION_TYPE__OS_MACROS);
		createEAttribute(osVersionTypeEClass, OS_VERSION_TYPE__VERSION);

		platformMacrosTypeEClass = createEClass(PLATFORM_MACROS_TYPE);
		createEReference(platformMacrosTypeEClass, PLATFORM_MACROS_TYPE__PLATFORM);

		platformTypeEClass = createEClass(PLATFORM_TYPE);
		createEAttribute(platformTypeEClass, PLATFORM_TYPE__MACRO);
		createEAttribute(platformTypeEClass, PLATFORM_TYPE__NAME);

		sdkVendorTypeEClass = createEClass(SDK_VENDOR_TYPE);
		createEAttribute(sdkVendorTypeEClass, SDK_VENDOR_TYPE__MACRO);
		createEAttribute(sdkVendorTypeEClass, SDK_VENDOR_TYPE__NAME);
		createEAttribute(sdkVendorTypeEClass, SDK_VENDOR_TYPE__VERSION);

		symbianMacroStoreTypeEClass = createEClass(SYMBIAN_MACRO_STORE_TYPE);
		createEReference(symbianMacroStoreTypeEClass, SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS);
		createEReference(symbianMacroStoreTypeEClass, SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS);

		symbianOSMacrosTypeEClass = createEClass(SYMBIAN_OS_MACROS_TYPE);
		createEReference(symbianOSMacrosTypeEClass, SYMBIAN_OS_MACROS_TYPE__OS_VERSION);

		vendorMacrosTypeEClass = createEClass(VENDOR_MACROS_TYPE);
		createEReference(vendorMacrosTypeEClass, VENDOR_MACROS_TYPE__SDK_VENDOR);

		// Create data types
		macroTypeEDataType = createEDataType(MACRO_TYPE);
		nameTypeEDataType = createEDataType(NAME_TYPE);
		nameType1EDataType = createEDataType(NAME_TYPE1);
		versionTypeEDataType = createEDataType(VERSION_TYPE);
		versionType1EDataType = createEDataType(VERSION_TYPE1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Macro(), this.getMacroType(), "macro", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_OsMacros(), this.getOsMacrosType(), null, "osMacros", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_OsVersion(), this.getOsVersionType(), null, "osVersion", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Platform(), this.getPlatformType(), null, "platform", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_PlatformMacros(), this.getPlatformMacrosType(), null, "platformMacros", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SdkVendor(), this.getSdkVendorType(), null, "sdkVendor", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SymbianMacroStore(), this.getSymbianMacroStoreType(), null, "symbianMacroStore", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SymbianOSMacros(), this.getSymbianOSMacrosType(), null, "symbianOSMacros", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_VendorMacros(), this.getVendorMacrosType(), null, "vendorMacros", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(osMacrosTypeEClass, OsMacrosType.class, "OsMacrosType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOsMacrosType_Macro(), this.getMacroType(), "macro", null, 1, -1, OsMacrosType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(osVersionTypeEClass, OsVersionType.class, "OsVersionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOsVersionType_PlatformMacros(), this.getPlatformMacrosType(), null, "platformMacros", null, 1, 1, OsVersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOsVersionType_OsMacros(), this.getOsMacrosType(), null, "osMacros", null, 1, 1, OsVersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOsVersionType_Version(), this.getVersionType1(), "version", null, 1, 1, OsVersionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(platformMacrosTypeEClass, PlatformMacrosType.class, "PlatformMacrosType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlatformMacrosType_Platform(), this.getPlatformType(), null, "platform", null, 1, -1, PlatformMacrosType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(platformTypeEClass, PlatformType.class, "PlatformType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPlatformType_Macro(), this.getMacroType(), "macro", null, 1, -1, PlatformType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlatformType_Name(), this.getNameType1(), "name", null, 1, 1, PlatformType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sdkVendorTypeEClass, SdkVendorType.class, "SdkVendorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSdkVendorType_Macro(), this.getMacroType(), "macro", null, 1, -1, SdkVendorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSdkVendorType_Name(), this.getNameType(), "name", null, 1, 1, SdkVendorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSdkVendorType_Version(), this.getVersionType(), "version", null, 1, 1, SdkVendorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(symbianMacroStoreTypeEClass, SymbianMacroStoreType.class, "SymbianMacroStoreType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSymbianMacroStoreType_SymbianOSMacros(), this.getSymbianOSMacrosType(), null, "symbianOSMacros", null, 1, 1, SymbianMacroStoreType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSymbianMacroStoreType_VendorMacros(), this.getVendorMacrosType(), null, "vendorMacros", null, 1, 1, SymbianMacroStoreType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(symbianOSMacrosTypeEClass, SymbianOSMacrosType.class, "SymbianOSMacrosType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSymbianOSMacrosType_OsVersion(), this.getOsVersionType(), null, "osVersion", null, 1, -1, SymbianOSMacrosType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vendorMacrosTypeEClass, VendorMacrosType.class, "VendorMacrosType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVendorMacrosType_SdkVendor(), this.getSdkVendorType(), null, "sdkVendor", null, 1, -1, VendorMacrosType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(macroTypeEDataType, String.class, "MacroType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameTypeEDataType, String.class, "NameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameType1EDataType, String.class, "NameType1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(versionTypeEDataType, String.class, "VersionType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(versionType1EDataType, String.class, "VersionType1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "qualified", "false"
		   });		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getDocumentRoot_Macro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "macro",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_OsMacros(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "osMacros",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_OsVersion(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "osVersion",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Platform(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "platform",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_PlatformMacros(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "platformMacros",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SdkVendor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sdkVendor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SymbianMacroStore(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SymbianMacroStore",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SymbianOSMacros(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SymbianOSMacros",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_VendorMacros(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "VendorMacros",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (macroTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "macro_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (nameTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "name_._1_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (nameType1EDataType, 
		   source, 
		   new String[] {
			 "name", "name_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (osMacrosTypeEClass, 
		   source, 
		   new String[] {
			 "name", "osMacros_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getOsMacrosType_Macro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "macro",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (osVersionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "osVersion_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getOsVersionType_PlatformMacros(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "platformMacros",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOsVersionType_OsMacros(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "osMacros",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOsVersionType_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "version",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (platformMacrosTypeEClass, 
		   source, 
		   new String[] {
			 "name", "platformMacros_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPlatformMacrosType_Platform(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "platform",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (platformTypeEClass, 
		   source, 
		   new String[] {
			 "name", "platform_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPlatformType_Macro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "macro",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPlatformType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (sdkVendorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "sdkVendor_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSdkVendorType_Macro(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "macro",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSdkVendorType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSdkVendorType_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "version",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (symbianMacroStoreTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SymbianMacroStore_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSymbianMacroStoreType_SymbianOSMacros(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SymbianOSMacros",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSymbianMacroStoreType_VendorMacros(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "VendorMacros",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (symbianOSMacrosTypeEClass, 
		   source, 
		   new String[] {
			 "name", "SymbianOSMacros_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getSymbianOSMacrosType_OsVersion(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "osVersion",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (vendorMacrosTypeEClass, 
		   source, 
		   new String[] {
			 "name", "VendorMacros_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getVendorMacrosType_SdkVendor(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sdkVendor",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (versionTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "version_._1_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (versionType1EDataType, 
		   source, 
		   new String[] {
			 "name", "version_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });
	}

} //SymbianMacroStorePackageImpl
