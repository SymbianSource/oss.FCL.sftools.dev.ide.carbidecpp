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

import com.nokia.sdt.looknfeel.feel.BooleanType;
import com.nokia.sdt.looknfeel.feel.ColorType;
import com.nokia.sdt.looknfeel.feel.DimensionType;
import com.nokia.sdt.looknfeel.feel.DocumentRoot;
import com.nokia.sdt.looknfeel.feel.FontType;
import com.nokia.sdt.looknfeel.feel.ImageType;
import com.nokia.sdt.looknfeel.feel.IntegerType;
import com.nokia.sdt.looknfeel.feel.LookAndFeelPackage;
import com.nokia.sdt.looknfeel.feel.LookAndFeelType;
import com.nokia.sdt.looknfeel.feel.MaskedImageType;
import com.nokia.sdt.looknfeel.feel.PositionType;
import com.nokia.sdt.looknfeel.feel.RectangleType;
import com.nokia.sdt.looknfeel.feel.StringType;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getColor <em>Color</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getDimension <em>Dimension</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getFont <em>Font</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getImage <em>Image</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getInteger <em>Integer</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getLookAndFeel <em>Look And Feel</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getMaskedImage <em>Masked Image</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getRectangle <em>Rectangle</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.DocumentRootImpl#getString <em>String</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends EObjectImpl implements DocumentRoot {
    /**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

    /**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap xMLNSPrefixMap;

    /**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap xSISchemaLocation;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return LookAndFeelPackage.Literals.DOCUMENT_ROOT;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, LookAndFeelPackage.DOCUMENT_ROOT__MIXED);
		}
		return mixed;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, LookAndFeelPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, LookAndFeelPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanType getBoolean() {
		return (BooleanType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__BOOLEAN, true);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBoolean(BooleanType newBoolean, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__BOOLEAN, newBoolean, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBoolean(BooleanType newBoolean) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__BOOLEAN, newBoolean);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColorType getColor() {
		return (ColorType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__COLOR, true);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColor(ColorType newColor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__COLOR, newColor, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColor(ColorType newColor) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__COLOR, newColor);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DimensionType getDimension() {
		return (DimensionType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__DIMENSION, true);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDimension(DimensionType newDimension, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__DIMENSION, newDimension, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDimension(DimensionType newDimension) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__DIMENSION, newDimension);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FontType getFont() {
		return (FontType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__FONT, true);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFont(FontType newFont, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__FONT, newFont, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFont(FontType newFont) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__FONT, newFont);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImageType getImage() {
		return (ImageType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__IMAGE, true);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetImage(ImageType newImage, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__IMAGE, newImage, msgs);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImage(ImageType newImage) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__IMAGE, newImage);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerType getInteger() {
		return (IntegerType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__INTEGER, true);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInteger(IntegerType newInteger, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__INTEGER, newInteger, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInteger(IntegerType newInteger) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__INTEGER, newInteger);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LookAndFeelType getLookAndFeel() {
		return (LookAndFeelType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__LOOK_AND_FEEL, true);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLookAndFeel(LookAndFeelType newLookAndFeel, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__LOOK_AND_FEEL, newLookAndFeel, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLookAndFeel(LookAndFeelType newLookAndFeel) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__LOOK_AND_FEEL, newLookAndFeel);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MaskedImageType getMaskedImage() {
		return (MaskedImageType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__MASKED_IMAGE, true);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaskedImage(MaskedImageType newMaskedImage, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__MASKED_IMAGE, newMaskedImage, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaskedImage(MaskedImageType newMaskedImage) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__MASKED_IMAGE, newMaskedImage);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PositionType getPosition() {
		return (PositionType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__POSITION, true);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPosition(PositionType newPosition, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__POSITION, newPosition, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPosition(PositionType newPosition) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__POSITION, newPosition);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RectangleType getRectangle() {
		return (RectangleType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__RECTANGLE, true);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRectangle(RectangleType newRectangle, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__RECTANGLE, newRectangle, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRectangle(RectangleType newRectangle) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__RECTANGLE, newRectangle);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public StringType getString() {
		return (StringType)getMixed().get(LookAndFeelPackage.Literals.DOCUMENT_ROOT__STRING, true);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetString(StringType newString, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(LookAndFeelPackage.Literals.DOCUMENT_ROOT__STRING, newString, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setString(StringType newString) {
		((FeatureMap.Internal)getMixed()).set(LookAndFeelPackage.Literals.DOCUMENT_ROOT__STRING, newString);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LookAndFeelPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__BOOLEAN:
				return basicSetBoolean(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__COLOR:
				return basicSetColor(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__DIMENSION:
				return basicSetDimension(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__FONT:
				return basicSetFont(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__IMAGE:
				return basicSetImage(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__INTEGER:
				return basicSetInteger(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__LOOK_AND_FEEL:
				return basicSetLookAndFeel(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__MASKED_IMAGE:
				return basicSetMaskedImage(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__POSITION:
				return basicSetPosition(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__RECTANGLE:
				return basicSetRectangle(null, msgs);
			case LookAndFeelPackage.DOCUMENT_ROOT__STRING:
				return basicSetString(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LookAndFeelPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case LookAndFeelPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case LookAndFeelPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case LookAndFeelPackage.DOCUMENT_ROOT__BOOLEAN:
				return getBoolean();
			case LookAndFeelPackage.DOCUMENT_ROOT__COLOR:
				return getColor();
			case LookAndFeelPackage.DOCUMENT_ROOT__DIMENSION:
				return getDimension();
			case LookAndFeelPackage.DOCUMENT_ROOT__FONT:
				return getFont();
			case LookAndFeelPackage.DOCUMENT_ROOT__IMAGE:
				return getImage();
			case LookAndFeelPackage.DOCUMENT_ROOT__INTEGER:
				return getInteger();
			case LookAndFeelPackage.DOCUMENT_ROOT__LOOK_AND_FEEL:
				return getLookAndFeel();
			case LookAndFeelPackage.DOCUMENT_ROOT__MASKED_IMAGE:
				return getMaskedImage();
			case LookAndFeelPackage.DOCUMENT_ROOT__POSITION:
				return getPosition();
			case LookAndFeelPackage.DOCUMENT_ROOT__RECTANGLE:
				return getRectangle();
			case LookAndFeelPackage.DOCUMENT_ROOT__STRING:
				return getString();
		}
		return super.eGet(featureID, resolve, coreType);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LookAndFeelPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__BOOLEAN:
				setBoolean((BooleanType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__COLOR:
				setColor((ColorType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__DIMENSION:
				setDimension((DimensionType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__FONT:
				setFont((FontType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__IMAGE:
				setImage((ImageType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__INTEGER:
				setInteger((IntegerType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__LOOK_AND_FEEL:
				setLookAndFeel((LookAndFeelType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__MASKED_IMAGE:
				setMaskedImage((MaskedImageType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__POSITION:
				setPosition((PositionType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__RECTANGLE:
				setRectangle((RectangleType)newValue);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__STRING:
				setString((StringType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case LookAndFeelPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__BOOLEAN:
				setBoolean((BooleanType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__COLOR:
				setColor((ColorType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__DIMENSION:
				setDimension((DimensionType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__FONT:
				setFont((FontType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__IMAGE:
				setImage((ImageType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__INTEGER:
				setInteger((IntegerType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__LOOK_AND_FEEL:
				setLookAndFeel((LookAndFeelType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__MASKED_IMAGE:
				setMaskedImage((MaskedImageType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__POSITION:
				setPosition((PositionType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__RECTANGLE:
				setRectangle((RectangleType)null);
				return;
			case LookAndFeelPackage.DOCUMENT_ROOT__STRING:
				setString((StringType)null);
				return;
		}
		super.eUnset(featureID);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case LookAndFeelPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case LookAndFeelPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case LookAndFeelPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case LookAndFeelPackage.DOCUMENT_ROOT__BOOLEAN:
				return getBoolean() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__COLOR:
				return getColor() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__DIMENSION:
				return getDimension() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__FONT:
				return getFont() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__IMAGE:
				return getImage() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__INTEGER:
				return getInteger() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__LOOK_AND_FEEL:
				return getLookAndFeel() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__MASKED_IMAGE:
				return getMaskedImage() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__POSITION:
				return getPosition() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__RECTANGLE:
				return getRectangle() != null;
			case LookAndFeelPackage.DOCUMENT_ROOT__STRING:
				return getString() != null;
		}
		return super.eIsSet(featureID);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl
