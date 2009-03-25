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
* Contributors:
*
* Description: 
*
*/
package com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl;

import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.CarbideBuildConfigPackage;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.ConfigurationType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.EnvVarsType;
import com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.SisBuilderType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.ConfigurationTypeImpl#getSisBuilder <em>Sis Builder</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.ConfigurationTypeImpl#getEnvVars <em>Env Vars</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.ConfigurationTypeImpl#getEpocRoot <em>Epoc Root</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.ConfigurationTypeImpl#getErrorParsers <em>Error Parsers</em>}</li>
 *   <li>{@link com.nokia.carbide.cdt.internal.builder.gen.CarbideBuildConfig.impl.ConfigurationTypeImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigurationTypeImpl extends EObjectImpl implements ConfigurationType {
	/**
	 * The cached value of the '{@link #getSisBuilder() <em>Sis Builder</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSisBuilder()
	 * @generated
	 * @ordered
	 */
	protected SisBuilderType sisBuilder = null;

	/**
	 * The cached value of the '{@link #getEnvVars() <em>Env Vars</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnvVars()
	 * @generated
	 * @ordered
	 */
	protected EnvVarsType envVars = null;

	/**
	 * The default value of the '{@link #getEpocRoot() <em>Epoc Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEpocRoot()
	 * @generated
	 * @ordered
	 */
	protected static final String EPOC_ROOT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEpocRoot() <em>Epoc Root</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEpocRoot()
	 * @generated
	 * @ordered
	 */
	protected String epocRoot = EPOC_ROOT_EDEFAULT;

	/**
	 * The default value of the '{@link #getErrorParsers() <em>Error Parsers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorParsers()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_PARSERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getErrorParsers() <em>Error Parsers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorParsers()
	 * @generated
	 * @ordered
	 */
	protected String errorParsers = ERROR_PARSERS_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return CarbideBuildConfigPackage.Literals.CONFIGURATION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SisBuilderType getSisBuilder() {
		return sisBuilder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSisBuilder(SisBuilderType newSisBuilder, NotificationChain msgs) {
		SisBuilderType oldSisBuilder = sisBuilder;
		sisBuilder = newSisBuilder;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.CONFIGURATION_TYPE__SIS_BUILDER, oldSisBuilder, newSisBuilder);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSisBuilder(SisBuilderType newSisBuilder) {
		if (newSisBuilder != sisBuilder) {
			NotificationChain msgs = null;
			if (sisBuilder != null)
				msgs = ((InternalEObject)sisBuilder).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CarbideBuildConfigPackage.CONFIGURATION_TYPE__SIS_BUILDER, null, msgs);
			if (newSisBuilder != null)
				msgs = ((InternalEObject)newSisBuilder).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CarbideBuildConfigPackage.CONFIGURATION_TYPE__SIS_BUILDER, null, msgs);
			msgs = basicSetSisBuilder(newSisBuilder, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.CONFIGURATION_TYPE__SIS_BUILDER, newSisBuilder, newSisBuilder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnvVarsType getEnvVars() {
		return envVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnvVars(EnvVarsType newEnvVars, NotificationChain msgs) {
		EnvVarsType oldEnvVars = envVars;
		envVars = newEnvVars;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.CONFIGURATION_TYPE__ENV_VARS, oldEnvVars, newEnvVars);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnvVars(EnvVarsType newEnvVars) {
		if (newEnvVars != envVars) {
			NotificationChain msgs = null;
			if (envVars != null)
				msgs = ((InternalEObject)envVars).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CarbideBuildConfigPackage.CONFIGURATION_TYPE__ENV_VARS, null, msgs);
			if (newEnvVars != null)
				msgs = ((InternalEObject)newEnvVars).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CarbideBuildConfigPackage.CONFIGURATION_TYPE__ENV_VARS, null, msgs);
			msgs = basicSetEnvVars(newEnvVars, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.CONFIGURATION_TYPE__ENV_VARS, newEnvVars, newEnvVars));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEpocRoot() {
		return epocRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEpocRoot(String newEpocRoot) {
		String oldEpocRoot = epocRoot;
		epocRoot = newEpocRoot;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.CONFIGURATION_TYPE__EPOC_ROOT, oldEpocRoot, epocRoot));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getErrorParsers() {
		return errorParsers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setErrorParsers(String newErrorParsers) {
		String oldErrorParsers = errorParsers;
		errorParsers = newErrorParsers;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.CONFIGURATION_TYPE__ERROR_PARSERS, oldErrorParsers, errorParsers));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CarbideBuildConfigPackage.CONFIGURATION_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__SIS_BUILDER:
				return basicSetSisBuilder(null, msgs);
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__ENV_VARS:
				return basicSetEnvVars(null, msgs);
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
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__SIS_BUILDER:
				return getSisBuilder();
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__ENV_VARS:
				return getEnvVars();
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__EPOC_ROOT:
				return getEpocRoot();
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__ERROR_PARSERS:
				return getErrorParsers();
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__NAME:
				return getName();
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
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__SIS_BUILDER:
				setSisBuilder((SisBuilderType)newValue);
				return;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__ENV_VARS:
				setEnvVars((EnvVarsType)newValue);
				return;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__EPOC_ROOT:
				setEpocRoot((String)newValue);
				return;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__ERROR_PARSERS:
				setErrorParsers((String)newValue);
				return;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__NAME:
				setName((String)newValue);
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
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__SIS_BUILDER:
				setSisBuilder((SisBuilderType)null);
				return;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__ENV_VARS:
				setEnvVars((EnvVarsType)null);
				return;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__EPOC_ROOT:
				setEpocRoot(EPOC_ROOT_EDEFAULT);
				return;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__ERROR_PARSERS:
				setErrorParsers(ERROR_PARSERS_EDEFAULT);
				return;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__NAME:
				setName(NAME_EDEFAULT);
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
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__SIS_BUILDER:
				return sisBuilder != null;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__ENV_VARS:
				return envVars != null;
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__EPOC_ROOT:
				return EPOC_ROOT_EDEFAULT == null ? epocRoot != null : !EPOC_ROOT_EDEFAULT.equals(epocRoot);
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__ERROR_PARSERS:
				return ERROR_PARSERS_EDEFAULT == null ? errorParsers != null : !ERROR_PARSERS_EDEFAULT.equals(errorParsers);
			case CarbideBuildConfigPackage.CONFIGURATION_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (epocRoot: ");
		result.append(epocRoot);
		result.append(", errorParsers: ");
		result.append(errorParsers);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ConfigurationTypeImpl