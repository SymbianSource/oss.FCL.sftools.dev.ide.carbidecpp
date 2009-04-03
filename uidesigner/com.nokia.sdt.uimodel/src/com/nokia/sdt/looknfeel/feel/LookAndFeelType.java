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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getColor <em>Color</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getColorAlias <em>Color Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getFont <em>Font</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getFontAlias <em>Font Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getImage <em>Image</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getMaskedImage <em>Masked Image</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getPosition <em>Position</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getPositionAlias <em>Position Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getDimension <em>Dimension</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getDimensionAlias <em>Dimension Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getRectangle <em>Rectangle</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getRectangleAlias <em>Rectangle Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getInteger <em>Integer</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getIntegerAlias <em>Integer Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getBooleanAlias <em>Boolean Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getString <em>String</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getStringAlias <em>String Alias</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType()
 * @model extendedMetaData="name='lookAndFeel_._type' kind='elementOnly'"
 * @generated
 */
public interface LookAndFeelType extends EObject{
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
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
    FeatureMap getGroup();

    /**
	 * Returns the value of the '<em><b>Color</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.ColorType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_Color()
	 * @model type="com.nokia.sdt.looknfeel.feel.ColorType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='color' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getColor();

    /**
	 * Returns the value of the '<em><b>Color Alias</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.AliasType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color Alias</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color Alias</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_ColorAlias()
	 * @model type="com.nokia.sdt.looknfeel.feel.AliasType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='colorAlias' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getColorAlias();

    /**
	 * Returns the value of the '<em><b>Font</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.FontType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_Font()
	 * @model type="com.nokia.sdt.looknfeel.feel.FontType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='font' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getFont();

    /**
	 * Returns the value of the '<em><b>Font Alias</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.AliasType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font Alias</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font Alias</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_FontAlias()
	 * @model type="com.nokia.sdt.looknfeel.feel.AliasType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='fontAlias' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getFontAlias();

    /**
	 * Returns the value of the '<em><b>Image</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.ImageType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_Image()
	 * @model type="com.nokia.sdt.looknfeel.feel.ImageType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='image' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getImage();

				/**
	 * Returns the value of the '<em><b>Masked Image</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.MaskedImageType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Masked Image</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Masked Image</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_MaskedImage()
	 * @model type="com.nokia.sdt.looknfeel.feel.MaskedImageType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='maskedImage' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getMaskedImage();

    /**
	 * Returns the value of the '<em><b>Position</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.PositionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Position</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_Position()
	 * @model type="com.nokia.sdt.looknfeel.feel.PositionType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='position' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getPosition();

    /**
	 * Returns the value of the '<em><b>Position Alias</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.AliasType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Position Alias</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position Alias</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_PositionAlias()
	 * @model type="com.nokia.sdt.looknfeel.feel.AliasType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='positionAlias' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getPositionAlias();

    /**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.DimensionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dimension</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_Dimension()
	 * @model type="com.nokia.sdt.looknfeel.feel.DimensionType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='dimension' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getDimension();

    /**
	 * Returns the value of the '<em><b>Dimension Alias</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.AliasType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dimension Alias</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension Alias</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_DimensionAlias()
	 * @model type="com.nokia.sdt.looknfeel.feel.AliasType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='dimensionAlias' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getDimensionAlias();

    /**
	 * Returns the value of the '<em><b>Rectangle</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.RectangleType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rectangle</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rectangle</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_Rectangle()
	 * @model type="com.nokia.sdt.looknfeel.feel.RectangleType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rectangle' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getRectangle();

    /**
	 * Returns the value of the '<em><b>Rectangle Alias</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.AliasType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rectangle Alias</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rectangle Alias</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_RectangleAlias()
	 * @model type="com.nokia.sdt.looknfeel.feel.AliasType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rectangleAlias' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getRectangleAlias();

    /**
	 * Returns the value of the '<em><b>Integer</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.IntegerType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Integer</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Integer</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_Integer()
	 * @model type="com.nokia.sdt.looknfeel.feel.IntegerType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='integer' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getInteger();

    /**
	 * Returns the value of the '<em><b>Integer Alias</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.AliasType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Integer Alias</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Integer Alias</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_IntegerAlias()
	 * @model type="com.nokia.sdt.looknfeel.feel.AliasType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='integerAlias' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getIntegerAlias();

    /**
	 * Returns the value of the '<em><b>Boolean</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.BooleanType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Boolean</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boolean</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_Boolean()
	 * @model type="com.nokia.sdt.looknfeel.feel.BooleanType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='boolean' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getBoolean();

    /**
	 * Returns the value of the '<em><b>Boolean Alias</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.AliasType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Boolean Alias</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boolean Alias</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_BooleanAlias()
	 * @model type="com.nokia.sdt.looknfeel.feel.AliasType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='booleanAlias' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList getBooleanAlias();

    /**
	 * Returns the value of the '<em><b>String</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.StringType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>String</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>String</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_String()
	 * @model type="com.nokia.sdt.looknfeel.feel.StringType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='string' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getString();

    /**
	 * Returns the value of the '<em><b>String Alias</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.sdt.looknfeel.feel.AliasType}.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>String Alias</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>String Alias</em>' containment reference list.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getLookAndFeelType_StringAlias()
	 * @model type="com.nokia.sdt.looknfeel.feel.AliasType" containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='stringAlias' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
    EList getStringAlias();

} // LookAndFeelType
