/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.SourceMappingType;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source Mapping Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceMappingTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceMappingTypeImpl#getMapResource <em>Map Resource</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceMappingTypeImpl#getSelect <em>Select</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SourceMappingTypeImpl extends EObjectImpl implements SourceMappingType {
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
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected SourceMappingTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.SOURCE_MAPPING_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.SOURCE_MAPPING_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapResource() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_MAPPING_TYPE__MAP_RESOURCE);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getSelect() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_MAPPING_TYPE__SELECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.SOURCE_MAPPING_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_MAPPING_TYPE__MAP_RESOURCE:
				return ((InternalEList)getMapResource()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_MAPPING_TYPE__SELECT:
				return ((InternalEList)getSelect()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.SOURCE_MAPPING_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.SOURCE_MAPPING_TYPE__MAP_RESOURCE:
				return getMapResource();
			case ComponentPackage.SOURCE_MAPPING_TYPE__SELECT:
				return getSelect();
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
			case ComponentPackage.SOURCE_MAPPING_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.SOURCE_MAPPING_TYPE__MAP_RESOURCE:
				getMapResource().clear();
				getMapResource().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_MAPPING_TYPE__SELECT:
				getSelect().clear();
				getSelect().addAll((Collection)newValue);
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
			case ComponentPackage.SOURCE_MAPPING_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.SOURCE_MAPPING_TYPE__MAP_RESOURCE:
				getMapResource().clear();
				return;
			case ComponentPackage.SOURCE_MAPPING_TYPE__SELECT:
				getSelect().clear();
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
			case ComponentPackage.SOURCE_MAPPING_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.SOURCE_MAPPING_TYPE__MAP_RESOURCE:
				return !getMapResource().isEmpty();
			case ComponentPackage.SOURCE_MAPPING_TYPE__SELECT:
				return !getSelect().isEmpty();
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
		result.append(')');
		return result.toString();
	}

} //SourceMappingTypeImpl
