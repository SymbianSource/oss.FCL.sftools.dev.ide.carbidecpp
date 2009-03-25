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

import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfComponentsType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage;

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
 * An implementation of the model object '<em><b>Inf Components Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.InfComponentsTypeImpl#getInfComponent <em>Inf Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InfComponentsTypeImpl extends EObjectImpl implements InfComponentsType {
	/**
	 * The cached value of the '{@link #getInfComponent() <em>Inf Component</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfComponent()
	 * @generated
	 * @ordered
	 */
	protected EList<InfComponentType> infComponent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InfComponentsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfImportTestDataPackage.Literals.INF_COMPONENTS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InfComponentType> getInfComponent() {
		if (infComponent == null) {
			infComponent = new EObjectContainmentEList<InfComponentType>(InfComponentType.class, this, InfImportTestDataPackage.INF_COMPONENTS_TYPE__INF_COMPONENT);
		}
		return infComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfImportTestDataPackage.INF_COMPONENTS_TYPE__INF_COMPONENT:
				return ((InternalEList<?>)getInfComponent()).basicRemove(otherEnd, msgs);
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
			case InfImportTestDataPackage.INF_COMPONENTS_TYPE__INF_COMPONENT:
				return getInfComponent();
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
			case InfImportTestDataPackage.INF_COMPONENTS_TYPE__INF_COMPONENT:
				getInfComponent().clear();
				getInfComponent().addAll((Collection<? extends InfComponentType>)newValue);
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
			case InfImportTestDataPackage.INF_COMPONENTS_TYPE__INF_COMPONENT:
				getInfComponent().clear();
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
			case InfImportTestDataPackage.INF_COMPONENTS_TYPE__INF_COMPONENT:
				return infComponent != null && !infComponent.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //InfComponentsTypeImpl
