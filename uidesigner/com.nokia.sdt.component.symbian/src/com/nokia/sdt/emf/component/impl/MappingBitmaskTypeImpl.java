/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.MappingBitmaskType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping Bitmask Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingBitmaskTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingBitmaskTypeImpl#getMapBitmaskValue <em>Map Bitmask Value</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingBitmaskTypeImpl#getIncludedProperties <em>Included Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MappingBitmaskTypeImpl extends TwoWayMappingTypeImpl implements MappingBitmaskType {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
    protected FeatureMap group;

	/**
	 * The default value of the '{@link #getIncludedProperties() <em>Included Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getIncludedProperties()
	 * @generated
	 * @ordered
	 */
    protected static final List INCLUDED_PROPERTIES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIncludedProperties() <em>Included Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getIncludedProperties()
	 * @generated
	 * @ordered
	 */
    protected List includedProperties = INCLUDED_PROPERTIES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected MappingBitmaskTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.MAPPING_BITMASK_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.MAPPING_BITMASK_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapBitmaskValue() {
		return getGroup().list(ComponentPackage.Literals.MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public List getIncludedProperties() {
		return includedProperties;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setIncludedProperties(List newIncludedProperties) {
		List oldIncludedProperties = includedProperties;
		includedProperties = newIncludedProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES, oldIncludedProperties, includedProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.MAPPING_BITMASK_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE:
				return ((InternalEList)getMapBitmaskValue()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.MAPPING_BITMASK_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE:
				return getMapBitmaskValue();
			case ComponentPackage.MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES:
				return getIncludedProperties();
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
			case ComponentPackage.MAPPING_BITMASK_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE:
				getMapBitmaskValue().clear();
				getMapBitmaskValue().addAll((Collection)newValue);
				return;
			case ComponentPackage.MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES:
				setIncludedProperties((List)newValue);
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
			case ComponentPackage.MAPPING_BITMASK_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE:
				getMapBitmaskValue().clear();
				return;
			case ComponentPackage.MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES:
				setIncludedProperties(INCLUDED_PROPERTIES_EDEFAULT);
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
			case ComponentPackage.MAPPING_BITMASK_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.MAPPING_BITMASK_TYPE__MAP_BITMASK_VALUE:
				return !getMapBitmaskValue().isEmpty();
			case ComponentPackage.MAPPING_BITMASK_TYPE__INCLUDED_PROPERTIES:
				return INCLUDED_PROPERTIES_EDEFAULT == null ? includedProperties != null : !INCLUDED_PROPERTIES_EDEFAULT.equals(includedProperties);
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
		result.append(" (group: ");
		result.append(group);
		result.append(", includedProperties: ");
		result.append(includedProperties);
		result.append(')');
		return result.toString();
	}

} //MappingBitmaskTypeImpl
