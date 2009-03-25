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

import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CanpanicType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodereviewType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodingstandardsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DocumentationType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.FunctionalityType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LegalType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LocalisationType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.OtherType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PanicType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PerformanceType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Categories Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getCanpanic <em>Canpanic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getCodereview <em>Codereview</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getCodingstandards <em>Codingstandards</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getFunctionality <em>Functionality</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getLegal <em>Legal</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getLocalisation <em>Localisation</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getOther <em>Other</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getPanic <em>Panic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CategoriesTypeImpl#getPerformance <em>Performance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CategoriesTypeImpl extends EObjectImpl implements CategoriesType {
	/**
	 * The cached value of the '{@link #getCanpanic() <em>Canpanic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCanpanic()
	 * @generated
	 * @ordered
	 */
	protected CanpanicType canpanic;

	/**
	 * The cached value of the '{@link #getCodereview() <em>Codereview</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodereview()
	 * @generated
	 * @ordered
	 */
	protected CodereviewType codereview;

	/**
	 * The cached value of the '{@link #getCodingstandards() <em>Codingstandards</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCodingstandards()
	 * @generated
	 * @ordered
	 */
	protected CodingstandardsType codingstandards;

	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected DocumentationType documentation;

	/**
	 * The cached value of the '{@link #getFunctionality() <em>Functionality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionality()
	 * @generated
	 * @ordered
	 */
	protected FunctionalityType functionality;

	/**
	 * The cached value of the '{@link #getLegal() <em>Legal</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLegal()
	 * @generated
	 * @ordered
	 */
	protected LegalType legal;

	/**
	 * The cached value of the '{@link #getLocalisation() <em>Localisation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalisation()
	 * @generated
	 * @ordered
	 */
	protected LocalisationType localisation;

	/**
	 * The cached value of the '{@link #getOther() <em>Other</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOther()
	 * @generated
	 * @ordered
	 */
	protected OtherType other;

	/**
	 * The cached value of the '{@link #getPanic() <em>Panic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPanic()
	 * @generated
	 * @ordered
	 */
	protected PanicType panic;

	/**
	 * The cached value of the '{@link #getPerformance() <em>Performance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPerformance()
	 * @generated
	 * @ordered
	 */
	protected PerformanceType performance;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CategoriesTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CSConfigPackage.eINSTANCE.getCategoriesType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CanpanicType getCanpanic() {
		return canpanic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCanpanic(CanpanicType newCanpanic, NotificationChain msgs) {
		CanpanicType oldCanpanic = canpanic;
		canpanic = newCanpanic;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__CANPANIC, oldCanpanic, newCanpanic);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCanpanic(CanpanicType newCanpanic) {
		if (newCanpanic != canpanic) {
			NotificationChain msgs = null;
			if (canpanic != null)
				msgs = ((InternalEObject)canpanic).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__CANPANIC, null, msgs);
			if (newCanpanic != null)
				msgs = ((InternalEObject)newCanpanic).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__CANPANIC, null, msgs);
			msgs = basicSetCanpanic(newCanpanic, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__CANPANIC, newCanpanic, newCanpanic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodereviewType getCodereview() {
		return codereview;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCodereview(CodereviewType newCodereview, NotificationChain msgs) {
		CodereviewType oldCodereview = codereview;
		codereview = newCodereview;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__CODEREVIEW, oldCodereview, newCodereview);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCodereview(CodereviewType newCodereview) {
		if (newCodereview != codereview) {
			NotificationChain msgs = null;
			if (codereview != null)
				msgs = ((InternalEObject)codereview).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__CODEREVIEW, null, msgs);
			if (newCodereview != null)
				msgs = ((InternalEObject)newCodereview).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__CODEREVIEW, null, msgs);
			msgs = basicSetCodereview(newCodereview, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__CODEREVIEW, newCodereview, newCodereview));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodingstandardsType getCodingstandards() {
		return codingstandards;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCodingstandards(CodingstandardsType newCodingstandards, NotificationChain msgs) {
		CodingstandardsType oldCodingstandards = codingstandards;
		codingstandards = newCodingstandards;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__CODINGSTANDARDS, oldCodingstandards, newCodingstandards);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCodingstandards(CodingstandardsType newCodingstandards) {
		if (newCodingstandards != codingstandards) {
			NotificationChain msgs = null;
			if (codingstandards != null)
				msgs = ((InternalEObject)codingstandards).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__CODINGSTANDARDS, null, msgs);
			if (newCodingstandards != null)
				msgs = ((InternalEObject)newCodingstandards).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__CODINGSTANDARDS, null, msgs);
			msgs = basicSetCodingstandards(newCodingstandards, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__CODINGSTANDARDS, newCodingstandards, newCodingstandards));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentationType getDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentation(DocumentationType newDocumentation, NotificationChain msgs) {
		DocumentationType oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__DOCUMENTATION, oldDocumentation, newDocumentation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentation(DocumentationType newDocumentation) {
		if (newDocumentation != documentation) {
			NotificationChain msgs = null;
			if (documentation != null)
				msgs = ((InternalEObject)documentation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__DOCUMENTATION, null, msgs);
			if (newDocumentation != null)
				msgs = ((InternalEObject)newDocumentation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__DOCUMENTATION, null, msgs);
			msgs = basicSetDocumentation(newDocumentation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__DOCUMENTATION, newDocumentation, newDocumentation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionalityType getFunctionality() {
		return functionality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunctionality(FunctionalityType newFunctionality, NotificationChain msgs) {
		FunctionalityType oldFunctionality = functionality;
		functionality = newFunctionality;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__FUNCTIONALITY, oldFunctionality, newFunctionality);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionality(FunctionalityType newFunctionality) {
		if (newFunctionality != functionality) {
			NotificationChain msgs = null;
			if (functionality != null)
				msgs = ((InternalEObject)functionality).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__FUNCTIONALITY, null, msgs);
			if (newFunctionality != null)
				msgs = ((InternalEObject)newFunctionality).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__FUNCTIONALITY, null, msgs);
			msgs = basicSetFunctionality(newFunctionality, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__FUNCTIONALITY, newFunctionality, newFunctionality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LegalType getLegal() {
		return legal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLegal(LegalType newLegal, NotificationChain msgs) {
		LegalType oldLegal = legal;
		legal = newLegal;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__LEGAL, oldLegal, newLegal);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLegal(LegalType newLegal) {
		if (newLegal != legal) {
			NotificationChain msgs = null;
			if (legal != null)
				msgs = ((InternalEObject)legal).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__LEGAL, null, msgs);
			if (newLegal != null)
				msgs = ((InternalEObject)newLegal).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__LEGAL, null, msgs);
			msgs = basicSetLegal(newLegal, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__LEGAL, newLegal, newLegal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalisationType getLocalisation() {
		return localisation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocalisation(LocalisationType newLocalisation, NotificationChain msgs) {
		LocalisationType oldLocalisation = localisation;
		localisation = newLocalisation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__LOCALISATION, oldLocalisation, newLocalisation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalisation(LocalisationType newLocalisation) {
		if (newLocalisation != localisation) {
			NotificationChain msgs = null;
			if (localisation != null)
				msgs = ((InternalEObject)localisation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__LOCALISATION, null, msgs);
			if (newLocalisation != null)
				msgs = ((InternalEObject)newLocalisation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__LOCALISATION, null, msgs);
			msgs = basicSetLocalisation(newLocalisation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__LOCALISATION, newLocalisation, newLocalisation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OtherType getOther() {
		return other;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOther(OtherType newOther, NotificationChain msgs) {
		OtherType oldOther = other;
		other = newOther;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__OTHER, oldOther, newOther);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOther(OtherType newOther) {
		if (newOther != other) {
			NotificationChain msgs = null;
			if (other != null)
				msgs = ((InternalEObject)other).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__OTHER, null, msgs);
			if (newOther != null)
				msgs = ((InternalEObject)newOther).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__OTHER, null, msgs);
			msgs = basicSetOther(newOther, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__OTHER, newOther, newOther));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanicType getPanic() {
		return panic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPanic(PanicType newPanic, NotificationChain msgs) {
		PanicType oldPanic = panic;
		panic = newPanic;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__PANIC, oldPanic, newPanic);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPanic(PanicType newPanic) {
		if (newPanic != panic) {
			NotificationChain msgs = null;
			if (panic != null)
				msgs = ((InternalEObject)panic).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__PANIC, null, msgs);
			if (newPanic != null)
				msgs = ((InternalEObject)newPanic).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__PANIC, null, msgs);
			msgs = basicSetPanic(newPanic, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__PANIC, newPanic, newPanic));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformanceType getPerformance() {
		return performance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPerformance(PerformanceType newPerformance, NotificationChain msgs) {
		PerformanceType oldPerformance = performance;
		performance = newPerformance;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__PERFORMANCE, oldPerformance, newPerformance);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPerformance(PerformanceType newPerformance) {
		if (newPerformance != performance) {
			NotificationChain msgs = null;
			if (performance != null)
				msgs = ((InternalEObject)performance).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__PERFORMANCE, null, msgs);
			if (newPerformance != null)
				msgs = ((InternalEObject)newPerformance).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.CATEGORIES_TYPE__PERFORMANCE, null, msgs);
			msgs = basicSetPerformance(newPerformance, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.CATEGORIES_TYPE__PERFORMANCE, newPerformance, newPerformance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CSConfigPackage.CATEGORIES_TYPE__CANPANIC:
				return basicSetCanpanic(null, msgs);
			case CSConfigPackage.CATEGORIES_TYPE__CODEREVIEW:
				return basicSetCodereview(null, msgs);
			case CSConfigPackage.CATEGORIES_TYPE__CODINGSTANDARDS:
				return basicSetCodingstandards(null, msgs);
			case CSConfigPackage.CATEGORIES_TYPE__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case CSConfigPackage.CATEGORIES_TYPE__FUNCTIONALITY:
				return basicSetFunctionality(null, msgs);
			case CSConfigPackage.CATEGORIES_TYPE__LEGAL:
				return basicSetLegal(null, msgs);
			case CSConfigPackage.CATEGORIES_TYPE__LOCALISATION:
				return basicSetLocalisation(null, msgs);
			case CSConfigPackage.CATEGORIES_TYPE__OTHER:
				return basicSetOther(null, msgs);
			case CSConfigPackage.CATEGORIES_TYPE__PANIC:
				return basicSetPanic(null, msgs);
			case CSConfigPackage.CATEGORIES_TYPE__PERFORMANCE:
				return basicSetPerformance(null, msgs);
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
			case CSConfigPackage.CATEGORIES_TYPE__CANPANIC:
				return getCanpanic();
			case CSConfigPackage.CATEGORIES_TYPE__CODEREVIEW:
				return getCodereview();
			case CSConfigPackage.CATEGORIES_TYPE__CODINGSTANDARDS:
				return getCodingstandards();
			case CSConfigPackage.CATEGORIES_TYPE__DOCUMENTATION:
				return getDocumentation();
			case CSConfigPackage.CATEGORIES_TYPE__FUNCTIONALITY:
				return getFunctionality();
			case CSConfigPackage.CATEGORIES_TYPE__LEGAL:
				return getLegal();
			case CSConfigPackage.CATEGORIES_TYPE__LOCALISATION:
				return getLocalisation();
			case CSConfigPackage.CATEGORIES_TYPE__OTHER:
				return getOther();
			case CSConfigPackage.CATEGORIES_TYPE__PANIC:
				return getPanic();
			case CSConfigPackage.CATEGORIES_TYPE__PERFORMANCE:
				return getPerformance();
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
			case CSConfigPackage.CATEGORIES_TYPE__CANPANIC:
				setCanpanic((CanpanicType)newValue);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__CODEREVIEW:
				setCodereview((CodereviewType)newValue);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__CODINGSTANDARDS:
				setCodingstandards((CodingstandardsType)newValue);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__DOCUMENTATION:
				setDocumentation((DocumentationType)newValue);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__FUNCTIONALITY:
				setFunctionality((FunctionalityType)newValue);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__LEGAL:
				setLegal((LegalType)newValue);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__LOCALISATION:
				setLocalisation((LocalisationType)newValue);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__OTHER:
				setOther((OtherType)newValue);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__PANIC:
				setPanic((PanicType)newValue);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__PERFORMANCE:
				setPerformance((PerformanceType)newValue);
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
			case CSConfigPackage.CATEGORIES_TYPE__CANPANIC:
				setCanpanic((CanpanicType)null);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__CODEREVIEW:
				setCodereview((CodereviewType)null);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__CODINGSTANDARDS:
				setCodingstandards((CodingstandardsType)null);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__DOCUMENTATION:
				setDocumentation((DocumentationType)null);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__FUNCTIONALITY:
				setFunctionality((FunctionalityType)null);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__LEGAL:
				setLegal((LegalType)null);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__LOCALISATION:
				setLocalisation((LocalisationType)null);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__OTHER:
				setOther((OtherType)null);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__PANIC:
				setPanic((PanicType)null);
				return;
			case CSConfigPackage.CATEGORIES_TYPE__PERFORMANCE:
				setPerformance((PerformanceType)null);
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
			case CSConfigPackage.CATEGORIES_TYPE__CANPANIC:
				return canpanic != null;
			case CSConfigPackage.CATEGORIES_TYPE__CODEREVIEW:
				return codereview != null;
			case CSConfigPackage.CATEGORIES_TYPE__CODINGSTANDARDS:
				return codingstandards != null;
			case CSConfigPackage.CATEGORIES_TYPE__DOCUMENTATION:
				return documentation != null;
			case CSConfigPackage.CATEGORIES_TYPE__FUNCTIONALITY:
				return functionality != null;
			case CSConfigPackage.CATEGORIES_TYPE__LEGAL:
				return legal != null;
			case CSConfigPackage.CATEGORIES_TYPE__LOCALISATION:
				return localisation != null;
			case CSConfigPackage.CATEGORIES_TYPE__OTHER:
				return other != null;
			case CSConfigPackage.CATEGORIES_TYPE__PANIC:
				return panic != null;
			case CSConfigPackage.CATEGORIES_TYPE__PERFORMANCE:
				return performance != null;
		}
		return super.eIsSet(featureID);
	}

} //CategoriesTypeImpl