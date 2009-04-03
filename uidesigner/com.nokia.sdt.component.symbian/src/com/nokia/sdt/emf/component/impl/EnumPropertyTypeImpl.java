/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component.impl;

import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.EnumPropertyType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enum Property Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EnumPropertyTypeImpl#getDefault <em>Default</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EnumPropertyTypeImpl#getExtendWithEnum <em>Extend With Enum</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.impl.EnumPropertyTypeImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EnumPropertyTypeImpl extends AbstractPropertyTypeImpl implements EnumPropertyType {
	/**
	 * The default value of the '{@link #getDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefault()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefault()
	 * @generated
	 * @ordered
	 */
	protected String default_ = DEFAULT_EDEFAULT;

	/**
	 * The default value of the '{@link #getExtendWithEnum() <em>Extend With Enum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendWithEnum()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTEND_WITH_ENUM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExtendWithEnum() <em>Extend With Enum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendWithEnum()
	 * @generated
	 * @ordered
	 */
	protected String extendWithEnum = EXTEND_WITH_ENUM_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnumPropertyTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ComponentPackage.Literals.ENUM_PROPERTY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefault() {
		return default_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefault(String newDefault) {
		String oldDefault = default_;
		default_ = newDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ENUM_PROPERTY_TYPE__DEFAULT, oldDefault, default_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtendWithEnum() {
		return extendWithEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendWithEnum(String newExtendWithEnum) {
		String oldExtendWithEnum = extendWithEnum;
		extendWithEnum = newExtendWithEnum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ENUM_PROPERTY_TYPE__EXTEND_WITH_ENUM, oldExtendWithEnum, extendWithEnum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ComponentPackage.ENUM_PROPERTY_TYPE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ComponentPackage.ENUM_PROPERTY_TYPE__DEFAULT:
				return getDefault();
			case ComponentPackage.ENUM_PROPERTY_TYPE__EXTEND_WITH_ENUM:
				return getExtendWithEnum();
			case ComponentPackage.ENUM_PROPERTY_TYPE__TYPE:
				return getType();
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
			case ComponentPackage.ENUM_PROPERTY_TYPE__DEFAULT:
				setDefault((String)newValue);
				return;
			case ComponentPackage.ENUM_PROPERTY_TYPE__EXTEND_WITH_ENUM:
				setExtendWithEnum((String)newValue);
				return;
			case ComponentPackage.ENUM_PROPERTY_TYPE__TYPE:
				setType((String)newValue);
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
			case ComponentPackage.ENUM_PROPERTY_TYPE__DEFAULT:
				setDefault(DEFAULT_EDEFAULT);
				return;
			case ComponentPackage.ENUM_PROPERTY_TYPE__EXTEND_WITH_ENUM:
				setExtendWithEnum(EXTEND_WITH_ENUM_EDEFAULT);
				return;
			case ComponentPackage.ENUM_PROPERTY_TYPE__TYPE:
				setType(TYPE_EDEFAULT);
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
			case ComponentPackage.ENUM_PROPERTY_TYPE__DEFAULT:
				return DEFAULT_EDEFAULT == null ? default_ != null : !DEFAULT_EDEFAULT.equals(default_);
			case ComponentPackage.ENUM_PROPERTY_TYPE__EXTEND_WITH_ENUM:
				return EXTEND_WITH_ENUM_EDEFAULT == null ? extendWithEnum != null : !EXTEND_WITH_ENUM_EDEFAULT.equals(extendWithEnum);
			case ComponentPackage.ENUM_PROPERTY_TYPE__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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
		result.append(" (default: ");
		result.append(default_);
		result.append(", extendWithEnum: ");
		result.append(extendWithEnum);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //EnumPropertyTypeImpl
