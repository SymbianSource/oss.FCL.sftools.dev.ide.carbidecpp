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

package com.nokia.carbide.internal.template.gen.Template;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.nokia.carbide.internal.template.gen.Template.TemplateFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
@SuppressWarnings("hiding")
public interface TemplatePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Template";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/com.nokia.carbide.templatewizard/schema/template.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "Template";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TemplatePackage eINSTANCE = com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.BaseFieldTypeImpl <em>Base Field Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.BaseFieldTypeImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getBaseFieldType()
	 * @generated
	 */
	int BASE_FIELD_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FIELD_TYPE__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FIELD_TYPE__ID = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FIELD_TYPE__LABEL = 2;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FIELD_TYPE__MANDATORY = 3;

	/**
	 * The feature id for the '<em><b>Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FIELD_TYPE__PERSIST = 4;

	/**
	 * The number of structural features of the '<em>Base Field Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FIELD_TYPE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 1;

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
	 * The feature id for the '<em><b>Base Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BASE_FIELD = 3;

	/**
	 * The feature id for the '<em><b>Filename Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FILENAME_FIELD = 4;

	/**
	 * The feature id for the '<em><b>Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEMPLATE = 5;

	/**
	 * The feature id for the '<em><b>Text Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEXT_FIELD = 6;

	/**
	 * The feature id for the '<em><b>Uid Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__UID_FIELD = 7;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.FilenameFieldTypeImpl <em>Filename Field Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.FilenameFieldTypeImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getFilenameFieldType()
	 * @generated
	 */
	int FILENAME_FIELD_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILENAME_FIELD_TYPE__DESCRIPTION = BASE_FIELD_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILENAME_FIELD_TYPE__ID = BASE_FIELD_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILENAME_FIELD_TYPE__LABEL = BASE_FIELD_TYPE__LABEL;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILENAME_FIELD_TYPE__MANDATORY = BASE_FIELD_TYPE__MANDATORY;

	/**
	 * The feature id for the '<em><b>Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILENAME_FIELD_TYPE__PERSIST = BASE_FIELD_TYPE__PERSIST;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILENAME_FIELD_TYPE__DEFAULT = BASE_FIELD_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Filename Field Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILENAME_FIELD_TYPE_FEATURE_COUNT = BASE_FIELD_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.MetadataTypeImpl <em>Metadata Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.MetadataTypeImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getMetadataType()
	 * @generated
	 */
	int METADATA_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_TYPE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_TYPE__NAME = 1;

	/**
	 * The feature id for the '<em><b>Value1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_TYPE__VALUE1 = 2;

	/**
	 * The number of structural features of the '<em>Metadata Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.ParameterTypeImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getParameterType()
	 * @generated
	 */
	int PARAMETER_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE__ANY_ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.ProcessTypeImpl <em>Process Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.ProcessTypeImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getProcessType()
	 * @generated
	 */
	int PROCESS_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_TYPE__PARAMETER = 0;

	/**
	 * The feature id for the '<em><b>Bundle</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_TYPE__BUNDLE = 1;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_TYPE__CLASS = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_TYPE__NAME = 3;

	/**
	 * The number of structural features of the '<em>Process Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_TYPE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getTemplateType()
	 * @generated
	 */
	int TEMPLATE_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Wizard Page</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__WIZARD_PAGE = 0;

	/**
	 * The feature id for the '<em><b>Process</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__PROCESS = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Metadata</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__METADATA = 3;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__AUTHOR = 4;

	/**
	 * The feature id for the '<em><b>Copyright</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__COPYRIGHT = 5;

	/**
	 * The feature id for the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__HELP = 6;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__LABEL = 7;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE__VERSION = 8;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_TYPE_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.TextFieldTypeImpl <em>Text Field Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TextFieldTypeImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getTextFieldType()
	 * @generated
	 */
	int TEXT_FIELD_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_TYPE__DESCRIPTION = BASE_FIELD_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_TYPE__ID = BASE_FIELD_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_TYPE__LABEL = BASE_FIELD_TYPE__LABEL;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_TYPE__MANDATORY = BASE_FIELD_TYPE__MANDATORY;

	/**
	 * The feature id for the '<em><b>Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_TYPE__PERSIST = BASE_FIELD_TYPE__PERSIST;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_TYPE__DEFAULT = BASE_FIELD_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Multiline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_TYPE__MULTILINE = BASE_FIELD_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_TYPE__PATTERN = BASE_FIELD_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Text Field Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FIELD_TYPE_FEATURE_COUNT = BASE_FIELD_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.UidFieldTypeImpl <em>Uid Field Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.UidFieldTypeImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getUidFieldType()
	 * @generated
	 */
	int UID_FIELD_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UID_FIELD_TYPE__DESCRIPTION = BASE_FIELD_TYPE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UID_FIELD_TYPE__ID = BASE_FIELD_TYPE__ID;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UID_FIELD_TYPE__LABEL = BASE_FIELD_TYPE__LABEL;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UID_FIELD_TYPE__MANDATORY = BASE_FIELD_TYPE__MANDATORY;

	/**
	 * The feature id for the '<em><b>Persist</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UID_FIELD_TYPE__PERSIST = BASE_FIELD_TYPE__PERSIST;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UID_FIELD_TYPE__MAX = BASE_FIELD_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UID_FIELD_TYPE__MIN = BASE_FIELD_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Uid Field Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UID_FIELD_TYPE_FEATURE_COUNT = BASE_FIELD_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl <em>Wizard Page Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl
	 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getWizardPageType()
	 * @generated
	 */
	int WIZARD_PAGE_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIZARD_PAGE_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Uid Field</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIZARD_PAGE_TYPE__UID_FIELD = 1;

	/**
	 * The feature id for the '<em><b>Text Field</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIZARD_PAGE_TYPE__TEXT_FIELD = 2;

	/**
	 * The feature id for the '<em><b>Filename Field</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIZARD_PAGE_TYPE__FILENAME_FIELD = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIZARD_PAGE_TYPE__DESCRIPTION = 4;

	/**
	 * The feature id for the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIZARD_PAGE_TYPE__HELP = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIZARD_PAGE_TYPE__ID = 6;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIZARD_PAGE_TYPE__LABEL = 7;

	/**
	 * The number of structural features of the '<em>Wizard Page Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WIZARD_PAGE_TYPE_FEATURE_COUNT = 8;


	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.BaseFieldType <em>Base Field Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base Field Type</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.BaseFieldType
	 * @generated
	 */
	EClass getBaseFieldType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.BaseFieldType#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.BaseFieldType#getDescription()
	 * @see #getBaseFieldType()
	 * @generated
	 */
	EAttribute getBaseFieldType_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.BaseFieldType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.BaseFieldType#getId()
	 * @see #getBaseFieldType()
	 * @generated
	 */
	EAttribute getBaseFieldType_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.BaseFieldType#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.BaseFieldType#getLabel()
	 * @see #getBaseFieldType()
	 * @generated
	 */
	EAttribute getBaseFieldType_Label();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.BaseFieldType#isMandatory <em>Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mandatory</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.BaseFieldType#isMandatory()
	 * @see #getBaseFieldType()
	 * @generated
	 */
	EAttribute getBaseFieldType_Mandatory();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.BaseFieldType#isPersist <em>Persist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Persist</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.BaseFieldType#isPersist()
	 * @see #getBaseFieldType()
	 * @generated
	 */
	EAttribute getBaseFieldType_Persist();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getBaseField <em>Base Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Base Field</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getBaseField()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BaseField();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getFilenameField <em>Filename Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Filename Field</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getFilenameField()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_FilenameField();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Template</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getTemplate()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Template();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getTextField <em>Text Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Text Field</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getTextField()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_TextField();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getUidField <em>Uid Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Uid Field</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getUidField()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_UidField();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.FilenameFieldType <em>Filename Field Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Filename Field Type</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.FilenameFieldType
	 * @generated
	 */
	EClass getFilenameFieldType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.FilenameFieldType#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.FilenameFieldType#getDefault()
	 * @see #getFilenameFieldType()
	 * @generated
	 */
	EAttribute getFilenameFieldType_Default();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.MetadataType <em>Metadata Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metadata Type</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.MetadataType
	 * @generated
	 */
	EClass getMetadataType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.MetadataType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.MetadataType#getValue()
	 * @see #getMetadataType()
	 * @generated
	 */
	EAttribute getMetadataType_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.MetadataType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.MetadataType#getName()
	 * @see #getMetadataType()
	 * @generated
	 */
	EAttribute getMetadataType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.MetadataType#getValue1 <em>Value1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value1</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.MetadataType#getValue1()
	 * @see #getMetadataType()
	 * @generated
	 */
	EAttribute getMetadataType_Value1();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.ParameterType <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Type</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.ParameterType
	 * @generated
	 */
	EClass getParameterType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.ParameterType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.ParameterType#getName()
	 * @see #getParameterType()
	 * @generated
	 */
	EAttribute getParameterType_Name();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.internal.template.gen.Template.ParameterType#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.ParameterType#getAnyAttribute()
	 * @see #getParameterType()
	 * @generated
	 */
	EAttribute getParameterType_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.ProcessType <em>Process Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Type</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.ProcessType
	 * @generated
	 */
	EClass getProcessType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.internal.template.gen.Template.ProcessType#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.ProcessType#getParameter()
	 * @see #getProcessType()
	 * @generated
	 */
	EReference getProcessType_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.ProcessType#getBundle <em>Bundle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bundle</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.ProcessType#getBundle()
	 * @see #getProcessType()
	 * @generated
	 */
	EAttribute getProcessType_Bundle();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.ProcessType#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.ProcessType#getClass_()
	 * @see #getProcessType()
	 * @generated
	 */
	EAttribute getProcessType_Class();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.ProcessType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.ProcessType#getName()
	 * @see #getProcessType()
	 * @generated
	 */
	EAttribute getProcessType_Name();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType
	 * @generated
	 */
	EClass getTemplateType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getWizardPage <em>Wizard Page</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Wizard Page</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType#getWizardPage()
	 * @see #getTemplateType()
	 * @generated
	 */
	EReference getTemplateType_WizardPage();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getProcess <em>Process</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Process</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType#getProcess()
	 * @see #getTemplateType()
	 * @generated
	 */
	EReference getTemplateType_Process();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType#getDescription()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metadata</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType#getMetadata()
	 * @see #getTemplateType()
	 * @generated
	 */
	EReference getTemplateType_Metadata();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType#getAuthor()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Author();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getCopyright <em>Copyright</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Copyright</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType#getCopyright()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Copyright();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getHelp <em>Help</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType#getHelp()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Help();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType#getLabel()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Label();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplateType#getVersion()
	 * @see #getTemplateType()
	 * @generated
	 */
	EAttribute getTemplateType_Version();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType <em>Text Field Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Field Type</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TextFieldType
	 * @generated
	 */
	EClass getTextFieldType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TextFieldType#getDefault()
	 * @see #getTextFieldType()
	 * @generated
	 */
	EAttribute getTextFieldType_Default();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#isMultiline <em>Multiline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiline</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TextFieldType#isMultiline()
	 * @see #getTextFieldType()
	 * @generated
	 */
	EAttribute getTextFieldType_Multiline();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#getPattern <em>Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.TextFieldType#getPattern()
	 * @see #getTextFieldType()
	 * @generated
	 */
	EAttribute getTextFieldType_Pattern();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.UidFieldType <em>Uid Field Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Uid Field Type</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.UidFieldType
	 * @generated
	 */
	EClass getUidFieldType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.UidFieldType#getMax <em>Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.UidFieldType#getMax()
	 * @see #getUidFieldType()
	 * @generated
	 */
	EAttribute getUidFieldType_Max();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.UidFieldType#getMin <em>Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.UidFieldType#getMin()
	 * @see #getUidFieldType()
	 * @generated
	 */
	EAttribute getUidFieldType_Min();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType <em>Wizard Page Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wizard Page Type</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.WizardPageType
	 * @generated
	 */
	EClass getWizardPageType();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.WizardPageType#getGroup()
	 * @see #getWizardPageType()
	 * @generated
	 */
	EAttribute getWizardPageType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getUidField <em>Uid Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uid Field</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.WizardPageType#getUidField()
	 * @see #getWizardPageType()
	 * @generated
	 */
	EReference getWizardPageType_UidField();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getTextField <em>Text Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Text Field</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.WizardPageType#getTextField()
	 * @see #getWizardPageType()
	 * @generated
	 */
	EReference getWizardPageType_TextField();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getFilenameField <em>Filename Field</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Filename Field</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.WizardPageType#getFilenameField()
	 * @see #getWizardPageType()
	 * @generated
	 */
	EReference getWizardPageType_FilenameField();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.WizardPageType#getDescription()
	 * @see #getWizardPageType()
	 * @generated
	 */
	EAttribute getWizardPageType_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getHelp <em>Help</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Help</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.WizardPageType#getHelp()
	 * @see #getWizardPageType()
	 * @generated
	 */
	EAttribute getWizardPageType_Help();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.WizardPageType#getId()
	 * @see #getWizardPageType()
	 * @generated
	 */
	EAttribute getWizardPageType_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.nokia.carbide.internal.template.gen.Template.WizardPageType#getLabel()
	 * @see #getWizardPageType()
	 * @generated
	 */
	EAttribute getWizardPageType_Label();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TemplateFactory getTemplateFactory();

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
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.BaseFieldTypeImpl <em>Base Field Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.BaseFieldTypeImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getBaseFieldType()
		 * @generated
		 */
		EClass BASE_FIELD_TYPE = eINSTANCE.getBaseFieldType();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_FIELD_TYPE__DESCRIPTION = eINSTANCE.getBaseFieldType_Description();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_FIELD_TYPE__ID = eINSTANCE.getBaseFieldType_Id();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_FIELD_TYPE__LABEL = eINSTANCE.getBaseFieldType_Label();

		/**
		 * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_FIELD_TYPE__MANDATORY = eINSTANCE.getBaseFieldType_Mandatory();

		/**
		 * The meta object literal for the '<em><b>Persist</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_FIELD_TYPE__PERSIST = eINSTANCE.getBaseFieldType_Persist();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.DocumentRootImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getDocumentRoot()
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
		 * The meta object literal for the '<em><b>Base Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BASE_FIELD = eINSTANCE.getDocumentRoot_BaseField();

		/**
		 * The meta object literal for the '<em><b>Filename Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FILENAME_FIELD = eINSTANCE.getDocumentRoot_FilenameField();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TEMPLATE = eINSTANCE.getDocumentRoot_Template();

		/**
		 * The meta object literal for the '<em><b>Text Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TEXT_FIELD = eINSTANCE.getDocumentRoot_TextField();

		/**
		 * The meta object literal for the '<em><b>Uid Field</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__UID_FIELD = eINSTANCE.getDocumentRoot_UidField();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.FilenameFieldTypeImpl <em>Filename Field Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.FilenameFieldTypeImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getFilenameFieldType()
		 * @generated
		 */
		EClass FILENAME_FIELD_TYPE = eINSTANCE.getFilenameFieldType();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILENAME_FIELD_TYPE__DEFAULT = eINSTANCE.getFilenameFieldType_Default();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.MetadataTypeImpl <em>Metadata Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.MetadataTypeImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getMetadataType()
		 * @generated
		 */
		EClass METADATA_TYPE = eINSTANCE.getMetadataType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METADATA_TYPE__VALUE = eINSTANCE.getMetadataType_Value();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METADATA_TYPE__NAME = eINSTANCE.getMetadataType_Name();

		/**
		 * The meta object literal for the '<em><b>Value1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METADATA_TYPE__VALUE1 = eINSTANCE.getMetadataType_Value1();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.ParameterTypeImpl <em>Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.ParameterTypeImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getParameterType()
		 * @generated
		 */
		EClass PARAMETER_TYPE = eINSTANCE.getParameterType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_TYPE__NAME = eINSTANCE.getParameterType_Name();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_TYPE__ANY_ATTRIBUTE = eINSTANCE.getParameterType_AnyAttribute();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.ProcessTypeImpl <em>Process Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.ProcessTypeImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getProcessType()
		 * @generated
		 */
		EClass PROCESS_TYPE = eINSTANCE.getProcessType();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_TYPE__PARAMETER = eINSTANCE.getProcessType_Parameter();

		/**
		 * The meta object literal for the '<em><b>Bundle</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS_TYPE__BUNDLE = eINSTANCE.getProcessType_Bundle();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS_TYPE__CLASS = eINSTANCE.getProcessType_Class();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS_TYPE__NAME = eINSTANCE.getProcessType_Name();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplateTypeImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getTemplateType()
		 * @generated
		 */
		EClass TEMPLATE_TYPE = eINSTANCE.getTemplateType();

		/**
		 * The meta object literal for the '<em><b>Wizard Page</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_TYPE__WIZARD_PAGE = eINSTANCE.getTemplateType_WizardPage();

		/**
		 * The meta object literal for the '<em><b>Process</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_TYPE__PROCESS = eINSTANCE.getTemplateType_Process();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__DESCRIPTION = eINSTANCE.getTemplateType_Description();

		/**
		 * The meta object literal for the '<em><b>Metadata</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE_TYPE__METADATA = eINSTANCE.getTemplateType_Metadata();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__AUTHOR = eINSTANCE.getTemplateType_Author();

		/**
		 * The meta object literal for the '<em><b>Copyright</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__COPYRIGHT = eINSTANCE.getTemplateType_Copyright();

		/**
		 * The meta object literal for the '<em><b>Help</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__HELP = eINSTANCE.getTemplateType_Help();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__LABEL = eINSTANCE.getTemplateType_Label();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE_TYPE__VERSION = eINSTANCE.getTemplateType_Version();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.TextFieldTypeImpl <em>Text Field Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TextFieldTypeImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getTextFieldType()
		 * @generated
		 */
		EClass TEXT_FIELD_TYPE = eINSTANCE.getTextFieldType();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_FIELD_TYPE__DEFAULT = eINSTANCE.getTextFieldType_Default();

		/**
		 * The meta object literal for the '<em><b>Multiline</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_FIELD_TYPE__MULTILINE = eINSTANCE.getTextFieldType_Multiline();

		/**
		 * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_FIELD_TYPE__PATTERN = eINSTANCE.getTextFieldType_Pattern();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.UidFieldTypeImpl <em>Uid Field Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.UidFieldTypeImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getUidFieldType()
		 * @generated
		 */
		EClass UID_FIELD_TYPE = eINSTANCE.getUidFieldType();

		/**
		 * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UID_FIELD_TYPE__MAX = eINSTANCE.getUidFieldType_Max();

		/**
		 * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UID_FIELD_TYPE__MIN = eINSTANCE.getUidFieldType_Min();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl <em>Wizard Page Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.WizardPageTypeImpl
		 * @see com.nokia.carbide.internal.template.gen.Template.impl.TemplatePackageImpl#getWizardPageType()
		 * @generated
		 */
		EClass WIZARD_PAGE_TYPE = eINSTANCE.getWizardPageType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIZARD_PAGE_TYPE__GROUP = eINSTANCE.getWizardPageType_Group();

		/**
		 * The meta object literal for the '<em><b>Uid Field</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIZARD_PAGE_TYPE__UID_FIELD = eINSTANCE.getWizardPageType_UidField();

		/**
		 * The meta object literal for the '<em><b>Text Field</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIZARD_PAGE_TYPE__TEXT_FIELD = eINSTANCE.getWizardPageType_TextField();

		/**
		 * The meta object literal for the '<em><b>Filename Field</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WIZARD_PAGE_TYPE__FILENAME_FIELD = eINSTANCE.getWizardPageType_FilenameField();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIZARD_PAGE_TYPE__DESCRIPTION = eINSTANCE.getWizardPageType_Description();

		/**
		 * The meta object literal for the '<em><b>Help</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIZARD_PAGE_TYPE__HELP = eINSTANCE.getWizardPageType_Help();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIZARD_PAGE_TYPE__ID = eINSTANCE.getWizardPageType_Id();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WIZARD_PAGE_TYPE__LABEL = eINSTANCE.getWizardPageType_Label();

	}

} //TemplatePackage
