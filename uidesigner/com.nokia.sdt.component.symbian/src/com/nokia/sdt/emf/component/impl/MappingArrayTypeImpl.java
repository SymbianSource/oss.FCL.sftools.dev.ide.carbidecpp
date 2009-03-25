/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.MappingArrayType;
import com.nokia.sdt.emf.component.SelectType;
import com.nokia.sdt.emf.component.TwoWayMappingType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping Array Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingArrayTypeImpl#getTwoWayMappingGroup <em>Two Way Mapping Group</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingArrayTypeImpl#getTwoWayMapping <em>Two Way Mapping</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.MappingArrayTypeImpl#getSelect <em>Select</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MappingArrayTypeImpl extends TwoWayMappingTypeImpl implements MappingArrayType {
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
	 * The cached value of the '{@link #getSelect() <em>Select</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSelect()
	 * @generated
	 * @ordered
	 */
    protected SelectType select;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected MappingArrayTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.MAPPING_ARRAY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public FeatureMap getTwoWayMappingGroup() {
		if (twoWayMappingGroup == null) {
			twoWayMappingGroup = new BasicFeatureMap(this, ComponentPackage.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP);
		}
		return twoWayMappingGroup;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public TwoWayMappingType getTwoWayMapping() {
		return (TwoWayMappingType)getTwoWayMappingGroup().get(ComponentPackage.Literals.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING, true);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetTwoWayMapping(TwoWayMappingType newTwoWayMapping, NotificationChain msgs) {
		return ((FeatureMap.Internal)getTwoWayMappingGroup()).basicAdd(ComponentPackage.Literals.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING, newTwoWayMapping, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public SelectType getSelect() {
		return select;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetSelect(SelectType newSelect, NotificationChain msgs) {
		SelectType oldSelect = select;
		select = newSelect;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_ARRAY_TYPE__SELECT, oldSelect, newSelect);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSelect(SelectType newSelect) {
		if (newSelect != select) {
			NotificationChain msgs = null;
			if (select != null)
				msgs = ((InternalEObject)select).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.MAPPING_ARRAY_TYPE__SELECT, null, msgs);
			if (newSelect != null)
				msgs = ((InternalEObject)newSelect).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.MAPPING_ARRAY_TYPE__SELECT, null, msgs);
			msgs = basicSetSelect(newSelect, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.MAPPING_ARRAY_TYPE__SELECT, newSelect, newSelect));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP:
				return ((InternalEList)getTwoWayMappingGroup()).basicRemove(otherEnd, msgs);
			case ComponentPackage.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING:
				return basicSetTwoWayMapping(null, msgs);
			case ComponentPackage.MAPPING_ARRAY_TYPE__SELECT:
				return basicSetSelect(null, msgs);
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
			case ComponentPackage.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP:
				if (coreType) return getTwoWayMappingGroup();
				return ((FeatureMap.Internal)getTwoWayMappingGroup()).getWrapper();
			case ComponentPackage.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING:
				return getTwoWayMapping();
			case ComponentPackage.MAPPING_ARRAY_TYPE__SELECT:
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
			case ComponentPackage.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP:
				((FeatureMap.Internal)getTwoWayMappingGroup()).set(newValue);
				return;
			case ComponentPackage.MAPPING_ARRAY_TYPE__SELECT:
				setSelect((SelectType)newValue);
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
			case ComponentPackage.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP:
				getTwoWayMappingGroup().clear();
				return;
			case ComponentPackage.MAPPING_ARRAY_TYPE__SELECT:
				setSelect((SelectType)null);
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
			case ComponentPackage.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING_GROUP:
				return twoWayMappingGroup != null && !twoWayMappingGroup.isEmpty();
			case ComponentPackage.MAPPING_ARRAY_TYPE__TWO_WAY_MAPPING:
				return getTwoWayMapping() != null;
			case ComponentPackage.MAPPING_ARRAY_TYPE__SELECT:
				return select != null;
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
		result.append(')');
		return result.toString();
	}

} //MappingArrayTypeImpl
