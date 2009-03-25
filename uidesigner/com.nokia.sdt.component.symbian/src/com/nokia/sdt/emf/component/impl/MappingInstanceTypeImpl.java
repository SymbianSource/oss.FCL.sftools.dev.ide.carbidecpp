/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.MappingInstanceType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping Instance Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingInstanceTypeImpl#getRsrcId <em>Rsrc Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MappingInstanceTypeImpl extends TwoWayMappingTypeImpl implements MappingInstanceType {
	/**
	 * The default value of the '{@link #getRsrcId() <em>Rsrc Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getRsrcId()
	 * @generated
	 * @ordered
	 */
    protected static final String RSRC_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRsrcId() <em>Rsrc Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getRsrcId()
	 * @generated
	 * @ordered
	 */
    protected String rsrcId = RSRC_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected MappingInstanceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.MAPPING_INSTANCE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getRsrcId() {
		return rsrcId;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setRsrcId(String newRsrcId) {
		String oldRsrcId = rsrcId;
		rsrcId = newRsrcId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_INSTANCE_TYPE__RSRC_ID, oldRsrcId, rsrcId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.MAPPING_INSTANCE_TYPE__RSRC_ID:
				return getRsrcId();
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
			case ComponentPackage.MAPPING_INSTANCE_TYPE__RSRC_ID:
				setRsrcId((String)newValue);
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
			case ComponentPackage.MAPPING_INSTANCE_TYPE__RSRC_ID:
				setRsrcId(RSRC_ID_EDEFAULT);
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
			case ComponentPackage.MAPPING_INSTANCE_TYPE__RSRC_ID:
				return RSRC_ID_EDEFAULT == null ? rsrcId != null : !RSRC_ID_EDEFAULT.equals(rsrcId);
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
		result.append(" (rsrcId: ");
		result.append(rsrcId);
		result.append(')');
		return result.toString();
	}

} //MappingInstanceTypeImpl
