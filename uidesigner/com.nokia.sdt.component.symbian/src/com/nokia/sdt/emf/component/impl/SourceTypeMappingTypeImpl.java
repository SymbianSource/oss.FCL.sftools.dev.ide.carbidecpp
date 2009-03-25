/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.SourceTypeMappingType;

import java.util.Collection;

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
 * An implementation of the model object '<em><b>Source Type Mapping Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getMapResourceType <em>Map Resource Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getMapEnumType <em>Map Enum Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getMapSimpleType <em>Map Simple Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getMapFixedType <em>Map Fixed Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getMapBitmaskType <em>Map Bitmask Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getMapIdentifierType <em>Map Identifier Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getMapReferenceType <em>Map Reference Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getMapArrayType <em>Map Array Type</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SourceTypeMappingTypeImpl#getSelect <em>Select</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SourceTypeMappingTypeImpl extends EObjectImpl implements SourceTypeMappingType {
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
	protected SourceTypeMappingTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapResourceType() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE__MAP_RESOURCE_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapEnumType() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE__MAP_ENUM_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapSimpleType() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE__MAP_SIMPLE_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapFixedType() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE__MAP_FIXED_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapBitmaskType() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE__MAP_BITMASK_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapIdentifierType() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE__MAP_IDENTIFIER_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapReferenceType() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE__MAP_REFERENCE_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getMapArrayType() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE__MAP_ARRAY_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getSelect() {
		return getGroup().list(ComponentPackage.Literals.SOURCE_TYPE_MAPPING_TYPE__SELECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_RESOURCE_TYPE:
				return ((InternalEList)getMapResourceType()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ENUM_TYPE:
				return ((InternalEList)getMapEnumType()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_SIMPLE_TYPE:
				return ((InternalEList)getMapSimpleType()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_FIXED_TYPE:
				return ((InternalEList)getMapFixedType()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_BITMASK_TYPE:
				return ((InternalEList)getMapBitmaskType()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_IDENTIFIER_TYPE:
				return ((InternalEList)getMapIdentifierType()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_REFERENCE_TYPE:
				return ((InternalEList)getMapReferenceType()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ARRAY_TYPE:
				return ((InternalEList)getMapArrayType()).basicRemove(otherEnd, msgs);
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__SELECT:
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
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_RESOURCE_TYPE:
				return getMapResourceType();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ENUM_TYPE:
				return getMapEnumType();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_SIMPLE_TYPE:
				return getMapSimpleType();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_FIXED_TYPE:
				return getMapFixedType();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_BITMASK_TYPE:
				return getMapBitmaskType();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_IDENTIFIER_TYPE:
				return getMapIdentifierType();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_REFERENCE_TYPE:
				return getMapReferenceType();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ARRAY_TYPE:
				return getMapArrayType();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__SELECT:
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
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_RESOURCE_TYPE:
				getMapResourceType().clear();
				getMapResourceType().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ENUM_TYPE:
				getMapEnumType().clear();
				getMapEnumType().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_SIMPLE_TYPE:
				getMapSimpleType().clear();
				getMapSimpleType().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_FIXED_TYPE:
				getMapFixedType().clear();
				getMapFixedType().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_BITMASK_TYPE:
				getMapBitmaskType().clear();
				getMapBitmaskType().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_IDENTIFIER_TYPE:
				getMapIdentifierType().clear();
				getMapIdentifierType().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_REFERENCE_TYPE:
				getMapReferenceType().clear();
				getMapReferenceType().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ARRAY_TYPE:
				getMapArrayType().clear();
				getMapArrayType().addAll((Collection)newValue);
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__SELECT:
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
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_RESOURCE_TYPE:
				getMapResourceType().clear();
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ENUM_TYPE:
				getMapEnumType().clear();
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_SIMPLE_TYPE:
				getMapSimpleType().clear();
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_FIXED_TYPE:
				getMapFixedType().clear();
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_BITMASK_TYPE:
				getMapBitmaskType().clear();
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_IDENTIFIER_TYPE:
				getMapIdentifierType().clear();
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_REFERENCE_TYPE:
				getMapReferenceType().clear();
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ARRAY_TYPE:
				getMapArrayType().clear();
				return;
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__SELECT:
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
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_RESOURCE_TYPE:
				return !getMapResourceType().isEmpty();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ENUM_TYPE:
				return !getMapEnumType().isEmpty();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_SIMPLE_TYPE:
				return !getMapSimpleType().isEmpty();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_FIXED_TYPE:
				return !getMapFixedType().isEmpty();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_BITMASK_TYPE:
				return !getMapBitmaskType().isEmpty();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_IDENTIFIER_TYPE:
				return !getMapIdentifierType().isEmpty();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_REFERENCE_TYPE:
				return !getMapReferenceType().isEmpty();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__MAP_ARRAY_TYPE:
				return !getMapArrayType().isEmpty();
			case ComponentPackage.SOURCE_TYPE_MAPPING_TYPE__SELECT:
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

} //SourceTypeMappingTypeImpl
