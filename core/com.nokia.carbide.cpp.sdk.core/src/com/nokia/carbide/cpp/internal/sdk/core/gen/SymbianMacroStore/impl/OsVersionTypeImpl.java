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

import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Os Version Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsVersionTypeImpl#getPlatformMacros <em>Platform Macros</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsVersionTypeImpl#getOsMacros <em>Os Macros</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.OsVersionTypeImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OsVersionTypeImpl extends EObjectImpl implements OsVersionType {
	/**
	 * The cached value of the '{@link #getPlatformMacros() <em>Platform Macros</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlatformMacros()
	 * @generated
	 * @ordered
	 */
	protected PlatformMacrosType platformMacros = null;

	/**
	 * The cached value of the '{@link #getOsMacros() <em>Os Macros</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOsMacros()
	 * @generated
	 * @ordered
	 */
	protected OsMacrosType osMacros = null;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OsVersionTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SymbianMacroStorePackage.Literals.OS_VERSION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlatformMacrosType getPlatformMacros() {
		return platformMacros;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlatformMacros(PlatformMacrosType newPlatformMacros, NotificationChain msgs) {
		PlatformMacrosType oldPlatformMacros = platformMacros;
		platformMacros = newPlatformMacros;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SymbianMacroStorePackage.OS_VERSION_TYPE__PLATFORM_MACROS, oldPlatformMacros, newPlatformMacros);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlatformMacros(PlatformMacrosType newPlatformMacros) {
		if (newPlatformMacros != platformMacros) {
			NotificationChain msgs = null;
			if (platformMacros != null)
				msgs = ((InternalEObject)platformMacros).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SymbianMacroStorePackage.OS_VERSION_TYPE__PLATFORM_MACROS, null, msgs);
			if (newPlatformMacros != null)
				msgs = ((InternalEObject)newPlatformMacros).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SymbianMacroStorePackage.OS_VERSION_TYPE__PLATFORM_MACROS, null, msgs);
			msgs = basicSetPlatformMacros(newPlatformMacros, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymbianMacroStorePackage.OS_VERSION_TYPE__PLATFORM_MACROS, newPlatformMacros, newPlatformMacros));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OsMacrosType getOsMacros() {
		return osMacros;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOsMacros(OsMacrosType newOsMacros, NotificationChain msgs) {
		OsMacrosType oldOsMacros = osMacros;
		osMacros = newOsMacros;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SymbianMacroStorePackage.OS_VERSION_TYPE__OS_MACROS, oldOsMacros, newOsMacros);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOsMacros(OsMacrosType newOsMacros) {
		if (newOsMacros != osMacros) {
			NotificationChain msgs = null;
			if (osMacros != null)
				msgs = ((InternalEObject)osMacros).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SymbianMacroStorePackage.OS_VERSION_TYPE__OS_MACROS, null, msgs);
			if (newOsMacros != null)
				msgs = ((InternalEObject)newOsMacros).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SymbianMacroStorePackage.OS_VERSION_TYPE__OS_MACROS, null, msgs);
			msgs = basicSetOsMacros(newOsMacros, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymbianMacroStorePackage.OS_VERSION_TYPE__OS_MACROS, newOsMacros, newOsMacros));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SymbianMacroStorePackage.OS_VERSION_TYPE__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymbianMacroStorePackage.OS_VERSION_TYPE__PLATFORM_MACROS:
				return basicSetPlatformMacros(null, msgs);
			case SymbianMacroStorePackage.OS_VERSION_TYPE__OS_MACROS:
				return basicSetOsMacros(null, msgs);
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
			case SymbianMacroStorePackage.OS_VERSION_TYPE__PLATFORM_MACROS:
				return getPlatformMacros();
			case SymbianMacroStorePackage.OS_VERSION_TYPE__OS_MACROS:
				return getOsMacros();
			case SymbianMacroStorePackage.OS_VERSION_TYPE__VERSION:
				return getVersion();
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
			case SymbianMacroStorePackage.OS_VERSION_TYPE__PLATFORM_MACROS:
				setPlatformMacros((PlatformMacrosType)newValue);
				return;
			case SymbianMacroStorePackage.OS_VERSION_TYPE__OS_MACROS:
				setOsMacros((OsMacrosType)newValue);
				return;
			case SymbianMacroStorePackage.OS_VERSION_TYPE__VERSION:
				setVersion((String)newValue);
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
			case SymbianMacroStorePackage.OS_VERSION_TYPE__PLATFORM_MACROS:
				setPlatformMacros((PlatformMacrosType)null);
				return;
			case SymbianMacroStorePackage.OS_VERSION_TYPE__OS_MACROS:
				setOsMacros((OsMacrosType)null);
				return;
			case SymbianMacroStorePackage.OS_VERSION_TYPE__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case SymbianMacroStorePackage.OS_VERSION_TYPE__PLATFORM_MACROS:
				return platformMacros != null;
			case SymbianMacroStorePackage.OS_VERSION_TYPE__OS_MACROS:
				return osMacros != null;
			case SymbianMacroStorePackage.OS_VERSION_TYPE__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
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
		result.append(" (version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //OsVersionTypeImpl