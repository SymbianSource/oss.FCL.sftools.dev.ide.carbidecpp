/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IEnumMapping;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IEnum Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IEnumMappingImpl#getInstanceName <em>Instance Name</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IEnumMappingImpl#getPropertyId <em>Property Id</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IEnumMappingImpl#getNameAlgorithm <em>Name Algorithm</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.IEnumMappingImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IEnumMappingImpl extends EObjectImpl implements IEnumMapping {
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
	 * The default value of the '{@link #getNameAlgorithm() <em>Name Algorithm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameAlgorithm()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_ALGORITHM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNameAlgorithm() <em>Name Algorithm</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameAlgorithm()
	 * @generated
	 * @ordered
	 */
	protected String nameAlgorithm = NAME_ALGORITHM_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IEnumMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.IENUM_MAPPING;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IENUM_MAPPING__INSTANCE_NAME, oldInstanceName, instanceName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IENUM_MAPPING__PROPERTY_ID, oldPropertyId, propertyId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNameAlgorithm() {
		return nameAlgorithm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameAlgorithm(String newNameAlgorithm) {
		String oldNameAlgorithm = nameAlgorithm;
		nameAlgorithm = newNameAlgorithm;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IENUM_MAPPING__NAME_ALGORITHM, oldNameAlgorithm, nameAlgorithm));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.IENUM_MAPPING__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DmPackage.IENUM_MAPPING__INSTANCE_NAME:
				return getInstanceName();
			case DmPackage.IENUM_MAPPING__PROPERTY_ID:
				return getPropertyId();
			case DmPackage.IENUM_MAPPING__NAME_ALGORITHM:
				return getNameAlgorithm();
			case DmPackage.IENUM_MAPPING__VALUE:
				return getValue();
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
			case DmPackage.IENUM_MAPPING__INSTANCE_NAME:
				setInstanceName((String)newValue);
				return;
			case DmPackage.IENUM_MAPPING__PROPERTY_ID:
				setPropertyId((String)newValue);
				return;
			case DmPackage.IENUM_MAPPING__NAME_ALGORITHM:
				setNameAlgorithm((String)newValue);
				return;
			case DmPackage.IENUM_MAPPING__VALUE:
				setValue((String)newValue);
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
			case DmPackage.IENUM_MAPPING__INSTANCE_NAME:
				setInstanceName(INSTANCE_NAME_EDEFAULT);
				return;
			case DmPackage.IENUM_MAPPING__PROPERTY_ID:
				setPropertyId(PROPERTY_ID_EDEFAULT);
				return;
			case DmPackage.IENUM_MAPPING__NAME_ALGORITHM:
				setNameAlgorithm(NAME_ALGORITHM_EDEFAULT);
				return;
			case DmPackage.IENUM_MAPPING__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case DmPackage.IENUM_MAPPING__INSTANCE_NAME:
				return INSTANCE_NAME_EDEFAULT == null ? instanceName != null : !INSTANCE_NAME_EDEFAULT.equals(instanceName);
			case DmPackage.IENUM_MAPPING__PROPERTY_ID:
				return PROPERTY_ID_EDEFAULT == null ? propertyId != null : !PROPERTY_ID_EDEFAULT.equals(propertyId);
			case DmPackage.IENUM_MAPPING__NAME_ALGORITHM:
				return NAME_ALGORITHM_EDEFAULT == null ? nameAlgorithm != null : !NAME_ALGORITHM_EDEFAULT.equals(nameAlgorithm);
			case DmPackage.IENUM_MAPPING__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
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
		result.append(", nameAlgorithm: ");
		result.append(nameAlgorithm);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //IEnumMappingImpl
