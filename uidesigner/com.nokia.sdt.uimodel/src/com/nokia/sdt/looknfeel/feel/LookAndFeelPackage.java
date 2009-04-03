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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelFactory
 * @model kind="package"
 * @generated
 */
public interface LookAndFeelPackage extends EPackage{
    /**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "feel";

    /**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.nokia.com/sdt/lookAndFeel";

    /**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "feel";

    /**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LookAndFeelPackage eINSTANCE = com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl.init();

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.AliasTypeImpl <em>Alias Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.AliasTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getAliasType()
	 * @generated
	 */
	int ALIAS_TYPE = 0;

    /**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS_TYPE__VALUE = 0;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS_TYPE__KEY = 1;

    /**
	 * The feature id for the '<em><b>Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS_TYPE__REF = 2;

    /**
	 * The number of structural features of the '<em>Alias Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALIAS_TYPE_FEATURE_COUNT = 3;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.BooleanTypeImpl <em>Boolean Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.BooleanTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getBooleanType()
	 * @generated
	 */
	int BOOLEAN_TYPE = 1;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__KEY = 0;

    /**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE__VALUE = 1;

    /**
	 * The number of structural features of the '<em>Boolean Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_TYPE_FEATURE_COUNT = 2;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.ColorTypeImpl <em>Color Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.ColorTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getColorType()
	 * @generated
	 */
	int COLOR_TYPE = 2;

    /**
	 * The feature id for the '<em><b>B</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_TYPE__B = 0;

    /**
	 * The feature id for the '<em><b>G</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_TYPE__G = 1;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_TYPE__KEY = 2;

    /**
	 * The feature id for the '<em><b>R</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_TYPE__R = 3;

    /**
	 * The number of structural features of the '<em>Color Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_TYPE_FEATURE_COUNT = 4;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.DimensionTypeImpl <em>Dimension Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.DimensionTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getDimensionType()
	 * @generated
	 */
	int DIMENSION_TYPE = 3;

    /**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_TYPE__HEIGHT = 0;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_TYPE__KEY = 1;

    /**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_TYPE__WIDTH = 2;

    /**
	 * The number of structural features of the '<em>Dimension Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_TYPE_FEATURE_COUNT = 3;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 4;

    /**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

    /**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

    /**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

    /**
	 * The feature id for the '<em><b>Boolean</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BOOLEAN = 3;

    /**
	 * The feature id for the '<em><b>Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COLOR = 4;

    /**
	 * The feature id for the '<em><b>Dimension</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DIMENSION = 5;

    /**
	 * The feature id for the '<em><b>Font</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FONT = 6;

    /**
	 * The feature id for the '<em><b>Image</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMAGE = 7;

				/**
	 * The feature id for the '<em><b>Integer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INTEGER = 8;

    /**
	 * The feature id for the '<em><b>Look And Feel</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LOOK_AND_FEEL = 9;

    /**
	 * The feature id for the '<em><b>Masked Image</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MASKED_IMAGE = 10;

    /**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__POSITION = 11;

    /**
	 * The feature id for the '<em><b>Rectangle</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RECTANGLE = 12;

    /**
	 * The feature id for the '<em><b>String</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int DOCUMENT_ROOT__STRING = 13;

    /**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 14;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.FontTypeImpl <em>Font Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.FontTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getFontType()
	 * @generated
	 */
	int FONT_TYPE = 5;

    /**
	 * The feature id for the '<em><b>Init Data</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FONT_TYPE__INIT_DATA = 0;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FONT_TYPE__KEY = 1;

    /**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int FONT_TYPE__SIZE = 2;

    /**
	 * The number of structural features of the '<em>Font Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FONT_TYPE_FEATURE_COUNT = 3;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.ImageTypeImpl <em>Image Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.ImageTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getImageType()
	 * @generated
	 */
	int IMAGE_TYPE = 6;

				/**
	 * The feature id for the '<em><b>Image File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_TYPE__IMAGE_FILE = 0;

				/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_TYPE__KEY = 1;

				/**
	 * The number of structural features of the '<em>Image Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_TYPE_FEATURE_COUNT = 2;

				/**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.IntegerTypeImpl <em>Integer Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.IntegerTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getIntegerType()
	 * @generated
	 */
	int INTEGER_TYPE = 7;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__KEY = 0;

    /**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE__VALUE = 1;

    /**
	 * The number of structural features of the '<em>Integer Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_TYPE_FEATURE_COUNT = 2;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getLookAndFeelType()
	 * @generated
	 */
	int LOOK_AND_FEEL_TYPE = 8;

    /**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LOOK_AND_FEEL_TYPE__GROUP = 0;

    /**
	 * The feature id for the '<em><b>Color</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__COLOR = 1;

    /**
	 * The feature id for the '<em><b>Color Alias</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__COLOR_ALIAS = 2;

    /**
	 * The feature id for the '<em><b>Font</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__FONT = 3;

    /**
	 * The feature id for the '<em><b>Font Alias</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__FONT_ALIAS = 4;

    /**
	 * The feature id for the '<em><b>Image</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__IMAGE = 5;

				/**
	 * The feature id for the '<em><b>Masked Image</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__MASKED_IMAGE = 6;

    /**
	 * The feature id for the '<em><b>Position</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__POSITION = 7;

    /**
	 * The feature id for the '<em><b>Position Alias</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__POSITION_ALIAS = 8;

    /**
	 * The feature id for the '<em><b>Dimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__DIMENSION = 9;

    /**
	 * The feature id for the '<em><b>Dimension Alias</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__DIMENSION_ALIAS = 10;

    /**
	 * The feature id for the '<em><b>Rectangle</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__RECTANGLE = 11;

    /**
	 * The feature id for the '<em><b>Rectangle Alias</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__RECTANGLE_ALIAS = 12;

    /**
	 * The feature id for the '<em><b>Integer</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__INTEGER = 13;

    /**
	 * The feature id for the '<em><b>Integer Alias</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__INTEGER_ALIAS = 14;

    /**
	 * The feature id for the '<em><b>Boolean</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__BOOLEAN = 15;

    /**
	 * The feature id for the '<em><b>Boolean Alias</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE__BOOLEAN_ALIAS = 16;

    /**
	 * The feature id for the '<em><b>String</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LOOK_AND_FEEL_TYPE__STRING = 17;

    /**
	 * The feature id for the '<em><b>String Alias</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LOOK_AND_FEEL_TYPE__STRING_ALIAS = 18;

    /**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOOK_AND_FEEL_TYPE_FEATURE_COUNT = 19;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.MaskedImageTypeImpl <em>Masked Image Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.MaskedImageTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getMaskedImageType()
	 * @generated
	 */
	int MASKED_IMAGE_TYPE = 9;

    /**
	 * The feature id for the '<em><b>Image File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MASKED_IMAGE_TYPE__IMAGE_FILE = 0;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MASKED_IMAGE_TYPE__KEY = 1;

    /**
	 * The feature id for the '<em><b>Mask File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MASKED_IMAGE_TYPE__MASK_FILE = 2;

    /**
	 * The number of structural features of the '<em>Masked Image Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MASKED_IMAGE_TYPE_FEATURE_COUNT = 3;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.PositionTypeImpl <em>Position Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.PositionTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getPositionType()
	 * @generated
	 */
	int POSITION_TYPE = 10;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_TYPE__KEY = 0;

    /**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_TYPE__X = 1;

    /**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_TYPE__Y = 2;

    /**
	 * The number of structural features of the '<em>Position Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_TYPE_FEATURE_COUNT = 3;

    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.RectangleTypeImpl <em>Rectangle Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.RectangleTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getRectangleType()
	 * @generated
	 */
	int RECTANGLE_TYPE = 11;

    /**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGLE_TYPE__HEIGHT = 0;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGLE_TYPE__KEY = 1;

    /**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGLE_TYPE__WIDTH = 2;

    /**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGLE_TYPE__X = 3;

    /**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGLE_TYPE__Y = 4;

    /**
	 * The number of structural features of the '<em>Rectangle Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECTANGLE_TYPE_FEATURE_COUNT = 5;


    /**
	 * The meta object id for the '{@link com.nokia.sdt.looknfeel.feel.impl.StringTypeImpl <em>String Type</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see com.nokia.sdt.looknfeel.feel.impl.StringTypeImpl
	 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getStringType()
	 * @generated
	 */
    int STRING_TYPE = 12;

    /**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int STRING_TYPE__KEY = 0;

    /**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int STRING_TYPE__VALUE = 1;

    /**
	 * The number of structural features of the '<em>String Type</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int STRING_TYPE_FEATURE_COUNT = 2;


    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.AliasType <em>Alias Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Alias Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.AliasType
	 * @generated
	 */
	EClass getAliasType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.AliasType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.AliasType#getValue()
	 * @see #getAliasType()
	 * @generated
	 */
	EAttribute getAliasType_Value();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.AliasType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.AliasType#getKey()
	 * @see #getAliasType()
	 * @generated
	 */
	EAttribute getAliasType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.AliasType#getRef <em>Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ref</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.AliasType#getRef()
	 * @see #getAliasType()
	 * @generated
	 */
	EAttribute getAliasType_Ref();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.BooleanType <em>Boolean Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.BooleanType
	 * @generated
	 */
	EClass getBooleanType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.BooleanType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.BooleanType#getKey()
	 * @see #getBooleanType()
	 * @generated
	 */
	EAttribute getBooleanType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.BooleanType#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.BooleanType#isValue()
	 * @see #getBooleanType()
	 * @generated
	 */
	EAttribute getBooleanType_Value();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.ColorType <em>Color Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Color Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.ColorType
	 * @generated
	 */
	EClass getColorType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.ColorType#getB <em>B</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>B</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.ColorType#getB()
	 * @see #getColorType()
	 * @generated
	 */
	EAttribute getColorType_B();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.ColorType#getG <em>G</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>G</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.ColorType#getG()
	 * @see #getColorType()
	 * @generated
	 */
	EAttribute getColorType_G();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.ColorType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.ColorType#getKey()
	 * @see #getColorType()
	 * @generated
	 */
	EAttribute getColorType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.ColorType#getR <em>R</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>R</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.ColorType#getR()
	 * @see #getColorType()
	 * @generated
	 */
	EAttribute getColorType_R();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.DimensionType <em>Dimension Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DimensionType
	 * @generated
	 */
	EClass getDimensionType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.DimensionType#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DimensionType#getHeight()
	 * @see #getDimensionType()
	 * @generated
	 */
	EAttribute getDimensionType_Height();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.DimensionType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DimensionType#getKey()
	 * @see #getDimensionType()
	 * @generated
	 */
	EAttribute getDimensionType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.DimensionType#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DimensionType#getWidth()
	 * @see #getDimensionType()
	 * @generated
	 */
	EAttribute getDimensionType_Width();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

    /**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

    /**
	 * Returns the meta object for the map '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

    /**
	 * Returns the meta object for the map '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getBoolean <em>Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Boolean</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getBoolean()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Boolean();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Color</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getColor()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Color();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Dimension</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getDimension()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Dimension();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getFont <em>Font</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Font</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getFont()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Font();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getImage <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Image</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getImage()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Image();

				/**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getInteger <em>Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Integer</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getInteger()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Integer();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getLookAndFeel <em>Look And Feel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Look And Feel</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getLookAndFeel()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_LookAndFeel();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getMaskedImage <em>Masked Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Masked Image</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getMaskedImage()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_MaskedImage();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Position</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getPosition()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Position();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getRectangle <em>Rectangle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rectangle</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getRectangle()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Rectangle();

    /**
	 * Returns the meta object for the containment reference '{@link com.nokia.sdt.looknfeel.feel.DocumentRoot#getString <em>String</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>String</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.DocumentRoot#getString()
	 * @see #getDocumentRoot()
	 * @generated
	 */
    EReference getDocumentRoot_String();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.FontType <em>Font Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Font Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.FontType
	 * @generated
	 */
	EClass getFontType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.FontType#getInitData <em>Init Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Init Data</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.FontType#getInitData()
	 * @see #getFontType()
	 * @generated
	 */
	EAttribute getFontType_InitData();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.FontType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.FontType#getKey()
	 * @see #getFontType()
	 * @generated
	 */
	EAttribute getFontType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.FontType#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.FontType#getSize()
	 * @see #getFontType()
	 * @generated
	 */
    EAttribute getFontType_Size();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.ImageType <em>Image Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Image Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.ImageType
	 * @generated
	 */
	EClass getImageType();

				/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.ImageType#getImageFile <em>Image File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image File</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.ImageType#getImageFile()
	 * @see #getImageType()
	 * @generated
	 */
	EAttribute getImageType_ImageFile();

				/**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.ImageType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.ImageType#getKey()
	 * @see #getImageType()
	 * @generated
	 */
	EAttribute getImageType_Key();

				/**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.IntegerType <em>Integer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.IntegerType
	 * @generated
	 */
	EClass getIntegerType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.IntegerType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.IntegerType#getKey()
	 * @see #getIntegerType()
	 * @generated
	 */
	EAttribute getIntegerType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.IntegerType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.IntegerType#getValue()
	 * @see #getIntegerType()
	 * @generated
	 */
	EAttribute getIntegerType_Value();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType
	 * @generated
	 */
	EClass getLookAndFeelType();

    /**
	 * Returns the meta object for the attribute list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getGroup()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
    EAttribute getLookAndFeelType_Group();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Color</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getColor()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_Color();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getColorAlias <em>Color Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Color Alias</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getColorAlias()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_ColorAlias();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getFont <em>Font</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Font</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getFont()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_Font();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getFontAlias <em>Font Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Font Alias</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getFontAlias()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_FontAlias();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getImage <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Image</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getImage()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_Image();

				/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getMaskedImage <em>Masked Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Masked Image</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getMaskedImage()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_MaskedImage();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Position</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getPosition()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_Position();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getPositionAlias <em>Position Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Position Alias</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getPositionAlias()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_PositionAlias();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getDimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dimension</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getDimension()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_Dimension();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getDimensionAlias <em>Dimension Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Dimension Alias</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getDimensionAlias()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_DimensionAlias();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getRectangle <em>Rectangle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rectangle</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getRectangle()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_Rectangle();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getRectangleAlias <em>Rectangle Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rectangle Alias</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getRectangleAlias()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_RectangleAlias();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getInteger <em>Integer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Integer</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getInteger()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_Integer();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getIntegerAlias <em>Integer Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Integer Alias</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getIntegerAlias()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_IntegerAlias();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getBoolean <em>Boolean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Boolean</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getBoolean()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_Boolean();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getBooleanAlias <em>Boolean Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Boolean Alias</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getBooleanAlias()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
	EReference getLookAndFeelType_BooleanAlias();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getString <em>String</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>String</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getString()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
    EReference getLookAndFeelType_String();

    /**
	 * Returns the meta object for the containment reference list '{@link com.nokia.sdt.looknfeel.feel.LookAndFeelType#getStringAlias <em>String Alias</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>String Alias</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelType#getStringAlias()
	 * @see #getLookAndFeelType()
	 * @generated
	 */
    EReference getLookAndFeelType_StringAlias();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.MaskedImageType <em>Masked Image Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Masked Image Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.MaskedImageType
	 * @generated
	 */
	EClass getMaskedImageType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.MaskedImageType#getImageFile <em>Image File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image File</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.MaskedImageType#getImageFile()
	 * @see #getMaskedImageType()
	 * @generated
	 */
	EAttribute getMaskedImageType_ImageFile();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.MaskedImageType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.MaskedImageType#getKey()
	 * @see #getMaskedImageType()
	 * @generated
	 */
	EAttribute getMaskedImageType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.MaskedImageType#getMaskFile <em>Mask File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mask File</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.MaskedImageType#getMaskFile()
	 * @see #getMaskedImageType()
	 * @generated
	 */
	EAttribute getMaskedImageType_MaskFile();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.PositionType <em>Position Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Position Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.PositionType
	 * @generated
	 */
	EClass getPositionType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.PositionType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.PositionType#getKey()
	 * @see #getPositionType()
	 * @generated
	 */
	EAttribute getPositionType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.PositionType#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.PositionType#getX()
	 * @see #getPositionType()
	 * @generated
	 */
	EAttribute getPositionType_X();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.PositionType#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.PositionType#getY()
	 * @see #getPositionType()
	 * @generated
	 */
	EAttribute getPositionType_Y();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.RectangleType <em>Rectangle Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rectangle Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.RectangleType
	 * @generated
	 */
	EClass getRectangleType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.RectangleType#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.RectangleType#getHeight()
	 * @see #getRectangleType()
	 * @generated
	 */
	EAttribute getRectangleType_Height();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.RectangleType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.RectangleType#getKey()
	 * @see #getRectangleType()
	 * @generated
	 */
	EAttribute getRectangleType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.RectangleType#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.RectangleType#getWidth()
	 * @see #getRectangleType()
	 * @generated
	 */
	EAttribute getRectangleType_Width();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.RectangleType#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.RectangleType#getX()
	 * @see #getRectangleType()
	 * @generated
	 */
	EAttribute getRectangleType_X();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.RectangleType#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.RectangleType#getY()
	 * @see #getRectangleType()
	 * @generated
	 */
	EAttribute getRectangleType_Y();

    /**
	 * Returns the meta object for class '{@link com.nokia.sdt.looknfeel.feel.StringType <em>String Type</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Type</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.StringType
	 * @generated
	 */
    EClass getStringType();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.StringType#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.StringType#getKey()
	 * @see #getStringType()
	 * @generated
	 */
    EAttribute getStringType_Key();

    /**
	 * Returns the meta object for the attribute '{@link com.nokia.sdt.looknfeel.feel.StringType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.nokia.sdt.looknfeel.feel.StringType#getValue()
	 * @see #getStringType()
	 * @generated
	 */
    EAttribute getStringType_Value();

    /**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LookAndFeelFactory getLookAndFeelFactory();

				/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.AliasTypeImpl <em>Alias Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.AliasTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getAliasType()
		 * @generated
		 */
		EClass ALIAS_TYPE = eINSTANCE.getAliasType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS_TYPE__VALUE = eINSTANCE.getAliasType_Value();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS_TYPE__KEY = eINSTANCE.getAliasType_Key();

		/**
		 * The meta object literal for the '<em><b>Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALIAS_TYPE__REF = eINSTANCE.getAliasType_Ref();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.BooleanTypeImpl <em>Boolean Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.BooleanTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getBooleanType()
		 * @generated
		 */
		EClass BOOLEAN_TYPE = eINSTANCE.getBooleanType();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_TYPE__KEY = eINSTANCE.getBooleanType_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_TYPE__VALUE = eINSTANCE.getBooleanType_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.ColorTypeImpl <em>Color Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.ColorTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getColorType()
		 * @generated
		 */
		EClass COLOR_TYPE = eINSTANCE.getColorType();

		/**
		 * The meta object literal for the '<em><b>B</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR_TYPE__B = eINSTANCE.getColorType_B();

		/**
		 * The meta object literal for the '<em><b>G</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR_TYPE__G = eINSTANCE.getColorType_G();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR_TYPE__KEY = eINSTANCE.getColorType_Key();

		/**
		 * The meta object literal for the '<em><b>R</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR_TYPE__R = eINSTANCE.getColorType_R();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.DimensionTypeImpl <em>Dimension Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.DimensionTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getDimensionType()
		 * @generated
		 */
		EClass DIMENSION_TYPE = eINSTANCE.getDimensionType();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIMENSION_TYPE__HEIGHT = eINSTANCE.getDimensionType_Height();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIMENSION_TYPE__KEY = eINSTANCE.getDimensionType_Key();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIMENSION_TYPE__WIDTH = eINSTANCE.getDimensionType_Width();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Boolean</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BOOLEAN = eINSTANCE.getDocumentRoot_Boolean();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__COLOR = eINSTANCE.getDocumentRoot_Color();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DIMENSION = eINSTANCE.getDocumentRoot_Dimension();

		/**
		 * The meta object literal for the '<em><b>Font</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FONT = eINSTANCE.getDocumentRoot_Font();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__IMAGE = eINSTANCE.getDocumentRoot_Image();

		/**
		 * The meta object literal for the '<em><b>Integer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__INTEGER = eINSTANCE.getDocumentRoot_Integer();

		/**
		 * The meta object literal for the '<em><b>Look And Feel</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__LOOK_AND_FEEL = eINSTANCE.getDocumentRoot_LookAndFeel();

		/**
		 * The meta object literal for the '<em><b>Masked Image</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__MASKED_IMAGE = eINSTANCE.getDocumentRoot_MaskedImage();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__POSITION = eINSTANCE.getDocumentRoot_Position();

		/**
		 * The meta object literal for the '<em><b>Rectangle</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__RECTANGLE = eINSTANCE.getDocumentRoot_Rectangle();

		/**
		 * The meta object literal for the '<em><b>String</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__STRING = eINSTANCE.getDocumentRoot_String();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.FontTypeImpl <em>Font Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.FontTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getFontType()
		 * @generated
		 */
		EClass FONT_TYPE = eINSTANCE.getFontType();

		/**
		 * The meta object literal for the '<em><b>Init Data</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FONT_TYPE__INIT_DATA = eINSTANCE.getFontType_InitData();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FONT_TYPE__KEY = eINSTANCE.getFontType_Key();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FONT_TYPE__SIZE = eINSTANCE.getFontType_Size();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.ImageTypeImpl <em>Image Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.ImageTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getImageType()
		 * @generated
		 */
		EClass IMAGE_TYPE = eINSTANCE.getImageType();

		/**
		 * The meta object literal for the '<em><b>Image File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMAGE_TYPE__IMAGE_FILE = eINSTANCE.getImageType_ImageFile();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMAGE_TYPE__KEY = eINSTANCE.getImageType_Key();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.IntegerTypeImpl <em>Integer Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.IntegerTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getIntegerType()
		 * @generated
		 */
		EClass INTEGER_TYPE = eINSTANCE.getIntegerType();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_TYPE__KEY = eINSTANCE.getIntegerType_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_TYPE__VALUE = eINSTANCE.getIntegerType_Value();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getLookAndFeelType()
		 * @generated
		 */
		EClass LOOK_AND_FEEL_TYPE = eINSTANCE.getLookAndFeelType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOOK_AND_FEEL_TYPE__GROUP = eINSTANCE.getLookAndFeelType_Group();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__COLOR = eINSTANCE.getLookAndFeelType_Color();

		/**
		 * The meta object literal for the '<em><b>Color Alias</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__COLOR_ALIAS = eINSTANCE.getLookAndFeelType_ColorAlias();

		/**
		 * The meta object literal for the '<em><b>Font</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__FONT = eINSTANCE.getLookAndFeelType_Font();

		/**
		 * The meta object literal for the '<em><b>Font Alias</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__FONT_ALIAS = eINSTANCE.getLookAndFeelType_FontAlias();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__IMAGE = eINSTANCE.getLookAndFeelType_Image();

		/**
		 * The meta object literal for the '<em><b>Masked Image</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__MASKED_IMAGE = eINSTANCE.getLookAndFeelType_MaskedImage();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__POSITION = eINSTANCE.getLookAndFeelType_Position();

		/**
		 * The meta object literal for the '<em><b>Position Alias</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__POSITION_ALIAS = eINSTANCE.getLookAndFeelType_PositionAlias();

		/**
		 * The meta object literal for the '<em><b>Dimension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__DIMENSION = eINSTANCE.getLookAndFeelType_Dimension();

		/**
		 * The meta object literal for the '<em><b>Dimension Alias</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__DIMENSION_ALIAS = eINSTANCE.getLookAndFeelType_DimensionAlias();

		/**
		 * The meta object literal for the '<em><b>Rectangle</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__RECTANGLE = eINSTANCE.getLookAndFeelType_Rectangle();

		/**
		 * The meta object literal for the '<em><b>Rectangle Alias</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__RECTANGLE_ALIAS = eINSTANCE.getLookAndFeelType_RectangleAlias();

		/**
		 * The meta object literal for the '<em><b>Integer</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__INTEGER = eINSTANCE.getLookAndFeelType_Integer();

		/**
		 * The meta object literal for the '<em><b>Integer Alias</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__INTEGER_ALIAS = eINSTANCE.getLookAndFeelType_IntegerAlias();

		/**
		 * The meta object literal for the '<em><b>Boolean</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__BOOLEAN = eINSTANCE.getLookAndFeelType_Boolean();

		/**
		 * The meta object literal for the '<em><b>Boolean Alias</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__BOOLEAN_ALIAS = eINSTANCE.getLookAndFeelType_BooleanAlias();

		/**
		 * The meta object literal for the '<em><b>String</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__STRING = eINSTANCE.getLookAndFeelType_String();

		/**
		 * The meta object literal for the '<em><b>String Alias</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LOOK_AND_FEEL_TYPE__STRING_ALIAS = eINSTANCE.getLookAndFeelType_StringAlias();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.MaskedImageTypeImpl <em>Masked Image Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.MaskedImageTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getMaskedImageType()
		 * @generated
		 */
		EClass MASKED_IMAGE_TYPE = eINSTANCE.getMaskedImageType();

		/**
		 * The meta object literal for the '<em><b>Image File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MASKED_IMAGE_TYPE__IMAGE_FILE = eINSTANCE.getMaskedImageType_ImageFile();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MASKED_IMAGE_TYPE__KEY = eINSTANCE.getMaskedImageType_Key();

		/**
		 * The meta object literal for the '<em><b>Mask File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MASKED_IMAGE_TYPE__MASK_FILE = eINSTANCE.getMaskedImageType_MaskFile();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.PositionTypeImpl <em>Position Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.PositionTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getPositionType()
		 * @generated
		 */
		EClass POSITION_TYPE = eINSTANCE.getPositionType();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_TYPE__KEY = eINSTANCE.getPositionType_Key();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_TYPE__X = eINSTANCE.getPositionType_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION_TYPE__Y = eINSTANCE.getPositionType_Y();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.RectangleTypeImpl <em>Rectangle Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.RectangleTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getRectangleType()
		 * @generated
		 */
		EClass RECTANGLE_TYPE = eINSTANCE.getRectangleType();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECTANGLE_TYPE__HEIGHT = eINSTANCE.getRectangleType_Height();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECTANGLE_TYPE__KEY = eINSTANCE.getRectangleType_Key();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECTANGLE_TYPE__WIDTH = eINSTANCE.getRectangleType_Width();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECTANGLE_TYPE__X = eINSTANCE.getRectangleType_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RECTANGLE_TYPE__Y = eINSTANCE.getRectangleType_Y();

		/**
		 * The meta object literal for the '{@link com.nokia.sdt.looknfeel.feel.impl.StringTypeImpl <em>String Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.sdt.looknfeel.feel.impl.StringTypeImpl
		 * @see com.nokia.sdt.looknfeel.feel.impl.LookAndFeelPackageImpl#getStringType()
		 * @generated
		 */
		EClass STRING_TYPE = eINSTANCE.getStringType();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TYPE__KEY = eINSTANCE.getStringType_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TYPE__VALUE = eINSTANCE.getStringType_Value();

	}

} //LookAndFeelPackage
