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

import com.nokia.sdt.looknfeel.feel.LookAndFeelPackage;
import com.nokia.sdt.looknfeel.feel.LookAndFeelType;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getColor <em>Color</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getColorAlias <em>Color Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getFont <em>Font</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getFontAlias <em>Font Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getImage <em>Image</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getMaskedImage <em>Masked Image</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getPositionAlias <em>Position Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getDimension <em>Dimension</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getDimensionAlias <em>Dimension Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getRectangle <em>Rectangle</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getRectangleAlias <em>Rectangle Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getInteger <em>Integer</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getIntegerAlias <em>Integer Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getBoolean <em>Boolean</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getBooleanAlias <em>Boolean Alias</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getString <em>String</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.LookAndFeelTypeImpl#getStringAlias <em>String Alias</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LookAndFeelTypeImpl extends EObjectImpl implements LookAndFeelType {
    /**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
    protected FeatureMap group;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LookAndFeelTypeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, LookAndFeelPackage.LOOK_AND_FEEL_TYPE__GROUP);
		}
		return group;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getColor() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__COLOR);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getColorAlias() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__COLOR_ALIAS);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getFont() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__FONT);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getFontAlias() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__FONT_ALIAS);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getImage() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__IMAGE);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMaskedImage() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__MASKED_IMAGE);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPosition() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__POSITION);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPositionAlias() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__POSITION_ALIAS);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDimension() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__DIMENSION);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDimensionAlias() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__DIMENSION_ALIAS);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRectangle() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__RECTANGLE);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getRectangleAlias() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__RECTANGLE_ALIAS);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getInteger() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__INTEGER);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getIntegerAlias() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__INTEGER_ALIAS);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getBoolean() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__BOOLEAN);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getBooleanAlias() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__BOOLEAN_ALIAS);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getString() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__STRING);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getStringAlias() {
		return getGroup().list(LookAndFeelPackage.Literals.LOOK_AND_FEEL_TYPE__STRING_ALIAS);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR:
				return ((InternalEList)getColor()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR_ALIAS:
				return ((InternalEList)getColorAlias()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT:
				return ((InternalEList)getFont()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT_ALIAS:
				return ((InternalEList)getFontAlias()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__IMAGE:
				return ((InternalEList)getImage()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__MASKED_IMAGE:
				return ((InternalEList)getMaskedImage()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION:
				return ((InternalEList)getPosition()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION_ALIAS:
				return ((InternalEList)getPositionAlias()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION:
				return ((InternalEList)getDimension()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION_ALIAS:
				return ((InternalEList)getDimensionAlias()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE:
				return ((InternalEList)getRectangle()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE_ALIAS:
				return ((InternalEList)getRectangleAlias()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER:
				return ((InternalEList)getInteger()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER_ALIAS:
				return ((InternalEList)getIntegerAlias()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN:
				return ((InternalEList)getBoolean()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN_ALIAS:
				return ((InternalEList)getBooleanAlias()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING:
				return ((InternalEList)getString()).basicRemove(otherEnd, msgs);
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING_ALIAS:
				return ((InternalEList)getStringAlias()).basicRemove(otherEnd, msgs);
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
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR:
				return getColor();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR_ALIAS:
				return getColorAlias();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT:
				return getFont();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT_ALIAS:
				return getFontAlias();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__IMAGE:
				return getImage();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__MASKED_IMAGE:
				return getMaskedImage();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION:
				return getPosition();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION_ALIAS:
				return getPositionAlias();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION:
				return getDimension();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION_ALIAS:
				return getDimensionAlias();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE:
				return getRectangle();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE_ALIAS:
				return getRectangleAlias();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER:
				return getInteger();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER_ALIAS:
				return getIntegerAlias();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN:
				return getBoolean();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN_ALIAS:
				return getBooleanAlias();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING:
				return getString();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING_ALIAS:
				return getStringAlias();
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
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR:
				getColor().clear();
				getColor().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR_ALIAS:
				getColorAlias().clear();
				getColorAlias().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT:
				getFont().clear();
				getFont().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT_ALIAS:
				getFontAlias().clear();
				getFontAlias().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__IMAGE:
				getImage().clear();
				getImage().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__MASKED_IMAGE:
				getMaskedImage().clear();
				getMaskedImage().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION:
				getPosition().clear();
				getPosition().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION_ALIAS:
				getPositionAlias().clear();
				getPositionAlias().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION:
				getDimension().clear();
				getDimension().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION_ALIAS:
				getDimensionAlias().clear();
				getDimensionAlias().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE:
				getRectangle().clear();
				getRectangle().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE_ALIAS:
				getRectangleAlias().clear();
				getRectangleAlias().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER:
				getInteger().clear();
				getInteger().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER_ALIAS:
				getIntegerAlias().clear();
				getIntegerAlias().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN:
				getBoolean().clear();
				getBoolean().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN_ALIAS:
				getBooleanAlias().clear();
				getBooleanAlias().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING:
				getString().clear();
				getString().addAll((Collection)newValue);
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING_ALIAS:
				getStringAlias().clear();
				getStringAlias().addAll((Collection)newValue);
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
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__GROUP:
				getGroup().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR:
				getColor().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR_ALIAS:
				getColorAlias().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT:
				getFont().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT_ALIAS:
				getFontAlias().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__IMAGE:
				getImage().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__MASKED_IMAGE:
				getMaskedImage().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION:
				getPosition().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION_ALIAS:
				getPositionAlias().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION:
				getDimension().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION_ALIAS:
				getDimensionAlias().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE:
				getRectangle().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE_ALIAS:
				getRectangleAlias().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER:
				getInteger().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER_ALIAS:
				getIntegerAlias().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN:
				getBoolean().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN_ALIAS:
				getBooleanAlias().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING:
				getString().clear();
				return;
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING_ALIAS:
				getStringAlias().clear();
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
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR:
				return !getColor().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__COLOR_ALIAS:
				return !getColorAlias().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT:
				return !getFont().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__FONT_ALIAS:
				return !getFontAlias().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__IMAGE:
				return !getImage().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__MASKED_IMAGE:
				return !getMaskedImage().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION:
				return !getPosition().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__POSITION_ALIAS:
				return !getPositionAlias().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION:
				return !getDimension().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__DIMENSION_ALIAS:
				return !getDimensionAlias().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE:
				return !getRectangle().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__RECTANGLE_ALIAS:
				return !getRectangleAlias().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER:
				return !getInteger().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__INTEGER_ALIAS:
				return !getIntegerAlias().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN:
				return !getBoolean().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__BOOLEAN_ALIAS:
				return !getBooleanAlias().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING:
				return !getString().isEmpty();
			case LookAndFeelPackage.LOOK_AND_FEEL_TYPE__STRING_ALIAS:
				return !getStringAlias().isEmpty();
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
		result.append(" (group: ");
		result.append(group);
		result.append(')');
		return result.toString();
	}

} //LookAndFeelTypeImpl
