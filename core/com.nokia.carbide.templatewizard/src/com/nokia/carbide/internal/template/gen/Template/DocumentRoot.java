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

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getBaseField <em>Base Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getFilenameField <em>Filename Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getTemplate <em>Template</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getTextField <em>Text Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getUidField <em>Uid Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Base Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Field</em>' containment reference.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getDocumentRoot_BaseField()
	 * @model containment="true" upper="-2" transient="true" changeable="false" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='baseField' namespace='##targetNamespace'"
	 * @generated
	 */
	BaseFieldType getBaseField();

	/**
	 * Returns the value of the '<em><b>Filename Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filename Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filename Field</em>' containment reference.
	 * @see #setFilenameField(FilenameFieldType)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getDocumentRoot_FilenameField()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='filenameField' namespace='##targetNamespace'"
	 * @generated
	 */
	FilenameFieldType getFilenameField();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getFilenameField <em>Filename Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filename Field</em>' containment reference.
	 * @see #getFilenameField()
	 * @generated
	 */
	void setFilenameField(FilenameFieldType value);

	/**
	 * Returns the value of the '<em><b>Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Template</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template</em>' containment reference.
	 * @see #setTemplate(TemplateType)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getDocumentRoot_Template()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='template' namespace='##targetNamespace'"
	 * @generated
	 */
	TemplateType getTemplate();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getTemplate <em>Template</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template</em>' containment reference.
	 * @see #getTemplate()
	 * @generated
	 */
	void setTemplate(TemplateType value);

	/**
	 * Returns the value of the '<em><b>Text Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text Field</em>' containment reference.
	 * @see #setTextField(TextFieldType)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getDocumentRoot_TextField()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='textField' namespace='##targetNamespace'"
	 * @generated
	 */
	TextFieldType getTextField();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getTextField <em>Text Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text Field</em>' containment reference.
	 * @see #getTextField()
	 * @generated
	 */
	void setTextField(TextFieldType value);

	/**
	 * Returns the value of the '<em><b>Uid Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uid Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uid Field</em>' containment reference.
	 * @see #setUidField(UidFieldType)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getDocumentRoot_UidField()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='uidField' namespace='##targetNamespace'"
	 * @generated
	 */
	UidFieldType getUidField();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.DocumentRoot#getUidField <em>Uid Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uid Field</em>' containment reference.
	 * @see #getUidField()
	 * @generated
	 */
	void setUidField(UidFieldType value);

} // DocumentRoot