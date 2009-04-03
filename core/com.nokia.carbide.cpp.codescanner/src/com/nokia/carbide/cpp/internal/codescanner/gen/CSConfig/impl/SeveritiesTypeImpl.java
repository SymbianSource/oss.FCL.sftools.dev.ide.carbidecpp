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
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.HighType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LowType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MediumType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Severities Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.SeveritiesTypeImpl#getHigh <em>High</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.SeveritiesTypeImpl#getMedium <em>Medium</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.SeveritiesTypeImpl#getLow <em>Low</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SeveritiesTypeImpl extends EObjectImpl implements SeveritiesType {
	/**
	 * The cached value of the '{@link #getHigh() <em>High</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHigh()
	 * @generated
	 * @ordered
	 */
	protected HighType high;

	/**
	 * The cached value of the '{@link #getMedium() <em>Medium</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMedium()
	 * @generated
	 * @ordered
	 */
	protected MediumType medium;

	/**
	 * The cached value of the '{@link #getLow() <em>Low</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLow()
	 * @generated
	 * @ordered
	 */
	protected LowType low;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SeveritiesTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CSConfigPackage.eINSTANCE.getSeveritiesType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HighType getHigh() {
		return high;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHigh(HighType newHigh, NotificationChain msgs) {
		HighType oldHigh = high;
		high = newHigh;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.SEVERITIES_TYPE__HIGH, oldHigh, newHigh);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHigh(HighType newHigh) {
		if (newHigh != high) {
			NotificationChain msgs = null;
			if (high != null)
				msgs = ((InternalEObject)high).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.SEVERITIES_TYPE__HIGH, null, msgs);
			if (newHigh != null)
				msgs = ((InternalEObject)newHigh).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.SEVERITIES_TYPE__HIGH, null, msgs);
			msgs = basicSetHigh(newHigh, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.SEVERITIES_TYPE__HIGH, newHigh, newHigh));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MediumType getMedium() {
		return medium;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMedium(MediumType newMedium, NotificationChain msgs) {
		MediumType oldMedium = medium;
		medium = newMedium;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.SEVERITIES_TYPE__MEDIUM, oldMedium, newMedium);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMedium(MediumType newMedium) {
		if (newMedium != medium) {
			NotificationChain msgs = null;
			if (medium != null)
				msgs = ((InternalEObject)medium).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.SEVERITIES_TYPE__MEDIUM, null, msgs);
			if (newMedium != null)
				msgs = ((InternalEObject)newMedium).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.SEVERITIES_TYPE__MEDIUM, null, msgs);
			msgs = basicSetMedium(newMedium, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.SEVERITIES_TYPE__MEDIUM, newMedium, newMedium));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LowType getLow() {
		return low;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLow(LowType newLow, NotificationChain msgs) {
		LowType oldLow = low;
		low = newLow;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CSConfigPackage.SEVERITIES_TYPE__LOW, oldLow, newLow);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLow(LowType newLow) {
		if (newLow != low) {
			NotificationChain msgs = null;
			if (low != null)
				msgs = ((InternalEObject)low).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.SEVERITIES_TYPE__LOW, null, msgs);
			if (newLow != null)
				msgs = ((InternalEObject)newLow).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CSConfigPackage.SEVERITIES_TYPE__LOW, null, msgs);
			msgs = basicSetLow(newLow, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.SEVERITIES_TYPE__LOW, newLow, newLow));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CSConfigPackage.SEVERITIES_TYPE__HIGH:
				return basicSetHigh(null, msgs);
			case CSConfigPackage.SEVERITIES_TYPE__MEDIUM:
				return basicSetMedium(null, msgs);
			case CSConfigPackage.SEVERITIES_TYPE__LOW:
				return basicSetLow(null, msgs);
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
			case CSConfigPackage.SEVERITIES_TYPE__HIGH:
				return getHigh();
			case CSConfigPackage.SEVERITIES_TYPE__MEDIUM:
				return getMedium();
			case CSConfigPackage.SEVERITIES_TYPE__LOW:
				return getLow();
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
			case CSConfigPackage.SEVERITIES_TYPE__HIGH:
				setHigh((HighType)newValue);
				return;
			case CSConfigPackage.SEVERITIES_TYPE__MEDIUM:
				setMedium((MediumType)newValue);
				return;
			case CSConfigPackage.SEVERITIES_TYPE__LOW:
				setLow((LowType)newValue);
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
			case CSConfigPackage.SEVERITIES_TYPE__HIGH:
				setHigh((HighType)null);
				return;
			case CSConfigPackage.SEVERITIES_TYPE__MEDIUM:
				setMedium((MediumType)null);
				return;
			case CSConfigPackage.SEVERITIES_TYPE__LOW:
				setLow((LowType)null);
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
			case CSConfigPackage.SEVERITIES_TYPE__HIGH:
				return high != null;
			case CSConfigPackage.SEVERITIES_TYPE__MEDIUM:
				return medium != null;
			case CSConfigPackage.SEVERITIES_TYPE__LOW:
				return low != null;
		}
		return super.eIsSet(featureID);
	}

} //SeveritiesTypeImpl