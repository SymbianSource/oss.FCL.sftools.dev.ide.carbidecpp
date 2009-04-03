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

import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.InfImportTestDataPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bld Inf Import Data Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.impl.BldInfImportDataTypeImpl#getBldInfFiles <em>Bld Inf Files</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BldInfImportDataTypeImpl extends EObjectImpl implements BldInfImportDataType {
	/**
	 * The cached value of the '{@link #getBldInfFiles() <em>Bld Inf Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBldInfFiles()
	 * @generated
	 * @ordered
	 */
	protected BldInfFilesType bldInfFiles;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BldInfImportDataTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfImportTestDataPackage.Literals.BLD_INF_IMPORT_DATA_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BldInfFilesType getBldInfFiles() {
		return bldInfFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBldInfFiles(BldInfFilesType newBldInfFiles, NotificationChain msgs) {
		BldInfFilesType oldBldInfFiles = bldInfFiles;
		bldInfFiles = newBldInfFiles;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES, oldBldInfFiles, newBldInfFiles);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBldInfFiles(BldInfFilesType newBldInfFiles) {
		if (newBldInfFiles != bldInfFiles) {
			NotificationChain msgs = null;
			if (bldInfFiles != null)
				msgs = ((InternalEObject)bldInfFiles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES, null, msgs);
			if (newBldInfFiles != null)
				msgs = ((InternalEObject)newBldInfFiles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES, null, msgs);
			msgs = basicSetBldInfFiles(newBldInfFiles, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES, newBldInfFiles, newBldInfFiles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES:
				return basicSetBldInfFiles(null, msgs);
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
			case InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES:
				return getBldInfFiles();
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
			case InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES:
				setBldInfFiles((BldInfFilesType)newValue);
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
			case InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES:
				setBldInfFiles((BldInfFilesType)null);
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
			case InfImportTestDataPackage.BLD_INF_IMPORT_DATA_TYPE__BLD_INF_FILES:
				return bldInfFiles != null;
		}
		return super.eIsSet(featureID);
	}

} //BldInfImportDataTypeImpl
