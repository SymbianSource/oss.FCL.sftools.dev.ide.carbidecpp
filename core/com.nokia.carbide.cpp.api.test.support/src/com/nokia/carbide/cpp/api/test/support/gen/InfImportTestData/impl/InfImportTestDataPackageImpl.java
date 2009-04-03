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

import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFileType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BuildConfigurationsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataFactory;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.RootDirectoryType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.TargetType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfImportTestDataPackageImpl extends EPackageImpl implements InfImportTestDataPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bldInfFilesTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bldInfFileTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bldInfImportDataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass buildConfigTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass buildConfigurationsTypeEClass = null;

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
	private EClass infComponentsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass infComponentTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass makMakeRefsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass makMakeRefTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rootDirectoryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum targetTypeEEnum = null;

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
	private EDataType pathTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pathType1EDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType platformTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType projectNameTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType sdkIdTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType targetTypeObjectEDataType = null;

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
	 * @see com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private InfImportTestDataPackageImpl() {
		super(eNS_URI, InfImportTestDataFactory.eINSTANCE);
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
	public static InfImportTestDataPackage init() {
		if (isInited) return (InfImportTestDataPackage)EPackage.Registry.INSTANCE.getEPackage(InfImportTestDataPackage.eNS_URI);

		// Obtain or create and register package
		InfImportTestDataPackageImpl theInfImportTestDataPackage = (InfImportTestDataPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof InfImportTestDataPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new InfImportTestDataPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theInfImportTestDataPackage.createPackageContents();

		// Initialize created meta-data
		theInfImportTestDataPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theInfImportTestDataPackage.freeze();

		return theInfImportTestDataPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBldInfFilesType() {
		return bldInfFilesTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBldInfFilesType_BldInfFile() {
		return (EReference)bldInfFilesTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBldInfFileType() {
		return bldInfFileTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBldInfFileType_RootDirectory() {
		return (EReference)bldInfFileTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBldInfFileType_BuildConfigurations() {
		return (EReference)bldInfFileTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBldInfFileType_InfComponents() {
		return (EReference)bldInfFileTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBldInfFileType_MakMakeRefs() {
		return (EReference)bldInfFileTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBldInfFileType_Path() {
		return (EAttribute)bldInfFileTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBldInfFileType_ProjectName() {
		return (EAttribute)bldInfFileTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBldInfFileType_SdkId() {
		return (EAttribute)bldInfFileTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBldInfImportDataType() {
		return bldInfImportDataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBldInfImportDataType_BldInfFiles() {
		return (EReference)bldInfImportDataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBuildConfigType() {
		return buildConfigTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBuildConfigType_Platform() {
		return (EAttribute)buildConfigTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBuildConfigType_Target() {
		return (EAttribute)buildConfigTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBuildConfigurationsType() {
		return buildConfigurationsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBuildConfigurationsType_BuildConfig() {
		return (EReference)buildConfigurationsTypeEClass.getEStructuralFeatures().get(0);
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
	public EReference getDocumentRoot_BldInfFile() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BldInfFiles() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BldInfImportData() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BuildConfig() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BuildConfigurations() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_InfComponent() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_InfComponents() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MakMakeRef() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MakMakeRefs() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_RootDirectory() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInfComponentsType() {
		return infComponentsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInfComponentsType_InfComponent() {
		return (EReference)infComponentsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInfComponentType() {
		return infComponentTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInfComponentType_Name() {
		return (EAttribute)infComponentTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMakMakeRefsType() {
		return makMakeRefsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMakMakeRefsType_MakMakeRef() {
		return (EReference)makMakeRefsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMakMakeRefType() {
		return makMakeRefTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMakMakeRefType_Name() {
		return (EAttribute)makMakeRefTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRootDirectoryType() {
		return rootDirectoryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRootDirectoryType_Path() {
		return (EAttribute)rootDirectoryTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTargetType() {
		return targetTypeEEnum;
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
	public EDataType getPathType() {
		return pathTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPathType1() {
		return pathType1EDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPlatformType() {
		return platformTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getProjectNameType() {
		return projectNameTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSdkIdType() {
		return sdkIdTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getTargetTypeObject() {
		return targetTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfImportTestDataFactory getInfImportTestDataFactory() {
		return (InfImportTestDataFactory)getEFactoryInstance();
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
		bldInfFilesTypeEClass = createEClass(BLD_INF_FILES_TYPE);
		createEReference(bldInfFilesTypeEClass, BLD_INF_FILES_TYPE__BLD_INF_FILE);

		bldInfFileTypeEClass = createEClass(BLD_INF_FILE_TYPE);
		createEReference(bldInfFileTypeEClass, BLD_INF_FILE_TYPE__ROOT_DIRECTORY);
		createEReference(bldInfFileTypeEClass, BLD_INF_FILE_TYPE__BUILD_CONFIGURATIONS);
		createEReference(bldInfFileTypeEClass, BLD_INF_FILE_TYPE__INF_COMPONENTS);
		createEReference(bldInfFileTypeEClass, BLD_INF_FILE_TYPE__MAK_MAKE_REFS);
		createEAttribute(bldInfFileTypeEClass, BLD_INF_FILE_TYPE__PATH);
		createEAttribute(bldInfFileTypeEClass, BLD_INF_FILE_TYPE__PROJECT_NAME);
		createEAttribute(bldInfFileTypeEClass, BLD_INF_FILE_TYPE__SDK_ID);

		bldInfImportDataTypeEClass = createEClass(BLD_INF_IMPORT_DATA_TYPE);
		createEReference(bldInfImportDataTypeEClass, BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES);

		buildConfigTypeEClass = createEClass(BUILD_CONFIG_TYPE);
		createEAttribute(buildConfigTypeEClass, BUILD_CONFIG_TYPE__PLATFORM);
		createEAttribute(buildConfigTypeEClass, BUILD_CONFIG_TYPE__TARGET);

		buildConfigurationsTypeEClass = createEClass(BUILD_CONFIGURATIONS_TYPE);
		createEReference(buildConfigurationsTypeEClass, BUILD_CONFIGURATIONS_TYPE__BUILD_CONFIG);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BLD_INF_FILE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BLD_INF_FILES);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BLD_INF_IMPORT_DATA);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BUILD_CONFIG);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BUILD_CONFIGURATIONS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__INF_COMPONENT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__INF_COMPONENTS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAK_MAKE_REF);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MAK_MAKE_REFS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ROOT_DIRECTORY);

		infComponentsTypeEClass = createEClass(INF_COMPONENTS_TYPE);
		createEReference(infComponentsTypeEClass, INF_COMPONENTS_TYPE__INF_COMPONENT);

		infComponentTypeEClass = createEClass(INF_COMPONENT_TYPE);
		createEAttribute(infComponentTypeEClass, INF_COMPONENT_TYPE__NAME);

		makMakeRefsTypeEClass = createEClass(MAK_MAKE_REFS_TYPE);
		createEReference(makMakeRefsTypeEClass, MAK_MAKE_REFS_TYPE__MAK_MAKE_REF);

		makMakeRefTypeEClass = createEClass(MAK_MAKE_REF_TYPE);
		createEAttribute(makMakeRefTypeEClass, MAK_MAKE_REF_TYPE__NAME);

		rootDirectoryTypeEClass = createEClass(ROOT_DIRECTORY_TYPE);
		createEAttribute(rootDirectoryTypeEClass, ROOT_DIRECTORY_TYPE__PATH);

		// Create enums
		targetTypeEEnum = createEEnum(TARGET_TYPE);

		// Create data types
		nameTypeEDataType = createEDataType(NAME_TYPE);
		nameType1EDataType = createEDataType(NAME_TYPE1);
		pathTypeEDataType = createEDataType(PATH_TYPE);
		pathType1EDataType = createEDataType(PATH_TYPE1);
		platformTypeEDataType = createEDataType(PLATFORM_TYPE);
		projectNameTypeEDataType = createEDataType(PROJECT_NAME_TYPE);
		sdkIdTypeEDataType = createEDataType(SDK_ID_TYPE);
		targetTypeObjectEDataType = createEDataType(TARGET_TYPE_OBJECT);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(bldInfFilesTypeEClass, BldInfFilesType.class, "BldInfFilesType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBldInfFilesType_BldInfFile(), this.getBldInfFileType(), null, "bldInfFile", null, 1, -1, BldInfFilesType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bldInfFileTypeEClass, BldInfFileType.class, "BldInfFileType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBldInfFileType_RootDirectory(), this.getRootDirectoryType(), null, "rootDirectory", null, 0, 1, BldInfFileType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBldInfFileType_BuildConfigurations(), this.getBuildConfigurationsType(), null, "buildConfigurations", null, 1, 1, BldInfFileType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBldInfFileType_InfComponents(), this.getInfComponentsType(), null, "infComponents", null, 0, 1, BldInfFileType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBldInfFileType_MakMakeRefs(), this.getMakMakeRefsType(), null, "makMakeRefs", null, 0, 1, BldInfFileType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBldInfFileType_Path(), this.getPathType(), "path", null, 1, 1, BldInfFileType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBldInfFileType_ProjectName(), this.getProjectNameType(), "projectName", null, 1, 1, BldInfFileType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBldInfFileType_SdkId(), this.getSdkIdType(), "sdkId", null, 1, 1, BldInfFileType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bldInfImportDataTypeEClass, BldInfImportDataType.class, "BldInfImportDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBldInfImportDataType_BldInfFiles(), this.getBldInfFilesType(), null, "bldInfFiles", null, 1, 1, BldInfImportDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(buildConfigTypeEClass, BuildConfigType.class, "BuildConfigType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBuildConfigType_Platform(), this.getPlatformType(), "platform", null, 1, 1, BuildConfigType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBuildConfigType_Target(), this.getTargetType(), "target", null, 1, 1, BuildConfigType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(buildConfigurationsTypeEClass, BuildConfigurationsType.class, "BuildConfigurationsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBuildConfigurationsType_BuildConfig(), this.getBuildConfigType(), null, "buildConfig", null, 1, -1, BuildConfigurationsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BldInfFile(), this.getBldInfFileType(), null, "bldInfFile", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BldInfFiles(), this.getBldInfFilesType(), null, "bldInfFiles", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BldInfImportData(), this.getBldInfImportDataType(), null, "bldInfImportData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BuildConfig(), this.getBuildConfigType(), null, "buildConfig", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BuildConfigurations(), this.getBuildConfigurationsType(), null, "buildConfigurations", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_InfComponent(), this.getInfComponentType(), null, "infComponent", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_InfComponents(), this.getInfComponentsType(), null, "infComponents", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MakMakeRef(), this.getMakMakeRefType(), null, "makMakeRef", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MakMakeRefs(), this.getMakMakeRefsType(), null, "makMakeRefs", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_RootDirectory(), this.getRootDirectoryType(), null, "rootDirectory", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(infComponentsTypeEClass, InfComponentsType.class, "InfComponentsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInfComponentsType_InfComponent(), this.getInfComponentType(), null, "infComponent", null, 1, -1, InfComponentsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(infComponentTypeEClass, InfComponentType.class, "InfComponentType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInfComponentType_Name(), this.getNameType1(), "name", null, 1, 1, InfComponentType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(makMakeRefsTypeEClass, MakMakeRefsType.class, "MakMakeRefsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMakMakeRefsType_MakMakeRef(), this.getMakMakeRefType(), null, "makMakeRef", null, 1, -1, MakMakeRefsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(makMakeRefTypeEClass, MakMakeRefType.class, "MakMakeRefType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMakMakeRefType_Name(), this.getNameType(), "name", null, 1, 1, MakMakeRefType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rootDirectoryTypeEClass, RootDirectoryType.class, "RootDirectoryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRootDirectoryType_Path(), this.getPathType1(), "path", null, 1, 1, RootDirectoryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(targetTypeEEnum, TargetType.class, "TargetType");
		addEEnumLiteral(targetTypeEEnum, TargetType.UDEB);
		addEEnumLiteral(targetTypeEEnum, TargetType.UREL);

		// Initialize data types
		initEDataType(nameTypeEDataType, String.class, "NameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameType1EDataType, String.class, "NameType1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pathTypeEDataType, String.class, "PathType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pathType1EDataType, String.class, "PathType1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(platformTypeEDataType, String.class, "PlatformType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(projectNameTypeEDataType, String.class, "ProjectNameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(sdkIdTypeEDataType, String.class, "SdkIdType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(targetTypeObjectEDataType, TargetType.class, "TargetTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

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
		  (bldInfFilesTypeEClass, 
		   source, 
		   new String[] {
			 "name", "bldInfFiles_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getBldInfFilesType_BldInfFile(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "bldInfFile",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (bldInfFileTypeEClass, 
		   source, 
		   new String[] {
			 "name", "bldInfFile_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getBldInfFileType_RootDirectory(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "rootDirectory",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBldInfFileType_BuildConfigurations(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "buildConfigurations",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBldInfFileType_InfComponents(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "infComponents",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBldInfFileType_MakMakeRefs(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "makMakeRefs",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBldInfFileType_Path(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "path",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBldInfFileType_ProjectName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "projectName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBldInfFileType_SdkId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "sdkId",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (bldInfImportDataTypeEClass, 
		   source, 
		   new String[] {
			 "name", "BldInfImportData_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getBldInfImportDataType_BldInfFiles(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "bldInfFiles",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (buildConfigTypeEClass, 
		   source, 
		   new String[] {
			 "name", "buildConfig_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getBuildConfigType_Platform(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "platform",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getBuildConfigType_Target(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "target",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (buildConfigurationsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "buildConfigurations_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getBuildConfigurationsType_BuildConfig(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "buildConfig",
			 "namespace", "##targetNamespace"
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
		  (getDocumentRoot_BldInfFile(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "bldInfFile",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_BldInfFiles(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "bldInfFiles",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_BldInfImportData(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BldInfImportData",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_BuildConfig(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "buildConfig",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_BuildConfigurations(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "buildConfigurations",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_InfComponent(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "infComponent",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_InfComponents(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "infComponents",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_MakMakeRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "makMakeRef",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_MakMakeRefs(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "makMakeRefs",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_RootDirectory(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "rootDirectory",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (infComponentsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "infComponents_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getInfComponentsType_InfComponent(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "infComponent",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (infComponentTypeEClass, 
		   source, 
		   new String[] {
			 "name", "infComponent_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getInfComponentType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (makMakeRefsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "makMakeRefs_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getMakMakeRefsType_MakMakeRef(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "makMakeRef",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (makMakeRefTypeEClass, 
		   source, 
		   new String[] {
			 "name", "makMakeRef_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getMakMakeRefType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (nameTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "name_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (nameType1EDataType, 
		   source, 
		   new String[] {
			 "name", "name_._1_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (pathTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "path_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#anyURI"
		   });		
		addAnnotation
		  (pathType1EDataType, 
		   source, 
		   new String[] {
			 "name", "path_._1_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#anyURI"
		   });		
		addAnnotation
		  (platformTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "platform_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (projectNameTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "projectName_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (rootDirectoryTypeEClass, 
		   source, 
		   new String[] {
			 "name", "rootDirectory_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getRootDirectoryType_Path(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "path",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (sdkIdTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "sdkId_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (targetTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "target_._type"
		   });		
		addAnnotation
		  (targetTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "target_._type:Object",
			 "baseType", "target_._type"
		   });
	}

} //InfImportTestDataPackageImpl
