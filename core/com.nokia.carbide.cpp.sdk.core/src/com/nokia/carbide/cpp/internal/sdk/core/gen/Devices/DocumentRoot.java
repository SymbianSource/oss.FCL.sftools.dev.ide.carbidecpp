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

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getDevice <em>Device</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getDevices <em>Devices</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getEpocroot <em>Epocroot</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getToolsroot <em>Toolsroot</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDocumentRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDocumentRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDocumentRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry" keyType="java.lang.String" valueType="java.lang.String" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Device</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Device</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Device</em>' containment reference.
	 * @see #setDevice(DeviceType)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDocumentRoot_Device()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='device' namespace='##targetNamespace'"
	 * @generated
	 */
	DeviceType getDevice();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getDevice <em>Device</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Device</em>' containment reference.
	 * @see #getDevice()
	 * @generated
	 */
	void setDevice(DeviceType value);

	/**
	 * Returns the value of the '<em><b>Devices</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Devices</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Devices</em>' containment reference.
	 * @see #setDevices(DevicesType)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDocumentRoot_Devices()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='devices' namespace='##targetNamespace'"
	 * @generated
	 */
	DevicesType getDevices();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getDevices <em>Devices</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Devices</em>' containment reference.
	 * @see #getDevices()
	 * @generated
	 */
	void setDevices(DevicesType value);

	/**
	 * Returns the value of the '<em><b>Epocroot</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Epocroot</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Epocroot</em>' attribute.
	 * @see #setEpocroot(String)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDocumentRoot_Epocroot()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.EpocrootType" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='epocroot' namespace='##targetNamespace'"
	 * @generated
	 */
	String getEpocroot();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getEpocroot <em>Epocroot</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Epocroot</em>' attribute.
	 * @see #getEpocroot()
	 * @generated
	 */
	void setEpocroot(String value);

	/**
	 * Returns the value of the '<em><b>Toolsroot</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Toolsroot</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Toolsroot</em>' attribute.
	 * @see #setToolsroot(String)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDocumentRoot_Toolsroot()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.ToolsrootType" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='toolsroot' namespace='##targetNamespace'"
	 * @generated
	 */
	String getToolsroot();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getToolsroot <em>Toolsroot</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Toolsroot</em>' attribute.
	 * @see #getToolsroot()
	 * @generated
	 */
	void setToolsroot(String value);

} // DocumentRoot