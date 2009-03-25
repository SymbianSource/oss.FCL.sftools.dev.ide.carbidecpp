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

package com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl;

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywords;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywordsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Keywords</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.impl.KbdataKeywordsImpl#getKbdataKeywords <em>Kbdata Keywords</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class KbdataKeywordsImpl extends EObjectImpl implements KbdataKeywords {
	/**
	 * The cached value of the '{@link #getKbdataKeywords() <em>Kbdata Keywords</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKbdataKeywords()
	 * @generated
	 * @ordered
	 */
	protected KbdataKeywordsType kbdataKeywords;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KbdataKeywordsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return KbdataPackage.eINSTANCE.getKbdataKeywords();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataKeywordsType getKbdataKeywords() {
		return kbdataKeywords;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKbdataKeywords(KbdataKeywordsType newKbdataKeywords, NotificationChain msgs) {
		KbdataKeywordsType oldKbdataKeywords = kbdataKeywords;
		kbdataKeywords = newKbdataKeywords;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS__KBDATA_KEYWORDS, oldKbdataKeywords, newKbdataKeywords);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKbdataKeywords(KbdataKeywordsType newKbdataKeywords) {
		if (newKbdataKeywords != kbdataKeywords) {
			NotificationChain msgs = null;
			if (kbdataKeywords != null)
				msgs = ((InternalEObject)kbdataKeywords).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS__KBDATA_KEYWORDS, null, msgs);
			if (newKbdataKeywords != null)
				msgs = ((InternalEObject)newKbdataKeywords).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KbdataPackage.KBDATA_KEYWORDS__KBDATA_KEYWORDS, null, msgs);
			msgs = basicSetKbdataKeywords(newKbdataKeywords, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KbdataPackage.KBDATA_KEYWORDS__KBDATA_KEYWORDS, newKbdataKeywords, newKbdataKeywords));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case KbdataPackage.KBDATA_KEYWORDS__KBDATA_KEYWORDS:
				return basicSetKbdataKeywords(null, msgs);
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
			case KbdataPackage.KBDATA_KEYWORDS__KBDATA_KEYWORDS:
				return getKbdataKeywords();
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
			case KbdataPackage.KBDATA_KEYWORDS__KBDATA_KEYWORDS:
				setKbdataKeywords((KbdataKeywordsType)newValue);
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
			case KbdataPackage.KBDATA_KEYWORDS__KBDATA_KEYWORDS:
				setKbdataKeywords((KbdataKeywordsType)null);
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
			case KbdataPackage.KBDATA_KEYWORDS__KBDATA_KEYWORDS:
				return kbdataKeywords != null;
		}
		return super.eIsSet(featureID);
	}

} //KbdataKeywordsImpl
