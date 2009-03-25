/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentDefinitionType;
import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.ComponentType;
import com.nokia.sdt.emf.component.CompoundPropertyDeclarationType;
import com.nokia.sdt.emf.component.EnumPropertyDeclarationType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import java.util.Collection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Definition Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentDefinitionTypeImpl#getCompoundPropertyDeclaration <em>Compound Property Declaration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentDefinitionTypeImpl#getEnumPropertyDeclaration <em>Enum Property Declaration</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.ComponentDefinitionTypeImpl#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentDefinitionTypeImpl extends EObjectImpl implements ComponentDefinitionType {
	/**
	 * The cached value of the '{@link #getCompoundPropertyDeclaration() <em>Compound Property Declaration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompoundPropertyDeclaration()
	 * @generated
	 * @ordered
	 */
	protected EList compoundPropertyDeclaration;

	/**
	 * The cached value of the '{@link #getEnumPropertyDeclaration() <em>Enum Property Declaration</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnumPropertyDeclaration()
	 * @generated
	 * @ordered
	 */
	protected EList enumPropertyDeclaration;

	/**
	 * The cached value of the '{@link #getComponent() <em>Component</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponent()
	 * @generated
	 * @ordered
	 */
	protected ComponentType component;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentDefinitionTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.COMPONENT_DEFINITION_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getCompoundPropertyDeclaration() {
		if (compoundPropertyDeclaration == null) {
			compoundPropertyDeclaration = new EObjectContainmentEList(CompoundPropertyDeclarationType.class, this, ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPOUND_PROPERTY_DECLARATION);
		}
		return compoundPropertyDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getEnumPropertyDeclaration() {
		if (enumPropertyDeclaration == null) {
			enumPropertyDeclaration = new EObjectContainmentEList(EnumPropertyDeclarationType.class, this, ComponentPackage.COMPONENT_DEFINITION_TYPE__ENUM_PROPERTY_DECLARATION);
		}
		return enumPropertyDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentType getComponent() {
		return component;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComponent(ComponentType newComponent, NotificationChain msgs) {
		ComponentType oldComponent = component;
		component = newComponent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPONENT, oldComponent, newComponent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(ComponentType newComponent) {
		if (newComponent != component) {
			NotificationChain msgs = null;
			if (component != null)
				msgs = ((InternalEObject)component).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPONENT, null, msgs);
			if (newComponent != null)
				msgs = ((InternalEObject)newComponent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPONENT, null, msgs);
			msgs = basicSetComponent(newComponent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPONENT, newComponent, newComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPOUND_PROPERTY_DECLARATION:
				return ((InternalEList)getCompoundPropertyDeclaration()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__ENUM_PROPERTY_DECLARATION:
				return ((InternalEList)getEnumPropertyDeclaration()).basicRemove(otherEnd, msgs);
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPONENT:
				return basicSetComponent(null, msgs);
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
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPOUND_PROPERTY_DECLARATION:
				return getCompoundPropertyDeclaration();
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__ENUM_PROPERTY_DECLARATION:
				return getEnumPropertyDeclaration();
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPONENT:
				return getComponent();
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
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPOUND_PROPERTY_DECLARATION:
				getCompoundPropertyDeclaration().clear();
				getCompoundPropertyDeclaration().addAll((Collection)newValue);
				return;
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__ENUM_PROPERTY_DECLARATION:
				getEnumPropertyDeclaration().clear();
				getEnumPropertyDeclaration().addAll((Collection)newValue);
				return;
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPONENT:
				setComponent((ComponentType)newValue);
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
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPOUND_PROPERTY_DECLARATION:
				getCompoundPropertyDeclaration().clear();
				return;
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__ENUM_PROPERTY_DECLARATION:
				getEnumPropertyDeclaration().clear();
				return;
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPONENT:
				setComponent((ComponentType)null);
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
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPOUND_PROPERTY_DECLARATION:
				return compoundPropertyDeclaration != null && !compoundPropertyDeclaration.isEmpty();
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__ENUM_PROPERTY_DECLARATION:
				return enumPropertyDeclaration != null && !enumPropertyDeclaration.isEmpty();
			case ComponentPackage.COMPONENT_DEFINITION_TYPE__COMPONENT:
				return component != null;
		}
		return super.eIsSet(featureID);
	}

} //ComponentDefinitionTypeImpl
