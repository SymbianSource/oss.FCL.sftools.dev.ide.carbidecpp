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

import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vendor Macros Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.VendorMacrosTypeImpl#getSdkVendor <em>Sdk Vendor</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VendorMacrosTypeImpl extends EObjectImpl implements VendorMacrosType {
	/**
	 * The cached value of the '{@link #getSdkVendor() <em>Sdk Vendor</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSdkVendor()
	 * @generated
	 * @ordered
	 */
	protected EList sdkVendor = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VendorMacrosTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SymbianMacroStorePackage.Literals.VENDOR_MACROS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSdkVendor() {
		if (sdkVendor == null) {
			sdkVendor = new EObjectContainmentEList(SdkVendorType.class, this, SymbianMacroStorePackage.VENDOR_MACROS_TYPE__SDK_VENDOR);
		}
		return sdkVendor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymbianMacroStorePackage.VENDOR_MACROS_TYPE__SDK_VENDOR:
				return ((InternalEList)getSdkVendor()).basicRemove(otherEnd, msgs);
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
			case SymbianMacroStorePackage.VENDOR_MACROS_TYPE__SDK_VENDOR:
				return getSdkVendor();
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
			case SymbianMacroStorePackage.VENDOR_MACROS_TYPE__SDK_VENDOR:
				getSdkVendor().clear();
				getSdkVendor().addAll((Collection)newValue);
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
			case SymbianMacroStorePackage.VENDOR_MACROS_TYPE__SDK_VENDOR:
				getSdkVendor().clear();
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
			case SymbianMacroStorePackage.VENDOR_MACROS_TYPE__SDK_VENDOR:
				return sdkVendor != null && !sdkVendor.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //VendorMacrosTypeImpl