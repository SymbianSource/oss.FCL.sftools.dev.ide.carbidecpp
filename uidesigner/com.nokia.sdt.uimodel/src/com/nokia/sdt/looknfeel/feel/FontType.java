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
 * A representation of the model object '<em><b>Font Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.FontType#getInitData <em>Init Data</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.FontType#getKey <em>Key</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.FontType#getSize <em>Size</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getFontType()
 * @model extendedMetaData="name='font_._type' kind='empty'"
 * @generated
 */
public interface FontType extends EObject{
    /**
	 * Returns the value of the '<em><b>Init Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Data</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Data</em>' attribute.
	 * @see #setInitData(String)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getFontType_InitData()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='initData'"
	 * @generated
	 */
	String getInitData();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.FontType#getInitData <em>Init Data</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Init Data</em>' attribute.
	 * @see #getInitData()
	 * @generated
	 */
	void setInitData(String value);

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
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getFontType_Key()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='key'"
	 * @generated
	 */
	String getKey();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.FontType#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

    /**
	 * Returns the value of the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Size</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Size</em>' attribute.
	 * @see #isSetSize()
	 * @see #unsetSize()
	 * @see #setSize(short)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getFontType_Size()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='size'"
	 * @generated
	 */
    short getSize();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.FontType#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Size</em>' attribute.
	 * @see #isSetSize()
	 * @see #unsetSize()
	 * @see #getSize()
	 * @generated
	 */
    void setSize(short value);

    /**
	 * Unsets the value of the '{@link com.nokia.sdt.looknfeel.feel.FontType#getSize <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetSize()
	 * @see #getSize()
	 * @see #setSize(short)
	 * @generated
	 */
    void unsetSize();

    /**
	 * Returns whether the value of the '{@link com.nokia.sdt.looknfeel.feel.FontType#getSize <em>Size</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Size</em>' attribute is set.
	 * @see #unsetSize()
	 * @see #getSize()
	 * @see #setSize(short)
	 * @generated
	 */
    boolean isSetSize();

} // FontType
