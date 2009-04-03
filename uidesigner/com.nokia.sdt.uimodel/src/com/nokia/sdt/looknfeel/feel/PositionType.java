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
 * A representation of the model object '<em><b>Position Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.PositionType#getKey <em>Key</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.PositionType#getX <em>X</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.PositionType#getY <em>Y</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getPositionType()
 * @model extendedMetaData="name='position_._type' kind='empty'"
 * @generated
 */
public interface PositionType extends EObject{
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
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getPositionType_Key()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='key'"
	 * @generated
	 */
	String getKey();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.PositionType#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

    /**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #isSetX()
	 * @see #unsetX()
	 * @see #setX(short)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getPositionType_X()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='x'"
	 * @generated
	 */
	short getX();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.PositionType#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #isSetX()
	 * @see #unsetX()
	 * @see #getX()
	 * @generated
	 */
	void setX(short value);

    /**
	 * Unsets the value of the '{@link com.nokia.sdt.looknfeel.feel.PositionType#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetX()
	 * @see #getX()
	 * @see #setX(short)
	 * @generated
	 */
	void unsetX();

    /**
	 * Returns whether the value of the '{@link com.nokia.sdt.looknfeel.feel.PositionType#getX <em>X</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>X</em>' attribute is set.
	 * @see #unsetX()
	 * @see #getX()
	 * @see #setX(short)
	 * @generated
	 */
	boolean isSetX();

    /**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #isSetY()
	 * @see #unsetY()
	 * @see #setY(short)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getPositionType_Y()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Short" required="true"
	 *        extendedMetaData="kind='attribute' name='y'"
	 * @generated
	 */
	short getY();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.PositionType#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #isSetY()
	 * @see #unsetY()
	 * @see #getY()
	 * @generated
	 */
	void setY(short value);

    /**
	 * Unsets the value of the '{@link com.nokia.sdt.looknfeel.feel.PositionType#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetY()
	 * @see #getY()
	 * @see #setY(short)
	 * @generated
	 */
	void unsetY();

    /**
	 * Returns whether the value of the '{@link com.nokia.sdt.looknfeel.feel.PositionType#getY <em>Y</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Y</em>' attribute is set.
	 * @see #unsetY()
	 * @see #getY()
	 * @see #setY(short)
	 * @generated
	 */
	boolean isSetY();

} // PositionType
