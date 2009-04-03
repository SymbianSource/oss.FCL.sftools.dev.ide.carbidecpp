/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.PropertiesType;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Properties Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.PropertiesTypeImpl#getAbstractPropertyGroup <em>Abstract Property Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.PropertiesTypeImpl#getAbstractProperty <em>Abstract Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertiesTypeImpl extends EObjectImpl implements PropertiesType {
	/**
	 * The cached value of the '{@link #getAbstractPropertyGroup() <em>Abstract Property Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAbstractPropertyGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap abstractPropertyGroup;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertiesTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.PROPERTIES_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAbstractPropertyGroup() {
		if (abstractPropertyGroup == null) {
			abstractPropertyGroup = new BasicFeatureMap(this, ComponentPackage.PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP);
		}
		return abstractPropertyGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAbstractProperty() {
		return getAbstractPropertyGroup().list(ComponentPackage.Literals.PROPERTIES_TYPE__ABSTRACT_PROPERTY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP:
				return ((InternalEList)getAbstractPropertyGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.PROPERTIES_TYPE__ABSTRACT_PROPERTY:
				return ((InternalEList)getAbstractProperty()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP:
				if (coreType) return getAbstractPropertyGroup();
				return ((FeatureMap.Internal)getAbstractPropertyGroup()).getWrapper();
			case ComponentPackage.PROPERTIES_TYPE__ABSTRACT_PROPERTY:
				return getAbstractProperty();
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
			case ComponentPackage.PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP:
				((FeatureMap.Internal)getAbstractPropertyGroup()).set(newValue);
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
			case ComponentPackage.PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP:
				getAbstractPropertyGroup().clear();
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
			case ComponentPackage.PROPERTIES_TYPE__ABSTRACT_PROPERTY_GROUP:
				return abstractPropertyGroup != null && !abstractPropertyGroup.isEmpty();
			case ComponentPackage.PROPERTIES_TYPE__ABSTRACT_PROPERTY:
				return !getAbstractProperty().isEmpty();
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
		result.append(" (abstractPropertyGroup: ");
		result.append(abstractPropertyGroup);
		result.append(')');
		return result.toString();
	}

} //PropertiesTypeImpl
