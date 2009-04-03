/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.MapResourceElementType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Resource Element Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapResourceElementTypeImpl#getInstanceIdentifyingMember <em>Instance Identifying Member</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MapResourceElementTypeImpl extends MappingResourceTypeImpl implements MapResourceElementType {
	/**
	 * The default value of the '{@link #getInstanceIdentifyingMember() <em>Instance Identifying Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceIdentifyingMember()
	 * @generated
	 * @ordered
	 */
	protected static final String INSTANCE_IDENTIFYING_MEMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInstanceIdentifyingMember() <em>Instance Identifying Member</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceIdentifyingMember()
	 * @generated
	 * @ordered
	 */
	protected String instanceIdentifyingMember = INSTANCE_IDENTIFYING_MEMBER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected MapResourceElementTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.MAP_RESOURCE_ELEMENT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInstanceIdentifyingMember() {
		return instanceIdentifyingMember;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstanceIdentifyingMember(String newInstanceIdentifyingMember) {
		String oldInstanceIdentifyingMember = instanceIdentifyingMember;
		instanceIdentifyingMember = newInstanceIdentifyingMember;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAP_RESOURCE_ELEMENT_TYPE__INSTANCE_IDENTIFYING_MEMBER, oldInstanceIdentifyingMember, instanceIdentifyingMember));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.MAP_RESOURCE_ELEMENT_TYPE__INSTANCE_IDENTIFYING_MEMBER:
				return getInstanceIdentifyingMember();
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
			case ComponentPackage.MAP_RESOURCE_ELEMENT_TYPE__INSTANCE_IDENTIFYING_MEMBER:
				setInstanceIdentifyingMember((String)newValue);
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
			case ComponentPackage.MAP_RESOURCE_ELEMENT_TYPE__INSTANCE_IDENTIFYING_MEMBER:
				setInstanceIdentifyingMember(INSTANCE_IDENTIFYING_MEMBER_EDEFAULT);
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
			case ComponentPackage.MAP_RESOURCE_ELEMENT_TYPE__INSTANCE_IDENTIFYING_MEMBER:
				return INSTANCE_IDENTIFYING_MEMBER_EDEFAULT == null ? instanceIdentifyingMember != null : !INSTANCE_IDENTIFYING_MEMBER_EDEFAULT.equals(instanceIdentifyingMember);
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
		result.append(" (instanceIdentifyingMember: ");
		result.append(instanceIdentifyingMember);
		result.append(')');
		return result.toString();
	}

} //MapResourceElementTypeImpl
