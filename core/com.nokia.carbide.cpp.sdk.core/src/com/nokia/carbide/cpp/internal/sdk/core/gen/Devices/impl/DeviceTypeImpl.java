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

import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DefaultType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DeviceType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.DevicesPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Device Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl#getEpocroot <em>Epocroot</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl#getToolsroot <em>Toolsroot</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl#getDefault <em>Default</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl#getUserdeletable <em>Userdeletable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.sdk.core.gen.Devices.impl.DeviceTypeImpl#getUserdeletetable <em>Userdeletetable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeviceTypeImpl extends EObjectImpl implements DeviceType {
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
	 * The cached value of the '{@link #getEpocroot() <em>Epocroot</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEpocroot()
	 * @generated
	 * @ordered
	 */
	protected String epocroot = EPOCROOT_EDEFAULT;

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
	 * The cached value of the '{@link #getToolsroot() <em>Toolsroot</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToolsroot()
	 * @generated
	 * @ordered
	 */
	protected String toolsroot = TOOLSROOT_EDEFAULT;

	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefault()
	 * @generated
	 * @ordered
	 */
	protected static final DefaultType DEFAULT_EDEFAULT = DefaultType.NO_LITERAL;

	/**
	 * The cached value of the '{@link #getDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefault()
	 * @generated
	 * @ordered
	 */
	protected DefaultType default_ = DEFAULT_EDEFAULT;

	/**
	 * This is true if the Default attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean defaultESet = false;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getUserdeletable() <em>Userdeletable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserdeletable()
	 * @generated
	 * @ordered
	 */
	protected static final String USERDELETABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUserdeletable() <em>Userdeletable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserdeletable()
	 * @generated
	 * @ordered
	 */
	protected String userdeletable = USERDELETABLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUserdeletetable() <em>Userdeletetable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserdeletetable()
	 * @generated
	 * @ordered
	 */
	protected static final String USERDELETETABLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUserdeletetable() <em>Userdeletetable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserdeletetable()
	 * @generated
	 * @ordered
	 */
	protected String userdeletetable = USERDELETETABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeviceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DevicesPackage.Literals.DEVICE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEpocroot() {
		return epocroot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEpocroot(String newEpocroot) {
		String oldEpocroot = epocroot;
		epocroot = newEpocroot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevicesPackage.DEVICE_TYPE__EPOCROOT, oldEpocroot, epocroot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getToolsroot() {
		return toolsroot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToolsroot(String newToolsroot) {
		String oldToolsroot = toolsroot;
		toolsroot = newToolsroot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevicesPackage.DEVICE_TYPE__TOOLSROOT, oldToolsroot, toolsroot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevicesPackage.DEVICE_TYPE__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefaultType getDefault() {
		return default_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefault(DefaultType newDefault) {
		DefaultType oldDefault = default_;
		default_ = newDefault == null ? DEFAULT_EDEFAULT : newDefault;
		boolean oldDefaultESet = defaultESet;
		defaultESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevicesPackage.DEVICE_TYPE__DEFAULT, oldDefault, default_, !oldDefaultESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDefault() {
		DefaultType oldDefault = default_;
		boolean oldDefaultESet = defaultESet;
		default_ = DEFAULT_EDEFAULT;
		defaultESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, DevicesPackage.DEVICE_TYPE__DEFAULT, oldDefault, DEFAULT_EDEFAULT, oldDefaultESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDefault() {
		return defaultESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevicesPackage.DEVICE_TYPE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevicesPackage.DEVICE_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUserdeletable() {
		return userdeletable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserdeletable(String newUserdeletable) {
		String oldUserdeletable = userdeletable;
		userdeletable = newUserdeletable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevicesPackage.DEVICE_TYPE__USERDELETABLE, oldUserdeletable, userdeletable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUserdeletetable() {
		return userdeletetable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserdeletetable(String newUserdeletetable) {
		String oldUserdeletetable = userdeletetable;
		userdeletetable = newUserdeletetable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DevicesPackage.DEVICE_TYPE__USERDELETETABLE, oldUserdeletetable, userdeletetable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DevicesPackage.DEVICE_TYPE__EPOCROOT:
				return getEpocroot();
			case DevicesPackage.DEVICE_TYPE__TOOLSROOT:
				return getToolsroot();
			case DevicesPackage.DEVICE_TYPE__ALIAS:
				return getAlias();
			case DevicesPackage.DEVICE_TYPE__DEFAULT:
				return getDefault();
			case DevicesPackage.DEVICE_TYPE__ID:
				return getId();
			case DevicesPackage.DEVICE_TYPE__NAME:
				return getName();
			case DevicesPackage.DEVICE_TYPE__USERDELETABLE:
				return getUserdeletable();
			case DevicesPackage.DEVICE_TYPE__USERDELETETABLE:
				return getUserdeletetable();
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
			case DevicesPackage.DEVICE_TYPE__EPOCROOT:
				setEpocroot((String)newValue);
				return;
			case DevicesPackage.DEVICE_TYPE__TOOLSROOT:
				setToolsroot((String)newValue);
				return;
			case DevicesPackage.DEVICE_TYPE__ALIAS:
				setAlias((String)newValue);
				return;
			case DevicesPackage.DEVICE_TYPE__DEFAULT:
				setDefault((DefaultType)newValue);
				return;
			case DevicesPackage.DEVICE_TYPE__ID:
				setId((String)newValue);
				return;
			case DevicesPackage.DEVICE_TYPE__NAME:
				setName((String)newValue);
				return;
			case DevicesPackage.DEVICE_TYPE__USERDELETABLE:
				setUserdeletable((String)newValue);
				return;
			case DevicesPackage.DEVICE_TYPE__USERDELETETABLE:
				setUserdeletetable((String)newValue);
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
			case DevicesPackage.DEVICE_TYPE__EPOCROOT:
				setEpocroot(EPOCROOT_EDEFAULT);
				return;
			case DevicesPackage.DEVICE_TYPE__TOOLSROOT:
				setToolsroot(TOOLSROOT_EDEFAULT);
				return;
			case DevicesPackage.DEVICE_TYPE__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case DevicesPackage.DEVICE_TYPE__DEFAULT:
				unsetDefault();
				return;
			case DevicesPackage.DEVICE_TYPE__ID:
				setId(ID_EDEFAULT);
				return;
			case DevicesPackage.DEVICE_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DevicesPackage.DEVICE_TYPE__USERDELETABLE:
				setUserdeletable(USERDELETABLE_EDEFAULT);
				return;
			case DevicesPackage.DEVICE_TYPE__USERDELETETABLE:
				setUserdeletetable(USERDELETETABLE_EDEFAULT);
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
			case DevicesPackage.DEVICE_TYPE__EPOCROOT:
				return EPOCROOT_EDEFAULT == null ? epocroot != null : !EPOCROOT_EDEFAULT.equals(epocroot);
			case DevicesPackage.DEVICE_TYPE__TOOLSROOT:
				return TOOLSROOT_EDEFAULT == null ? toolsroot != null : !TOOLSROOT_EDEFAULT.equals(toolsroot);
			case DevicesPackage.DEVICE_TYPE__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case DevicesPackage.DEVICE_TYPE__DEFAULT:
				return isSetDefault();
			case DevicesPackage.DEVICE_TYPE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DevicesPackage.DEVICE_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DevicesPackage.DEVICE_TYPE__USERDELETABLE:
				return USERDELETABLE_EDEFAULT == null ? userdeletable != null : !USERDELETABLE_EDEFAULT.equals(userdeletable);
			case DevicesPackage.DEVICE_TYPE__USERDELETETABLE:
				return USERDELETETABLE_EDEFAULT == null ? userdeletetable != null : !USERDELETETABLE_EDEFAULT.equals(userdeletetable);
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
		result.append(" (epocroot: ");
		result.append(epocroot);
		result.append(", toolsroot: ");
		result.append(toolsroot);
		result.append(", alias: ");
		result.append(alias);
		result.append(", default: ");
		if (defaultESet) result.append(default_); else result.append("<unset>");
		result.append(", id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", userdeletable: ");
		result.append(userdeletable);
		result.append(", userdeletetable: ");
		result.append(userdeletetable);
		result.append(')');
		return result.toString();
	}

} //DeviceTypeImpl