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

package com.nokia.sdt.looknfeel.feel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Masked Image Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.MaskedImageType#getImageFile <em>Image File</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.MaskedImageType#getKey <em>Key</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.MaskedImageType#getMaskFile <em>Mask File</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getMaskedImageType()
 * @model extendedMetaData="name='maskedImage_._type' kind='empty'"
 * @generated
 */
public interface MaskedImageType extends EObject{
    /**
	 * Returns the value of the '<em><b>Image File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image File</em>' attribute.
	 * @see #setImageFile(String)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getMaskedImageType_ImageFile()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='imageFile'"
	 * @generated
	 */
	String getImageFile();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.MaskedImageType#getImageFile <em>Image File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image File</em>' attribute.
	 * @see #getImageFile()
	 * @generated
	 */
	void setImageFile(String value);

    /**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getMaskedImageType_Key()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='key'"
	 * @generated
	 */
	String getKey();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.MaskedImageType#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

    /**
	 * Returns the value of the '<em><b>Mask File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mask File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mask File</em>' attribute.
	 * @see #setMaskFile(String)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getMaskedImageType_MaskFile()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='maskFile'"
	 * @generated
	 */
	String getMaskFile();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.MaskedImageType#getMaskFile <em>Mask File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mask File</em>' attribute.
	 * @see #getMaskFile()
	 * @generated
	 */
	void setMaskFile(String value);

} // MaskedImageType
