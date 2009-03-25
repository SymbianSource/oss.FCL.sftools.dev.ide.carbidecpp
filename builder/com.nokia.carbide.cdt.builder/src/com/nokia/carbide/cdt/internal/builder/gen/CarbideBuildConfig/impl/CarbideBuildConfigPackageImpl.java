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
package com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl;

import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigFactory;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.EnvVarsType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CarbideBuildConfigPackageImpl extends EPackageImpl implements CarbideBuildConfigPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass carbideBuilderConfigInfoTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass configurationTypeEClass = null;

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
	private EClass envVarsTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sisBuilderTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass varTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum useTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType certTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType contentSearchLocationTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType createStubFormatTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType createStubFormatTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType epocRootTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType errorParsersTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType keyTypeEDataType = null;

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
	private EDataType outputFileNameTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType passwordTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pkgFileTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType signedFileNameTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType useTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType valueTypeEDataType = null;

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
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CarbideBuildConfigPackageImpl() {
		super(eNS_URI, CarbideBuildConfigFactory.eINSTANCE);
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
	public static CarbideBuildConfigPackage init() {
		if (isInited) return (CarbideBuildConfigPackage)EPackage.Registry.INSTANCE.getEPackage(CarbideBuildConfigPackage.eNS_URI);

		// Obtain or create and register package
		CarbideBuildConfigPackageImpl theCarbideBuildConfigPackage = (CarbideBuildConfigPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof CarbideBuildConfigPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new CarbideBuildConfigPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCarbideBuildConfigPackage.createPackageContents();

		// Initialize created meta-data
		theCarbideBuildConfigPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCarbideBuildConfigPackage.freeze();

		return theCarbideBuildConfigPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCarbideBuilderConfigInfoType() {
		return carbideBuilderConfigInfoTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCarbideBuilderConfigInfoType_Configuration() {
		return (EReference)carbideBuilderConfigInfoTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCarbideBuilderConfigInfoType_Version() {
		return (EAttribute)carbideBuilderConfigInfoTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConfigurationType() {
		return configurationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConfigurationType_SisBuilder() {
		return (EReference)configurationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConfigurationType_EnvVars() {
		return (EReference)configurationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigurationType_EpocRoot() {
		return (EAttribute)configurationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigurationType_ErrorParsers() {
		return (EAttribute)configurationTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConfigurationType_Name() {
		return (EAttribute)configurationTypeEClass.getEStructuralFeatures().get(4);
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
	public EReference getDocumentRoot_CarbideBuilderConfigInfo() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Configuration() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_EnvVars() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_SisBuilder() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Var() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnvVarsType() {
		return envVarsTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnvVarsType_Var() {
		return (EReference)envVarsTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSisBuilderType() {
		return sisBuilderTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_Mixed() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_AdditionalOptions() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_Cert() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_ContentSearchLocation() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_CreateStubFormat() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_Key() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_OutputFileName() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_Password() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_PkgFile() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSisBuilderType_SignedFileName() {
		return (EAttribute)sisBuilderTypeEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVarType() {
		return varTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVarType_Name() {
		return (EAttribute)varTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVarType_Use() {
		return (EAttribute)varTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVarType_Value() {
		return (EAttribute)varTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getUseType() {
		return useTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getCertType() {
		return certTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getContentSearchLocationType() {
		return contentSearchLocationTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getCreateStubFormatType() {
		return createStubFormatTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getCreateStubFormatTypeObject() {
		return createStubFormatTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getEpocRootType() {
		return epocRootTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getErrorParsersType() {
		return errorParsersTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getKeyType() {
		return keyTypeEDataType;
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
	public EDataType getOutputFileNameType() {
		return outputFileNameTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPasswordType() {
		return passwordTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPkgFileType() {
		return pkgFileTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getSignedFileNameType() {
		return signedFileNameTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getUseTypeObject() {
		return useTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getValueType() {
		return valueTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CarbideBuildConfigFactory getCarbideBuildConfigFactory() {
		return (CarbideBuildConfigFactory)getEFactoryInstance();
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
		carbideBuilderConfigInfoTypeEClass = createEClass(CARBIDE_BUILDER_CONFIG_INFO_TYPE);
		createEReference(carbideBuilderConfigInfoTypeEClass, CARBIDE_BUILDER_CONFIG_INFO_TYPE__CONFIGURATION);
		createEAttribute(carbideBuilderConfigInfoTypeEClass, CARBIDE_BUILDER_CONFIG_INFO_TYPE__VERSION);

		configurationTypeEClass = createEClass(CONFIGURATION_TYPE);
		createEReference(configurationTypeEClass, CONFIGURATION_TYPE__SIS_BUILDER);
		createEReference(configurationTypeEClass, CONFIGURATION_TYPE__ENV_VARS);
		createEAttribute(configurationTypeEClass, CONFIGURATION_TYPE__EPOC_ROOT);
		createEAttribute(configurationTypeEClass, CONFIGURATION_TYPE__ERROR_PARSERS);
		createEAttribute(configurationTypeEClass, CONFIGURATION_TYPE__NAME);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO);
		createEReference(documentRootEClass, DOCUMENT_ROOT__CONFIGURATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ENV_VARS);
		createEReference(documentRootEClass, DOCUMENT_ROOT__SIS_BUILDER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__VAR);

		envVarsTypeEClass = createEClass(ENV_VARS_TYPE);
		createEReference(envVarsTypeEClass, ENV_VARS_TYPE__VAR);

		sisBuilderTypeEClass = createEClass(SIS_BUILDER_TYPE);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__MIXED);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__ADDITIONAL_OPTIONS);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__CERT);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__CONTENT_SEARCH_LOCATION);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__CREATE_STUB_FORMAT);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__KEY);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__OUTPUT_FILE_NAME);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__PASSWORD);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__PKG_FILE);
		createEAttribute(sisBuilderTypeEClass, SIS_BUILDER_TYPE__SIGNED_FILE_NAME);

		varTypeEClass = createEClass(VAR_TYPE);
		createEAttribute(varTypeEClass, VAR_TYPE__NAME);
		createEAttribute(varTypeEClass, VAR_TYPE__USE);
		createEAttribute(varTypeEClass, VAR_TYPE__VALUE);

		// Create enums
		useTypeEEnum = createEEnum(USE_TYPE);

		// Create data types
		certTypeEDataType = createEDataType(CERT_TYPE);
		contentSearchLocationTypeEDataType = createEDataType(CONTENT_SEARCH_LOCATION_TYPE);
		createStubFormatTypeEDataType = createEDataType(CREATE_STUB_FORMAT_TYPE);
		createStubFormatTypeObjectEDataType = createEDataType(CREATE_STUB_FORMAT_TYPE_OBJECT);
		epocRootTypeEDataType = createEDataType(EPOC_ROOT_TYPE);
		errorParsersTypeEDataType = createEDataType(ERROR_PARSERS_TYPE);
		keyTypeEDataType = createEDataType(KEY_TYPE);
		nameTypeEDataType = createEDataType(NAME_TYPE);
		nameType1EDataType = createEDataType(NAME_TYPE1);
		outputFileNameTypeEDataType = createEDataType(OUTPUT_FILE_NAME_TYPE);
		passwordTypeEDataType = createEDataType(PASSWORD_TYPE);
		pkgFileTypeEDataType = createEDataType(PKG_FILE_TYPE);
		signedFileNameTypeEDataType = createEDataType(SIGNED_FILE_NAME_TYPE);
		useTypeObjectEDataType = createEDataType(USE_TYPE_OBJECT);
		valueTypeEDataType = createEDataType(VALUE_TYPE);
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

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(carbideBuilderConfigInfoTypeEClass, CarbideBuilderConfigInfoType.class, "CarbideBuilderConfigInfoType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCarbideBuilderConfigInfoType_Configuration(), this.getConfigurationType(), null, "configuration", null, 1, -1, CarbideBuilderConfigInfoType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCarbideBuilderConfigInfoType_Version(), theXMLTypePackage.getShort(), "version", "0", 0, 1, CarbideBuilderConfigInfoType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(configurationTypeEClass, ConfigurationType.class, "ConfigurationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConfigurationType_SisBuilder(), this.getSisBuilderType(), null, "sisBuilder", null, 1, 1, ConfigurationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConfigurationType_EnvVars(), this.getEnvVarsType(), null, "envVars", null, 0, 1, ConfigurationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfigurationType_EpocRoot(), this.getEpocRootType(), "epocRoot", null, 0, 1, ConfigurationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfigurationType_ErrorParsers(), this.getErrorParsersType(), "errorParsers", null, 1, 1, ConfigurationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConfigurationType_Name(), this.getNameType(), "name", null, 1, 1, ConfigurationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_CarbideBuilderConfigInfo(), this.getCarbideBuilderConfigInfoType(), null, "carbideBuilderConfigInfo", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Configuration(), this.getConfigurationType(), null, "configuration", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_EnvVars(), this.getEnvVarsType(), null, "envVars", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_SisBuilder(), this.getSisBuilderType(), null, "sisBuilder", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Var(), this.getVarType(), null, "var", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(envVarsTypeEClass, EnvVarsType.class, "EnvVarsType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnvVarsType_Var(), this.getVarType(), null, "var", null, 1, -1, EnvVarsType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sisBuilderTypeEClass, SisBuilderType.class, "SisBuilderType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSisBuilderType_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSisBuilderType_AdditionalOptions(), theXMLTypePackage.getString(), "additionalOptions", null, 0, 1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSisBuilderType_Cert(), this.getCertType(), "cert", null, 0, 1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSisBuilderType_ContentSearchLocation(), this.getContentSearchLocationType(), "contentSearchLocation", null, 0, 1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSisBuilderType_CreateStubFormat(), this.getCreateStubFormatType(), "createStubFormat", null, 0, 1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSisBuilderType_Key(), this.getKeyType(), "key", null, 0, 1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSisBuilderType_OutputFileName(), this.getOutputFileNameType(), "outputFileName", null, 0, 1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSisBuilderType_Password(), this.getPasswordType(), "password", null, 0, 1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSisBuilderType_PkgFile(), this.getPkgFileType(), "pkgFile", null, 1, 1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSisBuilderType_SignedFileName(), this.getSignedFileNameType(), "signedFileName", null, 0, 1, SisBuilderType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(varTypeEClass, VarType.class, "VarType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVarType_Name(), this.getNameType1(), "name", null, 1, 1, VarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVarType_Use(), this.getUseType(), "use", "prepend", 1, 1, VarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVarType_Value(), this.getValueType(), "value", null, 1, 1, VarType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(useTypeEEnum, UseType.class, "UseType");
		addEEnumLiteral(useTypeEEnum, UseType.PREPEND_LITERAL);
		addEEnumLiteral(useTypeEEnum, UseType.REPLACE_LITERAL);
		addEEnumLiteral(useTypeEEnum, UseType.APPEND_LITERAL);
		addEEnumLiteral(useTypeEEnum, UseType.UNDEFINE_LITERAL);

		// Initialize data types
		initEDataType(certTypeEDataType, String.class, "CertType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(contentSearchLocationTypeEDataType, String.class, "ContentSearchLocationType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(createStubFormatTypeEDataType, boolean.class, "CreateStubFormatType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(createStubFormatTypeObjectEDataType, Boolean.class, "CreateStubFormatTypeObject", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(epocRootTypeEDataType, String.class, "EpocRootType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(errorParsersTypeEDataType, String.class, "ErrorParsersType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(keyTypeEDataType, String.class, "KeyType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameTypeEDataType, String.class, "NameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(nameType1EDataType, String.class, "NameType1", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(outputFileNameTypeEDataType, String.class, "OutputFileNameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(passwordTypeEDataType, String.class, "PasswordType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(pkgFileTypeEDataType, String.class, "PkgFileType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(signedFileNameTypeEDataType, String.class, "SignedFileNameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(useTypeObjectEDataType, UseType.class, "UseTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(valueTypeEDataType, String.class, "ValueType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

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
		  (carbideBuilderConfigInfoTypeEClass, 
		   source, 
		   new String[] {
			 "name", "CarbideBuilderConfigInfo_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getCarbideBuilderConfigInfoType_Configuration(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "configuration",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getCarbideBuilderConfigInfoType_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "version",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (certTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "cert_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (configurationTypeEClass, 
		   source, 
		   new String[] {
			 "name", "configuration_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getConfigurationType_SisBuilder(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sisBuilder",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfigurationType_EnvVars(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "envVars",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfigurationType_EpocRoot(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "epocRoot",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfigurationType_ErrorParsers(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "errorParsers",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getConfigurationType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (contentSearchLocationTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "contentSearchLocation_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (createStubFormatTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "createStubFormat_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#boolean"
		   });		
		addAnnotation
		  (createStubFormatTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "createStubFormat_._type:Object",
			 "baseType", "createStubFormat_._type"
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
		  (getDocumentRoot_CarbideBuilderConfigInfo(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "CarbideBuilderConfigInfo",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Configuration(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "configuration",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_EnvVars(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "envVars",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_SisBuilder(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "sisBuilder",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Var(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "var",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (envVarsTypeEClass, 
		   source, 
		   new String[] {
			 "name", "envVars_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getEnvVarsType_Var(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "var",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (epocRootTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "epocRoot_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (errorParsersTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "errorParsers_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (keyTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "key_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
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
		  (outputFileNameTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "outputFileName_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (passwordTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "password_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (pkgFileTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "pkgFile_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (signedFileNameTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "signedFileName_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (sisBuilderTypeEClass, 
		   source, 
		   new String[] {
			 "name", "sisBuilder_._type",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getSisBuilderType_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getSisBuilderType_AdditionalOptions(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "additionalOptions",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSisBuilderType_Cert(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "cert",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSisBuilderType_ContentSearchLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "contentSearchLocation",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSisBuilderType_CreateStubFormat(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "createStubFormat",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSisBuilderType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSisBuilderType_OutputFileName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "outputFileName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSisBuilderType_Password(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "password",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSisBuilderType_PkgFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "pkgFile",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getSisBuilderType_SignedFileName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "signedFileName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (useTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "use_._type"
		   });		
		addAnnotation
		  (useTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "use_._type:Object",
			 "baseType", "use_._type"
		   });		
		addAnnotation
		  (valueTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "value_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (varTypeEClass, 
		   source, 
		   new String[] {
			 "name", "var_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getVarType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getVarType_Use(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "use",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getVarType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value",
			 "namespace", "##targetNamespace"
		   });
	}

} //CarbideBuildConfigPackageImpl
