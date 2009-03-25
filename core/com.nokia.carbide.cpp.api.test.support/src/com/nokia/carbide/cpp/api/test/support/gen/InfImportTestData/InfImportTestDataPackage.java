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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface InfImportTestDataPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "InfImportTestData";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/com.nokia.carbide.cpp.project.core.tests/schema/infImportTestData.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "InfImportTestData";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InfImportTestDataPackage eINSTANCE = com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFilesTypeImpl <em>Bld Inf Files Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFilesTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBldInfFilesType()
	 * @generated
	 */
	int BLD_INF_FILES_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Bld Inf File</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILES_TYPE__BLD_INF_FILE = 0;

	/**
	 * The number of structural features of the '<em>Bld Inf Files Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILES_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl <em>Bld Inf File Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBldInfFileType()
	 * @generated
	 */
	int BLD_INF_FILE_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Root Directory</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILE_TYPE__ROOT_DIRECTORY = 0;

	/**
	 * The feature id for the '<em><b>Build Configurations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS = 1;

	/**
	 * The feature id for the '<em><b>Inf Components</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILE_TYPE__INF_COMPONENTS = 2;

	/**
	 * The feature id for the '<em><b>Mak Make Refs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILE_TYPE__MAK_MAKE_REFS = 3;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILE_TYPE__PATH = 4;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILE_TYPE__PROJECT_NAME = 5;

	/**
	 * The feature id for the '<em><b>Sdk Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILE_TYPE__SDK_ID = 6;

	/**
	 * The number of structural features of the '<em>Bld Inf File Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_FILE_TYPE_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfImportDataTypeImpl <em>Bld Inf Import Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfImportDataTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBldInfImportDataType()
	 * @generated
	 */
	int BLD_INF_IMPORT_DATA_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Bld Inf Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES = 0;

	/**
	 * The number of structural features of the '<em>Bld Inf Import Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BLD_INF_IMPORT_DATA_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BuildConfigTypeImpl <em>Build Config Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BuildConfigTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBuildConfigType()
	 * @generated
	 */
	int BUILD_CONFIG_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Platform</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_CONFIG_TYPE__PLATFORM = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_CONFIG_TYPE__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Build Config Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_CONFIG_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BuildConfigurationsTypeImpl <em>Build Configurations Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BuildConfigurationsTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBuildConfigurationsType()
	 * @generated
	 */
	int BUILD_CONFIGURATIONS_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Build Config</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_CONFIGURATIONS_TYPE__BUILD_CONFIG = 0;

	/**
	 * The number of structural features of the '<em>Build Configurations Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUILD_CONFIGURATIONS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 5;

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
	 * The feature id for the '<em><b>Bld Inf File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BLD_INF_FILE = 3;

	/**
	 * The feature id for the '<em><b>Bld Inf Files</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BLD_INF_FILES = 4;

	/**
	 * The feature id for the '<em><b>Bld Inf Import Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BLD_INF_IMPORT_DATA = 5;

	/**
	 * The feature id for the '<em><b>Build Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BUILD_CONFIG = 6;

	/**
	 * The feature id for the '<em><b>Build Configurations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BUILD_CONFIGURATIONS = 7;

	/**
	 * The feature id for the '<em><b>Inf Component</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INF_COMPONENT = 8;

	/**
	 * The feature id for the '<em><b>Inf Components</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INF_COMPONENTS = 9;

	/**
	 * The feature id for the '<em><b>Mak Make Ref</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAK_MAKE_REF = 10;

	/**
	 * The feature id for the '<em><b>Mak Make Refs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MAK_MAKE_REFS = 11;

	/**
	 * The feature id for the '<em><b>Root Directory</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ROOT_DIRECTORY = 12;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 13;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfComponentsTypeImpl <em>Inf Components Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfComponentsTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getInfComponentsType()
	 * @generated
	 */
	int INF_COMPONENTS_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Inf Component</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_COMPONENTS_TYPE__INF_COMPONENT = 0;

	/**
	 * The number of structural features of the '<em>Inf Components Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_COMPONENTS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfComponentTypeImpl <em>Inf Component Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfComponentTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getInfComponentType()
	 * @generated
	 */
	int INF_COMPONENT_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_COMPONENT_TYPE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Inf Component Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INF_COMPONENT_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.MakMakeRefsTypeImpl <em>Mak Make Refs Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.MakMakeRefsTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getMakMakeRefsType()
	 * @generated
	 */
	int MAK_MAKE_REFS_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Mak Make Ref</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAK_MAKE_REFS_TYPE__MAK_MAKE_REF = 0;

	/**
	 * The number of structural features of the '<em>Mak Make Refs Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAK_MAKE_REFS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.MakMakeRefTypeImpl <em>Mak Make Ref Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.MakMakeRefTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getMakMakeRefType()
	 * @generated
	 */
	int MAK_MAKE_REF_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAK_MAKE_REF_TYPE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Mak Make Ref Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAK_MAKE_REF_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.RootDirectoryTypeImpl <em>Root Directory Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.RootDirectoryTypeImpl
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getRootDirectoryType()
	 * @generated
	 */
	int ROOT_DIRECTORY_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DIRECTORY_TYPE__PATH = 0;

	/**
	 * The number of structural features of the '<em>Root Directory Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_DIRECTORY_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType <em>Target Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getTargetType()
	 * @generated
	 */
	int TARGET_TYPE = 11;

	/**
	 * The meta object id for the '<em>Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getNameType()
	 * @generated
	 */
	int NAME_TYPE = 12;

	/**
	 * The meta object id for the '<em>Name Type1</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getNameType1()
	 * @generated
	 */
	int NAME_TYPE1 = 13;

	/**
	 * The meta object id for the '<em>Path Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getPathType()
	 * @generated
	 */
	int PATH_TYPE = 14;

	/**
	 * The meta object id for the '<em>Path Type1</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getPathType1()
	 * @generated
	 */
	int PATH_TYPE1 = 15;

	/**
	 * The meta object id for the '<em>Platform Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getPlatformType()
	 * @generated
	 */
	int PLATFORM_TYPE = 16;

	/**
	 * The meta object id for the '<em>Project Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getProjectNameType()
	 * @generated
	 */
	int PROJECT_NAME_TYPE = 17;

	/**
	 * The meta object id for the '<em>Sdk Id Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getSdkIdType()
	 * @generated
	 */
	int SDK_ID_TYPE = 18;

	/**
	 * The meta object id for the '<em>Target Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getTargetTypeObject()
	 * @generated
	 */
	int TARGET_TYPE_OBJECT = 19;


	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType <em>Bld Inf Files Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bld Inf Files Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType
	 * @generated
	 */
	EClass getBldInfFilesType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType#getBldInfFile <em>Bld Inf File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bld Inf File</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType#getBldInfFile()
	 * @see #getBldInfFilesType()
	 * @generated
	 */
	EReference getBldInfFilesType_BldInfFile();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType <em>Bld Inf File Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bld Inf File Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType
	 * @generated
	 */
	EClass getBldInfFileType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getRootDirectory <em>Root Directory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root Directory</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getRootDirectory()
	 * @see #getBldInfFileType()
	 * @generated
	 */
	EReference getBldInfFileType_RootDirectory();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getBuildConfigurations <em>Build Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Build Configurations</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getBuildConfigurations()
	 * @see #getBldInfFileType()
	 * @generated
	 */
	EReference getBldInfFileType_BuildConfigurations();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getInfComponents <em>Inf Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inf Components</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getInfComponents()
	 * @see #getBldInfFileType()
	 * @generated
	 */
	EReference getBldInfFileType_InfComponents();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getMakMakeRefs <em>Mak Make Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mak Make Refs</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getMakMakeRefs()
	 * @see #getBldInfFileType()
	 * @generated
	 */
	EReference getBldInfFileType_MakMakeRefs();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getPath()
	 * @see #getBldInfFileType()
	 * @generated
	 */
	EAttribute getBldInfFileType_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getProjectName <em>Project Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getProjectName()
	 * @see #getBldInfFileType()
	 * @generated
	 */
	EAttribute getBldInfFileType_ProjectName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getSdkId <em>Sdk Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sdk Id</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType#getSdkId()
	 * @see #getBldInfFileType()
	 * @generated
	 */
	EAttribute getBldInfFileType_SdkId();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType <em>Bld Inf Import Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bld Inf Import Data Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType
	 * @generated
	 */
	EClass getBldInfImportDataType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType#getBldInfFiles <em>Bld Inf Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Bld Inf Files</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType#getBldInfFiles()
	 * @see #getBldInfImportDataType()
	 * @generated
	 */
	EReference getBldInfImportDataType_BldInfFiles();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType <em>Build Config Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Build Config Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType
	 * @generated
	 */
	EClass getBuildConfigType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getPlatform <em>Platform</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Platform</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getPlatform()
	 * @see #getBuildConfigType()
	 * @generated
	 */
	EAttribute getBuildConfigType_Platform();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType#getTarget()
	 * @see #getBuildConfigType()
	 * @generated
	 */
	EAttribute getBuildConfigType_Target();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigurationsType <em>Build Configurations Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Build Configurations Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigurationsType
	 * @generated
	 */
	EClass getBuildConfigurationsType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigurationsType#getBuildConfig <em>Build Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Build Config</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigurationsType#getBuildConfig()
	 * @see #getBuildConfigurationsType()
	 * @generated
	 */
	EReference getBuildConfigurationsType_BuildConfig();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfFile <em>Bld Inf File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Bld Inf File</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfFile()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BldInfFile();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfFiles <em>Bld Inf Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Bld Inf Files</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfFiles()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BldInfFiles();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfImportData <em>Bld Inf Import Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Bld Inf Import Data</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBldInfImportData()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BldInfImportData();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBuildConfig <em>Build Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Build Config</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBuildConfig()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BuildConfig();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBuildConfigurations <em>Build Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Build Configurations</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getBuildConfigurations()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BuildConfigurations();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getInfComponent <em>Inf Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inf Component</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getInfComponent()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_InfComponent();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getInfComponents <em>Inf Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inf Components</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getInfComponents()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_InfComponents();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMakMakeRef <em>Mak Make Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mak Make Ref</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMakMakeRef()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MakMakeRef();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMakMakeRefs <em>Mak Make Refs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mak Make Refs</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getMakMakeRefs()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MakMakeRefs();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getRootDirectory <em>Root Directory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Root Directory</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot#getRootDirectory()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_RootDirectory();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType <em>Inf Components Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inf Components Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType
	 * @generated
	 */
	EClass getInfComponentsType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType#getInfComponent <em>Inf Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inf Component</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType#getInfComponent()
	 * @see #getInfComponentsType()
	 * @generated
	 */
	EReference getInfComponentsType_InfComponent();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType <em>Inf Component Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inf Component Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType
	 * @generated
	 */
	EClass getInfComponentType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType#getName()
	 * @see #getInfComponentType()
	 * @generated
	 */
	EAttribute getInfComponentType_Name();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType <em>Mak Make Refs Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mak Make Refs Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType
	 * @generated
	 */
	EClass getMakMakeRefsType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType#getMakMakeRef <em>Mak Make Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mak Make Ref</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType#getMakMakeRef()
	 * @see #getMakMakeRefsType()
	 * @generated
	 */
	EReference getMakMakeRefsType_MakMakeRef();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType <em>Mak Make Ref Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mak Make Ref Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType
	 * @generated
	 */
	EClass getMakMakeRefType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType#getName()
	 * @see #getMakMakeRefType()
	 * @generated
	 */
	EAttribute getMakMakeRefType_Name();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType <em>Root Directory Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root Directory Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType
	 * @generated
	 */
	EClass getRootDirectoryType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType#getPath()
	 * @see #getRootDirectoryType()
	 * @generated
	 */
	EAttribute getRootDirectoryType_Path();

	/**
	 * Returns the meta object for enum '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType <em>Target Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Target Type</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType
	 * @generated
	 */
	EEnum getTargetType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='name_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
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
	 *        extendedMetaData="name='name_._1_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getNameType1();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Path Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Path Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='path_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#anyURI'"
	 * @generated
	 */
	EDataType getPathType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Path Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Path Type1</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='path_._1_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#anyURI'"
	 * @generated
	 */
	EDataType getPathType1();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Platform Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Platform Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='platform_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getPlatformType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Project Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Project Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='projectName_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getProjectNameType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Sdk Id Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Sdk Id Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='sdkId_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getSdkIdType();

	/**
	 * Returns the meta object for data type '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType <em>Target Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Target Type Object</em>'.
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType
	 * @model instanceClass="com.nokia.carbide.cpp.project.core.gen.InfImportTestData.TargetType"
	 *        extendedMetaData="name='target_._type:Object' baseType='target_._type'"
	 * @generated
	 */
	EDataType getTargetTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InfImportTestDataFactory getInfImportTestDataFactory();

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
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFilesTypeImpl <em>Bld Inf Files Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFilesTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBldInfFilesType()
		 * @generated
		 */
		EClass BLD_INF_FILES_TYPE = eINSTANCE.getBldInfFilesType();

		/**
		 * The meta object literal for the '<em><b>Bld Inf File</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLD_INF_FILES_TYPE__BLD_INF_FILE = eINSTANCE.getBldInfFilesType_BldInfFile();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl <em>Bld Inf File Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfFileTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBldInfFileType()
		 * @generated
		 */
		EClass BLD_INF_FILE_TYPE = eINSTANCE.getBldInfFileType();

		/**
		 * The meta object literal for the '<em><b>Root Directory</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLD_INF_FILE_TYPE__ROOT_DIRECTORY = eINSTANCE.getBldInfFileType_RootDirectory();

		/**
		 * The meta object literal for the '<em><b>Build Configurations</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS = eINSTANCE.getBldInfFileType_BuildConfigurations();

		/**
		 * The meta object literal for the '<em><b>Inf Components</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLD_INF_FILE_TYPE__INF_COMPONENTS = eINSTANCE.getBldInfFileType_InfComponents();

		/**
		 * The meta object literal for the '<em><b>Mak Make Refs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLD_INF_FILE_TYPE__MAK_MAKE_REFS = eINSTANCE.getBldInfFileType_MakMakeRefs();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLD_INF_FILE_TYPE__PATH = eINSTANCE.getBldInfFileType_Path();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLD_INF_FILE_TYPE__PROJECT_NAME = eINSTANCE.getBldInfFileType_ProjectName();

		/**
		 * The meta object literal for the '<em><b>Sdk Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BLD_INF_FILE_TYPE__SDK_ID = eINSTANCE.getBldInfFileType_SdkId();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfImportDataTypeImpl <em>Bld Inf Import Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfImportDataTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBldInfImportDataType()
		 * @generated
		 */
		EClass BLD_INF_IMPORT_DATA_TYPE = eINSTANCE.getBldInfImportDataType();

		/**
		 * The meta object literal for the '<em><b>Bld Inf Files</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES = eINSTANCE.getBldInfImportDataType_BldInfFiles();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BuildConfigTypeImpl <em>Build Config Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BuildConfigTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBuildConfigType()
		 * @generated
		 */
		EClass BUILD_CONFIG_TYPE = eINSTANCE.getBuildConfigType();

		/**
		 * The meta object literal for the '<em><b>Platform</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILD_CONFIG_TYPE__PLATFORM = eINSTANCE.getBuildConfigType_Platform();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUILD_CONFIG_TYPE__TARGET = eINSTANCE.getBuildConfigType_Target();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BuildConfigurationsTypeImpl <em>Build Configurations Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BuildConfigurationsTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getBuildConfigurationsType()
		 * @generated
		 */
		EClass BUILD_CONFIGURATIONS_TYPE = eINSTANCE.getBuildConfigurationsType();

		/**
		 * The meta object literal for the '<em><b>Build Config</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUILD_CONFIGURATIONS_TYPE__BUILD_CONFIG = eINSTANCE.getBuildConfigurationsType_BuildConfig();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.DocumentRootImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Bld Inf File</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BLD_INF_FILE = eINSTANCE.getDocumentRoot_BldInfFile();

		/**
		 * The meta object literal for the '<em><b>Bld Inf Files</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BLD_INF_FILES = eINSTANCE.getDocumentRoot_BldInfFiles();

		/**
		 * The meta object literal for the '<em><b>Bld Inf Import Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BLD_INF_IMPORT_DATA = eINSTANCE.getDocumentRoot_BldInfImportData();

		/**
		 * The meta object literal for the '<em><b>Build Config</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BUILD_CONFIG = eINSTANCE.getDocumentRoot_BuildConfig();

		/**
		 * The meta object literal for the '<em><b>Build Configurations</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BUILD_CONFIGURATIONS = eINSTANCE.getDocumentRoot_BuildConfigurations();

		/**
		 * The meta object literal for the '<em><b>Inf Component</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__INF_COMPONENT = eINSTANCE.getDocumentRoot_InfComponent();

		/**
		 * The meta object literal for the '<em><b>Inf Components</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__INF_COMPONENTS = eINSTANCE.getDocumentRoot_InfComponents();

		/**
		 * The meta object literal for the '<em><b>Mak Make Ref</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAK_MAKE_REF = eINSTANCE.getDocumentRoot_MakMakeRef();

		/**
		 * The meta object literal for the '<em><b>Mak Make Refs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MAK_MAKE_REFS = eINSTANCE.getDocumentRoot_MakMakeRefs();

		/**
		 * The meta object literal for the '<em><b>Root Directory</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ROOT_DIRECTORY = eINSTANCE.getDocumentRoot_RootDirectory();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfComponentsTypeImpl <em>Inf Components Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfComponentsTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getInfComponentsType()
		 * @generated
		 */
		EClass INF_COMPONENTS_TYPE = eINSTANCE.getInfComponentsType();

		/**
		 * The meta object literal for the '<em><b>Inf Component</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INF_COMPONENTS_TYPE__INF_COMPONENT = eINSTANCE.getInfComponentsType_InfComponent();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfComponentTypeImpl <em>Inf Component Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfComponentTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getInfComponentType()
		 * @generated
		 */
		EClass INF_COMPONENT_TYPE = eINSTANCE.getInfComponentType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INF_COMPONENT_TYPE__NAME = eINSTANCE.getInfComponentType_Name();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.MakMakeRefsTypeImpl <em>Mak Make Refs Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.MakMakeRefsTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getMakMakeRefsType()
		 * @generated
		 */
		EClass MAK_MAKE_REFS_TYPE = eINSTANCE.getMakMakeRefsType();

		/**
		 * The meta object literal for the '<em><b>Mak Make Ref</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAK_MAKE_REFS_TYPE__MAK_MAKE_REF = eINSTANCE.getMakMakeRefsType_MakMakeRef();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.MakMakeRefTypeImpl <em>Mak Make Ref Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.MakMakeRefTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getMakMakeRefType()
		 * @generated
		 */
		EClass MAK_MAKE_REF_TYPE = eINSTANCE.getMakMakeRefType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAK_MAKE_REF_TYPE__NAME = eINSTANCE.getMakMakeRefType_Name();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.RootDirectoryTypeImpl <em>Root Directory Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.RootDirectoryTypeImpl
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getRootDirectoryType()
		 * @generated
		 */
		EClass ROOT_DIRECTORY_TYPE = eINSTANCE.getRootDirectoryType();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOT_DIRECTORY_TYPE__PATH = eINSTANCE.getRootDirectoryType_Path();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType <em>Target Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getTargetType()
		 * @generated
		 */
		EEnum TARGET_TYPE = eINSTANCE.getTargetType();

		/**
		 * The meta object literal for the '<em>Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getNameType()
		 * @generated
		 */
		EDataType NAME_TYPE = eINSTANCE.getNameType();

		/**
		 * The meta object literal for the '<em>Name Type1</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getNameType1()
		 * @generated
		 */
		EDataType NAME_TYPE1 = eINSTANCE.getNameType1();

		/**
		 * The meta object literal for the '<em>Path Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getPathType()
		 * @generated
		 */
		EDataType PATH_TYPE = eINSTANCE.getPathType();

		/**
		 * The meta object literal for the '<em>Path Type1</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getPathType1()
		 * @generated
		 */
		EDataType PATH_TYPE1 = eINSTANCE.getPathType1();

		/**
		 * The meta object literal for the '<em>Platform Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getPlatformType()
		 * @generated
		 */
		EDataType PLATFORM_TYPE = eINSTANCE.getPlatformType();

		/**
		 * The meta object literal for the '<em>Project Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getProjectNameType()
		 * @generated
		 */
		EDataType PROJECT_NAME_TYPE = eINSTANCE.getProjectNameType();

		/**
		 * The meta object literal for the '<em>Sdk Id Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getSdkIdType()
		 * @generated
		 */
		EDataType SDK_ID_TYPE = eINSTANCE.getSdkIdType();

		/**
		 * The meta object literal for the '<em>Target Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType
		 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfImportTestDataPackageImpl#getTargetTypeObject()
		 * @generated
		 */
		EDataType TARGET_TYPE_OBJECT = eINSTANCE.getTargetTypeObject();

	}

} //InfImportTestDataPackage
