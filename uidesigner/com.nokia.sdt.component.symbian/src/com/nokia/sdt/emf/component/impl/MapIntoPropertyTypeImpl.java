/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.MapIntoPropertyType;

import com.nokia.sdt.emf.component.TwoWayMappingType;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Into Property Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapIntoPropertyTypeImpl#getTwoWayMappingGroup <em>Two Way Mapping Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapIntoPropertyTypeImpl#getTwoWayMapping <em>Two Way Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MapIntoPropertyTypeImpl#getProperty <em>Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MapIntoPropertyTypeImpl extends TwoWayMappingTypeImpl implements MapIntoPropertyType {
	/**
	 * The cached value of the '{@link #getTwoWayMappingGroup() <em>Two Way Mapping Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTwoWayMappingGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap twoWayMappingGroup;

	/**
	 * The default value of the '{@link #getProperty() <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected String property = PROPERTY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MapIntoPropertyTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.MAP_INTO_PROPERTY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getTwoWayMappingGroup() {
		if (twoWayMappingGroup == null) {
			twoWayMappingGroup = new BasicFeatureMap(this, ComponentPackage.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING_GROUP);
		}
		return twoWayMappingGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TwoWayMappingType getTwoWayMapping() {
		return (TwoWayMappingType)getTwoWayMappingGroup().get(ComponentPackage.Literals.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTwoWayMapping(TwoWayMappingType newTwoWayMapping, NotificationChain msgs) {
		return ((FeatureMap.Internal)getTwoWayMappingGroup()).basicAdd(ComponentPackage.Literals.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING, newTwoWayMapping, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperty(String newProperty) {
		String oldProperty = property;
		property = newProperty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAP_INTO_PROPERTY_TYPE__PROPERTY, oldProperty, property));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING_GROUP:
				return ((InternalEList)getTwoWayMappingGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING:
				return basicSetTwoWayMapping(null, msgs);
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
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING_GROUP:
				if (coreType) return getTwoWayMappingGroup();
				return ((FeatureMap.Internal)getTwoWayMappingGroup()).getWrapper();
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING:
				return getTwoWayMapping();
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__PROPERTY:
				return getProperty();
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
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING_GROUP:
				((FeatureMap.Internal)getTwoWayMappingGroup()).set(newValue);
				return;
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__PROPERTY:
				setProperty((String)newValue);
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
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING_GROUP:
				getTwoWayMappingGroup().clear();
				return;
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__PROPERTY:
				setProperty(PROPERTY_EDEFAULT);
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
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING_GROUP:
				return twoWayMappingGroup != null && !twoWayMappingGroup.isEmpty();
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__TWO_WAY_MAPPING:
				return getTwoWayMapping() != null;
			case ComponentPackage.MAP_INTO_PROPERTY_TYPE__PROPERTY:
				return PROPERTY_EDEFAULT == null ? property != null : !PROPERTY_EDEFAULT.equals(property);
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
		result.append(" (twoWayMappingGroup: ");
		result.append(twoWayMappingGroup);
		result.append(", property: ");
		result.append(property);
		result.append(')');
		return result.toString();
	}

} //MapIntoPropertyTypeImpl
