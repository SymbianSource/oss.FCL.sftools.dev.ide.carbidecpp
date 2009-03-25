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

package com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl;

import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodescannerConfigType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomrulesType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SourcesType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Codescanner Config Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CodescannerConfigTypeImpl#getArguments <em>Arguments</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CodescannerConfigTypeImpl#getCustomrules <em>Customrules</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CodescannerConfigTypeImpl#getSources <em>Sources</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CodescannerConfigTypeImpl#getScripts <em>Scripts</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CodescannerConfigTypeImpl#getSeverities <em>Severities</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CodescannerConfigTypeImpl#getCategories <em>Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CodescannerConfigTypeImpl extends EObjectImpl implements CodescannerConfigType {
	/**
	 * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected ArgumentsType arguments;

	/**
	 * The cached value of the '{@link #getCustomrules() <em>Customrules</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCustomrules()
	 * @generated
	 * @ordered
	 */
	protected CustomrulesType customrules;

	/**
	 * The cached value of the '{@link #getSources() <em>Sources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSources()
	 * @generated
	 * @ordered
	 */
	protected SourcesType sources;

	/**
	 * The cached value of the '{@link #getScripts() <em>Scripts</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScripts()
	 * @generated
	 * @ordered
	 */
	protected ScriptsType scripts;

	/**
	 * The cached value of the '{@link #getSeverities() <em>Severities</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverities()
	 * @generated
	 * @ordered
	 */
	protected SeveritiesType severities;

	/**
	 * The cached value of the '{@link #getCategories() <em>Categories</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategories()
	 * @generated
	 * @ordered
	 */
	protected CategoriesType categories;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CodescannerConfigTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CSConfigPackage.eINSTANCE.getCodescannerConfigType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArgumentsType getArguments() {
		return arguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArguments(ArgumentsType newArguments, NotificationChain msgs) {
		ArgumentsType oldArguments = arguments;
		arguments = newArguments;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__ARGUMENTS, oldArguments, newArguments);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArguments(ArgumentsType newArguments) {
		if (newArguments != arguments) {
			NotificationChain msgs = null;
			if (arguments != null)
				msgs = ((InternalEObject)arguments).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__ARGUMENTS, null, msgs);
			if (newArguments != null)
				msgs = ((InternalEObject)newArguments).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__ARGUMENTS, null, msgs);
			msgs = basicSetArguments(newArguments, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__ARGUMENTS, newArguments, newArguments));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomrulesType getCustomrules() {
		return customrules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCustomrules(CustomrulesType newCustomrules, NotificationChain msgs) {
		CustomrulesType oldCustomrules = customrules;
		customrules = newCustomrules;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__CUSTOMRULES, oldCustomrules, newCustomrules);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCustomrules(CustomrulesType newCustomrules) {
		if (newCustomrules != customrules) {
			NotificationChain msgs = null;
			if (customrules != null)
				msgs = ((InternalEObject)customrules).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__CUSTOMRULES, null, msgs);
			if (newCustomrules != null)
				msgs = ((InternalEObject)newCustomrules).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__CUSTOMRULES, null, msgs);
			msgs = basicSetCustomrules(newCustomrules, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__CUSTOMRULES, newCustomrules, newCustomrules));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourcesType getSources() {
		return sources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSources(SourcesType newSources, NotificationChain msgs) {
		SourcesType oldSources = sources;
		sources = newSources;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__SOURCES, oldSources, newSources);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSources(SourcesType newSources) {
		if (newSources != sources) {
			NotificationChain msgs = null;
			if (sources != null)
				msgs = ((InternalEObject)sources).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__SOURCES, null, msgs);
			if (newSources != null)
				msgs = ((InternalEObject)newSources).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__SOURCES, null, msgs);
			msgs = basicSetSources(newSources, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__SOURCES, newSources, newSources));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScriptsType getScripts() {
		return scripts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetScripts(ScriptsType newScripts, NotificationChain msgs) {
		ScriptsType oldScripts = scripts;
		scripts = newScripts;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__SCRIPTS, oldScripts, newScripts);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScripts(ScriptsType newScripts) {
		if (newScripts != scripts) {
			NotificationChain msgs = null;
			if (scripts != null)
				msgs = ((InternalEObject)scripts).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__SCRIPTS, null, msgs);
			if (newScripts != null)
				msgs = ((InternalEObject)newScripts).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__SCRIPTS, null, msgs);
			msgs = basicSetScripts(newScripts, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__SCRIPTS, newScripts, newScripts));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeveritiesType getSeverities() {
		return severities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSeverities(SeveritiesType newSeverities, NotificationChain msgs) {
		SeveritiesType oldSeverities = severities;
		severities = newSeverities;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__SEVERITIES, oldSeverities, newSeverities);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeverities(SeveritiesType newSeverities) {
		if (newSeverities != severities) {
			NotificationChain msgs = null;
			if (severities != null)
				msgs = ((InternalEObject)severities).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__SEVERITIES, null, msgs);
			if (newSeverities != null)
				msgs = ((InternalEObject)newSeverities).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__SEVERITIES, null, msgs);
			msgs = basicSetSeverities(newSeverities, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__SEVERITIES, newSeverities, newSeverities));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoriesType getCategories() {
		return categories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCategories(CategoriesType newCategories, NotificationChain msgs) {
		CategoriesType oldCategories = categories;
		categories = newCategories;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__CATEGORIES, oldCategories, newCategories);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategories(CategoriesType newCategories) {
		if (newCategories != categories) {
			NotificationChain msgs = null;
			if (categories != null)
				msgs = ((InternalEObject)categories).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__CATEGORIES, null, msgs);
			if (newCategories != null)
				msgs = ((InternalEObject)newCategories).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CODESCANNER_CONFIG_TYPE__CATEGORIES, null, msgs);
			msgs = basicSetCategories(newCategories, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CODESCANNER_CONFIG_TYPE__CATEGORIES, newCategories, newCategories));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__ARGUMENTS:
				return basicSetArguments(null, msgs);
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CUSTOMRULES:
				return basicSetCustomrules(null, msgs);
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SOURCES:
				return basicSetSources(null, msgs);
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SCRIPTS:
				return basicSetScripts(null, msgs);
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SEVERITIES:
				return basicSetSeverities(null, msgs);
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CATEGORIES:
				return basicSetCategories(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__ARGUMENTS:
				return getArguments();
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CUSTOMRULES:
				return getCustomrules();
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SOURCES:
				return getSources();
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SCRIPTS:
				return getScripts();
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SEVERITIES:
				return getSeverities();
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CATEGORIES:
				return getCategories();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__ARGUMENTS:
				setArguments((ArgumentsType)newValue);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CUSTOMRULES:
				setCustomrules((CustomrulesType)newValue);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SOURCES:
				setSources((SourcesType)newValue);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SCRIPTS:
				setScripts((ScriptsType)newValue);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SEVERITIES:
				setSeverities((SeveritiesType)newValue);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CATEGORIES:
				setCategories((CategoriesType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__ARGUMENTS:
				setArguments((ArgumentsType)null);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CUSTOMRULES:
				setCustomrules((CustomrulesType)null);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SOURCES:
				setSources((SourcesType)null);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SCRIPTS:
				setScripts((ScriptsType)null);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SEVERITIES:
				setSeverities((SeveritiesType)null);
				return;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CATEGORIES:
				setCategories((CategoriesType)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__ARGUMENTS:
				return arguments != null;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CUSTOMRULES:
				return customrules != null;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SOURCES:
				return sources != null;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SCRIPTS:
				return scripts != null;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__SEVERITIES:
				return severities != null;
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE__CATEGORIES:
				return categories != null;
		}
		return super.eIsSet(featureID);
	}

} //CodescannerConfigTypeImpl