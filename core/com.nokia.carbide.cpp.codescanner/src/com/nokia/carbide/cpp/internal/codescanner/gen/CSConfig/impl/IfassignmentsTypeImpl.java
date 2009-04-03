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
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoryType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.IfassignmentsType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeverityType;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ifassignments Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.IfassignmentsTypeImpl#getCategory <em>Category</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.IfassignmentsTypeImpl#isEnable <em>Enable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.IfassignmentsTypeImpl#getSeverity <em>Severity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IfassignmentsTypeImpl extends EObjectImpl implements IfassignmentsType {
	/**
	 * The default value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected static final CategoryType CATEGORY_EDEFAULT = CategoryType.CANPANIC;

	/**
	 * The cached value of the '{@link #getCategory() <em>Category</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected CategoryType category = CATEGORY_EDEFAULT;

	/**
	 * This is true if the Category attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean categoryESet;

	/**
	 * The default value of the '{@link #isEnable() <em>Enable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnable() <em>Enable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnable()
	 * @generated
	 * @ordered
	 */
	protected boolean enable = ENABLE_EDEFAULT;

	/**
	 * This is true if the Enable attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean enableESet;

	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final SeverityType SEVERITY_EDEFAULT = SeverityType.HIGH;

	/**
	 * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected SeverityType severity = SEVERITY_EDEFAULT;

	/**
	 * This is true if the Severity attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean severityESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IfassignmentsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CSConfigPackage.eINSTANCE.getIfassignmentsType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoryType getCategory() {
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategory(CategoryType newCategory) {
		CategoryType oldCategory = category;
		category = newCategory == null ? CATEGORY_EDEFAULT : newCategory;
		boolean oldCategoryESet = categoryESet;
		categoryESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.IFASSIGNMENTS_TYPE__CATEGORY, oldCategory, category, !oldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCategory() {
		CategoryType oldCategory = category;
		boolean oldCategoryESet = categoryESet;
		category = CATEGORY_EDEFAULT;
		categoryESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CSConfigPackage.IFASSIGNMENTS_TYPE__CATEGORY, oldCategory, CATEGORY_EDEFAULT, oldCategoryESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCategory() {
		return categoryESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnable(boolean newEnable) {
		boolean oldEnable = enable;
		enable = newEnable;
		boolean oldEnableESet = enableESet;
		enableESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.IFASSIGNMENTS_TYPE__ENABLE, oldEnable, enable, !oldEnableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetEnable() {
		boolean oldEnable = enable;
		boolean oldEnableESet = enableESet;
		enable = ENABLE_EDEFAULT;
		enableESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CSConfigPackage.IFASSIGNMENTS_TYPE__ENABLE, oldEnable, ENABLE_EDEFAULT, oldEnableESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetEnable() {
		return enableESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeverityType getSeverity() {
		return severity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeverity(SeverityType newSeverity) {
		SeverityType oldSeverity = severity;
		severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
		boolean oldSeverityESet = severityESet;
		severityESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CSConfigPackage.IFASSIGNMENTS_TYPE__SEVERITY, oldSeverity, severity, !oldSeverityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSeverity() {
		SeverityType oldSeverity = severity;
		boolean oldSeverityESet = severityESet;
		severity = SEVERITY_EDEFAULT;
		severityESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CSConfigPackage.IFASSIGNMENTS_TYPE__SEVERITY, oldSeverity, SEVERITY_EDEFAULT, oldSeverityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSeverity() {
		return severityESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CSConfigPackage.IFASSIGNMENTS_TYPE__CATEGORY:
				return getCategory();
			case CSConfigPackage.IFASSIGNMENTS_TYPE__ENABLE:
				return isEnable() ? Boolean.TRUE : Boolean.FALSE;
			case CSConfigPackage.IFASSIGNMENTS_TYPE__SEVERITY:
				return getSeverity();
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
			case CSConfigPackage.IFASSIGNMENTS_TYPE__CATEGORY:
				setCategory((CategoryType)newValue);
				return;
			case CSConfigPackage.IFASSIGNMENTS_TYPE__ENABLE:
				setEnable(((Boolean)newValue).booleanValue());
				return;
			case CSConfigPackage.IFASSIGNMENTS_TYPE__SEVERITY:
				setSeverity((SeverityType)newValue);
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
			case CSConfigPackage.IFASSIGNMENTS_TYPE__CATEGORY:
				unsetCategory();
				return;
			case CSConfigPackage.IFASSIGNMENTS_TYPE__ENABLE:
				unsetEnable();
				return;
			case CSConfigPackage.IFASSIGNMENTS_TYPE__SEVERITY:
				unsetSeverity();
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
			case CSConfigPackage.IFASSIGNMENTS_TYPE__CATEGORY:
				return isSetCategory();
			case CSConfigPackage.IFASSIGNMENTS_TYPE__ENABLE:
				return isSetEnable();
			case CSConfigPackage.IFASSIGNMENTS_TYPE__SEVERITY:
				return isSetSeverity();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (category: ");
		if (categoryESet) result.append(category); else result.append("<unset>");
		result.append(", enable: ");
		if (enableESet) result.append(enable); else result.append("<unset>");
		result.append(", severity: ");
		if (severityESet) result.append(severity); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //IfassignmentsTypeImpl