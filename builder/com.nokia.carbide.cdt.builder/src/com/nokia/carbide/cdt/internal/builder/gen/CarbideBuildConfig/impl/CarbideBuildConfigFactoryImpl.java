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

import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.*;

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
public class CarbideBuildConfigFactoryImpl extends EFactoryImpl implements CarbideBuildConfigFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CarbideBuildConfigFactory init() {
		try {
			CarbideBuildConfigFactory theCarbideBuildConfigFactory = (CarbideBuildConfigFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/com.nokia.carbide.cdt.builder/schema/carbideBuildConfig.xsd"); 
			if (theCarbideBuildConfigFactory != null) {
				return theCarbideBuildConfigFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CarbideBuildConfigFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CarbideBuildConfigFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CarbideBuildConfigPackage.CARBIDE_BUILDER_CONFIG_INFO_TYPE: return createCarbideBuilderConfigInfoType();
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE: return createConfigurationType();
			case CarbideBuildConfigPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case CarbideBuildConfigPackage.ENV_VARS_TYPE: return createEnvVarsType();
			case CarbideBuildConfigPackage.SIS_BUILDER_TYPE: return createSisBuilderType();
			case CarbideBuildConfigPackage.VAR_TYPE: return createVarType();
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
			case CarbideBuildConfigPackage.USE_TYPE:
				return createUseTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.CERT_TYPE:
				return createCertTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.CONTENT_SEARCH_LOCATION_TYPE:
				return createContentSearchLocationTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.CREATE_STUB_FORMAT_TYPE:
				return createCreateStubFormatTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.CREATE_STUB_FORMAT_TYPE_OBJECT:
				return createCreateStubFormatTypeObjectFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.EPOC_ROOT_TYPE:
				return createEpocRootTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.ERROR_PARSERS_TYPE:
				return createErrorParsersTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.KEY_TYPE:
				return createKeyTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.NAME_TYPE:
				return createNameTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.NAME_TYPE1:
				return createNameType1FromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.OUTPUT_FILE_NAME_TYPE:
				return createOutputFileNameTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.PASSWORD_TYPE:
				return createPasswordTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.PKG_FILE_TYPE:
				return createPkgFileTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.SIGNED_FILE_NAME_TYPE:
				return createSignedFileNameTypeFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.USE_TYPE_OBJECT:
				return createUseTypeObjectFromString(eDataType, initialValue);
			case CarbideBuildConfigPackage.VALUE_TYPE:
				return createValueTypeFromString(eDataType, initialValue);
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
			case CarbideBuildConfigPackage.USE_TYPE:
				return convertUseTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.CERT_TYPE:
				return convertCertTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.CONTENT_SEARCH_LOCATION_TYPE:
				return convertContentSearchLocationTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.CREATE_STUB_FORMAT_TYPE:
				return convertCreateStubFormatTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.CREATE_STUB_FORMAT_TYPE_OBJECT:
				return convertCreateStubFormatTypeObjectToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.EPOC_ROOT_TYPE:
				return convertEpocRootTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.ERROR_PARSERS_TYPE:
				return convertErrorParsersTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.KEY_TYPE:
				return convertKeyTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.NAME_TYPE:
				return convertNameTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.NAME_TYPE1:
				return convertNameType1ToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.OUTPUT_FILE_NAME_TYPE:
				return convertOutputFileNameTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.PASSWORD_TYPE:
				return convertPasswordTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.PKG_FILE_TYPE:
				return convertPkgFileTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.SIGNED_FILE_NAME_TYPE:
				return convertSignedFileNameTypeToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.USE_TYPE_OBJECT:
				return convertUseTypeObjectToString(eDataType, instanceValue);
			case CarbideBuildConfigPackage.VALUE_TYPE:
				return convertValueTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CarbideBuilderConfigInfoType createCarbideBuilderConfigInfoType() {
		CarbideBuilderConfigInfoTypeImpl carbideBuilderConfigInfoType = new CarbideBuilderConfigInfoTypeImpl();
		return carbideBuilderConfigInfoType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConfigurationType createConfigurationType() {
		ConfigurationTypeImpl configurationType = new ConfigurationTypeImpl();
		return configurationType;
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
	public EnvVarsType createEnvVarsType() {
		EnvVarsTypeImpl envVarsType = new EnvVarsTypeImpl();
		return envVarsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SisBuilderType createSisBuilderType() {
		SisBuilderTypeImpl sisBuilderType = new SisBuilderTypeImpl();
		return sisBuilderType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VarType createVarType() {
		VarTypeImpl varType = new VarTypeImpl();
		return varType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UseType createUseTypeFromString(EDataType eDataType, String initialValue) {
		UseType result = UseType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUseTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createCertTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCertTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createContentSearchLocationTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertContentSearchLocationTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean createCreateStubFormatTypeFromString(EDataType eDataType, String initialValue) {
		return (Boolean)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.BOOLEAN, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCreateStubFormatTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.BOOLEAN, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean createCreateStubFormatTypeObjectFromString(EDataType eDataType, String initialValue) {
		return (Boolean)createCreateStubFormatTypeFromString(CarbideBuildConfigPackage.Literals.CREATE_STUB_FORMAT_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCreateStubFormatTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCreateStubFormatTypeToString(CarbideBuildConfigPackage.Literals.CREATE_STUB_FORMAT_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createEpocRootTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertEpocRootTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createErrorParsersTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertErrorParsersTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createKeyTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertKeyTypeToString(EDataType eDataType, Object instanceValue) {
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
	public String createOutputFileNameTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOutputFileNameTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createPasswordTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPasswordTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createPkgFileTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPkgFileTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createSignedFileNameTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSignedFileNameTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UseType createUseTypeObjectFromString(EDataType eDataType, String initialValue) {
		return (UseType)createUseTypeFromString(CarbideBuildConfigPackage.Literals.USE_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUseTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertUseTypeToString(CarbideBuildConfigPackage.Literals.USE_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createValueTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertValueTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CarbideBuildConfigPackage getCarbideBuildConfigPackage() {
		return (CarbideBuildConfigPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static CarbideBuildConfigPackage getPackage() {
		return CarbideBuildConfigPackage.eINSTANCE;
	}

} //CarbideBuildConfigFactoryImpl
