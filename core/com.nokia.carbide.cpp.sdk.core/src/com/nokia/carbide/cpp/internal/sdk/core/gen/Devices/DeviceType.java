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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Device Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getEpocroot <em>Epocroot</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getToolsroot <em>Toolsroot</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getDefault <em>Default</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getUserdeletable <em>Userdeletable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getUserdeletetable <em>Userdeletetable</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDeviceType()
 * @model extendedMetaData="name='device_._type' kind='elementOnly'"
 * @generated
 */
public interface DeviceType extends EObject {
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
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDeviceType_Epocroot()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.EpocrootType" required="true"
	 *        extendedMetaData="kind='element' name='epocroot' namespace='##targetNamespace'"
	 * @generated
	 */
	String getEpocroot();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getEpocroot <em>Epocroot</em>}' attribute.
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
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDeviceType_Toolsroot()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.ToolsrootType" required="true"
	 *        extendedMetaData="kind='element' name='toolsroot' namespace='##targetNamespace'"
	 * @generated
	 */
	String getToolsroot();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getToolsroot <em>Toolsroot</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Toolsroot</em>' attribute.
	 * @see #getToolsroot()
	 * @generated
	 */
	void setToolsroot(String value);

	/**
	 * Returns the value of the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alias</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alias</em>' attribute.
	 * @see #setAlias(String)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDeviceType_Alias()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.AliasType"
	 *        extendedMetaData="kind='attribute' name='alias' namespace='##targetNamespace'"
	 * @generated
	 */
	String getAlias();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getAlias <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias</em>' attribute.
	 * @see #getAlias()
	 * @generated
	 */
	void setAlias(String value);

	/**
	 * Returns the value of the '<em><b>Default</b></em>' attribute.
	 * The default value is <code>"no"</code>.
	 * The literals are from the enumeration {@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType
	 * @see #isSetDefault()
	 * @see #unsetDefault()
	 * @see #setDefault(DefaultType)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDeviceType_Default()
	 * @model default="no" unique="false" unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='default' namespace='##targetNamespace'"
	 * @generated
	 */
	DefaultType getDefault();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getDefault <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' attribute.
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType
	 * @see #isSetDefault()
	 * @see #unsetDefault()
	 * @see #getDefault()
	 * @generated
	 */
	void setDefault(DefaultType value);

	/**
	 * Unsets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getDefault <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDefault()
	 * @see #getDefault()
	 * @see #setDefault(DefaultType)
	 * @generated
	 */
	void unsetDefault();

	/**
	 * Returns whether the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getDefault <em>Default</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Default</em>' attribute is set.
	 * @see #unsetDefault()
	 * @see #getDefault()
	 * @see #setDefault(DefaultType)
	 * @generated
	 */
	boolean isSetDefault();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDeviceType_Id()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.IdType" required="true"
	 *        extendedMetaData="kind='attribute' name='id' namespace='##targetNamespace'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDeviceType_Name()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.NameType" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Userdeletable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Userdeletable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Userdeletable</em>' attribute.
	 * @see #setUserdeletable(String)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDeviceType_Userdeletable()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.UserdeletableType"
	 *        extendedMetaData="kind='attribute' name='userdeletable' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUserdeletable();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getUserdeletable <em>Userdeletable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Userdeletable</em>' attribute.
	 * @see #getUserdeletable()
	 * @generated
	 */
	void setUserdeletable(String value);

	/**
	 * Returns the value of the '<em><b>Userdeletetable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Userdeletetable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Userdeletetable</em>' attribute.
	 * @see #setUserdeletetable(String)
	 * @see com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage#getDeviceType_Userdeletetable()
	 * @model unique="false" dataType="com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.UserdeletetableType"
	 *        extendedMetaData="kind='attribute' name='userdeletetable' namespace='##targetNamespace'"
	 * @generated
	 */
	String getUserdeletetable();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType#getUserdeletetable <em>Userdeletetable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Userdeletetable</em>' attribute.
	 * @see #getUserdeletetable()
	 * @generated
	 */
	void setUserdeletetable(String value);

} // DeviceType