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

package com.nokia.sdt.looknfeel.feel.impl;

import com.nokia.sdt.looknfeel.feel.AliasType;
import com.nokia.sdt.looknfeel.feel.BooleanType;
import com.nokia.sdt.looknfeel.feel.ColorType;
import com.nokia.sdt.looknfeel.feel.DimensionType;
import com.nokia.sdt.looknfeel.feel.DocumentRoot;
import com.nokia.sdt.looknfeel.feel.FontType;
import com.nokia.sdt.looknfeel.feel.ImageType;
import com.nokia.sdt.looknfeel.feel.IntegerType;
import com.nokia.sdt.looknfeel.feel.LookAndFeelFactory;
import com.nokia.sdt.looknfeel.feel.LookAndFeelPackage;
import com.nokia.sdt.looknfeel.feel.LookAndFeelType;
import com.nokia.sdt.looknfeel.feel.MaskedImageType;
import com.nokia.sdt.looknfeel.feel.PositionType;
import com.nokia.sdt.looknfeel.feel.RectangleType;
import com.nokia.sdt.looknfeel.feel.StringType;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LookAndFeelFactoryImpl extends EFactoryImpl implements LookAndFeelFactory {
    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LookAndFeelFactory init() {
		try {
			LookAndFeelFactory theLookAndFeelFactory = (LookAndFeelFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.nokia.com/sdt/lookAndFeel"); 
			if (theLookAndFeelFactory != null) {
				return theLookAndFeelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LookAndFeelFactoryImpl();
	}

				/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LookAndFeelFactoryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case LookAndFeelPackage.ALIAS_TYPE: return createAliasType();
			case LookAndFeelPackage.BOOLEAN_TYPE: return createBooleanType();
			case LookAndFeelPackage.COLOR_TYPE: return createColorType();
			case LookAndFeelPackage.DIMENSION_TYPE: return createDimensionType();
			case LookAndFeelPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case LookAndFeelPackage.FONT_TYPE: return createFontType();
			case LookAndFeelPackage.IMAGE_TYPE: return createImageType();
			case LookAndFeelPackage.INTEGER_TYPE: return createIntegerType();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE: return createLookAndFeelType();
			case LookAndFeelPackage.MASKED_IMAGE_TYPE: return createMaskedImageType();
			case LookAndFeelPackage.POSITION_TYPE: return createPositionType();
			case LookAndFeelPackage.RECTANGLE_TYPE: return createRectangleType();
			case LookAndFeelPackage.STRING_TYPE: return createStringType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AliasType createAliasType() {
		AliasTypeImpl aliasType = new AliasTypeImpl();
		return aliasType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanType createBooleanType() {
		BooleanTypeImpl booleanType = new BooleanTypeImpl();
		return booleanType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColorType createColorType() {
		ColorTypeImpl colorType = new ColorTypeImpl();
		return colorType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DimensionType createDimensionType() {
		DimensionTypeImpl dimensionType = new DimensionTypeImpl();
		return dimensionType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FontType createFontType() {
		FontTypeImpl fontType = new FontTypeImpl();
		return fontType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImageType createImageType() {
		ImageTypeImpl imageType = new ImageTypeImpl();
		return imageType;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerType createIntegerType() {
		IntegerTypeImpl integerType = new IntegerTypeImpl();
		return integerType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LookAndFeelType createLookAndFeelType() {
		LookAndFeelTypeImpl lookAndFeelType = new LookAndFeelTypeImpl();
		return lookAndFeelType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MaskedImageType createMaskedImageType() {
		MaskedImageTypeImpl maskedImageType = new MaskedImageTypeImpl();
		return maskedImageType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PositionType createPositionType() {
		PositionTypeImpl positionType = new PositionTypeImpl();
		return positionType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RectangleType createRectangleType() {
		RectangleTypeImpl rectangleType = new RectangleTypeImpl();
		return rectangleType;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public StringType createStringType() {
		StringTypeImpl stringType = new StringTypeImpl();
		return stringType;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LookAndFeelPackage getLookAndFeelPackage() {
		return (LookAndFeelPackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static LookAndFeelPackage getPackage() {
		return LookAndFeelPackage.eINSTANCE;
	}

} //LookAndFeelFactoryImpl
