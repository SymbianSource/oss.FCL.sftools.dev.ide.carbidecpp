/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IArrayMapping;
import com.nokia.sdt.emf.dm.IElementMapping;

import com.nokia.sdt.emf.dm.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IArray Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IArrayMappingImpl#getInstanceName <em>Instance Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IArrayMappingImpl#getPropertyId <em>Property Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IArrayMappingImpl#getMemberId <em>Member Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IArrayMappingImpl#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IArrayMappingImpl extends EObjectImpl implements IArrayMapping {
	/**
	 * The default value of the '{@link #getInstanceName() <em>Instance Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceName()
	 * @generated
	 * @ordered
	 */
	protected static final String INSTANCE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInstanceName() <em>Instance Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceName()
	 * @generated
	 * @ordered
	 */
	protected String instanceName = INSTANCE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropertyId() <em>Property Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyId()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyId() <em>Property Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyId()
	 * @generated
	 * @ordered
	 */
	protected String propertyId = PROPERTY_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getMemberId() <em>Member Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemberId()
	 * @generated
	 * @ordered
	 */
	protected static final String MEMBER_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMemberId() <em>Member Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMemberId()
	 * @generated
	 * @ordered
	 */
	protected String memberId = MEMBER_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList elements = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IArrayMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.IARRAY_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstanceName(String newInstanceName) {
		String oldInstanceName = instanceName;
		instanceName = newInstanceName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IARRAY_MAPPING__INSTANCE_NAME, oldInstanceName, instanceName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertyId() {
		return propertyId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyId(String newPropertyId) {
		String oldPropertyId = propertyId;
		propertyId = newPropertyId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IARRAY_MAPPING__PROPERTY_ID, oldPropertyId, propertyId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemberId(String newMemberId) {
		String oldMemberId = memberId;
		memberId = newMemberId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IARRAY_MAPPING__MEMBER_ID, oldMemberId, memberId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList(IElementMapping.class, this, DmPackage.IARRAY_MAPPING__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DmPackage.IARRAY_MAPPING__ELEMENTS:
				return ((InternalEList)getElements()).basicRemove(otherEnd, msgs);
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
			case DmPackage.IARRAY_MAPPING__INSTANCE_NAME:
				return getInstanceName();
			case DmPackage.IARRAY_MAPPING__PROPERTY_ID:
				return getPropertyId();
			case DmPackage.IARRAY_MAPPING__MEMBER_ID:
				return getMemberId();
			case DmPackage.IARRAY_MAPPING__ELEMENTS:
				return getElements();
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
			case DmPackage.IARRAY_MAPPING__INSTANCE_NAME:
				setInstanceName((String)newValue);
				return;
			case DmPackage.IARRAY_MAPPING__PROPERTY_ID:
				setPropertyId((String)newValue);
				return;
			case DmPackage.IARRAY_MAPPING__MEMBER_ID:
				setMemberId((String)newValue);
				return;
			case DmPackage.IARRAY_MAPPING__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection)newValue);
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
			case DmPackage.IARRAY_MAPPING__INSTANCE_NAME:
				setInstanceName(INSTANCE_NAME_EDEFAULT);
				return;
			case DmPackage.IARRAY_MAPPING__PROPERTY_ID:
				setPropertyId(PROPERTY_ID_EDEFAULT);
				return;
			case DmPackage.IARRAY_MAPPING__MEMBER_ID:
				setMemberId(MEMBER_ID_EDEFAULT);
				return;
			case DmPackage.IARRAY_MAPPING__ELEMENTS:
				getElements().clear();
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
			case DmPackage.IARRAY_MAPPING__INSTANCE_NAME:
				return INSTANCE_NAME_EDEFAULT == null ? instanceName != null : !INSTANCE_NAME_EDEFAULT.equals(instanceName);
			case DmPackage.IARRAY_MAPPING__PROPERTY_ID:
				return PROPERTY_ID_EDEFAULT == null ? propertyId != null : !PROPERTY_ID_EDEFAULT.equals(propertyId);
			case DmPackage.IARRAY_MAPPING__MEMBER_ID:
				return MEMBER_ID_EDEFAULT == null ? memberId != null : !MEMBER_ID_EDEFAULT.equals(memberId);
			case DmPackage.IARRAY_MAPPING__ELEMENTS:
				return elements != null && !elements.isEmpty();
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
		result.append(" (instanceName: ");
		result.append(instanceName);
		result.append(", propertyId: ");
		result.append(propertyId);
		result.append(", memberId: ");
		result.append(memberId);
		result.append(')');
		return result.toString();
	}

} //IArrayMappingImpl
