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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Wizard Page Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getUidField <em>Uid Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getTextField <em>Text Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getFilenameField <em>Filename Field</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getDescription <em>Description</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getWizardPageType()
 * @model extendedMetaData="name='wizardPage_._type' kind='elementOnly'"
 * @generated
 */
public interface WizardPageType extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getWizardPageType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Uid Field</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.internal.template.gen.Template.UidFieldType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uid Field</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uid Field</em>' containment reference list.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getWizardPageType_UidField()
	 * @model type="com.nokia.carbide.internal.template.gen.Template.UidFieldType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='uidField' namespace='##targetNamespace' group='group:0'"
	 * @generated
	 */
	EList getUidField();

	/**
	 * Returns the value of the '<em><b>Text Field</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.internal.template.gen.Template.TextFieldType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text Field</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text Field</em>' containment reference list.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getWizardPageType_TextField()
	 * @model type="com.nokia.carbide.internal.template.gen.Template.TextFieldType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='textField' namespace='##targetNamespace' group='group:0'"
	 * @generated
	 */
	EList getTextField();

	/**
	 * Returns the value of the '<em><b>Filename Field</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.internal.template.gen.Template.FilenameFieldType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Filename Field</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Filename Field</em>' containment reference list.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getWizardPageType_FilenameField()
	 * @model type="com.nokia.carbide.internal.template.gen.Template.FilenameFieldType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='filenameField' namespace='##targetNamespace' group='group:0'"
	 * @generated
	 */
	EList getFilenameField();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getWizardPageType_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Help</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Help</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Help</em>' attribute.
	 * @see #setHelp(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getWizardPageType_Help()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='help' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHelp();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getHelp <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help</em>' attribute.
	 * @see #getHelp()
	 * @generated
	 */
	void setHelp(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getWizardPageType_Id()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getWizardPageType_Label()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='label' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.WizardPageType#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

} // WizardPageType