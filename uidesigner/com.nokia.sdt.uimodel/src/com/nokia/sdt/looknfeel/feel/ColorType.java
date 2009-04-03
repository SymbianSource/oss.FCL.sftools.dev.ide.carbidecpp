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
 * A representation of the model object '<em><b>Color Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.ColorType#getB <em>B</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.ColorType#getG <em>G</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.ColorType#getKey <em>Key</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.ColorType#getR <em>R</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getColorType()
 * @model extendedMetaData="name='color_._type' kind='empty'"
 * @generated
 */
public interface ColorType extends EObject{
    /**
	 * Returns the value of the '<em><b>B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>B</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>B</em>' attribute.
	 * @see #isSetB()
	 * @see #unsetB()
	 * @see #setB(short)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getColorType_B()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='b'"
	 * @generated
	 */
	short getB();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getB <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>B</em>' attribute.
	 * @see #isSetB()
	 * @see #unsetB()
	 * @see #getB()
	 * @generated
	 */
	void setB(short value);

    /**
	 * Unsets the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getB <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetB()
	 * @see #getB()
	 * @see #setB(short)
	 * @generated
	 */
	void unsetB();

    /**
	 * Returns whether the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getB <em>B</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>B</em>' attribute is set.
	 * @see #unsetB()
	 * @see #getB()
	 * @see #setB(short)
	 * @generated
	 */
	boolean isSetB();

    /**
	 * Returns the value of the '<em><b>G</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>G</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>G</em>' attribute.
	 * @see #isSetG()
	 * @see #unsetG()
	 * @see #setG(short)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getColorType_G()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='g'"
	 * @generated
	 */
	short getG();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getG <em>G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>G</em>' attribute.
	 * @see #isSetG()
	 * @see #unsetG()
	 * @see #getG()
	 * @generated
	 */
	void setG(short value);

    /**
	 * Unsets the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getG <em>G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetG()
	 * @see #getG()
	 * @see #setG(short)
	 * @generated
	 */
	void unsetG();

    /**
	 * Returns whether the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getG <em>G</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>G</em>' attribute is set.
	 * @see #unsetG()
	 * @see #getG()
	 * @see #setG(short)
	 * @generated
	 */
	boolean isSetG();

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
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getColorType_Key()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='key'"
	 * @generated
	 */
	String getKey();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

    /**
	 * Returns the value of the '<em><b>R</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>R</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>R</em>' attribute.
	 * @see #isSetR()
	 * @see #unsetR()
	 * @see #setR(short)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getColorType_R()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='r'"
	 * @generated
	 */
	short getR();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getR <em>R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>R</em>' attribute.
	 * @see #isSetR()
	 * @see #unsetR()
	 * @see #getR()
	 * @generated
	 */
	void setR(short value);

    /**
	 * Unsets the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getR <em>R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetR()
	 * @see #getR()
	 * @see #setR(short)
	 * @generated
	 */
	void unsetR();

    /**
	 * Returns whether the value of the '{@link com.nokia.sdt.looknfeel.feel.ColorType#getR <em>R</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>R</em>' attribute is set.
	 * @see #unsetR()
	 * @see #getR()
	 * @see #setR(short)
	 * @generated
	 */
	boolean isSetR();

} // ColorType
