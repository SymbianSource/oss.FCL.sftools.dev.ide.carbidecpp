/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ChoiceType;
import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.SelectType;

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
 * An implementation of the model object '<em><b>Select Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SelectTypeImpl#getChoice <em>Choice</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SelectTypeImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SelectTypeImpl#getIsComponentInstanceOf <em>Is Component Instance Of</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SelectTypeImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.SelectTypeImpl#getPropertyExists <em>Property Exists</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SelectTypeImpl extends EObjectImpl implements SelectType {
	/**
	 * The cached value of the '{@link #getChoice() <em>Choice</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getChoice()
	 * @generated
	 * @ordered
	 */
    protected EList choice;

	/**
	 * The default value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
    protected static final String ATTRIBUTE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
    protected String attribute = ATTRIBUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #getIsComponentInstanceOf() <em>Is Component Instance Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsComponentInstanceOf()
	 * @generated
	 * @ordered
	 */
	protected static final String IS_COMPONENT_INSTANCE_OF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIsComponentInstanceOf() <em>Is Component Instance Of</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsComponentInstanceOf()
	 * @generated
	 * @ordered
	 */
	protected String isComponentInstanceOf = IS_COMPONENT_INSTANCE_OF_EDEFAULT;

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
	 * The default value of the '{@link #getPropertyExists() <em>Property Exists</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyExists()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_EXISTS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyExists() <em>Property Exists</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyExists()
	 * @generated
	 * @ordered
	 */
	protected String propertyExists = PROPERTY_EXISTS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected SelectTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return ComponentPackage.Literals.SELECT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getChoice() {
		if (choice == null) {
			choice = new EObjectContainmentEList(ChoiceType.class, this, ComponentPackage.SELECT_TYPE__CHOICE);
		}
		return choice;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setAttribute(String newAttribute) {
		String oldAttribute = attribute;
		attribute = newAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SELECT_TYPE__ATTRIBUTE, oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIsComponentInstanceOf() {
		return isComponentInstanceOf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsComponentInstanceOf(String newIsComponentInstanceOf) {
		String oldIsComponentInstanceOf = isComponentInstanceOf;
		isComponentInstanceOf = newIsComponentInstanceOf;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SELECT_TYPE__IS_COMPONENT_INSTANCE_OF, oldIsComponentInstanceOf, isComponentInstanceOf));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SELECT_TYPE__PROPERTY, oldProperty, property));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertyExists() {
		return propertyExists;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyExists(String newPropertyExists) {
		String oldPropertyExists = propertyExists;
		propertyExists = newPropertyExists;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.SELECT_TYPE__PROPERTY_EXISTS, oldPropertyExists, propertyExists));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ComponentPackage.SELECT_TYPE__CHOICE:
				return ((InternalEList)getChoice()).basicRemove(otherEnd, msgs);
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
			case ComponentPackage.SELECT_TYPE__CHOICE:
				return getChoice();
			case ComponentPackage.SELECT_TYPE__ATTRIBUTE:
				return getAttribute();
			case ComponentPackage.SELECT_TYPE__IS_COMPONENT_INSTANCE_OF:
				return getIsComponentInstanceOf();
			case ComponentPackage.SELECT_TYPE__PROPERTY:
				return getProperty();
			case ComponentPackage.SELECT_TYPE__PROPERTY_EXISTS:
				return getPropertyExists();
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
			case ComponentPackage.SELECT_TYPE__CHOICE:
				getChoice().clear();
				getChoice().addAll((Collection)newValue);
				return;
			case ComponentPackage.SELECT_TYPE__ATTRIBUTE:
				setAttribute((String)newValue);
				return;
			case ComponentPackage.SELECT_TYPE__IS_COMPONENT_INSTANCE_OF:
				setIsComponentInstanceOf((String)newValue);
				return;
			case ComponentPackage.SELECT_TYPE__PROPERTY:
				setProperty((String)newValue);
				return;
			case ComponentPackage.SELECT_TYPE__PROPERTY_EXISTS:
				setPropertyExists((String)newValue);
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
			case ComponentPackage.SELECT_TYPE__CHOICE:
				getChoice().clear();
				return;
			case ComponentPackage.SELECT_TYPE__ATTRIBUTE:
				setAttribute(ATTRIBUTE_EDEFAULT);
				return;
			case ComponentPackage.SELECT_TYPE__IS_COMPONENT_INSTANCE_OF:
				setIsComponentInstanceOf(IS_COMPONENT_INSTANCE_OF_EDEFAULT);
				return;
			case ComponentPackage.SELECT_TYPE__PROPERTY:
				setProperty(PROPERTY_EDEFAULT);
				return;
			case ComponentPackage.SELECT_TYPE__PROPERTY_EXISTS:
				setPropertyExists(PROPERTY_EXISTS_EDEFAULT);
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
			case ComponentPackage.SELECT_TYPE__CHOICE:
				return choice != null && !choice.isEmpty();
			case ComponentPackage.SELECT_TYPE__ATTRIBUTE:
				return ATTRIBUTE_EDEFAULT == null ? attribute != null : !ATTRIBUTE_EDEFAULT.equals(attribute);
			case ComponentPackage.SELECT_TYPE__IS_COMPONENT_INSTANCE_OF:
				return IS_COMPONENT_INSTANCE_OF_EDEFAULT == null ? isComponentInstanceOf != null : !IS_COMPONENT_INSTANCE_OF_EDEFAULT.equals(isComponentInstanceOf);
			case ComponentPackage.SELECT_TYPE__PROPERTY:
				return PROPERTY_EDEFAULT == null ? property != null : !PROPERTY_EDEFAULT.equals(property);
			case ComponentPackage.SELECT_TYPE__PROPERTY_EXISTS:
				return PROPERTY_EXISTS_EDEFAULT == null ? propertyExists != null : !PROPERTY_EXISTS_EDEFAULT.equals(propertyExists);
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
		result.append(" (attribute: ");
		result.append(attribute);
		result.append(", isComponentInstanceOf: ");
		result.append(isComponentInstanceOf);
		result.append(", property: ");
		result.append(property);
		result.append(", propertyExists: ");
		result.append(propertyExists);
		result.append(')');
		return result.toString();
	}

} //SelectTypeImpl
