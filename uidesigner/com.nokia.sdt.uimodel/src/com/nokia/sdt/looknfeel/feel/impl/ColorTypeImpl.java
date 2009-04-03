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

import com.nokia.sdt.looknfeel.feel.ColorType;
import com.nokia.sdt.looknfeel.feel.LookAndFeelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Color Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.ColorTypeImpl#getB <em>B</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.ColorTypeImpl#getG <em>G</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.ColorTypeImpl#getKey <em>Key</em>}</li>
 *   <li>{@link com.nokia.sdt.looknfeel.feel.impl.ColorTypeImpl#getR <em>R</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ColorTypeImpl extends EObjectImpl implements ColorType {
    /**
	 * The default value of the '{@link #getB() <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB()
	 * @generated
	 * @ordered
	 */
	protected static final short B_EDEFAULT = 0;

    /**
	 * The cached value of the '{@link #getB() <em>B</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getB()
	 * @generated
	 * @ordered
	 */
	protected short b = B_EDEFAULT;

    /**
	 * This is true if the B attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean bESet;

    /**
	 * The default value of the '{@link #getG() <em>G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getG()
	 * @generated
	 * @ordered
	 */
	protected static final short G_EDEFAULT = 0;

    /**
	 * The cached value of the '{@link #getG() <em>G</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getG()
	 * @generated
	 * @ordered
	 */
	protected short g = G_EDEFAULT;

    /**
	 * This is true if the G attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean gESet;

    /**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

    /**
	 * The default value of the '{@link #getR() <em>R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getR()
	 * @generated
	 * @ordered
	 */
	protected static final short R_EDEFAULT = 0;

    /**
	 * The cached value of the '{@link #getR() <em>R</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getR()
	 * @generated
	 * @ordered
	 */
	protected short r = R_EDEFAULT;

    /**
	 * This is true if the R attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rESet;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ColorTypeImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return LookAndFeelPackage.Literals.COLOR_TYPE;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getB() {
		return b;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setB(short newB) {
		short oldB = b;
		b = newB;
		boolean oldBESet = bESet;
		bESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LookAndFeelPackage.COLOR_TYPE__B, oldB, b, !oldBESet));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetB() {
		short oldB = b;
		boolean oldBESet = bESet;
		b = B_EDEFAULT;
		bESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, LookAndFeelPackage.COLOR_TYPE__B, oldB, B_EDEFAULT, oldBESet));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetB() {
		return bESet;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getG() {
		return g;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setG(short newG) {
		short oldG = g;
		g = newG;
		boolean oldGESet = gESet;
		gESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LookAndFeelPackage.COLOR_TYPE__G, oldG, g, !oldGESet));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetG() {
		short oldG = g;
		boolean oldGESet = gESet;
		g = G_EDEFAULT;
		gESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, LookAndFeelPackage.COLOR_TYPE__G, oldG, G_EDEFAULT, oldGESet));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetG() {
		return gESet;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKey() {
		return key;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LookAndFeelPackage.COLOR_TYPE__KEY, oldKey, key));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getR() {
		return r;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setR(short newR) {
		short oldR = r;
		r = newR;
		boolean oldRESet = rESet;
		rESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LookAndFeelPackage.COLOR_TYPE__R, oldR, r, !oldRESet));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetR() {
		short oldR = r;
		boolean oldRESet = rESet;
		r = R_EDEFAULT;
		rESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, LookAndFeelPackage.COLOR_TYPE__R, oldR, R_EDEFAULT, oldRESet));
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetR() {
		return rESet;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case LookAndFeelPackage.COLOR_TYPE__B:
				return new Short(getB());
			case LookAndFeelPackage.COLOR_TYPE__G:
				return new Short(getG());
			case LookAndFeelPackage.COLOR_TYPE__KEY:
				return getKey();
			case LookAndFeelPackage.COLOR_TYPE__R:
				return new Short(getR());
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
			case LookAndFeelPackage.COLOR_TYPE__B:
				setB(((Short)newValue).shortValue());
				return;
			case LookAndFeelPackage.COLOR_TYPE__G:
				setG(((Short)newValue).shortValue());
				return;
			case LookAndFeelPackage.COLOR_TYPE__KEY:
				setKey((String)newValue);
				return;
			case LookAndFeelPackage.COLOR_TYPE__R:
				setR(((Short)newValue).shortValue());
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
			case LookAndFeelPackage.COLOR_TYPE__B:
				unsetB();
				return;
			case LookAndFeelPackage.COLOR_TYPE__G:
				unsetG();
				return;
			case LookAndFeelPackage.COLOR_TYPE__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case LookAndFeelPackage.COLOR_TYPE__R:
				unsetR();
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
			case LookAndFeelPackage.COLOR_TYPE__B:
				return isSetB();
			case LookAndFeelPackage.COLOR_TYPE__G:
				return isSetG();
			case LookAndFeelPackage.COLOR_TYPE__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case LookAndFeelPackage.COLOR_TYPE__R:
				return isSetR();
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
		result.append(" (b: ");
		if (bESet) result.append(b); else result.append("<unset>");
		result.append(", g: ");
		if (gESet) result.append(g); else result.append("<unset>");
		result.append(", key: ");
		result.append(key);
		result.append(", r: ");
		if (rESet) result.append(r); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ColorTypeImpl
