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

package com.nokia.carbide.internal.template.gen.Template.impl;

import com.nokia.carbide.internal.template.gen.Template.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TemplateFactoryImpl extends EFactoryImpl implements TemplateFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TemplateFactory init() {
		try {
			TemplateFactory theTemplateFactory = (TemplateFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/com.nokia.carbide.templatewizard/schema/template.xsd"); 
			if (theTemplateFactory != null) {
				return theTemplateFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TemplateFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TemplatePackage.BASE_FIELD_TYPE: return createBaseFieldType();
			case TemplatePackage.DOCUMENT_ROOT: return createDocumentRoot();
			case TemplatePackage.FILENAME_FIELD_TYPE: return createFilenameFieldType();
			case TemplatePackage.METADATA_TYPE: return createMetadataType();
			case TemplatePackage.PARAMETER_TYPE: return createParameterType();
			case TemplatePackage.PROCESS_TYPE: return createProcessType();
			case TemplatePackage.TEMPLATE_TYPE: return createTemplateType();
			case TemplatePackage.TEXT_FIELD_TYPE: return createTextFieldType();
			case TemplatePackage.UID_FIELD_TYPE: return createUidFieldType();
			case TemplatePackage.WIZARD_PAGE_TYPE: return createWizardPageType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseFieldType createBaseFieldType() {
		BaseFieldTypeImpl baseFieldType = new BaseFieldTypeImpl();
		return baseFieldType;
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
	public FilenameFieldType createFilenameFieldType() {
		FilenameFieldTypeImpl filenameFieldType = new FilenameFieldTypeImpl();
		return filenameFieldType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataType createMetadataType() {
		MetadataTypeImpl metadataType = new MetadataTypeImpl();
		return metadataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterType createParameterType() {
		ParameterTypeImpl parameterType = new ParameterTypeImpl();
		return parameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessType createProcessType() {
		ProcessTypeImpl processType = new ProcessTypeImpl();
		return processType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplateType createTemplateType() {
		TemplateTypeImpl templateType = new TemplateTypeImpl();
		return templateType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TextFieldType createTextFieldType() {
		TextFieldTypeImpl textFieldType = new TextFieldTypeImpl();
		return textFieldType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UidFieldType createUidFieldType() {
		UidFieldTypeImpl uidFieldType = new UidFieldTypeImpl();
		return uidFieldType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WizardPageType createWizardPageType() {
		WizardPageTypeImpl wizardPageType = new WizardPageTypeImpl();
		return wizardPageType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TemplatePackage getTemplatePackage() {
		return (TemplatePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static TemplatePackage getPackage() {
		return TemplatePackage.eINSTANCE;
	}

} //TemplateFactoryImpl
