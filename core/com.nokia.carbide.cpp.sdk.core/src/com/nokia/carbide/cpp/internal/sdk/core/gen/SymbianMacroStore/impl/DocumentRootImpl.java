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

import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.OsVersionType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.PlatformType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SdkVendorType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStorePackage;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianOSMacrosType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.VendorMacrosType;

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
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getMacro <em>Macro</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getOsMacros <em>Os Macros</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getOsVersion <em>Os Version</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getPlatform <em>Platform</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getPlatformMacros <em>Platform Macros</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getSdkVendor <em>Sdk Vendor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getSymbianMacroStore <em>Symbian Macro Store</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getSymbianOSMacros <em>Symbian OS Macros</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.impl.DocumentRootImpl#getVendorMacros <em>Vendor Macros</em>}</li>
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
	protected FeatureMap mixed = null;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap xMLNSPrefixMap = null;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap xSISchemaLocation = null;

	/**
	 * The default value of the '{@link #getMacro() <em>Macro</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMacro()
	 * @generated
	 * @ordered
	 */
	protected static final String MACRO_EDEFAULT = null;

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
		return SymbianMacroStorePackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, SymbianMacroStorePackage.DOCUMENT_ROOT__MIXED);
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
			xMLNSPrefixMap = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, SymbianMacroStorePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
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
			xSISchemaLocation = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, SymbianMacroStorePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMacro() {
		return (String)getMixed().get(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__MACRO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMacro(String newMacro) {
		((FeatureMap.Internal)getMixed()).set(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__MACRO, newMacro);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OsMacrosType getOsMacros() {
		return (OsMacrosType)getMixed().get(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__OS_MACROS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOsMacros(OsMacrosType newOsMacros, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__OS_MACROS, newOsMacros, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOsMacros(OsMacrosType newOsMacros) {
		((FeatureMap.Internal)getMixed()).set(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__OS_MACROS, newOsMacros);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OsVersionType getOsVersion() {
		return (OsVersionType)getMixed().get(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__OS_VERSION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOsVersion(OsVersionType newOsVersion, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__OS_VERSION, newOsVersion, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOsVersion(OsVersionType newOsVersion) {
		((FeatureMap.Internal)getMixed()).set(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__OS_VERSION, newOsVersion);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlatformType getPlatform() {
		return (PlatformType)getMixed().get(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__PLATFORM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlatform(PlatformType newPlatform, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__PLATFORM, newPlatform, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlatform(PlatformType newPlatform) {
		((FeatureMap.Internal)getMixed()).set(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__PLATFORM, newPlatform);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlatformMacrosType getPlatformMacros() {
		return (PlatformMacrosType)getMixed().get(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__PLATFORM_MACROS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlatformMacros(PlatformMacrosType newPlatformMacros, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__PLATFORM_MACROS, newPlatformMacros, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlatformMacros(PlatformMacrosType newPlatformMacros) {
		((FeatureMap.Internal)getMixed()).set(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__PLATFORM_MACROS, newPlatformMacros);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SdkVendorType getSdkVendor() {
		return (SdkVendorType)getMixed().get(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__SDK_VENDOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSdkVendor(SdkVendorType newSdkVendor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__SDK_VENDOR, newSdkVendor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSdkVendor(SdkVendorType newSdkVendor) {
		((FeatureMap.Internal)getMixed()).set(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__SDK_VENDOR, newSdkVendor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianMacroStoreType getSymbianMacroStore() {
		return (SymbianMacroStoreType)getMixed().get(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__SYMBIAN_MACRO_STORE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSymbianMacroStore(SymbianMacroStoreType newSymbianMacroStore, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__SYMBIAN_MACRO_STORE, newSymbianMacroStore, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbianMacroStore(SymbianMacroStoreType newSymbianMacroStore) {
		((FeatureMap.Internal)getMixed()).set(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__SYMBIAN_MACRO_STORE, newSymbianMacroStore);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SymbianOSMacrosType getSymbianOSMacros() {
		return (SymbianOSMacrosType)getMixed().get(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__SYMBIAN_OS_MACROS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSymbianOSMacros(SymbianOSMacrosType newSymbianOSMacros, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__SYMBIAN_OS_MACROS, newSymbianOSMacros, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbianOSMacros(SymbianOSMacrosType newSymbianOSMacros) {
		((FeatureMap.Internal)getMixed()).set(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__SYMBIAN_OS_MACROS, newSymbianOSMacros);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VendorMacrosType getVendorMacros() {
		return (VendorMacrosType)getMixed().get(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__VENDOR_MACROS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVendorMacros(VendorMacrosType newVendorMacros, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__VENDOR_MACROS, newVendorMacros, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVendorMacros(VendorMacrosType newVendorMacros) {
		((FeatureMap.Internal)getMixed()).set(SymbianMacroStorePackage.Literals.DOCUMENT_ROOT__VENDOR_MACROS, newVendorMacros);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SymbianMacroStorePackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_MACROS:
				return basicSetOsMacros(null, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_VERSION:
				return basicSetOsVersion(null, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM:
				return basicSetPlatform(null, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM_MACROS:
				return basicSetPlatformMacros(null, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SDK_VENDOR:
				return basicSetSdkVendor(null, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_MACRO_STORE:
				return basicSetSymbianMacroStore(null, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_OS_MACROS:
				return basicSetSymbianOSMacros(null, msgs);
			case SymbianMacroStorePackage.DOCUMENT_ROOT__VENDOR_MACROS:
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
			case SymbianMacroStorePackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__MACRO:
				return getMacro();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_MACROS:
				return getOsMacros();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_VERSION:
				return getOsVersion();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM:
				return getPlatform();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM_MACROS:
				return getPlatformMacros();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SDK_VENDOR:
				return getSdkVendor();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_MACRO_STORE:
				return getSymbianMacroStore();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_OS_MACROS:
				return getSymbianOSMacros();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__VENDOR_MACROS:
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
			case SymbianMacroStorePackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__MACRO:
				setMacro((String)newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_MACROS:
				setOsMacros((OsMacrosType)newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_VERSION:
				setOsVersion((OsVersionType)newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM:
				setPlatform((PlatformType)newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM_MACROS:
				setPlatformMacros((PlatformMacrosType)newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SDK_VENDOR:
				setSdkVendor((SdkVendorType)newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_MACRO_STORE:
				setSymbianMacroStore((SymbianMacroStoreType)newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_OS_MACROS:
				setSymbianOSMacros((SymbianOSMacrosType)newValue);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__VENDOR_MACROS:
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
			case SymbianMacroStorePackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__MACRO:
				setMacro(MACRO_EDEFAULT);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_MACROS:
				setOsMacros((OsMacrosType)null);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_VERSION:
				setOsVersion((OsVersionType)null);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM:
				setPlatform((PlatformType)null);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM_MACROS:
				setPlatformMacros((PlatformMacrosType)null);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SDK_VENDOR:
				setSdkVendor((SdkVendorType)null);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_MACRO_STORE:
				setSymbianMacroStore((SymbianMacroStoreType)null);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_OS_MACROS:
				setSymbianOSMacros((SymbianOSMacrosType)null);
				return;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__VENDOR_MACROS:
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
			case SymbianMacroStorePackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case SymbianMacroStorePackage.DOCUMENT_ROOT__MACRO:
				return MACRO_EDEFAULT == null ? getMacro() != null : !MACRO_EDEFAULT.equals(getMacro());
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_MACROS:
				return getOsMacros() != null;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__OS_VERSION:
				return getOsVersion() != null;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM:
				return getPlatform() != null;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__PLATFORM_MACROS:
				return getPlatformMacros() != null;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SDK_VENDOR:
				return getSdkVendor() != null;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_MACRO_STORE:
				return getSymbianMacroStore() != null;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__SYMBIAN_OS_MACROS:
				return getSymbianOSMacros() != null;
			case SymbianMacroStorePackage.DOCUMENT_ROOT__VENDOR_MACROS:
				return getVendorMacros() != null;
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