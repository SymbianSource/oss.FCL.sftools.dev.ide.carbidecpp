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
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getColor <em>Color</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getDimension <em>Dimension</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getFont <em>Font</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getImage <em>Image</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getInteger <em>Integer</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getLookAndFeel <em>Look And Feel</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getMaskedImage <em>Masked Image</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getPosition <em>Position</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getRectangle <em>Rectangle</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getString <em>String</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject{
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
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_Mixed()
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
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_XMLNSPrefixMap()
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
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap getXSISchemaLocation();

    /**
	 * Returns the value of the '<em><b>Boolean</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Boolean</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boolean</em>' containment reference.
	 * @see #setBoolean(BooleanType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_Boolean()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='boolean' namespace='##targetNamespace'"
	 * @generated
	 */
	BooleanType getBoolean();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getBoolean <em>Boolean</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Boolean</em>' containment reference.
	 * @see #getBoolean()
	 * @generated
	 */
	void setBoolean(BooleanType value);

    /**
	 * Returns the value of the '<em><b>Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' containment reference.
	 * @see #setColor(ColorType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_Color()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='color' namespace='##targetNamespace'"
	 * @generated
	 */
	ColorType getColor();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getColor <em>Color</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' containment reference.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(ColorType value);

    /**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dimension</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference.
	 * @see #setDimension(DimensionType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_Dimension()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='dimension' namespace='##targetNamespace'"
	 * @generated
	 */
	DimensionType getDimension();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getDimension <em>Dimension</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dimension</em>' containment reference.
	 * @see #getDimension()
	 * @generated
	 */
	void setDimension(DimensionType value);

    /**
	 * Returns the value of the '<em><b>Font</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Font</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Font</em>' containment reference.
	 * @see #setFont(FontType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_Font()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='font' namespace='##targetNamespace'"
	 * @generated
	 */
	FontType getFont();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getFont <em>Font</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Font</em>' containment reference.
	 * @see #getFont()
	 * @generated
	 */
	void setFont(FontType value);

    /**
	 * Returns the value of the '<em><b>Image</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image</em>' containment reference.
	 * @see #setImage(ImageType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_Image()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='image' namespace='##targetNamespace'"
	 * @generated
	 */
	ImageType getImage();

				/**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getImage <em>Image</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image</em>' containment reference.
	 * @see #getImage()
	 * @generated
	 */
	void setImage(ImageType value);

				/**
	 * Returns the value of the '<em><b>Integer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Integer</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Integer</em>' containment reference.
	 * @see #setInteger(IntegerType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_Integer()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='integer' namespace='##targetNamespace'"
	 * @generated
	 */
	IntegerType getInteger();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getInteger <em>Integer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Integer</em>' containment reference.
	 * @see #getInteger()
	 * @generated
	 */
	void setInteger(IntegerType value);

    /**
	 * Returns the value of the '<em><b>Look And Feel</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Look And Feel</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Look And Feel</em>' containment reference.
	 * @see #setLookAndFeel(LookAndFeelType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_LookAndFeel()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='lookAndFeel' namespace='##targetNamespace'"
	 * @generated
	 */
	LookAndFeelType getLookAndFeel();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getLookAndFeel <em>Look And Feel</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Look And Feel</em>' containment reference.
	 * @see #getLookAndFeel()
	 * @generated
	 */
	void setLookAndFeel(LookAndFeelType value);

    /**
	 * Returns the value of the '<em><b>Masked Image</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Masked Image</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Masked Image</em>' containment reference.
	 * @see #setMaskedImage(MaskedImageType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_MaskedImage()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='maskedImage' namespace='##targetNamespace'"
	 * @generated
	 */
	MaskedImageType getMaskedImage();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getMaskedImage <em>Masked Image</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Masked Image</em>' containment reference.
	 * @see #getMaskedImage()
	 * @generated
	 */
	void setMaskedImage(MaskedImageType value);

    /**
	 * Returns the value of the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Position</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' containment reference.
	 * @see #setPosition(PositionType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_Position()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='position' namespace='##targetNamespace'"
	 * @generated
	 */
	PositionType getPosition();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getPosition <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' containment reference.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(PositionType value);

    /**
	 * Returns the value of the '<em><b>Rectangle</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rectangle</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rectangle</em>' containment reference.
	 * @see #setRectangle(RectangleType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_Rectangle()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rectangle' namespace='##targetNamespace'"
	 * @generated
	 */
	RectangleType getRectangle();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getRectangle <em>Rectangle</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rectangle</em>' containment reference.
	 * @see #getRectangle()
	 * @generated
	 */
	void setRectangle(RectangleType value);

    /**
	 * Returns the value of the '<em><b>String</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>String</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>String</em>' containment reference.
	 * @see #setString(StringType)
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#getDocumentRoot_String()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='string' namespace='##targetNamespace'"
	 * @generated
	 */
    StringType getString();

    /**
	 * Sets the value of the '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getString <em>String</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String</em>' containment reference.
	 * @see #getString()
	 * @generated
	 */
    void setString(StringType value);

} // DocumentRoot
