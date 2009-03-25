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

import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SymbianMacroStoreFactoryImpl extends EFactoryImpl implements SymbianMacroStoreFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SymbianMacroStoreFactory init() {
		try {
			SymbianMacroStoreFactory theSymbianMacroStoreFactory = (SymbianMacroStoreFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/com.nokia.carbide.cpp.sdk.core/schema/symbianMacroStore.xsd"); 
			if (theSymbianMacroStoreFactory != null) {
				return theSymbianMacroStoreFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SymbianMacroStoreFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianMacroStoreFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SymbianMacroStorePackage.DOCUMENT_ROOT: return createDocumentRoot();
			case SymbianMacroStorePackage.OS_MACROS_TYPE: return createOsMacrosType();
			case SymbianMacroStorePackage.OS_VERSION_TYPE: return createOsVersionType();
			case SymbianMacroStorePackage.PLATFORM_MACROS_TYPE: return createPlatformMacrosType();
			case SymbianMacroStorePackage.PLATFORM_TYPE: return createPlatformType();
			case SymbianMacroStorePackage.SDK_VENDOR_TYPE: return createSdkVendorType();
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE: return createSymbianMacroStoreType();
			case SymbianMacroStorePackage.SYMBIAN_OS_MACROS_TYPE: return createSymbianOSMacrosType();
			case SymbianMacroStorePackage.VENDOR_MACROS_TYPE: return createVendorMacrosType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case SymbianMacroStorePackage.MACRO_TYPE:
				return createMacroTypeFromString(eDataType, initialValue);
			case SymbianMacroStorePackage.NAME_TYPE:
				return createNameTypeFromString(eDataType, initialValue);
			case SymbianMacroStorePackage.NAME_TYPE1:
				return createNameType1FromString(eDataType, initialValue);
			case SymbianMacroStorePackage.VERSION_TYPE:
				return createVersionTypeFromString(eDataType, initialValue);
			case SymbianMacroStorePackage.VERSION_TYPE1:
				return createVersionType1FromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case SymbianMacroStorePackage.MACRO_TYPE:
				return convertMacroTypeToString(eDataType, instanceValue);
			case SymbianMacroStorePackage.NAME_TYPE:
				return convertNameTypeToString(eDataType, instanceValue);
			case SymbianMacroStorePackage.NAME_TYPE1:
				return convertNameType1ToString(eDataType, instanceValue);
			case SymbianMacroStorePackage.VERSION_TYPE:
				return convertVersionTypeToString(eDataType, instanceValue);
			case SymbianMacroStorePackage.VERSION_TYPE1:
				return convertVersionType1ToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OsMacrosType createOsMacrosType() {
		OsMacrosTypeImpl osMacrosType = new OsMacrosTypeImpl();
		return osMacrosType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OsVersionType createOsVersionType() {
		OsVersionTypeImpl osVersionType = new OsVersionTypeImpl();
		return osVersionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlatformMacrosType createPlatformMacrosType() {
		PlatformMacrosTypeImpl platformMacrosType = new PlatformMacrosTypeImpl();
		return platformMacrosType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlatformType createPlatformType() {
		PlatformTypeImpl platformType = new PlatformTypeImpl();
		return platformType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SdkVendorType createSdkVendorType() {
		SdkVendorTypeImpl sdkVendorType = new SdkVendorTypeImpl();
		return sdkVendorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianMacroStoreType createSymbianMacroStoreType() {
		SymbianMacroStoreTypeImpl symbianMacroStoreType = new SymbianMacroStoreTypeImpl();
		return symbianMacroStoreType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianOSMacrosType createSymbianOSMacrosType() {
		SymbianOSMacrosTypeImpl symbianOSMacrosType = new SymbianOSMacrosTypeImpl();
		return symbianOSMacrosType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VendorMacrosType createVendorMacrosType() {
		VendorMacrosTypeImpl vendorMacrosType = new VendorMacrosTypeImpl();
		return vendorMacrosType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createMacroTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertMacroTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createNameTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNameTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createNameType1FromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNameType1ToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createVersionTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVersionTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createVersionType1FromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVersionType1ToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianMacroStorePackage getSymbianMacroStorePackage() {
		return (SymbianMacroStorePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static SymbianMacroStorePackage getPackage() {
		return SymbianMacroStorePackage.eINSTANCE;
	}

} //SymbianMacroStoreFactoryImpl
