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
package com.nokia.carbide.cpp.internal.sdk.core.gen.Devices;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType#getDevice <em>Device</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDevicesType()
 * @model extendedMetaData="name='devices_._type' kind='elementOnly'"
 * @generated
 */
public interface DevicesType extends EObject {
	/**
	 * Returns the value of the '<em><b>Device</b></em>' containment reference list.
	 * The list contents are of type {@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Device</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Device</em>' containment reference list.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDevicesType_Device()
	 * @model type="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType" containment="true" required="true"
	 *        extendedMetaData="kind='element' name='device' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getDevice();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * The default value is <code>"1.0"</code>.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #setVersion(VersionType)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDevicesType_Version()
	 * @model default="1.0" unique="false" unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='version' namespace='##targetNamespace'"
	 * @generated
	 */
	VersionType getVersion();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType
	 * @see #isSetVersion()
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(VersionType value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVersion()
	 * @see #getVersion()
	 * @see #setVersion(VersionType)
	 * @generated
	 */
	void unsetVersion();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType#getVersion <em>Version</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Version</em>' attribute is set.
	 * @see #unsetVersion()
	 * @see #getVersion()
	 * @see #setVersion(VersionType)
	 * @generated
	 */
	boolean isSetVersion();

} // DevicesType