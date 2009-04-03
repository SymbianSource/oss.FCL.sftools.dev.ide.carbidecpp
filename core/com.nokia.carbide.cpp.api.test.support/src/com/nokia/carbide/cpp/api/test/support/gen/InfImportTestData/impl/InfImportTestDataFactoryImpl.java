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
package com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl;

import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.*;

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
public class InfImportTestDataFactoryImpl extends EFactoryImpl implements InfImportTestDataFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InfImportTestDataFactory init() {
		try {
			InfImportTestDataFactory theInfImportTestDataFactory = (InfImportTestDataFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/com.nokia.carbide.cpp.project.core.tests/schema/infImportTestData.xsd"); 
			if (theInfImportTestDataFactory != null) {
				return theInfImportTestDataFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new InfImportTestDataFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfImportTestDataFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case InfImportTestDataPackage.BLD_INF_FILES_TYPE: return createBldInfFilesType();
			case InfImportTestDataPackage.BLD_INF_FILE_TYPE: return createBldInfFileType();
			case InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE: return createBldInfImportDataType();
			case InfImportTestDataPackage.BUILD_CONFIG_TYPE: return createBuildConfigType();
			case InfImportTestDataPackage.BUILD_CONFIGURATIONS_TYPE: return createBuildConfigurationsType();
			case InfImportTestDataPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case InfImportTestDataPackage.INF_COMPONENTS_TYPE: return createInfComponentsType();
			case InfImportTestDataPackage.INF_COMPONENT_TYPE: return createInfComponentType();
			case InfImportTestDataPackage.MAK_MAKE_REFS_TYPE: return createMakMakeRefsType();
			case InfImportTestDataPackage.MAK_MAKE_REF_TYPE: return createMakMakeRefType();
			case InfImportTestDataPackage.ROOT_DIRECTORY_TYPE: return createRootDirectoryType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case InfImportTestDataPackage.TARGET_TYPE:
				return createTargetTypeFromString(eDataType, initialValue);
			case InfImportTestDataPackage.NAME_TYPE:
				return createNameTypeFromString(eDataType, initialValue);
			case InfImportTestDataPackage.NAME_TYPE1:
				return createNameType1FromString(eDataType, initialValue);
			case InfImportTestDataPackage.PATH_TYPE:
				return createPathTypeFromString(eDataType, initialValue);
			case InfImportTestDataPackage.PATH_TYPE1:
				return createPathType1FromString(eDataType, initialValue);
			case InfImportTestDataPackage.PLATFORM_TYPE:
				return createPlatformTypeFromString(eDataType, initialValue);
			case InfImportTestDataPackage.PROJECT_NAME_TYPE:
				return createProjectNameTypeFromString(eDataType, initialValue);
			case InfImportTestDataPackage.SDK_ID_TYPE:
				return createSdkIdTypeFromString(eDataType, initialValue);
			case InfImportTestDataPackage.TARGET_TYPE_OBJECT:
				return createTargetTypeObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case InfImportTestDataPackage.TARGET_TYPE:
				return convertTargetTypeToString(eDataType, instanceValue);
			case InfImportTestDataPackage.NAME_TYPE:
				return convertNameTypeToString(eDataType, instanceValue);
			case InfImportTestDataPackage.NAME_TYPE1:
				return convertNameType1ToString(eDataType, instanceValue);
			case InfImportTestDataPackage.PATH_TYPE:
				return convertPathTypeToString(eDataType, instanceValue);
			case InfImportTestDataPackage.PATH_TYPE1:
				return convertPathType1ToString(eDataType, instanceValue);
			case InfImportTestDataPackage.PLATFORM_TYPE:
				return convertPlatformTypeToString(eDataType, instanceValue);
			case InfImportTestDataPackage.PROJECT_NAME_TYPE:
				return convertProjectNameTypeToString(eDataType, instanceValue);
			case InfImportTestDataPackage.SDK_ID_TYPE:
				return convertSdkIdTypeToString(eDataType, instanceValue);
			case InfImportTestDataPackage.TARGET_TYPE_OBJECT:
				return convertTargetTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BldInfFilesType createBldInfFilesType() {
		BldInfFilesTypeImpl bldInfFilesType = new BldInfFilesTypeImpl();
		return bldInfFilesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BldInfFileType createBldInfFileType() {
		BldInfFileTypeImpl bldInfFileType = new BldInfFileTypeImpl();
		return bldInfFileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BldInfImportDataType createBldInfImportDataType() {
		BldInfImportDataTypeImpl bldInfImportDataType = new BldInfImportDataTypeImpl();
		return bldInfImportDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildConfigType createBuildConfigType() {
		BuildConfigTypeImpl buildConfigType = new BuildConfigTypeImpl();
		return buildConfigType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BuildConfigurationsType createBuildConfigurationsType() {
		BuildConfigurationsTypeImpl buildConfigurationsType = new BuildConfigurationsTypeImpl();
		return buildConfigurationsType;
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
	public InfComponentsType createInfComponentsType() {
		InfComponentsTypeImpl infComponentsType = new InfComponentsTypeImpl();
		return infComponentsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfComponentType createInfComponentType() {
		InfComponentTypeImpl infComponentType = new InfComponentTypeImpl();
		return infComponentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MakMakeRefsType createMakMakeRefsType() {
		MakMakeRefsTypeImpl makMakeRefsType = new MakMakeRefsTypeImpl();
		return makMakeRefsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MakMakeRefType createMakMakeRefType() {
		MakMakeRefTypeImpl makMakeRefType = new MakMakeRefTypeImpl();
		return makMakeRefType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootDirectoryType createRootDirectoryType() {
		RootDirectoryTypeImpl rootDirectoryType = new RootDirectoryTypeImpl();
		return rootDirectoryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TargetType createTargetTypeFromString(EDataType eDataType, String initialValue) {
		TargetType result = TargetType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTargetTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
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
	public String createPathTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.ANY_URI, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPathTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.ANY_URI, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createPathType1FromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.ANY_URI, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPathType1ToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.ANY_URI, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createPlatformTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPlatformTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createProjectNameTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertProjectNameTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createSdkIdTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSdkIdTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TargetType createTargetTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createTargetTypeFromString(InfImportTestDataPackage.Literals.TARGET_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTargetTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTargetTypeToString(InfImportTestDataPackage.Literals.TARGET_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfImportTestDataPackage getInfImportTestDataPackage() {
		return (InfImportTestDataPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static InfImportTestDataPackage getPackage() {
		return InfImportTestDataPackage.eINSTANCE;
	}

} //InfImportTestDataFactoryImpl
