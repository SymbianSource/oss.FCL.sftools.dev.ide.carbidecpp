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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface DevicesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Devices";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/com.nokia.carbide.cpp.sdk.core/schema/devices.xsd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "Devices";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DevicesPackage eINSTANCE = com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDevicesType()
	 * @generated
	 */
	int DEVICES_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Device</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICES_TYPE__DEVICE = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICES_TYPE__VERSION = 1;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICES_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl <em>Device Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDeviceType()
	 * @generated
	 */
	int DEVICE_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Epocroot</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__EPOCROOT = 0;

	/**
	 * The feature id for the '<em><b>Toolsroot</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__TOOLSROOT = 1;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__ALIAS = 2;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__DEFAULT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__ID = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__NAME = 5;

	/**
	 * The feature id for the '<em><b>Userdeletable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__USERDELETABLE = 6;

	/**
	 * The feature id for the '<em><b>Userdeletetable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__USERDELETETABLE = 7;

	/**
	 * The number of structural features of the '<em>Device Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 2;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Device</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEVICE = 3;

	/**
	 * The feature id for the '<em><b>Devices</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEVICES = 4;

	/**
	 * The feature id for the '<em><b>Epocroot</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EPOCROOT = 5;

	/**
	 * The feature id for the '<em><b>Toolsroot</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TOOLSROOT = 6;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType <em>Default Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDefaultType()
	 * @generated
	 */
	int DEFAULT_TYPE = 3;

	/**
	 * The meta object id for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType <em>Version Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getVersionType()
	 * @generated
	 */
	int VERSION_TYPE = 4;

	/**
	 * The meta object id for the '<em>Alias Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getAliasType()
	 * @generated
	 */
	int ALIAS_TYPE = 5;

	/**
	 * The meta object id for the '<em>Default Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDefaultTypeObject()
	 * @generated
	 */
	int DEFAULT_TYPE_OBJECT = 6;

	/**
	 * The meta object id for the '<em>Epocroot Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getEpocrootType()
	 * @generated
	 */
	int EPOCROOT_TYPE = 7;

	/**
	 * The meta object id for the '<em>Id Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getIdType()
	 * @generated
	 */
	int ID_TYPE = 8;

	/**
	 * The meta object id for the '<em>Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getNameType()
	 * @generated
	 */
	int NAME_TYPE = 9;

	/**
	 * The meta object id for the '<em>Toolsroot Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getToolsrootType()
	 * @generated
	 */
	int TOOLSROOT_TYPE = 10;

	/**
	 * The meta object id for the '<em>Userdeletable Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getUserdeletableType()
	 * @generated
	 */
	int USERDELETABLE_TYPE = 11;

	/**
	 * The meta object id for the '<em>Userdeletetable Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getUserdeletetableType()
	 * @generated
	 */
	int USERDELETETABLE_TYPE = 12;

	/**
	 * The meta object id for the '<em>Version Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getVersionTypeObject()
	 * @generated
	 */
	int VERSION_TYPE_OBJECT = 13;


	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType
	 * @generated
	 */
	EClass getDevicesType();

	/**
	 * Returns the meta object for the containment reference list '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType#getDevice <em>Device</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Device</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType#getDevice()
	 * @see #getDevicesType()
	 * @generated
	 */
	EReference getDevicesType_Device();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesType#getVersion()
	 * @see #getDevicesType()
	 * @generated
	 */
	EAttribute getDevicesType_Version();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType <em>Device Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Device Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType
	 * @generated
	 */
	EClass getDeviceType();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getEpocroot <em>Epocroot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Epocroot</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getEpocroot()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Epocroot();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getToolsroot <em>Toolsroot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Toolsroot</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getToolsroot()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Toolsroot();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getAlias()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Alias();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getDefault()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Default();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getId()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getName()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getUserdeletable <em>Userdeletable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Userdeletable</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getUserdeletable()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Userdeletable();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getUserdeletetable <em>Userdeletetable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Userdeletetable</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getUserdeletetable()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Userdeletetable();

	/**
	 * Returns the meta object for class '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the attribute list '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getMixed()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Mixed();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getXMLNSPrefixMap()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getXSISchemaLocation()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getDevice <em>Device</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Device</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getDevice()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Device();

	/**
	 * Returns the meta object for the containment reference '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getDevices <em>Devices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Devices</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getDevices()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Devices();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getEpocroot <em>Epocroot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Epocroot</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getEpocroot()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Epocroot();

	/**
	 * Returns the meta object for the attribute '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getToolsroot <em>Toolsroot</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Toolsroot</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DocumentRoot#getToolsroot()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Toolsroot();

	/**
	 * Returns the meta object for enum '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType <em>Default Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Default Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType
	 * @generated
	 */
	EEnum getDefaultType();

	/**
	 * Returns the meta object for enum '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType <em>Version Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Version Type</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType
	 * @generated
	 */
	EEnum getVersionType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Alias Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Alias Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='alias_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getAliasType();

	/**
	 * Returns the meta object for data type '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType <em>Default Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Default Type Object</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType
	 * @model instanceClass="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType"
	 *        extendedMetaData="name='default_._type:Object' baseType='default_._type'" 
	 * @generated
	 */
	EDataType getDefaultTypeObject();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Epocroot Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Epocroot Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='epocroot_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getEpocrootType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Id Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Id Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='id_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getIdType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='name_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getNameType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Toolsroot Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Toolsroot Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='toolsroot_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getToolsrootType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Userdeletable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Userdeletable Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='userdeletable_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getUserdeletableType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Userdeletetable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Userdeletetable Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='userdeletetable_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'" 
	 * @generated
	 */
	EDataType getUserdeletetableType();

	/**
	 * Returns the meta object for data type '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType <em>Version Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version Type Object</em>'.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType
	 * @model instanceClass="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType"
	 *        extendedMetaData="name='version_._type:Object' baseType='version_._type'" 
	 * @generated
	 */
	EDataType getVersionTypeObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DevicesFactory getDevicesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals  {
		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDevicesType()
		 * @generated
		 */
		EClass DEVICES_TYPE = eINSTANCE.getDevicesType();

		/**
		 * The meta object literal for the '<em><b>Device</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICES_TYPE__DEVICE = eINSTANCE.getDevicesType_Device();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICES_TYPE__VERSION = eINSTANCE.getDevicesType_Version();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl <em>Device Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDeviceType()
		 * @generated
		 */
		EClass DEVICE_TYPE = eINSTANCE.getDeviceType();

		/**
		 * The meta object literal for the '<em><b>Epocroot</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__EPOCROOT = eINSTANCE.getDeviceType_Epocroot();

		/**
		 * The meta object literal for the '<em><b>Toolsroot</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__TOOLSROOT = eINSTANCE.getDeviceType_Toolsroot();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__ALIAS = eINSTANCE.getDeviceType_Alias();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__DEFAULT = eINSTANCE.getDeviceType_Default();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__ID = eINSTANCE.getDeviceType_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__NAME = eINSTANCE.getDeviceType_Name();

		/**
		 * The meta object literal for the '<em><b>Userdeletable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__USERDELETABLE = eINSTANCE.getDeviceType_Userdeletable();

		/**
		 * The meta object literal for the '<em><b>Userdeletetable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__USERDELETETABLE = eINSTANCE.getDeviceType_Userdeletetable();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DocumentRootImpl
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__MIXED = eINSTANCE.getDocumentRoot_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XMLNS_PREFIX_MAP = eINSTANCE.getDocumentRoot_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = eINSTANCE.getDocumentRoot_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Device</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEVICE = eINSTANCE.getDocumentRoot_Device();

		/**
		 * The meta object literal for the '<em><b>Devices</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DEVICES = eINSTANCE.getDocumentRoot_Devices();

		/**
		 * The meta object literal for the '<em><b>Epocroot</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__EPOCROOT = eINSTANCE.getDocumentRoot_Epocroot();

		/**
		 * The meta object literal for the '<em><b>Toolsroot</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__TOOLSROOT = eINSTANCE.getDocumentRoot_Toolsroot();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType <em>Default Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDefaultType()
		 * @generated
		 */
		EEnum DEFAULT_TYPE = eINSTANCE.getDefaultType();

		/**
		 * The meta object literal for the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType <em>Version Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getVersionType()
		 * @generated
		 */
		EEnum VERSION_TYPE = eINSTANCE.getVersionType();

		/**
		 * The meta object literal for the '<em>Alias Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getAliasType()
		 * @generated
		 */
		EDataType ALIAS_TYPE = eINSTANCE.getAliasType();

		/**
		 * The meta object literal for the '<em>Default Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getDefaultTypeObject()
		 * @generated
		 */
		EDataType DEFAULT_TYPE_OBJECT = eINSTANCE.getDefaultTypeObject();

		/**
		 * The meta object literal for the '<em>Epocroot Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getEpocrootType()
		 * @generated
		 */
		EDataType EPOCROOT_TYPE = eINSTANCE.getEpocrootType();

		/**
		 * The meta object literal for the '<em>Id Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getIdType()
		 * @generated
		 */
		EDataType ID_TYPE = eINSTANCE.getIdType();

		/**
		 * The meta object literal for the '<em>Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getNameType()
		 * @generated
		 */
		EDataType NAME_TYPE = eINSTANCE.getNameType();

		/**
		 * The meta object literal for the '<em>Toolsroot Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getToolsrootType()
		 * @generated
		 */
		EDataType TOOLSROOT_TYPE = eINSTANCE.getToolsrootType();

		/**
		 * The meta object literal for the '<em>Userdeletable Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getUserdeletableType()
		 * @generated
		 */
		EDataType USERDELETABLE_TYPE = eINSTANCE.getUserdeletableType();

		/**
		 * The meta object literal for the '<em>Userdeletetable Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getUserdeletetableType()
		 * @generated
		 */
		EDataType USERDELETETABLE_TYPE = eINSTANCE.getUserdeletetableType();

		/**
		 * The meta object literal for the '<em>Version Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.VersionType
		 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DevicesPackageImpl#getVersionTypeObject()
		 * @generated
		 */
		EDataType VERSION_TYPE_OBJECT = eINSTANCE.getVersionTypeObject();

	}

} //DevicesPackage
