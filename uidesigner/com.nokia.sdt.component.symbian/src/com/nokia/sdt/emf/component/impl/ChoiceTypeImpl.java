/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ChoiceType;
import com.nokia.sdt.emf.component.ComponentPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Choice Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ChoiceTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ChoiceTypeImpl#getTwoWayMappingGroup <em>Two Way Mapping Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ChoiceTypeImpl#getTwoWayMapping <em>Two Way Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ChoiceTypeImpl#getMapResource <em>Map Resource</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ChoiceTypeImpl#getSelect <em>Select</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ChoiceTypeImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChoiceTypeImpl extends EObjectImpl implements ChoiceType {
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
    protected ChoiceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.CHOICE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, ComponentPackage.CHOICE_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated NOT
	 */
    public FeatureMap getTwoWayMappingGroup() {
		return (FeatureMap)(EList)((FeatureMap)getGroup()).list(ComponentPackage.eINSTANCE.getChoiceType_TwoWayMappingGroup());
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getTwoWayMapping() {
		return getTwoWayMappingGroup().list(ComponentPackage.Literals.CHOICE_TYPE__TWO_WAY_MAPPING);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getMapResource() {
		return getGroup().list(ComponentPackage.Literals.CHOICE_TYPE__MAP_RESOURCE);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getSelect() {
		return getGroup().list(ComponentPackage.Literals.CHOICE_TYPE__SELECT);
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.CHOICE_TYPE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.CHOICE_TYPE__GROUP:
				return ((InternalEList)getGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.CHOICE_TYPE__TWO_WAY_MAPPING_GROUP:
				return ((InternalEList)getTwoWayMappingGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.CHOICE_TYPE__TWO_WAY_MAPPING:
				return ((InternalEList)getTwoWayMapping()).basicRemove(otherEnd, msgs);
			case ComponentPackage.CHOICE_TYPE__MAP_RESOURCE:
				return ((InternalEList)getMapResource()).basicRemove(otherEnd, msgs);
			case ComponentPackage.CHOICE_TYPE__SELECT:
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
			case ComponentPackage.CHOICE_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case ComponentPackage.CHOICE_TYPE__TWO_WAY_MAPPING_GROUP:
				if (coreType) return getTwoWayMappingGroup();
				return ((FeatureMap.Internal)getTwoWayMappingGroup()).getWrapper();
			case ComponentPackage.CHOICE_TYPE__TWO_WAY_MAPPING:
				return getTwoWayMapping();
			case ComponentPackage.CHOICE_TYPE__MAP_RESOURCE:
				return getMapResource();
			case ComponentPackage.CHOICE_TYPE__SELECT:
				return getSelect();
			case ComponentPackage.CHOICE_TYPE__VALUE:
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
			case ComponentPackage.CHOICE_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case ComponentPackage.CHOICE_TYPE__TWO_WAY_MAPPING_GROUP:
				((FeatureMap.Internal)getTwoWayMappingGroup()).set(newValue);
				return;
			case ComponentPackage.CHOICE_TYPE__MAP_RESOURCE:
				getMapResource().clear();
				getMapResource().addAll((Collection)newValue);
				return;
			case ComponentPackage.CHOICE_TYPE__SELECT:
				getSelect().clear();
				getSelect().addAll((Collection)newValue);
				return;
			case ComponentPackage.CHOICE_TYPE__VALUE:
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
			case ComponentPackage.CHOICE_TYPE__GROUP:
				getGroup().clear();
				return;
			case ComponentPackage.CHOICE_TYPE__TWO_WAY_MAPPING_GROUP:
				getTwoWayMappingGroup().clear();
				return;
			case ComponentPackage.CHOICE_TYPE__MAP_RESOURCE:
				getMapResource().clear();
				return;
			case ComponentPackage.CHOICE_TYPE__SELECT:
				getSelect().clear();
				return;
			case ComponentPackage.CHOICE_TYPE__VALUE:
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
			case ComponentPackage.CHOICE_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case ComponentPackage.CHOICE_TYPE__TWO_WAY_MAPPING_GROUP:
				return !getTwoWayMappingGroup().isEmpty();
			case ComponentPackage.CHOICE_TYPE__TWO_WAY_MAPPING:
				return !getTwoWayMapping().isEmpty();
			case ComponentPackage.CHOICE_TYPE__MAP_RESOURCE:
				return !getMapResource().isEmpty();
			case ComponentPackage.CHOICE_TYPE__SELECT:
				return !getSelect().isEmpty();
			case ComponentPackage.CHOICE_TYPE__VALUE:
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
		result.append(" (group: ");
		result.append(group);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //ChoiceTypeImpl
