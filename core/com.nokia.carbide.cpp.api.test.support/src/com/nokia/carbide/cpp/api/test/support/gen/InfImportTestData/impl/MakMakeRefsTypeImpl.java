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
package com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl;

import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.MakMakeRefsType;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mak Make Refs Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.MakMakeRefsTypeImpl#getMakMakeRef <em>Mak Make Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MakMakeRefsTypeImpl extends EObjectImpl implements MakMakeRefsType {
	/**
	 * The cached value of the '{@link #getMakMakeRef() <em>Mak Make Ref</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMakMakeRef()
	 * @generated
	 * @ordered
	 */
	protected EList<MakMakeRefType> makMakeRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MakMakeRefsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfImportTestDataPackage.Literals.MAK_MAKE_REFS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MakMakeRefType> getMakMakeRef() {
		if (makMakeRef == null) {
			makMakeRef = new EObjectContainmentEList<MakMakeRefType>(MakMakeRefType.class, this, InfImportTestDataPackage.MAK_MAKE_REFS_TYPE__MAK_MAKE_REF);
		}
		return makMakeRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfImportTestDataPackage.MAK_MAKE_REFS_TYPE__MAK_MAKE_REF:
				return ((InternalEList<?>)getMakMakeRef()).basicRemove(otherEnd, msgs);
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
			case InfImportTestDataPackage.MAK_MAKE_REFS_TYPE__MAK_MAKE_REF:
				return getMakMakeRef();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InfImportTestDataPackage.MAK_MAKE_REFS_TYPE__MAK_MAKE_REF:
				getMakMakeRef().clear();
				getMakMakeRef().addAll((Collection<? extends MakMakeRefType>)newValue);
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
			case InfImportTestDataPackage.MAK_MAKE_REFS_TYPE__MAK_MAKE_REF:
				getMakMakeRef().clear();
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
			case InfImportTestDataPackage.MAK_MAKE_REFS_TYPE__MAK_MAKE_REF:
				return makMakeRef != null && !makMakeRef.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MakMakeRefsTypeImpl
