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
*/
package com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl;

import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStoreTypeImpl#getSymbianOSMacros <em>Symbian OS Macros</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.SymbianMacroStoreTypeImpl#getVendorMacros <em>Vendor Macros</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SymbianMacroStoreTypeImpl extends EObjectImpl implements SymbianMacroStoreType {
	/**
	 * The cached value of the '{@link #getSymbianOSMacros() <em>Symbian OS Macros</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbianOSMacros()
	 * @generated
	 * @ordered
	 */
	protected SymbianOSMacrosType symbianOSMacros = null;

	/**
	 * The cached value of the '{@link #getVendorMacros() <em>Vendor Macros</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVendorMacros()
	 * @generated
	 * @ordered
	 */
	protected VendorMacrosType vendorMacros = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SymbianMacroStoreTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SymbianMacroStorePackage.Literals.SYMBIAN_MACRO_STORE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianOSMacrosType getSymbianOSMacros() {
		return symbianOSMacros;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSymbianOSMacros(SymbianOSMacrosType newSymbianOSMacros, NotificationChain msgs) {
		SymbianOSMacrosType oldSymbianOSMacros = symbianOSMacros;
		symbianOSMacros = newSymbianOSMacros;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS, oldSymbianOSMacros, newSymbianOSMacros);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbianOSMacros(SymbianOSMacrosType newSymbianOSMacros) {
		if (newSymbianOSMacros != symbianOSMacros) {
			NotificationChain msgs = null;
			if (symbianOSMacros != null)
				msgs = ((InternalEObject)symbianOSMacros).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS, null, msgs);
			if (newSymbianOSMacros != null)
				msgs = ((InternalEObject)newSymbianOSMacros).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS, null, msgs);
			msgs = basicSetSymbianOSMacros(newSymbianOSMacros, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS, newSymbianOSMacros, newSymbianOSMacros));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VendorMacrosType getVendorMacros() {
		return vendorMacros;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVendorMacros(VendorMacrosType newVendorMacros, NotificationChain msgs) {
		VendorMacrosType oldVendorMacros = vendorMacros;
		vendorMacros = newVendorMacros;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS, oldVendorMacros, newVendorMacros);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVendorMacros(VendorMacrosType newVendorMacros) {
		if (newVendorMacros != vendorMacros) {
			NotificationChain msgs = null;
			if (vendorMacros != null)
				msgs = ((InternalEObject)vendorMacros).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS, null, msgs);
			if (newVendorMacros != null)
				msgs = ((InternalEObject)newVendorMacros).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS, null, msgs);
			msgs = basicSetVendorMacros(newVendorMacros, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS, newVendorMacros, newVendorMacros));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS:
				return basicSetSymbianOSMacros(null, msgs);
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS:
				return basicSetVendorMacros(null, msgs);
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
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS:
				return getSymbianOSMacros();
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS:
				return getVendorMacros();
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
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS:
				setSymbianOSMacros((SymbianOSMacrosType)newValue);
				return;
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS:
				setVendorMacros((VendorMacrosType)newValue);
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
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS:
				setSymbianOSMacros((SymbianOSMacrosType)null);
				return;
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS:
				setVendorMacros((VendorMacrosType)null);
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
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__SYMBIAN_OS_MACROS:
				return symbianOSMacros != null;
			case SymbianMacroStorePackage.SYMBIAN_MACRO_STORE_TYPE__VENDOR_MACROS:
				return vendorMacros != null;
		}
		return super.eIsSet(featureID);
	}

} //SymbianMacroStoreTypeImpl