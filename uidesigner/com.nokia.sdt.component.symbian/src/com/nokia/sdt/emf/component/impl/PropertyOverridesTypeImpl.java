/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.PropertyOverrideType;
import com.nokia.sdt.emf.component.PropertyOverridesType;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Overrides Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.PropertyOverridesTypeImpl#getPropertyOverride <em>Property Override</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyOverridesTypeImpl extends EObjectImpl implements PropertyOverridesType {
	/**
	 * The cached value of the '{@link #getPropertyOverride() <em>Property Override</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyOverride()
	 * @generated
	 * @ordered
	 */
	protected EList propertyOverride;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyOverridesTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.PROPERTY_OVERRIDES_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getPropertyOverride() {
		if (propertyOverride == null) {
			propertyOverride = new EObjectContainmentEList(PropertyOverrideType.class, this, ComponentPackage.PROPERTY_OVERRIDES_TYPE__PROPERTY_OVERRIDE);
		}
		return propertyOverride;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.PROPERTY_OVERRIDES_TYPE__PROPERTY_OVERRIDE:
				return ((InternalEList)getPropertyOverride()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.PROPERTY_OVERRIDES_TYPE__PROPERTY_OVERRIDE:
				return getPropertyOverride();
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
			case ComponentPackage.PROPERTY_OVERRIDES_TYPE__PROPERTY_OVERRIDE:
				getPropertyOverride().clear();
				getPropertyOverride().addAll((Collection)newValue);
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
			case ComponentPackage.PROPERTY_OVERRIDES_TYPE__PROPERTY_OVERRIDE:
				getPropertyOverride().clear();
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
			case ComponentPackage.PROPERTY_OVERRIDES_TYPE__PROPERTY_OVERRIDE:
				return propertyOverride != null && !propertyOverride.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PropertyOverridesTypeImpl
