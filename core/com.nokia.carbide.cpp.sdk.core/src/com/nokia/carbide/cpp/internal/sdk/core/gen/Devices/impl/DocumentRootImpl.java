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
package com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl;

import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot;

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
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl#getDevice <em>Device</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl#getDevices <em>Devices</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl#getEpocroot <em>Epocroot</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl#getToolsroot <em>Toolsroot</em>}</li>
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
	 * The default value of the '{@link #getEpocroot() <em>Epocroot</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEpocroot()
	 * @generated
	 * @ordered
	 */
	protected static final String EPOCROOT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getToolsroot() <em>Toolsroot</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolsroot()
	 * @generated
	 * @ordered
	 */
	protected static final String TOOLSROOT_EDEFAULT = null;

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
		return DevicesPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, DevicesPackage.DOCUMENT_ROOT__MIXED);
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
			xMLNSPrefixMap = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, DevicesPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP);
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
			xSISchemaLocation = new EcoreEMap(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, DevicesPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeviceType getDevice() {
		return (DeviceType)getMixed().get(DevicesPackage.Literals.DOCUMENT_ROOT__DEVICE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDevice(DeviceType newDevice, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(DevicesPackage.Literals.DOCUMENT_ROOT__DEVICE, newDevice, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDevice(DeviceType newDevice) {
		((FeatureMap.Internal)getMixed()).set(DevicesPackage.Literals.DOCUMENT_ROOT__DEVICE, newDevice);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DevicesType getDevices() {
		return (DevicesType)getMixed().get(DevicesPackage.Literals.DOCUMENT_ROOT__DEVICES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDevices(DevicesType newDevices, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(DevicesPackage.Literals.DOCUMENT_ROOT__DEVICES, newDevices, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDevices(DevicesType newDevices) {
		((FeatureMap.Internal)getMixed()).set(DevicesPackage.Literals.DOCUMENT_ROOT__DEVICES, newDevices);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEpocroot() {
		return (String)getMixed().get(DevicesPackage.Literals.DOCUMENT_ROOT__EPOCROOT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEpocroot(String newEpocroot) {
		((FeatureMap.Internal)getMixed()).set(DevicesPackage.Literals.DOCUMENT_ROOT__EPOCROOT, newEpocroot);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getToolsroot() {
		return (String)getMixed().get(DevicesPackage.Literals.DOCUMENT_ROOT__TOOLSROOT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToolsroot(String newToolsroot) {
		((FeatureMap.Internal)getMixed()).set(DevicesPackage.Literals.DOCUMENT_ROOT__TOOLSROOT, newToolsroot);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DevicesPackage.DOCUMENT_ROOT__MIXED:
				return ((InternalEList)getMixed()).basicRemove(otherEnd, msgs);
			case DevicesPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case DevicesPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case DevicesPackage.DOCUMENT_ROOT__DEVICE:
				return basicSetDevice(null, msgs);
			case DevicesPackage.DOCUMENT_ROOT__DEVICES:
				return basicSetDevices(null, msgs);
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
			case DevicesPackage.DOCUMENT_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case DevicesPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case DevicesPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case DevicesPackage.DOCUMENT_ROOT__DEVICE:
				return getDevice();
			case DevicesPackage.DOCUMENT_ROOT__DEVICES:
				return getDevices();
			case DevicesPackage.DOCUMENT_ROOT__EPOCROOT:
				return getEpocroot();
			case DevicesPackage.DOCUMENT_ROOT__TOOLSROOT:
				return getToolsroot();
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
			case DevicesPackage.DOCUMENT_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case DevicesPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case DevicesPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case DevicesPackage.DOCUMENT_ROOT__DEVICE:
				setDevice((DeviceType)newValue);
				return;
			case DevicesPackage.DOCUMENT_ROOT__DEVICES:
				setDevices((DevicesType)newValue);
				return;
			case DevicesPackage.DOCUMENT_ROOT__EPOCROOT:
				setEpocroot((String)newValue);
				return;
			case DevicesPackage.DOCUMENT_ROOT__TOOLSROOT:
				setToolsroot((String)newValue);
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
			case DevicesPackage.DOCUMENT_ROOT__MIXED:
				getMixed().clear();
				return;
			case DevicesPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case DevicesPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case DevicesPackage.DOCUMENT_ROOT__DEVICE:
				setDevice((DeviceType)null);
				return;
			case DevicesPackage.DOCUMENT_ROOT__DEVICES:
				setDevices((DevicesType)null);
				return;
			case DevicesPackage.DOCUMENT_ROOT__EPOCROOT:
				setEpocroot(EPOCROOT_EDEFAULT);
				return;
			case DevicesPackage.DOCUMENT_ROOT__TOOLSROOT:
				setToolsroot(TOOLSROOT_EDEFAULT);
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
			case DevicesPackage.DOCUMENT_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case DevicesPackage.DOCUMENT_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case DevicesPackage.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case DevicesPackage.DOCUMENT_ROOT__DEVICE:
				return getDevice() != null;
			case DevicesPackage.DOCUMENT_ROOT__DEVICES:
				return getDevices() != null;
			case DevicesPackage.DOCUMENT_ROOT__EPOCROOT:
				return EPOCROOT_EDEFAULT == null ? getEpocroot() != null : !EPOCROOT_EDEFAULT.equals(getEpocroot());
			case DevicesPackage.DOCUMENT_ROOT__TOOLSROOT:
				return TOOLSROOT_EDEFAULT == null ? getToolsroot() != null : !TOOLSROOT_EDEFAULT.equals(getToolsroot());
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