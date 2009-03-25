/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.dm.impl;

import com.nokia.sdt.emf.dm.DmPackage;
import com.nokia.sdt.emf.dm.IArrayMappings;
import com.nokia.sdt.emf.dm.IEnumMappings;
import com.nokia.sdt.emf.dm.IResourceMappings;
import com.nokia.sdt.emf.dm.ISourceGenMappingState;

import com.nokia.sdt.emf.dm.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ISource Gen Mapping State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.ISourceGenMappingStateImpl#getResourceMappings <em>Resource Mappings</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.ISourceGenMappingStateImpl#getEnumMappings <em>Enum Mappings</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.dm.impl.ISourceGenMappingStateImpl#getArrayMappings <em>Array Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ISourceGenMappingStateImpl extends EObjectImpl implements ISourceGenMappingState {
	/**
	 * The cached value of the '{@link #getResourceMappings() <em>Resource Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceMappings()
	 * @generated
	 * @ordered
	 */
	protected IResourceMappings resourceMappings = null;

	/**
	 * The cached value of the '{@link #getEnumMappings() <em>Enum Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumMappings()
	 * @generated
	 * @ordered
	 */
	protected IEnumMappings enumMappings = null;

	/**
	 * The cached value of the '{@link #getArrayMappings() <em>Array Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArrayMappings()
	 * @generated
	 * @ordered
	 */
	protected IArrayMappings arrayMappings = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ISourceGenMappingStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DmPackage.Literals.ISOURCE_GEN_MAPPING_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IResourceMappings getResourceMappings() {
		return resourceMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourceMappings(IResourceMappings newResourceMappings, NotificationChain msgs) {
		IResourceMappings oldResourceMappings = resourceMappings;
		resourceMappings = newResourceMappings;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS, oldResourceMappings, newResourceMappings);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceMappings(IResourceMappings newResourceMappings) {
		if (newResourceMappings != resourceMappings) {
			NotificationChain msgs = null;
			if (resourceMappings != null)
				msgs = ((InternalEObject)resourceMappings).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DmPackage.ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS, null, msgs);
			if (newResourceMappings != null)
				msgs = ((InternalEObject)newResourceMappings).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DmPackage.ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS, null, msgs);
			msgs = basicSetResourceMappings(newResourceMappings, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS, newResourceMappings, newResourceMappings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IEnumMappings getEnumMappings() {
		return enumMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnumMappings(IEnumMappings newEnumMappings, NotificationChain msgs) {
		IEnumMappings oldEnumMappings = enumMappings;
		enumMappings = newEnumMappings;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS, oldEnumMappings, newEnumMappings);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnumMappings(IEnumMappings newEnumMappings) {
		if (newEnumMappings != enumMappings) {
			NotificationChain msgs = null;
			if (enumMappings != null)
				msgs = ((InternalEObject)enumMappings).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DmPackage.ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS, null, msgs);
			if (newEnumMappings != null)
				msgs = ((InternalEObject)newEnumMappings).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DmPackage.ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS, null, msgs);
			msgs = basicSetEnumMappings(newEnumMappings, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS, newEnumMappings, newEnumMappings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IArrayMappings getArrayMappings() {
		return arrayMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArrayMappings(IArrayMappings newArrayMappings, NotificationChain msgs) {
		IArrayMappings oldArrayMappings = arrayMappings;
		arrayMappings = newArrayMappings;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DmPackage.ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS, oldArrayMappings, newArrayMappings);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArrayMappings(IArrayMappings newArrayMappings) {
		if (newArrayMappings != arrayMappings) {
			NotificationChain msgs = null;
			if (arrayMappings != null)
				msgs = ((InternalEObject)arrayMappings).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DmPackage.ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS, null, msgs);
			if (newArrayMappings != null)
				msgs = ((InternalEObject)newArrayMappings).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DmPackage.ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS, null, msgs);
			msgs = basicSetArrayMappings(newArrayMappings, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DmPackage.ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS, newArrayMappings, newArrayMappings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS:
				return basicSetResourceMappings(null, msgs);
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS:
				return basicSetEnumMappings(null, msgs);
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS:
				return basicSetArrayMappings(null, msgs);
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
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS:
				return getResourceMappings();
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS:
				return getEnumMappings();
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS:
				return getArrayMappings();
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
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS:
				setResourceMappings((IResourceMappings)newValue);
				return;
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS:
				setEnumMappings((IEnumMappings)newValue);
				return;
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS:
				setArrayMappings((IArrayMappings)newValue);
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
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS:
				setResourceMappings((IResourceMappings)null);
				return;
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS:
				setEnumMappings((IEnumMappings)null);
				return;
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS:
				setArrayMappings((IArrayMappings)null);
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
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__RESOURCE_MAPPINGS:
				return resourceMappings != null;
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ENUM_MAPPINGS:
				return enumMappings != null;
			case DmPackage.ISOURCE_GEN_MAPPING_STATE__ARRAY_MAPPINGS:
				return arrayMappings != null;
		}
		return super.eIsSet(featureID);
	}

} //ISourceGenMappingStateImpl
