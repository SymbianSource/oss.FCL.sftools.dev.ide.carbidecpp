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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getWizardPage <em>Wizard Page</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getProcess <em>Process</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getDescription <em>Description</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getAuthor <em>Author</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getCopyright <em>Copyright</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getHelp <em>Help</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getLabel <em>Label</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType()
 * @model extendedMetaData="name='template_._type' kind='elementOnly'"
 * @generated
 */
public interface TemplateType extends EObject {
	/**
	 * Returns the value of the '<em><b>Wizard Page</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.internal.template.gen.Template.WizardPageType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Wizard Page</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Wizard Page</em>' containment reference list.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType_WizardPage()
	 * @model type="com.nokia.carbide.internal.template.gen.Template.WizardPageType" containment="true"
	 *        extendedMetaData="kind='element' name='wizardPage' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getWizardPage();

	/**
	 * Returns the value of the '<em><b>Process</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.internal.template.gen.Template.ProcessType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process</em>' containment reference list.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType_Process()
	 * @model type="com.nokia.carbide.internal.template.gen.Template.ProcessType" containment="true"
	 *        extendedMetaData="kind='element' name='process' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getProcess();

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
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Metadata</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.internal.template.gen.Template.MetadataType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metadata</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata</em>' containment reference list.
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType_Metadata()
	 * @model type="com.nokia.carbide.internal.template.gen.Template.MetadataType" containment="true"
	 *        extendedMetaData="kind='element' name='metadata' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getMetadata();

	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType_Author()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='author' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * Returns the value of the '<em><b>Copyright</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Copyright</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Copyright</em>' attribute.
	 * @see #setCopyright(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType_Copyright()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='copyright' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCopyright();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getCopyright <em>Copyright</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Copyright</em>' attribute.
	 * @see #getCopyright()
	 * @generated
	 */
	void setCopyright(String value);

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
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType_Help()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='help' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHelp();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getHelp <em>Help</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help</em>' attribute.
	 * @see #getHelp()
	 * @generated
	 */
	void setHelp(String value);

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
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType_Label()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='label' namespace='##targetNamespace'"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTemplateType_Version()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='version' namespace='##targetNamespace'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TemplateType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // TemplateType