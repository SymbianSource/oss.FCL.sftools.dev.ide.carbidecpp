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
package com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig;

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
 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface CarbideBuildConfigPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "CarbideBuildConfig";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/com.nokia.carbide.cdt.builder/schema/carbideBuildConfig.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "CarbideBuildConfig";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CarbideBuildConfigPackage eINSTANCE = com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuilderConfigInfoTypeImpl <em>Carbide Builder Config Info Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuilderConfigInfoTypeImpl
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getCarbideBuilderConfigInfoType()
	 * @generated
	 */
	int CARBIDE_BUILDER_CONFIG_INFO_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Configuration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARBIDE_BUILDER_CONFIG_INFO_TYPE__CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARBIDE_BUILDER_CONFIG_INFO_TYPE__VERSION = 1;

	/**
	 * The number of structural features of the '<em>Carbide Builder Config Info Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARBIDE_BUILDER_CONFIG_INFO_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.ConfigurationTypeImpl <em>Configuration Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.ConfigurationTypeImpl
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getConfigurationType()
	 * @generated
	 */
	int CONFIGURATION_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Sis Builder</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_TYPE__SIS_BUILDER = 0;

	/**
	 * The feature id for the '<em><b>Env Vars</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_TYPE__ENV_VARS = 1;

	/**
	 * The feature id for the '<em><b>Epoc Root</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_TYPE__EPOC_ROOT = 2;

	/**
	 * The feature id for the '<em><b>Error Parsers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_TYPE__ERROR_PARSERS = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_TYPE__NAME = 4;

	/**
	 * The number of structural features of the '<em>Configuration Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 2;

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
	 * The feature id for the '<em><b>Carbide Builder Config Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO = 3;

	/**
	 * The feature id for the '<em><b>Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONFIGURATION = 4;

	/**
	 * The feature id for the '<em><b>Env Vars</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ENV_VARS = 5;

	/**
	 * The feature id for the '<em><b>Sis Builder</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SIS_BUILDER = 6;

	/**
	 * The feature id for the '<em><b>Var</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__VAR = 7;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.EnvVarsTypeImpl <em>Env Vars Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.EnvVarsTypeImpl
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getEnvVarsType()
	 * @generated
	 */
	int ENV_VARS_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Var</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENV_VARS_TYPE__VAR = 0;

	/**
	 * The number of structural features of the '<em>Env Vars Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENV_VARS_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl <em>Sis Builder Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getSisBuilderType()
	 * @generated
	 */
	int SIS_BUILDER_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__MIXED = 0;

	/**
	 * The feature id for the '<em><b>Additional Options</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__ADDITIONAL_OPTIONS = 1;

	/**
	 * The feature id for the '<em><b>Cert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__CERT = 2;

	/**
	 * The feature id for the '<em><b>Content Search Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__CONTENT_SEARCH_LOCATION = 3;

	/**
	 * The feature id for the '<em><b>Create Stub Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__CREATE_STUB_FORMAT = 4;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__KEY = 5;

	/**
	 * The feature id for the '<em><b>Output File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__OUTPUT_FILE_NAME = 6;

	/**
	 * The feature id for the '<em><b>Password</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__PASSWORD = 7;

	/**
	 * The feature id for the '<em><b>Pkg File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__PKG_FILE = 8;

	/**
	 * The feature id for the '<em><b>Signed File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE__SIGNED_FILE_NAME = 9;

	/**
	 * The number of structural features of the '<em>Sis Builder Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIS_BUILDER_TYPE_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.VarTypeImpl <em>Var Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.VarTypeImpl
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getVarType()
	 * @generated
	 */
	int VAR_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Use</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_TYPE__USE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_TYPE__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Var Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType <em>Use Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getUseType()
	 * @generated
	 */
	int USE_TYPE = 6;

	/**
	 * The meta object id for the '<em>Cert Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getCertType()
	 * @generated
	 */
	int CERT_TYPE = 7;

	/**
	 * The meta object id for the '<em>Content Search Location Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getContentSearchLocationType()
	 * @generated
	 */
	int CONTENT_SEARCH_LOCATION_TYPE = 8;

	/**
	 * The meta object id for the '<em>Create Stub Format Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getCreateStubFormatType()
	 * @generated
	 */
	int CREATE_STUB_FORMAT_TYPE = 9;

	/**
	 * The meta object id for the '<em>Create Stub Format Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Boolean
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getCreateStubFormatTypeObject()
	 * @generated
	 */
	int CREATE_STUB_FORMAT_TYPE_OBJECT = 10;

	/**
	 * The meta object id for the '<em>Epoc Root Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getEpocRootType()
	 * @generated
	 */
	int EPOC_ROOT_TYPE = 11;

	/**
	 * The meta object id for the '<em>Error Parsers Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getErrorParsersType()
	 * @generated
	 */
	int ERROR_PARSERS_TYPE = 12;

	/**
	 * The meta object id for the '<em>Key Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getKeyType()
	 * @generated
	 */
	int KEY_TYPE = 13;

	/**
	 * The meta object id for the '<em>Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getNameType()
	 * @generated
	 */
	int NAME_TYPE = 14;

	/**
	 * The meta object id for the '<em>Name Type1</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getNameType1()
	 * @generated
	 */
	int NAME_TYPE1 = 15;

	/**
	 * The meta object id for the '<em>Output File Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getOutputFileNameType()
	 * @generated
	 */
	int OUTPUT_FILE_NAME_TYPE = 16;

	/**
	 * The meta object id for the '<em>Password Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getPasswordType()
	 * @generated
	 */
	int PASSWORD_TYPE = 17;

	/**
	 * The meta object id for the '<em>Pkg File Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getPkgFileType()
	 * @generated
	 */
	int PKG_FILE_TYPE = 18;

	/**
	 * The meta object id for the '<em>Signed File Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getSignedFileNameType()
	 * @generated
	 */
	int SIGNED_FILE_NAME_TYPE = 19;

	/**
	 * The meta object id for the '<em>Use Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getUseTypeObject()
	 * @generated
	 */
	int USE_TYPE_OBJECT = 20;

	/**
	 * The meta object id for the '<em>Value Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getValueType()
	 * @generated
	 */
	int VALUE_TYPE = 21;


	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType <em>Carbide Builder Config Info Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Carbide Builder Config Info Type</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType
	 * @generated
	 */
	EClass getCarbideBuilderConfigInfoType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType#getConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configuration</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType#getConfiguration()
	 * @see #getCarbideBuilderConfigInfoType()
	 * @generated
	 */
	EReference getCarbideBuilderConfigInfoType_Configuration();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuilderConfigInfoType#getVersion()
	 * @see #getCarbideBuilderConfigInfoType()
	 * @generated
	 */
	EAttribute getCarbideBuilderConfigInfoType_Version();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType <em>Configuration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration Type</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType
	 * @generated
	 */
	EClass getConfigurationType();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getSisBuilder <em>Sis Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sis Builder</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getSisBuilder()
	 * @see #getConfigurationType()
	 * @generated
	 */
	EReference getConfigurationType_SisBuilder();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getEnvVars <em>Env Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Env Vars</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getEnvVars()
	 * @see #getConfigurationType()
	 * @generated
	 */
	EReference getConfigurationType_EnvVars();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getEpocRoot <em>Epoc Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Epoc Root</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getEpocRoot()
	 * @see #getConfigurationType()
	 * @generated
	 */
	EAttribute getConfigurationType_EpocRoot();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getErrorParsers <em>Error Parsers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Error Parsers</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getErrorParsers()
	 * @see #getConfigurationType()
	 * @generated
	 */
	EAttribute getConfigurationType_ErrorParsers();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType#getName()
	 * @see #getConfigurationType()
	 * @generated
	 */
	EAttribute getConfigurationType_Name();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getCarbideBuilderConfigInfo <em>Carbide Builder Config Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Carbide Builder Config Info</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getCarbideBuilderConfigInfo()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_CarbideBuilderConfigInfo();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Configuration</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getConfiguration()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Configuration();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getEnvVars <em>Env Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Env Vars</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getEnvVars()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_EnvVars();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getSisBuilder <em>Sis Builder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sis Builder</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getSisBuilder()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_SisBuilder();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getVar <em>Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Var</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.DocumentRoot#getVar()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Var();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.EnvVarsType <em>Env Vars Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Env Vars Type</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.EnvVarsType
	 * @generated
	 */
	EClass getEnvVarsType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.EnvVarsType#getVar <em>Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.EnvVarsType#getVar()
	 * @see #getEnvVarsType()
	 * @generated
	 */
	EReference getEnvVarsType_Var();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType <em>Sis Builder Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sis Builder Type</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType
	 * @generated
	 */
	EClass getSisBuilderType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getMixed()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_Mixed();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getAdditionalOptions <em>Additional Options</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Additional Options</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getAdditionalOptions()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_AdditionalOptions();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getCert <em>Cert</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cert</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getCert()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_Cert();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getContentSearchLocation <em>Content Search Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content Search Location</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getContentSearchLocation()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_ContentSearchLocation();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#isCreateStubFormat <em>Create Stub Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Create Stub Format</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#isCreateStubFormat()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_CreateStubFormat();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getKey()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_Key();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getOutputFileName <em>Output File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Output File Name</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getOutputFileName()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_OutputFileName();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getPassword <em>Password</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Password</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getPassword()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_Password();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getPkgFile <em>Pkg File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pkg File</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getPkgFile()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_PkgFile();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getSignedFileName <em>Signed File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Signed File Name</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType#getSignedFileName()
	 * @see #getSisBuilderType()
	 * @generated
	 */
	EAttribute getSisBuilderType_SignedFileName();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType <em>Var Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Var Type</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType
	 * @generated
	 */
	EClass getVarType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getName()
	 * @see #getVarType()
	 * @generated
	 */
	EAttribute getVarType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getUse <em>Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getUse()
	 * @see #getVarType()
	 * @generated
	 */
	EAttribute getVarType_Use();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.VarType#getValue()
	 * @see #getVarType()
	 * @generated
	 */
	EAttribute getVarType_Value();

	/**
	 * Returns the meta object for enum '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType <em>Use Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Use Type</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType
	 * @generated
	 */
	EEnum getUseType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Cert Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Cert Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='cert_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getCertType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Content Search Location Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Content Search Location Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='contentSearchLocation_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getContentSearchLocationType();

	/**
	 * Returns the meta object for data type '<em>Create Stub Format Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Create Stub Format Type</em>'.
	 * @model instanceClass="boolean"
	 *        extendedMetaData="name='createStubFormat_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#boolean'" 
	 * @generated
	 */
	EDataType getCreateStubFormatType();

	/**
	 * Returns the meta object for data type '{@link java.lang.Boolean <em>Create Stub Format Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Create Stub Format Type Object</em>'.
	 * @see java.lang.Boolean
	 * @model instanceClass="java.lang.Boolean"
	 *        extendedMetaData="name='createStubFormat_._type:Object' baseType='createStubFormat_._type'" 
	 * @generated
	 */
	EDataType getCreateStubFormatTypeObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Epoc Root Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Epoc Root Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='epocRoot_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getEpocRootType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Error Parsers Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Error Parsers Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='errorParsers_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getErrorParsersType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Key Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Key Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='key_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getKeyType();

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
	 * Returns the meta object for data type '{@link java.lang.String <em>Output File Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Output File Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='outputFileName_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getOutputFileNameType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Password Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Password Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='password_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getPasswordType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Pkg File Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Pkg File Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='pkgFile_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getPkgFileType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Signed File Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Signed File Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='signedFileName_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getSignedFileNameType();

	/**
	 * Returns the meta object for data type '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType <em>Use Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Use Type Object</em>'.
	 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType
	 * @model instanceClass="com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType"
	 *        extendedMetaData="name='use_._type:Object' baseType='use_._type'" 
	 * @generated
	 */
	EDataType getUseTypeObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Value Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='value_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getValueType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CarbideBuildConfigFactory getCarbideBuildConfigFactory();

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
	interface Literals  {
		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuilderConfigInfoTypeImpl <em>Carbide Builder Config Info Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuilderConfigInfoTypeImpl
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getCarbideBuilderConfigInfoType()
		 * @generated
		 */
		EClass CARBIDE_BUILDER_CONFIG_INFO_TYPE = eINSTANCE.getCarbideBuilderConfigInfoType();

		/**
		 * The meta object literal for the '<em><b>Configuration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARBIDE_BUILDER_CONFIG_INFO_TYPE__CONFIGURATION = eINSTANCE.getCarbideBuilderConfigInfoType_Configuration();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARBIDE_BUILDER_CONFIG_INFO_TYPE__VERSION = eINSTANCE.getCarbideBuilderConfigInfoType_Version();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.ConfigurationTypeImpl <em>Configuration Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.ConfigurationTypeImpl
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getConfigurationType()
		 * @generated
		 */
		EClass CONFIGURATION_TYPE = eINSTANCE.getConfigurationType();

		/**
		 * The meta object literal for the '<em><b>Sis Builder</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION_TYPE__SIS_BUILDER = eINSTANCE.getConfigurationType_SisBuilder();

		/**
		 * The meta object literal for the '<em><b>Env Vars</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION_TYPE__ENV_VARS = eINSTANCE.getConfigurationType_EnvVars();

		/**
		 * The meta object literal for the '<em><b>Epoc Root</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION_TYPE__EPOC_ROOT = eINSTANCE.getConfigurationType_EpocRoot();

		/**
		 * The meta object literal for the '<em><b>Error Parsers</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION_TYPE__ERROR_PARSERS = eINSTANCE.getConfigurationType_ErrorParsers();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIGURATION_TYPE__NAME = eINSTANCE.getConfigurationType_Name();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.DocumentRootImpl
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Carbide Builder Config Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CARBIDE_BUILDER_CONFIG_INFO = eINSTANCE.getDocumentRoot_CarbideBuilderConfigInfo();

		/**
		 * The meta object literal for the '<em><b>Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__CONFIGURATION = eINSTANCE.getDocumentRoot_Configuration();

		/**
		 * The meta object literal for the '<em><b>Env Vars</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ENV_VARS = eINSTANCE.getDocumentRoot_EnvVars();

		/**
		 * The meta object literal for the '<em><b>Sis Builder</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__SIS_BUILDER = eINSTANCE.getDocumentRoot_SisBuilder();

		/**
		 * The meta object literal for the '<em><b>Var</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__VAR = eINSTANCE.getDocumentRoot_Var();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.EnvVarsTypeImpl <em>Env Vars Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.EnvVarsTypeImpl
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getEnvVarsType()
		 * @generated
		 */
		EClass ENV_VARS_TYPE = eINSTANCE.getEnvVarsType();

		/**
		 * The meta object literal for the '<em><b>Var</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENV_VARS_TYPE__VAR = eINSTANCE.getEnvVarsType_Var();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl <em>Sis Builder Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.SisBuilderTypeImpl
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getSisBuilderType()
		 * @generated
		 */
		EClass SIS_BUILDER_TYPE = eINSTANCE.getSisBuilderType();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__MIXED = eINSTANCE.getSisBuilderType_Mixed();

		/**
		 * The meta object literal for the '<em><b>Additional Options</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__ADDITIONAL_OPTIONS = eINSTANCE.getSisBuilderType_AdditionalOptions();

		/**
		 * The meta object literal for the '<em><b>Cert</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__CERT = eINSTANCE.getSisBuilderType_Cert();

		/**
		 * The meta object literal for the '<em><b>Content Search Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__CONTENT_SEARCH_LOCATION = eINSTANCE.getSisBuilderType_ContentSearchLocation();

		/**
		 * The meta object literal for the '<em><b>Create Stub Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__CREATE_STUB_FORMAT = eINSTANCE.getSisBuilderType_CreateStubFormat();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__KEY = eINSTANCE.getSisBuilderType_Key();

		/**
		 * The meta object literal for the '<em><b>Output File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__OUTPUT_FILE_NAME = eINSTANCE.getSisBuilderType_OutputFileName();

		/**
		 * The meta object literal for the '<em><b>Password</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__PASSWORD = eINSTANCE.getSisBuilderType_Password();

		/**
		 * The meta object literal for the '<em><b>Pkg File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__PKG_FILE = eINSTANCE.getSisBuilderType_PkgFile();

		/**
		 * The meta object literal for the '<em><b>Signed File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIS_BUILDER_TYPE__SIGNED_FILE_NAME = eINSTANCE.getSisBuilderType_SignedFileName();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.VarTypeImpl <em>Var Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.VarTypeImpl
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getVarType()
		 * @generated
		 */
		EClass VAR_TYPE = eINSTANCE.getVarType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR_TYPE__NAME = eINSTANCE.getVarType_Name();

		/**
		 * The meta object literal for the '<em><b>Use</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR_TYPE__USE = eINSTANCE.getVarType_Use();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR_TYPE__VALUE = eINSTANCE.getVarType_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType <em>Use Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getUseType()
		 * @generated
		 */
		EEnum USE_TYPE = eINSTANCE.getUseType();

		/**
		 * The meta object literal for the '<em>Cert Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getCertType()
		 * @generated
		 */
		EDataType CERT_TYPE = eINSTANCE.getCertType();

		/**
		 * The meta object literal for the '<em>Content Search Location Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getContentSearchLocationType()
		 * @generated
		 */
		EDataType CONTENT_SEARCH_LOCATION_TYPE = eINSTANCE.getContentSearchLocationType();

		/**
		 * The meta object literal for the '<em>Create Stub Format Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getCreateStubFormatType()
		 * @generated
		 */
		EDataType CREATE_STUB_FORMAT_TYPE = eINSTANCE.getCreateStubFormatType();

		/**
		 * The meta object literal for the '<em>Create Stub Format Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Boolean
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getCreateStubFormatTypeObject()
		 * @generated
		 */
		EDataType CREATE_STUB_FORMAT_TYPE_OBJECT = eINSTANCE.getCreateStubFormatTypeObject();

		/**
		 * The meta object literal for the '<em>Epoc Root Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getEpocRootType()
		 * @generated
		 */
		EDataType EPOC_ROOT_TYPE = eINSTANCE.getEpocRootType();

		/**
		 * The meta object literal for the '<em>Error Parsers Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getErrorParsersType()
		 * @generated
		 */
		EDataType ERROR_PARSERS_TYPE = eINSTANCE.getErrorParsersType();

		/**
		 * The meta object literal for the '<em>Key Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getKeyType()
		 * @generated
		 */
		EDataType KEY_TYPE = eINSTANCE.getKeyType();

		/**
		 * The meta object literal for the '<em>Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getNameType()
		 * @generated
		 */
		EDataType NAME_TYPE = eINSTANCE.getNameType();

		/**
		 * The meta object literal for the '<em>Name Type1</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getNameType1()
		 * @generated
		 */
		EDataType NAME_TYPE1 = eINSTANCE.getNameType1();

		/**
		 * The meta object literal for the '<em>Output File Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getOutputFileNameType()
		 * @generated
		 */
		EDataType OUTPUT_FILE_NAME_TYPE = eINSTANCE.getOutputFileNameType();

		/**
		 * The meta object literal for the '<em>Password Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getPasswordType()
		 * @generated
		 */
		EDataType PASSWORD_TYPE = eINSTANCE.getPasswordType();

		/**
		 * The meta object literal for the '<em>Pkg File Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getPkgFileType()
		 * @generated
		 */
		EDataType PKG_FILE_TYPE = eINSTANCE.getPkgFileType();

		/**
		 * The meta object literal for the '<em>Signed File Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getSignedFileNameType()
		 * @generated
		 */
		EDataType SIGNED_FILE_NAME_TYPE = eINSTANCE.getSignedFileNameType();

		/**
		 * The meta object literal for the '<em>Use Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.UseType
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getUseTypeObject()
		 * @generated
		 */
		EDataType USE_TYPE_OBJECT = eINSTANCE.getUseTypeObject();

		/**
		 * The meta object literal for the '<em>Value Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.CarbideBuildConfigPackageImpl#getValueType()
		 * @generated
		 */
		EDataType VALUE_TYPE = eINSTANCE.getValueType();

	}

} //CarbideBuildConfigPackage
