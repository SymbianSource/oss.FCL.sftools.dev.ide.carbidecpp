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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LookAndFeelPackageImpl extends EPackageImpl implements LookAndFeelPackage {
    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass aliasTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dimensionTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fontTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass imageTypeEClass = null;

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass lookAndFeelTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass maskedImageTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass positionTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rectangleTypeEClass = null;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    private EClass stringTypeEClass = null;

    /**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.nokia.sdt.looknfeel.feel.LookAndFeelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LookAndFeelPackageImpl() {
		super(eNS_URI, LookAndFeelFactory.eINSTANCE);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

    /**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LookAndFeelPackage init() {
		if (isInited) return (LookAndFeelPackage)EPackage.Registry.INSTANCE.getEPackage(LookAndFeelPackage.eNS_URI);

		// Obtain or create and register package
		LookAndFeelPackageImpl theLookAndFeelPackage = (LookAndFeelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof LookAndFeelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new LookAndFeelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theLookAndFeelPackage.createPackageContents();

		// Initialize created meta-data
		theLookAndFeelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLookAndFeelPackage.freeze();

		return theLookAndFeelPackage;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAliasType() {
		return aliasTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAliasType_Value() {
		return (EAttribute)aliasTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAliasType_Key() {
		return (EAttribute)aliasTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAliasType_Ref() {
		return (EAttribute)aliasTypeEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanType() {
		return booleanTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanType_Key() {
		return (EAttribute)booleanTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanType_Value() {
		return (EAttribute)booleanTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColorType() {
		return colorTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColorType_B() {
		return (EAttribute)colorTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColorType_G() {
		return (EAttribute)colorTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColorType_Key() {
		return (EAttribute)colorTypeEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColorType_R() {
		return (EAttribute)colorTypeEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDimensionType() {
		return dimensionTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDimensionType_Height() {
		return (EAttribute)dimensionTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDimensionType_Key() {
		return (EAttribute)dimensionTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDimensionType_Width() {
		return (EAttribute)dimensionTypeEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Mixed() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XMLNSPrefixMap() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_XSISchemaLocation() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Boolean() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Color() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Dimension() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Font() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Image() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Integer() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_LookAndFeel() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_MaskedImage() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Position() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Rectangle() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getDocumentRoot_String() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(13);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFontType() {
		return fontTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFontType_InitData() {
		return (EAttribute)fontTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFontType_Key() {
		return (EAttribute)fontTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getFontType_Size() {
		return (EAttribute)fontTypeEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImageType() {
		return imageTypeEClass;
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImageType_ImageFile() {
		return (EAttribute)imageTypeEClass.getEStructuralFeatures().get(0);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImageType_Key() {
		return (EAttribute)imageTypeEClass.getEStructuralFeatures().get(1);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerType() {
		return integerTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerType_Key() {
		return (EAttribute)integerTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerType_Value() {
		return (EAttribute)integerTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLookAndFeelType() {
		return lookAndFeelTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getLookAndFeelType_Group() {
		return (EAttribute)lookAndFeelTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_Color() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_ColorAlias() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_Font() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_FontAlias() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_Image() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(5);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_MaskedImage() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(6);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_Position() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(7);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_PositionAlias() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(8);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_Dimension() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(9);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_DimensionAlias() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(10);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_Rectangle() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(11);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_RectangleAlias() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(12);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_Integer() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(13);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_IntegerAlias() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(14);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_Boolean() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(15);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLookAndFeelType_BooleanAlias() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(16);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getLookAndFeelType_String() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(17);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EReference getLookAndFeelType_StringAlias() {
		return (EReference)lookAndFeelTypeEClass.getEStructuralFeatures().get(18);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMaskedImageType() {
		return maskedImageTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMaskedImageType_ImageFile() {
		return (EAttribute)maskedImageTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMaskedImageType_Key() {
		return (EAttribute)maskedImageTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMaskedImageType_MaskFile() {
		return (EAttribute)maskedImageTypeEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPositionType() {
		return positionTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPositionType_Key() {
		return (EAttribute)positionTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPositionType_X() {
		return (EAttribute)positionTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPositionType_Y() {
		return (EAttribute)positionTypeEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRectangleType() {
		return rectangleTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRectangleType_Height() {
		return (EAttribute)rectangleTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRectangleType_Key() {
		return (EAttribute)rectangleTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRectangleType_Width() {
		return (EAttribute)rectangleTypeEClass.getEStructuralFeatures().get(2);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRectangleType_X() {
		return (EAttribute)rectangleTypeEClass.getEStructuralFeatures().get(3);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRectangleType_Y() {
		return (EAttribute)rectangleTypeEClass.getEStructuralFeatures().get(4);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EClass getStringType() {
		return stringTypeEClass;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getStringType_Key() {
		return (EAttribute)stringTypeEClass.getEStructuralFeatures().get(0);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EAttribute getStringType_Value() {
		return (EAttribute)stringTypeEClass.getEStructuralFeatures().get(1);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LookAndFeelFactory getLookAndFeelFactory() {
		return (LookAndFeelFactory)getEFactoryInstance();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

    /**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		aliasTypeEClass = createEClass(ALIAS_TYPE);
		createEAttribute(aliasTypeEClass, ALIAS_TYPE__VALUE);
		createEAttribute(aliasTypeEClass, ALIAS_TYPE__KEY);
		createEAttribute(aliasTypeEClass, ALIAS_TYPE__REF);

		booleanTypeEClass = createEClass(BOOLEAN_TYPE);
		createEAttribute(booleanTypeEClass, BOOLEAN_TYPE__KEY);
		createEAttribute(booleanTypeEClass, BOOLEAN_TYPE__VALUE);

		colorTypeEClass = createEClass(COLOR_TYPE);
		createEAttribute(colorTypeEClass, COLOR_TYPE__B);
		createEAttribute(colorTypeEClass, COLOR_TYPE__G);
		createEAttribute(colorTypeEClass, COLOR_TYPE__KEY);
		createEAttribute(colorTypeEClass, COLOR_TYPE__R);

		dimensionTypeEClass = createEClass(DIMENSION_TYPE);
		createEAttribute(dimensionTypeEClass, DIMENSION_TYPE__HEIGHT);
		createEAttribute(dimensionTypeEClass, DIMENSION_TYPE__KEY);
		createEAttribute(dimensionTypeEClass, DIMENSION_TYPE__WIDTH);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__MIXED);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		createEReference(documentRootEClass, DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BOOLEAN);
		createEReference(documentRootEClass, DOCUMENT_ROOT__COLOR);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DIMENSION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__FONT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__IMAGE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__INTEGER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__LOOK_AND_FEEL);
		createEReference(documentRootEClass, DOCUMENT_ROOT__MASKED_IMAGE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__POSITION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__RECTANGLE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__STRING);

		fontTypeEClass = createEClass(FONT_TYPE);
		createEAttribute(fontTypeEClass, FONT_TYPE__INIT_DATA);
		createEAttribute(fontTypeEClass, FONT_TYPE__KEY);
		createEAttribute(fontTypeEClass, FONT_TYPE__SIZE);

		imageTypeEClass = createEClass(IMAGE_TYPE);
		createEAttribute(imageTypeEClass, IMAGE_TYPE__IMAGE_FILE);
		createEAttribute(imageTypeEClass, IMAGE_TYPE__KEY);

		integerTypeEClass = createEClass(INTEGER_TYPE);
		createEAttribute(integerTypeEClass, INTEGER_TYPE__KEY);
		createEAttribute(integerTypeEClass, INTEGER_TYPE__VALUE);

		lookAndFeelTypeEClass = createEClass(LOOK_AND_FEEL_TYPE);
		createEAttribute(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__GROUP);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__COLOR);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__COLOR_ALIAS);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__FONT);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__FONT_ALIAS);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__IMAGE);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__MASKED_IMAGE);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__POSITION);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__POSITION_ALIAS);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__DIMENSION);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__DIMENSION_ALIAS);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__RECTANGLE);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__RECTANGLE_ALIAS);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__INTEGER);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__INTEGER_ALIAS);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__BOOLEAN);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__BOOLEAN_ALIAS);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__STRING);
		createEReference(lookAndFeelTypeEClass, LOOK_AND_FEEL_TYPE__STRING_ALIAS);

		maskedImageTypeEClass = createEClass(MASKED_IMAGE_TYPE);
		createEAttribute(maskedImageTypeEClass, MASKED_IMAGE_TYPE__IMAGE_FILE);
		createEAttribute(maskedImageTypeEClass, MASKED_IMAGE_TYPE__KEY);
		createEAttribute(maskedImageTypeEClass, MASKED_IMAGE_TYPE__MASK_FILE);

		positionTypeEClass = createEClass(POSITION_TYPE);
		createEAttribute(positionTypeEClass, POSITION_TYPE__KEY);
		createEAttribute(positionTypeEClass, POSITION_TYPE__X);
		createEAttribute(positionTypeEClass, POSITION_TYPE__Y);

		rectangleTypeEClass = createEClass(RECTANGLE_TYPE);
		createEAttribute(rectangleTypeEClass, RECTANGLE_TYPE__HEIGHT);
		createEAttribute(rectangleTypeEClass, RECTANGLE_TYPE__KEY);
		createEAttribute(rectangleTypeEClass, RECTANGLE_TYPE__WIDTH);
		createEAttribute(rectangleTypeEClass, RECTANGLE_TYPE__X);
		createEAttribute(rectangleTypeEClass, RECTANGLE_TYPE__Y);

		stringTypeEClass = createEClass(STRING_TYPE);
		createEAttribute(stringTypeEClass, STRING_TYPE__KEY);
		createEAttribute(stringTypeEClass, STRING_TYPE__VALUE);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

    /**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(aliasTypeEClass, AliasType.class, "AliasType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAliasType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, AliasType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAliasType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, AliasType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAliasType_Ref(), theXMLTypePackage.getString(), "ref", null, 1, 1, AliasType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanTypeEClass, BooleanType.class, "BooleanType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, BooleanType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBooleanType_Value(), theXMLTypePackage.getBoolean(), "value", null, 1, 1, BooleanType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(colorTypeEClass, ColorType.class, "ColorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColorType_B(), theXMLTypePackage.getShort(), "b", null, 1, 1, ColorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColorType_G(), theXMLTypePackage.getShort(), "g", null, 1, 1, ColorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColorType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, ColorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColorType_R(), theXMLTypePackage.getShort(), "r", null, 1, 1, ColorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dimensionTypeEClass, DimensionType.class, "DimensionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDimensionType_Height(), theXMLTypePackage.getShort(), "height", null, 1, 1, DimensionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDimensionType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, DimensionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDimensionType_Width(), theXMLTypePackage.getShort(), "width", null, 1, 1, DimensionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentRoot_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Boolean(), this.getBooleanType(), null, "boolean", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Color(), this.getColorType(), null, "color", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Dimension(), this.getDimensionType(), null, "dimension", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Font(), this.getFontType(), null, "font", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Image(), this.getImageType(), null, "image", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Integer(), this.getIntegerType(), null, "integer", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_LookAndFeel(), this.getLookAndFeelType(), null, "lookAndFeel", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_MaskedImage(), this.getMaskedImageType(), null, "maskedImage", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Position(), this.getPositionType(), null, "position", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Rectangle(), this.getRectangleType(), null, "rectangle", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_String(), this.getStringType(), null, "string", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(fontTypeEClass, FontType.class, "FontType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFontType_InitData(), theXMLTypePackage.getString(), "initData", null, 1, 1, FontType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFontType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, FontType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFontType_Size(), theXMLTypePackage.getShort(), "size", null, 1, 1, FontType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(imageTypeEClass, ImageType.class, "ImageType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImageType_ImageFile(), theXMLTypePackage.getString(), "imageFile", null, 1, 1, ImageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getImageType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, ImageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerTypeEClass, IntegerType.class, "IntegerType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, IntegerType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIntegerType_Value(), theXMLTypePackage.getShort(), "value", null, 1, 1, IntegerType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lookAndFeelTypeEClass, LookAndFeelType.class, "LookAndFeelType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLookAndFeelType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, LookAndFeelType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_Color(), this.getColorType(), null, "color", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_ColorAlias(), this.getAliasType(), null, "colorAlias", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_Font(), this.getFontType(), null, "font", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_FontAlias(), this.getAliasType(), null, "fontAlias", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_Image(), this.getImageType(), null, "image", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_MaskedImage(), this.getMaskedImageType(), null, "maskedImage", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_Position(), this.getPositionType(), null, "position", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_PositionAlias(), this.getAliasType(), null, "positionAlias", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_Dimension(), this.getDimensionType(), null, "dimension", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_DimensionAlias(), this.getAliasType(), null, "dimensionAlias", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_Rectangle(), this.getRectangleType(), null, "rectangle", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_RectangleAlias(), this.getAliasType(), null, "rectangleAlias", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_Integer(), this.getIntegerType(), null, "integer", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_IntegerAlias(), this.getAliasType(), null, "integerAlias", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_Boolean(), this.getBooleanType(), null, "boolean", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_BooleanAlias(), this.getAliasType(), null, "booleanAlias", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_String(), this.getStringType(), null, "string", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getLookAndFeelType_StringAlias(), this.getAliasType(), null, "stringAlias", null, 0, -1, LookAndFeelType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(maskedImageTypeEClass, MaskedImageType.class, "MaskedImageType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMaskedImageType_ImageFile(), theXMLTypePackage.getString(), "imageFile", null, 1, 1, MaskedImageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaskedImageType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, MaskedImageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMaskedImageType_MaskFile(), theXMLTypePackage.getString(), "maskFile", null, 1, 1, MaskedImageType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(positionTypeEClass, PositionType.class, "PositionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPositionType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, PositionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPositionType_X(), theXMLTypePackage.getShort(), "x", null, 1, 1, PositionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPositionType_Y(), theXMLTypePackage.getShort(), "y", null, 1, 1, PositionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rectangleTypeEClass, RectangleType.class, "RectangleType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRectangleType_Height(), theXMLTypePackage.getShort(), "height", null, 1, 1, RectangleType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRectangleType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, RectangleType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRectangleType_Width(), theXMLTypePackage.getShort(), "width", null, 1, 1, RectangleType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRectangleType_X(), theXMLTypePackage.getShort(), "x", null, 1, 1, RectangleType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRectangleType_Y(), theXMLTypePackage.getShort(), "y", null, 1, 1, RectangleType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringTypeEClass, StringType.class, "StringType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringType_Key(), theXMLTypePackage.getString(), "key", null, 1, 1, StringType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringType_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, StringType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

    /**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (aliasTypeEClass, 
		   source, 
		   new String[] {
			 "name", "aliasType",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getAliasType_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getAliasType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getAliasType_Ref(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ref"
		   });		
		addAnnotation
		  (booleanTypeEClass, 
		   source, 
		   new String[] {
			 "name", "boolean_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getBooleanType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getBooleanType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (colorTypeEClass, 
		   source, 
		   new String[] {
			 "name", "color_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getColorType_B(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "b"
		   });		
		addAnnotation
		  (getColorType_G(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "g"
		   });		
		addAnnotation
		  (getColorType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getColorType_R(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "r"
		   });		
		addAnnotation
		  (dimensionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "dimension_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getDimensionType_Height(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "height"
		   });		
		addAnnotation
		  (getDimensionType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getDimensionType_Width(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "width"
		   });		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });		
		addAnnotation
		  (getDocumentRoot_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });		
		addAnnotation
		  (getDocumentRoot_Boolean(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "boolean",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Color(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "color",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Dimension(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "dimension",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Font(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "font",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Image(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "image",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Integer(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "integer",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_LookAndFeel(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "lookAndFeel",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_MaskedImage(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "maskedImage",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Position(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "position",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Rectangle(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "rectangle",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_String(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "string",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (fontTypeEClass, 
		   source, 
		   new String[] {
			 "name", "font_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getFontType_InitData(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "initData"
		   });		
		addAnnotation
		  (getFontType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getFontType_Size(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "size"
		   });		
		addAnnotation
		  (imageTypeEClass, 
		   source, 
		   new String[] {
			 "name", "image_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getImageType_ImageFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "imageFile"
		   });		
		addAnnotation
		  (getImageType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (integerTypeEClass, 
		   source, 
		   new String[] {
			 "name", "integer_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getIntegerType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getIntegerType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (lookAndFeelTypeEClass, 
		   source, 
		   new String[] {
			 "name", "lookAndFeel_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getLookAndFeelType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_Color(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "color",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_ColorAlias(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "colorAlias",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_Font(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "font",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_FontAlias(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "fontAlias",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_Image(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "image",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_MaskedImage(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "maskedImage",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_Position(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "position",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_PositionAlias(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "positionAlias",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_Dimension(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "dimension",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_DimensionAlias(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "dimensionAlias",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_Rectangle(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "rectangle",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_RectangleAlias(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "rectangleAlias",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_Integer(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "integer",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_IntegerAlias(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "integerAlias",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_Boolean(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "boolean",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_BooleanAlias(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "booleanAlias",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_String(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "string",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (getLookAndFeelType_StringAlias(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "stringAlias",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (maskedImageTypeEClass, 
		   source, 
		   new String[] {
			 "name", "maskedImage_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getMaskedImageType_ImageFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "imageFile"
		   });		
		addAnnotation
		  (getMaskedImageType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getMaskedImageType_MaskFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "maskFile"
		   });		
		addAnnotation
		  (positionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "position_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getPositionType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getPositionType_X(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "x"
		   });		
		addAnnotation
		  (getPositionType_Y(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "y"
		   });		
		addAnnotation
		  (rectangleTypeEClass, 
		   source, 
		   new String[] {
			 "name", "rectangle_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getRectangleType_Height(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "height"
		   });		
		addAnnotation
		  (getRectangleType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getRectangleType_Width(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "width"
		   });		
		addAnnotation
		  (getRectangleType_X(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "x"
		   });		
		addAnnotation
		  (getRectangleType_Y(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "y"
		   });		
		addAnnotation
		  (stringTypeEClass, 
		   source, 
		   new String[] {
			 "name", "string_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getStringType_Key(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "key"
		   });		
		addAnnotation
		  (getStringType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });
	}

} //LookAndFeelPackageImpl
