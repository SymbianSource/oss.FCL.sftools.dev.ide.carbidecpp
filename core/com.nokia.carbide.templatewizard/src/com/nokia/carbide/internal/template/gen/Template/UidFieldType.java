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
 * A representation of the model object '<em><b>Uid Field Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.UidFieldType#getMax <em>Max</em>}</li>
 *   <li>{@link com.nokia.carbide.internal.template.gen.Template.UidFieldType#getMin <em>Min</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getUidFieldType()
 * @model extendedMetaData="name='uidField_._type' kind='elementOnly'"
 * @generated
 */
public interface UidFieldType extends BaseFieldType {
	/**
	 * Returns the value of the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max</em>' attribute.
	 * @see #setMax(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getUidFieldType_Max()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='max' namespace='##targetNamespace'"
	 * @generated
	 */
	String getMax();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.UidFieldType#getMax <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max</em>' attribute.
	 * @see #getMax()
	 * @generated
	 */
	void setMax(String value);

	/**
	 * Returns the value of the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min</em>' attribute.
	 * @see #setMin(String)
	 * @see com.nokia.carbide.internal.template.gen.Template.TemplatePackage#getUidFieldType_Min()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='min' namespace='##targetNamespace'"
	 * @generated
	 */
	String getMin();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.internal.template.gen.Template.UidFieldType#getMin <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min</em>' attribute.
	 * @see #getMin()
	 * @generated
	 */
	void setMin(String value);

} // UidFieldType