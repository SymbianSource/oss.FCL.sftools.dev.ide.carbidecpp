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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Field Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#getDefault <em>Default</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#isMultiline <em>Multiline</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#getPattern <em>Pattern</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTextFieldType()
 * @model extendedMetaData="name='textField_._type' kind='elementOnly'"
 * @generated
 */
public interface TextFieldType extends BaseFieldType {
	/**
	 * Returns the value of the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default</em>' attribute.
	 * @see #setDefault(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTextFieldType_Default()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='default' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDefault();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#getDefault <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' attribute.
	 * @see #getDefault()
	 * @generated
	 */
	void setDefault(String value);

	/**
	 * Returns the value of the '<em><b>Multiline</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiline</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiline</em>' attribute.
	 * @see #isSetMultiline()
	 * @see #unsetMultiline()
	 * @see #setMultiline(boolean)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTextFieldType_Multiline()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='multiline' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isMultiline();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#isMultiline <em>Multiline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiline</em>' attribute.
	 * @see #isSetMultiline()
	 * @see #unsetMultiline()
	 * @see #isMultiline()
	 * @generated
	 */
	void setMultiline(boolean value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#isMultiline <em>Multiline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMultiline()
	 * @see #isMultiline()
	 * @see #setMultiline(boolean)
	 * @generated
	 */
	void unsetMultiline();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#isMultiline <em>Multiline</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Multiline</em>' attribute is set.
	 * @see #unsetMultiline()
	 * @see #isMultiline()
	 * @see #setMultiline(boolean)
	 * @generated
	 */
	boolean isSetMultiline();

	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pattern</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern</em>' attribute.
	 * @see #setPattern(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getTextFieldType_Pattern()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='pattern' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPattern();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.TextFieldType#getPattern <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern</em>' attribute.
	 * @see #getPattern()
	 * @generated
	 */
	void setPattern(String value);

} // TextFieldType